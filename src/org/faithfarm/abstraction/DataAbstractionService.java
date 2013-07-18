/*    */ package org.faithfarm.abstraction;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.PrintStream;
/*    */ import javax.xml.parsers.DocumentBuilder;
/*    */ import javax.xml.parsers.DocumentBuilderFactory;
/*    */ import org.w3c.dom.Document;
/*    */ import org.w3c.dom.Element;
/*    */ import org.w3c.dom.NodeList;
/*    */ import org.xml.sax.SAXException;
/*    */ import org.xml.sax.SAXParseException;
/*    */ 
/*    */ public class DataAbstractionService
/*    */ {
/*    */   private static final String configFile = "data-config.xml";
/*    */   private static final String rootNode = "DataConfig";
/*    */   private static final String optionNode = "option";
/* 19 */   private static String dataLayer = null;
/* 20 */   private static String mysqlHost = null;
/* 21 */   private static String mysqlUser = null;
/* 22 */   private static String mysqlPass = null;
/* 23 */   private static String mysqlDb = null;
/*    */ 
/*    */   public static DataAbstraction getInstance() throws IllegalArgumentException {
/* 26 */     DataAbstraction instance = null;
/* 27 */     if (dataLayer == null) {
/*    */       try {
/* 29 */         DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
/* 30 */         Document doc = docBuilder.parse(new File("data-config.xml"));
/* 31 */         NodeList list = doc.getElementsByTagName("DataConfig");
/*    */ 
/* 33 */         Element rootElement = (Element)list.item(0);
/* 34 */         NodeList properties = rootElement.getElementsByTagName("option");
/* 35 */         for (int i = 0; i < properties.getLength(); i++) {
/* 36 */           Element thisElement = (Element)properties.item(i);
/* 37 */           if (thisElement.getAttribute("name").equals("data-layer")) {
/* 38 */             dataLayer = thisElement.getAttribute("value");
/*    */           }
/* 40 */           if (thisElement.getAttribute("name").equals("mysql-host")) {
/* 41 */             mysqlHost = thisElement.getAttribute("value");
/*    */           }
/* 43 */           if (thisElement.getAttribute("name").equals("mysql-user")) {
/* 44 */             mysqlUser = thisElement.getAttribute("value");
/*    */           }
/* 46 */           if (thisElement.getAttribute("name").equals("mysql-pass")) {
/* 47 */             mysqlPass = thisElement.getAttribute("value");
/*    */           }
/* 49 */           if (thisElement.getAttribute("name").equals("mysql-db"))
/* 50 */             mysqlDb = thisElement.getAttribute("value");
/*    */         }
/*    */       }
/*    */       catch (SAXParseException localSAXParseException)
/*    */       {
/*    */       }
/*    */       catch (SAXException e) {
/* 57 */         e.printStackTrace();
/*    */       } catch (Throwable t) {
/* 59 */         t.printStackTrace();
/*    */       }
/*    */     }
/* 62 */     if (dataLayer.equals("HRD")) {
/* 63 */       System.out.println("return hrddataabstraction");
/* 64 */       return new HRDDataAbstraction();
/* 65 */     }if ((dataLayer.equals("mysql")) || (dataLayer.equals("MySQL")) || (dataLayer.equals("MYSQL"))) {
/* 66 */       System.out.println("return mysqldataabstraction");
/*    */     }
/*    */     else {
/* 69 */       throw new IllegalArgumentException("data-layer option in data-config.xmlcontained invalid value: " + dataLayer);
/*    */     }
/* 71 */     return instance;
/*    */   }
/*    */ }

/* Location:           C:\development\workspace\faithfarmoss\WEB-INF\classes\
 * Qualified Name:     org.faithfarm.abstraction.DataAbstractionService
 * JD-Core Version:    0.6.2
 */