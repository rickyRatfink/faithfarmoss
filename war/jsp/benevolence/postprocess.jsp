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
							<%
							Boolean success = (Boolean)request.getAttribute("success");
							if(success) {
								%>
								<strong>Successfully processed benevolence updates.</strong>
								<%
							}
							else {
								%>
								<strong>An error occured during processing.  Please contact IT department.</strong>
								<%
							}
							%>
							

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


