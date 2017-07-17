<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.motor.model.*" %>
<%@ page import="java.util.*" %>
<% MotorService moSvc = new MotorService();
   List<MotorVO> list = moSvc.getAll();
   pageContext.setAttribute("list", list);

%>
<jsp:useBean id="mmSvc" scope="page" class="com.motor_model.model.MotorModelService"/>
<jsp:useBean id="locSvc" scope="page" class="com.location.model.LocationService"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
<title>Insert title here</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">

</head>
<body>
<div class="table-responsive">
<table class="table table-bordered " style="border-color:black; border-style:solid;">
	<tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
		<td>
		<h3>二手車出售區</h3>
		<a href="<%=request.getContextPath()%>/index.jsp"><img src="images/back1.gif" width="100" height="32" border="0">回首頁</a>
		</td>
	</tr>
</table>
</div>
<div class="table-responsive">
<table class="table-bordered table table-hover" border='1' bordercolor='#CCCCFF' width='100%'>
	<tr>
<!-- 		<th>車輛編號</th> -->
<!-- 		<th>車型編號</th> -->
<!-- 		<th>車牌號碼</th> -->
		<th style="text-align:center;">圖片</th>
		<th style="text-align:center;">廠牌</th>
		<th style="text-align:center;">型號</th>
<!-- 		<th style="text-align:center;" width="80">排氣量</th> -->
		<th style="text-align:center;">介紹</th>
<!-- 		<th>引擎編號</th> -->
<!-- 		<th>出廠日期</th> -->
<!-- 		<th>里程數</th> -->
		<th style="text-align:center;">售價(NTD)</th>
<!-- 		<th>據點</th> -->
<!-- 		<th>status</th> -->
<!-- 		<th>note</th> -->
	</tr>
<%-- 					begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>" --%>
	<c:forEach var="motorVO" items="${list}" >
	  <c:if test="${ motorVO.status=='seconsale'}">
		<tr align='center' valign='middle'>
		
<%-- 			<td>${motorVO.motno}</td> --%>
<%-- 			<td>${motorVO.modtype}</td> --%>
<%-- 			<td>${motorVO.plateno}</td> --%>
			<td><img src="<%=request.getContextPath()%>/backend/motor_model/mmReader.do?modtype=${motorVO.modtype}" width='200' height='180'></td>
           
<%-- 			<td>${motorVO.engno}</td> --%>
<%-- 			<td><fmt:formatDate value="${motorVO.manudate}" pattern="yyyy年MM月dd日"/></td>		 --%>
<%-- 			<td>${motorVO.mile}</td> --%>
		 	<c:forEach var="mm" items="${mmSvc.all}">
			  <c:if test="${motorVO.modtype==mm.modtype}">
			  <td>${mm.brand}</td>	
			   <td>${mm.name}</td>	
<%-- 			   <td>${mm.displacement}</td> --%>
			    <td height="200"><span>${mm.intro}</span></td>	
			   <td>${mm.saleprice}</td>	
			  </c:if>
			  
			</c:forEach>	
		
<%-- 			<c:forEach var="loc" items="${locSvc.all}"> --%>
<%-- 			  <c:if test="${motorVO.locno==loc.locno}"> --%>
<%-- 			   <td>${loc.locname}</td>	 --%>
<%-- 			  </c:if> --%>
			  
<%-- 			</c:forEach>	 --%>
<%-- 			<td>${motorVO.status}</td>	 --%>
<%-- 			<td>${motorVO.note}</td>	 --%>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/SaleOnOff.do">
			     <input type="submit" value="我想了解"> 
			     <input type="hidden" name="motno" value="${motorVO.motno}">
			     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller--><!-- 目前尚未用到  -->
			     <input type="hidden" name="action"	value="I_WANT_IT"></FORM>
			</td>
		</tr>
		</c:if>
	</c:forEach>
</table>
</div>



</body>
</html>