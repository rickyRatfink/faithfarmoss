/*     */ package org.faithfarm;
/*     */ 
/*     */ /*     */ import java.io.IOException;
/*     */ import java.util.Date;
/*     */ import java.util.List;

/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;

/*     */ import org.faithfarm.dataobjects.PurchaseOrder;
/*     */ import org.faithfarm.utilities.FFSession;

import com.google.appengine.api.datastore.DatastoreService;
/*     */ import com.google.appengine.api.datastore.DatastoreServiceFactory;
/*     */ import com.google.appengine.api.datastore.Entity;
/*     */ import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
/*     */ import com.google.appengine.api.datastore.Key;
/*     */ import com.google.appengine.api.datastore.KeyFactory;
/*     */ import com.google.appengine.api.datastore.Query;
/*     */ import com.google.appengine.api.users.User;
/*     */ import com.google.appengine.api.users.UserService;
/*     */ import com.google.appengine.api.users.UserServiceFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PurchaseOrderServlet extends BaseServlet
/*     */ {
/*  26 */   private static final UserService userService = UserServiceFactory.getUserService();
/*  27 */   private static final DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
/*     */   private static final long serialVersionUID = -5683468984463980193L;
/*     */ 
/*     */   protected void doGet(HttpServletRequest req, HttpServletResponse resp)
/*     */     throws IOException, ServletException
/*     */   {
/*  32 */     User user = userService.getCurrentUser();
/*  33 */     if (FFSession.needsRedirect(req, resp)) {
/*  34 */       return;
/*     */     }
/*  36 */     String action = req.getParameter("action");
/*  37 */     if (action == null) {
/*  38 */       List results = PurchaseOrder.searchPO(req.getParameter("po_number"), req.getParameter("vendor_company"), req.getParameter("vendor_name"), req.getParameter("shipto_name"));
/*  39 */       req.setAttribute("results", results);
/*  40 */       req.getRequestDispatcher(req.getContextPath() + "/jsp/po/index.jsp").forward(req, resp);
/*  41 */       return;
/*  42 */     }if (action.equals("create_po")) {
/*  43 */       String farmBase = (String)req.getSession(true).getAttribute("farmBase");
/*  44 */       req.setAttribute("farmBase", farmBase == null ? "" : farmBase);
/*  45 */       req.getRequestDispatcher(req.getContextPath() + "/jsp/po/create_po.jsp").forward(req, resp);
/*  46 */       return;
/*  47 */     }if (action.equals("create_po_post")) {
/*  48 */       Query metaQuery = new Query("PurchaseOrderMeta");
/*  49 */       List metaResults = datastore.prepare(metaQuery).asList(FetchOptions.Builder.withLimit(1));
/*  50 */       Entity metaData = null;
/*  51 */       if (metaResults.size() == 0) {
/*  52 */         metaData = new Entity("PurchaseOrderMeta");
/*  53 */         metaData.setProperty("nextNumber", Integer.valueOf(1));
/*     */       } else {
/*  55 */         metaData = (Entity)metaResults.get(0);
/*     */       }
/*  57 */       Integer nextNumber = Integer.valueOf(Integer.parseInt(metaData.getProperty("nextNumber").toString(), 10));
/*  58 */       String farm = req.getParameter("farm") == null ? "" : req.getParameter("farm").toString();
/*  59 */       String vendor_name = req.getParameter("vendor_name") == null ? "" : req.getParameter("vendor_name").toString();
/*  60 */       String vendor_company = req.getParameter("vendor_company") == null ? "" : req.getParameter("vendor_company").toString();
/*  61 */       String vendor_address = req.getParameter("vendor_address") == null ? "" : req.getParameter("vendor_address").toString();
/*  62 */       String vendor_city = req.getParameter("vendor_city") == null ? "" : req.getParameter("vendor_city").toString();
/*  63 */       String vendor_state = req.getParameter("vendor_state") == null ? "" : req.getParameter("vendor_state").toString();
/*  64 */       String vendor_zip = req.getParameter("vendor_zip") == null ? "" : req.getParameter("vendor_zip").toString();
/*  65 */       String vendor_phone = req.getParameter("vendor_phone") == null ? "" : req.getParameter("vendor_phone").toString();
/*  66 */       String shipto_name = req.getParameter("shipto_name") == null ? "" : req.getParameter("shipto_name").toString();
/*  67 */       String shipto_address = req.getParameter("shipto_address") == null ? "" : req.getParameter("shipto_address").toString();
/*  68 */       String shipto_city = req.getParameter("shipto_city") == null ? "" : req.getParameter("shipto_city").toString();
/*  69 */       String shipto_state = req.getParameter("shipto_state") == null ? "" : req.getParameter("shipto_state").toString();
/*  70 */       String shipto_zip = req.getParameter("shipto_zip") == null ? "" : req.getParameter("shipto_zip").toString();
/*  71 */       String shipto_phone = req.getParameter("shipto_phone") == null ? "" : req.getParameter("shipto_phone").toString();
/*  72 */       Entity purchase_order = new Entity("PurchaseOrder");
/*  73 */       purchase_order.setProperty("farm", farm);
/*  74 */       purchase_order.setProperty("po_number", nextNumber);
/*  75 */       purchase_order.setProperty("vendor_name", vendor_name);
/*  76 */       purchase_order.setProperty("vendor_company", vendor_company);
/*  77 */       purchase_order.setProperty("vendor_address", vendor_address);
/*  78 */       purchase_order.setProperty("vendor_city", vendor_city);
/*  79 */       purchase_order.setProperty("vendor_state", vendor_state);
/*  80 */       purchase_order.setProperty("vendor_zip", vendor_zip);
/*  81 */       purchase_order.setProperty("vendor_phone", vendor_phone);
/*  82 */       purchase_order.setProperty("shipto_name", shipto_name);
/*  83 */       purchase_order.setProperty("shipto_address", shipto_address);
/*  84 */       purchase_order.setProperty("shipto_city", shipto_city);
/*  85 */       purchase_order.setProperty("shipto_state", shipto_state);
/*  86 */       purchase_order.setProperty("shipto_zip", shipto_zip);
/*  87 */       purchase_order.setProperty("shipto_phone", shipto_phone);
/*  88 */       purchase_order.setProperty("items", "{\"items\":[],\"total\":0}");
/*  89 */       purchase_order.setProperty("created", Long.valueOf(new Date().getTime()));
/*  90 */       Util.persistEntity(purchase_order);
/*  91 */       metaData.setProperty("nextNumber", Integer.valueOf(nextNumber.intValue() + 1));
/*  92 */       Util.persistEntity(metaData);
/*  93 */       req.setAttribute("isSave", Boolean.valueOf(false));
/*  94 */       req.setAttribute("purchase_order", purchase_order);
/*  95 */       req.getRequestDispatcher(req.getContextPath() + "/jsp/po/edit_po.jsp").forward(req, resp);
/*  96 */     } else if (action.equals("print_po")) {
/*  97 */       String keyString = req.getParameter("po");
/*  98 */       Key poKey = KeyFactory.stringToKey(keyString);
/*     */       try {
/* 100 */         Entity po_entity = datastore.get(poKey);
/* 101 */         PurchaseOrder purchaseOrder = PurchaseOrder.getInstanceFromEntity(po_entity);
/* 102 */         req.setAttribute("purchase_order", purchaseOrder);
/* 103 */         req.getRequestDispatcher(req.getContextPath() + "/jsp/po/print_po.jsp").forward(req, resp);
/*     */       }
/*     */       catch (EntityNotFoundException localEntityNotFoundException) {
/*     */       }
/*     */     }
/* 108 */     else if (action.equals("edit_po")) {
/* 109 */       String keyString = req.getParameter("po");
/* 110 */       Key poKey = KeyFactory.stringToKey(keyString);
/*     */       try {
/* 112 */         Entity purchase_order = datastore.get(poKey);
/* 113 */         Boolean isSave = Boolean.valueOf(req.getParameter("save") != null);
/* 114 */         if (isSave.booleanValue()) {
/* 115 */           purchase_order.setProperty("vendor_name", req.getParameter("vendor_name"));
/* 116 */           purchase_order.setProperty("vendor_company", req.getParameter("vendor_company"));
/* 117 */           purchase_order.setProperty("vendor_address", req.getParameter("vendor_address"));
/* 118 */           purchase_order.setProperty("vendor_city", req.getParameter("vendor_city"));
/* 119 */           purchase_order.setProperty("vendor_state", req.getParameter("vendor_state"));
/* 120 */           purchase_order.setProperty("vendor_zip", req.getParameter("vendor_zip"));
/* 121 */           purchase_order.setProperty("vendor_phone", req.getParameter("vendor_phone"));
/* 122 */           purchase_order.setProperty("shipto_name", req.getParameter("shipto_name"));
/* 123 */           purchase_order.setProperty("shipto_address", req.getParameter("shipto_address"));
/* 124 */           purchase_order.setProperty("shipto_city", req.getParameter("shipto_city"));
/* 125 */           purchase_order.setProperty("shipto_zip", req.getParameter("shipto_zip"));
/* 126 */           purchase_order.setProperty("shipto_phone", req.getParameter("shipto_phone"));
/* 127 */           purchase_order.setProperty("items", req.getParameter("items"));
/* 128 */           Util.persistEntity(purchase_order);
/*     */         }
/* 130 */         req.setAttribute("purchase_order", purchase_order);
/* 131 */         req.setAttribute("isSave", isSave);
/* 132 */         req.getRequestDispatcher(req.getContextPath() + "/jsp/po/edit_po.jsp").forward(req, resp);
/*     */       } catch (EntityNotFoundException localEntityNotFoundException1) {
/*     */       }
/*     */     }
/* 136 */     else if (action.equals("add_item_form")) {
/* 137 */       req.getRequestDispatcher(req.getContextPath() + "/jsp/po/add_item_form.jsp").forward(req, resp);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doPost(HttpServletRequest req, HttpServletResponse resp)
/*     */     throws IOException, ServletException
/*     */   {
/* 144 */     doGet(req, resp);
/*     */   }
/*     */ }

/* Location:           C:\development\workspace\faithfarmoss\WEB-INF\classes\
 * Qualified Name:     org.faithfarm.PurchaseOrderServlet
 * JD-Core Version:    0.6.2
 */