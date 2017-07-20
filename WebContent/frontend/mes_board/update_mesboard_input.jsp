<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.mes_board.model.*"%>
<%
	MesBoardVO mesboardVO = (MesBoardVO) request.getAttribute("mesboardVO"); //NewsServlet.java (Concroller), 存入req的newsVO物件 (包括幫忙取出的newsVO, 也包括輸入資料錯誤時的newsVO物件)
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>留言修改</title>
</head>
<link rel="stylesheet" type="text/css" href="js/calendar.css">
<script language="JavaScript" src="js/calendarcode.js"></script>
<div id="popupcalendar" class="text"></div>

<body bgcolor='white'>

	<table border='1' cellpadding='5' cellspacing='0' width='400'>
		<tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
			<td>
				<h3>留言修改</h3> <a
				href="<%=request.getContextPath()%>/backend/mes_board/mesboard_select_page.jsp"><img
					src="images/back1.gif" width="100" height="32" border="0">回首頁</a>
			</td>
		</tr>
	</table>

	<h3>留言修改:</h3>
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

	<FORM METHOD="post" ACTION="mesboard.do" name="form1"
		enctype="multipart/form-data">
		<table border="0">
			<tr>
				<td>留言板編號:<font color=red><b>*</b></font></td>
				<td><%=mesboardVO.getMesno()%></td>
			</tr>

			<tr>
				<td>會員編號:<font color=red><b>*</b></font></td>
				<td><input type="TEXT" name="memno" size="45"
					value="<%=(mesboardVO == null) ? "" : mesboardVO.getMemno()%>" /></td>
			</tr>
			<tr>
				<td>留言內容:</td>
				<td><input type="TEXT" name="cont" size="45"
					value="<%=(mesboardVO == null) ? "CONT" : mesboardVO.getCont()%>" /></td>
			</tr>
			<tr>
				<td>留言圖片修改:</td>
				<td><input type="file" name="pic"></td>
				<br>
				<!--         <input type="file" name="upfile2"> -->
			</tr>
			<tr>
				<td>留言狀態:<font color=red><b>*</b></font></td>
				<td><select size="1" name="status">
						<option value="normal">正常顯示</option>
						<option value="hid">隱藏</option>
				</select></td>
			</tr>
		</table>
		<br> <input type="hidden" name="action" value="update"> <input
			type="hidden" name="newsno" value="<%=mesboardVO.getMesno()%>">
		<input type="hidden" name="requestURL"
			value="<%=request.getParameter("requestURL")%>">
		<!--接收原送出修改的來源網頁路徑後,再送給Controller準備轉交之用-->
		<input type="hidden" name="whichPage"
			value="<%=request.getParameter("whichPage")%>">
		<!--只用於:istAllEmp.jsp-->
		<input type="hidden" name="mesno" value="<%=mesboardVO.getMesno()%>">
		<input type="submit" value="送出修改">
	</FORM>
</body>
</html>
