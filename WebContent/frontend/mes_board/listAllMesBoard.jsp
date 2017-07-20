<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.mes_board.model.*"%>
<%@ page import="com.member.model.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<%
	MesBoardVO mesboardVO = (MesBoardVO) request.getAttribute("mesboardVO");
%>
<%
	MemberService as = new MemberService();
	MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");

	String memno = (String) session.getAttribute("memno");
	String memname = (String) session.getAttribute("memname");
%>
<%-- 此頁練習採用 EL 的寫法取值 --%>

<%
	MesBoardService mesboardSvc = new MesBoardService();
	List<MesBoardVO> list = mesboardSvc.getAll();
	pageContext.setAttribute("list", list);
%>
<jsp:useBean id="mesboardSvc1" scope="page"
	class="com.mes_board.model.MesBoardService" />

<html>
<head>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/ckeditor/ckeditor.js"></script>
<title>所有留言板資料</title>
<style type="text/css">
body {
	font-size: 13px;
}

@media ( max-width : 768px) {
	body {
		font-size: 12px;
	}
}

.chat_list .list-group-item {
	padding: 5px 4px;
	width: 60%;
	min-height: 50px;
	background-color: #FFFFFF;
	border-style: solid;
}

@media ( max-width : 768px) {
	.chat_list .list-group-item {
		min-height: inherit;
		position: relative;
	}
}

.chat_list .list-group-item-text {
	word-wrap: break-word;
	display: flex;
	overflow: auto;
}

.chat_list .pull-left {
	width: 55px;
	text-align: center;
}
</style>
</head>
<body bgcolor='white'background="<%=request.getContextPath()%>/img/header1.jpg"style="margin: 0px; padding: 0px; background-size: cover;">

	<%@ include file="page1.file"%>
	<c:forEach var="mesboardVO" items="${list}" begin="<%=pageIndex%>"
		end="<%=pageIndex+rowsPerPage-1%>">

		<div align=center>
			<div class="chat_list" style="position: relative;">
				<ul class="list-group">
					<li class="list-group-item">
						<div class="pull-left hidden-xs" style="margin-right: 600px;">
							<img class="img-circle" title="User1" alt="User1" data-src="holder.js/40x40/lava"> 
							
								<small class="list-group-item-heading text-muted text-primary"><font face="serif" size="3.5" color="green">${memSvc.getOneMember(mesboardVO.memno).memname}</font></small>
						</div>
						<div style="margin-left: 500px;">
						<small class="pull-right text-muted"><font size="3.5"face="monospace" color="green">發表:<fmt:formatDate value="${mesboardVO.mesdate}" pattern="yyyy-MM-dd HH:mm:ss"/></font></small><br> 
						</div>
						<div align="left">
							<jsp:useBean id="memSvc" scope="page"
								class="com.member.model.MemberService"></jsp:useBean>

						</div>
						<div>
							<td><img
								src="<%=request.getContextPath()%>/backend/mes_board/mesboardread.do?mesno=${mesboardVO.mesno}"
								style="max-width: 150px; max-height: 150px;"></td>
							<p class="list-group-item-text">${mesboardVO.cont}</p>
						</div>
					</li>
				</ul>
			</div>
		</div>
		<%-- 		<tr align='center' valign='middle' ${(mesboardVO.mesno==param.mesno) ? 'bgcolor=#CCCCFF':''}><!--將修改的那一筆加入對比色而已--> --%>
		<%-- 			<td>${mesboardVO.mesno}</td> --%>
		<%-- 			<td>${mesboardVO.memno}</td> --%>
		<%-- 			<td>${mesboardVO.mesdate}</td> --%>
		<%-- 			<td>${mesboardVO.cont}</td> --%>
		<%-- 			<td><img src="<%=request.getContextPath()%>/backend/mes_board/mesboardread.do?mesno=${mesboardVO.mesno}" style= max-width:150px;max-height:150px;></td> --%>
		<%-- 			<td>${mesboardVO.status}</td>			 --%>






		<td>
			<FORM METHOD="post" ACTION="mesboard.do">
				<!-- 			     <input type="submit" value="修改">  -->
				<%-- 			     <input type="hidden" name="mesno" value="${mesboardVO.mesno}"> --%>
				<input type="hidden" name="requestURL"
					value="<%=request.getServletPath()%>">
				<!--送出本網頁的路徑給Controller-->
				<%-- 			    <input type="hidden" name="whichPage"	value="<%=whichPage%>">               <!--送出當前是第幾頁給Controller--> --%>
				<input type="hidden" name="action" value="getOne_For_Update">
			</FORM>
		</td>
		<td>
			<FORM METHOD="post" ACTION="mesboard.do">
				<!-- 			    <input type="submit" value="刪除"> -->
				<%-- 			    <input type="hidden" name="mesno" value="${mesboardVO.mesno}"> --%>
				<input type="hidden" name="requestURL"
					value="<%=request.getServletPath()%>">
				<!--送出本網頁的路徑給Controller-->
				<%-- 			    <input type="hidden" name="whichPage"	value="<%=whichPage%>">               <!--送出當前是第幾頁給Controller--> --%>
				<input type="hidden" name="action" value="delete">
			</FORM>
		</td>
		</tr>
	</c:forEach>
	<%@ include file="page2.file"%>
	<!-- 	--------------------------------------------------------------------------------------------------------- -->

	<table style="margin-left: 270px;" border='1' cellpadding='5'
		cellspacing='0' width='60%' bgcolor='white'>
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

	<FORM METHOD="post"
		ACTION="<%=request.getContextPath()%>/forntend/mes_board/mesboard.do"
		name="form1" enctype="multipart/form-data">
		<table
			style="border: 3px #FFAC55 dashed; padding: 5px; background-color: #FFFFFF; margin-left: 270px;"
			rules="all" cellpadding='5' width="60%">
			<%-- 			<jsp:useBean id="mesboardSvc" scope="page" --%>
			<%-- 				class="com.mes_board.model.MesBoardService" /> --%>
			<tr>
				<td>相片圖片:</td>
				<td><input type="file" name="pic"></td>
				<br>
				<!--         <input type="file" name="upfile2"> -->

				<td align="center"><input type="submit" value="送出新增"></td>
			</tr>
			<!-- 			<tr> -->
			<!-- 				<td>留言內容:</td> -->

			<!--  				<td><input type="TEXT" name="cont" size="45"  -->
			<%-- 					value="<%=(mesboardVO == null) ? "" : mesboardVO.getCont()%>" /></td> --%>
			<!-- 			</tr> -->

			<div style="width: 60%; margin-left: 270px;">
				<textarea class="ckeditor" cols="80" id="cont" name="cont" rows="12"></textarea>
			</div>


			<!-- 			<tr> -->
			<!-- 				<td>留言狀態:<font color=red><b>*</b></font></td> -->
			<!-- 				<td><select size="1" name="status"> -->
			<!-- 						<option value="normal">正常顯示</option> -->
			<!-- 						<option value="hid">隱藏</option> -->
			<!-- 				</select></td> -->
			<!-- 			</tr> -->
		</table>
		<br> <input type="hidden" name="action" value="insert"> <input
			type="hidden" name="memno" value="<%=memno%>"> <input
			type="hidden" name="requestURL"
			value="<%=request.getServletPath()%>">
	</FORM>



	<script src="https://code.jquery.com/jquery.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script type="text/javascript"
		src="//cdnjs.cloudflare.com/ajax/libs/holder/2.4.1/holder.js"></script>

</body>
</html>