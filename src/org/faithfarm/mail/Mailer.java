/*    */ package org.faithfarm.mail;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import java.io.Serializable;
/*    */ import java.io.UnsupportedEncodingException;
/*    */ import java.util.Properties;
/*    */ import javax.mail.Message;
/*    */ import javax.mail.Message.RecipientType;
/*    */ import javax.mail.MessagingException;
/*    */ import javax.mail.Session;
/*    */ import javax.mail.Transport;
/*    */ import javax.mail.internet.AddressException;
/*    */ import javax.mail.internet.InternetAddress;
/*    */ import javax.mail.internet.MimeMessage;
/*    */ 
/*    */ public class Mailer
/*    */   implements Serializable
/*    */ {
/*    */   public static void sendMail(String recipient, String subject, StringBuffer message, String sender)
/*    */   {
/* 22 */     Properties props = new Properties();
/* 23 */     Session session = Session.getDefaultInstance(props, null);
/*    */ 
/* 25 */     String msgBody = "...";
/*    */     try
/*    */     {
/* 28 */       Message msg = new MimeMessage(session);
/* 29 */       msg.setFrom(new InternetAddress(sender));
/* 30 */       msg.addRecipient(Message.RecipientType.TO, 
/* 31 */         new InternetAddress(recipient));
/* 32 */       msg.setSubject(subject);
/* 33 */       msg.setText(msgBody);
/* 34 */       Transport.send(msg);
/*    */     }
/*    */     catch (AddressException e) {
/* 37 */       System.out.println("error:" + e.getMessage());
/*    */     } catch (MessagingException e) {
/* 39 */       System.out.println("error:" + e.getMessage());
/*    */     }
/*    */   }
/*    */ 
/*    */   public static void sendNewStudentMail(String receiver, String subject, String body) {
/* 44 */     Properties props = new Properties();
/* 45 */     Session session = Session.getDefaultInstance(props, null);
/*    */     try
/*    */     {
/* 48 */       Message msg = new MimeMessage(session);
/*    */ 
/* 50 */       msg.setFrom(new InternetAddress("faithfarm.emailteam@gmail.com", "faithfarm.emailteam@gmail.com"));
/* 51 */       msg.addRecipient(Message.RecipientType.TO, 
/* 52 */         new InternetAddress(receiver, receiver));
/* 53 */       msg.setSubject(subject);
/* 54 */       msg.setContent(body, "text/html");
/* 55 */       Transport.send(msg);
/*    */     }
/*    */     catch (AddressException e) {
/* 58 */       System.out.println("error:" + e.getMessage());
/*    */     } catch (MessagingException e) {
/* 60 */       System.out.println("error:" + e.getMessage());
/*    */     } catch (UnsupportedEncodingException e) {
/* 62 */       System.out.println("error:" + e.getMessage());
/*    */ 
/* 64 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\development\workspace\faithfarmoss\WEB-INF\classes\
 * Qualified Name:     org.faithfarm.mail.Mailer
 * JD-Core Version:    0.6.2
 */