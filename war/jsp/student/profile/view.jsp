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
<%@ page import="com.google.appengine.api.blobstore.BlobKey" %>
<%@ page import="com.google.appengine.api.images.ImagesService" %>
<%@ page import="com.google.appengine.api.images.ImagesServiceFactory" %>

<%@ page import="org.w3c.dom.NodeList" %>
<%@ page import="org.w3c.dom.Element" %>
<%@ page import="java.util.ArrayList" %>
<jsp:include page="../../includes/header.jsp"/>


<jsp:include page="../../includes/menu/index.jsp" flush="true"/>

<%
	Entity Person = (Entity)session.getAttribute("Person");
	Entity Address = (Entity)session.getAttribute("Address");
	Entity PersonMisc = (Entity)session.getAttribute("PersonMisc");
	Entity JobSkill = (Entity)session.getAttribute("JobSkill");
	NodeList xmlPerson=(NodeList)session.getAttribute("xmlPerson");
	NodeList xmlPersonMisc=(NodeList)session.getAttribute("xmlPersonMisc");
	NodeList xmlAddress=(NodeList)session.getAttribute("xmlAddress");
	NodeList xmlJobSkill=(NodeList)session.getAttribute("xmlJobSkill");


%>
		<!----Main Content--->
        <form method="POST" action="<%=request.getContextPath()%>/student">
		<td valign="top">
			<table width="750" border="0" cellpadding="0" cellspacing="0">
				<tr><td><br><!--img src="<%=request.getContextPath()%>/images/header_top.png"--></td></tr>
				<tr>
				<td class="content-body">
					<table width="750" border="0" cellpadding="0" cellspacing="0">
					<tr><td valign="bottom"><img src="<%=request.getContextPath()%>/images/titles/application-title.jpg"></td></tr>
					<tr><td valign="top" class="subheading">
                    	<b><%=((String)Person.getProperty("firstName")).toUpperCase()%>&nbsp;<%=((String)Person.getProperty("middleInitial")).toUpperCase()%>&nbsp;<%=((String)Person.getProperty("lastName")).toUpperCase()%>&nbsp;<%=Person.getProperty("suffix")%></b><br />
                        <i>DOB:&nbsp;<%=Person.getProperty("dateOfBirth")%></i><br />
                        <%
                        String ssn=org.faithfarm.dataobjects.Person.decryptSsn(Person.getProperty("ssn").toString());
                        if (ssn.length()==0)
                        	ssn="N/A";
                        else
	               			ssn= "***-**-"+ssn.substring(5,9);
                        %>
                        <i>SSN:&nbsp;<%=ssn%></i><br />
                        </td></tr>
                    </table>
                    <br />
                    <table width="750" border="0" cellpadding="0" cellspacing="0">
					<tr>
                    	<td valign="top" align="center">
                    			<% 
                    			String imageUrl = "";
                    			try {
                    			BlobKey blobKey = (BlobKey)Person.getProperty("imageKey");
                    			ImagesService imagesService = ImagesServiceFactory.getImagesService();
                    			imageUrl = imagesService.getServingUrl(blobKey);
                    			} catch (Exception e) {
                    			imageUrl="/images/person_photos/blank.gif";
                    			}
                    			%>
                    			<img src="<%=imageUrl%>" border="1" width="180" height="180"/>
                    	</td>
                        <td>&nbsp;</td>
                    	<td valign="top">
                        	<table  border="0" cellpadding="0" cellspacing="0">
                            <tr>
                            	<td colspan="2"><img src="../../../images/titles/address-information.jpg" /></td>
                            </tr>
                             <% for (int i=0;i<xmlAddress.getLength();i++) { 
											Element propertyElement = (Element)xmlAddress.item(i);
											%>
							                <tr>
							                 <td class="<% if (i%2==0) { %>profile-field-dark<%}else{%>profile-field-white<%}%>"><%=propertyElement.getAttribute("display")%></td>
                                             <td class="<% if (i%2==0) { %>profile-value-dark<%}else{%>profile-value-white<%}%>"><%=Address.getProperty(propertyElement.getAttribute("name").toString())%></td>
											</tr>
                                           
                                   <% } %>
                            </table>
                        </td>
                    </tr>
                    </table>
                    <br />
                    <table width="750" border="0" cellpadding="0" cellspacing="0">
					<tr>
                    	<td valign="top">
                        	<table width="740" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                            	<td colspan="2"><img src="../../../images/titles/personal-info.jpg" /></td>
                            </tr>
                             <% for (int i=0;i<xmlPerson.getLength();i++) { 
											Element propertyElement = (Element)xmlPerson.item(i);
											%>
							                <tr>
							                 <td class="<% if (i%2==0) { %>profile-field-dark<%}else{%>profile-field-white<%}%>"><%=propertyElement.getAttribute("display")%></td>
                                             <td class="<% if (i%2==0) { %>profile-value-dark<%}else{%>profile-value-white<%}%>">
                                             <% if ("ssn".equals(propertyElement.getAttribute("name").toString())) { %>
                                             	<%=ssn%>
                                             <% } else { %>
                                                <%=Person.getProperty(propertyElement.getAttribute("name").toString())%></td>
                                             <% } %>
											</tr>
                                           
                              <% } %>
                            </table>
                        </td>
                       
                    </tr>
                    </table>
                    
                    <br />
                    <table width="750" border="0" cellpadding="0" cellspacing="0">
					<tr>
                    	<td valign="top">
                        	<table width="740" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                            	<td colspan="2"><img src="../../../images/titles/other-info.jpg" /></td>
                            </tr>
                             <% for (int i=0;i<xmlPersonMisc.getLength();i++) { 
											Element propertyElement = (Element)xmlPersonMisc.item(i);
											%>
							                <tr>
							                 <td class="<% if (i%2==0) { %>profile-field-dark<%}else{%>profile-field-white<%}%>"><%=propertyElement.getAttribute("display")%></td>
                                             <td class="<% if (i%2==0) { %>profile-value-dark<%}else{%>profile-value-white<%}%>"><%=PersonMisc.getProperty(propertyElement.getAttribute("name").toString())%></td>
											</tr>
                                           
                                   <% } %>
                            </table>
                        </td>
                       
                    </tr>
                    </table>
                    
                    <br />
                    <table width="750" border="0" cellpadding="0" cellspacing="0">
					<tr>
                    	<td valign="top">
                        	<table width="740" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                            	<td colspan="2"><img src="../../../images/titles/skills-title.jpg" /></td>
                            </tr>
                             <% for (int i=0;i<xmlJobSkill.getLength();i++) { 
											Element propertyElement = (Element)xmlJobSkill.item(i);
											%>
							                <tr>
							                 <td class="<% if (i%2==0) { %>profile-field-dark<%}else{%>profile-field-white<%}%>"><%=propertyElement.getAttribute("display")%></td>
                                             <td class="<% if (i%2==0) { %>profile-value-dark<%}else{%>profile-value-white<%}%>"><%=JobSkill.getProperty(propertyElement.getAttribute("name").toString())%></td>
											</tr>
                                           
                                   <% } %>
                            </table>
                        </td>
                       
                    </tr>

                    </table>
                    
                    <table width="750" border="0" cellpadding="0" cellspacing="0">
                    <tr>
								<td height="60" valign="bottom" align="center">
									<input type="submit" name="action" value="Print" class="buttonPrint">
									<input type="hidden" name="id" value="<%=Person.getKey().getId()%>" />
                </td>
							</tr>
                    </table>
 
                </td>
                <tr><td><!--img src="<%=request.getContextPath()%>/images/header_btm.png"--></td></tr>
			</table>
		</td>
		</form>
	</tr>
    </div>
    
</table>


<jsp:include page="../../includes/footer.jsp"/>

