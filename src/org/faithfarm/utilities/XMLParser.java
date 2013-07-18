/*     */ package org.faithfarm.utilities;
/*     */ 
/*     */ import com.google.appengine.api.datastore.Entity;
/*     */ import com.google.appengine.api.datastore.Key;
/*     */ import java.io.File;
/*     */ import java.io.PrintStream;
/*     */ import java.io.Serializable;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.SAXParseException;
/*     */ 
/*     */ public class XMLParser
/*     */   implements Serializable
/*     */ {
/*  20 */   private static final HTMLBuilder html = new HTMLBuilder();
/*     */ 
/*     */   public NodeList getEntityProperties(String entityType, HttpServletRequest req)
/*     */   {
/*  24 */     Entity entity = new Entity(entityType);
/*  25 */     NodeList propertyList = null;
/*     */     try
/*     */     {
/*  29 */       DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
/*  30 */       DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
/*  31 */       Document doc = docBuilder.parse(new File(entityType + ".xml"));
/*     */ 
/*  34 */       doc.getDocumentElement().normalize();
/*  35 */       doc.getDocumentElement().getNodeName();
/*     */ 
/*  37 */       NodeList listOfEntities = doc.getElementsByTagName(entityType);
/*  38 */       int totalEntities = listOfEntities.getLength();
/*  39 */       for (int s = 0; s < listOfEntities.getLength(); s++) {
/*  40 */         Node entityNode = listOfEntities.item(s);
/*  41 */         if (entityNode.getNodeType() == 1) {
/*  42 */           Element firstElement = (Element)entityNode;
/*  43 */           propertyList = firstElement.getElementsByTagName("property");
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (SAXParseException err)
/*     */     {
/*  49 */       System.out.println("** Parsing error, line " + 
/*  50 */         err.getLineNumber() + ", uri " + err.getSystemId());
/*  51 */       System.out.println(" " + err.getMessage());
/*     */     }
/*     */     catch (SAXException e) {
/*  54 */       Exception x = e.getException();
/*  55 */       (x == null ? e : x).printStackTrace();
/*     */     }
/*     */     catch (Throwable t) {
/*  58 */       t.printStackTrace();
/*     */     }
/*     */ 
/*  61 */     return propertyList;
/*     */   }
/*     */ 
/*     */   public Entity xmlToEntity(String entityType, HttpServletRequest req)
/*     */   {
/*  66 */     Entity entity = new Entity(entityType);
/*  67 */     NodeList propertyList = null;
/*     */     try
/*     */     {
/*  71 */       DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
/*  72 */       DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
/*  73 */       Document doc = docBuilder.parse(new File(entityType + ".xml"));
/*     */ 
/*  76 */       doc.getDocumentElement().normalize();
/*     */ 
/*  78 */       NodeList listOfEntities = doc.getElementsByTagName(entityType);
/*  79 */       int totalEntities = listOfEntities.getLength();
/*     */ 
/*  81 */       for (int s = 0; s < listOfEntities.getLength(); s++)
/*     */       {
/*  83 */         Node entityNode = listOfEntities.item(s);
/*  84 */         if (entityNode.getNodeType() == 1)
/*     */         {
/*  87 */           Element firstElement = (Element)entityNode;
/*     */ 
/*  90 */           propertyList = firstElement.getElementsByTagName("property");
/*  91 */           for (int i = 0; i < propertyList.getLength(); i++) {
/*  92 */             Element propertyElement = (Element)propertyList.item(i);
/*  93 */             String propertyName = propertyElement.getAttribute("name").toString();
/*  94 */             String propertyType = propertyElement.getAttribute("type").toString();
/*     */ 
/*  97 */             entity.setProperty(propertyName, html.cleanData(req.getParameter(propertyName)));
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SAXParseException err)
/*     */     {
/* 109 */       System.out.println("** Parsing error, line " + 
/* 110 */         err.getLineNumber() + ", uri " + err.getSystemId());
/* 111 */       System.out.println(" " + err.getMessage());
/*     */     }
/*     */     catch (SAXException e) {
/* 114 */       Exception x = e.getException();
/* 115 */       (x == null ? e : x).printStackTrace();
/*     */     }
/*     */     catch (Throwable t) {
/* 118 */       t.printStackTrace();
/*     */     }
/*     */ 
/* 123 */     return entity;
/*     */   }
/*     */ 
/*     */   public Entity xmlToChildEntity(String entityType, Entity parentEntity, HttpServletRequest req)
/*     */   {
/* 128 */     Entity entity = new Entity(entityType, parentEntity.getKey().getId());
/* 129 */     NodeList propertyList = null;
/*     */     try
/*     */     {
/* 133 */       DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
/* 134 */       DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
/* 135 */       Document doc = docBuilder.parse(new File(entityType + ".xml"));
/*     */ 
/* 138 */       doc.getDocumentElement().normalize();
/*     */ 
/* 141 */       NodeList listOfEntities = doc.getElementsByTagName(entityType);
/* 142 */       int totalEntities = listOfEntities.getLength();
/*     */ 
/* 144 */       for (int s = 0; s < listOfEntities.getLength(); s++)
/*     */       {
/* 147 */         Node entityNode = listOfEntities.item(s);
/* 148 */         if (entityNode.getNodeType() == 1)
/*     */         {
/* 151 */           Element firstElement = (Element)entityNode;
/*     */ 
/* 154 */           propertyList = firstElement.getElementsByTagName("property");
/* 155 */           for (int i = 0; i < propertyList.getLength(); i++) {
/* 156 */             Element propertyElement = (Element)propertyList.item(i);
/* 157 */             String propertyName = propertyElement.getAttribute("name").toString();
/* 158 */             String propertyType = propertyElement.getAttribute("type").toString();
/*     */ 
/* 160 */             entity.setProperty(propertyName, html.cleanData(req.getParameter(propertyName)));
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SAXParseException err)
/*     */     {
/* 171 */       System.out.println("** Parsing error, line " + 
/* 172 */         err.getLineNumber() + ", uri " + err.getSystemId());
/* 173 */       System.out.println(" " + err.getMessage());
/*     */     }
/*     */     catch (SAXException e) {
/* 176 */       Exception x = e.getException();
/* 177 */       (x == null ? e : x).printStackTrace();
/*     */     }
/*     */     catch (Throwable t) {
/* 180 */       t.printStackTrace();
/*     */     }
/*     */ 
/* 185 */     return entity;
/*     */   }
/*     */ 
/*     */   public Entity xmlChildToParentEntity(String entityType, Entity childEntity, Entity parentEntity)
/*     */   {
/* 190 */     Entity entity = new Entity(entityType, parentEntity.getKey());
/* 191 */     NodeList propertyList = null;
/*     */     try
/*     */     {
/* 194 */       DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
/* 195 */       DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
/* 196 */       Document doc = docBuilder.parse(new File(entityType + ".xml"));
/*     */ 
/* 199 */       doc.getDocumentElement().normalize();
/*     */ 
/* 202 */       NodeList listOfEntities = doc.getElementsByTagName(entityType);
/* 203 */       int totalEntities = listOfEntities.getLength();
/*     */ 
/* 205 */       for (int s = 0; s < listOfEntities.getLength(); s++)
/*     */       {
/* 208 */         Node entityNode = listOfEntities.item(s);
/* 209 */         if (entityNode.getNodeType() == 1)
/*     */         {
/* 212 */           Element firstElement = (Element)entityNode;
/*     */ 
/* 215 */           propertyList = firstElement.getElementsByTagName("property");
/* 216 */           for (int i = 0; i < propertyList.getLength(); i++) {
/* 217 */             Element propertyElement = (Element)propertyList.item(i);
/* 218 */             String propertyName = propertyElement.getAttribute("name").toString();
/* 219 */             String propertyType = propertyElement.getAttribute("type").toString();
/*     */ 
/* 221 */             entity.setProperty(propertyName, childEntity.getProperty(propertyName));
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SAXParseException err)
/*     */     {
/* 232 */       System.out.println("** Parsing error, line " + 
/* 233 */         err.getLineNumber() + ", uri " + err.getSystemId());
/* 234 */       System.out.println(" " + err.getMessage());
/*     */     }
/*     */     catch (SAXException e) {
/* 237 */       Exception x = e.getException();
/* 238 */       (x == null ? e : x).printStackTrace();
/*     */     }
/*     */     catch (Throwable t) {
/* 241 */       t.printStackTrace();
/*     */     }
/*     */ 
/* 245 */     return entity;
/*     */   }
/*     */ 
/*     */   public Entity updateEntity(Entity entity, HttpServletRequest req)
/*     */   {
/* 250 */     NodeList propertyList = null;
/*     */     try
/*     */     {
/* 254 */       DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
/* 255 */       DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
/* 256 */       Document doc = docBuilder.parse(new File(entity.getKind() + ".xml"));
/*     */ 
/* 259 */       doc.getDocumentElement().normalize();
/*     */ 
/* 262 */       NodeList listOfEntities = doc.getElementsByTagName(entity.getKind());
/* 263 */       int totalEntities = listOfEntities.getLength();
/*     */ 
/* 265 */       for (int s = 0; s < listOfEntities.getLength(); s++)
/*     */       {
/* 268 */         Node entityNode = listOfEntities.item(s);
/* 269 */         if (entityNode.getNodeType() == 1)
/*     */         {
/* 272 */           Element firstElement = (Element)entityNode;
/*     */ 
/* 275 */           propertyList = firstElement.getElementsByTagName("property");
/* 276 */           for (int i = 0; i < propertyList.getLength(); i++) {
/* 277 */             Element propertyElement = (Element)propertyList.item(i);
/* 278 */             String propertyName = propertyElement.getAttribute("name");
/* 279 */             String editable = propertyElement.getAttribute("editable");
/* 280 */             String value = req.getParameter(propertyName);
/* 281 */             if (value == null)
/* 282 */               value = "";
/* 283 */             if ((!value.equals(entity.getProperty(propertyName))) && ("Y".equals(editable))) {
/* 284 */               entity.setProperty(propertyName, value);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SAXParseException err)
/*     */     {
/* 293 */       System.out.println("** Parsing error, line " + 
/* 294 */         err.getLineNumber() + ", uri " + err.getSystemId());
/* 295 */       System.out.println(" " + err.getMessage());
/*     */     }
/*     */     catch (SAXException e) {
/* 298 */       Exception x = e.getException();
/* 299 */       (x == null ? e : x).printStackTrace();
/*     */     }
/*     */     catch (Throwable t) {
/* 302 */       t.printStackTrace();
/*     */     }
/*     */ 
/* 305 */     return entity;
/*     */   }
/*     */ }

/* Location:           C:\development\workspace\faithfarmoss\WEB-INF\classes\
 * Qualified Name:     org.faithfarm.utilities.XMLParser
 * JD-Core Version:    0.6.2
 */