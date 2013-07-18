/*     */ package org.faithfarm.dataobjects;
/*     */ 
/*     */ import com.google.appengine.api.datastore.Entity;
/*     */ import java.io.PrintStream;
/*     */ import java.io.Serializable;
/*     */ import java.security.Key;
/*     */ import java.util.Date;
/*     */ import javax.crypto.BadPaddingException;
/*     */ import javax.crypto.Cipher;
/*     */ import javax.crypto.spec.SecretKeySpec;
/*     */ import org.apache.commons.codec.binary.Base64;
/*     */ 
/*     */ public class Person
/*     */   implements Serializable
/*     */ {
/*     */   private Long personId;
/*  17 */   private String farmBase = "";
/*  18 */   private String firstName = "";
/*  19 */   private String lastName = "";
/*  20 */   private String middleInitial = "";
/*  21 */   private String suffix = "";
/*  22 */   private String dateOfBirth = "";
/*  23 */   private String ssn = "";
/*  24 */   private String ssnCard = "";
/*  25 */   private String age = "";
/*  26 */   private String height = "";
/*  27 */   private String weight = "";
/*  28 */   private String ethnicity = "";
/*  29 */   private String hairColor = "";
/*  30 */   private String eyeColor = "";
/*  31 */   private String maritalStatus = "";
/*  32 */   private String educationLevel = "";
/*  33 */   private String graduateFlag = "";
/*  34 */   private String transcriptsFlag = "";
/*  35 */   private String englishSpeakFlag = "";
/*  36 */   private String englishReadFlag = "";
/*  37 */   private String homeLocation = "";
/*  38 */   private String personType = "";
/*  39 */   private String referredBy = "";
/*  40 */   private String referralPhone = "";
/*  41 */   private String emergencyContact = "";
/*  42 */   private String emergencyPhone = "";
/*  43 */   private String emergencyRelationship = "";
/*  44 */   private Benevolence benevolence = new Benevolence();
/*  45 */   private Address address = new Address();
/*  46 */   private PersonMisc personMisc = new PersonMisc();
/*  47 */   private JobSkill jobSkill = new JobSkill();
/*  48 */   private String createdBy = "";
/*  49 */   private Date creationDate = new Date();
/*  50 */   private String entryDate = "";
/*     */ 
/*     */   public Person convert(Entity entity)
/*     */   {
/*  55 */     setFarmBase((String)entity.getProperty("farmBase"));
/*  56 */     setFirstName((String)entity.getProperty("firstName"));
/*  57 */     setLastName((String)entity.getProperty("lastName"));
/*  58 */     setMiddleInitial((String)entity.getProperty("middleInitial"));
/*  59 */     setSuffix((String)entity.getProperty("suffix"));
/*  60 */     setDateOfBirth((String)entity.getProperty("dateOfBirth"));
/*  61 */     setSsn((String)entity.getProperty("ssn"));
/*  62 */     setSsnCard((String)entity.getProperty("ssnCard"));
/*  63 */     setAge((String)entity.getProperty("age"));
/*  64 */     setHeight((String)entity.getProperty("height"));
/*  65 */     setWeight((String)entity.getProperty("weight"));
/*  66 */     setEthnicity((String)entity.getProperty("ethnicity"));
/*  67 */     setHairColor((String)entity.getProperty("hairColor"));
/*  68 */     setEyeColor((String)entity.getProperty("eyeColor"));
/*  69 */     setMaritalStatus((String)entity.getProperty("maritalStatus"));
/*  70 */     setEducationLevel((String)entity.getProperty("educationLevel"));
/*  71 */     setGraduateFlag((String)entity.getProperty("graduateFlag"));
/*  72 */     setEnglishSpeakFlag((String)entity.getProperty("englishSpeakFlag"));
/*  73 */     setEnglishReadFlag((String)entity.getProperty("englishReadFlag"));
/*  74 */     setHomeLocation((String)entity.getProperty("eomeLocation"));
/*  75 */     setPersonType((String)entity.getProperty("personType"));
/*  76 */     setReferredBy((String)entity.getProperty("referredBy"));
/*  77 */     setReferralPhone((String)entity.getProperty("referralPhone"));
/*  78 */     setEmergencyContact((String)entity.getProperty("emergencyContact"));
/*  79 */     setEmergencyPhone((String)entity.getProperty("emergencyPhone"));
/*  80 */     setEmergencyRelationship((String)entity.getProperty("EmergencyRelationship"));
/*  81 */     return this;
/*     */   }
/*     */ 
/*     */   public Date getCreationDate() {
/*  85 */     return this.creationDate;
/*     */   }
/*     */   public String getTranscriptsFlag() {
/*  88 */     return this.transcriptsFlag;
/*     */   }
/*     */ 
/*     */   public void setTranscriptsFlag(String transcriptsFlag) {
/*  92 */     this.transcriptsFlag = transcriptsFlag;
/*     */   }
/*     */ 
/*     */   public void setCreationDate(Date creationDate) {
/*  96 */     this.creationDate = creationDate;
/*     */   }
/*     */   public String getCreatedBy() {
/*  99 */     return this.createdBy;
/*     */   }
/*     */   public void setCreatedBy(String createdBy) {
/* 102 */     this.createdBy = createdBy;
/*     */   }
/*     */   public String getEntryDate() {
/* 105 */     return this.entryDate;
/*     */   }
/*     */   public void setEntryDate(String entryDate) {
/* 108 */     this.entryDate = entryDate;
/*     */   }
/*     */   public JobSkill getJobSkill() {
/* 111 */     return this.jobSkill;
/*     */   }
/*     */   public void setJobSkill(JobSkill jobSkill) {
/* 114 */     this.jobSkill = jobSkill;
/*     */   }
/*     */   public Address getAddress() {
/* 117 */     return this.address;
/*     */   }
/*     */   public Benevolence getBenevolence() {
/* 120 */     return this.benevolence;
/*     */   }
/*     */   public PersonMisc getPersonMisc() {
/* 123 */     return this.personMisc;
/*     */   }
/*     */ 
/*     */   public void setPersonMisc(PersonMisc personMisc) {
/* 127 */     this.personMisc = personMisc;
/*     */   }
/*     */ 
/*     */   public void setAddress(Address address) {
/* 131 */     this.address = address;
/*     */   }
/*     */ 
/*     */   public void setBenevolence(Benevolence benevolence) {
/* 135 */     this.benevolence = benevolence;
/*     */   }
/*     */ 
/*     */   public Long getPersonId() {
/* 139 */     return this.personId;
/*     */   }
/*     */   public void setPersonId(Long personId) {
/* 142 */     this.personId = personId;
/*     */   }
/*     */   public String getFirstName() {
/* 145 */     return this.firstName;
/*     */   }
/*     */   public void setFirstName(String firstName) {
/* 148 */     this.firstName = firstName;
/*     */   }
/*     */   public String getLastName() {
/* 151 */     return this.lastName;
/*     */   }
/*     */   public void setLastName(String lastName) {
/* 154 */     this.lastName = lastName;
/*     */   }
/*     */   public String getMiddleInitial() {
/* 157 */     return this.middleInitial;
/*     */   }
/*     */   public void setMiddleInitial(String middleInitial) {
/* 160 */     this.middleInitial = middleInitial;
/*     */   }
/*     */   public String getSuffix() {
/* 163 */     return this.suffix;
/*     */   }
/*     */   public void setSuffix(String suffix) {
/* 166 */     this.suffix = suffix;
/*     */   }
/*     */   public String getDateOfBirth() {
/* 169 */     return this.dateOfBirth;
/*     */   }
/*     */   public void setDateOfBirth(String dateOfBirth) {
/* 172 */     this.dateOfBirth = dateOfBirth;
/*     */   }
/*     */   public String getAge() {
/* 175 */     return this.age;
/*     */   }
/*     */   public void setAge(String age) {
/* 178 */     this.age = age;
/*     */   }
/*     */   public String getEthnicity() {
/* 181 */     return this.ethnicity;
/*     */   }
/*     */   public void setEthnicity(String ethnicity) {
/* 184 */     this.ethnicity = ethnicity;
/*     */   }
/*     */   public String getMaritalStatus() {
/* 187 */     return this.maritalStatus;
/*     */   }
/*     */   public void setMaritalStatus(String maritalStatus) {
/* 190 */     this.maritalStatus = maritalStatus;
/*     */   }
/*     */   public String getEducationLevel() {
/* 193 */     return this.educationLevel;
/*     */   }
/*     */   public void setEducationLevel(String educationLevel) {
/* 196 */     this.educationLevel = educationLevel;
/*     */   }
/*     */   public String getGraduateFlag() {
/* 199 */     return this.graduateFlag;
/*     */   }
/*     */   public void setGraduateFlag(String graduateFlag) {
/* 202 */     this.graduateFlag = graduateFlag;
/*     */   }
/*     */   public String getEnglishSpeakFlag() {
/* 205 */     return this.englishSpeakFlag;
/*     */   }
/*     */   public void setEnglishSpeakFlag(String englishSpeakFlag) {
/* 208 */     this.englishSpeakFlag = englishSpeakFlag;
/*     */   }
/*     */   public String getEnglishReadFlag() {
/* 211 */     return this.englishReadFlag;
/*     */   }
/*     */   public void setEnglishReadFlag(String englishReadFlag) {
/* 214 */     this.englishReadFlag = englishReadFlag;
/*     */   }
/*     */   public String getHomeLocation() {
/* 217 */     return this.homeLocation;
/*     */   }
/*     */   public void setHomeLocation(String homeLocation) {
/* 220 */     this.homeLocation = homeLocation;
/*     */   }
/*     */ 
/*     */   public String getPersonType() {
/* 224 */     return this.personType;
/*     */   }
/*     */   public void setPersonType(String personType) {
/* 227 */     this.personType = personType;
/*     */   }
/*     */   public static String decryptSsn(String base64) {
/* 230 */     String clearSsn = base64;
/* 231 */     System.out.println("Decrypting: " + base64);
/*     */     try {
/* 233 */       Cipher des = Cipher.getInstance("DES");
/* 234 */       Key key = new SecretKeySpec("potatoes".getBytes(), "DES");
/* 235 */       des.init(2, key);
/* 236 */       clearSsn = new String(des.doFinal(Base64.decodeBase64(base64.getBytes())));
/*     */     } catch (BadPaddingException e) {
/* 238 */       System.out.println("BadPaddingException in decryptSsn: " + e.getMessage());
/*     */     } catch (Exception e) {
/* 240 */       System.out.println("General exception in decryptSsn: " + e.getMessage());
/*     */     }
/* 242 */     return clearSsn;
/*     */   }
/*     */ 
/*     */   public static String encryptSsn(String clear) {
/* 246 */     String base64 = "";
/*     */     try {
/* 248 */       Cipher des = Cipher.getInstance("DES");
/* 249 */       Key key = new SecretKeySpec("potatoes".getBytes(), "DES");
/* 250 */       des.init(1, key);
/* 251 */       base64 = Base64.encodeBase64String(des.doFinal(clear.getBytes()));
/*     */     } catch (Exception e) {
/* 253 */       System.out.println("Exception in encryptSsn: " + e.getMessage());
/*     */     }
/* 255 */     return base64;
/*     */   }
/*     */ 
/*     */   public String getSsnEncrypted() {
/* 259 */     return this.ssn;
/*     */   }
/*     */ 
/*     */   public String getSsn() {
/* 263 */     String clearSsn = "";
/*     */ 
/* 265 */     if (this.ssn.length() > 0) {
/* 266 */       System.out.println(this.ssn);
/*     */       try {
/* 268 */         Cipher des = Cipher.getInstance("DES");
/* 269 */         Key key = new SecretKeySpec("potatoes".getBytes(), "DES");
/* 270 */         des.init(2, key);
/* 271 */         clearSsn = new String(des.doFinal(Base64.decodeBase64(this.ssn.getBytes())));
/*     */       } catch (Exception e) {
/* 273 */         System.out.println("Exception in getSsn: " + e.getMessage());
/*     */       }
/*     */     }
/* 276 */     return clearSsn;
/*     */   }
/*     */   public void setSsn(String ssn) {
/* 279 */     String ssnBase64 = "";
/*     */     try {
/* 281 */       Cipher des = Cipher.getInstance("DES");
/* 282 */       Key key = new SecretKeySpec("potatoes".getBytes(), "DES");
/* 283 */       des.init(1, key);
/* 284 */       ssnBase64 = Base64.encodeBase64String(des.doFinal(ssn.getBytes()));
/*     */     } catch (Exception e) {
/* 286 */       System.out.println("Exception in setSsn: " + e.getMessage());
/*     */     }
/* 288 */     this.ssn = ssnBase64;
/*     */   }
/*     */   public String getReferredBy() {
/* 291 */     return this.referredBy;
/*     */   }
/*     */   public void setReferredBy(String referredBy) {
/* 294 */     this.referredBy = referredBy;
/*     */   }
/*     */   public String getReferralPhone() {
/* 297 */     return this.referralPhone;
/*     */   }
/*     */   public void setReferralPhone(String referralPhone) {
/* 300 */     this.referralPhone = referralPhone;
/*     */   }
/*     */   public String getEmergencyContact() {
/* 303 */     return this.emergencyContact;
/*     */   }
/*     */   public void setEmergencyContact(String emergencyContact) {
/* 306 */     this.emergencyContact = emergencyContact;
/*     */   }
/*     */   public String getEmergencyPhone() {
/* 309 */     return this.emergencyPhone;
/*     */   }
/*     */   public void setEmergencyPhone(String emergencyPhone) {
/* 312 */     this.emergencyPhone = emergencyPhone;
/*     */   }
/*     */   public String getEmergencyRelationship() {
/* 315 */     return this.emergencyRelationship;
/*     */   }
/*     */   public void setEmergencyRelationship(String emergencyRelationship) {
/* 318 */     this.emergencyRelationship = emergencyRelationship;
/*     */   }
/*     */   public String getHairColor() {
/* 321 */     return this.hairColor;
/*     */   }
/*     */   public void setHairColor(String hairColor) {
/* 324 */     this.hairColor = hairColor;
/*     */   }
/*     */   public String getEyeColor() {
/* 327 */     return this.eyeColor;
/*     */   }
/*     */   public void setEyeColor(String eyeColor) {
/* 330 */     this.eyeColor = eyeColor;
/*     */   }
/*     */   public String getHeight() {
/* 333 */     return this.height;
/*     */   }
/*     */   public void setHeight(String height) {
/* 336 */     this.height = height;
/*     */   }
/*     */   public String getWeight() {
/* 339 */     return this.weight;
/*     */   }
/*     */   public void setWeight(String weight) {
/* 342 */     this.weight = weight;
/*     */   }
/*     */   public String getFarmBase() {
/* 345 */     return this.farmBase;
/*     */   }
/*     */   public void setFarmBase(String farmBase) {
/* 348 */     this.farmBase = farmBase;
/*     */   }
/*     */   public String getSsnCard() {
/* 351 */     return this.ssnCard;
/*     */   }
/*     */ 
/*     */   public void setSsnCard(String ssnCard) {
/* 355 */     this.ssnCard = ssnCard;
/*     */   }
/*     */ }

/* Location:           C:\development\workspace\faithfarmoss\WEB-INF\classes\
 * Qualified Name:     org.faithfarm.dataobjects.Person
 * JD-Core Version:    0.6.2
 */