<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ page import="org.w3c.dom.NodeList" %>
<%@ page import="org.w3c.dom.Element" %>
<%@ page import="java.util.ArrayList" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<%
String required = "<font size='-2' color='red'>*</font>";
Entity Person = (Entity)request.getAttribute("Person");
Entity Address = (Entity)request.getAttribute("Address");
NodeList xmlPerson=(NodeList)request.getAttribute("xmlPerson");
NodeList xmlAddress=(NodeList)request.getAttribute("xmlAddress");
%>


<jsp:include page="includes/header.jsp" flush="true"/>

	<div id="container">																																																																																																																																																																											
	  <div id="center" class="column">
	  	<div id="content">
			<p>
			<form method="POST" action="<%=request.getContextPath()%>/sfreg" >
            <table width="80%" border=0 cellpadding=0 cellspacing=0>
              <tr>
                <td class="instructions">
                  <b>Please enter your information below and click save to create your customer profile.</b>
                  <br /><br/>
                </td>
              </tr>
<%
	ArrayList messages = (ArrayList)request.getAttribute("messages");
	String password="", password1="";
	String username=(String)request.getAttribute("username");
	if (username==null) 
		username="";
	
	if (messages==null)
		messages = new ArrayList();
	
	if (messages.size()>0) {						
	%>
	<div class="errorMessage">
		<font color="#000000">Please correct the following errors before saving.</font><br>
		<% for (int i=0;i<messages.size();i++) { %>
		<img src="<%=request.getContextPath()%>/images/error.jpg"/>&nbsp;<%=messages.get(i)%><br>
		<%
		if (((String)messages.get(i)).indexOf("WARNING")>-1) { %>
			<input type="hidden" name="overwrite" value="Y"/>
		<% }
		} %>
	</div>
	<br/>
<% } %>


            
              <tr>
                <td >
                  <table width="500" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
                   <tr>
                    <td height="25" valign="bottom" class="instructions">
                      Select a username and password.
                    </td>
                  </tr>
                  <tr>
                    <td height="25" valign="bottom" class="edit-field-white-small">
                      <b>username</b>&nbsp;<i>(must be at least 8 characters using letters and/or numbers)</i>
                    </td>
                  </tr>
                  <tr>
                    <td class="edit-field-white">
                      <b>
                        <input 
                            name="username"
                            value="<%=username%>"
                            size="20"
                            maxlength="20"
                            class="entry"/>
                      </b>
                    </td>
                  </tr>
                  <tr>
                    <td height="25" valign="bottom" class="edit-field-white-small">
                      <b>password</b>&nbsp;<i>(must be at least 6 characters using letters and/or numbers)</i>
                    </td>
                  </tr>
                  <tr>
                    <td class="edit-field-white">
                      <b>
                        <input type="password" 
                            name="password"
                            value=""
                            size="20"
                            maxlength="20"
                            class="entry"/>
                      </b>
                    </td>
                  </tr>
                  <tr>
                    <td height="25" valign="bottom" class="edit-field-white-small">
                      <b>confirm password</b>
                    </td>
                  </tr>
                  <tr>
                    <td class="edit-field-white">
                      <b>
                        <input type="password"
                            name="password1"
                            value=""
                            size="20"
                            maxlength="20"
                            class="entry"/>
                      </b>
                      </br></br>
                    </td>
                  </tr>
                    <tr >
                      <td height="25" valign="bottom" class="edit-field-dark-small">
                        last name<%=required%>
                      </td>
                     </tr>
                     <tr>
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
                    <td height="25" valign="bottom" class="edit-field-white-small">
                      middle initial
                    </td>
                  </tr>
                  <tr>
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
                    <td height="25" valign="bottom" class="edit-field-dark-small">
                      first name<%=required%>
                    </td>
                   </tr>
                   <tr>
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

                </table>
              </td>
            </tr>

            <!--
              -------------------Address Information
              ---------------------------------------------------------------------
            -->
           
            <tr>
              <td>
                <table width="500" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
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
                    %>
                    <tr>
                      <td height="25" valign="bottom" class="<% if (i%2==0) { %>edit-field-dark<%}else{%>edit-field-white<%}%>">
                        <%=propertyElement.getAttribute("display")%>
                        <% if ("true".equals(requiredField)) { %>
                        <%=required%>
                        <%}%>
                        </td>
                     </tr>
                     <tr>
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
                            ArrayList ddl = (ArrayList)session.getAttribute(source);
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
                    <br/><br/>
                    <input type="submit" name="action" value="Save"/>
                    <input type="reset" name="action" value="Clear"/>
                  </td>
                </tr>
                
                </table>
                
                </form>
			</p>			
		</div>
	  </div>


<jsp:include page="includes/categories.jsp" flush="true"/>
<jsp:include page="includes/right.jsp?pg=register" flush="true"/>

</div>
	
	<jsp:include page="includes/footer.jsp" flush="true"/>
	

