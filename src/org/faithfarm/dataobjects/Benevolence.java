/*     */ package org.faithfarm.dataobjects;
/*     */ 
/*     */ /*     */ import java.io.Serializable;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Arrays;
/*     */ import java.util.Calendar;
/*     */ import java.util.Comparator;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.TreeMap;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;

/*     */ import org.faithfarm.Util;
/*     */ import org.faithfarm.datawriters.PersonDao;
/*     */ import org.faithfarm.utilities.ApplicationTools;

import com.google.appengine.api.datastore.DatastoreService;
/*     */ import com.google.appengine.api.datastore.DatastoreServiceFactory;
/*     */ import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
/*     */ import com.google.appengine.api.datastore.PropertyProjection;
/*     */ import com.google.appengine.api.datastore.Query;
/*     */ import com.google.appengine.api.memcache.Expiration;
/*     */ import com.google.appengine.api.memcache.MemcacheService;
/*     */ import com.google.appengine.api.memcache.MemcacheServiceFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Benevolence
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 237436402630063177L;
/*     */   private int ytdPaid;
/*     */   private int saved;
/*     */   private int amountDue;
/*     */   private static String farmBase;
/*  47 */   private static final Logger logger = Logger.getLogger(Benevolence.class.getCanonicalName());
/*  48 */   private static final DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
/*  49 */   private static final MemcacheService cache = MemcacheServiceFactory.getMemcacheService();
/*     */ 
/*     */   public Benevolence()
/*     */   {
/*  56 */     this.ytdPaid = 0;
/*  57 */     this.saved = 0;
/*  58 */     this.amountDue = 0;
/*     */   }
/*     */ 
/*     */   public Benevolence(Integer y, Integer s) {
/*  62 */     this.ytdPaid = y.intValue();
/*  63 */     this.saved = s.intValue();
/*  64 */     this.amountDue = 0;
/*     */   }
/*     */ 
/*     */   public static Boolean processWeek() {
/*  68 */     Boolean result = Boolean.valueOf(false);
/*  69 */     Iterable active = getResidents("Active", farmBase);
/*  70 */     Iterable omega = getResidents("Omega School", farmBase);
/*  71 */     Iterable sls = getResidents("SLS", farmBase);
/*  72 */     Date reportDay = getReportDay().getTime();
/*  73 */     SimpleDateFormat toFormat = new SimpleDateFormat("MM/dd/yy");
/*  74 */     String lastProcessStr = toFormat.format(reportDay);
/*     */     try
/*     */     {
/*  78 */       //for (Entity resident : active) {
				for(Iterator i = active.iterator();i.hasNext();) {
				  Entity resident = (Entity)i.next();

/*  79 */         String amtPaid = getAmountDueFromEntity(resident);
/*  80 */         Number savings = Integer.valueOf(getWeeklySavingsFromEntity(resident));
/*  81 */         Number iAmtPaid = Integer.valueOf(0);
/*  82 */         if (resident.getProperty("ytdPaid") == null) {
/*  83 */           resident.setProperty("ytdPaid", Integer.valueOf(0));
/*     */         }
/*  85 */         if (resident.getProperty("saved") == null)
/*  86 */           resident.setProperty("saved", Integer.valueOf(0));
/*     */         try
/*     */         {
/*  89 */           iAmtPaid = Integer.valueOf(Integer.parseInt(amtPaid));
/*     */         } catch (NumberFormatException e) {
/*  91 */           logger.log(Level.SEVERE, e.getMessage());
/*     */         }
/*  93 */         iAmtPaid = Integer.valueOf(iAmtPaid.intValue() - Deduction.getTotalDeductions(resident));
/*  94 */         Number previousYtd = (Number)resident.getProperty("ytdPaid");
/*  95 */         resident.setProperty("ytdPaid", Integer.valueOf(previousYtd.intValue() + iAmtPaid.intValue()));
/*  96 */         if (savings.intValue() > 0) {
/*  97 */           Number previousSavings = (Number)resident.getProperty("saved");
/*  98 */           resident.setProperty("saved", Integer.valueOf(previousSavings.intValue() + savings.intValue()));
/*     */         }
/* 100 */         datastore.put(resident);
/*     */       }
/*     */ 
/* 103 */       //for (Entity resident : omega) {
			    for(Iterator i = omega.iterator();i.hasNext();) {
			      Entity resident = (Entity)i.next();

/* 104 */         if (resident.getProperty("ytdPaid") == null) {
/* 105 */           resident.setProperty("ytdPaid", Integer.valueOf(0));
/*     */         }
/* 107 */         Number iAmtPaid = Integer.valueOf(25 - Deduction.getTotalDeductions(resident));
/* 108 */         Number previousYtd = (Number)resident.getProperty("ytdPaid");
/* 109 */         resident.setProperty("ytdPaid", Integer.valueOf(previousYtd.intValue() + iAmtPaid.intValue()));
/* 110 */         datastore.put(resident);
/*     */       }
/*     */ 
/* 113 */       //for (Entity resident : sls) {
			    for(Iterator i = sls.iterator();i.hasNext();) {
			      Entity resident = (Entity)i.next();

/* 114 */         if (resident.getProperty("ytdPaid") == null) {
/* 115 */           resident.setProperty("ytdPaid", Integer.valueOf(0));
/*     */         }
/* 117 */         Number iAmtPaid = Integer.valueOf(getAmountDueSLSFromEntity(resident).intValue() - Deduction.getTotalDeductions(resident));
/* 118 */         Number previousYtd = (Number)resident.getProperty("ytdPaid");
/* 119 */         resident.setProperty("ytdPaid", Integer.valueOf(previousYtd.intValue() + iAmtPaid.intValue()));
/* 120 */         datastore.put(resident);
/*     */       }
/*     */     }
/*     */     finally {
/* 124 */       setMetaData("lastProcess", lastProcessStr);
/* 125 */       result = Boolean.valueOf(true);
/*     */     }
/*     */ 
/* 128 */     return result;
/*     */   }
/*     */ 
/*     */   public static void setFarmBase(String farm) {
/* 132 */     farmBase = farm;
/*     */   }
/*     */ 
/*     */   public static void setMetaData(String key, String value) {
/* 136 */     Entity metaData = getMetaData();
/* 137 */     metaData.setProperty(key, value);
/* 138 */     Util.persistEntity(metaData);
/*     */   }
/*     */ 
/*     */   public static Entity getMetaData() {
/* 142 */     Query query = new Query("BenevolenceMetaData");
/* 143 */     query.addFilter("farmBase", Query.FilterOperator.EQUAL, farmBase);
/* 144 */     List metaDataList = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(1));
/* 145 */     if (metaDataList.size() == 0) {
/* 146 */       Entity metaData = new Entity("BenevolenceMetaData");
/* 147 */       metaData.setProperty("weekOffset", Integer.valueOf(0));
/* 148 */       metaData.setProperty("lastProcess", "");
/* 149 */       metaData.setProperty("farmBase", farmBase);
/* 150 */       Util.persistEntity(metaData);
/* 151 */       return metaData;
/*     */     }
/*     */ 
/* 154 */     Entity metaData = (Entity)metaDataList.get(0);
/* 155 */     return metaData;
/*     */   }
/*     */ 
/*     */   public static HashMap<String, Integer> getGrandTotal(String farmBase)
/*     */   {
/* 166 */     HashMap grandTotal = new HashMap();
/* 167 */     Query query = new Query("Person");
/* 168 */     query.addProjection(new PropertyProjection("personStatus", String.class));
/* 169 */     query.addProjection(new PropertyProjection("entryDate", String.class));
/*     */ 
/* 171 */     query.addFilter("personType", Query.FilterOperator.EQUAL, "Resident");
/* 172 */     List theList = datastore.prepare(query).asList(FetchOptions.Builder.withChunkSize(200));
/*     */ 
/* 174 */     Integer previousPaidYtd = Integer.valueOf(0);
/* 175 */     Integer previousSavings = Integer.valueOf(0);
/* 176 */     Integer amountDue = Integer.valueOf(0);
/* 177 */     Integer deductions = Integer.valueOf(0);
/* 178 */     Integer weeklySavings = Integer.valueOf(0);
/*     */     Entity localEntity;
/* 180 */     for (Iterator localIterator = theList.iterator(); localIterator.hasNext(); localEntity = (Entity)localIterator.next());
/* 182 */     grandTotal.put("previousPaidYtd", previousPaidYtd);
/* 183 */     grandTotal.put("previousSavings", previousSavings);
/* 184 */     grandTotal.put("amountDue", amountDue);
/* 185 */     grandTotal.put("deductions", deductions);
/* 186 */     grandTotal.put("amountPaid", Integer.valueOf(amountDue.intValue() - deductions.intValue()));
/* 187 */     grandTotal.put("weeklySavings", weeklySavings);
/* 188 */     grandTotal.put("amountPaidYtd", Integer.valueOf(previousPaidYtd.intValue() + (amountDue.intValue() - deductions.intValue())));
/* 189 */     grandTotal.put("totalSavings", Integer.valueOf(previousSavings.intValue() + weeklySavings.intValue()));
/* 190 */     return grandTotal;
/*     */   }
/*     */ 
/*     */   public static String formatSSN(Entity person)
/*     */   {
/* 200 */     String ssn = (String)person.getProperty("ssn");
/* 201 */     if ((ssn == null) || (ssn.length() < 9)) {
/* 202 */       return "Unknown/Invalid";
/*     */     }
/* 204 */     String lastFour = ssn.substring(5);
/* 205 */     return "xxx-xx-".concat(lastFour);
/*     */   }
/*     */ 
/*     */   public static Iterable<Entity> getResidents(String personStatus, String farm)
/*     */   {
/* 216 */     Iterable residents = PersonDao.getAllPersonsCached(null, null, null, "Resident", personStatus, null, null, farm);
/* 217 */     return residents;
/*     */   }
/*     */ 
/*     */   public static Calendar getReportDay()
/*     */   {
/* 227 */     String hashStr = farmBase + "reportDay";
/* 228 */     String hash = ApplicationTools.hashString(hashStr);
/* 229 */     Calendar reportDay = null;
/* 230 */     reportDay = (Calendar)cache.get(hash);
/*     */ 
/* 232 */     if (reportDay == null) {
/* 233 */       Entity benevolenceMetaData = getMetaData();
/* 234 */       reportDay = Calendar.getInstance();
/* 235 */       reportDay.set(reportDay.get(1), reportDay.get(2), reportDay.get(5), 0, 0, 0);
/* 236 */       reportDay.set(14, 0);
/* 237 */       if (reportDay.get(7) >= 6) {
/* 238 */         reportDay.set(7, 6);
/* 239 */         reportDay.add(3, 1);
/*     */       }
/*     */       else {
/* 242 */         reportDay.set(7, 6);
/*     */       }
/* 244 */       Number iOffset = Integer.valueOf(0);
/*     */       try {
/* 246 */         String offset = (String)benevolenceMetaData.getProperty("weekOffset");
/*     */         try {
/* 248 */           iOffset = Integer.valueOf(Integer.parseInt(offset, 10));
/*     */         } catch (NumberFormatException e) {
/* 250 */           iOffset = Integer.valueOf(0);
/*     */         }
/*     */       } catch (ClassCastException e) {
/*     */         try {
/* 254 */           iOffset = (Integer)benevolenceMetaData.getProperty("weekOffset");
/*     */         } catch (ClassCastException e2) {
/* 256 */           iOffset = (Long)benevolenceMetaData.getProperty("weekOffset");
/*     */         }
/*     */       }
/* 259 */       if (iOffset.intValue() > 0) {
/* 260 */         reportDay.add(3, iOffset.intValue());
/*     */       }
/* 262 */       cache.put(hash, reportDay, Expiration.byDeltaSeconds(60));
/*     */     }
/* 264 */     return reportDay;
/*     */   }
/*     */ 
/*     */   public static String getChangeOrderDeliveryDate()
/*     */   {
/* 273 */     SimpleDateFormat toFormat = new SimpleDateFormat("MM/dd/yy");
/* 274 */     Calendar changeOrderDay = (Calendar)getReportDay().clone();
/* 275 */     changeOrderDay.set(7, 3);
/* 276 */     return toFormat.format(changeOrderDay.getTime());
/*     */   }
/*     */ 
/*     */   public static boolean isNewPayScheme(Entity person)
/*     */   {
/* 287 */     Calendar theFirst = Calendar.getInstance();
/* 288 */     theFirst.set(2012, 0, 1, 0, 0, 0);
/*     */ 
/* 290 */     Date entryDate = Util.parseDate((String)person.getProperty("entryDate"));
/* 291 */     if (entryDate.after(theFirst.getTime())) {
/* 292 */       return true;
/*     */     }
/* 294 */     return false;
/*     */   }
/*     */ 
/*     */   public static int getWeeklySavingsFromEntity(Entity person)
/*     */   {
/* 306 */     int weeks = 0;
/*     */ 
/* 308 */     if (!person.getProperty("personStatus").equals("Active"))
/* 309 */       return 0;
/*     */     try
/*     */     {
/* 312 */       weeks = Integer.parseInt(getWeeksFromEntity(person), 10);
/*     */     } catch (NumberFormatException e) {
/* 314 */       logger.log(Level.SEVERE, e.getMessage());
/*     */     }
/*     */ 
/* 317 */     if ((isNewPayScheme(person)) && (weeks > 12)) {
/* 318 */       return 7;
/*     */     }
/* 320 */     return 0;
/*     */   }
/*     */ 
/*     */   public static Integer getAmountDueSLSFromEntity(Entity person)
/*     */   {
/* 330 */     Calendar thisFriday = getReportDay();
/* 331 */     SimpleDateFormat fromFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
/* 332 */     Date entryDate = new Date();
/*     */     try {
/* 334 */       entryDate = fromFormat.parse((String)person.getProperty("lastStatusUpdated"));
/*     */     } catch (ParseException e) {
/* 336 */       logger.log(Level.SEVERE, e.getMessage());
/*     */     } catch (NullPointerException ne) {
/*     */       try {
/* 339 */         entryDate = fromFormat.parse((String)person.getProperty("entryDate"));
/*     */       } catch (ParseException pe) {
/* 341 */         logger.log(Level.SEVERE, pe.getMessage());
/*     */       }
/*     */     }
/* 344 */     int daysBetween = (int)((thisFriday.getTime().getTime() - entryDate.getTime()) / 86400000L);
/* 345 */     int periods = (int)Math.floor(daysBetween / 90);
/* 346 */     return Integer.valueOf(periods * 10 + 25 > 55 ? 55 : periods * 10 + 25);
/*     */   }
/*     */ 
/*     */   public static String getAmountDueFromEntity(Entity entity)
/*     */   {
/* 357 */     String theirStatus = (String)entity.getProperty("personStatus");
/* 358 */     if (!theirStatus.equals("Active")) {
/* 359 */       return "0";
/*     */     }
/* 361 */     String exitDateStr = (String)entity.getProperty("exitDate");
/* 362 */     if ((exitDateStr == null) || (exitDateStr.equals(""))) {
/* 363 */       if (isNewPayScheme(entity)) {
/* 364 */         return "7";
/*     */       }
/*     */ 
/* 367 */       return "20";
/*     */     }
/*     */ 
/* 371 */     return "0";
/*     */   }
/*     */ 
/*     */   public static String getWeeksFromEntity(Entity entity)
/*     */   {
/* 383 */     Integer weeks = Integer.valueOf(0);
/* 384 */     Date entryDate = new Date();
/* 385 */     Date exitDate = new Date();
/* 386 */     Calendar entry = Calendar.getInstance();
/* 387 */     Calendar compareDay = null;
/* 388 */     entryDate = Util.parseDate((String)entity.getProperty("entryDate"));
/*     */ 
/* 390 */     String exitDateStr = (String)entity.getProperty("exitDate");
/* 391 */     if ((exitDateStr == null) || (exitDateStr.equals(""))) {
/* 392 */       compareDay = getReportDay();
/*     */     } else {
/* 394 */       compareDay = Calendar.getInstance();
/* 395 */       compareDay.setTime(Util.parseDate(exitDateStr));
/*     */     }
/* 397 */     entry.setTime(entryDate);
/* 398 */     entry.set(entry.get(1), entry.get(2), entry.get(5), 0, 0, 0);
/* 399 */     entry.set(14, 0);
/* 400 */     for (weeks = Integer.valueOf(1); ; weeks = Integer.valueOf(weeks.intValue() + 1)) {
/* 401 */       entry.add(3, 1);
/* 402 */       if (entry.after(compareDay))
/* 403 */         return weeks.toString();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static String getNameFromEntity(Entity entity)
/*     */   {
/* 414 */     String lastName = (String)entity.getProperty("lastName");
/* 415 */     String firstName = (String)entity.getProperty("firstName");
/* 416 */     lastName = lastName.toUpperCase().substring(0, 1).concat(lastName.toLowerCase().substring(1));
/* 417 */     firstName = firstName.toUpperCase().substring(0, 1).concat(firstName.toLowerCase().substring(1));
/* 418 */     return lastName.concat(", ").concat(firstName);
/*     */   }
/*     */ 
/*     */   public static String getEntryDateFromEntity(Entity entity)
/*     */   {
/* 429 */     SimpleDateFormat toFormat = new SimpleDateFormat("MM/dd/yy");
/* 430 */     Date theDate = Util.parseDate((String)entity.getProperty("entryDate"));
/*     */ 
/* 432 */     return toFormat.format(theDate);
/*     */   }
/*     */ 
/*     */   public static Map<Entity, Benevolence> sortBenevolenceData(Map<Entity, Benevolence> theMap) {
/* 436 */     Map theSevens = new TreeMap(new Comparator() {
/*     */       public int compare(Entity a, Entity b) {
/* 438 */         int lastCompare = ((String)a.getProperty("lastName")).compareTo((String)b.getProperty("lastName"));
/* 439 */         if (lastCompare == 0) {
/* 440 */           return ((String)a.getProperty("firstName")).compareTo((String)b.getProperty("firstName"));
/*     */         }
/* 442 */         return lastCompare;
/*     */       }
/*     */

@Override
public int compare(Object arg0, Object arg1) {
	// TODO Auto-generated method stub
	return 0;
}     });
/* 445 */     Map theOthers = new TreeMap(new Comparator() {
/*     */       public int compare(Entity a, Entity b) {
/* 447 */         int lastCompare = ((String)a.getProperty("lastName")).compareTo((String)b.getProperty("lastName"));
/* 448 */         if (lastCompare == 0) {
/* 449 */           return ((String)a.getProperty("firstName")).compareTo((String)b.getProperty("firstName"));
/*     */         }
/* 451 */         return lastCompare;
/*     */       }
/*     */

@Override
public int compare(Object o1, Object o2) {
	// TODO Auto-generated method stub
	return 0;
}     });
/* 454 */     Map allSorted = new LinkedHashMap();
/* 455 */     Entity entity = null;
/* 456 */     Benevolence benevolence = null;
/* 457 */     for (Map.Entry entry : theMap.entrySet()) {
/* 458 */       entity = (Entity)entry.getKey();
/* 459 */       benevolence = (Benevolence)entry.getValue();
/* 460 */       Integer amt = Integer.valueOf(Integer.parseInt(getAmountDueFromEntity(entity), 10));
/* 461 */       benevolence.setAmountDue(amt.intValue());
/* 462 */       if (amt.intValue() == 7) {
/* 463 */         theSevens.put(entity, (Benevolence)entry.getValue());
/*     */       }
/*     */       else {
/* 466 */         theOthers.put(entity, (Benevolence)entry.getValue());
/*     */       }
/*     */     }
/* 469 */     allSorted.putAll(theSevens);
/* 470 */     allSorted.putAll(theOthers);
/* 471 */     return allSorted;
/*     */   }
/*     */ 
/*     */   public static Map<Entity, Benevolence> getBenevolenceData(String personStatus) {
/* 475 */     Calendar theFirst = Calendar.getInstance();
/* 476 */     theFirst.set(theFirst.get(1), 0, 1, 0, 0, 0);
/* 477 */     theFirst.set(14, 0);
/* 478 */     List paidStatuses = Arrays.asList(new String[] { "Active", "SLS", "Omega School" });
/* 479 */     Map theMap = new TreeMap(new Comparator()
/*     */     {
/*     */       public int compare(Entity a, Entity b) {
/* 482 */         String alastName = a.getProperty("lastName").toString().toLowerCase();
/* 483 */         String blastName = b.getProperty("lastName").toString().toLowerCase();
/* 484 */         String afirstName = a.getProperty("firstName").toString().toLowerCase();
/* 485 */         String bfirstName = b.getProperty("firstName").toString().toLowerCase();
/*     */ 
/* 487 */         int lastCompare = alastName.compareTo(blastName);
/* 488 */         if (lastCompare == 0) {
/* 489 */           return afirstName.compareTo(bfirstName);
/*     */         }
/* 491 */         return lastCompare;
/*     */       }
/*     */

@Override
public int compare(Object o1, Object o2) {
	// TODO Auto-generated method stub
	return 0;
}     });
/* 495 */     Iterable persons = PersonDao.getAllPersonsCached(null, null, null, "Resident", null, null, null, farmBase);
/* 496 */     //for (Entity person : persons) {
			  for(Iterator i = persons.iterator();i.hasNext();) {
				Entity person = (Entity)i.next();

/* 497 */       String theirStatus = (String)person.getProperty("personStatus");
/*     */ 
/* 499 */       if ((theirStatus.equals(personStatus)) || ((personStatus.equals("Other")) && (!paidStatuses.contains(theirStatus)))) {
/* 500 */         String exitDateStr = (String)person.getProperty("exitDate");
/*     */ 
/* 502 */         if ((exitDateStr == null) || (exitDateStr.equals("")) || 
/* 503 */           (Util.parseDate(exitDateStr).equals(theFirst.getTime())) || (Util.parseDate(exitDateStr).after(theFirst.getTime()))) {
/* 504 */           if (person.getProperty("ytdPaid") == null) {
/* 505 */             person.setProperty("ytdPaid", Integer.valueOf(0));
/*     */           }
/* 507 */           if (person.getProperty("saved") == null) {
/* 508 */             person.setProperty("saved", Integer.valueOf(0));
/*     */           }
/* 510 */           Benevolence benevolence = new Benevolence(Integer.valueOf(((Number)person.getProperty("ytdPaid")).intValue()), Integer.valueOf(((Number)person.getProperty("saved")).intValue()));
/* 511 */           theMap.put(person, benevolence);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 516 */     return theMap;
/*     */   }
/*     */ 
/*     */   public void setYtdPaid(int ytdPaid) {
/* 520 */     this.ytdPaid = ytdPaid;
/*     */   }
/*     */ 
/*     */   public int getYtdPaid() {
/* 524 */     return this.ytdPaid;
/*     */   }
/*     */ 
/*     */   public void setSaved(int saved) {
/* 528 */     this.saved = saved;
/*     */   }
/*     */ 
/*     */   public int getSaved() {
/* 532 */     return this.saved;
/*     */   }
/*     */ 
/*     */   public int getAmountDue() {
/* 536 */     return this.amountDue;
/*     */   }
/*     */ 
/*     */   public void setAmountDue(int amt) {
/* 540 */     this.amountDue = amt;
/*     */   }
/*     */ }

/* Location:           C:\development\workspace\faithfarmoss\WEB-INF\classes\
 * Qualified Name:     org.faithfarm.dataobjects.Benevolence
 * JD-Core Version:    0.6.2
 */