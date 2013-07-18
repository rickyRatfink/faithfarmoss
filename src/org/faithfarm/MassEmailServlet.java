/*     */ package org.faithfarm;
/*     */ 
/*     */ /*     */ import java.io.IOException;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.logging.Logger;

/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;

/*     */ import org.faithfarm.utilities.FFSession;

import com.google.appengine.api.datastore.DatastoreService;
/*     */ import com.google.appengine.api.datastore.DatastoreServiceFactory;
/*     */ import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
/*     */ import com.google.appengine.api.datastore.Query;
/*     */ import com.google.appengine.api.taskqueue.Queue;
/*     */ import com.google.appengine.api.taskqueue.QueueFactory;
/*     */ import com.google.appengine.api.taskqueue.TaskOptions;
/*     */ import com.google.appengine.api.users.User;
/*     */ import com.google.appengine.api.users.UserService;
/*     */ import com.google.appengine.api.users.UserServiceFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MassEmailServlet extends BaseServlet
/*     */ {
/*     */   private static final long serialVersionUID = 3148377863795912183L;
/*  38 */   private static final DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
/*  39 */   private static final Logger logger = Logger.getLogger(MassEmailServlet.class.getCanonicalName());
/*     */ 
/*     */   protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
/*  42 */     if (FFSession.needsRedirect(req, resp)) {
/*  43 */       return;
/*     */     }
/*     */ 
/*  46 */     List errors = new ArrayList();
/*     */ 
/*  48 */     UserService userService = UserServiceFactory.getUserService();
/*  49 */     User user = userService.getCurrentUser();
/*  50 */     HttpSession session = req.getSession(true);
/*     */ 
/*  52 */     String action = req.getParameter("action");
/*  53 */     if ((action == null) || (action.equals(""))) {
/*  54 */       req.getRequestDispatcher(req.getContextPath() + "/jsp/massemail/index.jsp").forward(req, resp);
/*  55 */     } else if (action.equals("search"))
/*     */     {
/*  57 */       List results = new ArrayList();
/*  58 */       List results_final = new ArrayList();
/*     */ 
/*  60 */       Date start_date = new Date();
/*  61 */       Date end_date = new Date();
/*  62 */       SimpleDateFormat fromFormat = new SimpleDateFormat("MM/dd/yyyy");
/*  63 */       String personType = req.getParameter("personType");
/*  64 */       String startDate = req.getParameter("startDate");
/*  65 */       String endDate = req.getParameter("endDate");
/*     */ 
/*  67 */       if ((personType == null) || (personType.equals(""))) {
/*  68 */         errors.add("You must select a person type.");
/*     */       }
/*  70 */       if ((startDate == null) || (startDate.equals(""))) {
/*  71 */         errors.add("You must select a start date.");
/*     */       }
/*  73 */       if ((endDate == null) || (endDate.equals(""))) {
/*  74 */         errors.add("You must select an end date.");
/*     */       }
/*  76 */       if ((endDate != null) && (!endDate.equals("")) && (startDate != null) && (!startDate.equals(""))) {
/*     */         try
/*     */         {
/*  79 */           end_date = fromFormat.parse(endDate);
/*  80 */           start_date = fromFormat.parse(startDate);
/*     */         } catch (ParseException e) {
/*  82 */           errors.add("Problem parsing start or end date.");
/*     */         }
/*     */       }
/*  85 */       if (errors.size() > 0) {
/*  86 */         req.setAttribute("errors", errors);
/*  87 */         req.getRequestDispatcher(req.getContextPath() + "/jsp/massemail/index.jsp").forward(req, resp);
/*     */       }
/*     */ 
/*  91 */       logger.severe("Start date: " + start_date);
/*  92 */       logger.severe("End date: " + end_date);
/*  93 */       logger.severe("person type: " + personType);
/*  94 */       end_date.setHours(23);
/*  95 */       end_date.setMinutes(59);
/*  96 */       end_date.setSeconds(59);
/*     */ 
/*  99 */       Query.FilterPredicate typeFilter = new Query.FilterPredicate("personType", Query.FilterOperator.EQUAL, personType);
/* 100 */       Query.FilterPredicate startDateFilter = new Query.FilterPredicate("creationDate", Query.FilterOperator.GREATER_THAN_OR_EQUAL, start_date);
/* 101 */       Query.FilterPredicate endDateFilter = new Query.FilterPredicate("creationDate", Query.FilterOperator.LESS_THAN_OR_EQUAL, end_date);
/*     */ 
/* 105 */       Query query = new Query("Person");
/* 106 */       query.setFilter(Query.CompositeFilterOperator.and(new Query.Filter[] { typeFilter, startDateFilter, endDateFilter }));
/*     */ 
/* 109 */       results = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(500));
/* 110 */       logger.severe("results count: " + results.size());
/* 111 */       //for (Entity result : results) {
	for (Iterator it = results.iterator(); it.hasNext();) {
		Entity result = (Entity) it.next();

/* 112 */         List tmpList = new ArrayList();
/* 113 */         Query emailQuery = new Query("Address");
/* 114 */         emailQuery.setAncestor(result.getKey());
/* 115 */         tmpList = datastore.prepare(emailQuery).asList(FetchOptions.Builder.withLimit(1));
/* 116 */         if (tmpList.size() > 0) {
/* 117 */           String email = (String)((Entity)tmpList.get(0)).getProperty("email");
/* 118 */           if ((email != null) && (!email.equals(""))) {
/* 119 */             result.setProperty("email", email);
/* 120 */             results_final.add(result);
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 125 */       req.setAttribute("results", results_final);
/* 126 */       req.getRequestDispatcher(req.getContextPath() + "/jsp/massemail/results.jsp").forward(req, resp);
/*     */     }
/* 129 */     else if (action.equals("sendmail")) {
/* 130 */       Queue queue = QueueFactory.getDefaultQueue();
/* 131 */       String email_text = req.getParameter("email_text");
/* 132 */       String subject = req.getParameter("subject");
/* 133 */       String[] emails = req.getParameterValues("emails[]");
/*     */ 		int i=0;
/* 135 */       for (i = 0; i < emails.length; i++) {
/* 136 */         queue.add(TaskOptions.Builder.withParam("data", emails[i]).param("subject", subject).param("msg_text", email_text).url("/worker/massemail"));
/*     */       }
/* 138 */       req.setAttribute("count", Integer.valueOf(i));
/* 139 */       req.getRequestDispatcher(req.getContextPath() + "/jsp/massemail/success.jsp").forward(req, resp);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doPost(HttpServletRequest req, HttpServletResponse resp)
/*     */     throws IOException, ServletException
/*     */   {
/* 147 */     doGet(req, resp);
/*     */   }
/*     */ }

/* Location:           C:\development\workspace\faithfarmoss\WEB-INF\classes\
 * Qualified Name:     org.faithfarm.MassEmailServlet
 * JD-Core Version:    0.6.2
 */