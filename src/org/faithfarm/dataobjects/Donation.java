/*     */ package org.faithfarm.dataobjects;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class Donation
/*     */   implements Serializable
/*     */ {
/*     */   private Long donationId;
/*     */   private Long personId;
/*     */   private Long addressId;
/*  10 */   private String airConditioner = "";
/*  11 */   private String lawnFurniture = "";
/*  12 */   private String bedding = "";
/*  13 */   private String beddingMeasurement = "";
/*  14 */   private String mattressBoxspring = "";
/*  15 */   private String mattressBoxspringMeasurement = "";
/*  16 */   private String books = "";
/*  17 */   private String booksMeasurement = "";
/*  18 */   private String wallUnit = "";
/*  19 */   private String chair = "";
/*  20 */   private String nightstand = "";
/*  21 */   private String chest = "";
/*  22 */   private String headboardFootboard = "";
/*  23 */   private String bagsboxes = "";
/*  24 */   private String clothing = "";
/*  25 */   private String clothingMeasurement = "";
/*  26 */   private String refrigerator = "";
/*  27 */   private String computer = "";
/*  28 */   private String entertainment = "";
/*  29 */   private String center = "";
/*  30 */   private String desk = "";
/*  31 */   private String sofaLoveseat = "";
/*  32 */   private String dishwasher = "";
/*  33 */   private String recliners = "";
/*  34 */   private String armoire = "";
/*  35 */   private String stove = "";
/*  36 */   private String dresser = "";
/*  37 */   private String television = "";
/*  38 */   private String televisionMeasurement = "";
/*  39 */   private String tableChair = "";
/*  40 */   private String dryer = "";
/*  41 */   private String exercise = "";
/*  42 */   private String equipment = "";
/*  43 */   private String vcr = "";
/*  44 */   private String lamp = "";
/*  45 */   private String washer = "";
/*  46 */   private String miscHouseholdItems = "";
/*  47 */   private String otherItems = "";
/*     */ 
/*  49 */   private Integer airConditionerQty = new Integer("0");
/*  50 */   private Integer lawnFurnitureQty = new Integer("0");
/*  51 */   private Integer beddingQty = new Integer("0");
/*  52 */   private Integer mattressBoxSpringQty = new Integer("0");
/*  53 */   private Integer booksQty = new Integer("0");
/*  54 */   private Integer wallUnitQty = new Integer("0");
/*  55 */   private Integer chairQty = new Integer("0");
/*  56 */   private Integer nightstandQty = new Integer("0");
/*  57 */   private Integer chestQty = new Integer("0");
/*  58 */   private Integer headboardFootboardQty = new Integer("0");
/*  59 */   private Integer bagsboxesQty = new Integer("0");
/*  60 */   private Integer clothingQty = new Integer("0");
/*  61 */   private Integer refrigeratorQty = new Integer("0");
/*  62 */   private Integer computerQty = new Integer("0");
/*  63 */   private Integer entertainmentQty = new Integer("0");
/*  64 */   private Integer centerQty = new Integer("0");
/*  65 */   private Integer deskQty = new Integer("0");
/*  66 */   private Integer sofaLoveseatQty = new Integer("0");
/*  67 */   private Integer dishwasherQty = new Integer("0");
/*  68 */   private Integer reclinersQty = new Integer("0");
/*  69 */   private Integer armoireQty = new Integer("0");
/*  70 */   private Integer stoveQty = new Integer("0");
/*  71 */   private Integer dresserQty = new Integer("0");
/*  72 */   private Integer tableChairQty = new Integer("0");
/*  73 */   private Integer dryerQty = new Integer("0");
/*  74 */   private Integer televisionQty = new Integer("0");
/*  75 */   private Integer exerciseQty = new Integer("0");
/*  76 */   private Integer equipmentQty = new Integer("0");
/*  77 */   private Integer vcrQty = new Integer("0");
/*  78 */   private Integer lampQty = new Integer("0");
/*  79 */   private Integer washerQty = new Integer("0");
/*  80 */   private Integer miscHouseholdItemsQty = new Integer("0");
/*  81 */   private Integer otherItemsQty = new Integer("0");
/*     */ 
/*     */   public String getAirConditioner()
/*     */   {
/*  85 */     return this.airConditioner;
/*     */   }
/*     */   public Long getDonationId() {
/*  88 */     return this.donationId;
/*     */   }
/*     */   public void setDonationId(Long donationId) {
/*  91 */     this.donationId = donationId;
/*     */   }
/*     */   public Long getPersonId() {
/*  94 */     return this.personId;
/*     */   }
/*     */   public void setPersonId(Long personId) {
/*  97 */     this.personId = personId;
/*     */   }
/*     */   public Long getAddressId() {
/* 100 */     return this.addressId;
/*     */   }
/*     */   public void setAddressId(Long addressId) {
/* 103 */     this.addressId = addressId;
/*     */   }
/*     */   public void setAirConditioner(String airConditioner) {
/* 106 */     this.airConditioner = airConditioner;
/*     */   }
/*     */   public String getLawnFurniture() {
/* 109 */     return this.lawnFurniture;
/*     */   }
/*     */   public void setLawnFurniture(String lawnFurniture) {
/* 112 */     this.lawnFurniture = lawnFurniture;
/*     */   }
/*     */   public String getBedding() {
/* 115 */     return this.bedding;
/*     */   }
/*     */   public void setBedding(String bedding) {
/* 118 */     this.bedding = bedding;
/*     */   }
/*     */   public String getBeddingMeasurement() {
/* 121 */     return this.beddingMeasurement;
/*     */   }
/*     */   public void setBeddingMeasurement(String beddingMeasurement) {
/* 124 */     this.beddingMeasurement = beddingMeasurement;
/*     */   }
/*     */   public String getMattressBoxspring() {
/* 127 */     return this.mattressBoxspring;
/*     */   }
/*     */   public void setMattressBoxspring(String mattressBoxspring) {
/* 130 */     this.mattressBoxspring = mattressBoxspring;
/*     */   }
/*     */   public String getMattressBoxspringMeasurement() {
/* 133 */     return this.mattressBoxspringMeasurement;
/*     */   }
/*     */   public void setMattressBoxspringMeasurement(String mattressBoxspringMeasurement) {
/* 136 */     this.mattressBoxspringMeasurement = mattressBoxspringMeasurement;
/*     */   }
/*     */   public String getBooks() {
/* 139 */     return this.books;
/*     */   }
/*     */   public void setBooks(String books) {
/* 142 */     this.books = books;
/*     */   }
/*     */   public String getBooksMeasurement() {
/* 145 */     return this.booksMeasurement;
/*     */   }
/*     */   public void setBooksMeasurement(String booksMeasurement) {
/* 148 */     this.booksMeasurement = booksMeasurement;
/*     */   }
/*     */   public String getWallUnit() {
/* 151 */     return this.wallUnit;
/*     */   }
/*     */   public void setWallUnit(String wallUnit) {
/* 154 */     this.wallUnit = wallUnit;
/*     */   }
/*     */   public String getChair() {
/* 157 */     return this.chair;
/*     */   }
/*     */   public void setChair(String chair) {
/* 160 */     this.chair = chair;
/*     */   }
/*     */   public String getNightstand() {
/* 163 */     return this.nightstand;
/*     */   }
/*     */   public void setNightstand(String nightstand) {
/* 166 */     this.nightstand = nightstand;
/*     */   }
/*     */   public String getChest() {
/* 169 */     return this.chest;
/*     */   }
/*     */   public void setChest(String chest) {
/* 172 */     this.chest = chest;
/*     */   }
/*     */   public String getHeadboardFootboard() {
/* 175 */     return this.headboardFootboard;
/*     */   }
/*     */   public void setHeadboardFootboard(String headboardFootboard) {
/* 178 */     this.headboardFootboard = headboardFootboard;
/*     */   }
/*     */   public String getBagsboxes() {
/* 181 */     return this.bagsboxes;
/*     */   }
/*     */   public void setBagsboxes(String bagsboxes) {
/* 184 */     this.bagsboxes = bagsboxes;
/*     */   }
/*     */   public String getClothing() {
/* 187 */     return this.clothing;
/*     */   }
/*     */   public void setClothing(String clothing) {
/* 190 */     this.clothing = clothing;
/*     */   }
/*     */   public String getClothingMeasurement() {
/* 193 */     return this.clothingMeasurement;
/*     */   }
/*     */   public void setClothingMeasurement(String clothingMeasurement) {
/* 196 */     this.clothingMeasurement = clothingMeasurement;
/*     */   }
/*     */   public String getRefrigerator() {
/* 199 */     return this.refrigerator;
/*     */   }
/*     */   public void setRefrigerator(String refrigerator) {
/* 202 */     this.refrigerator = refrigerator;
/*     */   }
/*     */   public String getComputer() {
/* 205 */     return this.computer;
/*     */   }
/*     */   public void setComputer(String computer) {
/* 208 */     this.computer = computer;
/*     */   }
/*     */   public String getEntertainment() {
/* 211 */     return this.entertainment;
/*     */   }
/*     */   public void setEntertainment(String entertainment) {
/* 214 */     this.entertainment = entertainment;
/*     */   }
/*     */   public String getCenter() {
/* 217 */     return this.center;
/*     */   }
/*     */   public void setCenter(String center) {
/* 220 */     this.center = center;
/*     */   }
/*     */   public String getDesk() {
/* 223 */     return this.desk;
/*     */   }
/*     */   public void setDesk(String desk) {
/* 226 */     this.desk = desk;
/*     */   }
/*     */   public String getSofaLoveseat() {
/* 229 */     return this.sofaLoveseat;
/*     */   }
/*     */   public void setSofaLoveseat(String sofaLoveseat) {
/* 232 */     this.sofaLoveseat = sofaLoveseat;
/*     */   }
/*     */   public String getDishwasher() {
/* 235 */     return this.dishwasher;
/*     */   }
/*     */   public void setDishwasher(String dishwasher) {
/* 238 */     this.dishwasher = dishwasher;
/*     */   }
/*     */   public String getRecliners() {
/* 241 */     return this.recliners;
/*     */   }
/*     */   public void setRecliners(String recliners) {
/* 244 */     this.recliners = recliners;
/*     */   }
/*     */   public String getArmoire() {
/* 247 */     return this.armoire;
/*     */   }
/*     */   public void setArmoire(String armoire) {
/* 250 */     this.armoire = armoire;
/*     */   }
/*     */   public String getStove() {
/* 253 */     return this.stove;
/*     */   }
/*     */   public void setStove(String stove) {
/* 256 */     this.stove = stove;
/*     */   }
/*     */   public String getDresser() {
/* 259 */     return this.dresser;
/*     */   }
/*     */   public void setDresser(String dresser) {
/* 262 */     this.dresser = dresser;
/*     */   }
/*     */   public String getTelevision() {
/* 265 */     return this.television;
/*     */   }
/*     */   public void setTelevision(String television) {
/* 268 */     this.television = television;
/*     */   }
/*     */   public String getTelevisionMeasurement() {
/* 271 */     return this.televisionMeasurement;
/*     */   }
/*     */   public void setTelevisionMeasurement(String televisionMeasurement) {
/* 274 */     this.televisionMeasurement = televisionMeasurement;
/*     */   }
/*     */   public String getTableChair() {
/* 277 */     return this.tableChair;
/*     */   }
/*     */   public void setTableChair(String tableChair) {
/* 280 */     this.tableChair = tableChair;
/*     */   }
/*     */   public String getDryer() {
/* 283 */     return this.dryer;
/*     */   }
/*     */   public void setDryer(String dryer) {
/* 286 */     this.dryer = dryer;
/*     */   }
/*     */   public String getExercise() {
/* 289 */     return this.exercise;
/*     */   }
/*     */   public void setExercise(String exercise) {
/* 292 */     this.exercise = exercise;
/*     */   }
/*     */   public String getEquipment() {
/* 295 */     return this.equipment;
/*     */   }
/*     */   public void setEquipment(String equipment) {
/* 298 */     this.equipment = equipment;
/*     */   }
/*     */   public String getVcr() {
/* 301 */     return this.vcr;
/*     */   }
/*     */   public void setVcr(String vcr) {
/* 304 */     this.vcr = vcr;
/*     */   }
/*     */   public String getLamp() {
/* 307 */     return this.lamp;
/*     */   }
/*     */   public void setLamp(String lamp) {
/* 310 */     this.lamp = lamp;
/*     */   }
/*     */   public String getWasher() {
/* 313 */     return this.washer;
/*     */   }
/*     */   public void setWasher(String washer) {
/* 316 */     this.washer = washer;
/*     */   }
/*     */   public String getMiscHouseholdItems() {
/* 319 */     return this.miscHouseholdItems;
/*     */   }
/*     */   public void setMiscHouseholdItems(String miscHouseholdItems) {
/* 322 */     this.miscHouseholdItems = miscHouseholdItems;
/*     */   }
/*     */   public String getOtherItems() {
/* 325 */     return this.otherItems;
/*     */   }
/*     */   public void setOtherItems(String otherItems) {
/* 328 */     this.otherItems = otherItems;
/*     */   }
/*     */   public Integer getAirConditionerQty() {
/* 331 */     return this.airConditionerQty;
/*     */   }
/*     */   public void setAirConditionerQty(Integer airConditionerQty) {
/* 334 */     this.airConditionerQty = airConditionerQty;
/*     */   }
/*     */   public Integer getLawnFurnitureQty() {
/* 337 */     return this.lawnFurnitureQty;
/*     */   }
/*     */   public void setLawnFurnitureQty(Integer lawnFurnitureQty) {
/* 340 */     this.lawnFurnitureQty = lawnFurnitureQty;
/*     */   }
/*     */   public Integer getBeddingQty() {
/* 343 */     return this.beddingQty;
/*     */   }
/*     */   public void setBeddingQty(Integer beddingQty) {
/* 346 */     this.beddingQty = beddingQty;
/*     */   }
/*     */   public Integer getMattressBoxSpringQty() {
/* 349 */     return this.mattressBoxSpringQty;
/*     */   }
/*     */   public void setMattressBoxSpringQty(Integer mattressBoxSpringQty) {
/* 352 */     this.mattressBoxSpringQty = mattressBoxSpringQty;
/*     */   }
/*     */   public Integer getBooksQty() {
/* 355 */     return this.booksQty;
/*     */   }
/*     */   public void setBooksQty(Integer booksQty) {
/* 358 */     this.booksQty = booksQty;
/*     */   }
/*     */   public Integer getWallUnitQty() {
/* 361 */     return this.wallUnitQty;
/*     */   }
/*     */   public void setWallUnitQty(Integer wallUnitQty) {
/* 364 */     this.wallUnitQty = wallUnitQty;
/*     */   }
/*     */   public Integer getChairQty() {
/* 367 */     return this.chairQty;
/*     */   }
/*     */   public void setChairQty(Integer chairQty) {
/* 370 */     this.chairQty = chairQty;
/*     */   }
/*     */   public Integer getNightstandQty() {
/* 373 */     return this.nightstandQty;
/*     */   }
/*     */   public void setNightstandQty(Integer nightstandQty) {
/* 376 */     this.nightstandQty = nightstandQty;
/*     */   }
/*     */   public Integer getChestQty() {
/* 379 */     return this.chestQty;
/*     */   }
/*     */   public void setChestQty(Integer chestQty) {
/* 382 */     this.chestQty = chestQty;
/*     */   }
/*     */   public Integer getHeadboardFootboardQty() {
/* 385 */     return this.headboardFootboardQty;
/*     */   }
/*     */   public void setHeadboardFootboardQty(Integer headboardFootboardQty) {
/* 388 */     this.headboardFootboardQty = headboardFootboardQty;
/*     */   }
/*     */   public Integer getBagsboxesQty() {
/* 391 */     return this.bagsboxesQty;
/*     */   }
/*     */   public void setBagsboxesQty(Integer bagsboxesQty) {
/* 394 */     this.bagsboxesQty = bagsboxesQty;
/*     */   }
/*     */   public Integer getClothingQty() {
/* 397 */     return this.clothingQty;
/*     */   }
/*     */   public void setClothingQty(Integer clothingQty) {
/* 400 */     this.clothingQty = clothingQty;
/*     */   }
/*     */   public Integer getRefrigeratorQty() {
/* 403 */     return this.refrigeratorQty;
/*     */   }
/*     */   public void setRefrigeratorQty(Integer refrigeratorQty) {
/* 406 */     this.refrigeratorQty = refrigeratorQty;
/*     */   }
/*     */   public Integer getComputerQty() {
/* 409 */     return this.computerQty;
/*     */   }
/*     */   public void setComputerQty(Integer computerQty) {
/* 412 */     this.computerQty = computerQty;
/*     */   }
/*     */   public Integer getEntertainmentQty() {
/* 415 */     return this.entertainmentQty;
/*     */   }
/*     */   public void setEntertainmentQty(Integer entertainmentQty) {
/* 418 */     this.entertainmentQty = entertainmentQty;
/*     */   }
/*     */   public Integer getCenterQty() {
/* 421 */     return this.centerQty;
/*     */   }
/*     */   public void setCenterQty(Integer centerQty) {
/* 424 */     this.centerQty = centerQty;
/*     */   }
/*     */   public Integer getDeskQty() {
/* 427 */     return this.deskQty;
/*     */   }
/*     */   public void setDeskQty(Integer deskQty) {
/* 430 */     this.deskQty = deskQty;
/*     */   }
/*     */   public Integer getSofaLoveseatQty() {
/* 433 */     return this.sofaLoveseatQty;
/*     */   }
/*     */   public void setSofaLoveseatQty(Integer sofaLoveseatQty) {
/* 436 */     this.sofaLoveseatQty = sofaLoveseatQty;
/*     */   }
/*     */   public Integer getDishwasherQty() {
/* 439 */     return this.dishwasherQty;
/*     */   }
/*     */   public void setDishwasherQty(Integer dishwasherQty) {
/* 442 */     this.dishwasherQty = dishwasherQty;
/*     */   }
/*     */   public Integer getReclinersQty() {
/* 445 */     return this.reclinersQty;
/*     */   }
/*     */   public void setReclinersQty(Integer reclinersQty) {
/* 448 */     this.reclinersQty = reclinersQty;
/*     */   }
/*     */   public Integer getArmoireQty() {
/* 451 */     return this.armoireQty;
/*     */   }
/*     */   public void setArmoireQty(Integer armoireQty) {
/* 454 */     this.armoireQty = armoireQty;
/*     */   }
/*     */   public Integer getStoveQty() {
/* 457 */     return this.stoveQty;
/*     */   }
/*     */   public void setStoveQty(Integer stoveQty) {
/* 460 */     this.stoveQty = stoveQty;
/*     */   }
/*     */   public Integer getDresserQty() {
/* 463 */     return this.dresserQty;
/*     */   }
/*     */   public void setDresserQty(Integer dresserQty) {
/* 466 */     this.dresserQty = dresserQty;
/*     */   }
/*     */   public Integer getTableChairQty() {
/* 469 */     return this.tableChairQty;
/*     */   }
/*     */   public void setTableChairQty(Integer tableChairQty) {
/* 472 */     this.tableChairQty = tableChairQty;
/*     */   }
/*     */   public Integer getDryerQty() {
/* 475 */     return this.dryerQty;
/*     */   }
/*     */   public void setDryerQty(Integer dryerQty) {
/* 478 */     this.dryerQty = dryerQty;
/*     */   }
/*     */   public Integer getTelevisionQty() {
/* 481 */     return this.televisionQty;
/*     */   }
/*     */   public void setTelevisionQty(Integer televisionQty) {
/* 484 */     this.televisionQty = televisionQty;
/*     */   }
/*     */   public Integer getExerciseQty() {
/* 487 */     return this.exerciseQty;
/*     */   }
/*     */   public void setExerciseQty(Integer exerciseQty) {
/* 490 */     this.exerciseQty = exerciseQty;
/*     */   }
/*     */   public Integer getEquipmentQty() {
/* 493 */     return this.equipmentQty;
/*     */   }
/*     */   public void setEquipmentQty(Integer equipmentQty) {
/* 496 */     this.equipmentQty = equipmentQty;
/*     */   }
/*     */   public Integer getVcrQty() {
/* 499 */     return this.vcrQty;
/*     */   }
/*     */   public void setVcrQty(Integer vcrQty) {
/* 502 */     this.vcrQty = vcrQty;
/*     */   }
/*     */   public Integer getLampQty() {
/* 505 */     return this.lampQty;
/*     */   }
/*     */   public void setLampQty(Integer lampQty) {
/* 508 */     this.lampQty = lampQty;
/*     */   }
/*     */   public Integer getWasherQty() {
/* 511 */     return this.washerQty;
/*     */   }
/*     */   public void setWasherQty(Integer washerQty) {
/* 514 */     this.washerQty = washerQty;
/*     */   }
/*     */   public Integer getMiscHouseholdItemsQty() {
/* 517 */     return this.miscHouseholdItemsQty;
/*     */   }
/*     */   public void setMiscHouseholdItemsQty(Integer miscHouseholdItemsQty) {
/* 520 */     this.miscHouseholdItemsQty = miscHouseholdItemsQty;
/*     */   }
/*     */   public Integer getOtherItemsQty() {
/* 523 */     return this.otherItemsQty;
/*     */   }
/*     */   public void setOtherItemsQty(Integer otherItemsQty) {
/* 526 */     this.otherItemsQty = otherItemsQty;
/*     */   }
/*     */ }

/* Location:           C:\development\workspace\faithfarmoss\WEB-INF\classes\
 * Qualified Name:     org.faithfarm.dataobjects.Donation
 * JD-Core Version:    0.6.2
 */