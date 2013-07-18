/*     */ package org.faithfarm.onlinestore;
/*     */ 
/*     */ /*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;

/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;

/*     */ import org.faithfarm.BaseServlet;
/*     */ import org.faithfarm.dataobjects.SearchParameter;
/*     */ import org.faithfarm.datawriters.ApplicationDao;
/*     */ import org.faithfarm.datawriters.PersonDao;
/*     */ import org.faithfarm.mail.Mailer;
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
/*     */ 
/*     */ public class CustomerServlet extends BaseServlet
/*     */ {
/*  35 */   private static final Logger logger = Logger.getLogger(CustomerServlet.class.getCanonicalName());
/*  36 */   private static final HTMLBuilder html = new HTMLBuilder();
/*  37 */   private static final XMLParser xml = new XMLParser();
/*  38 */   private static final PersonDao dao = new PersonDao();
/*  39 */   private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
/*     */ 
/*  41 */   private String homePhone = "";
/*  42 */   private String cellPhone = "";
/*  43 */   private String workPhone = "";
/*     */ 
/*     */   protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
/*     */   {
/*  47 */     super.doGet(req, resp);
/*     */ 
/*  49 */     String uid = "";
/*  50 */     String pwd = "";
/*     */ 
/*  57 */     req.getSession().setAttribute("ddlStates", html.buildStateList());
/*     */ 
/*  60 */     UserService userService = UserServiceFactory.getUserService();
/*  61 */     User user = userService.getCurrentUser();
/*     */ 
/*  66 */     String action = (String)req.getAttribute("action");
/*  67 */     String url = "/jsp/onlinestore/register.jsp";
/*     */ 
/*  69 */     if (action == null) {
/*  70 */       action = req.getParameter("action");
/*     */     }
/*     */ 
/*  73 */     Entity Person = xml.xmlToEntity("Person", req);
/*  74 */     Entity Address = xml.xmlToEntity("Address", req);
/*  75 */     NodeList xmlPerson = xml.getEntityProperties("Person", req);
/*  76 */     NodeList xmlAddress = xml.getEntityProperties("Address", req);
/*  77 */     req.setAttribute("Person", Person);
/*  78 */     req.setAttribute("Address", Address);
/*  79 */     req.setAttribute("xmlPerson", xmlPerson);
/*  80 */     req.setAttribute("xmlAddress", xmlAddress);
/*     */ 
/*  87 */     if ("Login".equals(action)) {
/*  88 */       uid = req.getParameter("uid");
/*  89 */       pwd = req.getParameter("pwd");
/*  90 */       String encryptedPwd = org.faithfarm.dataobjects.Person.encryptSsn(pwd);
/*     */ 
/*  92 */       ArrayList parameters = new ArrayList();
/*     */ 
/*  94 */       SearchParameter param = new SearchParameter("password", encryptedPwd, Query.FilterOperator.EQUAL);
/*  95 */       parameters.add(param);
/*  96 */       param = new SearchParameter("username", uid, Query.FilterOperator.EQUAL);
/*  97 */       parameters.add(param);
/*     */ 
/*  99 */       ArrayList results = ApplicationDao.getEntities("Person", parameters, null, null, "Online Store");
/*     */ 
/* 101 */       if (results.size() == 1) {
/* 102 */         Entity Customer = (Entity)results.get(0);
/* 103 */         req.getSession().setAttribute("online_customer", Customer);
/*     */       }
/* 105 */       url = "/jsp/onlinestore/index.jsp";
/*     */     }
/*     */ 
/* 108 */     if ("Save".equals(action))
/*     */     {
/* 110 */       Person = null;
/* 111 */       Address = null;
/* 112 */       String personKey = req.getParameter("personKey");
/* 113 */       String addressKey = req.getParameter("addressKey");
/* 114 */       if (personKey != null) {
/* 115 */         Key key1 = KeyFactory.stringToKey(personKey);
/* 116 */         Key key2 = KeyFactory.stringToKey(addressKey);
/* 117 */         Person = ApplicationDao.getEntity(key1);
/* 118 */         Address = ApplicationDao.getEntity(key2);
/* 119 */         if (Address == null) Address = new Entity("Address", Person.getKey());
/* 120 */         Person = xml.updateEntity(Person, req);
/* 121 */         Address = xml.updateEntity(Address, req);
/* 122 */         Address = parseTelephone(Address, req);
/*     */       }
/*     */       else {
/* 125 */         Person = xml.xmlToEntity("Person", req);
/* 126 */         Address = xml.xmlToEntity("Address", req);
/* 127 */         Address = parseTelephone(Address, req);
/*     */ 
/* 129 */         Person.setProperty("personType", "Customer");
/* 130 */         Person.setProperty("personStatus", "Active");
/*     */       }
/*     */ 
/* 133 */       xmlPerson = xml.getEntityProperties("Person", req);
/* 134 */       xmlAddress = xml.getEntityProperties("Address", req);
/* 135 */       req.setAttribute("xmlPerson", xmlPerson);
/* 136 */       req.setAttribute("xmlAddress", xmlAddress);
/* 137 */       req.setAttribute("Person", Person);
/* 138 */       req.setAttribute("Address", Address);
/* 139 */       req.setAttribute("username", uid);
/*     */ 
/* 141 */       boolean valid1 = validate(Address, Person, req);
/* 142 */       boolean valid2 = false;
/*     */ 
/* 144 */       String returnVal = setUsernamePassword(req);
/* 145 */       if (returnVal != null) {
/* 146 */         int i = returnVal.indexOf(",");
/* 147 */         uid = returnVal.substring(0, i);
/* 148 */         pwd = returnVal.substring(i + 1, returnVal.length());
/* 149 */         valid2 = true;
/*     */       }
/*     */ 
/* 152 */       if ((valid1) && (valid2))
/*     */       {
/* 154 */         Person.setProperty("personStatus", "Active");
/* 155 */         Person.setProperty("personType", "Customer");
/* 156 */         Person.setProperty("username", uid);
/* 157 */         Person.setProperty("password", org.faithfarm.dataobjects.Person.encryptSsn(pwd));
/* 158 */         Person.setProperty("farmBase", "Online Store");
/*     */ 
/* 160 */         Entity p = ApplicationDao.createOrUpdateEntity(Person, user);
/*     */         Entity addy;
/* 161 */         if (addressKey == null) {
/* 162 */           addy = xml.xmlChildToParentEntity("Address", Address, p);
/* 163 */           addy = parseTelephone(addy, req);
/*     */ 
/* 167 */           ApplicationDao.createOrUpdateEntity(addy, user);
/*     */         } else {
/* 169 */           Address = parseTelephone(Address, req);
/*     */ 
/* 174 */           ApplicationDao.createOrUpdateEntity(Address, user);
/* 175 */           //addy = (String)Address.getProperty("email");
/*     */         }
/*     */ 
/* 185 */         String body = 
/* 186 */           "Thanks for registering with us, " + (String)Person.getProperty("firstName") + ":</br></br>" + 
/* 187 */           "Faith Farm Ministries Thrift Store is proud to announce the beginning of their quarterly furniture sale! " + 
/* 188 */           "Two days only, November 16st and 17th, 2012, everything in our showroom will be marked down! " + 
/* 189 */           "Nowhere else in town will you find better deals on the greatest chairs, sofas, recliners, and " + 
/* 190 */           "other household furnishings. </br></br>" + 
/* 191 */           "Come visit us at three different locations:</br>" + 
/* 192 */           "<b>Boynton Beach Faith Farm And Thrift Store</b>, 9538 Hwy 441,  Boynton Beach, FL. 33472</br>  " + 
/* 193 */           "<b>Fort Lauderdale Faith Farm And Thrift Store</b>, 1980 NW 9th Avenue, Fort Lauderdale, FL. 33311</br> " + 
/* 194 */           "<b>Okeechobee Faith Farm And Thrift Store</b>, 1852 State Road 70 West, Okeechobee, FL. 34972</br> " + 
/* 195 */           "</br></br>" + 
/* 196 */           "Hurry items in stock will not last long at these unbeatable prices! </br></br>" + 
/* 197 */           "Sincerely, </br>" + 
/* 198 */           "Faith Farm Ministries Thrift Store";
/*     */         try
/*     */         {
/* 202 */           logger.log(Level.SEVERE, "Sending Email to " + (String)Address.getProperty("email"));
/* 203 */           Mailer.sendNewStudentMail((String)Address.getProperty("email"), "Faith Farm Ministries Thrift Store user registration.", body);
/* 204 */           logger.log(Level.SEVERE, "Sent registration confirmation");
/*     */         }
/*     */         catch (Exception e) {
/* 207 */           logger.log(Level.SEVERE, e.getMessage());
/*     */         }
/*     */ 
/* 218 */         url = "/jsp/onlinestore/index.jsp";
/*     */       } else {
/* 220 */         req.setAttribute("username", uid);
/* 221 */         url = "/jsp/onlinestore/register.jsp";
/*     */       }
/*     */     }
/*     */ 
/* 225 */     req.getRequestDispatcher(req.getContextPath() + url).forward(req, resp);
/*     */   }
/*     */ 
/*     */   private String setUsernamePassword(HttpServletRequest req)
/*     */   {
/* 231 */     ArrayList messages = new ArrayList();
/*     */ 
/* 233 */     String username = req.getParameter("username");
/* 234 */     String password = req.getParameter("password");
/* 235 */     String password1 = req.getParameter("password1");
/* 236 */     String chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
/*     */ 
/* 239 */     if ((username == null) || (username.length() == 0)) {
/* 240 */       messages.add("you must enter a username");
/*     */     }
/* 242 */     int uLen = username.length();
/* 243 */     int uOk = 0;
/* 244 */     if (uLen < 8) {
/* 245 */       messages.add("username must be at least 8 characters in length");
/*     */     }
/* 247 */     for (int j = 0; j < username.length(); j++)
/*     */     {
/* 249 */       for (int i = 0; i < chars.length(); i++) {
/* 250 */         char ch = chars.charAt(i);
/* 251 */         if (username.contains(ch+"")) {
/* 252 */           uOk++; break;
/*     */         }
/*     */       }
/*     */     }
/* 256 */     if (uOk != uLen) {
/* 257 */       messages.add("username may only contain numbers and letters");
/*     */     }
/*     */ 
/* 262 */     uLen = password.length();
/* 263 */     uOk = 0;
/*     */ 
/* 265 */     for (int j = 0; j < password.length(); j++)
/*     */     {
/* 267 */       for (int i = 0; i < chars.length(); i++) {
/* 268 */         char ch = chars.charAt(i);
/* 269 */         if (password.contains(ch+"")) {
/* 270 */           uOk++; break;
/*     */         }
/*     */       }
/*     */     } 
				
/*     */ 
/* 275 */     if ((password == null) || (password.length() == 0))
/* 276 */       messages.add("you must enter a password");
/* 277 */     else if (password.length() < 6) {
/* 278 */       messages.add("password must be at least 6 characters in length");
/*     */     }
/*     */ 
/* 281 */     if (uOk != uLen) {
/* 282 */       messages.add("password may only contain numbers and letters");
/*     */     }
/* 284 */     if (!password.equals(password1)) {
/* 285 */       messages.add("password must match");
/*     */     }
/*     */ 
/* 288 */     if (messages.size() > 0) {
/* 289 */       req.setAttribute("messages", messages);
/* 290 */       return null;
/*     */     }
/*     */ 
/* 294 */     return username + "," + password;
/*     */   }
/*     */ 
/*     */   protected void doPost(HttpServletRequest req, HttpServletResponse resp)
/*     */     throws ServletException, IOException
/*     */   {
/* 302 */     doGet(req, resp);
/*     */   }
/*     */ 
/*     */   private boolean validate(Entity Person, Entity Address, HttpServletRequest req)
/*     */   {
/* 307 */     Validator validator = new Validator();
/* 308 */     ArrayList messages = new ArrayList();
/*     */ 
/* 310 */     messages = validator.validateRequired("firstname", req.getParameter("firstName"), messages);
/* 311 */     messages = validator.validateRequired("lastname", req.getParameter("lastName"), messages);
/* 312 */     messages = validator.validateRequired("address line 1", req.getParameter("addressLine1"), messages);
/* 313 */     messages = validator.validateRequired("city", req.getParameter("city"), messages);
/* 314 */     messages = validator.validateRequired("state", req.getParameter("state"), messages);
/* 315 */     messages = validator.validateRequired("zipcode", req.getParameter("zipcode"), messages);
/* 316 */     messages = validator.validateEmail(req.getParameter("email"), messages);
/*     */ 
/* 318 */     String homePhone1 = html.cleanData(req.getParameter("homePhone_areacode"));
/* 319 */     String homePhone2 = html.cleanData(req.getParameter("homePhone_first3"));
/* 320 */     String homePhone3 = html.cleanData(req.getParameter("homePhone_last4"));
/* 321 */     this.homePhone = ("(" + homePhone1 + ")" + homePhone2 + "-" + homePhone3);
/* 322 */     messages = validator.validateRequired("home phone", this.homePhone, messages);
/*     */ 
/* 324 */     String cellPhone1 = html.cleanData(req.getParameter("cellPhone_areacode"));
/* 325 */     String cellPhone2 = html.cleanData(req.getParameter("cellPhone_first3"));
/* 326 */     String cellPhone3 = html.cleanData(req.getParameter("cellPhone_last4"));
/* 327 */     this.cellPhone = ("(" + cellPhone1 + ")" + cellPhone2 + "-" + cellPhone3);
/*     */ 
/* 329 */     String workPhone1 = html.cleanData(req.getParameter("workPhone_areacode"));
/* 330 */     String workPhone2 = html.cleanData(req.getParameter("workPhone_first3"));
/* 331 */     String workPhone3 = html.cleanData(req.getParameter("workPhone_last4"));
/* 332 */     this.workPhone = ("(" + workPhone1 + ")" + workPhone2 + "-" + workPhone3);
/*     */ 
/* 334 */     if (messages.size() > 0) {
/* 335 */       req.setAttribute("messages", messages);
/* 336 */       return false;
/*     */     }
/* 338 */     return true;
/*     */   }
/*     */ 
/*     */   private Entity parseTelephone(Entity entity, HttpServletRequest req)
/*     */   {
/* 343 */     String homePhone1 = html.cleanData(req.getParameter("homePhone_areacode"));
/* 344 */     String homePhone2 = html.cleanData(req.getParameter("homePhone_first3"));
/* 345 */     String homePhone3 = html.cleanData(req.getParameter("homePhone_last4"));
/* 346 */     String homePhone = "(" + homePhone1 + ")" + homePhone2 + "-" + homePhone3;
/* 347 */     entity.setProperty("homePhone", homePhone);
/*     */ 
/* 349 */     String cellPhone1 = html.cleanData(req.getParameter("cellPhone_areacode"));
/* 350 */     String cellPhone2 = html.cleanData(req.getParameter("cellPhone_first3"));
/* 351 */     String cellPhone3 = html.cleanData(req.getParameter("cellPhone_last4"));
/* 352 */     String cellPhone = "(" + cellPhone1 + ")" + cellPhone2 + "-" + cellPhone3;
/* 353 */     entity.setProperty("cellPhone", cellPhone);
/*     */ 
/* 355 */     String workPhone1 = html.cleanData(req.getParameter("workPhone_areacode"));
/* 356 */     String workPhone2 = html.cleanData(req.getParameter("workPhone_first3"));
/* 357 */     String workPhone3 = html.cleanData(req.getParameter("workPhone_last4"));
/* 358 */     String workPhone = "(" + workPhone1 + ")" + workPhone2 + "-" + workPhone3;
/* 359 */     entity.setProperty("workPhone", workPhone);
/*     */ 
/* 361 */     return entity;
/*     */   }
/*     */ 
/*     */   private void sendEmail(Entity Person, String emailAddress) {
/* 365 */     String body = "Thank you " + (String)Person.getProperty("firstName") + " " + (String)Person.getProperty("lastName") + 
/* 366 */       "for registering with Faith Farm Furniture & Thrift Store.  Your account has been created and you may login" + 
/* 367 */       "with the username and password you supplied.<br>" + 
/* 368 */       "Please keep us in mind when you need to get rid of things.<br><br>Call us at 954-763-7787 " + 
/* 369 */       "between the hours of 9:00am and 5:00pm, Monday thru Saturday to schedule a free pickup." + 
/* 370 */       "<br><br>";
/*     */     try {
/* 372 */       logger.log(Level.SEVERE, "Sending Email123");
/* 373 */       Mailer.sendNewStudentMail(emailAddress, "Faith Farm Thrift Store Account Creation", body);
/* 374 */       logger.log(Level.SEVERE, "Sent");
/*     */     } catch (Exception e) {
/* 376 */       logger.log(Level.SEVERE, e.getMessage());
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\development\workspace\faithfarmoss\WEB-INF\classes\
 * Qualified Name:     org.faithfarm.onlinestore.CustomerServlet
 * JD-Core Version:    0.6.2
 */