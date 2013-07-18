<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreService" %>
<%@ page import="com.google.appengine.api.datastore.Query" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>

<jsp:include page="../includes/header.jsp"/>

<script language="javascript">
  function window(var url) {
    window.open (url, 'Faith Farm Report','status=1,toolbar=1');
  }
</script>

<jsp:include page="../includes/menu/index.jsp" flush="true"/>

<%
String required = "<font size='-2' color='red'>*</font>";
%>
<!----Main Content--->
<td>
  <table width="520" border="0" cellpadding="0" cellspacing="0">
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
              Truck/Zone Assignment
            </td>
          </tr>

        </table>
        <br>
        <br>
        <jsp:include page="../includes/errors.jsp" flush="true"/>

        <!--
          <table width="620" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;"> <tr> <td
          colspan="6" class="results-section">Results 1-2 of 2.</td> </tr> </table>
        -->
        <form method="POST" action="<%=request.getContextPath()%>/dispatch">
          <div class="reports" style="border:0;" id="report-list-ctr">
            <table width="520" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">


              <tr>
                <td class="report-heading" colspan="3">
                  Zone/Trucks
                </td>
              </tr>

              <tr>
                <td class="edit-field-dark" >
                  Date
                </td>
                <td class="edit-field-dark" colspan="2">
                  <%
                  java.util.Date today = new java.util.Date();
                  DateFormat formatter;
                  formatter = new SimpleDateFormat("MM/dd/yyyy");
                  %>
                  <input name="zoneDate" value="<%=formatter.format(today)%>" size="12" maxlength="10" class="tcal"/>
                </td>
                
              </tr>
			  <tr>
			  	<td class="grid-columnHeading">Zone</td>
			  	<td class="grid-columnHeading">Truck</td>
			  	<td class="grid-columnHeading">Status</td>
			  </tr>
			  <% 
			  
			  String cssClass="";
			  for (int i=0;i<7;i++) { 
			  	if (i%2==0) cssClass="edit-field-white";
			  	else cssClass="edit-field-dark";
			  
			  %>
              <tr>
                <td class="<%=cssClass%>" >
                  Zone <%=i+1%>
                </td>
                <td class="<%=cssClass%>">
                  <%ArrayList ddl = (ArrayList)session.getAttribute("ddlTruckList"); %>
                    <select name="zone<%=i+1%>" class="entry">
                      <option value=""></option>
                      <%
                      if (ddl!=null) {
                        for (int j=0;j<ddl.size();j++) {
                          %>
                          <option value="<%=ddl.get(j)%>"><%=ddl.get(j)%></option>
                          <%
                        }
                      }
                      %>
                    </select>
                  </td>
                  <td class="<%=cssClass%>">
                    <select name="status<%=i+1%>" class="entry">
                      <option value="Assigned">Assigned</option>
                      <option value="Completed">Completed</option>
                    </select>
                  </td>
                </tr>
                <% } %>
                <tr>
                  <td  width="50%" height="60"  align="right" colspan="2">
                    <input type="submit" name="action" value="Save Trucks" class="buttonBlankMedium">
                  </td>
                </tr>

              </table>
            </div>
          </form>

          <br>



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


