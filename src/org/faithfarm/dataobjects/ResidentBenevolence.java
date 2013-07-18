/*     */ package org.faithfarm.dataobjects;
/*     */ 
/*     */ import com.google.appengine.api.datastore.Key;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class ResidentBenevolence
/*     */   implements Serializable
/*     */ {
/*   9 */   private int weeks = 0;
/*  10 */   private String firstName = "";
/*  11 */   private String lastName = "";
/*  12 */   private String entryDate = "";
/*  13 */   private long prepaidYTD = 0L;
/*  14 */   private long prevSavings = 0L;
/*  15 */   private long lessDeduction = 0L;
/*  16 */   private String deductionDescription = "";
/*  17 */   private long amtDue = 0L;
/*  18 */   private long amtPaid = 0L;
/*  19 */   private long weeklySavings = 0L;
/*  20 */   private long amtPaidYTD = 0L;
/*  21 */   private long totalSavings = 0L;
/*  22 */   private String exitDate = "";
/*  23 */   private String exitReason = "";
/*  24 */   private Key personKey = null;
/*     */ 
/*     */   public Key getPersonKey()
/*     */   {
/*  29 */     return this.personKey;
/*     */   }
/*     */   public void setPersonKey(Key personKey) {
/*  32 */     this.personKey = personKey;
/*     */   }
/*     */   public String getExitDate() {
/*  35 */     return this.exitDate;
/*     */   }
/*     */   public void setExitDate(String exitDate) {
/*  38 */     this.exitDate = exitDate;
/*     */   }
/*     */   public String getExitReason() {
/*  41 */     return this.exitReason;
/*     */   }
/*     */   public void setExitReason(String exitReason) {
/*  44 */     this.exitReason = exitReason;
/*     */   }
/*     */   public int getWeeks() {
/*  47 */     return this.weeks;
/*     */   }
/*     */   public void setWeeks(int weeks) {
/*  50 */     this.weeks = weeks;
/*     */   }
/*     */   public String getFirstName() {
/*  53 */     return this.firstName;
/*     */   }
/*     */   public void setFirstName(String firstName) {
/*  56 */     this.firstName = firstName;
/*     */   }
/*     */   public String getLastName() {
/*  59 */     return this.lastName;
/*     */   }
/*     */   public void setLastName(String lastName) {
/*  62 */     this.lastName = lastName;
/*     */   }
/*     */   public String getEntryDate() {
/*  65 */     return this.entryDate;
/*     */   }
/*     */   public void setEntryDate(String entryDate) {
/*  68 */     this.entryDate = entryDate;
/*     */   }
/*     */   public double getPrepaidYTD() {
/*  71 */     return this.prepaidYTD;
/*     */   }
/*     */   public long getPrevSavings() {
/*  74 */     return this.prevSavings;
/*     */   }
/*     */   public void setPrevSavings(long prevSavings) {
/*  77 */     this.prevSavings = prevSavings;
/*     */   }
/*     */   public long getLessDeduction() {
/*  80 */     return this.lessDeduction;
/*     */   }
/*     */   public void setLessDeduction(long lessDeduction) {
/*  83 */     this.lessDeduction = lessDeduction;
/*     */   }
/*     */   public String getDeductionDescription() {
/*  86 */     return this.deductionDescription;
/*     */   }
/*     */   public void setDeductionDescription(String deductionDescription) {
/*  89 */     this.deductionDescription = deductionDescription;
/*     */   }
/*     */   public long getAmtDue() {
/*  92 */     return this.amtDue;
/*     */   }
/*     */   public void setAmtDue(long amtDue) {
/*  95 */     this.amtDue = amtDue;
/*     */   }
/*     */   public long getAmtPaid() {
/*  98 */     return this.amtPaid;
/*     */   }
/*     */   public void setAmtPaid(long amtPaid) {
/* 101 */     this.amtPaid = amtPaid;
/*     */   }
/*     */   public long getWeeklySavings() {
/* 104 */     return this.weeklySavings;
/*     */   }
/*     */   public void setWeeklySavings(long weeklySavings) {
/* 107 */     this.weeklySavings = weeklySavings;
/*     */   }
/*     */   public long getAmtPaidYTD() {
/* 110 */     return this.amtPaidYTD;
/*     */   }
/*     */   public void setAmtPaidYTD(long amtPaidYTD) {
/* 113 */     this.amtPaidYTD = amtPaidYTD;
/*     */   }
/*     */   public long getTotalSavings() {
/* 116 */     return this.totalSavings;
/*     */   }
/*     */   public void setTotalSavings(long totalSavings) {
/* 119 */     this.totalSavings = totalSavings;
/*     */   }
/*     */   public void setPrepaidYTD(long prepaidYTD) {
/* 122 */     this.prepaidYTD = prepaidYTD;
/*     */   }
/*     */ }

/* Location:           C:\development\workspace\faithfarmoss\WEB-INF\classes\
 * Qualified Name:     org.faithfarm.dataobjects.ResidentBenevolence
 * JD-Core Version:    0.6.2
 */