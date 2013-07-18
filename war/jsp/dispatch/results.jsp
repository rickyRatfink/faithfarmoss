<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreService" %>
<%@ page import="com.google.appengine.api.datastore.Query" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ page import="org.faithfarm.utilities.HTMLBuilder" %>

<%
	//check user authentication via Google
	UserService userService = UserServiceFactory.getUserService(); 
	User user = userService.getCurrentUser();
    String userRole = (String)session.getAttribute(user.getNickname()+"_ROLE");
%>



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
    document.forms[0].elements[6].value='';
    document.forms[0].elements[7].value='';

  }
</script>
<%
String required = "<font size='-2' color='red'>*</font>";


//Initialize session filter variables to "" if they are null
HTMLBuilder html = new HTMLBuilder();
String confirmationNumber=html.cleanData((String)session.getAttribute("confirmationNumber"));
String firstName=html.cleanData((String)session.getAttribute("firstName"));
String middleInitial=html.cleanData((String)session.getAttribute("middleInitial"));
String lastName=html.cleanData((String)session.getAttribute("lastName"));
String special=html.cleanData((String)session.getAttribute("special"));
String status=html.cleanData((String)session.getAttribute("status"));
String zipcode=html.cleanData((String)session.getAttribute("zipcode"));
String dispatchDate=html.cleanData((String)session.getAttribute("dispatchDate"));

int pageLimit=150;
int cursor=0,size=0;
String sCursor = (String)request.getParameter("cursor");
Integer iSize = (Integer)request.getAttribute("size");

if (iSize==null) {
  String sSize = request.getParameter("size");

  if (sSize==null)
  size=0;
  else
  size=Integer.parseInt(sSize);
  } else
  size=iSize.intValue();

  if (sCursor!=null)
  cursor=Integer.parseInt(sCursor);

  ArrayList entities = (ArrayList)session.getAttribute("results");
  %>
  <!----Main Content--->
  <td valign="top">
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
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
                Current Donation/Delivery Dispatches
              </td>
            </tr>

          </table>
          <br>
          <jsp:include page="../includes/errors.jsp" flush="true"/>

          <form method="POST" action="<%=request.getContextPath()%>/dispatch">
            <table width="480" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
              <tr>
                <td class="filterPerson">
                  <i>
                    Filter By:
                  </i>
                  <table width="590" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">


                    <tr>
                      <td width="100" class="field-filter">
                        Confirmation#
                      </td>
                      <td width="100" class="field-filter">
                        First Name
                      </td>
                      <td width="50" class="field-filter">
                        Middle Initial
                      </td>
                      <td width="100" class="field-filter">
                        Last Name
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <input 
                            name="confirmationNumber"
                            value="<%=confirmationNumber%>"
                            maxlength="20"
                            size="20"
                            class="entry">

                      </td>
                      <td>
                        <input name="firstName" value="<%=firstName%>" maxlength="30" size="20" class="entry">
                      </td>
                      <td>
                        <input name="middleInitial" value="<%=middleInitial%>" maxlength="1" size="1" class="entry">
                      </td>
                      <td>
                        <input name="lastName" value="<%=lastName%>" maxlength="30" size="20" class="entry">
                      </td>
                    </tr>
                    <tr>
                      <td width="100" class="field-filter">
                        Zipcode
                      </td>
                      <td width="100" class="field-filter">
                        Status
                      </td>
                      <td width="100" class="field-filter">
                        Special
                      </td>
                      <td class="field-filter">
                        Date
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <input name="zipcode" value="<%=zipcode%>" maxlength="10" size="10" class="entry">
                      </td>
                      <td>
                        <%
                        ArrayList ddl = (ArrayList)session.getAttribute("ddlDispatchStatus");
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
                        ddl = (ArrayList)session.getAttribute("ddlYesNo");
                        %>
                        <select name="special" class="ddlentry">
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

                        <input name="dispatchDate" value="<%=dispatchDate%>" maxlength="10" size="12" class="tcal">
                      </td>
                    </tr>
                    <tr>
                      <td colspan="4" align="right">
                      </br>
                      <input type="submit" name="action" value="Filter" class="buttonBlankMedium">
                      <input type="button" value="Clear" class="buttonBlankMedium" onClick="javascript:clearFilter();" />
                      <input type="submit" name="action" value="Create" class="buttonBlankMedium">
                      
                      <% if ("Administrator".equals(userRole)) { %>
                      <input type="submit" name="action" value="Cash Donation" class="buttonBlankMedium">
                      <% } %>
                      &nbsp;&nbsp;
                    </td>
                  </tr>
                  <tr>
                    <!--
                      <td align="left"> <input type="submit" name="action" value="Filter" class="buttonFilter"> <input
                      type="submit" name="action" value="Add" class="buttonAdd"> </td> -
                    -->
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
          %>
          <jsp:include page="../includes/warning.jsp"/>
          <table  cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
            <tr>
              <td  class="grid">
                <table cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
                  <tr>
                    <td  class="grid-heading" >
                      Search Results Search Results
                      <a 
                          href="<%=request.getContextPath()%>/jsp/dispatch/results_classic.jsp"
                          style="font-weight:normal;font-family:arial;font-size:8pt;color:#325ea0;">

                        [Classic View]
                      </a>
                    </td>
                  </tr>
                  <tr>
                    <td width="950">
                      <%if (entities.size()>
                        0) { %>
                        <jsp:include page="grid.jsp" />
                      </br>

                      <%
                    } else {
                      %>
                      No Data
                      <%
                    }
                    %>
                  </td>
                </tr>
              </table>
            </td>
          </tr>
        </table>
      </br>
      <form method="POST" action="<%=request.getContextPath()%>/dispatch">
        <input type="submit" name="action" value="Print All" class="buttonBlank"/>
      </form>

      <%
    }
    %>
  </td>
</tr>
</table>
</td>
</tr>
</div>
</table>
<jsp:include page="../includes/footer.jsp"/>
<%
session.setAttribute("view","results.jsp");
%>

