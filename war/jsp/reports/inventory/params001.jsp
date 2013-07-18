<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Map" %><%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreService" %>
<%@ page import="com.google.appengine.api.datastore.Query" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>
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
				 <form method="POST" action="<%=request.getContextPath()%>/controller">
				 <div class="reports" style="border:0;" id="report-list-ctr">
				          <table width="620" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
				            
				          
						  <tr>
						  
								<td class="report-heading" colspan="2">
							  		Inventory Report
							  	</td>
 							
						  </tr>
                          <tr>
                          	<td height="15"></td>
                          </tr>
                      
                           <tr>
								<td class="edit-field-white" >
							  		Vendor
							  	</td>
 						   		<td class="edit-field-white">
 						   			<% ArrayList ddl = (ArrayList)session.getAttribute("vendors");%>
											  		<select name="vendor" class="entry">
                                                    	<option value=""></option>
                                                    	<% for (int i=0;i<ddl.size();i++) { %>
                                                    	<option value="<%=ddl.get(i)%>"><%=ddl.get(i)%></option>
                                                    	<% } %>
                                                    </select>    
 						   		</td>
                            </tr>
                            <tr>
								<td class="edit-field-white" >
							  		Item Type
							  	</td>
 						   		<td class="edit-field-white">
 						   			<% ddl = (ArrayList)session.getAttribute("ddlItemTypes");%>
											  		<select name="itemType" class="entry">
                                                    	<option value=""></option>
                                                    	<% for (int i=0;i<ddl.size();i++) { %>
                                                    	<option value="<%=ddl.get(i)%>"><%=ddl.get(i)%></option>
                                                    	<% } %>
                                                    </select>    
 						   		</td>
                            </tr>
                             <tr>
								<td class="edit-field-white" >
							  		Status
							  	</td>
 						   		<td class="edit-field-white">
 						   					  		<select name="status" class="entry">
 						   					  			<option value=""></option>
                                                    	<option value="Active">Active</option>
                                                    	<option value="Discontinued">Discontinued</option>
                                                    </select>    
 						   		</td>
                            </tr>
                           <tr>
								<td  width="50%" height="60"  align="right" colspan="2">
									<input type="hidden" name="farmBase" value="<%=session.getAttribute("farmBase") %>" />
									<input type="submit" name="action" value="Run Report" class="buttonRunReport">
									<input type="hidden" name="RPT" value="001"/>
									<input type="hidden" name="entity" value="Report"/>
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


