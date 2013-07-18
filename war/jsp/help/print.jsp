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


<%
String required = "<font size='-2' color='red'>*</font>";
Entity Ticket = (Entity)request.getAttribute("HelpDeskTicket");
NodeList xmlTicket=(NodeList)request.getAttribute("xmlHelpDeskTicket");
%>
<!----Main Content--->



<td  valign="top">
  <table width="820" border="0" cellpadding="0" cellspacing="0">
    <tr>
      <td>
        <br>
        <img src="<%=request.getContextPath()%>/images/logo.png">
      </td>
    </tr>
    <tr>
      <td class="content-body">
        <div class="content">
          <table>
            <tr>
                <td class="text1">
                  </br><b>Ticket #<%=Ticket.getProperty("ticketNumber")%></b></br></br>
                </td>
            </tr>
          </table>
       
      <!----Search Form ------->

      <form method="POST" action="<%=request.getContextPath()%>/help" >
        <table width="100%" border=0 cellpadding=0 cellspacing=0>
          <tr>
            <td class="sectionTitle">
              Ticket Information
              <br />
            </td>
          </tr>
          <tr>
            <td >
              <table width="700" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
                <%
                for (int i=0;i<xmlTicket.getLength();i++) {
                  Element propertyElement = (Element)xmlTicket.item(i);
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
                          if(Ticket.getProperty(name)!=null)
                          	value=Ticket.getProperty(name).toString();
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
                          %>
                          <input name="ticketDate" class="tcal" value="<%=Ticket.getProperty("ticketDate")%>" />
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
                                    (ddl.get(j).equals(Ticket.getProperty(propertyElement.getAttribute("name").toString())))
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
                          String value=Ticket.getProperty(name).toString();
                          %>
                          <%=value%>
                          <%
                        }

                        else {
                          %>
                          <%=Ticket.getProperty(propertyElement.getAttribute("name").toString())%>
                          <%
                        }
                        %>


                        <%
                      } else {
                        %>
                        <%=Ticket.getProperty(propertyElement.getAttribute("name").toString())%>
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



