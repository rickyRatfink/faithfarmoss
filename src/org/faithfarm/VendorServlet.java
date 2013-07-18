/*     */ package org.faithfarm;
/*     */ 
/*     */ import com.google.appengine.api.datastore.Entity;
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.logging.Logger;
/*     */ import javax.servlet.RequestDispatcher;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ 
/*     */ public class VendorServlet extends BaseServlet
/*     */   implements Serializable
/*     */ {
/*  45 */   private static final Logger logger = Logger.getLogger(VendorServlet.class.getCanonicalName());
/*     */ 
/*     */   protected void doGet(HttpServletRequest req, HttpServletResponse resp)
/*     */     throws ServletException, IOException
/*     */   {
/*  52 */     super.doGet(req, resp);
/*     */ 
/*  54 */     HttpSession session = req.getSession(true);
/*     */ 
/*  56 */     session.removeAttribute("results");
/*     */ 
/*  61 */     String action = (String)req.getAttribute("action");
/*  62 */     if (action == null) {
/*  63 */       action = req.getParameter("action");
/*     */     }
/*  65 */     String vendorName = req.getParameter("name"); if (vendorName == null) vendorName = "";
/*  66 */     String vendorRep = req.getParameter("representative"); if (vendorRep == null) vendorRep = "";
/*  67 */     String addressLine1 = req.getParameter("addressLine1"); if (addressLine1 == null) addressLine1 = "";
/*  68 */     String city = req.getParameter("city"); if (city == null) city = "";
/*  69 */     String state = req.getParameter("state"); if (state == null) state = "";
/*  70 */     String zipcode = req.getParameter("zipcode"); if (zipcode == null) zipcode = "";
/*  71 */     String email = req.getParameter("email"); if (email == null) email = "";
/*  72 */     String vendorNumber = req.getParameter("number"); if (vendorNumber == null) vendorNumber = "";
/*  73 */     String orgType = req.getParameter("orgType"); if (orgType == null) orgType = "";
/*     */ 
/*  75 */     String h1 = req.getParameter("phone_areacode"); if (h1 == null) h1 = "";
/*  76 */     String h2 = req.getParameter("phone_first3"); if (h2 == null) h2 = "";
/*  77 */     String h3 = req.getParameter("phone_last4"); if (h3 == null) h3 = "";
/*  78 */     String phone = "(" + h1 + ")" + h2 + "-" + h3;
/*     */ 
/*  80 */     if ("Search".equals(action))
/*     */     {
/*  82 */       Iterable list = Vendor.getAllVendors(vendorNumber, vendorName);
/*  83 */       session.setAttribute("results", list);
/*     */ 
/*  85 */       req.getRequestDispatcher(req.getContextPath() + "/jsp/vendor/results.jsp").forward(req, resp);
/*     */     }
/*  87 */     else if ("Add Vendor".equals(action)) {
/*  88 */       req.getRequestDispatcher(req.getContextPath() + "/jsp/vendor/add.jsp").forward(req, resp);
/*  89 */     } else if ("Save".equals(action)) {
/*  90 */       ArrayList messages = Vendor.saveVendor(vendorName, vendorRep, addressLine1, 
/*  91 */         city, state, zipcode, 
/*  92 */         email, vendorNumber, phone, orgType, (String)req.getSession().getAttribute("farmBase"));
/*     */ 
/*  94 */       if (messages == null) {
/*  95 */         Iterable list = Vendor.getAllVendors();
/*  96 */         session.setAttribute("results", list);
/*  97 */         req.getRequestDispatcher(req.getContextPath() + "/jsp/vendor/results.jsp").forward(req, resp);
/*     */       }
/*     */       else
/*     */       {
/* 101 */         req.setAttribute("messages", messages);
/* 102 */         req.getRequestDispatcher(req.getContextPath() + "/jsp/vendor/add.jsp").forward(req, resp);
/*     */       }
/*     */     }
/* 105 */     else if ("Delete".equals(action)) {
/* 106 */       Entity e = Vendor.getSingleVendor(vendorNumber);
/*     */ 
/* 108 */       ArrayList list = Vendor.doDelete(e);
/* 109 */       if (list == null) {
/* 110 */         Iterable results = Vendor.getAllVendors();
/* 111 */         session.setAttribute("results", results);
/* 112 */         req.getRequestDispatcher(req.getContextPath() + "/jsp/vendor/results.jsp").forward(req, resp);
/*     */       }
/*     */       else {
/* 115 */         req.getRequestDispatcher(req.getContextPath() + "/jsp/vendor/results.jsp").forward(req, resp);
/*     */       }
/* 117 */     } else if ("Edit".equals(action)) {
/* 118 */       Entity e = Vendor.getSingleVendor(vendorNumber);
/* 119 */       session.setAttribute("entity", e);
/* 120 */       req.getRequestDispatcher(req.getContextPath() + "/jsp/vendor/edit.jsp").forward(req, resp);
/*     */     }
/* 123 */     else if ("Save Changes".equals(action))
/*     */     {
/* 125 */       ArrayList messages = Vendor.saveVendor(vendorName, vendorRep, addressLine1, 
/* 126 */         city, state, zipcode, 
/* 127 */         email, vendorNumber, phone, orgType, (String)req.getSession().getAttribute("farmBase"));
/*     */ 
/* 129 */       if (messages == null) {
/* 130 */         Iterable list = Vendor.getAllVendors();
/* 131 */         session.setAttribute("results", list);
/* 132 */         req.getRequestDispatcher(req.getContextPath() + "/jsp/vendor/results.jsp").forward(req, resp);
/*     */       }
/*     */       else
/*     */       {
/* 136 */         req.setAttribute("messages", messages);
/* 137 */         req.getRequestDispatcher(req.getContextPath() + "/jsp/vendor/add.jsp").forward(req, resp);
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 154 */       req.getRequestDispatcher(req.getContextPath() + "/jsp/vendor/index.jsp").forward(req, resp);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doPost(HttpServletRequest req, HttpServletResponse resp)
/*     */     throws ServletException, IOException
/*     */   {
/* 165 */     doGet(req, resp);
/*     */   }
/*     */ }

/* Location:           C:\development\workspace\faithfarmoss\WEB-INF\classes\
 * Qualified Name:     org.faithfarm.VendorServlet
 * JD-Core Version:    0.6.2
 */