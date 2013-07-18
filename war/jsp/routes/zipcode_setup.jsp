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
<%@ page import="java.util.Arrays" %>

<jsp:include page="../includes/header.jsp"/>


<jsp:include page="../includes/menu/index.jsp" flush="true"/>


<%
String farm = (String)session.getAttribute("farmBase");


List zipcodes = null;

if ("Fort Lauderdale".equals(farm)) {
	zipcodes=Arrays.asList( "33323","33351","33322","33319","33313","33311","33309","33317",
		  			   "33328","33327","33326","33325","33324","33330","33331","33332",
		  			   "33029","33028","33027","33026","33025","33009","33179","33020",
		  			   "33021","33023","33024","33004","33312","33314","33315","33316",
		  			   "33019","33180","33441","33064","33062","33060","33334","33308",
		  			   "33306","33305","33304","33301","33442","33076","33067","33073",
		  			   "33066","33069","33065","33071","33063","33068","33321");
} else if ("Boynton Beach".equals(farm)) {
	
	for (int zip=33403;zip<33409;zip++)
		zipcodes=Arrays.asList( zip+"");
	
} if ("Okeechobee".equals(farm)) {
	
	for (int zip=33403;zip<33409;zip++)
		zipcodes=Arrays.asList("34972","34973","34974");

}

String required = "<font size='-2' color='red'>*</font>";
ArrayList results = (ArrayList)request.getAttribute("results");

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
              Zipcode/Zone Setup
            </td>
          </tr>

        </table>
        <br>
        <jsp:include page="../includes/errors.jsp" flush="true"/>



        <!-- -display results on after search -->
        <%
        int row=0;
        %>
        <div class="results" style="border:0;" id="customer-list-ctr">
          <jsp:include page="../includes/warning.jsp"/>

          <table width="300" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
            <tr>
              <td  class="grid">
                <table width="300" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
                  <tr>
                    <td  class="grid-heading" >
                      Current Zipcodes
                    </td>
                  </tr>
                </table>
                  <form method="POST" action="<%=request.getContextPath()%>/arrivals">
                <table width="300" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
                  <tr>
                     <td  class="grid-columnHeading" width="60">
                           Zipcode
                      </td>
                    <td  class="grid-columnHeading" width="120">
                      Sequence
                    </td>
                  </tr>
                  <%
                  if (results!=null&&results.size()>0) 
	                for (int a=0;a<results.size();a++) {
                    Entity Zipcode = (Entity)results.get(a);
                    %>
                  
                      <tr>
                        <td class="grid-results">
                          <%=Zipcode.getProperty("zipcode")%>
                        </td>
                        <td class="grid-results">
                          <input name="sequence<%=a%>" size="3" maxlength="3" value="<%=Zipcode.getProperty("sequence")%>" class="entry"/>
                          <input type="hidden" name="zipcode<%=a%>" value="<%=Zipcode.getProperty("zipcode")%>" class="entry"/>
                        </td>
                      </tr>
                   <% row++; } else { 
                   
                   	  for (int a=0;a<zipcodes.size();a++) { %>
                      <tr>
                        <td class="grid-results">
                          <%=zipcodes.get(a)%>
                        </td>
                        <td class="grid-results">
                          <input name="sequence<%=a%>" value="<%=a+1%>" size="3" maxlength="3" class="entry"/>
                          <input type="hidden" name="zipcode<%=a%>" value="<%=zipcodes.get(a)%>" class="entry"/>
                        </td>
                      </tr>
                      <% row++; }
                    }
                    %>
			     </table>


                </td>
              </tr>
            </table>
            
            </br>
            <input type="submit" name="action" value="Save Zipcodes" class="buttonAction">
            <input type="hidden" name="size" value="<%=row%>"/>
            </form>
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

