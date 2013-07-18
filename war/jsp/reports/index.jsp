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
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>

<jsp:include page="../includes/header.jsp"/>

 <script language="javascript">
  function window(var url) {
	window.open (url, 'Faith Farm Report','status=1,toolbar=1');
  }
  </script>
<%
	//check user authentication via Google
	UserService userService = UserServiceFactory.getUserService(); 
	User user = userService.getCurrentUser();
%>


<% String userRole = (String)session.getAttribute(user.getNickname()+"_ROLE"); %>

<jsp:include page="../includes/menu/index.jsp" flush="true"/>

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
				<jsp:include page="../includes/errors.jsp" flush="true"/>
				
				<!--
				 <table width="620" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
				          <tr>
							    <td colspan="6" class="results-section">Results 1-2 of 2.</td>
						  </tr>
				 </table>
				 -->
				 
				 <div class="reports" style="border:0;" id="report-list-ctr">
				          <table width="620" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
				            
				          <% if ("InvAdmin".equals(userRole)||"Administrator".equals(userRole)) { %>
				          <tr>
							  	<td valign="top" align="center" >
							  		<img src="<%=request.getContextPath()%>/images/report.jpg" width="65" height="65">
							  	</td>
							  	<td class="reports-bg" >
							  		<table width="400">
							  			<tr>
							  				<td class="reports-heading">
										  		New Furniture Inventory Report
										  	</td>
										</tr>
										<tr>
										  	<td class="reports-bg">
										  		Current list of inventory items and levels in the new furniture store.
										  	</td>
							  			</tr>
							  			<tr>
										  	<td class="reports-bg">
										  		<br><a href="<%=request.getContextPath()%>/jsp/reports/inventory/params001.jsp">Generate Report</a>
										  	</td>
							  			</tr>
							  		</table>
							</td>
							</tr>
							
							<tr><td colspan="2" height="30"></td></tr>
							<tr>
							  	<td valign="top" align="center" >
							  		<img src="<%=request.getContextPath()%>/images/report.jpg" width="65" height="65">
							  	</td>
							  	<td class="reports-bg" >
							  		<table width="400">
							  			<tr>
							  				<td class="reports-heading">
										  		New Furniture Daily Sales Report
										  	</td>
										</tr>
										<tr>
										  	<td class="reports-bg">
										  		Total sales for selected date.
										  	</td>
							  			</tr>
							  			<tr>
										  	<td class="reports-bg">
										  		<br><a href="#" onClick="javascript:window.open ('<%=request.getContextPath()%>/jsp/reports/inventory/params.jsp');">Generate Report</a>
										  	</td>
							  			</tr>
							  		</table>
							</td>
							</tr>
							<tr><td colspan="2" height="30"></td></tr>
							<% } %>
							<% if ("DispatchAdmin".equals(userRole)||"Administrator".equals(userRole)) { %>
							<tr>
							  	<td valign="top" align="center" >
							  		<img src="<%=request.getContextPath()%>/images/report.jpg" width="65" height="65">
							  	</td>
							  	<td class="reports-bg" >
							  		<table width="400">
							  			<tr>
							  				<td class="reports-heading">
										  		Donation Pick-Up Status Sheet
										  	</td>
										</tr>
										<tr>
										  	<td class="reports-bg">
										  		Phone operators' & drivers' report.
										  	</td>
							  			</tr>
							  			<tr>
										  	<td class="reports-bg">
										  		<br><a href="#" onClick="javascript:window.open ('<%=request.getContextPath()%>/jsp/reports/dispatch/param.jsp');">Generate Report</a>
										  	</td>
							  			</tr>
							  		</table>
							</td>
							</tr>
							<tr><td colspan="2" height="30"></td></tr>
							<% } %>
							<% if ("Intake".equals(userRole)||"Administrator".equals(userRole)) { %>
							
							<tr>
								<td valign="top" align="center" >
							  		<img src="<%=request.getContextPath()%>/images/report002.jpg" width="65" height="65">
							  	</td>
 								<td class="reports-bg" >
							  		<table width="400">
							  			<tr>
							  				<td class="reports-heading">
										  		New Arrivals Report
										  	</td>
										</tr>
										<tr>
										  	<td class="reports-bg">
										  		List of student just entering the program after a selected start date.
										  	</td>
							  			</tr>
							  			<tr>
										  	<td class="reports-bg">
										  		<br><a href="<%=request.getContextPath()%>/jsp/reports/new_arrivals/index.jsp">Generate Report</a>
										  	</td>
							  			</tr>
							  		</table>
							  	</td>
						  </tr>
							<tr><td colspan="2" height="30"></td></tr>
							<tr>
								<td valign="top" align="center" >
							  		<img src="<%=request.getContextPath()%>/images/report002.jpg" width="65" height="65">
							  	</td>
 								<td class="reports-bg" >
							  		<table width="400">
							  			<tr>
							  				<td class="reports-heading">
										  		Departures Report
										  	</td>
										</tr>
										<tr>
										  	<td class="reports-bg">
										  		List of student leaving the program after a selected date.
										  	</td>
							  			</tr>
							  			<tr>
										  	<td class="reports-bg">
										  		<br><a href="<%=request.getContextPath()%>/jsp/reports/departures/index.jsp">Generate Report</a>
										  	</td>
							  			</tr>
							  		</table>
							  	</td>
						  </tr>
							<tr><td colspan="2" height="30"></td></tr>
							<tr>
								<td valign="top" align="center" >
							  		<img src="<%=request.getContextPath()%>/images/report002.jpg" width="65" height="65">
							  	</td>
 								<td class="reports-bg" >
							  		<table width="400">
							  			<tr>
							  				<td class="reports-heading">
										  		Resident Report
										  	</td>
										</tr>
										<tr>
										  	<td class="reports-bg">
										  		Current list of students listed alphabetically by last name.
										  	</td>
							  			</tr>
							  			<tr>
										  	<td class="reports-bg">
										  		<br><a href="#" onClick="javascript:window.open ('<%=request.getContextPath()%>/controller?entity=Report&RPT=003');">Generate Report</a>
										  	</td>
							  			</tr>
							  		</table>
							  	</td>
						  </tr>
						  	<tr><td colspan="2" height="30"></td></tr>
						  	<tr>
								<td valign="top" align="center" >
							  		<img src="<%=request.getContextPath()%>/images/report002.jpg" width="65" height="65">
							  	</td>
 								<td class="reports-bg" >
							  		<table width="400">
							  			<tr>
							  				<td class="reports-heading">
										  		Class Breakdown Report
										  	</td>
										</tr>
										<tr>
										  	<td class="reports-bg">
										  		Graphical representation of current classes and enrollments.
										  	</td>
							  			</tr>
							  			<tr>
										  	<td class="reports-bg">
										  		<br><a href="<%=request.getContextPath()%>/reporting?RPT=breakdown">Generate Report</a>
										  	</td>
							  			</tr>
							  		</table>
							  	</td>
						  </tr>
						  
						  
						  	<tr><td colspan="2" height="30"></td></tr>
						  	<tr>
								<td valign="top" align="center" >
							  		<img src="<%=request.getContextPath()%>/images/report.jpg" width="65" height="65">
							  	</td>
 								<td class="reports-bg" >
							  		<table width="400">
							  			<tr>
							  				<td class="reports-heading">
										  		Actual/Budgeted Sales Forecast
										  	</td>
										</tr>
										<tr>
										  	<td class="reports-bg">
										  		Monthly breakdown of budgeted and actual sales.
										  	</td>
							  			</tr>
							  			<tr>
										  	<td class="reports-bg">
										  		<br><a href="<%=request.getContextPath()%>/reporting?RPT=forecast">Generate Report</a>
										  	</td>
							  			</tr>
							  		</table>
							  	</td>
						  </tr>
						
													
				     </table> 
				    
				     <% } %>
				     <% if ("Intake".equals(userRole)||"Administrator".equals(userRole)) { %>
							 
				     <!-- <table width="620" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
				          
				          <tr>
							  	<td valign="top" align="center" >
							  		<img src="<%=request.getContextPath()%>/images/report.jpg" width="65" height="65">
							  	</td>
							  	<td class="reports-bg" >
							  		<table width="400">
							  			<tr>
							  				<td class="reports-heading">
										  		Weekly Benevolence
										  	</td>
										</tr>
										<tr>
										  	<td class="reports-bg">
										  		Breakdown of students to be added to and removed removed from benevolence.
										  	</td>
							  			</tr>
							  			<tr>
										  	<td class="reports-bg">
										  		<br><a href="#" onClick="javascript:window.open ('<%=request.getContextPath()%>/controller?entity=Report&RPT=004');">Generate Report</a>
										  	</td>
							  			</tr>
							  		</table>
							  		</td>
						  </tr>
						  							
				     </table> -->
				     <br/><br/>    
				     <table width="620" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
				          <tr>
							  	<td valign="top" align="center" >
							  		<img src="<%=request.getContextPath()%>/images/report.jpg" width="65" height="65">
							  	</td>
							  	<td class="reports-bg" >
							  		<table width="400">
							  			<tr>
							  				<td class="reports-heading">
										  		Monthly Corporate Student Census
										  	</td>
										</tr>
										<tr>
										  	<td class="reports-bg">
										  		In and out student activity including check-out details.
										  	</td>
							  			</tr>
							  			<tr>
										  	<td class="reports-bg">
										  		<br><a href="#" onClick="javascript:window.open ('<%=request.getContextPath()%>/controller?entity=Report&RPT=005');">Generate Report</a>
										  	</td>
							  			</tr>
							  		</table>
							  		</td>
						  </tr>
						  							
				         </table>		
					<% } %>
					<br/><br/>
					<table width="620" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
				          <tr>
							  	<td valign="top" align="center" >
							  		<img src="<%=request.getContextPath()%>/images/report.jpg" width="65" height="65">
							  	</td>
							  	<td class="reports-bg" >
							  		<table width="400">
							  			<tr>
							  				<td class="reports-heading">
										  		Donor List
										  	</td>
										</tr>
										<tr>
										  	<td class="reports-bg">
										  		List of donors who donated during a specified time period.
										  	</td>
							  			</tr>
							  			<tr>
										  	<td class="reports-bg">
										  		<br><a href="<%= request.getContextPath() %>/jsp/reports/donor/index.jsp">Generate Report</a>
										  	</td>
							  			</tr>
							  		</table>
							  		</td>
						  </tr>
						  							
				     </table>
				  	</div>
				  	
				  	<br>
				  	
				  	
		
				<tr><td><!--img src="<%=request.getContextPath()%>/images/header_btm.png"--></td></tr>
			</table>
		</td>
	</tr>	
	

 

  	
  	</div>
</table>



<jsp:include page="../includes/footer.jsp"/>


