/*     */ package org.faithfarm.reporting;
/*     */ 
/*     */ /*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.logging.Logger;

/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;

/*     */ import org.faithfarm.BaseServlet;
/*     */ import org.faithfarm.CustomerServlet;
/*     */ import org.faithfarm.dataobjects.SearchParameter;
/*     */ import org.faithfarm.datawriters.ApplicationDao;
/*     */ import org.faithfarm.datawriters.CourseDao;
/*     */ import org.faithfarm.datawriters.PersonDao;
/*     */ import org.faithfarm.mail.Mailer;
/*     */ import org.faithfarm.utilities.CSVParser;
/*     */ import org.faithfarm.utilities.FFSession;
/*     */ import org.faithfarm.utilities.HTMLBuilder;
/*     */ import org.faithfarm.utilities.XMLParser;

import com.google.appengine.api.blobstore.BlobstoreService;
/*     */ import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
/*     */ import com.google.appengine.api.datastore.DatastoreService;
/*     */ import com.google.appengine.api.datastore.DatastoreServiceFactory;
/*     */ import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
/*     */ import com.google.appengine.api.users.User;
/*     */ import com.google.appengine.api.users.UserService;
/*     */ import com.google.appengine.api.users.UserServiceFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MainServlet extends BaseServlet
/*     */ {
/*  37 */   private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
/*     */ 
/*  39 */   private static final Logger logger = Logger.getLogger(CustomerServlet.class
/*  40 */     .getCanonicalName());
/*     */ 
/*  41 */   private static final HTMLBuilder html = new HTMLBuilder();
/*  42 */   private static final CSVParser csv = new CSVParser();
/*  43 */   private static final XMLParser xml = new XMLParser();
/*  44 */   private static final PersonDao dao = new PersonDao();
/*  45 */   private static final CourseDao cDao = new CourseDao();
/*  46 */   private static final ApplicationDao appDao = new ApplicationDao();
/*  47 */   private static final Mailer mailer = new Mailer();
/*     */ 
/*  49 */   private static final DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
/*     */ 
/*     */   protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
/*     */   {
/*  53 */     super.doGet(req, resp);
/*     */ 
/*  55 */     String farmBase = (String)req.getSession().getAttribute("farmBase");
/*     */ 
/*  57 */     if (FFSession.needsRedirect(req, resp)) {
/*  58 */       return;
/*     */     }
/*     */ 
/*  61 */     UserService userService = UserServiceFactory.getUserService();
/*  62 */     User user = userService.getCurrentUser();
/*     */ 
/*  64 */     String report = req.getParameter("RPT");
/*  65 */     String url = "";
/*     */ 
/*  67 */     if ("breakdown".equals(report)) {
/*  68 */       url = "/jsp/reports/class_breakdown.jsp";
/*     */ 
/*  71 */       for (int i = 0; i < 7; i++) {
/*  72 */         ArrayList parameters = new ArrayList();
/*  73 */         parameters.add(new SearchParameter("currentCourse", i+"", Query.FilterOperator.EQUAL));
/*  74 */         parameters.add(new SearchParameter("personType", "Resident", Query.FilterOperator.EQUAL));
/*  75 */         parameters.add(new SearchParameter("personStatus", "Active", Query.FilterOperator.EQUAL));
/*     */ 
/*  77 */         ArrayList results = ApplicationDao.getEntities("Person", parameters, null, null, farmBase);
/*  78 */         if (results == null) results = new ArrayList();
/*  79 */         req.getSession().setAttribute("class_" + i, Integer.valueOf(results.size()));
/*     */       }
/*     */     }
/*     */ 
/*  83 */     if ("forecast".equals(report)) {
/*  84 */       String[] smonth = { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };
/*     */ 
/*  87 */       for (int month = 0; month < 12; month++) {
/*  88 */         ArrayList parameters = new ArrayList();
/*  89 */         parameters.add(new SearchParameter("Month", smonth[month], Query.FilterOperator.EQUAL));
/*  90 */         parameters.add(new SearchParameter("Year", "2012", Query.FilterOperator.EQUAL));
/*  91 */         ArrayList results1 = ApplicationDao.getEntities("ActualDailySalesYTD", parameters, null, null, farmBase);
/*  92 */         ArrayList results2 = ApplicationDao.getEntities("BudgetedDailySalesYTD", parameters, null, null, farmBase);
/*  93 */         Entity listActualYtd = (Entity)results1.get(0);
/*  94 */         Entity listBudgetYtd = (Entity)results2.get(0);
/*  95 */         double actualYtd = 0.0D;
/*  96 */         double budgetYtd = 0.0D;
/*     */ 
/*  98 */         for (int day = 1; day < 32; day++) {
/*  99 */           double value1 = ((Double)listActualYtd.getProperty("Day_" + day)).doubleValue();
/* 100 */           double value2 = ((Double)listBudgetYtd.getProperty("Day_" + day)).doubleValue();
/* 101 */           actualYtd += value1;
/* 102 */           budgetYtd += value2;
/*     */         }
/*     */ 
/* 105 */         req.setAttribute("actual_Month_" + month, Double.valueOf(actualYtd));
/* 106 */         req.setAttribute("budget_Month_" + month, Double.valueOf(budgetYtd));
/*     */       }
/* 108 */       url = "/jsp/reports/forecast/index.jsp";
/*     */     }
/*     */ 
/* 111 */     req.getRequestDispatcher(req.getContextPath() + url).forward(req, resp);
/*     */   }
/*     */ 
/*     */   protected void doPost(HttpServletRequest req, HttpServletResponse resp)
/*     */     throws ServletException, IOException
/*     */   {
/* 118 */     doGet(req, resp);
/*     */   }
/*     */ }

/* Location:           C:\development\workspace\faithfarmoss\WEB-INF\classes\
 * Qualified Name:     org.faithfarm.reporting.MainServlet
 * JD-Core Version:    0.6.2
 */