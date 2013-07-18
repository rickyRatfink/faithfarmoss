/*      */ package org.faithfarm;
/*      */ 
/*      */ /*      */ import java.io.IOException;
/*      */ import java.text.DateFormat;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Calendar;
/*      */ import java.util.Date;
/*      */ import java.util.logging.Level;
/*      */ import java.util.logging.Logger;

/*      */ import javax.servlet.ServletException;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import javax.servlet.http.HttpSession;

/*      */ import org.faithfarm.dataobjects.SearchParameter;
/*      */ import org.faithfarm.datawriters.ApplicationDao;
/*      */ import org.faithfarm.datawriters.PersonDao;
/*      */ import org.faithfarm.mail.Mailer;
/*      */ import org.faithfarm.utilities.ApplicationTools;
/*      */ import org.faithfarm.utilities.FFSession;
/*      */ import org.faithfarm.utilities.HTMLBuilder;
/*      */ import org.faithfarm.utilities.XMLParser;
/*      */ import org.faithfarm.validators.Validator;
/*      */ import org.w3c.dom.Element;
/*      */ import org.w3c.dom.NodeList;
import com.google.appengine.api.datastore.Query.FilterOperator;

import com.google.appengine.api.datastore.DatastoreService;
/*      */ import com.google.appengine.api.datastore.DatastoreServiceFactory;
/*      */ import com.google.appengine.api.datastore.Entity;
/*      */ import com.google.appengine.api.datastore.FetchOptions;
/*      */ import com.google.appengine.api.datastore.Key;
/*      */ import com.google.appengine.api.datastore.KeyFactory;
/*      */ import com.google.appengine.api.datastore.PreparedQuery;
/*      */ import com.google.appengine.api.datastore.Query;
/*      */ import com.google.appengine.api.users.User;
/*      */ import com.google.appengine.api.users.UserService;
/*      */ import com.google.appengine.api.users.UserServiceFactory;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class DispatchServlet extends BaseServlet
/*      */ {
/*   49 */   private static final Logger logger = Logger.getLogger(ControllerServlet.class.getCanonicalName());
/*   50 */   private static final HTMLBuilder html = new HTMLBuilder();
/*   51 */   private static final PersonDao dao = new PersonDao();
/*   52 */   private static final XMLParser xml = new XMLParser();
/*   53 */   private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
/*      */ 
/*      */   protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
/*      */   {
/*   57 */     if (FFSession.needsRedirect(req, resp)) {
/*   58 */       return;
/*      */     }
/*      */ 
/*   61 */     UserService userService = UserServiceFactory.getUserService();
/*   62 */     User user = userService.getCurrentUser();
/*   63 */     String url = "/jsp/dispatch/index.jsp";
/*      */ 
/*   65 */     String dispatchPhoneNumber = "";
/*   66 */     String farm = (String)req.getSession().getAttribute("farmBase");
/*      */ 
/*   68 */     if ("Fort Lauderdale".equals(farm))
/*   69 */       dispatchPhoneNumber = "954-763-7787";
/*   70 */     else if ("Boynton Beach".equals(farm))
/*   71 */       dispatchPhoneNumber = "561-733-7238";
/*   72 */     else if ("Okeechobee".equals(farm)) {
/*   73 */       dispatchPhoneNumber = "863-763-4224 or 888-547-4258";
/*      */     }
/*      */ 
/*   78 */     boolean bPrint = false;
/*   79 */     boolean bDispatchSaved = false;
/*   80 */     String dispatchKey = "";
/*      */ 
/*   82 */     String view = "results.jsp";
/*   83 */     if (req.getSession().getAttribute("view") != null) {
/*   84 */       view = (String)req.getSession().getAttribute("view");
/*      */     }
/*      */ 
/*   88 */     super.doGet(req, resp);
/*   89 */     String action = req.getParameter("action");
/*   90 */     if (action == null) {
/*   91 */       action = req.getParameter("action");
/*      */     }
/*   93 */     NodeList xmlPerson = xml.getEntityProperties("Person", req);
/*   94 */     NodeList xmlAddress = xml.getEntityProperties("Address", req);
/*   95 */     NodeList xmlDispatch = xml.getEntityProperties("Dispatch", req);
/*   96 */     NodeList xmlMoneyDonation = xml.getEntityProperties("MoneyDonation", req);
/*      */ 
/*   98 */     req.setAttribute("xmlPerson", xmlPerson);
/*   99 */     req.setAttribute("xmlAddress", xmlAddress);
/*  100 */     req.setAttribute("xmlDispatch", xmlDispatch);
/*  101 */     req.setAttribute("xmlMoneyDonation", xmlMoneyDonation);
/*      */ 
/*  103 */     if (("Save & Print".equals(action)) || ("Save Changes & Print".equals(action))) {
/*  104 */       bPrint = true;
/*      */     }
/*  106 */     if ("Create".equals(action))
/*      */     {
/*  110 */       checkDailyLimit(req);
/*  111 */       req.setAttribute("Person", xml.xmlToEntity("Person", req));
/*  112 */       req.setAttribute("Address", xml.xmlToEntity("Address", req));
/*  113 */       Entity Dispatch = xml.xmlToEntity("Dispatch", req);
/*  114 */       Dispatch.setProperty("dispatchAddress", "");
/*  115 */       Dispatch.setProperty("status", "Pending");
/*  116 */       req.setAttribute("Dispatch", Dispatch);
/*  117 */       url = "/jsp/dispatch/index.jsp";
/*  118 */     } else if ("Cash Donation".equals(action))
/*      */     {
/*  123 */       req.setAttribute("Person", xml.xmlToEntity("Person", req));
/*  124 */       req.setAttribute("Address", xml.xmlToEntity("Address", req));
/*  125 */       req.setAttribute("xmlMoneyDonation", xmlMoneyDonation);
/*  126 */       req.setAttribute("xmlPerson", xmlPerson);
/*  127 */       req.setAttribute("xmlAddress", xmlAddress);
/*      */ 
/*  129 */       Entity MoneyDonation = xml.xmlToEntity("MoneyDonation", req);
/*  130 */       req.setAttribute("MoneyDonation", MoneyDonation);
/*  131 */       url = "/jsp/dispatch/cash_donation.jsp";
/*  132 */     } else if ("Set Limit".equals(action))
/*      */     {
/*  134 */       String date = req.getParameter("dispatchDate");
/*  135 */       String limit = req.getParameter("level");
/*  136 */       String farmBase = (String)req.getSession().getAttribute("farmBase");
/*  137 */       Entity entity = new Entity("DispatchLevel");
/*  138 */       Date d = new Date(date);
/*  139 */       DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
/*  140 */       entity.setProperty("createdBy", user.getNickname());
/*  141 */       entity.setProperty("creationDate", new Date());
/*  142 */       entity.setProperty("dispatchDate", formatter.format(d));
/*  143 */       entity.setProperty("limit", limit);
/*  144 */       entity.setProperty("farmBase", farmBase);
/*  145 */       ApplicationDao.createOrUpdateEntity(entity, user);
/*      */ 
/*  147 */       url = "/jsp/dispatch/" + view;
/*      */     }
/*  149 */     else if ("Closeout".equals(action))
/*      */     {
/*  151 */       ArrayList messages = new ArrayList();
/*  152 */       Validator validator = new Validator();
/*  153 */       String date = req.getParameter("closeoutDate");
/*  154 */       String pickedUp = req.getParameter("pickedUp");
/*  155 */       String cancelled = req.getParameter("cancelled");
/*  156 */       String rejected = req.getParameter("rejected");
/*  157 */       String noresponse = req.getParameter("noresponses");
/*  158 */       String special = req.getParameter("specials");
/*  159 */       String phoneOperators = req.getParameter("phoneOperators");
/*  160 */       String drivers = req.getParameter("drivers");
/*      */ 
/*  162 */       messages = validator.validateRequired("date", date, messages);
/*  163 */       messages = validator.validateRequired("# picked up", pickedUp, messages);
/*  164 */       messages = validator.validateRequired("# cancelled", cancelled, messages);
/*  165 */       messages = validator.validateRequired("# rejected", rejected, messages);
/*  166 */       messages = validator.validateRequired("# no response", noresponse, messages);
/*  167 */       messages = validator.validateRequired("# specials", special, messages);
/*  168 */       messages = validator.validateRequired("# phone operators", phoneOperators, messages);
/*  169 */       messages = validator.validateRequired("# drivers", drivers, messages);
/*      */ 
/*  171 */       if (messages.size() > 0) {
/*  172 */         req.setAttribute("messages", messages);
/*  173 */         url = "/jsp/dispatch/closeout.jsp";
/*      */       } else {
/*  175 */         Entity CloseOut = new Entity("DispatchCloseout");
/*  176 */         CloseOut.setProperty("date", date);
/*  177 */         CloseOut.setProperty("pickedUp", pickedUp);
/*  178 */         CloseOut.setProperty("cancelled", cancelled);
/*  179 */         CloseOut.setProperty("rejected", rejected);
/*  180 */         CloseOut.setProperty("noresponse", noresponse);
/*  181 */         CloseOut.setProperty("special", special);
/*  182 */         CloseOut.setProperty("phoneOperators", phoneOperators);
/*  183 */         CloseOut.setProperty("drivers", drivers);
/*  184 */         CloseOut.setProperty("createdBy", user.getNickname());
/*  185 */         CloseOut.setProperty("creationDate", new Date());
/*  186 */         CloseOut.setProperty("farmBase", (String)req.getSession().getAttribute("farmBase"));
/*  187 */         ApplicationDao.createOrUpdateEntity(CloseOut, user);
/*  188 */         url = "/jsp/main.jsp";
/*      */       }
/*      */     }
/*  191 */     else if ("Delete".equals(action))
/*      */     {
/*  198 */       String sKey = req.getParameter("dispatchKey");
/*  199 */       Key k = KeyFactory.stringToKey(sKey);
/*  200 */       Entity Dispatch = PersonDao.getSingleDispatch(k);
/*  201 */       ApplicationDao.deleteEntity(Dispatch);
/*  202 */       ArrayList results = ApplicationDao.getEntities("Dispatch", setSearchParameters(req), null, null, (String)req.getSession().getAttribute("farmBase"));
/*  203 */       req.getSession().setAttribute("results", results);
/*  204 */       req.setAttribute("size", Integer.valueOf(results.size()));
/*  205 */       url = "/jsp/dispatch/" + view;
/*  206 */     } else if ("Edit".equals(action))
/*      */     {
/*  208 */       checkDailyLimit(req);
/*  209 */       String sKey = req.getParameter("dispatchKey");
/*  210 */       Key k1 = KeyFactory.stringToKey(sKey);
/*  211 */       Entity Dispatch = PersonDao.getSingleDispatch(k1);
/*  212 */       Key key = Dispatch.getParent();
/*      */ 
/*  214 */       String dispatchAddress = "";
/*      */       try {
/*  216 */         dispatchAddress = (String)Dispatch.getProperty("dispatchAddress");
/*  217 */         if (dispatchAddress == null) dispatchAddress = ""; 
/*      */       } catch (Exception e) { dispatchAddress = ""; }
/*      */ 
/*  220 */       Entity Person = PersonDao.getSinglePerson(key.getId()+"");
/*  221 */       Entity Address = PersonDao.getAddressByPerson(Person);
/*  222 */       Address = PersonDao.getAddressByPerson(Person);
/*  223 */       if (Address == null) {
/*  224 */         Address = xml.xmlToChildEntity("Address", Person, req);
/*  225 */         Address = parseTelephone(Address, req);
/*      */       }
/*  227 */       req.setAttribute("Person", Person);
/*  228 */       req.setAttribute("Address", Address);
/*  229 */       req.setAttribute("Dispatch", Dispatch);
/*      */ 
/*  231 */       url = "/jsp/dispatch/index.jsp?edit=Y";
/*  232 */     } else if ("Clear".equals(action)) {
/*  233 */       req.getSession().setAttribute("confirmationNumber", "");
/*  234 */       req.getSession().setAttribute("firstName", "");
/*  235 */       req.getSession().setAttribute("middleInitial", "");
/*  236 */       req.getSession().setAttribute("lastName", "");
/*  237 */       req.getSession().setAttribute("status", "");
/*  238 */       req.getSession().setAttribute("zipcode", "");
/*  239 */       req.getSession().setAttribute("dispatchDate", "");
/*      */     }
/*  241 */     else if ("Filter".equals(action))
/*      */     {
/*  256 */       ArrayList results = ApplicationDao.getEntities("Dispatch", setSearchParameters(req), null, null, (String)req.getSession().getAttribute("farmBase"));
/*  257 */       req.getSession().setAttribute("results", results);
/*  258 */       req.setAttribute("size", Integer.valueOf(results.size()));
/*  259 */       if (results.size() > 199)
/*  260 */         req.setAttribute("message", "The maximum number of results returned was reached.  Please refine your search by using the filter above.");
/*  261 */       url = "/jsp/dispatch/" + view;
/*      */     }
/*  263 */     else if ("Search".equals(action)) {
/*  264 */       String firstName = req.getParameter("firstName");
/*  265 */       String lastName = req.getParameter("lastName");
/*  266 */       String middleInitial = req.getParameter("middleName");
/*  267 */       SearchParameter param1 = new SearchParameter("firstName", firstName, Query.FilterOperator.EQUAL);
/*  268 */       SearchParameter param2 = new SearchParameter("lastName", lastName, Query.FilterOperator.EQUAL);
/*  269 */       SearchParameter param3 = new SearchParameter("middleInitial", middleInitial, Query.FilterOperator.EQUAL);
/*  270 */       SearchParameter param4 = new SearchParameter("personType", Arrays.asList(new String[] { "Donor", "Customer" }), Query.FilterOperator.IN);
/*  271 */       ArrayList params = new ArrayList();
/*  272 */       params.add(param1);
/*  273 */       params.add(param2);
/*  274 */       params.add(param3);
/*  275 */       params.add(param4);
/*  276 */       ArrayList results = ApplicationDao.getEntities("Person", params, null, null, (String)req.getSession().getAttribute("farmBase"));
/*  277 */       req.getSession().setAttribute("results", results);
/*  278 */       req.setAttribute("size", Integer.valueOf(results.size()));
/*      */ 
/*  284 */       Entity Person = null;
/*  285 */       int count = results.size();
/*  286 */       if (count == 0) {
/*  287 */         ArrayList messages = new ArrayList();
/*      */ 
/*  289 */         messages.add("No customers matched the name you have entered. Proceed with creating a new customer.");
/*  290 */         req.setAttribute("messages", messages);
/*  291 */         req.setAttribute("Person", xml.xmlToEntity("Person", req));
/*  292 */         req.setAttribute("Address", xml.xmlToEntity("Address", req));
/*  293 */         Entity Dispatch = xml.xmlToEntity("Dispatch", req);
/*  294 */         Dispatch.setProperty("status", "Pending");
/*  295 */         req.setAttribute("Dispatch", Dispatch);
/*      */ 
/*  297 */         if ("moneyDonation".equals(req.getParameter("page")))
/*  298 */           url = "/jsp/dispatch/cash_donation.jsp?edit=Y&new=Y";
/*      */         else
/*  300 */           url = "/jsp/dispatch/index.jsp?edit=Y&new=Y";
/*      */       }
/*  302 */       else if (count == 1) {
/*  303 */         Person = (Entity)results.get(0);
/*  304 */         req.setAttribute("Person", Person);
/*  305 */         Entity Address = PersonDao.getAddressByPerson(Person);
/*  306 */         req.setAttribute("Address", Address);
/*  307 */         Entity Dispatch = xml.xmlToEntity("Dispatch", req);
/*  308 */         Dispatch.setProperty("status", "Pending");
/*  309 */         req.setAttribute("Dispatch", Dispatch);
/*  310 */         if ("moneyDonation".equals(req.getParameter("page")))
/*  311 */           url = "/jsp/dispatch/cash_donation.jsp?edit=Y";
/*      */         else
/*  313 */           url = "/jsp/dispatch/index.jsp?edit=Y";
/*      */       }
/*  315 */       else if ("moneyDonation".equals(req.getParameter("page"))) {
/*  316 */         url = "/jsp/dispatch/customer_list.jsp?page=moneyDonation";
/*      */       } else {
/*  318 */         url = "/jsp/dispatch/customer_list.jsp";
/*      */       }
/*      */ 
/*      */     }
/*  322 */     else if ("New Customer".equals(action)) {
/*  323 */       xmlDispatch = xml.getEntityProperties("Dispatch", req);
/*  324 */       xmlPerson = xml.getEntityProperties("Person", req);
/*  325 */       xmlAddress = xml.getEntityProperties("Address", req);
/*  326 */       xmlMoneyDonation = xml.getEntityProperties("MoneyDonation", req);
/*      */ 
/*  328 */       req.setAttribute("xmlDispatch", xmlDispatch);
/*  329 */       req.setAttribute("xmlPerson", xmlPerson);
/*  330 */       req.setAttribute("xmlAddress", xmlAddress);
/*  331 */       req.setAttribute("xmlMoneyDonation", xmlMoneyDonation);
/*      */ 
/*  333 */       req.setAttribute("Person", xml.xmlToEntity("Person", req));
/*  334 */       req.setAttribute("Address", xml.xmlToEntity("Address", req));
/*  335 */       req.setAttribute("MoneyDonation", xml.xmlToEntity("MoneyDonation", req));
/*      */ 
/*  337 */       Entity Dispatch = xml.xmlToEntity("Dispatch", req);
/*  338 */       Dispatch.setProperty("dispatchAddress", "");
/*  339 */       Dispatch.setProperty("status", "Pending");
/*  340 */       req.setAttribute("Dispatch", Dispatch);
/*      */ 
/*  342 */       if ("moneyDonation".equals(req.getParameter("page")))
/*  343 */         url = "/jsp/dispatch/cash_donation.jsp?edit=Y&new=Y";
/*      */       else
/*  345 */         url = "/jsp/dispatch/index.jsp?edit=Y&new=Y";
/*      */     }
/*  347 */     else if ("Select".equals(action)) {
/*  348 */       String sId = req.getParameter("id");
/*  349 */       Entity Person = PersonDao.getSinglePerson(sId);
/*  350 */       Entity Address = PersonDao.getAddressByPerson(Person);
/*  351 */       if (Address == null) {
/*  352 */         Address = xml.xmlToChildEntity("Address", Person, req);
/*      */       }
/*  354 */       Entity Dispatch = new Entity("Dispatch");
/*      */ 
/*  356 */       req.setAttribute("Person", Person);
/*  357 */       req.setAttribute("Address", Address);
/*  358 */       req.setAttribute("Dispatch", xml.xmlToEntity("Dispatch", req));
/*  359 */       req.setAttribute("MoneyDonation", xml.xmlToEntity("MoneyDonation", req));
/*      */ 
/*  361 */       if ("moneyDonation".equals(req.getParameter("page")))
/*  362 */         url = "/jsp/dispatch/cash_donation.jsp?edit=Y";
/*      */       else
/*  364 */         url = "/jsp/dispatch/index.jsp?edit=Y";
/*  365 */     } else if ("View".equals(action)) {
/*  366 */       req.getSession().setAttribute("results", null);
/*  367 */       req.setAttribute("size", new Integer("0"));
/*      */ 
/*  369 */       url = "/jsp/dispatch/" + view;
/*      */     }
/*  371 */     else if (("Save Changes".equals(action)) || ("Save Changes & Print".equals(action))) {
/*  372 */       String sId = req.getParameter("id");
/*  373 */       String sKey = req.getParameter("dispatchKey");
/*  374 */       Key k1 = KeyFactory.stringToKey(sKey);
/*      */ 
/*  376 */       String dispatchAddress = req.getParameter("dispatchAddress");
/*      */ 
/*  378 */       Entity Dispatch = PersonDao.getSingleDispatch(k1);
/*  379 */       Dispatch = xml.updateEntity(Dispatch, req);
/*      */ 
/*  382 */       String truck = (String)Dispatch.getProperty("truck");
/*  383 */       String status = (String)Dispatch.getProperty("status");
/*  384 */       if ((truck != null) && (truck.trim().length() > 0) && (status.equals("Pending")))
/*  385 */         Dispatch.setProperty("status", "Assigned");
/*  386 */       Dispatch.setProperty("dispatchAddress", dispatchAddress);
/*      */ 
/*  388 */       Dispatch.setProperty("lastUpdatedBy", user.getEmail());
/*  389 */       Dispatch.setProperty("lastUpdatedDate", new Date());
/*      */ 
/*  391 */       Entity Person = PersonDao.getSinglePerson(Dispatch.getParent().getId()+"");
/*  392 */       Entity Address = PersonDao.getAddressByPerson(Person);
/*      */ 
/*  394 */       boolean success = validate(Address, "Y", req);
/*  395 */       if (!success) {
/*  396 */         req.setAttribute("Person", Person);
/*  397 */         req.setAttribute("Address", Address);
/*  398 */         req.setAttribute("Dispatch", Dispatch);
/*  399 */         url = "/jsp/dispatch/index.jsp?edit=Y";
/*      */       }
/*      */       else {
/*  402 */         PersonDao.createDispatch(Dispatch);
/*  403 */         bDispatchSaved = true;
/*  404 */         dispatchKey = KeyFactory.keyToString(Dispatch.getKey());
/*      */ 
/*  411 */         if ((truck != null) && (truck.trim().length() > 0) && (status.equals("Completed"))) {
/*  412 */           extractDonations(Dispatch, req, user);
/*      */         }
/*  414 */         if ((bPrint) && (bDispatchSaved))
/*  415 */           url = "/jsp/dispatch/print.jsp";
/*      */         else
/*  417 */           url = "/jsp/dispatch/confirmation.jsp?conf=Y";
/*      */       }
/*      */     }
/*  420 */     else if (("Save".equals(action)) || ("Save & Print".equals(action))) {
/*  421 */       String sId = req.getParameter("id");
/*      */ 
/*  423 */       Entity Person = null;
/*  424 */       Entity Address = null;
/*  425 */       Entity Dispatch = null;
/*  426 */       long newId = 0L;
/*  427 */       String confirmationNumber = "";
/*  428 */       String dispatchAddress = req.getParameter("dispatchAddress");
/*      */ 
/*  430 */       if ((sId != null) && (!sId.equals("0"))) {
/*  431 */         Person = PersonDao.getSinglePerson(sId);
/*  432 */         Address = PersonDao.getAddressByPerson(Person);
/*  433 */         if (Address == null) {
/*  434 */           Address = xml.xmlToChildEntity("Address", Person, req);
/*  435 */           Address = parseTelephone(Address, req);
/*      */         }
/*      */ 
/*  438 */         Dispatch = xml.xmlToEntity("Dispatch", req);
/*      */ 
/*  440 */         Dispatch = xml.xmlChildToParentEntity("Dispatch", Dispatch, Person);
/*      */ 
/*  443 */         Dispatch.setProperty("acceptEmailNotifications", html.cleanData(req.getParameter("acceptEmailNotifications")));
/*  444 */         Dispatch.setProperty("acceptTermsPolicies", html.cleanData(req.getParameter("acceptTermsPolicies")));
/*      */       }
/*      */       else {
/*  447 */         Person = xml.xmlToEntity("Person", req);
/*  448 */         Address = xml.xmlToEntity("Address", req);
/*  449 */         Address = parseTelephone(Address, req);
/*  450 */         Dispatch = xml.xmlToEntity("Dispatch", req);
/*  451 */         Dispatch.setProperty("acceptEmailNotifications", html.cleanData(req.getParameter("acceptEmailNotifications")));
/*  452 */         Dispatch.setProperty("acceptTermsPolicies", html.cleanData(req.getParameter("acceptTermsPolicies")));
/*      */       }
/*      */ 
/*  455 */       req.setAttribute("Person", Person);
/*  456 */       req.setAttribute("Address", Address);
/*  457 */       req.setAttribute("Dispatch", Dispatch);
/*      */ 
/*  459 */       boolean success = validate(Address, "N", req);
/*  460 */       if (!success) {
/*  461 */         url = "/jsp/dispatch/index.jsp?edit=Y";
/*      */       } else {
/*  463 */         String zipcode = (String)Address.getProperty("zipcode");
/*  464 */         Dispatch.setProperty("status", "Pending");
/*      */ 
/*  466 */         if ("No".equals((String)Dispatch.getProperty("elevatorFlag"))) {
/*  467 */           Dispatch.setProperty("floorNumber", "1");
/*      */         }
/*  469 */         if (Person.getKey().getId() != 0L)
/*      */         {
/*  473 */           Entity newDispatch = xml.xmlChildToParentEntity("Dispatch", Dispatch, Person);
/*  474 */           newDispatch.setProperty("creationDate", new Date());
/*  475 */           newDispatch.setProperty("createdBy", user.getNickname());
/*  476 */           newDispatch.setProperty("firstName", Person.getProperty("firstName"));
/*  477 */           newDispatch.setProperty("lastName", Person.getProperty("lastName"));
/*  478 */           newDispatch.setProperty("middleInitial", Person.getProperty("middleInitial"));
/*  479 */           newDispatch.setProperty("farmBase", (String)req.getSession().getAttribute("farmBase"));
/*  480 */           newDispatch.setProperty("zipcode", zipcode);
/*  481 */           newDispatch.setProperty("status", "Pending");
/*  482 */           newDispatch.setProperty("dispatchType", "Donation");
/*  483 */           newDispatch.setProperty("dispatchAddress", dispatchAddress);
/*      */ 
/*  485 */           newDispatch.setProperty("acceptEmailNotifications", html.cleanData(req.getParameter("acceptEmailNotifications")));
/*  486 */           newDispatch.setProperty("acceptTermsPolicies", html.cleanData(req.getParameter("acceptTermsPolicies")));
/*      */ 
/*  488 */           Entity disp = PersonDao.createDispatch(newDispatch);
/*      */ 
/*  493 */           newId = disp.getKey().getId();
/*  494 */           int julianDay = Calendar.getInstance().get(6);
/*  495 */           Date now = new Date();
/*      */ 
/*  497 */           confirmationNumber = Person.getKey().getId()+"";
/*  498 */           disp.setProperty("confirmationNumber", confirmationNumber);
/*  499 */           PersonDao.createDispatch(disp);
/*      */ 
/*  501 */           bDispatchSaved = true;
/*      */ 
/*  504 */           String email = (String)Address.getProperty("email");
/*  505 */           if ((!"NA".equals(email)) && (!"na".equals(email)) && (!"".equals(email)) && (email != null)) {
/*  506 */             String body = "Thank you " + (String)Person.getProperty("firstName") + " " + (String)Person.getProperty("lastName") + 
/*  507 */               " for scheduling a pickup for your donation to Faith Farm Ministries of " + farm + ". Your confirmation number is <b>" + confirmationNumber + "</b>." + 
/*  508 */               "  Should you need to make changes or reschedule your appointment please give us a call at " + dispatchPhoneNumber + " between the hours of 9:00am and 5:00pm, Monday thru Saturday." + 
/*  509 */               "<br><br>";
/*  510 */             body = body + formEmail(Address, Person, newDispatch, xmlDispatch);
/*      */             try
/*      */             {
/*  517 */               logger.log(Level.SEVERE, "Sending Email to " + (String)Address.getProperty("email"));
/*  518 */               Mailer.sendNewStudentMail((String)Address.getProperty("email"), "Faith Farm Donation Pickup Confirmation", body);
/*  519 */               logger.log(Level.SEVERE, "Sent");
/*      */             }
/*      */             catch (Exception e) {
/*  522 */               logger.log(Level.SEVERE, e.getMessage());
/*      */             }
/*      */           }
/*      */           try
/*      */           {
/*  527 */             dispatchKey = KeyFactory.keyToString(disp.getKey());
/*      */           } catch (IllegalArgumentException e) {
/*  529 */             dispatchKey = null;
/*      */           }
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/*  536 */           Person.setProperty("personStatus", "Active");
/*  537 */           Person.setProperty("createdBy", user.getNickname());
/*  538 */           Person.setProperty("creationDate", new Date());
/*  539 */           Person.setProperty("farmBase", (String)req.getSession().getAttribute("farmBase"));
/*      */ 
/*  541 */           Entity newPerson = PersonDao.createDispatchPerson(Person, "Donor", user);
/*      */ 
/*  543 */           Address = parseTelephone(Address, req);
/*  544 */           Address.setProperty("createdBy", user.getNickname());
/*  545 */           Address.setProperty("creationDate", new Date());
/*  546 */           PersonDao.createAddress(Address, newPerson);
/*  547 */           Entity newDispatch = xml.xmlChildToParentEntity("Dispatch", Dispatch, newPerson);
/*  548 */           newDispatch.setProperty("createdBy", user.getNickname());
/*  549 */           newDispatch.setProperty("creationDate", new Date());
/*  550 */           newDispatch.setProperty("firstName", Person.getProperty("firstName"));
/*  551 */           newDispatch.setProperty("lastName", Person.getProperty("lastName"));
/*  552 */           newDispatch.setProperty("middleInitial", Person.getProperty("middleInitial"));
/*  553 */           newDispatch.setProperty("zipcode", zipcode);
/*  554 */           newDispatch.setProperty("status", "Pending");
/*  555 */           newDispatch.setProperty("farmBase", (String)req.getSession().getAttribute("farmBase"));
/*  556 */           newDispatch.setProperty("dispatchType", "Donation");
/*  557 */           newDispatch.setProperty("dispatchAddress", dispatchAddress);
/*  558 */           newDispatch.setProperty("acceptEmailNotifications", html.cleanData(req.getParameter("acceptEmailNotifications")));
/*  559 */           newDispatch.setProperty("acceptTermsPolicies", html.cleanData(req.getParameter("acceptTermsPolicies")));
/*      */ 
/*  561 */           Entity disp = PersonDao.createDispatch(newDispatch);
/*      */ 
/*  563 */           newId = disp.getKey().getId();
/*  564 */           int julianDay = Calendar.getInstance().get(6);
/*  565 */           Date now = new Date();
/*      */ 
/*  567 */           confirmationNumber = newPerson.getKey().getId()+"";
/*      */ 
/*  569 */           disp.setProperty("confirmationNumber", confirmationNumber);
/*  570 */           PersonDao.createDispatch(disp);
/*      */ 
/*  573 */           String email = (String)Address.getProperty("email");
/*  574 */           if ((!"NA".equals(email)) && (!"na".equals(email)) && (!"".equals(email)) && (email != null)) {
/*  575 */             String body = "Thank you " + (String)Person.getProperty("firstName") + " " + (String)Person.getProperty("lastName") + 
/*  576 */               " for scheduling a pickup for your donation to Faith Farm Ministries of " + farm + ". Your confirmation number is <b>" + confirmationNumber + "</b>." + 
/*  577 */               "  Should you need to make changes or reschedule your appointment please give us a call at " + dispatchPhoneNumber + " between the hours of 9:00am and 5:00pm, Monday thru Saturday." + 
/*  578 */               "<br><br>";
/*  579 */             body = body + formEmail(Address, Person, newDispatch, xmlDispatch);
/*      */             try
/*      */             {
/*  583 */               logger.log(Level.SEVERE, "Sending Email to " + (String)Address.getProperty("email"));
/*  584 */               Mailer.sendNewStudentMail((String)Address.getProperty("email"), "Faith Farm Donation Pickup Confirmation", body);
/*  585 */               logger.log(Level.SEVERE, "Sent");
/*      */             }
/*      */             catch (Exception e) {
/*  588 */               logger.log(Level.SEVERE, e.getMessage());
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/*  593 */           bDispatchSaved = true;
/*      */           try {
/*  595 */             dispatchKey = KeyFactory.keyToString(disp.getKey());
/*      */           } catch (IllegalArgumentException e) {
/*  597 */             dispatchKey = null;
/*      */           }
/*      */         }
/*      */ 
/*  601 */         req.setAttribute("Person", new Entity("Person"));
/*  602 */         req.setAttribute("Address", new Entity("Address"));
/*  603 */         req.setAttribute("Dispatch", new Entity("Dispatch"));
/*      */ 
/*  605 */         req.setAttribute("confirmation", confirmationNumber);
/*      */ 
/*  607 */         url = "/jsp/dispatch/confirmation.jsp";
/*      */       }
/*      */     }
/*  610 */     else if ("Sort".equals(action)) {
/*  611 */       ArrayList list = new ArrayList();
/*      */ 
/*  613 */       String direction = req.getParameter("sortDirection");
/*  614 */       if (("null".equals(direction)) || ("descending".equals(direction))) {
/*  615 */         list = ApplicationDao.getEntities("Dispatch", setSortParameters(req), req.getParameter("property"), "descending", (String)req.getSession().getAttribute("farmBase"));
/*      */ 
/*  619 */         direction = "ascending";
/*      */       } else {
/*  621 */         list = ApplicationDao.getEntities("Dispatch", setSortParameters(req), req.getParameter("property"), "ascending", (String)req.getSession().getAttribute("farmBase"));
/*      */ 
/*  625 */         direction = "descending";
/*      */       }
/*  627 */       req.getSession().setAttribute("results", list);
/*  628 */       req.setAttribute("size", Integer.valueOf(list.size()));
/*  629 */       if (list.size() > 199)
/*  630 */         req.setAttribute("message", "The maximum number of results returned was reached.  Please refine your search by using the filter above.");
/*  631 */       url = "/jsp/dispatch/" + view;
/*      */ 
/*  633 */       url = "/jsp/dispatch/" + view + "?sortDirection=" + direction;
/*      */     }
/*  635 */     else if ("Save Call".equals(action)) {
/*  636 */       ArrayList messages = new ArrayList();
/*  637 */       Validator validator = new Validator();
/*      */ 
/*  639 */       messages = validator.validateRequired("call date", req.getParameter("callDate"), messages);
/*  640 */       messages = validator.validateRequired("call type", req.getParameter("callType"), messages);
/*  641 */       messages = validator.validateRequired("how did you hear about us?", req.getParameter("farmSource"), messages);
/*      */ 
/*  643 */       if (messages.size() > 0) {
/*  644 */         req.setAttribute("messages", messages);
/*  645 */         url = "/jsp/dispatch/call_log.jsp";
/*      */       } else {
/*  647 */         Entity CallLog = new Entity("CallLog");
/*  648 */         CallLog.setProperty("callDate", req.getParameter("callDate"));
/*  649 */         CallLog.setProperty("callType", req.getParameter("callType"));
/*  650 */         CallLog.setProperty("source", req.getParameter("farmSource"));
/*  651 */         CallLog.setProperty("farmBase", (String)req.getSession().getAttribute("farmBase"));
/*  652 */         CallLog.setProperty("operator", user.getNickname());
/*  653 */         ApplicationDao.createOrUpdateEntity(CallLog, user);
/*  654 */         url = "/jsp/main.jsp";
/*      */       }
/*      */     }
/*  657 */     else if ("Print".equals(action)) {
/*  658 */       Key k1 = KeyFactory.stringToKey(req.getParameter("dispatchKey"));
/*  659 */       Entity Dispatch = PersonDao.getSingleDispatch(k1);
/*  660 */       Key key = Dispatch.getParent();
/*  661 */       Entity Person = Util.findEntity(key);
/*  662 */       Entity Address = PersonDao.getAddressByPerson(Person);
/*  663 */       if (Address == null) {
/*  664 */         Address = xml.xmlToChildEntity("Address", Person, req);
/*      */       }
/*  666 */       req.setAttribute("Person", Person);
/*  667 */       req.setAttribute("Address", Address);
/*  668 */       req.setAttribute("Dispatch", Dispatch);
/*  669 */       NodeList xmlDisp = xml.getEntityProperties("Dispatch", req);
/*  670 */       req.setAttribute("xmlDispatch", xmlDisp);
/*  671 */       url = "/jsp/dispatch/print.jsp";
/*      */     }
/*  673 */     else if ("Print All".equals(action)) {
/*  674 */       xmlDispatch = xml.getEntityProperties("Dispatch", req);
/*  675 */       xmlPerson = xml.getEntityProperties("Person", req);
/*  676 */       xmlAddress = xml.getEntityProperties("Address", req);
/*  677 */       req.setAttribute("xmlDispatch", xmlDispatch);
/*  678 */       req.setAttribute("xmlPerson", xmlPerson);
/*  679 */       req.setAttribute("xmlAddress", xmlAddress);
/*      */ 
/*  681 */       url = "/jsp/dispatch/print_all_dispatches.jsp";
/*  682 */     } else if ("Save Trucks".equals(action)) {
/*  683 */       String zoneDate = req.getParameter("zoneDate");
/*      */       try
/*      */       {
/*  686 */         for (int zone = 1; zone < 8; zone++)
/*      */         {
/*  688 */           String truck = req.getParameter("truck" + zone);
/*      */ 
/*  690 */           Query q1 = new Query("Zipcode");
/*  691 */           q1.addFilter("zone", Query.FilterOperator.EQUAL, zone);
/*  692 */           PreparedQuery pq1 = datastore.prepare(q1);
/*  693 */           ArrayList zipcodes = ApplicationTools.convertIterableToList(pq1.asIterable(FetchOptions.Builder.withLimit(100).chunkSize(100)));
/*      */ 
/*  695 */           for (int a = 0; a < zipcodes.size(); a++) {
/*  696 */             Entity Zipcode = (Entity)zipcodes.get(a);
/*  697 */             String zip = (String)Zipcode.getProperty("zipcode");
/*      */ 
/*  700 */             Query q2 = new Query("Dispatch");
/*  701 */             q2.addFilter("zipcode", Query.FilterOperator.EQUAL, zip);
/*  702 */             q2.addFilter("dispatchDate", Query.FilterOperator.EQUAL, zoneDate);
/*  703 */             PreparedQuery pq2 = datastore.prepare(q2);
/*  704 */             ArrayList results = ApplicationTools.convertIterableToList(pq2.asIterable(FetchOptions.Builder.withLimit(200).chunkSize(200)));
/*      */ 
/*  706 */             for (int i = 0; i < results.size(); i++) {
/*  707 */               Entity entity = (Entity)results.get(i);
/*  708 */               entity.setProperty("truck", truck);
/*  709 */               entity.setProperty("status", "Assigned");
/*  710 */               ApplicationDao.createOrUpdateEntity(entity, user);
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  722 */         logger.log(Level.SEVERE, e.getMessage());
/*      */       }
/*      */ 
/*  726 */       url = "/jsp/main.jsp";
/*      */     }
/*  728 */     else if ("Fill Trucks".equals(action)) {
/*  729 */       fillTrucks(req, user);
/*  730 */       url = "/jsp/main.jsp";
/*      */     }
/*      */ 
/*  733 */     if ((bPrint) && (bDispatchSaved))
/*      */     {
/*  735 */       Key k1 = KeyFactory.stringToKey(dispatchKey);
/*  736 */       Entity Dispatch = PersonDao.getSingleDispatch(k1);
/*  737 */       Key key = Dispatch.getParent();
/*  738 */       Entity Person = Util.findEntity(key);
/*  739 */       Entity Address = PersonDao.getAddressByPerson(Person);
/*  740 */       if (Address == null) {
/*  741 */         Address = xml.xmlToChildEntity("Address", Person, req);
/*      */       }
/*  743 */       req.setAttribute("Person", Person);
/*  744 */       req.setAttribute("Address", Address);
/*  745 */       req.setAttribute("Dispatch", Dispatch);
/*  746 */       NodeList xmlDisp = xml.getEntityProperties("Dispatch", req);
/*  747 */       req.setAttribute("xmlDispatch", xmlDisp);
/*  748 */       url = "/jsp/dispatch/print.jsp";
/*      */     }
/*      */ 
/*  751 */     req.getRequestDispatcher(req.getContextPath() + url).forward(req, resp);
/*      */   }
/*      */ 
/*      */   protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
/*      */   {
/*  756 */     doGet(req, resp);
/*      */   }
/*      */ 
/*      */   private Entity parseTelephone(Entity entity, HttpServletRequest req)
/*      */   {
/*  762 */     String homePhone1 = html.cleanData(req.getParameter("homePhone_areacode"));
/*  763 */     String homePhone2 = html.cleanData(req.getParameter("homePhone_first3"));
/*  764 */     String homePhone3 = html.cleanData(req.getParameter("homePhone_last4"));
/*  765 */     String homePhone = "(" + homePhone1 + ")" + homePhone2 + "-" + homePhone3;
/*  766 */     entity.setProperty("homePhone", homePhone);
/*      */ 
/*  768 */     String cellPhone1 = html.cleanData(req.getParameter("cellPhone_areacode"));
/*  769 */     String cellPhone2 = html.cleanData(req.getParameter("cellPhone_first3"));
/*  770 */     String cellPhone3 = html.cleanData(req.getParameter("cellPhone_last4"));
/*  771 */     String cellPhone = "(" + cellPhone1 + ")" + cellPhone2 + "-" + cellPhone3;
/*  772 */     entity.setProperty("cellPhone", cellPhone);
/*      */ 
/*  774 */     String workPhone1 = html.cleanData(req.getParameter("workPhone_areacode"));
/*  775 */     String workPhone2 = html.cleanData(req.getParameter("workPhone_first3"));
/*  776 */     String workPhone3 = html.cleanData(req.getParameter("workPhone_last4"));
/*  777 */     String workPhone = "(" + workPhone1 + ")" + workPhone2 + "-" + workPhone3;
/*  778 */     entity.setProperty("workPhone", workPhone);
/*      */ 
/*  780 */     return entity;
/*      */   }
/*      */ 
/*      */   private boolean validate(Entity Address, String edit, HttpServletRequest req)
/*      */   {
/*  786 */     Validator validator = new Validator();
/*      */ 
/*  788 */     ArrayList messages = new ArrayList();
/*  789 */     boolean success = false;
/*      */ 
/*  791 */     if (Address.getKey().getId() == 0L)
/*      */     {
/*  793 */       messages = validator.validateRequired("firstname", req.getParameter("firstName").trim(), messages);
/*  794 */       messages = validator.validateRequired("lastname", req.getParameter("lastName").trim(), messages);
/*  795 */       messages = validator.validateRequired("address line 1", req.getParameter("addressLine1"), messages);
/*  796 */       messages = validator.validateRequired("city", req.getParameter("city"), messages);
/*  797 */       messages = validator.validateRequired("state", req.getParameter("state"), messages);
/*  798 */       messages = validator.validateRequired("zipcode", req.getParameter("zipcode"), messages);
/*  799 */       messages = validator.validateEmail(req.getParameter("email"), messages);
/*      */     }
/*      */ 
/*  802 */     String dispatchAddress = "";
/*      */ 
/*  806 */     messages = validator.validateRequired("major intersection", req.getParameter("majorIntersection"), messages);
/*  807 */     messages = validator.validateRequired("street suffix", req.getParameter("streetSuffix"), messages);
/*  808 */     messages = validator.validateRequired("type of structure", req.getParameter("structureType"), messages);
/*      */ 
/*  810 */     String structureType = req.getParameter("structureType");
/*      */ 
/*  812 */     if (("apartment".equals(structureType)) || ("condominium".equals(structureType)) || ("townhome".equals(structureType)) || ("assisted living facility".equals(structureType))) {
/*  813 */       messages = validator.validateRequired("elevator availability", req.getParameter("elevatorFlag"), messages);
/*  814 */       messages = validator.validateRequired("building number", req.getParameter("buildingNumber"), messages);
/*  815 */       messages = validator.validateRequired("unit number", req.getParameter("unitNumber"), messages);
/*  816 */       messages = validator.validateRequired("floor number", req.getParameter("floorNumber"), messages);
/*      */     }
/*      */ 
/*  819 */     messages = validator.validateRequired("gated community", req.getParameter("gatedCommunityFlag"), messages);
/*      */ 
/*  821 */     if ((req.getParameter("subdivision") != null) && (req.getParameter("subdivision").trim().length() > 0)) {
/*  822 */       messages = validator.validateRequired("gated community", req.getParameter("gatedCommunityFlag"), messages);
/*      */     }
/*  824 */     if ("Yes".equals(req.getParameter("gatedCommunityFlag"))) {
/*  825 */       messages = validator.validateRequired("gate instructions", req.getParameter("gateInstructions"), messages);
/*  826 */       messages = validator.validateRequired("gate code", req.getParameter("gateCode"), messages);
/*      */     }
/*  828 */     messages = validator.validateRequired("Is this a special?", req.getParameter("special"), messages);
/*      */ 
/*  830 */     messages = validator.validateRequired("donation/delivery location", req.getParameter("dispatchLocation"), messages);
/*  831 */     messages = validator.validateRequired("call requirements", req.getParameter("callRequirements"), messages);
/*      */ 
/*  833 */     if ("tag (donation only)".equals(req.getParameter("callRequirements"))) {
/*  834 */       messages = validator.validateRequired("item location", req.getParameter("itemLocation"), messages);
/*      */     }
/*  836 */     String clothingQty = req.getParameter("clothing");
/*  837 */     if ((clothingQty != null) && (!clothingQty.equals(""))) {
/*  838 */       messages = validator.validateRequired("clothing must be specified as bags or boxes", req.getParameter("clothingBagsBoxes"), messages);
/*      */     }
/*  840 */     String beddingQty = req.getParameter("bedding");
/*  841 */     if ((beddingQty != null) && (!beddingQty.equals(""))) {
/*  842 */       messages = validator.validateRequired("bedding must be specified as bags or boxes", req.getParameter("beddingBagsBoxes"), messages);
/*      */     }
/*  844 */     String booksQty = req.getParameter("books");
/*  845 */     if ((booksQty != null) && (!booksQty.equals(""))) {
/*  846 */       messages = validator.validateRequired("books must be specified as bags or boxes", req.getParameter("booksBagsBoxes"), messages);
/*      */     }
/*  848 */     String miscQty = req.getParameter("miscHouseholdItems");
/*  849 */     if ((miscQty != null) && (!miscQty.equals(""))) {
/*  850 */       messages = validator.validateRequired("misc. items must be specified as bags or boxes", req.getParameter("miscBagsBoxes"), messages);
/*      */     }
/*  852 */     String mattressBoxSpring = req.getParameter("mattressBoxSpring");
/*  853 */     if ((mattressBoxSpring != null) && (!mattressBoxSpring.equals(""))) {
/*  854 */       messages = validator.validateRequired("mattress/box spring size must be specified", req.getParameter("mattressSize"), messages);
/*      */     }
/*  856 */     String television = req.getParameter("television");
/*  857 */     if ((television != null) && (!television.equals(""))) {
/*  858 */       messages = validator.validateRequired("television size must be specified", req.getParameter("televisionSize"), messages);
/*      */     }
/*  860 */     String dispatchStatus = req.getParameter("status");
/*  861 */     if (((dispatchStatus == null) || (dispatchStatus.equals(""))) && ("Y".equals(edit))) {
/*  862 */       messages = validator.validateRequired("dispatch status must be specified", req.getParameter("status"), messages);
/*      */     }
/*  864 */     if ("10/5".equals(dispatchStatus)) {
/*  865 */       String notes = req.getParameter("notes");
/*  866 */       if ((notes == null) || (notes.equals(""))) {
/*  867 */         messages.add("partial or total explanation must be specified for status change");
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  873 */     String dispatchDate = req.getParameter("dispatchDate");
/*  874 */     if ("N".equals(edit)) {
/*  875 */       if ((dispatchDate == null) || (dispatchDate.equals(""))) {
/*  876 */         messages.add("dispatch date is required.");
/*      */       }
/*      */     }
/*  879 */     else if ("Reschedule".equals(dispatchStatus)) {
/*  880 */       if ((dispatchDate == null) || (dispatchDate.equals("")))
/*  881 */         messages.add("dispatch date must be entered. If date is unknown change status to Pending.");
/*      */     }
/*  883 */     else if ((!"Reschedule".equals(dispatchStatus)) && (!"Pending".equals(dispatchStatus)) && (!"Cancelled By Donor".equals(dispatchStatus))) {
/*  884 */       messages = validator.validateRequired("dispatch date", req.getParameter("dispatchDate"), messages);
/*      */     }
/*      */ 
/*  888 */     if ((!"Y".equals(edit)) && 
/*  889 */       (checkDailyLimit(req))) {
/*  890 */       messages.add("The maximum # of dispatches for " + dispatchDate + " has been reached.");
/*      */     }
/*  892 */     if (!"Yes".equals(req.getParameter("acceptTermsPolicies"))) {
/*  893 */       messages.add("You must acknowledge that you have read Faith Farm's delivery/donation terms and policies to the customer.");
/*      */     }
/*  895 */     if (("".equals(req.getParameter("acceptEmailNotifications"))) || (req.getParameter("acceptEmailNotifications") == null)) {
/*  896 */       messages.add("You must ask the customer if they would like to receive notifications via email.");
/*      */     }
/*  898 */     if (messages.size() > 0) {
/*  899 */       req.setAttribute("messages", messages);
/*  900 */       success = false;
/*      */     } else {
/*  902 */       success = true;
/*      */     }
/*  904 */     return success;
/*      */   }
/*      */ 
/*      */   private boolean checkDailyLimit(HttpServletRequest req)
/*      */   {
/*  914 */     String dayToCheck = req.getParameter("dispatchDate");
/*  915 */     if ((dayToCheck == null) || (dayToCheck.equals("")))
/*      */     {
/*  917 */       DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
/*  918 */       Date date = new Date();
/*  919 */       dayToCheck = formatter.format(date);
/*      */     }
/*  921 */     SearchParameter param1 = new SearchParameter("dispatchDate", dayToCheck, Query.FilterOperator.EQUAL);
/*  922 */     ArrayList parameters = new ArrayList();
/*  923 */     parameters.add(param1);
/*      */ 
/*  925 */     ArrayList list = ApplicationDao.getEntities("DispatchLevel", parameters, null, null, (String)req.getSession().getAttribute("farmBase"));
/*  926 */     Entity DispatchLevel = new Entity("DispatchLevel");
/*  927 */     if ((list != null) && (list.size() > 0)) {
/*  928 */       DispatchLevel = (Entity)list.get(list.size() - 1);
/*      */     }
/*  930 */     String sLimit = (String)DispatchLevel.getProperty("limit");
/*      */ 
/*  932 */     int limit = 65;
/*      */ 
/*  934 */     if (sLimit != null) {
/*  935 */       limit = Integer.parseInt(sLimit);
/*      */     }
/*      */ 
/*  938 */     ArrayList dispatches = ApplicationDao.getEntities("Dispatch", parameters, null, null, (String)req.getSession().getAttribute("farmBase"));
/*  939 */     int size = dispatches.size();
/*      */ 
/*  941 */     req.getSession().setAttribute("dispatch_limit", Integer.valueOf(limit));
/*  942 */     req.getSession().setAttribute("dispatch_size", new Integer(size));
/*      */ 
/*  944 */     if (size >= limit) {
/*  945 */       return true;
/*      */     }
/*  947 */     return false;
/*      */   }
/*      */ 
/*      */   private ArrayList setSearchParameters(HttpServletRequest req)
/*      */   {
/*  953 */     ArrayList parameters = new ArrayList();
/*  954 */     String confirmationNumber = html.cleanData(req.getParameter("confirmationNumber"));
/*  955 */     String firstName = html.cleanData(req.getParameter("firstName"));
/*  956 */     String middleInitial = html.cleanData(req.getParameter("middleInitial"));
/*  957 */     String lastName = html.cleanData(req.getParameter("lastName"));
/*  958 */     String status = html.cleanData(req.getParameter("status"));
/*  959 */     String zipcode = html.cleanData(req.getParameter("zipcode"));
/*  960 */     String dispatchDate = html.cleanData(req.getParameter("dispatchDate"));
/*      */ 
/*  962 */     req.getSession().setAttribute("confirmationNumber", confirmationNumber);
/*  963 */     req.getSession().setAttribute("firstName", firstName);
/*  964 */     req.getSession().setAttribute("middleInitial", middleInitial);
/*  965 */     req.getSession().setAttribute("lastName", lastName);
/*  966 */     req.getSession().setAttribute("status", status);
/*  967 */     req.getSession().setAttribute("zipcode", zipcode);
/*  968 */     req.getSession().setAttribute("dispatchDate", dispatchDate);
/*      */ 
/*  970 */     parameters.add(new SearchParameter("confirmationNumber", html.cleanData(req.getParameter("confirmationNumber")), Query.FilterOperator.EQUAL));
/*  971 */     parameters.add(new SearchParameter("firstName", html.cleanData(req.getParameter("firstName")), Query.FilterOperator.EQUAL));
/*  972 */     parameters.add(new SearchParameter("middleInitial", html.cleanData(req.getParameter("middleInitial")), Query.FilterOperator.EQUAL));
/*  973 */     parameters.add(new SearchParameter("lastName", html.cleanData(req.getParameter("lastName")), Query.FilterOperator.EQUAL));
/*  974 */     parameters.add(new SearchParameter("zipcode", html.cleanData(req.getParameter("zipcode")), Query.FilterOperator.EQUAL));
/*  975 */     parameters.add(new SearchParameter("status", html.cleanData(req.getParameter("status")), Query.FilterOperator.EQUAL));
/*  976 */     parameters.add(new SearchParameter("special", html.cleanData(req.getParameter("special")), Query.FilterOperator.EQUAL));
/*  977 */     parameters.add(new SearchParameter("dispatchDate", html.cleanData(req.getParameter("dispatchDate")), Query.FilterOperator.EQUAL));
/*      */ 
/*  979 */     return parameters;
/*      */   }
/*      */ 
/*      */   private ArrayList setSortParameters(HttpServletRequest req)
/*      */   {
/*  984 */     HttpSession session = req.getSession();
/*  985 */     ArrayList parameters = new ArrayList();
/*      */ 
/*  987 */     if (session.getAttribute("confirmationNumber") != null)
/*  988 */       parameters.add(new SearchParameter("confirmationNumber", html.cleanData((String)session.getAttribute("confirmationNumber")), Query.FilterOperator.EQUAL));
/*  989 */     if (session.getAttribute("firstName") != null)
/*  990 */       parameters.add(new SearchParameter("firstName", html.cleanData((String)session.getAttribute("firstName")), Query.FilterOperator.EQUAL));
/*  991 */     if (session.getAttribute("middleInitial") != null)
/*  992 */       parameters.add(new SearchParameter("middleInitial", html.cleanData((String)session.getAttribute("middleInitial")), Query.FilterOperator.EQUAL));
/*  993 */     if (session.getAttribute("lastName") != null)
/*  994 */       parameters.add(new SearchParameter("lastName", html.cleanData((String)session.getAttribute("lastName")), Query.FilterOperator.EQUAL));
/*  995 */     if (session.getAttribute("zipcode") != null)
/*  996 */       parameters.add(new SearchParameter("zipcode", html.cleanData((String)session.getAttribute("zipcode")), Query.FilterOperator.EQUAL));
/*  997 */     if (session.getAttribute("status") != null)
/*  998 */       parameters.add(new SearchParameter("status", html.cleanData((String)session.getAttribute("status")), Query.FilterOperator.EQUAL));
/*  999 */     if (session.getAttribute("special") != null)
/* 1000 */       parameters.add(new SearchParameter("special", html.cleanData((String)session.getAttribute("special")), Query.FilterOperator.EQUAL));
/* 1001 */     if (session.getAttribute("dispatchDate") != null) {
/* 1002 */       parameters.add(new SearchParameter("dispatchDate", html.cleanData((String)session.getAttribute("dispatchDate")), Query.FilterOperator.EQUAL));
/*      */     }
/* 1004 */     return parameters;
/*      */   }
/*      */ 
/*      */   private void fillTrucks(HttpServletRequest req, User user)
/*      */   {
/* 1009 */     String zoneDate = req.getParameter("zoneDate");
/* 1010 */     Query q2 = new Query("Dispatch");
/* 1011 */     q2.addFilter("dispatchDate", Query.FilterOperator.EQUAL, zoneDate);
/* 1012 */     q2.addFilter("status", Query.FilterOperator.EQUAL, "Assigned");
/* 1013 */     PreparedQuery pq2 = datastore.prepare(q2);
/*      */ 
/* 1015 */     ArrayList results = ApplicationTools.convertIterableToList(pq2.asIterable(FetchOptions.Builder.withLimit(200).chunkSize(200)));
/*      */ 
/* 1017 */     for (int i = 0; i < results.size(); i++) {
/* 1018 */       Entity entity = (Entity)results.get(i);
/* 1019 */       extractDonations(entity, req, user);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void extractDonations(Entity Dispatch, HttpServletRequest req, User user)
/*      */   {
/* 1027 */     NodeList xmlDispatch = xml.getEntityProperties("Dispatch", req);
/*      */ 
/* 1029 */     int quantity = 0;
/* 1030 */     for (int i = 18; i < xmlDispatch.getLength() - 1; i++) {
/* 1031 */       quantity = 0;
/* 1032 */       Element propertyElement = (Element)xmlDispatch.item(i);
/* 1033 */       String name = propertyElement.getAttribute("name").toString();
/*      */ 
/* 1035 */       if ((Dispatch.getProperty(name) != null) && (!Dispatch.getProperty(name).equals(""))) {
/*      */         try
/*      */         {
/* 1038 */           quantity = Integer.parseInt(Dispatch.getProperty(name).toString());
/*      */         } catch (NumberFormatException e) {
/* 1040 */           quantity = 0;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1045 */       if (quantity > 0)
/*      */       {
/* 1047 */         for (int a = 0; a < quantity; a++) {
/* 1048 */           Entity DonationQueue = new Entity("DonationQueue");
/* 1049 */           String truck = "N/A";
/* 1050 */           if (Dispatch.getProperty("truck") != null)
/* 1051 */             truck = Dispatch.getProperty("truck").toString();
/* 1052 */           DonationQueue.setProperty("dispatchKey", Dispatch.getKey());
/* 1053 */           DonationQueue.setProperty("truck", truck);
/* 1054 */           DonationQueue.setProperty("arrivedDate", Dispatch.getProperty("dispatchDate").toString());
/* 1055 */           DonationQueue.setProperty("itemStatus", "Onboard");
/* 1056 */           DonationQueue.setProperty("farmBase", (String)Dispatch.getProperty("farmBase"));
/* 1057 */           DonationQueue.setProperty(name, Integer.valueOf(1));
/* 1058 */           ApplicationDao.createOrUpdateEntity(DonationQueue, user);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public String formEmail(Entity Address, Entity Person, Entity Dispatch, NodeList xmlDispatch)
/*      */   {
/* 1068 */     String message = "";
/*      */ 
/* 1070 */     message = message + "<b>CUSTOMER ORDER</b><br/>";
/* 1071 */     message = message + Person.getProperty("firstName") + "&nbsp;" + Person.getProperty("middleInitial") + "&nbsp;" + Person.getProperty("lastName") + "<br/>";
/* 1072 */     message = message + Address.getProperty("addressLine1") + "<br/>";
/* 1073 */     message = message + Address.getProperty("city") + "&nbsp;" + Address.getProperty("state") + "&nbsp;" + Address.getProperty("zipcode");
/* 1074 */     message = message + "<br>CONFIRMATION #:<b>" + Dispatch.getProperty("confirmationNumber") + "</b><br/>";
/* 1075 */     message = message + "DISPATCH DATE:" + Dispatch.getProperty("dispatchDate") + "<br/>";
/* 1076 */     message = message + "LOCATION:" + Dispatch.getProperty("dispatchLocation") + "<br/>";
/* 1077 */     message = message + "<br><br>QTY    ITEM<br>";
/* 1078 */     message = message + "--------------------------------";
/* 1079 */     message = message + "<br>";
/*      */ 
/* 1081 */     for (int i = 17; i < xmlDispatch.getLength() - 1; i += 2) {
/* 1082 */       Element propertyElement1 = (Element)xmlDispatch.item(i);
/* 1083 */       String name1 = propertyElement1.getAttribute("name").toString();
/* 1084 */       String display1 = propertyElement1.getAttribute("display").toString();
/*      */ 
/* 1086 */       Element propertyElement2 = (Element)xmlDispatch.item(i + 1);
/* 1087 */       String name2 = propertyElement2.getAttribute("name").toString();
/* 1088 */       String display2 = propertyElement2.getAttribute("display").toString();
/*      */ 
/* 1090 */       String value1 = (String)Dispatch.getProperty(name1);
/* 1091 */       String value2 = (String)Dispatch.getProperty(name2);
/* 1092 */       if (("bedding".equals(name1)) || ("books".equals(name1)) || ("clothing".equals(name1)) || ("television".equals(name1))) {
/* 1093 */         value1 = value1 + "          " + value2;
/* 1094 */         display2 = "";
/* 1095 */         value2 = "";
/*      */       }
/*      */ 
/* 1098 */       if (value1.trim().length() > 0) {
/* 1099 */         message = message + value1 + "   " + display1;
/* 1100 */         message = message + "<br/>";
/*      */       }
/* 1102 */       if (value2.trim().length() > 0) {
/* 1103 */         message = message + value2 + "   " + display2;
/* 1104 */         message = message + "<br/>";
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1109 */     if (((String)Dispatch.getProperty("notes")).trim().length() > 0)
/* 1110 */       message = message + "Notes: " + (String)Dispatch.getProperty("notes");
/* 1111 */     message = message + "<br><br>";
/* 1112 */     message = message + "<b>IMPORTANT PLEASE READ</b><br/>";
/* 1113 */     message = message + "This slip will serve as your receipt in case you wish to use your contribution for purposes of<br/>";
/* 1114 */     message = message + "Income Tax reduction. The Internal Revenue Department informs us that the contributor can estimate<br/>";
/* 1115 */     message = message + "the fair market value of his/her contribution.  No goods or services were received in exchange for this<br/>";
/* 1116 */     message = message + "donation.";
/* 1117 */     message = message + "<br/><br/>";
/* 1118 */     message = message + "<b>A WORK IN YOUR COMMUNITY BRINGING THE LIGHT OF CHRIST TO THOSE IN MORE NEED THAN OURSELVES</B>";
/*      */ 
/* 1120 */     return message;
/*      */   }
/*      */ }

/* Location:           C:\development\workspace\faithfarmoss\WEB-INF\classes\
 * Qualified Name:     org.faithfarm.DispatchServlet
 * JD-Core Version:    0.6.2
 */