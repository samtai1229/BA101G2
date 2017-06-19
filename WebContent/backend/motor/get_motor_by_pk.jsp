<%@page import="java.sql.Timestamp"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.motor.model.*"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>車輛查詢-AutoBike</title>
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

</head>
<body>

	<%
		MotorVO motorQueryVO = (MotorVO)request.getAttribute("motorQueryVO");
	%>
	車輛編號:<c:out value="${motorQueryVO.motno}" default="未收到結果"/><br>
	車輛型號:<c:out value="${motorQueryVO.modtype}" default="未收到結果"/><br>
	車牌號碼:<c:out value="${motorQueryVO.plateno}" default="未收到結果"/><br>
	引擎號碼:<c:out value="${motorQueryVO.engno}" default="未收到結果"/><br>
            出廠日期:<fmt:formatDate pattern = "yyyy-MM-dd" value = "${motorQueryVO.manudate}" /><br>
	累計里程:<c:out value="${motorQueryVO.mile}" default="未收到結果"/><br>
	據點編號:<c:out value="${motorQueryVO.locno}" default="未收到結果"/><br>
	車輛狀態:<c:out value="${motorQueryVO.status}" default="未收到結果"/><br>
	車輛備註<c:out value="${motorQueryVO.note}" default="NA"/><br>

</body>
</html>