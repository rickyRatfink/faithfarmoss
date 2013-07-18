<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>

<jsp:include page="../includes/header.jsp"/>
<jsp:include page="../includes/menu/index.jsp" flush="true"/>

<%
	ArrayList locations = (ArrayList)session.getAttribute("locations");
	if (locations==null) locations = new ArrayList();	
	
	
		
%>
		<!----Main Content--->
		<td valign="top">
			<table width="820" border="0" cellpadding="0" cellspacing="0">
				<tr><td><br><!--img src="<%=request.getContextPath()%>/images/header_top.png"--></td></tr>
				<tr>
				<td class="content-body">
						<div class="content">
						<img src="<%=request.getContextPath()%>/images/titles/intake-application.jpg">
						<br>
						<jsp:include page="../includes/errors.jsp" flush="true"/>
						
						<!----Search Form ------->
						
						<form action="<%=request.getContextPath()%>/intake?page=0" >
							<table width="70%" height="400" border=0 cellpadding=0 cellspacing=0>
							<tr>
								<td height="350">
								<textarea  class="intake">
Welcome to our website. If you continue to browse and use this website you are agreeing to comply with and be bound by the following terms and conditions of use, which together with our privacy policy govern Faith Farm�s relationship with you in relation to this website.The term �Faith Farm� or �us� or �we� refers to the owner of the website whose registered office is YOUR BUSINESS ADDRESS. The term �you� refers to the user or viewer of our website.The use of this website is subject to the following terms of use:
The content of the pages of this website is for your general information and use only. It is subject to change without notice.
Neither we nor any third parties provide any warranty or guarantee as to the accuracy, timeliness, performance, completeness or suitability of the information and materials found or offered on this website for any particular purpose. You acknowledge that such information and materials may contain inaccuracies or errors and we expressly exclude liability for any such inaccuracies or errors to the fullest extent permitted by law.
Your use of any information or materials on this website is entirely at your own risk, for which we shall not be liable. It shall be your own responsibility to ensure that any products, services or information available through this website meet your specific requirements.
This website contains material which is owned by or licensed to us. This material includes, but is not limited to, the design, layout, look, appearance and graphics. Reproduction is prohibited other than in accordance with the copyright notice, which forms part of these terms and conditions.
All trade marks reproduced in this website which are not the property of, or licensed to, the operator are acknowledged on the website.
Unauthorised use of this website may give rise to a claim for damages and/or be a criminal offence.
From time to time this website may also include links to other websites. These links are provided for your convenience to provide further information. They do not signify that we endorse the website(s). We have no responsibility for the content of the linked website(s).
You may not create a link to this website from another website or document without Faith Farm�s prior written consent.
Your use of this website and any dispute arising out of such use of the website is subject to the laws of the United States of America.
								</textarea>				
								</td>
								<tr>
									<td	align="center">
										<input type="checkbox" name="accept" value="Y">
										<i>I accept the terms of use and wish to continue to the intake application.</i><br>			
									</td>
								</tr>
								<tr>
									<td align="center">
										<input type="submit" name="" value="Next" class="buttonNext">
									</td>
								</tr>
							
							</table>
							<input type="hidden" name="action" value="tos"/>
						</form>			
						</div>
									
						</td></tr>
					</table>
		</td>
	</tr>	
	
</div>
</table>

<jsp:include page="../includes/footer.jsp"/>


