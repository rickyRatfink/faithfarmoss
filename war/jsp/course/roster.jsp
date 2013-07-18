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
<%@ page import="java.util.ArrayList" %>

<jsp:include page="../includes/header.jsp"/>
<jsp:include page="../includes/menu/index.jsp" flush="true"/>


<%
	String required = "<font size='-2' color='red'>*</font>";
	
    int pageLimit=45;
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
	
	Entity Course = (Entity)request.getAttribute("course");
	
%>
		<!----Main Content--->
		<td valign="top">
			<table width="820" border="0" cellpadding="0" cellspacing="0">
				<tr><td><br><!--img src="<%=request.getContextPath()%>/images/header_top.png"--></td></tr>
				<tr>
				<td class="content-body">
				    <table>
				    	<tr>
				    		<td class="text1">Course Roster</td>
				    	</tr>
				    	<tr>
				    		<td class="course">
				    			<%=Course.getProperty("title")%>,<%=Course.getProperty("sequence")%>
				    	</td>
				    	</tr>
				    </table>
				<br>
				<jsp:include page="../includes/errors.jsp" flush="true"/>
						
				
         <br />
				 
				 <div class="results" style="border:0;" id="customer-list-ctr">
                         
                                      <table width="700" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
                                       <tr>
                                       <td  class="grid">
                                       <table width="700" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
                                      <tr>
                                            <td  class="grid-heading" >Search Results</td>
                                      </tr>  
                                      </table>
                                      <form method="POST" action="<%=request.getContextPath()%>/course">
                                      <table width="700" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
                                       <tr>
                                            <td  class="grid-columnHeading" width="40"><a href="<%=request.getContextPath()%>/customer?action=Sort&property=title&sortDirection=<%=request.getParameter("sortDirection")%>" style="text-decoration:font-family:"Courier New", Courier, monospace;font-size:medium;color:#5e82a6;">Id</a></td>
                                            <td  class="grid-columnHeading" width="140"><a href="<%=request.getContextPath()%>/customer?action=Sort&property=title&sortDirection=<%=request.getParameter("sortDirection")%>" style="text-decoration:font-family:"Courier New", Courier, monospace;font-size:medium;color:#5e82a6;">Last Name</a></td>
                                            <td  class="grid-columnHeading" width="100"><a href="<%=request.getContextPath()%>/customer?action=Sort&property=number&sortDirection=<%=request.getParameter("sortDirection")%>" style="text-decoration:font-family:"Courier New", Courier, monospace;font-size:medium;color:#5e82a6;">First Name</a></td>
                                            <td  class="grid-columnHeading" width="100"><a href="<%=request.getContextPath()%>/customer?action=Sort&property=location&sortDirection=<%=request.getParameter("sortDirection")%>" style="text-decoration:font-family:"Courier New", Courier, monospace;font-size:medium;color:#5e82a6;">DOB</a></td>
                                            <td  class="grid-columnHeading" width="100"><a href="<%=request.getContextPath()%>/customer?action=Sort&property=location&sortDirection=<%=request.getParameter("sortDirection")%>" style="text-decoration:font-family:"Courier New", Courier, monospace;font-size:medium;color:#5e82a6;">Status</a></td>
                                            <!--<td  class="grid-columnHeading" width="100"></td>-->
                                       </tr>
                                       <%
                                        int row=0;
                                        Iterable<Entity> entities = (Iterable<Entity>)session.getAttribute("results");
                                        
                                        Map<String, Object> properties = null;
                                        
                                        if (entities!=null) {                                  
                                        for (Entity result : entities) {
                                        	if (result!=null) {
					                                          properties = result.getProperties();
					                                      %>
					                                      <% 
					                                      if (row==cursor) { %>
					                                      
					                                          <tr>
					                                            <td class="grid-Id"><%=result.getKey().getId()%></td>
					                                            <td class="grid-results"><%=properties.get("lastName")%></td>
					                                            <td class="grid-results"><%=properties.get("firstName")%></td>
					                                           <td class="grid-results"><%=properties.get("dateOfBirth")%></td>
					                                            <td class="grid-results"><%=properties.get("personStatus")%></td>
					                                          </tr>
					                                          <input type="hidden" name="id_<%=row%>" value="<%=result.getKey().getId()%>" /> 
					                                      
					                                         <% 
					                                              cursor++;
					                                              if (cursor%pageLimit==0) break;						  
					                                               }
					                                               row++;
					                                               
					                                              //if (cursor%pageLimit==0) break;
					                                            } // end of row==cursor if
					                                            }// end of result!=null if
					                                              if (cursor==0) {%>
					                                              <tr>
					                                                    <td class="results-bg" colspan="5">no data found</td>
					                                              </tr>
					                                     
                                        <% } } %>
                                    
                                     </table> 
                                      <input type="hidden" name="studentCount" value="<%=row%>" /> 
                                      <input type="hidden" name="courseId" value="<%=properties.get("course")%>" /> 
                                     
                                   </td>
                                 </tr>
                                  <tr>
                                     		<td colspan="5" height="60" valign="center" align="center">
                                     			<!--<input type="submit" name="action" value="Rotate" class="buttonRotate">-->
                                     		</td>
                                    </tr>
                               </table>
                                </form>
				  	</div>
				  	
				  	<br>
				  	
				  	<table width="620" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
				          <tr>
							    <td class="results-page-link">
							    
							    <% if (size/pageLimit!=0) { %>
							    	<%	if ( (cursor/pageLimit)>1 ) { %>
							    		<b><a href="<%=request.getContextPath()%>/jsp/course/roster?action=Search&cursor=<%=cursor-(pageLimit*2)%>&size=<%=size%>"><< Previous</a></b>
							    	<% } %>
							    	<i>&nbsp;&nbsp;(pages <%=cursor/pageLimit%> of <%=size/pageLimit%>)</i>&nbsp;&nbsp;
							    	<%	if ((cursor/pageLimit)<(size/pageLimit)) { %>
							    		<b><a href="<%=request.getContextPath()%>/jsp/course/roster?action=Search&cursor=<%=cursor%>&size=<%=size%>">Next >></a></b>
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

<script language="javascript">



	var checked=false;
	
	function test(){
	if (!checked) {
		for (var i = 0; i < <%=row%>; i++) {
			//alert('>'+document.forms[0].elements['student_'+i].value);
			document.forms[0].elements['student_'+i].checked=true;
		}
	  checked=true;
	}
	else {
	 for (var i = 0; i <  <%=row%>; i++) {
			document.forms[0].elements['student_'+i].checked=false;
		}
	 	checked=false;	
	}
}	
	
</script>



<jsp:include page="../includes/footer.jsp"/>


