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
<jsp:include page="../../includes/header.jsp"/>
<jsp:include page="../../includes/menu/index.jsp" flush="true"/>

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

int pageLimit=25;
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

  String action=request.getParameter("action");
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
                <%
                if ("Export".equals(action)||"export".equals(action)) {
                  %>
                  Export Intake
                  <%} else if ("Profile".equals(action)) {%>
                    Resident Profiles
                    <%} else if ("Card".equals(action)) {%>
                      Application Intake Card
                      <%} else if ("Work".equals(action)) {%>
                        Work Assignments
                        <%} else if ("Index".equals(action)) {%>
                          Person Management
                          <%} else if ("Deny".equals(action)||"deny".equals(action)) {%>
                            Deny Intake
                            <%} else { %>
                              Application Packets
                              <%}%>
                              </td>
                            </tr>
                          </table>
						  <form method="POST" action="<%=request.getContextPath()%>/student">
                          <table 
                              width="400"
                              cellspacing="0"
                              cellpadding="0"
                              border="0"
                              style="border-collapse:collapse;">

                            <tr>
                              <td class="filterPerson">
                                <i>
                                  Filter By:
                                </i>
                                <table 
                                    width="620"
                                    cellspacing="0"
                                    cellpadding="0"
                                    border="0"
                                    style="border-collapse:collapse;">



                                  <tr>
                                    <td width="100" class="field-filter">
                                      First Name
                                    </td>
                                    <td width="100" class="field-filter">
                                      Last Name
                                    </td>
                                    <td width="80" class="field-filter">
                                      Status
                                    </td>
                                    <td width="80" class="field-filter">
                                      Type
                                    </td>
                                  </tr>
                                  <tr>
                                    <td>
                                      <input name="firstName" value="" maxlength="20" size="20" class="entry">
                                    </td>
                                    <td>
                                      <input name="lastName" value=""maxlength="30" size="30" class="entry">
                                    </td>
                                    <td>
                                      <select name="personStatus" class="entry">
                                        <%
                                        ArrayList ddl = (ArrayList)session.getAttribute("ddlPersonStatus");
                                        %>
                                        <option value="">
                                        </option>
                                        <%
                                        for (int i=0;i<ddl.size();i++) {
                                          String value=(String)ddl.get(i);
                                          %>
                                          <option value="<%=value%>">
                                            <%=value%>
                                          </option>
                                          <%
                                        }
                                        %>
                                      </select>
                                    </td>
                                    <td>
                                      <select name="personType" class="entry">
                                        <option value="">
                                        </option>
                                        <option value="Graduate">
                                          Graduate
                                        </option>
                                        <option value="Intake">
                                          Intake
                                        </option>
                                        <option value="Intern">
                                          Intern
                                        </option>
                                        <option value="Resident">
                                          Resident
                                        </option>
                                        <option value="SLS">
                                          SLS
                                        </option>
                                        <option value="Staff">
                                          Staff
                                        </option>
                                      </select>
                                    </td>
                                  </tr>
                                  <tr>
                                    <td colspan="4" align="right">
                                    </br>
                                      <input type="submit" name="action" value="Filter" class="buttonBlank">
                                      <input 
                                          type="button"
                                          value="Clear"
                                          class="buttonBlank"
                                          onClick="javascript:clearFilter();"
                                      />
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
                        ArrayList entities = (ArrayList)session.getAttribute("results");

                        if (entities!=null) {
                          %>
                          <jsp:include page="../../includes/warning.jsp"/>
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
                                    <td width="740">
                                      <%if (entities.size()>
                                        0) { %>
                                        <jsp:include page="grid.jsp" flush="true"/>
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
                        <div align="center">
                          <a href="/jsp/student/profile/index.jsp?action=<%=action%>">
                            Return to Search
                          </a>
                        </div>
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
        <jsp:include page="../../includes/footer.jsp"/>

