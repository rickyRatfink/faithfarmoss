/*    */ package org.faithfarm.utilities;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import java.text.DateFormat;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Calendar;
/*    */ import java.util.Date;
/*    */ 
/*    */ public class Test
/*    */ {
/*    */   public static void main(String[] args)
/*    */   {
/* 12 */     Date d = new Date();
/* 13 */     System.out.println("year=" + d.getYear());
/* 14 */     System.out.println("day=" + d.getDay());
/* 15 */     System.out.println("month=" + d.getMonth());
/*    */ 
/* 18 */     String entryDate = "Mon Apr 24 09:27:30 EDT 2012";
/*    */ 
/* 21 */     Date date = new Date();
/*    */ 
/* 23 */     DateFormat formatter = new SimpleDateFormat("dd-MMM-yy");
/* 24 */     Calendar cal1 = Calendar.getInstance();
/* 25 */     cal1.setTime(date);
/*    */ 
/* 28 */     date = new Date(entryDate);
/* 29 */     Calendar cal2 = Calendar.getInstance();
/* 30 */     cal2.setTime(date);
/* 31 */     cal2.add(4, 4);
/* 32 */     System.out.println("Comparing " + cal1.getTime() + " to " + cal2.getTime());
/* 33 */     System.out.println(cal1.before(cal2));
/*    */   }
/*    */ }

/* Location:           C:\development\workspace\faithfarmoss\WEB-INF\classes\
 * Qualified Name:     org.faithfarm.utilities.Test
 * JD-Core Version:    0.6.2
 */