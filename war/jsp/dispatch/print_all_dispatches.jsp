<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ page import="org.faithfarm.datawriters.ApplicationDao" %>
<%@ page import="org.w3c.dom.NodeList" %>
<%@ page import="org.w3c.dom.Element" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.TimeZone" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Faith Farm Ministries - Operational Support System</title>
  <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/styles/dispatch.css"  />
</head>

<script language="javascript">

	
	
	function printPage()
	{
	window.print();
	}
</script>

<STYLE TYPE="text/css">
     body { line-height: 1.00 }
     
     @media print
{
  .page-break  { display:block; page-break-before:always; }
}

     td {}
     .receiptTitle {
     	font-family: sans-serif;
     	font-size: 14px;
     	font-weight: normal;
     	line-height: 1.00;
     }
         .receiptFormItems {
     	font-family: sans-serif;
     	font-size: 14px;
     	font-weight: normal;
     	line-height: 1.00;
     }
         .receiptTitle {
     	font-family: sans-serif;
     	font-size: 14px;
     	font-weight: normal;
     	line-height: 1.00;
     }    .receiptFormAddress {
     	font-family: sans-serif;
     	font-size: 14px;
     	font-weight: normal;
     	line-height: 1.00;
     }     .receiptFormHeading {
     	font-family: sans-serif;
     	font-size: 14px;
     	font-weight: normal;
     	line-height: 1.00;
     } .receiptFooter {
     	font-family: sans-serif;
     	font-size: 9px;
     	font-weight: normal;
     	line-height: 1.00;
</STYLE>

<body onLoad="javascript:printPage();" topmargin="0" >
<!-- body -->
<%
	String farm=(String)session.getAttribute("farmBase");
	String address = "1980 NW 9th Ave, Fort lauderdale, Florida";
	String phoneNum = "(954)763-7787&nbsp;/&nbsp;FAX (954)468-1416";
	if(farm.toLowerCase().equals("boynton beach")) {
		address = "9538 Hwy 441, Boynton Beach, Florida";
		phoneNum = "(561) 737-2222&nbsp;/&nbsp;FAX (561) 735-0227";
	} else if(farm.toLowerCase().equals("okeechobee")) {
		address = "7595 NE 128th Ave, Okeechobee, Florida";
		phoneNum = "(863) 763-4224&nbsp;/&nbsp;FAX (863) 763-1785";
	}

int copies=1;
	
	String required = "<font size='-2' color='red'>*</font>";
	ArrayList results = (ArrayList)session.getAttribute("results");
	NodeList xmlPerson=(NodeList)request.getAttribute("xmlPerson");
	NodeList xmlAddress=(NodeList)request.getAttribute("xmlAddress");
	NodeList xmlDispatch=(NodeList)request.getAttribute("xmlDispatch");
	
	if ("Boynton Beach".equals(farm))
		copies=1;
	
for (int a=0;a<results.size();a++) {
	Entity Dispatch = (Entity)results.get(a);
	
	Entity Person = ApplicationDao.getEntity(Dispatch.getParent());
	Entity Address =  ApplicationDao.getEntityByParent("Address",Person.getKey());
	SimpleDateFormat toFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
	toFormat.setTimeZone(TimeZone.getTimeZone("America/New_York"));
	
	
 for (int copy=0;copy<copies;copy++) { %>



<table  width="700" cellpadding="0" cellspacing="0" >
		<tr>				
				<td valign="top" align="left" class="receiptTitle">
					<img src="<%=request.getContextPath()%>/images/logo.png">
					<br/>
					<%if (copy==0) { %>
						<b>CUSTOMER COPY</b><br/>
					<% } else { %>
						<b>MERCHANT COPY</b><br/>
					<% } %>
					<br/>
				</td>
				<td valign="top" align="right" class="receiptTitle">
					<b>CUSTOMER ORDER</b><br/>
					CONFIRMATION #: <%=Dispatch.getProperty("confirmationNumber")%><br/>
					DISPATCH DATE: <%=Dispatch.getProperty("dispatchDate")%><br/>
					LOCATION: <%=Dispatch.getProperty("dispatchLocation")%><br/>
					<b><%=Dispatch.getProperty("callRequirements")%></b><br/>
					CALL AGENT:<%=Dispatch.getProperty("createdBy")%></br>
					TRUCK ASSIGNED:<%=Dispatch.getProperty("truck")%></br>
				</td>
		</tr>
		<tr>
			<td class="dispatchFormHeading" align="left"><%= address %></td>
			<td class="dispatchFormHeading" align="right"><%= phoneNum %></td>
		</tr>
  		<tr>
			<td colspan="2"><hr></td>
		</tr>
</table>
<table  width="700" cellpadding="0" cellspacing="0">
<tr>
	<td align="left" valign="top">	
		<table width="350"  cellpadding="0" cellspacing="0">
	 <tr>
			<td class="receiptFormAddress" align="left"><br/><b>CUSTOMER:</b></br><%= toFormat.format(Dispatch.getProperty("creationDate")) %></td>
		</tr>
	
		<tr>
			<td class="receiptFormAddress" align="left">
					<%=Person.getProperty("firstName")%>&nbsp;<%=Person.getProperty("middleInitial")%>&nbsp;<%=Person.getProperty("lastName")%><br/>
					<%=Address.getProperty("addressLine1")%><br/>
					<%=Address.getProperty("city")%>,&nbsp;<%=Address.getProperty("state")%>&nbsp;<%=Address.getProperty("zipcode")%>
					<br/><br/>
					<b>Home Phone:</b>&nbsp;<%=Address.getProperty("homePhone")%><br/>
					<b>Work Phone:</b>&nbsp;<%=Address.getProperty("workPhone")%><br/>
					<b>Cell Phone:</b>&nbsp;<%=Address.getProperty("cellPhone")%><br/>
					<b>Email:</b>&nbsp;<%=Address.getProperty("email")%><br/>
					
			</td>
		</tr>
	</table>
	
	</td>
	<td width="50%" class="receiptFormAddress" align="left" valign="top">
		<br/>
					<b>Major Intersection:</b><%=Dispatch.getProperty("majorIntersection")%><br/>
					<b>Street Suffix:</b><%=Dispatch.getProperty("streetSuffix")%><br/>
					<b>Subdivision:</b><%=Dispatch.getProperty("subdivision")%><br/>
					<br/>
					<b>Property Type:</b>&nbsp;<%=Dispatch.getProperty("structureType")%><br/>
					<b>Gated Community #:</b>&nbsp;<%=Dispatch.getProperty("gatedCommunityFlag")%><br/>
					<b>Gate Instructions:</b>&nbsp;<%=Dispatch.getProperty("gateInstructions")%><br/>
					<b>Gate Code:</b>&nbsp;<%=Dispatch.getProperty("gateCode")%><br/>
					<b>Building #:</b>&nbsp;<%=Dispatch.getProperty("buildingNumber")%><br/>
					<b>Unit #:</b>&nbsp;<%=Dispatch.getProperty("unitNumber")%><br/>
					<b>Elevator Access:</b>&nbsp;<%=Dispatch.getProperty("elevatorFlag")%><br/>
					<b>Floor #:</b>&nbsp;<%=Dispatch.getProperty("floorNumber")%><br/>
	</td>
	</tr>
	<tr>
		<td colspan="2" class="receiptFormItems">
		<br/>
			<table width="700" border="1" cellpadding="0" cellspacing="0">
			<tr>
				<td colspan="4" class="receiptFormHeading"><b>ITEM LISTING</b>, Location:<%=Dispatch.getProperty("itemLocation")%></td>
			</tr>
			   <% for (int i=18;i<xmlDispatch.getLength()-1;i+=2) { 
									Element propertyElement1 = (Element)xmlDispatch.item(i);
									String name1=propertyElement1.getAttribute("name").toString();
									String display1=propertyElement1.getAttribute("display").toString();
									
									Element propertyElement2 = (Element)xmlDispatch.item(i+1);
									String name2=propertyElement2.getAttribute("name").toString();
									String display2=propertyElement2.getAttribute("display").toString();
									
									String value1=(String)Dispatch.getProperty(name1);
									String value2=(String)Dispatch.getProperty(name2);
									if ("bedding".equals(name1)||"books".equals(name1)||"clothing".equals(name1)||"television".equals(name1)){
									 value1=value1+"          "+value2;
									 display2=""; value2="";
									}
									
									
				 %>
				<tr>
					<td class="receiptFormItems" width="50"><%=display1%></td>
					<td class="receiptFormItems" width="100"><%=value1%></td>
					<td class="receiptFormItems" width="50"><%=display2%></td>
					<td class="receiptFormItems" width="100"><%=value2%></td>
				</tr>
				<% } %>
			</table>
		</td>
	</tr>
	</table>
</td>
</tr>
</table>
<table  width="700" border="1" cellpadding="0" cellspacing="0">
<tr>
	<td height="1" bgcolor="#000000"></td>
</tr>
<tr>
	<td height="60" class="receiptFormItems" valign="top" align="left">
		<b>NOTES:</b><br/>
		<%=Dispatch.getProperty("notes")%>
	</td>
</tr>
<tr>
</table>
<table  width="700" border="0" cellpadding="0" cellspacing="0">
<tr>
	<td class="receiptFooter">
    	<input type="checkbox" name="acceptTermsPolicies" checked/>
        <i>I acknowledge that I have read Faith Farm's delivery/donation terms and policies to the customer.</i>
		<br/>
		<br/>
		<b>IMPORTANT PLEASE READ</b><br/>
		This slip will serve as your receipt in case you wish to use your contribution for purposes of<br/>
		Income Tax reduction. The Internal Revenue Department informs us that the contributor can estimate<br/>
		the fair market value of his/her contribution.  No goods or services were received in exchange for this<br/>
		donation.
		<br/><br/></br>
		<b>A WORK IN YOUR COMMUNITY BRINGING THE LIGHT OF CHRIST TO THOSE IN MORE NEED THAN OURSELVES</B>
	</td>	
</tr>
</table>

<div class="page-break"></div>
<% } 
}
 %>



</body>
</html>