<%@ page import="java.util.*" %>
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
<%@ page import="com.google.appengine.api.blobstore.BlobKey" %>
<%@ page import="com.google.appengine.api.images.ImagesService" %>
<%@ page import="com.google.appengine.api.images.ImagesServiceFactory" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>

<!DOCTYPE 
    html
    PUBLIC
    "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>
      Faith Farm Ministries - New Arrivals Report
    </title>
    <link href="<%=request.getContextPath()%>/styles/report.css" rel="stylesheet" type="text/css" />

    <script language="javascript" src='script/jquery-1.6.min.js'>
    </script>
    <script language="javascript" src='script/ajax.util.js'>
    </script>

    <script language="javascript">
      function printPage()
      {
        window.print();
      }
    </script>

  </head>
  <body onLoad="javascript:printPage();">
  </style>
  <!-- content -->

  <!-- home page content -->
  <!-- list container -->
  <div align="center">

    <table width="60%" cellspacing="0" cellpadding="0" border="0">
      <tr>
        <td width="50%" align="left">
          &nbsp;&nbsp;
          <img src="<%=request.getContextPath()%>/images/report-logo.jpg"  />
        </td>
        <td width="50%" align="right" valign="top">
          <div style="font-size:14px">
            <b>
              New Arrivals Report
            </b>
          </div>
          <div style="font-size:10px">
            Run on:&nbsp;&nbsp;
            <i>
              <%=new java.util.Date()%>
            </i>
          </div>
        </td>
      </tr>
    </table>
    </br>
    <table width="59%" cellspacing="0" cellpadding="0" border="0">

      <%
      int index=0;
      ArrayList entities = (ArrayList)request.getAttribute("results");
      %>
	  <tr>
	  	<td align="Left" colspan="2" valign="bottom"><h2><%=entities.size()%>&nbsp;&nbsp;New Arrivals</h2></td>
	  </tr>
      <%
      Map<String, Object> properties = null;
      for (int i=0;i<entities.size();i++) {
        Entity result = (Entity)entities.get(i);
        properties = result.getProperties();
        %>
        <tr>
          <td colspan="2" height="1" bgcolor="#000000">
          </td>
        </tr>
        <tr>
          <td width="352">
            <%
            String imageUrl = "";
            try {
              BlobKey blobKey = (BlobKey)result.getProperty("imageKey");
              ImagesService imagesService = ImagesServiceFactory.getImagesService();
              imageUrl = imagesService.getServingUrl(blobKey);
            } catch (Exception e) {
              imageUrl="/images/person_photos/blank.gif";
            }
            %>
            <img src="<%=imageUrl%>" border="1" width="129" height="97"/>
          </td>
          <td align="left" valign="top" <% if (i%2==0) {%>bgcolor="#e4e4e4" <%}%>>
            <b>
              Name
            </b>
            :&nbsp;
            <%=properties.get("firstName")+" "+properties.get("middleInitial")+" "+properties.get("lastName")+" "+properties.get("suffix")%>
          </br>
          <b>
            Workarea#
          </b>
          :&nbsp;
          <%=properties.get("workAssignment")%>
        </br>
        <b>
          Supervisor
        </b>
        :&nbsp;
        <%=properties.get("supervisor")%>
      </br>
      <b>
          Birthdate
        </b>
        :&nbsp;
        <%=properties.get("dateOfBirth")%>
      </br>
    </td>
  </tr>

  <%
}
%>

</table>

</div>

</body>
</html>
