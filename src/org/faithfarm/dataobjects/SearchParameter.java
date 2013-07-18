/*    */ package org.faithfarm.dataobjects;
/*    */ 
/*    */ /*    */ import java.io.Serializable;
/*    */ import java.util.List;

import com.google.appengine.api.datastore.Query;
/*    */ 
/*    */ public class SearchParameter
/*    */   implements Serializable
/*    */ {
/* 10 */   private String name = "";
/* 11 */   private String value = "";
/* 12 */   private List<String> values = null;
/*    */   private Query.FilterOperator operator;
/*    */ 
/*    */   public SearchParameter(String name, String value, Query.FilterOperator operator)
/*    */   {
/* 18 */     this.name = name;
/* 19 */     this.value = value;
/* 20 */     this.operator = operator;
/*    */   }
/*    */ 
/*    */   public SearchParameter(String name, List<String> values, Query.FilterOperator operator)
/*    */   {
/* 25 */     this.name = name;
/* 26 */     this.value = this.value;
/* 27 */     this.operator = operator;
/*    */   }
/*    */ 
/*    */   public List getValues()
/*    */   {
/* 32 */     return this.values;
/*    */   }
/*    */ 
/*    */   public void setValues(List values) {
/* 36 */     this.values = values;
/*    */   }
/*    */ 
/*    */   public String getName() {
/* 40 */     return this.name;
/*    */   }
/*    */ 
/*    */   public void setName(String name)
/*    */   {
/* 45 */     this.name = name;
/*    */   }
/*    */ 
/*    */   public String getValue()
/*    */   {
/* 50 */     return this.value;
/*    */   }
/*    */ 
/*    */   public void setValue(String value)
/*    */   {
/* 55 */     this.value = value;
/*    */   }
/*    */ 
/*    */   public Query.FilterOperator getOperator()
/*    */   {
/* 60 */     return this.operator;
/*    */   }
/*    */ 
/*    */   public void setOperator(Query.FilterOperator operator)
/*    */   {
/* 65 */     this.operator = operator;
/*    */   }
/*    */ }

/* Location:           C:\development\workspace\faithfarmoss\WEB-INF\classes\
 * Qualified Name:     org.faithfarm.dataobjects.SearchParameter
 * JD-Core Version:    0.6.2
 */