<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreService" %>
<%@ page import="com.google.appengine.api.datastore.Query" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ page import="java.util.ArrayList" %>
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
	
	ArrayList entities = (ArrayList)session.getAttribute("results");

%>
		<!----Main Content--->
		<td valign="top">
			<table width="820" border="0" cellpadding="0" cellspacing="0">
				<tr><td><br><!--img src="<%=request.getContextPath()%>/images/header_top.png"--></td></tr>
				<tr>
				<td class="content-body">
				 <table>
				    	<tr>
				    		<td class="text1">Current Sales/Deliveries</td>
				    	</tr>
				    
				    </table>
				<br>
				<jsp:include page="../includes/errors.jsp" flush="true"/>
					
				
				<!--
				 <table width="620" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
				          <tr>
							    <td colspan="6" class="results-section">Results 1-2 of 2.</td>
						  </tr>
				 </table>
				 -->
				 <form method="POST" action="<%=request.getContextPath()%>/sales">
				<table width="480" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
                <tr>
                	<td class="filterPerson"><i>Filter By:</i>
                    		<table width="590" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
				            		
                                   
                             <tr>
                               <td width="100" class="field-filter">Order#</td>
                               <td width="100" class="field-filter">First Name</td>
                               <td width="50" class="field-filter">Middle Initial</td>
							   <td width="100" class="field-filter">Last Name</td>
							 </tr>
							 <tr>
							   <td><input name="orderNumber" value="" maxlength="20" size="20" class="entry"></td>
                               <td><input name="firstName" value="" maxlength="30" size="20" class="entry"></td>
                               <td><input name="middleInitial" value="" maxlength="1" size="1" class="entry"></td>
                               <td><input name="lastName" value="" maxlength="30" size="20" class="entry"></td>
                               </tr>
                              <tr>
                               <td width="100" class="field-filter">Zipcode</td>
							   <td width="100" class="field-filter">Status</td>
                               <td colspan="2" class="field-filter">Date</td>
							  </tr>
                               <tr>
                               <td><input name="zipcode" value="" maxlength="10" size="10" class="entry"></td>
							               	 <td>
                                             	<% ArrayList ddl = (ArrayList)session.getAttribute("ddlDispatchStatus"); %>
                                             		<select name="status" class="ddlentry">
                                                    	<option value=""></option>
                                                        <% if (ddl!=null) {
                                                        	for (int j=0;j<ddl.size();j++) { %>
                                                              <option value="<%=ddl.get(j)%>" <% if (ddl.get(j).equals(request.getParameter("status"))) {%>selected<%}%>><%=ddl.get(j)%></option>
                                                           <%  } 
                                                        } %>
                                                    </select> 
                                 </td>
                                 <td ><input name="salesOrderDate" value="" maxlength="10" size="10" class="tcal"></td>
                                 <td colspan="2" align="left"> 
                        				 	<input type="submit" name="action" value="Filter" class="buttonBlank">
										 	<input type="submit" name="action" value="Create" class="buttonBlank">
								</td>
                                 </tr>
                                 <tr>
                                 <!-- 	<td align="left">
                                    	       <input type="submit" name="action" value="Filter" class="buttonFilter">
                                  		       <input type="submit" name="action" value="Add" class="buttonAdd">
                           			  </td>
                           			  --->
                                  </tr>
                               
                           
                          </table>
                         
                        </td>
                       </tr>
				 </table>
                  <br />
				 </form>
	<!-- -display results on after search -->
	<% if (entities!=null) { %>
				 <div class="results" style="border:0;" id="customer-list-ctr">
                         
                                      <table width="800" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
                                       <tr>
                                       <td  class="grid">
                                       <table width="800" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
                                      <tr>
                                            <td  class="grid-heading" >Search Results</td>
                                      </tr>  
                                      </table>
                                      <table width="800" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
                                       <tr>
                                       		<td  class="grid-columnHeading" width="60"> <a href="<%=request.getContextPath()%>/sales?action=Sort&property=orderNumber&sortDirection=<%=request.getParameter("sortDirection")%>" style="text-decoration:font-family:"Courier New", Courier, monospace;font-size:medium;color:#5e82a6;">Order#</a></td>
                                            <td  class="grid-columnHeading" width="120"><a href="<%=request.getContextPath()%>/sales?action=Sort&property=lastName&sortDirection=<%=request.getParameter("sortDirection")%>" style="text-decoration:font-family:"Courier New", Courier, monospace;font-size:medium;color:#5e82a6;">Last Name</a></td>
                                            <td  class="grid-columnHeading" width="120"><a href="<%=request.getContextPath()%>/sales?action=Sort&property=firstName&sortDirection=<%=request.getParameter("sortDirection")%>" style="text-decoration:font-family:"Courier New", Courier, monospace;font-size:medium;color:#5e82a6;">First Name</a></td>
                                            <td  class="grid-columnHeading" width="60"><a href="<%=request.getContextPath()%>/sales?action=Sort&property=zipcode&sortDirection=<%=request.getParameter("sortDirection")%>" style="text-decoration:font-family:"Courier New", Courier, monospace;font-size:medium;color:#5e82a6;">Zipcode</a></td>
                                            <td  class="grid-columnHeading" width="60"><a href="<%=request.getContextPath()%>/sales?action=Sort&property=status&sortDirection=<%=request.getParameter("sortDirection")%>" style="text-decoration:font-family:"Courier New", Courier, monospace;font-size:medium;color:#5e82a6;">Status</a></td>
                                            <td  class="grid-columnHeading" width="150"><a href="<%=request.getContextPath()%>/sales?action=Sort&property=salesOrderDate&sortDirection=<%=request.getParameter("sortDirection")%>" style="text-decoration:font-family:"Courier New", Courier, monospace;font-size:medium;color:#5e82a6;">Order Date</a></td>
                                            <td  class="grid-columnHeading" width="130"></td>
                                       </tr>
                                       <%
                                        int row=0;
                                       
										Map<String, Object> properties = null;
                                        if (entities!=null) 
                                        for (int i=0;i<entities.size();i++) {
                                        //for (Entity result : entities) {
                                         Entity result=(Entity)entities.get(i);
                                         properties = result.getProperties();
                                      %>
                                      <% 
                                      if (row==cursor) { %>
                                      <form method="POST" action="<%=request.getContextPath()%>/sales">
                                          <tr>
                                          	<td class="grid-Id"><%=properties.get("orderNumber")%></td>
                                            <td class="grid-results"><%=properties.get("lastName")%></td>
                                            <td class="grid-results"><%=properties.get("firstName")%></td>
                                            <td class="grid-results"><%=properties.get("zipcode")%></td>
                                            <td class="grid-results"><%=properties.get("status")%></td>
                                            <td class="grid-results"><%=properties.get("salesOrderDate")%></td>
                                            <td class="grid-buttons"> 
                                                <input type="submit" name="action" value="Print" class="buttonAction">
                                                <input type="submit" name="action" value="Edit" class="buttonAction">
                                                <input type="submit" name="action" value="Delete" class="buttonAction">
                                            </td>
                                          </tr>
                                          <input type="hidden" name="salesOrderKey" value="<%=KeyFactory.keyToString(result.getKey())%>" /> 
                                      	  <input type="hidden" name="salesOrderId" value="<%=result.getKey().getId()%>"/>
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
							    			<b><a href="<%=request.getContextPath()%>/jsp/sales/results.jsp?action=Search&cursor=<%=cursor-(pageLimit*2)%>&size=<%=size%>"><< Previous</a></b>
							    	<% } %>
							    	<i>&nbsp;&nbsp;(pages <%=cursor/pageLimit%> of <%=size/pageLimit%>)</i>&nbsp;&nbsp;
							    	<%	if ((cursor/pageLimit)<(size/pageLimit)) { %>
							    		<b><a href="<%=request.getContextPath()%>/jsp/sales/results.jsp?action=Search&cursor=<%=cursor%>&size=<%=size%>">Next >></a></b>
							    	<% } %>
							    <% } else { %>
							   		<i>&nbsp;&nbsp;(pages 1 of 1)</i>&nbsp;&nbsp;
							    <% } %>
							    </td>
						  </tr>
						 <tr>
							<td align="left"> 
								 <form method="POST" action="<%=request.getContextPath()%>/sales">
								 	 	<input type="submit" name="action" value="Create" class="buttonBlank">
								 </form>
							</td>
						 </tr>
				 </table>
	<% } %>	
				<tr><td><!--img src="<%=request.getContextPath()%>/images/header_btm.png"--></td></tr>
			</table>
		</td>
	</tr>	
	

 

  	
  	</div>
</table>



<jsp:include page="../includes/footer.jsp"/>


