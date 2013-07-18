<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ page import="org.w3c.dom.NodeList" %>
<%@ page import="org.w3c.dom.Element" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Map" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>
      Faith Farm Ministries - Operational Support System
    </title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/styles/dispatch.css"  />
  </head>

  <script language="javascript">
    function printPage()
    {
      window.print();
    }
  </script>

  <body onLoad="javascript:printPage();" topmargin="0">
    <!-- body -->
    <%
    String required = "<font size='-2' color='red'>*</font>";
    Entity Person = (Entity)request.getAttribute("Person");
    Entity Address = (Entity)request.getAttribute("Address");
    Entity SalesOrder = (Entity)request.getAttribute("SalesOrder");
    Entity SalesDispatch = (Entity)request.getAttribute("SalesDispatch");
    NodeList xmlPerson=(NodeList)request.getAttribute("xmlPerson");
    NodeList xmlAddress=(NodeList)request.getAttribute("xmlAddress");
    NodeList xmlSalesOrder=(NodeList)request.getAttribute("xmlSalesOrder");
    NodeList xmlSalesDispatch=(NodeList)request.getAttribute("xmlSalesDispatch");
    %>

    <table  width="700" cellpadding="0" cellspacing="0">
      <tr>
        <td valign="top" align="left" valign="top">
          <img src="<%=request.getContextPath()%>/images/logo.png">
          <br/>
          <b>
            CUSTOMER COPY
          </b>
          <br/>
          <br/>
        </td>
        <td valign="top" align="right" class="dispatchTitle">
          <b>
            CUSTOMER ORDER
          </b>
          <br/>
          ORDER #:
          <%=SalesOrder.getProperty("orderNumber")%>
          <br/>
          SALES DATE:&nbsp;<%=SalesOrder.getProperty("creationDate")%>
          <br/>
          <%
          if (SalesDispatch!=null) {
            %>
            DELIVERY DATE:
            <%=SalesDispatch.getProperty("deliveryDate")%>
            <br/>
            LOCATION:
            <%=SalesDispatch.getProperty("deliveryLocation")%>
            <br/>
            <%
          }
          %>
          SALES AGENT:
          <%=SalesOrder.getProperty("createdBy")%>
        </br>
      </td>
    </tr>
    <tr>
      <td class="dispatchFormHeading" align="left">
        1980 NW 9th Ave, Fort Lauderdale, Florida
      </td>
      <td class="dispatchFormHeading" align="right">
        (954)763-7787&nbsp;/&nbsp;FAX (954)468-1416
      </td>
    </tr>
    <tr>
      <td colspan="2">
        <hr>
      </td>
    </tr>
  </table>
  <table  width="700" cellpadding="0" cellspacing="0">
    <tr>
      <td align="left" valign="top">
        <table width="350"  cellpadding="0" cellspacing="0">
          <tr>
            <td class="dispatchFormAddress" align="left">
              <br/>
              <b>
                CUSTOMER:
              </b>
            </td>
          </tr>

          <tr>
            <td class="dispatchFormAddress" align="left">
              <%=Person.getProperty("firstName")%>
              &nbsp;
              <%=Person.getProperty("middleInitial")%>
              &nbsp;
              <%=Person.getProperty("lastName")%>
              <br/>
              <%=Address.getProperty("addressLine1")%>
              <br/>
              <%=Address.getProperty("city")%>
              ,&nbsp;
              <%=Address.getProperty("state")%>
              &nbsp;
              <%=Address.getProperty("zipcode")%>
              <br/>
              <br/>
              <b>
                Home Phone:
              </b>
              &nbsp;
              <%=Address.getProperty("homePhone")%>
              <br/>
              <b>
                Work Phone:
              </b>
              &nbsp;
              <%=Address.getProperty("workPhone")%>
              <br/>
              <b>
                Cell Phone:
              </b>
              &nbsp;
              <%=Address.getProperty("cellPhone")%>
              <br/>
              <b>
                Email:
              </b>
              &nbsp;
              <%=Address.getProperty("email")%>
              <br/>

            </td>
          </tr>
        </table>
      </td>
      <td width="50%" class="dispatchFormAddress" align="left" valign="top">
        <%
        if (SalesDispatch!=null) {
          %>
          <br/>
          <b>
            Major Intersection:
          </b>
          <%=SalesDispatch.getProperty("majorIntersection")%>
          <br/>
          <b>
            Street Suffix:
          </b>
          <%=SalesDispatch.getProperty("streetSuffix")%>
          <br/>
          <b>
            Subdivision:
          </b>
          <%=SalesDispatch.getProperty("subdivision")%>
          <br/>
          <br/>
          <b>
            Property Type:
          </b>
          &nbsp;
          <%=SalesDispatch.getProperty("structureType")%>
          <br/>
          <b>
            Gated Community #:
          </b>
          &nbsp;
          <%=SalesDispatch.getProperty("gatedCommunityFlag")%>
          <br/>
          <b>
            Gate Instructions:
          </b>
          &nbsp;
          <%=SalesDispatch.getProperty("gateInstructions")%>
          <br/>
          <b>
            Gate Code
          </b>
          &nbsp;
          <%=SalesDispatch.getProperty("gateCode")%>
          <br/>
          <b>
            Building #:
          </b>
          &nbsp;
          <%=SalesDispatch.getProperty("buildingNumber")%>
          <br/>
          <b>
            Unit #:
          </b>
          &nbsp;
          <%=SalesDispatch.getProperty("unitNumber")%>
          <br/>
          <b>
            Elevator Access:
          </b>
          &nbsp;
          <%=SalesDispatch.getProperty("elevatorFlag")%>
          <br/>
          <b>
            Floor #:
          </b>
          &nbsp;
          <%=SalesDispatch.getProperty("floorNumber")%>
          <br/>
          <%
        }
        %>
      </td>
    </tr>
    <tr>
      <td colspan="2" class="dispatchFormItems">
        <br/>
        <table width="700" border="1" cellpadding="0" cellspacing="0">
          <tr>
            <td width="50" bgcolor="#e4e4e4">
              Item#
            </td>
            <td width="50" bgcolor="#e4e4e4">
              Quantity
            </td>
            <td width="150" bgcolor="#e4e4e4">
              Vendor
            </td>
            <td width="150" bgcolor="#e4e4e4">
              Item Name
            </td>
            <td width="100" bgcolor="#e4e4e4">
              Price
            </td>
          </tr>
          <%
          double total=0.00;
          for (int i=0;i<9;i++) {

            if (SalesOrder.getProperty("itemNumber"+i)!=null&&!"".equals(SalesOrder.getProperty("itemNumber"+i).toString())) {
              String price=SalesOrder.getProperty("price"+i).toString();
              String qty=SalesOrder.getProperty("quantity"+i).toString();
              total+= (Double.valueOf(price).doubleValue())*(Integer.valueOf(qty).intValue());
              %>
              <tr>
                <td class="dispatchFormItems" width="50">
                  <%=SalesOrder.getProperty("itemNumber"+i)%>
                </td>
                <td class="dispatchFormItems" width="50">
                  <%=SalesOrder.getProperty("quantity"+i)%>
                </td>
                <td class="dispatchFormItems" width="150">
                  <%=SalesOrder.getProperty("vendor"+i)%>
                </td>
                <td class="dispatchFormItems" width="150">
                  <%=SalesOrder.getProperty("itemName"+i)%>
                </td>
                <td class="dispatchFormItems" width="100">
                  <%=SalesOrder.getProperty("price"+i)%>
                </td>
              </tr>
              <%
              } //end null check
              }//end item loop
              %>
              <tr>
                <td height="1" bgcolor="#000000">
                </td>
              </tr>
              <tr>
                <td colspan="4" align="right">
                  Sub-Total&nbsp;
                </td>
                <td>
                  <fmt:formatNumber value="<%=total%>" type="currency" />
                </td>
              </tr>
              <tr>
                <td colspan="4" align="right">
                  Tax&nbsp;
                </td>
                <td>
                  <fmt:formatNumber value="<%=total*.07%>" type="currency" />
                </td>
              </tr>
              <tr>
                <td colspan="4" align="right">
                  Total&nbsp;
                </td>
                <td>
                  <fmt:formatNumber value="<%=total+(total*.07)%>" type="currency" />
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<table  width="700" border="1" cellpadding="0" cellspacing="0">

  <%
  if (SalesDispatch!=null) {
    %>
    <tr>
      <td height="60" class="dispatchFormItems" valign="top" align="left">
        <b>
          NOTES:
        </b>
        <br/>
        <%=SalesDispatch.getProperty("notes")%>
      </td>
    </tr>
    <%
  }
  %>
</table>
</br>
<%
if (SalesDispatch!=null) {
  %>
  <input type="checkbox" name="acceptTermsPolicies" checked/>
  <i>
    I acknowledge that I have read Faith Farm's delivery/donation terms and policies to the customer.
  </i>
  <br/>
  <br/>
  <br/>
  <br/>
  <%
}
%>
<b>
  A WORK IN YOUR COMMUNITY BRINING THE LIGHT OF CHRIST TO THOSE IN MORE NEED THAN OURSELVES
</B>


<br/>
<br/>
</body>
</html>
