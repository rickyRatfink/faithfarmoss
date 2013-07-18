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
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>

<jsp:include page="../includes/header.jsp"/>


<jsp:include page="../includes/menu/index.jsp" flush="true"/>


<%
  String required = "<font size='-2' color='red'>*</font>";
  Map map = (Map)session.getAttribute("resultMap");
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
                Truck/Lines for <%=request.getAttribute("arrivalDate")%>
              </td>
            </tr>

          </table>
          <br>
          <jsp:include page="../includes/errors.jsp" flush="true"/>


  
      <!-- -display results on after search -->
      <%
    int row=0;
      if (map!=null) { 
        %>
        <div class="results" style="border:0;" id="customer-list-ctr">
          <jsp:include page="../includes/warning.jsp"/>

          <table width="300" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
            <tr>
              <td  class="grid">
                <table width="300" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
                  <tr>
                    <td  class="grid-heading" >
                       Current Lines
                    </td>
                  </tr>
                </table>
                <table width="300" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
                  <tr>
                    <td class="grid-columnHeading" width="20">
                    </td>
                    <td  class="grid-columnHeading" width="60">
                     <a 
                          href="#"
                          style="text-decoration:font-family:"Courier New", Courier, monospace;font-size:medium;color:#5e82a6;">
						<b>Truck#</b>
                      </a>
                    </td>
                    <td  class="grid-columnHeading" width="120">
                     <a 
                          href="#" style="text-decoration:font-family:"Courier New", Courier, monospace;font-size:medium;color:#5e82a6;">
						Lines
                      </a>
                    </td>
                    <td class="grid-columnHeading">
                     <a 
                          href="#" style="text-decoration:font-family:"Courier New", Courier, monospace;font-size:medium;color:#5e82a6;">
                         Action 
                     </a>
                    </td>                   
                  </tr>
                  <%
                  
                  if (map!=null)
                   
                  for (int a=200;a<300;a++) {
                   	   Integer lines = (Integer)map.get(""+a);
                   	   
                   	   if (map.get(""+a)!=null) {
                  	  %>
                        <form method="POST" action="<%=request.getContextPath()%>/arrivals">
                          <tr>
                            <td class="grid-RowCount" >
                              <%=row+1%>
                            </td>
                            <td class="grid-results">
                              <%=a%>
                            </td>
                            <td class="grid-results">
                              <%=lines%>
                            </td>
                            <td>
                              <input type="submit" name="action" value="Extract Donations" class="buttonAction">
                              <input type="hidden" name="truck" value="<%=a%>"/>
                              <input type="hidden" name="arrivalDate" value="<%=request.getAttribute("arrivalDate")%>"/>
                             </td>
                          </tr>
                          
                        
                        </form>
                        <% row++; } 
                       } 
                     }
                    if (row==0) {
                      %>
                      <tr>
                        <td class="results-bg" colspan="9">
                          no data found
                        </td>
                      </tr>
                      <%
                    } %>
                  </table>


                </td>
              </tr>
            </table>
          </div>

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

