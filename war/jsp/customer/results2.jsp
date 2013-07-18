<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %><%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreService" %>
<%@ page import="com.google.appengine.api.datastore.Query" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>

<jsp:include page="../includes/header.jsp"/>


<jsp:include page="../includes/menu/index.jsp" flush="true"/>

<%
	String required = "<font size='-2' color='red'>*</font>";
	
    int pageLimit=25;
	int cursor=0,size=0;
	String sCursor = (String)request.getParameter("cursor");
	Integer iSize = (Integer)request.getAttribute("size");
	
	if (iSize==null) {
		String sSize = request.getParameter("size");
		
		if (sSize==null) 
			size=0;
		else
			size=Integer.parseInt(sSize);
	} else 
		size=iSize.intValue();
		
	if (sCursor!=null)
		cursor=Integer.parseInt(sCursor);
		
%>
		<!----Main Content--->
		<td valign="top">
			<table width="820" border="0" cellpadding="0" cellspacing="0">
				<tr><td><br><!--img src="<%=request.getContextPath()%>/images/header_top.png"--></td></tr>
				<tr>
				<td class="content-body">
				<img src="<%=request.getContextPath()%>/images/titles/customer-results.jpg">
				<br><br>
				
				<!--
				 <table width="620" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
				          <tr>
							    <td colspan="6" class="results-section">Results 1-2 of 2.</td>
						  </tr>
				 </table>
				 -->
				 <form method="POST" action="<%=request.getContextPath()%>/customer">
				<table width="620" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
				          <tr>
				          	<td width="60" height="30" align="left" valign="top">
				               		<input onfocus="this.value='';" name="key" value="firstname lastname" maxlength="50" size="50" class="filterEntry">
				          	</td>
				          	
				          	<td valign="top" align="left">
							       <input type="submit" name="action" value="Search" class="buttonFilter">
                                   <input type="submit" name="action" value="Add Customer" class="buttonAdd">
                                    <a href="<%=request.getContextPath()%>/customer?entity=Customer">Advanced Search</a>&nbsp;&nbsp;
							</td>
						  </tr>
				 </table>
				 	
				 </form>
				 <div class="results" style="border:0;" id="customer-list-ctr">
                         
                                      <table width="600" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
                                       <tr>
                                            <td  class="grid">
                                                                          <table width="600" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
                                      <tr>
                                            <td  class="grid-heading" >Search Results</td>
                                      </tr>  
                                      </table>
                                      <table width="600" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
                                       <tr>
                                            <td  class="grid-columnHeading" width="40"><a href="<%=request.getContextPath()%>/customer?action=Sort&property=lastName" style="text-decoration:font-family:"Courier New", Courier, monospace;font-size:medium;color:#5e82a6;">Id</a></td>
                                            <td  class="grid-columnHeading" width="140"><a href="<%=request.getContextPath()%>/customer?action=Sort&property=lastName" style="text-decoration:font-family:"Courier New", Courier, monospace;font-size:medium;color:#5e82a6;">Last Name</a></td>
                                            <td  class="grid-columnHeading" width="100"><a href="<%=request.getContextPath()%>/customer?action=Sort&property=firstName" style="text-decoration:font-family:"Courier New", Courier, monospace;font-size:medium;color:#5e82a6;">First Name</a></td>
                                            <td  class="grid-columnHeading" width="100"><a href="<%=request.getContextPath()%>/customer?action=Sort&property=firstName" style="text-decoration:font-family:"Courier New", Courier, monospace;font-size:medium;color:#5e82a6;">Created By</a></td>
                                            <td  class="grid-columnHeading" width="100"></td>
                                       </tr>
                                       <%
                                        int row=0;
                                        Iterable<Entity> entities = (Iterable<Entity>)request.getAttribute("results");
                                        Map<String, Object> properties = null;
                                        
                                        if (entities!=null) 
                                        for (Entity result : entities) {
                                          properties = result.getProperties();
                                      %>
                                      <% 
                                      if (row==cursor) { %>
                                      <form method="POST" action="<%=request.getContextPath()%>/customer">
                                          <tr>
                                            <td class="grid-Id"><%=result.getKey().getId()%></td>
                                            <td class="grid-results"><%=properties.get("lastName")%></td>
                                            <td class="grid-results"><%=properties.get("firstName")%>&nbsp;<%=properties.get("middleInitial")%></td>
                                            <td class="grid-results"><%=properties.get("createdBy")%></td>
                                            <td class="grid-buttons"> 
                                                <input type="submit" name="action" value="Edit" class="buttonAction">
                                                <input type="submit" name="action" value="Delete" class="buttonAction">
                                            </td>
                                          </tr>
                                          <input type="hidden" name="id" value="<%=result.getKey().getId()%>" />  
                                       </form>
                                         <% 
                                              cursor++;
                                              if (cursor%pageLimit==0) break;						  
                                               }
                                               row++;
                                               
                                              //if (cursor%pageLimit==0) break;
                                              } 
                                              if (cursor==0) {%>
                                              <tr>
                                                    <td class="results-bg" colspan="9">no data found</td>
                                              </tr>
                                        <% } %>
                                     </table> 
                                   </td>
                                 </tr>
                               </table>
				  	</div>
				  	
				  	<br>
				  	
				  	<table width="620" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
				          <tr>
							    <td class="results-page-link">
							    
							    <% if (size/pageLimit!=0) { %>
							    	<%	if ( (cursor/pageLimit)>1 ) { %>
							    			<b><a href="<%=request.getContextPath()%>/customer?action=Search&cursor=<%=cursor-(pageLimit*2)%>&size=<%=size%>"><< Previous</a></b>
							    	<% } %>
							    	<i>&nbsp;&nbsp;(pages <%=cursor/pageLimit%> of <%=size/pageLimit%>)</i>&nbsp;&nbsp;
							    	<%	if ((cursor/pageLimit)<(size/pageLimit)) { %>
							    		<b><a href="<%=request.getContextPath()%>/customer?action=Search&cursor=<%=cursor%>&size=<%=size%>">Next >></a></b>
							    	<% } %>
							    <% } else { %>
							   		<i>&nbsp;&nbsp;(pages 1 of 1)</i>&nbsp;&nbsp;
							    <% } %>
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


