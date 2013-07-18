<%@ page import="org.faithfarm.dataobjects.Person" %>
<%
	Person person = (Person)request.getAttribute("person");
	if (person==null)
		 person = (Person)session.getAttribute("person");
	
	String state="";
	
	if ("1".equals(request.getParameter("pg")))
		state=person.getAddress().getState();
	if ("3".equals(request.getParameter("pg")))
		state=person.getPersonMisc().getDriversLicenseState();
	if ("7".equals(request.getParameter("pg")))
		state=person.getPersonMisc().getProbationState();
%>
<select id="State" name="state" class="entry">
                    <option value="">Select a State</option>
                    <option value="AK" <% if ("AK".equals(state)) {%>selected<%}%>>Alaska</option>
                    <option value="AL" <% if ("AL".equals(state)) {%>selected<%}%>>Alabama</option>
                    <option value="AR" <% if ("AR".equals(state)) {%>selected<%}%>>Arkansas</option>
                    <option value="AZ" <% if ("AZ".equals(state)) {%>selected<%}%>>Arizona</option>
                    <option value="CA" <% if ("CA".equals(state)) {%>selected<%}%>>California</option>
                    <option value="CO" <% if ("CO".equals(state)) {%>selected<%}%>>Colorado</option>
                    <option value="CT" <% if ("CT".equals(state)) {%>selected<%}%>>Connecticut</option>
                    <option value="DC" <% if ("DC".equals(state)) {%>selected<%}%>>Washington D.C.</option>
                    <option value="DE" <% if ("DE".equals(state)) {%>selected<%}%>>Delaware</option>
                    <option value="FL" <% if ("FL".equals(state)) {%>selected<%}%>>Florida</option>
                    <option value="GA" <% if ("GA".equals(state)) {%>selected<%}%>>Georgia</option>
                    <option value="HI" <% if ("HI".equals(state)) {%>selected<%}%>>Hawaii</option>
                    <option value="IA" <% if ("IA".equals(state)) {%>selected<%}%>>Iowa</option>
                    <option value="ID" <% if ("ID".equals(state)) {%>selected<%}%>>Idaho</option>
                    <option value="IL" <% if ("IL".equals(state)) {%>selected<%}%>>Illinois</option>
                    <option value="IN" <% if ("IN".equals(state)) {%>selected<%}%>>Indiana</option>
                    <option value="KS" <% if ("KS".equals(state)) {%>selected<%}%>>Kansas</option>
                    <option value="KY" <% if ("KY".equals(state)) {%>selected<%}%>>Kentucky</option>
                    <option value="LA" <% if ("LA".equals(state)) {%>selected<%}%>>Louisiana</option>
                    <option value="MA" <% if ("MA".equals(state)) {%>selected<%}%>>Massachusetts</option>
                    <option value="MD" <% if ("MD".equals(state)) {%>selected<%}%>>Maryland</option>
                    <option value="ME" <% if ("ME".equals(state)) {%>selected<%}%>>Maine</option>
                    <option value="MI" <% if ("MI".equals(state)) {%>selected<%}%>>Michigan</option>
                    <option value="MN" <% if ("MN".equals(state)) {%>selected<%}%>>Minnesota</option>
                    <option value="MO" <% if ("MO".equals(state)) {%>selected<%}%>>Missourri</option>
                    <option value="MS" <% if ("MS".equals(state)) {%>selected<%}%>>Mississippi</option>
                    <option value="MT" <% if ("MT".equals(state)) {%>selected<%}%>>Montana</option>
                    <option value="NC" <% if ("NC".equals(state)) {%>selected<%}%>>North Carolina</option>
                    <option value="ND" <% if ("ND".equals(state)) {%>selected<%}%>>North Dakota</option>
                    <option value="NE" <% if ("NE".equals(state)) {%>selected<%}%>>Nebraska</option>
                    <option value="NH" <% if ("NH".equals(state)) {%>selected<%}%>>New Hampshire</option>
                    <option value="NJ" <% if ("NJ".equals(state)) {%>selected<%}%>>New Jersey</option>
                    <option value="NM" <% if ("NM".equals(state)) {%>selected<%}%>>New Mexico</option>
                    <option value="NV" <% if ("NV".equals(state)) {%>selected<%}%>>Nevada</option>
                    <option value="NY" <% if ("NY".equals(state)) {%>selected<%}%>>New York</option>
                    <option value="OH" <% if ("OH".equals(state)) {%>selected<%}%>>Ohio</option>
                    <option value="OK" <% if ("OK".equals(state)) {%>selected<%}%>>Oklahoma</option>
                    <option value="OR" <% if ("OR".equals(state)) {%>selected<%}%>>Oregon</option>
                    <option value="PA" <% if ("PA".equals(state)) {%>selected<%}%>>Pennsylvania</option>
                    <option value="PR" <% if ("PR".equals(state)) {%>selected<%}%>>Puerto Rico</option>
                    <option value="RI" <% if ("RI".equals(state)) {%>selected<%}%>>Rhode Island</option>
                    <option value="SC" <% if ("SC".equals(state)) {%>selected<%}%>>South Carolina</option>
                    <option value="SD" <% if ("SD".equals(state)) {%>selected<%}%>>South Dakota</option>
                    <option value="TN" <% if ("TN".equals(state)) {%>selected<%}%>>Tennessee</option>
                    <option value="TX" <% if ("TX".equals(state)) {%>selected<%}%>>Texas</option>
                    <option value="UT" <% if ("UT".equals(state)) {%>selected<%}%>>Utah</option>
                    <option value="VA" <% if ("VA".equals(state)) {%>selected<%}%>>Virginia</option>
                    <option value="VT" <% if ("VT".equals(state)) {%>selected<%}%>>Vermont</option>
                    <option value="WA" <% if ("WA".equals(state)) {%>selected<%}%>>Washington</option>
                    <option value="WI" <% if ("WI".equals(state)) {%>selected<%}%>>Wisconsin</option>
                    <option value="WV" <% if ("WV".equals(state)) {%>selected<%}%>>West Virginia</option>
                    <option value="WY" <% if ("WY".equals(state)) {%>selected<%}%>>Wyoming</option>
</select>
