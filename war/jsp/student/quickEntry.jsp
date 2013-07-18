<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>

<jsp:include page="../includes/header.jsp"/>
<jsp:include page="../includes/menu/index.jsp" flush="true"/>

<%

    String required = "<font size='-2' color='red'>*</font>";
	
 	Entity person = (Entity)request.getAttribute("Person");
 	System.out.println("person="+person);
		
%>


		<!----Main Content--->
		<td valign="top">
			<table width="820" border="0" cellpadding="0" cellspacing="0">
				<tr><td><br><!--img src="<%=request.getContextPath()%>/images/header_top.png"--></td></tr>
				<tr>
				<td class="content-body">
						<div class="content">
						 <table>
				    	 <tr>
				    		<td class="text1">Person Quick Entry</td>
				    	 </tr>
				         </table>
						<br>
						<jsp:include page="../includes/errors.jsp" flush="true"/>
						<br>
						
						<!----Application Form ------->
						
						<form method="POST" action="<%=request.getContextPath()%>/student">
						<table width="70%" border=0 cellpadding=0 cellspacing=0>
							
							<tr>
								<td >
								 <table cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
							             
							                
							                <tr>
							                 <td width="200" class="field">First Name<%=required%></td>
							                 <td width="100" class="field">Middle Initial</td>
							                 <td width="200" class="field">Last Name<%=required%></td>
							                 <td width="100" class="field">Prefix</td>
							               </tr>
							               	
							               <tr>
							               	 <td><input value="<%=person.getProperty("firstName")%>" name="firstName" maxlength="30" size="30" class="entry"></td>
							               	 <td><input value="<%=person.getProperty("middleInitial")%>" name="middleInitial" maxlength="1" size="1" class="entry"></td>
							               	 <td><input value="<%=person.getProperty("lastName")%>"  name="lastName" maxlength="30" size="30" class="entry"></td>
							               	 <td>
							               	 	<select name="suffix" class="entry">
							               	 	    <option value=""></option>
							               	 		<option value="Jr.">Jr.</option>
							               	 		<option value="Sr.">Sr.</option>
							               	 		<option value="I">I</option>
							               	 		<option value="II">II</option>
							               	 		<option value="III">III</option>
							               	 	</select>
							               	 </td>							               	 
							               </tr> 
									   
							           </table>
									
								</td>
							</tr>
							<tr>
								<td class="field" >Social Security Number<%=required%></td>
							</tr>
							<tr>
								<td >
									<input onKeyPress="return isNumberKey(event)" value="<%=person.getProperty("ssn")%>" name="ssn" maxlength="9" size="9" class="entry">
							    </td>
							</tr>
							<tr>
								<td class="field" >Current Course</td>
							</tr>
							<tr>
								<td >
									<input value="<%=person.getProperty("currentCourse")%>" name="currentCourse" maxlength="2" size="2" class="entry">
								</td>
							</tr>
							<tr>
								<td class="field" >Birth Date</td>
							</tr>
							<tr>
								<td >
									<input value="<%=person.getProperty("dateOfBirth")%>" name="dateOfBirth" maxlength="10" size="10" class="tcal">
								</td>
							</tr>
							<tr>
							<td class="field" >Entry Date</td>
							</tr>
							<tr>
								<td >
									<input value="<%=person.getProperty("entryDate")%>" name="entryDate" maxlength="10" size="10" class="tcal">
								</td>
							</tr>
							<tr>
								<td class="field" >Person Type</td>
							</tr>
			              	
			                <tr>
			               	 <td>
                             	 <%	ArrayList ddl = (ArrayList)session.getAttribute("ddlPersonType"); %>
                             		<select name="personType" class="entry">
                                    	<option value=""></option>
                                        <% if (ddl!=null) {
                                        	for (int j=0;j<ddl.size();j++) { %>
                                              <option value="<%=ddl.get(j)%>"  <% if (person.getProperty("personType").equals(ddl.get(j))) {%>selected<%}%>><%=ddl.get(j)%></option>
                                           <% } %>
                                        <% } %>
                                    </select>
                           	 </td>
			                </tr> 
							<tr>
								<td class="field" >Status</td>
							</tr>
			              	
			                <tr>
			               	 <td>
                             	 <%	 ddl = (ArrayList)session.getAttribute("ddlPersonStatus"); %>
                             		<select name="personStatus" class="entry">
                                    	<option value=""></option>
                                        <% if (ddl!=null) {
                                        	for (int j=0;j<ddl.size();j++) { %>
                                              <option value="<%=ddl.get(j)%>"  <% if (person.getProperty("personStatus").equals(ddl.get(j))) {%>selected<%}%>><%=ddl.get(j)%></option>
                                           <% } %>
                                        <% } %>
                                    </select>
                           	 </td>
			                </tr> 
			                
			                <tr>
								<td class="field" >Supervisor</td>
							</tr>
			              	
			                <tr>
			               	 <td>
                             	 <%	 ddl = (ArrayList)session.getAttribute("ddlEmployees"); %>
                             		<select name="supervisor" class="entry">
                                    	<option value=""></option>
                                        <% if (ddl!=null) {
                                        	for (int j=0;j<ddl.size();j++) { %>
                                              <option value="<%=ddl.get(j)%>"  <% if (person.getProperty("supervisor").equals(ddl.get(j))) {%>selected<%}%>><%=ddl.get(j)%></option>
                                           <% } %>
                                        <% } %>
                                    </select>
                           	 </td>
			                </tr> 
			                
			                <tr>
								<td class="field" >Work Assignment</td>
							</tr>
			              	
			                <tr>
			               	 <td>
                             	 <%	 ddl = (ArrayList)session.getAttribute("ddlWorkAssignments"); %>
                             		<select name="workAssignment" class="entry">
                                    	<option value=""></option>
                                        <% if (ddl!=null) {
                                        	for (int j=0;j<ddl.size();j++) { %>
                                              <option value="<%=ddl.get(j)%>" <% if (person.getProperty("workAssignment").equals(ddl.get(j))) {%>selected<%}%>><%=ddl.get(j)%></option>
                                           <% } %>
                                        <% } %>
                                    </select>
                           	 </td>
			                </tr> 
								
							
							<tr>
								<td height="60" valign="bottom" align="right">
									<input type="submit" name="action" value="Quick Save" class="buttonSave">
								</td>
							</tr>
							
							</table>
						
						
						
						</form>			
						
						<!----End Form----------->
						</div>
									
				</td></tr>
				<tr><td><!--img src="<%=request.getContextPath()%>/images/header_btm.png"--></td></tr>
			</table>
		</td>
	</tr>	
	
</div>
</table>

<jsp:include page="../includes/footer.jsp"/>


