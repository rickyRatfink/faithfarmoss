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


<%
String required = "<font size='-2' color='red'>*</font>";
Entity Person = (Entity)request.getAttribute("Person");
Entity Address = (Entity)request.getAttribute("Address");
NodeList xmlPerson=(NodeList)request.getAttribute("xmlPerson");
NodeList xmlAddress=(NodeList)request.getAttribute("xmlAddress");
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
                Create/Edit Customer
              </td>
            </tr>
          </table>
          <br />
          <jsp:include page="../includes/errors.jsp" flush="true"/>
          <br/>
          <!----Search Form ------->
          <form method="POST" action="<%=request.getContextPath()%>/customer" >
            <table width="80%" border=0 cellpadding=0 cellspacing=0>

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
                        last name<%=required%>
                      </td>
                      <td class="edit-field-dark">
                        <input 
                            name="lastName"
                            value="<%=Person.getProperty("lastName")%>"
                            size="40"
                            maxlength="30"
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
                            class="entry"/>
                      </b>
                    </td>
                  </tr>
                  <tr>
                    <td class="edit-field-dark-small">
                      first name<%=required%>
                    </td>
                    <td class="edit-field-dark">
                      <b>
                        <input 
                            name="firstName"
                            value="<%=Person.getProperty("firstName")%>"
                            size="30"
                            maxlength="30"
                            class="entry"/>
                      </b>
                    </td>
                  </tr>
                  
                                  <tr>
                    <td class="edit-field-white-small">
                      Organization
                    </td>
                    <td class="edit-field-white">
                    		<%
                    		ArrayList ddl=new ArrayList();
                    		
                    		if(session.getAttribute("ddlOrg")!=null)
                    		    ddl = (ArrayList)session.getAttribute("ddlOrg");
                    		
                    		String value="";
                    		try {
                    			value=(String)Person.getProperty("organization");
                    		} catch (Exception e) {}
                    		 %>
                            <select name="organization" class="entry" >
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
                                      (ddl.get(j).equals(value))
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
                           

                    </td>
                  </tr>

                </table>
              </td>
            </tr>

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
                    
                    String name=propertyElement.getAttribute("name").toString();
                    String type=propertyElement.getAttribute("type").toString();
                    String edit=propertyElement.getAttribute("editable").toString();
                    String maxlength=propertyElement.getAttribute("maxlength").toString();
                    String input=propertyElement.getAttribute("input").toString();
                    String source=propertyElement.getAttribute("source").toString();
                    String requiredField=propertyElement.getAttribute("required").toString();
                    
                    System.out.println(name);
                    %>
                    <tr>
                      <td class="<% if (i%2==0) { %>edit-field-dark<%}else{%>edit-field-white<%}%>">
                        <%=propertyElement.getAttribute("display")%>
                        <% if ("true".equals(requiredField)) { %>
                        <%=required%>
                        <%}%>
                        </td>
                        <td class="<% if (i%2==0) { %>edit-value-dark<%}else{%>edit-value-white<%}%>">
                          <%
                          if ("text".equals(input)) {
                            %>
                            <input 
                                name="<%=name%>"
                                value="<%=Address.getProperty(propertyElement.getAttribute("name").toString())%>"
                                maxlength="<%=maxlength%>"
                                size="<%=maxlength%>"
                                class="entry"/>
                            <%
                          }
                          else if ("ddl".equals(input)) {
                            ddl = (ArrayList)session.getAttribute(source);
                            %>
                            <select name="<%=name%>" class="entry"  >
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
                              <!-- 
                              (
                              <input 
                                  onKeyPress="return isNumberKey(event)"
                                  value=""
                                  name="<%=name%>_areacode"
                                  maxlength="3"
                                  size="3"
                                  class="entry">







                              )
                              <input 
                                  onKeyPress="return isNumberKey(event)"
                                  value=""
                                  name="<%=name%>_first3"
                                  maxlength="3"
                                  size="3"
                                  class="entry">







                              &nbsp;-
                              <input 
                                  onKeyPress="return isNumberKey(event)"
                                  value=""
                                  name="<%=name%>_last4"
                                  maxlength="4"
                                  size="4"
                                  class="entry">

								-->


						(<input type="text" id="<%=name%>_areacode" name="<%=name%>_areacode" maxlength=3 size=3 onkeyup="moveOnMax(this,'<%=name%>_first3')" onKeyPress="return isNumberKey(event)" class="entry"/>)
						<input type="text" id="<%=name%>_first3" name="<%=name%>_first3" maxlength=3 size=3 onkeyup="moveOnMax(this,'<%=name%>_last4')" onKeyPress="return isNumberKey(event)" class="entry"/>-
						<input type="text" id="<%=name%>_last4" name="<%=name%>_last4"  maxlength=4 size=4 onKeyPress="return isNumberKey(event)" class="entry" />




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
                                  class="entry">







                              )
                              <input 
                                  onKeyPress="return isNumberKey(event)"
                                  value="<%=((String)Address.getProperty(name)).substring(5,8)%>"
                                  name="<%=name%>_first3"
                                  maxlength="3"
                                  size="3"
                                  class="entry">







                              &nbsp;-
                              <input 
                                  onKeyPress="return isNumberKey(event)"
                                  value="<%=((String)Address.getProperty(name)).substring(9,13)%>"
                                  name="<%=name%>_last4"
                                  maxlength="4"
                                  size="4"
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


                        </td>
                      </tr>
                      <%
                      } //end for
                      %>
                    </table>
                  </td>
                </tr>

                <tr>
                  <td  height="60" valign="bottom" align="center">
                    <%if (Person.getKey().getId()!=0) { %>
                      <input type="hidden" name="personKey" value="<%=KeyFactory.keyToString(Person.getKey())%>"/>
                      <input type="hidden" name="addressKey" value="<%=KeyFactory.keyToString(Address.getKey())%>"/>
                      <%
                    }
                    %>
                    <input type="submit" name="action" value="Save" class="buttonBlankSmall"/>

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



      </div>
    </table>
	</form>
    <jsp:include page="../includes/footer.jsp"/>


