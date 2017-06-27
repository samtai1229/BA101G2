<%@page import="java.sql.Timestamp"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.motor.model.*"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">	


 	<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
 	<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script> 	

	<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.11/jquery-ui.min.js"></script>
	<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css"/>	
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">     
	<link rel="stylesheet" href="<%=request.getContextPath()%>/backend/Modified/backendHP_css.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/backend/Modified/main.css" >
	
	<title>車輛查詢-AutoBike</title>

	<script type="text/javascript">
		$(function(){
			alert(1);
		})
	</script>

</head>

<style>
	 th, tr{
		/*死都不換行*/
		white-space:nowrap;
	} 
</style>

<body>
	<table id="QueryTable" class="table table-bordred table-striped table-hover">
		<thead>
			<tr class="QueryTable_TR">
				<th>租賃單狀態</th>			
				<th>租賃單編號</th>
				<th>交車據點</th>
				<th>起始時間</th>
				<th>會員編號</th>
				<th>車輛編號</th>
				<th>還車據點</th>
				<th>結束時間</th>
				<th>備註</th>
				<th>修改訂單</th>

			</tr>
		</thead>
		<tbody>
			<c:forEach var="roVO" items="${get_for_lease_view}">
				<tr class="QueryTable_TR">

					<c:if test="${(roVO.status == 'unpaid')}">					
						<td>
							<form method="post" action="<%=request.getContextPath()%>/backend/rent_ord/rentOrd.do" target="_blank">	
								<input type="hidden" name="rentno" value="${roVO.rentno}">
								<input type="hidden" name="status" value="unpaid">
								<input type="hidden" name="action" value="leaseform_default">	
								<input type="submit" value="尚未繳費" class="btn btn-primary"/>
							</form>								
						</td>				 
					</c:if>
										
					<c:if test="${(roVO.status =='unoccupied')}">					

					
					<!-- 產生超連結，直接進入表格頁面  -->
					
					<td><c:out value="${roVO.rentno}" default="無資料" /></td>
					<td><c:out value="${roVO.status}" default="無資料" /></td>
					<td><c:out value="${roVO.slocno}" default="無資料" /></td>
					<td><fmt:formatDate pattern="yyyy-MM-dd" value="${roVO.startdate}" /></td>
					<td><c:out value="${roVO.memno}" default="無資料" /></td>
					<td><c:out value="${roVO.motno}" default="無資料" /></td>
					<td><c:out value="${roVO.rlocno}" default="無資料" /></td>
					<td><fmt:formatDate pattern="yyyy-MM-dd" value="${roVO.enddate}" /></td>
					<td><c:out value="${roVO.note}" default="無資料" /></td>
					<td>
						<form method="post" action="NewFile.jsp">	
							<input type="hidden" name="rentno" value="${roVO.rentno}">
							<input type="submit" value="update" class="click2"/>
						</form>						
					</td>
					
					<c:if test="${(roVO.status == 'unpaid')||(roVO.status =='unoccupied')}">					

						<td>
							<form method="post" action="<%=request.getContextPath()%>/backend/rent_ord/rentOrd.do" target="_blank">	
								<input type="hidden" name="rentno" value="${roVO.rentno}">

								<input type="hidden" name="status" value="unoccupied">
								<input type="hidden" name="action" value="leaseform_default">	
								<input type="submit" value="完成繳費" class="btn btn-success"/>
							</form>								
						</td>				 
					</c:if>
					
					<c:if test="${roVO.status == 'available'}">

								<input type="hidden" name="status" value="unoccupied">	
								<input type="submit" value="Wait" class="click2"/>
							</form>								
						</td>				 
					</c:if>

					<c:if test="${roVO.status =='noshow'}">

						<td>
							<form method="post" action="<%=request.getContextPath()%>/backend/rent_ord/rentOrd.do" target="_blank">						
								<input type="hidden" name="rentno" value="${roVO.rentno}">

								<input type="hidden" name="status" value="available">
								<input type="hidden" name="action" value="leaseform_available">
								<input type="submit" value="等待取車" class="btn btn-warning"/>
							</form>					
						</td>					 
					</c:if>	
					
					<c:if test="${roVO.status == 'canceled'}">
						<td>
							<form method="post" action="<%=request.getContextPath()%>/backend/rent_ord/rentOrd.do" target="_blank" target="_blank">						
								<input type="hidden" name="rentno" value="${roVO.rentno}">
								<input type="hidden" name="status" value="canceled">
								<input type="hidden" name="action" value="leaseform_noshow">
								<input type="submit" value="訂單取消" class="btn btn-info"/>
							</form>					
						</td>					 
					</c:if>			

					<c:if test="${roVO.status =='noshow'}">
						<td>
							<form method="post" action="<%=request.getContextPath()%>/backend/rent_ord/rentOrd.do" target="_blank">						
								<input type="hidden" name="rentno" value="${roVO.rentno}">
								<input type="hidden" name="status" value="noshow">
								<input type="hidden" name="action" value="leaseform_noshow">	
								<input type="submit" value="逾期未取" class="btn btn-danger"/>
							</form>					
						</td>					 
					</c:if>

					<td><c:out value="${roVO.rentno}" default="無資料" /></td>
					<td><c:out value="${roVO.slocno}" default="無資料" /></td>
					<td><fmt:formatDate pattern="yyyy-MM-dd" value="${roVO.startdate}" /></td>
					<td><c:out value="${roVO.memno}" default="無資料" /></td>
					<td><c:out value="${roVO.motno}" default="無資料" /></td>
					<td><c:out value="${roVO.rlocno}" default="無資料" /></td>
					<td><fmt:formatDate pattern="yyyy-MM-dd" value="${roVO.enddate}" /></td>
					<td><c:out value="${roVO.note}" default="無資料" /></td>
					<td>
						<form method="post" action="NewFile.jsp">	
							<input type="hidden" name="rentno" value="${roVO.rentno}">
							<input type="submit" value="修改" class="btn btn-default"/>
						</form>						
					</td>

								<input type="hidden" name="status" value="noshow">	
								<input type="submit" value="NoShow" class="click4"/>
							</form>					
						</td>					 
					</c:if>
					
					<c:if test="${roVO.status == 'available'}">
						<td>
							<form method="post" action="<%=request.getContextPath()%>/backend/rent_ord/leaseForm.jsp">						
								<input type="hidden" name="rentno" value="${roVO.rentno}">
								<input type="hidden" name="status" value="available">
								<input type="hidden" name="action" value="availableRoute">
								<input type="submit" value="Available" class="click3"/>
							</form>					
						</td>					 
					</c:if>
					
					

				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<script src="<%=request.getContextPath()%>/backend/rent_ord/Modified/rentOrdNew.js"></script>
    <script src="<%=request.getContextPath()%>/backend/rent_ord/Modified/motorKanli_for_ro.js"></script>
 	<script src="<%=request.getContextPath()%>/backend/rent_ord/Modified/paging_for_ro.js"></script>	
</body>
</html>

