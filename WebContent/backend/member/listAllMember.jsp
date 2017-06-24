<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.member.model.*"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>

<%
	MemberService memSvc = new MemberService();
	List<MemberVO> list = memSvc.getAll();
	pageContext.setAttribute("list",list);
%>

<html>
<head>
<title>所有會員資料 - listAllMembers.jsp</title>

</head>
<body bgcolor='white'>
<table border='1' cellpadding='5' cellspacing='0' width='1600'>
	<tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
		<td>
		<h3>所有會員資料 - ListAllMembers.jsp</h3>
		<a href="<%=request.getContextPath()%>/backend/member/select_page.jsp"><img src="images/back1.gif" width="100" height="32" border="0">回首頁</a>
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
<%-- <%@ include file="pages/page1.file" %>  --%>
<table border='1' bordercolor='#CCCCFF' width='1600'>
	<tr>
		<th>會員編號</th>
		<th width='70'>會員名稱</th>
		<th  width='50'>性別 </th>
		<th>生日</th>
		<th>Mail</th>
		<th>電話</th>
		<th>地址</th>
		<th>帳號</th>
		<th>密碼</th>
		<th>身分證正面</th>
		<th>身分證反面</th>
		<th>駕照</th>
		<th>加入時間</th>
		<th>認證狀態</th>
	</tr>
<%-- 	begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>" --%>
	<c:forEach var="memVO" items="${list}" >
		<tr align='center' valign='middle'>
			<td>${memVO.memno}</td>
			<td>${memVO.memname}</td>
			<td>${memVO.sex}</td>
			<td><fmt:formatDate pattern = "yyyy年MM月dd號" 
         value = "${memVO.birth}" /></td>
			<td>${memVO.mail}</td>
			<td>${memVO.phone}</td>	
			<td>${memVO.addr}</td>	
			<td>${memVO.acc}</td>
			<td>${memVO.pwd}</td>	
<!-- 正面 --><td><img src='<%=request.getContextPath()%>/backend/member/memReader.do?memno=${memVO.memno}&card=idcard1' width='120' height='100'></td>	
			
<!-- 反面 --><td><img src='<%=request.getContextPath()%>/backend/member/memReader.do?memno=${memVO.memno}&card=idcard2' width='120' height='100'></td>				
<!-- 駕照 --><td><img src='<%=request.getContextPath()%>/backend/member/memReader.do?memno=${memVO.memno}&card=license' width='120' height='100'></td>							
			<td><fmt:formatDate pattern = "yyyy年MM月dd號" 
         value = "${memVO.credate}" /></td>
			<td>${memVO.status}</td>
				
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/member/member.do">
			     <input type="submit" value="修改"> 
			     <input type="hidden" name="memno" value="${memVO.memno}">
			     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller--><!-- 目前尚未用到  -->
			     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
			</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>backend/member/member.do">
			    <input type="submit" value="刪除">
			    <input type="hidden" name="memno" value="${memVO.memno}">
			    <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
			    <input type="hidden" name="action"value="delete"></FORM>
			</td>
		</tr>
	</c:forEach>
</table>
<%-- <%@ include file="pages/page2.file" %> --%>

<br>本網頁的路徑:<br><b>
   <font color=blue>request.getServletPath():</font> <%= request.getServletPath()%><br>
   <font color=blue>request.getRequestURI(): </font> <%= request.getRequestURI()%> </b>
</body>
</html>
