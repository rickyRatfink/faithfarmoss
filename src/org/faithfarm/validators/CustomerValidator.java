/*    */ package org.faithfarm.validators;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.logging.Logger;
/*    */ 
/*    */ public class CustomerValidator
/*    */ {
/* 12 */   private static final Logger logger = Logger.getLogger(CustomerValidator.class.getCanonicalName());
/* 13 */   private Validator validator = new Validator();
/*    */ 
/*    */   public ArrayList validateSave(String firstName, String lastName, String address1, String city, String state, String zip, String email, String cell, String work, String home)
/*    */   {
/* 20 */     ArrayList messages = new ArrayList();
/*    */ 
/* 22 */     this.validator.validateRequired("First Name", firstName, messages);
/* 23 */     this.validator.validateRequired("Last Name", lastName, messages);
/* 24 */     this.validator.validateRequired("Address Line 1", address1, messages);
/* 25 */     this.validator.validateRequired("City", city, messages);
/* 26 */     this.validator.validateRequired("State", state, messages);
/* 27 */     this.validator.validateRequired("Zipcode", zip, messages);
/* 28 */     this.validator.validateRequired("Email Address", email, messages);
/*    */ 
/* 30 */     return messages;
/*    */   }
/*    */ }

/* Location:           C:\development\workspace\faithfarmoss\WEB-INF\classes\
 * Qualified Name:     org.faithfarm.validators.CustomerValidator
 * JD-Core Version:    0.6.2
 */