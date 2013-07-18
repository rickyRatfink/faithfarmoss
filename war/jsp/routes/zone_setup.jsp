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
	//zipcodes=Arrays.asList("33496","33495","33494","33493","33492","33491","33490","33489","33488","33487","33486","33485","33484","33483","33482","33481","33480","33479","33478","33477","33476","33475","33474","33473","33472","33471","33470","33469","33468","33467","33466","33465","33464","33463","33462","33461","33460","33459","33458","33457","33456","33455","33454","33453","33452","33451","33450","33449","33448","33447","33446","33445","33444","33443","33442","33441","33440","33439","33438","33437","33436","33435","33434","33433","33432","33431","33430","33429","33428","33427","33426","33425","33424","33423","33422","33421","33420","33419","33418","33417","33416","33415","33414","33413","33412","33411","33410","33409","33408","33407","33406","33405","33404","33403");
    zipcodes=Arrays.asList("33063","33065","33066","33067","33069","33401","33403","33404","33405","33406","33407","33408","33410","33411","33412","33413","33414","33415",
							"33417","33418","33449","33458","33460","33461","33462","33463","33467","33469","33470","33477","33478","33480","33483","33484","33486",
							"33487","33496","33498");
			//zipcodes = Arrays.asList(zips);
} if ("Okeechobee".equals(farm)) {
	
	
		zipcodes=Arrays.asList( "32960","32961","32962","32963","32964","32965","32966","32967","32968","32969",
								"34945","34956","34972","34973", "34974","34987",
								"33401","33402","33403","33404","33405","33406","33407","33408","33409","33410",
								"33411","33412","33413","33414","33415","33416","33417","33418","33419","33420",
								"33421","33422","33423","33424","33425","33426","33427","33428","33429","33430",
								"33431","33432","33433","33434","33435","33436","33437","33438","33439","33440",
								"33441","33442","33443","33444","33445","33446","33447","33448","33449","33450",
								"33451","33452","33453","33454","33455","33456","33457","33458","33459","33460",
								"33461","33562","33463","33464","33465","33466","33467","33468","33569","33470",
								"33471","33472","33473","33474","33475","33476","33477","33478","33479","33480",
								"33481","33482","33483","33484","33485","33486","33487","33488","33489","33490",
								"33491","33492","33493","33494","33495","33496","33497","33498","33499","33500"				
				);
}

String required = "<font size='-2' color='red'>*</font>";
ArrayList results = (ArrayList)request.getAttribute("results");
if (results==null)
	results=new ArrayList();

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
                      Zone
                    </td>
                  </tr>
                  <%
                  if (results!=null&&results.size()>0) 
	                for (int a=0;a<results.size();a++) {
                    Entity Zipcode = (Entity)results.get(a);
                    String zip=(String)Zipcode.getProperty("zipcode");
                    int zone=0;
                    try {
                       zone=Integer.parseInt((String)Zipcode.getProperty("zone"));
                    } catch (Exception e) {
                    	zone=0;
                    }
                    %>
                  
                      <tr>
                        <td class="grid-results">
                          <%=Zipcode.getProperty("zipcode")%>
                        </td>
                        <td class="grid-results">
                        <select name="zone_<%=zip %>" class="entry">
                        	<% for (int z=1;z<8;z++) { %>
                        		<option value="<%=z%>" <% if (z==zone) { %>selected<% } %>><%=z %></option>
                        	<% } %>
                        </select>
                         
                          <input type="hidden" name="key_<%=row %>" value="<%=KeyFactory.keyToString(Zipcode.getKey())%>" />
                          <input type="hidden" name="zipcode_<%=row %>" value="<%=zip %>"/>
                        </td>
                      </tr>
                   <% row++; } else { 
                   
                   	  for (int a=0;a<zipcodes.size();a++) { %>
                      <tr>
                        <td class="grid-results">
                        	<%=zipcodes.get(a) %>
                        </td>
                        <td class="grid-results">
                          <select name="zone_<%=zipcodes.get(a) %>" class="entry">
                        	<% for (int z=1;z<8;z++) { %>
                        		<option value="<%=z%>"><%=z %></option>
                        	<% } %>
                        </select>
                        <input type="hidden" name="zipcode_<%=row %>" value="<%=zipcodes.get(a)%>"/>
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
            <input type="submit" name="action" value="Save Zones" class="buttonAction">
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

