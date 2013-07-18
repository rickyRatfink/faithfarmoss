<%@ page import=" com.google.appengine.api.blobstore.BlobstoreService" %>
<%@ page import=" com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>

<%
	BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
%>
<body>
    <form action="<%= blobstoreService.createUploadUrl("/upload") %>" method="post" enctype="multipart/form-data">
        <input type="file" name="myFile">
        <input type="submit" value="Submit">
    </form>
</body>