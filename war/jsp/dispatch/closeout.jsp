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
              Daily Dispatch/Driver Closeout
            </td>
          </tr>

        </table>

        <br>
        <br>
        <jsp:include page="../includes/errors.jsp" flush="true"/>
		</br>
        <!--
          <table width="620" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;"> <tr> <td
          colspan="6" class="results-section">Results 1-2 of 2.</td> </tr> </table>
        -->
        <form method="POST" action="<%=request.getContextPath()%>/dispatch">
          <div class="reports" style="border:0;" id="report-list-ctr">
            <table width="260" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">



              <td class="edit-field-dark" >
                Closeout Date
              </td>
              <td class="edit-field-dark">
                <%
                java.util.Date today = new java.util.Date();
                DateFormat formatter;
                formatter = new SimpleDateFormat("MM/dd/yyyy");
                %>
                <input name="closeoutDate" class="tcal" value="<%=formatter.format(today)%>" />
              </td>
            </tr>

            <tr>
              <td class="edit-field-white" >
                Tickets Picked Up
              </td>
              <td class="edit-field-white">
                <select name="pickedUp" class="entry">
                  <%
                  for (int i=0;i<150;i++) {
                    %>
                    <option value="<%=i%>">
                      <%=i%>
                    </option>
                    <%
                  }
                  %>
                </select>
              </td>
            </tr>

            <tr>
              <td class="edit-field-dark" >
                Tickets Rejects
              </td>
              <td class="edit-field-dark">
                <select name="rejected" class="entry">
                  <%
                  for (int i=0;i<150;i++) {
                    %>
                    <option value="<%=i%>">
                      <%=i%>
                    </option>
                    <%
                  }
                  %>
                </select>
              </td>
            </tr>

            <tr>
              <td class="edit-field-white" >
                No Reponses
              </td>
              <td class="edit-field-white">
                <select name="noresponses" class="entry">
                  <%
                  for (int i=0;i<150;i++) {
                    %>
                    <option value="<%=i%>">
                      <%=i%>
                    </option>
                    <%
                  }
                  %>
                </select>
              </td>
            </tr>

            <tr>
              <td class="edit-field-dark" >
                Cancelled By Donor
              </td>
              <td class="edit-field-dark">
                <select name="cancelled" class="entry">
                  <%
                  for (int i=0;i<150;i++) {
                    %>
                    <option value="<%=i%>">
                      <%=i%>
                    </option>
                    <%
                  }
                  %>
                </select>
              </td>
            </tr>

            <tr>
              <td class="edit-field-white" >
                Specials
              </td>
              <td class="edit-field-white">
                <select name="specials" class="entry">
                  <%
                  for (int i=0;i<150;i++) {
                    %>
                    <option value="<%=i%>">
                      <%=i%>
                    </option>
                    <%
                  }
                  %>
                </select>
              </td>
            </tr>

            <tr>
              <td class="edit-field-dark" >
                Number of Drivers
              </td>
              <td class="edit-field-dark">
                <select name="drivers" class="entry">
                  <%
                  for (int i=0;i<16;i++) {
                    %>
                    <option value="<%=i%>"> 
                      <%=i%>
                    </option>
                    <%
                  }
                  %>
                </select>
              </td>
            </tr>

            <tr>
              <td class="edit-field-white" >
                Number of Dispatchers
              </td>
              <td class="edit-field-white">
                <select name="phoneOperators" class="entry">
                  <%
                  for (int i=0;i<16;i++) {
                    %>
                    <option value="<%=i%>">
                      <%=i%>
                    </option>
                    <%
                  }
                  %>
                </select>
              </td>
            </tr>
            <tr>
              <td  width="50%" height="60"  align="right" colspan="2">
                <input type="submit" name="action" value="Closeout" class="buttonBlank">
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


