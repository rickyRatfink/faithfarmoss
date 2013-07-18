<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreService" %>
<%@ page import="com.google.appengine.api.datastore.Query" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ page import="org.faithfarm.utilities.HTMLBuilder" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>

<%
	//check user authentication via Google
	UserService userService = UserServiceFactory.getUserService(); 
	User user = userService.getCurrentUser();
%>


<% String userRole = (String)session.getAttribute(user.getNickname()+"_ROLE"); %>

<jsp:include page="../includes/header.jsp"/>


<jsp:include page="../includes/menu/index.jsp" flush="true"/>

<script language="javascript">
  function clearFilter() {
    document.forms[0].elements[0].value='';
    document.forms[0].elements[1].value='';
  }
</script>
<%
String required = "<font size='-2' color='red'>*</font>";


//Initialize session filter variables to "" if they are null
HTMLBuilder html = new HTMLBuilder();
String itemNumber=html.cleanData((String)session.getAttribute("itemNumber"));
String itemType=html.cleanData((String)session.getAttribute("itemType"));
String vendor=html.cleanData((String)session.getAttribute("vendor"));
String farm=html.cleanData((String)session.getAttribute("farmBase"));
String status=html.cleanData((String)session.getAttribute("status"));

int pageLimit=200;
int cursor=0;


String sCursor = (String)request.getParameter("cursor");
if (sCursor!=null)
cursor=Integer.parseInt(sCursor);

ArrayList entities = (ArrayList)session.getAttribute("results");
%>
<!----Main Content--->
<td valign="top">
  <table width="980" border="0" cellpadding="0" cellspacing="0">
    <tr>
      <td>
        <br>
        <!--img src="<%=request.getContextPath()%>/images/header_top.png"-->
      </td>
    </tr>
    <tr>
      <td class="content-body">
        <table>
          <tr>
            <td class="text1">
              Current Inventory
            </td>
          </tr>

        </table>
        <br>
        <jsp:include page="../includes/errors.jsp" flush="true"/>


        <!--
          <table width="620" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;"> <tr> <td
          colspan="6" class="results-section">Results 1-2 of 2.</td> </tr> </table>
        -->
        <form method="POST" action="<%=request.getContextPath()%>/item">
          <table width="550" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
            <tr>
              <td class="filterPerson">
                <i>
                  Filter By:
                </i>
                <table width="550" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">


                  <tr>
                    <td width="100" class="field-filter">
                      Item Number
                    </td>
                    <td width="130" class="field-filter">
                      Item Type
                    </td>
                    <td width="130" class="field-filter">
                      Vendor
                    </td>
                    <td width="140" class="field-filter">
                      Farm
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <input name="itemNumber" value="<%=itemNumber%>" maxlength="15" size="15" class="entry">
                    </td>
                    <td>
                      <%
                      ArrayList ddl = (ArrayList)session.getAttribute("ddlItemTypes");
                      %>
                      <select name="itemType" class="ddlentry">
                        <option value="">
                        </option>
                        <%
                        if (ddl!=null) {
                          for (int j=0;j<ddl.size();j++) {
                            %>
                            <option value="<%=ddl.get(j)%>" <% if (ddl.get(j).equals(itemType)) {%>selected<%}%>>

                              <%=ddl.get(j)%>
                            </option>
                            <%
                          }
                        }
                        %>
                      </select>
                    </td>
                    <td>
                      <%
                      ddl = (ArrayList)session.getAttribute("vendors");
                      %>
                      <select name="vendor" class="ddlentry">
                        <option value="">
                        </option>
                        <%
                        if (ddl!=null) {
                          for (int j=0;j<ddl.size();j++) {
                            %>
                            <option value="<%=ddl.get(j)%>" <% if (ddl.get(j).equals(vendor)) {%>selected<%}%>>

                              <%=ddl.get(j)%>
                            </option>
                            <%
                          }
                        }
                        %>
                      </select>
                    </td>
                    <td>
                      <%
                      ddl = (ArrayList)session.getAttribute("ddlFarms");
                      String searchFarm = (String)request.getSession().getAttribute("farmLocation");
                      if (searchFarm==null||"".equals(searchFarm))
                    	  searchFarm=farm;
                      %>
                      <select name="farmLocation" class="ddlentry">
                        <%
                        if (ddl!=null) {
                          for (int j=0;j<ddl.size();j++) {
                            %>
                            <option value="<%=ddl.get(j)%>" <% if (ddl.get(j).equals(searchFarm)) {%>selected<%}%>>

                              <%=ddl.get(j)%>
                            </option>
                            <%
                          }
                        }
                        %>
                      </select>
                    </td>

                    <!--
                      - <td> <% ddl = (ArrayList)session.getAttribute("ddlItemStatus"); %> <select name="status"
                      class="ddlentry"> <option value=""> </option> <% if (ddl!=null) { for (int j=0;j<ddl.size();j++) {
                      %> <option value="<%=ddl.get(j)%>" <% if (ddl.get(j).equals(status)) {%>selected<%}%>>
                      <%=ddl.get(j)%> </option> <% } } %> </select> </td> -
                    -->
                  </tr>
                  <tr>
                    <td colspan="4" align="right">
                    </br>
                    <input type="submit" name="action" value="Filter" class="buttonBlank">
                    <input type="button" value="Clear" class="buttonBlank" onClick="javascript:clearFilter();" />
                    <%if ("Administrator".equals(userRole)||"InvAdmin".equals(userRole)) { %>
						<input type="submit" name="action" value="Create" class="buttonBlank">
                    <% } %>
                    &nbsp;&nbsp;
                  </td>
                </tr>
              </table>

            </td>
          </tr>
        </table>
        <br />
      </form>
      <!-- -display results on after search -->
      <%
      if (entities!=null) {
        int size=entities.size();
        %>
        <div class="results" style="border:0;" id="customer-list-ctr">
          <jsp:include page="../includes/warning.jsp"/>
          <table width="1080" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
            <tr>
              <td  class="grid">
                <table width="1080" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
                  <tr>
                    <td  class="grid-heading" >
                      Search Results
                    </td>
                  </tr>
                </table>
                <table width="1080" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
                  <tr>
                    <td  class="grid-columnHeading" width="50">
                      <a 
                          href="<%=request.getContextPath()%>/item?action=Sort&property=itemNumber&sortDirection=<%=request.getParameter("sortDirection")%>"
                          style="text-decoration:font-family:"Courier
                          New", Courier, monospace;font-size:medium;color:#5e82a6;">Item#
                      </a>
                    </td>
                    <td  class="grid-columnHeading" width="80">
                      <a 
                          href="<%=request.getContextPath()%>/item?action=Sort&property=vendor&sortDirection=<%=request.getParameter("sortDirection")%>"
                          style="text-decoration:font-family:"Courier
                          New", Courier, monospace;font-size:medium;color:#5e82a6;">Vendor
                      </a>
                    </td>
                    <td  class="grid-columnHeading" width="110">
                      <a 
                          href="<%=request.getContextPath()%>/item?action=Sort&property=productName&sortDirection=<%=request.getParameter("sortDirection")%>"
                          style="text-decoration:font-family:"Courier
                          New", Courier, monospace;font-size:medium;color:#5e82a6;">Product Name
                      </a>
                    </td>
                    <td  class="grid-columnHeading" width="110">
                      <a 
                          href="<%=request.getContextPath()%>/item?action=Sort&property=location&sortDirection=<%=request.getParameter("sortDirection")%>"
                          style="text-decoration:font-family:"Courier
                          New", Courier, monospace;font-size:medium;color:#5e82a6;">Location
                      </a>
                    </td>
                    <td  class="grid-columnHeading" width="60">
                      <a 
                          href="<%=request.getContextPath()%>/item?action=Sort&property=qtyWarehouse&sortDirection=<%=request.getParameter("sortDirection")%>"
                          style="text-decoration:font-family:"Courier
                          New", Courier, monospace;font-size:medium;color:#5e82a6;">Warehouse
                      </a>
                    </td>
                    <td  class="grid-columnHeading" width="60">
                      <a 
                          href="<%=request.getContextPath()%>/item?action=Sort&property=qtyFloor&sortDirection=<%=request.getParameter("sortDirection")%>"
                          style="text-decoration:font-family:"Courier
                          New", Courier, monospace;font-size:medium;color:#5e82a6;">Floor
                      </a>
                    </td>
                    <td  class="grid-columnHeading" width="60">
                      <a 
                          href="<%=request.getContextPath()%>/item?action=Sort&property=qtyTagged&sortDirection=<%=request.getParameter("sortDirection")%>"
                          style="text-decoration:font-family:"Courier
                          New", Courier, monospace;font-size:medium;color:#5e82a6;">Tagged
                      </a>
                    </td>
                    <td  class="grid-columnHeading" width="60">
                      <a 
                          href="<%=request.getContextPath()%>/item?action=Sort&property=price&sortDirection=<%=request.getParameter("sortDirection")%>"
                          style="text-decoration:font-family:"Courier
                          New", Courier, monospace;font-size:medium;color:#5e82a6;">Price
                      </a>
                    </td>
                    <td  class="grid-columnHeading" width="60">
                      <a 
                          href="<%=request.getContextPath()%>/item?action=Sort&property=cost&sortDirection=<%=request.getParameter("sortDirection")%>"
                          style="text-decoration:font-family:"Courier
                          New", Courier, monospace;font-size:medium;color:#5e82a6;">Cost
                      </a>
                    </td>
                    <td  class="grid-columnHeading" width="60">
                      <a 
                          href="<%=request.getContextPath()%>/item?action=Sort&property=status&sortDirection=<%=request.getParameter("sortDirection")%>"
                          style="text-decoration:font-family:"Courier
                          New", Courier, monospace;font-size:medium;color:#5e82a6;">Status
                      </a>
                    </td>
                    <td  class="grid-columnHeading" width="60">
                      <a 
                          href="<%=request.getContextPath()%>/item?action=Sort&property=itemType&sortDirection=<%=request.getParameter("sortDirection")%>"
                          style="text-decoration:font-family:"Courier
                          New", Courier, monospace;font-size:medium;color:#5e82a6;">Type
                      </a>
                    </td>
                    <%if ("Administrator".equals(userRole)||"InvAdmin".equals(userRole)) { %>
                    <td  class="grid-columnHeading">
                    </td>
                    <% } %>
                  </tr>
                  <%
                  int row=0;

                  Map<String, Object> properties = null;
                  if (entities!=null)
                  for (int i=0;i<entities.size();i++) {
                    //for (Entity result : entities) {
                      Entity result=(Entity)entities.get(i);
                      properties = result.getProperties();
                      %>
                      <%
                      if (row==cursor) {
                        %>
                        <form method="POST" action="<%=request.getContextPath()%>/item">
                          <tr>
                            <td class="grid-Id">
                              <%=properties.get("itemNumber")%>
                            </td>
                            <td class="grid-results">
                              <%=properties.get("vendor")%>
                            </td>
                            <td class="grid-results">
                              <%=properties.get("itemName").toString().toUpperCase()%>
                            </td>
                            <td class="grid-results">
                              <%=properties.get("location")%>
                            </td>
                            <td class="grid-results">
                              <%=properties.get("qtyWarehouse")%>
                            </td>
                            <td class="grid-results">
                              <%=properties.get("qtyFloor")%>
                            </td>
                            <td class="grid-results">
                            <% if (properties.get("qtyTagged")==null) { %>
                              0
                            <% } else { %>
                              <%=properties.get("qtyTagged")%>
                            <% } %>
                            </td>
                            <td class="grid-results">
                              <%
                              String price=(String)properties.get("price");
                              if (price==null||"".equals(price)) price="0.00";
                              %>
                              <fmt:formatNumber value="<%=price%>" type="currency" />
                            </td>
                            <td class="grid-results">
                              <%
                              String cost=(String)properties.get("cost");
                              if (cost==null||"".equals(cost)) cost="0.00";
                              %>
                              <fmt:formatNumber value="<%=cost%>" type="currency" />
                            </td>
                            <td class="grid-results">
                              <%=properties.get("status")%>
                            </td>
                            <td class="grid-results">
                              <%=properties.get("itemType")%>
                            </td>
                            <%if ("Administrator".equals(userRole)||"InvAdmin".equals(userRole)) { 
								if (((String)properties.get("farmLocation")).equals(farm)) { %>
		                            <td class="grid-buttons">
		                            <!-- 
		                              <input type="submit" name="action" value="Levels" class="buttonAction">
		                              <input type="submit" name="action" value="Edit" class="buttonAction">
		                              <input type="submit" name="action" value="Delete" class="buttonAction">
		                             -->
		                              <input type="submit" name="action" value="Levels" class="imageButtonLevels" title="Adjust Levels" />
 									  <input type="submit" name="action" value="Edit" class="imageButtonEditItem" title="Edit Item" />
 									 <% if ("Donation".equals(((String)properties.get("vendor")))) { %>
 									  <input type="submit" name="action" value="Upload Photo" class="imageButtonUpload" title="Upload Item" />
 									 <% } %>
 									  <input type="submit" name="action" value="Delete" class="imageButtonDelete" title="Delete Item" />
 
		                            </td>
		                        <% } %>
                            <% } %>
                          </tr>
                          <input type="hidden" name="itemKey" value="<%=KeyFactory.keyToString(result.getKey())%>" />
                        </form>
                        <%
                        cursor++;
                        if (cursor%pageLimit==0) break;
                      }
                      row++;

                      //if (cursor%pageLimit==0) break;
                    }
                    if (cursor==0) {
                      %>
                      <tr>
                        <td class="results-bg" colspan="12">
                          no data found
                        </td>
                      </tr>
                      <%
                    }
                    %>
                  </table>
                </td>
              </tr>
            </table>
          </div>

          <br>
 <%if ("Administrator".equals(userRole)||"InvAdmin".equals(userRole)) { %>
          <table width="1080" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
            <tr>
              <td class="results-page-link" align="left">
                <form method="POST" action="<%=request.getContextPath()%>/item">
                  <input type="submit" name="action" value="Create" class="buttonBlank">
                </form>
              </td>
            </tr>
          </table>
<% } 
}
        %>
        <tr>
          <td>
            <!--img src="<%=request.getContextPath()%>/images/header_btm.png"-->
          </td>
        </tr>
      </table>
    </td>
  </tr>
</div>
</table>
<jsp:include page="../includes/footer.jsp"/>
