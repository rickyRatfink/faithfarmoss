/*    */ package org.faithfarm;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.http.HttpServlet;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ 
/*    */ public class BaseServlet extends HttpServlet
/*    */ {
/*    */   protected void doGet(HttpServletRequest req, HttpServletResponse resp)
/*    */     throws ServletException, IOException
/*    */   {
/* 16 */     resp.setContentType("application/json; charset=utf-8");
/* 17 */     resp.setHeader("Cache-Control", "no-cache");
/*    */   }
/*    */ }

/* Location:           C:\development\workspace\faithfarmoss\WEB-INF\classes\
 * Qualified Name:     org.faithfarm.BaseServlet
 * JD-Core Version:    0.6.2
 */