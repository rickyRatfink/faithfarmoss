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

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/styles/profile.css"  />
<title>Print Student/Intake Profile</title>

  	<script language="javascript">
	function printPage()
	{
	window.print();
	}
	
</script>
</head>
<body background="#FFFFFF" onLoad="javascript:print();">

<div align="left">
					<table width="750" border="0" cellpadding="0" cellspacing="0">
					<tr><td valign="bottom"><img src="<%=request.getContextPath()%>/images/titles/application-title.jpg"></td></tr>
					<tr><td valign="top" class="subheading">
                    	<b><%=Person.getProperty("firstName")%>&nbsp;<%=Person.getProperty("middleInitial")%>&nbsp;<%=Person.getProperty("lastName")%>,&nbsp;<%=Person.getProperty("suffix")%></b><br />
                        <i>DOB:&nbsp;<%=Person.getProperty("dateOfBirth")%></i><br />
                        <i>SSN:&nbsp;<%=Person.getProperty("ssn")%></i><br />
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
                        	<table width="750" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                            	<td colspan="2"><img src="../../../images/titles/personal-info.jpg" /></td>
                            </tr>
                             <% for (int i=0;i<xmlPerson.getLength();i++) { 
											Element propertyElement = (Element)xmlPerson.item(i);
											%>
							                <tr>
							                 <td class="<% if (i%2==0) { %>profile-field-dark<%}else{%>profile-field-white<%}%>"><%=propertyElement.getAttribute("display")%></td>
                                             <td class="<% if (i%2==0) { %>profile-value-dark<%}else{%>profile-value-white<%}%>"><%=Person.getProperty(propertyElement.getAttribute("name").toString())%></td>
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
					<tr><td height="520"></td></tr>
					<tr>
												
                        <td>
                        	<img src="../../../images/intake_documents/propertyList.jpg" />
                        </td>
  					</tr>
  					<tr><td height="150"></td></tr>
            <tr>
                        <td>
                        	<img src="../../../images/intake_documents/release01.jpg" />
                        </td>
            </tr>
            <tr><td height="100"></td></tr>
            <tr>
            						<td>
                        	<img src="../../../images/intake_documents/release02.jpg" />
                        </td>
  					</tr>
  					<tr><td height="100"></td></tr>
            <tr>
                        <td>
                        	<img src="../../../images/intake_documents/cellDisclosure.jpg" />
                        </td>
  					</tr>
  					<tr><td height="700"></td></tr>
            <tr>
                        <td>
                        	<img src="../../../images/intake_documents/studentAgreement.jpg" />
                        </td>
  					</tr>
           
            </table>
    </div>
<body>
</body>
</html>

