/*    */ package org.faithfarm.utilities;
/*    */ 
/*    */ import com.google.appengine.api.users.User;
/*    */ import com.google.appengine.api.users.UserService;
/*    */ import com.google.appengine.api.users.UserServiceFactory;
/*    */ import java.io.IOException;
/*    */ import java.io.Serializable;
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.Logger;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import javax.servlet.http.HttpSession;
/*    */ 
/*    */ public class FFSession
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -7472538758396237840L;
/* 25 */   private static final Logger logger = Logger.getLogger(FFSession.class.getCanonicalName());
/*    */ 
/*    */   public static boolean needsRedirect(HttpServletRequest req, HttpServletResponse resp)
/*    */   {
/* 35 */     UserService userService = UserServiceFactory.getUserService();
/* 36 */     User user = userService.getCurrentUser();
/* 37 */     HttpSession session = req.getSession(true);
/*    */ 
/* 40 */     if (user == null) {
/*    */       try {
/* 42 */         resp.sendRedirect(userService.createLoginURL("/"));
/*    */       } catch (IOException e) {
/* 44 */         logger.log(Level.SEVERE, e.getMessage());
/*    */       }
/* 46 */       return true;
/*    */     }
/*    */ 
/* 49 */     if (session.getAttribute("farmBase") == null) {
/*    */       try {
/* 51 */         resp.sendRedirect(userService.createLogoutURL("/security"));
/*    */       } catch (IOException e) {
/* 53 */         logger.log(Level.SEVERE, e.getMessage());
/*    */       }
/* 55 */       return true;
/*    */     }
/* 57 */     return false;
/*    */   }
/*    */ }

/* Location:           C:\development\workspace\faithfarmoss\WEB-INF\classes\
 * Qualified Name:     org.faithfarm.utilities.FFSession
 * JD-Core Version:    0.6.2
 */