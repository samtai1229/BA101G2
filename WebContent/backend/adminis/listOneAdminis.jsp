<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.adminis.model.*"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.adminis.model.*"%>
<%
	AdminisService admSvc = new AdminisService();
	List<AdminisVO> list = admSvc.getAll();
	pageContext.setAttribute("list", list);
%>
<!DOCTYPE html>
<html>

<head>
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
    <link rel="stylesheet" href="${pageContext.request.contextPath}Modified/backendHP_css.css">

</head>
   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>所有管理員資料</title>
</head>
<body bgcolor='white'>

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
        <img id="menuLogo" src="images/logo.jpg">
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
    <div class="col-xs-12 col-sm-10 rightHTML">
		<div class="topTitle">
            <h1>後端權限管理</h1>
        </div>
        
        <div class="container-fluid">

	<table border='1' cellpadding='5' cellspacing='0' width='600'>
		<tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
			<td>
				<h3>員工資料</h3> <a href="<%=request.getContextPath()%>/backend/adminis/adm_select_page.jsp"><img src="images/back1.gif" width="100" height="32" border="0">回首頁</a>
			</td>
		</tr>
	</table>

	<table border='1' bordercolor='#CCCCFF' width='800'>
		<tr>
			<th>管理員編號</th>
			<th>據點編號</th>
			<th>權限類別編號</th>
			<th>管理員姓名</th>
			<th>管理員帳號</th>
			<th>管理員密碼</th>
		</tr>
		<tr align='center' valign='middle'>
			<td>${adminisVO.admno}</td>
			<td>${adminisVO.locno}</td>
			<td>${adminisVO.authno}</td>
			<td>${adminisVO.name}</td>
			<td>${adminisVO.acc}</td>
			<td>${adminisVO.pw}</td>
		</tr>
	</table>
</body>
</html>