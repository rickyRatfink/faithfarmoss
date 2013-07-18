/*     */ package org.faithfarm;
/*     */ 
/*     */ import com.google.appengine.api.datastore.DatastoreService;
/*     */ import com.google.appengine.api.datastore.DatastoreServiceFactory;
/*     */ import com.google.appengine.api.datastore.Entity;
/*     */ import com.google.appengine.api.datastore.EntityNotFoundException;
/*     */ import com.google.appengine.api.datastore.Key;
/*     */ import com.google.appengine.api.datastore.PreparedQuery;
/*     */ import com.google.appengine.api.datastore.Query;
/*     */ import com.google.appengine.api.datastore.Query.FilterOperator;
/*     */ import com.google.appengine.api.datastore.Transaction;
/*     */ import com.google.appengine.api.memcache.MemcacheService;
/*     */ import com.google.appengine.api.memcache.MemcacheServiceFactory;
/*     */ import java.io.IOException;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.TimeZone;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ public class Util
/*     */ {
/*  48 */   private static final Logger logger = Logger.getLogger(Util.class.getCanonicalName());
/*  49 */   private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
/*     */ 
/*  51 */   private static MemcacheService keycache = MemcacheServiceFactory.getMemcacheService();
/*     */ 
/*     */   public static Date parseDate(String date)
/*     */   {
/*  57 */     date = date.replace("UTC 11", "EST 2011");
/*  58 */     date = date.replace("UTC 12", "EST 2012");
/*  59 */     date = date.replace("UTC 13", "EST 2013");
/*  60 */     date = date.replace("UTC 14", "EST 2014");
/*  61 */     date = date.replace("UTC 15", "EST 2015");
/*  62 */     date = date.replace("UTC 16", "EST 2016");
/*  63 */     date = date.replace("UTC 17", "EST 2017");
/*  64 */     date = date.replace("UTC 18", "EST 2018");
/*  65 */     date = date.replace("UTC 19", "EST 2019");
/*     */ 
/*  67 */     Date theDate = null;
/*  68 */     SimpleDateFormat fromFormatLong = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
/*  69 */     SimpleDateFormat fromFormatShort = new SimpleDateFormat("MM/dd/yy");
/*  70 */     fromFormatLong.setTimeZone(TimeZone.getTimeZone("EST"));
/*     */     try
/*     */     {
/*  73 */       theDate = fromFormatLong.parse(date);
/*     */     } catch (ParseException e) {
/*     */       try {
/*  76 */         theDate = fromFormatShort.parse(date);
/*     */       } catch (ParseException e2) {
/*  78 */         logger.log(Level.SEVERE, "Unable to parse date: " + date);
/*     */       }
/*     */     }
/*     */ 
/*  82 */     return theDate;
/*     */   }
/*     */ 
/*     */   public static void persistEntity(Entity entity)
/*     */   {
/*  90 */     logger.log(Level.INFO, "Saving entity");
/*  91 */     Key key = entity.getKey();
/*  92 */     Transaction txn = datastore.beginTransaction();
/*     */     try {
/*  94 */       datastore.put(entity);
/*  95 */       txn.commit();
/*     */     }
/*     */     finally
/*     */     {
/* 100 */       if (txn.isActive())
/* 101 */         txn.rollback();
/*     */       else
/* 103 */         addToCache(key, entity);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void deleteEntity(Key key)
/*     */   {
/* 113 */     logger.log(Level.INFO, "Deleting entity");
/* 114 */     Transaction txn = datastore.beginTransaction();
/*     */     try {
/* 116 */       datastore.delete(new Key[] { key });
/* 117 */       txn.commit();
/*     */     } finally {
/* 119 */       if (txn.isActive())
/* 120 */         txn.rollback();
/*     */       else
/* 122 */         deleteFromCache(key);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void deleteEntity(List<Key> keys)
/*     */   {
/* 128 */     datastore.delete(new Iterable() {
/*     */       public Iterator<Key> iterator() {
/* 130 */         return null; //Util.this.iterator();
/*     */       }
/*     */     });
/* 133 */     deleteFromCache(keys);
/*     */   }
protected Iterator<Key> iterator() {
	// TODO Auto-generated method stub
	return null;
}
/*     */ 
/*     */   public static Entity findEntity(Key key)
/*     */   {
/* 142 */     logger.log(Level.INFO, "Search the entity");
/*     */     try {
/* 144 */       Entity e = getFromCache(key);
/* 145 */       if (e != null) {
/* 146 */         return e;
/*     */       }
/* 148 */       return datastore.get(key); } catch (EntityNotFoundException e) {
/*     */     }
/* 150 */     return null;
/*     */   }
/*     */ 
/*     */   public static Iterable<Entity> listEntities(String kind, String searchBy, String searchFor)
/*     */   {
/* 166 */     logger.log(Level.INFO, "Search entities based on search criteria");
/* 167 */     Query q = new Query(kind);
/* 168 */     if ((searchFor != null) && (!"".equals(searchFor))) {
/* 169 */       q.addFilter(searchBy, Query.FilterOperator.EQUAL, searchFor);
/*     */     }
/* 171 */     PreparedQuery pq = datastore.prepare(q);
/* 172 */     return pq.asIterable();
/*     */   }
/*     */ 
/*     */   public static Iterable<Entity> listChildren(String kind, Key ancestor) {
/* 176 */     logger.log(Level.INFO, "Search entities based on parent");
/* 177 */     Query q = new Query(kind);
/* 178 */     q.setAncestor(ancestor);
/* 179 */     q.addFilter("__key__", Query.FilterOperator.GREATER_THAN, ancestor);
/* 180 */     PreparedQuery pq = datastore.prepare(q);
/* 181 */     return pq.asIterable();
/*     */   }
/*     */ 
/*     */   public static Iterable<Entity> listChildKeys(String kind, Key ancestor) {
/* 185 */     logger.log(Level.INFO, "Search entities based on parent");
/* 186 */     Query q = new Query(kind);
/* 187 */     q.setAncestor(ancestor).setKeysOnly();
/* 188 */     q.addFilter("__key__", Query.FilterOperator.GREATER_THAN, ancestor);
/* 189 */     PreparedQuery pq = datastore.prepare(q);
/* 190 */     return pq.asIterable();
/*     */   }
/*     */ 
/*     */   public static String writeJSON(Iterable<Entity> entities)
/*     */   {
/* 199 */     logger.log(Level.INFO, "creating JSON format object");
/* 200 */     StringBuilder sb = new StringBuilder();
/* 201 */     int i = 0;
/* 202 */     sb.append("{\"data\": [");
/* 203 */     for (Entity result : entities) {
/* 204 */       Map properties = result.getProperties();
/* 205 */       sb.append("{");
/* 206 */       if (result.getKey().getName() == null)
/* 207 */         sb.append("\"name\" : \"" + result.getKey().getId() + "\",");
/*     */       else {
/* 209 */         sb.append("\"name\" : \"" + result.getKey().getName() + "\",");
/*     */       }
/* 211 */       //for (String key : properties.keySet()) {
	 for(Iterator it = properties.keySet().iterator();it.hasNext();) {
			Entity key = (Entity)it.next();
/* 212 */         sb.append("\"" + key + "\" : \"" + properties.get(key) + "\",");
/*     */       }
/* 214 */       sb.deleteCharAt(sb.lastIndexOf(","));
/* 215 */       sb.append("},");
/* 216 */       i++;
/*     */     }
/* 218 */     if (i > 0) {
/* 219 */       sb.deleteCharAt(sb.lastIndexOf(","));
/*     */     }
/* 221 */     sb.append("]}");
/* 222 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static String writeJSON(Iterable<Entity> entities, String childKind, String fkName)
/*     */   {
/* 237 */     logger.log(Level.INFO, "creating JSON format object for parent child relation");
/* 238 */     StringBuilder sb = new StringBuilder();
/* 239 */     int i = 0;
/* 240 */     sb.append("{\"data\": [");
/* 241 */     for (Entity result : entities) {
/* 242 */       Map properties = result.getProperties();
/* 243 */       sb.append("{");
/* 244 */       if (result.getKey().getName() == null)
/* 245 */         sb.append("\"name\" : \"" + result.getKey().getId() + "\",");
/*     */       else
/* 247 */         sb.append("\"name\" : \"" + result.getKey().getName() + "\",");
/* 248 */      // for (String key : properties.keySet()) {
	for(Iterator it = properties.keySet().iterator();it.hasNext();) {
		  Entity key = (Entity)it.next();
/* 249 */         sb.append("\"" + key + "\" : \"" + properties.get(key) + "\",");
/*     */       }
/* 251 */       Iterable child = listEntities(childKind, fkName, 
/* 252 */         String.valueOf(result.getKey().getId()));
/*     */       Iterator localIterator4;
/* 253 */       for (Iterator localIterator3 = child.iterator(); localIterator3.hasNext(); 
/* 254 */         localIterator4.hasNext())
/*     */       {
/* 253 */         Entity en = (Entity)localIterator3.next();
/* 254 */         localIterator4 = en.getProperties().keySet().iterator(); //continue; 
				  String key = (String)localIterator4.next();
/* 255 */         sb.append("\"" + key + "\" : \"" + en.getProperties().get(key) + "\",");
/*     */       }
/*     */ 
/* 258 */       sb.deleteCharAt(sb.lastIndexOf(","));
/* 259 */       sb.append("},");
/* 260 */       i++;
/*     */     }
/* 262 */     if (i > 0) {
/* 263 */       sb.deleteCharAt(sb.lastIndexOf(","));
/*     */     }
/* 265 */     sb.append("]}");
/* 266 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static void addToCache(Key key, Entity entity)
/*     */   {
/* 275 */     logger.log(Level.INFO, "Adding entity to cache");
/* 276 */     keycache.put(key, entity);
/*     */   }
/*     */ 
/*     */   public static void deleteFromCache(Key key) {
/* 280 */     logger.log(Level.INFO, "Deleting entity from cache");
/* 281 */     keycache.delete(key);
/*     */   }
/*     */ 
/*     */   public static void deleteFromCache(List<Key> keys) {
/* 285 */     keycache.deleteAll(keys);
/*     */   }
/*     */ 
/*     */   public static Entity getFromCache(Key key) {
/* 289 */     logger.log(Level.INFO, "Searching entity in cache");
/* 290 */     return (Entity)keycache.get(key);
/*     */   }
/*     */ 
/*     */   public static String getErrorResponse(Exception ex)
/*     */     throws IOException
/*     */   {
/* 300 */     return "Error:" + ex.toString();
/*     */   }
/*     */ 
/*     */   public static DatastoreService getDatastoreServiceInstance() {
/* 304 */     return datastore;
/*     */   }
/*     */ }

/* Location:           C:\development\workspace\faithfarmoss\WEB-INF\classes\
 * Qualified Name:     org.faithfarm.Util
 * JD-Core Version:    0.6.2
 */