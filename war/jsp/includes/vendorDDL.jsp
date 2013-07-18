<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>

<select name="vendor" class="entry">
	<option value="">select</option>
		<% //try {
		    int index=0;
			Iterable<Entity> vendors = (Iterable<Entity>)session.getAttribute("vendors");
			Map<String, Object> properties = null;
			
			if (vendors!=null)
			for (Entity result : vendors) {
			  properties = result.getProperties();
			  
			 %>
					<option value="<%=properties.get("vendorName")%>" ><%=properties.get("vendorName")%></option>
			<% } %>
			
		<% //} catch (Exception e) { System.out.println ("error"+e); } %>
</select>