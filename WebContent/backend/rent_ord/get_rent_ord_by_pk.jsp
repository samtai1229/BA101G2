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
.sizeTag{
	height: 22px;
	margin-bottom:3px;
}

.sizeTag:nth-child(4n+1) {background: #EEE;}
.sizeTag:nth-child(4n+2) {background: #EEE;}
#checkBtn{margin-top:10px; text-align:center;}

</style>
<body>

	<%
		RentOrdVO roQueryVO = (RentOrdVO)request.getAttribute("roQueryVO");
	
		Map<String, String> roStatusMap = new HashMap<String, String>();
		roStatusMap.put("unpaid", "待繳費");
		roStatusMap.put("canceled", "取消");
		roStatusMap.put("unoccupied", "準備交車");
		roStatusMap.put("available", "今日取車");	
		roStatusMap.put("noshow", "逾期未取");	
		roStatusMap.put("noreturn", "待還車");	
		roStatusMap.put("overtime", "逾期未還");	
		roStatusMap.put("abnormalclosed", "異常結案");	
		roStatusMap.put("closed", "正常結案");	
		roStatusMap.put("other", "其他");
	%>	
	
	<jsp:useBean id="locSvc" scope="page" class="com.location.model.LocationService"/>
	
	<div>
		<div class="col-xs-12 col-sm-5 sizeTag">租賃單號:</div>
		<div class="col-xs-12 col-sm-7 sizeTag"><c:out value="${roQueryVO.rentno}" /></div>
		<div class="col-xs-12 col-sm-5 sizeTag">會員編號:</div>
		<div class="col-xs-12 col-sm-7 sizeTag"><c:out value="${roQueryVO.memno}" /></div>
		<div class="col-xs-12 col-sm-5 sizeTag">車輛編號:</div>
		<div class="col-xs-12 col-sm-7 sizeTag"><c:out value="${roQueryVO.motorVO.motno}" /></div>
		<div class="col-xs-12 col-sm-5 sizeTag">交車據點:</div>
		<div class="col-xs-12 col-sm-7 sizeTag"><c:out value="${locSvc.getOneLocation(roQueryVO.slocno).locname}營業所" /></div>
	    <div class="col-xs-12 col-sm-5 sizeTag">還車據點:</div>
	    <div class="col-xs-12 col-sm-7 sizeTag"><c:out value="${locSvc.getOneLocation(roQueryVO.rlocno).locname}營業所" /></div>
		<div class="col-xs-12 col-sm-5 sizeTag">起始里程:</div>
		<div class="col-xs-12 col-sm-7 sizeTag"><c:out value="${roQueryVO.milstart}" /></div>
		<div class="col-xs-12 col-sm-5 sizeTag">結束里程:</div>
		<div class="col-xs-12 col-sm-7 sizeTag"><c:out value="${roQueryVO.milend}" /></div>
		<div class="col-xs-12 col-sm-5 sizeTag">填表日期:</div>
		<div class="col-xs-12 col-sm-7 sizeTag"><fmt:formatDate pattern = "yyyy / MM / dd" value = "${roQueryVO.filldate}" /></div>
		<div class="col-xs-12 col-sm-5 sizeTag">起始時間:</div>
		<div class="col-xs-12 col-sm-7 sizeTag"><fmt:formatDate pattern = "yyyy / MM / dd" value = "${roQueryVO.startdate}" /></div>
		<div class="col-xs-12 col-sm-5 sizeTag">結束日期:</div>
		<div class="col-xs-12 col-sm-7 sizeTag"><fmt:formatDate pattern = "yyyy / MM / dd" value = "${roQueryVO.enddate}" /></div>
		<div class="col-xs-12 col-sm-5 sizeTag">還車日期:</div>
		<div class="col-xs-12 col-sm-7 sizeTag"><fmt:formatDate pattern = "yyyy / MM / dd" value = "${roQueryVO.returndate}" /></div>
		<div class="col-xs-12 col-sm-5 sizeTag">罰金:</div>
		<div class="col-xs-12 col-sm-7 sizeTag"><c:out value="${roQueryVO.fine}" /></div>
		<div class="col-xs-12 col-sm-5 sizeTag">總金額:</div>
		<div class="col-xs-12 col-sm-7 sizeTag"><c:out value="${roQueryVO.total}" /></div>
		<div class="col-xs-12 col-sm-5 sizeTag">評價:</div>
		<div class="col-xs-12 col-sm-7 sizeTag"><c:out value="${roQueryVO.rank}" /></div>
		
		<%-- show readable-status with map --%>
		<c:set scope="page" var="temp"><c:out value="${roQueryVO.status}"/></c:set>
		<% String key = String.valueOf(pageContext.getAttribute("temp")); %>
		
		<div class="col-xs-12 col-sm-5 sizeTag">狀態:</div>
		<div class="col-xs-12 col-sm-7 sizeTag"><c:out value="<%=roStatusMap.get(key)%>" /></div>
		<div class="col-xs-12 col-sm-5 sizeTag">備註:</div>
		<div class="col-xs-12 col-sm-7 sizeTag"><c:out value="${roQueryVO.note}" /></div>
	</div>
	


	<div class="col-xs-12 col-sm-12" id="checkBtn">
		<form method="post" action="<%=request.getContextPath()%>/backend/rent_ord/rentOrd.do" target="_blank">
								
			<input type="hidden" name="rentno" value="${roQueryVO.rentno}">
			
	        <c:if test="${(roQueryVO.status == 'unpaid')}">					
				<input type="hidden" name="status" value="unpaid">
				<input type="hidden" name="action" value="leaseform_default">	
				<input type="submit" value="尚未繳費" class="btn btn-primary btn-lg"/>
			</c:if>
			<c:if test="${(roQueryVO.status =='unoccupied')}">					
				<input type="hidden" name="status" value="unoccupied">
				<input type="hidden" name="action" value="leaseform_default">	
				<input type="submit" value="完成繳費" class="btn btn-success btn-lg"/>
			</c:if>				
			<c:if test="${roQueryVO.status == 'available'}">
				<input type="hidden" name="status" value="available">
				<input type="hidden" name="action" value="leaseform_available">
				<input type="submit" value="等待取車" class="btn btn-warning btn-lg"/>
			</c:if>					
			<c:if test="${roQueryVO.status == 'canceled'}">
				<input type="hidden" name="status" value="canceled">
				<input type="hidden" name="action" value="leaseform_noshow">
				<input type="submit" value="訂單取消" class="btn btn-info btn-lg"/>
			</c:if>					
			<c:if test="${roQueryVO.status =='noshow'}">
				<input type="hidden" name="status" value="noshow">
				<input type="hidden" name="action" value="leaseform_noshow">	
				<input type="submit" value="逾期未取" class="btn btn-danger btn-lg"/>
			</c:if>				
			<c:if test="${roQueryVO.status =='noreturn'}">
				<input type="hidden" name="comeFrom" value="noreturn">
				<input type="hidden" name="action" value="returnform_noreturn">								
				<input type="submit" value="等待還車" class="btn btn-warning btn-lg"/>
			</c:if>				
			<c:if test="${roQueryVO.status =='overtime'}">	
				<input type="hidden" name="comeFrom" value="overtime">
				<input type="hidden" name="action" value="returnform_overtime">	
				<input type="submit" value="逾期未還" class="btn btn-danger btn-lg"/>
			</c:if>	
		</form>	
	</div>				
 
</body>
</html>