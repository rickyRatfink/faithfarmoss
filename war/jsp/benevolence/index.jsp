<jsp:include page="../includes/header.jsp"/>


<jsp:include page="../includes/menu/index.jsp" flush="true"/>
<script type="text/javascript">
$(document).ready(function() {
  $("#process-button").click(function(e) {
    location.href = $(this).data('href');
  });
});
</script>
<%
	String required = "<font size='-2' color='red'>*</font>";
%>
		<!----Main Content--->
		<td valign="top">
			<table width="820" border="0" cellpadding="0" cellspacing="0">
				<tr><td><br><!--img src="<%=request.getContextPath()%>/images/header_top.png"--></td></tr>
				<tr>
					<td class="content-body">
						<div class="content">
							<div class="benevolence-header">Benevolence</div>
							
							<div>&nbsp;</div>
							<%
							String action = (String)request.getAttribute("action");
							String lastProcess = (String)request.getAttribute("lastProcess");
							if(action != null && !action.equals("")) {
							%>
								<div class="err">Invalid action specified: <%= action %></div>
							<%
							}
							%>
							<div>
								<div>
								<strong>Last Processed: </strong>
								<%
								if(lastProcess.isEmpty()) {
									%>
									Never
									<%
								}
								%>
								<%= lastProcess %>
								</div>
								<div>&nbsp;</div>
								<div><input type="button" id="process-button" data-href="/benevolence/Process" value="Process Now"/></div>
							</div>
							<ul class="plain-list">
								<li><a href="/benevolence/SetPseudoweek">Set Pseudoweek</a></li>
								<li><a href="/benevolence/FineStudent">Fine Resident</a></li>
								<li><a href="/benevolence/EditFines">View Pending Fines</a></li>
								<li><a href="/benevolence/ResidentDistribution">Distribution Report</a></li>
								<li><a href="/benevolence/CalculateDistribution">Resident Distribution</a> <font style="color:#db2536;font-size:.70em;"><i>(beta)</i></font></li>
								<li><a href="/benevolence/PacketBreakdown">Packet Breakdown Form</a></li>
								<li><a href="/benevolence/ChangeOrder">Change Order Form</a></li>
								<li><a href="/benevolence/Confirmation">Confirmation Report</a></li>
							</ul>
						</div>
					</td>
				</tr>									
				
		
				<tr><td><!--img src="<%=request.getContextPath()%>/images/header_btm.png"--></td></tr>
			</table>
		</td>
	</tr>	
	

 

  	
  	</div>
</table>

  


<jsp:include page="../includes/footer.jsp"/>


