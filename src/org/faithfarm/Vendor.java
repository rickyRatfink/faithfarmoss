/*     */ package org.faithfarm;
/*     */ 
/*     */ /*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;

/*     */ import javax.servlet.ServletException;

/*     */ import org.faithfarm.validators.VendorValidator;

import com.google.appengine.api.datastore.DatastoreService;
/*     */ import com.google.appengine.api.datastore.DatastoreServiceFactory;
/*     */ import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
/*     */ import com.google.appengine.api.datastore.PreparedQuery;
/*     */ import com.google.appengine.api.datastore.Query;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Vendor
/*     */   implements Serializable
/*     */ {
/*  44 */   private static final Logger logger = Logger.getLogger(Vendor.class.getCanonicalName());
/*  45 */   private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
/*  46 */   private static VendorValidator validator = new VendorValidator();
/*     */ 
/*     */   public static ArrayList saveVendor(String vendorName, String vendorRep, String addressLine1, String city, String state, String zipcode, String email, String vendorNumber, String phone, String orgType, String farmBase)
/*     */   {
/*  75 */     logger.log(Level.INFO, "Creating Org");
/*     */ 
/*  77 */     ArrayList messages = validator.validateSave(vendorName, vendorNumber, addressLine1, city, state, zipcode, email, phone, vendorRep, orgType);
/*     */ 
/*  79 */     if (messages.size() == 0) {
/*  80 */       createOrUpdateVendor(vendorNumber, vendorName, addressLine1, 
/*  81 */         city, state, zipcode, phone, email, vendorRep, orgType, farmBase);
/*  82 */       return null;
/*     */     }
/*     */ 
/*  85 */     return messages;
/*     */   }
/*     */ 
/*     */   public static void createOrUpdateVendor(String vendorNumber, String vendorName, String vendorAddress, String city, String state, String zip, String phone, String email, String vendorRep, String orgType, String farmBase)
/*     */   {
/* 104 */     Entity Vendor = getSingleVendor(vendorNumber);
/* 105 */     if (Vendor == null) {
/* 106 */       Vendor = new Entity("Org");
/* 107 */       Vendor.setProperty("number", vendorNumber);
/* 108 */       Vendor.setProperty("name", vendorName);
/* 109 */       Vendor.setProperty("addressLine1", vendorAddress);
/* 110 */       Vendor.setProperty("city", city);
/* 111 */       Vendor.setProperty("state", state);
/* 112 */       Vendor.setProperty("zipcode", zip);
/* 113 */       Vendor.setProperty("email", email);
/* 114 */       Vendor.setProperty("phone", phone);
/* 115 */       Vendor.setProperty("representative", vendorRep);
/* 116 */       Vendor.setProperty("orgType", orgType);
/* 117 */       Vendor.setProperty("farmBase", farmBase);
/* 118 */       Vendor.setProperty("creationDate", new Date());
/*     */     }
/*     */     else {
/* 121 */       if ((vendorName != null) && (!"".equals(vendorName))) {
/* 122 */         Vendor.setProperty("name", vendorName);
/*     */       }
/* 124 */       if ((vendorNumber != null) && (!"".equals(vendorNumber))) {
/* 125 */         Vendor.setProperty("number", vendorNumber);
/*     */       }
/* 127 */       if ((vendorAddress != null) && (!"".equals(vendorAddress))) {
/* 128 */         Vendor.setProperty("addressLine1", vendorAddress);
/*     */       }
/* 130 */       if ((city != null) && (!"".equals(city))) {
/* 131 */         Vendor.setProperty("city", city);
/*     */       }
/* 133 */       if ((state != null) && (!"".equals(state))) {
/* 134 */         Vendor.setProperty("state", state);
/*     */       }
/* 136 */       if ((zip != null) && (!"".equals(zip))) {
/* 137 */         Vendor.setProperty("zipcode", zip);
/*     */       }
/* 139 */       if ((vendorRep != null) && (!"".equals(vendorRep))) {
/* 140 */         Vendor.setProperty("representative", vendorRep);
/*     */       }
/* 142 */       if ((email != null) && (!"".equals(email))) {
/* 143 */         Vendor.setProperty("email", email);
/*     */       }
/* 145 */       if ((phone != null) && (!"".equals(phone))) {
/* 146 */         Vendor.setProperty("phone", phone);
/*     */       }
/* 148 */       if ((orgType != null) && (!"".equals(orgType))) {
/* 149 */         Vendor.setProperty("orgType", orgType);
/*     */       }
/* 151 */       Vendor.setProperty("lastUpdatedDate", new Date());
/*     */     }
/* 153 */     Util.persistEntity(Vendor);
/*     */   }
/*     */ 
/*     */   public static Iterable<Entity> getAllVendors()
/*     */   {
/* 165 */     Iterable entities = Util.listEntities("Vendor", null, null);
/* 166 */     return entities;
/*     */   }
/*     */ 
/*     */   public static Iterable<Entity> getAllVendors(String vendorNumber, String vendorName)
/*     */   {
/* 171 */     Query q = new Query("Org");
/* 172 */     PreparedQuery pq = datastore.prepare(q);
/*     */     try
/*     */     {
/* 176 */       logger.log(Level.INFO, "searching items");
/*     */ 
/* 178 */       if ((vendorNumber != null) && (!vendorNumber.equals("")))
/* 179 */         q.addFilter("number", Query.FilterOperator.EQUAL, vendorNumber);
/* 180 */       if ((vendorName != null) && (!vendorName.equals("")))
/* 181 */         q.addFilter("name", Query.FilterOperator.EQUAL, vendorName);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 185 */       logger.log(Level.SEVERE, e.getStackTrace().toString());
/*     */     }
/* 187 */     return pq.asIterable();
/*     */   }
/*     */ 
/*     */   public static Iterable<Entity> getVendor(String vendorNumber)
/*     */   {
/* 200 */     Iterable entities = Util.listEntities("Org", "name", vendorNumber);
/* 201 */     return entities;
/*     */   }
/*     */ 
/*     */   public static Entity getSingleVendor(String vendorNumber)
/*     */   {
/* 218 */     Query q = new Query("Org");
/* 219 */     q.addFilter("number", Query.FilterOperator.EQUAL, vendorNumber);
/* 220 */     List results = Util.getDatastoreServiceInstance().prepare(q).asList(FetchOptions.Builder.withDefaults());
/* 221 */     if (!results.isEmpty()) {
/* 222 */       return (Entity)results.remove(0);
/*     */     }
/* 224 */     return null;
/*     */   }
/*     */ 
/*     */   protected static ArrayList doDelete(Entity e)
/*     */     throws ServletException, IOException
/*     */   {
/* 230 */     ArrayList messages = new ArrayList();
/*     */     try
/*     */     {
/* 234 */       Util.deleteEntity(e.getKey());
/* 235 */       messages = null;
/*     */     }
/*     */     catch (Exception err) {
/* 238 */       String msg = Util.getErrorResponse(err);
/* 239 */       messages.add("Delete Error: " + msg);
/*     */     }
/*     */ 
/* 243 */     return messages;
/*     */   }
/*     */ }

/* Location:           C:\development\workspace\faithfarmoss\WEB-INF\classes\
 * Qualified Name:     org.faithfarm.Vendor
 * JD-Core Version:    0.6.2
 */