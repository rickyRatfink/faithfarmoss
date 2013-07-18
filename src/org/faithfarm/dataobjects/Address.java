/*     */ package org.faithfarm.dataobjects;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class Address
/*     */   implements Serializable
/*     */ {
/*     */   private Long addressId;
/*   8 */   private String addressLine1 = "";
/*   9 */   private String addressLine2 = "";
/*  10 */   private String city = "";
/*  11 */   private String state = "";
/*  12 */   private String zipcode = "";
/*  13 */   private String homePhone = "";
/*  14 */   private String workPhone = "";
/*  15 */   private String cellPhone = "";
/*  16 */   private String email = "";
/*  17 */   private String building = "";
/*  18 */   private String subdivision = "";
/*  19 */   private String apartmentNumber = "";
/*  20 */   private String majorIntersection = "";
/*  21 */   private String addressType = "";
/*  22 */   private String elevatorAvailability = "";
/*  23 */   private String floorLevel = "";
/*  24 */   private String typeOfStructure = "";
/*  25 */   private String buildingNumber = "";
/*  26 */   private String gatedCommunity = "";
/*  27 */   private String donationDeliveryLocation = "";
/*  28 */   private String donationDeliveryDate = "";
/*  29 */   private String callRequirements = "";
/*  30 */   private String gatedInstruction = "";
/*     */ 
/*     */   public Long getAddressId()
/*     */   {
/*  36 */     return this.addressId;
/*     */   }
/*     */   public void setAddressId(Long addressId) {
/*  39 */     this.addressId = addressId;
/*     */   }
/*     */   public String getAddressLine1() {
/*  42 */     return this.addressLine1;
/*     */   }
/*     */   public void setAddressLine1(String addressLine1) {
/*  45 */     this.addressLine1 = addressLine1;
/*     */   }
/*     */   public String getAddressLine2() {
/*  48 */     return this.addressLine2;
/*     */   }
/*     */   public void setAddressLine2(String addressLine2) {
/*  51 */     this.addressLine2 = addressLine2;
/*     */   }
/*     */   public String getCity() {
/*  54 */     return this.city;
/*     */   }
/*     */   public void setCity(String city) {
/*  57 */     this.city = city;
/*     */   }
/*     */   public String getState() {
/*  60 */     return this.state;
/*     */   }
/*     */   public void setState(String state) {
/*  63 */     this.state = state;
/*     */   }
/*     */   public String getZipcode() {
/*  66 */     return this.zipcode;
/*     */   }
/*     */   public void setZipcode(String zipcode) {
/*  69 */     this.zipcode = zipcode;
/*     */   }
/*     */   public String getHomePhone() {
/*  72 */     return this.homePhone;
/*     */   }
/*     */   public void setHomePhone(String homePhone) {
/*  75 */     this.homePhone = homePhone;
/*     */   }
/*     */   public String getWorkPhone() {
/*  78 */     return this.workPhone;
/*     */   }
/*     */   public void setWorkPhone(String workPhone) {
/*  81 */     this.workPhone = workPhone;
/*     */   }
/*     */   public String getCellPhone() {
/*  84 */     return this.cellPhone;
/*     */   }
/*     */   public void setCellPhone(String cellPhone) {
/*  87 */     this.cellPhone = cellPhone;
/*     */   }
/*     */   public String getEmail() {
/*  90 */     return this.email;
/*     */   }
/*     */   public void setEmail(String email) {
/*  93 */     this.email = email;
/*     */   }
/*     */   public String getBuilding() {
/*  96 */     return this.building;
/*     */   }
/*     */   public void setBuilding(String building) {
/*  99 */     this.building = building;
/*     */   }
/*     */   public String getSubdivision() {
/* 102 */     return this.subdivision;
/*     */   }
/*     */   public void setSubdivision(String subdivision) {
/* 105 */     this.subdivision = subdivision;
/*     */   }
/*     */   public String getApartmentNumber() {
/* 108 */     return this.apartmentNumber;
/*     */   }
/*     */   public void setApartmentNumber(String apartmentNumber) {
/* 111 */     this.apartmentNumber = apartmentNumber;
/*     */   }
/*     */   public String getMajorIntersection() {
/* 114 */     return this.majorIntersection;
/*     */   }
/*     */   public void setMajorIntersection(String majorIntersection) {
/* 117 */     this.majorIntersection = majorIntersection;
/*     */   }
/*     */   public String getAddressType() {
/* 120 */     return this.addressType;
/*     */   }
/*     */   public void setAddressType(String addressType) {
/* 123 */     this.addressType = addressType;
/*     */   }
/*     */   public String getElevatorAvailability() {
/* 126 */     return this.elevatorAvailability;
/*     */   }
/*     */   public void setElevatorAvailability(String elevatorAvailability) {
/* 129 */     this.elevatorAvailability = elevatorAvailability;
/*     */   }
/*     */   public String getFloorLevel() {
/* 132 */     return this.floorLevel;
/*     */   }
/*     */   public void setFloorLevel(String floorLevel) {
/* 135 */     this.floorLevel = floorLevel;
/*     */   }
/*     */   public String getTypeOfStructure() {
/* 138 */     return this.typeOfStructure;
/*     */   }
/*     */   public void setTypeOfStructure(String typeOfStructure) {
/* 141 */     this.typeOfStructure = typeOfStructure;
/*     */   }
/*     */   public String getBuildingNumber() {
/* 144 */     return this.buildingNumber;
/*     */   }
/*     */   public void setBuildingNumber(String buildingNumber) {
/* 147 */     this.buildingNumber = buildingNumber;
/*     */   }
/*     */   public String getGatedCommunity() {
/* 150 */     return this.gatedCommunity;
/*     */   }
/*     */   public void setGatedCommunity(String gatedCommunity) {
/* 153 */     this.gatedCommunity = gatedCommunity;
/*     */   }
/*     */   public String getDonationDeliveryLocation() {
/* 156 */     return this.donationDeliveryLocation;
/*     */   }
/*     */   public void setDonationDeliveryLocation(String donationDeliveryLocation) {
/* 159 */     this.donationDeliveryLocation = donationDeliveryLocation;
/*     */   }
/*     */   public String getDonationDeliveryDate() {
/* 162 */     return this.donationDeliveryDate;
/*     */   }
/*     */   public void setDonationDeliveryDate(String donationDeliveryDate) {
/* 165 */     this.donationDeliveryDate = donationDeliveryDate;
/*     */   }
/*     */   public String getCallRequirements() {
/* 168 */     return this.callRequirements;
/*     */   }
/*     */   public void setCallRequirements(String callRequirements) {
/* 171 */     this.callRequirements = callRequirements;
/*     */   }
/*     */   public String getGatedInstruction() {
/* 174 */     return this.gatedInstruction;
/*     */   }
/*     */   public void setGatedInstruction(String gatedInstruction) {
/* 177 */     this.gatedInstruction = gatedInstruction;
/*     */   }
/*     */ }

/* Location:           C:\development\workspace\faithfarmoss\WEB-INF\classes\
 * Qualified Name:     org.faithfarm.dataobjects.Address
 * JD-Core Version:    0.6.2
 */