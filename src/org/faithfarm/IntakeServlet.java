/*     */ package org.faithfarm;
/*     */ 
/*     */ import com.google.appengine.api.users.User;
/*     */ import com.google.appengine.api.users.UserService;
/*     */ import com.google.appengine.api.users.UserServiceFactory;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.logging.Logger;
/*     */ import javax.servlet.RequestDispatcher;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.faithfarm.dataobjects.Address;
/*     */ import org.faithfarm.dataobjects.Benevolence;
/*     */ import org.faithfarm.dataobjects.JobSkill;
/*     */ import org.faithfarm.dataobjects.Person;
/*     */ import org.faithfarm.dataobjects.PersonMisc;
/*     */ import org.faithfarm.mail.Mailer;
/*     */ import org.faithfarm.utilities.HTMLBuilder;
/*     */ import org.faithfarm.validators.Validator;
/*     */ 
/*     */ public class IntakeServlet extends BaseServlet
/*     */ {
/*  33 */   private static final Logger logger = Logger.getLogger(CustomerServlet.class.getCanonicalName());
/*  34 */   private static final HTMLBuilder html = new HTMLBuilder();
/*  35 */   private static final Mailer mailer = new Mailer();
/*     */ 
/*     */   protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
/*     */   {
/*  39 */     super.doGet(req, resp);
/*     */ 
/*  41 */     String page = req.getParameter("page");
/*  42 */     String action = req.getParameter("action");
/*  43 */     String farm = (String)req.getSession().getAttribute("farmBase");
/*  44 */     UserService userService = UserServiceFactory.getUserService();
/*  45 */     User user = userService.getCurrentUser();
/*     */ 
/*  64 */     if ("init".equals(action)) {
/*  65 */       req.getSession().setAttribute("person", new Person());
/*  66 */       parsePersonPage1(page, user, action, req);
/*  67 */       req.getRequestDispatcher(req.getContextPath() + "/jsp/intake/page1.jsp").forward(req, resp);
/*     */     }
/*  69 */     else if ("Next".equals(action)) {
/*  70 */       if ("01".equals(page)) {
/*  71 */         if (parsePersonPage1(page, user, action, req))
/*  72 */           req.getRequestDispatcher(req.getContextPath() + "/jsp/intake/page2.jsp").forward(req, resp);
/*     */         else
/*  74 */           req.getRequestDispatcher(req.getContextPath() + "/jsp/intake/page1.jsp").forward(req, resp);
/*     */       }
/*  76 */       else if ("02".equals(page)) {
/*  77 */         if (parsePersonPage2(page, action, req)) {
/*  78 */           parsePersonPage3(page, action, req);
/*  79 */           req.getRequestDispatcher(req.getContextPath() + "/jsp/intake/page3.jsp").forward(req, resp);
/*     */         } else {
/*  81 */           req.getRequestDispatcher(req.getContextPath() + "/jsp/intake/page2.jsp").forward(req, resp);
/*     */         }
/*  83 */       } else if ("03".equals(page)) {
/*  84 */         if (parsePersonPage3(page, action, req))
/*  85 */           req.getRequestDispatcher(req.getContextPath() + "/jsp/intake/page4.jsp").forward(req, resp);
/*     */         else
/*  87 */           req.getRequestDispatcher(req.getContextPath() + "/jsp/intake/page3.jsp").forward(req, resp);
/*     */       }
/*  89 */       else if ("04".equals(page)) {
/*  90 */         if (parsePersonPage4(page, action, req))
/*  91 */           req.getRequestDispatcher(req.getContextPath() + "/jsp/intake/page5.jsp").forward(req, resp);
/*     */         else
/*  93 */           req.getRequestDispatcher(req.getContextPath() + "/jsp/intake/page4.jsp").forward(req, resp);
/*  94 */       } else if ("05".equals(page)) {
/*  95 */         if (parsePersonPage5(page, action, req))
/*  96 */           req.getRequestDispatcher(req.getContextPath() + "/jsp/intake/page6.jsp").forward(req, resp);
/*     */         else
/*  98 */           req.getRequestDispatcher(req.getContextPath() + "/jsp/intake/page5.jsp").forward(req, resp);
/*  99 */       } else if ("06".equals(page)) {
/* 100 */         if (parsePersonPage6(page, action, req))
/* 101 */           req.getRequestDispatcher(req.getContextPath() + "/jsp/intake/page7.jsp").forward(req, resp);
/*     */         else
/* 103 */           req.getRequestDispatcher(req.getContextPath() + "/jsp/intake/page6.jsp").forward(req, resp);
/* 104 */       } else if ("07".equals(page)) {
/* 105 */         if (parsePersonPage7(page, action, req))
/* 106 */           req.getRequestDispatcher(req.getContextPath() + "/jsp/intake/page8.jsp").forward(req, resp);
/*     */         else
/* 108 */           req.getRequestDispatcher(req.getContextPath() + "/jsp/intake/page7.jsp").forward(req, resp);
/* 109 */       } else if ("08".equals(page)) {
/* 110 */         if (parsePersonPage8(page, action, req))
/* 111 */           req.getRequestDispatcher(req.getContextPath() + "/jsp/intake/review.jsp").forward(req, resp);
/*     */         else
/* 113 */           req.getRequestDispatcher(req.getContextPath() + "/jsp/intake/page8.jsp").forward(req, resp);
/*     */       }
/*     */     }
/* 116 */     else if ("Back".equals(action)) {
/* 117 */       if ("02".equals(page))
/* 118 */         req.getRequestDispatcher(req.getContextPath() + "/jsp/intake/page1.jsp").forward(req, resp);
/* 119 */       else if ("03".equals(page))
/* 120 */         req.getRequestDispatcher(req.getContextPath() + "/jsp/intake/page2.jsp").forward(req, resp);
/* 121 */       else if ("04".equals(page))
/* 122 */         req.getRequestDispatcher(req.getContextPath() + "/jsp/intake/page3.jsp").forward(req, resp);
/* 123 */       else if ("05".equals(page))
/* 124 */         req.getRequestDispatcher(req.getContextPath() + "/jsp/intake/page4.jsp").forward(req, resp);
/* 125 */       else if ("06".equals(page))
/* 126 */         req.getRequestDispatcher(req.getContextPath() + "/jsp/intake/page5.jsp").forward(req, resp);
/* 127 */       else if ("07".equals(page))
/* 128 */         req.getRequestDispatcher(req.getContextPath() + "/jsp/intake/page6.jsp").forward(req, resp);
/* 129 */       else if ("08".equals(page))
/* 130 */         req.getRequestDispatcher(req.getContextPath() + "/jsp/intake/page7.jsp").forward(req, resp);
/* 131 */       else if ("09".equals(page)) {
/* 132 */         req.getRequestDispatcher(req.getContextPath() + "/jsp/intake/page8.jsp").forward(req, resp);
/*     */       }
/*     */     }
/* 135 */     else if ("Save".equals(action)) {
/* 136 */       Person person = (Person)req.getSession().getAttribute("person");
/* 137 */       Intake.createOrUpdateIntake(person);
/*     */ 
/* 139 */       if ("Fort Lauderdale".equals(farm)) {
/* 140 */         String body = person.getFirstName() + " " + person.getLastName() + " has been added as a new intake in Fort Lauderdale.";
/* 141 */         Mailer.sendNewStudentMail("ricky.raymond.ratliff@gmail.com", "New Intake Added", body);
/*     */ 
/* 143 */         Mailer.sendNewStudentMail("jayhuff1963@faithfarm.org", "New Intake Added", body);
/*     */       }
/*     */ 
/* 148 */       req.getRequestDispatcher(req.getContextPath() + "/jsp/intake/success.jsp").forward(req, resp);
/*     */     }
/*     */     else {
/* 151 */       req.getRequestDispatcher(req.getContextPath() + "/jsp/intake/page1.jsp").forward(req, resp);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
/*     */   {
/* 157 */     doGet(req, resp);
/*     */   }
/*     */ 
/*     */   protected boolean parsePersonPage1(String page, User user, String action, HttpServletRequest req) {
/* 161 */     Validator validator = new Validator();
/* 162 */     boolean success = true;
/*     */ 
/* 164 */     Person person = (Person)req.getSession().getAttribute("person");
/*     */ 
/* 166 */     person.setCreatedBy(user.getEmail());
/* 167 */     person.setPersonType("Intake");
/* 168 */     person.setFarmBase((String)req.getSession().getAttribute("farm"));
/* 169 */     person.setFirstName(html.cleanData(req.getParameter("firstName")));
/* 170 */     person.setLastName(html.cleanData(req.getParameter("lastName")));
/* 171 */     person.setMiddleInitial(html.cleanData(req.getParameter("middleInitial")).toUpperCase());
/* 172 */     person.setSsn(html.cleanData(req.getParameter("ssn")));
/* 173 */     person.setSsnCard(html.cleanData(req.getParameter("ssnCard")));
/*     */ 
/* 188 */     Benevolence benevolence = new Benevolence();
/* 189 */     benevolence.setSaved(0);
/* 190 */     benevolence.setYtdPaid(0);
/*     */ 
/* 193 */     Address address = new Address();
/* 194 */     address.setAddressLine1(html.cleanData(req.getParameter("addressLine1")));
/* 195 */     address.setAddressLine2(html.cleanData(req.getParameter("addressLine2")));
/* 196 */     address.setCity(html.cleanData(req.getParameter("city")));
/* 197 */     address.setZipcode(html.cleanData(req.getParameter("zipcode")));
/* 198 */     address.setState(html.cleanData(req.getParameter("state")));
/*     */ 
/* 200 */     String phone1 = html.cleanData(req.getParameter("phone_areacode"));
/* 201 */     String phone2 = html.cleanData(req.getParameter("phone_first3"));
/* 202 */     String phone3 = html.cleanData(req.getParameter("phone_last4"));
/* 203 */     String phone = "(" + phone1 + ")" + phone2 + "-" + phone3;
/* 204 */     address.setHomePhone(phone);
/* 205 */     person.setAddress(address);
/* 206 */     person.setBenevolence(benevolence);
/* 207 */     person.setReferredBy(html.cleanData(req.getParameter("referredBy")));
/* 208 */     String refPhone1 = html.cleanData(req.getParameter("ref_areacode"));
/* 209 */     String refPhone2 = html.cleanData(req.getParameter("ref_first3"));
/* 210 */     String refPhone3 = html.cleanData(req.getParameter("ref_last4"));
/* 211 */     String refPhone = "(" + refPhone1 + ")" + refPhone2 + "-" + refPhone3;
/* 212 */     person.setReferralPhone(refPhone);
/*     */ 
/* 214 */     person.setEmergencyContact(html.cleanData(req.getParameter("emergencyContact")));
/* 215 */     person.setEmergencyRelationship(html.cleanData(req.getParameter("emergencyRelationship")));
/* 216 */     String emergPhone1 = html.cleanData(req.getParameter("emer_areacode"));
/* 217 */     String emergPhone2 = html.cleanData(req.getParameter("emer_first3"));
/* 218 */     String emergPhone3 = html.cleanData(req.getParameter("emer_last4"));
/* 219 */     String emergPhone = "(" + emergPhone1 + ")" + emergPhone2 + "-" + emergPhone3;
/* 220 */     person.setEmergencyPhone(emergPhone);
/*     */ 
/* 222 */     req.getSession().setAttribute("person", person);
/*     */ 
/* 224 */     if (("01".equals(page)) && ("Next".equals(action))) {
/* 225 */       ArrayList messages = new ArrayList();
/*     */ 
/* 227 */       messages = validator.validateRequired("firstname", person.getFirstName(), messages);
/* 228 */       messages = validator.validateRequired("lastname", person.getLastName(), messages);
/* 229 */       messages = validator.validateRequired("address line 1", address.getAddressLine1(), messages);
/* 230 */       messages = validator.validateRequired("city", address.getCity(), messages);
/* 231 */       messages = validator.validateRequired("state", address.getState(), messages);
/* 232 */       messages = validator.validateRequired("zipcode", address.getZipcode(), messages);
/* 233 */       messages = validator.validateRequired("social security number", person.getSsn(), messages);
/* 234 */       messages = validator.validateRequired("social security card", person.getSsnCard(), messages);
/*     */ 
/* 236 */       if (messages.size() > 0) {
/* 237 */         success = false;
/*     */       }
/* 239 */       req.setAttribute("messages", messages);
/*     */     }
/* 241 */     return success;
/*     */   }
/*     */ 
/*     */   protected boolean parsePersonPage2(String page, String action, HttpServletRequest req)
/*     */   {
/* 247 */     Validator validator = new Validator();
/* 248 */     boolean success = true;
/*     */ 
/* 251 */     Person person = (Person)req.getSession().getAttribute("person");
/*     */ 
/* 253 */     person.setDateOfBirth(html.cleanData(req.getParameter("dob")));
/* 254 */     person.setAge(html.cleanData(req.getParameter("age")));
/* 255 */     person.setWeight(html.cleanData(req.getParameter("weight")));
/* 256 */     person.setHeight(html.cleanData(req.getParameter("height")));
/* 257 */     person.setEyeColor(html.cleanData(req.getParameter("eyeColor")));
/* 258 */     person.setHairColor(html.cleanData(req.getParameter("hairColor")));
/* 259 */     person.setEthnicity(html.cleanData(req.getParameter("ethnicity")));
/* 260 */     person.setMaritalStatus(html.cleanData(req.getParameter("maritalStatus")));
/* 261 */     person.setHomeLocation(html.cleanData(req.getParameter("homeLocation")));
/* 262 */     person.setEducationLevel(html.cleanData(req.getParameter("education")));
/* 263 */     person.setGraduateFlag(html.cleanData(req.getParameter("graduate")));
/* 264 */     person.setEnglishSpeakFlag(html.cleanData(req.getParameter("speakEnglish")));
/* 265 */     person.setEnglishReadFlag(html.cleanData(req.getParameter("readEnglish")));
/* 266 */     person.setTranscriptsFlag(html.cleanData(req.getParameter("transcripts")));
/*     */ 
/* 270 */     req.getSession().setAttribute("person", person);
/*     */ 
/* 272 */     if (("02".equals(page)) && ("Next".equals(action))) {
/* 273 */       ArrayList messages = new ArrayList();
/*     */ 
/* 275 */       messages = validator.validateRequired("date of birth", person.getDateOfBirth(), messages);
/* 276 */       messages = validator.validateRequired("age", person.getAge(), messages);
/* 277 */       messages = validator.validateRequired("weight", person.getWeight(), messages);
/* 278 */       messages = validator.validateRequired("height", person.getHeight(), messages);
/* 279 */       messages = validator.validateRequired("eye color", person.getEyeColor(), messages);
/* 280 */       messages = validator.validateRequired("hair color", person.getHairColor(), messages);
/* 281 */       messages = validator.validateRequired("ethnicity", person.getEthnicity(), messages);
/* 282 */       messages = validator.validateRequired("marital status", person.getMaritalStatus(), messages);
/* 283 */       messages = validator.validateRequired("where do you live", person.getHomeLocation(), messages);
/* 284 */       messages = validator.validateRequired("school level completed", person.getEducationLevel(), messages);
/* 285 */       messages = validator.validateRequired("transcripts", person.getTranscriptsFlag(), messages);
/* 286 */       messages = validator.validateRequired("did you graduate", person.getGraduateFlag(), messages);
/* 287 */       messages = validator.validateRequired("can you speak English", person.getEnglishSpeakFlag(), messages);
/* 288 */       messages = validator.validateRequired("can you read English", person.getEnglishReadFlag(), messages);
/*     */ 
/* 290 */       if (messages.size() > 0) {
/* 291 */         success = false;
/*     */       }
/* 293 */       req.setAttribute("messages", messages);
/*     */     }
/* 295 */     req.getSession().setAttribute("person", person);
/* 296 */     return success;
/*     */   }
/*     */ 
/*     */   protected boolean parsePersonPage3(String page, String action, HttpServletRequest req)
/*     */   {
/* 301 */     Validator validator = new Validator();
/* 302 */     boolean success = true;
/*     */ 
/* 305 */     Person person = (Person)req.getSession().getAttribute("person");
/* 306 */     PersonMisc personMisc = person.getPersonMisc();
/*     */ 
/* 308 */     personMisc.setVeteranStatus(html.cleanData(req.getParameter("veteranStatus")));
/* 309 */     personMisc.setBranchOfService(html.cleanData(req.getParameter("branchOfService")));
/* 310 */     personMisc.setRank(html.cleanData(req.getParameter("rank")));
/* 311 */     personMisc.setLengthOfService(html.cleanData(req.getParameter("lengthOfService")));
/* 312 */     personMisc.setSelectiveService(html.cleanData(req.getParameter("selectiveService")));
/*     */ 
/* 314 */     String b0 = html.cleanData(req.getParameter("benefit0"));
/* 315 */     String b1 = html.cleanData(req.getParameter("benefit1"));
/* 316 */     String b2 = html.cleanData(req.getParameter("benefit2"));
/* 317 */     String b3 = html.cleanData(req.getParameter("benefit3"));
/* 318 */     String b4 = html.cleanData(req.getParameter("benefit4"));
/* 319 */     String b5 = html.cleanData(req.getParameter("benefit5"));
/* 320 */     String b6 = html.cleanData(req.getParameter("benefit6"));
/* 321 */     String b7 = html.cleanData(req.getParameter("benefit7"));
/*     */ 
/* 323 */     String benefits = "";
/* 324 */     if (b0.length() > 0) benefits = benefits + b0 + ",";
/* 325 */     if (b1.length() > 0) benefits = benefits + b1 + ",";
/* 326 */     if (b2.length() > 0) benefits = benefits + b2 + ",";
/* 327 */     if (b3.length() > 0) benefits = benefits + b3 + ",";
/* 328 */     if (b4.length() > 0) benefits = benefits + b4 + ",";
/* 329 */     if (b5.length() > 0) benefits = benefits + b5 + ",";
/* 330 */     if (b6.length() > 0) benefits = benefits + b6 + ",";
/* 331 */     if (b7.length() > 0) benefits = benefits + b7;
/* 332 */     personMisc.setGovernmentBenefits(benefits);
/*     */ 
/* 334 */     personMisc.setDriversLicenseStatus(html.cleanData(req.getParameter("licenseStatus")));
/* 335 */     personMisc.setDriversLicenseState(html.cleanData(req.getParameter("driversLicenseState")));
/* 336 */     personMisc.setDriversLicenseExpirationDate(html.cleanData(req.getParameter("driversLicenseExpirationDate")));
/* 337 */     personMisc.setStateIdentificationFlag(html.cleanData(req.getParameter("stateIdentificationFlag")));
/* 338 */     personMisc.setBirthCertFlag(html.cleanData(req.getParameter("birthCertFlag")));
/* 339 */     personMisc.setBirthCertCounty(html.cleanData(req.getParameter("birthCertCounty")));
/* 340 */     personMisc.setBirthCertState(html.cleanData(req.getParameter("birthCertState")));
/* 341 */     personMisc.setStateIdentificationFlag(html.cleanData(req.getParameter("stateIdentificationFlag")));
/* 342 */     personMisc.setStateIdentificationState(html.cleanData(req.getParameter("stateIdentificationState")));
/* 343 */     personMisc.setStateIdentificationExpirationDate(html.cleanData(req.getParameter("stateIdentificationExpirationDate")));
/* 344 */     personMisc.setDriversLicenseNumber(html.cleanData(req.getParameter("licenseNumber")));
/* 345 */     personMisc.setMotherLiving(html.cleanData(req.getParameter("motherLiving")));
/* 346 */     personMisc.setFatherLiving(html.cleanData(req.getParameter("fatherLiving")));
/* 347 */     personMisc.setBrothers(html.cleanData(req.getParameter("brothers")));
/* 348 */     personMisc.setSisters(html.cleanData(req.getParameter("sisters")));
/* 349 */     personMisc.setChildren(html.cleanData(req.getParameter("children")));
/* 350 */     personMisc.setLibraryCard(html.cleanData(req.getParameter("libraryCard")));
/* 351 */     personMisc.setReligion(html.cleanData(req.getParameter("religion")));
/* 352 */     personMisc.setReligiousExperience(html.cleanData(req.getParameter("religousExperience")));
/*     */ 
/* 354 */     person.setPersonMisc(personMisc);
/*     */ 
/* 356 */     req.getSession().setAttribute("person", person);
/*     */ 
/* 358 */     if (("03".equals(page)) && ("Next".equals(action))) {
/* 359 */       ArrayList messages = new ArrayList();
/*     */ 
/* 361 */       messages = validator.validateRequired("US veteran", personMisc.getVeteranStatus(), messages);
/* 362 */       if ("Yes".equals(personMisc.getVeteranStatus())) {
/* 363 */         messages = validator.validateRequired("branch of service", personMisc.getBranchOfService(), messages);
/* 364 */         messages = validator.validateRequired("rank", personMisc.getRank(), messages);
/* 365 */         messages = validator.validateRequired("length of service", personMisc.getLengthOfService(), messages);
/*     */       }
/* 367 */       if ("No".equals(personMisc.getVeteranStatus())) {
/* 368 */         messages = validator.validateRequired("selective service registration", personMisc.getSelectiveService(), messages);
/*     */       }
/* 370 */       messages = validator.validateRequired("government benefits", personMisc.getGovernmentBenefits(), messages);
/* 371 */       messages = validator.validateRequired("current driver's license", personMisc.getDriversLicenseStatus(), messages);
/* 372 */       if ("Yes".equals(personMisc.getDriversLicenseStatus())) {
/* 373 */         messages = validator.validateRequired("driver's license state", personMisc.getDriversLicenseState(), messages);
/* 374 */         messages = validator.validateRequired("driver's license number", personMisc.getDriversLicenseNumber(), messages);
/* 375 */         messages = validator.validateRequired("driver's license expiration date", personMisc.getDriversLicenseExpirationDate(), messages);
/*     */       }
/*     */ 
/* 378 */       messages = validator.validateRequired("copy of birth certificate", personMisc.getBirthCertFlag(), messages);
/* 379 */       if ("Yes".equals(personMisc.getBirthCertFlag())) {
/* 380 */         messages = validator.validateRequired("birth certificate county", personMisc.getBirthCertCounty(), messages);
/* 381 */         messages = validator.validateRequired("birth certificate state", personMisc.getBirthCertState(), messages);
/*     */       }
/*     */ 
/* 384 */       messages = validator.validateRequired("valid state id", personMisc.getStateIdentificationFlag(), messages);
/* 385 */       if ("Yes".equals(personMisc.getStateIdentificationFlag())) {
/* 386 */         messages = validator.validateRequired("state identification state", personMisc.getStateIdentificationState(), messages);
/* 387 */         messages = validator.validateRequired("state identification expiration date", personMisc.getStateIdentificationExpirationDate(), messages);
/*     */       }
/* 389 */       messages = validator.validateRequired("library card", personMisc.getLibraryCard(), messages);
/*     */ 
/* 391 */       messages = validator.validateRequired("mother living", personMisc.getMotherLiving(), messages);
/* 392 */       messages = validator.validateRequired("father living", personMisc.getFatherLiving(), messages);
/*     */ 
/* 396 */       messages = validator.validateRequired("religious background", personMisc.getReligion(), messages);
/*     */ 
/* 399 */       if (messages.size() > 0) {
/* 400 */         success = false;
/*     */       }
/* 402 */       req.setAttribute("messages", messages);
/*     */     }
/* 404 */     req.getSession().setAttribute("person", person);
/* 405 */     return success;
/*     */   }
/*     */ 
/*     */   protected boolean parsePersonPage4(String page, String action, HttpServletRequest req)
/*     */   {
/* 410 */     Validator validator = new Validator();
/* 411 */     boolean success = true;
/*     */ 
/* 413 */     Person person = (Person)req.getSession().getAttribute("person");
/* 414 */     PersonMisc personMisc = person.getPersonMisc();
/*     */ 
/* 416 */     personMisc.setAlcoholYearsUsed(html.cleanData(req.getParameter("alcoholYearsUsed")));
/* 417 */     personMisc.setCocaineYearsUsed(html.cleanData(req.getParameter("cocaineYearsUsed")));
/* 418 */     personMisc.setMarijuanaYearsUsed(html.cleanData(req.getParameter("marijuanaYearsUsed")));
/* 419 */     personMisc.setXanaxYearsUsed(html.cleanData(req.getParameter("xanaxYearsUsed")));
/* 420 */     personMisc.setOxicodenYearsUsed(html.cleanData(req.getParameter("oxicodenYearsUsed")));
/* 421 */     personMisc.setSpeedYearsUsed(html.cleanData(req.getParameter("speedYearsUsed")));
/* 422 */     personMisc.setHeroinYearsUsed(html.cleanData(req.getParameter("heroinYearsUsed")));
/* 423 */     personMisc.setOtherYearsUsed(html.cleanData(req.getParameter("otherYearsUsed")));
/* 424 */     personMisc.setAlcoholLastUsed(html.cleanData(req.getParameter("alcoholLastUsed")));
/* 425 */     personMisc.setCocaineLastUsed(html.cleanData(req.getParameter("cocaineLastUsed")));
/* 426 */     personMisc.setMarijuanaLastUsed(html.cleanData(req.getParameter("marijuanaLastUsed")));
/* 427 */     personMisc.setXanaxLastUsed(html.cleanData(req.getParameter("xanaxLastUsed")));
/* 428 */     personMisc.setOxicodenLastUsed(html.cleanData(req.getParameter("oxicodenLastUsed")));
/* 429 */     personMisc.setSpeedLastUsed(html.cleanData(req.getParameter("speedLastUsed")));
/* 430 */     personMisc.setHeroinLastUsed(html.cleanData(req.getParameter("heroinLastUsed")));
/* 431 */     personMisc.setOtherLastUsed(html.cleanData(req.getParameter("otherLastUsed")));
/* 432 */     personMisc.setOtherDrug(html.cleanData(req.getParameter("otherDrug")));
/* 433 */     personMisc.setSober1Years(html.cleanData(req.getParameter("sober1Years")));
/* 434 */     personMisc.setSober3Years(html.cleanData(req.getParameter("sober3Years")));
/* 435 */     personMisc.setUsagePattern(html.cleanData(req.getParameter("usagePattern")));
/* 436 */     personMisc.setQuantityPerWeek(html.cleanData(req.getParameter("quantityPerWeek")));
/* 437 */     personMisc.setQuantity2Years(html.cleanData(req.getParameter("quantity2Years")));
/* 438 */     personMisc.setUsageLosses(html.cleanData(req.getParameter("usageLosses")));
/* 439 */     personMisc.setAbusePhysicalEffects(html.cleanData(req.getParameter("abusePhysicalEffects")));
/* 440 */     personMisc.setSpouseAddicted(html.cleanData(req.getParameter("spouseAddicted")));
/* 441 */     personMisc.setFamilyAddicted(html.cleanData(req.getParameter("familyAddicted")));
/* 442 */     personMisc.setAttendAA(html.cleanData(req.getParameter("attendAA")));
/* 443 */     personMisc.setAttendNA(html.cleanData(req.getParameter("attendNA")));
/* 444 */     personMisc.setYearsAttended(html.cleanData(req.getParameter("yearsAttended")));
/* 445 */     personMisc.setPreviousFaithFarm(html.cleanData(req.getParameter("previousFaithFarm")));
/* 446 */     personMisc.setFaithFarmYear(html.cleanData(req.getParameter("faithFarmYear")));
/*     */ 
/* 448 */     person.setPersonMisc(personMisc);
/*     */ 
/* 451 */     req.getSession().setAttribute("person", person);
/* 452 */     return success;
/*     */   }
/*     */ 
/*     */   protected boolean parsePersonPage5(String page, String action, HttpServletRequest req)
/*     */   {
/* 457 */     Validator validator = new Validator();
/* 458 */     boolean success = true;
/*     */ 
/* 460 */     Person person = (Person)req.getSession().getAttribute("person");
/* 461 */     PersonMisc personMisc = person.getPersonMisc();
/*     */ 
/* 463 */     personMisc.setCurrentHealth(html.cleanData(req.getParameter("currentHealth")));
/* 464 */     personMisc.setCurrentMedicationsFlag(html.cleanData(req.getParameter("currentMedicationsFlag")));
/* 465 */     personMisc.setCurrentMedicationsDetails(html.cleanData(req.getParameter("currentMedicationsDetails")));
/* 466 */     personMisc.setMedicationSuppyFlag(html.cleanData(req.getParameter("medicationSuppyFlag")));
/* 467 */     personMisc.setNeedMedicationFlag(html.cleanData(req.getParameter("needMedicationFlag")));
/* 468 */     personMisc.setRefillDetails(html.cleanData(req.getParameter("refillDetails")));
/* 469 */     personMisc.setGovtHealthCoverage(html.cleanData(req.getParameter("govtHealthCoverage")));
/* 470 */     personMisc.setPrivateHealthCoverage(html.cleanData(req.getParameter("privateHealthCoverage")));
/* 471 */     personMisc.setDoctorsAppointment(html.cleanData(req.getParameter("doctorsAppointment")));
/* 472 */     personMisc.setMedicalQuestion1Flag(html.cleanData(req.getParameter("medicalQuestion1Flag")));
/* 473 */     personMisc.setMedicalQuestion2Flag(html.cleanData(req.getParameter("medicalQuestion2Flag")));
/* 474 */     personMisc.setMedicalQuestion3Flag(html.cleanData(req.getParameter("medicalQuestion3Flag")));
/* 475 */     personMisc.setMedicalQuestion4Flag(html.cleanData(req.getParameter("medicalQuestion4Flag")));
/* 476 */     personMisc.setMedicalQuestion5Flag(html.cleanData(req.getParameter("medicalQuestion5Flag")));
/* 477 */     personMisc.setMedicalQuestion1Details(html.cleanData(req.getParameter("medicalQuestion1Details")));
/* 478 */     personMisc.setMedicalQuestion2Details(html.cleanData(req.getParameter("medicalQuestion2Details")));
/* 479 */     personMisc.setMedicalQuestion3Details(html.cleanData(req.getParameter("medicalQuestion3Details")));
/* 480 */     personMisc.setMedicalQuestion4Details(html.cleanData(req.getParameter("medicalQuestion4Details")));
/* 481 */     personMisc.setMedicalQuestion5Details(html.cleanData(req.getParameter("medicalQuestion5Details")));
/* 482 */     personMisc.setGlassesContactLenses(html.cleanData(req.getParameter("glassesContactLenses")));
/*     */ 
/* 484 */     person.setPersonMisc(personMisc);
/*     */ 
/* 486 */     if (("03".equals(page)) && ("Next".equals(action))) {
/* 487 */       ArrayList messages = new ArrayList();
/*     */ 
/* 489 */       messages = validator.validateRequired("W80 or W72 coverage", personMisc.getGovtHealthCoverage(), messages);
/* 490 */       messages = validator.validateRequired("private healthcare coverage", personMisc.getPrivateHealthCoverage(), messages);
/*     */ 
/* 492 */       if (messages.size() > 0) {
/* 493 */         success = false;
/*     */       }
/* 495 */       req.setAttribute("messages", messages);
/*     */     }
/*     */ 
/* 498 */     req.getSession().setAttribute("person", person);
/* 499 */     return success;
/*     */   }
/*     */ 
/*     */   protected boolean parsePersonPage6(String page, String action, HttpServletRequest req)
/*     */   {
/* 504 */     Validator validator = new Validator();
/* 505 */     boolean success = true;
/*     */ 
/* 507 */     Person person = (Person)req.getSession().getAttribute("person");
/* 508 */     PersonMisc personMisc = person.getPersonMisc();
/*     */ 
/* 510 */     personMisc.setIndustrialInjuryFlag(html.cleanData(req.getParameter("industrialInjuryFlag")));
/* 511 */     personMisc.setIndustrialInjuryDate(html.cleanData(req.getParameter("industrialInjuryDate")));
/* 512 */     personMisc.setIndustrialInjuryDetails(html.cleanData(req.getParameter("industrialInjuryDetails")));
/* 513 */     personMisc.setIndustrialInjuryWhere(html.cleanData(req.getParameter("industrialInjuryWhere")));
/* 514 */     personMisc.setIndustrialInjuryEmployer(html.cleanData(req.getParameter("industrialInjuryEmployer")));
/* 515 */     personMisc.setIndustrialInjuryClaimStatus(html.cleanData(req.getParameter("industrialInjuryClaimStatus")));
/* 516 */     personMisc.setDisabilityFlag(html.cleanData(req.getParameter("disabilityFlag")));
/* 517 */     personMisc.setDisabilityDetails(html.cleanData(req.getParameter("disabilityDetails")));
/* 518 */     personMisc.setExaminationDate(html.cleanData(req.getParameter("examinationDate")));
/* 519 */     personMisc.setPhysician(html.cleanData(req.getParameter("physician")));
/* 520 */     personMisc.setAddress(html.cleanData(req.getParameter("address")));
/*     */ 
/* 522 */     personMisc.setMedicalOpt1(html.cleanData(req.getParameter("medicalOpt1")));
/* 523 */     personMisc.setMedicalOpt2(html.cleanData(req.getParameter("medicalOpt2")));
/* 524 */     personMisc.setMedicalOpt3(html.cleanData(req.getParameter("medicalOpt3")));
/* 525 */     personMisc.setMedicalOpt4(html.cleanData(req.getParameter("medicalOpt4")));
/* 526 */     personMisc.setMedicalOpt5(html.cleanData(req.getParameter("medicalOpt5")));
/* 527 */     personMisc.setMedicalOpt6(html.cleanData(req.getParameter("medicalOpt6")));
/* 528 */     personMisc.setMedicalOpt7(html.cleanData(req.getParameter("medicalOpt7")));
/* 529 */     personMisc.setMedicalOpt8(html.cleanData(req.getParameter("medicalOpt8")));
/* 530 */     personMisc.setMedicalOpt9(html.cleanData(req.getParameter("medicalOpt9")));
/* 531 */     personMisc.setMedicalOpt10(html.cleanData(req.getParameter("medicalOpt10")));
/* 532 */     personMisc.setMedicalOpt11(html.cleanData(req.getParameter("medicalOpt11")));
/* 533 */     personMisc.setMedicalOpt12(html.cleanData(req.getParameter("medicalOpt12")));
/* 534 */     personMisc.setMedicalOpt13(html.cleanData(req.getParameter("medicalOpt13")));
/* 535 */     personMisc.setMedicalOpt14(html.cleanData(req.getParameter("medicalOpt14")));
/* 536 */     personMisc.setMedicalOpt15(html.cleanData(req.getParameter("medicalOpt15")));
/* 537 */     personMisc.setMedicalOpt16(html.cleanData(req.getParameter("medicalOpt16")));
/* 538 */     personMisc.setMedicalOpt17(html.cleanData(req.getParameter("medicalOpt17")));
/* 539 */     personMisc.setMedicalOpt18(html.cleanData(req.getParameter("medicalOpt18")));
/* 540 */     personMisc.setMedicalOpt19(html.cleanData(req.getParameter("medicalOpt19")));
/* 541 */     personMisc.setMedicalOpt20(html.cleanData(req.getParameter("medicalOpt20")));
/* 542 */     personMisc.setMedicalOpt21(html.cleanData(req.getParameter("medicalOpt21")));
/* 543 */     personMisc.setMedicalOpt22(html.cleanData(req.getParameter("medicalOpt22")));
/* 544 */     personMisc.setMedicalOpt23(html.cleanData(req.getParameter("medicalOpt23")));
/* 545 */     personMisc.setMedicalOpt24(html.cleanData(req.getParameter("medicalOpt24")));
/* 546 */     personMisc.setMedicalOpt25(html.cleanData(req.getParameter("medicalOpt25")));
/* 547 */     personMisc.setHerniaOperationFlag(html.cleanData(req.getParameter("herniaOperationFlag")));
/* 548 */     personMisc.setHerniaSide(html.cleanData(req.getParameter("herniaSide")));
/*     */ 
/* 550 */     person.setPersonMisc(personMisc);
/*     */ 
/* 552 */     if (("06".equals(page)) && ("Next".equals(action))) {
/* 553 */       ArrayList messages = new ArrayList();
/*     */ 
/* 555 */       messages = validator.validateRequired("state claim on injury", personMisc.getIndustrialInjuryFlag(), messages);
/* 556 */       if ("Yes".equals(personMisc.getIndustrialInjuryFlag())) {
/* 557 */         messages = validator.validateRequired("injury date", personMisc.getIndustrialInjuryDate(), messages);
/* 558 */         messages = validator.validateRequired("injury details", personMisc.getIndustrialInjuryDetails(), messages);
/* 559 */         messages = validator.validateRequired("location of injury", personMisc.getIndustrialInjuryWhere(), messages);
/* 560 */         messages = validator.validateRequired("employer", personMisc.getIndustrialInjuryEmployer(), messages);
/* 561 */         messages = validator.validateRequired("claims status", personMisc.getIndustrialInjuryClaimStatus(), messages);
/*     */       }
/* 563 */       messages = validator.validateRequired("physical disability", personMisc.getDisabilityFlag(), messages);
/* 564 */       if ("Yes".equals(personMisc.getDisabilityFlag())) {
/* 565 */         messages = validator.validateRequired("disability details", personMisc.getDisabilityDetails(), messages);
/* 566 */         messages = validator.validateRequired("date of last examination", personMisc.getExaminationDate(), messages);
/* 567 */         messages = validator.validateRequired("physician's name", personMisc.getPhysician(), messages);
/* 568 */         messages = validator.validateRequired("physician's address", personMisc.getAddress(), messages);
/*     */       }
/*     */ 
/* 571 */       if (messages.size() > 0) {
/* 572 */         success = false;
/*     */       }
/* 574 */       req.setAttribute("messages", messages);
/*     */     }
/* 576 */     req.getSession().setAttribute("person", person);
/* 577 */     return success;
/*     */   }
/*     */ 
/*     */   protected boolean parsePersonPage7(String page, String action, HttpServletRequest req)
/*     */   {
/* 582 */     Validator validator = new Validator();
/* 583 */     boolean success = true;
/*     */ 
/* 585 */     Person person = (Person)req.getSession().getAttribute("person");
/* 586 */     PersonMisc personMisc = person.getPersonMisc();
/*     */ 
/* 588 */     personMisc.setLegalQuestion1(html.cleanData(req.getParameter("legalQuestion1")));
/* 589 */     personMisc.setLegalQuestion1Date(html.cleanData(req.getParameter("legalQuestion1Date")));
/* 590 */     personMisc.setLegalQuestion1Details(html.cleanData(req.getParameter("legalQuestion1Details")));
/* 591 */     personMisc.setLegalQuestion2(html.cleanData(req.getParameter("legalQuestion2")));
/* 592 */     personMisc.setLegalQuestion2Date(html.cleanData(req.getParameter("legalQuestion2Date")));
/* 593 */     personMisc.setLegalQuestion2Details(html.cleanData(req.getParameter("legalQuestion2Details")));
/* 594 */     personMisc.setLegalQuestion3(html.cleanData(req.getParameter("legalQuestion3")));
/* 595 */     personMisc.setLegalQuestion3Quantity(html.cleanData(req.getParameter("legalQuestion3Quantity")));
/* 596 */     personMisc.setLegalQuestion3Details(html.cleanData(req.getParameter("legalQuestion3Details")));
/* 597 */     personMisc.setLegalQuestion4(html.cleanData(req.getParameter("legalQuestion4")));
/* 598 */     personMisc.setLegalQuestion4Details(html.cleanData(req.getParameter("legalQuestion4Details")));
/* 599 */     personMisc.setProbationFlag(html.cleanData(req.getParameter("probationFlag")));
/* 600 */     personMisc.setProbationCounty(html.cleanData(req.getParameter("probationCounty")));
/* 601 */     personMisc.setProbationState(html.cleanData(req.getParameter("probationState")));
/* 602 */     personMisc.setProbationOfficer(html.cleanData(req.getParameter("probationOfficer")));
/* 603 */     personMisc.setPendingCourtDates(html.cleanData(req.getParameter("pendingCourtDates")));
/* 604 */     personMisc.setChildSupport(html.cleanData(req.getParameter("childSupport")));
/* 605 */     personMisc.setRestitution(html.cleanData(req.getParameter("restitution")));
/*     */ 
/* 607 */     String p1 = html.cleanData(req.getParameter("probationOfficerPhone_areacode"));
/* 608 */     String p2 = html.cleanData(req.getParameter("probationOfficerPhone_first3"));
/* 609 */     String p3 = html.cleanData(req.getParameter("probationOfficerPhone_last4"));
/* 610 */     String phone = "(" + p1 + ")" + p2 + "-" + p3;
/* 611 */     personMisc.setProbationOfficerPhone(phone);
/*     */ 
/* 613 */     person.setPersonMisc(personMisc);
/*     */ 
/* 615 */     if (("07".equals(page)) && ("Next".equals(action))) {
/* 616 */       ArrayList messages = new ArrayList();
/*     */ 
/* 618 */       messages = validator.validateRequired("have you been sued", personMisc.getLegalQuestion1(), messages);
/* 619 */       if ("Yes".equals(personMisc.getLegalQuestion1())) {
/* 620 */         messages = validator.validateRequired("date sued", personMisc.getLegalQuestion1Date(), messages);
/* 621 */         messages = validator.validateRequired("details", personMisc.getLegalQuestion1Details(), messages);
/*     */       }
/* 623 */       messages = validator.validateRequired("have you been involved in a lawsuit", personMisc.getLegalQuestion2(), messages);
/* 624 */       if ("Yes".equals(personMisc.getLegalQuestion2())) {
/* 625 */         messages = validator.validateRequired("lawsuit date", personMisc.getLegalQuestion2Date(), messages);
/* 626 */         messages = validator.validateRequired("lawsuit details", personMisc.getLegalQuestion2Details(), messages);
/*     */       }
/* 628 */       messages = validator.validateRequired("have you ever been convicted of a felony", personMisc.getLegalQuestion3(), messages);
/* 629 */       if ("Yes".equals(personMisc.getLegalQuestion3())) {
/* 630 */         messages = validator.validateRequired("how many times", personMisc.getLegalQuestion3Quantity(), messages);
/* 631 */         messages = validator.validateRequired("felonies must be listed", personMisc.getLegalQuestion3Details(), messages);
/*     */       }
/* 633 */       messages = validator.validateRequired("have you ever convicted of a sexual offense", personMisc.getLegalQuestion4(), messages);
/* 634 */       if ("Yes".equals(personMisc.getLegalQuestion4())) {
/* 635 */         messages = validator.validateRequired("sexual offenses must be listed", personMisc.getLegalQuestion2Details(), messages);
/*     */       }
/* 637 */       messages = validator.validateRequired("are you on probation", personMisc.getProbationFlag(), messages);
/* 638 */       if ("Yes".equals(personMisc.getProbationFlag())) {
/* 639 */         messages = validator.validateRequired("probation county", personMisc.getProbationCounty(), messages);
/* 640 */         messages = validator.validateRequired("probation state", personMisc.getProbationState(), messages);
/* 641 */         messages = validator.validateRequired("probation officer", personMisc.getProbationOfficer(), messages);
/* 642 */         messages = validator.validateRequired("probation officer's phone#", personMisc.getProbationOfficerPhone(), messages);
/*     */       }
/* 644 */       messages = validator.validateRequired("pending court dates", personMisc.getPendingCourtDates(), messages);
/*     */ 
/* 647 */       if (messages.size() > 0) {
/* 648 */         success = false;
/*     */       }
/* 650 */       req.setAttribute("messages", messages);
/*     */     }
/* 652 */     req.getSession().setAttribute("person", person);
/* 653 */     return success;
/*     */   }
/*     */ 
/*     */   protected boolean parsePersonPage8(String page, String action, HttpServletRequest req)
/*     */   {
/* 659 */     Validator validator = new Validator();
/* 660 */     boolean success = true;
/*     */ 
/* 662 */     Person person = (Person)req.getSession().getAttribute("person");
/* 663 */     JobSkill job = person.getJobSkill();
/*     */ 
/* 665 */     job.setAirConditioning(html.cleanData(req.getParameter("airConditioning")));
/* 666 */     job.setApplianceRepair(html.cleanData(req.getParameter("applianceRepair")));
/* 667 */     job.setAutoBodyRepair(html.cleanData(req.getParameter("autoBodyRepair")));
/* 668 */     job.setCarpenter(html.cleanData(req.getParameter("carpenter")));
/* 669 */     job.setClothesSorter(html.cleanData(req.getParameter("clothesSorter")));
/* 670 */     job.setComputerProgramming(html.cleanData(req.getParameter("computerProgramming")));
/* 671 */     job.setCook(html.cleanData(req.getParameter("cook")));
/* 672 */     job.setCustodian(html.cleanData(req.getParameter("custodian")));
/* 673 */     job.setComputerRepair(html.cleanData(req.getParameter("computerRepair")));
/* 674 */     job.setElectrician(html.cleanData(req.getParameter("electrician")));
/* 675 */     job.setForkLiftOperator(html.cleanData(req.getParameter("forkLiftOperator")));
/* 676 */     job.setKitchen(html.cleanData(req.getParameter("kitchen")));
/* 677 */     job.setLandscaping(html.cleanData(req.getParameter("landscaping")));
/* 678 */     job.setMaintenance(html.cleanData(req.getParameter("maintenance")));
/* 679 */     job.setMechanic(html.cleanData(req.getParameter("mechanic")));
/* 680 */     job.setOfficeWork(html.cleanData(req.getParameter("officeWork")));
/* 681 */     job.setOtherSkill(html.cleanData(req.getParameter("otherSkill")));
/* 682 */     job.setPainter(html.cleanData(req.getParameter("painter")));
/* 683 */     job.setPhoneRoom(html.cleanData(req.getParameter("phoneRoom")));
/* 684 */     job.setPlumber(html.cleanData(req.getParameter("plumber")));
/* 685 */     job.setRadioTvRepair(html.cleanData(req.getParameter("radioTvRepair")));
/* 686 */     job.setSales(html.cleanData(req.getParameter("sales")));
/* 687 */     job.setTruckDriver(html.cleanData(req.getParameter("truckDriver")));
/* 688 */     job.setUpholstery(html.cleanData(req.getParameter("upholstery")));
/* 689 */     job.setWarehousing(html.cleanData(req.getParameter("warehousing")));
/* 690 */     job.setWelder(html.cleanData(req.getParameter("welder")));
/*     */ 
/* 692 */     job.setEmployer1(html.cleanData(req.getParameter("employer1")));
/* 693 */     job.setEmployer2(html.cleanData(req.getParameter("employer2")));
/* 694 */     job.setEmployer3(html.cleanData(req.getParameter("employer3")));
/* 695 */     job.setEmployer4(html.cleanData(req.getParameter("employer4")));
/*     */ 
/* 697 */     job.setJobTitle1(html.cleanData(req.getParameter("jobTitle1")));
/* 698 */     job.setJobTitle2(html.cleanData(req.getParameter("jobTitle2")));
/* 699 */     job.setJobTitle3(html.cleanData(req.getParameter("jobTitle3")));
/* 700 */     job.setJobTitle4(html.cleanData(req.getParameter("jobTitle4")));
/*     */ 
/* 702 */     job.setBeginDate1(html.cleanData(req.getParameter("beginDate1")));
/* 703 */     job.setBeginDate2(html.cleanData(req.getParameter("beginDate2")));
/* 704 */     job.setBeginDate3(html.cleanData(req.getParameter("beginDate3")));
/* 705 */     job.setBeginDate4(html.cleanData(req.getParameter("beginDate4")));
/*     */ 
/* 707 */     job.setEndDate1(html.cleanData(req.getParameter("endDate1")));
/* 708 */     job.setEndDate2(html.cleanData(req.getParameter("endDate2")));
/* 709 */     job.setEndDate3(html.cleanData(req.getParameter("endDate3")));
/* 710 */     job.setEndDate4(html.cleanData(req.getParameter("endDate4")));
/*     */ 
/* 712 */     job.setContactName1(html.cleanData(req.getParameter("contactName1")));
/* 713 */     job.setContactName2(html.cleanData(req.getParameter("contactName2")));
/* 714 */     job.setContactName3(html.cleanData(req.getParameter("contactName3")));
/* 715 */     job.setContactName4(html.cleanData(req.getParameter("contactName4")));
/*     */ 
/* 717 */     job.setContactNumber1(html.cleanData(req.getParameter("contactNumber1")));
/* 718 */     job.setContactNumber2(html.cleanData(req.getParameter("contactNumber2")));
/* 719 */     job.setContactNumber3(html.cleanData(req.getParameter("contactNumber3")));
/* 720 */     job.setContactNumber4(html.cleanData(req.getParameter("contactNumber4")));
/*     */ 
/* 722 */     person.setJobSkill(job);
/* 723 */     req.getSession().setAttribute("person", person);
/* 724 */     return true;
/*     */   }
/*     */ }

/* Location:           C:\development\workspace\faithfarmoss\WEB-INF\classes\
 * Qualified Name:     org.faithfarm.IntakeServlet
 * JD-Core Version:    0.6.2
 */