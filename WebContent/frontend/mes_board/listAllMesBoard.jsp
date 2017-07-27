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

	MemberService as = new MemberService();
	MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");
	String memno = (String) session.getAttribute("memno");
	String memname = (String) session.getAttribute("memname");

	MesBoardService mesboardSvc = new MesBoardService();
	List<MesBoardVO> list = mesboardSvc.getAll();
	pageContext.setAttribute("list", list);
%>
<jsp:useBean id="mesboardSvc1" scope="page" class="com.mes_board.model.MesBoardService" />
	
    
    <link rel="stylesheet" href="<%=request.getContextPath()%>/vendor/bootstrap/css/bootstrap.min.css" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/frontend/rental_form/Modified/agency.min.css" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/frontend/rental_form/Modified/agency.css" />
<html>
<head>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/ckeditor/ckeditor.js"></script>
<title>留言板</title>
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

body {
       background: linear-gradient( rgba(0, 0, 0, 0.3), rgba(0, 0, 0, 0.3) ),url(/BA101G2/img/header1.jpg) no-repeat center center fixed; 
	    -webkit-background-size: cover;
	    -moz-background-size: cover;
	    -o-background-size: cover;
	    background-size: cover;
}

.navTextTag{ font-size:16px!important; }

	.divTag{
	margin-top:200px;
}

#h3Tag{
	color:#fff;
}

</style>
</head>
<body>


<%-- Navigation --%>
	<nav id="mainNav" class="navbar navbar-default navbar-custom navbar-fixed-top">
		<div class="container-fluid">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header page-scroll">
				<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Toggle navigation</span> Menu <i class="fa fa-bars"></i>
				</button>
				<a class="navbar-brand page-scroll" href="<%=request.getContextPath()%>/index.jsp">AutoBike&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;留言板</a>
			</div>
			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
				 <ul class="nav navbar-nav navbar-right">
					<li class="hidden"><a href="#page-top"></a></li>
					<li><a class="page-scroll navTextTag" href="<%=request.getContextPath()%>/index.jsp">
						<i class="glyphicon glyphicon-home"></i>
						回首頁</a>
					</li>
					<li>
						<a class="page-scroll navTextTag" href="<%=request.getContextPath()%>/frontend/location/location.jsp">
						<i class="glyphicon glyphicon-map-marker"></i>
						服務據點</a>
					</li>					
					<li>
						<a class="page-scroll navTextTag" href="<%=request.getContextPath()%>/frontend/rental_form/rental_category.jsp">
						<i class="glyphicon glyphicon-heart"></i>
						我要租車</a>
					</li>
					<li>
						<a class="navTextTag" href="<%=request.getContextPath()%>/frontend/second_order/listAllSecond.jsp">
						<i class="fa fa-shopping-cart"></i>
						二手車購買</a>
					</li>
					<c:if test="${not empty memno}">	
						<li>
							<a class="navTextTag" href="<%=request.getContextPath()%>/backend/member/member.do?action=getOne_For_Enter&memid=${memno}">
							會員專區</a>
						</li>
						<li><a class="navTextTag" href="#" class="disabled">歡迎，${(memname == null) ? '會員':memname}</a></li>
						<li>
							<a class="navTextTag" href="<%=request.getContextPath()%>/backend/member/member.do?action=logout" data-toggle="modal"><i class="glyphicon glyphicon-user"></i>
							登出</a>
						</li>
					</c:if>
				</ul>
			</div>
		</div>
	</nav>
<%--end Navigation --%>
	
	
	<div class="container divTag">
		<%@ include file="page1.file"%>
		<c:forEach var="mesboardVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
			<div align=center>
				<div class="chat_list" style="position: relative;">
					<ul class="list-group">
						<li class="list-group-item">
							<div class="pull-left hidden-xs" style="margin-right: 600px;">
								<img class="img-circle" title="User1" alt="User1" src="<%=request.getContextPath()%>/img/smile.jpg" style="max-width:40px;max-height:40px;"> 
									<small class="list-group-item-heading text-muted text-primary"><font face="serif" size="3.5" color="green">${memSvc.getOneMember(mesboardVO.memno).memname}</font></small>
							</div>
							<div style="margin-left: 500px;">
							<small class="pull-right text-muted"><font size="3.5"face="monospace" color="green">發表:<fmt:formatDate value="${mesboardVO.mesdate}" pattern="yyyy-MM-dd HH:mm:ss"/></font></small><br> 
							</div>
							<div align="left">
								<jsp:useBean id="memSvc" scope="page" class="com.member.model.MemberService"/>
							</div>
							<div>
								<img src="<%=request.getContextPath()%>/backend/mes_board/mesboardread.do?mesno=${mesboardVO.mesno}"
									style="max-width: 300px; max-height: 300px;">
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
	
				<FORM METHOD="post" ACTION="mesboard.do">
					<!-- 			     <input type="submit" value="修改">  -->
					<%-- 			     <input type="hidden" name="mesno" value="${mesboardVO.mesno}"> --%>
					<input type="hidden" name="requestURL"
						value="<%=request.getServletPath()%>">
					<!--送出本網頁的路徑給Controller-->
					<%-- 			    <input type="hidden" name="whichPage"	value="<%=whichPage%>">               <!--送出當前是第幾頁給Controller--> --%>
					<input type="hidden" name="action" value="getOne_For_Update">
				</FORM>
				<FORM METHOD="post" ACTION="mesboard.do">
					<!-- 			    <input type="submit" value="刪除"> -->
					<%-- 			    <input type="hidden" name="mesno" value="${mesboardVO.mesno}"> --%>
					<input type="hidden" name="requestURL"
						value="<%=request.getServletPath()%>">
					<!--送出本網頁的路徑給Controller-->
					<%-- 			    <input type="hidden" name="whichPage"	value="<%=whichPage%>">               <!--送出當前是第幾頁給Controller--> --%>
					<input type="hidden" name="action" value="delete">
				</FORM>
		</c:forEach>
		<%@ include file="page2.file"%>
		<!-- 	--------------------------------------------------------------------------------------------------------- -->
	
		<table style="margin-left: 230px;" border='1' cellpadding='5'
			cellspacing='0' width='60%' bgcolor='white'>
			<tr>
				<td>
					<h3 align="center" id="h3Tag">新增留言</h3>
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
	
		<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/forntend/mes_board/mesboard.do"
			name="form1" enctype="multipart/form-data">
			<table
				style="border: 3px #FFAC55 dashed; padding: 5px; background-color: #FFFFFF; margin-left: 230px;"
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
	
				<div style="width: 60%; margin-left: 230px;">
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
			<br> <input type="hidden" name="action" value="insert"> 
			<input type="hidden" name="memno" value="<%=memno%>"> 
			<input type="hidden" name="requestURL" value="<%=request.getServletPath()%>">
		</FORM>
	</div>
	
	<script src="<%=request.getContextPath()%>/backend/Modified/jquery_1_10_1_min.js"></script>
	<script src="<%=request.getContextPath()%>/backend/Modified/twitter_bootstrap_3_3_7_min.js"></script>
	<script src="<%=request.getContextPath()%>/js/agency.min.js"></script>
	<script type="text/javascript" src="//cdnjs.cloudflare.com/ajax/libs/holder/2.4.1/holder.js"></script>

</body>
</html>