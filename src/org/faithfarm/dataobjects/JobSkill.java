/*     */ package org.faithfarm.dataobjects;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class JobSkill
/*     */   implements Serializable
/*     */ {
/*     */   private long id;
/*   8 */   private String officeWork = "";
/*   9 */   private String mechanic = "";
/*  10 */   private String painter = "";
/*  11 */   private String upholstery = "";
/*  12 */   private String maintenance = "";
/*  13 */   private String electrician = "";
/*  14 */   private String cook = "";
/*  15 */   private String airConditioning = "";
/*  16 */   private String radioTvRepair = "";
/*  17 */   private String autoBodyRepair = "";
/*  18 */   private String welder = "";
/*  19 */   private String carpenter = "";
/*  20 */   private String landscaping = "";
/*  21 */   private String custodian = "";
/*  22 */   private String warehousing = "";
/*  23 */   private String sales = "";
/*  24 */   private String applianceRepair = "";
/*  25 */   private String truckDriver = "";
/*  26 */   private String forkLiftOperator = "";
/*  27 */   private String computerRepair = "";
/*  28 */   private String plumber = "";
/*  29 */   private String kitchen = "";
/*  30 */   private String phoneRoom = "";
/*  31 */   private String clothesSorter = "";
/*  32 */   private String computerProgramming = "";
/*  33 */   private String otherSkill = "";
/*  34 */   private String employer1 = "";
/*  35 */   private String jobTitle1 = "";
/*  36 */   private String beginDate1 = "";
/*  37 */   private String endDate1 = "";
/*  38 */   private String employer2 = "";
/*  39 */   private String jobTitle2 = "";
/*  40 */   private String beginDate2 = "";
/*  41 */   private String endDate2 = "";
/*  42 */   private String employer3 = "";
/*  43 */   private String jobTitle3 = "";
/*  44 */   private String beginDate3 = "";
/*  45 */   private String endDate3 = "";
/*  46 */   private String employer4 = "";
/*  47 */   private String jobTitle4 = "";
/*  48 */   private String beginDate4 = "";
/*  49 */   private String endDate4 = "";
/*  50 */   private String contactName1 = "";
/*  51 */   private String contactNumber1 = "";
/*  52 */   private String contactName2 = "";
/*  53 */   private String contactNumber2 = "";
/*  54 */   private String contactName3 = "";
/*  55 */   private String contactNumber3 = "";
/*  56 */   private String contactName4 = "";
/*  57 */   private String contactNumber4 = "";
/*     */ 
/*     */   public long getId() {
/*  60 */     return this.id;
/*     */   }
/*     */   public void setId(long id) {
/*  63 */     this.id = id;
/*     */   }
/*     */   public String getOfficeWork() {
/*  66 */     return this.officeWork;
/*     */   }
/*     */   public void setOfficeWork(String officeWork) {
/*  69 */     this.officeWork = officeWork;
/*     */   }
/*     */   public String getMechanic() {
/*  72 */     return this.mechanic;
/*     */   }
/*     */   public void setMechanic(String mechanic) {
/*  75 */     this.mechanic = mechanic;
/*     */   }
/*     */   public String getPainter() {
/*  78 */     return this.painter;
/*     */   }
/*     */   public void setPainter(String painter) {
/*  81 */     this.painter = painter;
/*     */   }
/*     */   public String getUpholstery() {
/*  84 */     return this.upholstery;
/*     */   }
/*     */   public void setUpholstery(String upholstery) {
/*  87 */     this.upholstery = upholstery;
/*     */   }
/*     */   public String getMaintenance() {
/*  90 */     return this.maintenance;
/*     */   }
/*     */   public void setMaintenance(String maintenance) {
/*  93 */     this.maintenance = maintenance;
/*     */   }
/*     */   public String getElectrician() {
/*  96 */     return this.electrician;
/*     */   }
/*     */   public void setElectrician(String electrician) {
/*  99 */     this.electrician = electrician;
/*     */   }
/*     */   public String getCook() {
/* 102 */     return this.cook;
/*     */   }
/*     */   public void setCook(String cook) {
/* 105 */     this.cook = cook;
/*     */   }
/*     */   public String getAirConditioning() {
/* 108 */     return this.airConditioning;
/*     */   }
/*     */   public void setAirConditioning(String airConditioning) {
/* 111 */     this.airConditioning = airConditioning;
/*     */   }
/*     */   public String getRadioTvRepair() {
/* 114 */     return this.radioTvRepair;
/*     */   }
/*     */   public void setRadioTvRepair(String radioTvRepair) {
/* 117 */     this.radioTvRepair = radioTvRepair;
/*     */   }
/*     */   public String getAutoBodyRepair() {
/* 120 */     return this.autoBodyRepair;
/*     */   }
/*     */   public void setAutoBodyRepair(String autoBodyRepair) {
/* 123 */     this.autoBodyRepair = autoBodyRepair;
/*     */   }
/*     */   public String getWelder() {
/* 126 */     return this.welder;
/*     */   }
/*     */   public void setWelder(String welder) {
/* 129 */     this.welder = welder;
/*     */   }
/*     */   public String getCarpenter() {
/* 132 */     return this.carpenter;
/*     */   }
/*     */   public void setCarpenter(String carpenter) {
/* 135 */     this.carpenter = carpenter;
/*     */   }
/*     */   public String getLandscaping() {
/* 138 */     return this.landscaping;
/*     */   }
/*     */   public void setLandscaping(String landscaping) {
/* 141 */     this.landscaping = landscaping;
/*     */   }
/*     */   public String getCustodian() {
/* 144 */     return this.custodian;
/*     */   }
/*     */   public void setCustodian(String custodian) {
/* 147 */     this.custodian = custodian;
/*     */   }
/*     */   public String getWarehousing() {
/* 150 */     return this.warehousing;
/*     */   }
/*     */   public void setWarehousing(String warehousing) {
/* 153 */     this.warehousing = warehousing;
/*     */   }
/*     */   public String getSales() {
/* 156 */     return this.sales;
/*     */   }
/*     */   public void setSales(String sales) {
/* 159 */     this.sales = sales;
/*     */   }
/*     */   public String getApplianceRepair() {
/* 162 */     return this.applianceRepair;
/*     */   }
/*     */   public void setApplianceRepair(String applianceRepair) {
/* 165 */     this.applianceRepair = applianceRepair;
/*     */   }
/*     */   public String getTruckDriver() {
/* 168 */     return this.truckDriver;
/*     */   }
/*     */   public void setTruckDriver(String truckDriver) {
/* 171 */     this.truckDriver = truckDriver;
/*     */   }
/*     */   public String getForkLiftOperator() {
/* 174 */     return this.forkLiftOperator;
/*     */   }
/*     */   public void setForkLiftOperator(String forkLiftOperator) {
/* 177 */     this.forkLiftOperator = forkLiftOperator;
/*     */   }
/*     */   public String getComputerRepair() {
/* 180 */     return this.computerRepair;
/*     */   }
/*     */   public void setComputerRepair(String computerRepair) {
/* 183 */     this.computerRepair = computerRepair;
/*     */   }
/*     */   public String getPlumber() {
/* 186 */     return this.plumber;
/*     */   }
/*     */   public void setPlumber(String plumber) {
/* 189 */     this.plumber = plumber;
/*     */   }
/*     */   public String getKitchen() {
/* 192 */     return this.kitchen;
/*     */   }
/*     */   public void setKitchen(String kitchen) {
/* 195 */     this.kitchen = kitchen;
/*     */   }
/*     */   public String getPhoneRoom() {
/* 198 */     return this.phoneRoom;
/*     */   }
/*     */   public void setPhoneRoom(String phoneRoom) {
/* 201 */     this.phoneRoom = phoneRoom;
/*     */   }
/*     */   public String getClothesSorter() {
/* 204 */     return this.clothesSorter;
/*     */   }
/*     */   public void setClothesSorter(String clothesSorter) {
/* 207 */     this.clothesSorter = clothesSorter;
/*     */   }
/*     */   public String getComputerProgramming() {
/* 210 */     return this.computerProgramming;
/*     */   }
/*     */   public void setComputerProgramming(String computerProgramming) {
/* 213 */     this.computerProgramming = computerProgramming;
/*     */   }
/*     */   public String getOtherSkill() {
/* 216 */     return this.otherSkill;
/*     */   }
/*     */   public void setOtherSkill(String otherSkill) {
/* 219 */     this.otherSkill = otherSkill;
/*     */   }
/*     */   public String getEmployer1() {
/* 222 */     return this.employer1;
/*     */   }
/*     */   public void setEmployer1(String employer1) {
/* 225 */     this.employer1 = employer1;
/*     */   }
/*     */   public String getJobTitle1() {
/* 228 */     return this.jobTitle1;
/*     */   }
/*     */   public void setJobTitle1(String jobTitle1) {
/* 231 */     this.jobTitle1 = jobTitle1;
/*     */   }
/*     */   public String getBeginDate1() {
/* 234 */     return this.beginDate1;
/*     */   }
/*     */   public void setBeginDate1(String beginDate1) {
/* 237 */     this.beginDate1 = beginDate1;
/*     */   }
/*     */   public String getEndDate1() {
/* 240 */     return this.endDate1;
/*     */   }
/*     */   public void setEndDate1(String endDate1) {
/* 243 */     this.endDate1 = endDate1;
/*     */   }
/*     */   public String getEmployer2() {
/* 246 */     return this.employer2;
/*     */   }
/*     */   public void setEmployer2(String employer2) {
/* 249 */     this.employer2 = employer2;
/*     */   }
/*     */   public String getJobTitle2() {
/* 252 */     return this.jobTitle2;
/*     */   }
/*     */   public void setJobTitle2(String jobTitle2) {
/* 255 */     this.jobTitle2 = jobTitle2;
/*     */   }
/*     */   public String getBeginDate2() {
/* 258 */     return this.beginDate2;
/*     */   }
/*     */   public void setBeginDate2(String beginDate2) {
/* 261 */     this.beginDate2 = beginDate2;
/*     */   }
/*     */   public String getEndDate2() {
/* 264 */     return this.endDate2;
/*     */   }
/*     */   public void setEndDate2(String endDate2) {
/* 267 */     this.endDate2 = endDate2;
/*     */   }
/*     */   public String getEmployer3() {
/* 270 */     return this.employer3;
/*     */   }
/*     */   public void setEmployer3(String employer3) {
/* 273 */     this.employer3 = employer3;
/*     */   }
/*     */   public String getJobTitle3() {
/* 276 */     return this.jobTitle3;
/*     */   }
/*     */   public void setJobTitle3(String jobTitle3) {
/* 279 */     this.jobTitle3 = jobTitle3;
/*     */   }
/*     */   public String getBeginDate3() {
/* 282 */     return this.beginDate3;
/*     */   }
/*     */   public void setBeginDate3(String beginDate3) {
/* 285 */     this.beginDate3 = beginDate3;
/*     */   }
/*     */   public String getEndDate3() {
/* 288 */     return this.endDate3;
/*     */   }
/*     */   public void setEndDate3(String endDate3) {
/* 291 */     this.endDate3 = endDate3;
/*     */   }
/*     */   public String getEmployer4() {
/* 294 */     return this.employer4;
/*     */   }
/*     */   public void setEmployer4(String employer4) {
/* 297 */     this.employer4 = employer4;
/*     */   }
/*     */   public String getJobTitle4() {
/* 300 */     return this.jobTitle4;
/*     */   }
/*     */   public void setJobTitle4(String jobTitle4) {
/* 303 */     this.jobTitle4 = jobTitle4;
/*     */   }
/*     */   public String getBeginDate4() {
/* 306 */     return this.beginDate4;
/*     */   }
/*     */   public void setBeginDate4(String beginDate4) {
/* 309 */     this.beginDate4 = beginDate4;
/*     */   }
/*     */   public String getEndDate4() {
/* 312 */     return this.endDate4;
/*     */   }
/*     */   public void setEndDate4(String endDate4) {
/* 315 */     this.endDate4 = endDate4;
/*     */   }
/*     */   public String getContactName1() {
/* 318 */     return this.contactName1;
/*     */   }
/*     */   public void setContactName1(String contactName1) {
/* 321 */     this.contactName1 = contactName1;
/*     */   }
/*     */   public String getContactNumber1() {
/* 324 */     return this.contactNumber1;
/*     */   }
/*     */   public void setContactNumber1(String contactNumber1) {
/* 327 */     this.contactNumber1 = contactNumber1;
/*     */   }
/*     */   public String getContactName2() {
/* 330 */     return this.contactName2;
/*     */   }
/*     */   public void setContactName2(String contactName2) {
/* 333 */     this.contactName2 = contactName2;
/*     */   }
/*     */   public String getContactNumber2() {
/* 336 */     return this.contactNumber2;
/*     */   }
/*     */   public void setContactNumber2(String contactNumber2) {
/* 339 */     this.contactNumber2 = contactNumber2;
/*     */   }
/*     */   public String getContactName3() {
/* 342 */     return this.contactName3;
/*     */   }
/*     */   public void setContactName3(String contactName3) {
/* 345 */     this.contactName3 = contactName3;
/*     */   }
/*     */   public String getContactNumber3() {
/* 348 */     return this.contactNumber3;
/*     */   }
/*     */   public void setContactNumber3(String contactNumber3) {
/* 351 */     this.contactNumber3 = contactNumber3;
/*     */   }
/*     */   public String getContactName4() {
/* 354 */     return this.contactName4;
/*     */   }
/*     */   public void setContactName4(String contactName4) {
/* 357 */     this.contactName4 = contactName4;
/*     */   }
/*     */   public String getContactNumber4() {
/* 360 */     return this.contactNumber4;
/*     */   }
/*     */   public void setContactNumber4(String contactNumber4) {
/* 363 */     this.contactNumber4 = contactNumber4;
/*     */   }
/*     */ }

/* Location:           C:\development\workspace\faithfarmoss\WEB-INF\classes\
 * Qualified Name:     org.faithfarm.dataobjects.JobSkill
 * JD-Core Version:    0.6.2
 */