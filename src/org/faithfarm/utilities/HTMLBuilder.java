/*     */ package org.faithfarm.utilities;
/*     */ 
/*     */ import com.google.appengine.api.datastore.DatastoreService;
/*     */ import com.google.appengine.api.datastore.DatastoreServiceFactory;
/*     */ import com.google.appengine.api.datastore.Entity;
/*     */ import com.google.appengine.api.datastore.FetchOptions;
/*     */ import com.google.appengine.api.datastore.FetchOptions.Builder;
/*     */ import com.google.appengine.api.datastore.PreparedQuery;
/*     */ import com.google.appengine.api.datastore.Query;
/*     */ import com.google.appengine.api.datastore.Query.FilterOperator;
/*     */ import java.io.Serializable;
/*     */ import java.text.DateFormat;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ 
/*     */ public class HTMLBuilder
/*     */   implements Serializable
/*     */ {
/*  29 */   private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
/*     */ 
/*     */   public boolean isCurrentDateBeforeEntryDatePlus3Weeks(String entryDate)
/*     */   {
/*  35 */     Date date = new Date();
/*     */ 
/*  37 */     DateFormat formatter = new SimpleDateFormat("dd-MMM-yy");
/*  38 */     Calendar cal1 = Calendar.getInstance();
/*  39 */     cal1.setTime(date);
/*     */ 
/*  42 */     date = new Date(entryDate);
/*  43 */     Calendar cal2 = Calendar.getInstance();
/*  44 */     cal2.setTime(date);
/*  45 */     cal2.add(4, 3);
/*     */ 
/*  49 */     return cal1.before(cal2);
/*     */   }
/*     */ 
/*     */   public String formatDate(String entryDate)
/*     */   {
/*  55 */     DateFormat formatter = new SimpleDateFormat("dd-MMM-yy");
/*     */ 
/*  57 */     Date date = new Date(entryDate);
/*  58 */     Calendar cal2 = Calendar.getInstance();
/*  59 */     cal2.setTime(date);
/*     */ 
/*  61 */     return formatter.format(cal2.getTime());
/*     */   }
/*     */ 
/*     */   public ArrayList buildVendorList() {
/*  65 */     Iterable results = null;
/*  66 */     Query q = new Query("Org");
/*  67 */     q.addFilter("orgType", Query.FilterOperator.EQUAL, "Vendor");
/*  68 */     q.addSort("name");
/*  69 */     PreparedQuery pq = datastore.prepare(q);
/*  70 */     results = pq.asIterable(FetchOptions.Builder.withLimit(50)
/*  71 */       .chunkSize(50));
/*     */ 
/*  73 */     ArrayList Results = ApplicationTools.convertIterableToList(results);
/*  74 */     ArrayList list = new ArrayList();
/*  75 */     if (list != null) {
/*  76 */       for (int i = 0; i < Results.size(); i++) {
/*  77 */         Entity entity = (Entity)Results.get(i);
/*  78 */         String value = entity.getProperty("name").toString();
/*  79 */         list.add(value);
/*     */       }
/*     */     }
/*     */ 
/*  83 */     return list;
/*     */   }
/*     */ 
/*     */   public ArrayList buildOrgList()
/*     */   {
/*  88 */     Iterable results = null;
/*  89 */     Query q = new Query("Org");
/*  90 */     q.addFilter("orgType", Query.FilterOperator.EQUAL, "Civic Organization");
/*  91 */     q.addSort("name");
/*  92 */     PreparedQuery pq = datastore.prepare(q);
/*  93 */     results = pq.asIterable(FetchOptions.Builder.withLimit(50)
/*  94 */       .chunkSize(50));
/*     */ 
/*  96 */     ArrayList Results = ApplicationTools.convertIterableToList(results);
/*  97 */     ArrayList list = new ArrayList();
/*  98 */     if (list != null) {
/*  99 */       for (int i = 0; i < Results.size(); i++) {
/* 100 */         Entity entity = (Entity)Results.get(i);
/* 101 */         String value = entity.getProperty("name").toString();
/* 102 */         list.add(value);
/*     */       }
/*     */     }
/*     */ 
/* 106 */     return list;
/*     */   }
/*     */ 
/*     */   public ArrayList buildMonths()
/*     */   {
/* 111 */     ArrayList list = new ArrayList();
/*     */ 
/* 113 */     list.add("January");
/* 114 */     list.add("February");
/* 115 */     list.add("March");
/* 116 */     list.add("April");
/* 117 */     list.add("May");
/* 118 */     list.add("June");
/* 119 */     list.add("July");
/* 120 */     list.add("August");
/* 121 */     list.add("September");
/* 122 */     list.add("October");
/* 123 */     list.add("November");
/* 124 */     list.add("December");
/*     */ 
/* 126 */     return list;
/*     */   }
/*     */ 
/*     */   public ArrayList buildSystemRoles()
/*     */   {
/* 131 */     ArrayList list = new ArrayList();
/*     */ 
/* 133 */     list.add("Administrator");
/* 134 */     list.add("Dispatch");
/* 135 */     list.add("DispatchAdmin");
/* 136 */     list.add("HelpDesk");
/* 137 */     list.add("Intake");
/* 138 */     list.add("InvAdmin");
/* 139 */     list.add("SalesClerk");
/*     */ 
/* 141 */     return list;
/*     */   }
/*     */ 
/*     */   public ArrayList buildDays() {
/* 145 */     ArrayList list = new ArrayList();
/*     */ 
/* 147 */     for (int i = 1; i < 32; i++) {
/* 148 */       list.add(i);
/*     */     }
/* 150 */     return list;
/*     */   }
/*     */ 
/*     */   public ArrayList buildPaymentMethods() {
/* 154 */     ArrayList list = new ArrayList();
/*     */ 
/* 156 */     list.add("Cash");
/* 157 */     list.add("Check/Money Order");
/* 158 */     list.add("Credit Card");
/*     */ 
/* 160 */     return list;
/*     */   }
/*     */ 
/*     */   public ArrayList buildItemStatusList()
/*     */   {
/* 165 */     ArrayList list = new ArrayList();
/* 166 */     list.add("Active");
/* 167 */     list.add("Discontinued");
/*     */ 
/* 169 */     return list;
/*     */   }
/*     */ 
/*     */   public ArrayList buildInventoryLocation()
/*     */   {
/* 174 */     ArrayList list = new ArrayList();
/*     */ 
/* 176 */     list.add("1/W");
/* 177 */     list.add("1/F");
/* 178 */     list.add("R/O");
/*     */ 
/* 180 */     return list;
/*     */   }
/*     */ 
/*     */   public ArrayList buildTruckList(String farm)
/*     */   {
/* 185 */     ArrayList list = new ArrayList();
/*     */ 
/* 187 */     if ("Fort Lauderdale".equals(farm)) {
/* 188 */       list.add("201");
/* 189 */       list.add("202");
/* 190 */       list.add("204");
/* 191 */       list.add("206");
/* 192 */       list.add("207");
/* 193 */       list.add("209");
/* 194 */       list.add("210");
/* 195 */       list.add("211");
/* 196 */       list.add("234");
/* 197 */       list.add("235");
/* 198 */       list.add("241");
/* 199 */     } else if (("Boynton Beach".equals(farm)) || ("Okeechobee".equals(farm))) {
/* 200 */       list.add("305");
/* 201 */       list.add("311");
/* 202 */       list.add("317");
/* 203 */       list.add("322");
/* 204 */       list.add("324");
/* 205 */       list.add("325");
/* 206 */       list.add("326");
/* 207 */       list.add("327");
/* 208 */       list.add("328");
/* 209 */       list.add("329");
/* 210 */       list.add("334");
/* 211 */       list.add("335");
/* 212 */       list.add("339");
/*     */     }
/*     */ 
/* 215 */     return list;
/*     */   }
/*     */ 
/*     */   public ArrayList buildHowDidYouHearAboutUsList() {
/* 219 */     ArrayList list = new ArrayList();
/*     */ 
/* 221 */     list.add("repeat donor/customer");
/* 222 */     list.add("trucks");
/* 223 */     list.add("news/magazine");
/* 224 */     list.add("radio/tv ad");
/* 225 */     list.add("friend/relative");
/* 226 */     list.add("internet");
/* 227 */     list.add("yellow pages");
/* 228 */     list.add("other");
/*     */ 
/* 230 */     return list;
/*     */   }
/*     */ 
/*     */   public ArrayList buildDispatchStatusList() {
/* 234 */     ArrayList list = new ArrayList();
/*     */ 
/* 236 */     list.add("Assigned");
/* 237 */     list.add("Cancelled by Donor");
/* 238 */     list.add("Completed");
/* 239 */     list.add("In Route");
/* 240 */     list.add("No Response");
/* 241 */     list.add("Pending");
/* 242 */     list.add("Reschedule");
/* 243 */     list.add("10/5");
/*     */ 
/* 245 */     return list;
/*     */   }
/*     */ 
/*     */   public ArrayList buildSalesStatusList()
/*     */   {
/* 250 */     ArrayList list = new ArrayList();
/*     */ 
/* 252 */     list.add("Cancelled");
/* 253 */     list.add("Paid (Cash)");
/* 254 */     list.add("Paid (Credit Card)");
/* 255 */     list.add("Payment Declined");
/* 256 */     list.add("Pending");
/*     */ 
/* 258 */     return list;
/*     */   }
/*     */ 
/*     */   public ArrayList buildDispatchTypeList()
/*     */   {
/* 263 */     ArrayList list = new ArrayList();
/*     */ 
/* 265 */     list.add("Donation");
/* 266 */     list.add("Delivery");
/*     */ 
/* 268 */     return list;
/*     */   }
/*     */ 
/*     */   public ArrayList buildStreetSuffixList()
/*     */   {
/* 274 */     ArrayList list = new ArrayList();
/*     */ 
/* 276 */     list.add("avenue");
/* 277 */     list.add("boulevard");
/* 278 */     list.add("circle");
/* 279 */     list.add("court");
/* 280 */     list.add("drive");
/* 281 */     list.add("highway");
/* 282 */     list.add("lane");
/* 283 */     list.add("manor");
/* 284 */     list.add("parkway");
/* 285 */     list.add("place");
/* 286 */     list.add("road");
/* 287 */     list.add("street");
/* 288 */     list.add("terrace");
/* 289 */     list.add("way");
/* 290 */     list.add("other");
/*     */ 
/* 292 */     return list;
/*     */   }
/*     */ 
/*     */   public ArrayList buildStructureTypeList() {
/* 296 */     ArrayList list = new ArrayList();
/* 297 */     list.add("apartment");
/* 298 */     list.add("business");
/* 299 */     list.add("condominium");
/* 300 */     list.add("home");
/* 301 */     list.add("townhome");
/* 302 */     list.add("assisted living facility");
/* 303 */     list.add("mobile home");
/* 304 */     list.add("");
/* 305 */     return list;
/*     */   }
/*     */ 
/*     */   public ArrayList buildItemLocationList() {
/* 309 */     ArrayList list = new ArrayList();
/*     */ 
/* 311 */     list.add("carport");
/* 312 */     list.add("outside");
/* 313 */     list.add("porch");
/*     */ 
/* 315 */     return list;
/*     */   }
/*     */ 
/*     */   public ArrayList buildCallRequirementsList() {
/* 319 */     ArrayList list = new ArrayList();
/* 320 */     list.add("tag(donation only)");
/* 321 */     list.add("half-hour call");
/* 322 */     list.add("one hour call");
/*     */ 
/* 324 */     return list;
/*     */   }
/*     */ 
/*     */   public ArrayList buildFloorsList() {
/* 328 */     ArrayList list = new ArrayList();
/* 329 */     for (int i = 1; i < 51; i++) {
/* 330 */       list.add(i);
/*     */     }
/* 332 */     return list;
/*     */   }
/*     */ 
/*     */   public ArrayList buildGateInstructionsList() {
/* 336 */     ArrayList list = new ArrayList();
/* 337 */     list.add("callbox code");
/* 338 */     list.add("ask security");
/* 339 */     list.add("buzz owner by last name");
/*     */ 
/* 341 */     return list;
/*     */   }
/*     */ 
/*     */   public ArrayList buildBagsBoxesList() {
/* 345 */     ArrayList list = new ArrayList();
/* 346 */     list.add("Bags");
/* 347 */     list.add("Boxes");
/*     */ 
/* 349 */     return list;
/*     */   }
/*     */ 
/*     */   public ArrayList buildEmployeeList(String farm) {
/* 353 */     ArrayList list = new ArrayList();
/*     */ 
/* 355 */     if ("Fort Lauderdale".equals(farm)) {
/* 356 */       list.add("Mike Auerbach");
/* 357 */       list.add("Rick Corring");
/* 358 */       list.add("Shawn Haworth");
/* 359 */       list.add("Larry Hill");
/* 360 */       list.add("Sammie Johnson");
/* 361 */       list.add("Bruce Hanson");
/* 362 */       list.add("Stu Lane");
/* 363 */       list.add("Dick Mogged");
/* 364 */       list.add("Joe Rossok");
/* 365 */       list.add("Richard Rosendo");
/* 366 */       list.add("Jeff Schleibaum");
/* 367 */       list.add("Omega");
/*     */     }
/*     */ 
/* 371 */     return list;
/*     */   }
/*     */ 
/*     */   public ArrayList buildJobList()
/*     */   {
/* 376 */     ArrayList list = new ArrayList();
/* 377 */     list.add("Administrative");
/* 378 */     list.add("Appliance Repair");
/* 379 */     list.add("As-Is");
/* 380 */     list.add("Dispatch");
/* 381 */     list.add("Housecrew");
/* 382 */     list.add("Kitchen");
/* 383 */     list.add("Maintenance");
/* 384 */     list.add("New Furniture");
/* 385 */     list.add("Other");
/*     */ 
/* 387 */     return list;
/*     */   }
/*     */ 
/*     */   public ArrayList buildActiveInactiveList()
/*     */   {
/* 392 */     ArrayList list = new ArrayList();
/* 393 */     list.add("Active");
/* 394 */     list.add("Inactive");
/* 395 */     return list;
/*     */   }
/*     */ 
/*     */   public ArrayList buildBranchOfServiceList()
/*     */   {
/* 400 */     ArrayList list = new ArrayList();
/* 401 */     list.add("Air Force");
/* 402 */     list.add("Army");
/* 403 */     list.add("Army National Guard");
/* 404 */     list.add("Coast Guard");
/* 405 */     list.add("Marines");
/* 406 */     list.add("Navy");
/* 407 */     return list;
/*     */   }
/*     */ 
/*     */   public ArrayList buildPersonStatusList()
/*     */   {
/* 412 */     ArrayList list = new ArrayList();
/* 413 */     list.add("Active");
/* 414 */     list.add("Dismissed");
/* 415 */     list.add("Graduated");
/* 416 */     list.add("Intern");
/* 417 */     list.add("Omega School");
/* 418 */     list.add("Omega Work");
/* 419 */     list.add("SLS");
/* 420 */     list.add("Walked Off");
/* 421 */     list.add("Denied");
/* 422 */     list.add("Pending");
/* 423 */     list.add("Left Voluntarily");
/*     */ 
/* 425 */     return list;
/*     */   }
/*     */ 
/*     */   public ArrayList buildEyewearList()
/*     */   {
/* 430 */     ArrayList list = new ArrayList();
/* 431 */     list.add("All the time");
/* 432 */     list.add("Occasionally");
/* 433 */     list.add("Reading");
/* 434 */     return list;
/*     */   }
/*     */ 
/*     */   public ArrayList buildGovernmentBenefitsList()
/*     */   {
/* 439 */     ArrayList list = new ArrayList();
/* 440 */     list.add("none");
/* 441 */     list.add("social security");
/* 442 */     list.add("va");
/* 443 */     list.add("unemployment");
/* 444 */     list.add("disability");
/* 445 */     list.add("food stamp assistance");
/* 446 */     list.add("worker's compensation");
/* 447 */     list.add("other");
/* 448 */     list.add("multiple");
/* 449 */     return list;
/*     */   }
/*     */ 
/*     */   public ArrayList buildUsagePatternList()
/*     */   {
/* 454 */     ArrayList list = new ArrayList();
/* 455 */     list.add("constantly");
/* 456 */     list.add("weekends");
/* 457 */     list.add("special occasions");
/* 458 */     list.add("when available");
/* 459 */     list.add("when things get tough");
/* 460 */     list.add("on a week/off a week");
/* 461 */     return list;
/*     */   }
/*     */ 
/*     */   public ArrayList buildLossesList()
/*     */   {
/* 466 */     ArrayList list = new ArrayList();
/* 467 */     list.add("DUIs");
/* 468 */     list.add("Arrests");
/* 469 */     list.add("Friends");
/* 470 */     list.add("Health");
/* 471 */     list.add("Heavy Debt");
/* 472 */     list.add("Incarceration");
/* 473 */     list.add("Jobs");
/* 474 */     list.add("Marriage");
/* 475 */     list.add("Possessions");
/*     */ 
/* 477 */     return list;
/*     */   }
/*     */ 
/*     */   public ArrayList buildPhysicalEffectsList()
/*     */   {
/* 482 */     ArrayList list = new ArrayList();
/* 483 */     list.add("Motivational Loss");
/* 484 */     list.add("Shakes/Convulsions");
/* 485 */     list.add("Memory Loss");
/* 486 */     list.add("Incoherent Thinking");
/* 487 */     list.add("Organ Problems");
/*     */ 
/* 489 */     return list;
/*     */   }
/*     */ 
/*     */   public ArrayList buildCurrentHealthList()
/*     */   {
/* 494 */     ArrayList list = new ArrayList();
/* 495 */     list.add("excellent");
/* 496 */     list.add("good");
/* 497 */     list.add("fair");
/* 498 */     list.add("failing");
/* 499 */     list.add("poor");
/*     */ 
/* 501 */     return list;
/*     */   }
/*     */ 
/*     */   public ArrayList buildClaimStatusList()
/*     */   {
/* 506 */     ArrayList list = new ArrayList();
/* 507 */     list.add("open");
/* 508 */     list.add("closed");
/*     */ 
/* 510 */     return list;
/*     */   }
/*     */ 
/*     */   public ArrayList buildReligionList()
/*     */   {
/* 515 */     ArrayList list = new ArrayList();
/* 516 */     list.add("Christian");
/* 517 */     list.add("Buddhist");
/* 518 */     list.add("Catholic");
/* 519 */     list.add("Hindu");
/* 520 */     list.add("Jewish");
/* 521 */     list.add("New Age");
/* 522 */     list.add("Protestant");
/* 523 */     list.add("Rastafarian");
/* 524 */     list.add("Santaria");
/* 525 */     list.add("Other");
/* 526 */     return list;
/*     */   }
/*     */ 
/*     */   public ArrayList buildNameSuffixList()
/*     */   {
/* 531 */     ArrayList list = new ArrayList();
/* 532 */     list.add("Jr.");
/* 533 */     list.add("Sr.");
/* 534 */     list.add("I");
/* 535 */     list.add("II");
/* 536 */     list.add("III");
/* 537 */     return list;
/*     */   }
/*     */ 
/*     */   public ArrayList buildWeightList()
/*     */   {
/* 542 */     ArrayList list = new ArrayList();
/* 543 */     for (int f = 100; f < 500; f++)
/* 544 */       list.add(f + "lbs");
/* 545 */     return list;
/*     */   }
/*     */ 
/*     */   public ArrayList buildAgeList()
/*     */   {
/* 550 */     ArrayList list = new ArrayList();
/* 551 */     for (int f = 18; f < 90; f++)
/* 552 */       list.add(Integer.valueOf(f));
/* 553 */     return list;
/*     */   }
/*     */ 
/*     */   public ArrayList buildHeightList()
/*     */   {
/* 558 */     ArrayList list = new ArrayList();
/* 559 */     for (int f = 5; f < 7; f++) {
/* 560 */       for (int i = 1; i < 13; i++)
/* 561 */         list.add(f + "'" + i);
/*     */     }
/* 563 */     return list;
/*     */   }
/*     */ 
/*     */   public ArrayList buildEthnicityList()
/*     */   {
/* 568 */     ArrayList locations = new ArrayList();
/* 569 */     locations.add("American Indian/Alaskan Native");
/* 570 */     locations.add("Asian/Pacific Islander");
/* 571 */     locations.add("Black, not Hispanic");
/* 572 */     locations.add("Hispanic");
/* 573 */     locations.add("White, not Hispanic");
/*     */ 
/* 575 */     return locations;
/*     */   }
/*     */ 
/*     */   public ArrayList buildMaritalStatusList()
/*     */   {
/* 580 */     ArrayList list = new ArrayList();
/* 581 */     list.add("Never Married");
/* 582 */     list.add("Married");
/* 583 */     list.add("Divorced");
/* 584 */     list.add("Separated");
/* 585 */     list.add("Spouse Deceased");
/* 586 */     list.add("Lives with Girlfriend");
/* 587 */     list.add("Lives with Boyfriend");
/* 588 */     list.add("Remarried");
/* 589 */     list.add("Re-divorced");
/*     */ 
/* 591 */     return list;
/*     */   }
/*     */ 
/*     */   public ArrayList buildHomeLocationList()
/*     */   {
/* 596 */     ArrayList list = new ArrayList();
/* 597 */     list.add("Own Home");
/* 598 */     list.add("Parent's Home");
/* 599 */     list.add("Friend's Home");
/* 600 */     list.add("Halfway Home");
/* 601 */     list.add("Hotel");
/* 602 */     list.add("Rehab Program");
/* 603 */     list.add("Vehicle");
/* 604 */     list.add("On Street");
/* 605 */     list.add("Other");
/*     */ 
/* 607 */     return list;
/*     */   }
/*     */ 
/*     */   public ArrayList buildFarmList()
/*     */   {
/* 612 */     ArrayList list = new ArrayList();
/* 613 */     list.add("Fort Lauderdale");
/* 614 */     list.add("Boynton Beach");
/* 615 */     list.add("Okeechobee");
/*     */ 
/* 617 */     return list;
/*     */   }
/*     */ 
/*     */   public ArrayList buildEducationList()
/*     */   {
/* 622 */     ArrayList list = new ArrayList();
/* 623 */     list.add("Elementary");
/* 624 */     list.add("High School");
/* 625 */     list.add("GED");
/* 626 */     list.add("Bachelor's Degree");
/* 627 */     list.add("Master's Degree");
/* 628 */     list.add("Doctorate Degree");
/* 629 */     list.add("Associates Degree");
/* 630 */     list.add("Vocational School");
/*     */ 
/* 632 */     return list;
/*     */   }
/*     */ 
/*     */   public ArrayList buildYesNoList()
/*     */   {
/* 637 */     ArrayList list = new ArrayList();
/* 638 */     list.add("Yes");
/* 639 */     list.add("No");
/*     */ 
/* 641 */     return list;
/*     */   }
/*     */ 
/*     */   public ArrayList buildPersonTypeList()
/*     */   {
/* 646 */     ArrayList list = new ArrayList();
/* 647 */     list.add("Customer");
/* 648 */     list.add("Donor");
/* 649 */     list.add("Employee");
/* 650 */     list.add("Graduate");
/* 651 */     list.add("Intake");
/* 652 */     list.add("Staff");
/* 653 */     list.add("Resident");
/*     */ 
/* 655 */     return list;
/*     */   }
/*     */ 
/*     */   public ArrayList buildHaircolorList()
/*     */   {
/* 660 */     ArrayList locations = new ArrayList();
/* 661 */     locations.add("Brown");
/* 662 */     locations.add("Black");
/* 663 */     locations.add("Blonde");
/* 664 */     locations.add("Blonde/Strawberry");
/* 665 */     locations.add("Gray or Partially Gray");
/* 666 */     locations.add("Partly or Completely Bald");
/* 667 */     locations.add("Red/Auburn");
/* 668 */     locations.add("Sandy");
/* 669 */     locations.add("Salt & Pepper");
/* 670 */     locations.add("Shaved");
/* 671 */     locations.add("White");
/* 672 */     return locations;
/*     */   }
/*     */ 
/*     */   public ArrayList buildEyecolorList()
/*     */   {
/* 677 */     ArrayList locations = new ArrayList();
/* 678 */     locations.add("Blue");
/* 679 */     locations.add("Brown");
/* 680 */     locations.add("Green");
/* 681 */     locations.add("Hazel");
/* 682 */     locations.add("Other");
/* 683 */     return locations;
/*     */   }
/*     */ 
/*     */   public ArrayList buildLocationList()
/*     */   {
/* 688 */     ArrayList locations = new ArrayList();
/* 689 */     locations.add("New Furniture");
/* 690 */     locations.add("As-Is");
/* 691 */     locations.add("Kitchen");
/* 692 */     locations.add("Canteen");
/* 693 */     return locations;
/*     */   }
/*     */ 
/*     */   public ArrayList buildTransactionList()
/*     */   {
/* 698 */     ArrayList transactions = new ArrayList();
/* 699 */     transactions.add("Receipt");
/* 700 */     transactions.add("Sale");
/* 701 */     transactions.add("Stock Sale");
/* 702 */     transactions.add("No Sale");
/* 703 */     return transactions;
/*     */   }
/*     */ 
/*     */   public ArrayList buildStateList()
/*     */   {
/* 708 */     ArrayList states = new ArrayList();
/* 709 */     states.add("Alabama");
/* 710 */     states.add("Alaska");
/* 711 */     states.add("Arizona");
/* 712 */     states.add("Arkansas");
/* 713 */     states.add("California");
/* 714 */     states.add("Colorado");
/* 715 */     states.add("Connecticut");
/* 716 */     states.add("Delaware");
/* 717 */     states.add("Dist of Columbia");
/* 718 */     states.add("Florida");
/* 719 */     states.add("Georgia");
/* 720 */     states.add("Hawaii");
/* 721 */     states.add("Idaho");
/* 722 */     states.add("Illinois");
/* 723 */     states.add("Indiana");
/* 724 */     states.add("Iowa");
/* 725 */     states.add("Kansas");
/* 726 */     states.add("Kentucky");
/* 727 */     states.add("Louisiana");
/* 728 */     states.add("Maine");
/* 729 */     states.add("Maryland");
/* 730 */     states.add("Massachusetts");
/* 731 */     states.add("Michigan");
/* 732 */     states.add("Minnesota");
/* 733 */     states.add("Mississippi");
/* 734 */     states.add("Missouri");
/* 735 */     states.add("Montana");
/* 736 */     states.add("Nebraska");
/* 737 */     states.add("Nevada");
/* 738 */     states.add("New Hampshire");
/* 739 */     states.add("New Jersey");
/* 740 */     states.add("New Mexico");
/* 741 */     states.add("New York");
/* 742 */     states.add("North Carolina");
/* 743 */     states.add("North Dakota");
/* 744 */     states.add("Ohio");
/* 745 */     states.add("Oklahoma");
/* 746 */     states.add("Oregon");
/* 747 */     states.add("Pennsylvania");
/* 748 */     states.add("Rhode Island");
/* 749 */     states.add("South Carolina");
/* 750 */     states.add("South Dakota");
/* 751 */     states.add("Tennessee");
/* 752 */     states.add("Texas");
/* 753 */     states.add("Utah");
/* 754 */     states.add("Vermont");
/* 755 */     states.add("Virginia");
/* 756 */     states.add("Washington");
/* 757 */     states.add("West Virginia");
/* 758 */     states.add("Wisconsin");
/* 759 */     states.add("Wyoming");
/* 760 */     return states;
/*     */   }
/*     */ 
/*     */   public ArrayList buildItemTypes()
/*     */   {
/* 765 */     ArrayList list = new ArrayList();
/*     */ 
/* 767 */     list.add("Accessories/Bath");
/* 768 */     list.add("Accessories/Bedding");
/* 769 */     list.add("Accessories/Clothing");
/* 770 */     list.add("Accessories/Computer");
/* 771 */     list.add("Accessories/Decorative");
/* 772 */     list.add("Accessories/Electronics");
/* 773 */     list.add("Accessories/Jewelry");
/* 774 */     list.add("Accessories/Kitchen");
/* 775 */     list.add("Accessories/Pillows");
/* 776 */     list.add("Accessories/Throws");
/* 777 */     list.add("Armoire");
/* 778 */     list.add("Artwork/Paintings");
/* 779 */     list.add("Bakers Rack");
/* 780 */     list.add("Barstool");
/* 781 */     list.add("Bench");
/* 782 */     list.add("Bookcase");
/* 783 */     list.add("Boxspring/Full");
/* 784 */     list.add("Boxspring/King");
/* 785 */     list.add("Boxspring/Queen");
/* 786 */     list.add("Boxspring/Twin");
/* 787 */     list.add("Bunk Bed");
/* 788 */     list.add("Cart");
/* 789 */     list.add("Chair");
/* 790 */     list.add("Chest");
/* 791 */     list.add("China Cabinet");
/* 792 */     list.add("Clothing");
/* 793 */     list.add("Computer Equipment");
/* 794 */     list.add("Credenza");
/* 795 */     list.add("Desk");
/* 796 */     list.add("Divider");
/* 797 */     list.add("Dresser");
/* 798 */     list.add("Dryer");
/* 799 */     list.add("End Table");
/* 800 */     list.add("Entertainment Center");
/* 801 */     list.add("Flowers/Arrangements");
/* 802 */     list.add("Footboard");
/* 803 */     list.add("Fireplace");
/* 804 */     list.add("Frames");
/* 805 */     list.add("Futon");
/* 806 */     list.add("Headboard");
/* 807 */     list.add("Hutch");
/* 808 */     list.add("Lamp/Lighting");
/* 809 */     list.add("Mattress/Full");
/* 810 */     list.add("Mattress/King");
/* 811 */     list.add("Mattress/Queen");
/* 812 */     list.add("Mattress/Twin");
/* 813 */     list.add("Mirror");
/* 814 */     list.add("Miscellaneous/Other");
/* 815 */     list.add("Nightstand");
/* 816 */     list.add("Office/Desk");
/* 817 */     list.add("Office/Chair");
/* 818 */     list.add("Ottoman");
/* 819 */     list.add("Paintings/Artwork");
/* 820 */     list.add("Plant Stand");
/* 821 */     list.add("Radio/Stereo");
/* 822 */     list.add("Rails");
/* 823 */     list.add("Recliner");
/* 824 */     list.add("Rug");
/* 825 */     list.add("Server");
/* 826 */     list.add("Sofa/Couch");
/* 827 */     list.add("Sofa/Flip-Flop");
/* 828 */     list.add("Sofa/Loveseat");
/* 829 */     list.add("Sofa/Sectional");
/* 830 */     list.add("Sofa/Sleeper");
/* 831 */     list.add("Storage/Bedroom");
/* 832 */     list.add("Storage/Cubes");
/* 833 */     list.add("Storage/Dining Room");
/* 834 */     list.add("Storage/Living Room");
/* 835 */     list.add("Storage/Media");
/* 836 */     list.add("Storage/Office");
/* 837 */     list.add("Table");
/* 838 */     list.add("Television");
/* 839 */     list.add("TV Stand");
/* 840 */     list.add("VCR/DVD");
/* 841 */     list.add("Washer");
/*     */ 
/* 843 */     return list;
/*     */   }
/*     */ 
/*     */   public ArrayList buildTicketStatus() {
/* 847 */     ArrayList list = new ArrayList();
/*     */ 
/* 849 */     list.add("Pending");
/* 850 */     list.add("Assigned");
/* 851 */     list.add("Resolved");
/* 852 */     list.add("Dismissed");
/* 853 */     return list;
/*     */   }
/*     */ 
/*     */   public ArrayList buildTicketPriority() {
/* 857 */     ArrayList list = new ArrayList();
/*     */ 
/* 859 */     list.add("Low");
/* 860 */     list.add("Medium");
/* 861 */     list.add("High");
/* 862 */     list.add("Mission Critical");
/* 863 */     return list;
/*     */   }
/*     */ 
/*     */   public ArrayList buildOS() {
/* 867 */     ArrayList list = new ArrayList();
/*     */ 
/* 869 */     list.add("Windows NT/2000/XP/Me/7");
/* 870 */     list.add("MacOS");
/* 871 */     list.add("Linux");
/* 872 */     list.add("Other");
/* 873 */     list.add("Not Applicable");
/* 874 */     return list;
/*     */   }
/*     */ 
/*     */   public ArrayList buildTicketType() {
/* 878 */     ArrayList list = new ArrayList();
/*     */ 
/* 880 */     list.add("Software/Hardware/Equipment Failure");
/* 881 */     list.add("Service Failure");
/* 882 */     list.add("Equipment/Computer/Hardware/Software Request");
/* 883 */     list.add("System/Network Access Request");
/*     */ 
/* 885 */     return list;
/*     */   }
/*     */   public String cleanData(String value) {
/* 888 */     if (value == null) {
/* 889 */       return "";
/*     */     }
/* 891 */     return value;
/*     */   }
/*     */ }

/* Location:           C:\development\workspace\faithfarmoss\WEB-INF\classes\
 * Qualified Name:     org.faithfarm.utilities.HTMLBuilder
 * JD-Core Version:    0.6.2
 */