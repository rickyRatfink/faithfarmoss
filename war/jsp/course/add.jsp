<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ page import="org.faithfarm.dataobjects.Person" %>
<%@ page import="org.w3c.dom.NodeList" %>
<%@ page import="org.w3c.dom.Element" %>
<%@ page import="java.util.ArrayList" %>
<jsp:include page="../includes/header.jsp"/>


<jsp:include page="../includes/menu/index.jsp" flush="true"/>

<%
	String required = "<font size='-2' color='red'>*</font>";
	Entity Course = (Entity)request.getAttribute("Course");
	NodeList xmlCourse=(NodeList)request.getAttribute("xmlCourse");
	String action=request.getParameter("action");
%>


		<!----Main Content--->
		<td  valign="top">
			<table width="820" border="0" cellpadding="0" cellspacing="0">
				<tr><td><br><!--img src="<%=request.getContextPath()%>/images/header_top.png"--></td></tr>
				<tr>
				<td class="content-body">
						<div class="content">
						<img src="<%=request.getContextPath()%>/images/titles/add-course.jpg">
						<br />
						<br />
						<jsp:include page="../includes/errors.jsp" flush="true"/>
						
						<!----Search Form ------->
						
						<form method="POST" action="<%=request.getContextPath()%>/course" >
							<table width="100%" border=0 cellpadding=0 cellspacing=0>
							
							<tr>
								<td >
                    <table width="700" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
							                <% for (int i=0;i<xmlCourse.getLength();i++) { 
												Element propertyElement = (Element)xmlCourse.item(i);%>
							                <tr>
							                 <td class="<% if (i%2==0) { %>edit-field-dark<%}else{%>edit-field-white<%}%>"><%=propertyElement.getAttribute("display")%></td>
                                             <td class="<% if (i%2==0) { %>edit-value-dark<%}else{%>edit-value-white<%}%>">
											 <%
											 String name=propertyElement.getAttribute("name").toString();
											 String type=propertyElement.getAttribute("type").toString();
											 String edit=propertyElement.getAttribute("editable").toString();
											 String maxlength=propertyElement.getAttribute("maxlength").toString();
											 String input=propertyElement.getAttribute("input").toString();
											 String source=propertyElement.getAttribute("source").toString();
											  String sequence=propertyElement.getAttribute("sequence").toString();
										 if ("Y".equals(edit)) {
												 
											if ("text".equals(input)) {  %>
                                                 	<input name="<%=name%>" value="<%=Course.getProperty(propertyElement.getAttribute("name").toString())%>" maxlength="<%=maxlength%>" size="<%=maxlength%>" class="entry"/>
                                            <% }
											else if ("textarea".equals(input)) {  %>
                                                 	<textarea name="<%=name%>" class="entry"><%=Course.getProperty(propertyElement.getAttribute("name").toString())%></textarea>
                                            <% 
											} 
											else if ("ddl".equals(input)) {  
											
											ArrayList ddl = (ArrayList)session.getAttribute(source);
											
											 %>
                                             		<select name="<%=name%>" class="entry">
                                                    	<option value=""></option>
                                                        <% if (ddl!=null) {
                                                        	for (int j=0;j<ddl.size();j++) { %>
                                                              <option value="<%=ddl.get(j)%>" <% if (ddl.get(j).equals(request.getParameter(name))) {%>selected<%}%>><%=ddl.get(j)%></option>
                                                           <% }
														    } %>
                                                    </select>                                             
											 <%
											 } 
											 
											 else {  %>
												<%=Course.getProperty(name)%>
                                             <% 
											 }  
											 %>
                                             
                                             
                                             <% } else { %>
                                             <%=Course.getProperty(name)%>
                                             <% } %>
                                             </td>
											 </tr>
							                 <%  
											 } %>
                                 </table>
							
								</td>
    
							</tr>
                            
                            
							
							<tr>
								<td  height="60" valign="bottom" align="left">
									<% if ("edit".equals(action)) { %>
									<input type="hidden" name="id" value="<%=Course.getKey().getId()%>"/>
									<input type="submit" name="action" value="Save Changes" class="buttonSave">
								<% } else { %>
									<input type="submit" name="action" value="Save" class="buttonSave">
               	<% } %>               
               </td>
							</tr>
							
							</table>
						</form>			
						
						<!----End Form----------->
						</div>
									
			
		
				<tr><td><!--img src="<%=request.getContextPath()%>/images/header_btm.png"--></td></tr>
			</table>
		</td>
	</tr>	
	

 

  	
  	</div>
</table>

<jsp:include page="../includes/footer.jsp"/>


