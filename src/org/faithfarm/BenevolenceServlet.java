/*     */ package org.faithfarm;
/*     */ 
/*     */ /*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Logger;

/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;

/*     */ import org.faithfarm.dataobjects.Benevolence;
/*     */ import org.faithfarm.dataobjects.Deduction;
/*     */ import org.faithfarm.dataobjects.PacketBreakdown;
/*     */ import org.faithfarm.dataobjects.ResidentBenevolence;
/*     */ import org.faithfarm.dataobjects.SearchParameter;
/*     */ import org.faithfarm.datawriters.ApplicationDao;
/*     */ import org.faithfarm.datawriters.PersonDao;
/*     */ import org.faithfarm.utilities.ApplicationTools;
/*     */ import org.faithfarm.utilities.FFSession;
/*     */ import org.json.simple.JSONObject;
/*     */ import org.json.simple.JSONValue;

import com.google.appengine.api.datastore.DatastoreService;
/*     */ import com.google.appengine.api.datastore.DatastoreServiceFactory;
/*     */ import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
/*     */ import com.google.appengine.api.datastore.Key;
/*     */ import com.google.appengine.api.datastore.KeyFactory;
/*     */ import com.google.appengine.api.datastore.PreparedQuery;
/*     */ import com.google.appengine.api.datastore.Query;
/*     */ import com.google.appengine.api.memcache.MemcacheService;
/*     */ import com.google.appengine.api.memcache.MemcacheServiceFactory;
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
/*     */ public class BenevolenceServlet extends BaseServlet
/*     */ {
/*     */   private static final long serialVersionUID = 8298748132232744091L;
/*  52 */   private static final Calendar THE_FIRST = Calendar.getInstance();
/*     */  
/*  54 */   private static final DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
/*     */ 
/*  56 */   private static final Logger logger = Logger.getLogger(BenevolenceServlet.class.getCanonicalName());
/*     */ 
/*     */   protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
/*     */   {
/*  60 */     super.doGet(req, resp);
/*     */ 
/*  62 */     if (FFSession.needsRedirect(req, resp)) {
/*  63 */       return;
/*     */     }
/*     */ 
/*  66 */     UserService userService = UserServiceFactory.getUserService();
/*  67 */     User user = userService.getCurrentUser();
/*  68 */     HttpSession session = req.getSession();
/*     */ 
/*  70 */     String farmBase = (String)session.getAttribute("farmBase");
/*  71 */     Benevolence.setFarmBase(farmBase);
/*     */ 
/*  74 */     THE_FIRST.set(2012, 0, 1, 0, 0, 0);
/*     */ 
/*  78 */     Calendar thisFriday = Benevolence.getReportDay();
/*     */ 
/*  80 */     Entity metaData = Benevolence.getMetaData();
/*     */ 
/*  82 */     req.setAttribute("theFirst", THE_FIRST.getTime());
/*  83 */     req.setAttribute("thisFriday", thisFriday.getTime());
/*     */ 
/*  85 */     String uri = req.getRequestURI();
/*  86 */     String[] split = uri.split("/benevolence/");
/*  87 */     String Action = null;
/*  88 */     if (split.length != 2)
/*  89 */       Action = "";
/*     */     else {
/*  91 */       Action = split[1];
/*     */     }
/*     */ 
/*  94 */     if ("".equals(Action)) {
/*  95 */       Action = req.getParameter("action");
/*     */     }
/*  97 */     if (Action == null) {
/*  98 */       Action = "";
/*     */     }
/* 100 */     req.setAttribute("action", Action);
/*     */ 
/* 102 */     if (Action.equals("ManualAdjustments"))
/*     */     {
/* 104 */       ArrayList parameters = new ArrayList();
/* 105 */       SearchParameter param = new SearchParameter("personType", 
/* 106 */         "Resident", Query.FilterOperator.EQUAL);
/* 107 */       parameters.add(param);
/* 108 */       ArrayList residents = ApplicationDao.getEntities("Person", 
/* 109 */         parameters, "lastName", null, 
/* 110 */         (String)req.getSession()
/* 110 */         .getAttribute("farmBase"));
/*     */ 
/* 112 */       for (int i = 0; i < residents.size(); i++) {
/* 113 */         Entity Resident = (Entity)residents.get(i);
/*     */ 
/* 115 */         Key key = Resident.getKey();
/* 116 */         Long value1 = (Long)Resident.getProperty("ytdPaid");
/* 117 */         Long value2 = (Long)Resident.getProperty("savings");
/*     */ 
/* 119 */         String prepaidYTD = req.getParameter("prepaidYTD_" + 
/* 120 */           KeyFactory.keyToString(key));
/* 121 */         String savingsYTD = req.getParameter("prepaidSavings_" + 
/* 122 */           KeyFactory.keyToString(key));
/*     */ 
/* 124 */         if ((prepaidYTD != null) && (!"".equals(prepaidYTD)))
/* 125 */           prepaidYTD = prepaidYTD.replace(".0", "");
/* 126 */         if ((savingsYTD != null) && (!"".equals(savingsYTD))) {
/* 127 */           savingsYTD = savingsYTD.replace(".0", "");
/*     */         }
/* 129 */         if (prepaidYTD == null)
/* 130 */           prepaidYTD = "0";
/* 131 */         if (savingsYTD == null)
/* 132 */           savingsYTD = "0";
/* 133 */         if (value1 == null)
/* 134 */           value1 = new Long("0");
/* 135 */         if (value2 == null) {
/* 136 */           value2 = new Long("0");
/*     */         }
/*     */ 
/* 142 */         if (prepaidYTD != null) {
/* 143 */           long paid = new Long(prepaidYTD).longValue();
/* 144 */           if (value1.longValue() != paid)
/* 145 */             Resident.setProperty("ytdPaid", Long.valueOf(paid));
/*     */         }
/* 147 */         if (savingsYTD != null) {
/* 148 */           long savings = new Long(savingsYTD).longValue();
/* 149 */           if (value1.longValue() != savings) {
/* 150 */             Resident.setProperty("savings", Long.valueOf(savings));
/*     */           }
/*     */         }
/* 153 */         "Acosta"
/* 154 */           .equals(Resident.getProperty("lastName").toString());
/*     */       }
/*     */ 
/*     */     }
/* 158 */     else if (Action.equals("CalculateDistribution"))
/*     */     {
/* 160 */       String admin = req.getParameter("admin");
/*     */ 
/* 162 */       ArrayList updatedResidentList = new ArrayList();
/* 163 */       ArrayList parameters = new ArrayList();
/*     */ 
/* 167 */       SearchParameter param = new SearchParameter("personType", 
/* 168 */         "Resident", Query.FilterOperator.EQUAL);
/* 169 */       parameters.add(param);
/*     */ 
/* 171 */       ArrayList residents = ApplicationDao.getEntities("Person", 
/* 172 */         parameters, "lastName", null, 
/* 173 */         (String)req.getSession()
/* 173 */         .getAttribute("farmBase"));
/*     */ 
/* 175 */       ArrayList system1Residents = new ArrayList();
/* 176 */       ArrayList system2Residents = new ArrayList();
/* 177 */       ArrayList omegaResidents = new ArrayList();
/* 178 */       ArrayList slsResidents = new ArrayList();
/* 179 */       ArrayList leftResidents = new ArrayList();
/*     */ 
/* 181 */       for (int i = 0; i < residents.size(); i++)
/*     */       {
/* 183 */         Entity Resident = (Entity)residents.get(i);
/* 184 */         String status = (String)Resident.getProperty("personStatus");
/*     */ 
/* 186 */         ResidentBenevolence rBenevolence = new ResidentBenevolence();
/* 187 */         rBenevolence.setFirstName(
/* 188 */           (String)Resident
/* 188 */           .getProperty("firstName"));
/* 189 */         rBenevolence.setLastName(
/* 190 */           (String)Resident
/* 190 */           .getProperty("lastName"));
/*     */ 
/* 192 */         String exitDate = "";
/*     */         try {
/* 194 */           exitDate = (String)Resident.getProperty("exitDate");
/*     */         } catch (Exception e) {
/* 196 */           exitDate = "unknown";
/*     */         }
/*     */ 
/* 199 */         boolean newPlan = true;
/* 200 */         String entryDate = (String)Resident.getProperty("entryDate");
/*     */ 
/* 202 */         if (entryDate == null) {
/* 203 */           logger.severe("Null entryDate for student: " + 
/* 204 */             Resident.getKey().getId());
/* 205 */           return;
/*     */         }
/*     */ 
/* 208 */         Date beginDate = new Date("01/01/2012");
/* 209 */         Date date1 = null;
/*     */         try {
/* 211 */           if ((entryDate == null) || (entryDate.equals(""))) {
/*     */             continue;
/*     */           }
/* 214 */           date1 = new Date(entryDate);
/*     */         } catch (IllegalArgumentException e) {
/* 216 */           logger.severe("Illegal argument to deprecated Date constructor: " + 
/* 217 */             entryDate);
/* 218 */           logger.severe("Student id: " + Resident.getKey().getId());
/*     */         }
/* 220 */         if (date1.compareTo(beginDate) != 1)
/* 221 */           newPlan = false;
/* 222 */         SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
/* 223 */         rBenevolence.setEntryDate(dateFormat.format(date1));
/*     */ 
/* 225 */         if (("Active".equals(status)) || ("SLS".equals(status)) || 
/* 226 */           ("Omega School".equals(status)))
/*     */         {
/* 232 */           long amtDue = 7L;
/* 233 */           if (("Active".equals(status)) && (!newPlan))
/* 234 */             amtDue = 20L;
/* 235 */           else if ("SLS".equals(status))
/* 236 */             amtDue = 
/* 237 */               Benevolence.getAmountDueSLSFromEntity(Resident).intValue();
/* 238 */           else if ("Omega School".equals(status)) {
/* 239 */             amtDue = 25L;
/*     */           }
/* 241 */           rBenevolence.setAmtDue(amtDue);
/* 242 */           int weeks = Integer.parseInt(
/* 243 */             Benevolence.getWeeksFromEntity(Resident), 10);
/* 244 */           rBenevolence.setWeeks(weeks);
/*     */ 
/* 250 */           Long value = new Long("0");
/* 251 */           if (Resident.getProperty("ytdPaid") != null) {
/* 252 */             value = (Long)Resident.getProperty("ytdPaid");
/*     */           }
/*     */ 
/* 261 */           long prepaidYTD = 0L;
/*     */ 
/* 265 */           if (!newPlan)
/* 266 */             prepaidYTD = value.longValue();
/* 267 */           if ((newPlan) && (weeks > 1))
/* 268 */             prepaidYTD = weeks * 7;
/* 269 */           else if ((newPlan) && (weeks == 1)) {
/* 270 */             prepaidYTD = 7L;
/*     */           }
/* 272 */           long prevSavings = 0L;
/* 273 */           if ((newPlan) && (weeks > 12))
/* 274 */             prevSavings = (weeks - 13) * 7;
/* 275 */           if ((newPlan) && (weeks == 12))
/* 276 */             prevSavings = 0L;
/* 277 */           if (weeks < 12)
/* 278 */             prevSavings = 0L;
/* 279 */           if (!newPlan)
/*     */           {
/* 281 */             value = new Long("0");
/* 282 */             if ((Long)Resident.getProperty("savings") != null) {
/* 283 */               value = (Long)Resident.getProperty("savings");
/*     */             }
/* 285 */             prevSavings = value.longValue();
/*     */           }
/*     */ 
/* 289 */           long lessDeduction = 0L;
/* 290 */           String deductionDescription = "";
/*     */ 
/* 292 */           List fines = 
/* 293 */             Deduction.getDeductionsForEntity(Resident);
/* 294 */           if (fines.size() > 0) {
/* 295 */             //for (Entity Fine : fines) {
					for(Iterator it = fines.iterator();it.hasNext();) {
						Entity Fine = (Entity)it.next();

/* 296 */               Long fine = (Long)Fine.getProperty("amount");
/* 297 */               lessDeduction += fine.longValue();
/* 298 */               deductionDescription = "Fines";
/*     */             }
/*     */           }
/*     */ 
/* 302 */           long amtPaid = amtDue - lessDeduction;
/* 303 */           long amtPaidYTD = prepaidYTD + amtPaid;
/* 304 */           long weeklySavings = 
/* 305 */             Benevolence.getWeeklySavingsFromEntity(Resident);
/*     */ 
/* 307 */           long totalSavings = prevSavings;
/* 308 */           if ((newPlan) && (weeks > 12)) {
/* 309 */             totalSavings = prevSavings + weeklySavings;
/*     */           }
/* 311 */           rBenevolence.setPrepaidYTD(prepaidYTD);
/* 312 */           rBenevolence.setPrevSavings(prevSavings);
/* 313 */           rBenevolence.setLessDeduction(lessDeduction);
/* 314 */           rBenevolence.setDeductionDescription(deductionDescription);
/* 315 */           rBenevolence.setAmtPaid(amtPaid);
/* 316 */           rBenevolence.setWeeklySavings(weeklySavings);
/* 317 */           rBenevolence.setAmtPaidYTD(amtPaidYTD);
/* 318 */           rBenevolence.setTotalSavings(totalSavings);
/* 319 */           rBenevolence.setPersonKey(Resident.getKey());
/*     */ 
/* 322 */           Resident.setProperty("ytdPaid", Long.valueOf(amtPaidYTD));
/* 323 */           Resident.setProperty("savings", Long.valueOf(totalSavings));
/* 324 */           Resident.setProperty("ytdPaidLastUpdated", 
/* 325 */             new Date());
/* 326 */           Resident.setProperty("ytdPaidLastUpdated", 
/* 327 */             new Date());
/*     */ 
/* 329 */           if ("Active".equals(status)) {
/* 330 */             if (newPlan)
/* 331 */               system1Residents.add(rBenevolence);
/*     */             else
/* 333 */               system2Residents.add(rBenevolence);
/* 334 */           } else if ("Omega School".equals(status))
/* 335 */             omegaResidents.add(rBenevolence);
/* 336 */           else if ("SLS".equals(status)) {
/* 337 */             slsResidents.add(rBenevolence);
/*     */           }
/*     */ 
/* 340 */           updatedResidentList.add(Resident);
/*     */         }
/*     */ 
/* 343 */         if (("Walked Off".equals(status)) || ("Dismissed".equals(status)) || 
/* 344 */           ("Left Voluntarily".equals(status))) {
/* 345 */           long amtDue = 0L;
/* 346 */           rBenevolence.setAmtDue(amtDue);
/*     */ 
/* 348 */           int weeks = Integer.parseInt(
/* 349 */             Benevolence.getWeeksFromEntity(Resident), 10);
/* 350 */           rBenevolence.setWeeks(weeks);
/*     */ 
/* 352 */           Long value = (Long)Resident.getProperty("ytdPaid");
/* 353 */           if (value == null) {
/* 354 */             value = new Long("0");
/*     */           }
/* 356 */           long prepaidYTD = new Long(value.longValue()).longValue();
/* 357 */           long prevSavings = 0L;
/* 358 */           if ((newPlan) && (weeks > 12))
/* 359 */             prevSavings = (weeks - 13) * 7;
/* 360 */           if ((newPlan) && (weeks == 12))
/* 361 */             prevSavings = 0L;
/* 362 */           if (!newPlan) {
/* 363 */             value = (Long)Resident.getProperty("savings");
/* 364 */             if (value == null)
/* 365 */               value = new Long("0");
/* 366 */             prevSavings = new Long(value.longValue()).longValue();
/*     */           }
/*     */ 
/* 369 */           long lessDeduction = 0L;
/* 370 */           String deductionDescription = "";
/* 371 */           long amtPaid = 0L;
/* 372 */           long amtPaidYTD = prepaidYTD;
/* 373 */           long weeklySavings = 0L;
/* 374 */           long totalSavings = prevSavings;
/*     */ 
/* 376 */           rBenevolence.setPrepaidYTD(prepaidYTD);
/* 377 */           rBenevolence.setPrevSavings(prevSavings);
/* 378 */           rBenevolence.setLessDeduction(lessDeduction);
/* 379 */           rBenevolence.setDeductionDescription(deductionDescription);
/* 380 */           rBenevolence.setAmtPaid(amtPaid);
/* 381 */           rBenevolence.setWeeklySavings(weeklySavings);
/* 382 */           rBenevolence.setAmtPaidYTD(amtPaidYTD);
/* 383 */           rBenevolence.setTotalSavings(totalSavings);
/* 384 */           rBenevolence.setExitDate(exitDate);
/* 385 */           rBenevolence.setPersonKey(Resident.getKey());
/*     */ 
/* 387 */           if (newPlan) {
/* 388 */             leftResidents.add(rBenevolence);
/*     */           }
/* 390 */           if (!"".equals(exitDate)) {
/* 391 */             Date date2 = new Date(exitDate);
/* 392 */             if ((!newPlan) && (date2.compareTo(beginDate) == 1) && 
/* 393 */               (prepaidYTD > 0L)) {
/* 394 */               leftResidents.add(rBenevolence);
/*     */             }
/*     */           }
/*     */         }
/* 398 */         req.getSession().setAttribute("benevolence", 
/* 399 */           updatedResidentList);
/*     */       }
/*     */ 
/* 402 */       req.setAttribute("system1Residents", system1Residents);
/* 403 */       req.setAttribute("system2Residents", system2Residents);
/* 404 */       req.setAttribute("slsResidents", slsResidents);
/* 405 */       req.setAttribute("omegaResidents", omegaResidents);
/* 406 */       req.setAttribute("leftResidents", leftResidents);
/*     */ 
/* 408 */       if ((admin != null) && ("ManualAdjustments".equals(admin)))
/* 409 */         req.getRequestDispatcher(
/* 410 */           req.getContextPath() + 
/* 411 */           "/jsp/student/benevolence/manualAdjustments.jsp")
/* 412 */           .forward(req, resp);
/*     */       else
/* 414 */         req.getRequestDispatcher(
/* 415 */           req.getContextPath() + 
/* 416 */           "/jsp/student/benevolence/distribution.jsp")
/* 417 */           .forward(req, resp);
/*     */     }
/* 419 */     else if (Action.equals("ProcessBenevolence"))
/*     */     {
/* 421 */       ArrayList Residents = (ArrayList)req.getSession().getAttribute(
/* 422 */         "benevolence");
/*     */ 
/* 424 */       for (int i = 0; i < Residents.size(); i++) {
/* 425 */         Entity Resident = (Entity)Residents.get(i);
/* 426 */         ApplicationDao.createOrUpdateEntity(Resident, user);
/*     */       }
/*     */ 
/*     */     }
/* 431 */     else if (Action.equals("FineStudent")) {
/* 432 */       String studentId = req.getParameter("studentId");
/* 433 */       if (studentId == null) {
/* 434 */         ArrayList finable = 
/* 435 */           PersonDao.getFinablePersons((String)req.getSession()
/* 436 */           .getAttribute("farmBase"));
/* 437 */         req.setAttribute("finable", finable);
/* 438 */         req.getRequestDispatcher(
/* 439 */           req.getContextPath() + 
/* 440 */           "/jsp/benevolence/fine_student_select.jsp")
/* 441 */           .forward(req, resp);
/*     */       } else {
/* 443 */         Entity person = PersonDao.getSinglePerson(studentId);
/* 444 */         if (person != null) {
/* 445 */           req.setAttribute("person", person);
/*     */         }
/*     */         else {
/* 448 */           resp.sendRedirect("/benevolence?action=FineStudent");
/* 449 */           return;
/*     */         }
/* 451 */         req.getRequestDispatcher(
/* 452 */           req.getContextPath() + 
/* 453 */           "/jsp/benevolence/fine_student.jsp").forward(
/* 454 */           req, resp);
/*     */       }
/*     */     } else { if (Action.equals("Test")) {
/* 457 */         Query q = new Query("Person");
/* 458 */         q.setFilter(Query.CompositeFilterOperator.and(new Query.Filter[] { new Query.FilterPredicate(
/* 459 */           "personType", Query.FilterOperator.EQUAL, "Resident"), 
/* 460 */           new Query.FilterPredicate("farmBase", 
/* 461 */           Query.FilterOperator.EQUAL, "Fort Lauderdale") }));
/* 462 */         q.addSort("lastName", Query.SortDirection.ASCENDING);
/* 463 */         PreparedQuery pq = datastore.prepare(q);
/* 464 */         List results = pq.asList(
/* 465 */           FetchOptions.Builder.withChunkSize(512));
/* 466 */         req.setAttribute("results", results);
/* 467 */         req.getRequestDispatcher(
/* 468 */           req.getContextPath() + "/jsp/benevolence/ytd.jsp").forward(
/* 469 */           req, resp);
/* 470 */         return;
/* 471 */       }if (Action.equals("DeleteFine")) {
/* 472 */         String personId = req.getParameter("personId");
/* 473 */         String fineId = req.getParameter("fineId");
/* 474 */         if (fineId == null) {
/* 475 */           resp.sendRedirect("/benevolence?action=EditFines");
/* 476 */           return;
/*     */         }
/* 478 */         if (personId == null) {
/* 479 */           resp.sendRedirect("/benevolence?action=EditFines");
/*     */         }
/* 481 */         Entity deduction = Deduction.getByIdAndParentId(
/* 482 */           Integer.parseInt(fineId, 10), 
/* 483 */           Integer.parseInt(personId, 10));
/* 484 */         Entity person = Deduction.getPersonByDeductionEntity(deduction);
/* 485 */         datastore.delete(new Key[] { deduction.getKey() });
/* 486 */         req.setAttribute("fine", deduction);
/* 487 */         req.setAttribute("person", person);
/* 488 */         req.getRequestDispatcher(
/* 489 */           req.getContextPath() + 
/* 490 */           "/jsp/benevolence/delete_fine_success.jsp")
/* 491 */           .forward(req, resp);
/* 492 */       } else if (Action.equals("EditFine")) {
/* 493 */         String fineId = req.getParameter("fineId");
/* 494 */         String personId = req.getParameter("personId");
/* 495 */         if (fineId == null) {
/* 496 */           resp.sendRedirect("/benevolence?action=EditFines");
/* 497 */           return;
/*     */         }
/* 499 */         if (personId == null) {
/* 500 */           resp.sendRedirect("/benevolence?action=EditFines");
/*     */         }
/* 502 */         Entity deduction = Deduction.getByIdAndParentId(
/* 503 */           Integer.parseInt(fineId, 10), 
/* 504 */           Integer.parseInt(personId, 10));
/* 505 */         Entity person = Deduction.getPersonByDeductionEntity(deduction);
/* 506 */         req.setAttribute("fine", deduction);
/* 507 */         req.setAttribute("person", person);
/*     */ 
/* 509 */         req.getRequestDispatcher(
/* 510 */           req.getContextPath() + "/jsp/benevolence/edit_fine.jsp")
/* 511 */           .forward(req, resp);
/* 512 */       } else if (Action.equals("EditFines")) {
/* 513 */         HashMap finesAndPersons = 
/* 514 */           Deduction.getPendingDeductions();
/*     */ 
/* 516 */         req.setAttribute("finesAndPersons", finesAndPersons);
/* 517 */         req.getRequestDispatcher(
/* 518 */           req.getContextPath() + "/jsp/benevolence/edit_fines.jsp")
/* 519 */           .forward(req, resp);
/* 520 */       } else if (Action.equals("SetPseudoweek")) {
/* 521 */         req.setAttribute("weekOffset", metaData.getProperty("weekOffset"));
/* 522 */         req.getRequestDispatcher(
/* 523 */           req.getContextPath() + 
/* 524 */           "/jsp/benevolence/set_pseudoweek.jsp").forward(
/* 525 */           req, resp);
/* 526 */       } else if (Action.equals("ResidentDistribution")) {
/* 527 */         Map activeRecipientMap = 
/* 528 */           Benevolence.sortBenevolenceData(
/* 529 */           Benevolence.getBenevolenceData("Active"));
/* 530 */         Map omegaRecipientMap = 
/* 531 */           Benevolence.getBenevolenceData("Omega School");
/* 532 */         Map slsRecipientMap = 
/* 533 */           Benevolence.getBenevolenceData("SLS");
/* 534 */         Map otherRecipientMap = 
/* 535 */           Benevolence.getBenevolenceData("Other");
/* 536 */         req.setAttribute("activeRecipientMap", activeRecipientMap);
/* 537 */         req.setAttribute("omegaRecipientMap", omegaRecipientMap);
/* 538 */         req.setAttribute("slsRecipientMap", slsRecipientMap);
/* 539 */         req.setAttribute("otherRecipientMap", otherRecipientMap);
/* 540 */         req.getRequestDispatcher(
/* 541 */           req.getContextPath() + 
/* 542 */           "/jsp/benevolence/resident_distribution.jsp")
/* 543 */           .forward(req, resp);
/* 544 */       } else if (Action.equals("PacketBreakdown")) {
/* 545 */         HashMap breakdown = 
/* 546 */           PacketBreakdown.getPacketBreakdowns(farmBase);
/* 547 */         req.setAttribute("breakdown", breakdown);
/* 548 */         req.getRequestDispatcher(
/* 549 */           req.getContextPath() + 
/* 550 */           "/jsp/benevolence/packet_breakdown.jsp").forward(
/* 551 */           req, resp);
/* 552 */       } else if (Action.equals("ChangeOrder")) {
/* 553 */         PacketBreakdown studentBreakdown = 
/* 554 */           PacketBreakdown.getStudentBreakdown(farmBase);
/* 555 */         PacketBreakdown omegaBreakdown = 
/* 556 */           PacketBreakdown.getOmegaBreakdown(farmBase);
/* 557 */         PacketBreakdown slsBreakdown = 
/* 558 */           PacketBreakdown.getSLSBreakdown(farmBase);
/* 559 */         req.setAttribute("studentBreakdown", studentBreakdown);
/* 560 */         req.setAttribute("omegaBreakdown", omegaBreakdown);
/* 561 */         req.setAttribute("slsBreakdown", slsBreakdown);
/* 562 */         req.getRequestDispatcher(
/* 563 */           req.getContextPath() + "/jsp/benevolence/change_order.jsp")
/* 564 */           .forward(req, resp);
/* 565 */       } else if (Action.equals("Confirmation")) {
/* 566 */         Iterable residents = Benevolence.getResidents("Active", 
/* 567 */           (String)req.getSession().getAttribute("farmBase"));
/* 568 */         Iterable omega = Benevolence.getResidents("Omega School", 
/* 569 */           (String)req.getSession().getAttribute("farmBase"));
/* 570 */         Iterable sls = Benevolence.getResidents("SLS", 
/* 571 */           (String)req
/* 571 */           .getSession().getAttribute("farmBase"));
/* 572 */         req.setAttribute("residents", residents);
/* 573 */         req.setAttribute("omega", omega);
/* 574 */         req.setAttribute("sls", sls);
/* 575 */         req.getRequestDispatcher(
/* 576 */           req.getContextPath() + "/jsp/benevolence/confirmation.jsp")
/* 577 */           .forward(req, resp);
/* 578 */       } else if (Action.equals("ProcessConfirm")) {
/* 579 */         String lastProcess = (String)metaData.getProperty("lastProcess");
/* 580 */         SimpleDateFormat fromFormat = new SimpleDateFormat("MM/dd/yy");
/* 581 */         Date reportDay = Benevolence.getReportDay().getTime();
/*     */ 
/* 591 */         Boolean success = Benevolence.processWeek();
/* 592 */         req.setAttribute("success", success);
/* 593 */         req.getRequestDispatcher(
/* 594 */           req.getContextPath() + "/jsp/benevolence/postprocess.jsp")
/* 595 */           .forward(req, resp);
/* 596 */       } else if (Action.equals("Process")) {
/* 597 */         String lastProcess = (String)metaData.getProperty("lastProcess");
/* 598 */         SimpleDateFormat fromFormat = new SimpleDateFormat("MM/dd/yy");
/* 599 */         Date reportDay = Benevolence.getReportDay().getTime();
/* 600 */         if (!lastProcess.isEmpty()) {
/*     */           try {
/* 602 */             Date lastProcessDate = fromFormat.parse(lastProcess);
/* 603 */             if ((lastProcessDate.equals(reportDay)) || 
/* 604 */               (lastProcessDate.after(reportDay))) {
/* 605 */               req.getRequestDispatcher(
/* 606 */                 req.getContextPath() + 
/* 607 */                 "/jsp/benevolence/noprocess.jsp")
/* 608 */                 .forward(req, resp);
/* 609 */               return;
/*     */             }
/*     */           } catch (ParseException e) {
/* 612 */             e.printStackTrace();
/*     */           }
/*     */         }
/* 615 */         req.setAttribute("reportDay", fromFormat.format(reportDay));
/* 616 */         req.getRequestDispatcher(
/* 617 */           req.getContextPath() + "/jsp/benevolence/preprocess.jsp")
/* 618 */           .forward(req, resp);
/*     */       } else {
/* 620 */         req.setAttribute("lastProcess", metaData.getProperty("lastProcess"));
/* 621 */         req.getRequestDispatcher(
/* 622 */           req.getContextPath() + "/jsp/benevolence/index.jsp")
/* 623 */           .forward(req, resp);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doPost(HttpServletRequest req, HttpServletResponse resp)
/*     */     throws ServletException, IOException
/*     */   {
/* 633 */     if (FFSession.needsRedirect(req, resp)) {
/* 634 */       return;
/*     */     }
/*     */ 
/* 637 */     String uri = req.getRequestURI();
/* 638 */     String[] split = uri.split("/benevolence/");
/* 639 */     String Action = null;
/* 640 */     if (split.length != 2)
/* 641 */       Action = "";
/*     */     else {
/* 643 */       Action = split[1];
/*     */     }
/* 645 */     if (Action.equals("FineStudent")) {
/* 646 */       String studentId = req.getParameter("studentId");
/* 647 */       String amount = req.getParameter("amount");
/* 648 */       String reason = req.getParameter("reason");
/* 649 */       int iAmount = 0;
/*     */       try {
/* 651 */         iAmount = Integer.parseInt(amount);
/*     */       } catch (NumberFormatException e) {
/* 653 */         req.setAttribute("err", "Amount must be an integer");
/* 654 */         req.getRequestDispatcher(
/* 655 */           req.getContextPath() + 
/* 656 */           "/jsp/benevolence/fine_error.jsp").forward(
/* 657 */           req, resp);
/* 658 */         return;
/*     */       }
/*     */ 
/* 661 */       if (iAmount < 1) {
/* 662 */         req.setAttribute("err", "Amount must be greater than 0.");
/* 663 */         req.getRequestDispatcher(
/* 664 */           req.getContextPath() + 
/* 665 */           "/jsp/benevolence/fine_error.jsp").forward(
/* 666 */           req, resp);
/* 667 */         return;
/*     */       }
/*     */ 
/* 670 */       Entity person = PersonDao.getSinglePerson(studentId);
/* 671 */       if (person == null) {
/* 672 */         req.setAttribute("err", "Invalid ID supplied");
/* 673 */         req.getRequestDispatcher(
/* 674 */           req.getContextPath() + 
/* 675 */           "/jsp/benevolence/fine_error.jsp").forward(
/* 676 */           req, resp);
/* 677 */         return;
/*     */       }
/*     */ 
/* 680 */       Deduction deduction = new Deduction();
/* 681 */       deduction.setDate(new Date());
/* 682 */       deduction.setAmount(iAmount);
/* 683 */       deduction.setReason(reason);
/* 684 */       deduction.saveAsEntity(person);
/* 685 */       req.setAttribute("amount", amount);
/* 686 */       req.setAttribute("reason", reason);
/* 687 */       req.setAttribute("person", person);
/* 688 */       req.getRequestDispatcher(
/* 689 */         req.getContextPath() + "/jsp/benevolence/fine_success.jsp")
/* 690 */         .forward(req, resp);
/* 691 */       return;
/* 692 */     }if (Action.equals("SetPseudoweek")) {
/* 693 */       String offset = req.getParameter("offset");
/* 694 */       String err = null;
/* 695 */       Integer iOffset = Integer.valueOf(0);
/*     */       try {
/* 697 */         iOffset = Integer.valueOf(Integer.parseInt(offset));
/*     */       } catch (NumberFormatException e) {
/* 699 */         err = e.getMessage();
/*     */       }
/*     */ 
/* 702 */       JSONObject json = new JSONObject();
/*     */ 
/* 704 */       if (err != null) {
/* 705 */         json.put("err", err);
/* 706 */         json.put("status", "FAIL");
/*     */       } else {
/* 708 */         String farmBase = (String)req.getSession().getAttribute(
/* 709 */           "farmBase");
/* 710 */         String hash = ApplicationTools.hashString(farmBase + 
/* 711 */           "reportDay");
/* 712 */         MemcacheService cache = 
/* 713 */           MemcacheServiceFactory.getMemcacheService();
/* 714 */         cache.delete(hash);
/* 715 */         Benevolence.setMetaData("weekOffset", iOffset.toString());
/* 716 */         json.put("status", "OK");
/*     */       }
/* 718 */       resp.setContentType("application/json");
/* 719 */       PrintWriter writer = resp.getWriter();
/* 720 */       writer.write(JSONValue.toJSONString(json));
/* 721 */       writer.close();
/* 722 */     } else if (Action.equals("EditFine")) {
/* 723 */       String personId = req.getParameter("personId");
/* 724 */       String fineId = req.getParameter("fineId");
/* 725 */       if (fineId == null) {
/* 726 */         resp.sendRedirect("/benevolence?action=EditFines");
/* 727 */         return;
/*     */       }
/* 729 */       if (personId == null) {
/* 730 */         resp.sendRedirect("/benevolence?action=EditFines");
/* 731 */         return;
/*     */       }
/* 733 */       Entity deduction = Deduction.getByIdAndParentId(
/* 734 */         Integer.parseInt(fineId, 10), 
/* 735 */         Integer.parseInt(personId, 10));
/* 736 */       Entity person = Deduction.getPersonByDeductionEntity(deduction);
/* 737 */       deduction.setProperty("amount", req.getParameter("amount"));
/* 738 */       deduction.setProperty("reason", req.getParameter("reason"));
/* 739 */       Util.persistEntity(deduction);
/* 740 */       req.setAttribute("fine", deduction);
/* 741 */       req.setAttribute("person", person);
/* 742 */       req.getRequestDispatcher(
/* 743 */         req.getContextPath() + 
/* 744 */         "/jsp/benevolence/edit_fine_success.jsp")
/* 745 */         .forward(req, resp);
/*     */     } else {
/* 747 */       resp.sendRedirect("/benevolence");
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\development\workspace\faithfarmoss\WEB-INF\classes\
 * Qualified Name:     org.faithfarm.BenevolenceServlet
 * JD-Core Version:    0.6.2
 */