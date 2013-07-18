/*     */ package org.faithfarm.price;
/*     */ 
/*     */ import com.google.appengine.api.urlfetch.FetchOptions;
/*     */ import com.google.appengine.api.urlfetch.FetchOptions.Builder;
/*     */ import com.google.appengine.api.urlfetch.HTTPHeader;
/*     */ import com.google.appengine.api.urlfetch.HTTPMethod;
/*     */ import com.google.appengine.api.urlfetch.HTTPRequest;
/*     */ import com.google.appengine.api.urlfetch.HTTPResponse;
/*     */ import com.google.appengine.api.urlfetch.URLFetchService;
/*     */ import com.google.appengine.api.urlfetch.URLFetchServiceFactory;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.net.URLEncoder;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.json.simple.JSONArray;
/*     */ import org.json.simple.JSONObject;
/*     */ import org.jsoup.Jsoup;
/*     */ import org.jsoup.nodes.Document;
/*     */ import org.jsoup.nodes.Element;
/*     */ import org.jsoup.select.Elements;
/*     */ 
/*     */ public class CoasterPriceQuery
/*     */   implements PriceQuery
/*     */ {
/*     */   private Map<String, String> items;
/*     */   private String query;
/*  35 */   private static final Logger logger = Logger.getLogger(CoasterPriceQuery.class.getCanonicalName());
/*  36 */   private static final URLFetchService urlFetchService = URLFetchServiceFactory.getURLFetchService();
/*  37 */   private static List<String> cookies = null;
/*  38 */   private HttpSession session = null;
/*     */ 
/*     */   public CoasterPriceQuery(HttpSession sess, String queryStr) {
/*  41 */     this.query = queryStr;
/*  42 */     this.items = new HashMap();
/*     */ 
/*  44 */     this.session = sess;
/*  45 */     cookies = (List)this.session.getAttribute("cookies");
/*  46 */     if (cookies == null) {
/*  47 */       cookies = new ArrayList();
/*     */     }
/*  49 */     for (String cookie : cookies) {
/*  50 */       logger.log(Level.SEVERE, "Session cookie: " + cookie);
/*     */     }
/*  52 */     Boolean loggedIn = loggedIn();
/*     */ 
/*  54 */     logger.log(Level.SEVERE, "logged in?: " + loggedIn);
/*  55 */     if (!loggedIn.booleanValue())
/*  56 */       doLogin();
/*     */   }
/*     */ 
/*     */   private static String getCookieString()
/*     */   {
/*  61 */     StringBuilder cookieBuilder = new StringBuilder();
/*  62 */     if (cookies.size() == 0) {
/*  63 */       return null;
/*     */     }
/*  65 */     for (String cookie : cookies) {
/*  66 */       cookieBuilder.append(cookie + "; ");
/*     */     }
/*  68 */     String cookieStr = cookieBuilder.toString();
/*  69 */     cookieStr = cookieStr.substring(0, cookieStr.length() - 2);
/*  70 */     return cookieStr;
/*     */   }
/*     */ 
/*     */   public static Boolean loggedIn()
/*     */   {
/*     */     try {
/*  76 */       URL url = new URL("http://coasterconnect.coasteramer.com/CustomerSite/BrowseAll.aspx");
/*  77 */       HTTPRequest req = new HTTPRequest(url, HTTPMethod.GET, FetchOptions.Builder.doNotFollowRedirects().setDeadline(Double.valueOf(15.0D)));
/*  78 */       String cookieStr = getCookieString();
/*  79 */       if (cookieStr != null) {
/*  80 */         req.addHeader(new HTTPHeader("Cookie", cookieStr));
/*     */       }
/*     */ 
/*  83 */       HTTPResponse resp = urlFetchService.fetch(req);
/*  84 */       return Boolean.valueOf(resp.getResponseCode() != 302);
/*     */     } catch (Exception e) {
/*  86 */       System.out.println("Exception occurred: " + e.getMessage());
/*     */     }
/*  88 */     return Boolean.valueOf(false);
/*     */   }
/*     */ 
/*     */   public void doLogin() {
/*  92 */     cookies = new ArrayList();
/*     */     try {
/*  94 */       URL url = new URL("http://coasterconnect.coasteramer.com/Account/Login.aspx?ReturnUrl=%2fCustomerSite%2fBrowseAll.aspx");
/*  95 */       HTTPRequest req = new HTTPRequest(url, HTTPMethod.GET, FetchOptions.Builder.doNotFollowRedirects().setDeadline(Double.valueOf(15.0D)));
/*  96 */       HTTPResponse resp = urlFetchService.fetch(req);
/*  97 */       for (HTTPHeader header : resp.getHeaders()) {
/*  98 */         if (header.getName().equalsIgnoreCase("set-cookie")) {
/*  99 */           String cook = header.getValue().substring(0, header.getValue().indexOf(";"));
/* 100 */           cookies.add(cook);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 105 */       String output = new String(resp.getContent());
/*     */ 
/* 107 */       Document doc = Jsoup.parse(output);
/* 108 */       Element viewstateEl = doc.select("#__VIEWSTATE").first();
/* 109 */       Element validationEl = doc.select("#__EVENTVALIDATION").first();
/* 110 */       String viewState = viewstateEl.attr("value");
/* 111 */       String validation = validationEl.attr("value");
/* 112 */       String postData = "LoginUser%24LoginButton.x=0&LoginUser%24LoginButton.y=0&LoginUser%24UserName=31182&LoginUser%24Password=cc3118233311&__VIEWSTATE=" + URLEncoder.encode(viewState, "UTF-8") + "&__EVENTVALIDATION=" + URLEncoder.encode(validation, "UTF-8");
/* 113 */       url = new URL("http://coasterconnect.coasteramer.com/Account/Login.aspx");
/* 114 */       req = new HTTPRequest(url, HTTPMethod.POST, FetchOptions.Builder.doNotFollowRedirects().setDeadline(Double.valueOf(15.0D)));
/* 115 */       String cookieStr = getCookieString();
/* 116 */       if (cookieStr != null) {
/* 117 */         req.addHeader(new HTTPHeader("Cookie", cookieStr));
/*     */       }
/*     */ 
/* 121 */       req.setPayload(postData.getBytes());
/*     */ 
/* 123 */       resp = urlFetchService.fetch(req);
/* 124 */       for (HTTPHeader header : resp.getHeaders()) {
/* 125 */         if (header.getName().equalsIgnoreCase("set-cookie")) {
/* 126 */           String cook = header.getValue().substring(0, header.getValue().indexOf(";"));
/* 127 */           cookies.add(cook);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 132 */       this.session.setAttribute("cookies", cookies);
/*     */ 
/* 134 */       url = new URL("http://coasterconnect.coasteramer.com/CustomerSite/BrowseAll.aspx");
/* 135 */       req = new HTTPRequest(url, HTTPMethod.GET, FetchOptions.Builder.doNotFollowRedirects().setDeadline(Double.valueOf(15.0D)));
/* 136 */       cookieStr = getCookieString();
/* 137 */       if (cookieStr != null) {
/* 138 */         req.addHeader(new HTTPHeader("Cookie", cookieStr));
/*     */       }
/* 140 */       resp = urlFetchService.fetch(req);
/*     */     }
/*     */     catch (MalformedURLException e)
/*     */     {
/* 145 */       logger.log(Level.SEVERE, "MalformedURL: " + e.getMessage());
/*     */     } catch (IOException e) {
/* 147 */       logger.log(Level.SEVERE, "IOException: " + e.getMessage());
/*     */     } catch (Exception e) {
/* 149 */       logger.log(Level.SEVERE, "General Exception: " + e.getMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   public Map<String, String> getItems()
/*     */   {
/* 156 */     return this.items;
/*     */   }
/*     */ 
/*     */   public String doQuery()
/*     */   {
/*     */     try
/*     */     {
/* 164 */       URL url = new URL("http://coasterconnect.coasteramer.com/CustomerSite/SearchProduct.aspx?&itemno=" + this.query);
/* 165 */       HTTPRequest req = new HTTPRequest(url, HTTPMethod.GET, FetchOptions.Builder.doNotFollowRedirects().setDeadline(Double.valueOf(15.0D)));
/* 166 */       String cookieStr = getCookieString();
/* 167 */       if (cookieStr != null) {
/* 168 */         req.addHeader(new HTTPHeader("Cookie", cookieStr));
/*     */       }
/*     */ 
/* 171 */       HTTPResponse resp = urlFetchService.fetch(req);
/* 172 */       Document doc = Jsoup.parse(new String(resp.getContent()));
/* 173 */       Elements els = doc.select("#MainContent_gvItemInfo tr");
/*     */ 
/* 175 */       JSONArray itemArray = new JSONArray();
/* 176 */       JSONObject obj = new JSONObject();
/*     */ 
/* 178 */       for (int i = 1; i < els.size(); i++)
/*     */       {
/* 182 */         JSONObject tItem = new JSONObject();
/*     */ 
/* 186 */         Element el = els.get(i);
/* 187 */         Elements cells = el.select("td");
/* 188 */         Elements itemAndDescriptionSpans = cells.get(1).select("span");
/*     */ 
/* 190 */         String itemNum = itemAndDescriptionSpans.get(0).text();
/* 191 */         String desc = itemAndDescriptionSpans.get(1).text();
/*     */ 
/* 194 */         Float priceFloat = Float.valueOf(Float.parseFloat(cells.get(6).text().substring(1).replace(",", "")));
/*     */ 
/* 196 */         priceFloat = Float.valueOf(priceFloat.floatValue() * 1.93F);
/* 197 */         Integer price = Integer.valueOf(Math.round(priceFloat.floatValue()));
/* 198 */         String priceStr = price.toString();
/* 199 */         priceStr = priceStr.substring(0, priceStr.length() - 1) + "9";
/* 200 */         price = Integer.valueOf(Integer.parseInt(priceStr, 10));
/* 201 */         tItem.put("item_num", itemNum);
/* 202 */         tItem.put("description", desc);
/* 203 */         tItem.put("price", price);
/* 204 */         itemArray.add(tItem);
/*     */       }
/*     */ 
/* 207 */       obj.put("items", itemArray);
/* 208 */       obj.put("success", Boolean.valueOf(true));
/* 209 */       return obj.toJSONString();
/*     */     }
/*     */     catch (MalformedURLException e)
/*     */     {
/* 213 */       logger.log(Level.SEVERE, "MalformedURL: " + e.getMessage());
/*     */     } catch (IOException e) {
/* 215 */       logger.log(Level.SEVERE, "IOException: " + e.getMessage());
/*     */     } catch (Exception e) {
/* 217 */       e.printStackTrace();
/* 218 */       logger.log(Level.SEVERE, "General exception: " + e.getMessage());
/*     */     }
/* 220 */     return "";
/*     */   }
/*     */ }

/* Location:           C:\development\workspace\faithfarmoss\WEB-INF\classes\
 * Qualified Name:     org.faithfarm.price.CoasterPriceQuery
 * JD-Core Version:    0.6.2
 */