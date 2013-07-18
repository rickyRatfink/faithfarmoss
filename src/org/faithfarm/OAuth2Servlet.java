/*    */ package org.faithfarm;
/*    */ 
/*    */ /*    */ import java.io.IOException;
/*    */ import java.util.Collections;

/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
/*    */ import com.google.api.client.extensions.appengine.auth.oauth2.AbstractAppEngineAuthorizationCodeServlet;
/*    */ import com.google.api.client.extensions.appengine.auth.oauth2.AppEngineCredentialStore;
/*    */ import com.google.api.client.extensions.appengine.http.UrlFetchTransport;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
/*    */ import com.google.api.client.http.GenericUrl;
/*    */ import com.google.api.client.json.jackson.JacksonFactory;
/*    */ 
/*    */ 
/*    */ public class OAuth2Servlet extends AbstractAppEngineAuthorizationCodeServlet
/*    */ {
/*    */   private static final String CLIENT_ID = "125565848049-ank4e10haejl62si7nafi8iuh8j7r0jq.apps.googleusercontent.com";
/*    */   private static final String CLIENT_SECRET = "faXFreoCKfcGmhg5O9p7_N1j";
/*    */   private static final String GDOCS_SCOPES = "https://docs.google.com/feeds/ https://docs.googleusercontent.com/ https://spreadsheets.google.com/feeds/";
/*    */ 
/*    */   protected void doGet(HttpServletRequest request, HttpServletResponse response)
/*    */     throws IOException
/*    */   {
/*    */   }
/*    */ 
/*    */   protected String getRedirectUri(HttpServletRequest req)
/*    */     throws ServletException, IOException
/*    */   {
/* 30 */     GenericUrl url = new GenericUrl(req.getRequestURL().toString());
/* 31 */     url.setRawPath("/oauth2callback");
/* 32 */     return url.build();
/*    */   }
/*    */ 
/*    */   protected AuthorizationCodeFlow initializeFlow() throws IOException
/*    */   {
/* 37 */     return new GoogleAuthorizationCodeFlow.Builder(new UrlFetchTransport(), new JacksonFactory(), "125565848049-ank4e10haejl62si7nafi8iuh8j7r0jq.apps.googleusercontent.com", "faXFreoCKfcGmhg5O9p7_N1j", 
/* 38 */       Collections.singleton("https://docs.google.com/feeds/ https://docs.googleusercontent.com/ https://spreadsheets.google.com/feeds/")).setCredentialStore(new AppEngineCredentialStore()).build();
/*    */   }
/*    */ }

/* Location:           C:\development\workspace\faithfarmoss\WEB-INF\classes\
 * Qualified Name:     org.faithfarm.OAuth2Servlet
 * JD-Core Version:    0.6.2
 */