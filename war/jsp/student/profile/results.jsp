<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.text.DateFormat"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory"%>
<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory"%>
<%@ page import="com.google.appengine.api.datastore.DatastoreService"%>
<%@ page import="com.google.appengine.api.datastore.Query"%>
<%@ page import="com.google.appengine.api.datastore.Entity"%>
<%@ page import="com.google.appengine.api.datastore.FetchOptions"%>
<%@ page import="com.google.appengine.api.datastore.Key"%>
<%@ page import="com.google.appengine.api.datastore.KeyFactory"%>
<%@ page import="org.faithfarm.utilities.HTMLBuilder"%>
<jsp:include page="../../includes/header.jsp" />
<jsp:include page="../../includes/menu/index.jsp" flush="true" />

<script language="javascript">
	function clearFilter() {
		document.forms[0].elements[0].value = '';
		document.forms[0].elements[1].value = '';
		document.forms[0].elements[2].value = '';
		document.forms[0].elements[3].value = '';

	}
</script>

<%
	String required = "<font size='-2' color='red'>*</font>";

	int pageLimit = 200;
	int cursor = 0, size = 0;
	String sCursor = (String) request.getParameter("cursor");
	Integer iSize = (Integer) request.getAttribute("size");

	if (iSize == null) {
		String sSize = request.getParameter("size");

		if (sSize == null)
			size = 0;
		else
			size = Integer.parseInt(sSize);
	} else
		size = iSize.intValue();

	if (sCursor != null)
		cursor = Integer.parseInt(sCursor);

	String action = request.getParameter("action");
	
	String firstName=(String)session.getAttribute("firstName");
	String lastName=(String)session.getAttribute("lastName");
	String personStatus=(String)session.getAttribute("personStatus");
	String personType=(String)session.getAttribute("personType");
%>
<!----Main Content--->
<td valign="top">
	<table width="820" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td><br> <!--img src="<%=request.getContextPath()%>/images/header_top.png"--></td>
		</tr>
		<tr>
			<td class="content-body">
				<table>
					<tr>
						<form method="POST" action="<%=request.getContextPath()%>/student">

							<td class="text1">
								<%
									if ("Export".equals(action) || "export".equals(action)) {
								%> Export Intake <%
									} else if ("Profile".equals(action)) {
								%> Resident Profiles <input type="hidden" name="" value="" /> <%
 	} else if ("Card".equals(action)) {
 %> Application Intake Card <input type="hidden" name="card" value="Y" /> <%
 	} else if ("Work".equals(action)) {
 %> Work Assignments <input type="hidden" name="work" value="Y" /> <%
 	} else if ("Index".equals(action)) {
 %> Person Management <input type="hidden" name="index" value="Y" /> <%
 	} else if ("Deny".equals(action) || "deny".equals(action)) {
 %> Deny Intake <input type="hidden" name="deny" value="Y" /> <%
 	} else if ("Photo".equals(action)) {
 %> Upload Photo <input type="hidden" name="photo" value="Y" /> <%
 	} else if ("Packet".equals(action)) {
 %> Resident Packet <input type="hidden" name="packet" value="Y" /> <%
 	} else {
 %> Resident <%
 	}
 %>
							</td>
					</tr>
				</table>
				<table width="400" cellspacing="0" cellpadding="0" border="0" style="border-collapse: collapse;">


					<tr>
						<td class="filterPerson"><i> Filter By: </i>
							<table width="620" cellspacing="0" cellpadding="0" border="0" style="border-collapse: collapse;">




								<tr>
									<td width="100" class="field-filter">First Name</td>
									<td width="100" class="field-filter">Last Name</td>
									<td width="80" class="field-filter">Status</td>
									<td width="80" class="field-filter">Type</td>
								</tr>
								<tr>
									<td><input name="firstName" value="" maxlength="20" size="20" class="entry"></td>
									<td><input name="lastName" value="" maxlength="30" size="30" class="entry"></td>
									<td><select name="personStatus" class="entry">
											<%
												ArrayList ddl = (ArrayList) session.getAttribute("ddlPersonStatus");
											%>
											<option value=""></option>
											<%
												for (int i = 0; i < ddl.size(); i++) {
													String value = (String) ddl.get(i);
											%>
											<option value="<%=value%>" <% if (value.equals(personStatus)) { %>selected<% } %>>
												<%=value%>
											</option>
											<%
												}
											%>
									</select></td>
									<td><select name="personType" class="entry">
											<option value=""></option>
											<option value="Employee"  <% if ("Employee".equals(personType)) { %>selected<% } %>>Employee</option>
											<option value="Graduate" <% if ("Graduate".equals(personType)) { %>selected<% } %>>Graduate</option>
											<option value="Intake" <% if ("Intake".equals(personType)) { %>selected<% } %>>Intake</option>
											<option value="Resident" <% if ("Resident".equals(personType)) { %>selected<% } %>>Resident</option>
											<option value="Staff" <% if ("Staff".equals(personType)) { %>selected<% } %>>Staff</option>

									</select></td>
								</tr>
								<tr>
									<td colspan="4" align="right"></br> <input type="submit" name="action" value="Filter" class="buttonBlank"> <input type="button" value="Clear" class="buttonBlank"
										onClick="javascript:clearFilter();" /> &nbsp;&nbsp;</td>
								</tr>

							</table></td>
					</tr>
				</table> <br />
				</form> <br />
				<div class="results" style="border: 0;" id="customer-list-ctr">
					<jsp:include page="../../includes/warning.jsp" />
					<table width="900" cellspacing="0" cellpadding="0" border="0" style="border-collapse: collapse;">

						<tr>
							<td class="grid">
								<table width="900" cellspacing="0" cellpadding="0" border="0" style="border-collapse: collapse;">

									<tr>
										<td class="grid-heading">Search Results</td>
									</tr>
								</table>
								<table width="900" cellspacing="0" cellpadding="0" border="0" style="border-collapse: collapse;">

									<tr>
										<td class="grid-columnHeading" width="40"><a href="" style="text-decoration: font-family:"CourierNew", Courier, monospace;font-size:medium;color:#5e82a6;"> Row </a></td>
										<td class="grid-columnHeading" width="120"><a href="<%=request.getContextPath()%>/student?action=Sort&property=lastName&sortDirection=<%=request.getParameter("sortDirection")%>"
											style="text-decoration: font-family:"CourierNew", Courier, monospace;font-size:medium;color:#5e82a6;"> Last Name </a></td>
										<td class="grid-columnHeading" width="100"><a href="<%=request.getContextPath()%>/student?action=Sort&property=firstName&sortDirection=<%=request.getParameter("sortDirection")%>"
											style="text-decoration: font-family:"CourierNew", Courier, monospace;font-size:medium;color:#5e82a6;"> First Name </a></td>
										<td class="grid-columnHeading" width="80"><a href="<%=request.getContextPath()%>/student?action=Sort&property=personType&sortDirection=<%=request.getParameter("sortDirection")%>"
											style="text-decoration: font-family:"CourierNew", Courier, monospace;font-size:medium;color:#5e82a6;"> Type </a></td>
										<td class="grid-columnHeading" width="80"><a href="<%=request.getContextPath()%>/student?action=Sort&property=personStatus&sortDirection=<%=request.getParameter("sortDirection")%>"
											style="text-decoration: font-family:"CourierNew", Courier, monospace;font-size:medium;color:#5e82a6;"> Status </a></td>
										<td class="grid-columnHeading" width="120"><a href="<%=request.getContextPath()%>/student?action=Sort&property=personStatus&sortDirection=<%=request.getParameter("sortDirection")%>"
											style="text-decoration: font-family:"CourierNew", Courier, monospace;font-size:medium;color:#5e82a6;"> Entry Date </a></td>
										<td class="grid-columnHeading" width="120"><a href="<%=request.getContextPath()%>/student?action=Sort&property=personStatus&sortDirection=<%=request.getParameter("sortDirection")%>"
											style="text-decoration: font-family:"CourierNew", Courier, monospace;font-size:medium;color:#5e82a6;"> Date of Birth </a></td>
										<td class="grid-columnHeading"></td>
									</tr>
									<%
										int row = 0;
										//Iterable<Entity> entities = (Iterable<Entity>)session.getAttribute("results");
										ArrayList entities = (ArrayList) session.getAttribute("results");
										Map<String, Object> properties = null;
										if (entities != null)
											for (int i = 0; i < entities.size(); i++) {
												//for (Entity result : entities) {
												Entity result = (Entity) entities.get(i);
												properties = result.getProperties();
												String imageKey = "";
												if (properties.get("imageKey") != null)
													imageKey = properties.get("imageKey").toString();
												SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
												String sEntryDate = "";
												String sBirthDate = "";
												try {
													String entryDate = (String) result.getProperty("entryDate");
													java.util.Date dEntryDate = new java.util.Date(entryDate);
													sEntryDate = dateFormat.format(dEntryDate);
												} catch (Exception e) {
													sEntryDate = "";
												}

												try {
													String birthDate = (String) result.getProperty("dateOfBirth");
													java.util.Date dBirthDate = new java.util.Date(birthDate);
													sBirthDate = dateFormat.format(dBirthDate);
												} catch (Exception e) {
													sBirthDate = "";
												}
									%>
									<%
										if (row == cursor) {
									%>
									<form method="POST" action="<%=request.getContextPath()%>/student">
										<tr>
											<td class="grid-Id"><%=row + 1%></td>
											<td class="grid-results"><%=((String) properties.get("lastName")).toUpperCase()%></td>
											<td class="grid-results"><%=((String) properties.get("firstName")).toUpperCase()%> &nbsp; <%=((String) properties.get("middleInitial")).toUpperCase()%></td>
											<td class="grid-results"><%=properties.get("personType")%></td>
											<td class="grid-results"><%=properties.get("personStatus")%></td>
											<td class="grid-results"><%=sEntryDate%></td>
											<td class="grid-results"><%=sBirthDate%></td>
											<td class="grid-buttons"><input type="submit" name="action" value="View" class="imageButtonProfile" title="View Profile" /> <%
 	if ("Intake".equals(properties.get("personType").toString())) {
 %> <input type="submit" name="action" value="Export Intake" class="imageButtonAccept" title="Approve" /> <input type="submit" name="action" value="Deny Admission" class="imageButtonDeny"
												title="Deny" /> <%
 	}
 %> <input type="submit" name="action" value="Edit" class="imageButtonEdit" title="Edit Resident" /> <input type="submit" name="action" value="Assign Job" class="imageButtonJob" title="Assign Job" />
											 <input type="submit" name="action" value="Upload Photo" class="imageButtonUpload" title="Upload Photo" />
 
 <input type="submit" name="action" value="Documents" class="imageButtonDoc" title="Document Manager" />
 
 <input type="submit" name="action" value="Print Card" class="imageButtonCard" title="Print Card" />
 
  <!----
                                                <input type="submit" name="action" value="Edit" class="buttonAction">
                                                <input type="submit" name="action" value="View" class="buttonAction">
                                                ---></td>
										</tr>
										<input type="hidden" name="id" value="<%=result.getKey().getId()%>" /> <input type="hidden" name="personKey" value="<%=KeyFactory.keyToString(result.getKey())%>" />
									</form>
									<%
										cursor++;
													if (cursor % pageLimit == 0)
														break;
												}
												row++;

												//if (cursor%pageLimit==0) break;
											}
										if (cursor == 0) {
									%>
									<tr>
										<td class="results-bg" colspan="9">no data found</td>
									</tr>
									<%
										}
									%>
								</table>
							</td>
						</tr>
					</table>
				</div> <br>

				<table width="620" cellspacing="0" cellpadding="0" border="0" style="border-collapse: collapse;">

					<tr>
						<td class="results-page-link">
							<%
								if (size / pageLimit != 0) {
							%> <%
 	if ((cursor / pageLimit) > 1) {
 %> <b> <a href="<%=request.getContextPath()%>/jsp/student/profile/results.jsp?action=Search&cursor=<%=cursor - (pageLimit * 2)%>&size=<%=size%>"> << Previous</a></b> <%
 	}
 %> <i>&nbsp;&nbsp;(pages <%=cursor / pageLimit%> of <%=size / pageLimit%>)
						</i>&nbsp;&nbsp; <%
 	if ((cursor / pageLimit) < (size / pageLimit)) {
 %> <b><a href="<%=request.getContextPath()%>/jsp/student/profile/results.jsp?action=Search&cursor=<%=cursor%>&size=<%=size%>">Next >></a></b> <%
 	}
 %> <%
 	} else {
 %> <i>&nbsp;&nbsp;(pages 1 of 1)</i>&nbsp;&nbsp; <%
 	}
 %>
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
<jsp:include page="../../includes/footer.jsp" />
