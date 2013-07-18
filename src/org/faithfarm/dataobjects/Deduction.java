/*     */ package org.faithfarm.dataobjects;
/*     */ 
/*     */ /*     */ import java.io.Serializable;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;

/*     */ import org.faithfarm.Util;

import com.google.appengine.api.datastore.DatastoreService;
/*     */ import com.google.appengine.api.datastore.DatastoreServiceFactory;
/*     */ import com.google.appengine.api.datastore.Entity;
/*     */ import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
/*     */ import com.google.appengine.api.datastore.Key;
/*     */ import com.google.appengine.api.datastore.KeyFactory;
/*     */ import com.google.appengine.api.datastore.Query;
/*     */ import com.google.appengine.api.memcache.Expiration;
/*     */ import com.google.appengine.api.memcache.MemcacheService;
/*     */ import com.google.appengine.api.memcache.MemcacheServiceFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Deduction
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -2305804339316138875L;
/*     */   private Date date;
/*     */   private String reason;
/*     */   private int amount;
/*     */   private Key key;
/*  42 */   private static final Logger logger = Logger.getLogger(Deduction.class.getCanonicalName());
/*  43 */   private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
/*     */ 
/*     */   public static Deduction getInstanceFromEntity(Entity entity)
/*     */   {
/*  52 */     Deduction deduction = new Deduction();
/*  53 */     deduction.setAmount(Long.valueOf(((Long)entity.getProperty("amount")).longValue()));
/*  54 */     deduction.setDate((Date)entity.getProperty("date"));
/*  55 */     deduction.setReason((String)entity.getProperty("reason"));
/*  56 */     deduction.setKey(entity.getKey());
/*  57 */     return deduction;
/*     */   }
/*     */ 
/*     */   public static Entity getByIdAndParentId(int dId, int pId)
/*     */   {
/*  70 */     Entity deductionEntity = null;
/*  71 */     Key parentKey = KeyFactory.createKey("Person", pId);
/*  72 */     Key deductionKey = KeyFactory.createKey(parentKey, "Deduction", dId);
/*     */     try {
/*  74 */       deductionEntity = datastore.get(deductionKey);
/*     */     } catch (EntityNotFoundException e) {
/*  76 */       logger.log(Level.SEVERE, e.getMessage());
/*     */     }
/*  78 */     return deductionEntity;
/*     */   }
/*     */ 
/*     */   public static Entity getPersonByDeductionEntity(Entity deduction)
/*     */   {
/*  88 */     Entity person = null;
/*  89 */     Key parentKey = deduction.getParent();
/*     */     try {
/*  91 */       person = datastore.get(parentKey);
/*     */     } catch (EntityNotFoundException e) {
/*  93 */       logger.log(Level.SEVERE, e.getMessage());
/*     */     }
/*  95 */     return person;
/*     */   }
/*     */ 
/*     */   public static HashMap<Entity, Entity> getPendingDeductions()
/*     */   {
/* 104 */     HashMap theMap = new HashMap();
/* 105 */     Query query = new Query("Deduction");
/* 106 */     List results = null;
/* 107 */     Calendar thisFriday = Benevolence.getReportDay();
/*     */ 
/* 109 */     Calendar lastTuesday = (Calendar)thisFriday.clone();
/* 110 */     lastTuesday.add(3, -1);
/* 111 */     lastTuesday.set(7, 3);
/* 112 */     query.addFilter("date", Query.FilterOperator.GREATER_THAN_OR_EQUAL, lastTuesday.getTime());
/* 113 */     results = datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());
/* 114 */     for (int x = 0; x < results.size(); x++) {
/* 115 */       Entity deductionEntity = (Entity)results.get(x);
/* 116 */       Entity parentEntity = null;
/*     */       try {
/* 118 */         parentEntity = datastore.get(deductionEntity.getParent());
/*     */       } catch (EntityNotFoundException e) {
/* 120 */         logger.log(Level.SEVERE, "Parent entity not found: " + e.getMessage());
/*     */       }
/* 122 */       theMap.put(deductionEntity, parentEntity);
/*     */     }
/* 124 */     return theMap;
/*     */   }
/*     */ 
/*     */   public static String getDeductionString(Entity person)
/*     */   {
/* 129 */     StringBuffer deductionString = new StringBuffer();
/* 130 */     List deductions = getDeductionsForEntity(person);
/* 131 */     for (int x = 0; x < deductions.size(); x++) {
/* 132 */       deductionString.append(((Entity)deductions.get(x)).getProperty("reason"));
/* 133 */       deductionString.append("\n");
/*     */     }
/* 135 */     return deductionString.toString();
/*     */   }
/*     */ 
/*     */   public static List<Entity> getDeductionsForEntity(Entity person)
/*     */   {
/* 147 */     MemcacheService cache = MemcacheServiceFactory.getMemcacheService();
/* 148 */     List deductions = null;
/* 149 */     Calendar thisFriday = Benevolence.getReportDay();
/* 150 */     Calendar toMonday = (Calendar)thisFriday.clone();
/* 151 */     toMonday.set(7, 2);
/* 152 */     toMonday.set(11, 23);
/* 153 */     toMonday.set(12, 59);
/* 154 */     toMonday.set(13, 59);
/* 155 */     Calendar lastTuesday = (Calendar)thisFriday.clone();
/* 156 */     lastTuesday.add(3, -1);
/* 157 */     lastTuesday.set(7, 3);
/* 158 */     MessageDigest md = null;
/* 159 */     Query query = new Query("Deduction");
/* 160 */     query.setAncestor(person.getKey());
/* 161 */     query.addFilter("date", Query.FilterOperator.GREATER_THAN_OR_EQUAL, lastTuesday.getTime());
/* 162 */     query.addFilter("date", Query.FilterOperator.LESS_THAN_OR_EQUAL, toMonday.getTime());
/*     */     try {
/* 164 */       md = MessageDigest.getInstance("SHA");
/*     */     } catch (NoSuchAlgorithmException e) {
/* 166 */       logger.log(Level.SEVERE, e.getMessage());
/*     */     }
/* 168 */     byte[] messageBytes = query.toString().getBytes();
/* 169 */     md.update(messageBytes);
/* 170 */     byte[] messageDigest = md.digest();
/* 171 */     StringBuffer hexString = new StringBuffer();
/* 172 */     for (int i = 0; i < messageDigest.length; i++) {
/* 173 */       hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
/*     */     }
/* 175 */     deductions = (List)cache.get(hexString.toString());
/* 176 */     if (deductions == null) {
/* 177 */       deductions = datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());
/* 178 */       cache.put(hexString.toString(), deductions, Expiration.byDeltaSeconds(30));
/*     */     }
/* 180 */     return deductions;
/*     */   }
/*     */ 
/*     */   public static int getTotalDeductions(Entity entity)
/*     */   {
/* 190 */     int total = 0;
/* 191 */     Calendar thisFriday = Benevolence.getReportDay();
/* 192 */     Calendar toMonday = (Calendar)thisFriday.clone();
/*     */ 
/* 194 */     toMonday.set(7, 2);
/* 195 */     toMonday.set(11, 23);
/* 196 */     toMonday.set(12, 59);
/* 197 */     toMonday.set(13, 59);
/* 198 */     Calendar lastTuesday = (Calendar)thisFriday.clone();
/* 199 */     lastTuesday.add(3, -1);
/* 200 */     lastTuesday.set(7, 3);
/* 201 */     List results = getDeductionsForEntity(entity);
/* 202 */     for (int x = 0; x < results.size(); x++) {
/* 203 */       Entity result = (Entity)results.get(x);
/* 204 */       total += Integer.parseInt(result.getProperty("amount").toString());
/*     */     }
/* 206 */     return total;
/*     */   }
/*     */ 
/*     */   public Deduction()
/*     */   {
/* 214 */     this.date = new Date();
/* 215 */     this.reason = "Not set";
/* 216 */     this.amount = 0;
/*     */   }
/*     */ 
/*     */   public void saveAsEntity(Entity parent)
/*     */   {
/* 225 */     Entity entity = new Entity("Deduction", parent.getKey());
/* 226 */     entity.setProperty("date", this.date);
/* 227 */     entity.setProperty("reason", this.reason);
/* 228 */     entity.setProperty("amount", Integer.valueOf(this.amount));
/* 229 */     Util.persistEntity(entity);
/*     */   }
/*     */ 
/*     */   public Date getDate()
/*     */   {
/* 238 */     return this.date;
/*     */   }
/*     */ 
/*     */   public String getReason()
/*     */   {
/* 246 */     return this.reason;
/*     */   }
/*     */ 
/*     */   public int getAmount()
/*     */   {
/* 254 */     return this.amount;
/*     */   }
/*     */ 
/*     */   public Key getKey()
/*     */   {
/* 262 */     return this.key;
/*     */   }
/*     */ 
/*     */   public void setDate(Date d)
/*     */   {
/* 270 */     this.date = d;
/*     */   }
/*     */ 
/*     */   public void setReason(String r)
/*     */   {
/* 278 */     this.reason = r;
/*     */   }
/*     */ 
/*     */   public void setAmount(String amount)
/*     */   {
/* 286 */     int amt = Integer.parseInt(amount, 10);
/* 287 */     this.amount = amt;
/*     */   }
/*     */ 
/*     */   public void setAmount(int amount)
/*     */   {
/* 295 */     this.amount = amount;
/*     */   }
/*     */ 
/*     */   public void setAmount(Long amount)
/*     */   {
/* 303 */     this.amount = Integer.parseInt(amount.toString());
/*     */   }
/*     */ 
/*     */   public void setKey(Key key)
/*     */   {
/* 311 */     this.key = key;
/*     */   }
/*     */ }

/* Location:           C:\development\workspace\faithfarmoss\WEB-INF\classes\
 * Qualified Name:     org.faithfarm.dataobjects.Deduction
 * JD-Core Version:    0.6.2
 */