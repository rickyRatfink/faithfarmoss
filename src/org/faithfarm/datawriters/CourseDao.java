/*     */ package org.faithfarm.datawriters;
/*     */ 
/*     */ /*     */ import java.io.File;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
import java.util.Iterator;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;

/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;

/*     */ import org.faithfarm.Util;
/*     */ import org.faithfarm.dataobjects.Person;
/*     */ import org.faithfarm.validators.InventoryValidator;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.SAXParseException;

import com.google.appengine.api.datastore.DatastoreService;
/*     */ import com.google.appengine.api.datastore.DatastoreServiceFactory;
/*     */ import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
/*     */ import com.google.appengine.api.datastore.Key;
/*     */ import com.google.appengine.api.datastore.KeyFactory;
/*     */ import com.google.appengine.api.datastore.Query;
/*     */ import com.google.appengine.api.users.User;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CourseDao
/*     */   implements Serializable
/*     */ {
/*  37 */   private static final Logger logger = Logger.getLogger(Person.class
/*  38 */     .getCanonicalName());
/*     */ 
/*  39 */   private static InventoryValidator validator = new InventoryValidator();
/*     */ 
/*  41 */   private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
/*  42 */   private static final PersonDao dao = new PersonDao();
/*  43 */   private static int size = 0;
/*  44 */   private static int pages = 0;
/*  45 */   private static String cursor = null;
/*     */ 
/*     */   public static Entity getCourse(String sId)
/*     */   {
/*  49 */     Long id = Long.valueOf(sId);
/*  50 */     Key key = KeyFactory.createKey("Course", id.longValue());
/*  51 */     return Util.findEntity(key);
/*     */   }
/*     */ 
/*     */   public static Iterable<Entity> getCourses(String courseName, String courseNumber, String courseDescription, String instructor, String assistantInstructor, String status, String sequence, String farmBase)
/*     */   {
/*  57 */     Iterable results = null;
/*     */     try
/*     */     {
/*  60 */       Query query = new Query("Course").addSort("sequence", Query.SortDirection.ASCENDING);
/*  61 */       if ((courseNumber != null) && (!courseNumber.equals("")))
/*  62 */         query.addFilter("number", Query.FilterOperator.EQUAL, courseNumber);
/*  63 */       if ((courseName != null) && (!courseName.equals("")))
/*  64 */         query.addFilter("title", Query.FilterOperator.EQUAL, courseName);
/*  65 */       if ((courseDescription != null) && (!courseDescription.equals("")))
/*  66 */         query.addFilter("description", Query.FilterOperator.EQUAL, courseDescription);
/*  67 */       if ((instructor != null) && (!instructor.equals("")))
/*  68 */         query.addFilter("instructor", Query.FilterOperator.EQUAL, instructor);
/*  69 */       if ((assistantInstructor != assistantInstructor) && (!assistantInstructor.equals("")))
/*  70 */         query.addFilter("assistantInstructor", Query.FilterOperator.EQUAL, assistantInstructor);
/*  71 */       if ((status != null) && (!status.equals("")))
/*  72 */         query.addFilter("status", Query.FilterOperator.EQUAL, status);
/*  73 */       if ((sequence != null) && (!sequence.equals("")))
/*  74 */         query.addFilter("sequence", Query.FilterOperator.EQUAL, sequence);
/*  75 */       if ((farmBase != null) && (!farmBase.equals("")))
/*  76 */         query.addFilter("farmBase", Query.FilterOperator.EQUAL, farmBase);
/*  77 */       System.out.println(">" + query);
/*  78 */       results = datastore.prepare(query).asIterable(FetchOptions.Builder.withLimit(50));
/*     */     }
/*     */     catch (Exception e) {
/*  81 */       logger.log(Level.SEVERE, e.getStackTrace().toString());
/*     */     }
/*  83 */     return results;
/*     */   }
/*     */ 
/*     */   public static Entity getCourse(String courseName, String courseNumber, String courseDescription, String instructor, String assistantInstructor, String status, String sequence, String farmBase)
/*     */   {
/*  89 */     Entity result = null;
/*     */     try
/*     */     {
/*  92 */       Query query = new Query("Course").addSort("title", Query.SortDirection.ASCENDING);
/*  93 */       if ((courseNumber != null) && (!courseNumber.equals("")))
/*  94 */         query.addFilter("number", Query.FilterOperator.EQUAL, courseNumber);
/*  95 */       if ((courseName != null) && (!courseName.equals("")))
/*  96 */         query.addFilter("title", Query.FilterOperator.EQUAL, courseName);
/*  97 */       if ((courseDescription != null) && (!courseDescription.equals("")))
/*  98 */         query.addFilter("description", Query.FilterOperator.EQUAL, courseDescription);
/*  99 */       if ((instructor != null) && (!instructor.equals("")))
/* 100 */         query.addFilter("instructor", Query.FilterOperator.EQUAL, instructor);
/* 101 */       if ((assistantInstructor != assistantInstructor) && (!assistantInstructor.equals("")))
/* 102 */         query.addFilter("assistantInstructor", Query.FilterOperator.EQUAL, assistantInstructor);
/* 103 */       if ((status != null) && (!status.equals("")))
/* 104 */         query.addFilter("status", Query.FilterOperator.EQUAL, status);
/* 105 */       if ((sequence != null) && (!sequence.equals("")))
/* 106 */         query.addFilter("sequence", Query.FilterOperator.EQUAL, sequence);
/* 107 */       if ((farmBase != null) && (!farmBase.equals("")))
/* 108 */         query.addFilter("farmBase", Query.FilterOperator.EQUAL, farmBase);
/* 109 */       System.out.println(">" + query);
/* 110 */       result = datastore.prepare(query).asSingleEntity();
/*     */     }
/*     */     catch (Exception e) {
/* 113 */       logger.log(Level.SEVERE, e.getStackTrace().toString());
/*     */     }
/* 115 */     return result;
/*     */   }
/*     */   public static void createCourse(Entity Course) {
/* 118 */     Course.setProperty("creationDate", new Date());
/* 119 */     Course.setProperty("status", "Active");
/* 120 */     Util.persistEntity(Course);
/*     */   }
/*     */ 
/*     */   public static void deleteCourse(Entity Course) {
/* 124 */     Util.deleteEntity(Course.getKey());
/*     */   }
/*     */ 
/*     */   public Entity createUpdateEntity(Entity entity, User user, HttpServletRequest req)
/*     */   {
/* 129 */     NodeList propertyList = null;
/*     */     try
/*     */     {
/* 133 */       DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
/* 134 */       DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
/* 135 */       Document doc = docBuilder.parse(new File(entity.getKind() + ".xml"));
/*     */ 
/* 138 */       doc.getDocumentElement().normalize();
/*     */ 
/* 141 */       NodeList listOfEntities = doc.getElementsByTagName(entity.getKind());
/* 142 */       int totalEntities = listOfEntities.getLength();
/*     */ 
/* 144 */       for (int s = 0; s < listOfEntities.getLength(); s++)
/*     */       {
/* 147 */         Node entityNode = listOfEntities.item(s);
/* 148 */         if (entityNode.getNodeType() == 1)
/*     */         {
/* 151 */           Element firstElement = (Element)entityNode;
/*     */ 
/* 154 */           propertyList = firstElement.getElementsByTagName("property");
/* 155 */           for (int i = 0; i < propertyList.getLength(); i++) {
/* 156 */             Element propertyElement = (Element)propertyList.item(i);
/* 157 */             String propertyName = propertyElement.getAttribute("name");
/* 158 */             String editable = propertyElement.getAttribute("editable");
/* 159 */             String value = req.getParameter(propertyName);
/* 160 */             if (value == null)
/* 161 */               value = "";
/* 162 */             System.out.println(">" + editable);
/* 163 */             if ((!value.equals(entity.getProperty(propertyName))) && ("Y".equals(editable))) {
/* 164 */               entity.setProperty(propertyName, value);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 170 */       entity.setProperty("lastUpdatedBy", user.getEmail());
/* 171 */       entity.setProperty("lastUpdatedDate", new Date());
/* 172 */       Util.persistEntity(entity);
/*     */     }
/*     */     catch (SAXParseException err) {
/* 175 */       System.out.println("** Parsing error, line " + 
/* 176 */         err.getLineNumber() + ", uri " + err.getSystemId());
/* 177 */       System.out.println(" " + err.getMessage());
/*     */     }
/*     */     catch (SAXException e) {
/* 180 */       Exception x = e.getException();
/* 181 */       (x == null ? e : x).printStackTrace();
/*     */     }
/*     */     catch (Throwable t) {
/* 184 */       t.printStackTrace();
/*     */     }
/*     */ 
/* 187 */     return entity;
/*     */   }
/*     */ 
/*     */   public static boolean enrollStudent(String studentId, String sequence, String farmBase)
/*     */   {
/* 193 */     Iterable list = getCourses(null, null, null, null, null, "Active", sequence, farmBase);
/*     */ 
/* 195 */     boolean bUpdated = false;
/*     */ 
/* 197 */     //for (Entity result : list) {
			  for(Iterator i = list.iterator();i.hasNext();) {
				Entity result = (Entity)i.next();

			    String sSeq = (String)result.getProperty("sequence");
/* 199 */       String sFarm = (String)result.getProperty("farmBase");
/*     */ 
/* 205 */       if ((sequence.equals(sSeq)) && (farmBase.equals(sFarm))) {
/* 206 */         ArrayList roster = (ArrayList)result.getProperty("roster");
/* 207 */         roster.add(studentId);
/* 208 */         result.setProperty("roster", roster);
/* 209 */         createCourse(result);
/* 210 */         bUpdated = true;
/*     */       }
/*     */     }
/* 213 */     return bUpdated;
/*     */   }
/*     */ 
/*     */   public static ArrayList<Entity> getRoster(Entity Course)
/*     */   {
/* 219 */     ArrayList list = (ArrayList)Course.getProperty("roster");
/* 220 */     ArrayList roster = new ArrayList();
/*     */     try
/*     */     {
/* 223 */       for (int i = 0; i < list.size(); i++) {
/* 224 */         String sId = (String)list.get(i);
/* 225 */         Long id = Long.valueOf(sId);
/* 226 */         Key key = KeyFactory.createKey("Person", id.longValue());
/* 227 */         Entity Person = Util.findEntity(key);
/* 228 */         roster.add(Person);
/*     */       }
/*     */     } catch (Exception e) {
/* 231 */       logger.log(Level.SEVERE, e.getStackTrace().toString());
/*     */     }
/*     */ 
/* 234 */     return roster;
/*     */   }
/*     */ 
/*     */   public static boolean rotateRoster(Entity Course)
/*     */   {
/* 244 */     boolean bUpdated = false;
/*     */ 
/* 262 */     return bUpdated;
/*     */   }
/*     */ 
/*     */   public static int getSize(Iterable<Entity> results) {
/* 266 */     int size = 0;
/* 267 */     for (Entity entity : results)
/* 268 */       size++;
/* 269 */     return size;
/*     */   }
/*     */ }

/* Location:           C:\development\workspace\faithfarmoss\WEB-INF\classes\
 * Qualified Name:     org.faithfarm.datawriters.CourseDao
 * JD-Core Version:    0.6.2
 */