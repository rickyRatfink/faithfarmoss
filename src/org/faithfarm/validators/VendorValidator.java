/*    */ package org.faithfarm.validators;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.logging.Logger;
/*    */ 
/*    */ public class VendorValidator
/*    */ {
/* 12 */   private static final Logger logger = Logger.getLogger(VendorValidator.class.getCanonicalName());
/* 13 */   private Validator validator = new Validator();
/*    */ 
/*    */   public ArrayList validateSave(String vendorName, String vendorNumber, String address1, String city, String state, String zip, String email, String phone, String rep, String orgType)
/*    */   {
/* 19 */     ArrayList messages = new ArrayList();
/*    */ 
/* 21 */     this.validator.validateRequired("Organization Number", vendorNumber, messages);
/* 22 */     this.validator.validateRequired("Organization Type", orgType, messages);
/* 23 */     this.validator.validateRequired("Organization Name", vendorName, messages);
/* 24 */     this.validator.validateRequired("Address Line 1", address1, messages);
/* 25 */     this.validator.validateRequired("City", city, messages);
/* 26 */     this.validator.validateRequired("State", state, messages);
/* 27 */     this.validator.validateRequired("Zipcode", zip, messages);
/* 28 */     this.validator.validateRequired("Email Address", email, messages);
/* 29 */     this.validator.validateRequired("Phone Number", phone, messages);
/* 30 */     this.validator.validateRequired("Vendor Representative", rep, messages);
/*    */ 
/* 32 */     return messages;
/*    */   }
/*    */ }

/* Location:           C:\development\workspace\faithfarmoss\WEB-INF\classes\
 * Qualified Name:     org.faithfarm.validators.VendorValidator
 * JD-Core Version:    0.6.2
 */