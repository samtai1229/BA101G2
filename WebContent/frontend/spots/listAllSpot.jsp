<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.spots.model.*"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>

<%
	SpotService spSvc = new SpotService();
	List<SpotsVO> list = spSvc.getAll();
	pageContext.setAttribute("list",list);
%>

<html>
<head>
<title>所有景點資料 - listAllSpots.jsp</title>
</head>
<body bgcolor='white'>
<b><font color=red>此頁練習採用 EL 的寫法取值:</font></b>
<table border='1' cellpadding='5' cellspacing='0' width='800'>
	<tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
		<td>
		<h3>所有景點資料 - ListAllSpots.jsp</h3>
		<a href="<%=request.getContextPath()%>/frontend/spots/select_page.jsp"><img src="images/back1.gif" width="100" height="32" border="0">回首頁</a>
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
<%@ include file="pages/page1.file" %> 
<table border='1' bordercolor='#CCCCFF' width='800'>
	<tr>
		<th>景點編號</th>
		<th>景點名稱</th>
		<th>經度</th>
		<th>緯度</th>
		<th>據點編號</th>
	</tr>
	
	<c:forEach var="spotVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
		<tr align='center' valign='middle'>
			<td>${spotVO.spno}</td>
			<td>${spotVO.spname}</td>
			<td>${spotVO.splong}</td>
			<td>${spotVO.splat}</td>
			<td>${spotVO.locno}</td>		
			
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/spots/spot.do">
			     <input type="submit" value="修改"> 
			     <input type="hidden" name="spno" value="${spotVO.spno}">
			     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller--><!-- 目前尚未用到  -->
			     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
			</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/spots/spot.do">
			    <input type="submit" value="刪除">
			    <input type="hidden" name="spno" value="${spotVO.spno}">
			    <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
			    <input type="hidden" name="action"value="delete"></FORM>
			</td>
		</tr>
	</c:forEach>
</table>
<%@ include file="pages/page2.file" %>

<br>本網頁的路徑:<br><b>
   <font color=blue>request.getServletPath():</font> <%= request.getServletPath()%><br>
   <font color=blue>request.getRequestURI(): </font> <%= request.getRequestURI()%> </b>
</body>
</html>
