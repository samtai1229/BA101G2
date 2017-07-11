<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.member.model.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	MemberVO memVO = (MemberVO) request.getAttribute("memVO"); //EmpServlet.java (Concroller), 存入req的empVO物件 (包括幫忙取出的empVO, 也包括輸入資料錯誤時的empVO物件)
	String[] statusArray = {"unconfirmed","confirmed"};
	String[] gender = {"Girl","Boy"};
    pageContext.setAttribute("statusArray", statusArray);
	pageContext.setAttribute("gender", gender);
	pageContext.setAttribute("memVO", memVO);
%>
<html>
<head>
<title>會員資料修改 - update_member_input.jsp</title></head>
<link rel="stylesheet" type="text/css" href="js/calendar.css">
<script language="JavaScript" src="js/calendarcode.js"></script>
<div id="popupcalendar" class="text"></div>

<body bgcolor='white'>

<table border='1' cellpadding='5' cellspacing='0' width='1690'>
	<tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
		<td>
		<h3>請把會員資料填寫完整，以便成為身分驗證</h3>
		<a href="<%=request.getContextPath()%>/index.jsp"><img src="images/back1.gif" width="100" height="32" border="0">回首頁</a></td>
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

<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/member/member.do" name="form1" enctype="multipart/form-data">
<table border="0">
	<tr>
		<td>會員名稱:<font color='red'>*</font></td>
		<td><input  type="TEXT" name="memname" size="45" 
			value="<%= (memVO==null)? "我家" : memVO.getMemname()%>" /></td>
	</tr>
	<tr>
		<td>性別:<font color='red'>*</font></td>
		<td><select name='sex'>
		<option value="Boy">男
		<option value="Girl">女
		</select></td>
	</tr>	
	<tr>
		<td>生日:<font color='red'>*</font></td>
		<td><input type="date" name="birth"></td>
	</tr>	
	<tr>
		<td>信箱:</td>
		<td><input type="email" name="mail" size="45" readonly value="<%=memVO.getMail() %>"/></td>
	</tr>	
	<tr>
		<td>電話:<font color='red'>*</font></td>
		<td><input  type="tel" name="phone" size="45"/></td>
	</tr>
	<tr>
		<td>地址:<font color='red'>*</font></td>
		<td><input  type="text" name="address" size="45"/></td>
	</tr>
	<tr>
		<td>帳號:</td>
		<td><input readonly value="<%=memVO.getAcc() %>" type="text" name="acc" size="45"/></td>
	</tr>
	<tr>
		<td>密碼:</td>
		<td><input readonly value="<%=memVO.getPwd() %>" type="password" name="pwd" size="45"/></td>
	</tr>
	<tr>
		<td>身分證(正面):</td>
		<td><input type="file" name="idcard1" size="45"/></td>
		<!-- 正面 --><td><img src='<%=request.getContextPath()%>/backend/member/memReader.do?memno=${memVO.memno}&card=idcard1' width='120' height='100'></td>	
	   
	</tr>
	<tr>
		<td>身分證(反面):</td>
		<td><input type="file" name="idcard2" size="45"/></td>
		<!-- 反面 --><td><img src='<%=request.getContextPath()%>/backend/member/memReader.do?memno=${memVO.memno}&card=idcard2' width='120' height='100'></td>
	</tr>
	<tr>
		<td>駕照:</td>
		<td><input type="file" name="license" size="45"/></td>
		<!-- 正面 --><td><img src='<%=request.getContextPath()%>/backend/member/memReader.do?memno=${memVO.memno}&card=license' width='120' height='100'></td>
	</tr>
	
	
    <tr>
        <td>認證狀態:</td>
		<td><%=memVO.getStatus()%></td>
	</tr>
</table>
<br/>
<input type="hidden" name="action" value="update">
<input type="hidden" name="memno" value="<%=memVO.getMemno()%>">
<input type="hidden" name="status" value="<%=memVO.getStatus()%>">
<input type="hidden" name="credate" value="<fmt:formatDate pattern = "yyyy-MM-dd HH:mm:ss" 
 value = "${memVO.credate}" />">
<input type="submit" value="送出修改"></FORM>

</body>
</html>
