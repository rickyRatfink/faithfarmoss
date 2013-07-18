/*    */ package org.faithfarm.utilities;
/*    */ 
/*    */ import com.google.appengine.api.datastore.Blob;
/*    */ import com.google.appengine.api.datastore.Key;
/*    */ import javax.jdo.annotations.Extension;
/*    */ import javax.jdo.annotations.IdGeneratorStrategy;
/*    */ import javax.jdo.annotations.IdentityType;
/*    */ import javax.jdo.annotations.PersistenceCapable;
/*    */ import javax.jdo.annotations.Persistent;
/*    */ import javax.jdo.annotations.PrimaryKey;
/*    */ 
/*    */ @PersistenceCapable(identityType=IdentityType.APPLICATION)
/*    */ public class Photo
/*    */ {
/*    */ 
/*    */   @PrimaryKey
/*    */   @Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
/*    */   private Key key;
/*    */ 
/*    */   @Persistent
/*    */   private String title;
/*    */ 
/*    */   @Persistent
/*    */   @Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
/*    */   private String imageType;
/*    */ 
/*    */   @Persistent
/*    */   private Blob image;
/*    */ 
/*    */   public Long getId()
/*    */   {
/* 37 */     return Long.valueOf(this.key.getId());
/*    */   }
/*    */ 
/*    */   public String getTitle() {
/* 41 */     return this.title;
/*    */   }
/*    */ 
/*    */   public String getImageType() {
/* 45 */     return this.imageType;
/*    */   }
/*    */ 
/*    */   public byte[] getImage() {
/* 49 */     if (this.image == null) {
/* 50 */       return null;
/*    */     }
/*    */ 
/* 53 */     return this.image.getBytes();
/*    */   }
/*    */ 
/*    */   public void setTitle(String title) {
/* 57 */     this.title = title;
/*    */   }
/*    */ 
/*    */   public void setImageType(String imageType) {
/* 61 */     this.imageType = imageType;
/*    */   }
/*    */ 
/*    */   public void setImage(byte[] bytes) {
/* 65 */     this.image = new Blob(bytes);
/*    */   }
/*    */ }

/* Location:           C:\development\workspace\faithfarmoss\WEB-INF\classes\
 * Qualified Name:     org.faithfarm.utilities.Photo
 * JD-Core Version:    0.6.2
 */