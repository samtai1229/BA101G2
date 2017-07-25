<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.sec_ord.model.*"%>
<%@ page import="java.util.*" %> 
<%
    String status = (String)request.getAttribute("status");
	SecOrdVO soVO = (SecOrdVO) request.getAttribute("soVO"); //EmpServlet.java (Concroller), 存入req的empVO物件 (包括幫忙取出的empVO, 也包括輸入資料錯誤時的empVO物件)
	String[] statusArray = {"paid","unpaid","closed","other"};
	request.setAttribute("statusArray", statusArray);
	session.setAttribute("soVO", soVO);
	pageContext.setAttribute("status", status);
%>
<html>
<head>
<title>訂單資料修改 </title></head>
<link rel="stylesheet" type="text/css" href="js/calendar.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">

<div id="popupcalendar" class="text"></div>

<body bgcolor='white'>

<nav class="navbar navbar-default" role="navigation">
	 	<div class="container">
	 		<div class="navbar-header">
	 			<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
	 				<span class="sr-only">選單切換</span>
	 				<span class="icon-bar"></span>
	 				<span class="icon-bar"></span>
	 				<span class="icon-bar"></span>
	 			</button>
	 			<a class="navbar-brand" href="#">二手車交易區</a>
	 		</div>
	 		
	 		<!-- 手機隱藏選單區 -->
	 		<div class="collapse navbar-collapse navbar-ex1-collapse">
	 			<!-- 左選單 -->
	 			<ul class="nav navbar-nav">
	 				<li class="active"><a href="#">選擇你喜歡的車</a></li>
	 				<li><a href="<%=request.getContextPath()%>/backend/index.jsp">回首頁</a></li>
	 			</ul>
	 		
	 			
	 		
	 			<!-- 右選單 -->
	 			<c:if test="${not empty memVO}">
	 			<ul class="nav navbar-nav navbar-right">
	 				<li><a href="#">${memVO.memname} 您好</a></li>
	 				<li><a href="<%=request.getContextPath()%>/backend/member/member.do?action=logout"
              								data-toggle="modal">登出</a></li>
	 			</ul>
	 			</c:if>
	 			
	 		</div>
	 		<!-- 手機隱藏選單區結束 -->
	 	</div>
	 </nav> 
<h3>訂單資料修改:</h3>
<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font color='red'>請修正以下錯誤:
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li>${message}</li>
		</c:forEach>
	</ul>
	</font>
</c:if>

<FORM METHOD="post" ACTION="SecOrd.do" name="form1">
<div class="col-xs-12 col-sm-6 col-sm-offset-3"></div>
<table class="table table-hover" border='1' bordercolor='#CCCCFF' >
	<tr>
		<td>訂單編號:<font color=red><b>*</b></font></td>
		<td>${soVO.sono}</td>
	</tr>
	<tr>
		<td>會員編號:</td>
		<td>${soVO.memno}</td>
	</tr>
	
	<tr>
		<td>車輛編號:</td>
		<td>${soVO.motorno}</td>
	</tr>
	
	<tr>
		<td>訂單時間:</td>
		<td><fmt:formatDate pattern = "yyyy-MM-dd hh:mm" 
         value = "${soVO.buildtime}" /></td>
	</tr>
	<tr>
		<td>訂單狀態:</td>
		<td><input readonly type="text" value="${status}"></td>
	</tr>
</table>
<br>
<input type="hidden" name="action" value="update">
<input type="hidden" name="status" value="${status}">
<input type="hidden" name="memno" value="${soVO.memno}">
<input type="hidden" name="motno" value="${soVO.motorno}">
<input type="hidden" name="sono" value="${soVO.sono}">
<input type="hidden" name="sodate" value="<fmt:formatDate pattern = "yyyy-MM-dd hh:mm:ss" 
value = "${soVO.buildtime}" />">
<input type="submit" value="確認修改"></FORM>

</body>
<script src="https://code.jquery.com/jquery.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script language="JavaScript" src="js/calendarcode.js"></script>
</html>
