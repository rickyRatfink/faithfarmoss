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
String status=html.cleanData((String)session.getAttribute("status"));
String zipcode=html.cleanData((String)session.getAttribute("zipcode"));
String dispatchDate=html.cleanData((String)session.getAttribute("dispatchDate"));

int pageLimit=200;
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
                Current Donation/Delivery Dispatches
              </td>
            </tr>

          </table>
          <br>
          <jsp:include page="../includes/errors.jsp" flush="true"/>


          <!--
            <table width="620" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;"> <tr> <td
            colspan="6" class="results-section">Results 1-2 of 2.</td> </tr> </table>
          -->
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
                      <td colspan="2" class="field-filter">
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
                      <td colspan="2" >

                        <input name="dispatchDate" value="<%=dispatchDate%>" maxlength="10" size="12" class="tcal">
                      </td>
                    </tr>
                    <td colspan="4" align="right">
                    </br>
                    <input type="submit" name="action" value="Filter" class="buttonBlank">
                    <input type="button" value="Clear" class="buttonBlank" onClick="javascript:clearFilter();" />
                    <input type="submit" name="action" value="Create" class="buttonBlank">
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
        <div class="results" style="border:0;" id="customer-list-ctr">
          <jsp:include page="../includes/warning.jsp"/>

          <table width="950" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
            <tr>
              <td  class="grid">
                <table width="950" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
                  <tr>
                    <td  class="grid-heading" >
                      Search Results
                      <a 
                          href="<%=request.getContextPath()%>/jsp/dispatch/results.jsp"
                          style="font-weight:normal;font-family:arial;font-size:8pt;color:#325ea0;">

                        [Switch to Grid View]
                      </a>
                    </td>
                  </tr>
                </table>
                <table width="950" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
                  <tr>
                    <td class="grid-columnHeading" width="20">
                    </td>
                    <td  class="grid-columnHeading" width="60">
                      <a 
                          href="<%=request.getContextPath()%>/dispatch?action=Sort&property=confirmationNumber&sortDirection=<%=request.getParameter("sortDirection")%>"
                          style="text-decoration:font-family:"Courier
                          New", Courier, monospace;font-size:medium;color:#5e82a6;">



                        Confirm#
                      </a>
                    </td>
                    <td  class="grid-columnHeading" width="120">
                      <a 
                          href="<%=request.getContextPath()%>/dispatch?action=Sort&property=lastName&sortDirection=<%=request.getParameter("sortDirection")%>"
                          style="text-decoration:font-family:"Courier
                          New", Courier, monospace;font-size:medium;color:#5e82a6;">



                        Last Name
                      </a>
                    </td>
                    <td  class="grid-columnHeading" width="120">
                      <a 
                          href="<%=request.getContextPath()%>/dispatch?action=Sort&property=firstName&sortDirection=<%=request.getParameter("sortDirection")%>"
                          style="text-decoration:font-family:"Courier
                          New", Courier, monospace;font-size:medium;color:#5e82a6;">



                        First Name
                      </a>
                    </td>
                    <td  class="grid-columnHeading" width="60">
                      <a 
                          href="<%=request.getContextPath()%>/dispatch?action=Sort&property=zipcode&sortDirection=<%=request.getParameter("sortDirection")%>"
                          style="text-decoration:font-family:"Courier
                          New", Courier, monospace;font-size:medium;color:#5e82a6;">



                        Zipcode
                      </a>
                    </td>
                    <td  class="grid-columnHeading" width="140">
                      <a 
                          href="<%=request.getContextPath()%>/dispatch?action=Sort&property=status&sortDirection=<%=request.getParameter("sortDirection")%>"
                          style="text-decoration:font-family:"Courier
                          New", Courier, monospace;font-size:medium;color:#5e82a6;">



                        Status
                      </a>
                    </td>
                    <td  class="grid-columnHeading" width="60">
                      <a 
                          href="<%=request.getContextPath()%>/dispatch?action=Sort&property=truck&sortDirection=<%=request.getParameter("sortDirection")%>"
                          style="text-decoration:font-family:"Courier
                          New", Courier, monospace;font-size:medium;color:#5e82a6;">



                        Truck
                      </a>
                    </td>
                    <td  class="grid-columnHeading" width="60">
                      <a 
                          href="<%=request.getContextPath()%>/dispatch?action=Sort&property=special&sortDirection=<%=request.getParameter("sortDirection")%>"
                          style="text-decoration:font-family:"Courier
                          New", Courier, monospace;font-size:medium;color:#5e82a6;">



                        Special
                      </a>
                    </td>
                    <td  class="grid-columnHeading" width="110">
                      <a 
                          href="<%=request.getContextPath()%>/dispatch?action=Sort&property=dispatchDate&sortDirection=<%=request.getParameter("sortDirection")%>"
                          style="text-decoration:font-family:"Courier
                          New", Courier, monospace;font-size:medium;color:#5e82a6;">



                        Dispatch Date
                      </a>
                    </td>
                    <td  class="grid-columnHeading" width="130" >
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
                      String truck="N/A";
                      String special="No";
                      if (result.getProperty("truck")!=null)
                      truck=result.getProperty("truck").toString();
                      if (result.getProperty("special")!=null)
                      special=result.getProperty("special").toString();
                      %>
                      <%
                      if (row==cursor) {
                        %>
                        <form method="POST" action="<%=request.getContextPath()%>/dispatch">
                          <tr>
                            <td class="grid-RowCount" >
                              <%=row+1%>
                            </td>
                            <td class="grid-results">
                              <%=properties.get("confirmationNumber")%>
                            </td>
                            <td class="grid-results">
                              <%=properties.get("lastName")%>
                            </td>
                            <td class="grid-results">
                              <%=properties.get("firstName")%>
                            </td>
                            <td class="grid-results">
                              <%=properties.get("zipcode")%>
                            </td>
                            <td class="grid-results">
                              <%=properties.get("status")%>
                            </td>
                            <td class="grid-results">
                              <%=truck%>
                            </td>
                            <td class="grid-results">
                              <%=special%>
                            </td>
                            <td class="grid-results">
                              <%=properties.get("dispatchDate")%>
                            </td>
                            <td class="grid-buttons">
                              <input type="submit" name="action" value="Print" class="buttonAction">
                              <input type="submit" name="action" value="Edit" class="buttonAction">
                              <input type="submit" name="action" value="Delete" class="buttonAction">
                            </td>
                          </tr>
                          <input 
                              type="hidden"
                              name="dispatchKey"
                              value="<%=KeyFactory.keyToString(result.getKey())%>"
                          />
                          <input type="hidden" name="dispatchId" value="<%=result.getKey().getId()%>"/>
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
                        <td class="results-bg" colspan="9">
                          no data found
                        </td>
                      </tr>
                      <%
                    } else {
                      %>

                      <%
                    }
                    %>
                  </table>


                </td>
              </tr>
            </table>
          </div>

          <br>

          <table width="620" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
            <tr>
              <td class="results-page-link">

              </td>
            </tr>
            <tr>
              <td align="left">
                <form method="POST" action="<%=request.getContextPath()%>/dispatch">
                  <input type="submit" name="action" value="Verify" class="buttonBlank"/>
                </form>
              </td>
            </tr>
          </table>
          <%
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
<%
session.setAttribute("view","results_classic.jsp");
%>
