/*    */ package org.faithfarm.utilities;
/*    */ 
/*    */ import javax.jdo.JDOHelper;
/*    */ import javax.jdo.PersistenceManagerFactory;
/*    */ 
/*    */ public final class PMF
/*    */ {
/*  8 */   private static final PersistenceManagerFactory pmfInstance = JDOHelper.getPersistenceManagerFactory("transactions-optional");
/*    */ 
/*    */   public static PersistenceManagerFactory get()
/*    */   {
/* 13 */     return pmfInstance;
/*    */   }
/*    */ }

/* Location:           C:\development\workspace\faithfarmoss\WEB-INF\classes\
 * Qualified Name:     org.faithfarm.utilities.PMF
 * JD-Core Version:    0.6.2
 */