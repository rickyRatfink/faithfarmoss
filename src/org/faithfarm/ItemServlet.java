/*     */ package org.faithfarm;
/*     */ 
/*     */ /*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.logging.Logger;

/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;

/*     */ import org.faithfarm.dataobjects.SearchParameter;
/*     */ import org.faithfarm.datawriters.ApplicationDao;
/*     */ import org.faithfarm.utilities.FFSession;
/*     */ import org.faithfarm.utilities.HTMLBuilder;
/*     */ import org.faithfarm.utilities.XMLParser;
/*     */ import org.faithfarm.validators.Validator;
/*     */ import org.w3c.dom.NodeList;

import com.google.appengine.api.datastore.Entity;
/*     */ import com.google.appengine.api.datastore.Key;
/*     */ import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
/*     */ import com.google.appengine.api.users.User;
/*     */ import com.google.appengine.api.users.UserService;
/*     */ import com.google.appengine.api.users.UserServiceFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemServlet extends BaseServlet
/*     */ {
/*  32 */   private static final Logger logger = Logger.getLogger(ControllerServlet.class.getCanonicalName());
/*  33 */   private static final HTMLBuilder html = new HTMLBuilder();
/*  34 */   private static final XMLParser xml = new XMLParser();
/*  35 */   private static final ApplicationDao dao = new ApplicationDao();
/*  36 */   private static final Validator validator = new Validator();
/*     */ 
/*     */   protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
/*     */   {
/*  40 */     super.doGet(req, resp);
/*     */ 
/*  42 */     HttpSession session = req.getSession(true);
/*  43 */     UserService userService = UserServiceFactory.getUserService();
/*  44 */     User user = userService.getCurrentUser();
/*     */ 
/*  46 */     if (FFSession.needsRedirect(req, resp)) {
/*  47 */       return;
/*     */     }
/*     */ 
/*  50 */     String url = "/jsp/item/results.jsp";
/*  51 */     String farmBase = (String)session.getAttribute("farmBase");
/*  52 */     String action = (String)req.getAttribute("action");
/*  53 */     if (action == null) {
/*  54 */       action = req.getParameter("action");
/*     */     }
/*     */ 
/*  57 */     if ("func:setItemTypes".equals(action))
/*  58 */       ApplicationDao.addItemTypeToExistingEntities(user, req);
/*  59 */     if ("Index".equals(action)) {
/*  60 */       session.setAttribute("results", null);
/*  61 */     } else if ("Levels".equals(action)) {
/*  62 */       String sKey = req.getParameter("itemKey");
/*  63 */       Key key = KeyFactory.stringToKey(sKey);
/*  64 */       Entity Item = ApplicationDao.getEntity(key);
/*  65 */       req.setAttribute("Item", Item);
/*  66 */       url = "/jsp/item/levels.jsp";
/*     */     }
/*  68 */     else if ("Upload Photo".equals(action)) {
/*  69 */       String sKey = req.getParameter("itemKey");
/*  70 */       Key key = KeyFactory.stringToKey(sKey);
/*  71 */       Entity Item = ApplicationDao.getEntity(key);
/*  72 */       req.setAttribute("Item", Item);
/*  73 */       url = "/jsp/item/upload.jsp";
/*     */     }
/*  75 */     else if ("Save Levels".equals(action)) {
/*  76 */       String sKey = req.getParameter("itemKey");
/*  77 */       Key key = KeyFactory.stringToKey(sKey);
/*  78 */       Entity Item = ApplicationDao.getEntity(key);
/*  79 */       Item.setProperty("qtyWarehouse", req.getParameter("qtyWarehouse"));
/*  80 */       Item.setProperty("qtyFloor", req.getParameter("qtyFloor"));
/*  81 */       Item.setProperty("qtyTagged", req.getParameter("qtyTagged"));
/*  82 */       ApplicationDao.createOrUpdateEntity(Item, user);
/*     */     }
/*  84 */     else if ("Filter".equals(action)) {
/*  85 */       String itemNumber = req.getParameter("itemNumber");
/*  86 */       if (itemNumber == null) itemNumber = "";
/*  87 */       ArrayList results = ApplicationDao.getEntities("Item", 
/*  88 */         setSearchParameters(req), "itemNumber", null, farmBase);
/*  89 */       if (results == null) results = new ArrayList();
/*  90 */       if ((results.size() == 0) && (itemNumber.length() > 0)) {
/*  91 */         results = ApplicationDao.getEntitiesThatContain("Item", "itemNumber", req.getParameter("itemNumber"), null, farmBase);
/*     */       }
/*  93 */       if (results.size() > 199) {
/*  94 */         req.setAttribute(
/*  95 */           "message", 
/*  96 */           "The maximum number of results returned was reached.  Please refine your search by using the filter above.");
/*     */       }
/*  98 */       req.getSession().setAttribute("results", results);
/*  99 */       req.setAttribute("size", Integer.valueOf(results.size()));
/*     */     }
/* 101 */     else if ("Sort".equals(action)) {
/* 102 */       ArrayList list = new ArrayList();
/* 103 */       ArrayList parameters = new ArrayList();
/*     */ 
/* 105 */       String itemNumber = (String)req.getSession().getAttribute("itemNumber");
/* 106 */       String itemType = (String)req.getSession().getAttribute("itemType");
/* 107 */       String farm = (String)req.getSession().getAttribute("farmLocation");
/* 108 */       String vendor = (String)req.getSession().getAttribute("vendor");
/*     */ 
/* 110 */       SearchParameter param = new SearchParameter("itemNumber", itemNumber, Query.FilterOperator.EQUAL);
/* 111 */       parameters.add(param);
/* 112 */       param = new SearchParameter("itemType", itemType, Query.FilterOperator.EQUAL);
/* 113 */       parameters.add(param);
/* 114 */       param = new SearchParameter("farmBase", farm, Query.FilterOperator.EQUAL);
/* 115 */       parameters.add(param);
/* 116 */       param = new SearchParameter("vendor", vendor, Query.FilterOperator.EQUAL);
/* 117 */       parameters.add(param);
/*     */ 
/* 119 */       String direction = req.getParameter("sortDirection");
/* 120 */       if (("null".equals(direction)) || ("descending".equals(direction))) {
/* 121 */         list = ApplicationDao.getEntities("Item", parameters, 
/* 122 */           req.getParameter("property"), "descending", farmBase);
/* 123 */         direction = "ascending";
/*     */       } else {
/* 125 */         list = ApplicationDao.getEntities("Item", parameters, 
/* 126 */           req.getParameter("property"), "ascending", farmBase);
/* 127 */         direction = "descending";
/*     */       }
/* 129 */       session.setAttribute("results", list);
/* 130 */       url = "/jsp/item/results.jsp?sortDirection=" + direction;
/*     */     }
/* 132 */     else if ("Clear".equals(action)) {
/* 133 */       req.getSession().setAttribute("itemNumber", "");
/* 134 */       req.getSession().setAttribute("location", "");
/* 135 */       req.getSession().setAttribute("farmLocation", "");
/* 136 */     } else if ("Create".equals(action)) {
/* 137 */       NodeList xmlItem = xml.getEntityProperties("Item", req);
/* 138 */       Entity Item = xml.xmlToEntity("Item", req);
/* 139 */       Item.setProperty("status", "Active");
/* 140 */       req.setAttribute("xmlItem", xmlItem);
/* 141 */       req.setAttribute("Item", Item);
/* 142 */       url = "/jsp/item/create.jsp";
/* 143 */     } else if ("Save".equals(action)) {
/* 144 */       NodeList xmlItem = xml.getEntityProperties("Item", req);
/* 145 */       Entity Item = xml.xmlToEntity("Item", req);
/* 146 */       if (validator.validateForm(xmlItem, req)) {
/* 147 */         ApplicationDao.createOrUpdateEntity(Item, user);
/*     */       } else {
/* 149 */         req.setAttribute("xmlItem", xmlItem);
/* 150 */         req.setAttribute("Item", Item);
/* 151 */         url = "/jsp/item/create.jsp";
/*     */       }
/* 153 */     } else if ("Edit".equals(action)) {
/* 154 */       String sKey = req.getParameter("itemKey");
/* 155 */       Key key = KeyFactory.stringToKey(sKey);
/* 156 */       Entity Item = ApplicationDao.getEntity(key);
/* 157 */       NodeList xmlItem = xml.getEntityProperties("Item", req);
/* 158 */       req.setAttribute("xmlItem", xmlItem);
/* 159 */       req.setAttribute("Item", Item);
/* 160 */       url = "/jsp/item/create.jsp?edit=Y";
/* 161 */     } else if ("Save Changes".equals(action)) {
/* 162 */       String sKey = req.getParameter("itemKey");
/* 163 */       Key key = KeyFactory.stringToKey(sKey);
/* 164 */       Entity Item = ApplicationDao.getEntity(key);
/* 165 */       NodeList xmlItem = xml.getEntityProperties("Item", req);
/* 166 */       Entity ItemEdit = xml.updateEntity(Item, req);
/* 167 */       if (validator.validateForm(xmlItem, req)) {
/* 168 */         ApplicationDao.createOrUpdateEntity(ItemEdit, user);
/*     */       } else {
/* 170 */         req.setAttribute("xmlItem", xmlItem);
/* 171 */         req.setAttribute("Item", ItemEdit);
/* 172 */         url = "/jsp/item/create.jsp?edit=Y";
/*     */       }
/*     */     }
/* 175 */     else if ("Delete".equals(action)) {
/* 176 */       String sKey = req.getParameter("itemKey");
/* 177 */       Key key = KeyFactory.stringToKey(sKey);
/* 178 */       Entity Item = ApplicationDao.getEntity(key);
/* 179 */       Item.setProperty("status", "Discontinued");
/* 180 */       ApplicationDao.createOrUpdateEntity(Item, user);
/* 181 */     } else if ("adminUpdate".equals(action))
/*     */     {
/* 183 */       ApplicationDao.addPropertyToExistingEntities("Item", 
/* 184 */         req.getParameter("property"), req.getParameter("value"), 
/* 185 */         user);
/*     */     }
/* 187 */     else if ("replaceVendor".equals(action)) {
/* 188 */       replaceProductionEntityValue(user);
/* 189 */     } else if ("replaceCreationDate".equals(action)) {
/* 190 */       replaceProductionCreationDateValue(user);
/*     */     }
/* 192 */     req.getRequestDispatcher(req.getContextPath() + url).forward(req, resp);
/*     */   }
/*     */ 
/*     */   protected void doPost(HttpServletRequest req, HttpServletResponse resp)
/*     */     throws ServletException, IOException
/*     */   {
/* 198 */     doGet(req, resp);
/*     */   }
/*     */ 
/*     */   private ArrayList setSearchParameters(HttpServletRequest req)
/*     */   {
/* 203 */     ArrayList parameters = new ArrayList();
/* 204 */     String itemNumber = html.cleanData(req.getParameter("itemNumber"));
/* 205 */     String itemType = html.cleanData(req.getParameter("itemType"));
/* 206 */     String farm = html.cleanData(req.getParameter("farmLocation"));
/* 207 */     String vendor = html.cleanData(req.getParameter("vendor"));
/*     */ 
/* 210 */     if ("".equals(farm)) {
/* 211 */       farm = (String)req.getSession().getAttribute("farmBase");
/*     */     }
/* 213 */     req.getSession().setAttribute("itemNumber", itemNumber);
/* 214 */     req.getSession().setAttribute("itemType", itemType);
/* 215 */     req.getSession().setAttribute("farmLocation", farm);
/* 216 */     req.getSession().setAttribute("vendor", vendor);
/*     */ 
/* 218 */     parameters.add(new SearchParameter("itemNumber", itemNumber, Query.FilterOperator.EQUAL));
/* 219 */     parameters.add(new SearchParameter("itemType", itemType, Query.FilterOperator.EQUAL));
/* 220 */     parameters.add(new SearchParameter("farmLocation", farm, Query.FilterOperator.EQUAL));
/* 221 */     parameters.add(new SearchParameter("vendor", vendor, Query.FilterOperator.EQUAL));
/*     */ 
/* 223 */     if (req.getSession().getAttribute("INVENTORY_LOCATION") != null) {
/* 224 */       parameters.add(new SearchParameter("location", (String)req.getSession().getAttribute("INVENTORY_LOCATION"), Query.FilterOperator.EQUAL));
/*     */     }
/*     */ 
/* 227 */     return parameters;
/*     */   }
/*     */ 
/*     */   private void replaceProductionEntityValue(User user)
/*     */   {
/* 232 */     ArrayList parameters = new ArrayList();
/* 233 */     SearchParameter param = new SearchParameter("vendor", "Ashley Furniture", Query.FilterOperator.EQUAL);
/* 234 */     parameters.add(param);
/* 235 */     ArrayList list = ApplicationDao.getEntities("Item", parameters, null, null, "Fort Lauderdale");
/*     */ 
/* 237 */     for (int i = 0; i < list.size(); i++) {
/* 238 */       Entity Item = (Entity)list.get(i);
/* 239 */       Item.setProperty("vendor", "Ashley");
/* 240 */       ApplicationDao.createOrUpdateEntity(Item, user);
/*     */     }
/*     */   }
/*     */ 
/* 244 */   private void replaceProductionCreationDateValue(User user) { ArrayList parameters = new ArrayList();
/*     */ 
/* 246 */     ArrayList list = ApplicationDao.getEntities("Item", parameters, null, null, "Fort Lauderdale", 1000);
/*     */ 
/* 248 */     for (int i = 0; i < list.size(); i++) {
/* 249 */       Entity Item = (Entity)list.get(i);
/* 250 */       Item.setProperty("creationDate", new Date("07/06/2012"));
/* 251 */       ApplicationDao.createOrUpdateEntity(Item, user);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\development\workspace\faithfarmoss\WEB-INF\classes\
 * Qualified Name:     org.faithfarm.ItemServlet
 * JD-Core Version:    0.6.2
 */