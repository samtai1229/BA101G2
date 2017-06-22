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

		<table id="QueryTable">
			  <thead>
			  	
					<tr class="QueryTable_TR">
						<th>車輛編號</th>
						<th>車輛型號</th>
						<th>廠牌</th>
						<th>車輛名稱</th>
						<th>車牌號碼</th>	
						<th>出廠日期</th>
						<th>累計里程</th>
						<th>據點編號</th>
						<th>車輛狀態</th>				
						<th>備註</th>
						<th>修改</th>
						<th>刪除</th>								
					</tr>
				  </thead>
<jsp:useBean id="mmSvc" scope="page" class="com.motor_model.model.MotorModelService"/>
				  <tbody>				  		
			 		<c:forEach var="motorVO" items="${get_motors_by_modtype}">
						<tr class="QueryTable_TR">
							<td>${motorVO.motno}</td>
							<td>${motorVO.modtype}</td>
							<td>${mmSvc.findByPK(motorVO.modtype).brand}</td>
							<td>${mmSvc.findByPK(motorVO.modtype).name}</td>
							<td>${motorVO.plateno}</td>
						    <td><fmt:formatDate pattern = "yyyy-MM" value = "${motorVO.manudate}" /></td>
							<td>${motorVO.mile}</td>
							<td>${motorVO.locno}</td>
							<td>${motorVO.status}</td>
							<td>${motorVO.note}</td>
							<td>
							<input type="hidden" name="action" value="update">
							<input type="submit" value="update" class="click2" /> 
							</td>
							<td>
							<input type="hidden" name="action" value="delete">
							<input type="submit" value="delete" class="click2" /> 
							</td>								
						</tr>
					</c:forEach>							
			  </tbody>	 	  								
		</table>
	<script src="Modified/QueryTablePagination.js"></script> 
</body>
</html>