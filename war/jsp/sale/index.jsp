<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ page import="org.w3c.dom.NodeList" %>
<%@ page import="org.w3c.dom.Element" %>
<%@ page import="java.util.ArrayList" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>

<jsp:include page="../includes/header.jsp"/>

<jsp:include page="../includes/menu/index.jsp" flush="true"/>

<script language="javascript">
  function printPage()
  {
    window.print();
  }

  function disableFields() {
    var count = document.forms[0].elements.length-1;
    var i;
    for (i=15;i<count;i++) {
      document.forms[0].elements[i].disabled=true;
    }
  }

  function enableFields() {
    var count = document.forms[0].elements.length-2;
    var i;
    for (i=5;i<count;i++) {
      document.forms[0].elements[i].enabled=true;
    }
  }


  function toggle() {
    var ele = document.getElementById("toggleText");
    var text = document.getElementById("displayText");

    if(ele.style.display == "block") {
      ele.style.display = "none";
      text.innerHTML = "show";

    }
    else {
      ele.style.display = "block";
      text.innerHTML = "hide";

    }
  }

</script>

<%
String required = "<font size='-2' color='red'>*</font>";
Entity Person = (Entity)session.getAttribute("Person");
Entity Address = (Entity)session.getAttribute("Address");
Entity SalesOrder = (Entity)session.getAttribute("SalesOrder");
Entity SalesDispatch = (Entity)session.getAttribute("SalesDispatch");
NodeList xmlPerson=(NodeList)session.getAttribute("xmlPerson");
NodeList xmlAddress=(NodeList)session.getAttribute("xmlAddress");
NodeList xmlSalesOrder=(NodeList)session.getAttribute("xmlSalesOrder");
NodeList xmlSalesDispatch=(NodeList)session.getAttribute("xmlSalesDispatch");
String invLocation=(String)session.getAttribute("INVENTORY_LOCATION");
if (invLocation==null) invLocation="";

String disabled="";

if (Person.getKey().getId()!=0)
disabled="disabled=true";


String delivery="No";
%>

<!----Main Content--->
<td>
  <table width="820" border="0" cellpadding="0" cellspacing="0">
    <tr>
      <td>
        <br>
        <!--img src="<%=request.getContextPath()%>/images/header_top.png"-->
      </td>
    </tr>

    <!---------------------Person Information ----------------------------------------------------------------------->

    <tr>
      <td class="content-body">
        <div class="content">
          <table>
            <tr>
              <td class="text1">
                Current Sales/Deliveries 
                <% if (session.getAttribute("INVENTORY_LOCATION")!=null) { %>
                	(<%=session.getAttribute("INVENTORY_LOCATION")%>)
                <% } %>
              </td>
            </tr>
          </table>
          <br />
          <jsp:include page="../includes/errors.jsp" flush="true"/>
          <br/>
          <!----Search Form ------->
          <form method="POST" action="<%=request.getContextPath()%>/sales" >
            <table width="80%" border=0 cellpadding=0 cellspacing=0>

              <%
              if (!"Y".equals(request.getParameter("edit"))) {
                %>
                <tr>
                  <td class="dispatchNameSearch">
                    <table width="540" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">

                      <tr>
                        <td colspan="5" class="text5">
                          Enter a customer's name and click search to retrieve the customer's information.
                        </td>
                      </tr>

                      <tr>
                        <td width="100" class="field">
                          First Name
                          <%=required%>
                        </td>
                        <td width="50" class="field">
                          Middle Initial
                        </td>
                        <td width="13" class="field">
                          Last Name
                          <%=required%>
                        </td>
                        <td width="30">
                        </td>
                      </tr>


                      <tr>
                        <td>
                          <input 
                              name="firstName"
                              value="<%=Person.getProperty("firstName")%>"
                              maxlength="20"
                              size="20"
                              class="entry">







                        </td>
                        <td>
                          <input 
                              name="middleInitial"
                              value="<%=Person.getProperty("middleInitial")%>"
                              maxlength="1"
                              size="1"
                              class="entry">







                        </td>
                        <td>
                          <input 
                              name="lastName"
                              value="<%=Person.getProperty("lastName")%>"maxlength="30"
                              size="30"
                              class="entry">







                        </td>

                        <td>
                          <input type="submit" name="action" value="Search" class="buttonBlank">
                        </td>
                      </tr>



                    </table>

                  </td>
                </tr>
                <%
              } else {
                %>
                <tr>
                  <td class="sectionTitle">
                    Personal Information
                    <br />
                  </td>
                </tr>
                <tr>
                  <td>
                    <table width="700" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
                      <tr>
                        <td width="40" class="edit-field-dark-small">
                          first name
                        </td>
                        <td class="edit-field-dark">
                          <input 
                              name="firstName"
                              value="<%=Person.getProperty("firstName")%>"
                              size="30"
                              maxlength="30"
                              <%=disabled%>
                              class="entry"/>
                        </b>
                      </td>
                    </tr>
                    <tr>
                      <td class="edit-field-white-small">
                        middle initial
                      </td>
                      <td class="edit-field-white">
                        <b>
                          <input 
                              name="middleInitial"
                              value="<%=Person.getProperty("middleInitial")%>"
                              size="2"
                              maxlength="2"
                              <%=disabled%>
                              class="entry"/>
                        </b>
                      </td>
                    </tr>
                    <tr>
                      <td class="edit-field-dark-small">
                        last name
                      </td>
                      <td class="edit-field-dark">
                        <b>
                          <input 
                              name="lastName"
                              value="<%=Person.getProperty("lastName")%>"
                              size="30"
                              maxlength="30"
                              <%=disabled%>
                              class="entry"/>
                        </b>
                      </td>
                    </tr>

                  </table>
                </td>
              </tr>
              <%
            }
            %>
            <!--
              -------------------Address Information
              ---------------------------------------------------------------------
            -->
            <tr>
              <td height="15">
              </td>
            </tr>
            <tr>
              <td class="sectionTitle">
                Address Information
                <br />
              </td>
            </tr>
            <tr>
              <td>
                <table width="700" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
                  <%
                  for (int i=0;i<xmlAddress.getLength();i++) {
                    Element propertyElement = (Element)xmlAddress.item(i);
                    %>
                    <tr>
                      <td class="<% if (i%2==0) { %>edit-field-dark<%}else{%>edit-field-white<%}%>">
                        <%=propertyElement.getAttribute("display")%>
                      </td>
                      <td class="<% if (i%2==0) { %>edit-value-dark<%}else{%>edit-value-white<%}%>">
                        <%
                        String name=propertyElement.getAttribute("name").toString();
                        
                        String type=propertyElement.getAttribute("type").toString();
                        String edit=propertyElement.getAttribute("editable").toString();
                        String maxlength=propertyElement.getAttribute("maxlength").toString();
                        String input=propertyElement.getAttribute("input").toString();
                        String source=propertyElement.getAttribute("source").toString();

                        if ("Y".equals(edit)) {

                          if ("text".equals(input)) {
                            %>
                            <input 
                                name="<%=name%>"
                                value="<%=Address.getProperty(propertyElement.getAttribute("name").toString())%>"
                                maxlength="<%=maxlength%>"
                                size="<%=maxlength%>"
                                <%=disabled%>
                                class="entry"/>
                            <%
                          }
                          else if ("ddl".equals(input)) {
                            ArrayList ddl = (ArrayList)session.getAttribute(source);
                            %>
                            <select name="<%=name%>" class="entry" <%=disabled%> >
                              <option value="">
                              </option>
                              <%
                              if (ddl!=null) {
                                for (int j=0;j<ddl.size();j++) {
                                  %>
                                  <option 
                                      value="<%=ddl.get(j)%>"
                                      <%
                                      if
                                      (ddl.get(j).equals(Address.getProperty(propertyElement.getAttribute("name").toString())))
                                      {%>selected<%}%>>







                                    <%=ddl.get(j)%>
                                  </option>
                                  <%
                                }
                                %>
                                <%
                              }
                              %>
                            </select>
                            <%
                          }

                          else if ("telephone".equals(input)) {
                            %>
                            <%
                            if (((String)Address.getProperty(name)).length()!=13) {
                              %>
                              (
                              <input 
                                  onKeyPress="return isNumberKey(event)"
                                  value=""
                                  name="<%=name%>_areacode"
                                  maxlength="3"
                                  size="3"
                                  <%=disabled%>
                                  class="entry">







                              )
                              <input 
                                  onKeyPress="return isNumberKey(event)"
                                  value=""
                                  name="<%=name%>_first3"
                                  maxlength="3"
                                  size="3"
                                  <%=disabled%>
                                  class="entry">







                              &nbsp;-
                              <input 
                                  onKeyPress="return isNumberKey(event)"
                                  value=""
                                  name="<%=name%>_last4"
                                  maxlength="4"
                                  size="4"
                                  <%=disabled%>
                                  class="entry">







                              <%
                            } else {
                              %>
                              (
                              <input 
                                  onKeyPress="return isNumberKey(event)"
                                  value="<%=((String)Address.getProperty(name)).substring(1,4)%>"
                                  name="<%=name%>_areacode"
                                  maxlength="3"
                                  size="3"
                                  <%=disabled%>
                                  class="entry">







                              )
                              <input 
                                  onKeyPress="return isNumberKey(event)"
                                  value="<%=((String)Address.getProperty(name)).substring(5,8)%>"
                                  name="<%=name%>_first3"
                                  maxlength="3"
                                  size="3"
                                  <%=disabled%>
                                  class="entry">







                              &nbsp;-
                              <input 
                                  onKeyPress="return isNumberKey(event)"
                                  value="<%=((String)Address.getProperty(name)).substring(9,13)%>"
                                  name="<%=name%>_last4"
                                  maxlength="4"
                                  size="4"
                                  <%=disabled%>
                                  class="entry">







                              <%
                            }
                            %>

                            <%
                          } else {
                            %>
                            <%=Address.getProperty(propertyElement.getAttribute("name").toString())%>
                            <%
                          } 
                          %>


                          <%
                        } else {
                          %>
                          <%=Address.getProperty(propertyElement.getAttribute("name").toString())%>
                          <%
                        }
                        %>
                      </td>
                    </tr>
                    <%
                  }
                  %>
                </table>
              </td>
            </tr>

            <!--
              -------------------Sales Order Information
              ---------------------------------------------------------------------
            -->
            <tr>
              <td height="15" valign="middle" class="instructions">
                <br/>
              </br>
              <b>
                <i>
                  Enter the item number, quantity, and location then click the add button. Repeat this for each item to
                  be added to the sales form.
                </b>
              </i>
              <jsp:include page="validation.jsp" flush="true"/>
              <br/>
              <br/>
            </td>
          </tr>
          <tr>
            <td class="sectionTitle">
              New Sale
              <br />
            </td>
          </tr>
          <tr>
            <td>


              <!-----------------------BEGIN ORDER ---------------->
              <table width="700" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">

                <tr>
                  <td width="130" class="results-heading">
                    ITEM#
                  </td>
                  <td width="80" class="results-heading">
                    QUANTITY
                  </td>
                  <td width="80" class="results-heading">
                    LOCATION
                  </td>
                  <% if (!invLocation.contains("As-Is")) { %>
                  <td width="140" class="results-heading">
                    VENDOR
                  </td>
                  <% } %>
                  <td width="250" class="results-heading">
                    DESCRIPTION
                  </td>
                  <td width="100" class="results-heading">
                    PRICE
                  </td>
                </tr>

                <%
                double total=0.00;
                double discount=0.00;

				String sDiscount=(String)SalesOrder.getProperty("discount");
                 
                for (int i=0;i<10;i++) {

                  String itemNumber="";
                  String itemName="";
                  String itemVendor="";
                  String location="";
                  String price="0.00";
                  String qty="0";

                  itemNumber=(String)SalesOrder.getProperty("itemNumber"+i);
                  itemName=(String)SalesOrder.getProperty("itemName"+i);
                  itemVendor=(String)SalesOrder.getProperty("vendor"+i);
                  location=(String)SalesOrder.getProperty("location"+i);
                  price=(String)SalesOrder.getProperty("price"+i);
                  
                  if (SalesOrder.getProperty("quantity"+i)==null||"".equals(SalesOrder.getProperty("quantity"+i)))
                  qty="0";
                  else
                  qty=(String)SalesOrder.getProperty("quantity"+i);

                  if (itemNumber==null) itemNumber="";
                  if (itemName==null) itemName="";
                  if (location==null) location="";
                  if (qty==null||"".equals(qty)) qty="";
                  if (price==null||"".equals(price)) price="0.00";
                  total+= (Double.valueOf(price).doubleValue())*(Integer.valueOf(qty).intValue());
                  %>
                  <tr>
                    <td class="order-item-bg">
                      <table width="100%" cellpadding="0" cellspacing="0" border="0">
                        <tr>
                          <td valign="bottom">
                            <input 
                                name="itemNumber<%=i%>"
                                size="10"
                                maxlength="10"
                                value="<%=itemNumber%>"
                                class="entry"
                            >





                          </td>
                          <td valign="center">
                            <input type="submit" name="action" value="Find Item" class="buttonFilterWhite">
                            <input type="submit" name="action" value="Delete Item" class="buttonDelete">
                          </td>
                        </tr>
                      </table>


                    </td>

                    <td class="order-bg" >
                      <input name="quantity<%=i%>" size="2" maxlength="2" value="<%=qty%>" class="entry" >
                    </td>
                   
                    <% if (invLocation.contains("As-Is")) { %>
                    	<input type="hidden" name="location" value="1/F"/>
                    <% } else { %>
                    <td class="order-bg" >
                     <%                      
                      ArrayList ddl = (ArrayList)session.getAttribute("ddlInventoryLocation");
                      %>
                      <select name="location<%=i%>" class="entry">
                        <option value="">
                        </option>
                        <%
                        if (ddl!=null) {
                          if (SalesOrder.getProperty("location"+i)!=null)
                          location=SalesOrder.getProperty("location"+i).toString();
                          for (int j=0;j<ddl.size();j++) {
                            %>
                            <option value="<%=ddl.get(j)%>" <% if (ddl.get(j).equals(location)) {%>selected<%}%>>
                              <%=ddl.get(j)%>
                            </option>
                            <%
                          }
                          %>
                          <%
                        }
                        %>
                      </select>
                    </td>
                    <% } //end role if %>
                  </td>
                  <td class="order-item-bg">
                    <%
                    if (itemVendor==null) itemVendor="";
                    %>
                    <%=itemVendor.toUpperCase()%>
                    <input type="hidden" name="vendor<%=i%>" value="<%=itemVendor.toUpperCase()%>"/>
                  </td>
                  <td class="order-item-bg">
                    <%
                    if (itemName==null) itemName="";
                    %>
                    <%=itemName.toUpperCase()%>
                    <input type="hidden" name="itemName<%=i%>" value="<%=itemName.toUpperCase()%>"/>
                  </td>
                  <td class="order-item-bg">
                    <fmt:formatNumber value="<%=price%>" type="currency" />
                    <input type="hidden" name="price<%=i%>" value="<%=price%>"/>
                  </td>
                </tr>

                <%
              }
              %>

              <tr>
                <td colspan="6" height="25" valign="top" align="right">

                </td>
              </tr>


 

             <tr>
                <td colspan="5" align="right" class="total">
                  Discount
                </td>
                <td align="right" class="total">
                  <% 
                 
                  if (sDiscount==null||sDiscount.equals("")) sDiscount="0";
                  %>
                  <select name="discount" class="entry">
                    <%
                    for (int i=0;i<100;i++) {
                      %>
                      <option value="<%=i%>"<% if (sDiscount.equals(i+"")) { %>selected<%}%> >
                        <%=i%>
                        %
                      </option>
                      <%
                    }
                    %>
                    <input type="submit" name="action" value="Find Item" class="buttonCalc"/>
                  </td>
                </tr>

            <tr>
				 <%
				 double iDiscount=Integer.parseInt(sDiscount);
				 double discAmount = total*(iDiscount/100);
				 total-=discAmount;
				 %>
                  <td colspan="5" align="right" class="total">
                   Discount Amount
                  </td>
                  <td align="right" class="total">
                    <fmt:formatNumber value="<%=discAmount%>" type="currency" />
                    <input type="hidden" name="discount" value="<%=discAmount%>"/>
                  </td>
                </tr>
      
                <tr>

                  <td colspan="5" align="right" class="total">
                    Sub-Total
                  </td>
                  <td align="right" class="total">
                    <fmt:formatNumber value="<%=total%>" type="currency" />
                    <input type="hidden" name="subtotal" value="<%=total%>"/>
                  </td>
                </tr>
                <tr>

                  <td colspan="5" align="right" class="total">
                    Tax (6%)
                  </td>
                  <td align="right" class="total">
                    <fmt:formatNumber value="<%=total*.06%>" type="currency" />
                    <input type="hidden" name="tax" value="<%=total*.06%>"/>
                  </td>
                </tr>
                <tr>

                  <td colspan="5" align="right" class="total">
                    <b>
                      Total
                    </b>
                  </td>
                  <td align="right" class="total">
                    <b>
                      <fmt:formatNumber value="<%=total+(total*.06)%>" type="currency" />
                      <input type="hidden" name="total" value="<%=total%>"/>
                    </b>
                  </td>
                </tr>

                <tr>

                  <td colspan="6" valign="center">
                    <i>
                      Order Status
                      <%=required%>
                    </i>
                    <%
                    ArrayList ddl = (ArrayList)session.getAttribute("ddlSalesStatus");
                    %>
                    <select name="salesOrderStatus" class="entry">
                      <option value="">
                      </option>
                      <%
                      if (ddl!=null) {
                        String status="Pending";
                        if (SalesOrder.getProperty("status")!=null)
                        status=SalesOrder.getProperty("status").toString();


                        for (int j=0;j<ddl.size();j++) {
                          %>
                          <option value="<%=ddl.get(j)%>" <% if (ddl.get(j).equals(status)) {%>selected<%}%>>


                            <%=ddl.get(j)%>
                          </option>
                          <%
                        }
                        %>
                        <%
                      }
                      %>
                    </select>
                  </td>
                </tr>
                <tr>
                  <td colspan="6" height="35" align="left">
                  </br>
                  <i>
                    Does this sale require delivery?
                  </i>
                  <%
                  ddl = (ArrayList)session.getAttribute("ddlYesNo");
                  %>
                  <select name="delivery" class="entry" onChange="javascript:toggle();">
                    <%
                    if (ddl!=null) {

                      if (SalesOrder.getProperty("delivery")!=null)
                      delivery=SalesOrder.getProperty("delivery").toString();
                      if ("".equals(delivery)) delivery="No";
                      for (int j=0;j<ddl.size();j++) {
                        %>
                        <option value="<%=ddl.get(j)%>" <% if (ddl.get(j).equals(delivery)) {%>selected<%}%>>

                          <%=ddl.get(j)%>
                        </option>
                        <%
                      }
                      %>
                      <%
                    }
                    %>
                  </td>
                </tr>
                <tr>

                  <td colspan="6" height="35" valign="center">
                    <i>
                      How did you hear about us?
                    </i>
                    <%
                    ddl = (ArrayList)session.getAttribute("ddlHowDidYouHearAboutUs");
                    %>
                    <select name="source" class="entry">
                      <option value="">
                      </option>
                      <%
                      if (ddl!=null) {
                        String farmSource="";
                        if (SalesOrder.getProperty("farmSource")!=null)
                        farmSource=SalesOrder.getProperty("farmSource").toString();

                        for (int j=0;j<ddl.size();j++) {
                          %>
                          <option value="<%=ddl.get(j)%>" <% if (ddl.get(j).equals(farmSource)) {%>selected<%}%>>


                            <%=ddl.get(j)%>
                          </option>
                          <%
                        }
                        %>
                        <%
                      }
                      %>
                    </select>
                  </td>


                </tr>


              </table>


              <!------------------------END ORDER--------------------- -->


              <!--
                -------------------SalesDispatch Information
                ---------------------------------------------------------------------
              -->



              <tr>
                <td height="15">
                </td>
              </tr>

              <tr>
                <td>
                  <div id="toggleText" style="display: none">
                    <table width="700" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
                      <tr>
                        <td colspan="2" class="sectionTitle">
                          Delivery Information
                        </div>
                        <br />
                      </td>
                      <tr>
                        <%
                        for (int i=0;i<xmlSalesDispatch.getLength();i++) {
                          Element propertyElement = (Element)xmlSalesDispatch.item(i);
                          String name=propertyElement.getAttribute("name").toString();
                          String type=propertyElement.getAttribute("type").toString();
                          String edit=propertyElement.getAttribute("editable").toString();
                          String maxlength=propertyElement.getAttribute("maxlength").toString();
                          String input=propertyElement.getAttribute("input").toString();
                          String source=propertyElement.getAttribute("source").toString();
                          String requiredField=propertyElement.getAttribute("required").toString();
                          %>
                          <tr>
                            <td class="<% if (i%2==0) { %>edit-field-dark<%}else{%>edit-field-white<%}%>" >
                              <%=propertyElement.getAttribute("display")%>
                              <%
                              if ("true".equals(requiredField)) {
                                %>
                                <%=required%>
                                <%}%>
                                </td>
                                <td class="<% if (i%2==0) { %>edit-value-dark<%}else{%>edit-value-white<%}%>" >
                                  <%
                                  if ("Y".equals(edit)) {

                                    if ("text".equals(input)) {
                                      %>
                                      <input 
                                          name="<%=name%>"
                                          value="<%=SalesDispatch.getProperty(propertyElement.getAttribute("name").toString())%>"
                                          maxlength="<%=maxlength%>"
                                          size="<%=maxlength%>"
                                          class="entry"/>
                                      <%
                                    }
                                    if ("calendar".equals(input)) {
                                      %>
                                      <input 
                                          name="<%=name%>"
                                          class="tcal"
                                          value="<%=SalesDispatch.getProperty(propertyElement.getAttribute("name").toString())%>"
                                      />
                                      <%
                                    }
                                    else if ("ddl".equals(input)) {
                                      ddl = (ArrayList)session.getAttribute(source);
                                      %>
                                      <select name="<%=name%>" class="entry">
                                        <option value="">
                                        </option>
                                        <%
                                        if (ddl!=null) {
                                          for (int j=0;j<ddl.size();j++) {
                                            %>
                                            <option 
                                                value="<%=ddl.get(j)%>"
                                                <%
                                                if
                                                (ddl.get(j).equals(SalesDispatch.getProperty(propertyElement.getAttribute("name").toString())))
                                                {%>selected<%}%>>







                                              <%=ddl.get(j)%>
                                            </option>
                                            <%
                                          }
                                          %>
                                          <%
                                        }
                                        %>
                                      </select>
                                      <%
                                    } else if ("textarea".equals(input)) {
                                      %>
                                      <textarea name="<%=name%>" class="SalesOrder">
                                        <%=SalesDispatch.getProperty(propertyElement.getAttribute("name").toString())%>
                                      </textarea>
                                      <%
                                    }

                                  } else {
                                    %>
                                    <%=SalesDispatch.getProperty(propertyElement.getAttribute("name").toString())%>
                                    <%
                                  }
                                  %>
                                </td>
                              </tr>

                              <%}%>
                                <tr>
                                  <td height="30" colspan="2" valign="center">
                                    <input 
                                        type="checkbox"
                                        name="acceptTermsPolicies"
                                        value="Yes"
                                        <%
                                        if
                                        ("Yes".equals((String)SalesOrder.getProperty("acceptTermsPolicies")))
                                        {%>checked<%}%>/>
                                    <i>
                                      I acknowledge that I have read Faith Farm's delivery terms and policies to the
                                      customer.
                                    </i>
                                  </td>
                                </tr>
                              </table>
                            </td>
                          </tr>


                          <tr>

                          </div>
                          <!-- end of Toggle Div -->
                          <td  height="60" valign="bottom" align="center">
                            <%
                            if (Person.getKey().getId()!=0) {
                              %>
                              <input 
                                  type="hidden"
                                  name="personKey"
                                  value="<%=KeyFactory.keyToString(Person.getKey())%>"/>
                              <%
                            }
                            %>
                            <%if (SalesOrder.getKey().getId()==0) { %>
                              <input type="submit" name="action" value="Save" class="buttonBlankSmall"/>
                              <!-- <input type="submit" name="action" value="Save & Print" class="buttonBlankLarge"/>-->
                              <input type="hidden" name="edit" value="Y"/>
                              <%
                            } else {
                              %>
                              <input 
                                  type="hidden"
                                  name="salesOrderKey"
                                  value="<%=KeyFactory.keyToString(SalesOrder.getKey())%>"/>
                              <%if (SalesDispatch.getKey().getId()!=0) { %>
                                <input 
                                    type="hidden"
                                    name="salesDispatchKey"
                                    value="<%=KeyFactory.keyToString(SalesDispatch.getKey())%>"/>
                                <%
                              }
                              %>
                              <input type="submit" name="action" value="Save Changes" class="buttonBlankMedium"/>
                              <!--
                                <input type="submit" name="action" value="Save Changes & Print"
                                class="buttonBlankLarge"/>
                              -->
                              <%
                            }
                            %>
                          </td>
                        </tr>
                        <tr>
                          <td>
                            <!--img src="<%=request.getContextPath()%>/images/header_btm.png"-->
                          </td>
                        </tr>
                      </table>
                      <tr>
                        <td>
                          <!--img src="<%=request.getContextPath()%>/images/header_btm.png"-->
                        </td>
                      </tr>
                    </table>
                  </td>
                </tr>

                <%
                if (!"Y".equals(request.getParameter("edit"))) {
                  %>
                  <script language="javascript">
                    disableFields();
                  </script>
                  <%
                }
                %>
                <!--
                  <script language="javascript"> <% if (Person.getKey().getId()==0) { %> disableFields(); <% } else { %>
                  enableFields(); <% } %> </script> -
                -->

              </div>
            </table>

            <jsp:include page="../includes/footer.jsp"/>

            <%
            if ("Yes".equals(delivery)) {
              %>
              <script language="javascript">
                toggle();
              </script>
              <%
            }
            %>
