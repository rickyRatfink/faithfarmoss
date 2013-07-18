<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreService" %>
<%@ page import="com.google.appengine.api.datastore.Query" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ page import="org.faithfarm.utilities.HTMLBuilder" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.google.appengine.api.blobstore.BlobKey" %>
<%@ page import="com.google.appengine.api.images.ImagesService" %>
<%@ page import="com.google.appengine.api.images.ImagesServiceFactory" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>

<%
	//check user authentication via Google
	UserService userService = UserServiceFactory.getUserService(); 
	User user = userService.getCurrentUser();
	
	ArrayList entities = (ArrayList)request.getAttribute("results");
	if (entities==null)
		entities=new ArrayList();
%>

<jsp:include page="includes/header.jsp" flush="true"/>


	<div id="container">																																																																																																																																																																											
	  <div id="center" class="column">
	  
	  <h2><%=request.getAttribute("type") %></h2>
	  		<div class="stuff">
	  		<%
	  		for (int i=0;i<entities.size();i++) {
	  			Entity Item = (Entity)entities.get(i);
	  		%>
				<div class="item">
								<% 
                    			String imageUrl = "";
                    			try {
                    			BlobKey blobKey = (BlobKey)Item.getProperty("imageKey");
                    			ImagesService imagesService = ImagesServiceFactory.getImagesService();
                    			imageUrl = imagesService.getServingUrl(blobKey);
                    			} catch (Exception e) {
                    			imageUrl="/jsp/onlinestore/images/noimage.jpg";
                    			}
                    			%>
					<img src="<%=imageUrl%>" width="75" height="75" alt=""  />
					<h3>&nbsp;&nbsp;<%=Item.getProperty("itemName") %></br>
					&nbsp;&nbsp;<%=Item.getProperty("vendor") %></br>
					&nbsp;&nbsp;$<%=Item.getProperty("price") %></h3>
					&nbsp;&nbsp;<a href="#"><img src="<%=request.getContextPath() %>/jsp/onlinestore/images/addToCart.jpg" alt="" /></a>
				</div>
			<% } %>
				
			</div>
			
		</div>
	
<jsp:include page="includes/categories.jsp" flush="true"/>
<jsp:include page="includes/right.jsp" flush="true"/>

</div>
	
	<jsp:include page="includes/footer.jsp" flush="true"/>

	
		