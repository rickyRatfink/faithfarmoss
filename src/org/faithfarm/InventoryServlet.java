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
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.faithfarm.datawriters.PersonDao;
/*     */ import org.faithfarm.utilities.ApplicationTools;
/*     */ 
/*     */ public class InventoryServlet extends BaseServlet
/*     */ {
/*  21 */   private static final Logger logger = Logger.getLogger(InventoryServlet.class.getCanonicalName());
/*     */ 
/*     */   protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
/*     */   {
/*  25 */     super.doGet(req, resp);
/*  26 */     HttpSession session = req.getSession(true);
/*     */ 
/*  33 */     String action = (String)req.getAttribute("action");
/*  34 */     if (action == null) {
/*  35 */       action = req.getParameter("action");
/*     */     }
/*     */ 
/*  49 */     String vendorName = req.getParameter("vendor");
/*  50 */     if (vendorName == null)
/*  51 */       vendorName = "";
/*  52 */     String location = req.getParameter("location");
/*  53 */     if (location == null)
/*  54 */       location = "";
/*  55 */     String itemName = req.getParameter("itemName");
/*  56 */     if (itemName == null)
/*  57 */       itemName = "";
/*  58 */     String itemNumber = req.getParameter("itemNumber");
/*  59 */     if (itemNumber == null)
/*  60 */       itemNumber = "";
/*  61 */     String qty1 = req.getParameter("qtyWarehouse");
/*  62 */     if (qty1 == null)
/*  63 */       qty1 = "0";
/*  64 */     String qty2 = req.getParameter("qtyFloor");
/*  65 */     if (qty2 == null)
/*  66 */       qty2 = "0";
/*  67 */     String desc = req.getParameter("description");
/*  68 */     if (desc == null)
/*  69 */       desc = "";
/*  70 */     String cost = req.getParameter("cost");
/*  71 */     if (cost == null)
/*  72 */       cost = "0.00";
/*  73 */     String price = req.getParameter("price");
/*  74 */     if (price == null)
/*  75 */       price = "0.00";
/*  76 */     String minLevel = req.getParameter("min_level");
/*  77 */     if (minLevel == null)
/*  78 */       minLevel = "";
/*  79 */     String status = req.getParameter("status");
/*  80 */     if (status == null)
/*  81 */       status = "";
/*  82 */     String transactionType = req.getParameter("transactionType");
/*  83 */     if (transactionType == null)
/*  84 */       transactionType = "";
/*  85 */     String farmLocation = (String)session.getAttribute("farmBase");
/*  86 */     String startCursor = req.getParameter("cursor");
/*  87 */     String overwrite = req.getParameter("overwrite");
/*     */ 
/*  89 */     if ("Import".equals(action)) {
/*  90 */       for (int i = 0; i < 100; i++) {
/*  91 */         Entity item = new Entity("Item");
/*  92 */         item.setProperty("vendor", "Ashley");
/*  93 */         item.setProperty("location", "new furn");
/*  94 */         item.setProperty("itemNumber", "A" + i);
/*  95 */         item.setProperty("itemName", "name" + i);
/*  96 */         item.setProperty("price", "0.00");
/*  97 */         item.setProperty("qtyWarehouse", "0");
/*  98 */         item.setProperty("qtyFloor", "0");
/*  99 */         item.setProperty("description", "");
/* 100 */         item.setProperty("cost", "0.00");
/* 101 */         item.setProperty("minLevel", "");
/* 102 */         item.setProperty("status", "Active");
/* 103 */         item.setProperty("transactionType", "");
/* 104 */         Util.persistEntity(item);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 109 */     if ("Sort".equals(action)) {
/* 110 */       Iterable list1 = Item.search(itemNumber, itemName, desc, 
/* 111 */         location, vendorName, startCursor);
/* 112 */       ArrayList list = new ArrayList();
/* 113 */       list = ApplicationTools.convertIterableToList(list1);
/* 114 */       String direction = req.getParameter("sortDirection");
/* 115 */       if (("null".equals(direction)) || ("descending".equals(direction))) {
/* 116 */         list = ApplicationTools.convertIterableToList(Item.sort("Item", req.getParameter("property"), 
/* 117 */           "descending"));
/* 118 */         direction = "ascending";
/*     */       } else {
/* 120 */         list = ApplicationTools.convertIterableToList(Item.sort("Item", req.getParameter("property"), 
/* 121 */           "ascending"));
/* 122 */         direction = "descending";
/*     */       }
/* 124 */       req.getSession().setAttribute("results", list);
/* 125 */       req.setAttribute("size", Integer.valueOf(list.size()));
/* 126 */       req.getRequestDispatcher(req.getContextPath() + "/jsp/inventory/results.jsp?sortDirection=" + direction)
/* 127 */         .forward(req, resp);
/*     */     }
/* 129 */     else if ("Retrieve".equals(action)) {
/* 130 */       Entity e = Item.getSingleItem(itemNumber);
/* 131 */       ArrayList messages = new ArrayList();
/*     */ 
/* 133 */       if ((itemNumber == null) || (itemNumber.equals(""))) {
/* 134 */         messages.add("Please enter an item number.");
/* 135 */         req.setAttribute("messages", messages);
/* 136 */         req.getRequestDispatcher(
/* 137 */           req.getContextPath() + "/jsp/inventory/editLevels.jsp")
/* 138 */           .forward(req, resp);
/*     */       }
/* 140 */       if (e != null) {
/* 141 */         session.setAttribute("entity", e);
/* 142 */         req.getRequestDispatcher(
/* 143 */           req.getContextPath() + "/jsp/inventory/editLevels2.jsp")
/* 144 */           .forward(req, resp);
/*     */       } else {
/* 146 */         messages.add("The item number you entered is not found in the current inventory.");
/* 147 */         req.setAttribute("messages", messages);
/* 148 */         req.getRequestDispatcher(
/* 149 */           req.getContextPath() + "/jsp/inventory/editLevels.jsp")
/* 150 */           .forward(req, resp);
/*     */       }
/* 152 */     } else if ("Search".equals(action)) {
/* 153 */       Iterable list1 = Item.search(itemNumber, itemName, desc, 
/* 154 */         location, vendorName, startCursor);
/* 155 */       ArrayList list = ApplicationTools.convertIterableToList(list1);
/*     */ 
/* 164 */       req.getSession().setAttribute("results", list);
/* 165 */       req.setAttribute("size", new Integer(PersonDao.countEntities(list)));
/* 166 */       req.getRequestDispatcher(
/* 167 */         req.getContextPath() + "/jsp/inventory/results.jsp")
/* 168 */         .forward(req, resp);
/*     */     }
/* 170 */     else if ("Add Item".equals(action)) {
/* 171 */       req.getRequestDispatcher(
/* 172 */         req.getContextPath() + "/jsp/inventory/add.jsp").forward(
/* 173 */         req, resp);
/* 174 */     } else if ("Save".equals(action)) {
/* 175 */       ArrayList messages = Item.saveInventory(vendorName, location, 
/* 176 */         itemNumber, itemName, qty1, qty2, desc, cost, price, 
/* 177 */         transactionType, minLevel, status, farmLocation, overwrite);
/*     */ 
/* 179 */       if (messages == null) {
/* 180 */         Iterable list1 = Item.search(null, null, null, null, 
/* 181 */           null, null);
/* 182 */         ArrayList list = ApplicationTools.convertIterableToList(list1);
/* 183 */         req.setAttribute("size", new Integer(PersonDao.countEntities(list)));
/* 184 */         req.setAttribute("pages", Integer.valueOf(Item.getPages()));
/*     */ 
/* 186 */         req.getSession().setAttribute("results", list);
/* 187 */         req.getRequestDispatcher(
/* 188 */           req.getContextPath() + "/jsp/inventory/results.jsp")
/* 189 */           .forward(req, resp);
/*     */       } else {
/* 191 */         req.setAttribute("messages", messages);
/* 192 */         req.getRequestDispatcher(
/* 193 */           req.getContextPath() + "/jsp/inventory/add.jsp")
/* 194 */           .forward(req, resp);
/*     */       }
/*     */     }
/* 197 */     else if ("Delete".equals(action)) {
/* 198 */       Entity e = Item.getSingleItem(itemNumber);
/*     */ 
/* 200 */       ArrayList list = Item.doDelete(e);
/* 201 */       if (list == null) {
/* 202 */         Iterable list1 = Item.search(null, null, null, null, 
/* 203 */           null, null);
/* 204 */         ArrayList list2 = ApplicationTools.convertIterableToList(list1);
/* 205 */         req.setAttribute("size", new Integer(PersonDao.countEntities(list)));
/* 206 */         req.setAttribute("pages", Integer.valueOf(Item.getPages()));
/*     */ 
/* 208 */         req.getSession().setAttribute("results", list2);
/* 209 */         req.getRequestDispatcher(
/* 210 */           req.getContextPath() + "/jsp/inventory/results.jsp")
/* 211 */           .forward(req, resp);
/*     */       } else {
/* 213 */         req.getRequestDispatcher(
/* 214 */           req.getContextPath() + "/jsp/inventory/results.jsp")
/* 215 */           .forward(req, resp);
/*     */       }
/* 217 */     } else if ("Edit".equals(action)) {
/* 218 */       Entity e = Item.getSingleItem(itemNumber);
/* 219 */       req.setAttribute("entity", e);
/* 220 */       req.getRequestDispatcher(
/* 221 */         req.getContextPath() + "/jsp/inventory/edit.jsp").forward(
/* 222 */         req, resp);
/*     */     }
/* 224 */     else if ("Save Changes".equals(action))
/*     */     {
/* 226 */       ArrayList messages = Item.saveInventory(vendorName, location, 
/* 227 */         itemNumber, itemName, qty1, qty2, desc, cost, price, 
/* 228 */         transactionType, minLevel, status, farmLocation, null);
/*     */ 
/* 230 */       if (messages == null) {
/* 231 */         Iterable list1 = Item.search(null, null, null, null, 
/* 232 */           null, null);
/* 233 */         ArrayList list = ApplicationTools.convertIterableToList(list1);
/* 234 */         req.setAttribute("size", new Integer(PersonDao.countEntities(list)));
/* 235 */         req.setAttribute("pages", Integer.valueOf(Item.getPages()));
/*     */ 
/* 237 */         req.getSession().setAttribute("results", list);
/* 238 */         req.getRequestDispatcher(
/* 239 */           req.getContextPath() + "/jsp/inventory/results.jsp")
/* 240 */           .forward(req, resp);
/*     */       } else {
/* 242 */         req.setAttribute("messages", messages);
/* 243 */         req.getRequestDispatcher(
/* 244 */           req.getContextPath() + "/jsp/inventory/add.jsp")
/* 245 */           .forward(req, resp);
/*     */       }
/*     */     }
/* 248 */     else if ("Save Levels".equals(action))
/*     */     {
/* 250 */       Item.saveInventory(itemNumber, qty1, qty2, farmLocation);
/*     */ 
/* 259 */       req.getRequestDispatcher(
/* 260 */         req.getContextPath() + "/jsp/item/results.jsp")
/* 261 */         .forward(req, resp);
/*     */     }
/* 263 */     else if ("Edit Level".equals(action)) {
/* 264 */       req.getRequestDispatcher(
/* 265 */         req.getContextPath() + "/jsp/inventory/editLevels.jsp")
/* 266 */         .forward(req, resp);
/*     */     } else {
/* 268 */       req.getRequestDispatcher(
/* 269 */         req.getContextPath() + "/jsp/inventory/index.jsp").forward(
/* 270 */         req, resp);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doPost(HttpServletRequest req, HttpServletResponse resp)
/*     */     throws ServletException, IOException
/*     */   {
/* 280 */     doGet(req, resp);
/*     */   }
/*     */ }

/* Location:           C:\development\workspace\faithfarmoss\WEB-INF\classes\
 * Qualified Name:     org.faithfarm.InventoryServlet
 * JD-Core Version:    0.6.2
 */