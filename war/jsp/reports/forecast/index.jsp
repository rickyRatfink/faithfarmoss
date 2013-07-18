<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>CSS Stacked Bar Graphs</title>
</meta>
<link href="<%=request.getContextPath()%>/jsp/reports/forecast/stylesheets/csschart.css" rel="stylesheet" type="text/css" media="screen" />
<style>
<%

String []smonth = { "Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec" };
			
%>

body {
	margin:0;
	padding:40px;
	background:#fff;
	font:70% Arial, Helvetica, sans-serif;
	color:#555;
	line-height:180%;
}

</style>
</head>
<body>
	<h1><img src="<%=request.getContextPath()%>/images/logo.png" width="90" height="54" />&nbsp;&nbsp;2012 Projected vs Actual Sales Report</h1>
    
<ul class="yAxis">
	<li>100</li>
	<li>90</li>
	<li>80</li>
	<li>70</li>
	<li>60</li>
	<li>50</li>
	<li>40</li>
	<li>30</li>
	<li>20</li>
	<li>10</li>

</ul>
	<dl id="csschart">
		<% for (int month=0;month<12;month++) { %>
		<dt><%=smonth[month] %></dt>
		<dd class="p36"><span><b><%=request.getAttribute("actual_Month_"+month) %></b></span></dd>
		<dd class="sub p30" ><span><b><%=request.getAttribute("budget_Month_"+month) %></b></span></dd>
		<% } %>
	</dl>
	
<ul class="xAxis">
	<li>Jan</li>
	<li>Feb</li>
	<li>Mar</li>
	<li>Apr</li>
	<li>May</li>
	<li>Jun</li>
	<li>Jul</li>
	<li>Aug</li>
	<li>Sep</li>
	<li>Oct</li>
	<li>Nov</li>
	<li>Dec</li>
	
</ul>
</body>
</html>

