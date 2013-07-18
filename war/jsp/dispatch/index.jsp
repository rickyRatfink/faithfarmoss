<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ page import="org.w3c.dom.NodeList" %>
<%@ page import="org.w3c.dom.Element" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>

<jsp:include page="../includes/header.jsp"/>

<jsp:include page="../includes/menu/index.jsp" flush="true"/>



<style>
      #map_canvas {
        height: 300px;
        width: 500px;
        margin-top: 0.6em;
      }
</style>
    
<script type="text/javascript">


window.gcoder = new google.maps.Geocoder();
$(document).ready(function() {
  

	$(".entry[name=zipcode]").change(function(e) {
	  gcoder.geocode({
	    'address': $(this).val()
	  }, function(res, status) {
	    window.res = res;
	    if(status == google.maps.GeocoderStatus.OK && res.length) {
	      for(var i=0;i<res[0].address_components.length;i++) {
	        var component = res[0].address_components[i];
	        
	        for(var x=0;x<component.types.length;x++) {
	          var tType = component.types[x];
	          if(tType == "administrative_area_level_1") {
	            $(".entry[name=state]").val(component.long_name);
	          }
	          if(tType == "locality") {
	            $(".entry[name=city]").val(component.long_name);
	          }
	        }
	      }
	    }
	  });
	});
	
});
</script>
    
    
 <script language="javascript">
 
 function initialize() {
        var mapOptions = {
          center: new google.maps.LatLng(26.153936, -80.153442),
          zoom: 15,
          mapTypeId: google.maps.MapTypeId.ROADMAP
        };
        var map = new google.maps.Map(document.getElementById('map_canvas'),
          mapOptions);

        var input = document.getElementById('searchTextField');
        var options = {
        		  types: ['(cities)'],
        		  componentRestrictions: {country: 'us'}
        		};
        var autocomplete = new google.maps.places.Autocomplete(input);

        autocomplete.bindTo('bounds', map);

        var infowindow = new google.maps.InfoWindow();
        var marker = new google.maps.Marker({
          map: map
        });

        google.maps.event.addListener(autocomplete, 'place_changed', function() {
          infowindow.close();
          var place = autocomplete.getPlace();
          if (place.geometry.viewport) {
            map.fitBounds(place.geometry.viewport);
          } else {
            map.setCenter(place.geometry.location);
            map.setZoom(17);  // Why 17? Because it looks good.
          }

          var image = new google.maps.MarkerImage(
              place.icon,
              new google.maps.Size(71, 71),
              new google.maps.Point(0, 0),
              new google.maps.Point(17, 34),
              new google.maps.Size(35, 35));
          marker.setIcon(image);
          marker.setPosition(place.geometry.location);

          var address = '';
          if (place.address_components) {
            address = [
              (place.address_components[0] && place.address_components[0].short_name || ''),
              (place.address_components[1] && place.address_components[1].short_name || ''),
              (place.address_components[2] && place.address_components[2].short_name || '')
            ].join(' ');
          }

          infowindow.setContent('<div><strong>' + place.name + '</strong><br>' + address);
          infowindow.open(map, marker);
        });

        // Sets a listener on a radio button to change the filter type on Places
        // Autocomplete.
        function setupClickListener(id, types) {
          var radioButton = document.getElementById(id);
          google.maps.event.addDomListener(radioButton, 'click', function() {
            autocomplete.setTypes(types);
          });
        }

        setupClickListener('changetype-all', []);
        setupClickListener('changetype-establishment', ['establishment']);
        setupClickListener('changetype-geocode', ['geocode']);
      }
      google.maps.event.addDomListener(window, 'load', initialize);
  
  function printPage()
  {
    window.print();
  }

  function disableFields() {
    var count = document.forms[0].elements.length-1;
    var i;
    for (i=5;i<count;i++) {
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

  
  function disableSave()
  {
  		document.getElementById("button1").disabled=true;
    	document.getElementById("button2").disabled=true;
    	document.getElementById("action").value="Save";
    	document.forms[0].submit();
  }
  function disableSavePrint()
  {
  		document.getElementById("button1").disabled=true;
    	document.getElementById("button2").disabled=true;
    	document.getElementById("action").value="Save & Print";
    	document.forms[0].submit();
  }
  function disableSaveChanges()
  {
  		document.getElementById("button1").disabled=true;
    	document.getElementById("button2").disabled=true;
    	document.getElementById("action").value="Save Changes";
    	document.forms[0].submit();
  }
  function disableSaveChangesPrint()
  {
  		document.getElementById("button1").disabled=true;
    	document.getElementById("button2").disabled=true;
    	document.getElementById("action").value="Save Changes & Print";
    	document.forms[0].submit();
  }
  

</script>

<%
String required = "<font size='-2' color='red'>*</font>";
Entity Person = (Entity)request.getAttribute("Person");
Entity Address = (Entity)request.getAttribute("Address");
Entity Dispatch = (Entity)request.getAttribute("Dispatch");
NodeList xmlPerson=(NodeList)request.getAttribute("xmlPerson");
NodeList xmlAddress=(NodeList)request.getAttribute("xmlAddress");
NodeList xmlDispatch=(NodeList)request.getAttribute("xmlDispatch");

String disabled="";

if (Person.getKey().getId()!=0)
disabled="disabled=true";
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
                Create/Edit Dispatch
              </td>
            </tr>
  			<jsp:include page="dispatch_stats.jsp" flush="true"/>
  			
          </table>
          <jsp:include page="../includes/errors.jsp" flush="true"/>
          <br/>
          <!----Search Form ------->
          <form method="POST" action="<%=request.getContextPath()%>/dispatch" id="dispatchForm">
            <table width="80%" border=0 cellpadding=0 cellspacing=0>

              <%
              if (!"Y".equals(request.getParameter("edit"))) {
                %>
                <tr>
                  <td class="dispatchNameSearch">
                    <table width="460" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">

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
                        <td width="40" class="field">
                          Suffix
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
                              onKeypress=”changeToUpperCase();”
                              class="entry">


                        </td>
                        <td>
                          <select name="suffix" class="entry">
                            <option value="">
                            </option>
                            <option value="Jr.">
                              Jr.
                            </option>
                            <option value="Sr.">
                              Sr.
                            </option>
                            <option value="I">
                              I
                            </option>
                            <option value="II">
                              II
                            </option>
                            <option value="III">
                              III
                            </option>
                          </select>
                        </td>
                        <td>
                          <input type="submit" name="action" value="Search" class="buttonFilter">
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
                    <table width="500" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
                      <tr>
                        <td width="40" class="edit-field-dark-small">
                          last name
                        </td>
                        <td class="edit-field-dark">
                          <input 
                              name="lastName"
                              value="<%=Person.getProperty("lastName")%>"
                              size="40"
                              maxlength="30"
                              <%=disabled%>
                              class="entry"/>
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
                        first name
                      </td>
                      <td class="edit-field-dark">
                        <b>
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
                      <td class="<% if (i%2==0) { %>edit-field-dark<%}else{%>edit-field-white<%}%>">
                        <%=propertyElement.getAttribute("display")%>
                         <% if ("true".equals(requiredField)) { %>
                            <%=required%>
                            <%}%>
                      </td>
                      <td class="<% if (i%2==0) { %>edit-value-dark<%}else{%>edit-value-white<%}%>">
                        <%
  
                        if ("Y".equals(edit)) {

                          if ("text".equals(input)) {
                            %>
                            <input 
                                name="<%=name%>"
                                value="<%=Address.getProperty(propertyElement.getAttribute("name").toString())%>"
                                maxlength="<%=maxlength%>"
                                size="<%=maxlength%>"
                                <% if (!"email".equals(name)) { %><%=disabled%><% } %>
                                class="entry"/>
                            <%
                          }
                          else if ("ddl".equals(input)) {
                            ArrayList ddl = (ArrayList)session.getAttribute(source);
                            String value=Address.getProperty(propertyElement.getAttribute("name")).toString();
                           	if ("state".equals(name)) { 
                           		if (value=="")
                           			value="Florida";
                           	 }
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
                                  class="entry"
                                  <%=disabled%>
                              />

                              )
                              <input 
                                  onKeyPress="return isNumberKey(event)"
                                  value=""
                                  name="<%=name%>_first3"
                                  maxlength="3"
                                  size="3"
                                  class="entry"
                                  <%=disabled%>
                              />

                              &nbsp;-
                              <input 
                                  onKeyPress="return isNumberKey(event)"
                                  value=""
                                  name="<%=name%>_last4"
                                  maxlength="4"
                                  size="4"
                                  class="entry"
                                  <%=disabled%>
                              />
                              -->

						(<input type="text" id="<%=name%>_areacode" name="<%=name%>_areacode" maxlength=3 size=3 onkeyup="moveOnMax(this,'<%=name%>_first3')" onKeyPress="return isNumberKey(event)" class="entry" <%=disabled%> />)
						<input type="text" id="<%=name%>_first3" name="<%=name%>_first3" maxlength=3 size=3 onkeyup="moveOnMax(this,'<%=name%>_last4')" onKeyPress="return isNumberKey(event)" class="entry" <%=disabled%> />-
						<input type="text" id="<%=name%>_last4" name="<%=name%>_last4" maxlength=4 size=4 onKeyPress="return isNumberKey(event)" class="entry" <%=disabled%> />

                              <%
                            } else {
                              %>
                              
                        (<input type="text" id="<%=name%>_areacode"  name="<%=name%>_areacode" value="<%=((String)Address.getProperty(name)).substring(1,4)%>"  maxlength=3 size=3 onkeyup="moveOnMax(this,'<%=name%>_first3')" onKeyPress="return isNumberKey(event)" class="entry" <%=disabled%> />)
						<input type="text" id="<%=name%>_first3" name="<%=name%>_first3" value="<%=((String)Address.getProperty(name)).substring(5,8)%>" maxlength=3 size=3 onkeyup="moveOnMax(this,'<%=name%>_last4')" onKeyPress="return isNumberKey(event)" class="entry" <%=disabled%> />-
						<input type="text" id="<%=name%>_last4" name="<%=name%>_last4" value="<%=((String)Address.getProperty(name)).substring(9,13)%>" maxlength=4 size=4 onKeyPress="return isNumberKey(event)" class="entry" <%=disabled%> />
                              <!-- 
                              (
                              <input 
                                  onKeyPress="return isNumberKey(event)"
                                  value="<%=((String)Address.getProperty(name)).substring(1,4)%>"
                                  name="<%=name%>_areacode"
                                  maxlength="3"
                                  size="3"
                                  class="entry"
                                  <%=disabled%>
                              />

                              )
                              <input 
                                  onKeyPress="return isNumberKey(event)"
                                  value="<%=((String)Address.getProperty(name)).substring(5,8)%>"
                                  name="<%=name%>_first3"
                                  maxlength="3"
                                  size="3"
                                  class="entry"
                                  <%=disabled%>
                              />

                              &nbsp;-
                              <input 
                                  onKeyPress="return isNumberKey(event)"
                                  value="<%=((String)Address.getProperty(name)).substring(9,13)%>"
                                  name="<%=name%>_last4"
                                  maxlength="4"
                                  size="4"
                                  class="entry"
                                  <%=disabled%>
                              />
								 -->
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
              -------------------Dispatch Information
              ---------------------------------------------------------------------
            -->
            <tr>
              <td height="15">
              </td>
            </tr>
            <tr>
              <td class="sectionTitle">
                Dispatch/Delivery Information
              </td>
            </tr>
            
              <tr>
                <td>
                  <table width="500" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
                    
                      <tr>
		            	<%
		            	String deliveryAddress="";
		            	/*
		            	try {
		            	   deliveryAddress=(String)Dispatch.getProperty("dispatchAddress");
		            	} catch(Exception e) { deliveryAddress=""; }
		            	*/
		            	%>
		            	<!-- 
		            	<td width="80" class="edit-field-white">
		            		Delivery Address
		            	</td>
		            	
		            	<td class="edit-field-white">
		                	<input id="searchTextField" name="dispatchAddress" value="<%=deliveryAddress%>" type="text" size="50">
		                 </td>
		             </tr>
		             <tr>
		             	<td colspan="3">
		             	 	<div id="map_canvas"></div>
		                </td>
		             </tr>
		             -->
		            
                    <%
                    for (int i=0;i<xmlDispatch.getLength();i++) {
                      Element propertyElement = (Element)xmlDispatch.item(i);
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
                                      value="<%=Dispatch.getProperty(propertyElement.getAttribute("name").toString())%>"
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
                                      size="12"
                                      value="<%=Dispatch.getProperty(propertyElement.getAttribute("name").toString())%>"
                                  />
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
                                            (ddl.get(j).equals(Dispatch.getProperty(propertyElement.getAttribute("name").toString())))
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
                                  <textarea name="<%=name%>" class="dispatch"><%=Dispatch.getProperty(propertyElement.getAttribute("name").toString())%></textarea>
                                  <%
                                }

                              } else {
                                %>
                                <%=Dispatch.getProperty(propertyElement.getAttribute("name").toString())%>
                                <%
                              }
                              %>
                            </td>
                          </tr>
 					
                          <%}%>
                          </table>
                        </td>
                      </tr>
                      
                      <!-- 
                      <tr>

                        <td height="50" valign="center">
                          <i>
                            How you heard about us?
                          </i>
                          <%
                          ArrayList ddl = (ArrayList)session.getAttribute("ddlHowDidYouHearAboutUs");
                          %>
                          <select name="farmSource" class="entry">
                            <option value="">
                            </option>
                            <%
                            if (ddl!=null) {
                              String farmSource="";
                              if (Dispatch.getProperty("farmSource")!=null)
                              farmSource=Dispatch.getProperty("farmSource").toString();

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
					-->
					</br></br>
					
                     <tr>
                        <td height="50" valign="center">
                           <i>
	                            Would you like to receive email notifications regarding coupons, sale events, and Faith Farm news and events?
	                       </i>
	                      </td>
	                  </tr>
	                  <tr>
	                  <td align="left">
	                       yes<input 
                              type="radio"
                              name="acceptEmailNotifications"
                              value="Yes"
                              <%
                              if
                              ("Yes".equals((String)Dispatch.getProperty("acceptEmailNotifications")))
                              {%>checked<%}%>/>
                              
                            no<input 
                              type="radio"
                              name="acceptEmailNotifications"
                              value="No"
                              <%
                              if
                              ("No".equals((String)Dispatch.getProperty("acceptEmailNotifications")))
                              {%>checked<%}%>/>
                              
                              </br></br>
	                         </td>
                      </tr>
                      <tr>
                        <td height="50" valign="center">
                          <input 
                              type="checkbox"
                              name="acceptTermsPolicies"
                              value="Yes"
                              <%
                              if
                              ("Yes".equals((String)Dispatch.getProperty("acceptTermsPolicies")))
                              {%>checked<%}%>/>
                          <i>
                            I acknowledge that I have read Faith Farm's delivery/donation terms and policies to the
                            customer.
                          </i>
                        </td>
                      </tr>
                      <tr>
                        <td  height="60" valign="bottom" align="center">

                          <input type="hidden" name="id" value="<%=Person.getKey().getId()%>"/>
						  <input type="hidden" id="action" name="action" value=""/>
						  <%
                          if (Dispatch.getKey().getId()==0) {
                            %>
                            
                            <input type="submit" id="button1" value="Save" onclick="javascript:disableSave();" class="buttonSave"/>
                            <input type="submit" id="button2" value="Save & Print" onclick="javascript:disableSavePrint();" class="buttonSavePrint"/>
                            <input type="hidden" name="edit" value="Y"/>
                            <%
                          } else {
                            %>
                            <input 
                                type="hidden"
                                name="dispatchKey"
                                value="<%=KeyFactory.keyToString(Dispatch.getKey())%>"/>
                           <input type="submit" id="button1" value="Save Changes" onclick="javascript:disableSaveChanges();" class="buttonSave"/>
                            <input type="submit" id="button2" value="Save Changes & Print" onclick="javascript:disableSaveChangesPrint();" class="buttonSavePrint"/>
                            <%
                          }
                          %>

                        </td>
                      </tr>
                    </form>
  
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


