/*    */ package org.faithfarm.datawriters;
/*    */ 
/*    */ import com.google.appengine.api.datastore.DatastoreService;
/*    */ import com.google.appengine.api.datastore.DatastoreServiceFactory;
/*    */ import com.google.appengine.api.datastore.Entity;
/*    */ import com.google.appengine.api.datastore.Key;
/*    */ import com.google.appengine.api.datastore.KeyFactory;
/*    */ import java.io.Serializable;
/*    */ import java.util.Date;
/*    */ import java.util.logging.Logger;
/*    */ import org.faithfarm.Util;
/*    */ import org.faithfarm.dataobjects.Person;
/*    */ import org.faithfarm.validators.InventoryValidator;
/*    */ 
/*    */ public class DispatchDao
/*    */   implements Serializable
/*    */ {
/* 19 */   private static final Logger logger = Logger.getLogger(Person.class
/* 20 */     .getCanonicalName());
/*    */ 
/* 21 */   private static InventoryValidator validator = new InventoryValidator();
/*    */ 
/* 23 */   private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
/* 24 */   private static int size = 0;
/* 25 */   private static int pages = 0;
/* 26 */   private static String cursor = null;
/*    */ 
/*    */   public static Entity getSingleDispatch(String sId) {
/* 29 */     Long id = Long.valueOf(sId);
/* 30 */     Key key = KeyFactory.createKey("Dispatch", id.longValue());
/* 31 */     return Util.findEntity(key);
/*    */   }
/*    */   public static Iterable<Entity> getAllDispatches() {
/* 34 */     Iterable entities = Util.listEntities("Dispatch", null, null);
/* 35 */     return entities;
/*    */   }
/*    */ 
/*    */   public static void deleteDispatch(Entity dispatch) {
/* 39 */     Util.deleteEntity(dispatch.getKey());
/*    */   }
/*    */ 
/*    */   public static void createDispatch(Entity dispatch) {
/* 43 */     dispatch.setProperty("creationDate", new Date());
/* 44 */     Util.persistEntity(dispatch);
/*    */   }
/*    */ }

/* Location:           C:\development\workspace\faithfarmoss\WEB-INF\classes\
 * Qualified Name:     org.faithfarm.datawriters.DispatchDao
 * JD-Core Version:    0.6.2
 */