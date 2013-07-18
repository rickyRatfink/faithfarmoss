/*    */ package org.faithfarm.abstraction;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class DAObject
/*    */ {
/*    */   private Map<String, Object> properties;
/*    */   private String kind;
/*    */ 
/*    */   public DAObject(String kind)
/*    */   {
/* 11 */     this.kind = kind;
/* 12 */     this.properties = new HashMap();
/*    */   }
/*    */ 
/*    */   public DAObject(String kind, Map<String, Object> props) {
/* 16 */     this.kind = kind;
/* 17 */     this.properties = props;
/*    */   }
/*    */ 
/*    */   public void setProperty(String propertyName, Object value) {
/* 21 */     this.properties.put(propertyName, value);
/*    */   }
/*    */ 
/*    */   public void setProperties(Map<String, Object> props) {
/* 25 */     this.properties = props;
/*    */   }
/*    */ 
/*    */   public Object getProperty(String propertyName) {
/* 29 */     return this.properties.get(propertyName);
/*    */   }
/*    */ 
/*    */   public Map<String, Object> getProperties() {
/* 33 */     return this.properties;
/*    */   }
/*    */ 
/*    */   public String getKind() {
/* 37 */     return this.kind;
/*    */   }
/*    */ }

/* Location:           C:\development\workspace\faithfarmoss\WEB-INF\classes\
 * Qualified Name:     org.faithfarm.abstraction.DAObject
 * JD-Core Version:    0.6.2
 */