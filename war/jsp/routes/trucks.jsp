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
              Truck Assignment
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
        <form method="POST" action="<%=request.getContextPath()%>/arrivals">
          <div class="reports" style="border:0;" id="report-list-ctr">
            <table width="520" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">


              <tr>
                <td class="report-heading" colspan="3">
                  Truck Assignment
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
                  
                  String date=date=formatter.format(today);
                  if (request.getAttribute("dispatchDate")!=null)
                    date=(String)request.getAttribute("dispatchDate");
                  %>
                  <input name="dispatchDate" value="<%=date%>" size="12" maxlength="10" class="tcal"/>
                </td>
                
              </tr>
              <% if (request.getAttribute("next")!=null) { %>
              <tr>
                <td class="edit-field-white" >
                  Dispatches
                </td>
                <td class="edit-field-white" colspan="2">
                	<%=request.getAttribute("dispatchCount")%> 
                	<input type="hidden" name="dispatchCount" value="<%=request.getAttribute("dispatchCount")%>"/>
                </td>
                
              </tr>
              
			  <tr>
			  	 <td class="edit-field-dark">Number of Trucks</td>
			  	 <td class="edit-field-dark" colspan="2">
                	 <select name="truckCount" class="entry">
                	 	<option value="1">1</option>
                	 	<option value="2">2</option>
                	 	<option value="3">3</option>
                	 	<option value="4">4</option>
                	 	<option value="5">5</option>
                	 	<option value="6">6</option>
                	 </select>
                </td>
			  </tr>
			  <% } %>
			  <tr>
                  <td  width="50%" height="60"  align="right" colspan="2">
                  <% if (request.getAttribute("next")==null) { %>
                  	<input type="submit" name="action" value="Next" class="buttonBlankMedium">
                  <% } else { %>
                    <input type="submit" name="action" value="Generate Routes" class="buttonBlankMedium">
                  <% } %>
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


