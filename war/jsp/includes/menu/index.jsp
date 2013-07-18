<%@ page import="java.util.ArrayList" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>

<%
	//check user authentication via Google
	UserService userService = UserServiceFactory.getUserService(); 
	User user = userService.getCurrentUser();
    String userRole = (String)session.getAttribute(user.getNickname()+"_ROLE"); 
%>

<style type="text/css">#wrapper-191{width:191px;height:500px;margin:0 auto;}</style>
  <table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<!----Menu--->
		<td align="left" width="191" valign="top" bgcolor="#5870b3">
		
		<% if (null!=userRole) {%>
						<table width="191" border="0" cellpadding="0" cellspacing="0">
					<tr>
							<!----Menu--->
						<td align="left" width="191" valign="top">
						<div id="wrapper-191">
					
							<ul class="accordion">
								
								<%if ("DispatchAdmin".equals(userRole)||"SalesClerk".equals(userRole)||"Administrator".equals(userRole)||"InvAdmin".equals(userRole)) { %>
								<li id="one" class="files">						
									<a href="#one">CRM</a>
									<ul class="sub-menu">
										<li><a href="<%=request.getContextPath()%>/customer?action=Search">Customer Management</a></li>
										<%if (userRole.equals("Administrator")) { %>
										<li><a href="<%= request.getContextPath() %>/massemail">Mass Email</a></li>
										<% } %>
									</ul>
								</li>
								<% } %>
								<%if ("Administrator".equals(userRole)||"Dispatch".equals(userRole)||"DispatchAdmin".equals(userRole)||"InvAdmin".equals(userRole)||"SalesClerk".equals(userRole)||"As-IsClerk".equals(userRole)) { %>
								<li id="two" class="files">
									<a href="#two">Logistics</a>
									<ul class="sub-menu">
										<% if ("Dispatch".equals(userRole)||"DispatchAdmin".equals(userRole)||"Administrator".equals(userRole)) { %>
										 <li><a href="<%=request.getContextPath()%>/dispatch?action=View">Donations</a></li>
										 <li><a href="<%=request.getContextPath()%>/jsp/dispatch/call_log.jsp">Call Log</a></li>
										 <% }
										 if ("DispatchAdmin".equals(userRole)||"Administrator".equals(userRole)) { %>
										 <li><a href="<%=request.getContextPath()%>/jsp/dispatch/level.jsp">Daily Dispatch Levels</a></li>
										 <li><a href="<%=request.getContextPath()%>/jsp/dispatch/closeout.jsp">Daily Dispatch Closeout</a></li>
										 <!-- <li><a href="<%=request.getContextPath()%>/arrivals?action=Zipcode Setup">Zipcode Setup</a></li> -->
										 <li><a href="<%=request.getContextPath()%>/arrivals?action=Zone Setup">Zone Setup</a></li>
										 <!-- <li><a href="<%=request.getContextPath()%>/jsp/routes/trucks.jsp">Assign Trucks</a></li>-->
										 <li><a href="<%=request.getContextPath()%>/jsp/routes/truck_zones.jsp">Assign Trucks</a></li>
										 <% } if ("Administrator".equals(userRole)) { %>
										  <li><a href="<%= request.getContextPath() %>/po">Purchase Orders</a></li>
										  <li><a href="<%=request.getContextPath()%>/jsp/arrivals/index.jsp">Donation Checkoff</a></li>
										<% } %>				
									</ul>
								</li>
								<% } %>
								<% if ("Administrator".equals(userRole)||"InvAdmin".equals(userRole)) { %>
								<li id="three" class="files">						
									<a href="#three">Inventory</a>
									<ul class="sub-menu">
										<li><a href="<%=request.getContextPath()%>/item?action=Filter">Inventory Management</a></li>
										<li><a href="<%=request.getContextPath()%>/vendor?action=Search">Orgs Management</a></li>
										<li><a href="<%=request.getContextPath()%>/jsp/construction.jsp">Order Management</a></li>
										<li><a href="<%=request.getContextPath()%>/jsp/construction.jsp">Shipment Receipt</a></li>
									</ul>
								</li>
								<% } %>
								<% if ("SalesClerk".equals(userRole)||"Administrator".equals(userRole)||"As-IsClerk".equals(userRole)) { %>
								<li id="four" class="files">						
									<a href="#four">Order</a>
									<ul class="sub-menu">
										  <li><a href="<%=request.getContextPath()%>/sales?action=Index">Customer Sale</a></li>
									</ul>
								</li>
								<% } %>
								<%if ("Administrator".equals(userRole)||"Intake".equals(userRole)) { %>
								<li id="five" class="sign">
									<a href="#five">Resident</a>
									<ul class="sub-menu">
										<li><a href="<%=request.getContextPath()%>/student?action=Index">Person Management</a></li>
										<li><a href="<%=request.getContextPath()%>/intake?action=init">Intake Application</a></li>
										<li><a href="<%=request.getContextPath()%>/student?action=Quick">Quick Entry</a></li>
										<li><a href="<%=request.getContextPath()%>/student?action=Index">Resident Profile</a></li>
										<li><a href="<%=request.getContextPath()%>/benevolence">Benevolence Distribution</a></li>
										<!-- -<li><a href="<%=request.getContextPath()%>/student?action=ResidencyLetter">Residency Letter</a></li>-->
									</ul>
								</li>
								<%}%>
								<%if ("Administrator".equals(userRole)) { %>
								<li id="six" class="sign">
					
									<a href="#six">Course</a>
					
									<ul class="sub-menu">
										
										<li><a href="<%=request.getContextPath()%>/course?action=Add">Add Course</a></li>
										<li><a href="<%=request.getContextPath()%>/course?action=Rotate">Course Rotation</a></li>
										<li><a href="<%=request.getContextPath()%>/course?action=Search">Course/Roster Management</a></li>
										
									</ul>
					
								</li>
								<%}%>
								<%if ("Intake".equals(userRole)||"Administrator".equals(userRole)||"InvAdmin".equals(userRole)||"DispatchAdmin".equals(userRole)) { %>
								<li id="seven" class="sign">
					
									<a href="#seven">Reports</a>
					
									<ul class="sub-menu">
										
										<li><a href="<%=request.getContextPath()%>/jsp/reports/index.jsp">View</a></li>
											
									</ul>
					
								</li>
								<%}%>
								<%if ("HelpDesk".equals(userRole)||"Administrator".equals(userRole)) { %>
								<li id="eight" class="sign">
					
									<a href="#eight">Help Desk</a>
					
									<ul class="sub-menu">
										
										<li><a href="<%=request.getContextPath()%>/jsp/help/results.jsp">Tickets</a></li>
										<li><a href="http://trac.faith-farm.net/">Trac System</a></li>
											
									</ul>
					
								</li>
								<%}%>
								<%if ("Administrator".equals(userRole)) { %>
								<li id="nine" class="sign">
					
									<a href="#nine">System Administration</a>
					
									<ul class="sub-menu">										
										<li><a href="<%=request.getContextPath()%>/security?action=Index">Manage Users</a></li>
										<li><a href="<%=request.getContextPath()%>/jsp/construction.jsp">System Configuration</a></li>
										<li><a href="<%=request.getContextPath()%>/sfinv">Storefront</a></li>
									</ul>
					
								</li>
								<% } %>
							</ul>
							
						</div>
					</td>
					</tr>
					</table>
<% } %>
<br>			
</td>	

	<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
	<script type="text/javascript">

		$(document).ready(function() {

			// Store variables
			
			var accordion_head = $('.accordion > li > a'),
				accordion_body = $('.accordion li > .sub-menu');

			// Read cookie to determine which submenu we were viewing last
			
			var cookies = document.cookie.split(";");
			
			var current_menu = null;
			
			for(i=0;i<cookies.length;i++) {
				var csplit = cookies[i].split("=");
				var key = csplit[0].trim();
				var val = csplit[1].trim();
				if(key == "current_menu") {
					current_menu = val;
				}
			}

			$('a[href=#'+ current_menu + ']').addClass('active').next().slideDown('normal');
			
			

			// Click function

			accordion_head.on('click', function(event) {

				// Disable header links
				
				event.preventDefault();
				
				//set cookie for currently selected menu item.
				
				document.cookie = "current_menu=" + $(this).attr('href').substr(1);
				
				

				// Show and hide the tabs on click

				if ($(this).attr('class') != 'active'){
					accordion_body.slideUp('normal');
					$(this).next().stop(true,true).slideToggle('normal');
					accordion_head.removeClass('active');
					$(this).addClass('active');
				}

			});

		});

	</script>
