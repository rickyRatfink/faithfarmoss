<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ page import="com.google.appengine.api.blobstore.BlobKey" %>
<%@ page import="com.google.appengine.api.images.ImagesService" %>
<%@ page import="com.google.appengine.api.images.ImagesServiceFactory" %>
<%@ page import="com.google.appengine.api.images.ServingUrlOptions" %>
<%@ page import="org.faithfarm.dataobjects.Person" %>
<%@ page import="org.w3c.dom.NodeList" %>
<%@ page import="org.w3c.dom.Element" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<jsp:include page="../includes/header.jsp"/>


<jsp:include page="../includes/menu/index.jsp" flush="true"/>

<%
	String required = "<font size='-2' color='red'>*</font>";
	Entity Person = (Entity)request.getAttribute("Person");
	Entity Address = (Entity)request.getAttribute("Address");
	Entity PersonMisc = (Entity)request.getAttribute("PersonMisc");
	Entity JobSkill = (Entity)request.getAttribute("JobSkill");
	NodeList xmlPerson=(NodeList)request.getAttribute("xmlPerson");
	NodeList xmlPersonMisc=(NodeList)request.getAttribute("xmlPersonMisc");
	NodeList xmlAddress=(NodeList)request.getAttribute("xmlAddress");
	NodeList xmlJobSkill=(NodeList)request.getAttribute("xmlJobSkill");
	SimpleDateFormat toFormat = new SimpleDateFormat("MM/dd/yy");
	SimpleDateFormat fromFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
	
	
%>
		<!----Main Content--->
		<td  valign="top">
			<table width="820" border="0" cellpadding="0" cellspacing="0">
				<tr><td><br><!--img src="<%=request.getContextPath()%>/images/header_top.png"--></td></tr>
				<tr>
				<td class="content-body">
						<div class="content">
							<table>
				    	 		<tr>
				    				<td class="text1">Edit Person</td>
				    			 </tr>
				   			 </table>
						<br />
						<br />
						<jsp:include page="../includes/errors.jsp" flush="true"/>
						
						<!----Search Form ------->
						
						<form method="POST" action="<%=request.getContextPath()%>/student" >
							<table width="100%" border=0 cellpadding=0 cellspacing=0>
							
							<tr>
								<td >
                               	 <img src="../../images/titles/personal-info.jpg"/>
                                 <br /><br />
								 <table width="500" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
							                <%
							                String imgUrl = "";
							                
							                try {
							                	BlobKey blobKey = (BlobKey)Person.getProperty("imageKey");
				                    			ImagesService imagesService = ImagesServiceFactory.getImagesService();
				                    			imgUrl = imagesService.getServingUrl(blobKey);
				                    			//imgUrl = imagesService.getServingUrl(ServingUrlOptions.Builder.withBlobKey(blobKey));
							                } catch(Exception e) {
							                	imgUrl = "/images/person_photos/blank.gif";
							                }
							                %>
							                <tr>
							                	<td colspan="2"><img src="<%= imgUrl  %>" width="180" /><div>&nbsp;</div></td>
							                </tr>
							                <%
							                
							                for (int i=0;i<xmlPerson.getLength();i++) { 
												Element propertyElement = (Element)xmlPerson.item(i);%>
												
											
							          	 <%
											 String name=propertyElement.getAttribute("name").toString();
											 String type=propertyElement.getAttribute("type").toString();
											 String edit=propertyElement.getAttribute("editable").toString();
											 String maxlength=propertyElement.getAttribute("maxlength").toString();
											 String input=propertyElement.getAttribute("input").toString();
											 String source=propertyElement.getAttribute("source").toString();
											 String value = "";
											 if (Person.getProperty(name)!=null)
											 	value=Person.getProperty(name).toString();
											 if(name.equals("exitDate") || name.equals("entryDate")) {
											 	 if(value != null && !value.equals("")) {
													 try {
													 Date temp = fromFormat.parse(value);
													 value = toFormat.format(temp);
													 } catch (Exception e) { value=value; }
												 }
											 }
										%>
							<%
							//Hide SSN, per Pastor Steffe all SSN numbers should not be fully displayed on any screen.
							if ("ssn".equals(propertyElement.getAttribute("name").toString())) { %>
								<input type="hidden" name="ssn" value="<%=value%>"/>
							<% }
							 if (!"ssn".equals(propertyElement.getAttribute("name").toString())) { %>
										     <tr>
							                 <td class="<% if (i%2==0) { %>edit-field-dark<%}else{%>edit-field-white<%}%>"><%=propertyElement.getAttribute("display")%></td>
                                             <td class="<% if (i%2==0) { %>edit-value-dark<%}else{%>edit-value-white<%}%>">
										
											 
										<%     //String value=Person.getProperty("age").toString();
										 if ("Y".equals(edit)) { 
											if ("text".equals(input)) {  %>
											     	<input name="<%=name%>" value="<%= value %>" maxlength="<%=maxlength%>" size="<%=maxlength%>" class="entry"/>
                                            <% 
											} 	else if ("calendar".equals(input)) { %>
					                		<input name="<%=propertyElement.getAttribute("name").toString() %>" class="tcal" value="<%=value %>" />
                      						<% 
											} 
											else if ("ddl".equals(input)) {  
													ArrayList ddl = (ArrayList)session.getAttribute(source);
											 %>
                                             		<select name="<%=name%>" class="entry">
                                                    	<option value=""></option>
                                                        <% if (ddl!=null) { 
                                                        	for (int j=0;j<ddl.size();j++) { String ddlValue = ddl.get(j).toString(); %>
                                                              <option value="<%=ddlValue%>" <% if (value.equals(ddlValue)) {%>selected<%}%>><%=ddlValue%></option>
                                                           <% } %>
                                                        <% } %>
                                                    </select>                                             
											 <% 
											 }  else if ("textarea".equals(input)) { %>											 
											                       		<textarea name="<%=name%>" class="entry"><%=value%></textarea>
                     						  <% }  
											 
											 else {  %>
												<%=value%>
                       <% 
											 }  
											 %>
                                                                                       
                                             
                                             
                                                <% } else { %>
                                             <%=value%>
                                             <% } %>
                                             </td>
											 </tr>
											 <% } //end ssn check %>
							                 <%  
											 } %>
                                 </table>
								 <br /><br />
								 
								 <img src="../../images/titles/address-information.jpg" />
                                 <br /><br />
                                 <table width="500" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
							                <% for (int i=0;i<xmlAddress.getLength();i++) { 
							                	Element propertyElement = (Element)xmlAddress.item(i);%>
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
											 
										 if ("Y".equals(edit)) {
												 
											if ("text".equals(input)) { %>
                                                 	<input name="<%=name%>" value="<%=Address.getProperty(propertyElement.getAttribute("name").toString())%>" maxlength="<%=maxlength%>" size="<%=maxlength%>" class="entry"/>
                                            <% 
											} 
											else if ("ddl".equals(input)) { 
											 		ArrayList ddl = (ArrayList)session.getAttribute(source);
											 %>
                                             		<select name="<%=name%>" class="entry">
                                                    	<option value=""></option>
                                                        <% if (ddl!=null) {
                                                        	for (int j=0;j<ddl.size();j++) {%>
                                                              <option value="<%=ddl.get(j)%>" <% if (ddl.get(j).equals(Address.getProperty(propertyElement.getAttribute("name").toString()))) {%>selected<%}%>><%=ddl.get(j)%></option>
                                                           <% } %>
                                                        <% } %>
                                                    </select>                                             
											 <%
											 } 
											 
											 else { %>
												<%=Address.getProperty(propertyElement.getAttribute("name").toString())%>
                                             <% 
											 }  
											 %>
                                             
                                             
                                             <% } else { %>
                                             <%=Address.getProperty(propertyElement.getAttribute("name").toString())%>
                                             <% } %>
                                             </td>
											 </tr>
							                 <%  
											 } %>
                                 </table>
								 <br /><br />
								 <img src="../../images/titles/skills-title.jpg" />
                                 <br /><br />
								 <table width="500" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
							                <% for (int i=0;i<xmlJobSkill.getLength();i++) { 
												Element propertyElement = (Element)xmlJobSkill.item(i);%>
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
											
										 if ("Y".equals(edit)) {
												 
											if ("text".equals(input)) { %>
                                                 	<input name="<%=name%>" value="<%=JobSkill.getProperty(propertyElement.getAttribute("name").toString())%>" maxlength="<%=maxlength%>" size="<%=maxlength%>" class="entry"/>
                                            <% 
											} 
											else if ("ddl".equals(input)) { 
											 		ArrayList ddl = (ArrayList)session.getAttribute(source);
											 %>
                                             		<select name="<%=name%>" class="entry">
                                                    	<option value=""></option>
                                                        <% if (ddl!=null) {
                                                        	for (int j=0;j<ddl.size();j++) {%>
                                                              <option value="<%=ddl.get(j)%>" <% if (ddl.get(j).equals(JobSkill.getProperty(propertyElement.getAttribute("name").toString()))) {%>selected<%}%>><%=ddl.get(j)%></option>
                                                           <% } %>
                                                        <% } %>
                                                    </select>                                             
											 <%
											 } 
											 
											 else { %>
												<%=JobSkill.getProperty(propertyElement.getAttribute("name").toString())%>
                                             <% 
											 }  
											 %>
                                             
                                             
                                             <% } else { %>
                                             <%=JobSkill.getProperty(propertyElement.getAttribute("name").toString())%>
                                             <% } %>
                                             </td>
											 </tr>
							                 <%  
											 } %>
                                 </table>

								 <br /><br />
								 <img src="../../images/titles/medical-info.jpg" />
                                 <br /><br />
								 <table width="500" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
							                <% for (int i=0;i<xmlPersonMisc.getLength();i++) { 
												Element propertyElement = (Element)xmlPersonMisc.item(i);%>
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
											
										 if ("Y".equals(edit)) {
												 
											if ("text".equals(input)) {  %>
                                                 	<input name="<%=name%>" value="<%=PersonMisc.getProperty(propertyElement.getAttribute("name").toString())%>" maxlength="<%=maxlength%>" size="50" class="entry"/>
                                            <% 
											} 
											else if ("ddl".equals(input)) {  
											 		ArrayList ddl = (ArrayList)session.getAttribute(source);
											 %>
                                             		<select name="<%=name%>" class="entry">
                                                    	<option value=""></option>
                                                        <% if (ddl!=null) {
                                                        	for (int j=0;j<ddl.size();j++) {%>
                                                              <option value="<%=ddl.get(j)%>" <% if (ddl.get(j).equals(PersonMisc.getProperty(propertyElement.getAttribute("name").toString()))) {%>selected<%}%>><%=ddl.get(j)%></option>
                                                           <% } %>
                                                        <% } %>
                                                    </select>                                             
											 <%
											 } 
											 else if ("textarea".equals(input)) { %>
											 	<textarea name="<%=propertyElement.getAttribute("name").toString()%>" class="review"><%=PersonMisc.getProperty(propertyElement.getAttribute("name").toString())%></textarea>
											 <% } 
											 else { %>
												<%=PersonMisc.getProperty(propertyElement.getAttribute("name").toString())%>
                                             <% 
											 }  
											 %>
                                             
                                             
                                             <% } else { %>
                                             <%=PersonMisc.getProperty(propertyElement.getAttribute("name").toString())%>
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
									<input type="submit" name="action" value="Save Changes" class="buttonBlankLarge">
                                    <input type="hidden" name="id" value="<%=Person.getKey().getId()%>" />
                                    <input type="hidden" name="personKey" value="<%=KeyFactory.keyToString(Person.getKey())%>" />
                                      
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


