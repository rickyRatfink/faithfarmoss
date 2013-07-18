/*     */ package org.faithfarm;
/*     */ 
/*     */ /*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.logging.Logger;

/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;

/*     */ import org.faithfarm.dataobjects.Person;
/*     */ import org.faithfarm.dataobjects.SearchParameter;
/*     */ import org.faithfarm.datawriters.ApplicationDao;
/*     */ import org.faithfarm.datawriters.CourseDao;
/*     */ import org.faithfarm.datawriters.PersonDao;
/*     */ import org.faithfarm.mail.Mailer;
/*     */ import org.faithfarm.utilities.ApplicationTools;
/*     */ import org.faithfarm.utilities.CSVParser;
/*     */ import org.faithfarm.utilities.FFSession;
/*     */ import org.faithfarm.utilities.HTMLBuilder;
/*     */ import org.faithfarm.utilities.XMLParser;
/*     */ import org.faithfarm.validators.Validator;
/*     */ import org.w3c.dom.NodeList;

import com.google.appengine.api.blobstore.BlobInfo;
/*     */ import com.google.appengine.api.blobstore.BlobInfoFactory;
/*     */ import com.google.appengine.api.blobstore.BlobKey;
/*     */ import com.google.appengine.api.blobstore.BlobstoreService;
/*     */ import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
/*     */ import com.google.appengine.api.datastore.DatastoreService;
/*     */ import com.google.appengine.api.datastore.DatastoreServiceFactory;
/*     */ import com.google.appengine.api.datastore.Entity;
/*     */ import com.google.appengine.api.datastore.FetchOptions;
/*     */ import com.google.appengine.api.datastore.Key;
/*     */ import com.google.appengine.api.datastore.KeyFactory;
/*     */ import com.google.appengine.api.datastore.PreparedQuery;
/*     */ import com.google.appengine.api.datastore.Query;
/*     */ import com.google.appengine.api.users.User;
/*     */ import com.google.appengine.api.users.UserService;
/*     */ import com.google.appengine.api.users.UserServiceFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StudentServlet extends BaseServlet
/*     */ {
/*  66 */   private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
/*     */ 
/*  68 */   private static final Logger logger = Logger.getLogger(CustomerServlet.class
/*  69 */     .getCanonicalName());
/*     */ 
/*  70 */   private static final HTMLBuilder html = new HTMLBuilder();
/*  71 */   private static final CSVParser csv = new CSVParser();
/*  72 */   private static final XMLParser xml = new XMLParser();
/*  73 */   private static final PersonDao dao = new PersonDao();
/*  74 */   private static final CourseDao cDao = new CourseDao();
/*  75 */   private static final ApplicationDao appDao = new ApplicationDao();
/*  76 */   private static final Mailer mailer = new Mailer();
/*     */ 
/*  78 */   private static final DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
/*     */ 
/*     */   protected void doGet(HttpServletRequest req, HttpServletResponse resp)
/*     */     throws ServletException, IOException
/*     */   {
/*  83 */     super.doGet(req, resp);
/*     */ 
/*  85 */     String farmBase = (String)req.getSession().getAttribute("farmBase");
/*     */ 
/*  87 */     if (FFSession.needsRedirect(req, resp)) {
/*  88 */       return;
/*     */     }
/*     */ 
/*  91 */     UserService userService = UserServiceFactory.getUserService();
/*  92 */     User user = userService.getCurrentUser();
/*     */ 
/*  97 */     String url = "/jsp/main.jsp";
/*  98 */     String action = (String)req.getAttribute("action");
/*  99 */     String entity = (String)req.getAttribute("entity");
/*     */ 
/* 101 */     if (action == null) {
/* 102 */       action = req.getParameter("action");
/*     */     }
/* 104 */     if ("MassEncrypt".equals(action)) {
/* 105 */       PrintWriter writer = resp.getWriter();
/* 106 */       resp.setContentType("text/html");
/* 107 */       Query q = new Query("Person");
/*     */ 
/* 110 */       PreparedQuery pq = datastore.prepare(q);
/* 111 */       List results = pq.asList(FetchOptions.Builder.withLimit(100000));
/* 112 */       Integer numUpdated = Integer.valueOf(0);
/* 113 */       writer.println("<div># of results: " + results.size() + "</div>");
/* 114 */       
				
				//for (Entity row : results) {
				for (int i=0;i<results.size();i++) {
				  Entity row = (Entity)results.get(i);
/* 115 */         String ssn = (String)row.getProperty("ssn");
/* 116 */         if ((ssn != null) && (ssn.length() >= 9) && (ssn.length() <= 11)) {
/* 117 */           numUpdated = Integer.valueOf(numUpdated.intValue() + 1);
/* 118 */           row.setProperty("ssn", Person.encryptSsn(ssn));
/* 119 */           Util.persistEntity(row);
/*     */         }
/*     */       }
/* 122 */       writer.println("<div>Updated " + numUpdated + " records.</div>");
/* 123 */       writer.close();
/* 124 */       return;
/*     */     }
/*     */ 
/* 127 */     if ("ytd".equals(action)) {
/* 128 */       String[] smonth = { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };
/* 129 */       String sFarm = req.getParameter("farm");
/*     */ 
/* 132 */       for (int month = 0; month < 12; month++) {
/* 133 */         Entity ytd1 = new Entity("ActualDailySalesYTD");
/* 134 */         Entity ytd2 = new Entity("BudgetedDailySalesYTD");
/*     */ 
/* 136 */         ytd1.setProperty("farmBase", sFarm);
/* 137 */         ytd1.setProperty("Month", smonth[month]);
/* 138 */         ytd1.setProperty("Year", "2012");
/* 139 */         ytd2.setProperty("farmBase", sFarm);
/* 140 */         ytd2.setProperty("Month", smonth[month]);
/* 141 */         ytd2.setProperty("Year", "2012");
/*     */ 
/* 143 */         for (int day = 1; day < 32; day++) {
/* 144 */           ytd1.setProperty("Day_" + day, new Double("0.00"));
/* 145 */           ytd2.setProperty("Day_" + day, new Double("0.00"));
/*     */         }
/* 147 */         ApplicationDao.createOrUpdateEntity(ytd1, user);
/* 148 */         ApplicationDao.createOrUpdateEntity(ytd2, user);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 155 */     if ("MassImport".equals(action)) {
/* 156 */       CSVParser.parseData();
/*     */     }
/*     */ 
/* 165 */     boolean redirect = true;
/*     */ 
/* 167 */     if ("View Artifact".equals(action))
/*     */     {
/* 174 */       BlobKey blobKey = new BlobKey(req.getParameter("docKey"));
/* 175 */       BlobInfo b = new BlobInfoFactory().loadBlobInfo(blobKey);
/* 176 */       resp.setContentType(b.getContentType());
/* 177 */       this.blobstoreService.serve(blobKey, resp);
/* 178 */       redirect = false;
/*     */     }
/*     */ 
/* 181 */     if ("Documents".equals(action)) {
/* 182 */       String sKey = req.getParameter("personKey");
/* 183 */       Key key = KeyFactory.stringToKey(sKey);
/* 184 */       Entity Person = ApplicationDao.getEntity(key);
/* 185 */       req.setAttribute("Person", Person);
/*     */ 
/* 187 */       Query query = new Query("PersonArtifact");
/* 188 */       query.setAncestor(key);
/* 189 */       PreparedQuery pq = datastore.prepare(query);
/* 190 */       ArrayList results = ApplicationTools.convertIterableToList(pq.asIterable(FetchOptions.Builder.withLimit(20).chunkSize(20)));
/* 191 */       req.setAttribute("results", results);
/*     */ 
/* 193 */       url = "/jsp/documentRepo/upload.jsp";
/*     */     }
/* 195 */     if ("Delete Artifact".equals(action)) {
/* 196 */       String sKey = req.getParameter("key");
/* 197 */       Key key = KeyFactory.stringToKey(sKey);
/* 198 */       Entity Artifact = ApplicationDao.getEntity(key);
/* 199 */       ApplicationDao.deleteEntity(Artifact);
/*     */ 
/* 202 */       sKey = req.getParameter("personKey");
/* 203 */       key = KeyFactory.stringToKey(sKey);
/* 204 */       Entity Person = ApplicationDao.getEntity(key);
/* 205 */       req.setAttribute("Person", Person);
/* 206 */       Query query = new Query("PersonArtifact");
/* 207 */       query.setAncestor(key);
/* 208 */       PreparedQuery pq = datastore.prepare(query);
/* 209 */       ArrayList results = ApplicationTools.convertIterableToList(pq.asIterable(FetchOptions.Builder.withLimit(20).chunkSize(20)));
/* 210 */       req.setAttribute("results", results);
/*     */ 
/* 213 */       url = "/jsp/documentRepo/upload.jsp";
/*     */     }
/* 215 */     if ("Quick".equals(action)) {
/* 216 */       Entity Person = xml.xmlToEntity("Person", req);
/* 217 */       req.setAttribute("Person", Person);
/*     */ 
/* 219 */       url = "/jsp/student/quickEntry.jsp";
/*     */     }
/*     */ 
/* 236 */     if ("Save Changes".equals(action)) {
/* 237 */       String sKey = req.getParameter("personKey");
/* 238 */       Key key = KeyFactory.stringToKey(sKey);
/* 239 */       Entity Person = ApplicationDao.getEntity(key);
/* 240 */       Entity Address = PersonDao.getAddressByPerson(Person);
/* 241 */       Entity PersonMisc = PersonDao.getPersonMiscByPerson(Person);
/* 242 */       Entity JobSkill = PersonDao.getJobSkillByPerson(Person);
/*     */ 
/* 244 */       if (!Person.getProperty("personStatus").equals(
/* 244 */         req.getParameter("personStatus"))) {
/* 245 */         Person.setProperty("lastStatusUpdated", 
/* 246 */           new Date().toString());
/* 247 */         Person.setProperty("lastPersonStatus", 
/* 248 */           Person.getProperty("personStatus"));
/*     */       }
/* 250 */       Person.setProperty("lastUpdatedDate", new Date());
/* 251 */       Person.setProperty("lastUpdatedBy", user.getEmail());
/* 252 */       if (Address != null) {
/* 253 */         Address.setProperty("lastUpdatedDate", new Date());
/* 254 */         Address.setProperty("lastUpdatedBy", user.getEmail());
/* 255 */         Address = xml.updateEntity(Address, req);
/*     */       } else {
/* 257 */         Address = xml.xmlChildToParentEntity("Address", new Entity(
/* 258 */           "Address"), Person);
/*     */       }
/* 260 */       if (PersonMisc != null) {
/* 261 */         PersonMisc.setProperty("lastUpdatedDate", new Date());
/* 262 */         PersonMisc.setProperty("lastUpdatedBy", user.getEmail());
/* 263 */         PersonMisc = xml.updateEntity(PersonMisc, req);
/*     */       } else {
/* 265 */         PersonMisc = xml.xmlChildToParentEntity("PersonMisc", 
/* 266 */           new Entity("PersonMisc"), Person);
/*     */       }
/* 268 */       if (JobSkill != null) {
/* 269 */         JobSkill.setProperty("lastUpdatedDate", new Date());
/* 270 */         JobSkill.setProperty("lastUpdatedBy", user.getEmail());
/* 271 */         JobSkill = xml.updateEntity(JobSkill, req);
/*     */       } else {
/* 273 */         JobSkill = xml.xmlChildToParentEntity("JobSkill", new Entity(
/* 274 */           "JobSkill"), Person);
/*     */       }
/* 276 */       dao.updateEntity(Person, user, req);
/* 277 */       if (Address != null)
/* 278 */         dao.updateEntity(Address, user, req);
/* 279 */       if (PersonMisc != null)
/* 280 */         dao.updateEntity(PersonMisc, user, req);
/* 281 */       if (JobSkill != null) {
/* 282 */         dao.updateEntity(JobSkill, user, req);
/*     */       }
/* 284 */       url = "/jsp/student/profile/results.jsp";
/*     */     }
/* 286 */     if ("Quick Save".equals(action)) {
/* 287 */       Entity Person = xml.xmlToEntity("Person", req);
/* 288 */       boolean success = quickEntryValidate(Person, req);
/* 289 */       req.setAttribute("Person", Person);
/* 290 */       if (success) {
/* 291 */         Person.setProperty("createdBy", user.getEmail());
/* 292 */         Person.setProperty("farmBase", 
/* 293 */           req.getSession().getAttribute("farmBase"));
/*     */ 
/* 305 */         Person.setProperty("ytdPaid", Integer.valueOf(0));
/* 306 */         Person.setProperty("saved", Integer.valueOf(0));
/* 307 */         Person.setProperty("ssn", org.faithfarm.dataobjects.Person.encryptSsn(Person.getProperty("ssn").toString()));
/* 308 */         PersonDao.createPerson(Person);
/*     */ 
/* 310 */         if (("Active".equals((String)Person.getProperty("personStatus"))) && ("Resident".equals((String)Person.getProperty("personType")))) {
/* 311 */           String body = (String)Person.getProperty("firstName") + " " + (String)Person.getProperty("lastName") + " has been added as a new resident to the system.";
/* 312 */           Mailer.sendNewStudentMail("ricky.raymond.ratliff@gmail.com", "New Resident Added", body);
/* 313 */           Mailer.sendNewStudentMail("rmogged@faithfarm.org", "New Resident Added", body);
/* 314 */           Mailer.sendNewStudentMail("rrosendo@faithfarm.org", "New Resident Added", body);
/* 315 */           Mailer.sendNewStudentMail("gsteffe@faithfarm.org", "New Resident Added", body);
/*     */         }
/*     */ 
/* 318 */         if (("Pending".equals((String)Person.getProperty("personStatus"))) && ("Intake".equals((String)Person.getProperty("personType")))) {
/* 319 */           String body = (String)Person.getProperty("firstName") + " " + (String)Person.getProperty("lastName") + " has been added as a new intake to the system.";
/* 320 */           Mailer.sendNewStudentMail("ricky.raymond.ratliff@gmail.com", "New Intake Added", body);
/* 321 */           Mailer.sendNewStudentMail("rmogged@faithfarm.org", "New Intake Added", body);
/* 322 */           Mailer.sendNewStudentMail("rrosendo@faithfarm.org", "New Intake Added", body);
/* 323 */           Mailer.sendNewStudentMail("gsteffe@faithfarm.org", "New Intake Added", body);
/*     */         }
/*     */ 
/* 327 */         url = "/jsp/student/success.jsp";
/*     */       } else {
/* 329 */         url = "/jsp/student/quickEntry.jsp";
/*     */       }
/*     */     }
/* 332 */     if (("Filter".equals(action)) || ("Index".equals(action)))
/*     */     {
/* 334 */       ArrayList parameters = setSearchParameters(action, req);
/* 335 */       ArrayList results = new ArrayList();
/*     */ 
/* 337 */       if (parameters.size() == 0)
/* 338 */         results = 
/* 339 */           ApplicationDao.getActivePendingResidentsIntakes(farmBase);
/*     */       else
/* 341 */         results = ApplicationDao.getEntities("Person", parameters, 
/* 342 */           null, null, farmBase);
/* 343 */       url = "/jsp/student/profile/results.jsp";
/*     */ 
/* 345 */       if (results.size() > 199) {
/* 346 */         req.setAttribute(
/* 347 */           "message", 
/* 348 */           "The maximum number of results returned was reached.  Please refine your search by using the filter above.");
/*     */       }
/* 350 */       req.getSession().setAttribute("results", results);
/* 351 */       req.setAttribute("size", Integer.valueOf(results.size()));
/*     */     }
/*     */ 
/* 355 */     if ("Export".equals(action)) {
/* 356 */       String status = "Pending";
/* 357 */       String personType = "Intake";
/*     */ 
/* 359 */       url = "/jsp/student/profile/results.jsp?action=export";
/*     */ 
/* 361 */       Iterable results1 = PersonDao.getAllPersons("", "", "", 
/* 362 */         personType, status, "", "", 
/* 363 */         (String)req.getSession()
/* 363 */         .getAttribute("farmBase"));
/* 364 */       ArrayList results = 
/* 365 */         ApplicationTools.convertIterableToList(results1);
/* 366 */       req.getSession().setAttribute("results", results);
/* 367 */       req.setAttribute("size", Integer.valueOf(results.size()));
/*     */     }
/*     */ 
/* 370 */     if ("Export Intake".equals(action)) {
/* 371 */       String sKey = req.getParameter("personKey");
/* 372 */       Key key = KeyFactory.stringToKey(sKey);
/* 373 */       Entity Person = ApplicationDao.getEntity(key);
/* 374 */       req.setAttribute("Person", Person);
/* 375 */       url = "/jsp/student/export.jsp";
/*     */     }
/* 377 */     if ("Export As Resident".equals(action)) {
/* 378 */       String sKey = req.getParameter("personKey");
/* 379 */       Key key = KeyFactory.stringToKey(sKey);
/* 380 */       Entity Person = ApplicationDao.getEntity(key);
/* 381 */       String entryDate = req.getParameter("entryDate");
/* 382 */       String supervisor = req.getParameter("supervisor");
/* 383 */       String work = req.getParameter("workAssignment");
/*     */ 
/* 385 */       Person.setProperty("personType", "Resident");
/* 386 */       Person.setProperty("personStatus", "Active");
/* 387 */       Person.setProperty("lastStatusUpdated", 
/* 388 */         new Date().toString());
/* 389 */       Person.setProperty("currentCourse", "0");
/* 390 */       Person.setProperty("entryDate", entryDate);
/* 391 */       Person.setProperty("supervisor", supervisor);
/* 392 */       Person.setProperty("work", work);
/*     */ 
/* 394 */       if ((entryDate == null) || ("".equals(entryDate))) {
/* 395 */         ArrayList list = new ArrayList();
/* 396 */         list.add("Please select an entry date.");
/* 397 */         req.setAttribute("messages", list);
/* 398 */         req.setAttribute("Person", Person);
/* 399 */         url = "/student?action=Filter";
/*     */       } else {
/* 401 */         PersonDao.createPerson(Person);
/*     */ 
/* 403 */         Iterable results1 = PersonDao.getAllPersons("", "", "", 
/* 404 */           "Intake", "Pending", "", "", 
/* 405 */           (String)req.getSession()
/* 405 */           .getAttribute("farmBase"));
/* 406 */         ArrayList results = 
/* 407 */           ApplicationTools.convertIterableToList(results1);
/* 408 */         req.getSession().setAttribute("results", results);
/* 409 */         req.setAttribute("size", Integer.valueOf(results.size()));
/*     */ 
/* 411 */         if (("Active".equals((String)Person.getProperty("personStatus"))) && ("Resident".equals((String)Person.getProperty("personType")))) {
/* 412 */           String body = (String)Person.getProperty("firstName") + " " + (String)Person.getProperty("lastName") + " has been added as a new resident to the system.";
/* 413 */           Mailer.sendNewStudentMail("ricky.raymond.ratliff@gmail.com", "New Resident Added", body);
/* 414 */           Mailer.sendNewStudentMail("rmogged@faithfarm.org", "New Resident Added", body);
/* 415 */           Mailer.sendNewStudentMail("rrosendo@faithfarm.org", "New Resident Added", body);
/* 416 */           Mailer.sendNewStudentMail("gsteffe@faithfarm.org", "New Resident Added", body);
/*     */         }
/*     */ 
/* 420 */         url = "/student?action=Filter";
/*     */       }
/*     */     }
/*     */     
/* 424 */     if ("Deny Admission".equals(action)) {
/* 425 */       String sKey = req.getParameter("personKey");
/* 426 */       Key key = KeyFactory.stringToKey(sKey);
/* 427 */       Entity Person = ApplicationDao.getEntity(key);
/* 428 */       Person.setProperty("personStatus", "Denied");
/* 429 */       PersonDao.createPerson(Person);
/* 430 */       url = "/student?action=Filter";
/*     */     }
/* 432 */     if ("Revert".equals(action)) {
/* 433 */       Iterable list = PersonDao.getAllPersons(null, null, null, 
/* 434 */         "Resident", "Active", null, null, "Fort Lauderdale");
/* 435 */       //for (Entity result : list) {
				for(Iterator i = list.iterator();i.hasNext();) {
				  Entity result = (Entity)i.next();
				  result.setProperty("personStatus", "Pending");
/* 437 */         result.setProperty("personType", "Intake");
/* 438 */         PersonDao.createPerson(result);
/*     */       }
/*     */     }
/* 441 */     if (("View".equals(action)) || ("Print Card".equals(action)) || 
/* 442 */       ("Print".equals(action))) {
/* 443 */       String sKey = req.getParameter("personKey");
/* 444 */       Key key = KeyFactory.stringToKey(sKey);
/* 445 */       Entity Person = ApplicationDao.getEntity(key);
/* 446 */       Entity Address = PersonDao.getAddressByPerson(Person);
/* 447 */       if (Address == null) {
/* 448 */         Address = xml.xmlToChildEntity("Address", Person, req);
/*     */       }
/* 450 */       Entity PersonMisc = PersonDao.getPersonMiscByPerson(Person);
/* 451 */       if (PersonMisc == null) {
/* 452 */         PersonMisc = xml.xmlToChildEntity("PersonMisc", Person, req);
/*     */       }
/* 454 */       Entity JobSkill = PersonDao.getJobSkillByPerson(Person);
/* 455 */       if (JobSkill == null) {
/* 456 */         JobSkill = xml.xmlToChildEntity("JobSkill", Person, req);
/*     */       }
/* 458 */       NodeList xmlPerson = xml.getEntityProperties("Person", req);
/* 459 */       NodeList xmlPersonMisc = xml.getEntityProperties("PersonMisc", req);
/* 460 */       NodeList xmlAddress = xml.getEntityProperties("Address", req);
/* 461 */       NodeList xmlJobSkill = xml.getEntityProperties("JobSkill", req);
/*     */ 
/* 463 */       req.getSession().setAttribute("Person", Person);
/* 464 */       req.getSession().setAttribute("Address", Address);
/* 465 */       req.getSession().setAttribute("PersonMisc", PersonMisc);
/* 466 */       req.getSession().setAttribute("JobSkill", JobSkill);
/* 467 */       req.getSession().setAttribute("xmlPerson", xmlPerson);
/* 468 */       req.getSession().setAttribute("xmlJobSkill", xmlJobSkill);
/* 469 */       req.getSession().setAttribute("xmlPersonMisc", xmlPersonMisc);
/* 470 */       req.getSession().setAttribute("xmlAddress", xmlAddress);
/*     */ 
/* 472 */       if ("Print".equals(action))
/* 473 */         url = "/jsp/student/profile/print.jsp";
/* 474 */       else if ("View".equals(action))
/* 475 */         url = "/jsp/student/profile/view.jsp";
/*     */       else
/* 477 */         url = "/jsp/student/profile/print_card.jsp";
/*     */     }
/* 479 */     if ("Upload Photo".equals(action)) {
/* 480 */       String sKey = req.getParameter("personKey");
/* 481 */       Key key = KeyFactory.stringToKey(sKey);
/* 482 */       Entity Person = ApplicationDao.getEntity(key);
/* 483 */       req.setAttribute("Person", Person);
/* 484 */       url = "/jsp/student/profile/upload.jsp";
/*     */     }
/* 486 */     if ("Assign Job".equals(action)) {
/* 487 */       String sKey = req.getParameter("personKey");
/* 488 */       Key key = KeyFactory.stringToKey(sKey);
/* 489 */       Entity Person = ApplicationDao.getEntity(key);
/* 490 */       req.setAttribute("Person", Person);
/* 491 */       url = "/jsp/student/profile/work.jsp";
/*     */     }
/* 493 */     if ("Save Work".equals(action)) {
/* 494 */       String sKey = req.getParameter("personKey");
/* 495 */       Key key = KeyFactory.stringToKey(sKey);
/* 496 */       Entity Person1 = ApplicationDao.getEntity(key);
/* 497 */       String workAssignment = req.getParameter("workAssignment");
/* 498 */       String supervisor = req.getParameter("supervisor");
/* 499 */       Person1.setProperty("workAssignment", workAssignment);
/* 500 */       Person1.setProperty("supervisor", supervisor);
/* 501 */       PersonDao.createPerson(Person1);
/* 502 */       url = "/student?action=Filter";
/*     */     }
/* 504 */     if ("Edit".equals(action))
/*     */     {
/* 506 */       String sKey = req.getParameter("personKey");
/* 507 */       Key key = KeyFactory.stringToKey(sKey);
/* 508 */       Entity Person = ApplicationDao.getEntity(key);
/* 509 */       Entity Address = PersonDao.getAddressByPerson(Person);
/* 510 */       if (Address == null)
/* 511 */         Address = xml.xmlToChildEntity("Address", Person, req);
/* 512 */       Entity JobSkill = PersonDao.getJobSkillByPerson(Person);
/* 513 */       if (JobSkill == null)
/* 514 */         JobSkill = xml.xmlToChildEntity("JobSkill", Person, req);
/* 515 */       Entity PersonMisc = PersonDao.getPersonMiscByPerson(Person);
/* 516 */       if (PersonMisc == null)
/* 517 */         PersonMisc = xml.xmlToChildEntity("PersonMisc", Person, req);
/* 518 */       NodeList xmlPerson = xml.getEntityProperties("Person", req);
/* 519 */       NodeList xmlPersonMisc = xml.getEntityProperties("PersonMisc", req);
/* 520 */       NodeList xmlAddress = xml.getEntityProperties("Address", req);
/* 521 */       NodeList xmlJobSkill = xml.getEntityProperties("JobSkill", req);
/*     */ 
/* 523 */       req.setAttribute("Person", Person);
/* 524 */       req.setAttribute("Address", Address);
/* 525 */       req.setAttribute("PersonMisc", PersonMisc);
/* 526 */       req.setAttribute("JobSkill", JobSkill);
/* 527 */       req.setAttribute("xmlPerson", xmlPerson);
/* 528 */       req.setAttribute("xmlJobSkill", xmlJobSkill);
/* 529 */       req.setAttribute("xmlPersonMisc", xmlPersonMisc);
/* 530 */       req.setAttribute("xmlAddress", xmlAddress);
/*     */ 
/* 532 */       url = "/jsp/intake/edit.jsp";
/*     */     }
/*     */ 
/* 538 */     if ("Generate".equals(action)) {
/* 539 */       String rptType = req.getParameter("reportType");
/* 540 */       String startDate = "";
/* 541 */       String personStatus = "";
/* 542 */       String personType = "";
/* 543 */       ArrayList newList = new ArrayList();
/* 544 */       startDate = req.getParameter("startDate");
/*     */ 
/* 546 */       if ("Arrivals".equals(rptType))
/*     */       {
/* 548 */         personStatus = "Active";
/* 549 */         personType = "Resident";
/*     */ 
/* 551 */         ArrayList params = new ArrayList();
/* 552 */         SearchParameter parameter1 = new SearchParameter("personStatus", 
/* 553 */           "Active", Query.FilterOperator.EQUAL);
/* 554 */         SearchParameter parameter2 = new SearchParameter("personType", 
/* 555 */           "Resident", Query.FilterOperator.EQUAL);
/* 556 */         params.add(parameter1);
/* 557 */         params.add(parameter2);
/*     */ 
/* 559 */         ArrayList results = ApplicationDao.getEntities("Person", params, null, 
/* 560 */           null, farmBase);
/* 561 */         newList = new ArrayList();
/* 562 */         for (int i = 0; i < results.size(); i++) {
/* 563 */           Entity resident = (Entity)results.get(i);
/* 564 */           String entryDate = (String)resident.getProperty("entryDate");
/* 565 */           Date date1 = new Date(startDate);
/* 566 */           Date date2 = new Date();
/* 567 */           if (entryDate != null)
/* 568 */             date2 = new Date(entryDate);
/* 569 */           if (date1.compareTo(date2) < 0) {
/* 570 */             newList.add(resident);
/*     */           }
/*     */         }
/* 573 */         url = "/jsp/reports/new_arrivals/results.jsp";
/* 574 */       } else if ("Departures".equals(rptType)) {
/* 575 */         ArrayList parameters = new ArrayList();
/* 576 */         parameters.add("Dismissed");
/* 577 */         parameters.add("Graduated");
/* 578 */         parameters.add("Walked Off");
/* 579 */         parameters.add("Left Voluntarily");
/*     */ 
/* 581 */         ArrayList results = ApplicationDao.getEntitiesWithInClause("Person", "personStatus", parameters, farmBase);
/* 582 */         newList = new ArrayList();
/* 583 */         for (int i = 0; i < results.size(); i++) {
/* 584 */           Entity resident = (Entity)results.get(i);
/* 585 */           String exitDate = (String)resident.getProperty("exitDate");
/*     */ 
/* 587 */           if ((exitDate != null) && (exitDate.length() > 0)) {
/* 588 */             Date date1 = new Date(exitDate);
/* 589 */             Date date2 = new Date(startDate);
/*     */ 
/* 591 */             if (date1.compareTo(date2) > 0)
/* 592 */               newList.add(resident);
/*     */           }
/*     */           else {
/* 595 */             newList.add(resident);
/*     */           }
/*     */         }
/* 598 */         url = "/jsp/reports/departures/results.jsp";
/*     */       }
/*     */ 
/* 602 */       req.setAttribute("results", newList);
/*     */     }
/*     */ 
/* 607 */     if (redirect)
/* 608 */       req.getRequestDispatcher(req.getContextPath() + url).forward(req, resp);
/*     */   }
/*     */ 
/*     */   protected void doPost(HttpServletRequest req, HttpServletResponse resp)
/*     */     throws ServletException, IOException
/*     */   {
/* 614 */     doGet(req, resp);
/*     */   }
/*     */ 
/*     */   private boolean quickEntryValidate(Entity person, HttpServletRequest req)
/*     */   {
/* 621 */     Validator validator = new Validator();
/* 622 */     ArrayList messages = new ArrayList();
/*     */ 
/* 624 */     messages = validator.validateRequired("first name", 
/* 625 */       (String)person.getProperty("firstName"), messages);
/* 626 */     messages = validator.validateRequired("last name", 
/* 627 */       (String)person.getProperty("lastName"), messages);
/* 628 */     messages = validator.validateRequired("social security number", 
/* 629 */       (String)person.getProperty("ssn"), messages);
/* 630 */     messages = validator.validateRequired("current course", 
/* 631 */       (String)person.getProperty("currentCourse"), messages);
/* 632 */     messages = validator.validateRequired("birthdate", 
/* 633 */       (String)person.getProperty("dateOfBirth"), messages);
/* 634 */     messages = validator.validateRequired("entrydate", 
/* 635 */       (String)person.getProperty("entryDate"), messages);
/* 636 */     messages = validator.validateRequired("person type", 
/* 637 */       (String)person.getProperty("personType"), messages);
/* 638 */     messages = validator.validateRequired("status", 
/* 639 */       (String)person.getProperty("personStatus"), messages);
/* 640 */     messages = validator.validateRequired("supervisor", 
/* 641 */       (String)person.getProperty("supervisor"), messages);
/* 642 */     messages = validator.validateRequired("work assignment", 
/* 643 */       (String)person.getProperty("workAssignment"), messages);
/*     */ 
/* 645 */     if (messages.size() > 0) {
/* 646 */       req.setAttribute("messages", messages);
/* 647 */       return false;
/*     */     }
/*     */ 
/* 650 */     return true;
/*     */   }
/*     */ 
/*     */   private ArrayList setSearchParameters(String action, HttpServletRequest req)
/*     */   {
/* 655 */     ArrayList parameters = new ArrayList();
/* 656 */     String firstName = html.cleanData(req.getParameter("firstName"));
/* 657 */     String middleInitial = html
/* 658 */       .cleanData(req.getParameter("middleInitial"));
/* 659 */     String lastName = html.cleanData(req.getParameter("lastName"));
/* 660 */     String personStatus = html.cleanData(req.getParameter("personStatus"));
/* 661 */     String personType = html.cleanData(req.getParameter("personType"));
/* 662 */     String farmLocation = html.cleanData(req.getParameter("farmLocation"));
/* 663 */     String ssn = html.cleanData(req.getParameter("ssn"));
/*     */ 
/* 665 */     req.getSession().setAttribute("firstName", firstName);
/* 666 */     req.getSession().setAttribute("lastName", lastName);
/* 667 */     req.getSession().setAttribute("personStatus", personStatus);
/* 668 */     req.getSession().setAttribute("personType", personType);
/*     */ 
/* 670 */     if (!"".equals(firstName))
/* 671 */       parameters.add(new SearchParameter("firstName", firstName, 
/* 672 */         Query.FilterOperator.EQUAL));
/* 673 */     if (!"".equals(middleInitial))
/* 674 */       parameters.add(new SearchParameter("middleInitial", middleInitial, 
/* 675 */         Query.FilterOperator.EQUAL));
/* 676 */     if (!"".equals(lastName))
/* 677 */       parameters.add(new SearchParameter("lastName", lastName, 
/* 678 */         Query.FilterOperator.EQUAL));
/* 679 */     if (!"".equals(farmLocation))
/* 680 */       parameters.add(new SearchParameter("farmLocation", farmLocation, 
/* 681 */         Query.FilterOperator.EQUAL));
/* 682 */     if (!"".equals(ssn))
/* 683 */       parameters
/* 684 */         .add(new SearchParameter("ssn", ssn, Query.FilterOperator.EQUAL));
/* 685 */     if (!"".equals(personStatus))
/* 686 */       parameters.add(new SearchParameter("personStatus", personStatus, 
/* 687 */         Query.FilterOperator.EQUAL));
/* 688 */     if (!"".equals(personType)) {
/* 689 */       parameters.add(new SearchParameter("personType", personType, 
/* 690 */         Query.FilterOperator.EQUAL));
/*     */     }
/* 692 */     if (!"Index".equals(action)) {
/* 693 */       parameters.add(new SearchParameter("personType", "Customer", 
/* 694 */         Query.FilterOperator.NOT_EQUAL));
/*     */     }
/*     */ 
/* 702 */     return parameters;
/*     */   }
/*     */ }

/* Location:           C:\development\workspace\faithfarmoss\WEB-INF\classes\
 * Qualified Name:     org.faithfarm.StudentServlet
 * JD-Core Version:    0.6.2
 */