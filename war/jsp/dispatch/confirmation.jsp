<%@ page import="java.util.Date" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.DateFormat" %>
<jsp:include page="../includes/header.jsp"/>
<jsp:include page="../includes/menu/index.jsp" flush="true"/>


		<!----Main Content--->
		<td valign="top">
			<table width="820" border="0" cellpadding="0" cellspacing="0">
				<tr><td><br><!--img src="<%=request.getContextPath()%>/images/header_top.png"--></td></tr>
				<tr>
				<td class="content-body">
						<div class="content">
						
						
						<table width="70%" border=0 cellpadding=0 cellspacing=0>
							
							<tr>
								<td colspan="2">
									
									<br/><br/>
									<table cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
									<tr>
										<td valign="center">
											<img src="<%=request.getContextPath()%>/images/success.jpg"/>
										</td>
										<td valign="top" align="left">
											<% if (!"Y".equals(request.getParameter("conf"))) { %>
										   				<p>
										   				The dispatch has been successfully saved. Please give the customer the confirmation
										   				number listed below for future reference.
										   				<br/>
										   				<b>Confirmation#:</b><%=request.getAttribute("confirmation")%>
										   				</p>
										  <% } else { %>
										  				<p>
										   				The changes to the dispatch form have been successfully updated. 
										   				</p>
										  <% } %>
										</td>
								    </tr>
																										
									</table>
									
									
								</td>
							</tr>
						
							
							</table>
						
						
						<!----End Form----------->
						</div>
							
							
						        <jsp:include page="../includes/errors.jsp" flush="true"/>
        <br/></br>
        <form method="POST" action="<%=request.getContextPath()%>/dispatch">
          <div class="reports" style="border:0;" id="report-list-ctr">
            <table width="350" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">


			  <%
			  Date d = new java.util.Date();
			  DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
			  %>
              <td class="edit-field-dark" >
                Date
              </td>
              <td class="edit-field-dark">
                <input name="callDate" class="tcal" value="<%=formatter.format(d)%>" />
              </td>
            </tr>

            <tr>
              <td class="edit-field-white" >
                Type of Call
              </td>
              <td class="edit-field-white">
                <select name="callType" class="entry">
                    <option value=""></option>
                    <option value="cancel ticket">cancel ticket</option>
                    <option value="donation inquiry">donation inquiry</option>
                    <option value="new ticket">new ticket</option>
                    <option value="other inquiry">other inquiry</option>
                    <option value="phone reject">phone reject</option>
                    <option value="previously written ticket inquiry">previously written ticket inquiry</option>
                    <option value="reschedule">reschedule</option>
                </select>
              </td>
            </tr>
            
            
            <tr>
              <td class="edit-field-dark" >
                How did you hear about us?
              </td>
              <td class="edit-field-dark">
                          <%
                          ArrayList ddl = (ArrayList)session.getAttribute("ddlHowDidYouHearAboutUs");
                          %>
                          <select name="farmSource" class="entry">
                            <option value=""></option>
                            <%
                            for (int j=0;j<ddl.size();j++) { %>
                                <option value="<%=ddl.get(j)%>"><%=ddl.get(j)%></option>
                            <% } %>
                          </select>
                   </td>
            </tr>
            <tr>
              <td  height="60"  align="right" colspan="2">
                <input type="submit" name="action" value="Save Call" class="buttonBlank">
                <input type="hidden" name="entity" value="CallLog" >
              </td>
            </tr>

          </table>
        </div>
      </form>
			
				</td></tr>
				<tr><td><!--img src="<%=request.getContextPath()%>/images/header_btm.png"--></td></tr>
			</table>
			
			
		</td>
	</tr>	
	
	
	
</div>
</table>

<jsp:include page="../includes/footer.jsp"/>


