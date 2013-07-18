<%@ page import="org.faithfarm.dataobjects.PurchaseOrder" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<jsp:include page="../includes/header.jsp"/>

<jsp:include page="../includes/menu/index.jsp" flush="true"/>
<script type="text/javascript" src="<%= request.getContextPath() %>/script/handlebars-1.0.0.beta.6.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/script/spin.min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.8.23/jquery-ui.min.js"></script>
<link rel="stylesheet" href="http://ajax.aspnetcdn.com/ajax/jquery.ui/1.8.24/themes/smoothness/jquery-ui.css"/>
<script type="text/javascript" src="/script/tiny_mce/tiny_mce.js"></script>
<script type="text/javascript">
tinyMCE.init({
  mode: 'textareas'
});

$(document).ready(function() {
  
  $(".check-all").click(function(e) {
    $(".email_checkbox").each(function(idx, el) {
      $(el).attr('checked', true);
    });
  });
  
  $(".uncheck-all").click(function(e) {
    $(".email_checkbox").each(function(idx, el) {
      $(el).attr('checked', false);
    });
  });
  
  var $sendForm = $("#sendForm");
  $sendForm.submit(function(e) {
    $(".hidden_emails").remove();
    $(".email_checkbox:checked").each(function(idx, el) {
      var $hidden_field = $('<input class="hidden_emails" type="hidden" name="emails[]"/>');
      var data = $(el).parent().data();
      console.log(data);
      var value = data.email + "::" + data.first + "::" + data.last;
      console.log(value);
      $hidden_field.attr('value', value);
      $sendForm.append($hidden_field);
      
    });

    return true;
  });
});


</script>

<%
List<Entity> results = (List<Entity>)request.getAttribute("results");
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
        	<div>&nbsp;</div>
        	<div><strong><%= String.valueOf(results.size()) + (results.size() == 1 ? " person" : " people") %> will be emailed.</strong></div>
        	<div>&nbsp;</div>
        	<form action="/massemail?action=sendmail"  id="sendForm" method="POST">
        	<table width="640px" cellpadding="0" cellspacing="0">
        		<tr><td style="padding-right:5px;vertical-align:top;"><strong>Subject:</strong></td><td style="width: 95%;"><input type="text" name="subject" style="width: 94%;"/></td></tr>
        		<tr><td style="padding-right:5px;vertical-align:top;"><strong>Body:</strong></td><td style="width: 95%;">
        		<textarea id="email_text" name="email_text" style="width:94%;">
        		<p>Dear Mr. or Mrs. %last%,</p>
        		<p>
        		We'd like to thank you for shopping at Faith Farm Ministries recently.  We hope you were able to find everything you needed and look forward to seeing you again soon.  If you'd like to make a contribution to Faith Farm Ministries and further impact the lives of the men and women who receive help from Faith Farm Ministries, please click <a href="https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=N5AY3MUZ9LDS8">here</a>.
      
        		</p>
        		<p>Sincerely,</p>
        		<p>Faith Farm Ministries</p>
        		</textarea></td></tr>
        		<tr><td colspan="2" style="text-align:right;padding-top:10px;"><input type="submit" value="Send"/></td></tr>
        	</table>

        	<div>&nbsp;</div>
        	<div><a href="#" class="check-all">Check All</a>&nbsp;&nbsp;&nbsp;<a href="#" class="uncheck-all">Uncheck All</a></div>
			<table width="600" cellspacing="0" cellpadding="0" border="0" style="border-collapse: collapse;">
				<tr>
					<td class="grid">
						<table width="640" cellspacing="0" cellpadding="0" border="0" style="border-collapse: collapse;">
							<tr>
								<td class="grid-heading">Search Results</td>
							</tr>
						</table>
						<table width="640" cellspacing="0" cellpadding="0" border="0" style="border-collapse: collapse;">
							<tr>
								<td class="grid-columnHeading" width="40">To</td>
								<td class="grid-columnHeading" width="120">Last Name</td>
								<td class="grid-columnHeading" width="120">First Name</td>
								<td class="grid-columnHeading" width="140">Person Type</a></td>
								<td class="grid-columnHeading" width="100">Email</td>
							</tr>

							<%
							for(Entity res : results) {
			        			String email = (String)res.getProperty("email");
			        			String firstName = res.getProperty("firstName") != null ? (String)res.getProperty("firstName") : "";
			        			String lastName = res.getProperty("lastName") != null ? (String)res.getProperty("lastName") : "";
			        			if(email != null && !email.equals("")) {
			        				%>

									<tr>
										<td class="grid-Id" data-email="<%= email %>" data-first="<%= firstName %>" data-last="<%= lastName %>"><input type="checkbox" checked="checked" class="email_checkbox"/></td>
										<td class="grid-results"><%= res.getProperty("lastName") != null ? res.getProperty("lastName") : "" %></td>
										<td class="grid-results"><%= res.getProperty("firstName") != null ? res.getProperty("firstName") : "" %></td>
										<td class="grid-results"><%= res.getProperty("personType") != null ? res.getProperty("personType") : "" %></td>
										<td class="grid-results"><%= res.getProperty("email") != null ? res.getProperty("email") : "" %></td>
									</tr>
									<%
			        			}
	
							}
							%>


						</table>
					</td>
				</tr>
			</table>
			</div>
      </td>
    </tr>
  </table>
</td>
<script type="text/javascript">
$(document).ready(function() {
  
});

</script>
<jsp:include page="../includes/footer.jsp"/>





        