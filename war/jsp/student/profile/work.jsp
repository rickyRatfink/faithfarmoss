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

<jsp:include page="../../includes/header.jsp"/>
<jsp:include page="../../includes/menu/index.jsp" flush="true"/>

<%
	String required = "<font size='-2' color='red'>*</font>";
	String action=request.getParameter("action");
	Entity Person=(Entity)request.getAttribute("Person");
%>
		<!----Main Content--->
		<td>
			<table width="820" border="0" cellpadding="0" cellspacing="0">
				<tr><td><br><!--img src="<%=request.getContextPath()%>/images/header_top.png"--></td></tr>
				<tr>
				<td class="content-body">
               
		              <table>
						    	<tr>
						    		<td class="text1">Work Assignment</td>
						    	</tr>
						    	<tr>
						    		<td class="text2"><%=Person.getProperty("firstName")%>&nbsp;<%=Person.getProperty("middleInitial")%>&nbsp;<%=Person.getProperty("lastName")%></td>
						    	</tr>
						      </table>
                
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
				 <form method="POST" action="<%=request.getContextPath()%>/student">
				 <div class="reports" style="border:0;" id="report-list-ctr">
				          <table width="620" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
				            
				          
						 
								<td class="edit-field-dark" >
							  		Work Assignment
							  	</td>
 						  		<td class="edit-field-dark">
							  				<% ArrayList ddl = (ArrayList)session.getAttribute("ddlWorkAssignments");%>
											  		<select name="workAssignment" class="entry">
                                                    	<option value=""></option>
                                                        <% if (ddl!=null) {
                                                        	for (int j=0;j<ddl.size();j++) {%>
                                                              <option value="<%=ddl.get(j)%>"><%=ddl.get(j)%></option>
                                                           <% } %>
                                                        <% } %>
                             </select>    
							  	</td>
 						   </tr>
                           
               <tr>
								<td class="edit-field-white" >
							  		Supervisor
							  	</td>
 						  		<td class="edit-field-white">
							  				<% ddl = (ArrayList)session.getAttribute("ddlEmployees");%>
											  		<select name="supervisor" class="entry">
                                                    	<option value=""></option>
                                                        <% if (ddl!=null) {
                                                        	for (int j=0;j<ddl.size();j++) {%>
                                                              <option value="<%=ddl.get(j)%>"><%=ddl.get(j)%></option>
                                                           <% } %>
                                                        <% } %>
                             </select>    
							  	</td>
 						   </tr>
 						   <tr>
								<td  width="50%" height="60"  align="right" colspan="2">
								    	<input type="submit" name="action" value="Save Work" class="buttonSave">
                     	<input type="hidden" name="personKey" value="<%=KeyFactory.keyToString(Person.getKey())%>">
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


