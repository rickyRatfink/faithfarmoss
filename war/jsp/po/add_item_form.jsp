<script language="javascript" src='//ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js'></script>
<script type="text/javascript">
$(document).ready(function() {
  $("#item_num").focus();
  $("#add_btn").click(function(e) {
    if(window.parent.item_handler) {
      window.parent.item_handler.addItem({
        item_num: $("#item_num").val(),
        description: $("#description").val(),
        quantity: $("#quantity").val(),
        price: $("#price").val()
      });
      window.parent.$.fancybox.close();
    }
  });
});
</script>

<h3>Add Item</h3>
<div>&nbsp;</div>
<table>
	<tr>
		<td>Item Number:</td><td><input type="text" id="item_num"/></td>
	</tr>
	<tr>
		<td>Description:</td><td><input type="text" id="description"/></td>
	</tr>
	<tr>
		<td>Quantity:</td><td><input type="text" id="quantity"/></td>
	</tr>
	<tr>
		<td>Price:</td><td><input type="text" id="price"/></td>
	</tr>
	<tr>
		<td colspan="2" style="text-align:right;"><input type="button" id="add_btn" value="Add Item"/></td>
	</tr>
</table>
