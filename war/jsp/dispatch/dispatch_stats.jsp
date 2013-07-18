<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
 
 		
          <tr>
            	<td>
            		<% 
            		Integer max = (Integer)session.getAttribute("dispatch_limit");
            		Integer curr = (Integer)session.getAttribute("dispatch_size");
            		float perct = (curr*100.0f)/max;
            		
            		DateFormat formatter;
					formatter = new SimpleDateFormat("MM/dd/yyyy");
					java.util.Date date = new java.util.Date();
					String dayToCheck=formatter.format(date);
					%>
            		</br></br>
            		<table width="300" border="0" cellpadding="0" cellspacing="0">
						 <tr>
				 		 	<td class="dispatchStats"><b>Current Dispatch Statistics for <%=dayToCheck %></b></td>
				 		 </tr><tr>
							<td width="300" bgcolor="#f5c74d" height="20">
								<table width="<%=perct%>%" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td width="100%" bgcolor="#038d27" height="20"></td>
								</tr>
								</table>
							</td>
						</tr>
						</table>
						<table width="150" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td class="dispatchStats"><b>Allocated:</b></td><td class="dispatchStats"><i><%=max %></i></td>
						</tr>
						<tr>
							<td class="dispatchStats"><b>Assigned:</b></td><td class="dispatchStats"><i><%=curr %></i></td>
						</tr>
							
						</tr>
					</table>
            	</td>
            </tr>