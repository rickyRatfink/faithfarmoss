<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ page import="org.faithfarm.dataobjects.Person" %>
<%@ page import="org.w3c.dom.NodeList" %>
<%@ page import="org.w3c.dom.Element" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>

<jsp:include page="../includes/header.jsp"/>


<jsp:include page="../includes/menu/index.jsp" flush="true"/>

<%
String required = "<font size='-2' color='red'>*</font>";
Entity User = (Entity)request.getAttribute("User");
NodeList xmlUser=(NodeList)request.getAttribute("xmlUser");
%>
<!----Main Content--->

<script language="javascript">
function imposeMaxLength(event, object, MaxLen){
	var key = event.keyCode ? event.keyCode : event.which;
	if(key == 8){
	// 8 is the keycode for the backspace key
	return true;
	}else{
	return (object.value.length <= MaxLen);
	}
}
</script>

<td  valign="top">
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
          <table>
            <tr>
              <%
              if ("Y".equals(request.getParameter("edit"))) {
                %>
                <td class="text1">
                  Edit System User
                </td>
                <%
              } else {
                %>
                <td class="text1">
                  Create System User
                </td>
                <%
              }
              %>
            </tr>
          </table>
          <jsp:include page="../includes/errors.jsp" flush="true"/>
        </br>
      </br>
      <!----Search Form ------->

      <form method="POST" action="<%=request.getContextPath()%>/security" >
        <table width="100%" border=0 cellpadding=0 cellspacing=0>
          <tr>
            <td class="sectionTitle">
              System User
              <br />
            </td>
          </tr>
          <tr>
            <td >
              <table width="700" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
                <%
                for (int i=0;i<xmlUser.getLength();i++) {
                  Element propertyElement = (Element)xmlUser.item(i);
                  String name=propertyElement.getAttribute("name").toString();
                  String type=propertyElement.getAttribute("type").toString();
                  String edit=propertyElement.getAttribute("editable").toString();
                  String maxlength=propertyElement.getAttribute("maxlength").toString();
                  String input=propertyElement.getAttribute("input").toString();
                  String source=propertyElement.getAttribute("source").toString();
                  String isRequired=propertyElement.getAttribute("required").toString();
                  String defaultValue=propertyElement.getAttribute("default").toString();
                  String onkeypress=propertyElement.getAttribute("onkeypress").toString();
                  %>
                  <tr>
                    <td class="<% if (i%2==0) { %>edit-field-dark<%}else{%>edit-field-white<%}%>">
                      <%=propertyElement.getAttribute("display")%>
                      <%
                      if ("true".equals(isRequired)) {
                        %>
                        <%=required%>
                        <%
                      }
                      %>
                    </td>
                    <td class="<% if (i%2==0) { %>edit-value-dark<%}else{%>edit-value-white<%}%>">
                      <%
                      if ("Y".equals(edit)) {
                        if ("text".equals(input)) {
                          String value="";
                          if(User.getProperty(name)!=null)
                          	value=User.getProperty(name).toString();
                          %>
                          <input 
                              name="<%=name%>"
                              <%
                              if
                              ("".equals(value))
                              {
                              %>
                              value="<%=defaultValue%>"
                              <%}
                              else
                              {
                              %>
                              value="<%=value%>"
                              <%
                              }
                              %>
                              maxlength="<%=maxlength%>"
                              size="<%=maxlength%>"
                              <%
                              if
                              (onkeypress!=null&&!"".equals(onkeypress))
                              {
                              %>onkeypress="return isNumberKey(event)"<%
                              }
                              %>
                              class="entry"/>
                          <%
                        } 	else if ("calendar".equals(input)) {
                        	String value="";
                            if(User.getProperty(name)!=null)
                            	value=User.getProperty(name).toString();
                          %>
                          <input name="<%=name%>" class="tcal" value="<%=value%>" />
                          <%
                        }
                        else if ("ddl".equals(input)) {

                          ArrayList ddl = (ArrayList)session.getAttribute(source);
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
                                    (ddl.get(j).equals(User.getProperty(propertyElement.getAttribute("name").toString())))
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
                        }  else if ("textarea".equals(input)) {
                          String value=User.getProperty(name).toString();
                          %>
                          <textarea class="dispatch" name="<%=name%>" onkeypress="return imposeMaxLength(event, this, 1000);"><%=value%></textarea>
                          <%
                        }

                        else {
                          %>
                          <%=User.getProperty(propertyElement.getAttribute("name").toString())%>
                          <%
                        }
                        %>


                        <%
                      } else {
                        %>
                        <%=User.getProperty(propertyElement.getAttribute("name").toString())%>
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
          <tr>
            <td  height="60" valign="bottom" align="left">

              <% if (User.getKey().getId()!=0) { %>
                <input type="submit" name="action" value="Save Changes" class="buttonBlankLarge">
                <input type="hidden" name="userKey" value="<%=KeyFactory.keyToString(User.getKey())%>" />
              <% } else { %>
                <input type="submit" name="action" value="Save User" class="buttonMedium">
              <% } %>
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


