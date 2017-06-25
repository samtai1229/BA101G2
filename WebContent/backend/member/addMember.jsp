<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.member.model.*"%>
<%
MemberVO memVO = (MemberVO) request.getAttribute("memVO");
%>

<html>
<head>
<title>會員資料新增 - addSpot.jsp</title></head>
<link rel="stylesheet" type="text/css" href="js/calendar.css">
<script language="JavaScript" src="js/calendarcode.js"></script>
<div id="popupcalendar" class="text"></div>

<body bgcolor='white'>

<table border='1' cellpadding='5' cellspacing='0' width='1600'>
	<tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
		<td>
		<h3>會員資料新增 - addMember.jsp</h3>
		</td>
		<td>
		   <a href="<%=request.getContextPath()%>/backend/member/select_page.jsp"><img src="images/back1.gif" width="100" height="100" border="1">回首頁</a>
	    </td>
	</tr>
</table>

<h3>新增會員:</h3>
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

<FORM METHOD="post" ACTION="member.do" name="form1" enctype="multipart/form-data">
<table border="0">

	<tr>
		<td>會員名稱:</td>
		<td><input type="TEXT" name="memname" size="45" 
			value="<%= (memVO==null)? "我家" : memVO.getMemname()%>" /></td>
	</tr>
	<tr>
		<td>性別:</td>
		<td><select name="sex"><option value="male">男
								<option value="female">女
		</select></td>
	</tr>	
	<tr>
		<td>生日:</td>
		<td><input type="date" name="birth" size="45">
			</td>
	</tr>	
	<tr>
		<td>信箱:</td>
		<td><input type="email" name="mail" size="45"/></td>
	</tr>	
	<tr>
		<td>電話:</td>
		<td><input type="number" name="phone" size="45"/></td>
	</tr>
	<tr>
		<td>地址:</td>
		<td><input type="text" name="address" size="45"/></td>
	</tr>
	<tr>
		<td>帳號:</td>
		<td><input type="text" name="acc" size="45"/></td>
	</tr>
	<tr>
		<td>密碼:</td>
		<td><input type="password" name="pwd" size="45"/></td>
	</tr>
	<tr>
		<td>身分證(正面):</td>
		<td><input type="file" name="idcard1" size="45"/></td>
	</tr>
	<tr>
		<td>身分證(反面):</td>
		<td><input type="file" name="idcard2" size="45"/></td>
	</tr>
	<tr>
		<td>駕照:</td>
		<td><input type="file" name="license" size="45"/></td>
	</tr>
</table>
<br>
<input type="hidden" name="action" value="insert">
<input type="submit" value="送出新增"></FORM>
</body>
</html>
