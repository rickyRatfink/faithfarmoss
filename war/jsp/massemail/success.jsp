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
</script>

<%
Integer count = request.getAttribute("count") != null ? (Integer)request.getAttribute("count") : 0;
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
        	<h3>Successfully queued messages to be sent.</h3>
        	<div>&nbsp;</div>
        	<div><%= String.valueOf(count) + (count == 1 ? " person" : " people") %> will be emailed this message.</div>
        	<div>&nbsp;</div>
			
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





        