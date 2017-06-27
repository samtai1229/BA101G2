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
</head>

<style>
	 th, tr{
		/*死都不換行*/
		white-space:nowrap;
	} 
</style>

<body>
	<table id="QueryTable" class="table table-bordred table-striped  table-hover">
		<thead>
			<tr class="QueryTable_TR">
			
				<th>租賃單狀態</th>			
				<th>租賃單編號</th>
				<th>還車據點</th>
				<th>結束時間</th>
				<th>會員編號</th>
				<th>車輛編號</th>
				<th>罰金</th>
				<th>總金額</th>
				<th>交車據點</th>
				<th>起始時間</th>
				<th>備註</th>
				<th>修改訂單</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="roVO" items="${get_for_return_view}">
				<tr class="QueryTable_TR">
				
				
					<c:if test="${roVO.status =='noreturn'}">
						<td>
							<form method="post" action="<%=request.getContextPath()%>/backend/rent_ord/rentOrd.do" target="_blank">						
								<input type="hidden" name="rentno" value="${roVO.rentno}">
								<input type="hidden" name="comeFrom" value="noreturn">
								<input type="hidden" name="action" value="returnform_noreturn">								
								<input type="submit" value="等待還車" class="btn btn-warning"/>
							</form>					
						</td>					 
					</c:if>
					
					<c:if test="${roVO.status =='overtime'}">
						<td>
							<form method="post" action="<%=request.getContextPath()%>/backend/rent_ord/rentOrd.do" target="_blank">						
								<input type="hidden" name="rentno" value="${roVO.rentno}">
								<input type="hidden" name="comeFrom" value="overtime">
								<input type="hidden" name="action" value="returnform_overtime">	
								<input type="submit" value="逾期未還" class="btn btn-danger"/>
							</form>					
						</td>					 
					</c:if>	
				
				
				
					<td><c:out value="${roVO.rentno}" default="無資料" /></td>
					<td><c:out value="${roVO.rlocno}" default="無資料" /></td>
					<td><fmt:formatDate pattern="yyyy-MM-dd" value="${roVO.enddate}" /></td>
					<td><c:out value="${roVO.memno}" default="無資料" /></td>
					<td><c:out value="${roVO.motno}" default="無資料" /></td>
					<td><c:out value="${roVO.fine}" default="無資料" /></td>
					<td><c:out value="${roVO.total}" default="無資料" /></td>					
					<td><c:out value="${roVO.slocno}" default="無資料" /></td>
					<td><fmt:formatDate pattern="yyyy-MM-dd" value="${roVO.startdate}" /></td>
					<td><c:out value="${roVO.note}" default="無資料" /></td>

					<td>
						<form method="post" action="NewFile.jsp">	
							<input type="hidden" name="rentno" value="${roVO.rentno}">
							<input type="submit" value="修改" class="btn btn-default"/>
						</form>						
					</td>	
				
				</tr>
			</c:forEach>
		</tbody>
	</table>

    <script src="<%=request.getContextPath()%>/backend/rent_ord/Modified/motorKanli_for_ro.js"></script>
    <script src="<%=request.getContextPath()%>/backend/rent_ord/Modified/datepicker_for_ro.js"></script>
 	<script src="<%=request.getContextPath()%>/backend/rent_ord/Modified/paging_for_ro.js"></script>		
</body>
</html>