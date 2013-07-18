/*     */ package org.faithfarm;
/*     */ 
/*     */ /*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.logging.Logger;

/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;

/*     */ import org.faithfarm.dataobjects.SearchParameter;
/*     */ import org.faithfarm.datawriters.ApplicationDao;
/*     */ import org.faithfarm.datawriters.PersonDao;
/*     */ import org.faithfarm.utilities.FFSession;
/*     */ import org.faithfarm.utilities.HTMLBuilder;
/*     */ import org.faithfarm.utilities.XMLParser;
/*     */ import org.faithfarm.validators.Validator;
/*     */ import org.w3c.dom.NodeList;

import com.google.appengine.api.datastore.DatastoreService;
/*     */ import com.google.appengine.api.datastore.DatastoreServiceFactory;
/*     */ import com.google.appengine.api.datastore.Entity;
/*     */ import com.google.appengine.api.datastore.Key;
/*     */ import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
/*     */ import com.google.appengine.api.users.User;
/*     */ import com.google.appengine.api.users.UserService;
/*     */ import com.google.appengine.api.users.UserServiceFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HelpDeskServlet extends BaseServlet
/*     */ {
/*  46 */   private static final Logger logger = Logger.getLogger(HelpDeskServlet.class.getCanonicalName());
/*  47 */   private static final HTMLBuilder html = new HTMLBuilder();
/*  48 */   private static final PersonDao dao = new PersonDao();
/*  49 */   private static final XMLParser xml = new XMLParser();
/*  50 */   private static final Validator validator = new Validator();
/*     */ 
/*  52 */   private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
/*     */ 
/*     */   protected void doGet(HttpServletRequest req, HttpServletResponse resp)
/*     */     throws ServletException, IOException
/*     */   {
/*  57 */     if (FFSession.needsRedirect(req, resp)) {
/*  58 */       return;
/*     */     }
/*     */ 
/*  61 */     UserService userService = UserServiceFactory.getUserService();
/*  62 */     User user = userService.getCurrentUser();
/*     */ 
/*  64 */     String farmBase = (String)req.getSession().getAttribute("farmBase");
/*  65 */     String url = "/jsp/help/results.jsp";
/*     */ 
/*  67 */     String action = req.getParameter("action");
/*  68 */     ArrayList parameters = new ArrayList();
/*     */ 
/*  70 */     if ("Filter".equals(action)) {
/*  71 */       String ticketDate = html.cleanData(req.getParameter("ticketDate"));
/*  72 */       String status = html.cleanData(req.getParameter("status"));
/*  73 */       String priority = html.cleanData(req.getParameter("priority"));
/*  74 */       String farm = html.cleanData(req.getParameter("farmLocation"));
/*  75 */       SearchParameter searchParam = new SearchParameter("ticketDate", ticketDate, Query.FilterOperator.EQUAL);
/*  76 */       parameters.add(searchParam);
/*  77 */       searchParam = new SearchParameter("status", status, Query.FilterOperator.EQUAL);
/*  78 */       parameters.add(searchParam);
/*  79 */       searchParam = new SearchParameter("priority", priority, Query.FilterOperator.EQUAL);
/*  80 */       parameters.add(searchParam);
/*  81 */       searchParam = new SearchParameter("farmLocation", farm, Query.FilterOperator.EQUAL);
/*  82 */       parameters.add(searchParam);
/*     */ 
/*  84 */       ArrayList results = ApplicationDao.getEntities("HelpDeskTicket", parameters, null, null, farm);
/*     */ 
/*  86 */       req.setAttribute("results", results);
/*  87 */       req.getSession().setAttribute("ticketDate", ticketDate);
/*  88 */       req.getSession().setAttribute("status", status);
/*  89 */       req.getSession().setAttribute("priority", priority);
/*  90 */       req.getSession().setAttribute("farmLocation", farm);
/*     */     }
/*  93 */     else if ("Create".equals(action)) {
/*  94 */       NodeList xmlHelpDeskTicket = xml.getEntityProperties("HelpDeskTicket", req);
/*  95 */       Entity HelpDeskTicket = xml.xmlToEntity("HelpDeskTicket", req);
/*  96 */       HelpDeskTicket.setProperty("status", "Pending");
/*  97 */       req.setAttribute("xmlHelpDeskTicket", xmlHelpDeskTicket);
/*  98 */       req.setAttribute("HelpDeskTicket", HelpDeskTicket);
/*  99 */       url = "/jsp/help/index.jsp";
/* 100 */     } else if ("Save".equals(action)) {
/* 101 */       NodeList xmlHelpDeskTicket = xml.getEntityProperties("HelpDeskTicket", req);
/* 102 */       Entity HelpDeskTicket = xml.xmlToEntity("HelpDeskTicket", req);
/* 103 */       if (validator.validateForm(xmlHelpDeskTicket, req)) {
/* 104 */         Date now = new Date();
/* 105 */         int julianDay = Calendar.getInstance().get(
/* 106 */           6);
/* 107 */         String ticketNumber = julianDay + 
/* 108 */           now.getHours() + now.getMinutes() + 
/* 109 */           now.getSeconds()+"";
/* 110 */         HelpDeskTicket.setProperty("ticketNumber", ticketNumber);
/* 111 */         ApplicationDao.createOrUpdateEntity(HelpDeskTicket, user);
/* 112 */         refreshResults(req);
/*     */       }
/*     */       else {
/* 115 */         req.setAttribute("xmlHelpDeskTicket", xmlHelpDeskTicket);
/* 116 */         req.setAttribute("HelpDeskTicket", HelpDeskTicket);
/* 117 */         url = "/jsp/help/index.jsp";
/*     */       }
/*     */ 
/* 120 */       ArrayList results = ApplicationDao.getEntities("HelpDeskTicket", parameters, null, null, farmBase);
/* 121 */       req.setAttribute("results", results);
/*     */     }
/* 123 */     else if ("Edit".equals(action)) {
/* 124 */       String sKey = req.getParameter("ticketKey");
/* 125 */       Key key = KeyFactory.stringToKey(sKey);
/* 126 */       Entity Ticket = ApplicationDao.getEntity(key);
/*     */ 
/* 128 */       NodeList xmlHelpDeskTicket = xml.getEntityProperties("HelpDeskTicket", req);
/* 129 */       req.setAttribute("xmlHelpDeskTicket", xmlHelpDeskTicket);
/* 130 */       req.setAttribute("HelpDeskTicket", Ticket);
/* 131 */       url = "/jsp/help/index.jsp?edit=Y";
/* 132 */     } else if ("Save Changes".equals(action)) {
/* 133 */       String sKey = req.getParameter("ticketKey");
/* 134 */       Key key = KeyFactory.stringToKey(sKey);
/* 135 */       Entity Ticket = ApplicationDao.getEntity(key);
/* 136 */       String prevStatus = Ticket.getProperty("status").toString();
/* 137 */       Ticket = xml.updateEntity(Ticket, req);
/* 138 */       String currStatus = Ticket.getProperty("status").toString();
/*     */ 
/* 140 */       if (("Resolved".equals(currStatus)) && (!"Resolved".equals(prevStatus))) {
/* 141 */         Ticket.setProperty("resolvedDate", new Date());
/*     */       }
/* 143 */       NodeList xmlHelpDeskTicket = xml.getEntityProperties("HelpDeskTicket", req);
/* 144 */       ArrayList messages = new ArrayList();
/*     */ 
/* 146 */       if (validator.validateForm(xmlHelpDeskTicket, req)) {
/* 147 */         ApplicationDao.createOrUpdateEntity(Ticket, user);
/* 148 */         refreshResults(req);
/*     */       }
/*     */       else {
/* 151 */         req.setAttribute("xmlHelpDeskTicket", xmlHelpDeskTicket);
/* 152 */         req.setAttribute("HelpDeskTicket", Ticket);
/* 153 */         url = "/jsp/help/index.jsp";
/*     */       }
/* 155 */     } else if ("Print".equals(action)) {
/* 156 */       String sKey = req.getParameter("ticketKey");
/* 157 */       Key key = KeyFactory.stringToKey(sKey);
/* 158 */       Entity Ticket = ApplicationDao.getEntity(key);
/*     */ 
/* 160 */       NodeList xmlHelpDeskTicket = xml.getEntityProperties("HelpDeskTicket", req);
/* 161 */       req.setAttribute("xmlHelpDeskTicket", xmlHelpDeskTicket);
/* 162 */       req.setAttribute("HelpDeskTicket", Ticket);
/* 163 */       url = "/jsp/help/print.jsp";
/*     */     }
/* 165 */     req.getRequestDispatcher(req.getContextPath() + url).forward(req, resp);
/*     */   }
/*     */ 
/*     */   protected void doPost(HttpServletRequest req, HttpServletResponse resp)
/*     */     throws ServletException, IOException
/*     */   {
/* 171 */     doGet(req, resp);
/*     */   }
/*     */ 
/*     */   private void refreshResults(HttpServletRequest req) {
/* 175 */     ArrayList parameters = new ArrayList();
/*     */ 
/* 177 */     String ticketDate = (String)req.getSession().getAttribute("ticketDate");
/* 178 */     String status = (String)req.getSession().getAttribute("status");
/* 179 */     String priority = (String)req.getSession().getAttribute("priority");
/* 180 */     String farmLocation = (String)req.getSession().getAttribute("farmLocation");
/* 181 */     SearchParameter searchParam = new SearchParameter("ticketDate", ticketDate, Query.FilterOperator.EQUAL);
/* 182 */     parameters.add(searchParam);
/* 183 */     searchParam = new SearchParameter("status", status, Query.FilterOperator.EQUAL);
/* 184 */     parameters.add(searchParam);
/* 185 */     searchParam = new SearchParameter("priority", priority, Query.FilterOperator.EQUAL);
/* 186 */     parameters.add(searchParam);
/* 187 */     searchParam = new SearchParameter("farmLocation", farmLocation, Query.FilterOperator.EQUAL);
/* 188 */     parameters.add(searchParam);
/*     */ 
/* 190 */     ArrayList results = ApplicationDao.getEntities("HelpDeskTicket", parameters, null, null, farmLocation);
/* 191 */     req.setAttribute("results", results);
/*     */   }
/*     */ 
/*     */   private boolean validateClose(HttpServletRequest req)
/*     */   {
/* 197 */     String status = html.cleanData(req.getParameter("status"));
/* 198 */     String resolvedDate = html.cleanData(req.getParameter("resolvedDate"));
/* 199 */     if (("Resolved".equals(status)) && 
/* 200 */       (resolvedDate.equals(""))) {
/* 201 */       return false;
/*     */     }
/* 203 */     return true;
/*     */   }
/*     */ }

/* Location:           C:\development\workspace\faithfarmoss\WEB-INF\classes\
 * Qualified Name:     org.faithfarm.HelpDeskServlet
 * JD-Core Version:    0.6.2
 */