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
/*     */ public class CashDonationServlet extends BaseServlet
/*     */ {
/*  46 */   private static final Logger logger = Logger.getLogger(CashDonationServlet.class.getCanonicalName());
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
/*  65 */     String url = "/jsp/cashDonations/results.jsp";
/*     */ 
/*  67 */     String action = req.getParameter("action");
/*  68 */     ArrayList parameters = new ArrayList();
/*     */ 
/*  70 */     if ("Filter".equals(action)) {
/*  71 */       String confirmationNumber = html.cleanData(req.getParameter("confirmationNumber"));
/*  72 */       String donationDate = html.cleanData(req.getParameter("donationDate"));
/*  73 */       String paymentMethod = html.cleanData(req.getParameter("paymentMethod"));
/*  74 */       String firstName = html.cleanData(req.getParameter("firstName"));
/*  75 */       String lastName = html.cleanData(req.getParameter("lastName"));
/*  76 */       String farm = html.cleanData(req.getParameter("farmLocation"));
/*     */ 
/*  78 */       SearchParameter searchParam = new SearchParameter("paymentMethod", paymentMethod, Query.FilterOperator.EQUAL);
/*  79 */       parameters.add(searchParam);
/*  80 */       searchParam = new SearchParameter("confirmationNumber", confirmationNumber, Query.FilterOperator.EQUAL);
/*  81 */       parameters.add(searchParam);
/*  82 */       searchParam = new SearchParameter("donationDate", donationDate, Query.FilterOperator.EQUAL);
/*  83 */       parameters.add(searchParam);
/*  84 */       searchParam = new SearchParameter("firstName", firstName, Query.FilterOperator.EQUAL);
/*  85 */       parameters.add(searchParam);
/*  86 */       searchParam = new SearchParameter("lastName", lastName, Query.FilterOperator.EQUAL);
/*  87 */       parameters.add(searchParam);
/*     */ 
/*  89 */       ArrayList results = ApplicationDao.getEntities("MoneyDonation", parameters, null, null, farm);
/*  90 */       req.setAttribute("results", results);
/*     */ 
/*  92 */       req.getSession().setAttribute("confirmationNumber", confirmationNumber);
/*  93 */       req.getSession().setAttribute("donationDate", donationDate);
/*  94 */       req.getSession().setAttribute("paymentMethod", paymentMethod);
/*  95 */       req.getSession().setAttribute("firstName", firstName);
/*  96 */       req.getSession().setAttribute("lastName", lastName);
/*  97 */       req.getSession().setAttribute("farmLocation", farm);
/*     */     }
/* 100 */     else if ("Create".equals(action)) {
/* 101 */       NodeList xmlMoneyDonation = xml.getEntityProperties("", req);
/* 102 */       Entity MoneyDonation = xml.xmlToEntity("MoneyDonation", req);
/* 103 */       MoneyDonation.setProperty("status", "Pending");
/* 104 */       req.setAttribute("xmlMoneyDonation", xmlMoneyDonation);
/* 105 */       req.setAttribute("MoneyDonation", MoneyDonation);
/* 106 */       url = "/jsp/cashDonations/index.jsp";
/* 107 */     } else if ("Save".equals(action)) {
/* 108 */       NodeList xmlMoneyDonation = xml.getEntityProperties("MoneyDonation", req);
/* 109 */       Entity MoneyDonation = xml.xmlToEntity("MoneyDonation", req);
/* 110 */       if (validator.validateForm(xmlMoneyDonation, req)) {
/* 111 */         Date now = new Date();
/* 112 */         int julianDay = Calendar.getInstance().get(
/* 113 */           6);
/* 114 */         String ticketNumber = julianDay + 
/* 115 */           now.getHours() + now.getMinutes() + 
/* 116 */           now.getSeconds()+"";
/* 117 */         MoneyDonation.setProperty("ticketNumber", ticketNumber);
/* 118 */         ApplicationDao.createOrUpdateEntity(MoneyDonation, user);
/* 119 */         refreshResults(req);
/*     */       }
/*     */       else {
/* 122 */         req.setAttribute("xmlMoneyDonation", xmlMoneyDonation);
/* 123 */         req.setAttribute("MoneyDonation", MoneyDonation);
/* 124 */         url = "/jsp/cashDonations/index.jsp";
/*     */       }
/*     */ 
/* 127 */       ArrayList results = ApplicationDao.getEntities("MoneyDonation", parameters, null, null, farmBase);
/* 128 */       req.setAttribute("results", results);
/*     */     }
/* 130 */     else if ("Edit".equals(action)) {
/* 131 */       String sKey = req.getParameter("moneyDonationKey");
/* 132 */       Key key = KeyFactory.stringToKey(sKey);
/* 133 */       Entity Ticket = ApplicationDao.getEntity(key);
/*     */ 
/* 135 */       NodeList xmlMoneyDonation = xml.getEntityProperties("MoneyDonation", req);
/* 136 */       req.setAttribute("xmlMoneyDonation", xmlMoneyDonation);
/* 137 */       req.setAttribute("MoneyDonation", Ticket);
/* 138 */       url = "/jsp/cashDonations/index.jsp?edit=Y";
/* 139 */     } else if ("Save Changes".equals(action)) {
/* 140 */       String sKey = req.getParameter("ticketKey");
/* 141 */       Key key = KeyFactory.stringToKey(sKey);
/* 142 */       Entity Ticket = ApplicationDao.getEntity(key);
/* 143 */       String prevStatus = Ticket.getProperty("status").toString();
/* 144 */       Ticket = xml.updateEntity(Ticket, req);
/* 145 */       String currStatus = Ticket.getProperty("status").toString();
/*     */ 
/* 147 */       if (("Resolved".equals(currStatus)) && (!"Resolved".equals(prevStatus))) {
/* 148 */         Ticket.setProperty("resolvedDate", new Date());
/*     */       }
/* 150 */       NodeList xmlMoneyDonation = xml.getEntityProperties("MoneyDonation", req);
/* 151 */       ArrayList messages = new ArrayList();
/*     */ 
/* 153 */       if (validator.validateForm(xmlMoneyDonation, req)) {
/* 154 */         ApplicationDao.createOrUpdateEntity(Ticket, user);
/* 155 */         refreshResults(req);
/*     */       }
/*     */       else {
/* 158 */         req.setAttribute("xmlMoneyDonation", xmlMoneyDonation);
/* 159 */         req.setAttribute("MoneyDonation", Ticket);
/* 160 */         url = "/jsp/cashDonations/index.jsp";
/*     */       }
/* 162 */     } else if ("Print".equals(action)) {
/* 163 */       String sKey = req.getParameter("moneyDonationKey");
/* 164 */       Key key = KeyFactory.stringToKey(sKey);
/* 165 */       Entity Ticket = ApplicationDao.getEntity(key);
/*     */ 
/* 167 */       NodeList xmlMoneyDonation = xml.getEntityProperties("MoneyDonation", req);
/* 168 */       req.setAttribute("xmlMoneyDonation", xmlMoneyDonation);
/* 169 */       req.setAttribute("MoneyDonation", Ticket);
/* 170 */       url = "/jsp/cashDonations/print.jsp";
/*     */     }
/* 172 */     else if ("Search".equals(action)) {
/* 173 */       String firstName = req.getParameter("firstName");
/* 174 */       String lastName = req.getParameter("lastName");
/* 175 */       SearchParameter param1 = new SearchParameter("firstName", 
/* 176 */         firstName, Query.FilterOperator.EQUAL);
/* 177 */       SearchParameter param2 = new SearchParameter("lastName", lastName, 
/* 178 */         Query.FilterOperator.EQUAL);
/* 179 */       SearchParameter param3 = new SearchParameter("personType", 
/* 180 */         "Customer", Query.FilterOperator.EQUAL);
/* 181 */       ArrayList params = new ArrayList();
/* 182 */       params.add(param1);
/* 183 */       params.add(param2);
/* 184 */       params.add(param3);
/* 185 */       ArrayList results = ApplicationDao.getEntities("Person", params, 
/* 186 */         null, null, 
/* 187 */         (String)req.getSession().getAttribute("farmBase"));
/* 188 */       req.getSession().setAttribute("results", results);
/* 189 */       req.setAttribute("size", Integer.valueOf(results.size()));
/*     */ 
/* 195 */       Entity Person = null;
/* 196 */       int count = results.size();
/* 197 */       if (count == 0) {
/* 198 */         ArrayList messages = new ArrayList();
/*     */ 
/* 200 */         messages.add("No customers matched the name you have entered. Proceed with creating a new customer.");
/* 201 */         req.setAttribute("messages", messages);
/* 202 */         req.setAttribute("Person", xml.xmlToEntity("Person", req));
/* 203 */         req.setAttribute("Address", xml.xmlToEntity("Address", req));
/* 204 */         Entity MoneyDonation = xml.xmlToEntity("MoneyDonation", req);
/* 205 */         MoneyDonation.setProperty("status", "Pending");
/* 206 */         req.setAttribute("MoneyDonation", MoneyDonation);
/*     */ 
/* 208 */         if ("moneyDonation".equals(req.getParameter("page")))
/* 209 */           url = "/jsp/cashDonations/cash_donation.jsp?edit=Y&new=Y";
/*     */         else
/* 211 */           url = "/jsp/cashDonations/index.jsp?edit=Y&new=Y";
/*     */       }
/* 213 */       else if (count == 1) {
/* 214 */         Person = (Entity)results.get(0);
/* 215 */         req.setAttribute("Person", Person);
/* 216 */         Entity Address = PersonDao.getAddressByPerson(Person);
/* 217 */         req.setAttribute("Address", Address);
/* 218 */         Entity MoneyDonation = xml.xmlToEntity("MoneyDonation", req);
/* 219 */         MoneyDonation.setProperty("status", "Pending");
/* 220 */         req.setAttribute("MoneyDonation", MoneyDonation);
/* 221 */         url = "/jsp/cashDonations/index.jsp?edit=Y";
/*     */       } else {
/* 223 */         url = "/jsp/cashDonations/customer_list.jsp";
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 228 */     req.getRequestDispatcher(req.getContextPath() + url).forward(req, resp);
/*     */   }
/*     */ 
/*     */   protected void doPost(HttpServletRequest req, HttpServletResponse resp)
/*     */     throws ServletException, IOException
/*     */   {
/* 234 */     doGet(req, resp);
/*     */   }
/*     */ 
/*     */   private void refreshResults(HttpServletRequest req) {
/* 238 */     ArrayList parameters = new ArrayList();
/*     */ 
/* 240 */     String ticketDate = (String)req.getSession().getAttribute("ticketDate");
/* 241 */     String status = (String)req.getSession().getAttribute("status");
/* 242 */     String priority = (String)req.getSession().getAttribute("priority");
/* 243 */     String farmLocation = (String)req.getSession().getAttribute("farmLocation");
/* 244 */     SearchParameter searchParam = new SearchParameter("ticketDate", ticketDate, Query.FilterOperator.EQUAL);
/* 245 */     parameters.add(searchParam);
/* 246 */     searchParam = new SearchParameter("status", status, Query.FilterOperator.EQUAL);
/* 247 */     parameters.add(searchParam);
/* 248 */     searchParam = new SearchParameter("priority", priority, Query.FilterOperator.EQUAL);
/* 249 */     parameters.add(searchParam);
/* 250 */     searchParam = new SearchParameter("farmLocation", farmLocation, Query.FilterOperator.EQUAL);
/* 251 */     parameters.add(searchParam);
/*     */ 
/* 253 */     ArrayList results = ApplicationDao.getEntities("MoneyDonation", parameters, null, null, farmLocation);
/* 254 */     req.setAttribute("results", results);
/*     */   }
/*     */ }

/* Location:           C:\development\workspace\faithfarmoss\WEB-INF\classes\
 * Qualified Name:     org.faithfarm.CashDonationServlet
 * JD-Core Version:    0.6.2
 */