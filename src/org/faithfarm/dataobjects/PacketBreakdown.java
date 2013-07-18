/*     */ package org.faithfarm.dataobjects;
/*     */ 
/*     */ /*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.logging.Logger;

import com.google.appengine.api.datastore.DatastoreService;
/*     */ import com.google.appengine.api.datastore.DatastoreServiceFactory;
/*     */ import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
/*     */ import com.google.appengine.api.datastore.Query;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PacketBreakdown
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -7188690076051762471L;
/*  25 */   private static final Logger logger = Logger.getLogger(PacketBreakdown.class.getCanonicalName());
/*     */   private int ones;
/*     */   private int fives;
/*     */   private int tens;
/*     */   private int twenties;
/*     */ 
/*     */   public static PacketBreakdown getOmegaBreakdown(String farmBase)
/*     */   {
/*  33 */     int ones = 0;
/*  34 */     int fives = 0;
/*  35 */     int tens = 0;
/*  36 */     int twenties = 0;
/*  37 */     DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
/*  38 */     Query query = new Query("Person");
/*  39 */     query.addFilter("personStatus", Query.FilterOperator.EQUAL, "Omega School");
/*  40 */     query.addFilter("personType", Query.FilterOperator.EQUAL, "Resident");
/*  41 */     query.addFilter("farmBase", Query.FilterOperator.EQUAL, farmBase);
/*  42 */     List persons = datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());
/*  43 */     for (int x = 0; x < persons.size(); x++) {
/*  44 */       int deductions = Deduction.getTotalDeductions((Entity)persons.get(x));
/*  45 */       PacketBreakdown tmpBreakdown = new PacketBreakdown(25 - deductions);
/*  46 */       ones += tmpBreakdown.getOnes();
/*  47 */       fives += tmpBreakdown.getFives();
/*  48 */       tens += tmpBreakdown.getTens();
/*  49 */       twenties += tmpBreakdown.getTwenties();
/*     */     }
/*  51 */     PacketBreakdown breakdown = new PacketBreakdown(twenties, tens, fives, ones);
/*  52 */     return breakdown;
/*     */   }
/*     */ 
/*     */   public static PacketBreakdown getSLSBreakdown(String farmBase) {
/*  56 */     int ones = 0;
/*  57 */     int fives = 0;
/*  58 */     int tens = 0;
/*  59 */     int twenties = 0;
/*  60 */     DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
/*  61 */     Query query = new Query("Person");
/*  62 */     query.addFilter("personStatus", Query.FilterOperator.EQUAL, "SLS");
/*  63 */     query.addFilter("personType", Query.FilterOperator.EQUAL, "Resident");
/*  64 */     query.addFilter("farmBase", Query.FilterOperator.EQUAL, farmBase);
/*  65 */     Iterable persons = datastore.prepare(query).asIterable();
/*  66 */     //for (Entity person : persons) {
			  for(Iterator i = persons.iterator();i.hasNext();) {
				Entity person = (Entity)i.next();
/*  67 */       int amount = Benevolence.getAmountDueSLSFromEntity(person).intValue();
/*  68 */       int deductions = Deduction.getTotalDeductions(person);
/*  69 */       PacketBreakdown tmpBreakdown = new PacketBreakdown(amount - deductions);
/*  70 */       ones += tmpBreakdown.getOnes();
/*  71 */       fives += tmpBreakdown.getFives();
/*  72 */       tens += tmpBreakdown.getTens();
/*  73 */       twenties += tmpBreakdown.getTwenties();
/*     */     }
/*  75 */     PacketBreakdown breakdown = new PacketBreakdown(twenties, tens, fives, ones);
/*  76 */     return breakdown;
/*     */   }
/*     */ 
/*     */   public static PacketBreakdown getStudentBreakdown(String farmBase) {
/*  80 */     int ones = 0;
/*  81 */     int fives = 0;
/*  82 */     int tens = 0;
/*  83 */     int twenties = 0;
/*  84 */     DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
/*  85 */     Query query = new Query("Person");
/*  86 */     query.addFilter("personStatus", Query.FilterOperator.EQUAL, "Active");
/*  87 */     query.addFilter("personType", Query.FilterOperator.EQUAL, "Resident");
/*  88 */     query.addFilter("farmBase", Query.FilterOperator.EQUAL, farmBase);
/*  89 */     query.addSort("lastName");
/*  90 */     Iterable persons = datastore.prepare(query).asIterable();
/*  91 */     //for (Entity person : persons) {
			  for(Iterator i = persons.iterator();i.hasNext();) {
		        Entity person = (Entity)i.next();

/*  92 */       int amount = Integer.parseInt(Benevolence.getAmountDueFromEntity(person)) - Deduction.getTotalDeductions(person);
/*     */ 
/*  94 */       PacketBreakdown tmpBreakdown = new PacketBreakdown(amount);
/*  95 */       ones += tmpBreakdown.getOnes();
/*  96 */       fives += tmpBreakdown.getFives();
/*  97 */       tens += tmpBreakdown.getTens();
/*  98 */       twenties += tmpBreakdown.getTwenties();
/*     */     }
/* 100 */     PacketBreakdown breakdown = new PacketBreakdown(twenties, tens, fives, ones);
/* 101 */     return breakdown;
/*     */   }
/*     */ 
/*     */   public static HashMap<Integer, Integer> getPacketBreakdowns(String farmBase)
/*     */   {
/* 110 */     HashMap theMap = new HashMap();
/* 111 */     DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
/* 112 */     Query query = new Query("Person");
/* 113 */     query.addFilter("personStatus", Query.FilterOperator.IN, Arrays.asList(new String[] { "SLS", "Omega School", "Active" }));
/* 114 */     query.addFilter("personType", Query.FilterOperator.EQUAL, "Resident");
/* 115 */     query.addFilter("farmBase", Query.FilterOperator.EQUAL, farmBase);
/* 116 */     Iterable persons = datastore.prepare(query).asList(FetchOptions.Builder.withChunkSize(100));
/* 117 */     //for (Entity person : persons) {
			  for(Iterator i = persons.iterator();i.hasNext();) {
				Entity person = (Entity)i.next();

/* 118 */       String status = (String)person.getProperty("personStatus");
/* 119 */       Integer amount = Integer.valueOf(0);
/* 120 */       Integer deductions = Integer.valueOf(0);
/* 121 */       if (status.equals("Active")) {
/* 122 */         amount = Integer.valueOf(Integer.parseInt(Benevolence.getAmountDueFromEntity(person)) - Deduction.getTotalDeductions(person));
/*     */ 
/* 124 */         Integer count = (Integer)theMap.get(amount);
/* 125 */         if (count == null) {
/* 126 */           count = Integer.valueOf(1);
/*     */         }
/*     */         else {
/* 129 */           count = Integer.valueOf(count.intValue() + 1);
/*     */         }
/* 131 */         if (amount.intValue() == 0) {
/* 132 */           logger.severe("Zero amount for student id: " + person.getKey().getId());
/*     */         }
/* 134 */         theMap.put(amount, count);
/*     */       }
/* 136 */       else if (status.equals("Omega School")) {
/* 137 */         amount = Integer.valueOf(25 - Deduction.getTotalDeductions(person));
/* 138 */         Integer count = (Integer)theMap.get(amount);
/* 139 */         if (count == null) {
/* 140 */           count = Integer.valueOf(1);
/*     */         }
/*     */         else {
/* 143 */           count = Integer.valueOf(count.intValue() + 1);
/*     */         }
/* 145 */         theMap.put(amount, count);
/*     */       }
/* 147 */       else if (status.equals("SLS")) {
/* 148 */         amount = Integer.valueOf(Benevolence.getAmountDueSLSFromEntity(person).intValue() - Deduction.getTotalDeductions(person));
/* 149 */         Integer count = (Integer)theMap.get(amount);
/* 150 */         if (count == null) {
/* 151 */           count = Integer.valueOf(1);
/*     */         }
/*     */         else {
/* 154 */           count = Integer.valueOf(count.intValue() + 1);
/*     */         }
/* 156 */         if (amount.intValue() == 0) {
/* 157 */           logger.severe("Zero amount for person: " + person.getKey().getId());
/*     */         }
/* 159 */         theMap.put(amount, count);
/*     */       }
/*     */     }
/*     */ 
/* 163 */     return theMap;
/*     */   }
/*     */ 
/*     */   public PacketBreakdown(int twenties, int tens, int fives, int ones)
/*     */   {
/* 176 */     this.twenties = twenties;
/* 177 */     this.tens = tens;
/* 178 */     this.fives = fives;
/* 179 */     this.ones = ones;
/*     */   }
/*     */ 
/*     */   public PacketBreakdown(int amount)
/*     */   {
/* 186 */     this.ones = 0;
/* 187 */     this.fives = 0;
/* 188 */     this.tens = 0;
/* 189 */     this.twenties = 0;
/* 190 */     this.ones = (amount % 5 != 0 ? amount % 5 : 5);
/* 191 */     amount -= this.ones;
/* 192 */     while (amount != 0)
/* 193 */       if (Math.floor(amount / 5) > 2.0D) { this.tens += 1; amount -= 10; } else {
/* 194 */         this.fives = ((int)Math.floor(amount / 5)); amount -= this.fives * 5;
/*     */       }
/* 196 */     for (; this.tens > 2; this.twenties += 1) this.tens -= 2; 
/*     */   }
/*     */ 
/*     */   public int getOnes()
/*     */   {
/* 200 */     return this.ones;
/*     */   }
/*     */ 
/*     */   public int getFives() {
/* 204 */     return this.fives;
/*     */   }
/*     */ 
/*     */   public int getTens() {
/* 208 */     return this.tens;
/*     */   }
/*     */ 
/*     */   public int getTwenties() {
/* 212 */     return this.twenties;
/*     */   }
/*     */ }

/* Location:           C:\development\workspace\faithfarmoss\WEB-INF\classes\
 * Qualified Name:     org.faithfarm.dataobjects.PacketBreakdown
 * JD-Core Version:    0.6.2
 */