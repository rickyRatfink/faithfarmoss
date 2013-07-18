/*    */ package org.faithfarm.validators;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.logging.Logger;
/*    */ 
/*    */ public class InventoryValidator
/*    */ {
/* 12 */   private static final Logger logger = Logger.getLogger(InventoryValidator.class.getCanonicalName());
/*    */ 
/* 15 */   Validator validator = new Validator();
/*    */ 
/*    */   public ArrayList validateSave(String vendorName, String location, String itemNumber, String itemName, String qty1, String qty2, String desc, String cost, String price, String minLevel, String status, String transactionType)
/*    */   {
/* 23 */     ArrayList messages = new ArrayList();
/*    */ 
/* 25 */     messages = this.validator.validateRequired("Vendor", vendorName, messages);
/* 26 */     messages = this.validator.validateRequired("Location", location, messages);
/* 27 */     messages = this.validator.validateRequired("Item Number", itemNumber, messages);
/* 28 */     messages = this.validator.validateRequired("Price", price, messages);
/* 29 */     messages = this.validator.validateRequired("Item Name", itemName, messages);
/*    */ 
/* 31 */     messages = this.validator.validateRequired("Warehouse Quantity", qty1, messages);
/* 32 */     messages = this.validator.validateRequired("Floor Quantity", qty2, messages);
/* 33 */     messages = this.validator.validateRequired("Cost", cost, messages);
/* 34 */     messages = this.validator.validateRequired("Transaction Type", transactionType, messages);
/*    */ 
/* 36 */     return messages;
/*    */   }
/*    */ }

/* Location:           C:\development\workspace\faithfarmoss\WEB-INF\classes\
 * Qualified Name:     org.faithfarm.validators.InventoryValidator
 * JD-Core Version:    0.6.2
 */