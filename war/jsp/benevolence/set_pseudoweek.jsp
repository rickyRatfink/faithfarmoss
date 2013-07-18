<%@ page import="org.faithfarm.dataobjects.Benevolence" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.text.SimpleDateFormat" %>
<jsp:include page="../includes/header.jsp"/>


<jsp:include page="../includes/menu/index.jsp" flush="true"/>

<%
	String required = "<font size='-2' color='red'>*</font>";
	Integer weekOffset = 0;
	try {
		String offset = (String)request.getAttribute("weekOffset");
		weekOffset = Integer.parseInt(offset, 10);
	} catch(ClassCastException e) {
		try {
			Integer offset = (Integer)request.getAttribute("weekOffset");
			weekOffset = offset;
		} catch(ClassCastException e2) {
			Long offset = (Long)request.getAttribute("weekOffset");
			weekOffset = offset.intValue();
		}
	}
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
							<div><strong>Set Pseudoweek:</strong></div>
							<div style="display: inline;">
								<select name="pseudoweek" id="pseudoweek">
									<option value="0">No pseudoweek</option>
									<%
									SimpleDateFormat toFormat = new SimpleDateFormat("MM/dd/yy");
									
									// This section cannot use getReportDay because it must get the next friday and go one month in advance
									// if we use getReportDay() it might return a pseudoweek.  Hope this explains the dilemma.
									Calendar reportDay = Calendar.getInstance();
									reportDay.set(reportDay.get(Calendar.YEAR), reportDay.get(Calendar.MONTH), reportDay.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
									reportDay.set(Calendar.MILLISECOND, 0);	
									if(reportDay.get(Calendar.DAY_OF_WEEK) > Calendar.FRIDAY) {
										reportDay.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
										reportDay.add(Calendar.WEEK_OF_YEAR, 1);
									}
									else {
										reportDay.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
									}
									
									for(int x = 1; x <= 4; x++) {
										reportDay.add(Calendar.WEEK_OF_YEAR, 1);
										%>
										<option value="<%= x %>"<%= weekOffset == x ? " selected=\"selected\"" : "" %>><%= toFormat.format(reportDay.getTime()) %></option>
										<%
									}
									%>
								</select>
							</div>
							<div>&nbsp;</div>
							<div class="err" id="err"></div>
						</div>
					</td>
				</tr>									
				
		
				<tr><td><!--img src="<%=request.getContextPath()%>/images/header_btm.png"--></td></tr>
			</table>
		</td>
	</tr>	
	

 

  	
  	</div>
</table>

<script type="text/javascript">
$(document).ready(function() {
	$("#pseudoweek").change(function(e) {
		$("#err").hide();
		var deferred = $.ajax({
			url: '/benevolence/SetPseudoweek',
			type: 'POST',
			data: {
				offset: $(this).val()
			},
			dataType: 'json'
		});
		deferred.done(function(data) {
			if(data.status == "OK") {
				$("#err").html("Successfully saved pseudoweek.");
				$("#err").fadeIn(1000, function() {
					$("#err").fadeOut(1000);
				});
				
			}
			else {
				$("#err").html("An unknown error has occured");
				$("#err").fadeIn(1000);
			}
		});
		deferred.fail(function(xhr, status) {
			$("#err").html("A problem occured during request: " + status);
		});
	});
});
</script>


<jsp:include page="../includes/footer.jsp"/>


