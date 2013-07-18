/*     */ package org.faithfarm.dataobjects;
/*     */ 
/*     */ /*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
import java.util.Iterator;
/*     */ import java.util.List;

/*     */ import org.apache.log4j.Logger;

import com.google.appengine.api.datastore.DatastoreService;
/*     */ import com.google.appengine.api.datastore.DatastoreServiceFactory;
/*     */ import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
/*     */ import com.google.appengine.api.datastore.KeyFactory;
/*     */ import com.google.appengine.api.datastore.Query;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PurchaseOrder
/*     */ {
/*  22 */   private static final DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
/*  23 */   private static final Logger logger = Logger.getLogger(PurchaseOrder.class.getCanonicalName());
/*     */   private Long id;
/*     */   private Long po_number;
/*     */   private Date created;
/*     */   private String farm;
/*     */   private String key_string;
/*     */   private String shipto_address;
/*     */   private String shipto_city;
/*     */   private String shipto_name;
/*     */   private String shipto_phone;
/*     */   private String shipto_state;
/*     */   private String shipto_zip;
/*     */   private String vendor_address;
/*     */   private String vendor_city;
/*     */   private String vendor_company;
/*     */   private String vendor_name;
/*     */   private String vendor_phone;
/*     */   private String vendor_state;
/*     */   private String vendor_zip;
/*     */ 
/*     */   public static List<PurchaseOrder> searchPO(String po_number, String vendor_company, String vendor_name, String shipto_name)
/*     */   {
/*  45 */     List results = new ArrayList();
/*  46 */     Query query = new Query("PurchaseOrder");
/*  47 */     query.addSort("created", Query.SortDirection.DESCENDING);
/*  48 */     List entities = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(20));
/*     */ 
/*  50 */     //for (Entity tEntity : entities) {
			  for(Iterator i = entities.iterator();i.hasNext();) {
				  Entity tEntity = (Entity)i.next();
/*  51 */         results.add(getInstanceFromEntity(tEntity));
/*     */     }
/*  53 */     return results;
/*     */   }
/*     */ 
/*     */   public static PurchaseOrder getInstanceFromEntity(Entity e) {
/*  57 */     if (e == null) {
/*  58 */       return null;
/*     */     }
/*  60 */     PurchaseOrder po = new PurchaseOrder();
/*  61 */     po.setFarm((String)e.getProperty("farm"));
/*  62 */     po.setCreated((Long)e.getProperty("created"));
/*  63 */     po.setKeyString(KeyFactory.keyToString(e.getKey()));
/*  64 */     po.setShiptoAddress((String)e.getProperty("shipto_address"));
/*  65 */     po.setShiptoCity((String)e.getProperty("shipto_city"));
/*  66 */     po.setShiptoName((String)e.getProperty("shipto_name"));
/*  67 */     po.setShiptoPhone((String)e.getProperty("shipto_phone"));
/*  68 */     po.setShiptoState((String)e.getProperty("shipto_state"));
/*  69 */     po.setShiptoZip((String)e.getProperty("shipto_zip"));
/*  70 */     po.setVendorAddress((String)e.getProperty("vendor_address"));
/*  71 */     po.setVendorCity((String)e.getProperty("vendor_city"));
/*  72 */     po.setVendorCompany((String)e.getProperty("vendor_company"));
/*  73 */     po.setVendorName((String)e.getProperty("vendor_name"));
/*  74 */     po.setVendorPhone((String)e.getProperty("vendor_phone"));
/*  75 */     po.setVendorState((String)e.getProperty("vendor_state"));
/*  76 */     po.setVendorZip((String)e.getProperty("vendor_zip"));
/*  77 */     po.setId(Long.valueOf(e.getKey().getId()));
/*  78 */     po.setPoNumber(e.getProperty("po_number"));
/*     */ 
/*  80 */     return po;
/*     */   }
/*     */ 
/*     */   public String getFarmShortCode()
/*     */   {
/*  88 */     if (this.farm.equals("Fort Lauderdale"))
/*  89 */       return "FLF";
/*  90 */     return "???";
/*     */   }
/*     */ 
/*     */   public void setFarm(String theFarm) {
/*  94 */     this.farm = (theFarm == null ? "" : theFarm);
/*     */   }
/*     */ 
/*     */   public String getFarm() {
/*  98 */     return this.farm;
/*     */   }
/*     */ 
/*     */   public void setCreated(Long ms) {
/* 102 */     this.created = (ms == null ? new Date(0L) : new Date(ms.longValue()));
/*     */   }
/*     */ 
/*     */   public Date getCreated()
/*     */   {
/* 107 */     return this.created;
/*     */   }
/*     */ 
/*     */   public String getDateShort() {
/* 111 */     String dateStr = "";
/*     */     try {
/* 113 */       SimpleDateFormat toFormat = new SimpleDateFormat("MM/dd/yyyy");
/* 114 */       dateStr = toFormat.format(this.created);
/*     */     }
/*     */     catch (IllegalArgumentException localIllegalArgumentException) {
/*     */     }
/* 118 */     return dateStr;
/*     */   }
/*     */ 
/*     */   public void setKeyString(String keyStr) {
/* 122 */     this.key_string = (keyStr == null ? "" : keyStr);
/*     */   }
/*     */ 
/*     */   public String getKeyString() {
/* 126 */     return this.key_string;
/*     */   }
/*     */ 
/*     */   public void setVendorZip(String vendorZip) {
/* 130 */     this.vendor_zip = (vendorZip == null ? "" : vendorZip);
/*     */   }
/*     */ 
/*     */   public String getVendorZip() {
/* 134 */     return this.vendor_zip;
/*     */   }
/*     */ 
/*     */   public void setVendorState(String vendorState) {
/* 138 */     this.vendor_state = (vendorState == null ? "" : vendorState);
/*     */   }
/*     */ 
/*     */   public String getVendorState() {
/* 142 */     return this.vendor_state;
/*     */   }
/*     */ 
/*     */   public void setVendorPhone(String vendorPhone) {
/* 146 */     this.vendor_phone = (vendorPhone == null ? "" : vendorPhone);
/*     */   }
/*     */ 
/*     */   public String getVendorPhone() {
/* 150 */     return this.vendor_phone;
/*     */   }
/*     */ 
/*     */   public void setVendorName(String vendorName) {
/* 154 */     this.vendor_name = (vendorName == null ? "" : vendorName);
/*     */   }
/*     */ 
/*     */   public String getVendorName() {
/* 158 */     return this.vendor_name;
/*     */   }
/*     */ 
/*     */   public void setVendorCompany(String vendorCompany) {
/* 162 */     this.vendor_company = (vendorCompany == null ? "" : vendorCompany);
/*     */   }
/*     */ 
/*     */   public String getVendorCompany() {
/* 166 */     return this.vendor_company;
/*     */   }
/*     */ 
/*     */   public void setVendorCity(String vendorCity) {
/* 170 */     this.vendor_city = (vendorCity == null ? "" : vendorCity);
/*     */   }
/*     */ 
/*     */   public String getVendorCity() {
/* 174 */     return this.vendor_city;
/*     */   }
/*     */ 
/*     */   public void setVendorAddress(String vendorAddress) {
/* 178 */     this.vendor_address = (vendorAddress == null ? "" : vendorAddress);
/*     */   }
/*     */ 
/*     */   public String getVendorAddress() {
/* 182 */     return this.vendor_address;
/*     */   }
/*     */ 
/*     */   public void setShiptoState(String shipToState) {
/* 186 */     this.shipto_state = (shipToState == null ? "" : shipToState);
/*     */   }
/*     */ 
/*     */   public String getShiptoState() {
/* 190 */     return this.shipto_state;
/*     */   }
/*     */ 
/*     */   public void setShiptoZip(String shipToZip) {
/* 194 */     this.shipto_zip = (shipToZip == null ? "" : shipToZip);
/*     */   }
/*     */ 
/*     */   public String getShiptoZip() {
/* 198 */     return this.shipto_zip;
/*     */   }
/*     */ 
/*     */   public void setShiptoPhone(String shipToPhone) {
/* 202 */     this.shipto_phone = (shipToPhone == null ? "" : shipToPhone);
/*     */   }
/*     */ 
/*     */   public String getShiptoPhone() {
/* 206 */     return this.shipto_phone;
/*     */   }
/*     */ 
/*     */   public void setShiptoName(String shipToName) {
/* 210 */     this.shipto_name = (shipToName == null ? "" : shipToName);
/*     */   }
/*     */ 
/*     */   public String getShiptoName() {
/* 214 */     return this.shipto_name;
/*     */   }
/*     */ 
/*     */   public void setShiptoCity(String shipToCity) {
/* 218 */     this.shipto_city = (shipToCity == null ? "" : shipToCity);
/*     */   }
/*     */ 
/*     */   public String getShiptoCity() {
/* 222 */     return this.shipto_city;
/*     */   }
/*     */ 
/*     */   public void setShiptoAddress(String shipToAddress) {
/* 226 */     this.shipto_address = (shipToAddress == null ? "" : shipToAddress);
/*     */   }
/*     */ 
/*     */   public String getShiptoAddress() {
/* 230 */     return this.shipto_address;
/*     */   }
/*     */ 
/*     */   public void setPoNumber(Object poNumber) {
/* 234 */     if ((poNumber instanceof Long))
/* 235 */       this.po_number = ((Long)poNumber);
/* 236 */     else if ((poNumber instanceof String))
/* 237 */       this.po_number = Long.valueOf((String)poNumber);
/*     */   }
/*     */ 
/*     */   public void setPoNumber(Long poNumber)
/*     */   {
/* 242 */     this.po_number = poNumber;
/*     */   }
/*     */ 
/*     */   public void setPoNumber(String poNumber) {
/* 246 */     this.po_number = Long.valueOf(poNumber == null ? -1L : Long.valueOf(poNumber).longValue());
/*     */   }
/*     */ 
/*     */   public Long getPoNumber() {
/* 250 */     return this.po_number;
/*     */   }
/*     */ 
/*     */   public void setId(Long theId) {
/* 254 */     this.id = theId;
/*     */   }
/*     */ 
/*     */   public Long getId() {
/* 258 */     return this.id;
/*     */   }
/*     */ }

/* Location:           C:\development\workspace\faithfarmoss\WEB-INF\classes\
 * Qualified Name:     org.faithfarm.dataobjects.PurchaseOrder
 * JD-Core Version:    0.6.2
 */