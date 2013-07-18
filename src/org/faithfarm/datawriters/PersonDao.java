/*      */ package org.faithfarm.datawriters;
/*      */ 
/*      */ /*      */ import java.io.File;
/*      */ import java.io.Serializable;
/*      */ import java.security.MessageDigest;
/*      */ import java.text.ParseException;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Calendar;
/*      */ import java.util.Date;
import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.logging.Level;
/*      */ import java.util.logging.Logger;

/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.xml.parsers.DocumentBuilder;
/*      */ import javax.xml.parsers.DocumentBuilderFactory;

/*      */ import org.faithfarm.Util;
/*      */ import org.faithfarm.dataobjects.Address;
/*      */ import org.faithfarm.dataobjects.Person;
/*      */ import org.faithfarm.validators.InventoryValidator;
/*      */ import org.w3c.dom.Document;
/*      */ import org.w3c.dom.Element;
/*      */ import org.w3c.dom.Node;
/*      */ import org.w3c.dom.NodeList;
/*      */ import org.xml.sax.SAXException;
/*      */ import org.xml.sax.SAXParseException;

import com.google.appengine.api.datastore.DatastoreService;
/*      */ import com.google.appengine.api.datastore.DatastoreServiceFactory;
/*      */ import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
/*      */ import com.google.appengine.api.datastore.Key;
/*      */ import com.google.appengine.api.datastore.KeyFactory;
/*      */ import com.google.appengine.api.datastore.PreparedQuery;
/*      */ import com.google.appengine.api.datastore.Query;
/*      */ import com.google.appengine.api.memcache.Expiration;
/*      */ import com.google.appengine.api.memcache.MemcacheService;
/*      */ import com.google.appengine.api.memcache.MemcacheServiceFactory;
/*      */ import com.google.appengine.api.users.User;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class PersonDao
/*      */   implements Serializable
/*      */ {
/*   55 */   private static final Logger logger = Logger.getLogger(Person.class
/*   56 */     .getCanonicalName());
/*      */ 
/*   57 */   private static InventoryValidator validator = new InventoryValidator();
/*      */ 
/*   59 */   private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
/*   60 */   private static int size = 0;
/*   61 */   private static int pages = 0;
/*   62 */   private static String cursor = null;
/*      */ 
/*      */   public static Entity getSinglePerson(String sId) {
/*   65 */     Long id = Long.valueOf(sId);
/*   66 */     Key key = KeyFactory.createKey("Person", id.longValue());
/*   67 */     return Util.findEntity(key);
/*      */   }
/*      */ 
/*      */   public static Entity getSingleDispatch(Key key) {
/*   71 */     return Util.findEntity(key);
/*      */   }
/*      */ 
/*      */   public static Iterable<Entity> getAllDispatches(String confirmationNumber, String firstName, String middleInitial, String lastName, String zipcode, String status, String dispatchDate, String farmBase)
/*      */   {
/*   78 */     Iterable results = null;
/*      */     try
/*      */     {
/*   81 */       Query q = new Query("Dispatch").addSort("zipcode", 
/*   82 */         Query.SortDirection.ASCENDING);
/*      */ 
/*   85 */       if ((firstName != null) && (!firstName.equals(""))) {
/*   86 */         ArrayList list = new ArrayList();
/*   87 */         list.add(firstName.toLowerCase());
/*   88 */         list.add(firstName.toUpperCase());
/*   89 */         list.add(firstName.substring(0, 1).toUpperCase() + 
/*   90 */           firstName.substring(1, firstName.length()));
/*   91 */         q.addFilter("firstName", Query.FilterOperator.IN, list);
/*      */       }
/*   93 */       if ((middleInitial != null) && (!middleInitial.equals(""))) {
/*   94 */         ArrayList list = new ArrayList();
/*   95 */         list.add(middleInitial.toLowerCase());
/*   96 */         list.add(middleInitial.toUpperCase());
/*   97 */         list.add(middleInitial.substring(0, 1).toUpperCase() + 
/*   98 */           middleInitial.substring(1, middleInitial.length()));
/*   99 */         q.addFilter("middleInitial", Query.FilterOperator.IN, list);
/*      */       }
/*  101 */       if ((lastName != null) && (!lastName.equals(""))) {
/*  102 */         ArrayList list = new ArrayList();
/*  103 */         list.add(lastName.toLowerCase());
/*  104 */         list.add(lastName.toUpperCase());
/*  105 */         list.add(lastName.substring(0, 1).toUpperCase() + 
/*  106 */           lastName.substring(1, lastName.length()));
/*  107 */         q.addFilter("lastName", Query.FilterOperator.IN, list);
/*      */       }
/*  109 */       if ((dispatchDate != null) && (!dispatchDate.equals("")))
/*  110 */         q.addFilter("dispatchDate", Query.FilterOperator.EQUAL, dispatchDate);
/*  111 */       if ((status != null) && (!status.equals("")))
/*  112 */         q.addFilter("status", Query.FilterOperator.EQUAL, status);
/*  113 */       if ((zipcode != null) && (!zipcode.equals("")))
/*  114 */         q.addFilter("zipcode", Query.FilterOperator.EQUAL, zipcode);
/*  115 */       if ((farmBase != null) && (!farmBase.equals(""))) {
/*  116 */         q.addFilter("farmBase", Query.FilterOperator.EQUAL, farmBase);
/*      */       }
/*  118 */       results = datastore.prepare(q).asIterable(
/*  119 */         FetchOptions.Builder.withLimit(500));
/*      */     }
/*      */     catch (Exception e) {
/*  122 */       logger.log(Level.SEVERE, e.getStackTrace().toString());
/*      */     }
/*      */ 
/*  125 */     return results;
/*      */   }
/*      */ 
/*      */   public static int countEntities(Iterable<Entity> list) {
/*  129 */     int count = 0;
/*  130 */     for (Entity entity : list)
/*  131 */       count++;
/*  132 */     return count;
/*      */   }
/*      */ 
/*      */   public static ArrayList<Entity> getFinablePersons(String farm) {
/*  136 */     ArrayList finable = new ArrayList();
/*  137 */     Query query = new Query("Person");
/*  138 */     query.addFilter("farmBase", Query.FilterOperator.EQUAL, farm);
/*  139 */     query.addFilter("personStatus", Query.FilterOperator.IN, 
/*  140 */       Arrays.asList(new String[] { 
/*  140 */       "Active", "SLS", "Omega School", "Omega Work" }));
/*  141 */     query.addSort("lastName", Query.SortDirection.ASCENDING);
/*  142 */     Iterable thePersons = datastore.prepare(query).asIterable();
/*  143 */     //for (Entity person : thePersons) {
/*  144 */     for(Iterator i = thePersons.iterator();i.hasNext();) {
					Entity person = (Entity)i.next();
					finable.add(person);
/*      */     }
/*  146 */     return finable;
/*      */   }
/*      */ 
/*      */   public static List<Entity> getAllPersonsCached(String firstName, String lastName, String middleInitial, String personType, String status, String ssn, String course, String farmBase)
/*      */   {
/*  154 */     MemcacheService cache = MemcacheServiceFactory.getMemcacheService();
/*      */ 
/*  156 */     List listResult = null;
/*      */     try
/*      */     {
/*  159 */       Query query = new Query("Person").addSort("lastName", 
/*  160 */         Query.SortDirection.ASCENDING);
/*  161 */       if ((firstName != null) && (!firstName.equals("")))
/*  162 */         query.addFilter("firstName", Query.FilterOperator.EQUAL, firstName);
/*  163 */       if ((lastName != null) && (!lastName.equals("")))
/*  164 */         query.addFilter("lastName", Query.FilterOperator.EQUAL, lastName);
/*  165 */       if ((middleInitial != null) && (!middleInitial.equals("")))
/*  166 */         query.addFilter("middleInitial", Query.FilterOperator.EQUAL, 
/*  167 */           middleInitial);
/*  168 */       if ((personType != null) && (!personType.equals("")))
/*  169 */         query.addFilter("personType", Query.FilterOperator.EQUAL, personType);
/*  170 */       if ((ssn != null) && (!ssn.equals("")))
/*  171 */         query.addFilter("ssn", Query.FilterOperator.EQUAL, ssn);
/*  172 */       if ((farmBase != null) && (!farmBase.equals("")))
/*  173 */         query.addFilter("farmBase", Query.FilterOperator.EQUAL, farmBase);
/*  174 */       if ((status != null) && (!status.equals("")))
/*  175 */         query.addFilter("personStatus", Query.FilterOperator.EQUAL, status);
/*  176 */       if ((course != null) && (!course.equals(""))) {
/*  177 */         query.addFilter("currentCourse", Query.FilterOperator.EQUAL, course);
/*      */       }
/*  179 */       byte[] queryBytes = query.toString().getBytes("UTF-8");
/*  180 */       MessageDigest md = MessageDigest.getInstance("SHA");
/*  181 */       md.update(queryBytes);
/*  182 */       byte[] messageDigest = md.digest();
/*  183 */       StringBuffer hexString = new StringBuffer();
/*  184 */       for (int i = 0; i < messageDigest.length; i++) {
/*  185 */         hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
/*      */       }
/*  187 */       listResult = (List)cache.get(hexString.toString());
/*  188 */       if (listResult == null) {
/*  189 */         PreparedQuery pq = datastore.prepare(query);
/*  190 */         listResult = pq.asList(FetchOptions.Builder.withChunkSize(200));
/*  191 */         cache.put(hexString.toString(), listResult, 
/*  192 */           Expiration.byDeltaSeconds(10));
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/*  196 */       logger.log(Level.SEVERE, e.getMessage());
/*      */     }
/*  198 */     return listResult;
/*      */   }
/*      */ 
/*      */   public static Iterable<Entity> getAllPersons(String firstName, String lastName, String middleInitial, String personType, String status, String ssn, String course, String farmBase)
/*      */   {
/*  206 */     MemcacheService cache = MemcacheServiceFactory.getMemcacheService();
/*      */ 
/*  208 */     Iterable results = null;
/*  209 */     List listResult = null;
/*      */     try
/*      */     {
/*  217 */       Query query = new Query("Person").addSort("lastName", 
/*  218 */         Query.SortDirection.ASCENDING);
/*      */ 
/*  220 */       if ((firstName != null) && (!firstName.equals(""))) {
/*  221 */         ArrayList list = new ArrayList();
/*  222 */         list.add(firstName.toLowerCase());
/*  223 */         list.add(firstName.toUpperCase());
/*  224 */         list.add(firstName.substring(0, 1).toUpperCase() + 
/*  225 */           firstName.substring(1, firstName.length()));
/*  226 */         query.addFilter("firstName", Query.FilterOperator.IN, list);
/*      */       }
/*  228 */       if ((middleInitial != null) && (!middleInitial.equals(""))) {
/*  229 */         ArrayList list = new ArrayList();
/*  230 */         list.add(middleInitial.toLowerCase());
/*  231 */         list.add(middleInitial.toUpperCase());
/*  232 */         list.add(middleInitial.substring(0, 1).toUpperCase() + 
/*  233 */           middleInitial.substring(1, middleInitial.length()));
/*  234 */         query.addFilter("middleInitial", Query.FilterOperator.IN, list);
/*      */       }
/*  236 */       if ((lastName != null) && (!lastName.equals(""))) {
/*  237 */         ArrayList list = new ArrayList();
/*  238 */         list.add(lastName.toLowerCase());
/*  239 */         list.add(lastName.toUpperCase());
/*  240 */         list.add(lastName.substring(0, 1).toUpperCase() + 
/*  241 */           lastName.substring(1, lastName.length()));
/*  242 */         query.addFilter("lastName", Query.FilterOperator.IN, list);
/*      */       }
/*  244 */       if ((personType != null) && (!personType.equals("")))
/*  245 */         query.addFilter("personType", Query.FilterOperator.EQUAL, personType);
/*  246 */       if ((ssn != null) && (!ssn.equals("")))
/*  247 */         query.addFilter("ssn", Query.FilterOperator.EQUAL, ssn);
/*  248 */       if ((farmBase != null) && (!farmBase.equals("")))
/*  249 */         query.addFilter("farmBase", Query.FilterOperator.EQUAL, farmBase);
/*  250 */       if ((status != null) && (!status.equals("")))
/*  251 */         query.addFilter("personStatus", Query.FilterOperator.EQUAL, status);
/*  252 */       if ((course != null) && (!course.equals(""))) {
/*  253 */         query.addFilter("currentCourse", Query.FilterOperator.EQUAL, course);
/*      */       }
/*  255 */       results = datastore.prepare(query).asIterable(
/*  256 */         FetchOptions.Builder.withLimit(120));
/*      */     }
/*      */     catch (Exception e) {
/*  259 */       logger.log(Level.SEVERE, e.getStackTrace().toString());
/*      */     }
/*  261 */     return results;
/*      */   }
/*      */ 
/*      */   public static Iterable<Entity> getRemovedPersons()
/*      */   {
/*  272 */     Iterable results = null;
/*      */     try
/*      */     {
/*  275 */       Query query = new Query("Person").addSort("lastName", 
/*  276 */         Query.SortDirection.ASCENDING);
/*  277 */       query.addFilter("personStatus", Query.FilterOperator.IN, Arrays.asList(new String[] { 
/*  278 */         "Dismissed", "Omega Work", "Left Voluntarily" }));
/*      */ 
/*  280 */       results = datastore.prepare(query).asIterable(
/*  281 */         FetchOptions.Builder.withLimit(500));
/*      */     } catch (Exception e) {
/*  283 */       logger.log(Level.SEVERE, e.getStackTrace().toString());
/*      */     }
/*  285 */     return results; } 
/*      */   public static Integer[] getCensusLeftByStatus(Iterable<Entity> allStudents, String thisStatus, String secondStatus) { return null; } // Byte code:
/*      */     //   0: invokestatic 388	java/util/Calendar:getInstance	()Ljava/util/Calendar;
/*      */     //   3: astore_3
/*      */     //   4: aload_3
/*      */     //   5: iconst_5
/*      */     //   6: iconst_1
/*      */     //   7: invokevirtual 393	java/util/Calendar:set	(II)V
/*      */     //   10: aload_3
/*      */     //   11: invokevirtual 397	java/util/Calendar:getTime	()Ljava/util/Date;
/*      */     //   14: astore 4
/*      */     //   16: aconst_null
/*      */     //   17: astore 5
/*      */     //   19: new 401	java/text/SimpleDateFormat
/*      */     //   22: dup
/*      */     //   23: ldc_w 403
/*      */     //   26: invokespecial 405	java/text/SimpleDateFormat:<init>	(Ljava/lang/String;)V
/*      */     //   29: astore 9
/*      */     //   31: iconst_4
/*      */     //   32: anewarray 325	java/lang/Integer
/*      */     //   35: astore 10
/*      */     //   37: iconst_0
/*      */     //   38: istore 11
/*      */     //   40: goto +15 -> 55
/*      */     //   43: aload 10
/*      */     //   45: iload 11
/*      */     //   47: iconst_0
/*      */     //   48: invokestatic 406	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
/*      */     //   51: aastore
/*      */     //   52: iinc 11 1
/*      */     //   55: iload 11
/*      */     //   57: aload 10
/*      */     //   59: arraylength
/*      */     //   60: if_icmplt -17 -> 43
/*      */     //   63: aconst_null
/*      */     //   64: astore 11
/*      */     //   66: aload_0
/*      */     //   67: invokeinterface 236 1 0
/*      */     //   72: astore 13
/*      */     //   74: goto +264 -> 338
/*      */     //   77: aload 13
/*      */     //   79: invokeinterface 240 1 0
/*      */     //   84: checkcast 246	com/google/appengine/api/datastore/Entity
/*      */     //   87: astore 12
/*      */     //   89: aload 12
/*      */     //   91: invokevirtual 409	com/google/appengine/api/datastore/Entity:getProperties	()Ljava/util/Map;
/*      */     //   94: astore 11
/*      */     //   96: aload 11
/*      */     //   98: ldc_w 258
/*      */     //   101: invokeinterface 413 2 0
/*      */     //   106: checkcast 121	java/lang/String
/*      */     //   109: astore 6
/*      */     //   111: aload 11
/*      */     //   113: ldc_w 416
/*      */     //   116: invokeinterface 413 2 0
/*      */     //   121: checkcast 121	java/lang/String
/*      */     //   124: astore 7
/*      */     //   126: aload 11
/*      */     //   128: ldc_w 418
/*      */     //   131: invokeinterface 413 2 0
/*      */     //   136: checkcast 121	java/lang/String
/*      */     //   139: astore 8
/*      */     //   141: aload 9
/*      */     //   143: aload 8
/*      */     //   145: invokevirtual 420	java/text/SimpleDateFormat:parse	(Ljava/lang/String;)Ljava/util/Date;
/*      */     //   148: astore 5
/*      */     //   150: goto +32 -> 182
/*      */     //   153: astore 14
/*      */     //   155: getstatic 35	org/faithfarm/datawriters/PersonDao:logger	Ljava/util/logging/Logger;
/*      */     //   158: getstatic 424	java/util/logging/Level:INFO	Ljava/util/logging/Level;
/*      */     //   161: aload 14
/*      */     //   163: invokevirtual 427	java/text/ParseException:getMessage	()Ljava/lang/String;
/*      */     //   166: invokevirtual 215	java/util/logging/Logger:log	(Ljava/util/logging/Level;Ljava/lang/String;)V
/*      */     //   169: aload 4
/*      */     //   171: astore 5
/*      */     //   173: goto +9 -> 182
/*      */     //   176: astore 14
/*      */     //   178: aload 4
/*      */     //   180: astore 5
/*      */     //   182: aload 6
/*      */     //   184: aload_1
/*      */     //   185: invokevirtual 120	java/lang/String:equals	(Ljava/lang/Object;)Z
/*      */     //   188: ifne +12 -> 200
/*      */     //   191: aload 6
/*      */     //   193: aload_2
/*      */     //   194: invokevirtual 120	java/lang/String:equals	(Ljava/lang/Object;)Z
/*      */     //   197: ifeq +141 -> 338
/*      */     //   200: aload 5
/*      */     //   202: aload 4
/*      */     //   204: invokevirtual 430	java/util/Date:after	(Ljava/util/Date;)Z
/*      */     //   207: ifeq +131 -> 338
/*      */     //   210: aload 7
/*      */     //   212: ldc_w 260
/*      */     //   215: invokevirtual 120	java/lang/String:equals	(Ljava/lang/Object;)Z
/*      */     //   218: ifeq +31 -> 249
/*      */     //   221: aload 10
/*      */     //   223: iconst_0
/*      */     //   224: dup2
/*      */     //   225: aaload
/*      */     //   226: invokevirtual 436	java/lang/Integer:intValue	()I
/*      */     //   229: iconst_1
/*      */     //   230: iadd
/*      */     //   231: invokestatic 406	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
/*      */     //   234: aastore
/*      */     //   235: aload 10
/*      */     //   237: iconst_3
/*      */     //   238: dup2
/*      */     //   239: aaload
/*      */     //   240: invokevirtual 436	java/lang/Integer:intValue	()I
/*      */     //   243: iconst_1
/*      */     //   244: iadd
/*      */     //   245: invokestatic 406	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
/*      */     //   248: aastore
/*      */     //   249: aload 7
/*      */     //   251: ldc_w 262
/*      */     //   254: invokevirtual 120	java/lang/String:equals	(Ljava/lang/Object;)Z
/*      */     //   257: ifeq +31 -> 288
/*      */     //   260: aload 10
/*      */     //   262: iconst_1
/*      */     //   263: dup2
/*      */     //   264: aaload
/*      */     //   265: invokevirtual 436	java/lang/Integer:intValue	()I
/*      */     //   268: iconst_1
/*      */     //   269: iadd
/*      */     //   270: invokestatic 406	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
/*      */     //   273: aastore
/*      */     //   274: aload 10
/*      */     //   276: iconst_3
/*      */     //   277: dup2
/*      */     //   278: aaload
/*      */     //   279: invokevirtual 436	java/lang/Integer:intValue	()I
/*      */     //   282: iconst_1
/*      */     //   283: iadd
/*      */     //   284: invokestatic 406	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
/*      */     //   287: aastore
/*      */     //   288: aload 7
/*      */     //   290: ldc_w 266
/*      */     //   293: invokevirtual 120	java/lang/String:equals	(Ljava/lang/Object;)Z
/*      */     //   296: ifne +14 -> 310
/*      */     //   299: aload 7
/*      */     //   301: ldc_w 264
/*      */     //   304: invokevirtual 120	java/lang/String:equals	(Ljava/lang/Object;)Z
/*      */     //   307: ifeq +31 -> 338
/*      */     //   310: aload 10
/*      */     //   312: iconst_2
/*      */     //   313: dup2
/*      */     //   314: aaload
/*      */     //   315: invokevirtual 436	java/lang/Integer:intValue	()I
/*      */     //   318: iconst_1
/*      */     //   319: iadd
/*      */     //   320: invokestatic 406	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
/*      */     //   323: aastore
/*      */     //   324: aload 10
/*      */     //   326: iconst_3
/*      */     //   327: dup2
/*      */     //   328: aaload
/*      */     //   329: invokevirtual 436	java/lang/Integer:intValue	()I
/*      */     //   332: iconst_1
/*      */     //   333: iadd
/*      */     //   334: invokestatic 406	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
/*      */     //   337: aastore
/*      */     //   338: aload 13
/*      */     //   340: invokeinterface 248 1 0
/*      */     //   345: ifne -268 -> 77
/*      */     //   348: aload 10
/*      */     //   350: areturn
/*      */     //
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   141	150	153	java/text/ParseException
/*      */     //   141	150	176	java/lang/NullPointerException } 
/*      */   public static Integer[] getCensusLeftByStatus(Iterable<Entity> allStudents, String thisStatus) { return null; }// Byte code:
/*      */     //   0: invokestatic 388	java/util/Calendar:getInstance	()Ljava/util/Calendar;
/*      */     //   3: astore_2
/*      */     //   4: aload_2
/*      */     //   5: iconst_5
/*      */     //   6: iconst_1
/*      */     //   7: invokevirtual 393	java/util/Calendar:set	(II)V
/*      */     //   10: aload_2
/*      */     //   11: invokevirtual 397	java/util/Calendar:getTime	()Ljava/util/Date;
/*      */     //   14: astore_3
/*      */     //   15: aconst_null
/*      */     //   16: astore 4
/*      */     //   18: new 401	java/text/SimpleDateFormat
/*      */     //   21: dup
/*      */     //   22: ldc_w 403
/*      */     //   25: invokespecial 405	java/text/SimpleDateFormat:<init>	(Ljava/lang/String;)V
/*      */     //   28: astore 8
/*      */     //   30: iconst_4
/*      */     //   31: anewarray 325	java/lang/Integer
/*      */     //   34: astore 9
/*      */     //   36: iconst_0
/*      */     //   37: istore 10
/*      */     //   39: goto +15 -> 54
/*      */     //   42: aload 9
/*      */     //   44: iload 10
/*      */     //   46: iconst_0
/*      */     //   47: invokestatic 406	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
/*      */     //   50: aastore
/*      */     //   51: iinc 10 1
/*      */     //   54: iload 10
/*      */     //   56: aload 9
/*      */     //   58: arraylength
/*      */     //   59: if_icmplt -17 -> 42
/*      */     //   62: aconst_null
/*      */     //   63: astore 10
/*      */     //   65: aload_0
/*      */     //   66: invokeinterface 236 1 0
/*      */     //   71: astore 12
/*      */     //   73: goto +252 -> 325
/*      */     //   76: aload 12
/*      */     //   78: invokeinterface 240 1 0
/*      */     //   83: checkcast 246	com/google/appengine/api/datastore/Entity
/*      */     //   86: astore 11
/*      */     //   88: aload 11
/*      */     //   90: invokevirtual 409	com/google/appengine/api/datastore/Entity:getProperties	()Ljava/util/Map;
/*      */     //   93: astore 10
/*      */     //   95: aload 10
/*      */     //   97: ldc_w 258
/*      */     //   100: invokeinterface 413 2 0
/*      */     //   105: checkcast 121	java/lang/String
/*      */     //   108: astore 5
/*      */     //   110: aload 10
/*      */     //   112: ldc_w 416
/*      */     //   115: invokeinterface 413 2 0
/*      */     //   120: checkcast 121	java/lang/String
/*      */     //   123: astore 6
/*      */     //   125: aload 10
/*      */     //   127: ldc_w 418
/*      */     //   130: invokeinterface 413 2 0
/*      */     //   135: checkcast 121	java/lang/String
/*      */     //   138: astore 7
/*      */     //   140: aload 8
/*      */     //   142: aload 7
/*      */     //   144: invokevirtual 420	java/text/SimpleDateFormat:parse	(Ljava/lang/String;)Ljava/util/Date;
/*      */     //   147: astore 4
/*      */     //   149: goto +30 -> 179
/*      */     //   152: astore 13
/*      */     //   154: getstatic 35	org/faithfarm/datawriters/PersonDao:logger	Ljava/util/logging/Logger;
/*      */     //   157: getstatic 424	java/util/logging/Level:INFO	Ljava/util/logging/Level;
/*      */     //   160: aload 13
/*      */     //   162: invokevirtual 427	java/text/ParseException:getMessage	()Ljava/lang/String;
/*      */     //   165: invokevirtual 215	java/util/logging/Logger:log	(Ljava/util/logging/Level;Ljava/lang/String;)V
/*      */     //   168: aload_3
/*      */     //   169: astore 4
/*      */     //   171: goto +8 -> 179
/*      */     //   174: astore 13
/*      */     //   176: aload_3
/*      */     //   177: astore 4
/*      */     //   179: aload 5
/*      */     //   181: aload_1
/*      */     //   182: invokevirtual 120	java/lang/String:equals	(Ljava/lang/Object;)Z
/*      */     //   185: ifeq +140 -> 325
/*      */     //   188: aload 4
/*      */     //   190: aload_3
/*      */     //   191: invokevirtual 430	java/util/Date:after	(Ljava/util/Date;)Z
/*      */     //   194: ifeq +131 -> 325
/*      */     //   197: aload 6
/*      */     //   199: ldc_w 260
/*      */     //   202: invokevirtual 120	java/lang/String:equals	(Ljava/lang/Object;)Z
/*      */     //   205: ifeq +31 -> 236
/*      */     //   208: aload 9
/*      */     //   210: iconst_0
/*      */     //   211: dup2
/*      */     //   212: aaload
/*      */     //   213: invokevirtual 436	java/lang/Integer:intValue	()I
/*      */     //   216: iconst_1
/*      */     //   217: iadd
/*      */     //   218: invokestatic 406	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
/*      */     //   221: aastore
/*      */     //   222: aload 9
/*      */     //   224: iconst_3
/*      */     //   225: dup2
/*      */     //   226: aaload
/*      */     //   227: invokevirtual 436	java/lang/Integer:intValue	()I
/*      */     //   230: iconst_1
/*      */     //   231: iadd
/*      */     //   232: invokestatic 406	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
/*      */     //   235: aastore
/*      */     //   236: aload 6
/*      */     //   238: ldc_w 262
/*      */     //   241: invokevirtual 120	java/lang/String:equals	(Ljava/lang/Object;)Z
/*      */     //   244: ifeq +31 -> 275
/*      */     //   247: aload 9
/*      */     //   249: iconst_1
/*      */     //   250: dup2
/*      */     //   251: aaload
/*      */     //   252: invokevirtual 436	java/lang/Integer:intValue	()I
/*      */     //   255: iconst_1
/*      */     //   256: iadd
/*      */     //   257: invokestatic 406	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
/*      */     //   260: aastore
/*      */     //   261: aload 9
/*      */     //   263: iconst_3
/*      */     //   264: dup2
/*      */     //   265: aaload
/*      */     //   266: invokevirtual 436	java/lang/Integer:intValue	()I
/*      */     //   269: iconst_1
/*      */     //   270: iadd
/*      */     //   271: invokestatic 406	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
/*      */     //   274: aastore
/*      */     //   275: aload 6
/*      */     //   277: ldc_w 266
/*      */     //   280: invokevirtual 120	java/lang/String:equals	(Ljava/lang/Object;)Z
/*      */     //   283: ifne +14 -> 297
/*      */     //   286: aload 6
/*      */     //   288: ldc_w 264
/*      */     //   291: invokevirtual 120	java/lang/String:equals	(Ljava/lang/Object;)Z
/*      */     //   294: ifeq +31 -> 325
/*      */     //   297: aload 9
/*      */     //   299: iconst_2
/*      */     //   300: dup2
/*      */     //   301: aaload
/*      */     //   302: invokevirtual 436	java/lang/Integer:intValue	()I
/*      */     //   305: iconst_1
/*      */     //   306: iadd
/*      */     //   307: invokestatic 406	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
/*      */     //   310: aastore
/*      */     //   311: aload 9
/*      */     //   313: iconst_3
/*      */     //   314: dup2
/*      */     //   315: aaload
/*      */     //   316: invokevirtual 436	java/lang/Integer:intValue	()I
/*      */     //   319: iconst_1
/*      */     //   320: iadd
/*      */     //   321: invokestatic 406	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
/*      */     //   324: aastore
/*      */     //   325: aload 12
/*      */     //   327: invokeinterface 248 1 0
/*      */     //   332: ifne -256 -> 76
/*      */     //   335: aload 9
/*      */     //   337: areturn
/*      */     //
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   140	149	152	java/text/ParseException
/*      */     //   140	149	174	java/lang/NullPointerException } 
/*  435 */   public static Integer[] getLeftTotal(Iterable<Entity> allStudents) { Calendar cal = Calendar.getInstance();
/*  436 */     cal.set(5, 1);
/*  437 */     Date theFirst = cal.getTime();
/*  438 */     Date studentEntry = null;
/*  439 */     Date lastStatusUpdated = null;
/*  440 */     String lastStatusUpdateStr = null;
/*  441 */     SimpleDateFormat df = new SimpleDateFormat(
/*  442 */       "EEE MMM dd HH:mm:ss zzz yyyy");
/*  443 */     Integer[] results = new Integer[4];
/*  444 */     for (int x = 0; x < results.length; x++) {
/*  445 */       results[x] = Integer.valueOf(0);
/*      */     }
/*      */ 
/*  448 */     Map properties = null;
/*      */ 
/*  452 */     for (Entity student : allStudents) {
/*  453 */       properties = student.getProperties();
/*  454 */       String theirStatus = (String)properties.get("personStatus");
/*  455 */       String lastStatus = (String)properties.get("lastPersonStatus");
/*  456 */       lastStatusUpdateStr = (String)properties.get("lastStatusUpdated");
/*      */ 
/*  458 */       if (lastStatusUpdateStr != null)
/*      */       {
/*      */         try
/*      */         {
/*  462 */           lastStatusUpdated = df.parse(lastStatusUpdateStr);
/*      */         } catch (ParseException e) {
/*  464 */           logger.log(Level.INFO, e.getMessage());
/*  465 */           lastStatusUpdated = theFirst;
/*      */         } catch (NullPointerException e) {
/*  467 */           lastStatusUpdated = theFirst;
/*      */         }
/*      */ 
/*  471 */         if (((theirStatus.equals("Walked Off")) || 
/*  472 */           (theirStatus.equals("Dismissed")) || 
/*  473 */           (theirStatus.equals("Left Voluntarily")) || 
/*  474 */           (theirStatus.equals("Graduated")) || 
/*  475 */           (theirStatus.equals("Omega School")) || 
/*  476 */           (theirStatus.equals("Omega Work")) || 
/*  477 */           (theirStatus
/*  477 */           .equals("SLS"))) && (lastStatusUpdated.after(theFirst))) {
/*  478 */           if (lastStatus.equals("Active"))
/*      */           {
/*      */             int tmp292_291 = 0;
/*      */             Integer[] tmp292_289 = results; tmp292_289[tmp292_291] = Integer.valueOf(tmp292_289[tmp292_291].intValue() + 1);
/*      */             int tmp306_305 = 3;
/*      */             Integer[] tmp306_303 = results; tmp306_303[tmp306_305] = Integer.valueOf(tmp306_303[tmp306_305].intValue() + 1);
/*      */           }
/*  482 */           if (lastStatus.equals("SLS"))
/*      */           {
/*      */             int tmp331_330 = 1;
/*      */             Integer[] tmp331_328 = results; tmp331_328[tmp331_330] = Integer.valueOf(tmp331_328[tmp331_330].intValue() + 1);
/*      */             int tmp345_344 = 3;
/*      */             Integer[] tmp345_342 = results; tmp345_342[tmp345_344] = Integer.valueOf(tmp345_342[tmp345_344].intValue() + 1);
/*      */           }
/*  486 */           if ((lastStatus.equals("Omega Work")) || 
/*  487 */             (lastStatus.equals("Omega School")))
/*      */           {
/*      */             int tmp381_380 = 2;
/*      */             Integer[] tmp381_378 = results; tmp381_378[tmp381_380] = Integer.valueOf(tmp381_378[tmp381_380].intValue() + 1);
/*      */             int tmp395_394 = 3;
/*      */             Integer[] tmp395_392 = results; tmp395_392[tmp395_394] = Integer.valueOf(tmp395_392[tmp395_394].intValue() + 1);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  495 */     return results;
/*      */   }
/*      */ 
/*      */   public static Integer[] getCensusNewTotal(Iterable<Entity> allStudents)
/*      */   {
/*  508 */     Calendar cal = Calendar.getInstance();
/*  509 */     cal.set(5, 1);
/*  510 */     cal.set(11, 0);
/*  511 */     cal.set(12, 0);
/*  512 */     cal.set(13, 0);
/*  513 */     cal.set(14, 0);
/*  514 */     Date theFirst = cal.getTime();
/*  515 */     Date studentEntry = null;
/*  516 */     String entryDate = null;
/*  517 */     SimpleDateFormat df = new SimpleDateFormat(
/*  518 */       "EEE MMM dd HH:mm:ss zzz yyyy");
/*  519 */     Integer[] results = new Integer[4];
/*  520 */     for (int x = 0; x < results.length; x++) {
/*  521 */       results[x] = Integer.valueOf(0);
/*      */     }
/*      */ 
/*  526 */     for (Entity student : allStudents) {
/*  527 */       entryDate = (String)student.getProperty("entryDate");
/*      */ 
/*  529 */       if (entryDate == null) {
/*  530 */         logger.log(Level.WARNING, 
/*  531 */           "entryDate was null in getCensusNewTotal");
/*      */       }
/*      */       else {
/*      */         try {
/*  535 */           studentEntry = df.parse(entryDate);
/*      */         } catch (ParseException e) {
/*  537 */           logger.log(Level.SEVERE, e.getMessage());
/*      */         }
/*      */ 
/*  540 */         if (!(studentEntry instanceof Date)) {
/*  541 */           logger.log(Level.WARNING, 
/*  542 */             "studentEntry was not an instanceof Date in getCensusNewTotal");
/*      */         }
/*  546 */         else if ((studentEntry.after(theFirst)) || 
/*  547 */           (studentEntry.compareTo(theFirst) == 0)) {
/*  548 */           String theirStatus = (String)student.getProperty("personStatus");
/*      */ 
/*  550 */           if (theirStatus == null) {
/*  551 */             theirStatus = "";
/*      */           }
/*      */ 
/*  554 */           if (theirStatus.equals("Active"))
/*      */           {
/*      */             int tmp249_248 = 0;
/*      */             Integer[] tmp249_246 = results; tmp249_246[tmp249_248] = Integer.valueOf(tmp249_246[tmp249_248].intValue() + 1);
/*      */             int tmp263_262 = 3;
/*      */             Integer[] tmp263_260 = results; tmp263_260[tmp263_262] = Integer.valueOf(tmp263_260[tmp263_262].intValue() + 1);
/*      */           }
/*  558 */           if (theirStatus.equals("SLS"))
/*      */           {
/*      */             int tmp288_287 = 1;
/*      */             Integer[] tmp288_285 = results; tmp288_285[tmp288_287] = Integer.valueOf(tmp288_285[tmp288_287].intValue() + 1);
/*      */             int tmp302_301 = 3;
/*      */             Integer[] tmp302_299 = results; tmp302_299[tmp302_301] = Integer.valueOf(tmp302_299[tmp302_301].intValue() + 1);
/*      */           }
/*  562 */           if ((theirStatus.equals("Omega Work")) || 
/*  563 */             (theirStatus.equals("Omega School")))
/*      */           {
/*      */             int tmp338_337 = 2;
/*      */             Integer[] tmp338_335 = results; tmp338_335[tmp338_337] = Integer.valueOf(tmp338_335[tmp338_337].intValue() + 1);
/*      */             int tmp352_351 = 3;
/*      */             Integer[] tmp352_349 = results; tmp352_349[tmp352_351] = Integer.valueOf(tmp352_349[tmp352_351].intValue() + 1);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*  569 */     return results;
/*      */   }
/*      */ 
/*      */   public static Integer[] getCensusBeginningTotal(Iterable<Entity> allStudents)
/*      */   {
/*  582 */     Calendar cal = Calendar.getInstance();
/*  583 */     cal.set(5, 1);
/*  584 */     Date theFirst = cal.getTime();
/*  585 */     Date studentEntry = null;
/*  586 */     SimpleDateFormat df = new SimpleDateFormat(
/*  587 */       "EEE MMM dd HH:mm:ss zzz yyyy");
/*  588 */     Integer[] results = new Integer[4];
/*  589 */     for (int x = 0; x < results.length; x++) {
/*  590 */       results[x] = Integer.valueOf(0);
/*      */     }
/*      */ 
/*  593 */     Map properties = null;
/*      */ 
/*  596 */     for (Entity student : allStudents) {
/*  597 */       properties = student.getProperties();
/*      */       try {
/*  599 */         studentEntry = df.parse((String)properties.get("entryDate"));
/*      */       } catch (ParseException e) {
/*  601 */         logger.log(Level.INFO, e.getMessage());
/*      */       }
/*  603 */       if (studentEntry.before(theFirst)) {
/*  604 */         String theirStatus = (String)properties.get("personStatus");
/*  605 */         if (theirStatus.equals("Active"))
/*      */         {
/*      */           int tmp169_168 = 0;
/*      */           Integer[] tmp169_166 = results; tmp169_166[tmp169_168] = Integer.valueOf(tmp169_166[tmp169_168].intValue() + 1);
/*      */           int tmp183_182 = 3;
/*      */           Integer[] tmp183_180 = results; tmp183_180[tmp183_182] = Integer.valueOf(tmp183_180[tmp183_182].intValue() + 1);
/*      */         }
/*  609 */         if (theirStatus.equals("SLS"))
/*      */         {
/*      */           int tmp208_207 = 1;
/*      */           Integer[] tmp208_205 = results; tmp208_205[tmp208_207] = Integer.valueOf(tmp208_205[tmp208_207].intValue() + 1);
/*      */           int tmp222_221 = 3;
/*      */           Integer[] tmp222_219 = results; tmp222_219[tmp222_221] = Integer.valueOf(tmp222_219[tmp222_221].intValue() + 1);
/*      */         }
/*  613 */         if ((theirStatus.equals("Omega School")) || 
/*  614 */           (theirStatus.equals("Omega Work")))
/*      */         {
/*      */           int tmp258_257 = 2;
/*      */           Integer[] tmp258_255 = results; tmp258_255[tmp258_257] = Integer.valueOf(tmp258_255[tmp258_257].intValue() + 1);
/*      */           int tmp272_271 = 3;
/*      */           Integer[] tmp272_269 = results; tmp272_269[tmp272_271] = Integer.valueOf(tmp272_269[tmp272_271].intValue() + 1);
/*      */         }
/*      */       }
/*      */     }
/*  620 */     return results;
/*      */   }
/*      */ 
/*      */   public static Entity getAddressByPerson(Entity person) {
/*  624 */     Query addressQuery = new Query("Address");
/*  625 */     addressQuery.setAncestor(person.getKey());
/*  626 */     return datastore.prepare(addressQuery).asSingleEntity();
/*      */   }
/*      */ 
/*      */   public static Entity getPersonMiscByPerson(Entity person) {
/*  630 */     Query personMiscQuery = new Query("PersonMisc");
/*  631 */     personMiscQuery.setAncestor(person.getKey());
/*  632 */     return datastore.prepare(personMiscQuery).asSingleEntity();
/*      */   }
/*      */ 
/*      */   public static Entity getJobSkillByPerson(Entity person) {
/*  636 */     Query jobSkillQuery = new Query("JobSkill");
/*  637 */     jobSkillQuery.setAncestor(person.getKey());
/*  638 */     return datastore.prepare(jobSkillQuery).asSingleEntity();
/*      */   }
/*      */ 
/*      */   public static Entity getSinglePerson(String firstName, String lastName, String middleInitial, String personType)
/*      */   {
/*  643 */     Query q = new Query("Person");
/*      */     try
/*      */     {
/*  646 */       if ((firstName != null) && (!firstName.equals(""))) {
/*  647 */         ArrayList list = new ArrayList();
/*  648 */         list.add(firstName.toLowerCase());
/*  649 */         list.add(firstName.toUpperCase());
/*  650 */         list.add(firstName.substring(0, 1).toUpperCase() + 
/*  651 */           firstName.substring(1, firstName.length()));
/*  652 */         q.addFilter("firstName", Query.FilterOperator.IN, list);
/*      */       }
/*  654 */       if ((middleInitial != null) && (!middleInitial.equals(""))) {
/*  655 */         ArrayList list = new ArrayList();
/*  656 */         list.add(middleInitial.toLowerCase());
/*  657 */         list.add(middleInitial.toUpperCase());
/*  658 */         list.add(middleInitial.substring(0, 1).toUpperCase() + 
/*  659 */           middleInitial.substring(1, middleInitial.length()));
/*  660 */         q.addFilter("middleInitial", Query.FilterOperator.IN, list);
/*      */       }
/*  662 */       if ((lastName != null) && (!lastName.equals(""))) {
/*  663 */         ArrayList list = new ArrayList();
/*  664 */         list.add(lastName.toLowerCase());
/*  665 */         list.add(lastName.toUpperCase());
/*  666 */         list.add(lastName.substring(0, 1).toUpperCase() + 
/*  667 */           lastName.substring(1, lastName.length()));
/*  668 */         q.addFilter("lastName", Query.FilterOperator.IN, list);
/*  669 */       }if ((personType != null) && (!personType.equals("")))
/*  670 */         q.addFilter("personType", Query.FilterOperator.EQUAL, personType);
/*      */     }
/*      */     catch (Exception e) {
/*  673 */       logger.log(Level.SEVERE, e.getStackTrace().toString());
/*      */     }
/*  675 */     PreparedQuery pq = datastore.prepare(q);
/*  676 */     setSize(pq.countEntities());
/*      */ 
/*  678 */     List results = Util.getDatastoreServiceInstance().prepare(q)
/*  679 */       .asList(FetchOptions.Builder.withDefaults());
/*  680 */     if (!results.isEmpty())
/*      */     {
/*  682 */       return (Entity)results.remove(0);
/*      */     }
/*      */ 
/*  685 */     return pq.asSingleEntity();
/*      */   }
/*      */ 
/*      */   public static Entity getUserRole(String email) {
/*  689 */     Query q = new Query("User");
/*      */     try
/*      */     {
/*  692 */       if ((email != null) && (!email.equals("")))
/*  693 */         q.addFilter("email", Query.FilterOperator.EQUAL, email);
/*      */     } catch (Exception e) {
/*  695 */       logger.log(Level.SEVERE, e.getStackTrace().toString());
/*      */     }
/*  697 */     PreparedQuery pq = datastore.prepare(q);
/*  698 */     setSize(pq.countEntities());
/*      */ 
/*  700 */     List results = Util.getDatastoreServiceInstance().prepare(q)
/*  701 */       .asList(FetchOptions.Builder.withDefaults());
/*      */ 
/*  703 */     if (!results.isEmpty()) {
/*  704 */       return (Entity)results.remove(0);
/*      */     }
/*      */ 
/*  707 */     return pq.asSingleEntity();
/*      */   }
/*      */ 
/*      */   public static Iterable<Entity> sortPersons(String property, String direction) {
/*  711 */     Query query = null;
/*  712 */     if (direction.equals("ascending"))
/*  713 */       query = new Query("Person").addSort(property, 
/*  714 */         Query.SortDirection.ASCENDING);
/*      */     else {
/*  716 */       query = new Query("Person").addSort(property, 
/*  717 */         Query.SortDirection.DESCENDING);
/*      */     }
/*  719 */     Iterable results = datastore.prepare(query).asIterable(
/*  720 */       FetchOptions.Builder.withLimit(500));
/*      */ 
/*  722 */     return results;
/*      */   }
/*      */ 
/*      */   public static Iterable<Entity> sort(String entity, String property, String direction)
/*      */   {
/*  743 */     Query query = null;
/*  744 */     if (direction.equals("ascending"))
/*  745 */       query = new Query(entity).addSort(property, 
/*  746 */         Query.SortDirection.ASCENDING);
/*      */     else {
/*  748 */       query = new Query(entity).addSort(property, 
/*  749 */         Query.SortDirection.DESCENDING);
/*      */     }
/*  751 */     Iterable results = datastore.prepare(query).asIterable(
/*  752 */       FetchOptions.Builder.withLimit(500));
/*      */ 
/*  754 */     return results;
/*      */   }
/*      */ 
/*      */   public static Iterable<Entity> sortDispatches(String property, String direction)
/*      */   {
/*  759 */     Query query = null;
/*  760 */     if (direction.equals("ascending"))
/*  761 */       query = new Query("Dispatch").addSort(property, 
/*  762 */         Query.SortDirection.ASCENDING);
/*      */     else {
/*  764 */       query = new Query("Dispatch").addSort(property, 
/*  765 */         Query.SortDirection.DESCENDING);
/*      */     }
/*  767 */     Iterable results = datastore.prepare(query).asIterable(
/*  768 */       FetchOptions.Builder.withLimit(500));
/*      */ 
/*  770 */     return results;
/*      */   }
/*      */ 
/*      */   public static void createPerson(Entity person) {
/*  774 */     person.setProperty("creationDate", new Date());
/*  775 */     Util.persistEntity(person);
/*      */   }
/*      */ 
/*      */   public static Entity createDispatchPerson(Entity person, String personType, User user)
/*      */   {
/*  781 */     person.setProperty("creationDate", new Date());
/*  782 */     person.setProperty("createdBy", user.getEmail());
/*  783 */     person.setProperty("personType", personType);
/*  784 */     Util.persistEntity(person);
/*  785 */     return person;
/*      */   }
/*      */ 
/*      */   public static void createSystemUser(User user, String email, String farm, String role)
/*      */   {
/*  790 */     Entity googleUser = new Entity("User");
/*  791 */     googleUser.setProperty("email", email);
/*  792 */     googleUser.setProperty("role", role);
/*  793 */     googleUser.setProperty("farm", farm);
/*  794 */     googleUser.setProperty("creationDate", new Date());
/*  795 */     googleUser.setProperty("createdBy", user.getEmail());
/*  796 */     Util.persistEntity(googleUser);
/*      */   }
/*      */ 
/*      */   public static void createAddress(Entity address) {
/*  800 */     address.setProperty("creationDate", new Date());
/*  801 */     Util.persistEntity(address);
/*      */   }
/*      */ 
/*      */   public static void deletePerson(Entity person)
/*      */   {
/*  806 */     Util.deleteEntity(person.getKey());
/*      */   }
/*      */ 
/*      */   public static void deleteAddress(Entity address)
/*      */   {
/*  816 */     Util.deleteEntity(address.getKey());
/*      */   }
/*      */ 
/*      */   public static Iterable<Entity> getAllDispatches() {
/*  820 */     Iterable results = null;
/*      */     try
/*      */     {
/*  823 */       Query query = new Query("Dispatch").addSort("zipcode", 
/*  824 */         Query.SortDirection.ASCENDING);
/*  825 */       results = datastore.prepare(query).asIterable(
/*  826 */         FetchOptions.Builder.withLimit(500));
/*      */     } catch (Exception e) {
/*  828 */       logger.log(Level.SEVERE, e.getStackTrace().toString());
/*      */     }
/*      */ 
/*  831 */     return results;
/*      */   }
/*      */ 
/*      */   public static void deleteDispatch(Entity dispatch)
/*      */   {
/*  842 */     Util.deleteEntity(dispatch.getKey());
/*      */   }
/*      */ 
/*      */   public static Entity createDispatch(Entity dispatch) {
/*  846 */     Util.persistEntity(dispatch);
/*  847 */     System.out.println("dispatch id=" + dispatch.getKey().getId());
/*  848 */     return dispatch;
/*      */   }
/*      */ 
/*      */   public static Entity updateDispatch(Entity dispatch, User user) {
/*  852 */     dispatch.setProperty("lastUpdated", new Date());
/*  853 */     dispatch.setProperty("updatedBy", user.getEmail());
/*  854 */     Util.persistEntity(dispatch);
/*  855 */     return dispatch;
/*      */   }
/*      */ 
/*      */   public Entity updateEntity(Entity entity, User user, HttpServletRequest req)
/*      */   {
/*  860 */     NodeList propertyList = null;
/*      */     try
/*      */     {
/*  864 */       DocumentBuilderFactory docBuilderFactory = 
/*  865 */         DocumentBuilderFactory.newInstance();
/*  866 */       DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
/*  867 */       Document doc = docBuilder
/*  868 */         .parse(new File(entity.getKind() + ".xml"));
/*      */ 
/*  871 */       doc.getDocumentElement().normalize();
/*  872 */       SimpleDateFormat fromFormat = new SimpleDateFormat("MM/dd/yyyy");
/*  873 */       NodeList listOfEntities = doc
/*  874 */         .getElementsByTagName(entity.getKind());
/*  875 */       int totalEntities = listOfEntities.getLength();
/*      */ 
/*  877 */       for (int s = 0; s < listOfEntities.getLength(); s++)
/*      */       {
/*  879 */         Node entityNode = listOfEntities.item(s);
/*  880 */         if (entityNode.getNodeType() == 1)
/*      */         {
/*  882 */           Element firstElement = (Element)entityNode;
/*      */ 
/*  885 */           propertyList = firstElement
/*  886 */             .getElementsByTagName("property");
/*  887 */           for (int i = 0; i < propertyList.getLength(); i++) {
/*  888 */             Element propertyElement = (Element)propertyList
/*  889 */               .item(i);
/*  890 */             String propertyName = propertyElement
/*  891 */               .getAttribute("name");
/*  892 */             String editable = propertyElement
/*  893 */               .getAttribute("editable");
/*  894 */             String value = req.getParameter(propertyName);
/*  895 */             if (value == null)
/*  896 */               value = "";
/*  897 */             if ((!value.equals(entity.getProperty(propertyName))) && 
/*  898 */               ("Y".equals(editable))) {
/*  899 */               if (propertyName.equals("entryDate"))
/*  900 */                 entity.setProperty(propertyName, fromFormat.parse(value).toString());
/*  901 */               else if (propertyName.equals("exitDate")) {
/*  902 */                 entity.setProperty(propertyName, fromFormat.parse(value).toString());
/*      */               }
/*      */               else {
/*  905 */                 entity.setProperty(propertyName, value);
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/*  912 */       entity.setProperty("lastUpdatedBy", user.getEmail());
/*  913 */       entity.setProperty("lastUpdatedDate", new Date());
/*  914 */       Util.persistEntity(entity);
/*      */     } catch (SAXParseException err) {
/*  916 */       System.out.println("** Parsing error, line " + 
/*  917 */         err.getLineNumber() + ", uri " + err.getSystemId());
/*  918 */       System.out.println(" " + err.getMessage());
/*      */     }
/*      */     catch (SAXException e) {
/*  921 */       Exception x = e.getException();
/*  922 */       (x == null ? e : x).printStackTrace();
/*      */     }
/*      */     catch (Throwable t) {
/*  925 */       t.printStackTrace();
/*      */     }
/*      */ 
/*  928 */     return entity;
/*      */   }
/*      */ 
/*      */   public static void createAddress(Address address, Entity Person)
/*      */   {
/*  933 */     Entity Address = new Entity("Address", Person.getKey());
/*      */ 
/*  935 */     Address.setProperty("addressLine1", address.getAddressLine1());
/*  936 */     Address.setProperty("addressLine2", address.getAddressLine2());
/*  937 */     Address.setProperty("city", address.getCity());
/*  938 */     Address.setProperty("state", address.getState());
/*  939 */     Address.setProperty("zipcode", address.getZipcode());
/*  940 */     Address.setProperty("homePhone", address.getHomePhone());
/*  941 */     Address.setProperty("workPhone", address.getWorkPhone());
/*  942 */     Address.setProperty("cellPhone", address.getCellPhone());
/*  943 */     Address.setProperty("email", address.getEmail());
/*  944 */     Address.setProperty("building", address.getBuilding());
/*  945 */     Address.setProperty("subdivision", address.getSubdivision());
/*  946 */     Address.setProperty("apartmentNumber", address.getApartmentNumber());
/*  947 */     Address.setProperty("majorIntersection", address.getMajorIntersection());
/*  948 */     Address.setProperty("addressType", address.getAddressType());
/*  949 */     Util.persistEntity(Address);
/*      */   }
/*      */ 
/*      */   public static void createAddress(Entity address, Entity Person)
/*      */   {
/*  955 */     Entity Address = new Entity("Address", Person.getKey());
/*      */ 
/*  957 */     Address.setProperty("addressLine1", address.getProperty("addressLine1"));
/*  958 */     Address.setProperty("addressLine2", address.getProperty("addressLine2"));
/*  959 */     Address.setProperty("city", address.getProperty("city"));
/*  960 */     Address.setProperty("state", address.getProperty("state"));
/*  961 */     Address.setProperty("zipcode", address.getProperty("zipcode"));
/*  962 */     Address.setProperty("homePhone", address.getProperty("homePhone"));
/*  963 */     Address.setProperty("workPhone", address.getProperty("workPhone"));
/*  964 */     Address.setProperty("cellPhone", address.getProperty("cellPhone"));
/*  965 */     Address.setProperty("email", address.getProperty("email"));
/*  966 */     Address.setProperty("majorIntersection", 
/*  967 */       address.getProperty("majorIntersection"));
/*  968 */     Address.setProperty("addressType", address.getProperty("addressType"));
/*  969 */     Address.setProperty("buildingNumber", 
/*  970 */       address.getProperty("buildingNumber"));
/*  971 */     Address.setProperty("majorIntersection", 
/*  972 */       address.getProperty("majorIntersection"));
/*  973 */     Address.setProperty("floorNumber", address.getProperty("floorNumber"));
/*  974 */     Address.setProperty("elevatorFlag", address.getProperty("elevatorFlag"));
/*  975 */     Address.setProperty("gatedCommunity", 
/*  976 */       address.getProperty("gatedCommunity"));
/*  977 */     Address.setProperty("streetSuffix", address.getProperty("streetSuffix"));
/*  978 */     Address.setProperty("gateInstructions", 
/*  979 */       address.getProperty("gateInstructions"));
/*  980 */     Address.setProperty("structureType", 
/*  981 */       address.getProperty("structureType"));
/*      */ 
/*  983 */     Util.persistEntity(Address);
/*      */   }
/*      */ 
/*      */   public static Entity createPerson(Person person)
/*      */   {
/*  988 */     Entity Person = new Entity("Person");
/*  989 */     Person.setProperty("farmBase", person.getFarmBase());
/*  990 */     Person.setProperty("firstName", person.getFirstName());
/*  991 */     Person.setProperty("lastName", person.getLastName());
/*  992 */     Person.setProperty("middleInitial", person.getMiddleInitial());
/*  993 */     Person.setProperty("suffix", person.getSuffix());
/*  994 */     Person.setProperty("dateOfBirth", person.getDateOfBirth());
/*  995 */     Person.setProperty("ssn", person.getSsn());
/*  996 */     Person.setProperty("age", person.getAge());
/*  997 */     Person.setProperty("height", person.getHeight());
/*  998 */     Person.setProperty("weight", person.getWeight());
/*  999 */     Person.setProperty("ethnicity", person.getEthnicity());
/* 1000 */     Person.setProperty("hairColor", person.getHairColor());
/* 1001 */     Person.setProperty("eyeColor", person.getEyeColor());
/* 1002 */     Person.setProperty("maritalStatus", person.getMaritalStatus());
/* 1003 */     Person.setProperty("educationLevel", person.getEducationLevel());
/* 1004 */     Person.setProperty("graduateFlag", person.getGraduateFlag());
/* 1005 */     Person.setProperty("englishSpeakFlag", person.getEnglishSpeakFlag());
/* 1006 */     Person.setProperty("englishReadFlag", person.getEnglishReadFlag());
/* 1007 */     Person.setProperty("homeLocation", person.getHomeLocation());
/* 1008 */     Person.setProperty("personType", person.getPersonType());
/* 1009 */     Person.setProperty("referredBy", person.getReferredBy());
/* 1010 */     Person.setProperty("referralPhone", person.getReferralPhone());
/* 1011 */     Person.setProperty("emergencyContact", person.getEmergencyContact());
/* 1012 */     Person.setProperty("emergencyPhone", person.getEmergencyPhone());
/* 1013 */     Person.setProperty("emergencyRelationship", 
/* 1014 */       person.getEmergencyRelationship());
/* 1015 */     Person.setProperty("creationDate", new Date());
/* 1016 */     Person.setProperty("createdBy", person.getCreatedBy());
/*      */ 
/* 1018 */     Util.persistEntity(Person);
/* 1019 */     createAddress(person.getAddress(), Person);
/* 1020 */     return Person;
/*      */   }
/*      */ 
/*      */   public static int getSize() {
/* 1024 */     return size;
/*      */   }
/*      */ 
/*      */   public static int getSize(Iterable<Entity> results) {
/* 1028 */     int size = 0;
/* 1029 */     for (Entity entity : results)
/* 1030 */       size++;
/* 1031 */     return size;
/*      */   }
/*      */ 
/*      */   public static void setSize(int size) {
/* 1035 */     size = size;
/*      */   }
/*      */ 
/*      */   public static int getPages() {
/* 1039 */     return pages;
/*      */   }
/*      */ 
/*      */   public static void setPages(int pages) {
/* 1043 */     pages = pages;
/*      */   }
/*      */ }

/* Location:           C:\development\workspace\faithfarmoss\WEB-INF\classes\
 * Qualified Name:     org.faithfarm.datawriters.PersonDao
 * JD-Core Version:    0.6.2
 */