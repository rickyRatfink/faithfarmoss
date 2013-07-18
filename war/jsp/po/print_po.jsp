<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="org.faithfarm.dataobjects.PurchaseOrder" %>
<%
PurchaseOrder po = (PurchaseOrder)request.getAttribute("purchase_order");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>PO #<%= po.getPoNumber() %></title>
</head>
<body>
<table width="100%" cellpadding="0" cellspacing="0">
	<tr>
		<td width="30%"><img src="/images/farm_logo3.png" width="300px" border="0" alt="Faith Farm Minsitries"/></td>
		<td colspan="2" style="vertical-align:top;">
			<div style="text-align:right;">
				<div style="font-size:46px;margin-top:30px;margin-right:30px;">PURCHASE ORDER</div>
				<div style="margin-right:30px;">
					<table style="float:right;" width="300px">
						<tr><td style="font-size:30px;width:200px;text-align:left;">Date:</td><td style="font-size:30px;text-decoration:underline;"><%= po.getDateShort() %></td></tr>
						<tr><td style="font-size:30px;width:200px;text-align:left;">P.O.#</td><td style="font-size:30px;border:1px solid black;background-color:#c0c0c0;"><%= po.getFarmShortCode() %>-<%= po.getPoNumber().toString() %></td></tr>
					</table>
				</div>
			</div>
		</td>
	</tr>
</table>
</body>
</html>