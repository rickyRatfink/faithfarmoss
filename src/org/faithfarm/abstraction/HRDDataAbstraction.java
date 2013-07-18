/*    */ package org.faithfarm.abstraction;
/*    */ 
/*    */ /*    */ import java.util.ArrayList;
import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.Map;

import com.google.appengine.api.datastore.DatastoreService;
/*    */ import com.google.appengine.api.datastore.DatastoreServiceFactory;
/*    */ import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
/*    */ import com.google.appengine.api.datastore.Query;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HRDDataAbstraction
/*    */   implements DataAbstraction
/*    */ {
/* 15 */   private static final DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
/*    */ 
/*    */   public List<DAObject> get(String kind, Map<String, String> conditions, Map<String, Integer> sorts)
/*    */   {
/* 20 */     Query query = new Query(kind);
/* 21 */     List list = datastore.prepare(query).asList(FetchOptions.Builder.withChunkSize(500));
/* 22 */     List daList = new ArrayList();
/* 23 */     //for (Entity entity : list) {
		     for(Iterator i = list.iterator();i.hasNext();) {
		       Entity entity = (Entity)i.next();

/* 24 */       DAObject newObject = new DAObject(kind, entity.getProperties());
/* 25 */       daList.add(newObject);
/*    */     }
/* 27 */     return daList;
/*    */   }
/*    */ }

/* Location:           C:\development\workspace\faithfarmoss\WEB-INF\classes\
 * Qualified Name:     org.faithfarm.abstraction.HRDDataAbstraction
 * JD-Core Version:    0.6.2
 */