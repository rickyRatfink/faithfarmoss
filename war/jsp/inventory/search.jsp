 <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Faith Farm Ministries - Operational Support System</title>
<link href="<%=request.getContextPath()%>/styles/main.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/styles/tcal.css" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/script/tcal.js"></script> 
</head>
<body>

<br>

<table width="590" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td class="results-section" colspan="3">Inventory Item Search</td>		
	</tr>	
	<tr>
		<td class="results-heading" width="100">PLU#</td>
		<td class="results-heading" width="400">Description</td>
		<td class="results-heading" width="100">Dept. Code</td>		
	</tr>
	<tr>
		<td class="results-blank" width="100"><input name="plu" class="entry" size="6" maxlength="6"/></td>
		<td class="results-blank" width="400"><input name="desc" class="entry" size="60" maxlength="60"/></td>
		<td class="results-blank" width="100"><input name="deptcode" class="entry" size="6" maxlength="6"/></td>		
	</tr>
	<tr>
		<td colspan="3" align="center"><br>
			<input type="submit" class="button" value="Find">
		</td>
	</tr>
	<!--<tr>
		<td colspan="2" bgcolor="#66676b" height="14"></td>
	</tr>-->
</table>

</body>
</html>