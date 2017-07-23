<%@page import="java.sql.Timestamp"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.rent_ord.model.*"%>
<!DOCTYPE html>
<html>

<head></head>

<style>

	 th, tr{ /*不換行*/ white-space:nowrap; }
	td{ vertical-align: middle!important; }
	
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
				
					<jsp:useBean id="locSvc" scope="page" class="com.location.model.LocationService"/>
					<td><c:out value="${roVO.rentno}" default="無資料" /></td>
					<td><c:out value="${locSvc.getOneLocation(roVO.rlocno).locname}" default="無資料" /></td>
					<td><fmt:formatDate pattern="yyyy-MM-dd hh:mm" value="${roVO.enddate}" /></td>
					<td><c:out value="${roVO.memno}" default="無資料" /></td>
					<td><c:out value="${roVO.motorVO.motno}" default="無資料" /></td>
					<td><c:out value="${roVO.fine}" default="無資料" /></td>
					<td><c:out value="${roVO.total}" default="無資料" /></td>					
					<td><c:out value="${locSvc.getOneLocation(roVO.slocno).locname}" default="無資料" /></td>
					<td><fmt:formatDate pattern="yyyy-MM-dd" value="${roVO.startdate}" /></td>
					<td><c:out value="${roVO.note}" default="無資料" /></td>

					<td>
						<form method="POST" target="print_popup" 
      				  		  action="<%=request.getContextPath()%>/backend/rent_ord/rentOrd.do" 
       						  onsubmit="window.open('about:blank','print_popup','width=1000,height=900');">
							<input type="hidden" name="rentno" value="${roVO.rentno}">
							<input type="hidden" name="action" value="query_for_update">
							<input type="submit" class="btn btn-default" value="修改">
						</form>						
					</td>	
				
				</tr>
			</c:forEach>
		</tbody>
	</table>
		
</body>
</html>