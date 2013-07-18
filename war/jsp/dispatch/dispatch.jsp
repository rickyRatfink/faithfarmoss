<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>

<jsp:include page="../includes/header.jsp"/>

<jsp:include page="../includes/menu/index.jsp" flush="true"/>

<script language="javascript">

	
	
	function printPage()
	{
	window.print();
	}
	
	function disableFields() {
		var count = document.forms[0].elements.length-2;
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
</script>

<%
		String required = "<font size='-2' color='red'>*</font>";
	
		Entity Person = (Entity)session.getAttribute("Person");	
		Entity Address = (Entity)session.getAttribute("Address");
		Entity Dispatch = (Entity)session.getAttribute("Dispatch");
		
%>
		<!----Main Content--->
      
		<td>
			<table width="820" border="0" cellpadding="0" cellspacing="0">
				<tr><td><br><!--img src="<%=request.getContextPath()%>/images/header_top.png"--></td></tr>
				<tr>
				<td class="content-body">
						<div class="content">
						<img src="<%=request.getContextPath()%>/images/titles/dispatch.jpg">
						<br>
						<br />
						<jsp:include page="../includes/errors.jsp" flush="true"/>
						
						<!----Search Form ------->
						
						<form method="POST" action="<%=request.getContextPath()%>/dispatch" >
							<table width="80%" border=0 cellpadding=0 cellspacing=0>
							<tr>
								<td <% if (Person.getKey().getId()==0) { %>class="dispatchNameSearch"<% } %>>
								 	<table width="630" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
							             
                                         	 <% if (Person.getKey().getId()==0) { %>
							                <tr>
                                            	<td colspan="5" class="text5">
                                                Enter a customer's name and click search to retrieve the customer's information.
                                                </td>
                                            </tr>
                                            <% } %>
							                <tr>
							                 <td width="100" class="field">First Name<%=required%></td>
                               <td width="80" class="field">Middle Initial</td>
							                 <td width="13" class="field">Last Name<%=required%></td>
							                 <td width="40" class="field">Suffix</td>
                                             <td width="30"></td>
							               </tr>
                                           
							               	
							               <tr>
							               	 <td><input name="firstName" value="<%=Person.getProperty("firstName")%>" maxlength="20" size="20" class="entry"></td>
                                             <td><input name="middleInitial" value="<%=Person.getProperty("middleInitial")%>" maxlength="1" size="1" class="entry"></td>
							               	 <td><input name="lastName" value="<%=Person.getProperty("lastName")%>"maxlength="30" size="30" class="entry"></td>
                                             <td>
                                             	<select name="suffix" class="entry">
							               	 	    <option value=""></option>
							               	 		<option value="Jr.">Jr.</option>
							               	 		<option value="Sr.">Sr.</option>
							               	 		<option value="I">I</option>
							               	 		<option value="II">II</option>
							               	 		<option value="III">III</option>
							               	 	</select>
                                             </td>
							               	 <td>
                                             	<input type="submit" name="action" value="Search Customer" class="buttonFilter">
                                                <input type="hidden" name="entity" value="Dispatch" />
                                                </td>
							               </tr> 
										   					               	
							             
							             
							           </table>
							        </td>
							</tr>
							
							<tr>
								<td class="field" >Email Address<%=required%></td>
							</tr>
							<tr>
								<td >
									<input name="email" value="<%=Address.getProperty("email")%>" maxlength="60" size="60" class="entry">
								</td>
							</tr>
							<tr>
								<td class="field">Address<%=required%></td>
                           </tr>
							<tr>
								<td >
									<input name="addressLine1" value="<%=Address.getProperty("addressLine1")%>" maxlength="80" size="50" class="entry">
								</td>
                             </tr>
                             <tr>
								 <td class="field" >Street Suffix<%=required%></td>
							</tr>
                            <tr>
                                 <td >
                                    <select name="streetSuffix" class="entry">
							               	 	    <option value="street" <%if ("street".equals((String)Address.getProperty("streetSuffix"))) {%>selected<%}%>>street</option>
							               	 	    <option value="road" <%if ("road".equals((String)Address.getProperty("streetSuffix"))) {%>selected<%}%>>road</option>
							               	 	    <option value="boulevard" <%if ("boulevard".equals((String)Address.getProperty("streetSuffix"))) {%>selected<%}%>>boulevard</option>
							               	 	    <option value="avenue" <%if ("avenue".equals((String)Address.getProperty("streetSuffix"))) {%>selected<%}%>>avenue</option>
							               	 	    <option value="place" <%if ("place".equals((String)Address.getProperty("streetSuffix"))) {%>selected<%}%>>place</option>
							               	 	    <option value="drive" <%if ("drive".equals((String)Address.getProperty("streetSuffix"))) {%>selected<%}%>>drive</option>
                                                    <option value="manor" <%if ("manor".equals((String)Address.getProperty("streetSuffix"))) {%>selected<%}%>>manor</option>
							               	 	    <option value="circle" <%if ("circle".equals((String)Address.getProperty("streetSuffix"))) {%>selected<%}%>>circle</option>
							               	 	    <option value="lane" <%if ("lane".equals((String)Address.getProperty("streetSuffix"))) {%>selected<%}%>>lane</option>
							               	 	    <option value="parkway" <%if ("parkway".equals((String)Address.getProperty("streetSuffix"))) {%>selected<%}%>>parkway</option>
							               	 	    <option value="way" <%if ("way".equals((String)Address.getProperty("streetSuffix"))) {%>selected<%}%>>way</option>
							               	 	    <option value="other" <%if ("other".equals((String)Address.getProperty("streetSuffix"))) {%>selected<%}%>>other</option>							               	 	
							        </select>
                                </td>
							</tr>
						
														
							
							<tr>
								<td colspan="2">
									<table width="100%">
										<tr>
											<td class="field" width="100" >City<%=required%></td>
                                            <td class="field" width="100" >State<%=required%></td>
											<td class="field" >Zipcode<%=required%></td>
										</tr>
										<tr>
										<td >
											<input name="city" value="<%=Address.getProperty("city")%>" maxlength="20" size="20" class="entry">
										</td>
                                        <td><jsp:include page="../includes/stateList.jsp" flush="true"/></td>
										<td >
											<input name="zipcode" value="<%=Address.getProperty("zipcode")%>" maxlength="10" size="10" class="entry">
										</td>
										</tr>
									</table>
								</td>
							</tr>
                            
						   <tr>
                                <td class="field">Major Intersection<%=required%></td>
                           </tr>
							<tr>
								<td >
									<input name="majorIntersection" value="<%=Address.getProperty("majorIntersection")%>" maxlength="50" size="50" class="entry">
								</td>
                             </tr>
                            <tr>
								 <td class="field" >Type of Structure<%=required%></td>
							</tr>
                            <tr>
                                 <td >
                                    <select name="structureType" class="entry">
                                    				<option value="">select</option>	
							               	 	    <option value="house"<% if ("house".equals((String)Address.getProperty("structureType"))) {%>selected<%}%>>house</option>	
                                                    <option value="apartment" <% if ("apartment".equals((String)Address.getProperty("structureType"))) {%>selected<%}%>>apartment</option>	
                                                    <option value="condominium" <% if ("condominium".equals((String)Address.getProperty("structureType"))) {%>selected<%}%>>condominium</option>	
                                                    <option value="townhome" <% if ("townhome".equals((String)Address.getProperty("structureType"))) {%>selected<%}%>>townhome</option>	
                                                    <option value="assisted living facility" <% if ("assisted living facility".equals((String)Address.getProperty("structureType"))) {%>selected<%}%>>assisted living facility</option>	
                                                    <option value="mobile home" <% if ("mobile home".equals((String)Address.getProperty("structureType"))) {%>selected<%}%>>mobile home</option>	
                                    </select>
                                </td>
							</tr>
                            <tr>
                            	<td>
                                	<table width="70%">
										<tr>
                                            <td class="field">Building Number</td>
                                            <td class="field">Floor Number</td>
                                            <td class="field">Elevator Availability<%=required%></td>
                                            <td class="field">Gated Community<%=required%></td>
									    </tr>
                                        <tr>
                                            <td >
                                                <input name="buildingNumber" value="<%=Address.getProperty("buildingNumber")%>" maxlength="8" size="8" class="entry">
                                            </td>
                                             <td >
                                                    <select name="floorNumber" class="entry">
                                                                    <option value="">select</option>	
                                                                    <% for (int f=1;f<30;f++) { %>
                                                                        <option value="<%=f%>" <% if ((f+"").equals((String)Address.getProperty("floorNumber"))) {%>selected<%}%>><%=f%></option>	
                                                                    <% } %>
                                                    </select>
                                               </td>
                                               <td >
                                                    <select name="elevatorFlag" class="entry">
                                                                    <option value="">select</option>	
                                                                    <option value="Y" <% if ("Y".equals((String)Address.getProperty("elevatorFlag"))) {%>selected<%}%>>Yes</option>	
                                                                    <option value="N" <% if ("N".equals((String)Address.getProperty("elevatorFlag"))) {%>selected<%}%>>No</option>
                                                    </select>
                                               </td>
                                               <td >
                                                    <select name="gatedCommunity" class="entry">
                                                                    <option value="">select</option>	
                                                                    <option value="Y" <% if ("Y".equals((String)Address.getProperty("gatedCommunity"))) {%>selected<%}%>>Yes</option>	
                                                                    <option value="N" <% if ("N".equals((String)Address.getProperty("gatedCommunity"))) {%>selected<%}%>>No</option>
                                                    </select>
                                               </td>
                                         </tr>
                                    </table>
                             </td>
                            </tr>
                            <tr>
                            	<td class="field">Gate Instructions</td>
                            </tr>
                            <tr>
                            	<td><select name="gateInstructions" class="entry">
                                                                    <option value="">select</option>	
                                                                    <option value="callbox code" <% if ("Y".equals((String)Address.getProperty("gateInstructions"))) {%>selected<%}%>>callbox code</option>	
                                                                    <option value="ask security" <% if ("N".equals((String)Address.getProperty("gateInstructions"))) {%>selected<%}%>>ask security</option>
                                                                    <option value="buzz owner by last name" <% if ("N".equals((String)Address.getProperty("gateInstructions"))) {%>selected<%}%>>buzz owner by last name</option>
                                                    </select>
                                 </td>
                            </tr>
									<%
									String cell=(String)Address.getProperty("cellPhone");
									String cell1="",cell2="",cell3="";
									
									String home=(String)Address.getProperty("homePhone");
									String home1="",home2="",home3="";
									
									String work=(String)Address.getProperty("workPhone");
									String work1="",work2="",work3="";
									
									if (home!=null&&home.length()==13) { 									                  
										home1=home.substring(1,4);
										home2=home.substring(5,8);
										home3=home.substring(9,13);
									}
									if (cell!=null&&cell.length()==13) {									                  
										cell1=cell.substring(1,4);
										cell2=cell.substring(5,8);
										cell3=cell.substring(9,13);
									}
									if (work!=null&&work.length()==13) { 									                  
										work1=work.substring(1,4);
										work2=work.substring(5,8);
										work3=work.substring(9,13);
									}
										
									%>
							<tr>
								<td colspan="2">
									<table width="100%">
										<tr>
											<td class="field" width="200" >Home Number<%=required%></td>
                                            <td class="field" width="200" >Cell Number</td>
											<td class="field" width="200" >Alternative Number</td>
										</tr>
									<tr>
										<td>
											(<input name="home_areacode" value="<%=home1%>" maxlength="3" size="3" class="entry">)
											<input name="home_first3" value="<%=home2%>" maxlength="3" size="3" class="entry">&nbsp;-
											<input name="home_last4" value="<%=home3%>" maxlength="4" size="4" class="entry">
										</td>
							
										<td>
											(<input name="cell_areacode" value="<%=cell1%>" maxlength="3" size="3" class="entry">)
											<input name="cell_first3" value="<%=cell2%>" maxlength="3" size="3" class="entry">&nbsp;-
											<input name="cell_last4" value="<%=cell3%>" maxlength="4" size="4" class="entry">
										</td>
							
										<td>
											(<input name="work_areacode" value="<%=work1%>" maxlength="3" size="3" class="entry">)
											<input name="work_first3" value="<%=work2%>" maxlength="3" size="3" class="entry">&nbsp;-
											<input name="work_last4" value="<%=work3%>" maxlength="4" size="4" class="entry">
										</td>
						    		</tr>
									</table>
								</td>
							</tr>
							
							
							
							<tr>
								<td colspan="2">
									<table width="100%">
										<tr>
											<td class="field" width="150" >Donation/Delivery Location<%=required%></td>
											<td class="field" width="150" >Dispatch/Delivery Date<%=required%></td>
                                            <td class="field">Call Requirements<%=required%></td>
											<td class="field" >Item Location</td>
										</tr>
										<tr>
										<td >
											<select name="dispatchLocation" class="entry">
                                                <option value="">select</option>
												<option value="Fort Lauderdale, FL" <% if ("Fort Lauderdale, FL".equals((String)Dispatch.getProperty("dispatchLocation"))) {%>selected<%}%>>Fort Lauderdale, FL</option>
												<option value="Boynton Beach, FL" <% if ("Boynton Beach, FL".equals((String)Dispatch.getProperty("dispatchLocation"))) {%>selected<%}%>>Boynton Beach, FL</option>
												<option value="Okeechobee, FL" <% if ("Okeechobee, FL".equals((String)Dispatch.getProperty("dispatchLocation"))) {%>selected<%}%>>Okeechobee, FL</option>
											</select>
										</td>
										<td >
											<div><input name="date" class="tcal" value="<%=Dispatch.getProperty("dispatchDate")%>" /></div>
										</td>
                                        <td>
                                        	<select name="callRequirements" class="entry">
												<option value="">select</option>
												<option value="tag (donation only)" <% if ("tag (donation only)".equals((String)Dispatch.getProperty("callRequirements"))) {%>selected<%}%>>tag (donation only)</option>
												<option value="half-hour call" <% if ("half-hour call".equals((String)Dispatch.getProperty("callRequirements"))) {%>selected<%}%>>half-hour call</option>
                                                <option value="one hour call" <% if ("one hour call".equals((String)Dispatch.getProperty("callRequirements"))) {%>selected<%}%>>one hour call</option>
												
											</select>
                                        </td>
										<td>
											<select name="itemLocation" class="entry">
                                                <option value="">select</option>
												<option value="Porch" <% if ("Porch".equals((String)Dispatch.getProperty("itemLocation"))) {%>selected<%}%>>Porch</option>
												<option value="Carport" <% if ("Carport".equals((String)Dispatch.getProperty("itemLocation"))) {%>selected<%}%>>Carport</option>
												<option value="Outside" <% if ("Outside".equals((String)Dispatch.getProperty("itemLocation"))) {%>selected<%}%>>Outside</option>
											</select>
										</td>
										</tr>
									</table>
								</td>
							</tr>
							
							
							<tr>
								<td>
									<br><br>
									
							           <table width="100%" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
							             
							                <tr>
							                 <td colspan="5" class="results-section">Donation</td>
							                </tr>
							                <tr>
							                 <td width="15" class="results-heading">QTY</td>
							                 <td width="100" class="results-heading">DESCRIPTION</td>
							               	 <td width="1" bgcolor="#7d8f98"></td>
							               	 <td width="15" class="results-heading">QTY</td>
							                 <td width="100" class="results-heading">DESCRIPTION</td>
							               	</tr>
							               	
							              
							               <tr>
							               	 <td class="results-blank"><input class="entry" name="airConditioner" value="<%=Dispatch.getProperty("airConditioner")%>" size="3" maxlength="3"></td>
							               	 <td class="results-blank">Air Conditioner</td>
							               	 <td bgcolor="#7d8f98"></td>
							               	 <td class="results-blank"><input class="entry" name="lamp" value="<%=Dispatch.getProperty("lamp")%>" size="3" maxlength="3"></td>
							               	 <td class="results-blank">Lamp</td>
							               </tr>
                                           <tr>
							               	 <td class="results-blank"><input class="entry" name="armoire" value="<%=Dispatch.getProperty("armoire")%>" size="3" maxlength="3"></td>
							               	 <td class="results-blank">Armoire</td>
							               	 <td bgcolor="#7d8f98"></td>
							               	 <td class="results-blank"><input class="entry" name="lawnFurniture" value="<%=Dispatch.getProperty("lawnFurniture")%>" size="3" maxlength="3"></td>
							               	 <td class="results-blank">Lawn Furniture</td>
							               </tr>
                                           <tr>
							               	 <td class="results-blank"><input class="entry" name="bedding" value="<%=Dispatch.getProperty("bedding")%>" size="3" maxlength="3">&nbsp;&nbsp;<select name="beddingBagsBoxes" class="entry"><option value="">select</option><option value="bags"<% if ("bags".equals((String)Dispatch.getProperty("beddingBagsBoxes"))) { %>selected<%}%>>bags</option><option value="boxes" <% if ("boxes".equals((String)Dispatch.getProperty("beddingBagsBoxes"))) { %>selected<%}%>>boxes</option></select></td>
							               	 <td class="results-blank">Bedding</td>
							               	 <td bgcolor="#7d8f98"></td>
							               	 <td class="results-blank" valign="center"><input name="mattressBoxSpring"  class="entry" value="<%=Dispatch.getProperty("mattressBoxSpring")%>" size="3" maxlength="3">&nbsp;&nbsp;<input name="mattressSize" value="<%=Dispatch.getProperty("televisionSize")%>" size="5" maxlength="10" class="entry"></td>
							               	 <td class="results-blank">Mattress/Box Spring</td>
							               </tr>
                                           <tr>
							               	 <td class="results-blank"><input class="entry" name="books" value="<%=Dispatch.getProperty("books")%>" size="3" maxlength="3">&nbsp;&nbsp;<select name="booksBagsBoxes" class="entry"><option value=""></option><option value="bags"<% if ("bags".equals((String)Dispatch.getProperty("booksBagsBoxes"))) { %>selected<%}%>>bags</option><option value="boxes" <% if ("boxes".equals((String)Dispatch.getProperty("booksBagsBoxes"))) { %>selected<%}%>>boxes</option></select></td>
							               	 <td class="results-blank">Books</td>
							                 <td bgcolor="#7d8f98"></td>
							               	 <td class="results-blank"><input class="entry"  name="miscHouseholdItems" value="<%=Dispatch.getProperty("miscHouseholdItems")%>" size="3" maxlength="3">&nbsp;&nbsp;<select name="miscBagsBoxes" class="entry"><option value="bags" <% if ("bags".equals((String)Dispatch.getProperty("miscBagsBoxes"))) { %>selected<%}%>>bags</option><option value="boxes"<% if ("boxes".equals((String)Dispatch.getProperty("miscBagsBoxes"))) { %>selected<%}%> >boxes</option></select></td>
							               	 <td class="results-blank">Misc. Household Items</td>
							               </tr>
                                           <tr>
							               	 <td class="results-blank"><input class="entry" name="clothing" value="<%=Dispatch.getProperty("clothing")%>" size="3" maxlength="3">&nbsp;&nbsp;<select name="clothingBagsBoxes" class="entry"><option value=""></option><option value="bags"<% if ("bags".equals((String)Dispatch.getProperty("clothingBagsBoxes"))) { %>selected<%}%>>bags</option><option value="boxes" <% if ("boxes".equals((String)Dispatch.getProperty("clothingBagsBoxes"))) { %>selected<%}%>>boxes</option></select></td>
							               	 <td class="results-blank">Clothing</td>
							               	 <td bgcolor="#7d8f98"></td>
							               	 <td class="results-blank"><input class="entry" name="nightstand" value="<%=Dispatch.getProperty("nightstand")%>" size="3" maxlength="3"></td>
							               	 <td class="results-blank">Nightstand</td>
							               </tr>
                                           <tr>
							               	 <td class="results-blank"><input class="entry" name="chair" value="<%=Dispatch.getProperty("chair")%>" size="3" maxlength="3"></td>
							               	 <td class="results-blank">Chair</td>
							               	 <td bgcolor="#7d8f98"></td>
							               	 <td class="results-blank"><input class="entry" name="refrigerator" value="<%=Dispatch.getProperty("refrigerator")%>" size="3" maxlength="3"></td>
							               	 <td class="results-blank">Refrigerator</td>
							               </tr>
                                           <tr>
							               	 <td class="results-blank"><input class="entry" name="computer" value="<%=Dispatch.getProperty("computer")%>" size="3" maxlength="3"></td>
							               	 <td class="results-blank">Computer</td>
							               	 <td bgcolor="#7d8f98"></td>
							               	 <td class="results-blank"><input class="entry" name="recliner" value="<%=Dispatch.getProperty("recliner")%>" size="3" maxlength="3"></td>
							               	 <td class="results-blank">Recliner</td>
							               </tr>
                                           <tr>
							               	 <td class="results-blank"><input class="entry" name="desk" value="<%=Dispatch.getProperty("desk")%>" size="3" maxlength="3"></td>
							               	 <td class="results-blank">Desk</td>
							               	 <td bgcolor="#7d8f98"></td>
							               	 <td class="results-blank"><input class="entry" name="sofaLoveseat" value="<%=Dispatch.getProperty("sofaLoveseat")%>" size="3" maxlength="3"></td>
							               	 <td class="results-blank">Sofa/Loveseat</td>
							               </tr>
                                           <tr>
							               	 <td class="results-blank"><input class="entry" name="dishwasher" value="<%=Dispatch.getProperty("dishwasher")%>" size="3" maxlength="3"></td>
							               	 <td class="results-blank">Dishwasher</td>
							               	 <td bgcolor="#7d8f98"></td>
							               	 <td class="results-blank"><input class="entry" name="stove" value="<%=Dispatch.getProperty("stove")%>" size="3" maxlength="3"></td>
							               	 <td class="results-blank">Stove</td>
							               </tr>
                                           <tr>
							               	 <td class="results-blank"><input class="entry" name="dresser" value="<%=Dispatch.getProperty("dresser")%>" size="3" maxlength="3"></td>
							               	 <td class="results-blank">Dresser</td>
							               	 <td bgcolor="#7d8f98"></td>
							               	 <td class="results-blank"><input class="entry" name="tableChair" value="<%=Dispatch.getProperty("tableChair")%>" size="3" maxlength="3"></td>
							               	 <td class="results-blank">Table/Chair</td>
							               </tr>
                                           <tr>
							               	 <td class="results-blank"><input class="entry" name="dryer" value="<%=Dispatch.getProperty("dryer")%>" size="3" maxlength="3"></td>
							               	 <td class="results-blank">Dryer</td>
							               	 <td bgcolor="#7d8f98"></td>
							               	 <td class="results-blank"><input class="entry" name="television"  value="<%=Dispatch.getProperty("television")%>" size="3" maxlength="3">&nbsp;&nbsp;<input name="televisionSize" value="<%=Dispatch.getProperty("televisionSize")%>" size="5" maxlength="10" class="entry"></td>
							               	 <td class="results-blank">Television</td>
							               </tr>
                                           <tr>
							               	 <td class="results-blank"><input class="entry" size="3" name="entertainmentCenter" value="<%=Dispatch.getProperty("entertainmentCenter")%>" maxlength="3"></td>
							               	 <td class="results-blank">Entertainment Center</td>
							               	 <td bgcolor="#7d8f98"></td>
							               	 <td class="results-blank"><input class="entry" name="vcr" value="<%=Dispatch.getProperty("vcr")%>" size="3" maxlength="3"></td>
							               	 <td class="results-blank">VCR</td>
							               </tr>
                                           <tr>
							               	 <td class="results-blank"><input class="entry" name="exerciseEquipment" value="<%=Dispatch.getProperty("exerciseEquipment")%>" size="3" maxlength="3"></td>
							               	 <td class="results-blank">Exercise Equipment</td>
							               	 <td bgcolor="#7d8f98"></td>
							               	 <td class="results-blank"><input class="entry" name="wallUnit" value="<%=Dispatch.getProperty("wallUnit")%>" size="3" maxlength="3"></td>
							               	 <td class="results-blank">Wall Unit</td>
							               </tr>
                                           <tr>
							               	 <td class="results-blank"><input class="entry" name="headboardFootboard" value="<%=Dispatch.getProperty("headboardFootboard")%>" size="3" maxlength="3"></td>
							               	 <td class="results-blank">Headboard/Footboard</td>
							               	 <td bgcolor="#7d8f98"></td>
							               	 <td class="results-blank"><input class="entry" name="washer" value="<%=Dispatch.getProperty("washer")%>" size="3" maxlength="3"></td>
							               	 <td class="results-blank">Washer</td>
							               </tr>
                                          <tr>
							               	 <td class="results-blank" colspan="5" width="100%" height="100" valign="center" align="left">
							               	 	Other Items<br>
							               	 	<textarea><%=Dispatch.getProperty("otherItems")%></textarea>
							               	 	<br><br />
							               	 </td>
							               </tr>
							              
										</table>
			  							
			  					</td>
			  				</tr>		
                            <tr>
                            	<td height="50" valign="center">
                                	<input type="checkbox" name="acceptTermsPolicies" value="Y" <% if ("Y".equals((String)Dispatch.getProperty("acceptTermsPolicies"))) {%>checked<%}%>/>
                                    <i>I acknowledge that I have read Faith Farm's delivery/donation terms and policies to the customer.</i>
                                </td>
                            </tr>
							<tr>
								<td  height="60" valign="bottom" align="right">
									<input type="submit" name="action" value="Save" class="buttonSave">
						  		</td>
							</tr>
							
							</table>
						<input type="hidden" name="entity" value="Dispatch"/>	
                        <input type="hidden" name="PersonId" value="<%=Person.getKey().getId()%>"/>			
						</form>			
						
						<!----End Form----------->
						</div>
									
				</td></tr>
				<tr><td><!--img src="<%=request.getContextPath()%>/images/header_btm.png"--></td></tr>
			</table>
		</td>
	</tr>	
	<script language="javascript">
	
		<% if (Person.getKey().getId()==0) { %>
				disableFields();
		<% } else { %>
				enableFields();
		<% } %>
	</script>
</div>
</table>

<jsp:include page="../includes/footer.jsp"/>


