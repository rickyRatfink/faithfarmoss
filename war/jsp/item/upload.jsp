<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreService" %>
<%@ page import="com.google.appengine.api.datastore.Query" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ page import=" com.google.appengine.api.blobstore.BlobstoreService" %>
<%@ page import=" com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>

<jsp:include page="../includes/header.jsp"/>
<jsp:include page="../includes/menu/index.jsp" flush="true"/>

<%
String required = "<font size='-2' color='red'>*</font>";
String action=request.getParameter("action");
Entity Item=(Entity)request.getAttribute("Item");

BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
%>
<!----Main Content--->
<td>
  <table width="820" border="0" cellpadding="0" cellspacing="0">
    <tr>
      <td  class="content-body">
        <br/>
        <br/>
        <!--form ENCTYPE="multipart/form-data" method="POST" action="<%=request.getContextPath()%>/upload"-->
        <form action="<%= blobstoreService.createUploadUrl("/upload") %>" method="post" enctype="multipart/form-data">
          <div class="reports" style="border:0;" id="report-list-ctr">
            <table width="620" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
              <tr>
                <td class="instructions" >
                  Use the browse button to locate the photo. Click the upload button after selecting the photo.
                </td>
              </tr>
              <tr>
                <td class="edit-field-dark">
                  <input type="file" name="myFile"/>
                </td>
              </tr>


              <tr>
                <td  width="50%" height="60"  align="right" colspan="2">
                  <input type="submit" name="action" value="Upload Photo" class="buttonSave">
                  <input type="hidden" name="docType" value="Donation">
                  <input type="hidden" name="itemKey" value="<%=KeyFactory.keyToString(Item.getKey())%>">
                </td>
              </tr>
            </table>
          </form>



          <tr>
            <td>
              <!--img src="<%=request.getContextPath()%>/images/header_btm.png"-->
            </td>
          </tr>
        </table>
      </td>
    </tr>





  </div>
</table>



<jsp:include page="../includes/footer.jsp"/>


