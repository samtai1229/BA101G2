<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.spots.model.*"%>
<%
SpotsVO spotVO = (SpotsVO) request.getAttribute("spotVO");
%>

<html>
<head>
<title>景點資料新增 - addSpot.jsp</title></head>
<link rel="stylesheet" type="text/css" href="js/calendar.css">
<script language="JavaScript" src="js/calendarcode.js"></script>
<div id="popupcalendar" class="text"></div>

<body bgcolor='white'>

<table border='1' cellpadding='5' cellspacing='0' width='400'>
	<tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
		<td>
		<h3>景點資料新增 - addSpot.jsp</h3>
		</td>
		<td>
		   <a href="<%=request.getContextPath()%>/frontend/spots/select_page.jsp"><img src="images/back1.gif" width="100" height="100" border="1">回首頁</a>
	    </td>
	</tr>
</table>

<h3>新增景點:</h3>
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

<FORM METHOD="post" ACTION="spot.do" name="form1">
<table border="0">

	<tr>
		<td>景點名稱:</td>
		<td><input type="TEXT" name="spname" size="45" 
			value="<%= (spotVO==null)? "我家" : spotVO.getSpname()%>" /></td>
	</tr>

	
	<tr>
		<td>據點編號:</td>
		<td><input type="TEXT" name="locno" size="45"
			value="<%= (spotVO==null)? "L000001" : spotVO.getLocno()%>" /></td>
	</tr>
	
	<tr>
		<td>經度:</td>
		<td><input type="TEXT" name="longitude" size="45"
			value="<%= (spotVO==null)? "121.21" : spotVO.getSplong()%>" /></td>
	</tr>
	
	<tr>
		<td>緯度:</td>
		<td><input type="TEXT" name="latitude" size="45"
			value="<%= (spotVO==null)? "23.25" : spotVO.getSplat()%>" /></td>
	</tr>
	

	
</table>
<br>
<input type="hidden" name="action" value="insert">
<input type="submit" value="送出新增"></FORM>
</body>

</html>
