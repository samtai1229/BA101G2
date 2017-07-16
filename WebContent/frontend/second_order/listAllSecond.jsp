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
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
<title>Insert title here</title>
</head>
<body>
<div class="col-xs-12 col-sm-12">
<table  border='1' cellpadding='5' cellspacing='0' width='100%'>
	<tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
		<td>
		<h3>二手車出售區</h3>
		<a href="<%=request.getContextPath()%>/index.jsp"><img src="images/back1.gif" width="100" height="32" border="0">回首頁</a>
		</td>
	</tr>
</table>
</div>
<table border='1' bordercolor='#CCCCFF' width='100%'>
	<tr>
		<th>車輛編號</th>
		<th>車型編號</th>
		<th>車牌號碼</th>
		<th>圖片</th>
		<th>引擎編號</th>
		<th>出廠日期</th>
		<th>Mile</th>
		<th>Locno</th>
<!-- 		<th>status</th> -->
		<th>note</th>
	</tr>
<%-- 					begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>" --%>
	<c:forEach var="motorVO" items="${list}" >
	  <c:if test="${ motorVO.status=='seconsale'}">
		<tr align='center' valign='middle'>
		
			<td>${motorVO.motno}</td>
			<td>${motorVO.modtype}</td>
			<td>${motorVO.plateno}</td>
			<td><img src="<%=request.getContextPath()%>/backend/motor_model/mmReader.do?modtype=${motorVO.modtype}" width='200' height='180'></td>
			<td>${motorVO.engno}</td>
			<td>${motorVO.manudate}</td>		
			<td>${motorVO.mile}</td>	
			<td>${motorVO.locno}</td>	
<%-- 			<td>${motorVO.status}</td>	 --%>
			<td>${motorVO.note}</td>	
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/second_order/SecOrd.do">
			     <input type="submit" value="我要購買"> 
			     <input type="hidden" name="sono" value="${soVO.sono}">
			     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller--><!-- 目前尚未用到  -->
			     <input type="hidden" name="action"	value="I_WANT_IT"></FORM>
			</td>
		</tr>
		</c:if>
	</c:forEach>
</table>




</body>
</html>