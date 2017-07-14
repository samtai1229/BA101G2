<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.adminis.model.*"%>
<%
	AdminisVO adminisVO = (AdminisVO) request.getAttribute("adminisVO"); //AdminisServlet.java (Concroller), 存入req的empVO物件 (包括幫忙取出的adminisVO, 也包括輸入資料錯誤時的adminisVO物件)
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>管理員資料修改</title>
</head>
<div id="popupcalendar" class="text"></div>
<body bgcolor='white'>
	<table border='1' cellpadding='5' cellspacing='0' width='400'>
		<tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
			<td>
				<h3>管理員資料修改</h3> <a href="<%=request.getContextPath()%>/backend/adminis/adm_select_page.jsp"><img
					src="images/back1.gif" width="100" height="32" border="0">回首頁</a>
			</td>
		</tr>
	</table>

	<h3>資料修改:</h3>
	<c:if test="${not empty errorMsgs}">
		<font color='red'>請修正以下錯誤:
			<ul>
				<c:forEach var="message" items="${errorMsgs}">
					<li>${message}</li>
				</c:forEach>
			</ul>
		</font>
	</c:if>

	<FORM METHOD="post" ACTION="adminis.do" name="form1">
		<table border="0">
			<tr>
				<td>管理員編號:<font color=red><b>*<b></font></td>
				<td><%=adminisVO.getAdmno()%></td>
			</tr>
			<tr>
				<td>管理員姓名:</td>
				<td><input type="TEXT" name="name" size="45"
					value="<%=adminisVO.getName()%>" /></td>
			</tr>
			<tr>
				<td>管理員帳號:</td>
				<td><input type="text" name="acc" size="45"
					value="<%=(adminisVO == null) ? " " : adminisVO.getAcc()%>"></td>
			</tr>
			<tr>
				<td>管理員密碼:</td>
				<td><input type="text" name="pw" size="45"
					value="<%=(adminisVO == null) ? " " : adminisVO.getPw()%>"></td>
			</tr>
			<tr>
				<td>據點:</td>
				<td><input type="text" name="locno" size="45"
					value="<%=(adminisVO == null) ? " " : adminisVO.getLocno()%>"></td>
			</tr>
<%-- 			<jsp:useBean id="locationSvc" scope="page" class="com.location.model.LocationService" /> --%>
<!-- 			<tr> -->
<!-- 				<td>據點:<font color=red><b>*</b></font></td> -->
<!-- 				<td><select size="1" name="locno"> -->
<%-- 						<c:forEach var="locationVO" items="${locationSvc.all}"> --%>
<%-- 							<option value="${locationVO.locno}" --%>
<%-- 								${(adminisVO.locno==locationVO.locno)? 'selected':'' }>${locationVO.locname} --%>
<%-- 						</c:forEach> --%>
<!-- 				</select></td> -->
<!-- 			</tr> -->

			<jsp:useBean id="authcateSvc" scope="page"
				class="com.auth_cate.model.AuthCateService" />
			<tr>
				<td>權限類別編號:<font color=red><b>*</b></font></td>
				<td><select size="1" name="authno">
						<c:forEach var="authcateVO" items="${authcateSvc.all}">
							<option value="${authcateVO.authno}"
								${(adminisVO.authno==authcateVO.authno)? 'selected':'' }>${authcateVO.descr}
						</c:forEach>
				</select></td>
			</tr>
		</table>
		<br> <input type="hidden" name="action" value="update"> <input
			type="hidden" name="admno" value="<%=adminisVO.getAdmno()%>">
		<input type="submit" value="送出修改">
	</FORM>


</body>
</html>