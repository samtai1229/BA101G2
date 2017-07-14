<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.mes_board.model.*"%>
<%
	MesBoardVO mesboardVO = (MesBoardVO) request.getAttribute("mesboardVO");
%>

<html>
<head>
<title>新增留言</title>
</head>

<body bgcolor='white'>

	<table border='1' cellpadding='5' cellspacing='0' width='400'>
		<tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
			<td>
				<h3>新增留言</h3>
			</td>
			<td><a
				href="<%=request.getContextPath()%>/backend/mes_board/mesboard_select_page.jsp"><img
					src="images/tomcat.gif" width="100" height="100" border="1">回首頁</a>
			</td>
		</tr>
	</table>

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

			<jsp:useBean id="mesboardSvc" scope="page"
				class="com.mes_board.model.MesBoardService" />
			<tr>
				<td>會員編號:</td>
				<td><input type="TEXT" name="memno" size="45"
					value="<%=(mesboardVO == null) ? "" : mesboardVO.getMemno()%>" /></td>
			</tr>
			<tr>
				<td>留言內容:</td>
				<td><input type="TEXT" name="cont" size="45"
					value="<%=(mesboardVO == null) ? "" : mesboardVO.getCont()%>" /></td>
			</tr>
			<tr>
				<td>相片圖片:</td>
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
		<br> <input type="hidden" name="action" value="insert"> <input
			type="submit" value="送出新增">
	</FORM>
</body>

</html>