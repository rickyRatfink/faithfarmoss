/*    */ package org.faithfarm;
/*    */ 
/*    */ /*    */ import java.io.IOException;
/*    */ import java.util.Collections;

/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import javax.servlet.http.HttpSession;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
/*    */ import com.google.api.client.auth.oauth2.AuthorizationCodeResponseUrl;
/*    */ import com.google.api.client.auth.oauth2.Credential;
/*    */ import com.google.api.client.extensions.appengine.auth.oauth2.AbstractAppEngineAuthorizationCodeCallbackServlet;
/*    */ import com.google.api.client.extensions.appengine.http.UrlFetchTransport;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
/*    */ import com.google.api.client.http.GenericUrl;
/*    */ import com.google.api.client.json.jackson.JacksonFactory;
/*    */ 
/*    */ 
/*    */ public class OAuth2CallbackServlet extends AbstractAppEngineAuthorizationCodeCallbackServlet
/*    */ {
/*    */   private static final String CLIENT_ID = "125565848049-ank4e10haejl62si7nafi8iuh8j7r0jq.apps.googleusercontent.com";
/*    */   private static final String CLIENT_SECRET = "faXFreoCKfcGmhg5O9p7_N1j";
/*    */   private static final String GDOCS_SCOPES = "https://docs.google.com/feeds/ https://docs.googleusercontent.com/ https://spreadsheets.google.com/feeds/";
/*    */ 
/*    */   protected void onSuccess(HttpServletRequest req, HttpServletResponse resp, Credential credential)
/*    */     throws ServletException, IOException
/*    */   {
/* 27 */     HttpSession session = req.getSession(true);
/* 28 */     session.setAttribute("api_credential", credential);
/* 29 */     resp.sendRedirect("/");
/*    */   }
/*    */ 
/*    */   protected void onError(HttpServletRequest req, HttpServletResponse resp, AuthorizationCodeResponseUrl errorResponse)
/*    */     throws ServletException, IOException
/*    */   {
/*    */   }
/*    */ 
/*    */   protected String getRedirectUri(HttpServletRequest req) throws ServletException, IOException
/*    */   {
/* 39 */     GenericUrl url = new GenericUrl(req.getRequestURL().toString());
/* 40 */     url.setRawPath("/oauth2callback");
/* 41 */     return url.build();
/*    */   }
/*    */ 
/*    */   protected AuthorizationCodeFlow initializeFlow() throws IOException
/*    */   {
/* 46 */     return new GoogleAuthorizationCodeFlow.Builder(new UrlFetchTransport(), new JacksonFactory(), "125565848049-ank4e10haejl62si7nafi8iuh8j7r0jq.apps.googleusercontent.com", "faXFreoCKfcGmhg5O9p7_N1j", 
/* 47 */       Collections.singleton("https://docs.google.com/feeds/ https://docs.googleusercontent.com/ https://spreadsheets.google.com/feeds/")).build();
/*    */   }
/*    */ }

/* Location:           C:\development\workspace\faithfarmoss\WEB-INF\classes\
 * Qualified Name:     org.faithfarm.OAuth2CallbackServlet
 * JD-Core Version:    0.6.2
 */