/*     */ package org.faithfarm;
/*     */ 
/*     */ /*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;

/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServletRequest;

/*     */ import org.faithfarm.validators.InventoryValidator;

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
/*     */ 
/*     */ 
/*     */ public class Item
/*     */   implements Serializable
/*     */ {
/*  30 */   private static final Logger logger = Logger.getLogger(Item.class.getCanonicalName());
/*  31 */   private static InventoryValidator validator = new InventoryValidator();
/*  32 */   private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
/*  33 */   private static int size = 0;
/*  34 */   private static int pages = 0;
/*  35 */   private static String cursor = null;
/*     */ 
/*     */   public static ArrayList saveInventory(String vendorName, String location, String itemNumber, String itemName, String qty1, String qty2, String desc, String cost, String price, String transactionType, String minLevel, String status, String farmLocation, String overwrite)
/*     */   {
/*  41 */     logger.log(Level.INFO, "Creating Inventory Item");
/*  42 */     ArrayList messages = validator.validateSave(vendorName, location, itemNumber, itemName, qty1, qty2, desc, cost, price, minLevel, status, transactionType);
/*     */ 
/*  44 */     if (messages.size() == 0)
/*     */     {
/*  50 */       Entity item = getSingleItem(itemNumber);
/*  51 */       if ((item != null) && (!"Y".equals(overwrite))) {
/*  52 */         messages.add("WARNING: Item " + itemNumber + " already exists. Click 'overwrite' if you wish to overwrite.");
/*  53 */         return messages;
/*     */       }
/*     */ 
/*  56 */       createOrUpdateItem(vendorName, location, itemNumber, itemName, qty1, qty2, desc, cost, price, transactionType, minLevel, status, farmLocation);
/*  57 */       return null;
/*     */     }
/*     */ 
/*  60 */     return messages;
/*     */   }
/*     */   public static Iterable<Entity> sort(String entity, String property, String direction) {
/*  63 */     Query query = null;
/*  64 */     if (direction.equals("ascending"))
/*  65 */       query = new Query(entity).addSort(property, Query.SortDirection.ASCENDING);
/*     */     else {
/*  67 */       query = new Query(entity).addSort(property, Query.SortDirection.DESCENDING);
/*     */     }
/*  69 */     Iterable results = datastore.prepare(query).asIterable(FetchOptions.Builder.withLimit(50));
/*     */ 
/*  71 */     return results;
/*     */   }
/*     */ 
/*     */   public static void saveInventory(String itemNumber, String qty1, String qty2, String farmLocation)
/*     */   {
/*  81 */     Entity item = getSingleItem(itemNumber);
/*  82 */     createOrUpdateItem(null, null, itemNumber, null, qty1, qty2, null, null, null, null, null, null, farmLocation);
/*     */   }
/*     */ 
/*     */   public static Entity createOrUpdateItem(String vendorName, String location, String itemNumber, String itemName, String qty1, String qty2, String desc, String cost, String price, String transactionType, String minLevel, String status, String farmLocation)
/*     */   {
/* 104 */     Entity item = getSingleItem(itemNumber);
/*     */ 
/* 106 */     if (item == null)
/*     */     {
/* 108 */       item = new Entity("Item");
/* 109 */       item.setProperty("vendor", vendorName);
/* 110 */       item.setProperty("location", location);
/* 111 */       item.setProperty("itemNumber", itemNumber.toUpperCase());
/* 112 */       item.setProperty("itemName", itemName);
/* 113 */       item.setProperty("price", price);
/* 114 */       item.setProperty("qtyWarehouse", qty1);
/* 115 */       item.setProperty("qtyFloor", qty2);
/* 116 */       item.setProperty("description", desc);
/* 117 */       item.setProperty("cost", cost);
/* 118 */       item.setProperty("minLevel", minLevel);
/* 119 */       item.setProperty("status", status);
/* 120 */       item.setProperty("transactionType", transactionType);
/* 121 */       item.setProperty("creationDate", new Date());
/* 122 */       recordAudit("CREATE", itemNumber);
/*     */     }
/*     */     else {
/* 125 */       if ((itemName != null) && (!"".equals(itemName))) {
/* 126 */         item.setProperty("itemName", itemName);
/*     */       }
/* 128 */       if ((itemNumber != null) && (!"".equals(itemNumber))) {
/* 129 */         item.setProperty("itemNumber", itemNumber);
/*     */       }
/* 131 */       if ((vendorName != null) && (!"".equals(vendorName))) {
/* 132 */         item.setProperty("vendor", vendorName);
/*     */       }
/* 134 */       if ((location != null) && (!"".equals(location))) {
/* 135 */         item.setProperty("location", location);
/*     */       }
/* 137 */       if ((qty1 != null) && (!"".equals(qty1))) {
/* 138 */         item.setProperty("qtyWarehouse", qty1);
/*     */       }
/* 140 */       if ((qty2 != null) && (!"".equals(qty2))) {
/* 141 */         item.setProperty("qtyFloor", qty2);
/*     */       }
/* 143 */       if ((desc != null) && (!"".equals(desc))) {
/* 144 */         item.setProperty("description", desc);
/*     */       }
/* 146 */       if ((cost != null) && (!"".equals(cost))) {
/* 147 */         item.setProperty("cost", cost);
/*     */       }
/* 149 */       if ((price != null) && (!"".equals(price))) {
/* 150 */         item.setProperty("price", price);
/*     */       }
/* 152 */       if ((transactionType != null) && (!"".equals(transactionType))) {
/* 153 */         item.setProperty("transactionType", transactionType);
/*     */       }
/* 155 */       if ((minLevel != null) && (!"".equals(minLevel))) {
/* 156 */         item.setProperty("minLevel", minLevel);
/*     */       }
/* 158 */       if ((status != null) && (!"".equals(status))) {
/* 159 */         item.setProperty("status", status);
/*     */       }
/* 161 */       item.setProperty("lastUpdatedDate", new Date());
/* 162 */       recordAudit("UPDATE", itemNumber);
/*     */     }
/* 164 */     item.setProperty("farmLocation", farmLocation);
/* 165 */     Util.persistEntity(item);
/* 166 */     return item;
/*     */   }
/*     */ 
/*     */   public static Iterable<Entity> search(String itemNumber, String itemName, String description, String location, String vendor, String startCursor)
/*     */   {
/* 183 */     Query q = new Query("Item");
/* 184 */     q.addFilter("status", Query.FilterOperator.EQUAL, "Active");
/* 185 */     q.addSort("vendor", Query.SortDirection.ASCENDING);
/* 186 */     q.addSort("itemNumber", Query.SortDirection.ASCENDING);
/* 187 */     q.addSort("itemName", Query.SortDirection.ASCENDING);
/*     */ 
/* 189 */     PreparedQuery pq = datastore.prepare(q);
/*     */     try
/*     */     {
/* 203 */       logger.log(Level.INFO, "searching items");
/*     */ 
/* 205 */       if ((itemNumber != null) && (!itemNumber.equals("")))
/* 206 */         q.addFilter("itemNumber", Query.FilterOperator.EQUAL, itemNumber.toUpperCase());
/* 207 */       if ((description != null) && (!description.equals("")))
/* 208 */         q.addFilter("description", Query.FilterOperator.EQUAL, description);
/* 209 */       if ((itemName != null) && (!itemName.equals("")))
/* 210 */         q.addFilter("itemName", Query.FilterOperator.EQUAL, itemName);
/* 211 */       if ((location != null) && (!location.equals("")))
/* 212 */         q.addFilter("location", Query.FilterOperator.EQUAL, location);
/* 213 */       if ((vendor != null) && (!vendor.equals(""))) {
/* 214 */         q.addFilter("vendor", Query.FilterOperator.EQUAL, vendor);
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 219 */       logger.log(Level.SEVERE, e.getStackTrace().toString());
/*     */     }
/*     */ 
/* 222 */     Iterable results = pq.asIterable();
/*     */ 
/* 225 */     setSize(pq.countEntities());
/*     */ 
/* 229 */     return results;
/*     */   }
/*     */ 
/*     */   public static Iterable<Entity> getAllItems()
/*     */   {
/* 242 */     Iterable entities = Util.listEntities("Item", null, null);
/*     */ 
/* 244 */     return entities;
/*     */   }
/*     */ 
/*     */   public static Iterable<Entity> getItem(String itemName)
/*     */   {
/* 255 */     Iterable entities = Util.listEntities("Item", "itemName", itemName);
/* 256 */     return entities;
/*     */   }
/*     */ 
/*     */   public static Iterable<Entity> getItemsForVendor(String kind, String vendorName)
/*     */   {
/* 269 */     Key ancestorKey = KeyFactory.createKey("VendorOld", vendorName);
/* 270 */     return Util.listChildren("Item", ancestorKey);
/*     */   }
/*     */ 
/*     */   public static Entity getSingleItem(String itemNumber)
/*     */   {
/* 279 */     Query q = new Query("Item");
/* 280 */     q.addFilter("itemNumber", Query.FilterOperator.EQUAL, itemNumber);
/* 281 */     List results = Util.getDatastoreServiceInstance().prepare(q).asList(FetchOptions.Builder.withDefaults());
/* 282 */     if (!results.isEmpty())
/*     */     {
/* 284 */       return (Entity)results.remove(0);
/*     */     }
/* 286 */     return null;
/*     */   }
/*     */ 
/*     */   protected static boolean doDelete(Entity e, HttpServletRequest req)
/*     */     throws ServletException, IOException
/*     */   {
/* 292 */     ArrayList messages = new ArrayList();
/*     */     try
/*     */     {
/* 295 */       Util.deleteEntity(e.getKey());
/* 296 */       recordAudit("DELETE", e.getProperty("itemNumber").toString());
/*     */     } catch (Exception err) {
/* 298 */       String msg = Util.getErrorResponse(err);
/* 299 */       messages.add("Delete Error: " + msg);
/* 300 */       req.setAttribute("messages", messages);
/* 301 */       return false;
/*     */     }
/*     */ 
/* 304 */     return true;
/*     */   }
/*     */ 
/*     */   protected static boolean doDelete(HttpServletRequest req) throws ServletException, IOException {
/* 308 */     String itemKey = "item";
/* 309 */     ArrayList messages = new ArrayList();
/*     */     try
/*     */     {
/* 313 */       int i = 0;
/* 314 */       int index = Integer.parseInt(req.getParameter("index"));
/* 315 */       Iterable entities = (Iterable)req.getSession().getAttribute("results");
/*     */ 
/* 317 */      // for (Entity e : entities) {
	for (Iterator it = entities.iterator(); it.hasNext();) {
		Entity e = (Entity) it.next();

/* 318 */         if (index == i) {
/* 319 */           Util.deleteEntity(e.getKey());
/*     */         }
/* 321 */         i++;
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 325 */       String msg = Util.getErrorResponse(e);
/* 326 */       messages.add("Delete Error: " + msg);
/* 327 */       req.setAttribute("messages", messages);
/* 328 */       return false;
/*     */     }
/*     */ 
/* 331 */     return true;
/*     */   }
/*     */ 
/*     */   protected static ArrayList doDelete(Entity e) throws ServletException, IOException
/*     */   {
/* 336 */     ArrayList messages = new ArrayList();
/*     */     try
/*     */     {
/* 340 */       Util.deleteEntity(e.getKey());
/* 341 */       messages = null;
/*     */     }
/*     */     catch (Exception err) {
/* 344 */       String msg = Util.getErrorResponse(err);
/* 345 */       messages.add("Delete Error: " + msg);
/*     */     }
/*     */ 
/* 349 */     return messages;
/*     */   }
/*     */ 
/*     */   protected static boolean doEdit(HttpServletRequest req) throws ServletException, IOException {
/* 353 */     String itemKey = "item";
/* 354 */     ArrayList messages = new ArrayList();
/*     */     try
/*     */     {
/* 358 */       int i = 0;
/* 359 */       int index = Integer.parseInt(req.getParameter("index"));
/* 360 */       Iterable entities = (Iterable)req.getSession().getAttribute("results");
/*     */ 
/* 362 */       //for (Entity e : entities) {
	for (Iterator it = entities.iterator(); it.hasNext();) {
		Entity e = (Entity) it.next();

/* 363 */         if (index == i) {
/* 364 */           Util.deleteEntity(e.getKey());
/* 365 */           String vendorName = (String)e.getProperty("itemName");
/* 366 */           String itemName = (String)e.getProperty("itemName");
/* 367 */           String str1 = (String)e.getProperty("qty1");
/*     */         }
/*     */ 
/* 373 */         i++;
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 377 */       String msg = Util.getErrorResponse(e);
/* 378 */       messages.add("Delete Error: " + msg);
/* 379 */       req.getSession().setAttribute("messages", messages);
/* 380 */       return false;
/*     */     }
/*     */ 
/* 383 */     return true;
/*     */   }
/*     */ 
/*     */   private static void recordAudit(String action, String key)
/*     */   {
/* 403 */     Entity Audit = new Entity("TransactionHistory");
/* 404 */     Audit.setProperty("entity", "Item");
/* 405 */     Audit.setProperty("action", action);
/* 406 */     Audit.setProperty("key", key);
/* 407 */     Audit.setProperty("datetime", new Date());
/* 408 */     Util.persistEntity(Audit);
/*     */   }
/*     */ 
/*     */   public static int getSize() {
/* 412 */     return size;
/*     */   }
/*     */ 
/*     */   public static void setSize(int size) {
/* 416 */     size = size;
/*     */   }
/*     */   public static int getPages() {
/* 419 */     return pages;
/*     */   }
/*     */ 
/*     */   public static void setPages(int pages) {
/* 423 */     pages = pages;
/*     */   }
/*     */ 
/*     */   public static String getCursor() {
/* 427 */     return cursor;
/*     */   }
/*     */ 
/*     */   public static void setCursor(String cursor) {
/* 431 */     cursor = cursor;
/*     */   }
/*     */ }

/* Location:           C:\development\workspace\faithfarmoss\WEB-INF\classes\
 * Qualified Name:     org.faithfarm.Item
 * JD-Core Version:    0.6.2
 */