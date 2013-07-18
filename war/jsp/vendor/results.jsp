<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %><%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
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
%>
		<!----Main Content--->
		<td valign="top">
			<table width="820" border="0" cellpadding="0" cellspacing="0">
				<tr><td><br><!--img src="<%=request.getContextPath()%>/images/header_top.png"--></td></tr>
				<tr>
				<td class="content-body">
				<img src="<%=request.getContextPath()%>/images/titles/vendor-results.jpg">
						<br><br>
						
				
				<!--
				 <table width="620" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
				          <tr>
							    <td colspan="6" class="results-section">Results 1-2 of 2.</td>
						  </tr>
				 </table>
				 -->
				 <form method="POST" action="<%=request.getContextPath()%>/vendor">
				<table width="800" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
				          <tr>
				          	<td width="60" height="30" align="left" valign="top">
				               		<input onfocus="this.value='';" name="vendorNumber" value="org number" maxlength="10" size="20" class="filterEntry">
				          	</td>
				          		
				          	<td valign="top" align="left">
							    	<a href="javascript:this.document.forms[0].submit();" style="text-decoration:none;"><img src="<%=request.getContextPath()%>/images/search.jpg" border="0"></a>
							    	<a href="<%=request.getContextPath()%>/vendor?entity=Vendor&action=Add Vendor" style="text-decoration:none;"><img src="<%=request.getContextPath()%>/images/add.jpg" border="0"></a>
							</td>
						  </tr>
				 </table>
				 	<input type="hidden" name="action" value="Search"/>
				 </form>
				
		
				  	<!-- BEGIN -->
				  <div class="results" style="border:0;" id="customer-list-ctr">
				          <table width="800" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
                           <tr>
                                <td  class="grid">
                                     <table width="800" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
                                          <tr>
                                             <td  class="grid-heading" >Search Results</td>
                                          </tr>  
                                      </table>
                                      
                          <table width="800" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
				          <tr>
                          	    <td  class="grid-columnHeading" width="40"><font style="text-decoration:font-family:"Courier New", Courier, monospace;font-size:medium;color:#5e82a6;">Number</td>
                                <td  class="grid-columnHeading" width="40"><font style="text-decoration:font-family:"Courier New", Courier, monospace;font-size:medium;color:#5e82a6;">Type</td>
                                <td  class="grid-columnHeading" width="40"><font style="text-decoration:font-family:"Courier New", Courier, monospace;font-size:medium;color:#5e82a6;">Name</td>
                                <td  class="grid-columnHeading" width="40"><font style="text-decoration:font-family:"Courier New", Courier, monospace;font-size:medium;color:#5e82a6;">Representative</td>
                                <td  class="grid-columnHeading" width="40"><font style="text-decoration:font-family:"Courier New", Courier, monospace;font-size:medium;color:#5e82a6;">Email</td>
                                <td  class="grid-columnHeading"></td>
				            </tr>
				         
				           <%
				            int row=0,cursor=0;
							//Iterable<Entity> entities = (Iterable<Entity>)session.getAttribute("results");
							Iterable<Entity> results = (Iterable<Entity>)session.getAttribute("results");
							
							Map<String, Object> props = null;
							
							if (results!=null) 
						   for (Entity result : results) {
							  props = result.getProperties();
						  %>
						  <% 
						  if (row==cursor) { %>
                          <form method="POST" action="<%=request.getContextPath()%>/vendor">
						    <tr>
							   	<td class="grid-Id"><%=props.get("number")%></td>
				                <td class="grid-results" width="200"><%=props.get("orgType")%></td>
							  	<td class="grid-results" width="200"><%=props.get("name")%></td>
							  	<td class="grid-results" width="150"><%=props.get("representative")%></td>
							  	<td class="grid-results" width="150"><%=props.get("email")%></td>
							  	<td class="grid-buttons" width="100"> 
                                	<a href="<%=request.getContextPath()%>/vendor?entity=Vendor&action=Edit&number=<%=result.getProperty("number")%>"><img src="<%=request.getContextPath()%>/images/icons/itemEdit.jpg" alt="Edit" border="0"></a>
							  		<a href="<%=request.getContextPath()%>/vendor?entity=Vendor&action=Delete&number=<%=result.getProperty("number")%>"><img src="<%=request.getContextPath()%>/images/delete.jpg" border="0"></a>
							  	 </td>
							  </tr>
                           </form>
						  <% 
						  cursor++;
						  //if (cursor%pageLimit==0) break;						  
						   }
					  	   row++;
					  	   
						  //if (cursor%pageLimit==0) break;
						  } 
						  if (cursor==0) {%>
						  <tr>
							  	<td class="results-bg" colspan="9">no data found</td>
						  </tr>
						  <% } %>
							 
				            
				         </table> 
                          </td>
                                 </tr>
                               </table>
				  	</div>
				  	
				  	
				  	
				  	
				  	
				  	<!-- END -->
	
				<tr><td><!--img src="<%=request.getContextPath()%>/images/header_btm.png"--></td></tr>
			</table>
		</td>
	</tr>	
	

 

  	
  	</div>
</table>



<jsp:include page="../includes/footer.jsp"/>


