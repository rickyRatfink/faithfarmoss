/*     */ package org.faithfarm.datawriters;
/*     */ 
/*     */ import com.google.appengine.api.datastore.DatastoreService;
/*     */ import com.google.appengine.api.datastore.DatastoreServiceFactory;
/*     */ import com.google.appengine.api.datastore.Entity;
/*     */ import com.google.appengine.api.datastore.FetchOptions;
/*     */ import com.google.appengine.api.datastore.FetchOptions.Builder;
/*     */ import com.google.appengine.api.datastore.Key;
/*     */ import com.google.appengine.api.datastore.PreparedQuery;
/*     */ import com.google.appengine.api.datastore.Query;
/*     */ import com.google.appengine.api.datastore.Query.FilterOperator;
/*     */ import com.google.appengine.api.datastore.Query.SortDirection;
/*     */ import com.google.appengine.api.users.User;
/*     */ import java.io.PrintStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Date;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.faithfarm.Util;
/*     */ import org.faithfarm.dataobjects.Person;
/*     */ import org.faithfarm.dataobjects.SearchParameter;
/*     */ import org.faithfarm.utilities.ApplicationTools;
/*     */ 
/*     */ public class ApplicationDao
/*     */   implements Serializable
/*     */ {
/*  31 */   private static final Logger logger = Logger.getLogger(Person.class
/*  32 */     .getCanonicalName());
/*     */ 
/*  34 */   private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
/*     */ 
/*     */   public static Entity getEntity(Key key) {
/*  37 */     return Util.findEntity(key);
/*     */   }
/*     */ 
/*     */   public static Entity getEntityByParent(String Kind, Key key) {
/*  41 */     Query query = new Query(Kind);
/*  42 */     query.setAncestor(key);
/*  43 */     return datastore.prepare(query).asSingleEntity();
/*     */   }
/*     */ 
/*     */   public static ArrayList<?> getEntities(String Kind, ArrayList parameters, String sortProperty, String sortDirection, String farmBase)
/*     */   {
/*  49 */     Iterable results = null;
/*  50 */     Query q = new Query(Kind);
/*     */ 
/*  52 */     if (parameters == null) parameters = new ArrayList();
/*     */ 
/*     */     try
/*     */     {
/*  56 */       for (int i = 0; i < parameters.size(); i++) {
/*  57 */         SearchParameter parameter = (SearchParameter)parameters.get(i);
/*  58 */         String property = parameter.getName();
/*  59 */         String value = parameter.getValue();
/*  60 */         Query.FilterOperator filterOperator = parameter.getOperator();
/*     */ 
/*  62 */         if (filterOperator == null)
/*  63 */           filterOperator = Query.FilterOperator.EQUAL;
/*  64 */         if ((value != null) && (!value.equals(""))) {
/*  65 */           if (filterOperator.equals(Query.FilterOperator.EQUAL)) {
/*  66 */             if (("itemNumber".equals(property)) || ("itemName".equals(property)) || ("firstName".equals(property)) || ("lastName".equals(property)) || ("middleInitial".equals(property))) {
/*  67 */               ArrayList valueList = new ArrayList();
/*  68 */               valueList.add(value);
/*  69 */               valueList.add(value.toUpperCase());
/*  70 */               valueList.add(value.toLowerCase());
/*  71 */               valueList.add(value.substring(0, 1).toUpperCase() + 
/*  72 */                 value.substring(1, value.length())
/*  73 */                 .toLowerCase());
/*  74 */               q.addFilter(property, Query.FilterOperator.IN, 
/*  75 */                 valueList);
/*     */             }
/*     */             else {
/*  78 */               q.addFilter(property, Query.FilterOperator.EQUAL, 
/*  79 */                 value);
/*     */             }
/*     */           }
/*  82 */           if (filterOperator.equals(Query.FilterOperator.NOT_EQUAL)) {
/*  83 */             q.addFilter(property, Query.FilterOperator.NOT_EQUAL, 
/*  84 */               value);
/*  85 */             q.addSort(property, Query.SortDirection.ASCENDING);
/*     */           }
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*  91 */       if (sortProperty != null) {
/*  92 */         if ((sortDirection == null) || (sortDirection.equals("")) || 
/*  93 */           (sortDirection.equals("ascending")))
/*  94 */           q.addSort(sortProperty, Query.SortDirection.ASCENDING);
/*     */         else
/*  96 */           q.addSort(sortProperty, Query.SortDirection.DESCENDING);
/*     */       }
/*  98 */       if ("Item".equals(Kind)) {
/*  99 */         q.addSort("itemName", Query.SortDirection.ASCENDING);
/*     */       }
/* 101 */       if ("Person".equals(Kind)) {
/* 102 */         q.addSort("personType", Query.SortDirection.ASCENDING);
/* 103 */         q.addSort("lastName", Query.SortDirection.ASCENDING);
/*     */       }
/*     */ 
/* 106 */       if ((!"Item".equals(Kind)) && (!"HelpDeskTicket".equals(Kind)))
/*     */       {
/* 109 */         if ("User".equals(Kind))
/* 110 */           q.addFilter("farm", Query.FilterOperator.EQUAL, farmBase);
/* 111 */         else if ("CallLog".equals(Kind)) {
/* 112 */           System.out.println("no farm");
/*     */         }
/* 114 */         else if ("Fort Lauderdale".equals(farmBase))
/* 115 */           q.addFilter("farmBase", Query.FilterOperator.IN, Arrays.asList(new String[] { farmBase, "" }));
/*     */         else
/* 117 */           q.addFilter("farmBase", Query.FilterOperator.EQUAL, farmBase);
/*     */       }
/* 119 */       PreparedQuery pq = datastore.prepare(q);
/* 120 */       int limit = 20000;
/* 121 */       if ("Item".equals(Kind))
/* 122 */         limit = 20;
/* 123 */       if ("Dispatch".equals(Kind)) {
/* 124 */         limit = 200;
/*     */       }
/* 126 */       results = pq.asIterable(FetchOptions.Builder.withLimit(limit)
/* 127 */         .chunkSize(1000));
/*     */     }
/*     */     catch (Exception e) {
/* 130 */       logger.log(Level.SEVERE, e.getMessage());
/* 131 */       logger.log(Level.INFO, q.toString());
/*     */     }
/*     */ 
/* 134 */     if (results == null) {
/* 135 */       return new ArrayList();
/*     */     }
/* 137 */     return ApplicationTools.convertIterableToList(results);
/*     */   }
/*     */ 
/*     */   public static ArrayList getEntities(String Kind, ArrayList parameters, String sortProperty, String sortDirection, String farmBase, int fetchSize)
/*     */   {
/* 143 */     if (parameters == null) parameters = new ArrayList();
/*     */ 
/* 145 */     Iterable results = null;
/* 146 */     Query q = new Query(Kind);
/*     */ 
/* 148 */     if (fetchSize == 0) {
/* 149 */       fetchSize = 200;
/*     */     }
/*     */     try
/*     */     {
/* 153 */       for (int i = 0; i < parameters.size(); i++) {
/* 154 */         SearchParameter parameter = (SearchParameter)parameters.get(i);
/* 155 */         String property = parameter.getName();
/* 156 */         String value = parameter.getValue();
/* 157 */         Query.FilterOperator filterOperator = parameter.getOperator();
/*     */ 
/* 159 */         if (filterOperator == null)
/* 160 */           filterOperator = Query.FilterOperator.EQUAL;
/* 161 */         if ((value != null) && (!value.equals(""))) {
/* 162 */           if (filterOperator.equals(Query.FilterOperator.EQUAL)) {
/* 163 */             if (("itemNumber".equals(property)) || ("itemName".equals(property)) || ("firstName".equals(property)) || ("lastName".equals(property)) || ("middleInitial".equals(property))) {
/* 164 */               ArrayList valueList = new ArrayList();
/* 165 */               valueList.add(value);
/* 166 */               valueList.add(value.toUpperCase());
/* 167 */               valueList.add(value.toLowerCase());
/* 168 */               valueList.add(value.substring(0, 1).toUpperCase() + 
/* 169 */                 value.substring(1, value.length())
/* 170 */                 .toLowerCase());
/* 171 */               q.addFilter(property, Query.FilterOperator.IN, 
/* 172 */                 valueList);
/*     */             }
/*     */             else {
/* 175 */               q.addFilter(property, Query.FilterOperator.EQUAL, 
/* 176 */                 value);
/*     */             }
/*     */           }
/* 179 */           if (filterOperator.equals(Query.FilterOperator.NOT_EQUAL)) {
/* 180 */             q.addFilter(property, Query.FilterOperator.NOT_EQUAL, 
/* 181 */               value);
/* 182 */             q.addSort(property, Query.SortDirection.ASCENDING);
/*     */           }
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 188 */       if (sortProperty != null) {
/* 189 */         if ((sortDirection == null) || (sortDirection.equals("")) || 
/* 190 */           (sortDirection.equals("ascending")))
/* 191 */           q.addSort(sortProperty, Query.SortDirection.ASCENDING);
/*     */         else
/* 193 */           q.addSort(sortProperty, Query.SortDirection.DESCENDING);
/*     */       }
/* 195 */       if ("Item".equals(Kind)) {
/* 196 */         q.addSort("itemName", Query.SortDirection.ASCENDING);
/*     */       }
/* 198 */       if ("Person".equals(Kind)) {
/* 199 */         q.addSort("personType", Query.SortDirection.ASCENDING);
/* 200 */         q.addSort("lastName", Query.SortDirection.ASCENDING);
/*     */       }
/*     */ 
/* 204 */       if (("Item".equals(Kind)) || ("HelpDeskTicket".equals(Kind))) {
/* 205 */         q.addFilter("farmLocation", Query.FilterOperator.EQUAL, farmBase);
/*     */       }
/* 207 */       else if ("Fort Lauderdale".equals(farmBase))
/* 208 */         q.addFilter("farmBase", Query.FilterOperator.IN, Arrays.asList(new String[] { farmBase, "" }));
/*     */       else {
/* 210 */         q.addFilter("farmBase", Query.FilterOperator.EQUAL, farmBase);
/*     */       }
/*     */ 
/* 214 */       PreparedQuery pq = datastore.prepare(q);
/*     */ 
/* 216 */       if (("Item".equals(Kind)) && (fetchSize != -1))
/* 217 */         fetchSize = 20;
/* 218 */       else if (("Item".equals(Kind)) && (fetchSize == -1)) {
/* 219 */         fetchSize = 2000;
/*     */       }
/* 221 */       results = pq.asIterable(FetchOptions.Builder.withLimit(fetchSize)
/* 222 */         .chunkSize(1000));
/*     */     }
/*     */     catch (Exception e) {
/* 225 */       logger.log(Level.SEVERE, e.getMessage());
/* 226 */       logger.log(Level.INFO, q.toString());
/*     */     }
/*     */ 
/* 229 */     if (results == null) {
/* 230 */       return new ArrayList();
/*     */     }
/* 232 */     return ApplicationTools.convertIterableToList(results);
/*     */   }
/*     */ 
/*     */   public static ArrayList getActivePendingResidentsIntakes(String farmBase)
/*     */   {
/* 237 */     Iterable results = null;
/* 238 */     Query q = new Query("Person");
/*     */     try
/*     */     {
/* 241 */       q.addFilter("personType", Query.FilterOperator.IN, 
/* 242 */         Arrays.asList(new String[] { 
/* 242 */         "Intake", "Resident", "Staff", "Employee", "Graduate" }));
/* 243 */       q.addFilter("personStatus", Query.FilterOperator.IN, 
/* 244 */         Arrays.asList(new String[] { 
/* 244 */         "Active", "Pending", "SLS", "Intern", "Omega School", "Omega Work" }));
/* 245 */       q.addFilter("farmBase", Query.FilterOperator.EQUAL, 
/* 246 */         farmBase);
/* 247 */       q.addSort("personType", Query.SortDirection.ASCENDING);
/* 248 */       q.addSort("lastName", Query.SortDirection.ASCENDING);
/*     */ 
/* 250 */       PreparedQuery pq = datastore.prepare(q);
/* 251 */       results = pq.asIterable(FetchOptions.Builder.withLimit(200)
/* 252 */         .chunkSize(200));
/*     */     }
/*     */     catch (Exception e) {
/* 255 */       logger.log(Level.SEVERE, e.getMessage());
/* 256 */       logger.log(Level.INFO, q.toString());
/*     */     }
/*     */ 
/* 259 */     if (results == null) {
/* 260 */       return new ArrayList();
/*     */     }
/* 262 */     return ApplicationTools.convertIterableToList(results);
/*     */   }
/*     */ 
/*     */   public static Entity createOrUpdateEntity(Entity Entity, User user)
/*     */   {
/* 268 */     String nickname = "";
/*     */ 
/* 270 */     if (user != null) {
/* 271 */       nickname = user.getEmail().toString();
/*     */     }
/* 273 */     if (Entity.getKey().getId() == 0L) {
/* 274 */       Entity.setProperty("createdBy", nickname);
/* 275 */       Entity.setProperty("creationDate", new Date());
/*     */     } else {
/* 277 */       Entity.setProperty("lastUpdatedBy", nickname);
/* 278 */       Entity.setProperty("lastUpdatedDate", new Date());
/*     */     }
/*     */ 
/* 281 */     Util.persistEntity(Entity);
/* 282 */     return Entity;
/*     */   }
/*     */ 
/*     */   public static void deleteEntity(Entity Entity) {
/* 286 */     Util.deleteEntity(Entity.getKey());
/*     */   }
/*     */ 
/*     */   public static void addPropertyToExistingEntities(String Kind, String propertyName, String propertyValue, User user)
/*     */   {
/* 291 */     ArrayList list = getEntities(Kind, new ArrayList(), null, null, null);
/* 292 */     for (int i = 0; i < list.size(); i++) {
/* 293 */       Entity e = (Entity)list.get(i);
/* 294 */       e.setProperty(propertyName, propertyValue);
/* 295 */       createOrUpdateEntity(e, user);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void addItemTypeToExistingEntities(User user, HttpServletRequest req)
/*     */   {
/* 301 */     ArrayList list = getEntities("Item", new ArrayList(), null, null, null);
/*     */ 
/* 303 */     int count = 0;
/* 304 */     for (int i = 0; i < list.size(); i++) {
/* 305 */       Entity e = (Entity)list.get(i);
/* 306 */       String name = "itemName";
/* 307 */       String value = e.getProperty("itemName").toString().toLowerCase();
/*     */ 
/* 309 */       if ((value.contains("bedding")) || (value.contains("bed")))
/* 310 */         e.setProperty("itemType", "Accessories/Bedding");
/* 311 */       if (value.contains("decor"))
/* 312 */         e.setProperty("itemType", "Accessories/Decorative");
/* 313 */       if (value.contains("throw"))
/* 314 */         e.setProperty("itemType", "Accessories/Throws");
/* 315 */       if (value.contains("pillow"))
/* 316 */         e.setProperty("itemType", "Accessories/Pillows");
/* 317 */       if (value.contains("armoire"))
/* 318 */         e.setProperty("itemType", "Armoire");
/* 319 */       if (value.contains("rack"))
/* 320 */         e.setProperty("itemType", "Bakers Rack");
/* 321 */       if (value.contains("stool"))
/* 322 */         e.setProperty("itemType", "Barstool");
/* 323 */       if (value.contains("bench"))
/* 324 */         e.setProperty("itemType", "Bench");
/* 325 */       if (value.contains("bunk"))
/* 326 */         e.setProperty("itemType", "Bunk Bed");
/* 327 */       if (value.contains("cart"))
/* 328 */         e.setProperty("itemType", "Cart");
/* 329 */       if (value.contains("chair"))
/* 330 */         e.setProperty("itemType", "Chair");
/* 331 */       if (value.contains("chest"))
/* 332 */         e.setProperty("itemType", "Chest");
/* 333 */       if (value.contains("credenza"))
/* 334 */         e.setProperty("itemType", "Credenza");
/* 335 */       if (value.contains("desk"))
/* 336 */         e.setProperty("itemType", "Desk");
/* 337 */       if (value.contains("divider"))
/* 338 */         e.setProperty("itemType", "Divider");
/* 339 */       if (value.contains("end table"))
/* 340 */         e.setProperty("itemType", "End Table");
/* 341 */       if (value.contains("enterain"))
/* 342 */         e.setProperty("itemType", "Entertainment Center");
/* 343 */       if ((value.contains("fb")) || (value.contains("footboard")))
/* 344 */         e.setProperty("itemType", "Footboard");
/* 345 */       if (value.contains("fireplace"))
/* 346 */         e.setProperty("itemType", "Fireplace");
/* 347 */       if ((value.contains("headboard")) || (value.contains("hb")))
/* 348 */         e.setProperty("itemType", "Headboard");
/* 349 */       if (value.contains("hutch"))
/* 350 */         e.setProperty("itemType", "Hutch");
/* 351 */       if (value.contains("lamp"))
/* 352 */         e.setProperty("itemType", "Lamp/Lighting");
/* 353 */       if (value.contains("mattress"))
/* 354 */         e.setProperty("itemType", "Mattress");
/* 355 */       if (value.contains("mirror"))
/* 356 */         e.setProperty("itemType", "Mirror");
/* 357 */       if ((value.contains("nightstand")) || (value.contains("night stand")))
/* 358 */         e.setProperty("itemType", "Nightstand");
/* 359 */       if (value.contains("office desk"))
/* 360 */         e.setProperty("itemType", "Office/Desk");
/* 361 */       if (value.contains("office chair"))
/* 362 */         e.setProperty("itemType", "Office/Chair");
/* 363 */       if (value.contains("otto"))
/* 364 */         e.setProperty("itemType", "Ottoman");
/* 365 */       if (value.contains("rail"))
/* 366 */         e.setProperty("itemType", "Rails");
/* 367 */       if ((value.contains("recliner")) || (value.contains("recl")))
/* 368 */         e.setProperty("itemType", "Recliner");
/* 369 */       if (value.contains("rug"))
/* 370 */         e.setProperty("itemType", "Rug");
/* 371 */       if (value.contains("server"))
/* 372 */         e.setProperty("itemType", "Server");
/* 373 */       if ((value.contains("couch")) || (value.contains("sofa")))
/* 374 */         e.setProperty("itemType", "Sofa/Couch");
/* 375 */       if (value.contains("love"))
/* 376 */         e.setProperty("itemType", "Sofa/Loveseat");
/* 377 */       if (value.contains("sectional"))
/* 378 */         e.setProperty("itemType", "Sofa/Sectional");
/* 379 */       if (value.contains("sleeper"))
/* 380 */         e.setProperty("itemType", "Sofa/Sleeper");
/* 381 */       if ((value.contains("storage")) || (value.contains("dresser")))
/* 382 */         e.setProperty("itemType", "Storage/Bedroom");
/* 383 */       if (value.contains("storage"))
/* 384 */         e.setProperty("itemType", "Storage/Cubes");
/* 385 */       if (value.contains("storage"))
/* 386 */         e.setProperty("itemType", "Storage/Dining Room");
/* 387 */       if (value.contains("storage"))
/* 388 */         e.setProperty("itemType", "Storage/Living Room");
/* 389 */       if (value.contains("storage"))
/* 390 */         e.setProperty("itemType", "Storage/Media");
/* 391 */       if (value.contains("storage"))
/* 392 */         e.setProperty("itemType", "Storage/Office");
/* 393 */       if ((value.contains("table")) || (value.contains("tbl")) || 
/* 394 */         (value.contains("dinette")))
/* 395 */         e.setProperty("itemType", "Table");
/* 396 */       if (value.contains("tv stand"))
/* 397 */         e.setProperty("itemType", "TV Stand");
/* 398 */       createOrUpdateEntity(e, user);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static ArrayList getEntitiesWithInClause(String Kind, String property, ArrayList parameters, String farmBase)
/*     */   {
/* 406 */     if (parameters == null) parameters = new ArrayList();
/*     */ 
/* 408 */     Iterable results = null;
/* 409 */     Query q = new Query(Kind);
/*     */ 
/* 411 */     if (("Item".equals(Kind)) || ("HelpDeskTicket".equals(Kind)))
/* 412 */       q.addFilter("farmLocation", Query.FilterOperator.EQUAL, farmBase);
/*     */     else {
/* 414 */       q.addFilter("farmBase", Query.FilterOperator.EQUAL, farmBase);
/*     */     }
/* 416 */     q.addFilter(property, Query.FilterOperator.IN, parameters);
/* 417 */     PreparedQuery pq = datastore.prepare(q);
/*     */ 
/* 419 */     int limit = 200;
/* 420 */     if ("Item".equals(Kind)) {
/* 421 */       limit = 20;
/*     */     }
/* 423 */     results = pq.asIterable(FetchOptions.Builder.withLimit(limit)
/* 424 */       .chunkSize(limit));
/*     */ 
/* 426 */     if (results == null) {
/* 427 */       return new ArrayList();
/*     */     }
/* 429 */     return ApplicationTools.convertIterableToList(results);
/*     */   }
/*     */ 
/*     */   public static ArrayList getEntitiesThatContain(String Kind, String property, String searcharg, ArrayList parameters, String farmBase)
/*     */   {
/* 439 */     if (parameters == null) parameters = new ArrayList();
/*     */ 
/* 441 */     Query q = new Query(Kind);
/* 442 */     if (("Item".equals(Kind)) || ("HelpDeskTicket".equals(Kind)))
/* 443 */       q.addFilter("farmLocation", Query.FilterOperator.EQUAL, farmBase);
/*     */     else
/* 445 */       q.addFilter("farmBase", Query.FilterOperator.EQUAL, farmBase);
/* 446 */     PreparedQuery pq = datastore.prepare(q);
/* 447 */     System.out.println(pq);
/*     */ 
/* 449 */     int fetchsize = 2000;
/* 450 */     if ("Item".equals(Kind)) {
/* 451 */       fetchsize = 20;
/*     */     }
/* 453 */     ArrayList results = ApplicationTools.convertIterableToList(pq.asIterable(FetchOptions.Builder.withLimit(fetchsize).chunkSize(fetchsize)));
/* 454 */     ArrayList newList = new ArrayList();
/*     */ 
/* 456 */     for (int i = 0; i < results.size(); i++) {
/* 457 */       Entity entity = (Entity)results.get(i);
/* 458 */       String value = (String)entity.getProperty(property);
/*     */ 
/* 460 */       if (value.contains(searcharg)) { System.out.println("match");
/* 461 */         newList.add(entity); }
/*     */     }
/* 463 */     return newList;
/*     */   }
/*     */ }

/* Location:           C:\development\workspace\faithfarmoss\WEB-INF\classes\
 * Qualified Name:     org.faithfarm.datawriters.ApplicationDao
 * JD-Core Version:    0.6.2
 */