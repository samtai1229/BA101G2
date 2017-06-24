<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.member.model.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	MemberVO memVO = (MemberVO) request.getAttribute("memVO"); //EmpServlet.java (Concroller), 存入req的empVO物件 (包括幫忙取出的empVO, 也包括輸入資料錯誤時的empVO物件)
   
%>
<html>
<head>
<title>會員資料修改 - update_member_input.jsp</title></head>
<link rel="stylesheet" type="text/css" href="js/calendar.css">
<script language="JavaScript" src="js/calendarcode.js"></script>
<div id="popupcalendar" class="text"></div>

<body bgcolor='white'>

<table border='1' cellpadding='5' cellspacing='0' width='1600'>
	<tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
		<td>
		<h3>會員資料修改 - update_member_input.jsp</h3>
		<a href="<%=request.getContextPath()%>/member/select_page.jsp"><img src="images/back1.gif" width="100" height="32" border="0">回首頁</a></td>
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

<FORM METHOD="post" ACTION="member.do" name="form1">
<table border="0">
	<tr>
		<td>會員編號:<font color=red><b>*</b></font></td>
		<td><%=memVO.getMemno()%></td>
	</tr>
	<tr>
		<td>會員名稱:</td>
		<td><%=memVO.getMemname()%></td>
	</tr>
	<tr>
		<td>性別:</td>
		<td><%=memVO.getSex()%></td>
	</tr>
	<tr>
		<td>Email:</td>
		<td><%=memVO.getMail()%></td>
	</tr>
	<tr>
		<td>Phone:</td>
		<td><%=memVO.getPhone()%></td>
	</tr>
	<tr>
		<td>Address:</td>
		<td><%=memVO.getSex()%></td>
	</tr>

    <tr>
		<td>生日:</td>
		<td><%=memVO.getBirth()%></td>
	</tr>
	
	<tr>
		<td>帳號:</td>
		<td><%=memVO.getAcc()%></td>
	</tr>
	<tr>
		<td>密碼:</td>
		<td><%=memVO.getPwd()%></td>
	</tr>
	<tr>
	<!-- 正面 --><td><img src='<%=request.getContextPath()%>/backend/member/memReader.do?memno=${memVO.memno}&card=idcard1' width='120' height='100'></td>	
	</tr>
	<tr>	
<!-- 反面 --><td><img src='<%=request.getContextPath()%>/backend/member/memReader.do?memno=${memVO.memno}&card=idcard2' width='120' height='100'></td>				
</tr>	
<tr>
<!-- 駕照 --><td><img src='<%=request.getContextPath()%>/backend/member/memReader.do?memno=${memVO.memno}&card=license' width='120' height='100'></td>							
</tr>
<tr>
			<td><fmt:formatDate pattern = "yyyy年MM月dd號" 
         value = "${memVO.credate}" /></td>
         </tr>
         <tr>
			<td>${memVO.status}</td>
	</tr>
	
</table>
<br>
<input type="hidden" name="action" value="update">
<input type="hidden" name="memno" value="<%=memVO.getMemno()%>">
<input type="submit" value="送出修改"></FORM>

</body>
</html>
