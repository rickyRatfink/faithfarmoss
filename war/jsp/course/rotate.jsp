<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
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
  int pageLimit=200;
	int cursor=0,size=0,row=0;
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
		
	org.faithfarm.utilities.HTMLBuilder html = new org.faithfarm.utilities.HTMLBuilder();
	
	
%>
		<!----Main Content--->
		<td valign="top">
			<table width="820" border="0" cellpadding="0" cellspacing="0">
				<tr><td><br><!--img src="<%=request.getContextPath()%>/images/header_top.png"--></td></tr>
				<tr>
				<td class="content-body">
				    <table>
				    	<tr>
				    		<td class="text1">Course Rotations</td>
				    	</tr>
				    
				    </table>
				<br>
				<jsp:include page="../includes/errors.jsp" flush="true"/>
					
				
	
				
         <br />
				<form method="POST" action="<%=request.getContextPath()%>/course"> 
				 <div class="results" style="border:0;" id="customer-list-ctr">
                         
                                      <table width="700" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
                                       <tr>
                                       <td  class="grid">
                                       	
                                       
                                     
                                      <table width="700" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
                                       
                                       <tr>
                                            <td  class="grid-columnHeading" width="40"><input type="checkbox" name="checkAll" onclick="javascript:test();"/></td>
                                            <td  class="grid-columnHeading" width="40"><a href="" style="text-decoration:font-family:"Courier New", Courier, monospace;font-size:medium;color:#5e82a6;">Class</a></td>
                                            <td  class="grid-columnHeading" width="140"><a href="" style="text-decoration:font-family:"Courier New", Courier, monospace;font-size:medium;color:#5e82a6;">Last Name</a></td>
                                            <td  class="grid-columnHeading" width="100"><a href="" style="text-decoration:font-family:"Courier New", Courier, monospace;font-size:medium;color:#5e82a6;">First Name</a></td>
                                            <td  class="grid-columnHeading" width="100"><a href="" style="text-decoration:font-family:"Courier New", Courier, monospace;font-size:medium;color:#5e82a6;">DOB</a></td>
                                            <td  class="grid-columnHeading" width="100"><a href="" style="text-decoration:font-family:"Courier New", Courier, monospace;font-size:medium;color:#5e82a6;">Entry Date</a></td>
                                            <td  class="grid-columnHeading" width="100"><a href="" style="text-decoration:font-family:"Courier New", Courier, monospace;font-size:medium;color:#5e82a6;">Rotate</a></td>
                                            <!--<td  class="grid-columnHeading" width="100"></td>-->
                                       </tr>
                                       <%
                                       
                                       //Iterable<Entity> entities = (Iterable<Entity>)session.getAttribute("results");
                                       ArrayList entities = (ArrayList)session.getAttribute("results");
                                       Map<String, Object> properties = null;
                                       
                                        if (entities!=null) 
                                        for (int i=0;i<entities.size();i++) {
                                        	
                                        	Entity result = (Entity)entities.get(i);
                                        
                                        	if (result!=null) {
					                                          properties = result.getProperties();
					                                      %>
					                                      <% 
					                                      if (row==cursor) { %>
					                                         	<%
					                                         	String  tdClass="";
					                                      		String rotate="Yes";
					                                      		/*
					                                      		* Do date check here.
					                                      		*
					                                      		*/
					                                      		String entryDate=(String)properties.get("entryDate");
					                                      		boolean holdBack=html.isCurrentDateBeforeEntryDatePlus3Weeks(entryDate);
					                                      		
					                                      		if (holdBack) {
					                                      				tdClass="-flagged";
					                                      				rotate="<3 weeks";
					                                      		}
					                                      		
					                                      		%>
					                                   
					                                          <tr>
					                                            <td  class="grid-columnHeading" width="40"><input type="checkbox" id="student" name="id_<%=result.getKey().getId()%>" value="Y"/></td>
                                           						<td class="grid-Id<%=tdClass%>"><%=properties.get("currentCourse")%></td>
					                                            <td class="grid-results<%=tdClass%>"><%=properties.get("lastName")%></td>
					                                            <td class="grid-results<%=tdClass%>"><%=properties.get("firstName")%></td>
					                                            <td class="grid-results<%=tdClass%>"><%=properties.get("dateOfBirth")%></td>
					                                            <td class="grid-results<%=tdClass%>"><%=html.formatDate((String)properties.get("entryDate"))%></td>
					                                            <td class="grid-results<%=tdClass%>"><%=rotate%></td>
					                                          </tr>
					                                          
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
					                                                    <td class="results-bg" colspan="7">no data found</td>
					                                              </tr>
					                                     
                                        <% } %>
                                    
                                     </table> 
                                     	
                                    
                                   </td>
                                 </tr>
                                  <tr>
                                     		<td colspan="7" height="60" valign="center" align="center">
                                     			<input type="submit" name="action" value="MasterRotate" class="buttonRotate">
                                     		</td>
                                    </tr>
                               </table>
                               
				  	</div>
				  	 </form>
				  	 
				  	<br>
				  	
				  	<table width="620" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
				          <tr>
							    <td class="results-page-link">
							    
							    <% if (size/pageLimit!=0) { %>
							    	<%	if ( (cursor/pageLimit)>1 ) { %>
							    			<b><a href="<%=request.getContextPath()%>/jsp/course/rotate?action=Search&cursor=<%=cursor-(pageLimit*2)%>&size=<%=size%>"><< Previous</a></b>
							    	<% } %>
							    	<i>&nbsp;&nbsp;(pages <%=cursor/pageLimit%> of <%=size/pageLimit%>)</i>&nbsp;&nbsp;
							    	<%	if ((cursor/pageLimit)<(size/pageLimit)) { %>
							    		<b><a href="<%=request.getContextPath()%>/jsp/course/rotate?action=Search&cursor=<%=cursor%>&size=<%=size%>">Next >></a></b>
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
		for (var i = 1; i < <%=row+5%>; i++) {
			//alert('>'+document.forms[0].elements['student_'+i].value);
			document.forms[0].elements[i].checked=true;
		}
	  checked=true;
	}
	else {
	 for (var i = 1; i <  <%=row+5%>; i++) {
			document.forms[0].elements[i].checked=false;
		}
	 	checked=false;	
	}
}	
	
</script>



<jsp:include page="../includes/footer.jsp"/>


