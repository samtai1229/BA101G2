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
</head>
<body>

<div class="col-xs-12 col-sm-12">
<table  class="table table-hover" border='1' width='100%'>
	<tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
		<td>
		<h3>二手車出售區</h3>
		<a href="<%=request.getContextPath()%>/index.jsp"><img src="images/back1.gif" width="100" height="32" border="0">回首頁</a>
		</td>
	</tr>
</table>
</div>
<table class="table table-hover" border='1' bordercolor='#CCCCFF' width='100%'>
	<tr align='center' valign='middle'>
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
			
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/second_order/SecOrd.do">
			     <input type="submit" value="我有興趣"> 
			     <input type="hidden" name="motno" value="${motorVO.motno}">
			     <input type="hidden" name="location"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller--><!-- 目前尚未用到  -->
			     <input type="hidden" name="action"	value="I_WANT_IT"></FORM>
			</td>
		</tr>
		</c:if>
	</c:forEach>
</table>




</body>
</html>