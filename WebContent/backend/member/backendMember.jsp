<%@page import="java.sql.Timestamp"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
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
   <title>會員管理-AutoBike</title>
</head>

<style>

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

<body>
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
        <img id="menuLogo" src="<%=request.getContextPath()%>/backend/images/android_logo2.jpg">
        <button class="accordion accordionMenu accordion accordionMenuMenu">總部管理系統</button>
        <div class="btn-group-vertical">
                
            <a class="btn btn-default" href="<%=request.getContextPath()%>/backend/motor/backendMotor.jsp"  role="button">車輛資料管理</a>
            <a class="btn btn-default" href="<%=request.getContextPath()%>/backend/motor_model/backendMotorModel.jsp"  role="button">車輛型號管理</a>           
            <a class="btn btn-default" href="#" role="button">車輛調度</a>            
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
        <div class="btn-group-vertical">
            <a class="btn btn-default" href="<%=request.getContextPath()%>/backend/member/backendMember.jsp" role="button">會員管理</a>
            <a class="btn btn-default" href="<%=request.getContextPath()%>/backend/member/addMember.jsp" role="button">新增會員</a>
        </div>
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
            <h1>會員資料管理系統</h1>
        </div>
         		<div class="container-fluid">       
<jsp:useBean id="roSvc" scope="page" class="com.rent_ord.model.RentOrdService"/>
<jsp:useBean id="locSvc" scope="page" class="com.location.model.LocationService"/>
<jsp:useBean id="motorSvc" scope="page" class="com.motor.model.MotorService"/>
<jsp:useBean id="memSvc" scope="page" class="com.member.model.MemberService"/>
						<div style="padding:5px; padding-left:0px">
							<b>Show / Hide Columns: </b>
							<a class="showHideColumn" data-columnindex="0">會員編號</a> -
							<a class="showHideColumn" data-columnindex="1">姓名</a> -
							<a class="showHideColumn" data-columnindex="2">會員狀態</a> -
							<a class="showHideColumn" data-columnindex="3">性別</a> -
							<a class="showHideColumn" data-columnindex="4">生日</a> -
							<a class="showHideColumn" data-columnindex="5">信箱</a> -
							<a class="showHideColumn" data-columnindex="6">手機號碼</a> -
							<a class="showHideColumn" data-columnindex="7">地址</a> -
							<a class="showHideColumn" data-columnindex="8">帳號</a> -
							<a class="showHideColumn" data-columnindex="10">建檔日期</a> -
							
						</div>	
						<table id="dataTable" class="stripe hover" width="100%" cellspacing="0">
							  <thead>	
									<tr class="QueryTable_TR">
										<th>查詢</th>	
										<th>會員編號</th>
										<th>姓名</th>	
										<th>會員狀態</th>	
										<th>性別</th>
										<th>生日</th>				
										<th>信箱</th>
										<th>手機號碼</th>
										<th>地址</th>				
										<th>帳號</th>
										<th>建檔日期</th>
									</tr>
							  </thead>
							  <tbody>				  		
						 		<c:forEach var="memVO" items="${memSvc.all}">
									<tr class="QueryTable_TR">
										<td>
										  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/member/member.do">
										     <input type="submit" value="查詢" class="btn btn-default"> 
										     <input type="hidden" name="memno" value="${memVO.memno}">
										     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller--><!-- 目前尚未用到  -->
										     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
										</td>
										<td>${memVO.memno}</td>
										<td>${memVO.memname}</td>
										<td>${memVO.status}</td>
										<td>${memVO.sex}</td>	
										<td><fmt:formatDate pattern = "yyyy/MM/dd" value = "${memVO.birth}" /></td>
										<td>${memVO.mail}</td>
										<td>${memVO.phone}</td>
										<td>${memVO.addr}</td>
										<td>${memVO.acc}</td>
										<td><fmt:formatDate pattern = "yyyy/MM/dd" value = "${memVO.credate}" /></td>
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

</script>  
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="<%=request.getContextPath()%>/backend/rent_ord/Modified/rentOrdNew.js"></script>
    <script src="<%=request.getContextPath()%>/backend/rent_ord/Modified/motorKanli_for_ro.js"></script>
    <script src="<%=request.getContextPath()%>/backend/rent_ord/Modified/datepicker_for_ro.js"></script>
    
</body>
</html>


