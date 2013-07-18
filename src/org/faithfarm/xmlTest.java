/*    */ package org.faithfarm;
/*    */ 
/*    */ import com.google.appengine.api.datastore.Entity;
/*    */ import java.io.PrintStream;
/*    */ import javax.xml.parsers.DocumentBuilder;
/*    */ import javax.xml.parsers.DocumentBuilderFactory;
/*    */ import org.w3c.dom.Document;
/*    */ import org.w3c.dom.Element;
/*    */ import org.w3c.dom.Node;
/*    */ import org.w3c.dom.NodeList;
/*    */ import org.xml.sax.SAXException;
/*    */ import org.xml.sax.SAXParseException;
/*    */ 
/*    */ public class xmlTest
/*    */ {
/*    */   public void main(String[] args)
/*    */   {
/* 20 */     String entityType = "Person";
/* 21 */     Entity entity = new Entity(entityType);
/*    */     try
/*    */     {
/* 25 */       DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
/* 26 */       DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
/* 27 */       Document doc = docBuilder.parse("text.xml");
/*    */ 
/* 30 */       doc.getDocumentElement().normalize();
/* 31 */       System.out.println("Root element of the doc is " + 
/* 32 */         doc.getDocumentElement().getNodeName());
/*    */ 
/* 35 */       NodeList listOfPersons = doc.getElementsByTagName(entityType);
/* 36 */       int totalPersons = listOfPersons.getLength();
/* 37 */       System.out.println("Total no of entities : " + totalPersons);
/*    */ 
/* 39 */       for (int s = 0; s < listOfPersons.getLength(); s++)
/*    */       {
/* 42 */         Node firstPersonNode = listOfPersons.item(s);
/* 43 */         if (firstPersonNode.getNodeType() == 1)
/*    */         {
/* 46 */           Element firstElement = (Element)firstPersonNode;
/*    */ 
/* 49 */           NodeList firstNameList = firstElement.getElementsByTagName("property");
/* 50 */           for (int i = 0; i < firstNameList.getLength(); i++) {
/* 51 */             Element firstNameElement = (Element)firstNameList.item(i);
/* 52 */             System.out.println(">" + firstNameElement.getAttribute("name"));
/* 53 */             System.out.println(">" + firstNameElement.getAttribute("type"));
/* 54 */             String propertyName = firstNameElement.getAttribute("name").toString();
/* 55 */             String str1 = firstNameElement.getAttribute("type").toString();
/*    */           }
/*    */ 
/* 61 */           firstNameList = firstElement.getElementsByTagName("address");
/* 62 */           for (int i = 0; i < firstNameList.getLength(); i++) {
/* 63 */             Element firstNameElement = (Element)firstNameList.item(i);
/* 64 */             System.out.println(">" + firstNameElement.getAttribute("property"));
/* 65 */             System.out.println(">" + firstNameElement.getAttribute("type"));
/*    */           }
/*    */ 
/*    */         }
/*    */ 
/*    */       }
/*    */ 
/*    */     }
/*    */     catch (SAXParseException err)
/*    */     {
/* 78 */       System.out.println("** Parsing error, line " + 
/* 79 */         err.getLineNumber() + ", uri " + err.getSystemId());
/* 80 */       System.out.println(" " + err.getMessage());
/*    */     }
/*    */     catch (SAXException e) {
/* 83 */       Exception x = e.getException();
/* 84 */       (x == null ? e : x).printStackTrace();
/*    */     }
/*    */     catch (Throwable t) {
/* 87 */       t.printStackTrace();
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\development\workspace\faithfarmoss\WEB-INF\classes\
 * Qualified Name:     org.faithfarm.xmlTest
 * JD-Core Version:    0.6.2
 */