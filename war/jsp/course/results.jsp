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
	
	
%>
		<!----Main Content--->
		<td valign="top">
			<table width="820" border="0" cellpadding="0" cellspacing="0">
				<tr><td><br><!--img src="<%=request.getContextPath()%>/images/header_top.png"--></td></tr>
				<tr>
				<td class="content-body">
				<img src="<%=request.getContextPath()%>/images/titles/course-results.jpg">
				<br>
				<jsp:include page="../includes/errors.jsp" flush="true"/>
					
				
				<!--
				 <table width="620" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
				          <tr>
							    <td colspan="6" class="results-section">Results 1-2 of 2.</td>
						  </tr>
				 </table>
				 -->
				 <form method="POST" action="<%=request.getContextPath()%>/course">
				<table width="620" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
                <tr>
                	<td class="filterPerson"><i>Filter By:</i>
                    		<table width="620" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
				            		
                                   
                             <tr>
							                 <td width="150" class="field-filter">Course Title</td>
                                             <td width="60" class="field-filter">Course Number</td>
							                 <td width="150" class="field-filter">Farm Location</td>
							                 <td width="60" class="field-filter">Status</td>
                               <td width="70"></td>
							               </tr>
                                           <tr>
							               	 <td><input name="title" value="" maxlength="40" size="40" class="entry"></td>
                               <td><input name="number" value="" maxlength="10" size="10" class="entry"></td>
							               	 <td>
                                             	<% ArrayList ddl = (ArrayList)session.getAttribute("ddlFarms"); %>
                                             		<select name="farmBase" class="entry">
                                                    	<option value=""></option>
                                                        <% if (ddl!=null) {
                                                        	for (int j=0;j<ddl.size();j++) { %>
                                                              <option value="<%=ddl.get(j)%>" <% if (ddl.get(j).equals(request.getParameter("farmBase"))) {%>selected<%}%>><%=ddl.get(j)%></option>
                                                           <%  } 
                                                        } %>
                                                    </select> 
                                             </td>
                                             <td><select name="status" class="entry-small">
							               	 	    <option value=""></option>
							               	 		<option value="Active">Active</option>
							               	 		<option value="Inactive">Inactive</option>
							               	 	</select>
                                             </td>
                                             <td align="left">
                                             	       <input type="submit" name="action" value="Search" class="buttonFilter">
                                   				       <input type="submit" name="action" value="Add" class="buttonAdd">
                           					 </td>
                                     </tr>
                                     
                              <!--
                             <tr>
				          	<td width="60" height="30" align="left" valign="top">
				               		<input onfocus="this.value='';" name="key" value="firstname lastname" maxlength="50" size="50" class="filterEntry">
				          	</td>

				          	<td width="60" height="30" align="left" valign="top">&nbsp;&nbsp;
				             	<select name="personType" class="entry-small">
							               	 	    <option value="">Type</option>
							               	 		<option value="Customer">Customer</option>
							               	 		<option value="Donor">Donor</option>
							               	 		<option value="Intake">Intake</option>
                                                   	<option value="Intern">Intern</option>
							               	 		<option value="Staff">Staff</option>
							               	 		<option value="Student">Student</option>
							               	 	</select>
                            </td>
				          	-->
                           
                          </table>
                         
                        </td>
                       </tr>
				 </table>
                  <br />
				 </form>
				 <div class="results" style="border:0;" id="customer-list-ctr">
                         
                                      <table width="700" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
                                       <tr>
                                       <td  class="grid">
                                       <table width="700" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
                                      <tr>
                                            <td  class="grid-heading" >Search Results</td>
                                      </tr>  
                                      </table>
                                      <table width="700" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
                                       <tr>
                                       			<td  class="grid-columnHeading" width="40"><a href="<%=request.getContextPath()%>/customer?action=Sort&property=title&sortDirection=<%=request.getParameter("sortDirection")%>" style="text-decoration:font-family:"Courier New", Courier, monospace;font-size:medium;color:#5e82a6;">Id</a></td>
                                            <td  class="grid-columnHeading" width="140"><a href="<%=request.getContextPath()%>/customer?action=Sort&property=title&sortDirection=<%=request.getParameter("sortDirection")%>" style="text-decoration:font-family:"Courier New", Courier, monospace;font-size:medium;color:#5e82a6;">Course Title</a></td>
                                            <td  class="grid-columnHeading" width="100"><a href="<%=request.getContextPath()%>/customer?action=Sort&property=number&sortDirection=<%=request.getParameter("sortDirection")%>" style="text-decoration:font-family:"Courier New", Courier, monospace;font-size:medium;color:#5e82a6;">Number</a></td>
                                            <td  class="grid-columnHeading" width="100"><a href="<%=request.getContextPath()%>/customer?action=Sort&property=instructor&sortDirection=<%=request.getParameter("sortDirection")%>" style="text-decoration:font-family:"Courier New", Courier, monospace;font-size:medium;color:#5e82a6;">Instructor</a></td>
                                            <td  class="grid-columnHeading" width="100"><a href="<%=request.getContextPath()%>/customer?action=Sort&property=location&sortDirection=<%=request.getParameter("sortDirection")%>" style="text-decoration:font-family:"Courier New", Courier, monospace;font-size:medium;color:#5e82a6;">Enrolled</a></td>
                                            <td  class="grid-columnHeading" width="100"><a href="<%=request.getContextPath()%>/customer?action=Sort&property=location&sortDirection=<%=request.getParameter("sortDirection")%>" style="text-decoration:font-family:"Courier New", Courier, monospace;font-size:medium;color:#5e82a6;">Seq</a></td>
                                            <td  class="grid-columnHeading" width="100"><a href="<%=request.getContextPath()%>/customer?action=Sort&property=location&sortDirection=<%=request.getParameter("sortDirection")%>" style="text-decoration:font-family:"Courier New", Courier, monospace;font-size:medium;color:#5e82a6;">Farm</a></td>
                                            <td  class="grid-columnHeading" width="100"></td>
                                       </tr>
                                       <%
                                        int row=0;
                                        Iterable<Entity> entities = (Iterable<Entity>)session.getAttribute("results");
                                        Map<String, Object> properties = null;
                                        
                                        if (entities!=null) 
                                        for (Entity result : entities) {
                                          properties = result.getProperties();
                                      %>
                                      <% 
                                      if (row==cursor) { %>
                                      <form method="POST" action="<%=request.getContextPath()%>/course">
                                          <tr>
                                          	 <td class="grid-Id"><%=result.getKey().getId()%></td>
                                            <td class="grid-results"><%=properties.get("title")%></td>
                                            <td class="grid-results"><%=properties.get("number")%></td>
                                            <td class="grid-results"><%=properties.get("instructor")%></td>
                                            <%
                                            String count="0";
                                            if ( request.getAttribute("enrollment_"+row)!=null)
                                               count=(String)request.getAttribute("enrollment_"+row);
                                            %>
                                            <td class="grid-results"><%=count%>&nbsp;&nbsp;<% if (!"0".equals(count)){%><input type="submit" name="action" value="Roster" class="buttonAction"><%}%><input type="hidden" name="seq" value="<%=properties.get("sequence")%>"></td>
                                            <td class="grid-results"><%=properties.get("sequence")%></td>
                                           <td class="grid-results"><%=properties.get("farmBase")%></td>
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
							    			<b><a href="<%=request.getContextPath()%>/jsp/course/results?action=Search&cursor=<%=cursor-(pageLimit*2)%>&size=<%=size%>"><< Previous</a></b>
							    	<% } %>
							    	<i>&nbsp;&nbsp;(pages <%=cursor/pageLimit%> of <%=size/pageLimit%>)</i>&nbsp;&nbsp;
							    	<%	if ((cursor/pageLimit)<(size/pageLimit)) { %>
							    		<b><a href="<%=request.getContextPath()%>/jsp/course/results?action=Search&cursor=<%=cursor%>&size=<%=size%>">Next >></a></b>
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


