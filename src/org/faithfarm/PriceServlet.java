/*    */ package org.faithfarm;
/*    */ 
/*    */ import com.google.appengine.api.datastore.DatastoreService;
/*    */ import com.google.appengine.api.datastore.DatastoreServiceFactory;
/*    */ import com.google.appengine.api.users.User;
/*    */ import com.google.appengine.api.users.UserService;
/*    */ import com.google.appengine.api.users.UserServiceFactory;
/*    */ import java.io.IOException;
/*    */ import java.io.PrintWriter;
/*    */ import java.util.logging.Logger;
/*    */ import javax.servlet.RequestDispatcher;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.faithfarm.price.CoasterPriceQuery;
/*    */ import org.faithfarm.price.PriceQuery;
/*    */ import org.faithfarm.utilities.FFSession;
/*    */ 
/*    */ public class PriceServlet extends BaseServlet
/*    */ {
/* 25 */   private static final Logger logger = Logger.getLogger(ControllerServlet.class.getCanonicalName());
/*    */ 
/* 28 */   private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
/*    */ 
/*    */   protected void doGet(HttpServletRequest req, HttpServletResponse resp)
/*    */     throws ServletException, IOException
/*    */   {
/* 33 */     if (FFSession.needsRedirect(req, resp)) {
/* 34 */       return;
/*    */     }
/*    */ 
/* 37 */     String action = req.getParameter("action");
/* 38 */     UserService userService = UserServiceFactory.getUserService();
/* 39 */     User user = userService.getCurrentUser();
/*    */ 
/* 41 */     if (action == null) {
/* 42 */       String url = "/jsp/price/index.jsp";
/* 43 */       req.getRequestDispatcher(req.getContextPath() + url).forward(req, resp);
/* 44 */     } else if (action.equals("query")) {
/* 45 */       String manufacturer = req.getParameter("manufacturer");
/* 46 */       String item = req.getParameter("item");
/* 47 */       if (manufacturer == null) {
/* 48 */         manufacturer = "";
/*    */       }
/* 50 */       if (item == null) {
/* 51 */         item = "";
/*    */       }
/* 53 */       PriceQuery pq = null;
/* 54 */       if (manufacturer.equals("coaster"))
/* 55 */         pq = new CoasterPriceQuery(req.getSession(true), item);
/* 56 */       else manufacturer.equals("ashley");
/*    */ 
/* 59 */       String json = pq.doQuery();
/*    */ 
/* 61 */       resp.setContentType("application/json");
/* 62 */       PrintWriter writer = resp.getWriter();
/* 63 */       writer.print(json);
/* 64 */       writer.close();
/*    */     }
/*    */   }
/*    */ 
/*    */   protected void doPost(HttpServletRequest req, HttpServletResponse resp)
/*    */     throws ServletException, IOException
/*    */   {
/* 73 */     doGet(req, resp);
/*    */   }
/*    */ }

/* Location:           C:\development\workspace\faithfarmoss\WEB-INF\classes\
 * Qualified Name:     org.faithfarm.PriceServlet
 * JD-Core Version:    0.6.2
 */