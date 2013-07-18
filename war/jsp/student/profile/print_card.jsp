<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
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
<%@ page import="org.apache.commons.codec.binary.Base64" %>

<%@ page import="javax.crypto.spec.SecretKeySpec" %>

<%@ page import="javax.crypto.Cipher" %>

<%@ page import="org.w3c.dom.NodeList" %>
<%@ page import="org.w3c.dom.Element" %>
<%@ page import="java.util.ArrayList" %>

<%
	Entity Person = (Entity)session.getAttribute("Person");
	Entity Address = (Entity)session.getAttribute("Address");
	Entity PersonMisc = (Entity)session.getAttribute("PersonMisc");
	Entity JobSkill = (Entity)session.getAttribute("JobSkill");
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
					<table width="700" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td valign="top" class="cardheading">
	                    	<b><%=((String)Person.getProperty("lastName")).toUpperCase()%>,&nbsp;<%=((String)Person.getProperty("firstName")).toUpperCase()%>&nbsp;<%=Person.getProperty("middleInitial")%>&nbsp;<%=Person.getProperty("suffix")%></b><br />
	            </td>
	            <td valign="top" class="cardheading">
	            <% //Format SSN with dashes
	               
	               String ssn="";
	               
	               try {
	            	   //this is why we don't name variables the same name as a class.... PS
	               ssn = org.faithfarm.dataobjects.Person.decryptSsn(Person.getProperty("ssn").toString());
	               //ssn= "***-**-"+ssn.substring(5,9);
		 		   } catch (Exception e) { ssn="N/A"; } 
	            
	            %>
	                      <b><%=ssn%></b>
	            <td valign="top" class="cardheading">
	                      <b><%=Person.getProperty("dateOfBirth")%></b>
	            </td>
	            <td valign="top" class="cardheading">
	            <%
	            String sDate = "";
	            try {
		       		sDate = Person.getProperty("entryDate").toString();
					java.util.Date nDate = new java.util.Date(sDate);
		        	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy"); 
		        	sDate = dateFormat.format(nDate);
		        } catch (Exception e) { sDate=""; }
			            
	            %>
	                      <b><%=sDate%></b>
	            </td>
	          </tr>
	          <tr>
							<td valign="top" class="cardheading">
	                    	Name(Last,First,MI)
	            </td>
	            <td valign="top" class="cardheading">
	                      Social Security Number
	            <td valign="top" class="cardheading">
	                      Birthdate
	            </td>
	            <td valign="top" class="cardheading">
	                      Entry Date
	            </td>
	          </tr>
	       </table>
         
         <table width="700" border="0" cellpadding="0" cellspacing="0">
					<tr>
              <td valign="top" align="left">
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
                 <td valign="top">
          			<br/>
				 					<table width="500" border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td width="10"></td>
											<td valign="top" class="cardheading">
					                    	<b><%=Person.getProperty("age")%>/<%=Person.getProperty("height")%>/<%=Person.getProperty("weight")%>
					            </td>
					            <td valign="top" class="cardheading">
					            		<% if ("Yes".equals(PersonMisc.getProperty("driversLicenseStatus"))) { %>
					                      <b><%=PersonMisc.getProperty("driversLicenseNumber")%></b>
					                <% } else { %>
					                			<b>None</b>
					                <% } %>
					            <td valign="top" class="cardheading">
					                      <b><%=Person.getProperty("maritalStatus")%></b>
					            </td>
					          </tr>
					          <tr>
					          	<td></td>
											<td valign="top" class="cardheading">
					                    	Description(Age/Weight/Height)
					            </td>
					            <td valign="top" class="cardheading">
					                      Driver's License
					            <td valign="top" class="cardheading">
					                      Marital Status
					            </td>
					          </tr>
					       </table> 
					      <br/>
				 					<table width="500" border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td width="10"></td>
											<td valign="top" class="cardheading">
					                    	<b><%=Person.getProperty("eyeColor")%>/<%=Person.getProperty("hairColor")%>
					            </td>
					            <td valign="top" class="cardheading">
					                      <b><%=Person.getProperty("ethnicity")%></b>
					            </td>
					          </tr>
					          <tr>
					          	<td></td>
											<td valign="top" class="cardheading">
					                    	Eye Color/Hair Color
					            </td>
					            <td valign="top" class="cardheading">
					                      Race/Ethnicity
					            </td>
					          </tr>
					       </table>     
					       <br/>
				 					<table width="500" border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td width="10"></td>
											<td valign="top" class="cardheading">
					                    	<b>
					                    	<%=Address.getProperty("addressLine1")%><br/>
					                    	<%=Address.getProperty("city")%>,<%=Address.getProperty("state")%>
					                    	&nbsp;<%=Address.getProperty("zipcode")%>
					                    	<br/>
					                    		
					            </td>
					            <td valign="top" class="cardheading">
					                    	<b>
					                    	<%=Person.getProperty("emergencyContact")%>&nbsp;(<%=Person.getProperty("emergencyRelationship")%>)<br/>
					                    	<%=Person.getProperty("emergencyPhone")%>
					                    	<br/>
					                    		
					            </td>
					            <td valign="top" class="cardheading">
					                    	<b>
					                    	<% if ("Yes".equals((String)PersonMisc.getProperty("probationFlag"))) { %>
					                    	<%=PersonMisc.getProperty("probationCounty")%>,<%=PersonMisc.getProperty("probationState")%><br/>
					                    	<%=PersonMisc.getProperty("probationOffice")%>,<%=PersonMisc.getProperty("probationOfficerPhone")%>
					                    <% } else { %>
					                    	Not On Probation
					                    <% } %>
					                      
					                    		
					            </td>
					          </tr>
					          <tr>
					          	<td></td>
											<td valign="top" class="cardheading">
					                    	Address
					            </td>
					            <td valign="top" class="cardheading">
					                    	Emergency Contact Information
					            </td>
					             <td valign="top" class="cardheading">
					                    	Probation Contact Information
					            </td>
					          </tr>
					       </table> 
					       <br/>
				 					<table width="500" border="0" cellpadding="0" cellspacing="0">
										<tr>
										
											<td valign="top" class="cardheading">
					                    	<b>
					                    	<%=PersonMisc.getProperty("currentHealth")%>
					                    	</b>
					                    		
					            </td>
					            <td valign="top" class="cardheading">
					                    	<b>
					                    	<%=PersonMisc.getProperty("currentMedicationsFlag")%>
					                    	</b>
					                    		
					            </td>
					            <td valign="top" class="cardheading">
					                    	<b>
					                    	<%=PersonMisc.getProperty("disabilityFlag")%>
					                    	</b>
					            </td>
					          </tr>
					          <tr>
					           <td valign="top" class="cardheading">
					                    	Health Condition
					            </td>
					            <td valign="top" class="cardheading">
					                    	Medications
					            </td>
					             <td valign="top" class="cardheading">
					                    	Disabled
					            </td>
					          </tr>
					       </table>  
					              			
          		</td>
          </tr>
         </table>
         <br/>
         <table width="700" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td valign="top" class="cardheading">
	                    	<b>None</b><br />
	            </td>
	             <td valign="top" class="cardheading">
	                      <b><%=PersonMisc.getProperty("religion")%></b>
	            </td>
	           	<td valign="top" class="cardheading">
	                    	  <b><%=PersonMisc.getProperty("governmentBenefits")%></b>
	            </td>
											
	          </tr>
	          <tr>
							<td valign="top" class="cardheading">
	                    	Student History (Date,Name,Location,Status)
	            </td>
	            <td valign="top" class="cardheading">
	                      Religious Background
	            </td>
	            <td valign="top" class="cardheading">
	                      Government Benefits
	            </td>
	          </tr>
	       </table>
</div>
<body>
</body>
</html>

