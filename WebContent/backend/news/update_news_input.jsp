<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.news.model.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="java.sql.Timestamp"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	NewsVO newsVO = (NewsVO) request.getAttribute("newsVO"); //NewsServlet.java (Concroller), 存入req的newsVO物件 (包括幫忙取出的newsVO, 也包括輸入資料錯誤時的newsVO物件)
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>最新資料修改</title>

<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>Backend HP</title>
    <meta name="description" content="">
    <meta name="keywords" content="">
    <link href="" rel="stylesheet">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
    <script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
	<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
    <link rel="stylesheet" href="Modified/backendHP_css.css">
    <link href="Modified/main.css" rel="stylesheet">
    <script src="Modified/motorKanli_js.js"></script>
    <script src="Modified/datepicker.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/backend/motor/js/motorMgmtHqSelectPage_css.css">
	<link rel="stylesheet" type="text/css" href="js/calendar.css">
	<script language="JavaScript" src="js/calendarcode.js"></script>
</head>



<body bgcolor='white'>
<div id="popupcalendar" class="text"></div>

<nav class="navbar navbar-default" role="navigation">
        <!-- logo區 -->
        <a class="navbar-brand" href="#" id="navA">AUTOBIKE</a>
        <!-- 左選單 -->
        <ul class="nav navbar-nav">
            <li><a href="#" id="navA">後端管理系統</a></li>
            <!-- 時鐘 -->
            <iframe scrolling="no" frameborder="no" clocktype="html5" style="overflow:hidden;border:0;margin:0;padding:0;width:120px;height:40px;" src="http://www.clocklink.com/html5embed.php?clock=004&timezone=CCT&color=yellow&size=120&Title=&Message=&Target=&From=2017,1,1,0,0,0&Color=yellow">
            </iframe>
        </ul>
        <!-- 右選單 -->
        <ul class="nav navbar-nav navbar-right">
        </ul>
    </nav>
    <div class="col-xs-12 col-sm-2 leftBar">
        <img id="menuLogo" src="<%=request.getContextPath()%>/backend/adminis/images/logo.jpg">
        <button class="accordion accordionMenu accordion accordionMenuMenu">總部管理系統</button>
        <div class="btn-group-vertical">
            <a class="btn btn-default" href="#" role="button">車輛管理</a>
            <a class="btn btn-default" href="#" role="button">車輛調度</a>
            <a class="btn btn-default" href="#" role="button">租賃單管理</a>
            <a class="btn btn-default" href="#" role="button">裝備管理</a>
            <a class="btn btn-default" href="#" role="button">裝備調度</a>
            <a class="btn btn-default" href="#" role="button">據點管理</a>
        </div>
        <button class="accordion accordionMenu">據點管理系統</button>
        <div class="btn-group-vertical">
            <a class="btn btn-default" href="#" role="button">據點車輛管理</a>
            <a class="btn btn-default" href="#" role="button">交車管理</a>
            <a class="btn btn-default" href="#" role="button">還車管理</a>
            <a class="btn btn-default" href="#" role="button">車輛調度申請</a>
            <a class="btn btn-default" href="#" role="button">車輛保養/維修管理</a>
            <a class="btn btn-default" href="#" role="button">據點裝備管理</a>
            <a class="btn btn-default" href="#" role="button">裝備申請</a>
        </div>
        <button class="accordion accordionMenu">二手車管理系統</button>
        <div class="btn-group-vertical">
            <a class="btn btn-default" href="#" role="button">二手車輛管理</a>
            <a class="btn btn-default" href="#" role="button">二手車訂單管理</a>
            <a class="btn btn-default" href="#" role="button">二手車交易管理</a>
        </div>
        <button class="accordion accordionMenu">會員管理系統</button>
        <div class="btn-group-vertical"></div>
        <button class="accordion accordionMenu">活動企劃管理系統</button>
        <div class="btn-group-vertical">
            <a class="btn btn-default" href="#" role="button">推播管理</a>
            <a class="btn btn-default" href="#" role="button">留言版管理</a>
            <a class="btn btn-default" href="#" role="button">最新消息管理</a>
        </div>
        <button class="accordion accordionMenu">後端管理系統</button>
        <div class="btn-group-vertical">
            <a class="btn btn-default" href="#" role="button">後端權限管理</a>
            <a class="btn btn-default" href="#" role="button">推薦景點管理</a>
            <a class="btn btn-default" href="#" role="button">後端登入管理</a>
        </div>
        <div class="btn-group-vertical"></div>
    </div>
    <div>
    
    <div class="col-xs-12 col-sm-10 rightHTML">
		<div class="topTitle">
            <h1>最新消息修改</h1>
        </div>
			<td><a
				href="<%=request.getContextPath()%>/backend/news/news_select_page.jsp">回首頁</a>
			</td>
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

	<FORM METHOD="post" ACTION="news.do" name="form1"
		enctype="multipart/form-data">
		<table border="0">
			<tr>
				<td>最新消息編號:<font color=red><b>*</b></font></td>
				<td><%=newsVO.getNewsno()%></td>
			</tr>
			<jsp:useBean id="adminisSvc" scope="page"
				class="com.adminis.model.AdminisService" />
			<tr>
				<td>管理員姓名:<font color=red><b>*</b></font></td>
				<td><select size="1" name="admno">
						<c:forEach var="adminisVO" items="${adminisSvc.all}">
							<option value="${adminisVO.admno}"
								${(newsVO.admno==adminisVO.admno)? 'selected':'' }>${adminisVO.name}
						</c:forEach>
				</select></td>
			</tr>
			<tr>
				<td>消息內容:</td>
				<td><input type="TEXT" name="cont" size="45"
					value="<%=(newsVO == null) ? "CONT" : newsVO.getCont()%>" /></td>
			</tr>
			<tr>
				<td>消息圖片修改:</td>
				<td><input type="file" name="pic">
				<p><img src="<%=request.getContextPath()%>/backend/news/newsread.do?newsno=${newsVO.newsno}" style= max-width:150px;max-height:150px;></img></p>
				</td>
			</tr>
			<tr>
				<td>消息標題:</td>
				<td><input type="TEXT" name="title" size="45"
					value="<%=(newsVO == null) ? "TITLE" : newsVO.getTitle()%>" /></td>
			</tr>
			<jsp:useBean id="newsSvc" scope="page"
				class="com.news.model.NewsService" />
			<tr>
				<td>最新消息狀態:<font color=red><b>*</b></font></td>
				<td><select size="1" name="status">
							<option value="normal">正常顯示</option>
							<option value="hid">隱藏</option>
				</select></td>
			</tr>
		</table>
		<br> <input type="hidden" name="action" value="update"> <input
			type="hidden" name="newsno" value="<%=newsVO.getNewsno()%>">
		<input type="hidden" name="requestURL"
			value="<%=request.getParameter("requestURL")%>">
		<!--接收原送出修改的來源網頁路徑後,再送給Controller準備轉交之用-->
		<input type="hidden" name="whichPage"
			value="<%=request.getParameter("whichPage")%>">
		<!--只用於:istAllEmp.jsp-->
		<input type="submit" value="送出修改">
	</FORM>
</body>
</html>
