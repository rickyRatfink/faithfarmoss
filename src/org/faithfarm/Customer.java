/*     */ package org.faithfarm;
/*     */ 
/*     */ /*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;

/*     */ import javax.servlet.ServletException;

/*     */ import org.faithfarm.utilities.HTMLBuilder;
/*     */ import org.faithfarm.validators.CustomerValidator;

import com.google.appengine.api.datastore.DatastoreService;
/*     */ import com.google.appengine.api.datastore.DatastoreServiceFactory;
/*     */ import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
/*     */ import com.google.appengine.api.datastore.Key;
/*     */ import com.google.appengine.api.datastore.KeyFactory;
/*     */ import com.google.appengine.api.datastore.PreparedQuery;
/*     */ import com.google.appengine.api.datastore.Query;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Customer
/*     */ {
/*  48 */   private static final Logger logger = Logger.getLogger(Customer.class.getCanonicalName());
/*  49 */   private static final HTMLBuilder html = new HTMLBuilder();
/*  50 */   private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
/*  51 */   private static CustomerValidator validator = new CustomerValidator();
/*  52 */   private static int size = 0;
/*  53 */   private static int pages = 0;
/*  54 */   private static String cursor = null;
/*     */ 
/*     */   public static ArrayList saveCustomer(String firstName, String lastName, String middleInitial, String suffix, String address1, String address2, String city, String state, String zip, String email, String key, String cell, String work, String home)
/*     */   {
/*  85 */     logger.log(Level.INFO, "Creating Customer");
/*     */ 
/*  87 */     ArrayList messages = validator.validateSave(firstName, lastName, address1, city, state, zip, email, cell, work, home);
/*     */ 
/*  89 */     if (messages.size() == 0) {
/*  90 */       createOrUpdateCustomer(key, firstName, middleInitial, lastName, suffix, 
/*  91 */         address1, address2, city, state, zip, email, cell, work, home);
/*  92 */       return null;
/*     */     }
/*     */ 
/*  95 */     return messages;
/*     */   }
/*     */ 
/*     */   public static void createOrUpdateCustomer(String key, String firstName, String middleInitial, String lastName, String suffix, String address1, String address2, String city, String state, String zip, String email, String cell, String work, String home)
/*     */   {
/* 103 */     if ((key == null) || (key.equals(""))) {
/* 104 */       key = firstName + " " + lastName;
/*     */     }
/* 106 */     Entity customer = getSingleCustomer(key);
/* 107 */     if (customer == null) {
/* 108 */       customer = new Entity("Customer", key);
/*     */ 
/* 110 */       customer.setProperty("name", key);
/* 111 */       customer.setProperty("firstName", firstName);
/* 112 */       customer.setProperty("middleInitial", middleInitial);
/* 113 */       customer.setProperty("lastName", lastName);
/* 114 */       customer.setProperty("suffix", suffix);
/* 115 */       customer.setProperty("addressLine1", address1);
/* 116 */       customer.setProperty("addressLine2", address2);
/* 117 */       customer.setProperty("city", city);
/* 118 */       customer.setProperty("state", state);
/* 119 */       customer.setProperty("zipcode", zip);
/* 120 */       customer.setProperty("email", email);
/* 121 */       customer.setProperty("cell", cell);
/* 122 */       customer.setProperty("work", work);
/* 123 */       customer.setProperty("home", home);
/* 124 */       customer.setProperty("creationDate", new Date());
/* 125 */       recordAudit("CREATE", key);
/*     */     }
/*     */     else {
/* 128 */       if ((firstName != null) && (!"".equals(firstName))) {
/* 129 */         customer.setProperty("firstName", firstName);
/*     */       }
/* 131 */       if ((middleInitial != null) && (!"".equals(middleInitial))) {
/* 132 */         customer.setProperty("middleInitial", middleInitial);
/*     */       }
/* 134 */       if ((lastName != null) && (!"".equals(lastName))) {
/* 135 */         customer.setProperty("lastName", lastName);
/*     */       }
/* 137 */       if ((suffix != null) && (!"".equals(suffix))) {
/* 138 */         customer.setProperty("suffix", suffix);
/*     */       }
/* 140 */       if ((cell != null) && (!"".equals(cell))) {
/* 141 */         customer.setProperty("cell", cell);
/*     */       }
/* 143 */       if ((work != null) && (!"".equals(work))) {
/* 144 */         customer.setProperty("work", work);
/*     */       }
/* 146 */       if ((home != null) && (!"".equals(home))) {
/* 147 */         customer.setProperty("home", home);
/*     */       }
/* 149 */       if ((address1 != null) && (!"".equals(address1))) {
/* 150 */         customer.setProperty("addressLine1", address1);
/*     */       }
/* 152 */       if ((address2 != null) && (!"".equals(address2))) {
/* 153 */         customer.setProperty("addressLine2", address2);
/*     */       }
/* 155 */       if ((city != null) && (!"".equals(city))) {
/* 156 */         customer.setProperty("city", city);
/*     */       }
/* 158 */       if ((state != null) && (!"".equals(state))) {
/* 159 */         customer.setProperty("state", state);
/*     */       }
/* 161 */       if ((zip != null) && (!"".equals(zip))) {
/* 162 */         customer.setProperty("zipcode", zip);
/*     */       }
/* 164 */       if ((email != null) && (!"".equals(email))) {
/* 165 */         customer.setProperty("email", email);
/*     */       }
/* 167 */       customer.setProperty("lastUpdatedDate", new Date());
/* 168 */       recordAudit("CREATE", key);
/*     */     }
/* 170 */     Util.persistEntity(customer);
/*     */   }
/*     */ 
/*     */   public static Iterable<Entity> getAllCustomers()
/*     */   {
/* 182 */     Iterable entities = Util.listEntities("Customer", null, null);
/* 183 */     return entities;
/*     */   }
/*     */ 
/*     */   public static Iterable<Entity> getAllCustomers(String key, String firstName, String lastName)
/*     */   {
/* 188 */     Query q = new Query("Customer");
/* 189 */     PreparedQuery pq = datastore.prepare(q);
/* 190 */     setSize(pq.countEntities());
/*     */     try
/*     */     {
/* 193 */       logger.log(Level.INFO, "searching items");
/*     */ 
/* 195 */       if ((key != null) && (!key.equals("")))
/* 196 */         q.addFilter("name", Query.FilterOperator.EQUAL, key);
/* 197 */       if ((firstName != null) && (!firstName.equals("")))
/* 198 */         q.addFilter("firstName", Query.FilterOperator.EQUAL, firstName);
/* 199 */       if ((lastName != null) && (!lastName.equals("")))
/* 200 */         q.addFilter("lastName", Query.FilterOperator.EQUAL, lastName);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 204 */       logger.log(Level.SEVERE, e.getStackTrace().toString());
/*     */     }
/* 206 */     return pq.asIterable();
/*     */   }
/*     */ 
/*     */   public static Iterable<Entity> getCustomer(String customerName)
/*     */   {
/* 219 */     Iterable entities = Util.listEntities("Customer", "name", customerName);
/* 220 */     return entities;
/*     */   }
/*     */ 
/*     */   public static Entity getSingleCustomer(String customerKey)
/*     */   {
/* 232 */     Key key = KeyFactory.createKey("Customer", customerKey);
/* 233 */     return Util.findEntity(key);
/*     */   }
/*     */ 
/*     */   public static Entity getSingleItem(String customerKey) {
/* 237 */     Query q = new Query("Customer");
/* 238 */     q.addFilter("name", Query.FilterOperator.EQUAL, customerKey);
/* 239 */     List results = Util.getDatastoreServiceInstance().prepare(q).asList(FetchOptions.Builder.withDefaults());
/* 240 */     if (!results.isEmpty()) {
/* 241 */       return (Entity)results.remove(0);
/*     */     }
/* 243 */     return null;
/*     */   }
/*     */ 
/*     */   protected static ArrayList doDelete(Entity e)
/*     */     throws ServletException, IOException
/*     */   {
/* 249 */     ArrayList messages = new ArrayList();
/*     */     try
/*     */     {
/* 253 */       Util.deleteEntity(e.getKey());
/* 254 */       recordAudit("DELETE", e.getProperty("firstName").toString() + " " + e.getProperty("lastName").toString());
/* 255 */       messages = null;
/*     */     }
/*     */     catch (Exception err) {
/* 258 */       String msg = Util.getErrorResponse(err);
/* 259 */       messages.add("Delete Error: " + msg);
/*     */     }
/*     */ 
/* 263 */     return messages;
/*     */   }
/*     */ 
/*     */   private static void recordAudit(String action, String key) {
/* 267 */     Entity Audit = new Entity("TransactionHistory");
/* 268 */     Audit.setProperty("entity", "Customer");
/* 269 */     Audit.setProperty("action", action);
/* 270 */     Audit.setProperty("key", key);
/* 271 */     Audit.setProperty("datetime", new Date());
/* 272 */     Util.persistEntity(Audit);
/*     */   }
/*     */ 
/*     */   public static int getSize() {
/* 276 */     return size;
/*     */   }
/*     */ 
/*     */   public static void setSize(int size) {
/* 280 */     size = size;
/*     */   }
/*     */ 
/*     */   public static int getPages() {
/* 284 */     return pages;
/*     */   }
/*     */ 
/*     */   public static void setPages(int pages) {
/* 288 */     pages = pages;
/*     */   }
/*     */ 
/*     */   public static String getCursor() {
/* 292 */     return cursor;
/*     */   }
/*     */ 
/*     */   public static void setCursor(String cursor) {
/* 296 */     cursor = cursor;
/*     */   }
/*     */ }

/* Location:           C:\development\workspace\faithfarmoss\WEB-INF\classes\
 * Qualified Name:     org.faithfarm.Customer
 * JD-Core Version:    0.6.2
 */