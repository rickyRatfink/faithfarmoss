/*    */ package org.faithfarm.dataobjects;
/*    */ 
/*    */ import com.google.appengine.api.datastore.Key;
/*    */ import javax.jdo.annotations.IdGeneratorStrategy;
/*    */ import javax.jdo.annotations.PersistenceCapable;
/*    */ import javax.jdo.annotations.Persistent;
/*    */ import javax.jdo.annotations.PrimaryKey;
/*    */ 
/*    */ @PersistenceCapable
/*    */ public class JdoTest
/*    */ {
/*    */ 
/*    */   @PrimaryKey
/*    */   @Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
/*    */   private Key key;
/*    */ 
/*    */   @Persistent
/*    */   private String firstName;
/*    */ 
/*    */   @Persistent
/*    */   private String lastName;
/*    */ 
/*    */   public JdoTest(String firstName, String lastName)
/*    */   {
/* 17 */     this.firstName = firstName;
/* 18 */     this.lastName = lastName;
/*    */   }
/*    */ 
/*    */   public String getFirstName() {
/* 22 */     return this.firstName;
/*    */   }
/*    */ 
/*    */   public String getLastName() {
/* 26 */     return this.lastName;
/*    */   }
/*    */   public String getName() {
/* 29 */     return this.firstName + " " + this.lastName;
/*    */   }
/*    */ 
/*    */   public Key getKey() {
/* 33 */     return this.key;
/*    */   }
/*    */ 
/*    */   public void setLastName(String lastName) {
/* 37 */     this.lastName = lastName;
/*    */   }
/*    */ }

/* Location:           C:\development\workspace\faithfarmoss\WEB-INF\classes\
 * Qualified Name:     org.faithfarm.dataobjects.JdoTest
 * JD-Core Version:    0.6.2
 */