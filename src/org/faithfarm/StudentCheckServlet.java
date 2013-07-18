/*     */ package org.faithfarm;
/*     */ 
/*     */ /*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.net.URL;
/*     */ import java.net.URLEncoder;
/*     */ import java.util.logging.Logger;

/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;

/*     */ import org.json.simple.JSONArray;
/*     */ import org.json.simple.JSONObject;
/*     */ import org.jsoup.Jsoup;
/*     */ import org.jsoup.nodes.Document;
/*     */ import org.jsoup.nodes.Element;
/*     */ import org.jsoup.select.Elements;

import com.google.appengine.api.datastore.FetchOptions;
/*     */ import com.google.appengine.api.urlfetch.HTTPHeader;
/*     */ import com.google.appengine.api.urlfetch.HTTPMethod;
/*     */ import com.google.appengine.api.urlfetch.HTTPRequest;
/*     */ import com.google.appengine.api.urlfetch.HTTPResponse;
/*     */ import com.google.appengine.api.urlfetch.URLFetchService;
/*     */ import com.google.appengine.api.urlfetch.URLFetchServiceFactory;
/*     */ 
/*     */ public class StudentCheckServlet extends BaseServlet
/*     */ {
/*  37 */   private static final URLFetchService urlFetch = URLFetchServiceFactory.getURLFetchService();
/*  38 */   private static final Logger logger = Logger.getLogger(StudentCheckServlet.class.getCanonicalName());
/*     */ 
/*     */   protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException
/*     */   {
/*  42 */     resp.addHeader("Allow", "POST");
/*  43 */     resp.setStatus(405);
/*     */   }
/*     */ 
/*     */   protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException
/*     */   {
/*  48 */     StringBuilder cookieBuilder = new StringBuilder();
/*  49 */     resp.setContentType("application/json");
/*     */ 
/*  51 */     PrintWriter writer = resp.getWriter();
/*     */ 
/*  53 */     String first = req.getParameter("first");
/*  54 */     String last = req.getParameter("last");
/*     */ 
/*  56 */     first = first == null ? "" : first;
/*  57 */     last = last == null ? "" : last;
/*     */ 
/*  60 */     JSONObject json = new JSONObject();
/*  61 */     json.put("success", new Boolean(false));
/*  62 */     json.put("errors", new JSONArray());
/*     */     try
/*     */     {
/*  65 */       HTTPRequest http_req = new HTTPRequest(new URL("http://intake.faithfarm.org/Account/Login.aspx"));
/*  66 */       HTTPResponse http_resp = urlFetch.fetch(http_req);
/*  67 */       for (HTTPHeader header : http_resp.getHeaders()) {
/*  68 */         if (header.getName().equals("Set-Cookie")) {
/*  69 */           String cookie = header.getValue().split(";")[0];
/*  70 */           cookieBuilder.append(cookie);
/*  71 */           cookieBuilder.append("; ");
/*     */         }
/*     */       }
/*  74 */       Document jDoc = Jsoup.parse(new String(http_resp.getContent()));
/*  75 */       Elements els = jDoc.getElementsByAttributeValue("name", "__VIEWSTATE");
/*  76 */       if (els.size() < 1) {
/*  77 */         throw new Exception("Unable to parse view state.");
/*     */       }
/*  79 */       Element el = els.get(0);
/*  80 */       String viewState = el.attr("value");
/*  81 */       String eventValidation = new String();
/*  82 */       els = jDoc.getElementsByAttributeValue("name", "__EVENTVALIDATION");
/*  83 */       if (els.size() > 0) {
/*  84 */         el = els.get(0);
/*  85 */         eventValidation = el.attr("value");
/*     */       }
/*  87 */      // http_req = new HTTPRequest(new URL("http://intake.faithfarm.org/Account/Login.aspx"), HTTPMethod.POST, FetchOptions.Builder.doNotFollowRedirects());
/*  88 */       http_req.addHeader(new HTTPHeader("Cookie", cookieBuilder.toString()));
/*  89 */       String payload = "ctl00%24MainContent%24LoginUser%24UserName=intake&ctl00%24MainContent%24LoginUser%24Password=Israel&ctl00%24MainContent%24LoginUser%24LoginButton=Log+In&__VIEWSTATE=" + URLEncoder.encode(viewState, "UTF-8") + "&__EVENTVALIDATION=" + URLEncoder.encode(eventValidation, "UTF-8");
/*     */ 
/*  92 */       http_req.setPayload(payload.getBytes());
/*     */ 
/*  95 */       http_resp = urlFetch.fetch(http_req);
/*     */       String cookie;
/*  96 */       for (HTTPHeader header : http_resp.getHeaders()) {
/*  97 */         if (header.getName().equals("Set-Cookie")) {
/*  98 */           cookie = header.getValue().split(";")[0];
/*  99 */           cookieBuilder.append(cookie);
/* 100 */           cookieBuilder.append("; ");
/*     */         }
/*     */       }
/* 103 */       //http_req = new HTTPRequest(new URL("http://intake.faithfarm.org/StudentPages/StudentSearch.aspx"), HTTPMethod.GET, FetchOptions.Builder.withDefaults());
/* 104 */       http_req.addHeader(new HTTPHeader("Cookie", cookieBuilder.toString()));
/* 105 */       http_resp = urlFetch.fetch(http_req);
/* 106 */       jDoc = Jsoup.parse(new String(http_resp.getContent()));
/* 107 */       els = jDoc.getElementsByAttributeValue("name", "__VIEWSTATE");
/* 108 */       if (els.size() > 0)
/*     */       {
/* 110 */         el = els.get(0);
/* 111 */         viewState = el.attr("value");
/*     */       }
/* 113 */       els = jDoc.getElementsByAttributeValue("name", "__EVENTVALIDATION");
/* 114 */       if (els.size() > 0) {
/* 115 */         el = els.get(0);
/* 116 */         eventValidation = el.attr("value");
/*     */       }
/* 118 */       payload = "ctl00%24MainContent%24StudentSearch1%24StudentSearchDataForm%24tb_LastName=" + last + 
/* 119 */         "&ctl00%24MainContent%24StudentSearch1%24StudentSearchDataForm%24tb_FirstName=" + first + 
/* 120 */         "&ctl00%24MainContent%24StudentSearch1%24StudentSearchDataForm%24tb_SSN_Part1=000" + 
/* 121 */         "&ctl00%24MainContent%24StudentSearch1%24StudentSearchDataForm%24tb_SSN_Part2=00" + 
/* 122 */         "&ctl00%24MainContent%24StudentSearch1%24StudentSearchDataForm%24tb_SSN_Part3=0000" + 
/* 123 */         "&__EVENTTARGET=ctl00%24MainContent%24StudentSearch1%24StudentSearchDataForm%24lb_SearchStudent" + 
/* 124 */         "&__VIEWSTATE=" + URLEncoder.encode(viewState, "UTF-8") + 
/* 125 */         "&__EVENTVALIDATION=" + URLEncoder.encode(eventValidation, "UTF-8");
/*     */ 
/* 128 */       //http_req = new HTTPRequest(new URL("http://intake.faithfarm.org/StudentPages/StudentSearch.aspx"), HTTPMethod.POST, FetchOptions.Builder.doNotFollowRedirects());
/* 129 */       http_req.addHeader(new HTTPHeader("Cookie", cookieBuilder.toString()));
/* 130 */       http_req.setPayload(payload.getBytes());
/* 131 */       http_resp = urlFetch.fetch(http_req);
/* 132 */      // http_req = new HTTPRequest(new URL("http://intake.faithfarm.org/StudentPages/SearchResult.aspx"), HTTPMethod.GET, FetchOptions.Builder.doNotFollowRedirects());
/* 133 */       http_req.addHeader(new HTTPHeader("Cookie", cookieBuilder.toString()));
/* 134 */       http_resp = urlFetch.fetch(http_req);
/* 135 */       jDoc = Jsoup.parse(new String(http_resp.getContent()));
/* 136 */       el = jDoc.getElementById("MainContent_SearchResultList1_rad_CLassList_ctl00");
/* 137 */       if (el == null) {
/* 138 */         throw new Exception("Unable to find search results table in results page.");
/*     */       }
/* 140 */       els = el.getElementsByClass("rgRow");
/* 141 */       Boolean match = Boolean.valueOf(false);
/* 142 */       for (Element e : els) {
/* 143 */         Elements cells = e.getElementsByTag("td");
/* 144 */         String thisFirst = cells.get(1).text();
/* 145 */         String thisLast = cells.get(3).text();
/* 146 */         if ((thisFirst.toLowerCase().equals(first.toLowerCase())) && (thisLast.toLowerCase().equals(last.toLowerCase()))) {
/* 147 */           match = Boolean.valueOf(true);
/* 148 */           break;
/*     */         }
/*     */       }
/* 151 */       json.put("result", match);
/* 152 */       json.put("success", new Boolean(true));
/*     */     }
/*     */     catch (Exception e) {
/* 155 */       JSONArray errors = new JSONArray();
/* 156 */       errors.add("Exception: " + e.getMessage());
/* 157 */       json.put("errors", errors);
/*     */     }
/*     */ 
/* 164 */     writer.print(json.toJSONString());
/*     */   }
/*     */ }

/* Location:           C:\development\workspace\faithfarmoss\WEB-INF\classes\
 * Qualified Name:     org.faithfarm.StudentCheckServlet
 * JD-Core Version:    0.6.2
 */