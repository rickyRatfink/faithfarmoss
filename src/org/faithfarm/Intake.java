/*     */ package org.faithfarm;
/*     */ 
/*     */ /*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;

/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServletRequest;

/*     */ import org.faithfarm.dataobjects.Address;
/*     */ import org.faithfarm.dataobjects.Benevolence;
/*     */ import org.faithfarm.dataobjects.JobSkill;
/*     */ import org.faithfarm.dataobjects.Person;
/*     */ import org.faithfarm.dataobjects.PersonMisc;
/*     */ import org.faithfarm.validators.InventoryValidator;

import com.google.appengine.api.datastore.DatastoreService;
/*     */ import com.google.appengine.api.datastore.DatastoreServiceFactory;
/*     */ import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
/*     */ import com.google.appengine.api.datastore.Key;
/*     */ import com.google.appengine.api.datastore.KeyFactory;
/*     */ import com.google.appengine.api.datastore.Query;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Intake
/*     */   implements Serializable
/*     */ {
/*  33 */   private static final Logger logger = Logger.getLogger(Intake.class.getCanonicalName());
/*  34 */   private static InventoryValidator validator = new InventoryValidator();
/*  35 */   private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
/*  36 */   private static int size = 0;
/*  37 */   private static int pages = 0;
/*  38 */   private static String cursor = null;
/*     */ 
/*     */   public static void createOrUpdateIntake(Person person)
/*     */   {
/*  74 */     Address address = person.getAddress();
/*  75 */     PersonMisc personMisc = person.getPersonMisc();
/*  76 */     JobSkill job = person.getJobSkill();
/*  77 */     Benevolence benevolence = person.getBenevolence();
/*     */ 
/*  79 */     Entity Person = createPerson(person);
/*  80 */     createAddress(address, Person);
/*  81 */     createPersonMisc(personMisc, Person);
/*  82 */     createJobSkill(job, Person);
/*     */   }
/*     */ 
/*     */   private static void createAddress(Address address, Entity Person)
/*     */   {
/*  87 */     Entity Address = new Entity("Address", Person.getKey());
/*     */ 
/*  89 */     Address.setProperty("addressLine1", address.getAddressLine1());
/*  90 */     Address.setProperty("addressLine2", address.getAddressLine2());
/*  91 */     Address.setProperty("city", address.getCity());
/*  92 */     Address.setProperty("state", address.getState());
/*  93 */     Address.setProperty("zipcode", address.getZipcode());
/*  94 */     Address.setProperty("homePhone", address.getHomePhone());
/*  95 */     Address.setProperty("workPhone", address.getWorkPhone());
/*  96 */     Address.setProperty("cellPhone", address.getCellPhone());
/*  97 */     Address.setProperty("email", address.getEmail());
/*  98 */     Address.setProperty("building", address.getBuilding());
/*  99 */     Address.setProperty("subdivision", address.getSubdivision());
/* 100 */     Address.setProperty("apartmentNumber", address.getApartmentNumber());
/* 101 */     Address.setProperty("majorIntersection", address.getMajorIntersection());
/* 102 */     Address.setProperty("addressType", address.getAddressType());
/* 103 */     Util.persistEntity(Address);
/*     */   }
/*     */ 
/*     */   private static void createPersonMisc(PersonMisc personMisc, Entity Person)
/*     */   {
/* 108 */     Entity PersonMisc = new Entity("PersonMisc", Person.getKey());
/*     */ 
/* 110 */     PersonMisc.setProperty("motherLiving", personMisc.getMotherLiving());
/* 111 */     PersonMisc.setProperty("fatherLiving", personMisc.getFatherLiving());
/* 112 */     PersonMisc.setProperty("brothers", personMisc.getBrothers());
/* 113 */     PersonMisc.setProperty("sisters", personMisc.getSisters());
/* 114 */     PersonMisc.setProperty("children", personMisc.getChildren());
/* 115 */     PersonMisc.setProperty("veteranStatus", personMisc.getVeteranStatus());
/* 116 */     PersonMisc.setProperty("branchOfService", personMisc.getBranchOfService());
/* 117 */     PersonMisc.setProperty("selectiveService", personMisc.getSelectiveService());
/* 118 */     PersonMisc.setProperty("childSupport", personMisc.getChildSupport());
/* 119 */     PersonMisc.setProperty("restitution", personMisc.getRestitution());
/* 120 */     PersonMisc.setProperty("stateIdentificationFlag", personMisc.getStateIdentificationFlag());
/* 121 */     PersonMisc.setProperty("stateIdentificationState", personMisc.getStateIdentificationState());
/* 122 */     PersonMisc.setProperty("stateIdentificationExpirationDate", personMisc.getStateIdentificationExpirationDate());
/* 123 */     PersonMisc.setProperty("driversLicenseExpirationDate", personMisc.getDriversLicenseExpirationDate());
/* 124 */     PersonMisc.setProperty("birthCertFlag", personMisc.getBirthCertFlag());
/* 125 */     PersonMisc.setProperty("birthCertCounty", personMisc.getBirthCertCounty());
/* 126 */     PersonMisc.setProperty("birthCertState", personMisc.getBirthCertState());
/* 127 */     PersonMisc.setProperty("stateIdentificationFlag", personMisc.getStateIdentificationFlag());
/* 128 */     PersonMisc.setProperty("libraryCard", personMisc.getLibraryCard());
/* 129 */     PersonMisc.setProperty("govtHealthCoverage", personMisc.getGovtHealthCoverage());
/* 130 */     PersonMisc.setProperty("privateHealthCoverage", personMisc.getPrivateHealthCoverage());
/* 131 */     PersonMisc.setProperty("pendingCourtDates", personMisc.getPendingCourtDates());
/*     */ 
/* 137 */     PersonMisc.setProperty("rank", personMisc.getRank());
/* 138 */     PersonMisc.setProperty("lengthOfService", personMisc.getLengthOfService());
/* 139 */     PersonMisc.setProperty("driversLicenseStatus", personMisc.getDriversLicenseStatus());
/* 140 */     PersonMisc.setProperty("driversLicenseNumber", personMisc.getDriversLicenseNumber());
/* 141 */     PersonMisc.setProperty("driversLicenseState", personMisc.getDriversLicenseState());
/* 142 */     PersonMisc.setProperty("governmentBenefits", personMisc.getGovernmentBenefits());
/* 143 */     PersonMisc.setProperty("religion", personMisc.getReligion());
/* 144 */     PersonMisc.setProperty("religiousExperience", personMisc.getReligiousExperience());
/* 145 */     PersonMisc.setProperty("alcohol", personMisc.getAlcohol());
/* 146 */     PersonMisc.setProperty("cocaine", personMisc.getCocaine());
/* 147 */     PersonMisc.setProperty("marijuana", personMisc.getMarijuana());
/* 148 */     PersonMisc.setProperty("oxicoden", personMisc.getOxicoden());
/* 149 */     PersonMisc.setProperty("speed", personMisc.getSpeed());
/* 150 */     PersonMisc.setProperty("heroin", personMisc.getHeroin());
/* 151 */     PersonMisc.setProperty("xanax", personMisc.getXanax());
/* 152 */     PersonMisc.setProperty("other", personMisc.getOther());
/* 153 */     PersonMisc.setProperty("alcoholYearsUsed", personMisc.getAlcoholYearsUsed());
/* 154 */     PersonMisc.setProperty("cocaineYearsUsed", personMisc.getCocaineYearsUsed());
/* 155 */     PersonMisc.setProperty("marijuanaYearsUsed", personMisc.getMarijuanaYearsUsed());
/* 156 */     PersonMisc.setProperty("oxicodenYearsUsed", personMisc.getOxicodenYearsUsed());
/* 157 */     PersonMisc.setProperty("speedYearsUsed", personMisc.getSpeedYearsUsed());
/* 158 */     PersonMisc.setProperty("heroinYearsUsed", personMisc.getHeroinYearsUsed());
/* 159 */     PersonMisc.setProperty("xanaxYearsUsed", personMisc.getXanaxYearsUsed());
/* 160 */     PersonMisc.setProperty("otherYearsUsed", personMisc.getOtherYearsUsed());
/* 161 */     PersonMisc.setProperty("alcoholLastUsed", personMisc.getAlcoholLastUsed());
/* 162 */     PersonMisc.setProperty("cocaineLastUsed", personMisc.getCocaineLastUsed());
/* 163 */     PersonMisc.setProperty("marijuanaLastUsed", personMisc.getMarijuanaLastUsed());
/* 164 */     PersonMisc.setProperty("oxicodenLastUsed", personMisc.getOxicodenLastUsed());
/* 165 */     PersonMisc.setProperty("speedLastUsed", personMisc.getSpeedLastUsed());
/* 166 */     PersonMisc.setProperty("heroinLastUsed", personMisc.getHeroinLastUsed());
/* 167 */     PersonMisc.setProperty("xanaxLastUsed", personMisc.getXanaxLastUsed());
/* 168 */     PersonMisc.setProperty("otherLastUsed", personMisc.getOtherLastUsed());
/* 169 */     PersonMisc.setProperty("otherDrug", personMisc.getOtherDrug());
/* 170 */     PersonMisc.setProperty("sober3Years", personMisc.getSober3Years());
/* 171 */     PersonMisc.setProperty("sober1Years", personMisc.getSober1Years());
/* 172 */     PersonMisc.setProperty("usagePattern", personMisc.getUsagePattern());
/* 173 */     PersonMisc.setProperty("quantityPerWeek", personMisc.getQuantityPerWeek());
/* 174 */     PersonMisc.setProperty("quantity2Years", personMisc.getQuantity2Years());
/* 175 */     PersonMisc.setProperty("usageLosses", personMisc.getUsageLosses());
/* 176 */     PersonMisc.setProperty("abusePhysicalEffects", personMisc.getAbusePhysicalEffects());
/* 177 */     PersonMisc.setProperty("spouseAddicted", personMisc.getSpouseAddicted());
/* 178 */     PersonMisc.setProperty("familyAddicted", personMisc.getFamilyAddicted());
/* 179 */     PersonMisc.setProperty("attendAA", personMisc.getAttendAA());
/* 180 */     PersonMisc.setProperty("attendNA", personMisc.getAttendNA());
/* 181 */     PersonMisc.setProperty("yearsAttended", personMisc.getYearsAttended());
/* 182 */     PersonMisc.setProperty("previousFaithFarm", personMisc.getPreviousFaithFarm());
/* 183 */     PersonMisc.setProperty("faithFarmYear", personMisc.getFaithFarmYear());
/* 184 */     PersonMisc.setProperty("currentHealth", personMisc.getCurrentHealth());
/* 185 */     PersonMisc.setProperty("currentMedicationsFlag", personMisc.getCurrentMedicationsFlag());
/* 186 */     PersonMisc.setProperty("currentMedicationsDetails", personMisc.getCurrentMedicationsDetails());
/* 187 */     PersonMisc.setProperty("needMedicationFlag", personMisc.getNeedMedicationFlag());
/* 188 */     PersonMisc.setProperty("medicationSuppyFlag", personMisc.getMedicationSuppyFlag());
/* 189 */     PersonMisc.setProperty("refillDetails", personMisc.getRefillDetails());
/* 190 */     PersonMisc.setProperty("doctorsAppointment", personMisc.getDoctorsAppointment());
/* 191 */     PersonMisc.setProperty("medicalQuestion1Flag", personMisc.getMedicalQuestion1Flag());
/* 192 */     PersonMisc.setProperty("medicalQuestion2Flag", personMisc.getMedicalQuestion2Flag());
/* 193 */     PersonMisc.setProperty("medicalQuestion3Flag", personMisc.getMedicalQuestion3Flag());
/* 194 */     PersonMisc.setProperty("medicalQuestion4Flag", personMisc.getMedicalQuestion4Flag());
/* 195 */     PersonMisc.setProperty("medicalQuestion5Flag", personMisc.getMedicalQuestion5Flag());
/* 196 */     PersonMisc.setProperty("medicalQuestion1Details", personMisc.getMedicalQuestion1Details());
/* 197 */     PersonMisc.setProperty("medicalQuestion2Details", personMisc.getMedicalQuestion2Details());
/* 198 */     PersonMisc.setProperty("medicalQuestion3Details", personMisc.getMedicalQuestion3Details());
/* 199 */     PersonMisc.setProperty("medicalQuestion4Details", personMisc.getMedicalQuestion4Details());
/* 200 */     PersonMisc.setProperty("medicalQuestion5Details", personMisc.getMedicalQuestion5Details());
/* 201 */     PersonMisc.setProperty("glassesContactLenses", personMisc.getGlassesContactLenses());
/* 202 */     PersonMisc.setProperty("industrialInjuryFlag", personMisc.getIndustrialInjuryFlag());
/* 203 */     PersonMisc.setProperty("industrialInjuryDate", personMisc.getIndustrialInjuryDate());
/* 204 */     PersonMisc.setProperty("industrialInjuryDetails", personMisc.getIndustrialInjuryDetails());
/* 205 */     PersonMisc.setProperty("industrialInjuryWhere", personMisc.getIndustrialInjuryWhere());
/* 206 */     PersonMisc.setProperty("industrialInjuryEmployer", personMisc.getIndustrialInjuryEmployer());
/* 207 */     PersonMisc.setProperty("industrialInjuryClaimStatus", personMisc.getIndustrialInjuryClaimStatus());
/* 208 */     PersonMisc.setProperty("disabilityFlag", personMisc.getDisabilityFlag());
/* 209 */     PersonMisc.setProperty("disabilityDetails", personMisc.getDisabilityDetails());
/* 210 */     PersonMisc.setProperty("examinationDate", personMisc.getExaminationDate());
/* 211 */     PersonMisc.setProperty("physician", personMisc.getPhysician());
/* 212 */     PersonMisc.setProperty("address", personMisc.getAddress());
/* 213 */     PersonMisc.setProperty("herniaSide", personMisc.getHerniaSide());
/* 214 */     PersonMisc.setProperty("herniaOperationFlag", personMisc.getHerniaOperationFlag());
/* 215 */     PersonMisc.setProperty("medicalConditionDetails", personMisc.getMedicalConditionDetails());
/* 216 */     PersonMisc.setProperty("legalQuestion1", personMisc.getLegalQuestion1());
/* 217 */     PersonMisc.setProperty("legalQuestion1Date", personMisc.getLegalQuestion1Date());
/* 218 */     PersonMisc.setProperty("legalQuestion1Details", personMisc.getLegalQuestion1Details());
/* 219 */     PersonMisc.setProperty("legalQuestion2", personMisc.getLegalQuestion2());
/* 220 */     PersonMisc.setProperty("legalQuestion2Date", personMisc.getLegalQuestion2Date());
/* 221 */     PersonMisc.setProperty("legalQuestion2Details", personMisc.getLegalQuestion2Details());
/* 222 */     PersonMisc.setProperty("legalQuestion3", personMisc.getLegalQuestion3());
/* 223 */     PersonMisc.setProperty("legalQuestion3Date", personMisc.getLegalQuestion3Date());
/* 224 */     PersonMisc.setProperty("legalQuestion3Details", personMisc.getLegalQuestion3Details());
/* 225 */     PersonMisc.setProperty("legalQuestion3Quantity", personMisc.getLegalQuestion3Quantity());
/* 226 */     PersonMisc.setProperty("legalQuestion4", personMisc.getLegalQuestion4());
/* 227 */     PersonMisc.setProperty("legalQuestion4Date", personMisc.getLegalQuestion4Date());
/* 228 */     PersonMisc.setProperty("legalQuestion4Details", personMisc.getLegalQuestion4Details());
/* 229 */     PersonMisc.setProperty("legalQuestion5", personMisc.getLegalQuestion5());
/* 230 */     PersonMisc.setProperty("legalQuestion5Date", personMisc.getLegalQuestion5Date());
/* 231 */     PersonMisc.setProperty("legalQuestion5Details", personMisc.getLegalQuestion5Details());
/* 232 */     PersonMisc.setProperty("probationFlag", personMisc.getProbationFlag());
/* 233 */     PersonMisc.setProperty("probationCounty", personMisc.getProbationCounty());
/* 234 */     PersonMisc.setProperty("probationState", personMisc.getProbationState());
/* 235 */     PersonMisc.setProperty("probationOfficer", personMisc.getProbationOfficer());
/* 236 */     PersonMisc.setProperty("probationOfficerPhone", personMisc.getProbationOfficerPhone());
/* 237 */     PersonMisc.setProperty("workExperienceDetails", personMisc.getWorkExperienceDetails());
/* 238 */     PersonMisc.setProperty("employer1", personMisc.getEmployer1());
/* 239 */     PersonMisc.setProperty("jobTitle1", personMisc.getJobTitle1());
/* 240 */     PersonMisc.setProperty("jobStartDate1", personMisc.getJobStartDate1());
/* 241 */     PersonMisc.setProperty("jobEndDate1", personMisc.getJobEndDate1());
/* 242 */     PersonMisc.setProperty("employer2", personMisc.getEmployer2());
/* 243 */     PersonMisc.setProperty("jobTitle2", personMisc.getJobTitle2());
/* 244 */     PersonMisc.setProperty("jobStartDate2", personMisc.getJobStartDate2());
/* 245 */     PersonMisc.setProperty("jobEndDate2", personMisc.getJobEndDate2());
/* 246 */     PersonMisc.setProperty("employer3", personMisc.getEmployer3());
/* 247 */     PersonMisc.setProperty("jobTitle3", personMisc.getJobTitle3());
/* 248 */     PersonMisc.setProperty("jobStartDate3", personMisc.getJobStartDate3());
/* 249 */     PersonMisc.setProperty("jobEndDate3", personMisc.getJobEndDate3());
/*     */ 
/* 251 */     PersonMisc.setProperty("medicalOpt1", personMisc.getMedicalOpt1());
/* 252 */     PersonMisc.setProperty("medicalOpt2", personMisc.getMedicalOpt2());
/* 253 */     PersonMisc.setProperty("medicalOpt3", personMisc.getMedicalOpt3());
/* 254 */     PersonMisc.setProperty("medicalOpt4", personMisc.getMedicalOpt4());
/* 255 */     PersonMisc.setProperty("medicalOpt5", personMisc.getMedicalOpt5());
/* 256 */     PersonMisc.setProperty("medicalOpt6", personMisc.getMedicalOpt6());
/* 257 */     PersonMisc.setProperty("medicalOpt7", personMisc.getMedicalOpt7());
/* 258 */     PersonMisc.setProperty("medicalOpt8", personMisc.getMedicalOpt8());
/* 259 */     PersonMisc.setProperty("medicalOpt9", personMisc.getMedicalOpt9());
/* 260 */     PersonMisc.setProperty("medicalOpt10", personMisc.getMedicalOpt10());
/* 261 */     PersonMisc.setProperty("medicalOpt11", personMisc.getMedicalOpt11());
/* 262 */     PersonMisc.setProperty("medicalOpt12", personMisc.getMedicalOpt12());
/* 263 */     PersonMisc.setProperty("medicalOpt13", personMisc.getMedicalOpt13());
/* 264 */     PersonMisc.setProperty("medicalOpt14", personMisc.getMedicalOpt14());
/* 265 */     PersonMisc.setProperty("medicalOpt15", personMisc.getMedicalOpt15());
/* 266 */     PersonMisc.setProperty("medicalOpt16", personMisc.getMedicalOpt16());
/* 267 */     PersonMisc.setProperty("medicalOpt17", personMisc.getMedicalOpt17());
/* 268 */     PersonMisc.setProperty("medicalOpt18", personMisc.getMedicalOpt18());
/* 269 */     PersonMisc.setProperty("medicalOpt19", personMisc.getMedicalOpt19());
/* 270 */     PersonMisc.setProperty("medicalOpt20", personMisc.getMedicalOpt20());
/* 271 */     PersonMisc.setProperty("medicalOpt21", personMisc.getMedicalOpt21());
/* 272 */     PersonMisc.setProperty("medicalOpt22", personMisc.getMedicalOpt22());
/* 273 */     PersonMisc.setProperty("medicalOpt23", personMisc.getMedicalOpt23());
/* 274 */     PersonMisc.setProperty("medicalOpt24", personMisc.getMedicalOpt24());
/* 275 */     PersonMisc.setProperty("medicalOpt25", personMisc.getMedicalOpt25());
/*     */ 
/* 277 */     Util.persistEntity(PersonMisc);
/*     */   }
/*     */ 
/*     */   private static void createJobSkill(JobSkill job, Entity Person)
/*     */   {
/* 282 */     Entity Job = new Entity("JobSkill", Person.getKey());
/*     */ 
/* 284 */     Job.setProperty("officeWork", job.getOfficeWork());
/* 285 */     Job.setProperty("mechanic", job.getMechanic());
/* 286 */     Job.setProperty("painter", job.getPainter());
/* 287 */     Job.setProperty("upholstery", job.getUpholstery());
/* 288 */     Job.setProperty("maintenance", job.getMaintenance());
/* 289 */     Job.setProperty("electrician", job.getElectrician());
/* 290 */     Job.setProperty("cook", job.getCook());
/* 291 */     Job.setProperty("airConditioning", job.getAirConditioning());
/* 292 */     Job.setProperty("radioTvRepair", job.getRadioTvRepair());
/* 293 */     Job.setProperty("autoBodyRepair", job.getAutoBodyRepair());
/* 294 */     Job.setProperty("welder", job.getWelder());
/* 295 */     Job.setProperty("carpenter", job.getCarpenter());
/* 296 */     Job.setProperty("landscaping", job.getLandscaping());
/* 297 */     Job.setProperty("custodian", job.getCustodian());
/* 298 */     Job.setProperty("warehousing", job.getWarehousing());
/* 299 */     Job.setProperty("sales", job.getSales());
/* 300 */     Job.setProperty("applianceRepair", job.getApplianceRepair());
/* 301 */     Job.setProperty("truckDriver", job.getTruckDriver());
/* 302 */     Job.setProperty("forkLiftOperator", job.getForkLiftOperator());
/* 303 */     Job.setProperty("computerRepair", job.getComputerRepair());
/* 304 */     Job.setProperty("plumber", job.getPlumber());
/* 305 */     Job.setProperty("kitchen", job.getKitchen());
/* 306 */     Job.setProperty("phoneRoom", job.getPhoneRoom());
/* 307 */     Job.setProperty("clothesSorter", job.getClothesSorter());
/* 308 */     Job.setProperty("computerProgramming", job.getComputerProgramming());
/* 309 */     Job.setProperty("otherSkill", job.getOtherSkill());
/* 310 */     Job.setProperty("employer1", job.getEmployer1());
/* 311 */     Job.setProperty("jobTitle1", job.getJobTitle1());
/* 312 */     Job.setProperty("beginDate1", job.getBeginDate1());
/* 313 */     Job.setProperty("endDate1", job.getEndDate1());
/* 314 */     Job.setProperty("employer2", job.getEmployer2());
/* 315 */     Job.setProperty("jobTitle2", job.getJobTitle2());
/* 316 */     Job.setProperty("beginDate2", job.getBeginDate2());
/* 317 */     Job.setProperty("endDate2", job.getEndDate2());
/* 318 */     Job.setProperty("employer3", job.getEmployer3());
/* 319 */     Job.setProperty("jobTitle3", job.getJobTitle3());
/* 320 */     Job.setProperty("beginDate3", job.getBeginDate3());
/* 321 */     Job.setProperty("endDate3", job.getEndDate3());
/* 322 */     Job.setProperty("employer4", job.getEmployer4());
/* 323 */     Job.setProperty("jobTitle4", job.getJobTitle4());
/* 324 */     Job.setProperty("beginDate4", job.getBeginDate4());
/* 325 */     Job.setProperty("endDate4", job.getEndDate4());
/* 326 */     Job.setProperty("contactName1", job.getContactName1());
/* 327 */     Job.setProperty("contactName2", job.getContactName2());
/* 328 */     Job.setProperty("contactName3", job.getContactName3());
/* 329 */     Job.setProperty("contactName4", job.getContactName4());
/* 330 */     Job.setProperty("contactNumber1", job.getContactNumber1());
/* 331 */     Job.setProperty("contactNumber2", job.getContactNumber2());
/* 332 */     Job.setProperty("contactNumber3", job.getContactNumber3());
/* 333 */     Job.setProperty("contactNumber4", job.getContactNumber4());
/*     */ 
/* 335 */     Util.persistEntity(Job);
/*     */   }
/*     */ 
/*     */   private static Entity createPerson(Person person) {
/* 339 */     Entity Person = new Entity("Person");
/* 340 */     Person.setProperty("farmBase", person.getFarmBase());
/* 341 */     Person.setProperty("firstName", person.getFirstName());
/* 342 */     Person.setProperty("lastName", person.getLastName());
/* 343 */     Person.setProperty("middleInitial", person.getMiddleInitial());
/* 344 */     Person.setProperty("suffix", person.getSuffix());
/* 345 */     Person.setProperty("dateOfBirth", person.getDateOfBirth());
/* 346 */     Person.setProperty("ssn", person.getSsnEncrypted());
/* 347 */     Person.setProperty("ssnCard", person.getSsnCard());
/* 348 */     Person.setProperty("age", person.getAge());
/* 349 */     Person.setProperty("height", person.getHeight());
/* 350 */     Person.setProperty("weight", person.getWeight());
/* 351 */     Person.setProperty("ethnicity", person.getEthnicity());
/* 352 */     Person.setProperty("hairColor", person.getHairColor());
/* 353 */     Person.setProperty("eyeColor", person.getEyeColor());
/* 354 */     Person.setProperty("maritalStatus", person.getMaritalStatus());
/* 355 */     Person.setProperty("educationLevel", person.getEducationLevel());
/* 356 */     Person.setProperty("graduateFlag", person.getGraduateFlag());
/* 357 */     Person.setProperty("englishSpeakFlag", person.getEnglishSpeakFlag());
/* 358 */     Person.setProperty("englishReadFlag", person.getEnglishReadFlag());
/* 359 */     Person.setProperty("homeLocation", person.getHomeLocation());
/* 360 */     Person.setProperty("personType", person.getPersonType());
/* 361 */     Person.setProperty("referredBy", person.getReferredBy());
/* 362 */     Person.setProperty("referralPhone", person.getReferralPhone());
/* 363 */     Person.setProperty("emergencyContact", person.getEmergencyContact());
/* 364 */     Person.setProperty("emergencyPhone", person.getEmergencyPhone());
/* 365 */     Person.setProperty("emergencyRelationship", person.getEmergencyRelationship());
/* 366 */     Person.setProperty("creationDate", new Date());
/* 367 */     Person.setProperty("createdBy", person.getCreatedBy());
/* 368 */     Person.setProperty("personStatus", "Pending");
/* 369 */     Person.setProperty("entryDate", "");
/* 370 */     Person.setProperty("exitDate", "");
/* 371 */     Person.setProperty("currentCourse", "");
/* 372 */     Person.setProperty("specialNotes", "");
/* 373 */     Person.setProperty("bedAssignment", "");
/* 374 */     Util.persistEntity(Person);
/*     */ 
/* 376 */     return Person;
/*     */   }
/*     */ 
/*     */   public static Iterable<Entity> search(String firstName, String lastName, String middleInitial, String suffix, String farm, String status)
/*     */   {
/* 393 */     Iterable results = null;
/*     */     try
/*     */     {
/* 396 */       Query query = new Query("Person").addSort("lastName", Query.SortDirection.ASCENDING);
/* 397 */       if ((farm != null) && (!farm.equals("")))
/* 398 */         query.addFilter("farmBase", Query.FilterOperator.EQUAL, farm);
/* 399 */       if ((status != null) && (!status.equals("")))
/* 400 */         query.addFilter("status", Query.FilterOperator.EQUAL, status);
/* 401 */       if ((firstName != null) && (!firstName.equals("")))
/* 402 */         query.addFilter("firstName", Query.FilterOperator.EQUAL, firstName);
/* 403 */       if ((lastName != null) && (!lastName.equals("")))
/* 404 */         query.addFilter("lastName", Query.FilterOperator.EQUAL, lastName);
/* 405 */       if ((middleInitial != null) && (!middleInitial.equals("")))
/* 406 */         query.addFilter("middleInitial", Query.FilterOperator.EQUAL, middleInitial);
/* 407 */       if ((suffix != null) && (!suffix.equals(""))) {
/* 408 */         query.addFilter("suffix", Query.FilterOperator.EQUAL, suffix);
/*     */       }
/* 410 */       query.addFilter("personType", Query.FilterOperator.EQUAL, "Intake");
/*     */ 
/* 412 */       results = datastore.prepare(query).asIterable(FetchOptions.Builder.withLimit(120));
/*     */     }
/*     */     catch (Exception e) {
/* 415 */       logger.log(Level.SEVERE, e.getStackTrace().toString());
/*     */     }
/* 417 */     return results;
/*     */   }
/*     */ 
/*     */   public static Iterable<Entity> getAllIntakes()
/*     */   {
/* 430 */     Iterable entities = Util.listEntities("Intake", null, null);
/*     */ 
/* 432 */     return entities;
/*     */   }
/*     */ 
/*     */   public static Iterable<Entity> getIntake(String IntakeName)
/*     */   {
/* 443 */     Iterable entities = Util.listEntities("Intake", "IntakeName", IntakeName);
/* 444 */     return entities;
/*     */   }
/*     */ 
/*     */   public static Iterable<Entity> getIntakesForVendor(String kind, String vendorName)
/*     */   {
/* 457 */     Key ancestorKey = KeyFactory.createKey("VendorOld", vendorName);
/* 458 */     return Util.listChildren("Intake", ancestorKey);
/*     */   }
/*     */ 
/*     */   public static Entity getSingleIntake(String IntakeNumber)
/*     */   {
/* 467 */     Query q = new Query("Intake");
/* 468 */     q.addFilter("IntakeNumber", Query.FilterOperator.EQUAL, IntakeNumber);
/* 469 */     List results = Util.getDatastoreServiceInstance().prepare(q).asList(FetchOptions.Builder.withDefaults());
/* 470 */     if (!results.isEmpty())
/*     */     {
/* 472 */       return (Entity)results.remove(0);
/*     */     }
/* 474 */     return null;
/*     */   }
/*     */ 
/*     */   protected static boolean doDelete(Entity e, HttpServletRequest req)
/*     */     throws ServletException, IOException
/*     */   {
/* 480 */     ArrayList messages = new ArrayList();
/*     */     try
/*     */     {
/* 483 */       Util.deleteEntity(e.getKey());
/* 484 */       recordAudit("DELETE", e.getProperty("IntakeNumber").toString());
/*     */     } catch (Exception err) {
/* 486 */       String msg = Util.getErrorResponse(err);
/* 487 */       messages.add("Delete Error: " + msg);
/* 488 */       req.setAttribute("messages", messages);
/* 489 */       return false;
/*     */     }
/*     */ 
/* 492 */     return true;
/*     */   }
/*     */ 
/*     */   protected static boolean doDelete(HttpServletRequest req) throws ServletException, IOException {
/* 496 */     String IntakeKey = "Intake";
/* 497 */     ArrayList messages = new ArrayList();
/*     */     try
/*     */     {
/* 501 */       int i = 0;
/* 502 */       int index = Integer.parseInt(req.getParameter("index"));
/* 503 */       Iterable entities = (Iterable)req.getSession().getAttribute("results");
/*     */ 
/* 505 */       //for (Entity e : entities) {
				for(Iterator entity = entities.iterator();entity.hasNext();) {
				  Entity e = (Entity)entity.next();

/* 506 */         if (index == i) {
/* 507 */           Util.deleteEntity(e.getKey());
/*     */         }
/* 509 */         i++;
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 513 */       String msg = Util.getErrorResponse(e);
/* 514 */       messages.add("Delete Error: " + msg);
/* 515 */       req.setAttribute("messages", messages);
/* 516 */       return false;
/*     */     }
/*     */ 
/* 519 */     return true;
/*     */   }
/*     */ 
/*     */   protected static ArrayList doDelete(Entity e) throws ServletException, IOException
/*     */   {
/* 524 */     ArrayList messages = new ArrayList();
/*     */     try
/*     */     {
/* 528 */       Util.deleteEntity(e.getKey());
/* 529 */       messages = null;
/*     */     }
/*     */     catch (Exception err) {
/* 532 */       String msg = Util.getErrorResponse(err);
/* 533 */       messages.add("Delete Error: " + msg);
/*     */     }
/*     */ 
/* 537 */     return messages;
/*     */   }
/*     */ 
/*     */   protected static boolean doEdit(HttpServletRequest req) throws ServletException, IOException {
/* 541 */     String IntakeKey = "Intake";
/* 542 */     ArrayList messages = new ArrayList();
/*     */     try
/*     */     {
/* 546 */       int i = 0;
/* 547 */       int index = Integer.parseInt(req.getParameter("index"));
/* 548 */       Iterable entities = (Iterable)req.getSession().getAttribute("results");
/*     */ 
/* 550 */       //for (Entity e : entities) {
				for(Iterator entity = entities.iterator();entity.hasNext();) {
				  Entity e = (Entity)entity.next();

/* 551 */         if (index == i) {
/* 552 */           Util.deleteEntity(e.getKey());
/* 553 */           String vendorName = (String)e.getProperty("IntakeName");
/* 554 */           String IntakeName = (String)e.getProperty("IntakeName");
/* 555 */           String str1 = (String)e.getProperty("qty1");
/*     */         }
/*     */ 
/* 561 */         i++;
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 565 */       String msg = Util.getErrorResponse(e);
/* 566 */       messages.add("Delete Error: " + msg);
/* 567 */       req.getSession().setAttribute("messages", messages);
/* 568 */       return false;
/*     */     }
/*     */ 
/* 571 */     return true;
/*     */   }
/*     */ 
/*     */   private static void recordAudit(String action, String key)
/*     */   {
/* 594 */     Entity Audit = new Entity("TransactionHistory");
/* 595 */     Audit.setProperty("entity", "Intake");
/* 596 */     Audit.setProperty("action", action);
/* 597 */     Audit.setProperty("key", key);
/* 598 */     Audit.setProperty("datetime", new Date());
/* 599 */     Util.persistEntity(Audit);
/*     */   }
/*     */ 
/*     */   public static int getSize() {
/* 603 */     return size;
/*     */   }
/*     */ 
/*     */   public static void setSize(int size) {
/* 607 */     size = size;
/*     */   }
/*     */   public static int getPages() {
/* 610 */     return pages;
/*     */   }
/*     */ 
/*     */   public static void setPages(int pages) {
/* 614 */     pages = pages;
/*     */   }
/*     */ 
/*     */   public static String getCursor() {
/* 618 */     return cursor;
/*     */   }
/*     */ 
/*     */   public static void setCursor(String cursor) {
/* 622 */     cursor = cursor;
/*     */   }
/*     */ }

/* Location:           C:\development\workspace\faithfarmoss\WEB-INF\classes\
 * Qualified Name:     org.faithfarm.Intake
 * JD-Core Version:    0.6.2
 */