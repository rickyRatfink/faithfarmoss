/*     */ package org.faithfarm;
/*     */ 
/*     */ import com.google.appengine.api.datastore.DatastoreService;
/*     */ import com.google.appengine.api.datastore.DatastoreServiceFactory;
/*     */ import com.google.appengine.api.datastore.Entity;
/*     */ import com.google.appengine.api.datastore.FetchOptions;
/*     */ import com.google.appengine.api.datastore.FetchOptions.Builder;
/*     */ import com.google.appengine.api.datastore.Key;
/*     */ import com.google.appengine.api.datastore.KeyFactory;
/*     */ import com.google.appengine.api.datastore.PreparedQuery;
/*     */ import com.google.appengine.api.datastore.Query;
/*     */ import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
/*     */ import com.google.appengine.api.datastore.Query.Filter;
/*     */ import com.google.appengine.api.datastore.Query.FilterOperator;
/*     */ import com.google.appengine.api.datastore.Query.FilterPredicate;
/*     */ import com.google.appengine.api.users.User;
/*     */ import com.google.appengine.api.users.UserService;
/*     */ import com.google.appengine.api.users.UserServiceFactory;

/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.Date;
import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;

/*     */ import javax.servlet.RequestDispatcher;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.ServletOutputStream;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;

/*     */ import org.apache.poi.ss.usermodel.Cell;
/*     */ import org.apache.poi.ss.usermodel.Row;
/*     */ import org.apache.poi.ss.usermodel.Sheet;
/*     */ import org.apache.poi.ss.usermodel.Workbook;
/*     */ import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/*     */ import org.faithfarm.dataobjects.SearchParameter;
/*     */ import org.faithfarm.datawriters.ApplicationDao;
/*     */ import org.faithfarm.datawriters.PersonDao;
/*     */ import org.faithfarm.utilities.ApplicationTools;
/*     */ import org.faithfarm.utilities.FFSession;
/*     */ import org.faithfarm.utilities.HTMLBuilder;
/*     */ import org.faithfarm.utilities.XMLParser;
/*     */ import org.faithfarm.validators.Validator;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ public class CustomerServlet extends BaseServlet
/*     */ {
/*  53 */   private static final Logger logger = Logger.getLogger(CustomerServlet.class
/*  54 */     .getCanonicalName());
/*     */ 
/*  55 */   private static final HTMLBuilder html = new HTMLBuilder();
/*  56 */   private static final XMLParser xml = new XMLParser();
/*  57 */   private static final PersonDao dao = new PersonDao();
/*     */ 
/*  59 */   private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
/*     */ 
/*  61 */   String homePhone = "";
/*  62 */   String cellPhone = "";
/*  63 */   String workPhone = "";
/*     */ 
/*     */   protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
/*     */   {
/*  67 */     super.doGet(req, resp);
/*     */ 
/*  69 */     if (FFSession.needsRedirect(req, resp)) {
/*  70 */       return;
/*     */     }
/*     */ 
/*  73 */     UserService userService = UserServiceFactory.getUserService();
/*  74 */     User user = userService.getCurrentUser();
/*     */ 
/*  79 */     String url = "/jsp/customer/results.jsp";
/*  80 */     String action = (String)req.getAttribute("action");
/*     */ 
/*  83 */     if (action == null) {
/*  84 */       action = req.getParameter("action");
/*     */     }
/*  86 */     if ("UpdateFarmBase".equals(action)) {
/*  87 */       Iterable results = null;
/*  88 */       Query q = new Query("Person");
/*  89 */       PreparedQuery pq = datastore.prepare(q);
/*  90 */       q.addFilter("farmBase", Query.FilterOperator.EQUAL, "");
/*  91 */       results = pq.asIterable(FetchOptions.Builder.withLimit(5000).chunkSize(5000));
/*  92 */       System.out.println("resutls=" + results);
/*  93 */       //for (Entity Person : results) {
	 		    for(Iterator i = results.iterator();i.hasNext();) {
			      Entity Person = (Entity)i.next();
/*  94 */         Person.setProperty("farmBase", "Fort Lauderdale");
/*  95 */         Util.persistEntity(Person);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 100 */     if ("Create".equals(action)) {
/* 101 */       Entity Person = xml.xmlToEntity("Person", req);
/* 102 */       Entity Address = xml.xmlToEntity("Address", req);
/* 103 */       NodeList xmlPerson = xml.getEntityProperties("Person", req);
/* 104 */       NodeList xmlAddress = xml.getEntityProperties("Address", req);
/* 105 */       req.setAttribute("Person", Person);
/* 106 */       req.setAttribute("Address", Address);
/* 107 */       req.setAttribute("xmlPerson", xmlPerson);
/* 108 */       req.setAttribute("xmlAddress", xmlAddress);
/* 109 */       url = "/jsp/customer/create.jsp";
/*     */     }
/* 111 */     else if ("Sort".equals(action)) {
/* 112 */       ArrayList list = null;
/*     */ 
/* 114 */       String direction = req.getParameter("sortDirection");
/* 115 */       if ("descending".equals(direction)) {
/* 116 */         list = ApplicationTools.convertIterableToList(PersonDao.sortPersons(
/* 117 */           req.getParameter("property"), "descending"));
/* 118 */         direction = "ascending";
/*     */       } else {
/* 120 */         list = ApplicationTools.convertIterableToList(PersonDao.sortPersons(
/* 121 */           req.getParameter("property"), "ascending"));
/* 122 */         direction = "descending";
/*     */       }
/*     */ 
/* 125 */       req.getSession().setAttribute("results", list);
/* 126 */       req.setAttribute("size", Integer.valueOf(PersonDao.getSize()));
/* 127 */       url = "/jsp/customer/results.jsp?sortDirection=" + direction;
/*     */     }
/* 134 */     else if ("Search".equals(action)) {
/* 135 */       ArrayList params = setSearchParameters(req);
/* 136 */       ArrayList results = ApplicationDao.getEntities("Person", params, 
/* 137 */         null, null, 
/* 138 */         (String)req.getSession().getAttribute("farmBase"), 2000);
/* 139 */       req.getSession().setAttribute("results", results);
/* 140 */       req.setAttribute("size", Integer.valueOf(results.size()));
/*     */     }
/* 142 */     else if ("Save".equals(action))
/*     */     {
/* 144 */       Entity Person = null;
/* 145 */       Entity Address = null;
/* 146 */       String personKey = req.getParameter("personKey");
/* 147 */       String addressKey = req.getParameter("addressKey");
/* 148 */       if (personKey != null) {
/* 149 */         Key key1 = KeyFactory.stringToKey(personKey);
/* 150 */         Key key2 = KeyFactory.stringToKey(addressKey);
/* 151 */         Person = ApplicationDao.getEntity(key1);
/* 152 */         Address = ApplicationDao.getEntity(key2);
/* 153 */         if (Address == null) Address = new Entity("Address", Person.getKey());
/* 154 */         Person = xml.updateEntity(Person, req);
/* 155 */         Address = xml.updateEntity(Address, req);
/*     */       }
/*     */       else {
/* 158 */         Person = xml.xmlToEntity("Person", req);
/* 159 */         Address = xml.xmlToEntity("Address", req);
/* 160 */         Person.setProperty("personType", "Donor");
/* 161 */         Person.setProperty("personStatus", "Active");
/*     */       }
/*     */ 
/* 164 */       NodeList xmlPerson = xml.getEntityProperties("Person", req);
/* 165 */       NodeList xmlAddress = xml.getEntityProperties("Address", req);
/* 166 */       req.setAttribute("xmlPerson", xmlPerson);
/* 167 */       req.setAttribute("xmlAddress", xmlAddress);
/* 168 */       req.setAttribute("Person", Person);
/* 169 */       req.setAttribute("Address", Address);
/* 170 */       if (validate(Address, Person, req)) {
/* 171 */         Person.setProperty("organization", html.cleanData(req.getParameter("organization")));
/* 172 */         Person.setProperty("personStatus", "Active");
/* 173 */         Person.setProperty("personType", "Donor");
/* 174 */         Person.setProperty("farmBase", (String)req.getSession().getAttribute("farmBase"));
/* 175 */         Entity p = ApplicationDao.createOrUpdateEntity(Person, user);
/* 176 */         if (addressKey == null) {
/* 177 */           Entity addy = xml.xmlChildToParentEntity("Address", Address, p);
/* 178 */           addy.setProperty("homePhone", this.homePhone);
/* 179 */           addy.setProperty("workPhone", this.workPhone);
/* 180 */           addy.setProperty("cellPhone", this.cellPhone);
/* 181 */           ApplicationDao.createOrUpdateEntity(addy, user);
/*     */         } else {
/* 183 */           Address.setProperty("homePhone", this.homePhone);
/* 184 */           Address.setProperty("workPhone", this.workPhone);
/* 185 */           Address.setProperty("cellPhone", this.cellPhone);
/*     */ 
/* 187 */           ApplicationDao.createOrUpdateEntity(Address, user);
/*     */         }
/*     */       } else {
/* 190 */         url = "/jsp/customer/create.jsp";
/*     */       }
/* 192 */     } else if ("Edit".equals(action)) {
/* 193 */       String sKey = req.getParameter("personKey");
/* 194 */       Key key = KeyFactory.stringToKey(sKey);
/* 195 */       Entity Person = ApplicationDao.getEntity(key);
/* 196 */       Entity Address = ApplicationDao.getEntityByParent("Address", Person.getKey());
/* 197 */       if (Address == null)
/* 198 */         Address = xml.xmlToChildEntity("Address", Person, req);
/* 199 */       NodeList xmlPerson = xml.getEntityProperties("Person", req);
/* 200 */       NodeList xmlAddress = xml.getEntityProperties("Address", req);
/* 201 */       req.setAttribute("xmlPerson", xmlPerson);
/* 202 */       req.setAttribute("xmlAddress", xmlAddress);
/* 203 */       req.setAttribute("Person", Person);
/* 204 */       req.setAttribute("Address", Address);
/* 205 */       url = "/jsp/customer/create.jsp";
/*     */     }
/* 208 */     else if ("Delete".equals(action)) {
/* 209 */       String sKey = req.getParameter("personKey");
/* 210 */       Key key = KeyFactory.stringToKey(sKey);
/* 211 */       Entity Person = ApplicationDao.getEntity(key);
/* 212 */       Entity Address = ApplicationDao.getEntityByParent("Address", Person.getKey());
/* 213 */       ApplicationDao.deleteEntity(Person);
/* 214 */       ApplicationDao.deleteEntity(Address);
/* 215 */       ArrayList params = setSearchParameters(req);
/* 216 */       ArrayList results = ApplicationDao.getEntities("Person", params, 
/* 217 */         null, null, 
/* 218 */         (String)req.getSession().getAttribute("farmBase"));
/*     */ 
/* 220 */       url = "/jsp/customer/results.jsp";
/* 221 */     } else if ("DonorList".equals(action)) {
/* 222 */       SimpleDateFormat fromFormat = new SimpleDateFormat("MM/dd/yyyy");
/* 223 */       SimpleDateFormat toFormat = new SimpleDateFormat("MM-dd-yyyy");
/* 224 */       SimpleDateFormat creationFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
/* 225 */       Date reportDate = null;
/* 226 */       Date endDate = null;
/* 227 */       DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
/*     */       try {
/* 229 */         String dateStr = req.getParameter("startDate");
/* 230 */         String endDateStr = req.getParameter("endDate");
/* 231 */         if ((dateStr == null) || (dateStr.equals(""))) {
/* 232 */           throw new IllegalArgumentException("Empty Start Date");
/*     */         }
/* 234 */         if ((endDateStr == null) || (endDateStr.equals(""))) {
/* 235 */           throw new IllegalArgumentException("Empty End Date");
/*     */         }
/* 237 */         reportDate = fromFormat.parse(dateStr);
/* 238 */         endDate = fromFormat.parse(endDateStr);
/* 239 */         Query q = new Query("Person");
/* 240 */         q.setFilter(Query.CompositeFilterOperator.and(new Query.Filter[] { 
/* 241 */           new Query.FilterPredicate("personType", Query.FilterOperator.EQUAL, "Customer"), 
/* 242 */           new Query.FilterPredicate("creationDate", Query.FilterOperator.GREATER_THAN_OR_EQUAL, reportDate) }));
/*     */ 
/* 246 */         PreparedQuery pq = datastore.prepare(q);
/* 247 */         List list = pq.asList(FetchOptions.Builder.withDefaults());
/* 248 */         Collections.sort(list, new Comparator() {
/*     */           public int compare(Entity a, Entity b) {
/* 250 */             String alastName = ((String)a.getProperty("lastName")).toLowerCase().trim();
/* 251 */             String afirstName = ((String)a.getProperty("firstName")).toLowerCase().trim();
/* 252 */             String blastName = ((String)b.getProperty("lastName")).toLowerCase().trim();
/* 253 */             String bfirstName = ((String)b.getProperty("firstName")).toLowerCase().trim();
/* 254 */             int lastCompare = alastName.compareTo(blastName);
/* 255 */             if (lastCompare == 0) {
/* 256 */               return afirstName.compareTo(bfirstName);
/*     */             }
/* 258 */             return lastCompare;
/*     */           }
/*     */

@Override
public int compare(Object o1, Object o2) {
	// TODO Auto-generated method stub
	return 0;
}         });
/* 262 */         Workbook wb = new XSSFWorkbook();
/* 263 */         Sheet sheet = wb.createSheet("donor list");
/* 264 */         Row headerRow = sheet.createRow(0);
/* 265 */         headerRow.createCell(0).setCellValue("Last Name");
/* 266 */         headerRow.createCell(1).setCellValue("First Name");
/* 267 */         headerRow.createCell(2).setCellValue("Prefix");
/* 268 */         headerRow.createCell(3).setCellValue("Company");
/* 269 */         headerRow.createCell(4).setCellValue("Address Line 1");
/* 270 */         headerRow.createCell(5).setCellValue("Address Line 2");
/* 271 */         headerRow.createCell(6).setCellValue("City");
/* 272 */         headerRow.createCell(7).setCellValue("State");
/* 273 */         headerRow.createCell(8).setCellValue("Zip");
/* 274 */         headerRow.createCell(9).setCellValue("Phone");
/* 275 */         int i = 0; for (int rownum = 0; i < list.size(); i++) {
/* 276 */           Entity person = (Entity)list.get(i);
/* 277 */           Date creationDate = null;
/*     */           try {
/* 279 */             creationDate = creationFormat.parse(person.getProperty("creationDate").toString());
/*     */           } catch (ParseException e) {
/* 281 */             logger.log(Level.SEVERE, e.getMessage());
/*     */           }
/* 283 */           if ((creationDate == null) || (!creationDate.after(endDate)))
/*     */           {
/* 286 */             rownum++;
/* 287 */             Query q2 = new Query("Address");
/* 288 */             q2.setAncestor(person.getKey());
/* 289 */             List results = datastore.prepare(q2).asList(FetchOptions.Builder.withLimit(1));
/* 290 */             Entity address = (Entity)results.get(0);
/* 291 */             String phoneNum = (String)address.getProperty("workPhone");
/* 292 */             if (((String)address.getProperty("homePhone")).length() >= phoneNum.length()) {
/* 293 */               phoneNum = (String)address.getProperty("homePhone");
/*     */             }
/* 295 */             if (((String)address.getProperty("cellPhone")).length() >= phoneNum.length()) {
/* 296 */               phoneNum = (String)address.getProperty("cellPhone");
/*     */             }
/* 298 */             Row newRow = sheet.createRow(rownum);
/* 299 */             newRow.createCell(0).setCellValue(((String)person.getProperty("lastName")).toUpperCase());
/* 300 */             newRow.createCell(1).setCellValue(((String)person.getProperty("firstName")).toUpperCase());
/* 301 */             newRow.createCell(2).setCellValue("");
/* 302 */             newRow.createCell(3).setCellValue("");
/* 303 */             newRow.createCell(4).setCellValue((String)address.getProperty("addressLine1"));
/* 304 */             newRow.createCell(5).setCellValue((String)address.getProperty("addressLine2"));
/* 305 */             newRow.createCell(6).setCellValue((String)address.getProperty("city"));
/* 306 */             newRow.createCell(7).setCellValue((String)address.getProperty("state"));
/* 307 */             newRow.createCell(8).setCellValue((String)address.getProperty("zipcode"));
/* 308 */             newRow.createCell(9).setCellValue(phoneNum);
/*     */           }
/*     */         }
/* 310 */         ServletOutputStream os = resp.getOutputStream();
/* 311 */         String fileName = "donor-list-" + toFormat.format(new Date());
/* 312 */         resp.addHeader("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
/* 313 */         resp.addHeader("Content-Disposition", "attachment; filename=" + fileName + ".xlsx");
/* 314 */         wb.write(os);
/* 315 */         return;
/*     */       } catch (Exception e) {
/* 317 */         StackTraceElement[] elements = e.getStackTrace();
/* 318 */         StringBuilder strBuilder = new StringBuilder();
/* 319 */         for (int x = 0; x < elements.length; x++) {
/* 320 */           strBuilder.append(elements[x].toString() + "\n");
/*     */         }
/* 322 */         logger.severe(strBuilder.toString());
/* 323 */         url = "/jsp/reports/donor/error.jsp";
/* 324 */         req.setAttribute("err", e.getMessage());
/*     */       }
/*     */     }
/* 327 */     req.getRequestDispatcher(req.getContextPath() + url).forward(req, resp);
/*     */   }
/*     */ 
/*     */   protected void doPost(HttpServletRequest req, HttpServletResponse resp)
/*     */     throws ServletException, IOException
/*     */   {
/* 338 */     doGet(req, resp);
/*     */   }
/*     */ 
/*     */   private boolean validate(Entity Person, Entity Address, HttpServletRequest req)
/*     */   {
/* 343 */     Validator validator = new Validator();
/* 344 */     ArrayList messages = new ArrayList();
/*     */ 
/* 346 */     messages = validator.validateRequired("firstname", req.getParameter("firstName"), messages);
/* 347 */     messages = validator.validateRequired("lastname", req.getParameter("lastName"), messages);
/* 348 */     messages = validator.validateRequired("address line 1", req.getParameter("addressLine1"), messages);
/* 349 */     messages = validator.validateRequired("city", req.getParameter("city"), messages);
/* 350 */     messages = validator.validateRequired("state", req.getParameter("state"), messages);
/* 351 */     messages = validator.validateRequired("zipcode", req.getParameter("zipcode"), messages);
/* 352 */     messages = validator.validateEmail(req.getParameter("email"), messages);
/*     */ 
/* 354 */     String homePhone1 = html.cleanData(req.getParameter("homePhone_areacode"));
/* 355 */     String homePhone2 = html.cleanData(req.getParameter("homePhone_first3"));
/* 356 */     String homePhone3 = html.cleanData(req.getParameter("homePhone_last4"));
/* 357 */     this.homePhone = 
/* 358 */       ("(" + homePhone1 + ")" + homePhone2 + "-" + 
/* 358 */       homePhone3);
/* 359 */     messages = validator.validateRequired("home phone", this.homePhone, messages);
/*     */ 
/* 361 */     String cellPhone1 = html.cleanData(req.getParameter("cellPhone_areacode"));
/* 362 */     String cellPhone2 = html.cleanData(req.getParameter("cellPhone_first3"));
/* 363 */     String cellPhone3 = html.cleanData(req.getParameter("cellPhone_last4"));
/* 364 */     this.cellPhone = 
/* 365 */       ("(" + cellPhone1 + ")" + cellPhone2 + "-" + 
/* 365 */       cellPhone3);
/*     */ 
/* 367 */     String workPhone1 = html.cleanData(req.getParameter("workPhone_areacode"));
/* 368 */     String workPhone2 = html.cleanData(req.getParameter("workPhone_first3"));
/* 369 */     String workPhone3 = html.cleanData(req.getParameter("workPhone_last4"));
/* 370 */     this.workPhone = 
/* 371 */       ("(" + workPhone1 + ")" + workPhone2 + "-" + 
/* 371 */       workPhone3);
/*     */ 
/* 373 */     if (messages.size() > 0) {
/* 374 */       req.setAttribute("messages", messages);
/* 375 */       return false;
/*     */     }
/* 377 */     return true;
/*     */   }
/*     */ 
/*     */   private ArrayList setSearchParameters(HttpServletRequest req) {
/* 381 */     ArrayList parameters = new ArrayList();
/* 382 */     parameters.add(new SearchParameter("firstName", html.cleanData(req
/* 383 */       .getParameter("firstName")), Query.FilterOperator.EQUAL));
/* 384 */     parameters.add(new SearchParameter("middleInitial", html.cleanData(req
/* 385 */       .getParameter("middleInitial")), Query.FilterOperator.EQUAL));
/* 386 */     parameters.add(new SearchParameter("lastName", html.cleanData(req
/* 387 */       .getParameter("lastName")), Query.FilterOperator.EQUAL));
/* 388 */     parameters.add(new SearchParameter("personStatus", "Active", 
/* 389 */       Query.FilterOperator.EQUAL));
/* 390 */     parameters.add(new SearchParameter("personType", "Donor", 
/* 391 */       Query.FilterOperator.EQUAL));
/*     */ 
/* 393 */     return parameters;
/*     */   }
/*     */ }

/* Location:           C:\development\workspace\faithfarmoss\WEB-INF\classes\
 * Qualified Name:     org.faithfarm.CustomerServlet
 * JD-Core Version:    0.6.2
 */