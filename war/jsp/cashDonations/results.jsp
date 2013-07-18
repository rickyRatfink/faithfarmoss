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
    document.forms[0].elements[2].value='';
    document.forms[0].elements[3].value='';
    document.forms[0].elements[4].value='';
    document.forms[0].elements[5].value='';
  }
</script>
<%
String required = "<font size='-2' color='red'>*</font>";


//Initialize session filter variables to "" if they are null
HTMLBuilder html = new HTMLBuilder();

int pageLimit=200;
int cursor=0;

String sCursor = (String)request.getParameter("cursor");
if (sCursor!=null)
cursor=Integer.parseInt(sCursor);

ArrayList entities = (ArrayList)request.getAttribute("results");
String donationDate=html.cleanData((String)session.getAttribute("donationDate"));
String status=html.cleanData((String)session.getAttribute("status"));
String firstName=html.cleanData((String)session.getAttribute("firstName"));
String lastName=html.cleanData((String)session.getAttribute("lastName"));
String farm=html.cleanData((String)session.getAttribute("farmLocation"));
if (farm==null||farm.equals(""))
	farm=html.cleanData((String)session.getAttribute("farmBase"));
%>
<!----Main Content--->
<td valign="top">
  <table width="800" border="0" cellpadding="0" cellspacing="0">
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
              Cash Donations
            </td>
          </tr>

        </table>
        <br>
        <jsp:include page="../includes/errors.jsp" flush="true"/>


        <!--
          <table width="620" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;"> <tr> <td
          colspan="6" class="results-section">Results 1-2 of 2.</td> </tr> </table>
        -->
        <form method="POST" action="<%=request.getContextPath()%>/cash">
          <table width="750" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
            <tr>
              <td class="filterPerson">
                <i>
                  Filter By:
                </i>
                <table width="750" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">


                  <tr>
                    <td width="100" class="field-filter">
                      Confirmation#
                    </td>
                    <td width="100" class="field-filter">
                      Donation Date
                    </td>
                    <td width="130" class="field-filter">
                      Payment Method
                    </td>
                    <td width="130" class="field-filter">
                      First Name
                    </td>
                    <td width="130" class="field-filter">
                      Last Name
                    </td>
                    <td width="140" class="field-filter">
                      Farm
                    </td>
                  </tr>
                  <tr>
                    <td>
                    	<input name="confirmationNumber" size="10" maxlength="20" class="entry"/>
                    </td>
                    <td>
                      <input name="donationDate" value="<%=donationDate%>" maxlength="12" size="12" class="tcal">
                    </td>
                    <td>
                      <%
                      ArrayList ddl = (ArrayList)session.getAttribute("ddlPaymentMethod");
                      %>
                      <select name="paymentMethod" class="ddlentry">
                        <option value="">
                        </option>
                        <%
                        if (ddl!=null) {
                          for (int j=0;j<ddl.size();j++) {
                            %>
                            <option value="<%=ddl.get(j)%>" <% if (ddl.get(j).equals(status)) {%>selected<%}%>>

                              <%=ddl.get(j)%>
                            </option>
                            <%
                          }
                        }
                        %>
                      </select>
                    </td>
                    <td>
                    	<input name="firstName" size="20" maxlength="20" class="entry"/>
                    </td>
                   <td>
                    	<input name="lastName" size="20" maxlength="30" class="entry"/>
                    </td>
                    <td>
                      <%
                      ddl = (ArrayList)session.getAttribute("ddlFarms");
                      %>
                      <select name="farmLocation" class="ddlentry">
                        <option value="">
                        </option>
                        <%
                        if (ddl!=null) {
                          for (int j=0;j<ddl.size();j++) {
                            %>
                            <option value="<%=ddl.get(j)%>" <% if (ddl.get(j).equals(farm)) {%>selected<%}%>>

                              <%=ddl.get(j)%>
                            </option>
                            <%
                          }
                        }
                        %>
                      </select>
                    </td>
                  </tr>
                  <tr>
                    <td colspan="6" align="right">
                    </br>
                    <input type="submit" name="action" value="Filter" class="buttonBlank">
                    <input type="button" value="Clear" class="buttonBlank" onClick="javascript:clearFilter();" />
                    <input type="submit" name="action" value="Create" class="buttonBlank">
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
          <table width="850" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
            <tr>
              <td  class="grid">
                <table width="850" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
                  <tr>
                    <td  class="grid-heading" >
                      Search Results
                    </td>
                  </tr>
                </table>
                <table width="850" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
                  <tr>
                    <td  class="resultsGrid-columnHeading" width="50">
                       Confirmation#
                    </td>
                    <td  class="resultsGrid-columnHeading" width="80">
                       Date
                    </td>
                    <td  class="resultsGrid-columnHeading" width="80">
                       Farm
                    </td>
                     <td  class="resultsGrid-columnHeading" width="120">
                       Customer Name
                    </td>
                    <td  class="resultsGrid-columnHeading" width="120">
                       Amount
                    </td>
                    <td  class="resultsGrid-columnHeading" width="70">
                       Status
                    </td>
                    <td  class="resultsGrid-columnHeading" width="70">
                       Amount
                    </td>
                   
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
                        <form method="POST" action="<%=request.getContextPath()%>/cash">
                          <tr>
                            <td class="grid-Id">
                              <%=properties.get("confirmationNumber")%>
                            </td>
                            <td class="grid-results">
                              <%=properties.get("donationDate")%>
                            </td>
                             <td class="grid-results">
                              <%=properties.get("farmBase")%>
                            </td>
                            <td class="grid-results">
                              <%=properties.get("firstName")%>&nbsp;<%=properties.get("lastName")%>
                            </td>
                            <td class="grid-results">
                              <%=properties.get("paymentType")%>
                            </td>
                            <td class="grid-results">
                              <%=properties.get("status")%>
                            </td>
                            <td class="grid-results">
                              <%=properties.get("amount")%>
                            </td>
                            <td class="grid-buttons">
                              <input type="submit" name="action" value="Edit" class="imageButtonEdit" title="Edit Ticket" />
                              <input type="submit" name="action" value="Print" class="imageButtonPrint" title="Print Ticket" />
                              <!-- input type="submit" name="action" value="Delete" class="buttonAction"-->
                            </td>
                          </tr>
                          <input type="hidden" name="moneyDonationKey" value="<%=KeyFactory.keyToString(result.getKey())%>" />
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
          <table width="100%" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
            <tr>
              <td  align="right">
                <form method="POST" action="<%=request.getContextPath()%>/cash">
                  <input type="submit" name="action" value="Create" class="buttonBlank">
                </form>
              </td>
            </tr>
          </table>
<% } %>
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
