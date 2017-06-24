<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>BA101G2 Spots: Home</title>
</head>
<body bgcolor='white'>

	<table border='1' cellpadding='5' cellspacing='0' width='400'>
		<tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
			<td><h3>IBM Spot: Home</h3>
				<font color=red>( MVC )</font></td>
		</tr>
	</table>

	<p>This is the Home page for BA101G2 Member: Home</p>

	<h3>會員資料查詢:</h3>
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

	<ul>
		<li><a
			href='<%=request.getContextPath()%>/backend/member/listAllMember.jsp'>List</a>
			all Member.</li>
		<br>
		<br>

		<li>
			<FORM METHOD="post"
				ACTION="<%=request.getContextPath()%>/backend/member/member.do">
				<b>輸入會員編號 (如MEM000001):</b> <input type="text" name="memno">
				<input type="submit" value="送出"> <input type="hidden"
					name="action" value="getOne_For_Display">
			</FORM>
		</li>

		<jsp:useBean id="memSvc" scope="page"
			class="com.member.model.MemberService" />

		<li>
			<FORM METHOD="post"
				ACTION="<%=request.getContextPath()%>/backend/member/member.do">
				<b>選擇會員編號:</b> <select size="1" name="memno">
					<c:forEach var="memVO" items="${memSvc.all}">
						<option value="${memVO.memno}">${memVO.memno}
					</c:forEach>
				</select> <input type="submit" value="送出"> <input type="hidden"
					name="action" value="getOne_For_Display">
			</FORM>
		</li>

		<li>
			<FORM METHOD="post"
				ACTION="<%=request.getContextPath()%>/backend/member/member.do">
				<b>選擇會員名稱:</b> <select size="1" name="memno">
					<c:forEach var="memVO" items="${memSvc.all}">
						<option value="${memVO.memno}">${memVO.memname}
					</c:forEach>
				</select> <input type="submit" value="送出"> <input type="hidden"
					name="action" value="getOne_For_Display">
			</FORM>
		</li>

		<!-- 這邊到時候加入 其他的table -->



		<h3>會員管理</h3>

		<ul>
			<li><a
				href='<%=request.getContextPath()%>/frontend/member/addMember.jsp'>Add</a>
				a new Member.</li>
		</ul>
</body>

</html>
