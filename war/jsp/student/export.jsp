<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>

<jsp:include page="../includes/header.jsp"/>
<jsp:include page="../includes/menu/index.jsp" flush="true"/>

<%
String required = "<font size='-2' color='red'>*</font>";

Entity Person = (Entity)request.getAttribute("Person");
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
        <div class="content">
          <table>
            <tr>
              <td class="text1">
                Export As Student
              </td>
            </tr>
          </table>
          <br>
          <jsp:include page="../includes/errors.jsp" flush="true"/>
          <br>

          <!----Application Form ------->

          <form method="POST" action="<%=request.getContextPath()%>/student">
            <table width="70%" border=0 cellpadding=0 cellspacing=0>

              <tr>
                <td class="field" >
                  Entry Date
                </td>
              </tr>
              <tr>
                <td >
                  <input value="" name="entryDate" maxlength="10" size="15" class="tcal">
                </td>
              </tr>
              <tr>
                <td class="field" >
                  Supervisor
                </td>
              </tr>

              <tr>
                <td>
                  <%
                  ArrayList ddl = (ArrayList)session.getAttribute("ddlEmployees");
                  %>
                  <select name="supervisor" class="entry">
                    <option value="">
                    </option>
                    <%
                    if (ddl!=null) {
                      for (int j=0;j<ddl.size();j++) {
                        %>
                        <option value="<%=ddl.get(j)%>"><%=ddl.get(j)%></option>
                        <%
                      }
                      %>
                      <%
                    }
                    %>
                  </select>
                </td>
              </tr>

              <tr>
                <td class="field" >
                  Work Assignment
                </td>
              </tr>

              <tr>
                <td>
                  <%
                  ddl = (ArrayList)session.getAttribute("ddlWorkAssignments");
                  %>
                  <select name="workAssignment" class="entry">
                    <option value="">
                    </option>
                    <%
                    if (ddl!=null) {
                      for (int j=0;j<ddl.size();j++) {
                        %>
                        <option value="<%=ddl.get(j)%>"><%=ddl.get(j)%></option>
                        <%
                      }
                      %>
                      <%
                    }
                    %>
                  </select>
                </td>
              </tr>


              <tr>
                <td height="60" valign="bottom" align="left">
                  <input type="submit" name="action" value="Export As Resident" class="buttonBlankLarge">
                  <input type="hidden" name="personKey" value="<%=KeyFactory.keyToString(Person.getKey())%>">
                </td>
              </tr>

            </table>



          </form>

          <!----End Form----------->
        </div>

      </td>
    </tr>
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


