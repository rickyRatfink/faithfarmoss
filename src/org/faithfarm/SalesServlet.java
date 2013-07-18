/*     */ package org.faithfarm;
/*     */ 
/*     */ import com.google.appengine.api.datastore.Entity;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.logging.Logger;
/*     */ import javax.servlet.RequestDispatcher;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.faithfarm.dataobjects.Person;
/*     */ import org.faithfarm.utilities.HTMLBuilder;
/*     */ 
/*     */ public class SalesServlet extends BaseServlet
/*     */ {
/*  17 */   private static final Logger logger = Logger.getLogger(CustomerServlet.class.getCanonicalName());
/*  18 */   private static final HTMLBuilder html = new HTMLBuilder();
/*  19 */   private Person person = new Person();
/*     */ 
/*     */   protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
/*     */   {
/*  23 */     super.doGet(req, resp);
/*     */ 
/*  25 */     String action = (String)req.getAttribute("action");
/*  26 */     if (action == null) {
/*  27 */       action = req.getParameter("action");
/*     */     }
/*  29 */     int size = 0;
/*  30 */     String sSize = req.getParameter("size");
/*  31 */     if (sSize != null)
/*  32 */       size = Integer.parseInt(sSize);
/*     */     else {
/*  34 */       size = 0;
/*     */     }
/*     */ 
/*  37 */     req.setAttribute("person", this.person);
/*     */ 
/*  39 */     String fName = req.getParameter("firstName");
/*  40 */     String lName = req.getParameter("lastName");
/*  41 */     String customerKey = fName + " " + lName;
/*     */ 
/*  43 */     Entity e = Customer.getSingleCustomer(customerKey);
/*  44 */     req.setAttribute("Customer", e);
/*     */ 
/*  46 */     if ("Search Customer".equals(action))
/*     */     {
/*  48 */       refreshItemList(size, req);
/*  49 */       if (e == null) {
/*  50 */         ArrayList messages = new ArrayList();
/*  51 */         messages.add("The customer entered could not be found.");
/*  52 */         req.setAttribute("messages", messages);
/*     */       }
/*  54 */       req.getRequestDispatcher(req.getContextPath() + "/jsp/forms/salesform.jsp").forward(req, resp);
/*     */     }
/*  56 */     else if ("Find Item".equals(action))
/*     */     {
/*  59 */       boolean increment = false;
/*     */ 
/*  61 */       for (int i = 0; i < size; i++)
/*     */       {
/*  63 */         String itemNumber = req.getParameter("itemNumber" + i);
/*  64 */         if ((itemNumber != null) && (!itemNumber.equals(""))) {
/*  65 */           e = Item.getSingleItem(itemNumber.toUpperCase());
/*  66 */           if (e == null) {
/*  67 */             ArrayList messages = new ArrayList();
/*  68 */             messages.add("The item#" + itemNumber + " entered could not be found.");
/*  69 */             req.setAttribute("messages", messages);
/*  70 */             req.setAttribute("Item" + i, null);
/*  71 */             size--;
/*     */           }
/*     */           else {
/*  74 */             increment = true;
/*  75 */             req.setAttribute("Item" + i, e);
/*     */           }
/*     */         }
/*     */         else {
/*  79 */           ArrayList messages = new ArrayList();
/*  80 */           messages.add("Please enter an item number.");
/*  81 */           req.setAttribute("messages", messages);
/*  82 */           req.setAttribute("Item" + i, null);
/*  83 */           size--;
/*     */         }
/*     */       }
/*     */ 
/*  87 */       if (increment)
/*  88 */         req.setAttribute("size", Integer.valueOf(size++));
/*     */       else
/*  90 */         req.setAttribute("size", Integer.valueOf(size));
/*  91 */       req.getRequestDispatcher(req.getContextPath() + "/jsp/forms/salesform.jsp").forward(req, resp);
/*     */     }
/*  93 */     else if ("Save Order".equals(action))
/*     */     {
/*  95 */       req.getRequestDispatcher(req.getContextPath() + "/jsp/forms/salesform.jsp").forward(req, resp);
/*     */     }
/*  97 */     else if ("Add".equals(action)) {
/*  98 */       req.setAttribute("size", Integer.valueOf(size++));
/*  99 */       req.getRequestDispatcher(req.getContextPath() + "/jsp/forms/salesform.jsp").forward(req, resp);
/*     */     }
/* 102 */     else if ("Print Order Form".equals(action)) {
/* 103 */       req.getRequestDispatcher(req.getContextPath() + "/jsp/forms/newfurn_print.jsp").forward(req, resp);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doPost(HttpServletRequest req, HttpServletResponse resp)
/*     */     throws ServletException, IOException
/*     */   {
/* 112 */     doGet(req, resp);
/*     */   }
/*     */ 
/*     */   protected void refreshItemList(int size, HttpServletRequest req)
/*     */   {
/* 117 */     for (int i = 0; i < size; i++)
/*     */     {
/* 119 */       String itemNumber = req.getParameter("itemNumber" + i);
/* 120 */       if (itemNumber != null) {
/* 121 */         Entity e = Item.getSingleItem(itemNumber.toUpperCase());
/* 122 */         req.setAttribute("Item" + i, e);
/*     */       }
/*     */     }
/* 125 */     req.setAttribute("size", Integer.valueOf(--size));
/*     */   }
/*     */ 
/*     */   public Person getPerson() {
/* 129 */     return this.person;
/*     */   }
/*     */ 
/*     */   public void setPerson(Person person) {
/* 133 */     this.person = person;
/*     */   }
/*     */ }

/* Location:           C:\development\workspace\faithfarmoss\WEB-INF\classes\
 * Qualified Name:     org.faithfarm.SalesServlet
 * JD-Core Version:    0.6.2
 */