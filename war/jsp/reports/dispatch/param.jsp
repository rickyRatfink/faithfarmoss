<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Map" %><%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreService" %>
<%@ page import="com.google.appengine.api.datastore.Query" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>

<jsp:include page="../../includes/header.jsp"/>

 <script language="javascript">
  function window(var url) {
	window.open (url, 'Faith Farm Report','status=1,toolbar=1');
  }
  </script>

<jsp:include page="../../includes/menu/index.jsp" flush="true"/>

<%
	String required = "<font size='-2' color='red'>*</font>";
%>
		<!----Main Content--->
		<td>
			<table width="820" border="0" cellpadding="0" cellspacing="0">
				<tr><td><br><!--img src="<%=request.getContextPath()%>/images/header_top.png"--></td></tr>
				<tr>
				<td class="content-body">
				<img src="<%=request.getContextPath()%>/images/titles/reporting.jpg">
						<br>
						<br>
				<jsp:include page="../../includes/errors.jsp" flush="true"/>
				
				<!--
				 <table width="620" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
				          <tr>
							    <td colspan="6" class="results-section">Results 1-2 of 2.</td>
						  </tr>
				 </table>
				 -->
				 <form method="POST" action="<%=request.getContextPath()%>/controller?entity=Report&RPT=DispatchPickUpStatus">
				 <div class="reports" style="border:0;" id="report-list-ctr">
				          <table width="620" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
				            
				          
						  <tr>
						  
								<td class="report-heading" colspan="2">
							  		Dispatch Pick-Up Status Sheet
							  	</td>
 							
						  </tr>
                          <tr>
                          	<td height="15"></td>
                          </tr>
                           <tr>
								<td class="edit-field-white" >
							  		Faith Farm Location
							  	</td>
 						   		<td class="edit-field-white">
 						   			<% 
 						   			String farm = (String)session.getAttribute("farmBase");
 						   			ArrayList ddl = (ArrayList)session.getAttribute("ddlFarms");%>
											  		<select name="farmBase" class="entry" disabled>
                                                       <option value="Boynton Beach"<% if ("Boynton Beach".equals(farm)) { %>selected<% } %>>Boynton Beach</option>
                                                       <option value="Fort Lauderdale" <% if ("Fort Lauderdale".equals(farm)) { %>selected<% } %>>Fort Lauderdale</option>
                                                       <option value="Okeechobee" <% if ("Okeechobee".equals(farm)) { %>selected<% } %>>Okeechobee</option>
                                                    </select>    
                                                    <input type="hidden" name="farmBase" value="<%=farm%>"/>
 						   		</td>
                            </tr>
 						  		<td class="edit-field-dark" >
							  		Date
							  	</td>
 						   		<td class="edit-field-dark">
 						   		<%
 						   			//java.util.Date today = new java.util.Date();
 						   			//DateFormat formatter;
									//formatter = new SimpleDateFormat("MM/dd/yyyy");
									
 						   		%>
 						   					<input name="startDate" value="" size="12" maxlength="10" class="tcal"/>   
 						   		</td>
 						   </tr>
 						   <tr>
								<td  width="50%" height="60"  align="right" colspan="2">
									<input type="submit" name="action" value="Run Report" class="buttonRunReport">
								</td>
							</tr>
						  							
				         </table> 
				  	</div>
				  	</form>			
					
				  	<br>
				  	
				  	
		
				<tr><td><!--img src="<%=request.getContextPath()%>/images/header_btm.png"--></td></tr>
			</table>
		</td>
	</tr>	
	

 

  	
  	</div>
</table>



<jsp:include page="../../includes/footer.jsp"/>


