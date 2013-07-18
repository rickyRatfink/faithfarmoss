/*     */ package org.faithfarm;
/*     */ 
/*     */ import com.google.appengine.api.datastore.DatastoreService;
/*     */ import com.google.appengine.api.datastore.DatastoreServiceFactory;
/*     */ import com.google.appengine.api.datastore.Entity;
/*     */ import com.google.appengine.api.datastore.EntityNotFoundException;
/*     */ import com.google.appengine.api.datastore.Key;
/*     */ import com.google.appengine.api.datastore.KeyFactory;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
/*     */ import java.util.Properties;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.mail.Message;
/*     */ import javax.mail.Message.RecipientType;
/*     */ import javax.mail.MessagingException;
/*     */ import javax.mail.Multipart;
/*     */ import javax.mail.Session;
/*     */ import javax.mail.Transport;
/*     */ import javax.mail.internet.AddressException;
/*     */ import javax.mail.internet.InternetAddress;
/*     */ import javax.mail.internet.MimeBodyPart;
/*     */ import javax.mail.internet.MimeMessage;
/*     */ import javax.mail.internet.MimeMultipart;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.faithfarm.datawriters.ApplicationDao;
/*     */ 
/*     */ public class WorkerServlet extends BaseServlet
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4558190689215651007L;
/*  43 */   private static final Logger logger = Logger.getLogger(WorkerServlet.class.getCanonicalName());
/*  44 */   private static final DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
/*     */ 
/*     */   protected void doGet(HttpServletRequest req, HttpServletResponse resp)
/*     */     throws ServletException, IOException
/*     */   {
/*  53 */     super.doGet(req, resp);
/*     */ 
/*  55 */     Properties props = new Properties();
/*  56 */     Session mailSession = Session.getDefaultInstance(props, null);
/*  57 */     String uri = req.getRequestURI();
/*  58 */     String[] split = uri.split("/worker/");
/*  59 */     String action = null;
/*  60 */     String[] args = null;
/*  61 */     if (split.length != 2)
/*  62 */       action = "";
/*     */     else {
/*  64 */       action = split[1];
/*     */     }
/*  66 */     if (action.indexOf('/') != -1) {
/*  67 */       String[] actionSplit = action.split("/");
/*  68 */       args = (String[])Arrays.copyOfRange(actionSplit, 1, actionSplit.length);
/*  69 */       action = actionSplit[0];
/*     */     }
/*  71 */     PrintWriter writer = resp.getWriter();
/*  72 */     switch ($SWITCH_TABLE$org$faithfarm$WorkerServlet$actions()[actions.valueOf(action.toUpperCase()).ordinal()]) {
/*     */     case 1:
/*  74 */       if ((args == null) || (args.length == 0) || (args[0] == null) || 
/*  75 */         (args[0].equals(""))) {
/*  76 */         writer.print("Invalid id supplied to DISPATCH_EMAIL");
/*  77 */         writer.close();
/*     */       }
/*     */       else {
/*  80 */         Key dispatchKey = KeyFactory.stringToKey(args[0]);
/*  81 */         Entity dispatch = null;
/*     */         try {
/*  83 */           dispatch = datastore.get(dispatchKey);
/*     */         } catch (EntityNotFoundException e) {
/*  85 */           logger.log(Level.SEVERE, e.getMessage());
/*  86 */           writer.print("Unable to find dispatch entity: " + dispatchKey);
/*  87 */           writer.close();
/*  88 */           return;
/*     */         }
/*  90 */         Entity person = null;
/*     */         try {
/*  92 */           person = datastore.get(dispatch.getParent());
/*     */         } catch (EntityNotFoundException e) {
/*  94 */           logger.log(Level.SEVERE, "Unable to find parent entity for dispatch: " + dispatchKey.getId());
/*  95 */           writer.print("Unable to find person entity for dispatch: " + dispatchKey.getId());
/*  96 */           writer.close();
/*  97 */           return;
/*     */         }
/*  99 */         Entity address = ApplicationDao.getEntityByParent("Address", person.getKey());
/* 100 */         if (address == null) {
/* 101 */           writer.print("Unable to find address for person");
/* 102 */           writer.close();
/*     */         }
/*     */         else {
/* 105 */           String email = (String)address.getProperty("email");
/* 106 */           String fullName = (String)person.getProperty("firstName") + " " + (String)person.getProperty("lastName");
/*     */           try {
/* 108 */             StringBuilder body = new StringBuilder();
/* 109 */             body.append("<div>To Mr. or Mrs. " + person.getProperty("lastName") + ",</div>\n");
/* 110 */             body.append("<div>&nbsp;</div>\n");
/* 111 */             body.append("<div>This is a confirmation of your scheduled donation to Faith Farm Ministries.  A donation truck is scheduled to come by your residence or business on " + dispatch.getProperty("dispatchDate") + ".  The donation truck will be going to the following address: </div>");
/* 112 */             body.append("<div>&nbsp;</div><div>" + address.getProperty("addressLine1") + "</div>\n");
/* 113 */             if ((address.getProperty("addressLine2") != null) && (!address.getProperty("addressLine2").equals(""))) {
/* 114 */               body.append("<div>" + address.getProperty("addressLine2") + "</div>\n");
/*     */             }
/* 116 */             body.append("<div>" + address.getProperty("city") + ", " + address.getProperty("state") + " " + address.getProperty("zipcode") + "</div>\n");
/* 117 */             body.append("<div>&nbsp;</div><div>If this information is incorrect, please contact Faith Farm Ministries by phone at 954-763-7787.</div>\n");
/* 118 */             body.append("<div>&nbsp;</div><div>We appreciate your donation!</div>\n");
/* 119 */             body.append("<div>&nbsp;</div><div>Sincerely,</div><div>Faith Farm Ministries - Donations Department</div>\n");
/* 120 */             Multipart mp = new MimeMultipart();
/* 121 */             MimeBodyPart htmlPart = new MimeBodyPart();
/* 122 */             htmlPart.setContent(body, "text/html");
/* 123 */             mp.addBodyPart(htmlPart);
/* 124 */             Message msg = new MimeMessage(mailSession);
/* 125 */             msg.setFrom(new InternetAddress("donations@faith-farm.net", "Faith Farm Donations"));
/* 126 */             msg.addRecipient(Message.RecipientType.TO, new InternetAddress("payden@paydensutherland.com", fullName));
/* 127 */             msg.setSubject("Scheduled Donation Confirmation: " + dispatch.getProperty("dispatchDate"));
/* 128 */             msg.setContent(mp);
/* 129 */             msg.setText("This is a test");
/* 130 */             Transport.send(msg);
/*     */           } catch (AddressException e) {
/* 132 */             logger.log(Level.SEVERE, e.getMessage());
/*     */           } catch (MessagingException e) {
/* 134 */             logger.log(Level.SEVERE, e.getMessage());
/*     */           }
/* 136 */           logger.log(Level.SEVERE, "Success");
/* 137 */           writer.write("Successfully sent out email");
/* 138 */           writer.close(); } 
/* 139 */       }break;
/*     */     case 2:
/* 142 */       String email_subject = req.getParameter("subject") == null ? "" : req.getParameter("subject");
/* 143 */       String email_text = req.getParameter("msg_text") == null ? "" : req.getParameter("msg_text");
/* 144 */       String data = req.getParameter("data") == null ? "" : req.getParameter("data");
/* 145 */       String[] data_parts = data.split("::");
/* 146 */       String theEmail = data_parts[0];
/* 147 */       String firstName = data_parts[1];
/* 148 */       String lastName = data_parts[2];
/* 149 */       email_text = email_text.replace("%first%", firstName);
/* 150 */       email_text = email_text.replace("%last%", lastName);
/* 151 */       System.out.println("Sending email to " + theEmail + ": \n" + email_text);
/*     */       try {
/* 153 */         Multipart mp = new MimeMultipart();
/* 154 */         MimeBodyPart textPart = new MimeBodyPart();
/* 155 */         textPart.setText("Text part");
/* 156 */         MimeBodyPart htmlPart = new MimeBodyPart();
/* 157 */         htmlPart.setContent(email_text, "text/html");
/*     */ 
/* 159 */         mp.addBodyPart(textPart);
/* 160 */         mp.addBodyPart(htmlPart);
/* 161 */         writer.println(mp.getCount() + "<br/>");
/*     */ 
/* 163 */         Message msg = new MimeMessage(mailSession);
/* 164 */         msg.setFrom(new InternetAddress("donations@faith-farm.net", "Faith Farm Ministries"));
/* 165 */         msg.addRecipient(Message.RecipientType.TO, new InternetAddress(theEmail, firstName + " " + lastName));
/* 166 */         msg.setSubject(email_subject);
/* 167 */         msg.setContent(mp);
/* 168 */         msg.saveChanges();
/*     */ 
/* 170 */         Transport.send(msg);
/*     */       } catch (AddressException e) {
/* 172 */         logger.log(Level.SEVERE, e.getMessage());
/*     */       } catch (MessagingException e) {
/* 174 */         logger.log(Level.SEVERE, e.getMessage());
/*     */       }
/*     */     }
/*     */   }
private int[] $SWITCH_TABLE$org$faithfarm$WorkerServlet$actions() {
	// TODO Auto-generated method stub
	return null;
}
/*     */ 
/*     */   protected void doPost(HttpServletRequest req, HttpServletResponse resp)
/*     */     throws ServletException, IOException
/*     */   {
/* 186 */     doGet(req, resp);
/*     */   }
/*     */ 
/*     */   private static enum actions
/*     */   {
/*  47 */     DISPATCH_EMAIL, 
/*  48 */     MASSEMAIL;
/*     */   }
/*     */ }

/* Location:           C:\development\workspace\faithfarmoss\WEB-INF\classes\
 * Qualified Name:     org.faithfarm.WorkerServlet
 * JD-Core Version:    0.6.2
 */