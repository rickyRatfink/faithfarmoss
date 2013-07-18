<%@ page import="java.util.*" %>
<%@ page import="java.util.Map" %>
<%@ page import="org.faithfarm.dataobjects.PhoneOperatorReport" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreService" %>
<%@ page import="com.google.appengine.api.datastore.Query" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>

 <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Faith Farm Ministries - Daily Sales Report</title>
<link href="<%=request.getContextPath()%>/styles/report.css" rel="stylesheet" type="text/css" />
  
  <script language="javascript" src='script/jquery-1.6.min.js'></script>
  <script language="javascript" src='script/ajax.util.js'></script>

  	<script language="javascript">
	function printPage()
	{
	window.print();
	}
	
</script>

<style>

td { 
-webkit-print-color-adjust: exact;
}
.tableHead {
	font-family: arial;
	font-size: 9px;
	vertical-align:bottom;
	text-align:center;
	
	
}
.fieldData {
	font-family: arial;
	font-size: 12px;
	text-align:right;
	
}
.fieldDataTotal {
	font-family: arial;
	font-size: 12px;
	text-align:right;
	background: #e4e4e4;
	
}
.report-heading {
	 background: #bfbdbd;
	 border-color: #798c95;
     border-style: solid;
     border-width: 1px;
     font-size: 13px;
	 font-family: courier;
	 font-weight: bold;
	 color: #000000;	 
	 height: 15px;
	 padding: 0px 0px 0px 3px;
	 text-align:left;	
}
.report-field {
	 font-size: 13px;
	 font-family: courier;
	 font-weight: normal;
	 color: #000000;	 
	 padding: 0px 0px 0px 3px;
	 text-align:left;	
}
.report-money {
	 font-size: 13px;
	 font-family: courier;
	 font-weight: normal;
	 color: #000000;	 
	 padding: 0px 0px 0px 3px;
	 text-align:right;	
}

</style>

</head>
<body>
</style>
  <!-- content -->  
  
 <!-- home page content -->
 <!-- list container -->
 <%
 int totalCalls=0;
 ArrayList closeouts = (ArrayList)request.getAttribute("report2");
 String farm=(String)session.getAttribute("farmBase");
 String addr1="";
 String addr2="";
 String person1="";
 String person2="";
 
 if ("Fort Lauderdale".equals(farm)) {
	 person1="Anthony Ellis";
	 person2="Shawn Haworth";
 } else if ("Boynton Beach".equals(farm)) {
	 person1="James Rodgers";
	 person2="Patrick Bourchier";
 	 
 } else {
	 person1="";
	 person2=""; 	 
 } 
 %>
 <div align="center">
 
	   	  <table width="90%" cellspacing="0" cellpadding="0" border="0">
          <tr>
          	<td align="left" width="200" valign="top">&nbsp;&nbsp;<img src="<%=request.getContextPath()%>/images/logo.png"  /></td>
          	<td align="left" valign="top">
          		<div style="font-size:14px"><b>FAITH FARM MINISTRIES</b></br>
          		<b>Donation Pick-Up Status Sheet</b>
          		</div>
          		<div style="font-size:10px">Run on:&nbsp;&nbsp;<i><%=new java.util.Date()%></i></div>
          	</td>
          	<td align="right" valign="top">
          		<div style="font-size:12px">Location:&nbsp;<u><%=(String)session.getAttribute("farmBase") %></u></div>
          	</td>
          </tr>
          </table>
          
           <table width="90%" cellspacing="0" cellpadding="0" >
          <tr>
          		<td align="left" colspan="2" width="80"><div style="font-size:12px"><b>Beginning Date:&nbsp;<u><%=request.getAttribute("startDate")%></u></div></b></td>
          		<td align="right" colspan="2"><div style="font-size:12px"><b>Dispatcher:&nbsp;</b><u><%=person1 %></u></div></td>
          </tr>
          <tr>
          		<td align="left" colspan="2" width="80"><div style="font-size:12px"><b>Ending Date:&nbsp;<u><%=request.getAttribute("endDate")%></u></div></b></td>
          		<td align="right" colspan="2"><div style="font-size:12px"><b>Manager:&nbsp;</b><u><%=person2 %></u></div></td>
          </tr>
          </table>
         
         <table width="90%" cellspacing="0" cellpadding="0" border="1" style="border-style: double; ">
         	<tr>
         		<!-- PHONE OPERATORS REPORT -->
         		<td width="60%">
         		         <table width="100%" cellspacing="0" cellpadding="0" border="1">
         		         	<tr>
         		         		<td colspan="11" align="center">
         		         			<div style="font-size:12px"><b>PHONE OPERATORS' REPORT</b></div>
         		         		</td>
         		         	</tr>
         		         	<tr>
         		         		<td width="80" height="50" class="tableHead">Day of Week</div></td>
         		         		<td width="50" class="tableHead"># New Tickets Written</div></td>
         		         		<td width="15" class="tableHead"></td>
         		         		<td width="50" class="tableHead">Phone Rejects</td>
         		         		<td width="15" class="tableHead"></td>
         		         		<td width="50" bgcolor="#e4e4e4" class="tableHead">Donation Inquiries</td>
         		         		<td width="50" class="tableHead">Tickets Cancelled</td>
         		         		<td width="50" class="tableHead">Tickets RS</td>
         		         		<!-- <td width="50" bgcolor="#e4e4e4" class="tableHead">Total Calls for new Tickets</td> -->
         		         		<td width="50" class="tableHead"># of Phone Operators (Max 7)</td>
         		         		<td width="80" class="tableHead">Previously Written Tickets & Other Inquiries</td>
         		         	</tr>
         		         	<% 
         		         	ArrayList report1 = new ArrayList();
         		         	report1 = (ArrayList)request.getAttribute("report1");
         		         	if (report1==null) report1=new ArrayList();
         		         	
         		         	int t1=0,t2=0,t3=0,t4=0,t5=0,t6=0,t7=0,t8=0;
         		         	double a1=0.00,a2=0.00,a3=0.00,a4=0.00,a5=0.00,a6=0.00,a7=0.00,a8=0.00;
         		         	for (int i=0;i<6;i++) { 
         		         		PhoneOperatorReport report = (PhoneOperatorReport)report1.get(i);
         		         		Entity CloseOut = (Entity)closeouts.get(i);
         		         		
         		         		int operators=0;
         		         		if (CloseOut.getProperty("phoneOperators")!=null)
         		         			operators=Integer.parseInt(CloseOut.getProperty("phoneOperators").toString());         		         			
         		         	%>
         		         	<tr>
         		         		<td class="fieldData"><%=report.getDayOfWeek()%></td>
         		         		<td class="fieldData"><%=report.getNewTicket()%></td>
         		         		<td class="fieldData"></td>
         		         		<td class="fieldData"><%=report.getRejects()%></td>
         		         		<td class="fieldData"></td>
         		         		<td class="fieldData" bgcolor="#e4e4e4"><%=report.getDonation()%></td>
         		         		<td class="fieldData"><%=report.getCancelled()%></td>
         		         		<td class="fieldData"><%=report.getReschedule()%></td>
         		         		<td class="fieldData" bgcolor="#e4e4e4"><%=report.getPrevious()%></td>
         		         		<td class="fieldData"><%=operators%></td>
         		         		<td class="fieldData"><%=report.getOther()%></td>
         		         	</tr>
         		         	<% 
         		         	t1+=report.getNewTicket()+report.getPrevious();
         		         	t2+=report.getRejects();
         		         	t3+=report.getDonation();
         		         	t4+=report.getCancelled();
         		         	//t5+=report.getReschedule();
         		         	t6+=report.getPrevious();
         		         	t7+=operators;
         		         	t8+=report.getOther();
         		         	} %>
         		         	<tr>
         		         		<td colspan="11" bgcolor="#000000" height="3">  		         		
         		         		</td>
         		         	</tr>
         		         	<tr>
         		         		<td class="fieldDataTotal"><b>Total Calls</b></td>
         		         		<td class="fieldDataTotal"><%=t1%></td>
         		         		<td class="fieldDataTotal"></td>
         		         		<td class="fieldDataTotal"><%=t2%></td>
         		         		<td class="fieldDataTotal"></td>
         		         		<td class="fieldDataTotal" bgcolor="#e4e4e4"><%=t3%></td>
         		         		<td class="fieldDataTotal"><%=t4%></td>
         		         		<!-- <td class="fieldDataTotal"><%=t5%></td> -->
         		         		<td class="fieldDataTotal" bgcolor="#e4e4e4"><%=t6%></td>
         		         		<td class="fieldDataTotal"><%=t7%></td>
         		         		<td class="fieldDataTotal"><%=t8%></td>
         		         	</tr>
         		         	<tr>
         		         		<td class="fieldData" align="right"><b>Divide</b></td>
         		         		<td class="fieldData">/6</td>
         		         		<td class="fieldData"></td>
         		         		<td class="fieldData">/6</td>
         		         		<td class="fieldData"></td>
         		         		<td class="fieldData" bgcolor="#e4e4e4">/6</td>
         		         		<td class="fieldData">/6</td>
         		         		<td class="fieldData">/6</td>
         		         		<td class="fieldData" bgcolor="#e4e4e4">/6</td>
         		         		<td class="fieldData">/6</td>
         		         		<td class="fieldData">/6</td>
         		         	</tr>
         		         	<%
         		         		a1=t1/6.0;
         		         		a2=t2/6.0;
         		         		a3=t3/6.0;
         		         		a4=t4/6.0;
         		         		a5=t5/6.0;
         		         		a6=t6/6.0;
         		         		a7=t7/6.0;
         		         		a8=t8/6.0;         		         	
         		         	%>
         		         	<tr>
         		         		<td class="fieldDataTotal" align="right"><b>Avg/Day</b></td>
         		         		<td class="fieldDataTotal"><fmt:formatNumber value="<%=a1%>" pattern="###.00" /></td>
         		         		<td class="fieldDataTotal"></td>
         		         		<td class="fieldDataTotal"><fmt:formatNumber value="<%=a2%>" pattern="###.00" /></td>
         		         		<td class="fieldDataTotal"></td>
         		         		<td class="fieldDataTotal" bgcolor="#e4e4e4"><fmt:formatNumber value="<%=a3%>" pattern="###.00" /></td>
         		         		<td class="fieldDataTotal"><fmt:formatNumber value="<%=a4%>" pattern="###.00" /></td>
         		         		<td class="fieldDataTotal"><fmt:formatNumber value="<%=a5%>" pattern="###.00" /></td>
         		         		<td class="fieldDataTotal" bgcolor="#e4e4e4"><fmt:formatNumber value="<%=a6%>" pattern="###.00" /></td>
         		         		<td class="fieldDataTotal"><fmt:formatNumber value="<%=a7%>" pattern="###.00" /></td>
         		         		<td class="fieldDataTotal"><fmt:formatNumber value="<%=a8%>" pattern="###.00" /></td>
         		         	</tr>
         		         	<tr>
         		         		<% totalCalls = t1+t2+t3+t4+t5+t6+t7+t8; %>
         		         		<td bgcolor="#f8e72d" colspan="10" align="center"><i><u>Total Weekly Call Center Activity - New & Previous Inquiries</i></u></td>
         		         		<td class="fieldData" bgcolor="#e4e4e4"><b><u><%=totalCalls%></u></b></td>
         		         	</tr>
         		         
         		         </table>		
         		</td>
         		
         		<!-- DRIVER'S REPORT -->
         		<td width="40%">
         		         <table width="100%" cellspacing="0" cellpadding="0" border="1">
         		         	<tr>
         		         		<td colspan="11" align="center">
         		         			<div style="font-size:12px"><b>DRIVERS' REPORT</b></div>
         		         		</td>
         		         	</tr>
         		         	<tr>
         		         		<td width="50" height="50" class="tableHead">Tickets Picked Up</div></td>
         		         		<td width="50" class="tableHead">Tickets Rejected</td>
         		         		<td width="50" class="tableHead">No Responses</td>
         		         		<td width="50" class="tableHead">Cancelled By Donor</td>
         		         		<td width="50" class="tableHead">Special Pick Ups</td>
         		         		<td width="50" class="tableHead"># of Drivers (Max 10)</td>
         		         		<td class="tableHead" colspan=2>Wk/Mo Pick Up Avg</td>
         		         	</tr>
         		         	<%
         		         	
         					int b1=0,b2=0,b3=0,b4=0,b5=0,b6=0;
         					double c1=0.00,c2=0.00,c3=0.00,c4=0.00,c5=0.00,c6=0.00;
         		         	
         		         	for (int i=0;i<6;i++) { 
         		         	    String pickedUp="0", rejected="0", noresponses="0", cancelled="0", specials="0", drivers="0";
         					
         		         		Entity entity = (Entity)closeouts.get(i);
         		         		
         		         		if (entity.getProperty("pickedUp")!=null)
         		         			pickedUp=(String)entity.getProperty("pickedUp");
         		         		if (entity.getProperty("rejected")!=null)
         		         			rejected=(String)entity.getProperty("rejected");
         		         		if (entity.getProperty("noresponse")!=null)
         		         			noresponses=(String)entity.getProperty("noresponse");
         		         		if (entity.getProperty("cancelled")!=null)
         		         			cancelled=(String)entity.getProperty("cancelled");
         		         		if (entity.getProperty("special")!=null)
         		         			specials=(String)entity.getProperty("special");
         		         		if (entity.getProperty("drivers")!=null)
         		         			drivers=(String)entity.getProperty("drivers");
         		         			
         		         		b1+=Integer.parseInt(pickedUp);
         		         		b2+=Integer.parseInt(rejected);
         		         		b3+=Integer.parseInt(noresponses);
         		         		b4+=Integer.parseInt(cancelled);
         		         		b5+=Integer.parseInt(specials);
         		         		b6+=Integer.parseInt(drivers);
         		         	%>
         		         	<tr>
         		         		<td class="fieldData"><%=pickedUp%></td>
         		         		<td class="fieldData"><%=rejected%></td>
         		         		<td class="fieldData"><%=noresponses%></td>
         		         		<td class="fieldData"><%=cancelled%></td>
         		         		<td class="fieldData"><%=specials%></td>
         		         		<td class="fieldData"><%=drivers%></td>
         		         		<td width="80" class="fieldData"><b>Wk <%=i+1
         		         		%></b></td>
         		         		<td class="fieldData"></td>
         		         	</tr>
         		         	<% } 
         		         	
         		         		c1=b1/6.0;
         		         		c2=b2/6.0;
         		         		c3=b3/6.0;
         		         		c4=b4/6.0;
         		         		c5=b5/6.0;
         		         		c6=b6/6.0;
         		         	
         		         	%>
         		         	<tr>
         		         		<td colspan="8" bgcolor="#000000" height="3">  		         		
         		         		</td>
         		         	</tr>
         		         	<tr>
         		         		<td class="fieldDataTotal"><%=b1%></td>
         		         		<td class="fieldDataTotal"><%=b2%></td>
         		         		<td class="fieldDataTotal"><%=b3%></td>
         		         		<td class="fieldDataTotal"><%=b4%></td>
         		         		<td class="fieldDataTotal"><%=b5%></td>
         		         		<td class="fieldDataTotal"><%=b6%></td>
         		         		<td class="fieldDataTotal"><b>Total/Mo</b></td>
         		         		<td class="fieldDataTotal"></td>
         		         </tr>
         		         	<tr>
         		         		<td class="fieldData">/6</td>
         		         		<td class="fieldData">/6</td>
         		         		<td class="fieldData">/6</td>
         		         		<td class="fieldData">/6</td>
         		         		<td class="fieldData">/6</td>
         		         		<td class="fieldData">/6</td>
         		         		<td class="fieldData"></td>
         		         		<td class="fieldData">/4</td>
         		         	</tr>
         		         	<tr>
         		         		<td class="fieldDataTotal"><fmt:formatNumber value="<%=c1%>" pattern="###.00" /></td>
         		         		<td class="fieldDataTotal"><fmt:formatNumber value="<%=c2%>" pattern="###.00" /></td>
         		         		<td class="fieldDataTotal"><fmt:formatNumber value="<%=c3%>" pattern="###.00" /></td>
         		         		<td class="fieldDataTotal"><fmt:formatNumber value="<%=c4%>" pattern="###.00" /></td>
         		         		<td class="fieldDataTotal"><fmt:formatNumber value="<%=c5%>" pattern="###.00" /></td>
         		         		<td class="fieldDataTotal"><fmt:formatNumber value="<%=c6%>" pattern="###.00" /></td>
         		         		<td class="fieldDataTotal"></td>
         		         		<td class="fieldDataTotal">0</td>
         		         </tr>
         		        	<tr>
         		         		<td bgcolor="#fffff" colspan="8" align="center">&nbsp;</td>
         		         	</tr> 
         		         </table>			
         		</td>         	
         	</tr>         
         </table>
         </br>
         <div style="font-size:12px" align="center"><b>How Did Donor hear about Faith Farm Ministries?</b></div>
         
         <table width="90%" cellspacing="0" cellpadding="0" border="1" style="border-style: double; ">
         	<tr>
         		<!-- MARKETING REPORT -->
         		<td width="60%">
         		         <table width="100%" cellspacing="0" cellpadding="0" border="1">
         		         	
         		         	<tr>
         		         		<td width="80" height="25" class="tableHead">MARKETING DATA</div></td>
         		         		<td width="50" class="tableHead">Yellow Pages</div></td>
         		         		<td width="15" class="tableHead">Truck Lettering</td>
         		         		<td width="50" class="tableHead">Newspaper Ad</td>
         		         		<td width="15" class="tableHead">Previous Donor</td>
         		         		<td width="50" class="tableHead">Radio</td>
         		         		<td width="50" class="tableHead">TV</td>
         		         		<td width="50" class="tableHead">Friends</td>
         		         		<td width="50" class="tableHead">Website</td>
         		         		<td width="50" class="tableHead">Other</td>
         		         		<td width="80" class="tableHead">Total Calls</td>
         		         		<td width="150" bgcolor="#f8e72d" class="tableHead"><b>1 special = 5 route stops/1ST.</br>WEEK OF NEW SYSTEM</b></td>
         		         	</tr>
         		         	<tr>
         		         		<td class="fieldData"><b>Callers Response</b></td>
         		         		<td class="fieldData"><%=request.getAttribute("source1")%></td>
         		         		<td class="fieldData"><%=request.getAttribute("source2")%></td>
         		         		<td class="fieldData"><%=request.getAttribute("source3")%></td>
         		         		<td class="fieldData"><%=request.getAttribute("source4")%></td>
         		         		<td class="fieldData"><%=request.getAttribute("source5")%></td>
         		         		<td class="fieldData"><%=request.getAttribute("source5")%></td>
         		         		<td class="fieldData"><%=request.getAttribute("source6")%></td>
         		         		<td class="fieldData"><%=request.getAttribute("source7")%></td>
         		         		<td class="fieldData"><%=request.getAttribute("source8")%></td>
         		         		<td class="fieldData"><%=totalCalls%></td>
         		         		<td class="fieldData"></td>
         		         	</tr>
         		         	<tr>
         		         		<td class="fieldData"><b>/Total Calls</b></td>
         		         		<td class="fieldDataTotal"><%=totalCalls%></td>
         		         		<td class="fieldDataTotal"><%=totalCalls%></td>
         		         		<td class="fieldDataTotal"><%=totalCalls%></td>
         		         		<td class="fieldDataTotal"><%=totalCalls%></td>
         		         		<td class="fieldDataTotal"><%=totalCalls%></td>
         		         		<td class="fieldDataTotal"><%=totalCalls%></td>
         		         		<td class="fieldDataTotal"><%=totalCalls%></td>
         		         		<td class="fieldDataTotal"><%=totalCalls%></td>
         		         		<td class="fieldDataTotal"><%=totalCalls%></td>
         		         		<td class="fieldDataTotal"><%=totalCalls%></td>
         		         		<td class="fieldDataTotal"></td>
         		         	</tr>
         		         	<tr>
         		         		<% double p1=0.00,p2=0.00,p3=0.00,p4=0.00,p5=0.00,p6=0.00,p7=0.00,p8=0.00,p9=0.00; 
         		         		   p1=new Double((Integer)request.getAttribute("source1"))/new Double(totalCalls);
         		         		   p2=new Double((Integer)request.getAttribute("source2"))/new Double(totalCalls);
         		         		   p3=new Double((Integer)request.getAttribute("source3"))/new Double(totalCalls);
         		         		   p4=new Double((Integer)request.getAttribute("source4"))/new Double(totalCalls);
         		         		   p5=new Double((Integer)request.getAttribute("source5"))/new Double(totalCalls);
         		         		   p6=new Double((Integer)request.getAttribute("source6"))/new Double(totalCalls);
         		         		   p7=new Double((Integer)request.getAttribute("source7"))/new Double(totalCalls);
         		         		   p8=new Double((Integer)request.getAttribute("source8"))/new Double(totalCalls);
         		         		%>
         		         		
         		         		<td class="fieldData"><b>= %</b> <i>Carry 2 decimals</i></td>
         		         		<td class="fieldData"><fmt:formatNumber value="<%=p1%>" pattern="###.00%" /></td>
         		         		<td class="fieldData"><fmt:formatNumber value="<%=p2%>" pattern="###.00%" /></td>
         		         		<td class="fieldData"><fmt:formatNumber value="<%=p3%>" pattern="###.00%" /></td>
         		         		<td class="fieldData"><fmt:formatNumber value="<%=p4%>" pattern="###.00%" /></td>
         		         		<td class="fieldData"><fmt:formatNumber value="<%=p5%>" pattern="###.00%" /></td>
         		         		<td class="fieldData"><fmt:formatNumber value="<%=p5%>" pattern="###.00%" /></td>
         		         		<td class="fieldData"><fmt:formatNumber value="<%=p6%>" pattern="###.00%" /></td>
         		         		<td class="fieldData"><fmt:formatNumber value="<%=p7%>" pattern="###.00%" /></td>
         		         		<td class="fieldData"><fmt:formatNumber value="<%=p8%>" pattern="###.00%" /></td>
         		         		<td class="fieldData">100.00%</td>
         		         		<td align="center"><div style="color:red;"><b>SHOULD EQUAL 1.00 or 100%</b></div></td>
         		         	</tr>
         		         	
         		         	<tr>
         		         		<td bgcolor="#e4e4e4" align="left" colspan="7"><b>Enter Avg/Day</b></td>
         		         		<td bgcolor="#e4e4e4" align="left" colspan="5"><b>Enter Avg/Week</b></td>
         		         		
         		         	</tr>
       
       					</table>
       			</td>
       	 </tr>
     </table>
			
	  	</br></br>	  		
	  		<a href="javascript:print();" style="text-decoration:none;"><img src="<%=request.getContextPath()%>/images/Printer-icon.png" border=0 height="15" width="15"></a>
  		</br></br>
	  	</div>
  		
<script type="text/javascript">
 $(window).load(function () {
   init();
});
</script>
</body>
</html>