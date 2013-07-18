<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreService" %>
<%@ page import="com.google.appengine.api.datastore.Query" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ page import="java.util.ArrayList" %>

<jsp:include page="../includes/header.jsp"/>


<jsp:include page="../includes/menu/index.jsp" flush="true"/>

<%
String required = "<font size='-2' color='red'>*</font>";


ArrayList locations = (ArrayList) session.getAttribute("locations");
if (locations==null)
locations = new ArrayList();

ArrayList transactions = (ArrayList) session.getAttribute("transactions");
if (transactions==null)
transactions = new ArrayList();


Entity e = (Entity)request.getAttribute("Item");

if (e.getProperty("qtyTagged")==null)
	e.setProperty("qtyTagged","0");
%>

<script language="javascript">
  function incrementQty1( ) {
    qty=document.forms['0'].elements['2'].value;
    qty++;
    document.forms['0'].elements['2'].value = qty;
  }

  function decrementQty1( ) {
    qty=document.forms['0'].elements['2'].value;
    if (qty>0)
    qty--;
    document.forms['0'].elements['2'].value = qty;
  }
  function incrementQty2( ) {
    qty=document.forms['0'].elements['3'].value;
    qty++;
    document.forms['0'].elements['3'].value = qty;
  }

  function decrementQty2( ) {
    qty=document.forms['0'].elements['3'].value;
    if (qty>0)
    qty--;
    document.forms['0'].elements['3'].value = qty;
  }
  
   function incrementQty3( ) {
    qty=document.forms['0'].elements['4'].value;
    qty++;
    document.forms['0'].elements['4'].value = qty;
  }

  function decrementQty3( ) {
    qty=document.forms['0'].elements['4'].value;
    if (qty>0)
    qty--;
    document.forms['0'].elements['4'].value = qty;
  }
  
</script>

<!----Main Content--->
<td valign="top" valign="top">
  <table width="820" border="0" cellpadding="0" cellspacing="0">
    <tr>
      <td>
        <br>
        <!--img src="<%=request.getContextPath()%>/images/header_top.png"-->
      </td>
    </tr>
    <tr>
      <td class="content-body">
        <div class="content">
          <table width="920" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
            <tr>
              <td class="text1">
                Edit Item Levels
              </td>
            </tr>
          </table>
          <br>


          <jsp:include page="../includes/errors.jsp" flush="true"/>

          <!----Search Form ------->

          <form 
              method="POST"
              action="<%=request.getContextPath()%>/item">
            

            <table width="90%" border=0 cellpadding=0 cellspacing=0>

              <tr>
                <td >
                  <table width="100%" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">


                    <tr>
                      <td class="field" width="80">
                        Item Number
                      </td>
                      <td class="field" width="180">
                        Item Name
                      </td>
                      <td class="field" width="140">
                        Warehouse Quantity
                      </td>
                      <td class="field">
                        Floor Quantity
                      </td>
                      <td class="field">
                        Tagged Quantity
                      </td>
                    </tr>

                    <tr>
                      <td>
                        <input 
                            name="itemNumber"
                            value="<%=e.getProperty("itemNumber")%>"
                            maxlength="10"
                            size="10"
                            class="entry"
                            disabled>

                      </td>
                      <td>
                        <input 
                            name="price"
                            value="<%=e.getProperty("itemName")%>"
                            onkeypress="return isNumberKey(event)"
                            maxlength="30"
                            size="30"
                            class="entry"
                            disabled>

                      </td>
                      <td>
                        <table>
                          <tr>
                            <td>
                              <input 
                                  id="qty"
                                  onkeypress="return isNumberKey(event)"
                                  value="<%=e.getProperty("qtyWarehouse")%>"
                                  name="qtyWarehouse"
                                   maxlength="3"
                                  size="3"
                                  class="entry">

                            </td>
                            <td>
                              <a href="#" onClick="javascript:incrementQty1();">
                                <img src="<%=request.getContextPath()%>/images/buttons/up.jpg"  border="0">
                              </a>
                              <a href="#" onClick="javascript:decrementQty1();">
                                <img src="<%=request.getContextPath()%>/images/buttons/down.jpg" border="0">
                              </a>
                            </td>
                            
                          </tr>
                        </table>
                      </td>
                      <td>
                        <table>
                          <tr>
                            <td>
                              <input 
                                  onkeypress="return isNumberKey(event)"
                                  name="qtyFloor"
                                  value="<%=e.getProperty("qtyFloor")%>"
                                  maxlength="3"
                                  size="3"
                                  class="entry">

                            </td>
                          </td>
                          <td>
                            <a href="#" onClick="javascript:incrementQty2();">
                              <img src="<%=request.getContextPath()%>/images/buttons/up.jpg">
                            </a>
                            <a href="#" onClick="javascript:decrementQty2();">
                              <img src="<%=request.getContextPath()%>/images/buttons/down.jpg">
                            </a>
                          </td>
                        </tr>
                      </table>
                    </td>
                                         <td>
                        <table>
                          <tr>
                            <td>
                              <input 
                                  onkeypress="return isNumberKey(event)"
                                  name="qtyTagged"
                                  value="<%=e.getProperty("qtyTagged")%>"
                                  maxlength="3"
                                  size="3"
                                  class="entry">

                            </td>
                          </td>
                          <td>
                            <a href="#" onClick="javascript:incrementQty3();">
                              <img src="<%=request.getContextPath()%>/images/buttons/up.jpg">
                            </a>
                            <a href="#" onClick="javascript:decrementQty3();">
                              <img src="<%=request.getContextPath()%>/images/buttons/down.jpg">
                            </a>
                          </td>
                        </tr>
                      </table>
                    </td>
                  </tr>



                </table>



              </td>
            </tr>



            <tr>
              <td  height="60" valign="bottom" align="left">
                <input type="submit" name="action" value="Save Levels" class="buttonActionLarge">
                <input type="hidden" name="itemKey" value="<%=KeyFactory.keyToString(e.getKey())%>" />
              </td>
            </tr>

          </table>
       </form>

        <!----End Form----------->
      </div>



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


