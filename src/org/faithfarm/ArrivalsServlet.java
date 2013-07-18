/*     */ package org.faithfarm;
/*     */ 
/*     */ /*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;

/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;

/*     */ import org.faithfarm.dataobjects.SearchParameter;
/*     */ import org.faithfarm.datawriters.ApplicationDao;
/*     */ import org.faithfarm.datawriters.PersonDao;
/*     */ import org.faithfarm.utilities.ApplicationTools;
/*     */ import org.faithfarm.utilities.FFSession;
/*     */ import org.faithfarm.utilities.HTMLBuilder;
/*     */ import org.faithfarm.utilities.XMLParser;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.NodeList;

import com.google.appengine.api.datastore.DatastoreService;
/*     */ import com.google.appengine.api.datastore.DatastoreServiceFactory;
/*     */ import com.google.appengine.api.datastore.Entity;
/*     */ import com.google.appengine.api.datastore.FetchOptions;
/*     */ import com.google.appengine.api.datastore.Key;
/*     */ import com.google.appengine.api.datastore.KeyFactory;
/*     */ import com.google.appengine.api.datastore.PreparedQuery;
/*     */ import com.google.appengine.api.datastore.Query;
/*     */ import com.google.appengine.api.users.User;
/*     */ import com.google.appengine.api.users.UserService;
/*     */ import com.google.appengine.api.users.UserServiceFactory;
/*     */ 
/*     */ 
/*     */  
/*     */  
/*     */ 
/*     */ public class ArrivalsServlet extends BaseServlet
/*     */ {
/*  44 */   private static final Logger logger = Logger.getLogger(ControllerServlet.class.getCanonicalName());
/*  45 */   private static final HTMLBuilder html = new HTMLBuilder();
/*  46 */   private static final PersonDao dao = new PersonDao();
/*  47 */   private static final XMLParser xml = new XMLParser();
/*     */ 
/*  49 */   private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
/*     */ 
/*     */   protected void doGet(HttpServletRequest req, HttpServletResponse resp)
/*     */     throws ServletException, IOException
/*     */   {
/*  54 */     if (FFSession.needsRedirect(req, resp)) {
/*  55 */       return;
/*     */     }
/*     */ 
/*  58 */     UserService userService = UserServiceFactory.getUserService();
/*  59 */     User user = userService.getCurrentUser();
/*     */ 
/*  61 */     String farmBase = (String)req.getSession().getAttribute("farmBase");
/*  62 */     String url = "/jsp/arrivals/results.jsp";
/*     */ 
/*  64 */     String action = req.getParameter("action");
/*     */ 
/*  66 */     if ("Search".equals(action))
/*     */     {
/*  70 */       String date = req.getParameter("arrivalDate");
/*     */ 
/*  72 */       ArrayList results = getArrivals(date);
/*     */ 
/*  74 */       int[] truck = new int[400];
/*     */ 
/*  76 */       for (int i = 0; i < results.size(); i++) {
/*  77 */         Entity Dispatch = (Entity)results.get(i);
/*  78 */         String sTruck = "N/A";
/*  79 */         if ((Dispatch.getProperty("truck") != null) && (!"".equals(Dispatch.getProperty("truck")))) {
/*  80 */           sTruck = Dispatch.getProperty("truck").toString();
/*  81 */           int truckNum = Integer.parseInt(sTruck);
/*  82 */           truck[truckNum] += 1;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*  88 */       Map map = new HashMap();
/*     */ 
/*  90 */       for (int a = 200; a < 300; a++) {
/*  91 */         if (truck[a] > 0) {
/*  92 */           map.put(a, Integer.valueOf(truck[a]));
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*  97 */       req.setAttribute("arrivalDate", date);
/*  98 */       req.getSession().setAttribute("resultMap", map);
/*  99 */       url = "/jsp/arrivals/lines.jsp";
/*     */     }
/* 101 */     else if ("Extract Donations".equals(action)) {
/* 102 */       String truckNumber = req.getParameter("truck");
/* 103 */       String date = req.getParameter("arrivalDate");
/* 104 */       getDonations(truckNumber, date, "Onboard", req);
/*     */ 
/* 106 */       url = "/jsp/arrivals/details.jsp";
/*     */     }
/* 108 */     else if ("Bric-N-Brac".equals(action)) {
/* 109 */       updateDonation(req, "Sent To Bric-N-Brac", user);
/*     */ 
/* 111 */       getDonations(req.getParameter("truck"), req.getParameter("arrivalDate"), "Onboard", req);
/*     */ 
/* 113 */       url = "/jsp/arrivals/details.jsp";
/*     */     }
/* 115 */     else if ("Trash".equals(action)) {
/* 116 */       updateDonation(req, "Sent To Trash", user);
/*     */ 
/* 118 */       getDonations(req.getParameter("truck"), req.getParameter("arrivalDate"), "Onboard", req);
/*     */ 
/* 120 */       url = "/jsp/arrivals/details.jsp";
/*     */     }
/* 122 */     else if ("DNR".equals(action)) {
/* 123 */       updateDonation(req, "Did Not Receive", user);
/*     */ 
/* 125 */       getDonations(req.getParameter("truck"), req.getParameter("arrivalDate"), "Onboard", req);
/*     */ 
/* 127 */       url = "/jsp/arrivals/details.jsp";
/*     */     }
/* 129 */     else if ("Add To Inventory".equals(action)) {
/* 130 */       String type = html.cleanData(req.getParameter("itemType"));
/* 131 */       String qty = html.cleanData(req.getParameter("quantity"));
/* 132 */       String name = html.cleanData(req.getParameter("itemName"));
/* 133 */       String price = html.cleanData(req.getParameter("price"));
/* 134 */       String date = html.cleanData(req.getParameter("arrivalDate"));
/* 135 */       String itemNumber = html.cleanData(req.getParameter("itemNumber"));
/*     */ 
/* 137 */       ArrayList errors = new ArrayList();
/* 138 */       if (price.equals(""))
/* 139 */         errors.add("price is required");
/* 140 */       if (type.equals(""))
/* 141 */         errors.add("item type is required");
/* 142 */       if (name.equals("")) {
/* 143 */         errors.add("item nam is required");
/*     */       }
/* 145 */       if (errors.size() > 0) {
/* 146 */         String row = req.getParameter("row");
/* 147 */         req.setAttribute("errorRow", row);
/* 148 */         req.setAttribute("messages", errors);
/*     */       }
/*     */       else
/*     */       {
/* 152 */         Entity Item = new Entity("Item");
/* 153 */         Item.setProperty("vendor", "Donation");
/* 154 */         Item.setProperty("itemType", type);
/* 155 */         Item.setProperty("itemName", name.toUpperCase());
/* 156 */         Item.setProperty("price", price);
/* 157 */         Item.setProperty("qtyFloor", "1");
/* 158 */         Item.setProperty("qtyWarehouse", "0");
/* 159 */         Item.setProperty("farmLocation", farmBase);
/* 160 */         Item.setProperty("cost", "0.00");
/* 161 */         Item.setProperty("itemNumber", itemNumber.toUpperCase());
/* 162 */         Item.setProperty("location", "As-Is");
/* 163 */         Item.setProperty("transactionType", "Receipt");
/* 164 */         Item.setProperty("status", "Active");
/* 165 */         Item.setProperty("createdBy", user.getNickname());
/* 166 */         Item.setProperty("creationDate", new Date());
/*     */ 
/* 169 */         ApplicationDao.createOrUpdateEntity(Item, user);
/*     */ 
/* 172 */         updateDonation(req, "Added To Inventory", user);
/*     */       }
/*     */ 
/* 175 */       getDonations(req.getParameter("truck"), req.getParameter("arrivalDate"), "Onboard", req);
/* 176 */       url = "/jsp/arrivals/details.jsp";
/*     */     }
/* 178 */     if ("Zipcode Setup".equals(action)) {
/* 179 */       ArrayList list = getZipcodes((String)req.getSession().getAttribute("farmBase"));
/* 180 */       req.setAttribute("results", list);
/* 181 */       url = "/jsp/routes/zipcode_setup.jsp";
/*     */     }
/* 183 */     if ("Zone Setup".equals(action)) {
/* 184 */       ArrayList list = getZipcodes((String)req.getSession().getAttribute("farmBase"));
/* 185 */       req.setAttribute("results", list);
/* 186 */       url = "/jsp/routes/zone_setup.jsp";
/*     */     }
/*     */ 
/* 189 */     if ("Save Zipcodes".equals(action)) {
/* 190 */       String sSize = req.getParameter("size");
/* 191 */       if (sSize == null) sSize = "0";
/* 192 */       int size = Integer.parseInt(sSize);
/*     */ 
/* 194 */       for (int a = 0; a < size; a++) {
/* 195 */         String zip = req.getParameter("zipcode" + a);
/* 196 */         String seq = req.getParameter("sequence" + a);
/* 197 */         Entity entity = new Entity("Zipcode");
/* 198 */         entity.setProperty("zipcode", zip);
/* 199 */         entity.setProperty("farmBase", (String)req.getSession().getAttribute("farmBase"));
/* 200 */         entity.setProperty("sequence", new Integer(seq));
/* 201 */         ApplicationDao.createOrUpdateEntity(entity, user);
/*     */       }
/* 203 */       url = "/jsp/main.jsp";
/*     */     }
/* 205 */     if ("Save Zones".equals(action)) {
/* 206 */       String sSize = req.getParameter("size");
/* 207 */       if (sSize == null) sSize = "0";
/* 208 */       int size = Integer.parseInt(sSize);
/*     */ 
/* 210 */       for (int a = 0; a < size; a++) {
/* 211 */         String k = req.getParameter("key_" + a);
/* 212 */         Entity entity = new Entity("Zipcode");
/*     */ 
/* 214 */         if (k != null) {
/* 215 */           Key key = KeyFactory.stringToKey(req.getParameter("key_" + a));
/* 216 */           entity = ApplicationDao.getEntity(key);
/*     */         }
/*     */ 
/* 219 */         String zip = req.getParameter("zipcode_" + a);
/* 220 */         String zone = req.getParameter("zone_" + zip);
/* 221 */         entity.setProperty("zipcode", zip);
/* 222 */         entity.setProperty("farmBase", (String)req.getSession().getAttribute("farmBase"));
/* 223 */         entity.setProperty("zone", zone);
/* 224 */         ApplicationDao.createOrUpdateEntity(entity, user);
/*     */       }
/* 226 */       url = "/jsp/main.jsp";
/*     */     }
/* 228 */     else if (("Assign Trucks".equals(action)) || ("Next".equals(action)))
/*     */     {
/* 232 */       String date = req.getParameter("dispatchDate");
/* 233 */       ArrayList parameters = new ArrayList();
/* 234 */       SearchParameter param = new SearchParameter("dispatchDate", date, Query.FilterOperator.EQUAL);
/* 235 */       parameters.add(param);
/* 236 */       param = new SearchParameter("status", "Pending", Query.FilterOperator.EQUAL);
/* 237 */       parameters.add(param);
/* 238 */       ArrayList results = ApplicationDao.getEntities("Dispatch", parameters, null, null, farmBase);
/* 239 */       req.setAttribute("dispatchCount", Integer.valueOf(results.size()));
/* 240 */       req.setAttribute("next", "");
/* 241 */       req.setAttribute("dispatchDate", date);
/* 242 */       url = "/jsp/routes/trucks.jsp";
/* 243 */     } else if ("Generate Routes".equals(action))
/*     */     {
/* 245 */       int trucks = Integer.parseInt(req.getParameter("truckCount"));
/* 246 */       String date = req.getParameter("dispatchDate");
/*     */ 
/* 248 */       ArrayList truckList = (ArrayList)req.getSession().getAttribute("ddlTruckList");
/* 249 */       ArrayList zipcodeList = getZipcodes((String)req.getSession().getAttribute("farmBase"));
/*     */ 
/* 252 */       ArrayList parameters = new ArrayList();
/* 253 */       SearchParameter param = new SearchParameter("dispatchDate", date, Query.FilterOperator.EQUAL);
/* 254 */       parameters.add(param);
/* 255 */       param = new SearchParameter("status", "Pending", Query.FilterOperator.EQUAL);
/* 256 */       parameters.add(param);
/* 257 */       ArrayList dispatchList = ApplicationDao.getEntities("Dispatch", parameters, null, null, farmBase);
/*     */ 
/* 260 */       int truckIndex = 0;
/* 261 */       int routes = dispatchList.size() / trucks;
/*     */ 
/* 265 */       int truckCounter = 0;
/* 266 */       for (int i = 0; i < zipcodeList.size(); i++) {
/* 267 */         Entity Zipcode = (Entity)zipcodeList.get(i);
/*     */ 
/* 271 */         for (int a = 0; a < dispatchList.size(); a++) {
/* 272 */           Entity Dispatch = (Entity)dispatchList.get(a);
/* 273 */           String zipcode1 = Zipcode.getProperty("zipcode").toString();
/* 274 */           String zipcode2 = Dispatch.getProperty("zipcode").toString();
/*     */ 
/* 277 */           if (zipcode1.equals(zipcode2))
/*     */           {
/* 280 */             if (truckCounter >= routes) {
/* 281 */               truckCounter = 0;
/* 282 */               truckIndex++;
/*     */             }
/* 284 */             truckCounter++;
/* 285 */             String truckNumber = (String)truckList.get(truckIndex);
/* 286 */             Dispatch.setProperty("truck", truckNumber);
/* 287 */             Dispatch.setProperty("status", "Assigned");
/* 288 */             ApplicationDao.createOrUpdateEntity(Dispatch, user);
/* 289 */             extractDonations(Dispatch, req, user);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 298 */       url = "/jsp/main.jsp";
/*     */     }
/* 300 */     req.getRequestDispatcher(req.getContextPath() + url).forward(req, resp);
/*     */   }
/*     */ 
/*     */   private void updateDonation(HttpServletRequest req, String status, User user)
/*     */   {
/* 305 */     String sKey = req.getParameter("donationKey");
/* 306 */     Key k = KeyFactory.stringToKey(sKey);
/* 307 */     Entity Donation = ApplicationDao.getEntity(k);
/* 308 */     Donation.setProperty("itemStatus", status);
/* 309 */     ApplicationDao.createOrUpdateEntity(Donation, user);
/*     */   }
/*     */ 
/*     */   protected void doPost(HttpServletRequest req, HttpServletResponse resp)
/*     */     throws ServletException, IOException
/*     */   {
/* 315 */     doGet(req, resp);
/*     */   }
/*     */ 
/*     */   private ArrayList getArrivals(String date)
/*     */   {
/* 321 */     Iterable results = null;
/* 322 */     Query q = new Query("Dispatch");
/*     */     try
/*     */     {
/* 325 */       q.addFilter("dispatchDate", Query.FilterOperator.EQUAL, date);
/* 326 */       q.addFilter("status", Query.FilterOperator.EQUAL, "Assigned");
/*     */ 
/* 330 */       PreparedQuery pq = datastore.prepare(q);
/* 331 */       results = pq.asIterable(FetchOptions.Builder.withLimit(100)
/* 332 */         .chunkSize(100));
/*     */     }
/*     */     catch (Exception e) {
/* 335 */       logger.log(Level.SEVERE, e.getMessage());
/* 336 */       logger.log(Level.INFO, q.toString());
/*     */     }
/*     */ 
/* 339 */     if (results == null) {
/* 340 */       return new ArrayList();
/*     */     }
/* 342 */     return ApplicationTools.convertIterableToList(results);
/*     */   }
/*     */ 
/*     */   private ArrayList getDonations(String truckNumber, String date, String status, HttpServletRequest req)
/*     */   {
/* 347 */     ArrayList parameters = new ArrayList();
/* 348 */     SearchParameter parameter = new SearchParameter("truck", truckNumber, Query.FilterOperator.EQUAL);
/* 349 */     parameters.add(parameter);
/* 350 */     parameter = new SearchParameter("arrivedDate", date, Query.FilterOperator.EQUAL);
/* 351 */     parameters.add(parameter);
/* 352 */     parameter = new SearchParameter("itemStatus", status, Query.FilterOperator.EQUAL);
/* 353 */     parameters.add(parameter);
/*     */ 
/* 355 */     ArrayList results = ApplicationDao.getEntities("DonationQueue", parameters, null, null, req.getSession().getAttribute("farmBase").toString());
/* 356 */     NodeList xmlDispatch = xml.getEntityProperties("Dispatch", req);
/* 357 */     req.getSession().setAttribute("results", results);
/* 358 */     req.setAttribute("xmlDispatch", xmlDispatch);
/* 359 */     req.setAttribute("arrivalDate", date);
/* 360 */     req.setAttribute("truck", truckNumber);
/*     */ 
/* 362 */     return results;
/*     */   }
/*     */ 
/*     */   private ArrayList getZipcodes(String farm)
/*     */   {
/* 367 */     Iterable results = null;
/* 368 */     Query q = new Query("Zipcode");
/* 369 */     q.addFilter("farmBase", Query.FilterOperator.EQUAL, farm);
/*     */     try
/*     */     {
/* 372 */       PreparedQuery pq = datastore.prepare(q);
/* 373 */       results = pq.asIterable(FetchOptions.Builder.withLimit(100)
/* 374 */         .chunkSize(100));
/*     */     }
/*     */     catch (Exception e) {
/* 377 */       logger.log(Level.SEVERE, e.getMessage());
/* 378 */       logger.log(Level.INFO, q.toString());
/*     */     }
/*     */ 
/* 381 */     return ApplicationTools.convertIterableToList(results);
/*     */   }
/*     */ 
/*     */   private void extractDonations(Entity Dispatch, HttpServletRequest req, User user)
/*     */   {
/* 387 */     NodeList xmlDispatch = xml.getEntityProperties("Dispatch", req);
/*     */ 
/* 389 */     int quantity = 0;
/* 390 */     for (int i = 18; i < xmlDispatch.getLength() - 1; i++) {
/* 391 */       quantity = 0;
/* 392 */       Element propertyElement = (Element)xmlDispatch.item(i);
/* 393 */       String name = propertyElement.getAttribute("name")
/* 394 */         .toString();
/*     */ 
/* 396 */       if ((Dispatch.getProperty(name) != null) && 
/* 397 */         (!Dispatch.getProperty(name).equals(""))) {
/*     */         try
/*     */         {
/* 400 */           quantity = Integer.parseInt(Dispatch.getProperty(
/* 401 */             name).toString()); } catch (NumberFormatException e) {
/* 402 */           quantity = 0;
/*     */         }
/*     */       }
/*     */ 
/* 406 */       if (quantity > 0)
/*     */       {
/* 408 */         for (int a = 0; a < quantity; a++) {
/* 409 */           Entity DonationQueue = new Entity("DonationQueue");
/* 410 */           String truck = "N/A";
/* 411 */           if (Dispatch.getProperty("truck") != null)
/* 412 */             truck = Dispatch.getProperty("truck").toString();
/* 413 */           DonationQueue.setProperty("dispatchKey", Dispatch.getKey());
/* 414 */           DonationQueue.setProperty("truck", truck);
/* 415 */           DonationQueue.setProperty("arrivedDate", Dispatch.getProperty("dispatchDate").toString());
/* 416 */           DonationQueue.setProperty("itemStatus", "Onboard");
/* 417 */           DonationQueue.setProperty(name, Integer.valueOf(1));
/* 418 */           ApplicationDao.createOrUpdateEntity(DonationQueue, user);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\development\workspace\faithfarmoss\WEB-INF\classes\
 * Qualified Name:     org.faithfarm.ArrivalsServlet
 * JD-Core Version:    0.6.2
 */