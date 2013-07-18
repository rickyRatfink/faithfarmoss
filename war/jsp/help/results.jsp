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
String ticketDate=html.cleanData((String)session.getAttribute("ticketDate"));
String status=html.cleanData((String)session.getAttribute("status"));
String priority=html.cleanData((String)session.getAttribute("priority"));
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
              Help Desk Tickets
            </td>
          </tr>

        </table>
        <br>
        <jsp:include page="../includes/errors.jsp" flush="true"/>


        <!--
          <table width="620" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;"> <tr> <td
          colspan="6" class="results-section">Results 1-2 of 2.</td> </tr> </table>
        -->
        <form method="POST" action="<%=request.getContextPath()%>/help">
          <table width="550" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
            <tr>
              <td class="filterPerson">
                <i>
                  Filter By:
                </i>
                <table width="550" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">


                  <tr>
                    <td width="100" class="field-filter">
                      Ticket Date
                    </td>
                    <td width="130" class="field-filter">
                      Status
                    </td>
                    <td width="130" class="field-filter">
                      Priority
                    </td>
                    <td width="140" class="field-filter">
                      Farm
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <input name="ticketDate" value="<%=ticketDate%>" maxlength="12" size="12" class="tcal">
                    </td>
                    <td>
                      <%
                      ArrayList ddl = (ArrayList)session.getAttribute("ddlTicketStatus");
                      %>
                      <select name="status" class="ddlentry">
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
                      <%
                      ddl = (ArrayList)session.getAttribute("ddlTicketPriority");
                      %>
                      <select name="priority" class="ddlentry">
                        <option value="">
                        </option>
                        <%
                        if (ddl!=null) {
                          for (int j=0;j<ddl.size();j++) {
                            %>
                            <option value="<%=ddl.get(j)%>" <% if (ddl.get(j).equals(priority)) {%>selected<%}%>>

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
                    <td colspan="4" align="right">
                    </br>
                    <input type="submit" name="action" value="Filter" class="buttonBlank">
                    <input type="button" value="Clear" class="buttonBlank" onClick="javascript:clearFilter();" />
                    <%if ("Administrator".equals(userRole)||"HelpDesk".equals(userRole)) { %>
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
                       Ticket#
                    </td>
                    <td  class="resultsGrid-columnHeading" width="80">
                       Date
                    </td>
                     <td  class="resultsGrid-columnHeading" width="120">
                       Customer Name
                    </td>
                    <td  class="resultsGrid-columnHeading" width="120">
                       Problem Location
                    </td>
                    <td  class="resultsGrid-columnHeading" width="150">
                       Priority
                    </td>
                    <td  class="resultsGrid-columnHeading" width="70">
                       Status
                    </td>
                    <td  class="resultsGrid-columnHeading" width="110">
                       Assign To
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
                        <form method="POST" action="<%=request.getContextPath()%>/help">
                          <tr>
                            <td class="grid-Id">
                              <%=properties.get("ticketNumber")%>
                            </td>
                            <td class="grid-results">
                              <%=properties.get("ticketDate")%>
                            </td>
                            <td class="grid-results">
                              <%=properties.get("customerName")%>
                            </td>
                            <td class="grid-results">
                              <%=properties.get("systemLocation").toString().toUpperCase()%>
                            </td>
                            <td class="grid-results">
                              <%=properties.get("priority")%>
                            </td>
                            <td class="grid-results">
                              <%=properties.get("status")%>
                            </td>
                            <td class="grid-results">
                              <%=properties.get("assignedTo")%>
                            </td>
                           <%if ("Administrator".equals(userRole)||"HelpDesk".equals(userRole)) { %>
                            <td class="grid-buttons">
                              <input type="submit" name="action" value="Edit" class="imageButtonEdit" title="Edit Ticket" />
                              <input type="submit" name="action" value="Print" class="imageButtonPrint" title="Print Ticket" />
                              <!-- input type="submit" name="action" value="Delete" class="buttonAction"-->
                            </td>
                            <% } %>
                          </tr>
                          <input type="hidden" name="ticketKey" value="<%=KeyFactory.keyToString(result.getKey())%>" />
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
 <%if ("Administrator".equals(userRole)||"HelpDesk".equals(userRole)) { %>
          <table width="100%" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
            <tr>
              <td  align="right">
                <form method="POST" action="<%=request.getContextPath()%>/help">
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
