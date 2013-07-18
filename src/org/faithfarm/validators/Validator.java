/*    */ package org.faithfarm.validators;
/*    */ 
/*    */ import com.google.appengine.api.datastore.Entity;
/*    */ import java.io.Serializable;
/*    */ import java.util.ArrayList;
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import org.faithfarm.utilities.HTMLBuilder;
/*    */ import org.w3c.dom.Element;
/*    */ import org.w3c.dom.NodeList;
/*    */ 
/*    */ public class Validator
/*    */   implements Serializable
/*    */ {
/*    */   public boolean validateString(String value)
/*    */   {
/* 18 */     boolean success = false;
/*    */ 
/* 22 */     return success;
/*    */   }
/*    */ 
/*    */   public ArrayList validateRequired(String key, String value, ArrayList messages)
/*    */   {
/* 27 */     if ((value == null) || (value.length() == 0) || (value.trim().length() == 0)) {
/* 28 */       messages.add(key + " is required.");
/*    */     }
/* 30 */     return messages;
/*    */   }
/*    */ 
/*    */   public ArrayList validateEmail(String email, ArrayList messages)
/*    */   {
/* 35 */     if (("NA".equals(email)) || ("na".equals(email))) {
/* 36 */       return messages;
/*    */     }
/* 38 */     if ((email == null) || (email.length() == 0) || (email.trim().length() == 0)) {
/* 39 */       messages.add("email address is required.");
/* 40 */       return messages;
/*    */     }
/* 42 */     Pattern p = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
/* 43 */     Matcher m = p.matcher(email);
/* 44 */     boolean b = m.matches();
/*    */ 
/* 46 */     if (!b) {
/* 47 */       messages.add("email address entered is not valid.");
/*    */     }
/* 49 */     return messages;
/*    */   }
/*    */ 
/*    */   public boolean validateForm(NodeList xmlForm, HttpServletRequest req) {
/* 53 */     Validator validator = new Validator();
/* 54 */     ArrayList messages = new ArrayList();
/*    */ 
/* 56 */     for (int i = 0; i < xmlForm.getLength(); i++) {
/* 57 */       Element propertyElement = (Element)xmlForm.item(i);
/* 58 */       String name = propertyElement.getAttribute("name").toString();
/* 59 */       String display = propertyElement.getAttribute("display").toString();
/* 60 */       String value = req.getParameter(name);
/* 61 */       String required = propertyElement.getAttribute("required").toString();
/*    */ 
/* 63 */       if ("true".equals(required)) {
/* 64 */         messages = validator.validateRequired(display, value, messages);
/*    */       }
/*    */     }
/* 67 */     if (messages.size() > 0) {
/* 68 */       req.setAttribute("messages", messages);
/* 69 */       return false;
/*    */     }
/*    */ 
/* 72 */     return true;
/*    */   }
/*    */ 
/*    */   private Entity parseTelephone(Entity entity, HttpServletRequest req)
/*    */   {
/* 78 */     HTMLBuilder html = new HTMLBuilder();
/*    */ 
/* 80 */     String homePhone1 = html.cleanData(req.getParameter("homePhone_areacode"));
/* 81 */     String homePhone2 = html.cleanData(req.getParameter("homePhone_first3"));
/* 82 */     String homePhone3 = html.cleanData(req.getParameter("homePhone_last4"));
/* 83 */     String homePhone = "(" + homePhone1 + ")" + homePhone2 + "-" + homePhone3;
/* 84 */     entity.setProperty("homePhone", homePhone);
/*    */ 
/* 86 */     String cellPhone1 = html.cleanData(req.getParameter("cellPhone_areacode"));
/* 87 */     String cellPhone2 = html.cleanData(req.getParameter("cellPhone_first3"));
/* 88 */     String cellPhone3 = html.cleanData(req.getParameter("cellPhone_last4"));
/* 89 */     String cellPhone = "(" + cellPhone1 + ")" + cellPhone2 + "-" + cellPhone3;
/* 90 */     entity.setProperty("cellPhone", cellPhone);
/*    */ 
/* 92 */     String workPhone1 = html.cleanData(req.getParameter("workPhone_areacode"));
/* 93 */     String workPhone2 = html.cleanData(req.getParameter("workPhone_first3"));
/* 94 */     String workPhone3 = html.cleanData(req.getParameter("workPhone_last4"));
/* 95 */     String workPhone = "(" + workPhone1 + ")" + workPhone2 + "-" + workPhone3;
/* 96 */     entity.setProperty("workPhone", workPhone);
/*    */ 
/* 98 */     return entity;
/*    */   }
/*    */ }

/* Location:           C:\development\workspace\faithfarmoss\WEB-INF\classes\
 * Qualified Name:     org.faithfarm.validators.Validator
 * JD-Core Version:    0.6.2
 */