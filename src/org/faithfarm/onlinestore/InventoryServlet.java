/*     */ package org.faithfarm.onlinestore;
/*     */ 
/*     */ import com.google.appengine.api.datastore.DatastoreService;
/*     */ import com.google.appengine.api.datastore.DatastoreServiceFactory;
/*     */ import com.google.appengine.api.datastore.FetchOptions;
/*     */ import com.google.appengine.api.datastore.FetchOptions.Builder;
/*     */ import com.google.appengine.api.datastore.PreparedQuery;
/*     */ import com.google.appengine.api.datastore.Query;
/*     */ import com.google.appengine.api.datastore.Query.FilterOperator;
/*     */ import com.google.appengine.api.users.User;
/*     */ import com.google.appengine.api.users.UserService;
/*     */ import com.google.appengine.api.users.UserServiceFactory;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.logging.Logger;
/*     */ import javax.servlet.RequestDispatcher;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.faithfarm.BaseServlet;
/*     */ import org.faithfarm.dataobjects.SearchParameter;
/*     */ import org.faithfarm.datawriters.ApplicationDao;
/*     */ import org.faithfarm.utilities.ApplicationTools;
/*     */ import org.faithfarm.utilities.HTMLBuilder;
/*     */ import org.faithfarm.utilities.XMLParser;
/*     */ import org.faithfarm.validators.Validator;
/*     */ 
/*     */ public class InventoryServlet extends BaseServlet
/*     */ {
/*  38 */   private static final Logger logger = Logger.getLogger(InventoryServlet.class.getCanonicalName());
/*  39 */   private static final HTMLBuilder html = new HTMLBuilder();
/*  40 */   private static final XMLParser xml = new XMLParser();
/*  41 */   private static final ApplicationDao dao = new ApplicationDao();
/*  42 */   private static final Validator validator = new Validator();
/*  43 */   private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
/*     */ 
/*     */   protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
/*  46 */     super.doGet(req, resp);
/*     */ 
/*  48 */     HttpSession session = req.getSession(true);
/*  49 */     UserService userService = UserServiceFactory.getUserService();
/*  50 */     User user = userService.getCurrentUser();
/*     */ 
/*  56 */     String url = "/jsp/onlinestore/index.jsp";
/*  57 */     String farmBase = (String)session.getAttribute("farmBase");
/*  58 */     String action = (String)req.getAttribute("action");
/*  59 */     String type = req.getParameter("type");
/*     */ 
/*  61 */     if (action == null) {
/*  62 */       action = req.getParameter("action");
/*     */     }
/*  64 */     if (type != null) {
/*  65 */       Iterable results = null;
/*  66 */       Query q = new Query("Item");
/*  67 */       q.addFilter("farmLocation", Query.FilterOperator.EQUAL, (String)req.getSession().getAttribute("farmBase"));
/*  68 */       q.addFilter("itemType", Query.FilterOperator.EQUAL, type);
/*  69 */       q.addFilter("vendor", Query.FilterOperator.EQUAL, "Donation");
/*     */ 
/*  71 */       PreparedQuery pq = datastore.prepare(q);
/*  72 */       results = pq.asIterable(FetchOptions.Builder.withLimit(100).chunkSize(100));
/*  73 */       ArrayList list = ApplicationTools.convertIterableToList(results);
/*     */ 
/*  75 */       req.setAttribute("results", list);
/*  76 */       req.setAttribute("type", type);
/*  77 */       url = "/jsp/onlinestore/results.jsp";
/*     */     }
/*  79 */     if ("Index".equals(action))
/*  80 */       session.setAttribute("results", null);
/*     */     else {
/*  82 */       req.getSession().setAttribute("results", new ArrayList());
/*     */     }
/*  84 */     req.getRequestDispatcher(req.getContextPath() + url).forward(req, resp);
/*     */   }
/*     */ 
/*     */   protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
/*     */   {
/*  89 */     doGet(req, resp);
/*     */   }
/*     */ 
/*     */   private ArrayList setSearchParameters(HttpServletRequest req)
/*     */   {
/*  94 */     ArrayList parameters = new ArrayList();
/*  95 */     String itemNumber = html.cleanData(req.getParameter("itemNumber"));
/*  96 */     String itemType = html.cleanData(req.getParameter("itemType"));
/*  97 */     String farm = html.cleanData(req.getParameter("farmLocation"));
/*  98 */     String vendor = html.cleanData(req.getParameter("vendor"));
/*     */ 
/* 101 */     if ("".equals(farm)) {
/* 102 */       farm = (String)req.getSession().getAttribute("farmBase");
/*     */     }
/* 104 */     req.getSession().setAttribute("itemNumber", itemNumber);
/* 105 */     req.getSession().setAttribute("itemType", itemType);
/* 106 */     req.getSession().setAttribute("farmLocation", farm);
/* 107 */     req.getSession().setAttribute("vendor", vendor);
/*     */ 
/* 109 */     parameters.add(new SearchParameter("itemNumber", itemNumber, Query.FilterOperator.EQUAL));
/* 110 */     parameters.add(new SearchParameter("itemType", itemType, Query.FilterOperator.EQUAL));
/* 111 */     parameters.add(new SearchParameter("farmLocation", farm, Query.FilterOperator.EQUAL));
/* 112 */     parameters.add(new SearchParameter("vendor", vendor, Query.FilterOperator.EQUAL));
/*     */ 
/* 114 */     if (req.getSession().getAttribute("INVENTORY_LOCATION") != null) {
/* 115 */       parameters.add(new SearchParameter("location", (String)req.getSession().getAttribute("INVENTORY_LOCATION"), Query.FilterOperator.EQUAL));
/*     */     }
/* 117 */     return parameters;
/*     */   }
/*     */ }

/* Location:           C:\development\workspace\faithfarmoss\WEB-INF\classes\
 * Qualified Name:     org.faithfarm.onlinestore.InventoryServlet
 * JD-Core Version:    0.6.2
 */