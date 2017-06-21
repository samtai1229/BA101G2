<%@page import="java.sql.Timestamp"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.motor_model.model.*"%>
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
		MotorModelVO mmQueryVO = (MotorModelVO)request.getAttribute("mmQueryVO");
	%>
	車輛型號:<c:out value="${mmQueryVO.modtype}" default="未收到結果"/><br>
	廠牌:<c:out value="${mmQueryVO.brand}" default="未收到結果"/><br>
	車輛名稱:<c:out value="${mmQueryVO.name}" default="未收到結果"/><br>
	排氣量:<c:out value="${mmQueryVO.displacement}" default="未收到結果"/><br>
	租賃價格:<c:out value="${mmQueryVO.renprice}" default="未收到結果"/><br>
	出售價格:<c:out value="${mmQueryVO.saleprice}" default="未收到結果"/><br>
	<img src='<%=request.getContextPath()%>/backend/motor_model/mmReader.do?modtype=${mmQueryVO.modtype}' width='500'>

</body>
</html>