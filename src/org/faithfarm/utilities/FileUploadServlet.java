/*     */ package org.faithfarm.utilities;
/*     */ 
/*     */ /*     */ import java.io.IOException;
/*     */ import java.util.Date;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.logging.Logger;

/*     */ import javax.activation.DataHandler;
/*     */ import javax.activation.DataSource;
/*     */ import javax.mail.Session;
/*     */ import javax.mail.Transport;
/*     */ import javax.mail.internet.InternetAddress;
/*     */ import javax.mail.internet.MimeBodyPart;
/*     */ import javax.mail.internet.MimeMessage;
/*     */ import javax.mail.internet.MimeMultipart;
/*     */ import javax.mail.util.ByteArrayDataSource;
/*     */ import javax.servlet.http.HttpServlet;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;

/*     */ import org.faithfarm.datawriters.ApplicationDao;
/*     */ import org.faithfarm.datawriters.PersonDao;

import com.google.appengine.api.blobstore.BlobInfo;
/*     */ import com.google.appengine.api.blobstore.BlobInfoFactory;
/*     */ import com.google.appengine.api.blobstore.BlobKey;
/*     */ import com.google.appengine.api.blobstore.BlobstoreService;
/*     */ import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
/*     */ import com.google.appengine.api.datastore.DatastoreService;
/*     */ import com.google.appengine.api.datastore.DatastoreServiceFactory;
/*     */ import com.google.appengine.api.datastore.Entity;
/*     */ import com.google.appengine.api.datastore.Key;
/*     */ import com.google.appengine.api.datastore.KeyFactory;
/*     */ import com.google.appengine.api.urlfetch.URLFetchService;
/*     */ import com.google.appengine.api.urlfetch.URLFetchServiceFactory;
/*     */ import com.google.appengine.api.users.User;
/*     */ import com.google.appengine.api.users.UserService;
/*     */ import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.api.xmpp.Message;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FileUploadServlet extends HttpServlet
/*     */ {
/*  49 */   private static final HTMLBuilder html = new HTMLBuilder();
/*     */ 
/*  51 */   private static final Logger log = Logger.getLogger(FileUploadServlet.class
/*  52 */     .getName());
/*     */ 
/*  54 */   private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
/*  55 */   private static final PersonDao dao = new PersonDao();
/*     */ 
/*  57 */   private static final DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
/*     */ 
/*     */   protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException
/*     */   {
/*  61 */     URLFetchService fetchService = 
/*  62 */       URLFetchServiceFactory.getURLFetchService();
/*     */   }
/*     */ 
/*     */   protected void doPost(HttpServletRequest req, HttpServletResponse resp)
/*     */     throws IOException
/*     */   {
/*  80 */     URLFetchService fetchService = 
/*  81 */       URLFetchServiceFactory.getURLFetchService();
/*     */ 
/*  87 */     UserService userService = UserServiceFactory.getUserService();
/*  88 */     User user = userService.getCurrentUser();
/*  89 */     String docType = req.getParameter("docType");
/*     */ 
/*  91 */     Map blobs = this.blobstoreService.getUploadedBlobs(req);
/*  92 */     blobs.size();
/*     */ 
/*  94 */     BlobKey blobKey = (BlobKey)blobs.get("myFile");
/*     */ 
/*  96 */     BlobInfo blobInfo = null;
/*     */ 
/*  98 */     String url = "/jsp/student/profile/results.jsp";
/*     */ 
/* 100 */     if (blobKey == null) {
/* 101 */       resp.sendRedirect("/");
/*     */     }
/*     */     else {
/* 104 */       if ("artifact".equals(docType)) {
/* 105 */         String sKey = req.getParameter("personKey");
/* 106 */         Key key = KeyFactory.stringToKey(sKey);
/* 107 */         Entity Person = ApplicationDao.getEntity(key);
/*     */ 
/* 109 */         BlobInfo b = new BlobInfoFactory().loadBlobInfo(blobKey);
/* 110 */         String title = html.cleanData(req.getParameter("docTitle"));
/* 111 */         if (title.trim().length() == 0)
/* 112 */           title = b.getFilename();
/* 113 */         Entity PersonDocument = new Entity("PersonArtifact", Person.getKey());
/* 114 */         PersonDocument.setProperty("docKey", blobKey.getKeyString());
/* 115 */         PersonDocument.setProperty("title", title);
/* 116 */         PersonDocument.setProperty("content_type", b.getContentType());
/* 117 */         PersonDocument.setProperty("creationDate", new Date());
/* 118 */         ApplicationDao.createOrUpdateEntity(PersonDocument, user);
/* 119 */       } else if ("Donation".equals(docType)) {
/* 120 */         String sKey = req.getParameter("itemKey");
/* 121 */         Key key = KeyFactory.stringToKey(sKey);
/* 122 */         Entity Item = ApplicationDao.getEntity(key);
/* 123 */         Item.setProperty("imageKey", blobKey);
/* 124 */         ApplicationDao.createOrUpdateEntity(Item, user);
/* 125 */         url = "/jsp/item/results.jsp";
/*     */       } else {
/* 127 */         String sKey = req.getParameter("personKey");
/* 128 */         Key key = KeyFactory.stringToKey(sKey);
/* 129 */         Entity person = ApplicationDao.getEntity(key);
/* 130 */         String imageKey = person.getProperty("imageKey") != null ? person.getProperty("imageKey").toString() : null;
/* 131 */         if (imageKey != null) {
/* 132 */           this.blobstoreService.delete(new BlobKey[] { new BlobKey(imageKey) });
/*     */         }
/*     */ 
/* 135 */         person.setProperty("imageKey", blobKey);
/* 136 */         ApplicationDao.createOrUpdateEntity(person, user);
/*     */ 
/* 138 */         String firstName = person.getProperty("firstName") != null ? person.getProperty("firstName").toString() : "";
/* 139 */         String lastName = person.getProperty("lastName") != null ? person.getProperty("lastName").toString() : "";
/* 140 */         String fullName = String.format("%s %s", new Object[] { firstName, lastName });
/*     */         try
/*     */         {
/* 143 */           blobInfo = new BlobInfoFactory().loadBlobInfo(blobKey);
/* 144 */           String html = String.format("<html><body>An image was uploaded for <strong>%s</strong>.  See attachment.</body></html>", new Object[] { fullName });
/* 145 */           Session mailSession = Session.getDefaultInstance(new Properties(), null);
/* 146 */           MimeMessage message = new MimeMessage(mailSession);
/* 147 */           MimeMultipart mp_message = new MimeMultipart();
/* 148 */           MimeBodyPart image_part = new MimeBodyPart();
/* 149 */           MimeBodyPart html_part = new MimeBodyPart();
/* 150 */           html_part.setContent(html, "text/html");
/* 151 */           DataSource source = new ByteArrayDataSource(this.blobstoreService.fetchData(blobKey, 0L, blobInfo.getSize()), blobInfo.getContentType());
/*     */ 
/* 153 */           image_part.setDataHandler(new DataHandler(source));
/* 154 */           image_part.setFileName(blobInfo.getFilename());
/* 155 */           image_part.setHeader("Content-Type", blobInfo.getContentType());
/* 156 */           image_part.setDisposition("inline");
/* 157 */           mp_message.addBodyPart(html_part);
/* 158 */           mp_message.addBodyPart(image_part);
/* 159 */           message.setContent(mp_message);
/* 160 */           message.setFrom(new InternetAddress("faithfarm.emailteam@gmail.com", "Faith Farm OSS"));
/* 161 */           //message.addRecipient(Message.RecipientType.BCC, new InternetAddress("payden@paydensutherland.com", "Payden Sutherland"));
/* 162 */           //message.addRecipient(Message.RecipientType.TO, new InternetAddress("rrosendo@faithfarm.org", "Richard Rosendo"));
/* 163 */           //message.addRecipient(Message.RecipientType.TO, new InternetAddress("rcorring@gmail.com", "Rick Corring"));
/* 164 */           //message.addRecipient(Message.RecipientType.TO, new InternetAddress("richardmogged@gmail.com", "Richard Mogged"));
/* 165 */           //message.addRecipient(Message.RecipientType.TO, new InternetAddress("gsteffe@faithfarm.org", "Pastor Garry Steffe"));
/* 166 */           //message.addRecipient(Message.RecipientType.TO, new InternetAddress("smokeygl@juno.com", "Pastor Garry Steffe"));
/* 167 */           //message.addRecipient(Message.RecipientType.BCC, new InternetAddress("jeisele1@fau.edu", "Ryan Eisele"));
/* 168 */           message.setSubject(String.format("Image uploaded for %s", new Object[] { fullName }));
/* 169 */           message.saveChanges();
/* 170 */           Transport.send(message);

/*     */         } catch (Exception e) {
/* 172 */           log.severe("Exception occurred during mail generation: " + e.getMessage());
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */       try
/*     */       {
/* 179 */         req.getRequestDispatcher(req.getContextPath() + url).forward(req, resp);
/*     */       }
/*     */       catch (Exception localException1)
/*     */       {
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\development\workspace\faithfarmoss\WEB-INF\classes\
 * Qualified Name:     org.faithfarm.utilities.FileUploadServlet
 * JD-Core Version:    0.6.2
 */