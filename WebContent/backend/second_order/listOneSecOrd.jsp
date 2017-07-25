<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ page import="com.sec_ord.model.*"%>
<%@ page import="com.location.model.*"%>
<%-- 此頁練習採用 Script 的寫法取值 --%>

<%-- 取出 Concroller EmpServlet.java已存入request的EmpVO物件--%>
<%SecOrdVO soVO = (SecOrdVO) request.getAttribute("soVO");%>

<%-- 取出 對應的LocationVO物件--%>
<%
 // DeptService deptSvc = new DeptService();
 // DeptVO deptVO = deptSvc.getOneDept(empVO.getDeptno());
%>
<html>
<head>
<title>資料 - listOneSecOrd.jsp</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
</head>
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
	 			<a class="navbar-brand" href="#">二手車訂單資料</a>
	 		</div>
	 		
	 		<!-- 手機隱藏選單區 -->
	 		<div class="collapse navbar-collapse navbar-ex1-collapse">
	 			<!-- 左選單 -->
	 			<ul class="nav navbar-nav">
	 				<li class="active"><a href="#">查詢特定訂單</a></li>
	 				<li><a href="<%=request.getContextPath()%>/backend/index.jsp">回首頁</a></li>
	 			</ul>
	 		
	 			
	 		
	 			<!-- 右選單 -->
	 			<c:if test="${not empty adminisVO}">
	 			<ul class="nav navbar-nav navbar-right">
	 				<li><a href="#">${adminisVO.name} 您好</a></li>
	 				<li><a href="<%=request.getContextPath()%>/backend/member/member.do?action=logout"
              								data-toggle="modal">登出</a></li>
	 			</ul>
	 			</c:if>
	 			
	 		</div>
	 		<!-- 手機隱藏選單區結束 -->
	 	</div>
	 </nav> 
<table  border="1" class="table table-striped table-inverse table-hover" bordercolor='#CCCCFF' width='100%'>
	<tr>
		<th style="text-align:center">二手車訂單編號</th>
		<th style="text-align:center">會員編號</th>
		<th style="text-align:center">車輛編號</th>
		<th style="text-align:center">建單時間</th>
		<th style="text-align:center">狀態</th>
		
	</tr>
	<tr align='center' valign='middle'>
		<td><%=soVO.getSono()%></td>
		<td><%=soVO.getMemno()%></td>
		<td><%=soVO.getMotorno()%></td>
		<td><%=soVO.getBuildtime()%></td>
		<td><%=soVO.getStatus()%></td>
	
		
	</tr>
</table>
<script src="https://code.jquery.com/jquery.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
</html>
