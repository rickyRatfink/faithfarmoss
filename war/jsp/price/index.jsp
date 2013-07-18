<jsp:include page="../includes/header.jsp"/>

<jsp:include page="../includes/menu/index.jsp" flush="true"/>
<script type="text/javascript" src="<%= request.getContextPath() %>/script/handlebars-1.0.0.beta.6.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/script/price.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/script/spin.min.js"></script>
<script type="text/x-handlebars-template" id="results_template">
<table style="width:80%;margin:0 auto;">
	<thead>
		<tr style="background-color:#5870b3;color:#fff;font-weight:bold;padding:5px;">
			<td>Item #</td><td>Description</td><td>Price</td>
		</tr>
	</thead>
	<tbody>
	{{#each items}}
		<tr>
			<td>{{item_num}}</td><td>{{description}}</td><td>\${{price}}.00</td>
		</tr>
	{{/each}}
	{{#unless items.length}}
		<tr>
			<td colspan="3" style="text-align:center;">No items for search.</td>
		</tr>
	{{/unless}}
</table>
</script>
<!----Main Content--->
<td>
  <table width="820" border="0" cellpadding="0" cellspacing="0">
    <tr>
      <td>
        <br>
        <!--img src="<%=request.getContextPath()%>/images/header_top.png"-->
      </td>
    </tr>

    <tr>
      <td class="content-body">
        <div class="content">
        	<h2 class="text1">New Furniture Price Lookup</h2>
        	<div>&nbsp;</div>
        	<table width="50%">
        		<tr>
        			<td><strong>Select Manufacturer:</strong></td>
        			<td>
        				<select id="manufacturer" style="width:100%;">
        					<option value="coaster">Coaster</option>
        				</select>
        			</td>
        		</tr>
        		<tr>
        			<td><strong>Item Number:</strong></td>
        			<td>
       					<input type="text" id="item" style="width:98%;"/>
       				</td>
       			</tr>
       			<tr>
       				<td colspan="2" style="text-align:right;"><input type="button" value="Search" id="search_btn"/></td>
       			</tr>
        	</table>
        	<div>&nbsp;</div>
        	<div id="results"></div>
        			
        </div>
      </td>
    </tr>
  </table>
</td>
<jsp:include page="../includes/footer.jsp"/>





        