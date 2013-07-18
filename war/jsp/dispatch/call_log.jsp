<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreService" %>
<%@ page import="com.google.appengine.api.datastore.Query" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>

<jsp:include page="../includes/header.jsp"/>
<jsp:include page="../includes/menu/index.jsp" flush="true"/>

<%
String required = "<font size='-2' color='red'>*</font>";
String action=request.getParameter("action");
%>
<!----Main Content--->
<td>
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
              Call Log
            </td>
          </tr>
         
        </table>

        <br>
        <br>
        <jsp:include page="../includes/errors.jsp" flush="true"/>
        <br/></br>
        <form method="POST" action="<%=request.getContextPath()%>/dispatch">
          <div class="reports" style="border:0;" id="report-list-ctr">
            <table width="350" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">


			  <%
			  Date d = new java.util.Date();
			  DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
			  %>
              <td class="edit-field-dark" >
                Date
              </td>
              <td class="edit-field-dark">
                <input name="callDate" class="tcal" value="<%=formatter.format(d)%>" />
              </td>
            </tr>

            <tr>
              <td class="edit-field-white" >
                Type of Call
              </td>
              <td class="edit-field-white">
                <select name="callType" class="entry">
                    <option value=""></option>
                    <option value="cancel ticket">cancel ticket</option>
                    <option value="donation inquiry">donation inquiry</option>
                    <option value="new ticket">new ticket</option>
                    <option value="other inquiry">other inquiry</option>
                    <option value="phone reject">phone reject</option>
                    <option value="previously written ticket inquiry">previously written ticket inquiry</option>
                    <option value="reschedule">reschedule</option>
                </select>
              </td>
            </tr>
            
            
            <tr>
              <td class="edit-field-dark" >
                How did you hear about us?
              </td>
              <td class="edit-field-dark">
                          <%
                          ArrayList ddl = (ArrayList)session.getAttribute("ddlHowDidYouHearAboutUs");
                          %>
                          <select name="farmSource" class="entry">
                            <option value=""></option>
                            <%
                            for (int j=0;j<ddl.size();j++) { %>
                                <option value="<%=ddl.get(j)%>"><%=ddl.get(j)%></option>
                            <% } %>
                          </select>
                   </td>
            </tr>
            <tr>
              <td  height="60"  align="right" colspan="2">
                <input type="submit" name="action" value="Save Call" class="buttonBlank">
                <input type="hidden" name="entity" value="CallLog" >
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


