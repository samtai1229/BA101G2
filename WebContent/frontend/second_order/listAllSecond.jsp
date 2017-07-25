<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.motor.model.*" %>
<%@ page import="com.motor_model.model.*" %>
<%@ page import="java.util.*" %>
<% MotorService moSvc = new MotorService();
   List<MotorVO> list = moSvc.getAll();
   MotorModelService mmSvc = new MotorModelService();
   List<MotorModelVO> mlist = mmSvc.getAll();
   pageContext.setAttribute("list", list);
   pageContext.setAttribute("mlist", mlist);

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
<title>Insert title here</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
</head>
<body>


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
	 				<li><a href="<%=request.getContextPath()%>/index.jsp">回首頁</a></li>
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
<table class="table table-hover" border='1' bordercolor='#CCCCFF' width='100%'>
	<tr>
		<th style="text-align:center">相片</th>
<!-- 		<th style="text-align:center">車輛編號</th> -->
<!-- 		<th style="text-align:center">車牌號碼</th> -->
<!-- 		<th style="text-align:center">引擎編號</th> -->
<!-- 		<th style="text-align:center">出廠日期</th> -->
<!-- 		<th style="text-align:center">里程數</th> -->
<!-- 		<th style="text-align:center">據點編號</th> -->
<!-- 		<th style="text-align:center">狀態</th> -->

	
	</tr>
<%-- 					begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>" --%>
	<c:forEach var="motorVO" items="${list}" >
	  <c:if test="${ motorVO.status=='seconsale'}">
		<tr align='center' valign='middle'>
		
			<td><img style="width:300px" src="<%=request.getContextPath()%>/backend/motor_model/mmReader.do?modtype=${motorVO.modtype}"></td>
<%-- 			<td>${motorVO.motno}</td> --%>
<%-- 			<td>${motorVO.plateno}</td> --%>
<%-- 			<td>${motorVO.engno}</td> --%>
<%-- 			<td><fmt:formatDate value="${motorVO.manudate}" pattern="yyyy年MM月dd日"/>		</td> --%>
			
<%-- 			<td>${motorVO.mile}</td>	 --%>
<%-- 			<td>${motorVO.locno}</td>	 --%>
<%-- 			<td>${motorVO.status}</td>	 --%>
			
			<td valign='middle'>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/second_order/SecOrd.do">
			     <input class="btn btn-success" type="submit" value="我有興趣"> 
			     <input type="hidden" name="motno" value="${motorVO.motno}">
			     <input type="hidden" name="location"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller--><!-- 目前尚未用到  -->
			     <input type="hidden" name="action"	value="I_WANT_IT"></FORM>
			</td>
		</tr>
		</c:if>
	</c:forEach>
</table>



<script src="https://code.jquery.com/jquery.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
</html>