<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreService" %>
<%@ page import="com.google.appengine.api.datastore.Query" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>

<jsp:include page="/jsp/includes/header.jsp" flush="true"/>


<jsp:include page="/jsp/includes/menu/index.jsp" flush="true"/>

<%
	String required = "<font size='-2' color='red'>*</font>";
%>
		<!----Main Content--->
		<td valign="top">
			<table width="820" border="0" cellpadding="0" cellspacing="0">
				<tr><td><br><!--img src="<%=request.getContextPath()%>/images/header_top.png"--></td></tr>
				<tr>
				<td class="content-body">
				<img src="<%=request.getContextPath()%>/images/titles/transaction-history.jpg">
				<br><br>
						
							               	  
				<!--
				 <table width="620" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
				          <tr>
							    <td colspan="6" class="results-section">Results 1-2 of 2.</td>
						  </tr>
				 </table>
				
				<form method="POST" action="<%=request.getContextPath()%>/inventory?entity=Inventory">
				<table width="620" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
				          <tr>
				          	<td width="60" height="30" align="left" valign="top">
				               		<input onfocus="this.value='';" name="itemNumber" value="item#" maxlength="6" size="10" class="filterEntry">
				          	</td>
				          		
				          	<td valign="top" align="left">
							    	<a href="javascript:this.document.forms[0].submit();" style="text-decoration:none;"><img src="<%=request.getContextPath()%>/images/search.jpg" border="0"></a>
							    	<a href="<%=request.getContextPath()%>/inventory?entity=Inventory&action=Add Item" style="text-decoration:none;"><img src="<%=request.getContextPath()%>/images/add.jpg" border="0" ></a>
							    	<a href="<%=request.getContextPath()%>/inventory?entity=Inventory">Advanced Search</a>&nbsp;&nbsp;
							</td>
						  </tr>
				 </table>
				 	<input type="hidden" name="action" value="Search"/>
				 </form>
				 --->     	
				 <div class="results" style="border:0;" id="customer-list-ctr">
				          <table width="620" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
				          
				          <tr>
				                <td  class="results-heading">Entity</td>
				                <td  class="results-heading">Action</td>
				                <td  class="results-heading">Key</td>
				                <td  class="results-heading">Datetime</td>
				          </tr>
				         
				           <%
				            int index=0;
							Iterable<Entity> entities = (Iterable<Entity>)session.getAttribute("results");
							Map<String, Object> properties = null;
							
							if (entities!=null)
							for (Entity result : entities) {
							  properties = result.getProperties();
							  
							  %>
							  <tr>
							   	<td class="results-bg"><%=properties.get("entity")%></td>
							  	<td class="results-bg"><%=properties.get("action")%></td>
							  	<td class="results-bg"><%=properties.get("key")%></td>
							  	<td class="results-bg"><%=properties.get("datetime")%></td>
							  </tr>
							  <% 
							  index++;  
						  } 
						  if (index==0) {%>
						  <tr>
							  	<td class="results-bg" colspan="8">no data found</td>
						  </tr>
						  <% } %>
				            
				         </table> 
				  	</div>
				  	
				  	<br>
				  	
				  	<table width="620" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
				          <tr>
							    <td class="results-page-link">
							    	page(s) &nbsp;&nbsp; <a href="">1</a>&nbsp;&nbsp;
							    </td>
						  </tr>
						 
				 </table>
		
				<tr><td><!--img src="<%=request.getContextPath()%>/images/header_btm.png"--></td></tr>
			</table>
		</td>
	</tr>	
	

 

  	
  	</div>
</table>



<jsp:include page="../includes/footer.jsp"/>


