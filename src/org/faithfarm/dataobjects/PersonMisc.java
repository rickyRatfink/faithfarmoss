/*      */ package org.faithfarm.dataobjects;
/*      */ 
/*      */ import java.io.Serializable;
/*      */ import java.util.ArrayList;
/*      */ 
/*      */ public class PersonMisc
/*      */   implements Serializable
/*      */ {
/*      */   private Long id;
/*      */   private String motherLiving;
/*      */   private String fatherLiving;
/*      */   private String brothers;
/*      */   private String sisters;
/*      */   private String children;
/*   14 */   private String veteranStatus = "";
/*   15 */   private String branchOfService = "";
/*   16 */   private String rank = "";
/*   17 */   private String lengthOfService = "";
/*   18 */   private String driversLicenseStatus = "";
/*   19 */   private String driversLicenseNumber = "";
/*   20 */   private String driversLicenseState = "";
/*   21 */   private String driversLicenseExpirationDate = "";
/*   22 */   private String stateIdentificationFlag = "";
/*   23 */   private String stateIdentificationState = "";
/*   24 */   private String stateIdentificationExpirationDate = "";
/*   25 */   private String governmentBenefits = "";
/*   26 */   private String religion = "";
/*   27 */   private String religiousExperience = "";
/*   28 */   private String alcohol = "";
/*   29 */   private String cocaine = "";
/*   30 */   private String marijuana = "";
/*   31 */   private String oxicoden = "";
/*   32 */   private String speed = "";
/*   33 */   private String heroin = "";
/*   34 */   private String xanax = "";
/*   35 */   private String other = "";
/*   36 */   private String alcoholYearsUsed = "";
/*   37 */   private String cocaineYearsUsed = "";
/*   38 */   private String marijuanaYearsUsed = "";
/*   39 */   private String oxicodenYearsUsed = "";
/*   40 */   private String speedYearsUsed = "";
/*   41 */   private String heroinYearsUsed = "";
/*   42 */   private String xanaxYearsUsed = "";
/*   43 */   private String otherYearsUsed = "";
/*   44 */   private String alcoholLastUsed = "";
/*   45 */   private String cocaineLastUsed = "";
/*   46 */   private String marijuanaLastUsed = "";
/*   47 */   private String oxicodenLastUsed = "";
/*   48 */   private String speedLastUsed = "";
/*   49 */   private String heroinLastUsed = "";
/*   50 */   private String xanaxLastUsed = "";
/*   51 */   private String otherLastUsed = "";
/*   52 */   private String otherDrug = "";
/*   53 */   private String sober3Years = "";
/*   54 */   private String sober1Years = "";
/*   55 */   private String usagePattern = "";
/*   56 */   private String quantityPerWeek = "";
/*   57 */   private String quantity2Years = "";
/*   58 */   private String usageLosses = "";
/*   59 */   private String abusePhysicalEffects = "";
/*   60 */   private String spouseAddicted = "";
/*   61 */   private String familyAddicted = "";
/*   62 */   private String attendAA = "";
/*   63 */   private String attendNA = "";
/*   64 */   private String yearsAttended = "";
/*   65 */   private String previousFaithFarm = "";
/*   66 */   private String faithFarmYear = "";
/*   67 */   private String currentHealth = "";
/*   68 */   private String currentMedicationsFlag = "";
/*   69 */   private String currentMedicationsDetails = "";
/*   70 */   private String needMedicationFlag = "";
/*   71 */   private String medicationSuppyFlag = "";
/*   72 */   private String refillDetails = "";
/*   73 */   private String doctorsAppointment = "";
/*   74 */   private String medicalQuestion1Flag = "";
/*   75 */   private String medicalQuestion2Flag = "";
/*   76 */   private String medicalQuestion3Flag = "";
/*   77 */   private String medicalQuestion4Flag = "";
/*   78 */   private String medicalQuestion5Flag = "";
/*   79 */   private String medicalQuestion1Details = "";
/*   80 */   private String medicalQuestion2Details = "";
/*   81 */   private String medicalQuestion3Details = "";
/*   82 */   private String medicalQuestion4Details = "";
/*   83 */   private String medicalQuestion5Details = "";
/*   84 */   private String glassesContactLenses = "";
/*   85 */   private String industrialInjuryFlag = "";
/*   86 */   private String industrialInjuryDate = "";
/*   87 */   private String industrialInjuryDetails = "";
/*   88 */   private String industrialInjuryWhere = "";
/*   89 */   private String industrialInjuryEmployer = "";
/*   90 */   private String industrialInjuryClaimStatus = "";
/*   91 */   private String disabilityFlag = "";
/*   92 */   private String disabilityDetails = "";
/*   93 */   private String examinationDate = "";
/*   94 */   private String physician = "";
/*   95 */   private String address = "";
/*   96 */   private ArrayList medicationCondition = new ArrayList();
/*   97 */   private String herniaSide = "";
/*   98 */   private String herniaOperationFlag = "";
/*   99 */   private String medicalConditionDetails = "";
/*  100 */   private String legalQuestion1 = "";
/*  101 */   private String legalQuestion1Date = "";
/*  102 */   private String legalQuestion1Details = "";
/*  103 */   private String legalQuestion2 = "";
/*  104 */   private String legalQuestion2Date = "";
/*  105 */   private String legalQuestion2Details = "";
/*  106 */   private String legalQuestion3 = "";
/*  107 */   private String legalQuestion3Date = "";
/*  108 */   private String legalQuestion3Quantity = "";
/*  109 */   private String legalQuestion3Details = "";
/*  110 */   private String legalQuestion4 = "";
/*  111 */   private String legalQuestion4Date = "";
/*  112 */   private String legalQuestion4Details = "";
/*  113 */   private String legalQuestion5 = "";
/*  114 */   private String legalQuestion5Date = "";
/*  115 */   private String legalQuestion5Details = "";
/*  116 */   private String probationFlag = "";
/*  117 */   private String probationCounty = "";
/*  118 */   private String probationState = "";
/*  119 */   private String probationOfficer = "";
/*  120 */   private String probationOfficerPhone = "";
/*  121 */   private ArrayList jobSkillFlag = new ArrayList();
/*  122 */   private String workExperienceDetails = "";
/*  123 */   private String employer1 = "";
/*  124 */   private String jobTitle1 = "";
/*  125 */   private String jobStartDate1 = "";
/*  126 */   private String jobEndDate1 = "";
/*  127 */   private String employer2 = "";
/*  128 */   private String jobTitle2 = "";
/*  129 */   private String jobStartDate2 = "";
/*  130 */   private String jobEndDate2 = "";
/*  131 */   private String employer3 = "";
/*  132 */   private String jobTitle3 = "";
/*  133 */   private String jobStartDate3 = "";
/*  134 */   private String jobEndDate3 = "";
/*      */ 
/*  136 */   private String MedicalOpt1 = "";
/*  137 */   private String MedicalOpt2 = "";
/*  138 */   private String MedicalOpt3 = "";
/*  139 */   private String MedicalOpt4 = "";
/*  140 */   private String MedicalOpt5 = "";
/*  141 */   private String MedicalOpt6 = "";
/*  142 */   private String MedicalOpt7 = "";
/*  143 */   private String MedicalOpt8 = "";
/*  144 */   private String MedicalOpt9 = "";
/*  145 */   private String MedicalOpt10 = "";
/*  146 */   private String MedicalOpt11 = "";
/*  147 */   private String MedicalOpt12 = "";
/*  148 */   private String MedicalOpt13 = "";
/*  149 */   private String MedicalOpt14 = "";
/*  150 */   private String MedicalOpt15 = "";
/*  151 */   private String MedicalOpt16 = "";
/*  152 */   private String MedicalOpt17 = "";
/*  153 */   private String MedicalOpt18 = "";
/*  154 */   private String MedicalOpt19 = "";
/*  155 */   private String MedicalOpt20 = "";
/*  156 */   private String MedicalOpt21 = "";
/*  157 */   private String MedicalOpt22 = "";
/*  158 */   private String MedicalOpt23 = "";
/*  159 */   private String MedicalOpt24 = "";
/*  160 */   private String MedicalOpt25 = "";
/*  161 */   private String libraryCard = "";
/*  162 */   private String birthCertFlag = "";
/*  163 */   private String birthCertCounty = "";
/*  164 */   private String birthCertState = "";
/*  165 */   private String govtHealthCoverage = "";
/*  166 */   private String privateHealthCoverage = "";
/*  167 */   private String selectiveService = "";
/*  168 */   private String pendingCourtDates = "";
/*  169 */   private String childSupport = "";
/*  170 */   private String restitution = "";
/*      */ 
/*      */   public String getStateIdentificationState()
/*      */   {
/*  174 */     return this.stateIdentificationState;
/*      */   }
/*      */   public void setStateIdentificationState(String stateIdentificationState) {
/*  177 */     this.stateIdentificationState = stateIdentificationState;
/*      */   }
/*      */   public String getStateIdentificationExpirationDate() {
/*  180 */     return this.stateIdentificationExpirationDate;
/*      */   }
/*      */   public void setStateIdentificationExpirationDate(String stateIdentificationExpirationDate) {
/*  183 */     this.stateIdentificationExpirationDate = stateIdentificationExpirationDate;
/*      */   }
/*      */   public String getRestitution() {
/*  186 */     return this.restitution;
/*      */   }
/*      */   public void setRestitution(String restitution) {
/*  189 */     this.restitution = restitution;
/*      */   }
/*      */   public String getChildSupport() {
/*  192 */     return this.childSupport;
/*      */   }
/*      */   public void setChildSupport(String childSupport) {
/*  195 */     this.childSupport = childSupport;
/*      */   }
/*      */   public String getPendingCourtDates() {
/*  198 */     return this.pendingCourtDates;
/*      */   }
/*      */   public void setPendingCourtDates(String pendingCourtDates) {
/*  201 */     this.pendingCourtDates = pendingCourtDates;
/*      */   }
/*      */   public String getSelectiveService() {
/*  204 */     return this.selectiveService;
/*      */   }
/*      */   public void setSelectiveService(String selectiveService) {
/*  207 */     this.selectiveService = selectiveService;
/*      */   }
/*      */   public String getPrivateHealthCoverage() {
/*  210 */     return this.privateHealthCoverage;
/*      */   }
/*      */   public void setPrivateHealthCoverage(String privateHealthCoverage) {
/*  213 */     this.privateHealthCoverage = privateHealthCoverage;
/*      */   }
/*      */   public String getGovtHealthCoverage() {
/*  216 */     return this.govtHealthCoverage;
/*      */   }
/*      */   public void setGovtHealthCoverage(String govtHealthCoverage) {
/*  219 */     this.govtHealthCoverage = govtHealthCoverage;
/*      */   }
/*      */   public String getBirthCertFlag() {
/*  222 */     return this.birthCertFlag;
/*      */   }
/*      */   public void setBirthCertFlag(String birthCertFlag) {
/*  225 */     this.birthCertFlag = birthCertFlag;
/*      */   }
/*      */   public String getBirthCertCounty() {
/*  228 */     return this.birthCertCounty;
/*      */   }
/*      */   public void setBirthCertCounty(String birthCertCounty) {
/*  231 */     this.birthCertCounty = birthCertCounty;
/*      */   }
/*      */   public String getBirthCertState() {
/*  234 */     return this.birthCertState;
/*      */   }
/*      */   public void setBirthCertState(String birthCertState) {
/*  237 */     this.birthCertState = birthCertState;
/*      */   }
/*      */   public String getLibraryCard() {
/*  240 */     return this.libraryCard;
/*      */   }
/*      */   public void setLibraryCard(String libraryCard) {
/*  243 */     this.libraryCard = libraryCard;
/*      */   }
/*      */   public String getStateIdentificationFlag() {
/*  246 */     return this.stateIdentificationFlag;
/*      */   }
/*      */   public void setStateIdentificationFlag(String stateIdentificationFlag) {
/*  249 */     this.stateIdentificationFlag = stateIdentificationFlag;
/*      */   }
/*      */   public String getDriversLicenseExpirationDate() {
/*  252 */     return this.driversLicenseExpirationDate;
/*      */   }
/*      */   public void setDriversLicenseExpirationDate(String driversLicenseExpirationDate) {
/*  255 */     this.driversLicenseExpirationDate = driversLicenseExpirationDate;
/*      */   }
/*      */ 
/*      */   public String getMedicalOpt1() {
/*  259 */     return this.MedicalOpt1;
/*      */   }
/*      */   public void setMedicalOpt1(String medicalOpt1) {
/*  262 */     this.MedicalOpt1 = medicalOpt1;
/*      */   }
/*      */   public String getMedicalOpt2() {
/*  265 */     return this.MedicalOpt2;
/*      */   }
/*      */   public void setMedicalOpt2(String medicalOpt2) {
/*  268 */     this.MedicalOpt2 = medicalOpt2;
/*      */   }
/*      */   public String getMedicalOpt3() {
/*  271 */     return this.MedicalOpt3;
/*      */   }
/*      */   public void setMedicalOpt3(String medicalOpt3) {
/*  274 */     this.MedicalOpt3 = medicalOpt3;
/*      */   }
/*      */   public String getMedicalOpt4() {
/*  277 */     return this.MedicalOpt4;
/*      */   }
/*      */   public void setMedicalOpt4(String medicalOpt4) {
/*  280 */     this.MedicalOpt4 = medicalOpt4;
/*      */   }
/*      */   public String getMedicalOpt5() {
/*  283 */     return this.MedicalOpt5;
/*      */   }
/*      */   public void setMedicalOpt5(String medicalOpt5) {
/*  286 */     this.MedicalOpt5 = medicalOpt5;
/*      */   }
/*      */   public String getMedicalOpt6() {
/*  289 */     return this.MedicalOpt6;
/*      */   }
/*      */   public void setMedicalOpt6(String medicalOpt6) {
/*  292 */     this.MedicalOpt6 = medicalOpt6;
/*      */   }
/*      */   public String getMedicalOpt7() {
/*  295 */     return this.MedicalOpt7;
/*      */   }
/*      */   public void setMedicalOpt7(String medicalOpt7) {
/*  298 */     this.MedicalOpt7 = medicalOpt7;
/*      */   }
/*      */   public String getMedicalOpt8() {
/*  301 */     return this.MedicalOpt8;
/*      */   }
/*      */   public void setMedicalOpt8(String medicalOpt8) {
/*  304 */     this.MedicalOpt8 = medicalOpt8;
/*      */   }
/*      */   public String getMedicalOpt9() {
/*  307 */     return this.MedicalOpt9;
/*      */   }
/*      */   public void setMedicalOpt9(String medicalOpt9) {
/*  310 */     this.MedicalOpt9 = medicalOpt9;
/*      */   }
/*      */   public String getMedicalOpt10() {
/*  313 */     return this.MedicalOpt10;
/*      */   }
/*      */   public void setMedicalOpt10(String medicalOpt10) {
/*  316 */     this.MedicalOpt10 = medicalOpt10;
/*      */   }
/*      */   public String getMedicalOpt11() {
/*  319 */     return this.MedicalOpt11;
/*      */   }
/*      */   public void setMedicalOpt11(String medicalOpt11) {
/*  322 */     this.MedicalOpt11 = medicalOpt11;
/*      */   }
/*      */   public String getMedicalOpt12() {
/*  325 */     return this.MedicalOpt12;
/*      */   }
/*      */   public void setMedicalOpt12(String medicalOpt12) {
/*  328 */     this.MedicalOpt12 = medicalOpt12;
/*      */   }
/*      */   public String getMedicalOpt13() {
/*  331 */     return this.MedicalOpt13;
/*      */   }
/*      */   public void setMedicalOpt13(String medicalOpt13) {
/*  334 */     this.MedicalOpt13 = medicalOpt13;
/*      */   }
/*      */   public String getMedicalOpt14() {
/*  337 */     return this.MedicalOpt14;
/*      */   }
/*      */   public void setMedicalOpt14(String medicalOpt14) {
/*  340 */     this.MedicalOpt14 = medicalOpt14;
/*      */   }
/*      */   public String getMedicalOpt15() {
/*  343 */     return this.MedicalOpt15;
/*      */   }
/*      */   public void setMedicalOpt15(String medicalOpt15) {
/*  346 */     this.MedicalOpt15 = medicalOpt15;
/*      */   }
/*      */   public String getMedicalOpt16() {
/*  349 */     return this.MedicalOpt16;
/*      */   }
/*      */   public void setMedicalOpt16(String medicalOpt16) {
/*  352 */     this.MedicalOpt16 = medicalOpt16;
/*      */   }
/*      */   public String getMedicalOpt17() {
/*  355 */     return this.MedicalOpt17;
/*      */   }
/*      */   public void setMedicalOpt17(String medicalOpt17) {
/*  358 */     this.MedicalOpt17 = medicalOpt17;
/*      */   }
/*      */   public String getMedicalOpt18() {
/*  361 */     return this.MedicalOpt18;
/*      */   }
/*      */   public void setMedicalOpt18(String medicalOpt18) {
/*  364 */     this.MedicalOpt18 = medicalOpt18;
/*      */   }
/*      */   public String getMedicalOpt19() {
/*  367 */     return this.MedicalOpt19;
/*      */   }
/*      */   public void setMedicalOpt19(String medicalOpt19) {
/*  370 */     this.MedicalOpt19 = medicalOpt19;
/*      */   }
/*      */   public String getMedicalOpt20() {
/*  373 */     return this.MedicalOpt20;
/*      */   }
/*      */   public void setMedicalOpt20(String medicalOpt20) {
/*  376 */     this.MedicalOpt20 = medicalOpt20;
/*      */   }
/*      */   public String getMedicalOpt21() {
/*  379 */     return this.MedicalOpt21;
/*      */   }
/*      */   public void setMedicalOpt21(String medicalOpt21) {
/*  382 */     this.MedicalOpt21 = medicalOpt21;
/*      */   }
/*      */   public String getMedicalOpt22() {
/*  385 */     return this.MedicalOpt22;
/*      */   }
/*      */   public void setMedicalOpt22(String medicalOpt22) {
/*  388 */     this.MedicalOpt22 = medicalOpt22;
/*      */   }
/*      */   public String getMedicalOpt23() {
/*  391 */     return this.MedicalOpt23;
/*      */   }
/*      */   public void setMedicalOpt23(String medicalOpt23) {
/*  394 */     this.MedicalOpt23 = medicalOpt23;
/*      */   }
/*      */   public String getMedicalOpt24() {
/*  397 */     return this.MedicalOpt24;
/*      */   }
/*      */   public void setMedicalOpt24(String medicalOpt24) {
/*  400 */     this.MedicalOpt24 = medicalOpt24;
/*      */   }
/*      */ 
/*      */   public String getMedicalOpt25() {
/*  404 */     return this.MedicalOpt25;
/*      */   }
/*      */   public void setMedicalOpt25(String medicalOpt25) {
/*  407 */     this.MedicalOpt25 = medicalOpt25;
/*      */   }
/*      */   public String getMotherLiving() {
/*  410 */     return this.motherLiving;
/*      */   }
/*      */   public String getXanax() {
/*  413 */     return this.xanax;
/*      */   }
/*      */   public void setXanax(String xanax) {
/*  416 */     this.xanax = xanax;
/*      */   }
/*      */   public void setMotherLiving(String motherLiving) {
/*  419 */     this.motherLiving = motherLiving;
/*      */   }
/*      */   public String getFatherLiving() {
/*  422 */     return this.fatherLiving;
/*      */   }
/*      */   public void setFatherLiving(String fatherLiving) {
/*  425 */     this.fatherLiving = fatherLiving;
/*      */   }
/*      */   public String getBrothers() {
/*  428 */     return this.brothers;
/*      */   }
/*      */   public void setBrothers(String brothers) {
/*  431 */     this.brothers = brothers;
/*      */   }
/*      */   public String getSisters() {
/*  434 */     return this.sisters;
/*      */   }
/*      */   public void setSisters(String sisters) {
/*  437 */     this.sisters = sisters;
/*      */   }
/*      */   public String getChildren() {
/*  440 */     return this.children;
/*      */   }
/*      */   public void setChildren(String children) {
/*  443 */     this.children = children;
/*      */   }
/*      */   public String getVeteranStatus() {
/*  446 */     return this.veteranStatus;
/*      */   }
/*      */   public void setVeteranStatus(String veteranStatus) {
/*  449 */     this.veteranStatus = veteranStatus;
/*      */   }
/*      */   public String getBranchOfService() {
/*  452 */     return this.branchOfService;
/*      */   }
/*      */   public void setBranchOfService(String branchOfService) {
/*  455 */     this.branchOfService = branchOfService;
/*      */   }
/*      */   public String getRank() {
/*  458 */     return this.rank;
/*      */   }
/*      */   public void setRank(String rank) {
/*  461 */     this.rank = rank;
/*      */   }
/*      */   public String getLengthOfService() {
/*  464 */     return this.lengthOfService;
/*      */   }
/*      */   public void setLengthOfService(String lengthOfService) {
/*  467 */     this.lengthOfService = lengthOfService;
/*      */   }
/*      */   public String getDriversLicenseStatus() {
/*  470 */     return this.driversLicenseStatus;
/*      */   }
/*      */   public void setDriversLicenseStatus(String driversLicenseStatus) {
/*  473 */     this.driversLicenseStatus = driversLicenseStatus;
/*      */   }
/*      */   public String getDriversLicenseNumber() {
/*  476 */     return this.driversLicenseNumber;
/*      */   }
/*      */   public void setDriversLicenseNumber(String driversLicenseNumber) {
/*  479 */     this.driversLicenseNumber = driversLicenseNumber;
/*      */   }
/*      */   public String getDriversLicenseState() {
/*  482 */     return this.driversLicenseState;
/*      */   }
/*      */   public void setDriversLicenseState(String driversLicenseState) {
/*  485 */     this.driversLicenseState = driversLicenseState;
/*      */   }
/*      */   public String getGovernmentBenefits() {
/*  488 */     return this.governmentBenefits;
/*      */   }
/*      */   public void setGovernmentBenefits(String governmentBenefits) {
/*  491 */     this.governmentBenefits = governmentBenefits;
/*      */   }
/*      */   public String getReligion() {
/*  494 */     return this.religion;
/*      */   }
/*      */   public void setReligion(String religion) {
/*  497 */     this.religion = religion;
/*      */   }
/*      */   public String getReligiousExperience() {
/*  500 */     return this.religiousExperience;
/*      */   }
/*      */   public void setReligiousExperience(String religiousExperience) {
/*  503 */     this.religiousExperience = religiousExperience;
/*      */   }
/*      */   public String getAlcohol() {
/*  506 */     return this.alcohol;
/*      */   }
/*      */   public void setAlcohol(String alcohol) {
/*  509 */     this.alcohol = alcohol;
/*      */   }
/*      */   public String getCocaine() {
/*  512 */     return this.cocaine;
/*      */   }
/*      */   public void setCocaine(String cocaine) {
/*  515 */     this.cocaine = cocaine;
/*      */   }
/*      */   public String getMarijuana() {
/*  518 */     return this.marijuana;
/*      */   }
/*      */   public void setMarijuana(String marijuana) {
/*  521 */     this.marijuana = marijuana;
/*      */   }
/*      */   public String getOxicoden() {
/*  524 */     return this.oxicoden;
/*      */   }
/*      */   public void setOxicoden(String oxicoden) {
/*  527 */     this.oxicoden = oxicoden;
/*      */   }
/*      */   public String getSpeed() {
/*  530 */     return this.speed;
/*      */   }
/*      */   public void setSpeed(String speed) {
/*  533 */     this.speed = speed;
/*      */   }
/*      */   public String getHeroin() {
/*  536 */     return this.heroin;
/*      */   }
/*      */   public void setHeroin(String heroin) {
/*  539 */     this.heroin = heroin;
/*      */   }
/*      */   public String getOther() {
/*  542 */     return this.other;
/*      */   }
/*      */   public void setOther(String other) {
/*  545 */     this.other = other;
/*      */   }
/*      */   public String getAlcoholYearsUsed() {
/*  548 */     return this.alcoholYearsUsed;
/*      */   }
/*      */   public void setAlcoholYearsUsed(String alcoholYearsUsed) {
/*  551 */     this.alcoholYearsUsed = alcoholYearsUsed;
/*      */   }
/*      */   public String getCocaineYearsUsed() {
/*  554 */     return this.cocaineYearsUsed;
/*      */   }
/*      */   public void setCocaineYearsUsed(String cocaineYearsUsed) {
/*  557 */     this.cocaineYearsUsed = cocaineYearsUsed;
/*      */   }
/*      */   public String getMarijuanaYearsUsed() {
/*  560 */     return this.marijuanaYearsUsed;
/*      */   }
/*      */   public void setMarijuanaYearsUsed(String marijuanaYearsUsed) {
/*  563 */     this.marijuanaYearsUsed = marijuanaYearsUsed;
/*      */   }
/*      */   public String getOxicodenYearsUsed() {
/*  566 */     return this.oxicodenYearsUsed;
/*      */   }
/*      */   public void setOxicodenYearsUsed(String oxicodenYearsUsed) {
/*  569 */     this.oxicodenYearsUsed = oxicodenYearsUsed;
/*      */   }
/*      */   public String getSpeedYearsUsed() {
/*  572 */     return this.speedYearsUsed;
/*      */   }
/*      */   public void setSpeedYearsUsed(String speedYearsUsed) {
/*  575 */     this.speedYearsUsed = speedYearsUsed;
/*      */   }
/*      */   public String getHeroinYearsUsed() {
/*  578 */     return this.heroinYearsUsed;
/*      */   }
/*      */   public void setHeroinYearsUsed(String heroinYearsUsed) {
/*  581 */     this.heroinYearsUsed = heroinYearsUsed;
/*      */   }
/*      */   public String getXanaxYearsUsed() {
/*  584 */     return this.xanaxYearsUsed;
/*      */   }
/*      */   public void setXanaxYearsUsed(String xanaxYearsUsed) {
/*  587 */     this.xanaxYearsUsed = xanaxYearsUsed;
/*      */   }
/*      */   public String getOtherYearsUsed() {
/*  590 */     return this.otherYearsUsed;
/*      */   }
/*      */   public void setOtherYearsUsed(String otherYearsUsed) {
/*  593 */     this.otherYearsUsed = otherYearsUsed;
/*      */   }
/*      */   public String getAlcoholLastUsed() {
/*  596 */     return this.alcoholLastUsed;
/*      */   }
/*      */   public void setAlcoholLastUsed(String alcoholLastUsed) {
/*  599 */     this.alcoholLastUsed = alcoholLastUsed;
/*      */   }
/*      */   public String getCocaineLastUsed() {
/*  602 */     return this.cocaineLastUsed;
/*      */   }
/*      */   public void setCocaineLastUsed(String cocaineLastUsed) {
/*  605 */     this.cocaineLastUsed = cocaineLastUsed;
/*      */   }
/*      */   public String getMarijuanaLastUsed() {
/*  608 */     return this.marijuanaLastUsed;
/*      */   }
/*      */   public void setMarijuanaLastUsed(String marijuanaLastUsed) {
/*  611 */     this.marijuanaLastUsed = marijuanaLastUsed;
/*      */   }
/*      */   public String getOxicodenLastUsed() {
/*  614 */     return this.oxicodenLastUsed;
/*      */   }
/*      */   public void setOxicodenLastUsed(String oxicodenLastUsed) {
/*  617 */     this.oxicodenLastUsed = oxicodenLastUsed;
/*      */   }
/*      */   public String getSpeedLastUsed() {
/*  620 */     return this.speedLastUsed;
/*      */   }
/*      */   public void setSpeedLastUsed(String speedLastUsed) {
/*  623 */     this.speedLastUsed = speedLastUsed;
/*      */   }
/*      */   public String getHeroinLastUsed() {
/*  626 */     return this.heroinLastUsed;
/*      */   }
/*      */   public void setHeroinLastUsed(String heroinLastUsed) {
/*  629 */     this.heroinLastUsed = heroinLastUsed;
/*      */   }
/*      */   public String getXanaxLastUsed() {
/*  632 */     return this.xanaxLastUsed;
/*      */   }
/*      */   public void setXanaxLastUsed(String xanaxLastUsed) {
/*  635 */     this.xanaxLastUsed = xanaxLastUsed;
/*      */   }
/*      */   public String getOtherLastUsed() {
/*  638 */     return this.otherLastUsed;
/*      */   }
/*      */   public void setOtherLastUsed(String otherLastUsed) {
/*  641 */     this.otherLastUsed = otherLastUsed;
/*      */   }
/*      */   public String getOtherDrug() {
/*  644 */     return this.otherDrug;
/*      */   }
/*      */   public void setOtherDrug(String otherDrug) {
/*  647 */     this.otherDrug = otherDrug;
/*      */   }
/*      */   public String getSober3Years() {
/*  650 */     return this.sober3Years;
/*      */   }
/*      */   public void setSober3Years(String sober3Years) {
/*  653 */     this.sober3Years = sober3Years;
/*      */   }
/*      */   public String getSober1Years() {
/*  656 */     return this.sober1Years;
/*      */   }
/*      */   public void setSober1Years(String sober1Years) {
/*  659 */     this.sober1Years = sober1Years;
/*      */   }
/*      */   public String getUsagePattern() {
/*  662 */     return this.usagePattern;
/*      */   }
/*      */   public void setUsagePattern(String usagePattern) {
/*  665 */     this.usagePattern = usagePattern;
/*      */   }
/*      */   public String getQuantityPerWeek() {
/*  668 */     return this.quantityPerWeek;
/*      */   }
/*      */   public void setQuantityPerWeek(String quantityPerWeek) {
/*  671 */     this.quantityPerWeek = quantityPerWeek;
/*      */   }
/*      */   public String getQuantity2Years() {
/*  674 */     return this.quantity2Years;
/*      */   }
/*      */   public void setQuantity2Years(String quantity2Years) {
/*  677 */     this.quantity2Years = quantity2Years;
/*      */   }
/*      */   public String getUsageLosses() {
/*  680 */     return this.usageLosses;
/*      */   }
/*      */   public void setUsageLosses(String usageLosses) {
/*  683 */     this.usageLosses = usageLosses;
/*      */   }
/*      */   public String getAbusePhysicalEffects() {
/*  686 */     return this.abusePhysicalEffects;
/*      */   }
/*      */   public void setAbusePhysicalEffects(String abusePhysicalEffects) {
/*  689 */     this.abusePhysicalEffects = abusePhysicalEffects;
/*      */   }
/*      */   public String getSpouseAddicted() {
/*  692 */     return this.spouseAddicted;
/*      */   }
/*      */   public void setSpouseAddicted(String spouseAddicted) {
/*  695 */     this.spouseAddicted = spouseAddicted;
/*      */   }
/*      */   public String getFamilyAddicted() {
/*  698 */     return this.familyAddicted;
/*      */   }
/*      */   public void setFamilyAddicted(String familyAddicted) {
/*  701 */     this.familyAddicted = familyAddicted;
/*      */   }
/*      */   public String getAttendAA() {
/*  704 */     return this.attendAA;
/*      */   }
/*      */   public void setAttendAA(String attendAA) {
/*  707 */     this.attendAA = attendAA;
/*      */   }
/*      */   public String getAttendNA() {
/*  710 */     return this.attendNA;
/*      */   }
/*      */   public void setAttendNA(String attendNA) {
/*  713 */     this.attendNA = attendNA;
/*      */   }
/*      */   public String getYearsAttended() {
/*  716 */     return this.yearsAttended;
/*      */   }
/*      */   public void setYearsAttended(String yearsAttended) {
/*  719 */     this.yearsAttended = yearsAttended;
/*      */   }
/*      */   public String getPreviousFaithFarm() {
/*  722 */     return this.previousFaithFarm;
/*      */   }
/*      */   public void setPreviousFaithFarm(String previousFaithFarm) {
/*  725 */     this.previousFaithFarm = previousFaithFarm;
/*      */   }
/*      */   public String getFaithFarmYear() {
/*  728 */     return this.faithFarmYear;
/*      */   }
/*      */   public void setFaithFarmYear(String faithFarmYear) {
/*  731 */     this.faithFarmYear = faithFarmYear;
/*      */   }
/*      */   public String getCurrentHealth() {
/*  734 */     return this.currentHealth;
/*      */   }
/*      */   public void setCurrentHealth(String currentHealth) {
/*  737 */     this.currentHealth = currentHealth;
/*      */   }
/*      */   public String getCurrentMedicationsFlag() {
/*  740 */     return this.currentMedicationsFlag;
/*      */   }
/*      */   public void setCurrentMedicationsFlag(String currentMedicationsFlag) {
/*  743 */     this.currentMedicationsFlag = currentMedicationsFlag;
/*      */   }
/*      */   public String getCurrentMedicationsDetails() {
/*  746 */     return this.currentMedicationsDetails;
/*      */   }
/*      */   public void setCurrentMedicationsDetails(String currentMedicationsDetails) {
/*  749 */     this.currentMedicationsDetails = currentMedicationsDetails;
/*      */   }
/*      */   public String getNeedMedicationFlag() {
/*  752 */     return this.needMedicationFlag;
/*      */   }
/*      */   public void setNeedMedicationFlag(String needMedicationFlag) {
/*  755 */     this.needMedicationFlag = needMedicationFlag;
/*      */   }
/*      */   public String getMedicationSuppyFlag() {
/*  758 */     return this.medicationSuppyFlag;
/*      */   }
/*      */   public void setMedicationSuppyFlag(String medicationSuppyFlag) {
/*  761 */     this.medicationSuppyFlag = medicationSuppyFlag;
/*      */   }
/*      */   public String getRefillDetails() {
/*  764 */     return this.refillDetails;
/*      */   }
/*      */   public void setRefillDetails(String refillDetails) {
/*  767 */     this.refillDetails = refillDetails;
/*      */   }
/*      */   public String getDoctorsAppointment() {
/*  770 */     return this.doctorsAppointment;
/*      */   }
/*      */   public void setDoctorsAppointment(String doctorsAppointment) {
/*  773 */     this.doctorsAppointment = doctorsAppointment;
/*      */   }
/*      */   public String getMedicalQuestion1Flag() {
/*  776 */     return this.medicalQuestion1Flag;
/*      */   }
/*      */   public void setMedicalQuestion1Flag(String medicalQuestion1Flag) {
/*  779 */     this.medicalQuestion1Flag = medicalQuestion1Flag;
/*      */   }
/*      */   public String getMedicalQuestion2Flag() {
/*  782 */     return this.medicalQuestion2Flag;
/*      */   }
/*      */   public void setMedicalQuestion2Flag(String medicalQuestion2Flag) {
/*  785 */     this.medicalQuestion2Flag = medicalQuestion2Flag;
/*      */   }
/*      */   public String getMedicalQuestion3Flag() {
/*  788 */     return this.medicalQuestion3Flag;
/*      */   }
/*      */   public void setMedicalQuestion3Flag(String medicalQuestion3Flag) {
/*  791 */     this.medicalQuestion3Flag = medicalQuestion3Flag;
/*      */   }
/*      */   public String getMedicalQuestion4Flag() {
/*  794 */     return this.medicalQuestion4Flag;
/*      */   }
/*      */   public void setMedicalQuestion4Flag(String medicalQuestion4Flag) {
/*  797 */     this.medicalQuestion4Flag = medicalQuestion4Flag;
/*      */   }
/*      */   public String getMedicalQuestion5Flag() {
/*  800 */     return this.medicalQuestion5Flag;
/*      */   }
/*      */   public void setMedicalQuestion5Flag(String medicalQuestion5Flag) {
/*  803 */     this.medicalQuestion5Flag = medicalQuestion5Flag;
/*      */   }
/*      */   public String getMedicalQuestion1Details() {
/*  806 */     return this.medicalQuestion1Details;
/*      */   }
/*      */   public void setMedicalQuestion1Details(String medicalQuestion1Details) {
/*  809 */     this.medicalQuestion1Details = medicalQuestion1Details;
/*      */   }
/*      */   public String getMedicalQuestion2Details() {
/*  812 */     return this.medicalQuestion2Details;
/*      */   }
/*      */   public void setMedicalQuestion2Details(String medicalQuestion2Details) {
/*  815 */     this.medicalQuestion2Details = medicalQuestion2Details;
/*      */   }
/*      */   public String getMedicalQuestion3Details() {
/*  818 */     return this.medicalQuestion3Details;
/*      */   }
/*      */   public void setMedicalQuestion3Details(String medicalQuestion3Details) {
/*  821 */     this.medicalQuestion3Details = medicalQuestion3Details;
/*      */   }
/*      */   public String getMedicalQuestion4Details() {
/*  824 */     return this.medicalQuestion4Details;
/*      */   }
/*      */   public void setMedicalQuestion4Details(String medicalQuestion4Details) {
/*  827 */     this.medicalQuestion4Details = medicalQuestion4Details;
/*      */   }
/*      */   public String getMedicalQuestion5Details() {
/*  830 */     return this.medicalQuestion5Details;
/*      */   }
/*      */   public void setMedicalQuestion5Details(String medicalQuestion5Details) {
/*  833 */     this.medicalQuestion5Details = medicalQuestion5Details;
/*      */   }
/*      */   public String getGlassesContactLenses() {
/*  836 */     return this.glassesContactLenses;
/*      */   }
/*      */   public void setGlassesContactLenses(String glassesContactLenses) {
/*  839 */     this.glassesContactLenses = glassesContactLenses;
/*      */   }
/*      */   public String getIndustrialInjuryFlag() {
/*  842 */     return this.industrialInjuryFlag;
/*      */   }
/*      */   public void setIndustrialInjuryFlag(String industrialInjuryFlag) {
/*  845 */     this.industrialInjuryFlag = industrialInjuryFlag;
/*      */   }
/*      */   public String getIndustrialInjuryDate() {
/*  848 */     return this.industrialInjuryDate;
/*      */   }
/*      */   public void setIndustrialInjuryDate(String industrialInjuryDate) {
/*  851 */     this.industrialInjuryDate = industrialInjuryDate;
/*      */   }
/*      */   public String getIndustrialInjuryDetails() {
/*  854 */     return this.industrialInjuryDetails;
/*      */   }
/*      */   public void setIndustrialInjuryDetails(String industrialInjuryDetails) {
/*  857 */     this.industrialInjuryDetails = industrialInjuryDetails;
/*      */   }
/*      */   public String getIndustrialInjuryWhere() {
/*  860 */     return this.industrialInjuryWhere;
/*      */   }
/*      */   public void setIndustrialInjuryWhere(String industrialInjuryWhere) {
/*  863 */     this.industrialInjuryWhere = industrialInjuryWhere;
/*      */   }
/*      */   public String getIndustrialInjuryEmployer() {
/*  866 */     return this.industrialInjuryEmployer;
/*      */   }
/*      */   public void setIndustrialInjuryEmployer(String industrialInjuryEmployer) {
/*  869 */     this.industrialInjuryEmployer = industrialInjuryEmployer;
/*      */   }
/*      */   public String getIndustrialInjuryClaimStatus() {
/*  872 */     return this.industrialInjuryClaimStatus;
/*      */   }
/*      */   public void setIndustrialInjuryClaimStatus(String industrialInjuryClaimStatus) {
/*  875 */     this.industrialInjuryClaimStatus = industrialInjuryClaimStatus;
/*      */   }
/*      */   public String getDisabilityFlag() {
/*  878 */     return this.disabilityFlag;
/*      */   }
/*      */   public void setDisabilityFlag(String disabilityFlag) {
/*  881 */     this.disabilityFlag = disabilityFlag;
/*      */   }
/*      */   public String getDisabilityDetails() {
/*  884 */     return this.disabilityDetails;
/*      */   }
/*      */   public void setDisabilityDetails(String disabilityDetails) {
/*  887 */     this.disabilityDetails = disabilityDetails;
/*      */   }
/*      */   public String getExaminationDate() {
/*  890 */     return this.examinationDate;
/*      */   }
/*      */   public void setExaminationDate(String examinationDate) {
/*  893 */     this.examinationDate = examinationDate;
/*      */   }
/*      */   public String getPhysician() {
/*  896 */     return this.physician;
/*      */   }
/*      */   public void setPhysician(String physician) {
/*  899 */     this.physician = physician;
/*      */   }
/*      */   public String getAddress() {
/*  902 */     return this.address;
/*      */   }
/*      */   public void setAddress(String address) {
/*  905 */     this.address = address;
/*      */   }
/*      */   public ArrayList getMedicationCondition() {
/*  908 */     return this.medicationCondition;
/*      */   }
/*      */   public void setMedicationCondition(ArrayList medicationCondition) {
/*  911 */     this.medicationCondition = medicationCondition;
/*      */   }
/*      */   public String getHerniaSide() {
/*  914 */     return this.herniaSide;
/*      */   }
/*      */   public void setHerniaSide(String herniaSide) {
/*  917 */     this.herniaSide = herniaSide;
/*      */   }
/*      */   public String getHerniaOperationFlag() {
/*  920 */     return this.herniaOperationFlag;
/*      */   }
/*      */   public void setHerniaOperationFlag(String herniaOperationFlag) {
/*  923 */     this.herniaOperationFlag = herniaOperationFlag;
/*      */   }
/*      */   public String getMedicalConditionDetails() {
/*  926 */     return this.medicalConditionDetails;
/*      */   }
/*      */   public void setMedicalConditionDetails(String medicalConditionDetails) {
/*  929 */     this.medicalConditionDetails = medicalConditionDetails;
/*      */   }
/*      */   public String getLegalQuestion1() {
/*  932 */     return this.legalQuestion1;
/*      */   }
/*      */   public void setLegalQuestion1(String legalQuestion1) {
/*  935 */     this.legalQuestion1 = legalQuestion1;
/*      */   }
/*      */   public String getLegalQuestion1Date() {
/*  938 */     return this.legalQuestion1Date;
/*      */   }
/*      */   public void setLegalQuestion1Date(String legalQuestion1Date) {
/*  941 */     this.legalQuestion1Date = legalQuestion1Date;
/*      */   }
/*      */   public String getLegalQuestion1Details() {
/*  944 */     return this.legalQuestion1Details;
/*      */   }
/*      */   public void setLegalQuestion1Details(String legalQuestion1Details) {
/*  947 */     this.legalQuestion1Details = legalQuestion1Details;
/*      */   }
/*      */   public String getLegalQuestion2() {
/*  950 */     return this.legalQuestion2;
/*      */   }
/*      */   public void setLegalQuestion2(String legalQuestion2) {
/*  953 */     this.legalQuestion2 = legalQuestion2;
/*      */   }
/*      */   public String getLegalQuestion2Date() {
/*  956 */     return this.legalQuestion2Date;
/*      */   }
/*      */   public void setLegalQuestion2Date(String legalQuestion2Date) {
/*  959 */     this.legalQuestion2Date = legalQuestion2Date;
/*      */   }
/*      */   public String getLegalQuestion2Details() {
/*  962 */     return this.legalQuestion2Details;
/*      */   }
/*      */   public void setLegalQuestion2Details(String legalQuestion2Details) {
/*  965 */     this.legalQuestion2Details = legalQuestion2Details;
/*      */   }
/*      */   public String getLegalQuestion3() {
/*  968 */     return this.legalQuestion3;
/*      */   }
/*      */   public void setLegalQuestion3(String legalQuestion3) {
/*  971 */     this.legalQuestion3 = legalQuestion3;
/*      */   }
/*      */   public String getLegalQuestion3Date() {
/*  974 */     return this.legalQuestion3Date;
/*      */   }
/*      */   public void setLegalQuestion3Date(String legalQuestion3Date) {
/*  977 */     this.legalQuestion3Date = legalQuestion3Date;
/*      */   }
/*      */   public String getLegalQuestion3Details() {
/*  980 */     return this.legalQuestion3Details;
/*      */   }
/*      */   public void setLegalQuestion3Details(String legalQuestion3Details) {
/*  983 */     this.legalQuestion3Details = legalQuestion3Details;
/*      */   }
/*      */   public String getLegalQuestion4() {
/*  986 */     return this.legalQuestion4;
/*      */   }
/*      */   public void setLegalQuestion4(String legalQuestion4) {
/*  989 */     this.legalQuestion4 = legalQuestion4;
/*      */   }
/*      */   public String getLegalQuestion4Date() {
/*  992 */     return this.legalQuestion4Date;
/*      */   }
/*      */   public void setLegalQuestion4Date(String legalQuestion4Date) {
/*  995 */     this.legalQuestion4Date = legalQuestion4Date;
/*      */   }
/*      */   public String getLegalQuestion4Details() {
/*  998 */     return this.legalQuestion4Details;
/*      */   }
/*      */   public void setLegalQuestion4Details(String legalQuestion4Details) {
/* 1001 */     this.legalQuestion4Details = legalQuestion4Details;
/*      */   }
/*      */   public String getLegalQuestion5() {
/* 1004 */     return this.legalQuestion5;
/*      */   }
/*      */   public void setLegalQuestion5(String legalQuestion5) {
/* 1007 */     this.legalQuestion5 = legalQuestion5;
/*      */   }
/*      */   public String getLegalQuestion5Date() {
/* 1010 */     return this.legalQuestion5Date;
/*      */   }
/*      */   public void setLegalQuestion5Date(String legalQuestion5Date) {
/* 1013 */     this.legalQuestion5Date = legalQuestion5Date;
/*      */   }
/*      */   public String getLegalQuestion5Details() {
/* 1016 */     return this.legalQuestion5Details;
/*      */   }
/*      */   public void setLegalQuestion5Details(String legalQuestion5Details) {
/* 1019 */     this.legalQuestion5Details = legalQuestion5Details;
/*      */   }
/*      */   public String getProbationFlag() {
/* 1022 */     return this.probationFlag;
/*      */   }
/*      */   public void setProbationFlag(String probationFlag) {
/* 1025 */     this.probationFlag = probationFlag;
/*      */   }
/*      */   public String getProbationCounty() {
/* 1028 */     return this.probationCounty;
/*      */   }
/*      */   public void setProbationCounty(String probationCounty) {
/* 1031 */     this.probationCounty = probationCounty;
/*      */   }
/*      */   public String getProbationState() {
/* 1034 */     return this.probationState;
/*      */   }
/*      */   public void setProbationState(String probationState) {
/* 1037 */     this.probationState = probationState;
/*      */   }
/*      */   public String getProbationOfficer() {
/* 1040 */     return this.probationOfficer;
/*      */   }
/*      */   public void setProbationOfficer(String probationOfficer) {
/* 1043 */     this.probationOfficer = probationOfficer;
/*      */   }
/*      */   public String getProbationOfficerPhone() {
/* 1046 */     return this.probationOfficerPhone;
/*      */   }
/*      */   public void setProbationOfficerPhone(String probationOfficerPhone) {
/* 1049 */     this.probationOfficerPhone = probationOfficerPhone;
/*      */   }
/*      */   public ArrayList getJobSkillFlag() {
/* 1052 */     return this.jobSkillFlag;
/*      */   }
/*      */   public void setJobSkillFlag(ArrayList jobSkillFlag) {
/* 1055 */     this.jobSkillFlag = jobSkillFlag;
/*      */   }
/*      */   public String getWorkExperienceDetails() {
/* 1058 */     return this.workExperienceDetails;
/*      */   }
/*      */   public void setWorkExperienceDetails(String workExperienceDetails) {
/* 1061 */     this.workExperienceDetails = workExperienceDetails;
/*      */   }
/*      */   public String getEmployer1() {
/* 1064 */     return this.employer1;
/*      */   }
/*      */   public void setEmployer1(String employer1) {
/* 1067 */     this.employer1 = employer1;
/*      */   }
/*      */   public String getJobTitle1() {
/* 1070 */     return this.jobTitle1;
/*      */   }
/*      */   public void setJobTitle1(String jobTitle1) {
/* 1073 */     this.jobTitle1 = jobTitle1;
/*      */   }
/*      */   public String getJobStartDate1() {
/* 1076 */     return this.jobStartDate1;
/*      */   }
/*      */   public void setJobStartDate1(String jobStartDate1) {
/* 1079 */     this.jobStartDate1 = jobStartDate1;
/*      */   }
/*      */   public String getJobEndDate1() {
/* 1082 */     return this.jobEndDate1;
/*      */   }
/*      */   public void setJobEndDate1(String jobEndDate1) {
/* 1085 */     this.jobEndDate1 = jobEndDate1;
/*      */   }
/*      */   public String getEmployer2() {
/* 1088 */     return this.employer2;
/*      */   }
/*      */   public void setEmployer2(String employer2) {
/* 1091 */     this.employer2 = employer2;
/*      */   }
/*      */   public String getJobTitle2() {
/* 1094 */     return this.jobTitle2;
/*      */   }
/*      */   public void setJobTitle2(String jobTitle2) {
/* 1097 */     this.jobTitle2 = jobTitle2;
/*      */   }
/*      */   public String getJobStartDate2() {
/* 1100 */     return this.jobStartDate2;
/*      */   }
/*      */   public void setJobStartDate2(String jobStartDate2) {
/* 1103 */     this.jobStartDate2 = jobStartDate2;
/*      */   }
/*      */   public String getJobEndDate2() {
/* 1106 */     return this.jobEndDate2;
/*      */   }
/*      */   public void setJobEndDate2(String jobEndDate2) {
/* 1109 */     this.jobEndDate2 = jobEndDate2;
/*      */   }
/*      */   public String getEmployer3() {
/* 1112 */     return this.employer3;
/*      */   }
/*      */   public void setEmployer3(String employer3) {
/* 1115 */     this.employer3 = employer3;
/*      */   }
/*      */   public String getJobTitle3() {
/* 1118 */     return this.jobTitle3;
/*      */   }
/*      */   public void setJobTitle3(String jobTitle3) {
/* 1121 */     this.jobTitle3 = jobTitle3;
/*      */   }
/*      */   public String getJobStartDate3() {
/* 1124 */     return this.jobStartDate3;
/*      */   }
/*      */   public void setJobStartDate3(String jobStartDate3) {
/* 1127 */     this.jobStartDate3 = jobStartDate3;
/*      */   }
/*      */   public String getJobEndDate3() {
/* 1130 */     return this.jobEndDate3;
/*      */   }
/*      */   public void setJobEndDate3(String jobEndDate3) {
/* 1133 */     this.jobEndDate3 = jobEndDate3;
/*      */   }
/*      */   public Long getId() {
/* 1136 */     return this.id;
/*      */   }
/*      */   public void setId(Long id) {
/* 1139 */     this.id = id;
/*      */   }
/*      */   public String getLegalQuestion3Quantity() {
/* 1142 */     return this.legalQuestion3Quantity;
/*      */   }
/*      */   public void setLegalQuestion3Quantity(String legalQuestion3Quantity) {
/* 1145 */     this.legalQuestion3Quantity = legalQuestion3Quantity;
/*      */   }
/*      */ }

/* Location:           C:\development\workspace\faithfarmoss\WEB-INF\classes\
 * Qualified Name:     org.faithfarm.dataobjects.PersonMisc
 * JD-Core Version:    0.6.2
 */