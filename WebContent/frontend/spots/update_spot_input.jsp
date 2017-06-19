<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.spots.model.*"%>
<%
	SpotsVO spotVO = (SpotsVO) request.getAttribute("spotVO"); //EmpServlet.java (Concroller), 存入req的empVO物件 (包括幫忙取出的empVO, 也包括輸入資料錯誤時的empVO物件)
%>
<html>
<head>
<title>景點資料修改 - update_spot_input.jsp</title></head>
<link rel="stylesheet" type="text/css" href="js/calendar.css">
<script language="JavaScript" src="js/calendarcode.js"></script>
<div id="popupcalendar" class="text"></div>

<body bgcolor='white'>

<table border='1' cellpadding='5' cellspacing='0' width='400'>
	<tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
		<td>
		<h3>員工資料修改 - update_spot_input.jsp</h3>
		<a href="<%=request.getContextPath()%>/select_page.jsp"><img src="images/back1.gif" width="100" height="32" border="0">回首頁</a></td>
	</tr>
</table>

<h3>資料修改:</h3>
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
		<td>景點編號:<font color=red><b>*</b></font></td>
		<td><%=spotVO.getSpno()%></td>
	</tr>
	<tr>
		<td>景點名稱:</td>
		<td><input type="TEXT" name="spname" size="45" value="<%=spotVO.getSpname()%>" /></td>
	</tr>
	<tr>
		<td>經度:</td>
		<td><input type="TEXT" name="splong" size="45"	value="<%=spotVO.getSplong()%>" /></td>
	</tr>
	<tr>
		<td>緯度:</td>
		<td bgcolor="#CCCCFF">
		    <input class="cal-TextBox"
			onFocus="this.blur()" size="9" readonly type="text" name="splat" value="<%=spotVO.getSplat()%>">
			<a class="so-BtnLink"
			href="javascript:calClick();return false;"
			onmouseover="calSwapImg('BTN_date', 'img_Date_OVER',true);"
			onmouseout="calSwapImg('BTN_date', 'img_Date_UP',true);"
			onclick="calSwapImg('BTN_date', 'img_Date_DOWN');showCalendar('form1','hiredate','BTN_date');return false;">
		    <img align="middle" border="0" name="BTN_date"	src="images/btn_date_up.gif" width="22" height="17" alt="開始日期"></a>
		</td>
	</tr>
	<tr>
		<td>所屬據點:</td>
		<td><input type="TEXT" name="loc" size="45"	value="<%=spotVO.getLocno()%>" /></td>
	</tr>
	



</table>
<br>
<input type="hidden" name="action" value="update">
<input type="hidden" name="spno" value="<%=spotVO.getSpno()%>">
<input type="submit" value="送出修改"></FORM>

</body>
</html>
