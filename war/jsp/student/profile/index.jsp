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

 <script language="javascript">
  function window(var url) {
	window.open (url, 'Faith Farm Report','status=1,toolbar=1');
  }
  </script>

<jsp:include page="../../includes/menu/index.jsp" flush="true"/>

<%
	String required = "<font size='-2' color='red'>*</font>";
	String action=request.getParameter("action");
%>
		<!----Main Content--->
		<td>
			<table width="820" border="0" cellpadding="0" cellspacing="0">
				<tr><td><br><!--img src="<%=request.getContextPath()%>/images/header_top.png"--></td></tr>
				<tr>
				<td class="content-body">
				 <table>
				    	<tr>
				    		<td class="text1">
                <% if ("Profile".equals(action)) { %>
						Resident Profiles
                <% } else if ("Export".equals(action)) { %>
                		Export to Resident
                <% }  else if ("Deny".equals(action)) { %>
                		Deny Application
                <% }  else if ("Packet".equals(action)) { %>
                		Application Packets
                <% } else if ("Card".equals(action)) { %>
		              Print Intake Card
                <% } else if ("Work".equals(action)) { %>
		             Work Assignments
		        <% } else if ("Index".equals(action)) { %>
		             Person Search
		        <% } %>
                			</td>
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
				            
				          
						  <tr>
						  
								<td class="report-heading" colspan="2">
							  	    <% if ("Index".equals(action)) { %>
                                    	Person Search
                                   <%  } else if ("Profile".equals(action)) { %>
                                    	Resident Profile Search
                                    <% } else if ("Export".equals(action)) { %>
                                    	Export Intake
                                    <% } else { %>
                                    	Pending Intakes
                                    <% } %>
							  	</td>
 							
						  </tr>
                          <tr>
                          	<td height="15"></td>
                          </tr>
                          <tr>
								<td class="edit-field-dark" >
							  		First Name
							  	</td>
                                <td class="edit-field-dark">
							  		<input name="firstName" maxlength="20" size="20" class="entry" />
							  	</td>
 						   </tr>
                           
                           <tr>
								<td class="edit-field-white" >
							  		Last Name
							  	</td>
 						  		<td class="edit-field-white">
							  		<input name="lastName" maxlength="30" size="30" class="entry" />
							  	</td>
 						   </tr>
                           <tr>
								<td class="edit-field-dark" >
							  		Middle Initial
							  	</td>
 						  		<td class="edit-field-dark">
							  		<input name="middleInitial" maxlength="1" size="2" class="entry" />
							  	</td>
 						   </tr>
                           <tr>
								<td class="edit-field-white" >
							  		Social Security Number
							  	</td>
 						  		<td class="edit-field-white">
							  		<input name="ssn" maxlength="9" size="9" class="entry"  onkeypress="return isNumberKey(event)"/>
							  	</td>
 						   </tr>
						   <tr>
								<td class="edit-field-dark" >
							  		Faith Farm Location
							  	</td>
 						   		<td class="edit-field-dark">
 						   			<% ArrayList ddl = (ArrayList)session.getAttribute("ddlFarms");%>
											  		<select name="farmBase" class="entry">
                                                    	<option value=""></option>
                                                        <% if (ddl!=null) {
                                                        	for (int j=0;j<ddl.size();j++) {%>
                                                              <option value="<%=ddl.get(j)%>"><%=ddl.get(j)%></option>
                                                           <% } %>
                                                        <% } %>
                                                    </select>    
 						   		</td>
                            </tr>
 						  		<td class="edit-field-white" >
							  		Person Status
							  	</td>
 						   		<td class="edit-field-white">
 						   					<% ddl = (ArrayList)session.getAttribute("ddlPersonStatus");%>
                                             		<select name="personStatus" class="entry">
                                                    	<option value=""></option>
                                                        <% if (ddl!=null) {
                                                        	for (int j=0;j<ddl.size();j++) {%>
                                                              <option value="<%=ddl.get(j)%>"><%=ddl.get(j)%></option>
                                                           <% } %>
                                                        <% } %>
                                                    </select>         
 						   		</td>
 						   </tr>
 						    </tr>
 						  		<td class="edit-field-white" >
							  		Person Type
							  	</td>
 						   		<td class="edit-field-white">
 						   					<% ddl = (ArrayList)session.getAttribute("ddlPersonType");%>
                                             		<select name="personType" class="entry">
                                                    	<option value=""></option>
                                                        <% if (ddl!=null) {
                                                        	for (int j=0;j<ddl.size();j++) {%>
                                                              <option value="<%=ddl.get(j)%>"><%=ddl.get(j)%></option>
                                                           <% } %>
                                                        <% } %>
                                                    </select>         
 						   		</td>
 						   </tr>
                           <!--<tr>
 						  		<td class="edit-field-dark" >
							  		Person Type
							  	</td>
 						   		<td class="edit-field-dark">
 						   					<% ddl = (ArrayList)session.getAttribute("ddlPersonType");%>
                                             		<select name="personType" class="entry">
                                                    	<option value=""></option>
                                                        <% if (ddl!=null) {
                                                        	for (int j=0;j<ddl.size();j++) {%>
                                                              <option value="<%=ddl.get(j)%>"><%=ddl.get(j)%></option>
                                                           <% } %>
                                                        <% } %>
                                                    </select>         
 						   		</td>
 						   </tr>-->
 						   <tr>
								<td  width="50%" height="60"  align="right" colspan="2">
									<input type="submit" name="action" value="Search" class="buttonSearch">
                                    <%if ("Packet".equals(action)) { %>
                                    	<input type="hidden" name="packet" value="Y">
                                    	<input type="hidden" name="personType" value="Intake">
                                    <% } %>
                                    <%if ("Export".equals(action)) { %>
                                    	<input type="hidden" name="export" value="Y">
                                        <input type="hidden" name="personType" value="Intake">
                                     <% } %>
                                    <%if ("Deny".equals(action)) { %>
                                    	<input type="hidden" name="deny" value="Y">
                                        <input type="hidden" name="personType" value="Intake">
                                    <% } %>
                                    <% if ("Profile".equals(action)) { %>
                                        <input type="hidden" name="student" value="Y">
                                        <input type="hidden" name="personType" value="Resident">
                                    <% } %>
                                     <% if ("Card".equals(action)) { %>
                                        <input type="hidden" name="card" value="Y">
                                        <input type="hidden" name="personType" value="Intake">
                                    <% } %>
                                     <% if ("Work".equals(action)) { %>
                                        <input type="hidden" name="work" value="Y">
                                        <input type="hidden" name="personType" value="Intake">
                                    <% } %>
                                     <% if ("Photo".equals(action)) { %>
                                        <input type="hidden" name="photo" value="Y">
                                        <input type="hidden" name="personType" value="Intake">
                                    <% } %>
                                    <% if ("Index".equals(action)) { %>
                                        <input type="hidden" name="index" value="Y">
                                    <% } %>
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


