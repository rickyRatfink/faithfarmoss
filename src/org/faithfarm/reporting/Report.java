/*     */ package org.faithfarm.reporting;
/*     */ 
/*     */ import com.google.appengine.api.datastore.DatastoreService;
/*     */ import com.google.appengine.api.datastore.DatastoreServiceFactory;
/*     */ import com.google.appengine.api.datastore.Entity;
/*     */ import com.google.appengine.api.datastore.FetchOptions;
/*     */ import com.google.appengine.api.datastore.FetchOptions.Builder;
/*     */ import com.google.appengine.api.datastore.PreparedQuery;
/*     */ import com.google.appengine.api.datastore.Query;
/*     */ import com.google.appengine.api.datastore.Query.FilterOperator;
/*     */ import java.io.Serializable;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Date;
/*     */ import java.util.logging.Logger;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.faithfarm.Item;
/*     */ import org.faithfarm.dataobjects.DailySalesReport;
/*     */ import org.faithfarm.dataobjects.Person;
/*     */ import org.faithfarm.dataobjects.PhoneOperatorReport;
/*     */ import org.faithfarm.dataobjects.SearchParameter;
/*     */ import org.faithfarm.datawriters.ApplicationDao;
/*     */ import org.faithfarm.utilities.ApplicationTools;
/*     */ 
/*     */ public class Report
/*     */   implements Serializable
/*     */ {
/*  31 */   private static final Logger logger = Logger.getLogger(Person.class
/*  32 */     .getCanonicalName());
/*     */ 
/*  34 */   private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
/*     */ 
/*     */   public static void generateInventoryReport(String farm, HttpServletRequest req)
/*     */   {
/*  38 */     ArrayList parameters = new ArrayList();
/*  39 */     SearchParameter parameter = null;
/*  40 */     String vendor = req.getParameter("vendor");
/*  41 */     String itemType = req.getParameter("itemType");
/*  42 */     String status = req.getParameter("status");
/*     */ 
/*  44 */     if ((vendor != null) && (!vendor.trim().equals(""))) {
/*  45 */       parameter = new SearchParameter("vendor", vendor, Query.FilterOperator.EQUAL);
/*  46 */       parameters.add(parameter);
/*     */     }
/*  48 */     if ((itemType != null) && (!itemType.trim().equals(""))) {
/*  49 */       parameter = new SearchParameter("itemType", itemType, Query.FilterOperator.EQUAL);
/*  50 */       parameters.add(parameter);
/*     */     }
/*  52 */     if ((status != null) && (!status.trim().equals(""))) {
/*  53 */       parameter = new SearchParameter("status", status, Query.FilterOperator.EQUAL);
/*  54 */       parameters.add(parameter);
/*     */     }
/*     */ 
/*  57 */     ArrayList results = ApplicationDao.getEntities("Item", parameters, null, null, farm, -1);
/*  58 */     req.setAttribute("results", results);
/*     */   }
/*     */ 
/*     */   public static ArrayList generateDailySalesReport(String salesDate, String farm, HttpServletRequest req)
/*     */   {
/*  64 */     ArrayList sales = new ArrayList();
/*  65 */     Query q = new Query("SalesOrder");
/*  66 */     q.addFilter("status", Query.FilterOperator.IN, 
/*  67 */       Arrays.asList(new String[] { 
/*  67 */       "Paid (Cash)", "Paid (Credit Card)" }));
/*  68 */     q.addFilter("salesOrderDate", Query.FilterOperator.EQUAL, salesDate);
/*  69 */     PreparedQuery pq = datastore.prepare(q);
/*  70 */     sales = 
/*  71 */       ApplicationTools.convertIterableToList(pq.asIterable(
/*  72 */       FetchOptions.Builder.withLimit(200).chunkSize(200)));
/*     */ 
/*  74 */     ArrayList report = new ArrayList();
/*  75 */     ArrayList report2 = new ArrayList();
/*     */ 
/*  77 */     for (int sale = 0; sale < sales.size(); sale++) {
/*  78 */       Entity SalesOrder = (Entity)sales.get(sale);
/*  79 */       for (int i = 0; i < 9; i++) {
/*  80 */         String itemNumber = SalesOrder.getProperty("itemNumber" + i)
/*  81 */           .toString();
/*  82 */         int qty = Integer.parseInt(SalesOrder.getProperty(
/*  83 */           "quantity" + i).toString());
/*     */ 
/*  85 */         String location = SalesOrder.getProperty("location" + i)
/*  86 */           .toString();
/*  87 */         String price = SalesOrder.getProperty("price" + i).toString();
/*     */ 
/*  89 */         if (!itemNumber.equals("")) {
/*  90 */           ArrayList parameters = new ArrayList();
/*  91 */           SearchParameter parameter = new SearchParameter(
/*  92 */             "itemNumber", itemNumber, Query.FilterOperator.EQUAL);
/*  93 */           parameters.add(parameter);
/*  94 */           ArrayList results = ApplicationDao.getEntities("Item", 
/*  95 */             parameters, null, null, farm);
/*  96 */           Entity Item = null;
/*  97 */           if (results != null) {
/*  98 */             Item = (Entity)results.get(0);
/*     */ 
/* 100 */             DailySalesReport Report = new DailySalesReport();
/* 101 */             Report.setItemNumber(Item.getProperty("itemNumber")
/* 102 */               .toString());
/* 103 */             Report.setItemName(Item.getProperty("itemName")
/* 104 */               .toString());
/* 105 */             Report.setVendor(Item.getProperty("vendor").toString());
/* 106 */             int qty1 = Integer.parseInt(Item.getProperty(
/* 107 */               "qtyWarehouse").toString());
/* 108 */             Report.setWarehouseQuantity(qty1);
/* 109 */             int qty2 = Integer.parseInt(Item
/* 110 */               .getProperty("qtyFloor").toString());
/* 111 */             Report.setFloorQuantity(qty2);
/* 112 */             Report.setTotalQuantity(qty1 + qty2);
/* 113 */             Report.setPar(0);
/* 114 */             Report.setPrice(price);
/* 115 */             Report.setLocation(location);
/* 116 */             if ("1/W".equals(location))
/* 117 */               Report.setQty1W(qty);
/* 118 */             if ("1/F".equals(location))
/* 119 */               Report.setQty1F(qty);
/* 120 */             if ("R/O".equals(location)) {
/* 121 */               Report.setQty1RO(qty);
/*     */             }
/* 123 */             int qtySold = qty;
/*     */ 
/* 125 */             double salesPrice = Double.valueOf(price)
/* 126 */               .doubleValue() * qtySold;
/* 127 */             Report.setQuantitySold(qtySold);
/* 128 */             Report.setSalesPrice(salesPrice);
/*     */ 
/* 130 */             report.add(Report);
/*     */           }
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 138 */     DailySalesReport[] newSalesReport = new DailySalesReport[report.size()];
/* 139 */     ArrayList newList = new ArrayList();
/* 140 */     int item = 0;
/*     */ 
/* 142 */     Boolean[] flag = new Boolean[report.size()];
/*     */ 
/* 144 */     for (int a = 0; a < report.size(); a++) {
/* 145 */       flag[a] = Boolean.valueOf(false);
/* 146 */       newSalesReport[a] = new DailySalesReport();
/*     */     }
/*     */ 
/* 149 */     for (int a = 0; a < report.size(); a++) {
/* 150 */       DailySalesReport salesReport = (DailySalesReport)report.get(a);
/* 151 */       String itemNumber = salesReport.getItemNumber();
/*     */ 
/* 153 */       for (int b = 0; b < report.size(); b++) {
/* 154 */         DailySalesReport tempSalesReport = 
/* 155 */           (DailySalesReport)report
/* 155 */           .get(b);
/* 156 */         String tempItemNumber = tempSalesReport.getItemNumber();
/* 157 */         int tempQtySold = tempSalesReport.getQuantitySold();
/* 158 */         int tempQty1W = tempSalesReport.getQty1W();
/* 159 */         int tempQty1F = tempSalesReport.getQty1F();
/* 160 */         int tempQty1RO = tempSalesReport.getQty1RO();
/*     */ 
/* 162 */         String tempLocation = tempSalesReport.getLocation();
/*     */ 
/* 165 */         if ((tempItemNumber.equals(itemNumber)) && (!flag[b].booleanValue())) {
/* 166 */           int initQty = 0;
/*     */           try {
/* 168 */             initQty = newSalesReport[a].getQuantitySold();
/*     */           } catch (Exception e) {
/* 170 */             initQty = 0;
/*     */           }
/* 172 */           int initQty1W = 0;
/*     */           try {
/* 174 */             initQty1W = newSalesReport[a].getQty1W();
/*     */           } catch (Exception e) {
/* 176 */             initQty1W = 0;
/*     */           }
/* 178 */           int initQty1F = 0;
/*     */           try {
/* 180 */             initQty1F = newSalesReport[a].getQty1F();
/*     */           } catch (Exception e) {
/* 182 */             initQty1F = 0;
/*     */           }
/* 184 */           int initQty1RO = 0;
/*     */           try {
/* 186 */             initQty1RO = newSalesReport[a].getQty1RO();
/*     */           } catch (Exception e) {
/* 188 */             initQty1RO = 0;
/*     */           }
/*     */ 
/* 191 */           newSalesReport[a].setItemNumber(tempItemNumber);
/* 192 */           newSalesReport[a].setQuantitySold(initQty + tempQtySold);
/* 193 */           if ("1/W".equals(tempLocation))
/* 194 */             newSalesReport[a].setQty1W(initQty1W + tempQty1W);
/* 195 */           if ("1/F".equals(tempLocation))
/* 196 */             newSalesReport[a].setQty1F(initQty1F + tempQty1F);
/* 197 */           if ("R/O".equals(tempLocation))
/* 198 */             newSalesReport[a].setQty1RO(initQty1RO + tempQty1RO);
/* 199 */           newSalesReport[a]
/* 200 */             .setItemName(tempSalesReport.getItemName());
/* 201 */           newSalesReport[a].setPar(tempSalesReport.getPar());
/* 202 */           newSalesReport[a].setPrice(tempSalesReport.getPrice());
/* 203 */           newSalesReport[a].setWarehouseQuantity(tempSalesReport
/* 204 */             .getWarehouseQuantity());
/* 205 */           newSalesReport[a].setFloorQuantity(tempSalesReport
/* 206 */             .getFloorQuantity());
/* 207 */           newSalesReport[a].setSalesPrice(tempSalesReport
/* 208 */             .getSalesPrice());
/* 209 */           newSalesReport[a].setVendor(tempSalesReport.getVendor());
/* 210 */           newSalesReport[a].setTotalQuantity(tempSalesReport
/* 211 */             .getTotalQuantity());
/* 212 */           flag[b] = Boolean.valueOf(true);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 217 */     ArrayList condensedList = new ArrayList();
/* 218 */     for (int a = 0; a < report.size(); a++) {
/* 219 */       if (newSalesReport[a].getItemName() != null) {
/* 220 */         Double salesPrice = Double.valueOf(
/* 221 */           Double.valueOf(newSalesReport[a].getPrice()).doubleValue() * 
/* 222 */           newSalesReport[a].getQuantitySold());
/* 223 */         newSalesReport[a].setSalesPrice(salesPrice.doubleValue());
/* 224 */         condensedList.add(newSalesReport[a]);
/*     */       }
/*     */     }
/*     */ 
/* 228 */     return condensedList;
/*     */   }
/*     */ 
/*     */   public static ArrayList generateDailySalesReport2(String salesDate, String farm, HttpServletRequest req)
/*     */   {
/* 234 */     ArrayList parameters = new ArrayList();
/* 235 */     ArrayList items = ApplicationTools.convertIterableToList(Item.search(
/* 236 */       null, null, null, null, null, null));
/* 237 */     SearchParameter parameter = new SearchParameter("salesOrderDate", 
/* 238 */       salesDate, Query.FilterOperator.EQUAL);
/*     */ 
/* 240 */     parameters.add(parameter);
/* 241 */     parameter = new SearchParameter("status", "Pending", 
/* 242 */       Query.FilterOperator.EQUAL);
/* 243 */     parameters.add(parameter);
/* 244 */     ArrayList sales = ApplicationDao.getEntities("SalesOrder", parameters, 
/* 245 */       null, null, farm);
/* 246 */     int[] quantities = new int[items.size()];
/*     */ 
/* 248 */     ArrayList report = new ArrayList();
/*     */ 
/* 250 */     for (int item = 0; item < items.size(); item++) {
/* 251 */       Entity Item = (Entity)items.get(item);
/* 252 */       DailySalesReport Report = new DailySalesReport();
/* 253 */       Report.setItemNumber(Item.getProperty("itemNumber").toString());
/* 254 */       Report.setItemName(Item.getProperty("itemName").toString());
/* 255 */       Report.setVendor(Item.getProperty("vendor").toString());
/* 256 */       int qty1 = Integer.parseInt(Item.getProperty("qtyWarehouse")
/* 257 */         .toString());
/* 258 */       Report.setWarehouseQuantity(qty1);
/* 259 */       int qty2 = 
/* 260 */         Integer.parseInt(Item.getProperty("qtyFloor").toString());
/* 261 */       Report.setFloorQuantity(qty2);
/* 262 */       Report.setTotalQuantity(qty1 + qty2);
/* 263 */       int par = 0;
/* 264 */       if ((Item.getProperty("minLevel") != null) && 
/* 265 */         (!"".equals(Item.getProperty("minLevel"))))
/* 266 */         par = Integer.parseInt(Item.getProperty("minLevel").toString());
/* 267 */       Report.setPar(par);
/* 268 */       Report.setQuantitySold(0);
/* 269 */       Report.setPrice(Item.getProperty("price").toString());
/* 270 */       Report.setSalesPrice(0.0D);
/* 271 */       report.add(Report);
/*     */     }
/*     */ 
/* 276 */     for (int sale = 0; sale < sales.size(); sale++) {
/* 277 */       Entity SalesOrder = (Entity)sales.get(sale);
/* 278 */       for (int i = 0; i < 9; i++) {
/* 279 */         String itemNumber = SalesOrder.getProperty("itemNumber" + i)
/* 280 */           .toString();
/* 281 */         int qty = Integer.parseInt(SalesOrder.getProperty(
/* 282 */           "quantity" + i).toString());
/*     */ 
/* 284 */         String location = SalesOrder.getProperty("location" + i)
/* 285 */           .toString();
/* 286 */         String price = SalesOrder.getProperty("price" + i).toString();
/*     */ 
/* 288 */         for (int item = 0; item < items.size(); item++)
/*     */         {
/* 290 */           Entity Item = (Entity)items.get(item);
/* 291 */           DailySalesReport reportItem = 
/* 292 */             (DailySalesReport)report
/* 292 */             .get(item);
/*     */ 
/* 295 */           if (itemNumber.equals(Item.getProperty("itemNumber")
/* 295 */             .toString()))
/*     */           {
/* 297 */             DailySalesReport Report = new DailySalesReport();
/* 298 */             Report.setItemNumber(Item.getProperty("itemNumber")
/* 299 */               .toString());
/* 300 */             Report.setItemName(Item.getProperty("itemName")
/* 301 */               .toString());
/* 302 */             Report.setVendor(Item.getProperty("vendor").toString());
/* 303 */             int qty1 = Integer.parseInt(Item.getProperty(
/* 304 */               "qtyWarehouse").toString());
/* 305 */             Report.setWarehouseQuantity(qty1);
/* 306 */             int qty2 = Integer.parseInt(Item
/* 307 */               .getProperty("qtyFloor").toString());
/* 308 */             Report.setFloorQuantity(qty2);
/* 309 */             Report.setTotalQuantity(qty1 + qty2);
/* 310 */             Report.setPar(0);
/* 311 */             Report.setPrice(price);
/* 312 */             Report.setLocation(location);
/* 313 */             int qtySold = reportItem.getQuantitySold();
/* 314 */             if ("1/W".equals(location))
/* 315 */               Report.setQty1W(qty);
/* 316 */             if ("1/F".equals(location))
/* 317 */               Report.setQty1F(qty);
/* 318 */             if ("R/O".equals(location)) {
/* 319 */               Report.setQty1RO(qty);
/*     */             }
/* 321 */             double salesPrice = Double.valueOf(price)
/* 322 */               .doubleValue() * (qty + qtySold);
/* 323 */             Report.setQuantitySold(qtySold + qty);
/* 324 */             Report.setSalesPrice(salesPrice);
/* 325 */             quantities[item] += qty;
/* 326 */             report.set(item, Report);
/*     */           }
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 334 */     return report;
/*     */   }
/*     */ 
/*     */   public static ArrayList generateDispatchTicketPickUpReport(String startDate, String farm, HttpServletRequest req)
/*     */   {
/* 339 */     Date date = new Date(startDate);
/* 340 */     SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
/* 341 */     String weekday = "";
/* 342 */     ArrayList report1 = new ArrayList();
/* 343 */     ArrayList report2 = new ArrayList();
/* 344 */     ArrayList closeouts = new ArrayList();
/* 345 */     String sDate = "";
/* 346 */     int source1 = 0; int source2 = 0; int source3 = 0; int source4 = 0; int source5 = 0; int source6 = 0; int source7 = 0; int source8 = 0;
/* 347 */     Entity CloseOut = null;
/*     */ 
/* 349 */     for (int i = 0; i < 6; i++)
/*     */     {
/* 351 */       int MILLIS_IN_DAY = 86400000 * i;
/* 352 */       sDate = dateFormat.format(Long.valueOf(date.getTime() + MILLIS_IN_DAY));
/*     */ 
/* 355 */       ArrayList parameters = new ArrayList();
/* 356 */       SearchParameter parameter = new SearchParameter("callDate", sDate, Query.FilterOperator.EQUAL);
/* 357 */       parameters.add(parameter);
/*     */ 
/* 362 */       ArrayList log = ApplicationDao.getEntities("CallLog", parameters, null, null, null);
/*     */ 
/* 366 */       parameters = new ArrayList();
/* 367 */       parameter = new SearchParameter("date", sDate, Query.FilterOperator.EQUAL);
/* 368 */       parameters.add(parameter);
/* 369 */       closeouts = ApplicationDao.getEntities("DispatchCloseout", parameters, null, null, farm);
/*     */ 
/* 371 */       if ((closeouts == null) || (closeouts.size() == 0))
/* 372 */         CloseOut = new Entity("DispatchCloseout");
/* 373 */       else if (closeouts.size() > 1)
/* 374 */         CloseOut = (Entity)closeouts.get(closeouts.size() - 1);
/*     */       else {
/* 376 */         CloseOut = (Entity)closeouts.get(0);
/*     */       }
/* 378 */       int total1 = 0; int total2 = 0; int total3 = 0; int total4 = 0; int total5 = 0; int total6 = 0; int total7 = 0;
/*     */ 
/* 380 */       PhoneOperatorReport por = new PhoneOperatorReport();
/* 381 */       int newTickets = 0; int previous = 0; int cancel = 0; int donation = 0; int reschedule = 0; int other = 0; int reject = 0;
/*     */ 
/* 383 */       if (i == 0) weekday = "Mon";
/* 384 */       if (i == 1) weekday = "Tues";
/* 385 */       if (i == 2) weekday = "Wed";
/* 386 */       if (i == 3) weekday = "Thur";
/* 387 */       if (i == 4) weekday = "Fri";
/* 388 */       if (i == 5) weekday = "Sat";
/*     */ 
/* 390 */       for (int a = 0; a < log.size(); a++)
/*     */       {
/* 392 */         Entity callLog = (Entity)log.get(a);
/* 393 */         String source = callLog.getProperty("source").toString();
/* 394 */         String callType = callLog.getProperty("callType").toString();
/*     */ 
/* 397 */         if ("new ticket".equals(callType))
/* 398 */           newTickets++;
/* 399 */         if ("previously written ticket inquiry".equals(callType))
/* 400 */           previous++;
/* 401 */         if ("cancel ticket".equals(callType))
/* 402 */           cancel++;
/* 403 */         if ("donation inquiry".equals(callType))
/* 404 */           donation++;
/* 405 */         if ("reschedule".equals(callType))
/* 406 */           reschedule++;
/* 407 */         if ("other inquiry".equals(callType))
/* 408 */           other++;
/* 409 */         if ("phone reject".equals(callType)) {
/* 410 */           reject++;
/*     */         }
/*     */ 
/* 413 */         if ("yellow pages".equals(source))
/* 414 */           source1++;
/* 415 */         if ("trucks".equals(source))
/* 416 */           source2++;
/* 417 */         if ("news/magazine".equals(source))
/* 418 */           source3++;
/* 419 */         if ("repeat donor/customer".equals(source))
/* 420 */           source4++;
/* 421 */         if ("radio/tv ad".equals(source))
/* 422 */           source5++;
/* 423 */         if ("friend/relative".equals(source))
/* 424 */           source6++;
/* 425 */         if ("internet".equals(source))
/* 426 */           source7++;
/* 427 */         if ("other".equals(source)) {
/* 428 */           source8++;
/*     */         }
/*     */       }
/* 431 */       por.setNewTicket(Integer.valueOf(newTickets));
/* 432 */       por.setCancelled(Integer.valueOf(cancel));
/* 433 */       por.setDonation(Integer.valueOf(donation));
/* 434 */       por.setRejects(Integer.valueOf(reject));
/* 435 */       por.setReschedule(Integer.valueOf(reschedule));
/* 436 */       por.setPrevious(Integer.valueOf(previous));
/* 437 */       por.setOther(Integer.valueOf(other));
/* 438 */       por.setDayOfWeek(weekday);
/*     */ 
/* 440 */       report1.add(por);
/* 441 */       report2.add(CloseOut);
/*     */     }
/*     */ 
/* 444 */     req.setAttribute("report1", report1);
/* 445 */     req.setAttribute("startDate", startDate);
/* 446 */     req.setAttribute("endDate", sDate);
/* 447 */     req.setAttribute("source1", Integer.valueOf(source1));
/* 448 */     req.setAttribute("source2", Integer.valueOf(source2));
/* 449 */     req.setAttribute("source3", Integer.valueOf(source3));
/* 450 */     req.setAttribute("source4", Integer.valueOf(source4));
/* 451 */     req.setAttribute("source5", Integer.valueOf(source5));
/* 452 */     req.setAttribute("source6", Integer.valueOf(source6));
/* 453 */     req.setAttribute("source7", Integer.valueOf(source7));
/* 454 */     req.setAttribute("source8", Integer.valueOf(source8));
/* 455 */     req.setAttribute("report2", report2);
/*     */ 
/* 457 */     return new ArrayList();
/*     */   }
/*     */ }

/* Location:           C:\development\workspace\faithfarmoss\WEB-INF\classes\
 * Qualified Name:     org.faithfarm.reporting.Report
 * JD-Core Version:    0.6.2
 */