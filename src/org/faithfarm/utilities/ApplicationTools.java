/*    */ package org.faithfarm.utilities;
/*    */ 
/*    */ import com.google.appengine.api.datastore.Entity;
/*    */ import java.io.Serializable;
/*    */ import java.io.UnsupportedEncodingException;
/*    */ import java.security.MessageDigest;
/*    */ import java.security.NoSuchAlgorithmException;
/*    */ import java.util.ArrayList;
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.Logger;
/*    */ 
/*    */ public class ApplicationTools
/*    */   implements Serializable
/*    */ {
/* 16 */   private static final Logger logger = Logger.getLogger(ApplicationTools.class.getCanonicalName());
/*    */ 
/*    */   public static ArrayList<Entity> convertIterableToList(Iterable<Entity> list)
/*    */   {
/* 20 */     ArrayList newList = new ArrayList();
/* 21 */     for (Entity obj : list) {
/* 22 */       newList.add(obj);
/*    */     }
/*    */ 
/* 25 */     return newList;
/*    */   }
/*    */ 
/*    */   public static String hashString(String hashThis) {
/* 29 */     String hash = null;
/*    */     try {
/* 31 */       byte[] hashBytes = hashThis.getBytes("UTF-8");
/* 32 */       MessageDigest md = MessageDigest.getInstance("SHA");
/* 33 */       md.update(hashBytes);
/* 34 */       byte[] digest = md.digest();
/* 35 */       StringBuffer hexString = new StringBuffer();
/* 36 */       for (int i = 0; i < digest.length; i++) {
/* 37 */         hexString.append(Integer.toHexString(0xFF & digest[i]));
/*    */       }
/* 39 */       hash = hexString.toString();
/*    */     } catch (NoSuchAlgorithmException e) {
/* 41 */       logger.log(Level.SEVERE, e.getMessage());
/*    */     } catch (UnsupportedEncodingException e) {
/* 43 */       logger.log(Level.SEVERE, e.getMessage());
/*    */     }
/* 45 */     return hash;
/*    */   }
/*    */ }

/* Location:           C:\development\workspace\faithfarmoss\WEB-INF\classes\
 * Qualified Name:     org.faithfarm.utilities.ApplicationTools
 * JD-Core Version:    0.6.2
 */