<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.sec_ord.model.*"%>
<%@ page import="java.util.*" %> 
<%
	SecOrdVO soVO = (SecOrdVO) request.getAttribute("soVO"); //EmpServlet.java (Concroller), 存入req的empVO物件 (包括幫忙取出的empVO, 也包括輸入資料錯誤時的empVO物件)
	String[] statusArray = {"paid","unpaid","closed","other"};
	request.setAttribute("statusArray", statusArray);
%>
<html>
<head>
<title>二手車訂單資料修改 - update_SecOrd_input.jsp</title></head>
<link rel="stylesheet" type="text/css" href="js/calendar.css">
<script language="JavaScript" src="js/calendarcode.js"></script>
<div id="popupcalendar" class="text"></div>

<body bgcolor='white'>

<table border='1' cellpadding='5' cellspacing='0' width='400'>
	<tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
		<td>
		<h3>資料修改 - update_SecOrd_input.jsp</h3>
		<a href="<%=request.getContextPath()%>/frontend/second_order/select_page.jsp"><img src="images/back1.gif" width="100" height="32" border="0">回首頁</a></td>
	</tr>
</table>

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
<table border="0">
	<tr>
		<td>訂單編號:<font color=red><b>*</b></font></td>
		<td>${soVO.sono}</td>
	</tr>
	<tr>
		<td>會員編號:</td>
		<td><input type="TEXT" name="memno" size="45" value="${soVO.memno}" /></td>
	</tr>
	
	<tr>
		<td>車輛編號:</td>
		<td><input type="TEXT" name="motno" size="45" value="${soVO.motorno}" /></td>
	</tr>
	
	<tr>
		<td>訂單時間:</td>
		<td><fmt:formatDate pattern = "yyyy-MM-dd hh:mm" 
         value = "${soVO.buildtime}" /></td>
	</tr>
	<tr>

	
		<td>訂單狀態:</td>
		<td><select size="1" name="status">
			<option disabled selected value="${soVO.status}">${soVO.status}
			<c:forEach var="s" items="${statusArray}">
			<c:if test="${soVO.status!=s}">
			<option  value="${s}">${s}
			</c:if>
			 
			</c:forEach>
       	  
		</select></td>
	</tr>
	



</table>
<br>
<input type="hidden" name="action" value="update">
<input type="hidden" name="sono" value="${soVO.sono}">
<input type="hidden" name="sodate" value="<fmt:formatDate pattern = "yyyy-MM-dd hh:mm:ss" 
         value = "${soVO.buildtime}" />">
<input type="submit" value="送出修改"></FORM>

</body>
</html>
