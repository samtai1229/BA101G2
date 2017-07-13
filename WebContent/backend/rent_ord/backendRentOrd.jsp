<%@page import="java.sql.Timestamp"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">	

	<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
	<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
	<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css"/>	
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">     
	<link rel="stylesheet" href="<%=request.getContextPath()%>/backend/Modified/backendHP_css.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/backend/Modified/main.css" >
	<link rel="stylesheet" type="text/css" href="//cdn.datatables.net/1.10.15/css/jquery.dataTables.min.css">
	<script type="text/javascript" src="//cdn.datatables.net/1.10.15/js/jquery.dataTables.min.js"></script>

   <title>租賃單管理-AutoBike</title>
</head>

<style>
/* Center the loader */
#loader {
  position: absolute;
  left: 50%;
  top: 50%;
  z-index: 1;
  width: 150px;
  height: 150px;
  margin: -75px 0 0 -75px;
  border: 16px solid #f3f3f3;
  border-radius: 50%;
  border-top: 16px solid #3498db;
  width: 120px;
  height: 120px;
  -webkit-animation: spin 0.5s linear infinite;
  animation: spin 0.5s linear infinite;
}

@-webkit-keyframes spin {
  0% { -webkit-transform: rotate(0deg); }
  100% { -webkit-transform: rotate(360deg); }
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}
/* Add animation to "page content" */
.animate-bottom {
  position: relative;
  -webkit-animation-name: animatebottom;
  -webkit-animation-duration: 1s;
  animation-name: animatebottom;
  animation-duration: 1s
}
@-webkit-keyframes animatebottom {
  from { bottom:-100px; opacity:0 } 
  to { bottom:0px; opacity:1 }
}

@keyframes animatebottom { 
  from{ bottom:-100px; opacity:0 } 
  to{ bottom:0; opacity:1 }
}

#myDiv {
  display: none;
  text-align: center;
}

th,td{
	height:20px;

}

/*自定*/
	 th, tr{
		/*死都不換行*/
		white-space:nowrap;
	} 
</style>

<body onload="myFunction()" style="margin:0;">

<div id="loader"></div>

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
 <%--保留寫法     href="<%=request.getContextPath()%>/backend/backendRentOrd.jsp"  --%>
    <div class="col-xs-12 col-sm-2 leftBar">
        <img id="menuLogo" src="<%=request.getContextPath()%>/backend/images/android_logo2.jpg">
        <button class="accordion accordionMenu accordion accordionMenuMenu">總部管理系統</button>
        <div class="btn-group-vertical">
                
            <a class="btn btn-default" href="<%=request.getContextPath()%>/backend/motor/backendMotor.jsp"  role="button">車輛資料管理</a>
            <a class="btn btn-default" href="<%=request.getContextPath()%>/backend/motor_model/backendMotorModel.jsp"  role="button">車輛型號管理</a>           
            <a class="btn btn-default" href="#" role="button">車輛調度</a>            
<!--        <a class="btn btn-default" role="button" onclick="loadRentOrd()">租賃單管理</a> -->
			<a class="btn btn-default" href="<%=request.getContextPath()%>/backend/rent_ord/backendRentOrd.jsp" role="button">租賃單管理</a>
            <a class="btn btn-default" href="#" role="button">裝備管理</a>
            <a class="btn btn-default" href="#" role="button">裝備調度</a>
            <a class="btn btn-default" href="#" role="button">據點管理</a>
        </div>
        <button class="accordion accordionMenu">據點管理系統</button>
        <div class="btn-group-vertical">
            <a class="btn btn-default" href="#" role="button">據點車輛管理</a>
            <a class="btn btn-default" href="<%=request.getContextPath()%>/backend/rent_ord/lease.jsp"  role="button">交車管理</a>
          	<a class="btn btn-default" href="<%=request.getContextPath()%>/backend/rent_ord/return.jsp"  role="button">還車管理</a>
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
    <div class="col-xs-12 col-sm-10 rightHTML" id="demo">

		<div class="topTitle">
            <h1>租賃單管理系統</h1>
        </div>
         		<div class="container-fluid">       
<jsp:useBean id="roSvc" scope="page" class="com.rent_ord.model.RentOrdService"/>
<jsp:useBean id="locSvc" scope="page" class="com.location.model.LocationService"/>
<jsp:useBean id="motorSvc" scope="page" class="com.motor.model.MotorService"/>
						<div style="padding:5px; padding-left:0px">
							<b>Show / Hide Columns: </b>
							<a class="showHideColumn" data-columnindex="0">租賃單號</a> -
							<a class="showHideColumn" data-columnindex="1">會員編號</a> -
							<a class="showHideColumn" data-columnindex="2">車輛編號</a> -
							<a class="showHideColumn" data-columnindex="3">交車據點</a> -
							<a class="showHideColumn" data-columnindex="4">還車據點</a> -
							<a class="showHideColumn" data-columnindex="5">年份</a> -
							<a class="showHideColumn" data-columnindex="6">填表日</a> -
							<a class="showHideColumn" data-columnindex="7">起始日</a> -
							<a class="showHideColumn" data-columnindex="8">結束日</a> -
							<a class="showHideColumn" data-columnindex="9">還車日</a> -
							<a class="showHideColumn" data-columnindex="10">罰金</a> -
							<a class="showHideColumn" data-columnindex="11">總金額</a> -
							<a class="showHideColumn" data-columnindex="12">評價</a> -
							<a class="showHideColumn" data-columnindex="13">狀態</a> -
						</div>	
						<table id="dataTable" class="stripe hover" width="80%" cellspacing="0">
							  <thead>	
									<tr class="QueryTable_TR">
										<th>租賃單號</th>
										<th>會員編號</th>		
										<th>車輛編號</th>
										<th>交車據點</th>				
										<th>還車據點</th>
										<th>年份</th>
										<th>填表日</th>				
										<th>起始日</th>
										<th>結束日</th>
										<th>還車日</th>
										<th>罰金</th>				
										<th>總金額</th>
										<th>評價</th>
										<th>狀態</th>	
										<th>修改</th>	
										<th>刪除</th>	
										<!--
										<th>里程數起始</th>
										<th>里程數結束</th> 
										<th>備註</th> 
										-->	
									</tr>
							  </thead>
							  <tbody>				  		
						 		<c:forEach var="roVO" items="${roSvc.all}">
									<tr class="QueryTable_TR">
										<td>${roVO.rentno}</td>
										<td>${roVO.memno}</td>
										<td>${roVO.motorVO.motno}</td>	
										<td>${locSvc.getOneLocation(roVO.slocno).locname}</td>
										<td>${locSvc.getOneLocation(roVO.rlocno).locname}</td>
											<td><fmt:formatDate pattern = "yyyy" value = "${roVO.filldate}" /></td>
										<td><fmt:formatDate pattern = "MM/dd" value = "${roVO.filldate}" /></td>
										<td><fmt:formatDate pattern = "MM/dd" value = "${roVO.startdate}" /></td>									
										<td><fmt:formatDate pattern = "MM/dd" value = "${roVO.enddate}" /></td>
										<td><fmt:formatDate pattern = "MM/dd" value = "${roVO.returndate}" /></td>
										<td>${roVO.fine}</td>
										<td>${roVO.total}</td>
										<td>${roVO.rank}</td>
										<td>${roVO.status}</td>	
										<td>
											<form method="POST" target="print_popup" 
					      				  		  action="<%=request.getContextPath()%>/backend/rent_ord/rentOrd.do" 
					       						  onsubmit="window.open('about:blank','print_popup','width=1000,height=900');">
												<input type="hidden" name="rentno" value="${roVO.rentno}">
												<input type="hidden" name="action" value="query_for_update">
												<input type="submit" class="btn btn-default" value="修改">
											</form>	
										</td>
										<!-- 
										<td>${roVO.milstart}</td>
										<td>${roVO.milend}</td>
										<td>${roVO.note}</td>-->	
										<td>
											<form method="POST" action="<%=request.getContextPath()%>/backend/rent_ord/rentOrd.do">
												<input type="hidden" name="action" value="delete">
												<input type="hidden" name="rentno" value="${roVO.rentno}">
												<input type="submit" value="刪除" class="btn btn-danger" disabled/>
											</form> 
										</td> 
																	
									</tr>
								</c:forEach>							
						  </tbody>	 	  								
						</table>
					</div>
<!--end: block3 --> 	
<!--container--></div>	
<script>
//table
$(document).ready(function(){
	var datatableInstance = $('#dataTable').DataTable({
		
	});
	

	$('.showHideColumn').on('click',function(){
		var tableColumn = datatableInstance.column($(this).attr('data-columnindex'));
		tableColumn.visible(!tableColumn.visible());
	})
});


//loader
var myVar;

function myFunction() {
    myVar = setTimeout(showPage, 50);
}

function showPage() {
  document.getElementById("loader").style.display = "none";
  document.getElementById("myDiv").style.display = "block";
}

</script>  


    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="<%=request.getContextPath()%>/backend/rent_ord/Modified/rentOrdNew.js"></script>
    <script src="<%=request.getContextPath()%>/backend/rent_ord/Modified/motorKanli_for_ro.js"></script>
    <script src="<%=request.getContextPath()%>/backend/rent_ord/Modified/datepicker_for_ro.js"></script>
</body>
</html>


