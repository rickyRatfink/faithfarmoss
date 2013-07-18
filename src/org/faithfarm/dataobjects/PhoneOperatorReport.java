/*    */ package org.faithfarm.dataobjects;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class PhoneOperatorReport
/*    */   implements Serializable
/*    */ {
/*    */   Integer newTicket;
/*    */   Integer rejects;
/*    */   Integer donation;
/*    */   Integer cancelled;
/*    */   Integer reschedule;
/*    */   Integer previous;
/*    */   Integer other;
/*    */   String dayOfWeek;
/*    */ 
/*    */   public Integer getNewTicket()
/*    */   {
/* 16 */     return this.newTicket;
/*    */   }
/*    */   public void setNewTicket(Integer newTicket) {
/* 19 */     this.newTicket = newTicket;
/*    */   }
/*    */   public Integer getRejects() {
/* 22 */     return this.rejects;
/*    */   }
/*    */   public void setRejects(Integer rejects) {
/* 25 */     this.rejects = rejects;
/*    */   }
/*    */   public Integer getDonation() {
/* 28 */     return this.donation;
/*    */   }
/*    */   public void setDonation(Integer donation) {
/* 31 */     this.donation = donation;
/*    */   }
/*    */   public Integer getCancelled() {
/* 34 */     return this.cancelled;
/*    */   }
/*    */   public void setCancelled(Integer cancelled) {
/* 37 */     this.cancelled = cancelled;
/*    */   }
/*    */   public Integer getReschedule() {
/* 40 */     return this.reschedule;
/*    */   }
/*    */   public void setReschedule(Integer reschedule) {
/* 43 */     this.reschedule = reschedule;
/*    */   }
/*    */   public Integer getPrevious() {
/* 46 */     return this.previous;
/*    */   }
/*    */   public void setPrevious(Integer previous) {
/* 49 */     this.previous = previous;
/*    */   }
/*    */   public Integer getOther() {
/* 52 */     return this.other;
/*    */   }
/*    */   public void setOther(Integer other) {
/* 55 */     this.other = other;
/*    */   }
/*    */   public String getDayOfWeek() {
/* 58 */     return this.dayOfWeek;
/*    */   }
/*    */   public void setDayOfWeek(String dayOfWeek) {
/* 61 */     this.dayOfWeek = dayOfWeek;
/*    */   }
/*    */ }

/* Location:           C:\development\workspace\faithfarmoss\WEB-INF\classes\
 * Qualified Name:     org.faithfarm.dataobjects.PhoneOperatorReport
 * JD-Core Version:    0.6.2
 */