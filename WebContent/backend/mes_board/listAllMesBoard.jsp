<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.mes_board.model.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%-- 此頁練習採用 EL 的寫法取值 --%>

<%
	MesBoardService mesboardSvc = new MesBoardService();
	List<MesBoardVO> list = mesboardSvc.getAll();
	pageContext.setAttribute("list",list);
%>
<jsp:useBean id="mesboardSvc1" scope="page" class="com.mes_board.model.MesBoardService" />

<html>
<head>
<title>所有留言板資料</title>
</head>
<body bgcolor='white'>
<table border='1' cellpadding='5' cellspacing='0' width='800'>
	<tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
		<td>
		<h3>所有留言板資料</h3>
		<a href="<%=request.getContextPath()%>/backend/mes_board/mesboard_select_page.jsp"><img src="images/back1.gif" width="100" height="32" border="0">回首頁</a>
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

<table border='1' bordercolor='#CCCCFF' width='800'>
	<tr>
		<th>留言板編號</th>
		<th>會員編號</th>
		<th>留言時間</th>
		<th>留言內容</th>
		<th>相片檔案</th>
		<th>留言版狀態</th>
		<th>修改</th>
		<th>刪除</th>
	</tr>
	<%@ include file="page1.file" %> 
	<c:forEach var="mesboardVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
		<tr align='center' valign='middle' ${(mesboardVO.mesno==param.mesno) ? 'bgcolor=#CCCCFF':''}><!--將修改的那一筆加入對比色而已-->
			<td>${mesboardVO.mesno}</td>
			<td>${mesboardVO.memno}</td>
			<td>${mesboardVO.mesdate}</td>
			<td>${mesboardVO.cont}</td>
			<td><img src="<%=request.getContextPath()%>/backend/mes_board/mesboardread.do?mesno=${mesboardVO.mesno}" style= max-width:150px;max-height:150px;></td>
			<td>${mesboardVO.status}</td>			
			<td>
			  <FORM METHOD="post" ACTION="mesboard.do">
			     <input type="submit" value="修改"> 
			     <input type="hidden" name="mesno" value="${mesboardVO.mesno}">
			     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
<%-- 			    <input type="hidden" name="whichPage"	value="<%=whichPage%>">               <!--送出當前是第幾頁給Controller--> --%>
			     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
			</td>
			<td>
			  <FORM METHOD="post" ACTION="mesboard.do">
			    <input type="submit" value="刪除">
			    <input type="hidden" name="mesno" value="${mesboardVO.mesno}">
			    <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
<%-- 			    <input type="hidden" name="whichPage"	value="<%=whichPage%>">               <!--送出當前是第幾頁給Controller--> --%>
			    <input type="hidden" name="action"value="delete"></FORM>
			</td>
		</tr>
	</c:forEach>
</table>
<%@ include file="page2.file" %>
</body>
</html>