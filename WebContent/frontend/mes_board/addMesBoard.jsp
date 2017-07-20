<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.mes_board.model.*"%>
<%@ page import="com.member.model.*"%>
<%
	MesBoardVO mesboardVO = (MesBoardVO) request.getAttribute("mesboardVO");
%>
<%	MemberService as = new MemberService();
	MemberVO memberVO= (MemberVO)session.getAttribute("memberVO");
	
	String memno= (String)session.getAttribute("memno");
	String memname= (String)session.getAttribute("memname");
%>

<html>
<head>
</head>

<body bgcolor='white'>

	<table border='1' cellpadding='5' cellspacing='0' width='400'>
		<tr>
			<td>
				<h3 align=center>新增留言</h3>
			</td>
		</tr>
	</table>

	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font color='red'>請修正以下錯誤:
			<ul>
				<td>你有沒填的地方喔</td>
			</ul>
		</font>
	</c:if>

	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/forntend/mes_board/mesboard.do" name="form1"
		enctype="multipart/form-data">
		<table table  style="border:3px #FFAC55 dashed;padding:5px;" rules="all" cellpadding='5';>

			<jsp:useBean id="mesboardSvc" scope="page"
				class="com.mes_board.model.MesBoardService" />
			<tr>
				<td>會員姓名:</td>
				<td ><%=memname %></td>
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
		<br> <input type="hidden" name="action" value="insert">
		 <input type="submit" value="送出新增">
		 <input type="hidden" name="memno" value="<%=memno %>">
		 <input type="hidden" name="requestURL" value="<%=request.getServletPath() %>">
	</FORM>
</body>

</html>