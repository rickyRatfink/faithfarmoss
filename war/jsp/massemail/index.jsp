<%@ page import="org.faithfarm.dataobjects.PurchaseOrder" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<jsp:include page="../includes/header.jsp"/>

<jsp:include page="../includes/menu/index.jsp" flush="true"/>
<script type="text/javascript" src="<%= request.getContextPath() %>/script/handlebars-1.0.0.beta.6.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/script/spin.min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.8.23/jquery-ui.min.js"></script>
<link rel="stylesheet" href="http://ajax.aspnetcdn.com/ajax/jquery.ui/1.8.24/themes/smoothness/jquery-ui.css"/>
<%
List<String> errors = (List<String>)request.getAttribute("errors");
%>
<!----Main Content--->
<td>
  <table width="820" border="0" cellpadding="0" cellspacing="0">
    <tr>
      <td>
        <br>
        <!--img src="<%=request.getContextPath()%>/images/header_top.png"-->
      </td>
    </tr>

    <tr>
      <td class="content-body">
        <div class="content">
        	<h2 class="text1">Mass Email</h2>
        	<%
        	if(errors != null && errors.size() > 0) {
        		%>
        		<ul id="errors" style="color:#ff0000;font-weight:bold;">
        		<%
        		for(String error : errors) {
        			%>
        			<li><%= error %></li>
        			<%
        		}
        		%>
        		</ul>
        		<%
        	}
        	%>
        	<form action="/massemail?action=search" method="POST">
<table width="400" cellspacing="0" cellpadding="0" border="0" style="border-collapse: collapse;">


					<tr>
						<td class="filterPerson"><i> Filter By: </i>
							<table width="620" cellspacing="0" cellpadding="0" border="0" style="border-collapse: collapse;">




								<tr>
									<td width="100" class="field-filter">Start Date</td>
									<td width="100" class="field-filter">End Date</td>
									<td width="80" class="field-filter">Person Type</td>
								</tr>
								<tr>
									<td><input name="startDate" id="startDate" value="" class="datepick" maxlength="20" size="20" class="entry"></td>
									<td><input name="endDate" id="endDate" value="" class="datepick" maxlength="20" size="20" class="entry"></td>
									<td><select name="personType" id="personType" class="entry">
											
											<option value=""></option>
											
											<option value="Customer" >
												Customer
											</option>
											
											
									</select></td>
								</tr>
								<tr>
									<td colspan="4" align="right"></br> <input type="submit" name="action" value="Filter" class="buttonBlank"> <input type="button" value="Clear" class="buttonBlank"
										onClick="javascript:clearFilter();" /> &nbsp;&nbsp;</td>
								</tr>

							</table></td>
					</tr>
				</table>
        			
        	</form>
		</div>
      </td>
    </tr>
  </table>
</td>
<script type="text/javascript">
$(document).ready(function() {
  $(".datepick").datepicker();
});

function clearFilter() {
  $(".datepick").val('');
  $("")
}
</script>
<jsp:include page="../includes/footer.jsp"/>





        