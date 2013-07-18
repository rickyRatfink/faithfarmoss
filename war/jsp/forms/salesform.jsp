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
	
</script>

<%
		Entity e = (Entity)request.getAttribute("Customer");	
		String firstName="";
		String lastName="";
		String addressLine1="";
		String city="";
		String zipcode="";
		String cell="";
		String home="";
		String work="";
		String cell1="",cell2="",cell3="";
		String work1="",work2="",work3="";
		String home1="",home2="",home3="";
		Integer iSize = (Integer)request.getAttribute("size");
		int size=0;
		Long customerId = new Long("0");
		
		if (iSize!=null)
			size = iSize.intValue();
		else 
			size=0;
																
		if (e!=null) {
			firstName=(String)e.getProperty("firstName");
			lastName=(String)e.getProperty("lastName");
			addressLine1=(String)e.getProperty("addressLine1");
			city=(String)e.getProperty("city");
			zipcode=(String)e.getProperty("zipcode");
			cell=(String)e.getProperty("cell");
			home=(String)e.getProperty("home");
			work=(String)e.getProperty("work");
			
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
		}
		String deliveryDate=request.getParameter("date");
		if (deliveryDate==null) deliveryDate="";
			

	   
	
		
%>

		<!----Main Content--->
		<td>
			<table width="820" border="0" cellpadding="0" cellspacing="0">
				<tr><td><br><!--img src="<%=request.getContextPath()%>/images/header_top.png"--></td></tr>
				<tr>
				<td class="content-body">
						<div class="content">
						<img src="<%=request.getContextPath()%>/images/titles/nf-order.jpg">
						<br>
						<!----Search Form ------->
						
						<form method="POST" action="<%=request.getContextPath()%>/sales">
							<jsp:include page="../includes/errors.jsp" flush="true"/>
							<table width="70%" border=0 cellpadding=0 cellspacing=0>
							
							<tr>
								<td >
								 <table width="350" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
							             
							                
							                <tr>
							                 <td width="170" class="field">First Name</td>
							                 <td width="150" class="field">Last Name</td>
							                 <td width="30"></td>
							               </tr>
							               	
							               <tr>
							               	 <td><input name="firstName" value="<%=firstName%>" maxlength="20" size="20" class="entry"></td>
							               	 <td><input name="lastName" value="<%=lastName%>" maxlength="30" size="30" class="entry"></td>
							               	 <td>
							               	 	<input type="image" name="action" value="Search Customer" src="<%=request.getContextPath()%>/images/search.jpg" border="0"></td>
							               </tr> 
										   					               	
							             
							             
							           </table>
								
								
									
								</td>
							</tr>
							<tr>
								<td class="field" >Address</td>
							</tr>
							<tr>
								<td >
									<input name="address" value="<%=addressLine1%>" maxlength="80" size="80" class="entry">
								</td>
							</tr>
							<tr>
								<td class="field">City</td>
							</tr>
							<tr>
								<td>
									<input name="city" value="<%=city%>" maxlength="30" size="30" class="entry">
								</td>
							</tr>
							<tr>
								<td class="field">Zipcode</td>
							</tr>
							<tr>
								<td>
									<input name="zipcode" value="<%=zipcode%>" maxlength="10" size="10" class="entry">
								</td>
							</tr>
							<tr>
								<td class="field">Home Phone</td>
							</tr>
							<tr>
										<td>
											(<input name="home_areacode" value="<%=home1%>" maxlength="3" size="3" class="entry">)
											<input name="home_first3" value="<%=home2%>" maxlength="3" size="3" class="entry">&nbsp;-
											<input name="home_last4" value="<%=home3%>" maxlength="4" size="4" class="entry">
										</td>
							</tr>
							<tr>
								<td class="field">Cell Phone</td>
							</tr>
							<tr>
										<td>
											(<input name="cell_areacode" value="<%=cell1%>" maxlength="3" size="3" class="entry">)
											<input name="cell_first3" value="<%=cell2%>" maxlength="3" size="3" class="entry">&nbsp;-
											<input name="cell_last4" value="<%=cell3%>" maxlength="4" size="4" class="entry">
										</td>
							</tr>
							<tr>
								<td class="field">Work Phone</td>
							</tr>
							<tr>
										<td>
											(<input name="work_areacode" value="<%=work1%>" maxlength="3" size="3" class="entry">)
											<input name="work_first3" value="<%=work2%>" maxlength="3" size="3" class="entry">&nbsp;-
											<input name="work_last4" value="<%=work3%>" maxlength="4" size="4" class="entry">
										</td>
						    </tr>
							<tr>
								<td class="field" >New Furniture Department</td>								
							</tr>
							<tr>
								<td>
									<% String farm=(String)session.getAttribute("farm"); %>
									<select class="entry" name="farmstore">
										
										<option value="FTL" <% if ("Fort Lauderdale".equals(farm)) { %>selected<% } %>>Fort Lauderdale, FL</option>
										<option value="BYN" <% if ("Boynton Beach".equals(farm)) { %>selected<% } %>>Boynton Beach, FL</option>
										<option value="OKE" <% if ("Okeechobee".equals(farm)) { %>selected<% } %>>Okeechobee, FL</option>
									</select>
								</td>
							</tr>
							<tr>
								<td class="field" >Delivery Date</td>								
							</tr>
							
							<tr>
								<td>
									 <div><input name="date" class="tcal" value="<%=deliveryDate%>" /></div>
								</td>
							</tr> 
							
							<tr>
								<td>
									<br><br>
									
							           <table width="100%" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
							             
							                <tr>
							                 <td colspan="4" class="results-section">Customer Order</td>
							                </tr>
							                <tr>
							                 <td width="30" class="results-heading">ITEM#</td>
							                 <td width="20" class="results-heading">QUANTITY</td>
							                 <td width="200" class="results-heading">DESCRIPTION</td>
							                 <td width="50" class="results-heading">PRICE</td>
							                </tr>
							               
							              <% 
							              double total=0.00;
										  
													
							              for (int i=0;i<size+1;i++) { 
								              
								           		Entity item = (Entity)request.getAttribute("Item"+i);
		
												String itemNumber="";
												String itemName="";
												String price="0.00";
												String qty="0";
												Long id=new Long("0");
												
												if (item!=null)	 {
													id=(Long)item.getKey().getId();
													itemNumber=(String)item.getProperty("itemNumber");
													itemName=(String)item.getProperty("itemName");
													price=(String)item.getProperty("price");
													qty=request.getParameter("qty"+i);
													if (itemNumber==null) itemNumber="";
											   		if (itemName==null) itemName="";
											   		if (qty==null) qty="0";
											   		if (price==null) price="0.00";	
												
													total+= (Double.valueOf(price).doubleValue())*(Integer.valueOf(qty).intValue());
											   }   
								              
								          %>
							              <tr>
							               	 <td width="80" class="order-item-bg">
							               	 	<% if (i<size) { %>
							               	 		<input type="hidden" name="itemNumber<%=i%>" value="<%=itemNumber%>" ><%=itemNumber%>&nbsp;&nbsp;
							               	 	<% } else { %>
							               	 	   <input name="itemNumber<%=i%>" size="10" maxlength="10" value="<%=itemNumber%>" class="entry" >
							               	 	<% } %>
							               	 	<% if (i<size) { %>
							               	 		<input type="image" name="action" value="Delete Item" src="<%=request.getContextPath()%>/images/delete.gif"></td>
							               	 	<% } else { %>
							               	 		<input type="image" name="action" value="Find Item" src="<%=request.getContextPath()%>/images/search.jpg"></td>
							               	 	<% } %>
							               	 </td>
							               	 
							               	 <td class="order-bg" >
							               	 <% if (i<size) { %>
							               	    <input type="hidden" name="qty<%=i%>" size="2" maxlength="2" value="<%=qty%>" class="entry"><%=qty%>
							               	 <% } else { %>
							               	 	<input name="qty<%=i%>" size="2" maxlength="2" value="<%=qty%>" class="entry" ></td>
							               	 <% } %>
							               	 </td>
							               	 <td class="order-item-bg"><%=itemName.toUpperCase()%></td>
							               	 <td class="order-item-bg"><fmt:formatNumber value="<%=price%>" type="currency" /></td>
							              </tr> 
							              
							              <input type="hidden" name="id<%=i%>" value="<%=id%>"/>
							              <% } %>
							             
							              <tr>
							              <td colspan="4" height="25" valign="top" align="right">
							                <!--<input type="image" name="action"  value="Add" src="<%=request.getContextPath()%>/images/buttons/addtocart.jpg">-->
						  		    		<input type="hidden" name="size" value="<%=++size%>"/>
							              </td>
							              </tr>
							             
							              <tr>
							               	 <td></td>
							               	 <td></td>
							               	 <td align="right" class="total">Sub-Total</td>
							               	 <td align="right" class="total"><fmt:formatNumber value="<%=total%>" type="currency" /></td>
							               </tr> 
							               <tr>
							               	 <td></td>
							               	 <td></td>
							               	 <td align="right" class="total">Tax (7%)</td>
							               	 <td align="right" class="total"><fmt:formatNumber value="<%=total*.07%>" type="currency" /></td>
							               </tr> 
							               <tr>
							               	 <td></td>
							               	 <td></td>
							               	 <td align="right" class="total"><b>Total</b></td>
							               	 <td align="right" class="total"><b><fmt:formatNumber value="<%=total+(total*.07)%>" type="currency" /></b></td>
							               </tr> 
							               
							           </table>
			  							
			  					</td>
			  				</tr>		
							<tr>
								<td  height="60" valign="bottom" align="left">
									<input type="image" name="action"  value="Save Order" src="<%=request.getContextPath()%>/images/buttons/save.jpg">
						  		    <input type="image" name="action"  value="Print Order Form" src="<%=request.getContextPath()%>/images/buttons/print.jpg">
						  		</td>
							</tr>
							
							</table>
						
						</form>			
						
						<!----End Form----------->
						</div>
									
				</td></tr>
				<tr><td><!--img src="<%=request.getContextPath()%>/images/header_btm.png"--></td></tr>
			</table>
		</td>
	</tr>	
	
</div>
</table>

<jsp:include page="../includes/footer.jsp"/>


