<%@ page import="java.util.ArrayList" %>

<select name="vendor" class="entry">
	<option value="">select</option>
		<% //try {
		    int index=0;
			ArrayList vendors = (ArrayList)session.getAttribute("vendors");
			
			if (vendors!=null)
			for (int i=0;i<vendors.size();i++) {
			  String vendorName = (String)vendors.get(i);
			  
			 %>
					<option value="<%=vendorName%>" ><%=vendorName%></option>
			<% } %>
			
		
</select>