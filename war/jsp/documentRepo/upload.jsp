<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Map"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory"%>
<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory"%>
<%@ page import="com.google.appengine.api.datastore.DatastoreService"%>
<%@ page import="com.google.appengine.api.datastore.Query"%>
<%@ page import="com.google.appengine.api.datastore.Entity"%>
<%@ page import="com.google.appengine.api.datastore.FetchOptions"%>
<%@ page import="com.google.appengine.api.datastore.Key"%>
<%@ page import="com.google.appengine.api.datastore.KeyFactory"%>
<%@ page import=" com.google.appengine.api.blobstore.BlobstoreService"%>
<%@ page import=" com.google.appengine.api.blobstore.BlobstoreServiceFactory"%>
<%@ page import="com.google.appengine.api.users.User"%>
<%@ page import="com.google.appengine.api.users.UserService"%>


<jsp:include page="../includes/header.jsp" />
<jsp:include page="../includes/menu/index.jsp" flush="true" />

<%
	String required = "<font size='-2' color='red'>*</font>";
	String action = request.getParameter("action");
	Entity Person = (Entity) request.getAttribute("Person");

	BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
%>
<!----Main Content--->
<td>
	<table width="820" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td class="content-body"><br /> <br /> <!--form ENCTYPE="multipart/form-data" method="POST" action="<%=request.getContextPath()%>/upload"-->
				<form action="<%=blobstoreService.createUploadUrl("/upload")%>" method="post" enctype="multipart/form-data">
					<div class="reports" style="border: 0;" id="report-list-ctr">
						<table width="620" cellspacing="0" cellpadding="0" border="0" style="border-collapse: collapse;">
							<tr>
								<td colspan="2" class="instructions">Use the browse button to locate the artifact. Click the upload button after selecting the photo.</td>
							</tr>
							<tr>
								<td class="edit-field-dark">Title:<input name="docTitle" maxlength="30" size="30" class="entry" /></td>
								<td class="edit-field-dark"><input type="file" name="myFile" /></td>
							</tr>
							

							<tr>
								<td width="50%" height="60" align="right" colspan="2"><input type="submit" name="action" value="Upload Photo" class="buttonSave"> <input type="hidden" name="personKey"
									value="<%=KeyFactory.keyToString(Person.getKey())%>"> <input type="hidden" name="docType" value="artifact" /></td>
							</tr>
						</table>
				</form> <!-- Document Listing --> <%
 	//check user authentication via Google
 	UserService userService = UserServiceFactory.getUserService();
 	User user = userService.getCurrentUser();

 	String userRole = (String) session.getAttribute(user.getNickname() + "_ROLE");
 	ArrayList entities = (ArrayList) request.getAttribute("results");
 	String farm = (String) session.getAttribute("farmBase");
 	
 	if (entities != null) {
 		int size = entities.size();
 %>
				<div class="results" style="border: 0;" id="customer-list-ctr">
					<table width="850" cellspacing="0" cellpadding="0" border="0" style="border-collapse: collapse;">
						<tr>
							<td class="grid">
								<table width="850" cellspacing="0" cellpadding="0" border="0" style="border-collapse: collapse;">
									<tr>
										<td class="grid-heading">Search Results</td>
									</tr>
								</table>
								<table width="850" cellspacing="0" cellpadding="0" border="0" style="border-collapse: collapse;">
									<tr>
										<td class="resultsGrid-columnHeading" width="200">Title</td>
										<td class="resultsGrid-columnHeading" width="150">Type</td>
										<td class="resultsGrid-columnHeading" width="200">Uploaded</td>
										<%
											if ("Administrator".equals(userRole)) {
										%>
										<td class="resultsGrid-columnHeading"></td>
										<%
											}
										%>
									</tr>
									<%
										Map<String, Object> properties = null;
											if (entities != null)
												for (int i = 0; i < entities.size(); i++) {
													//for (Entity result : entities) {
													Entity result = (Entity) entities.get(i);
													properties = result.getProperties();
									%>
									<form method="POST" action="<%=request.getContextPath()%>/student">
										<tr>
											<td class="grid-Id"><%=properties.get("title")%></td>
											<td class="grid-results"><%=properties.get("content_type")%></td>
											<td class="grid-results"><%=properties.get("creationDate")%></td>
											<%
												if ("Administrator".equals(userRole)) {
											%>
											<td class="grid-buttons"><input type="submit" name="action" value="View Artifact" class="imageButtonView" title="View Artifact" /> <input type="submit" name="action" value="Delete Artifact"
												class="imageButtonDelete" title="Delete Artifact" /></td>
											<%
												}
											%>
										</tr>
										<input type="hidden" name="docKey" value="<%=result.getProperty("docKey")%>" />
										<input type="hidden" name="key" value="<%=KeyFactory.keyToString(result.getKey())%>" /> 
                                      	<input type="hidden" name="personKey" value="<%=KeyFactory.keyToString(Person.getKey())%>" /> 
                                      	  
									</form>
									<%
										}
									%>
									<% if (size==0) { %>
									<tr>
										<td class="results-bg" colspan="12">no artifact have been uploaded for this person.</td>
									</tr>
									<% } %>
								</table>
							</td>
						</tr>
					</table>
				</div> <br>
 <%	} %></td>
		</tr>
	</table>
</td>
</tr>





</div>
</table>



<jsp:include page="../includes/footer.jsp" />


