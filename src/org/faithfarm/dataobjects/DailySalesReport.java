/*     */ package org.faithfarm.dataobjects;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class DailySalesReport
/*     */   implements Serializable
/*     */ {
/*     */   String vendor;
/*     */   String itemNumber;
/*     */   String itemName;
/*     */   int warehouseQuantity;
/*     */   int floorQuantity;
/*     */   int totalQuantity;
/*     */   int quantitySold;
/*     */   int par;
/*     */   int qty1W;
/*     */   int qty1F;
/*     */   int qty1RO;
/*     */   String location;
/*     */   String price;
/*     */   double salesPrice;
/*     */ 
/*     */   public int getQty1W()
/*     */   {
/*  23 */     return this.qty1W;
/*     */   }
/*     */   public void setQty1W(int qty1w) {
/*  26 */     this.qty1W = qty1w;
/*     */   }
/*     */   public int getQty1F() {
/*  29 */     return this.qty1F;
/*     */   }
/*     */   public void setQty1F(int qty1f) {
/*  32 */     this.qty1F = qty1f;
/*     */   }
/*     */   public int getQty1RO() {
/*  35 */     return this.qty1RO;
/*     */   }
/*     */   public void setQty1RO(int qty1ro) {
/*  38 */     this.qty1RO = qty1ro;
/*     */   }
/*     */ 
/*     */   public int getPar()
/*     */   {
/*  43 */     return this.par;
/*     */   }
/*     */   public void setPar(int par) {
/*  46 */     this.par = par;
/*     */   }
/*     */   public String getVendor() {
/*  49 */     return this.vendor;
/*     */   }
/*     */   public void setVendor(String vendor) {
/*  52 */     this.vendor = vendor;
/*     */   }
/*     */   public String getItemNumber() {
/*  55 */     return this.itemNumber;
/*     */   }
/*     */   public void setItemNumber(String itemNumber) {
/*  58 */     this.itemNumber = itemNumber;
/*     */   }
/*     */   public String getItemName() {
/*  61 */     return this.itemName;
/*     */   }
/*     */   public void setItemName(String itemName) {
/*  64 */     this.itemName = itemName;
/*     */   }
/*     */   public int getWarehouseQuantity() {
/*  67 */     return this.warehouseQuantity;
/*     */   }
/*     */   public void setWarehouseQuantity(int warehouseQuantity) {
/*  70 */     this.warehouseQuantity = warehouseQuantity;
/*     */   }
/*     */   public int getFloorQuantity() {
/*  73 */     return this.floorQuantity;
/*     */   }
/*     */   public void setFloorQuantity(int floorQuantity) {
/*  76 */     this.floorQuantity = floorQuantity;
/*     */   }
/*     */   public int getTotalQuantity() {
/*  79 */     return this.totalQuantity;
/*     */   }
/*     */   public void setTotalQuantity(int totalQuantity) {
/*  82 */     this.totalQuantity = totalQuantity;
/*     */   }
/*     */   public int getQuantitySold() {
/*  85 */     return this.quantitySold;
/*     */   }
/*     */   public void setQuantitySold(int quantitySold) {
/*  88 */     this.quantitySold = quantitySold;
/*     */   }
/*     */   public String getPrice() {
/*  91 */     return this.price;
/*     */   }
/*     */   public void setPrice(String price) {
/*  94 */     this.price = price;
/*     */   }
/*     */   public double getSalesPrice() {
/*  97 */     return this.salesPrice;
/*     */   }
/*     */   public void setSalesPrice(double salesPrice) {
/* 100 */     this.salesPrice = salesPrice;
/*     */   }
/*     */   public String getLocation() {
/* 103 */     return this.location;
/*     */   }
/*     */   public void setLocation(String location) {
/* 106 */     this.location = location;
/*     */   }
/*     */ }

/* Location:           C:\development\workspace\faithfarmoss\WEB-INF\classes\
 * Qualified Name:     org.faithfarm.dataobjects.DailySalesReport
 * JD-Core Version:    0.6.2
 */