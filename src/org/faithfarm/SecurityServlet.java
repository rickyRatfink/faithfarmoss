/*     */ package org.faithfarm;
/*     */ 
/*     */ /*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
import java.util.Iterator;
/*     */ import java.util.logging.Logger;

/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;

/*     */ import org.faithfarm.dataobjects.SearchParameter;
/*     */ import org.faithfarm.datawriters.ApplicationDao;
/*     */ import org.faithfarm.datawriters.PersonDao;
/*     */ import org.faithfarm.utilities.HTMLBuilder;
/*     */ import org.faithfarm.utilities.XMLParser;
/*     */ import org.faithfarm.validators.Validator;
/*     */ import org.w3c.dom.NodeList;

import com.google.appengine.api.datastore.DatastoreService;
/*     */ import com.google.appengine.api.datastore.DatastoreServiceFactory;
/*     */ import com.google.appengine.api.datastore.Entity;
/*     */ import com.google.appengine.api.datastore.Key;
/*     */ import com.google.appengine.api.datastore.KeyFactory;
/*     */ import com.google.appengine.api.datastore.PreparedQuery;
/*     */ import com.google.appengine.api.datastore.Query;
/*     */ import com.google.appengine.api.users.User;
/*     */ import com.google.appengine.api.users.UserService;
/*     */ import com.google.appengine.api.users.UserServiceFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SecurityServlet extends BaseServlet
/*     */ {
/*  41 */   private static final Logger logger = Logger.getLogger(ControllerServlet.class.getCanonicalName());
/*  42 */   private static final HTMLBuilder html = new HTMLBuilder();
/*  43 */   private static final XMLParser xml = new XMLParser();
/*  44 */   private static final Validator validator = new Validator();
/*     */ 
/*  46 */   private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
/*     */ 
/*     */   protected void doGet(HttpServletRequest req, HttpServletResponse resp)
/*     */     throws ServletException, IOException
/*     */   {
/*  53 */     super.doGet(req, resp);
/*  54 */     HttpSession session = req.getSession(true);
/*  55 */     String action = req.getParameter("action");
/*     */ 
/*  59 */     UserService userService = UserServiceFactory.getUserService();
/*  60 */     User user = userService.getCurrentUser();
/*  61 */     String url = "/jsp/main.jsp";
/*     */ 
/*  67 */     if ("createRole".equals(action)) {
	System.out.println ("here in create role");
/*  68 */       String email = req.getParameter("email");
/*  69 */       String role = req.getParameter("role");
/*  70 */       String farm = req.getParameter("farm");
/*  71 */       PersonDao.createSystemUser(user, email, farm, role);
/*     */     }
/*  73 */     else if ("Index".equals(action)) {
/*  74 */       NodeList xmlUser = xml.getEntityProperties("User", req);
/*  75 */       Entity User = xml.xmlToEntity("User", req);
/*  76 */       req.setAttribute("xmlUser", xmlUser);
/*  77 */       req.setAttribute("User", User);
/*  78 */       url = "/jsp/admin/results.jsp";
/*     */     }
/*  80 */     if ("Filter".equals(action))
/*     */     {
/*  82 */       String farm = html.cleanData(req.getParameter("farmLocation"));
/*     */ 
/*  85 */       ArrayList results = ApplicationDao.getEntities("User", null, null, null, farm);
/*     */ 
/*  87 */       req.setAttribute("results", results);
/*  88 */       req.getSession().setAttribute("farmLocation", farm);
/*     */ 
/*  90 */       url = "/jsp/admin/results.jsp";
/*     */     }
/*  92 */     if ("Create".equals(action)) {
/*  93 */       NodeList xmlUser = xml.getEntityProperties("User", req);
/*  94 */       Entity User = xml.xmlToEntity("User", req);
/*  95 */       req.setAttribute("xmlUser", xmlUser);
/*  96 */       req.setAttribute("User", User);
/*  97 */       url = "/jsp/admin/index.jsp";
/*  98 */     } else if ("Edit".equals(action)) {
/*  99 */       String sKey = req.getParameter("userKey");
/* 100 */       Key key = KeyFactory.stringToKey(sKey);
/* 101 */       Entity User = ApplicationDao.getEntity(key);
/* 102 */       NodeList xmlUser = xml.getEntityProperties("User", req);
/* 103 */       req.setAttribute("xmlUser", xmlUser);
/* 104 */       req.setAttribute("User", User);
/* 105 */       url = "/jsp/admin/index.jsp?edit=Y";
/*     */     }
/* 107 */     else if ("Delete".equals(action)) {
/* 108 */       String farm = html.cleanData(req.getParameter("farmLocation"));
/*     */ 
/* 110 */       String sKey = req.getParameter("ticketKey");
/* 111 */       Key key = KeyFactory.stringToKey(sKey);
/* 112 */       Entity User = ApplicationDao.getEntity(key);
/* 113 */       ApplicationDao.deleteEntity(User);
/* 114 */       ArrayList results = ApplicationDao.getEntities("User", null, null, null, farm);
/* 115 */       req.setAttribute("results", results);
/*     */ 
/* 117 */       url = "/jsp/admin/results.jsp";
/*     */     }
/* 119 */     else if ("Save User".equals(action)) {
/* 120 */       NodeList xmlUser = xml.getEntityProperties("User", req);
/* 121 */       Entity User = xml.xmlToEntity("User", req);
/* 122 */       if (validator.validateForm(xmlUser, req)) {
/* 123 */         User.setProperty("creationDate", new Date());
/* 124 */         User.setProperty("createdBy", user.getEmail());
/* 125 */         ApplicationDao.createOrUpdateEntity(User, user);
/*     */       } else {
/* 127 */         req.setAttribute("xmlUser", xmlUser);
/* 128 */         req.setAttribute("User", User);
/* 129 */         url = "/jsp/admin/index.jsp";
/*     */       }
/*     */ 
/*     */     }
/* 136 */     else if ("Save Changes".equals(action)) {
/* 137 */       String sKey = req.getParameter("userKey");
/* 138 */       Key key = KeyFactory.stringToKey(sKey);
/* 139 */       Entity User = ApplicationDao.getEntity(key);
/* 140 */       User = xml.updateEntity(User, req);
/*     */ 
/* 142 */       NodeList xmlUser = xml.getEntityProperties("User", req);
/* 143 */       ArrayList messages = new ArrayList();
/*     */ 
/* 145 */       if (validator.validateForm(xmlUser, req)) {
/* 146 */         ApplicationDao.createOrUpdateEntity(User, user);
/* 147 */         url = "/jsp/admin/results.jsp";
/*     */       }
/*     */       else {
/* 150 */         req.setAttribute("xmlUser", xmlUser);
/* 151 */         req.setAttribute("User", User);
/* 152 */         url = "/jsp/admin/index.jsp";
/*     */       }
/* 154 */     } else if ("CallLogAddFarm".equals(action)) {
/* 155 */       Iterable results = null;
/* 156 */       Query q = new Query("CallLog");
/* 157 */       PreparedQuery pq = datastore.prepare(q);
/* 158 */       results = pq.asIterable();
/* 159 */       int i = 0;
/* 160 */       //for (Entity CallLog : results) {
	for (Iterator it = results.iterator(); it.hasNext();) {
		Entity CallLog = (Entity) it.next();

/* 161 */         CallLog.setProperty("farmBase", "Fort Lauderdale");
/* 162 */         Util.persistEntity(CallLog);
/* 163 */         i++;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 169 */     if (user != null)
/*     */     {
/* 174 */       Entity User = PersonDao.getUserRole(user.getEmail());
/*     */ 
/* 176 */       if (User != null)
/*     */       {
/* 178 */         if ("ffm.corp.ceo".equals(user.getNickname())) {
/* 179 */           url = "/jsp/dashboard/index.jsp";
/*     */         }
/* 181 */         req.getSession().setAttribute(user.getNickname() + "_ROLE", User.getProperty("role"));
/* 182 */         String role = (String)User.getProperty("role");
/* 183 */         if (role.contains("Sales"))
/* 184 */           session.setAttribute("INVENTORY_LOCATION", "New Furniture");
/* 185 */         else if (role.contains("As-Is")) {
/* 186 */           session.setAttribute("INVENTORY_LOCATION", "As-Is");
/*     */         }
/*     */ 
/* 190 */         String farm = (String)User.getProperty("farm");
/* 191 */         session.setAttribute("farmBase", farm);
/* 192 */         session.setAttribute("farm", farm);
/* 193 */         session.setAttribute("User", user);
/*     */ 
/* 195 */         session.setAttribute("vendors", html.buildVendorList());
/* 196 */         session.setAttribute("ddlOrg", html.buildOrgList());
/* 197 */         session.setAttribute("locations", html.buildLocationList());
/* 198 */         session.setAttribute("transactions", html.buildTransactionList());
/* 199 */         session.setAttribute("ddlNameSuffix", html.buildNameSuffixList());
/* 200 */         session.setAttribute("ddlEducation", html.buildEducationList());
/* 201 */         session.setAttribute("ddlEthnicity", html.buildEthnicityList());
/* 202 */         session.setAttribute("ddlEyecolor", html.buildEyecolorList());
/* 203 */         session.setAttribute("ddlHaircolor", html.buildHaircolorList());
/* 204 */         session.setAttribute("ddlHomeLocation", html.buildHomeLocationList());
/* 205 */         session.setAttribute("ddlMaritalStatus", html.buildMaritalStatusList());
/* 206 */         session.setAttribute("ddlWeight", html.buildWeightList());
/* 207 */         session.setAttribute("ddlYesNo", html.buildYesNoList());
/* 208 */         session.setAttribute("ddlHeight", html.buildHeightList());
/* 209 */         session.setAttribute("ddlPersonType", html.buildPersonTypeList());
/* 210 */         session.setAttribute("ddlAge", html.buildAgeList());
/* 211 */         session.setAttribute("ddlStates", html.buildStateList());
/* 212 */         session.setAttribute("ddlBranchOfService", html.buildBranchOfServiceList());
/* 213 */         session.setAttribute("ddlClaimStatus", html.buildClaimStatusList());
/* 214 */         session.setAttribute("ddlCurrentHealth", html.buildCurrentHealthList());
/* 215 */         session.setAttribute("ddlFarms", html.buildFarmList());
/* 216 */         session.setAttribute("ddlGovernmentBenefits", html.buildGovernmentBenefitsList());
/* 217 */         session.setAttribute("ddlLosses", html.buildLossesList());
/* 218 */         session.setAttribute("ddlPhysicalEffects", html.buildPhysicalEffectsList());
/* 219 */         session.setAttribute("ddlUsagePattern", html.buildUsagePatternList());
/* 220 */         session.setAttribute("ddlReligion", html.buildReligionList());
/* 221 */         session.setAttribute("ddlEyewear", html.buildEyewearList());
/* 222 */         session.setAttribute("ddlPersonStatus", html.buildPersonStatusList());
/* 223 */         session.setAttribute("ddlEmployees", html.buildEmployeeList(farm));
/* 224 */         session.setAttribute("ddlWorkAssignments", html.buildJobList());
/* 225 */         session.setAttribute("ddlActiveInactive", html.buildActiveInactiveList());
/* 226 */         session.setAttribute("ddlBagsBoxes", html.buildBagsBoxesList());
/* 227 */         session.setAttribute("ddlFloors", html.buildFloorsList());
/* 228 */         session.setAttribute("ddlItemLocation", html.buildItemLocationList());
/* 229 */         session.setAttribute("ddlGateInstructions", html.buildGateInstructionsList());
/* 230 */         session.setAttribute("ddlCallRequirements", html.buildCallRequirementsList());
/* 231 */         session.setAttribute("ddlStreetSuffix", html.buildStreetSuffixList());
/* 232 */         session.setAttribute("ddlStructureType", html.buildStructureTypeList());
/* 233 */         session.setAttribute("ddlDispatchStatus", html.buildDispatchStatusList());
/* 234 */         session.setAttribute("ddlHowDidYouHearAboutUs", html.buildHowDidYouHearAboutUsList());
/* 235 */         session.setAttribute("ddlItemStatus", html.buildItemStatusList());
/* 236 */         session.setAttribute("ddlItemTypes", html.buildItemTypes());
/* 237 */         session.setAttribute("ddlSalesStatus", html.buildSalesStatusList());
/* 238 */         session.setAttribute("ddlDispatchType", html.buildDispatchTypeList());
/* 239 */         session.setAttribute("ddlInventoryLocation", html.buildInventoryLocation());
/* 240 */         session.setAttribute("ddlTruckList", html.buildTruckList(farm));
/* 241 */         session.setAttribute("ddlTicketStatus", html.buildTicketStatus());
/* 242 */         session.setAttribute("ddlTicketPriority", html.buildTicketPriority());
/* 243 */         session.setAttribute("ddlOS", html.buildOS());
/* 244 */         session.setAttribute("ddlTicketType", html.buildTicketType());
/* 245 */         session.setAttribute("ddlMonths", html.buildMonths());
/* 246 */         session.setAttribute("ddlDays", html.buildDays());
/* 247 */         session.setAttribute("ddlPaymentMethod", html.buildPaymentMethods());
/* 248 */         session.setAttribute("ddlSystemRoles", html.buildSystemRoles());
/*     */ 
/* 252 */         for (int i = 0; i < 7; i++) {
/* 253 */           ArrayList parameters = new ArrayList();
/* 254 */           parameters.add(new SearchParameter("currentCourse", i+"", Query.FilterOperator.EQUAL));
/* 255 */           parameters.add(new SearchParameter("personType", "Resident", Query.FilterOperator.EQUAL));
/* 256 */           parameters.add(new SearchParameter("personStatus", "Active", Query.FilterOperator.EQUAL));
/*     */ 
/* 258 */           ArrayList results = ApplicationDao.getEntities("Person", parameters, null, null, farm);
/* 259 */           if (results == null) results = new ArrayList();
/* 260 */           req.getSession().setAttribute("class_" + i, Integer.valueOf(results.size()));
/*     */         }
/*     */ 
/* 264 */         String[] smonth = { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };
/*     */ 
/* 266 */         for (int month = 0; month < 12; month++) {
/* 267 */           ArrayList parameters = new ArrayList();
/* 268 */           parameters.add(new SearchParameter("Month", smonth[month], Query.FilterOperator.EQUAL));
/* 269 */           parameters.add(new SearchParameter("Year", "2012", Query.FilterOperator.EQUAL));
/* 270 */           ArrayList results1 = ApplicationDao.getEntities("ActualDailySalesYTD", parameters, null, null, farm);
/* 271 */           ArrayList results2 = ApplicationDao.getEntities("BudgetedDailySalesYTD", parameters, null, null, farm);
/*     */ 
/* 273 */           if ((results1.size() > 0) && (results2.size() > 0)) {
/* 274 */             Entity listActualYtd = (Entity)results1.get(0);
/* 275 */             Entity listBudgetYtd = (Entity)results2.get(0);
/* 276 */             double actualYtd = 0.0D;
/* 277 */             double budgetYtd = 0.0D;
/*     */ 
/* 279 */             for (int day = 1; day < 32; day++) {
/* 280 */               double value1 = ((Double)listActualYtd.getProperty("Day_" + day)).doubleValue();
/* 281 */               double value2 = ((Double)listBudgetYtd.getProperty("Day_" + day)).doubleValue();
/* 282 */               actualYtd += value1;
/* 283 */               budgetYtd += value2;
/*     */             }
/*     */ 
/* 286 */             session.setAttribute("actual_Month_" + month, Double.valueOf(actualYtd));
/* 287 */             session.setAttribute("budget_Month_" + month, Double.valueOf(budgetYtd));
/*     */           }
/*     */         }
/*     */       }
/*     */       else {
/* 292 */         req.getSession().setAttribute(user.getNickname() + "_ROLE", null);
/* 293 */         url = "/jsp/access_denied.jsp";
/*     */       }
/*     */ 
/* 296 */       if ("logout".equals(action))
/* 297 */         req.getRequestDispatcher(userService.createLogoutURL(req.getRequestURI())).forward(req, resp);
/*     */       else
/* 299 */         req.getRequestDispatcher(req.getContextPath() + url).forward(req, resp);
/*     */     }
/*     */     else
/*     */     {
/* 303 */       req.getRequestDispatcher(userService.createLoginURL(req.getRequestURI() + "/security")).forward(req, resp);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doPost(HttpServletRequest req, HttpServletResponse resp)
/*     */     throws ServletException, IOException
/*     */   {
/* 313 */     doGet(req, resp);
/*     */   }
/*     */ }

/* Location:           C:\development\workspace\faithfarmoss\WEB-INF\classes\
 * Qualified Name:     org.faithfarm.SecurityServlet
 * JD-Core Version:    0.6.2
 */