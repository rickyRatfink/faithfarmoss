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
<%@ page import="java.util.ArrayList" %>

<jsp:include page="../includes/header.jsp"/>


<jsp:include page="../includes/menu/index.jsp" flush="true"/>

<%
String required = "<font size='-2' color='red'>*</font>";

int pageLimit=100;
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
    <table width="820" border="0" cellpadding="0" cellspacing="0">
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
                Current Sales                 
                <% if (session.getAttribute("INVENTORY_LOCATION")!=null) { %>
                	(<%=session.getAttribute("INVENTORY_LOCATION")%>)
                <% } %>
              </td>
            </tr>

          </table>
          <br>
          <jsp:include page="../includes/errors.jsp" flush="true"/>


          <!--
            <table width="620" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;"> <tr> <td
            colspan="6" class="results-section">Results 1-2 of 2.</td> </tr> </table>
          -->
          <form method="POST" action="<%=request.getContextPath()%>/sales">
            <table width="700" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
              <tr>
                <td class="filterPerson">
                  <i>
                    Filter By:
                  </i>
                  <table width="700" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">


                    <tr>
                      <td width="150" class="field-filter">
                        Order#
                      </td>
                      <td width="150" class="field-filter">
                        First Name
                      </td>
                      <td width="150" class="field-filter">
                        Last Name
                      </td>
                      <td class="field-filter">
                        Delivery
                      </td>

                    </tr>
                    <tr>
                      <td>
                        <input name="orderNumber" value="" maxlength="20" size="20" class="entry">
                      </td>
                      <td>
                        <input name="firstName" value="" maxlength="30" size="20" class="entry">
                      </td>
                      <td>
                        <input name="lastName" value="" maxlength="30" size="20" class="entry">
                      </td>
                      <td>
                        <%
                        ArrayList ddl = (ArrayList)session.getAttribute("ddlYesNo");
                        %>
                        <select name="delivery" class="ddlentry">
                          <option value="">
                          </option>
                          <%
                          if (ddl!=null) {
                            for (int j=0;j<ddl.size();j++) {
                              %>
                              <option 
                                  value="<%=ddl.get(j)%>"
                                  <%
                                  if
                                  (ddl.get(j).equals(request.getParameter("delivery")))
                                  {%>selected<%}%>>

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
                      <td width="100" class="field-filter">
                        Zipcode
                      </td>
                      <td width="100" class="field-filter">
                        Status
                      </td>
                      <td colspan="2" class="field-filter">
                        Date
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <input name="zipcode" value="" maxlength="10" size="10" class="entry">
                      </td>
                      <td>
                        <%
                        ddl = (ArrayList)session.getAttribute("ddlDispatchStatus");
                        %>
                        <select name="status" class="ddlentry">
                          <option value="">
                          </option>
                          <%
                          if (ddl!=null) {
                            for (int j=0;j<ddl.size();j++) {
                              %>
                              <option 
                                  value="<%=ddl.get(j)%>"
                                  <%
                                  if
                                  (ddl.get(j).equals(request.getParameter("status")))
                                  {%>selected<%}%>>

                                <%=ddl.get(j)%>
                              </option>
                              <%
                            }
                          }
                          %>
                        </select>
                      </td>
                      <td >
                        <input name="salesOrderDate" value="" maxlength="10" size="15" class="tcal">
                      </td>
                      <td colspan="2" align="left">
                        <input type="submit" name="action" value="Filter" class="buttonBlank">
                        <input type="submit" name="action" value="Create" class="buttonBlank">
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
                        Search Results
                      </td>
                    </tr>
                    <tr>
                      <td width="780">
                        <%if (entities.size()>
                          0) { %>
                          <jsp:include page="grid.jsp" />
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

