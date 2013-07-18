<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="org.faithfarm.dataobjects.Benevolence" %>
<jsp:include page="../includes/header.jsp"/>


<jsp:include page="../includes/menu/index.jsp" flush="true"/>

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
							<strong>You are about to process the benevolence for the week of <%= (String)request.getAttribute("reportDay") %>.  This process will adjust the residents' YTD totals and current savings amount to prepare for next week's report.  You should only continue below if you have already printed all reports and are satisfied there are no discrepancies.</strong>
							<div>&nbsp;</div>
							<div>
								<input type="button" value="Process" class="hrefBtn" data-href="/benevolence/ProcessConfirm"/>&nbsp;&nbsp;<input type="button" value="Cancel" class="hrefBtn" data-href="/benevolence"/>
							</div>
							

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
	$(".hrefBtn").click(function(e) {
		location.href = $(this).data('href');
	})
});
</script>


<jsp:include page="../includes/footer.jsp"/>


