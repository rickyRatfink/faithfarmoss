<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>

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
				    <table>
				    	<tr>
				    		<td class="text1">Customer Matches</td>
				    	</tr>
				    	
				    </table>
				<br/><br/>
				
				<!--
				 <table width="620" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
				          <tr>
							    <td colspan="6" class="results-section">Results 1-2 of 2.</td>
						  </tr>
				 </table>
				 -->
				
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
                                            <td  class="grid-columnHeading" width="40"><a href="<%=request.getContextPath()%>/dispatch?action=Sort&property=lastName&sortDirection=<%=request.getParameter("sortDirection")%>" style="text-decoration:font-family:"Courier New", Courier, monospace;font-size:medium;color:#5e82a6;">Id</a></td>
                                            <td  class="grid-columnHeading" width="140"><a href="<%=request.getContextPath()%>/dispatch?action=Sort&property=lastName&sortDirection=<%=request.getParameter("sortDirection")%>" style="text-decoration:font-family:"Courier New", Courier, monospace;font-size:medium;color:#5e82a6;">Last Name</a></td>
                                            <td  class="grid-columnHeading" width="100"><a href="<%=request.getContextPath()%>/dispatch?action=Sort&property=firstName&sortDirection=<%=request.getParameter("sortDirection")%>" style="text-decoration:font-family:"Courier New", Courier, monospace;font-size:medium;color:#5e82a6;">First Name</a></td>
                                            <td  class="grid-columnHeading" width="100"><a href="<%=request.getContextPath()%>/dispatch?action=Sort&property=personType&sortDirection=<%=request.getParameter("sortDirection")%>" style="text-decoration:font-family:"Courier New", Courier, monospace;font-size:medium;color:#5e82a6;">Type</a></td>
                                            <td  class="grid-columnHeading" width="100"><a href="<%=request.getContextPath()%>/dispatch?action=Sort&property=personStatus&sortDirection=<%=request.getParameter("sortDirection")%>" style="text-decoration:font-family:"Courier New", Courier, monospace;font-size:medium;color:#5e82a6;">Status</a></td>
                                            <td  class="grid-columnHeading" width="100"></td>
                                       </tr>
                                       <%
                                        int row=0;
                                        ArrayList entities = (ArrayList)session.getAttribute("results");
                                        Map<String, Object> properties = null;
                                        
                                        if (entities!=null) 
                                        for (int i=0;i<entities.size();i++) {
                                          Entity result = (Entity)entities.get(i);
                                          properties = result.getProperties();
                                      %>
                                      <% 
                                      if (row==cursor) { %>
                                      <form method="POST" action="<%=request.getContextPath()%>/dispatch">
                                          <tr>
                                            <td class="grid-Id"><%=result.getKey().getId()%></td>
                                            <td class="grid-results"><%=((String)properties.get("lastName")).toUpperCase()%></td>
                                            <td class="grid-results"><%=((String)properties.get("firstName")).toUpperCase()%>&nbsp;<%=((String)properties.get("middleInitial")).toUpperCase()%></td>
                                            <td class="grid-results"><%=properties.get("personType")%></td>
                                            <td class="grid-results"><%=properties.get("personStatus")%></td>
                                            <td class="grid-buttons"> 
                                                <input type="submit" name="action" value="Select" class="buttonAction">
                                            </td>
                                          </tr>
                                          <input type="hidden" name="id" value="<%=result.getKey().getId()%>" /> 
                                          <input type="hidden" name="page" value="<%=request.getParameter("page") %>" />
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
                                 <tr>
                                 	<td align="right">
                                 	 </br>
                                 	 <form method="POST" action="<%=request.getContextPath()%>/dispatch">
                                                <input type="submit" name="action" value="New Customer" class="buttonBlankLarge">
                                                <input type="hidden" name="page" value="<%=request.getParameter("page") %>" />
                                     </form>    
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
							    			<b><a href="<%=request.getContextPath()%>/jsp/dispatch/customer_list.jsp?action=Search&cursor=<%=cursor-(pageLimit*2)%>&size=<%=size%>"><< Previous</a></b>
							    	<% } %>
							    	<i>&nbsp;&nbsp;(pages <%=cursor/pageLimit%> of <%=size/pageLimit%>)</i>&nbsp;&nbsp;
							    	<%	if ((cursor/pageLimit)<(size/pageLimit)) { %>
							    		<b><a href="<%=request.getContextPath()%>/jsp/dispatch/customer_list.jsp?action=Search&cursor=<%=cursor%>&size=<%=size%>">Next >></a></b>
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


