/*     */ package org.faithfarm;
/*     */ 
/*     */ /*     */ import java.io.IOException;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;

/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;

/*     */ import org.faithfarm.dataobjects.SearchParameter;
/*     */ import org.faithfarm.datawriters.ApplicationDao;
/*     */ import org.faithfarm.datawriters.PersonDao;
/*     */ import org.faithfarm.reporting.Report;
/*     */ import org.faithfarm.utilities.ApplicationTools;
/*     */ import org.faithfarm.utilities.FFSession;
/*     */ import org.faithfarm.utilities.HTMLBuilder;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
/*     */ import com.google.appengine.api.users.User;
/*     */ import com.google.appengine.api.users.UserService;
/*     */ import com.google.appengine.api.users.UserServiceFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ControllerServlet extends BaseServlet
/*     */ {
/*  43 */   private static final Logger logger = Logger.getLogger(ControllerServlet.class.getCanonicalName());
/*  44 */   private static final HTMLBuilder html = new HTMLBuilder();
/*     */ 
/*     */   protected void doGet(HttpServletRequest req, HttpServletResponse resp)
/*     */     throws ServletException, IOException
/*     */   {
/*  52 */     super.doGet(req, resp);
/*  53 */     HttpSession session = req.getSession(true);
/*  54 */     UserService userService = UserServiceFactory.getUserService();
/*  55 */     User user = userService.getCurrentUser();
/*     */ 
/*  57 */     if (FFSession.needsRedirect(req, resp)) {
/*  58 */       return;
/*     */     }
/*     */ 
/*  61 */     session.removeAttribute("results");
/*     */ 
/*  67 */     String action = (String)req.getAttribute("action");
/*  68 */     if (action == null) {
/*  69 */       action = req.getParameter("action");
/*     */     }
/*  71 */     String entity = req.getParameter("entity");
/*     */ 
/*  73 */     if ("Login".equals(entity)) {
/*  74 */       boolean success = false;
/*  75 */       String farm = null;
/*  76 */       String username = req.getParameter("username");
/*  77 */       String password = req.getParameter("password");
/*     */ 
/*  79 */       if (("ftladmin".equals(username)) && ("admin".equals(password))) {
/*  80 */         farm = "Fort Lauderdale";
/*  81 */         success = true;
/*  82 */       } else if (("okeadmin".equals(username)) && ("admin".equals(password))) {
/*  83 */         farm = "Okeechobee";
/*  84 */         success = true;
/*  85 */       } else if (("bynadmin".equals(username)) && ("admin".equals(password))) {
/*  86 */         farm = "Boynton Beach";
/*  87 */         success = true;
/*     */       } else if (("bynadmin".equals(username)) && ("admin".equals(password))) {
	/*  86 */         farm = "Boynton Beach";
	/*  87 */         success = true;
	/*     */       } else {
/*  89 */         ArrayList messages = new ArrayList();
/*  90 */         messages.add("username and/or password is incorrect.");
/*  91 */         req.setAttribute("messages", messages);
/*  92 */         req.getRequestDispatcher(
/*  93 */           req.getContextPath() + "/jsp/index.jsp").forward(req, 
/*  94 */           resp);
/*     */       }
/*     */ 
/*  97 */       if (success)
/*     */       {
/* 101 */         if (req.getSession().getAttribute("vendors") == null) {
/* 102 */           session.setAttribute("vendors", html.buildVendorList());
/*     */         }
/* 104 */         ArrayList ddlNameSuffix = (ArrayList)session
/* 105 */           .getAttribute("ddlNameSuffix");
/* 106 */         if (ddlNameSuffix == null) {
/* 107 */           session.setAttribute("ddlNameSuffix", 
/* 108 */             html.buildNameSuffixList());
/*     */         }
/* 110 */         ArrayList ddlLocations = (ArrayList)session
/* 111 */           .getAttribute("locations");
/* 112 */         if (ddlLocations == null) {
/* 113 */           session.setAttribute("locations", html.buildLocationList());
/*     */         }
/* 115 */         ArrayList ddlTransactions = (ArrayList)session
/* 116 */           .getAttribute("transactions");
/* 117 */         if (ddlTransactions == null) {
/* 118 */           session.setAttribute("transactions", 
/* 119 */             html.buildTransactionList());
/*     */         }
/* 121 */         session.setAttribute("farm", farm);
/*     */ 
/* 123 */         if ("ffm.corp.ceo".equals(user.getNickname()))
/* 124 */           req.getRequestDispatcher(req.getContextPath() + "/jsp/dashboard/index.jsp")
/* 125 */             .forward(req, resp);
/*     */         else {
/* 127 */           req.getRequestDispatcher(req.getContextPath() + "/jsp/main.jsp")
/* 128 */             .forward(req, resp);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/* 134 */     else if ("NewFurnForm".equals(entity))
/*     */     {
/* 136 */       String fName = req.getParameter("firstName");
/* 137 */       String lName = req.getParameter("lastName");
/* 138 */       String customerKey = fName + " " + lName;
/* 139 */       Entity e = Customer.getSingleCustomer(customerKey);
/* 140 */       req.setAttribute("customer", e);
/*     */ 
/* 142 */       if ("Find Item".equals(action))
/*     */       {
/* 145 */         String item1 = req.getParameter("item1");
/* 146 */         if (item1 != null) {
/* 147 */           Entity i1 = Item.getSingleItem(item1.toUpperCase());
/* 148 */           if (i1 != null) {
/* 149 */             req.setAttribute("itemName1", 
/* 150 */               i1.getProperty("itemName"));
/* 151 */             req.setAttribute("price1", i1.getProperty("price"));
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 156 */         String item2 = req.getParameter("item2");
/* 157 */         if (item2 != null) {
/* 158 */           Entity i2 = Item.getSingleItem(item2.toUpperCase());
/* 159 */           if (i2 != null) {
/* 160 */             req.setAttribute("itemName2", 
/* 161 */               i2.getProperty("itemName"));
/* 162 */             req.setAttribute("price2", i2.getProperty("price"));
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 167 */         String item3 = req.getParameter("item3");
/* 168 */         if (item3 != null) {
/* 169 */           Entity i3 = Item.getSingleItem(item3.toUpperCase());
/* 170 */           if (i3 != null) {
/* 171 */             req.setAttribute("itemName3", 
/* 172 */               i3.getProperty("itemName"));
/* 173 */             req.setAttribute("price3", i3.getProperty("price"));
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 178 */         String item4 = req.getParameter("item4");
/* 179 */         if (item4 != null) {
/* 180 */           Entity i4 = Item.getSingleItem(item4.toUpperCase());
/* 181 */           if (i4 != null) {
/* 182 */             req.setAttribute("itemName4", 
/* 183 */               i4.getProperty("itemName"));
/* 184 */             req.setAttribute("price4", i4.getProperty("price"));
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 189 */         String item5 = req.getParameter("item5");
/* 190 */         if (item5 != null) {
/* 191 */           Entity i5 = Item.getSingleItem(item5.toUpperCase());
/* 192 */           if (i5 != null) {
/* 193 */             req.setAttribute("itemName5", 
/* 194 */               i5.getProperty("itemName"));
/* 195 */             req.setAttribute("price5", i5.getProperty("price"));
/*     */           }
/*     */         }
/*     */ 
/* 199 */         req.getRequestDispatcher(
/* 200 */           req.getContextPath() + "/jsp/forms/sales.jsp").forward(
/* 201 */           req, resp);
/* 202 */       } else if ("Find Customer".equals(action)) {
/* 203 */         req.getRequestDispatcher(
/* 204 */           req.getContextPath() + "/jsp/forms/sales.jsp").forward(
/* 205 */           req, resp);
/* 206 */       } else if ("Print Order Form".equals(action)) {
/* 207 */         req.getRequestDispatcher(
/* 208 */           req.getContextPath() + "/jsp/forms/newfurn_print.jsp")
/* 209 */           .forward(req, resp);
/*     */       }
/* 211 */     } else if ("Report".equals(entity))
/*     */     {
/* 214 */       if ("DispatchPickUpStatus".equals(req.getParameter("RPT"))) {
/* 215 */         String startDate = req.getParameter("startDate");
/* 216 */         String farm = req.getParameter("farmBase");
/* 217 */         if ((startDate == null) || ("".equals(startDate))) {
/* 218 */           ArrayList messages = new ArrayList();
/* 219 */           messages.add("Please select a start date.");
/* 220 */           req.getSession().setAttribute("errors", messages);
/* 221 */           req.getRequestDispatcher(
/* 222 */             req.getContextPath() + 
/* 223 */             "/jsp/reports/dispatch/param.jsp")
/* 224 */             .forward(req, resp);
/*     */         } else {
/* 226 */           ArrayList report = null;
/*     */ 
/* 228 */           report = Report.generateDispatchTicketPickUpReport(startDate, farm, req);
/*     */ 
/* 230 */           req.setAttribute("results", report);
/* 231 */           req.setAttribute("salesDate", startDate);
/* 232 */           req.getRequestDispatcher(
/* 233 */             req.getContextPath() + 
/* 234 */             "/jsp/reports/dispatch/index.jsp")
/* 235 */             .forward(req, resp);
/*     */         }
/*     */       }
/* 238 */       if ("DailySales".equals(req.getParameter("RPT"))) {
/* 239 */         String salesDate = req.getParameter("salesDate");
/* 240 */         String farm = req.getParameter("farmBase");
/*     */ 
/* 242 */         if ((salesDate == null) || ("".equals(salesDate))) {
/* 243 */           ArrayList messages = new ArrayList();
/* 244 */           messages.add("Please select a date.");
/* 245 */           req.getSession().setAttribute("errors", messages);
/* 246 */           req.getRequestDispatcher(
/* 247 */             req.getContextPath() + 
/* 248 */             "/jsp/reports/inventory/params.jsp")
/* 249 */             .forward(req, resp);
/*     */         } else {
/* 251 */           ArrayList report = Report.generateDailySalesReport(salesDate, farm, req);
/* 252 */           req.setAttribute("results", report);
/* 253 */           req.setAttribute("salesDate", salesDate);
/* 254 */           req.getRequestDispatcher(
/* 255 */             req.getContextPath() + 
/* 256 */             "/jsp/reports/inventory/daily_sales.jsp")
/* 257 */             .forward(req, resp);
/*     */         }
/*     */       }
/* 260 */       if ("001".equals(req.getParameter("RPT")))
/*     */       {
/* 262 */         Report.generateInventoryReport((String)session.getAttribute("farmBase"), req);
/* 263 */         req.getRequestDispatcher(
/* 264 */           req.getContextPath() + "/jsp/reports/inventory/RPT001.jsp")
/* 265 */           .forward(req, resp);
/*     */       }
/*     */ 
/* 268 */       if ("001B".equals(req.getParameter("RPT"))) {
/* 269 */         SearchParameter param = new SearchParameter("status", "Active", 
/* 270 */           Query.FilterOperator.EQUAL);
/* 271 */         ArrayList params = new ArrayList();
/* 272 */         params.add(param);
/* 273 */         ArrayList results = ApplicationTools.convertIterableToList(
/* 274 */           Item.search(null, null, null, null, null, null));
/*     */ 
/* 279 */         req.setAttribute("results", results);
/* 280 */         req.getRequestDispatcher(
/* 281 */           req.getContextPath() + 
/* 282 */           "/jsp/reports/inventory/inventory_count.jsp")
/* 283 */           .forward(req, resp);
/*     */       }
/*     */ 
/* 286 */       if ("002".equals(req.getParameter("RPT")))
/*     */       {
/* 288 */         String firstName = html.cleanData(req.getParameter("firstName"));
/* 289 */         String middleInitial = html.cleanData(req.getParameter("middleInitial"));
/* 290 */         String lastName = html.cleanData(req.getParameter("lastName"));
/* 291 */         String farm = html.cleanData(req.getParameter("farm"));
/* 292 */         String status = html.cleanData(req.getParameter("status"));
/*     */ 
/* 294 */         ArrayList parameters = new ArrayList();
/* 295 */         SearchParameter param = new SearchParameter("firstName", firstName, Query.FilterOperator.EQUAL);
/* 296 */         parameters.add(param);
/* 297 */         param = new SearchParameter("middleInitial", middleInitial, Query.FilterOperator.EQUAL);
/* 298 */         parameters.add(param);
/* 299 */         param = new SearchParameter("lastName", lastName, Query.FilterOperator.EQUAL);
/* 300 */         parameters.add(param);
/* 301 */         param = new SearchParameter("personStatus", status, Query.FilterOperator.EQUAL);
/* 302 */         parameters.add(param);
/*     */ 
/* 304 */         ArrayList results = ApplicationDao.getEntities("Person", parameters, null, null, farm);
/* 305 */         req.setAttribute("results", results);
/* 306 */         req.getRequestDispatcher(
/* 307 */           req.getContextPath() + "/jsp/reports/RPT002.jsp")
/* 308 */           .forward(req, resp);
/*     */       }
/*     */ 
/* 311 */       if ("003".equals(req.getParameter("RPT"))) {
/* 312 */         req.setAttribute("results", PersonDao.getAllPersons(null, null, 
/* 313 */           null, "Resident", "Active", null, null, 
/* 314 */           (String)session.getAttribute("farmBase")));
/* 315 */         req.getRequestDispatcher(
/* 316 */           req.getContextPath() + "/jsp/reports/RPT003.jsp")
/* 317 */           .forward(req, resp);
/*     */       }
/* 319 */       if ("004".equals(req.getParameter("RPT"))) {
/* 320 */         Iterable activeStudents = PersonDao.getAllPersons(null, 
/* 321 */           null, null, "Resident", "Active", null, null, 
/* 322 */           (String)session.getAttribute("farmBase"));
/* 323 */         Iterable inactiveStudents = 
/* 324 */           PersonDao.getRemovedPersons();
/* 325 */         ArrayList addedStudents = new ArrayList();
/* 326 */         ArrayList removedStudents = new ArrayList();
/*     */ 
/* 328 */         SimpleDateFormat df = new SimpleDateFormat(
/* 329 */           "EEE MMM dd HH:mm:ss zzz yyyy");
/* 330 */         Date myDate = null;
/* 331 */         Date oneWeek = null;
/* 332 */         Calendar myCal = Calendar.getInstance();
/* 333 */         myCal.setTime(new Date());
/* 334 */         myCal.add(7, -7);
/* 335 */         oneWeek = myCal.getTime();
/* 336 */         String entryDate = null;
/* 337 */         String exitDate = null;
/*     */ 
/* 339 */         Map properties = null;
/*     */ 
/* 341 */         //for (Entity student : activeStudents) {
				  for(Iterator i = activeStudents.iterator();i.hasNext();) {
					Entity student = (Entity)i.next();

/* 342 */           properties = student.getProperties();
/*     */ 
/* 344 */           entryDate = (String)properties.get("entryDate");
/* 345 */           if (entryDate != null)
/*     */           {
/*     */             try
/*     */             {
/* 349 */               myDate = df.parse(entryDate);
/*     */             }
/*     */             catch (ParseException localParseException1)
/*     */             {
/*     */             }
/* 354 */             if (myDate.after(oneWeek)) {
/* 355 */               addedStudents.add(student);
/*     */             }
/*     */           }
/*     */         }
/* 359 */         //for (Entity student : inactiveStudents) {
/* 360 */       for(Iterator i = inactiveStudents.iterator();i.hasNext();) {
	  			    Entity student = (Entity)i.next();
	  			    properties = student.getProperties();
/*     */ 
/* 362 */           exitDate = (String)properties.get("exitDate");
/* 363 */           if (exitDate != null)
/*     */           {
/*     */             try
/*     */             {
/* 367 */               myDate = df.parse(exitDate);
/*     */             }
/*     */             catch (ParseException localParseException2)
/*     */             {
/*     */             }
/* 372 */             if (myDate.after(oneWeek)) {
/* 373 */               removedStudents.add(student);
/*     */             }
/*     */           }
/*     */         }
/* 377 */         req.setAttribute("addedStudents", addedStudents);
/* 378 */         req.setAttribute("removedStudents", removedStudents);
/* 379 */         req.getRequestDispatcher(
/* 380 */           req.getContextPath() + "/jsp/reports/RPT004.jsp")
/* 381 */           .forward(req, resp);
/*     */       }
/* 383 */       if ("005".equals(req.getParameter("RPT")))
/*     */       {
/* 385 */         if (req.getParameter("destroy") != null) {
/* 386 */           session.invalidate();
/* 387 */           return;
/*     */         }
/*     */ 
/* 391 */         String farm = (String)session.getAttribute("farmBase");
/* 392 */         Iterable activeStudents = PersonDao.getAllPersons(null, 
/* 393 */           null, null, "Resident", "Active", null, null, farm);
/* 394 */         Iterable allStudents = PersonDao.getAllPersons(null, 
/* 395 */           null, null, "Resident", null, null, null, farm);
/*     */ 
/* 397 */         ArrayList newStudents = new ArrayList();
/* 398 */         ArrayList firstTime = new ArrayList();
/* 399 */         ArrayList returning = new ArrayList();
/*     */ 
/* 401 */         SimpleDateFormat df = new SimpleDateFormat(
/* 402 */           "EEE MMM dd HH:mm:ss zzz yyyy");
/* 403 */         Calendar myCal = Calendar.getInstance();
/* 404 */         myCal.set(5, 1);
/* 405 */         myCal.set(11, 0);
/* 406 */         myCal.set(12, 0);
/* 407 */         myCal.set(13, 0);
/* 408 */         myCal.set(14, 0);
/*     */ 
/* 410 */         Date studentEntryDate = null;
/* 411 */         Map properties = null;
/* 412 */         Map allProperties = null;
/*     */         Iterator localIterator3;
/* 417 */         label2880: for (Iterator localIterator2 = activeStudents.iterator(); localIterator2.hasNext(); 
/* 429 */           localIterator3.hasNext())
/*     */         {
/* 417 */           Entity student = (Entity)localIterator2.next();
/* 418 */           properties = student.getProperties();
/*     */           try {
/* 420 */             studentEntryDate = df.parse(
/* 421 */               (String)properties
/* 421 */               .get("entryDate"));
/*     */           } catch (ParseException e) {
/* 423 */             logger.log(Level.SEVERE, e.getMessage());
/*     */           }
/*     */ 
/* 426 */           if ((!studentEntryDate.after(myCal.getTime())) && 
/* 427 */             (studentEntryDate.compareTo(myCal.getTime()) != 0)) break label2880;
/* 428 */           else {
						newStudents.add(student);
	/* 429 */           localIterator3 = allStudents.iterator(); //continue; 
						
						Entity allStudent = (Entity)localIterator3.next();
	/* 430 */           allProperties = allStudent.getProperties();
	/* 431 */           String theirStatus = 
	/* 432 */             (String)allProperties
	/* 432 */             .get("personStatus");
	/*     */ 
	/* 434 */           String originalEntryDate = 
	/* 435 */             (String)properties
	/* 435 */             .get("entryDate");
	/* 436 */           String testEntryDate = 
	/* 437 */             (String)properties
	/* 437 */             .get("entryDate");
	/*     */ 
	/* 440 */           if (allProperties.get("firstName").equals(
	/* 440 */             properties.get("firstName")))
	/*     */           {
	/* 442 */             if ((allProperties.get("lastName").equals(
	/* 442 */               properties.get("lastName"))) && 
	/* 443 */               (!originalEntryDate.equals(testEntryDate))) {
	/* 444 */               returning.add(student);
	/*     */             }
	/*     */           }
	/* 447 */           if (allProperties.get("firstName").equals(
	/* 447 */             properties.get("firstName")))
	/*     */           {
	/* 449 */             if ((allProperties.get("lastName").equals(
	/* 449 */               properties.get("lastName"))) && 
	/* 450 */               (originalEntryDate.equals(testEntryDate))) {
	/* 451 */               firstTime.add(student);
	/*     */             }
	/*     */           }
/*     */ 			}
/*     */         }
/*     */ 
/* 457 */         req.setAttribute("newStudents", Integer.valueOf(newStudents.size()));
/* 458 */         req.setAttribute("firstTime", Integer.valueOf(firstTime.size()));
/* 459 */         req.setAttribute("returning", Integer.valueOf(returning.size()));
/* 460 */         req.setAttribute("beginningTotal", 
/* 461 */           PersonDao.getCensusBeginningTotal(allStudents));
/* 462 */         req.setAttribute("newTotal", 
/* 463 */           PersonDao.getCensusNewTotal(allStudents));
/* 464 */         req.setAttribute("leftTotal", 
/* 465 */           PersonDao.getLeftTotal(allStudents));
/* 466 */         req.setAttribute("walkedOff", PersonDao.getCensusLeftByStatus(
/* 467 */           allStudents, "Walked Off"));
/* 468 */         req.setAttribute("dismissed", PersonDao.getCensusLeftByStatus(
/* 469 */           allStudents, "Dismissed"));
/* 470 */         req.setAttribute("graduated", PersonDao.getCensusLeftByStatus(
/* 471 */           allStudents, "Graduated"));
/* 472 */         req.setAttribute("leftVoluntarily", 
/* 473 */           PersonDao.getCensusLeftByStatus(allStudents, "Left Voluntarily"));
/* 474 */         req.setAttribute("toSLS", 
/* 475 */           PersonDao.getCensusLeftByStatus(allStudents, "SLS"));
/* 476 */         req.setAttribute("toOmega", PersonDao.getCensusLeftByStatus(
/* 477 */           allStudents, "Omega Work", "Omega School"));
/*     */ 
/* 479 */         req.getRequestDispatcher(
/* 480 */           req.getContextPath() + "/jsp/reports/RPT005.jsp")
/* 481 */           .forward(req, resp);
/*     */       }
/* 483 */     } else if ("Audit".equals(entity)) {
/* 484 */       Iterable transactions = Util.listEntities(
/* 485 */         "TransactionHistory", null, null);
/* 486 */       req.getSession().setAttribute("results", transactions);
/* 487 */       req.getRequestDispatcher(
/* 488 */         req.getContextPath() + "/jsp/audit/index.jsp").forward(req, 
/* 489 */         resp);
/*     */     }
/* 494 */     else if ("ffm.corp.ceo".equals(user.getNickname())) {
/* 495 */       req.getRequestDispatcher(req.getContextPath() + "/jsp/dashboard/index.jsp")
/* 496 */         .forward(req, resp);
/*     */     } else {
/* 498 */       req.getRequestDispatcher(req.getContextPath() + "/jsp/main.jsp")
/* 499 */         .forward(req, resp);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doPost(HttpServletRequest req, HttpServletResponse resp)
/*     */     throws ServletException, IOException
/*     */   {
/* 506 */     doGet(req, resp);
/*     */   }
/*     */ }

/* Location:           C:\development\workspace\faithfarmoss\WEB-INF\classes\
 * Qualified Name:     org.faithfarm.ControllerServlet
 * JD-Core Version:    0.6.2
 */