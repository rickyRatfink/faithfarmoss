<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Calendar" %>
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
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Hashtable" %>
<%@ page import="java.util.Enumeration" %>
<%@ page import="java.util.Map" %>
<%@ page import="org.w3c.dom.Element" %>
<%@ page import="org.w3c.dom.NodeList" %>

<jsp:include page="../includes/header.jsp"/>


<jsp:include page="../includes/menu/index.jsp" flush="true"/>


<%
String required = "<font size='-2' color='red'>*</font>";
ArrayList results = (ArrayList)session.getAttribute("results");
%>
<!----Main Content--->
<td valign="top">
  <table width="820" border="0" cellpadding="0" cellspacing="0">
    <tr>
      <td>
        <br>
        <!--img src="<%=request.getContextPath()%>/images/header_top.png"-->
      </td>
    </tr>
    <tr>
      <td class="content-body">
        <table>
          <tr>
            <td class="text1">
              Truck #
              <%=request.getAttribute("truck")%>
              for
              <%=request.getAttribute("arrivalDate")%>
            </td>
          </tr>
          <tr>
          	<td class="instructions">
          		Listed below are the items that were scheduled for a donation pickup.  Enter the item name, item type and price
          		and select the appropriate action.
          	</td>          
          </tr>
          
          

        </table>
        <br>
        <jsp:include page="../includes/errors.jsp" flush="true"/>
		</br>


        <!-- -display results on after search -->

        <div class="results" style="border:0;" id="customer-list-ctr">
        
          <table width="950" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
            <tr>
              <td  class="grid">
                <table width="950" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
                  <tr>
                    <td  class="grid-heading" >
                      Donated Items On Ticket
                    </td>
                  </tr>
                </table>
                <table width="950" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
                  <tr>
                    <td class="grid-columnHeading" width="20">
                    </td>
                    <td  class="grid-columnHeading" width="60">
                      <a 
                          href="#"
                          style="text-decoration:font-family:"Courier
                          New", Courier, monospace;font-size:medium;color:#5e82a6;">



                        <b>
                          Vendor
                        </b>
                      </a>
                    </td>
                    <td  class="grid-columnHeading" width="60">
                      <a 
                          href="#"
                          style="text-decoration:font-family:"Courier
                          New", Courier, monospace;font-size:medium;color:#5e82a6;">



                        <b>
                          Item#
                        </b>
                      </a>
                    </td>
                    <td  class="grid-columnHeading" width="140">
                      <a 
                          href="#"
                          style="text-decoration:font-family:"Courier
                          New", Courier, monospace;font-size:medium;color:#5e82a6;">



                        <b>
                          Item Name
                        </b>
                      </a>
                    </td>
                    <td  class="grid-columnHeading" width="20">
                      <a 
                          href="#"
                          style="text-decoration:font-family:"Courier
                          New", Courier, monospace;font-size:medium;color:#5e82a6;">



                        Qty
                      </a>
                    </td>
                    <td  class="grid-columnHeading" width="120">
                      <a 
                          href="#"
                          style="text-decoration:font-family:"Courier
                          New", Courier, monospace;font-size:medium;color:#5e82a6;">



                        Item Type
                      </a>
                    </td>
                    <td  class="grid-columnHeading" width="80">
                      <a 
                          href="#"
                          style="text-decoration:font-family:"Courier
                          New", Courier, monospace;font-size:medium;color:#5e82a6;">



                        Price
                      </a>
                    </td>
                    <td class="grid-columnHeading">
                      <a 
                          href="#"
                          style="text-decoration:font-family:"Courier
                          New", Courier, monospace;font-size:medium;color:#5e82a6;">



                        Action
                      </a>
                    </td>
                  </tr>
                  <%
                  int row=0;
                  String disabled="";
                  String rowClass="";
                  
                  int errRow=-1;
                  String sErrRow=(String)request.getAttribute("errorRow");
                  if (sErrRow!=null) errRow=Integer.parseInt(sErrRow);
                  
                  String truck = (String)request.getAttribute("truck");
                  NodeList xmlDispatch = (NodeList)request.getAttribute("xmlDispatch");
                  for (int a=0;a<results.size();a++) {
                    
                    Entity Donation = (Entity)results.get(a);
                    for (int i = 18; i < xmlDispatch.getLength() - 1; i++) {
                      //if (row>0) disabled="disabled=true";
                      Element propertyElement = (Element) xmlDispatch.item(i);
                      String name = propertyElement.getAttribute("name").toString();
                      String sQty="0";
                      if (Donation.getProperty(name)!=null)
                      sQty = Donation.getProperty(name).toString();

                      int qty=Integer.parseInt(sQty);

                      if (qty>0) {

                        for (int b=0;b<qty;b++) {

                          String newId = truck+""+Donation.getKey().getId();
                          int julianDay = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
                          java.util.Date now = new java.util.Date();
                          
                          if (errRow==row) rowClass="grid-results-error";
                          else rowClass="grid-results";
                          
                          %>
                          <form method="POST" action="<%=request.getContextPath()%>/arrivals">
                            <tr>
                              <td class="grid-RowCount" >
                                <%=row+1%>
                              </td>
                              <td class="<%=rowClass%>">
                                Donation
                              </td>
                              <td class="<%=rowClass%>" width="80">
                                <%=newId%>
                              </td>
                              <td class="<%=rowClass%>">
                                <input 
                                    name="itemName"
                                    value="<%=name.toUpperCase()%>"
                                    maxlength="30"
                                    size="30"
                                    class="entry" <%=disabled%> />
                              </td>
                              <td class="<%=rowClass%>">
                                1
                              </td>
                              <td class="<%=rowClass%>">
                                <%
                                ArrayList ddl = (ArrayList)session.getAttribute("ddlItemTypes");
                                %>
                                <select name="itemType" class="ddlentry" <%=disabled%>>
                                  <option value="">
                                  </option>
                                  <%
                                  if (ddl!=null) {
                                    for (int j=0;j<ddl.size();j++) {
                                      %>
                                      <option value="<%=ddl.get(j)%>" <% if (ddl.get(j).equals(name)) {%>selected<%}%>>

                                        <%=ddl.get(j)%>
                                      </option>
                                      <%
                                    }
                                  }
                                  %>
                                </select>
                              </td>
                              <td class="<%=rowClass%>">
                                <input 
                                    name="price"
                                    value=""
                                    size="7"
                                    maxlength="7"
                                    onkeypress="return isNumberKey(event)"
                                    class="entry" <%=disabled%> />
                              </td>
                              <td class="<%=rowClass%>">
                             
                                <input type="submit" name="action" value="Add To Inventory" class="buttonAction">
                                <input type="submit" name="action" value="Bric-N-Brac" class="buttonAction">
                                <input type="submit" name="action" value="Trash" class="buttonAction">
                                <input type="submit" name="action" value="DNR" class="buttonAction">
                                <input type="hidden" name="truck" value="<%=truck%>"/>
                                <input type="hidden" name="quantity" value="1"/>
                                <input type="hidden" name="itemName" value="<%=name.toUpperCase()%>"/>
                                <input type="hidden" name="itemNumber" value="<%=newId%>"/>
                                <input type="hidden" name="row" value="<%=row%>"/>
                                <input 
                                    type="hidden"
                                    name="donationKey"
                                    value="<%=KeyFactory.keyToString(Donation.getKey())%>"/>
                                <input 
                                    type="hidden"
                                    name="arrivalDate"
                                    value="<%=request.getAttribute("arrivalDate")%>"/>
                              </td>
                            </tr>


                          </form>
                          <%
                          } //b
                          row++;
                        }
                      }

                    }

                    if (row==0) {
                      %>
                      <tr>
                        <td class="results-bg" colspan="9">
                          no items found. All items have been added to inventory, discarded, or dispatch hasn't changed
                          the dispatch status.
                        </td>
                      </tr>
                      <%
                    }
                    %>
                  </table>


                </td>
              </tr>
            </table>
          </div>

          <br>

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

