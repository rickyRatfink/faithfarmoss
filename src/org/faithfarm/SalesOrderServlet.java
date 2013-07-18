/*     */ package org.faithfarm;
/*     */ 
/*     */ /*     */ import java.io.IOException;
/*     */ import java.text.DateFormat;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;

/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;

/*     */ import org.faithfarm.dataobjects.SearchParameter;
/*     */ import org.faithfarm.datawriters.ApplicationDao;
/*     */ import org.faithfarm.mail.Mailer;
/*     */ import org.faithfarm.utilities.HTMLBuilder;
/*     */ import org.faithfarm.utilities.XMLParser;
/*     */ import org.faithfarm.validators.Validator;
/*     */ import org.w3c.dom.NodeList;

import com.google.appengine.api.datastore.Entity;
/*     */ import com.google.appengine.api.datastore.Key;
/*     */ import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
/*     */ import com.google.appengine.api.users.User;
/*     */ import com.google.appengine.api.users.UserService;
/*     */ import com.google.appengine.api.users.UserServiceFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SalesOrderServlet extends BaseServlet
/*     */ {
/*  37 */   private static final Logger logger = Logger.getLogger(ControllerServlet.class.getCanonicalName());
/*  38 */   private static final HTMLBuilder html = new HTMLBuilder();
/*  39 */   private static final XMLParser xml = new XMLParser();
/*  40 */   private static final ApplicationDao dao = new ApplicationDao();
/*     */ 
/*     */   protected void doGet(HttpServletRequest req, HttpServletResponse resp)
/*     */     throws ServletException, IOException
/*     */   {
/*  52 */     UserService userService = UserServiceFactory.getUserService();
/*  53 */     User user = userService.getCurrentUser();
/*  54 */     String url = "/jsp/sale/index.jsp";
/*     */ 
/*  56 */     String inventoryLocation = (String)req.getSession().getAttribute("INVENTORY_LOCATION");
/*     */ 
/*  60 */     boolean bPrint = false;
/*  61 */     boolean bSalesOrderSaved = false;
/*  62 */     String SalesOrderKey = "";
/*  63 */     String SalesDispatchKey = "";
/*  64 */     String farmBase = (String)req.getSession().getAttribute("farmBase");
/*     */ 
/*  66 */     req.getSession().setAttribute("results", null);
/*     */ 
/*  68 */     super.doGet(req, resp);
/*  69 */     String action = req.getParameter("action");
/*  70 */     if (action == null) {
/*  71 */       action = req.getParameter("action");
/*     */     }
/*  73 */     logger.log(Level.INFO, "action=" + action);
/*     */ 
/*  75 */     NodeList xmlPerson = xml.getEntityProperties("Person", req);
/*  76 */     NodeList xmlAddress = xml.getEntityProperties("Address", req);
/*  77 */     NodeList xmlSalesOrder = xml.getEntityProperties("SalesOrder", req);
/*  78 */     NodeList xmlSalesDispatch = xml.getEntityProperties("SalesDispatch", 
/*  79 */       req);
/*     */ 
/*  81 */     req.getSession().setAttribute("xmlPerson", xmlPerson);
/*  82 */     req.getSession().setAttribute("xmlAddress", xmlAddress);
/*  83 */     req.getSession().setAttribute("xmlSalesOrder", xmlSalesOrder);
/*  84 */     req.getSession().setAttribute("xmlSalesDispatch", xmlSalesDispatch);
/*     */ 
/*  87 */     if (("Save & Print".equals(action)) || 
/*  88 */       ("Save Changes & Print".equals(action))) {
/*  89 */       bPrint = true;
/*     */     }
/*  91 */     if ("Create".equals(action))
/*     */     {
/*  97 */       req.getSession().setAttribute("Person", xml.xmlToEntity("Person", req));
/*  98 */       req.getSession().setAttribute("Address", xml.xmlToEntity("Address", req));
/*  99 */       Entity SalesOrder = xml.xmlToEntity("SalesOrder", req);
/* 100 */       Entity SalesDispatch = xml.xmlToEntity("SalesDispatch", req);
/* 101 */       SalesOrder.setProperty("status", "Pending");
/* 102 */       req.getSession().setAttribute("SalesOrder", SalesOrder);
/* 103 */       req.getSession().setAttribute("SalesDispatch", SalesDispatch);
/*     */ 
/* 105 */       xmlSalesDispatch = xml.getEntityProperties("SalesDispatch", req);
/* 106 */       xmlSalesOrder = xml.getEntityProperties("SalesOrder", req);
/* 107 */       xmlPerson = xml.getEntityProperties("Person", req);
/* 108 */       xmlAddress = xml.getEntityProperties("Address", req);
/* 109 */       req.setAttribute("xmlSalesDispatch", xmlSalesDispatch);
/* 110 */       req.setAttribute("xmlSalesOrder", xmlSalesOrder);
/* 111 */       req.setAttribute("xmlPerson", xmlPerson);
/* 112 */       req.setAttribute("xmlAddress", xmlAddress);
/*     */ 
/* 114 */       for (int i = 0; i < 10; i++)
/* 115 */         req.getSession().setAttribute("Item" + i, new Entity("Item"));
/* 116 */       url = "/jsp/sale/index.jsp";
/* 117 */     } else if ("Delete".equals(action))
/*     */     {
/* 122 */       String sKey = req.getParameter("salesOrderKey");
/* 123 */       Key k = KeyFactory.stringToKey(sKey);
/* 124 */       Entity SalesOrder = ApplicationDao.getEntity(k);
/* 125 */       Entity SalesDispatch = ApplicationDao.getEntityByParent("SalesDispatch", 
/* 126 */         SalesOrder.getKey());
/* 127 */       ApplicationDao.deleteEntity(SalesOrder);
/* 128 */       if (SalesDispatch != null) {
/* 129 */         ApplicationDao.deleteEntity(SalesDispatch);
/*     */       }
/* 131 */       url = "/jsp/sale/results.jsp";
/* 132 */     } else if ("New Customer".equals(action)) {
/* 133 */       xmlSalesDispatch = xml.getEntityProperties("SalesDispatch", req);
/* 134 */       xmlSalesOrder = xml.getEntityProperties("SalesOrder", req);
/* 135 */       xmlPerson = xml.getEntityProperties("Person", req);
/* 136 */       xmlAddress = xml.getEntityProperties("Address", req);
/* 137 */       req.getSession().setAttribute("xmlSalesDispatch", xmlSalesDispatch);
/* 138 */       req.getSession().setAttribute("xmlSalesOrder", xmlSalesOrder);
/* 139 */       req.getSession().setAttribute("xmlPerson", xmlPerson);
/* 140 */       req.getSession().setAttribute("xmlAddress", xmlAddress);
/* 141 */       req.getSession().setAttribute("Person", xml.xmlToEntity("Person", req));
/* 142 */       req.getSession().setAttribute("Address", xml.xmlToEntity("Address", req));
/* 143 */       Entity SalesOrder = xml.xmlToEntity("SalesOrder", req);
/* 144 */       Entity SalesDispatch = xml.xmlToEntity("SalesDispatch", req);
/* 145 */       SalesDispatch.setProperty("status", "Pending");
/* 146 */       SalesOrder.setProperty("status", "Pending");
/* 147 */       req.getSession().setAttribute("SalesOrder", SalesOrder);
/* 148 */       req.getSession().setAttribute("SalesDispatch", SalesDispatch);
/* 149 */       url = "/jsp/sale/index.jsp?edit=Y&new=Y";
/*     */     }
/* 151 */     else if ("Edit".equals(action)) {
/* 152 */       String sKey = req.getParameter("salesOrderKey");
/* 153 */       Key k1 = KeyFactory.stringToKey(sKey);
/* 154 */       Entity SalesOrder = ApplicationDao.getEntity(k1);
/* 155 */       Key key = SalesOrder.getParent();
/* 156 */       Entity Person = ApplicationDao.getEntity(key);
/* 157 */       Entity Address = ApplicationDao.getEntityByParent("Address", key);
/* 158 */       Entity SalesDispatch = ApplicationDao.getEntityByParent("SalesDispatch", 
/* 159 */         SalesOrder.getKey());
/* 160 */       if (Address == null)
/* 161 */         Address = xml.xmlToChildEntity("Address", Person, req);
/* 162 */       if (SalesDispatch == null) {
/* 163 */         SalesDispatch = xml.xmlToChildEntity("SalesDispatch", 
/* 164 */           SalesOrder, req);
/*     */       }
/* 166 */       req.getSession().setAttribute("Person", Person);
/* 167 */       req.getSession().setAttribute("Address", Address);
/* 168 */       req.getSession().setAttribute("SalesOrder", SalesOrder);
/* 169 */       req.getSession().setAttribute("SalesDispatch", SalesDispatch);
/*     */ 
/* 171 */       url = "/jsp/sale/index.jsp?edit=Y";
/* 172 */     } else if ("Filter".equals(action))
/*     */     {
/* 174 */       ArrayList parameters = new ArrayList();
/* 175 */       parameters = setSearchParameters(req);
/* 176 */       if (req.getSession().getAttribute("INVENTORY_LOCATION") != null) {
/* 177 */         parameters.add(new SearchParameter("location", (String)req.getSession().getAttribute("INVENTORY_LOCATION"), Query.FilterOperator.EQUAL));
/*     */       }
/* 179 */       ArrayList results = ApplicationDao.getEntities("SalesOrder", 
/* 180 */         parameters, null, null, farmBase);
/* 181 */       req.getSession().setAttribute("results", results);
/* 182 */       if (results != null)
/* 183 */         req.getSession().setAttribute("size", Integer.valueOf(results.size()));
/*     */       else {
/* 185 */         req.getSession().setAttribute("size", new Integer(0));
/*     */       }
/* 187 */       if (results.size() > 99) {
/* 188 */         req.setAttribute(
/* 189 */           "message", 
/* 190 */           "The maximum number of results returned was reached.  Please refine your search by using the filter above.");
/*     */       }
/* 192 */       url = "/jsp/sale/results.jsp";
/* 193 */     } else if ("Index".equals(action)) {
/* 194 */       Date today = new Date();
/* 195 */       SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
/* 196 */       ArrayList parameters = new ArrayList();
/* 197 */       SearchParameter parameter = new SearchParameter("salesOrderDate", 
/* 198 */         format.format(today), Query.FilterOperator.EQUAL);
/* 199 */       parameters.add(parameter);
/* 200 */       if (req.getSession().getAttribute("INVENTORY_LOCATION") != null) {
/* 201 */         parameters.add(new SearchParameter("location", (String)req.getSession().getAttribute("INVENTORY_LOCATION"), Query.FilterOperator.EQUAL));
/*     */       }
/* 203 */       ArrayList results = ApplicationDao.getEntities("SalesOrder", parameters, null, 
/* 204 */         null, farmBase);
/* 205 */       req.getSession().setAttribute("results", results);
/* 206 */       if (results != null)
/* 207 */         req.getSession().setAttribute("size", Integer.valueOf(results.size()));
/*     */       else {
/* 209 */         req.getSession().setAttribute("size", new Integer(0));
/*     */       }
/* 211 */       if (results.size() > 99) {
/* 212 */         req.setAttribute(
/* 213 */           "message", 
/* 214 */           "The maximum number of results returned was reached.  Please refine your search by using the filter above.");
/*     */       }
/* 216 */       url = "/jsp/sale/results.jsp";
/* 217 */     } else if ("Search".equals(action)) {
/* 218 */       ArrayList parameters = setSearchParameters(req);
/* 219 */       SearchParameter parameter = new SearchParameter("personType", 
/* 220 */         "Customer", Query.FilterOperator.EQUAL);
/* 221 */       parameters.add(parameter);
/* 222 */       ArrayList results = ApplicationDao.getEntities("Person", parameters, null, 
/* 223 */         null, (String)req.getSession().getAttribute("farmBase"));
/*     */ 
/* 225 */       req.setAttribute("results", results);
/* 226 */       if (results != null)
/* 227 */         req.getSession().setAttribute("size", Integer.valueOf(results.size()));
/*     */       else {
/* 229 */         req.getSession().setAttribute("size", new Integer(0));
/*     */       }
/*     */ 
/* 235 */       Entity Person = null;
/* 236 */       int count = results.size();
/*     */ 
/* 238 */       if (count == 0) {
/* 239 */         ArrayList messages = new ArrayList();
/* 240 */         messages.add("No customers matched the name you have entered. Proceed with creating a new customer.");
/* 241 */         req.setAttribute("messages", messages);
/* 242 */         req.getSession().setAttribute("Person", xml.xmlToEntity("Person", req));
/* 243 */         req.getSession().setAttribute("Address", xml.xmlToEntity("Address", req));
/* 244 */         Entity SalesOrder = xml.xmlToEntity("SalesOrder", req);
/* 245 */         Entity SalesDispatch = xml.xmlToEntity("SalesDispatch", req);
/* 246 */         SalesOrder.setProperty("status", "Pending");
/* 247 */         req.getSession().setAttribute("SalesOrder", SalesOrder);
/* 248 */         req.getSession().setAttribute("SalesDispatch", SalesDispatch);
/* 249 */         url = "/jsp/sale/index.jsp?edit=Y&new=Y";
/* 250 */       } else if (count == 1) {
/* 251 */         Person = (Entity)results.get(0);
/* 252 */         req.getSession().setAttribute("Person", Person);
/* 253 */         Entity Address = ApplicationDao.getEntityByParent("Address", 
/* 254 */           Person.getKey());
/* 255 */         if (Address == null) Address = new Entity("Address", Person.getKey());
/* 256 */         req.getSession().setAttribute("Address", Address);
/*     */ 
/* 258 */         Entity SalesOrder = xml.xmlToEntity("SalesOrder", req);
/* 259 */         Entity SalesDispatch = xml.xmlToEntity("SalesDispatch", req);
/* 260 */         SalesOrder.setProperty("status", "Pending");
/* 261 */         req.getSession().setAttribute("SalesOrder", SalesOrder);
/* 262 */         req.getSession().setAttribute("SalesDispatch", SalesDispatch);
/* 263 */         url = "/jsp/sale/index.jsp?edit=Y";
/*     */       } else {
/* 265 */         url = "/jsp/sale/customer_list.jsp";
/*     */       }
/* 267 */     } else if ("Select".equals(action)) {
/* 268 */       String sKey = req.getParameter("personKey");
/* 269 */       Key key = KeyFactory.stringToKey(sKey);
/* 270 */       Entity Person = ApplicationDao.getEntity(key);
/* 271 */       Entity Address = ApplicationDao.getEntityByParent("Address", Person.getKey());
/* 272 */       if (Address == null) {
/* 273 */         Address = xml.xmlToChildEntity("Address", Person, req);
/*     */       }
/* 275 */       Entity SalesOrder = new Entity("SalesOrder");
/* 276 */       Entity SalesDispatch = new Entity("SalesDispatch");
/*     */ 
/* 278 */       req.getSession().setAttribute("Person", Person);
/* 279 */       req.getSession().setAttribute("Address", Address);
/* 280 */       req.getSession().setAttribute("SalesOrder", 
/* 281 */         xml.xmlToEntity("SalesOrder", req));
/* 282 */       req.getSession().setAttribute("SalesDispatch", 
/* 283 */         xml.xmlToEntity("SalesDispatch", req));
/* 284 */       url = "/jsp/sale/index.jsp?edit=Y";
/* 285 */     } else if ("View".equals(action)) {
/* 286 */       req.getSession().setAttribute("results", null);
/* 287 */       req.getSession().setAttribute("size", new Integer(0));
/* 288 */       url = "/jsp/sale/results.jsp";
/*     */     }
/* 290 */     else if (("Save Changes".equals(action)) || 
/* 291 */       ("Save Changes & Print".equals(action))) {
/* 292 */       String salesOrderKey = req.getParameter("salesOrderKey");
/* 293 */       Key k1 = KeyFactory.stringToKey(salesOrderKey);
/* 294 */       Entity SalesOrder = ApplicationDao.getEntity(k1);
/* 295 */       String delivery = req.getParameter("delivery");
/* 296 */       SalesOrder.setProperty("delivery", delivery);
/*     */ 
/* 298 */       String salesDispatchKey = req.getParameter("salesDispatchKey");
/* 299 */       Entity SalesDispatch = null;
/* 300 */       if (salesDispatchKey != null) {
/* 301 */         Key k2 = KeyFactory.stringToKey(salesDispatchKey);
/* 302 */         SalesDispatch = ApplicationDao.getEntity(k2);
/* 303 */         if (SalesDispatch == null) {
/* 304 */           SalesDispatch = xml.xmlToEntity("SalesDispatch", req);
/* 305 */           SalesDispatch = xml.xmlChildToParentEntity("SalesDispatch", 
/* 306 */             SalesDispatch, SalesOrder);
/*     */         }
/*     */       } else {
/* 309 */         SalesDispatch = xml.xmlToEntity("SalesDispatch", req);
/*     */       }
/* 311 */       SalesOrder = xml.updateEntity(SalesOrder, req);
/* 312 */       SalesDispatch = xml.updateEntity(SalesDispatch, req);
/* 313 */       Entity Person = ApplicationDao.getEntity(SalesOrder.getParent());
/* 314 */       Entity Address = ApplicationDao.getEntityByParent("Address", Person.getKey());
/*     */ 
/* 316 */       boolean success = validate(Address, "Y", req);
/* 317 */       if (!success) {
/* 318 */         req.getSession().setAttribute("Person", Person);
/* 319 */         req.getSession().setAttribute("Address", Address);
/* 320 */         req.getSession().setAttribute("SalesOrder", SalesOrder);
/* 321 */         req.getSession().setAttribute("SalesDispatch", SalesDispatch);
/* 322 */         url = "/jsp/sale/index.jsp?edit=Y";
/*     */       } else {
/* 324 */         SalesOrder.setProperty("salesOrderStatus", req.getParameter("salesOrderStatus"));
/* 325 */         String updateLevels = (String)SalesOrder
/* 326 */           .getProperty("updatedInventoryFlag");
/* 327 */         String status = (String)SalesOrder.getProperty("salesOrderStatus");
/* 328 */         if (("N".equals(updateLevels)) && (status.contains("Paid"))) {
/* 329 */           SalesOrder.setProperty("updatedInventoryFlag", "Y");
/* 330 */           updateInventoryLevels(SalesOrder, req);
/*     */         }
/*     */ 
/* 333 */         ApplicationDao.createOrUpdateEntity(SalesOrder, user);
/* 334 */         bSalesOrderSaved = true;
/* 335 */         SalesOrderKey = KeyFactory.keyToString(SalesOrder.getKey());
/* 336 */         if ("Yes".equals(SalesOrder.getProperty("delivery").toString())) {
/* 337 */           SalesDispatch = ApplicationDao.createOrUpdateEntity(SalesDispatch, 
/* 338 */             user);
/* 339 */           SalesDispatchKey = KeyFactory.keyToString(SalesDispatch
/* 340 */             .getKey());
/*     */         }
/*     */ 
/* 343 */         if ((bPrint) && (bSalesOrderSaved))
/* 344 */           url = "/jsp/sale/print.jsp";
/*     */         else
/* 346 */           url = "/jsp/sale/confirmation.jsp?conf=Y";
/*     */       }
/*     */     }
/* 349 */     else if (("Save".equals(action)) || ("Save & Print".equals(action)))
/*     */     {
/* 351 */       String delivery = req.getParameter("delivery");
/* 352 */       String sKey = req.getParameter("personKey");
/* 353 */       Key key = null;
/* 354 */       if (sKey != null)
/* 355 */         key = KeyFactory.stringToKey(sKey);
/* 356 */       Entity Person = null;
/* 357 */       Entity Address = null;
/* 358 */       Entity SalesOrder = null;
/* 359 */       Entity SalesDispatch = null;
/* 360 */       long newId = 0L;
/* 361 */       String confirmationNumber = "";
/*     */ 
/* 363 */       if (key != null) {
/* 364 */         Person = ApplicationDao.getEntity(key);
/* 365 */         Address = ApplicationDao.getEntityByParent("Address", Person.getKey());
/* 366 */         if (Address == null) {
/* 367 */           Address = xml.xmlToChildEntity("Address", Person, req);
/*     */         }
/*     */ 
/* 370 */         SalesOrder = xml.xmlToEntity("SalesOrder", req);
/* 371 */         SalesDispatch = xml.xmlToEntity("SalesDispatch", req);
/*     */ 
/* 373 */         SalesOrder = xml.xmlChildToParentEntity("SalesOrder", 
/* 374 */           SalesOrder, Person);
/*     */ 
/* 376 */         SalesDispatch = xml.xmlChildToParentEntity("SalesDispatch", 
/* 377 */           SalesDispatch, Person);
/*     */       }
/*     */       else {
/* 380 */         Person = xml.xmlToEntity("Person", req);
/* 381 */         Address = xml.xmlToEntity("Address", req);
/* 382 */         SalesOrder = xml.xmlToEntity("SalesOrder", req);
/* 383 */         SalesDispatch = xml.xmlToEntity("SalesDispatch", req);
/*     */       }
/* 385 */       req.getSession().setAttribute("Person", Person);
/* 386 */       req.getSession().setAttribute("Address", Address);
/* 387 */       req.getSession().setAttribute("SalesOrder", SalesOrder);
/* 388 */       req.getSession().setAttribute("SalesDispatch", SalesDispatch);
/*     */ 
/* 390 */       boolean success = validate(Address, "N", req);
/* 391 */       if (!success) {
/* 392 */         url = "/jsp/sale/index.jsp?edit=Y";
/*     */       } else {
/* 394 */         String zipcode = (String)Address.getProperty("zipcode");
/* 395 */         SalesOrder.setProperty("salesOrderStatus", "Pending");
/*     */ 
/* 397 */         if ("No".equals((String)SalesOrder.getProperty("elevatorFlag"))) {
/* 398 */           SalesOrder.setProperty("floorNumber", "1");
/*     */         }
/* 400 */         if (Person.getKey().getId() != 0L)
/*     */         {
/* 404 */           Entity newSalesOrder = xml.xmlChildToParentEntity(
/* 405 */             "SalesOrder", SalesOrder, Person);
/* 406 */           newSalesOrder.setProperty("firstName", 
/* 407 */             Person.getProperty("firstName"));
/* 408 */           newSalesOrder.setProperty("lastName", 
/* 409 */             Person.getProperty("lastName"));
/* 410 */           newSalesOrder.setProperty("middleInitial", 
/* 411 */             Person.getProperty("middleInitial"));
/* 412 */           newSalesOrder.setProperty("farmBase", 
/* 413 */             (String)req.getSession().getAttribute("farmBase"));
/* 414 */           newSalesOrder.setProperty("zipcode", zipcode);
/* 415 */           newSalesOrder.setProperty("location", inventoryLocation);
/*     */ 
/* 417 */           newSalesOrder.setProperty("updatedInventoryFlag", "N");
/*     */ 
/* 419 */           DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
/* 420 */           newSalesOrder.setProperty("salesOrderDate", 
/* 421 */             formatter.format(new Date()));
/* 422 */           newSalesOrder.setProperty("creationDate", 
/* 423 */             new Date());
/* 424 */           newSalesOrder.setProperty("createdBy", user.getNickname());
/* 425 */           String updateLevels = (String)SalesOrder
/* 426 */             .getProperty("updatedInventoryFlag");
/* 427 */           String status = (String)SalesOrder.getProperty("salesOrderStatus");
/* 428 */           if (("N".equals(updateLevels)) && (status.contains("Paid"))) {
/* 429 */             SalesOrder.setProperty("updatedInventoryFlag", "Y");
/* 430 */             updateInventoryLevels(SalesOrder, req);
/*     */           }
/*     */ 
/* 434 */           Entity sales = 
/* 435 */             ApplicationDao.createOrUpdateEntity(newSalesOrder, user);
/* 436 */           Entity newSalesDispatch = xml.xmlChildToParentEntity(
/* 437 */             "SalesDispatch", SalesDispatch, sales);
/* 438 */           Entity dispatch = null;
/* 439 */           if ("Yes".equals(delivery)) {
/* 440 */             dispatch = ApplicationDao.createOrUpdateEntity(newSalesDispatch, 
/* 441 */               user);
/* 442 */             SalesDispatchKey = KeyFactory.keyToString(dispatch
/* 443 */               .getKey());
/*     */           }
/*     */ 
/* 449 */           newId = sales.getKey().getId();
/* 450 */           int julianDay = Calendar.getInstance().get(
/* 451 */             6);
/* 452 */           Date now = new Date();
/* 453 */           confirmationNumber = julianDay + 
/* 454 */             Person.getKey().getId() + newId + 
/* 455 */             now.getHours() + now.getMinutes() + 
/* 456 */             now.getSeconds()+"";
/* 457 */           sales.setProperty("orderNumber", confirmationNumber);
/* 458 */           ApplicationDao.createOrUpdateEntity(sales, user);
/*     */ 
/* 460 */           bSalesOrderSaved = true;
/* 461 */           SalesOrderKey = KeyFactory.keyToString(sales.getKey());
/*     */         }
/*     */         else
/*     */         {
/* 466 */           Person.setProperty("personStatus", "Active");
/* 467 */           Person.setProperty("personType", "Customer");
/* 468 */           Entity newPerson = ApplicationDao.createOrUpdateEntity(Person, user);
/*     */ 
/* 470 */           Address = parseTelephone(Address, req);
/* 471 */           Entity newAddress = xml.xmlChildToParentEntity("Address", 
/* 472 */             Address, Person);
/* 473 */           ApplicationDao.createOrUpdateEntity(newAddress, user);
/* 474 */           Entity newSalesOrder = xml.xmlChildToParentEntity(
/* 475 */             "SalesOrder", SalesOrder, newPerson);
/* 476 */           Entity newSalesDispatch = xml.xmlChildToParentEntity(
/* 477 */             "SalesDispatch", SalesOrder, newPerson);
/* 478 */           newSalesOrder.setProperty("firstName", 
/* 479 */             Person.getProperty("firstName"));
/* 480 */           newSalesOrder.setProperty("lastName", 
/* 481 */             Person.getProperty("lastName"));
/* 482 */           newSalesOrder.setProperty("middleInitial", 
/* 483 */             Person.getProperty("middleInitial"));
/* 484 */           newSalesOrder.setProperty("zipcode", zipcode);
/* 485 */           newSalesOrder.setProperty("location", inventoryLocation);
/*     */ 
/* 487 */           newSalesOrder.setProperty("updatedInventoryFlag", "N");
/* 488 */           String updateLevels = (String)SalesOrder
/* 489 */             .getProperty("updatedInventoryFlag");
/* 490 */           String status = (String)SalesOrder.getProperty("salesOrderStatus");
/* 491 */           if (("N".equals(updateLevels)) && (status.contains("Paid"))) {
/* 492 */             SalesOrder.setProperty("updatedInventoryFlag", "Y");
/* 493 */             updateInventoryLevels(SalesOrder, req);
/*     */           }
/*     */ 
/* 498 */           DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
/* 499 */           newSalesOrder.setProperty("salesOrderDate", 
/* 500 */             formatter.format(new Date()));
/* 501 */           newSalesOrder.setProperty("creationDate", 
/* 502 */             new Date());
/* 503 */           newSalesOrder.setProperty("createdBy", user.getNickname());
/* 504 */           newSalesOrder.setProperty("farmBase", 
/* 505 */             (String)req.getSession().getAttribute("farmBase"));
/* 506 */           Entity sales = 
/* 507 */             ApplicationDao.createOrUpdateEntity(newSalesOrder, user);
/* 508 */           Entity dispatch = null;
/* 509 */           if ("Yes".equals(delivery)) {
/* 510 */             dispatch = ApplicationDao.createOrUpdateEntity(newSalesDispatch, 
/* 511 */               user);
/* 512 */             SalesDispatchKey = KeyFactory.keyToString(dispatch
/* 513 */               .getKey());
/*     */           }
/*     */ 
/* 516 */           newId = sales.getKey().getId();
/* 517 */           int julianDay = Calendar.getInstance().get(
/* 518 */             6);
/* 519 */           Date now = new Date();
/* 520 */           confirmationNumber = julianDay + 
/* 521 */             Person.getKey().getId() + newId + 
/* 522 */             now.getHours() + now.getMinutes() + 
/* 523 */             now.getSeconds()+"";
/* 524 */           sales.setProperty("orderNumber", confirmationNumber);
/* 525 */           ApplicationDao.createOrUpdateEntity(sales, user);
/*     */ 
/* 527 */           bSalesOrderSaved = true;
/* 528 */           SalesOrderKey = KeyFactory.keyToString(sales.getKey());
/*     */         }
/* 530 */         req.getSession().setAttribute("Person", new Entity("Person"));
/* 531 */         req.getSession().setAttribute("Address", new Entity("Address"));
/* 532 */         req.getSession().setAttribute("SalesOrder", new Entity("SalesOrder"));
/* 533 */         req.getSession().setAttribute("SalesDispatch", new Entity(
/* 534 */           "SalesDispatch"));
/*     */ 
/* 536 */         req.setAttribute("orderNumber", confirmationNumber);
/*     */ 
/* 538 */         url = "/jsp/sale/confirmation.jsp";
/*     */       }
/*     */     }
/* 541 */     else if ("Print".equals(action)) {
/* 542 */       Key salesOrderKey = KeyFactory.stringToKey(req
/* 543 */         .getParameter("salesOrderKey"));
/*     */ 
/* 546 */       Entity SalesOrder = ApplicationDao.getEntity(salesOrderKey);
/* 547 */       Entity SalesDispatch = ApplicationDao.getEntityByParent("SalesDispatch", 
/* 548 */         salesOrderKey);
/* 549 */       Key key = SalesOrder.getParent();
/* 550 */       Entity Person = ApplicationDao.getEntity(key);
/* 551 */       Entity Address = ApplicationDao.getEntityByParent("Address", Person.getKey());
/* 552 */       if (Address == null) {
/* 553 */         Address = xml.xmlToChildEntity("Address", Person, req);
/*     */       }
/* 555 */       req.setAttribute("Person", Person);
/* 556 */       req.setAttribute("Address", Address);
/* 557 */       req.setAttribute("SalesOrder", SalesOrder);
/* 558 */       req.setAttribute("SalesDispatch", SalesDispatch);
/* 559 */       NodeList xmlSalesO = xml.getEntityProperties("SalesOrder", req);
/* 560 */       NodeList xmlSalesD = xml.getEntityProperties("SalesDispatch", req);
/* 561 */       req.setAttribute("xmlSalesOrder", xmlSalesO);
/* 562 */       req.setAttribute("xmlSalesDispatch", xmlSalesD);
/* 563 */       url = "/jsp/sale/print.jsp";
/*     */     }
/* 565 */     else if ("Find Item".equals(action)) {
/* 566 */       logger.log(Level.ALL, "finding Item");
/*     */ 
/* 568 */       String discount = req.getParameter("discount");
/*     */ 
/* 570 */       SearchParameter parameter = null;
/* 571 */       Entity Item = null;
/* 572 */       String sKey = req.getParameter("personKey");
/* 573 */       Key key = null;
/* 574 */       if (sKey != null)
/* 575 */         key = KeyFactory.stringToKey(sKey);
/* 576 */       Entity Person = null;
/* 577 */       Entity Address = null;
/*     */ 
/* 579 */       if (key != null) {
/* 580 */         Person = ApplicationDao.getEntity(key);
/* 581 */         Address = ApplicationDao.getEntityByParent("Address", Person.getKey());
/* 582 */         if (Address == null)
/* 583 */           Address = xml.xmlToChildEntity("Address", Person, req);
/*     */       }
/* 585 */       Entity SalesOrder = (Entity)req.getSession().getAttribute("SalesOrder");
/* 586 */       Entity SalesDispatch = (Entity)req.getSession().getAttribute("SalesDispatch");
/*     */ 
/* 588 */       ArrayList inventoryMessages = new ArrayList();
/*     */ 
/* 590 */       if ((Person == null) || (Person.getKey().getId() == 0L)) {
/* 591 */         Person = xml.xmlToEntity("Person", req);
/*     */       }
/* 593 */       if ((Address == null) || (Address.getKey().getId() == 0L))
/* 594 */         Address = xml.xmlToEntity("Address", req);
/* 595 */       if ((SalesOrder == null) || (SalesOrder.getKey().getId() == 0L))
/* 596 */         SalesOrder = xml.xmlToEntity("SalesOrder", req);
/* 597 */       if ((SalesDispatch == null) || (SalesDispatch.getKey().getId() == 0L)) {
/* 598 */         SalesDispatch = xml.xmlToEntity("SalesDispatch", req);
/*     */       }
/* 600 */       boolean increment = false;
/*     */ 
/* 602 */       for (int i = 0; i < 10; i++) {
/* 603 */         logger.log(Level.INFO, "item" + i);
/* 604 */         String itemNumber = req.getParameter("itemNumber" + i);
/* 605 */         ArrayList params = new ArrayList();
/*     */ 
/* 607 */         if ((itemNumber != null) && (!itemNumber.equals(""))) {
/* 608 */           logger.log(Level.INFO, "no match");
/* 609 */           itemNumber = itemNumber.toUpperCase();
/*     */ 
/* 611 */           parameter = new SearchParameter("itemNumber", itemNumber, 
/* 612 */             Query.FilterOperator.EQUAL);
/* 613 */           params.add(parameter);
/* 614 */           if (req.getSession().getAttribute("INVENTORY_LOCATION") != null) {
/* 615 */             params.add(new SearchParameter("location", (String)req.getSession().getAttribute("INVENTORY_LOCATION"), Query.FilterOperator.EQUAL));
/*     */           }
/*     */ 
/* 618 */           logger.log(Level.INFO, "getting results");
/* 619 */           ArrayList results = ApplicationDao.getEntities("Item", params, null, 
/* 620 */             null, farmBase);
/* 621 */           logger.log(Level.INFO, "got results");
/*     */ 
/* 623 */           if ((results == null) || (results.size() == 0)) {
/* 624 */             ArrayList messages = new ArrayList();
/* 625 */             messages.add("Item #" + itemNumber + 
/* 626 */               " could not be found.");
/* 627 */             req.setAttribute("messages", messages);
/*     */           }
/* 629 */           else if (results.size() == 1) {
/* 630 */             String sQty1 = req.getParameter("quantity" + i);
/* 631 */             String sLocation = req.getParameter("location" + i);
/*     */ 
/* 633 */             if (sQty1 == null) sQty1 = "0";
/* 634 */             int qty1 = Integer.parseInt(sQty1);
/*     */ 
/* 636 */             Item = (Entity)results.get(0);
/* 637 */             String sQtyWarehouse = (String)Item.getProperty("qtyWarehouse");
/* 638 */             String sQtyFloor = (String)Item.getProperty("qtyFloor");
/* 639 */             if (sQtyWarehouse == null) sQtyWarehouse = "0";
/* 640 */             if (sQtyFloor == null) sQtyFloor = "0";
/* 641 */             int qtyWarehouse = Integer.parseInt(sQtyWarehouse);
/* 642 */             int qtyFloor = Integer.parseInt(sQtyFloor);
/*     */ 
/* 644 */             if (Item != null) {
/* 645 */               inventoryMessages.add("Item#" + Item.getProperty("itemNumber") + ": Warehouse Quantity=" + qtyWarehouse + ", Floor Quantity=" + qtyFloor + ".");
/*     */             }
/*     */ 
/* 663 */             SalesOrder.setProperty("itemNumber" + i, 
/* 664 */               Item.getProperty("itemNumber"));
/* 665 */             SalesOrder.setProperty("itemName" + i, 
/* 666 */               Item.getProperty("itemName"));
/* 667 */             SalesOrder.setProperty("vendor" + i, 
/* 668 */               Item.getProperty("vendor"));
/* 669 */             SalesOrder.setProperty("quantity" + i, 
/* 670 */               req.getParameter("quantity" + i));
/* 671 */             SalesOrder.setProperty("price" + i, 
/* 672 */               Item.getProperty("price"));
/* 673 */             SalesOrder.setProperty("location" + i, 
/* 674 */               req.getParameter("location" + i));
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 681 */       SalesOrder.setProperty("discount", html.cleanData(req.getParameter("discount")));
/*     */ 
/* 683 */       req.getSession().setAttribute("Person", Person);
/* 684 */       req.getSession().setAttribute("Address", Address);
/* 685 */       req.getSession().setAttribute("SalesOrder", SalesOrder);
/* 686 */       req.getSession().setAttribute("SalesDispatch", SalesDispatch);
/* 687 */       req.setAttribute("discount", discount);
/* 688 */       req.getSession().setAttribute("SalesOrder", SalesOrder);
/*     */ 
/* 690 */       if (inventoryMessages.size() > 0)
/* 691 */         req.setAttribute("inventoryMessages", inventoryMessages);
/* 692 */       url = "/jsp/sale/index.jsp?edit=Y";
/*     */     }
/*     */ 
/* 704 */     if ((bPrint) && (bSalesOrderSaved))
/*     */     {
/* 706 */       Key k1 = KeyFactory.stringToKey(SalesOrderKey);
/* 707 */       Entity SalesOrder = ApplicationDao.getEntity(k1);
/*     */ 
/* 712 */       Key key = SalesOrder.getParent();
/* 713 */       Entity Person = Util.findEntity(key);
/* 714 */       Entity Address = ApplicationDao.getEntityByParent("Address", Person.getKey());
/* 715 */       if (Address == null)
/* 716 */         Address = xml.xmlToChildEntity("Address", Person, req);
/* 717 */       Entity SalesDispatch = ApplicationDao.getEntityByParent("SalesDispatch", 
/* 718 */         SalesOrder.getKey());
/* 719 */       if (SalesDispatch == null) {
/* 720 */         SalesDispatch = xml.xmlToChildEntity("SalesDispatch", 
/* 721 */           SalesOrder, req);
/*     */       }
/* 723 */       req.setAttribute("Person", Person);
/* 724 */       req.setAttribute("Address", Address);
/* 725 */       req.setAttribute("SalesOrder", SalesOrder);
/* 726 */       req.setAttribute("SalesDispatch", SalesDispatch);
/* 727 */       NodeList xmlSalesO = xml.getEntityProperties("SalesOrder", req);
/* 728 */       NodeList xmlSalesD = xml.getEntityProperties("SalesDispatch", req);
/* 729 */       req.setAttribute("xmlSalesOrder", xmlSalesO);
/* 730 */       req.setAttribute("xmlSalesDispatch", xmlSalesD);
/* 731 */       url = "/jsp/sale/print.jsp";
/*     */     }
/*     */ 
/* 735 */     req.getRequestDispatcher(req.getContextPath() + url).forward(req, resp);
/*     */   }
/*     */ 
/*     */   protected void doPost(HttpServletRequest req, HttpServletResponse resp)
/*     */     throws ServletException, IOException
/*     */   {
/* 741 */     doGet(req, resp);
/*     */   }
/*     */ 
/*     */   private Entity parseTelephone(Entity entity, HttpServletRequest req)
/*     */   {
/* 747 */     String homePhone1 = html.cleanData(req
/* 748 */       .getParameter("homePhone_areacode"));
/* 749 */     String homePhone2 = html
/* 750 */       .cleanData(req.getParameter("homePhone_first3"));
/* 751 */     String homePhone3 = html.cleanData(req.getParameter("homePhone_last4"));
/* 752 */     String homePhone = "(" + homePhone1 + ")" + homePhone2 + "-" + 
/* 753 */       homePhone3;
/* 754 */     entity.setProperty("homePhone", homePhone);
/*     */ 
/* 756 */     String cellPhone1 = html.cleanData(req
/* 757 */       .getParameter("cellPhone_areacode"));
/* 758 */     String cellPhone2 = html
/* 759 */       .cleanData(req.getParameter("cellPhone_first3"));
/* 760 */     String cellPhone3 = html.cleanData(req.getParameter("cellPhone_last4"));
/* 761 */     String cellPhone = "(" + cellPhone1 + ")" + cellPhone2 + "-" + 
/* 762 */       cellPhone3;
/* 763 */     entity.setProperty("cellPhone", cellPhone);
/*     */ 
/* 765 */     String workPhone1 = html.cleanData(req
/* 766 */       .getParameter("workPhone_areacode"));
/* 767 */     String workPhone2 = html
/* 768 */       .cleanData(req.getParameter("workPhone_first3"));
/* 769 */     String workPhone3 = html.cleanData(req.getParameter("workPhone_last4"));
/* 770 */     String workPhone = "(" + workPhone1 + ")" + workPhone2 + "-" + 
/* 771 */       workPhone3;
/* 772 */     entity.setProperty("workPhone", workPhone);
/*     */ 
/* 774 */     return entity;
/*     */   }
/*     */ 
/*     */   private boolean validate(Entity Address, String edit, HttpServletRequest req)
/*     */   {
/* 780 */     Validator validator = new Validator();
/*     */ 
/* 782 */     ArrayList messages = new ArrayList();
/* 783 */     boolean success = false;
/*     */ 
/* 785 */     if (Address.getKey().getId() == 0L)
/*     */     {
/* 787 */       messages = validator.validateRequired("firstname", 
/* 788 */         req.getParameter("firstName"), messages);
/* 789 */       messages = validator.validateRequired("lastname", 
/* 790 */         req.getParameter("lastName"), messages);
/* 791 */       messages = validator.validateRequired("address line 1", 
/* 792 */         req.getParameter("addressLine1"), messages);
/* 793 */       messages = validator.validateRequired("city", 
/* 794 */         req.getParameter("city"), messages);
/* 795 */       messages = validator.validateRequired("state", 
/* 796 */         req.getParameter("state"), messages);
/* 797 */       messages = validator.validateRequired("zipcode", 
/* 798 */         req.getParameter("zipcode"), messages);
/*     */     }
/*     */ 
/* 803 */     String delivery = req.getParameter("delivery");
/* 804 */     if ((delivery != null) && ("Yes".equals(delivery))) {
/* 805 */       messages = validator.validateRequired("major intersection", 
/* 806 */         req.getParameter("majorIntersection"), messages);
/* 807 */       messages = validator.validateRequired("street suffix", 
/* 808 */         req.getParameter("streetSuffix"), messages);
/* 809 */       messages = validator.validateRequired("type of structure", 
/* 810 */         req.getParameter("structureType"), messages);
/* 811 */       messages = validator.validateRequired("sales order status", 
/* 812 */         req.getParameter("salesOrderStatus"), messages);
/*     */ 
/* 814 */       String structureType = req.getParameter("structureType");
/*     */ 
/* 816 */       if (("apartment".equals(structureType)) || 
/* 817 */         ("condominium".equals(structureType)) || 
/* 818 */         ("townhome".equals(structureType)) || 
/* 819 */         ("assisted living facility".equals(structureType))) {
/* 820 */         messages = validator.validateRequired("building number", 
/* 821 */           req.getParameter("buildingNumber"), messages);
/* 822 */         messages = validator.validateRequired("unit number", 
/* 823 */           req.getParameter("unitNumber"), messages);
/*     */       }
/*     */ 
/* 826 */       if (("apartment".equals(structureType)) || 
/* 827 */         ("condominium".equals(structureType)) || 
/* 828 */         ("townhome".equals(structureType)) || 
/* 829 */         ("assisted living facility".equals(structureType))) {
/* 830 */         messages = validator.validateRequired("floor number", 
/* 831 */           req.getParameter("floorNumber"), messages);
/*     */       }
/* 833 */       messages = validator.validateRequired("elevator availability", 
/* 834 */         req.getParameter("elevatorFlag"), messages);
/* 835 */       messages = validator.validateRequired("gated community", 
/* 836 */         req.getParameter("gatedCommunityFlag"), messages);
/*     */ 
/* 838 */       if ((req.getParameter("subdivision") != null) && 
/* 839 */         (req.getParameter("subdivision").trim().length() > 0)) {
/* 840 */         messages = validator.validateRequired("gated community", 
/* 841 */           req.getParameter("gatedCommunityFlag"), messages);
/*     */       }
/* 843 */       if ("Yes".equals(req.getParameter("gatedCommunityFlag"))) {
/* 844 */         messages = validator.validateRequired("gate instructions", 
/* 845 */           req.getParameter("gateInstructions"), messages);
/* 846 */         if ("callbox code".equals(req.getParameter("gateInstructions")))
/* 847 */           messages = validator.validateRequired("gate code", 
/* 848 */             req.getParameter("gateCode"), messages);
/*     */       }
/* 850 */       messages = validator.validateRequired("delivery location", 
/* 851 */         req.getParameter("deliveryLocation"), messages);
/*     */ 
/* 855 */       String deliveryStatus = req.getParameter("deliveryStatus");
/* 856 */       String deliveryDate = req.getParameter("deliveryDate");
/* 857 */       if ("N".equals(edit)) {
/* 858 */         if ((deliveryDate == null) || (deliveryDate.equals(""))) {
/* 859 */           messages.add("delivery date is required.");
/*     */         }
/*     */       }
/* 862 */       else if ("Reschedule".equals(deliveryStatus)) {
/* 863 */         if ((deliveryDate == null) || (deliveryDate.equals("")))
/* 864 */           messages.add("SalesOrder date must be entered. If date is unknown change status to Pending.");
/*     */       }
/* 866 */       else if ((!"Reschedule".equals(deliveryStatus)) && 
/* 867 */         (!"Pending".equals(deliveryStatus)) && 
/* 868 */         (!"Cancelled By Donor".equals(deliveryStatus))) {
/* 869 */         messages = validator.validateRequired("delivery date", 
/* 870 */           req.getParameter("deliveryDate"), messages);
/*     */       }
/*     */ 
/* 874 */       if (!"Yes".equals(req.getParameter("acceptTermsPolicies")))
/* 875 */         messages.add("You must acknowledge that you have read Faith Farm's delivery terms and policies to the customer.");
/*     */     }
/* 877 */     if (messages.size() > 0) {
/* 878 */       req.setAttribute("messages", messages);
/* 879 */       success = false;
/*     */     } else {
/* 881 */       success = true;
/*     */     }
/* 883 */     return success;
/*     */   }
/*     */ 
/*     */   private ArrayList setSearchParameters(HttpServletRequest req)
/*     */   {
/* 888 */     ArrayList parameters = new ArrayList();
/* 889 */     parameters.add(new SearchParameter("orderNumber", html.cleanData(req
/* 890 */       .getParameter("orderNumber")), Query.FilterOperator.EQUAL));
/* 891 */     parameters.add(new SearchParameter("firstName", html.cleanData(req
/* 892 */       .getParameter("firstName")), Query.FilterOperator.EQUAL));
/* 893 */     parameters.add(new SearchParameter("delivery", html.cleanData(req
/* 894 */       .getParameter("delivery")), Query.FilterOperator.EQUAL));
/* 895 */     parameters.add(new SearchParameter("lastName", html.cleanData(req
/* 896 */       .getParameter("lastName")), Query.FilterOperator.EQUAL));
/* 897 */     parameters.add(new SearchParameter("zipcode", html.cleanData(req
/* 898 */       .getParameter("zipcode")), Query.FilterOperator.EQUAL));
/* 899 */     parameters.add(new SearchParameter("status", html.cleanData(req
/* 900 */       .getParameter("status")), Query.FilterOperator.EQUAL));
/* 901 */     parameters.add(new SearchParameter("salesOrderDate", html.cleanData(req
/* 902 */       .getParameter("salesOrderDate")), Query.FilterOperator.EQUAL));
/*     */ 
/* 905 */     return parameters;
/*     */   }
/*     */ 
/*     */   private void clearSessionVariables(HttpSession session)
/*     */   {
/* 910 */     session.removeAttribute("Person");
/* 911 */     session.removeAttribute("Address");
/* 912 */     session.removeAttribute("SalesDispatch");
/* 913 */     session.removeAttribute("SalesOrder");
/* 914 */     session.removeAttribute("xmlPerson");
/* 915 */     session.removeAttribute("xmlAddress");
/* 916 */     session.removeAttribute("xmlSalesDispatch");
/* 917 */     session.removeAttribute("xmlSalesOrder");
/* 918 */     for (int i = 0; i < 10; i++)
/* 919 */       session.removeAttribute("Item" + i);
/*     */   }
/*     */ 
/*     */   private void updateInventoryLevels(Entity SalesOrder, HttpServletRequest req)
/*     */   {
/* 925 */     Entity Item = null;
/* 926 */     StringBuffer email = new StringBuffer();
/* 927 */     email = email.append("Attn: New Furniture & Warehouse\n\n\r");
/* 928 */     email = email.append("Sales Order#:" + SalesOrder.getProperty("orderNumber"));
/* 929 */     email = email.append("The following items have been sold and the inventory item level has been adjusted:\n\r");
/*     */ 
/* 932 */     for (int i = 0; i < 9; i++) {
/* 933 */       String itemNumber = (String)SalesOrder.getProperty("itemNumber" + 
/* 934 */         i);
/*     */ 
/* 936 */       if (!"".equals(itemNumber)) {
/* 937 */         String quantity = (String)SalesOrder.getProperty("quantity" + 
/* 938 */           i);
/* 939 */         String location = (String)SalesOrder.getProperty("location" + 
/* 940 */           i);
/*     */ 
/* 942 */         SearchParameter param = new SearchParameter("itemNumber", 
/* 943 */           itemNumber, Query.FilterOperator.EQUAL);
/* 944 */         ArrayList params = new ArrayList();
/* 945 */         params.add(param);
/*     */ 
/* 947 */         ArrayList list = ApplicationDao.getEntities("Item", params, 
/* 948 */           null, null, 
/* 949 */           (String)req.getSession().getAttribute("farmBase"));
/* 950 */         if (list.size() > 0)
/*     */         {
/* 952 */           Item = (Entity)list.get(0);
/* 953 */           int par = 0;
/* 954 */           int floor = Integer.parseInt(Item.getProperty("qtyFloor")
/* 955 */             .toString());
/* 956 */           int warehouse = Integer.parseInt(Item.getProperty(
/* 957 */             "qtyWarehouse").toString());
/*     */ 
/* 959 */           if (((Item.getProperty("minLevel") != null ? 1 : 0) & (
/* 959 */             "".equals(Item.getProperty("minLevel")) ? 0 : 1)) != 0)
/* 960 */             par = Integer.parseInt(Item.getProperty("minLevel")
/* 961 */               .toString());
/* 962 */           int qty = Integer.parseInt(quantity);
/*     */ 
/* 964 */           if ("1/W".equals(location))
/* 965 */             warehouse -= qty;
/* 966 */           else if ("1/F".equals(location))
/* 967 */             floor -= qty;
/* 968 */           else if ("R/O".equals(location)) {
/* 969 */             warehouse -= qty;
/*     */           }
/*     */ 
/* 974 */           email = email.append("Item#   :" + itemNumber);
/* 975 */           email = email.append("Quantity: " + quantity);
/* 976 */           email = email.append("Location: " + location);
/* 977 */           email = email.append("\n\r**********\n\r\n\r");
/*     */ 
/* 979 */           Item.setProperty("qtyWarehouse", Integer.valueOf(warehouse));
/* 980 */           Item.setProperty("qtyFloor", Integer.valueOf(floor));
/* 981 */           ApplicationDao.createOrUpdateEntity(Item, UserServiceFactory.getUserService().getCurrentUser());
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 988 */     Mailer.sendMail("rrratliff@yahoo.com", "Inventory Adjustment Notice", email, "oss_automessage@faithfarm.com");
/*     */   }
/*     */ }

/* Location:           C:\development\workspace\faithfarmoss\WEB-INF\classes\
 * Qualified Name:     org.faithfarm.SalesOrderServlet
 * JD-Core Version:    0.6.2
 */