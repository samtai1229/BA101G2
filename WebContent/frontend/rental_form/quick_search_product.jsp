<%@page import="java.sql.Timestamp"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.rent_ord.model.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
<title>Title Page</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" media="all" href="<%=request.getContextPath()%>/frontend/rental_form/Modified/other.css" />
<link rel="stylesheet" type="text/css" media="all" href="<%=request.getContextPath()%>/frontend/rental_form/Modified/daterangepicker.css" />

<%-- basic --%>
<link href="<%=request.getContextPath()%>/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
<link href="<%=request.getContextPath()%>/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
<link href='https://fonts.googleapis.com/css?family=Kaushan+Script' rel='stylesheet' type='text/css' />
<link href="<%=request.getContextPath()%>/frontend/rental_form/Modified/agency.min.css" rel="stylesheet" />
<link href="<%=request.getContextPath()%>/frontend/rental_form/Modified/agency.css" rel="stylesheet" />
<link href="<%=request.getContextPath()%>/frontend/rental_form/Modified/other.css" rel="stylesheet" />

</head>
<style type="text/css">

.btn{
	margin-right:5px;
	margin-left:5px;
}

.t1{

margin-top:100px;

}

#service-one{
font-size:20px;

}

.margin-tag{
	margin-top:5px;
}

.dynamic-text{
	font-size:16px;
}


</style>

<body>

<%-- 
	從RentOrdServlet.java過來:
	 進這jsp的參數有  MotorVO motorQueryVO, dayPicker, 
	
	應該要從這個頁面送出去的資料有(next -> servlet -> 2.jsp)
	memno, motno, confirmed_rentday
--%>


<%
MotorVO motorQueryVO = (MotorVO)request.getAttribute("motorQueryVO");
String dayrange = request.getParameter("dayrange");
String dayPicker = (String)request.getAttribute("dayPicker");

String memno = (String)session.getAttribute("memno");
String status = (String)session.getAttribute("status");
pageContext.setAttribute("dayrange", dayrange);
String tokens[] = dayrange.split(" - ");
String start_time = tokens[0];
String end_time = tokens[1];
pageContext.setAttribute("start_time", start_time);
pageContext.setAttribute("end_time", end_time);
pageContext.setAttribute("memno",memno);

%>
<%--<c:out value="${status}"></c:out>
 memno: <c:out value="${memno}" default="no member login" /><br>
kk: <c:out value="${status}" default="no status login" /><br>
start_time:<c:out value="${start_time}" default="no value"/><br>
end_time:  <c:out value="${end_time}" default="no value"/><br>
dayPicker <c:out value="${dayPicker}" default="no value"></c:out> --%>

<!-- Navigation -->
	<nav id="mainNav" class="navbar navbar-default navbar-custom navbar-fixed-top">
		<div class="container-fluid">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header page-scroll">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Toggle navigation</span> Menu <i
						class="fa fa-bars"></i>
				</button>
				<a class="navbar-brand page-scroll" href="<%=request.getContextPath()%>/index.jsp">AutoBike</a>
			</div>

			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav navbar-right">
					<li class="hidden"><a href="#page-top"></a></li>
					<li><a class="page-scroll" href="<%=request.getContextPath()%>/frontend/rental_form/rental_category.jsp">
					<i class="glyphicon glyphicon-heart"></i>我要租車</a></li>
					<li><a class="page-scroll" href="#news">
					<i class="glyphicon glyphicon-alert"></i>最新消息</a></li>
					<li><a class="page-scroll" href="#board">
					<i class="fa fa-comments-o"></i>留言板</a></li>
					<li><a class="page-scroll" href="#loc">
					<i class="fa fa-search"></i>服務據點</a></li>
					<li><a href="<%=request.getContextPath()%>/backend/member/member.do">
					<i class="fa fa-shopping-cart"></i>二手車購買</a></li>
					<c:if test="${not empty memno}">
						<li><a href="<%=request.getContextPath()%>/backend/member/member.do?action=getOne_For_Enter&memid=${memno}">歡迎，${memname}</a></li>
						<li><a href="<%=request.getContextPath()%>/backend/member/member.do?action=logout" data-toggle="modal">
						<i class="glyphicon glyphicon-user"></i>登出</a>
						</li>
					</c:if>
				</ul>
			</div>
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container-fluid -->
	</nav>
	
	<div id="blocker"></div>


	<!-- 租車主軸Header -->
	<header id="rent">
<%----------------------------------------------------VVVV building area VVVV-----------------------------------------------------------%>
	
	
	<jsp:useBean id="mmSvc" scope="page" class="com.motor_model.model.MotorModelService"/>
		
			<div class="container">
				<div class="col-md-offset-1 col-md-6 t1">
					<img id="item-display"
						src="<%=request.getContextPath()%>/backend/motor_model/mmReader.do?modtype=${motorQueryVO.modtype}"
						alt=""></img>
				</div>
				<div class="col-md-5 t1">
				
					<div class="product-title">
						<div class="margin-tag">${mmSvc.findByPK(motorQueryVO.modtype).brand} ${mmSvc.findByPK(motorQueryVO.modtype).name}</div>
						<c:if test="${mmSvc.findByPK(motorQueryVO.modtype).displacement > 150}">
							<div class="margin-tag"><mark>重機專區</mark></div>
						</c:if>
							<div class="dynamic-text margin-tag">${mmSvc.findByPK(motorQueryVO.modtype).displacement}c.c.</div>						
						</div>
					<hr>
					
					<form METHOD="post" ACTION="<%=request.getContextPath()%>/backend/rent_ord/rentOrd.do">		
					<div class="product-price margin-tag">日租價    NT$${mmSvc.findByPK(motorQueryVO.modtype).renprice}</div>
					<div class="dynamic-text margin-tag">車輛編號 : ${motorQueryVO.motno}</div>
					<div class="product-stock margin-tag">可出租</div>
					<hr>
					<div class="dynamic-text InputForm">租用日期:</div><li id="rentaled">紅色標記為當日不可出租</li>
						
						<div class="input-group">
	                        <input type="text" id="invalidtodaynextweek" class="form-control" name="confirmed_rentday">
	                        <div class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></div>
	                    </div><br/>
                    
                    <jsp:useBean id="locSvc" scope="page" class="com.location.model.LocationService"/>
					
							
						<p class="text-center">
							<input type="hidden" name="motno" value="${motorQueryVO.motno}">
							<c:if test="<%=memno==null%>">
						    	<input type="hidden" name="action" value="redirect_to_login">
						    	<input type="hidden" name="location" value="<%=request.getServletPath()%>">
			   					<button type="submit" class="btn btn-info btn-lg">
									<i class="glyphicon glyphicon-log-in"></i> 會員登入
								</button>						    	
								<a onclick="history.back()" class="btn btn-danger btn-lg">
									<i class="glyphicon glyphicon-arrow-up"></i> 返回前頁
								</a>						    	
						    </c:if>
							<c:if test="<%=memno!=null%>">
								<c:if test="${status=='verifing'}">
									<a onclick="history.back()" class="btn btn-info btn-lg">
										<i class="glyphicon glyphicon-exclamation-sign"></i> 驗證中
									</a>						    	
							    </c:if>
								<c:if test="${status=='confirmed'}">
							    	<input type="hidden" name="action" value="quick_search_product_2">
				   					<button type="submit" class="btn btn-success btn-lg">
										<i class="glyphicon glyphicon-ok"></i>我要訂車
									</button>
									<a onclick="history.back()" class="btn btn-danger btn-lg">
										<i class="glyphicon glyphicon-arrow-up"></i> 返回前頁
									</a>						    	
							    </c:if>							    
								<c:if test="${status=='uncompleted'||status=='unconfirmed'}">
									<a href="<%=request.getContextPath()%>/backend/member/member.do?addAction=frontMember&action=getOne_For_Update&memno=${memno}"
									 class="btn btn-info btn-lg">
										<i class="glyphicon glyphicon-edit"></i> 前往驗證
									</a>					   					
							    </c:if>								    
						    </c:if>	

						</p>
					</form>
				</div>
			</div>
		
		<div class="container-fluid">
			<div class="col-md-12 product-info">
				<ul id="myTab" class="nav nav-tabs nav_tabs">
					<li class="active"><a href="#service-one" data-toggle="tab">車輛簡介</a></li>
				</ul>
				<div id="myTabContent" class="tab-content">
					<div class="tab-pane fade in active" id="service-one">
						<blockquote>${mmSvc.findByPK(motorQueryVO.modtype).intro}</blockquote>
					</div>
					<div class="tab-pane fade" id="service-two">
						<section class="container"></section>
					</div>
					<div class="tab-pane fade" id="service-three"></div>
				</div>
				<hr>
			</div>
		</div>


	<%----------------------------------------------------^^^^ building area ^^^^-----------------------------------------------------------%>		
	</header>
	<footer>
		<div class="container-fluid">
			<div class="col-xs-12 col-sm-4">
				<span>地址:桃園市平鎮區中央路115號</span>
			</div>
			<div class="col-xs-12 col-sm-4">
				<span>EMAIL:taic@oregonstate.edu</span>
			</div>
			<div class="col-xs-12 col-sm-4">
				<span>TEL:0900-000-000</span>
			</div>
		</div>
	</footer>
	
	
	<script src="https://code.jquery.com/jquery.js"></script>
    <script type="text/javascript" src="https://code.jquery.com/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="http://netdna.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="https://raw.githack.com/JaapMoolenaar/bootstrap-daterangepicker/master/moment.js"></script>
    <script type="text/javascript" src="https://raw.githack.com/JaapMoolenaar/bootstrap-daterangepicker/master/daterangepicker.js"></script>

		<!-- basic -->

	<script src="<%=request.getContextPath()%>/js/owl.carousel.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/jquery.magnific-popup.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/agency.min.js"></script>

<script type="text/javascript">


$(document).ready(function() {
	
	var arr = '<%=dayPicker%>'.split(" ");
    var startday = "${start_time}";
	var endday = "${end_time}";
	
	$('#invalidtodaynextweek').daterangepicker({
		selectPastInvalidDate: false,
		"timePicker": true,
		"timePicker24Hour": true,
		"timePickerIncrement": 30,
		"alwaysShowCalendars": true,
		"minDate": moment().fromNow(),
		"maxDate": moment().add(60, 'days'),
	    "startDate": startday,
	    "endDate": endday,
		locale: {
			format: 'MM/DD/YYYY H:mm'
		},
		isInvalidDate: function(date) {
			var t = date.format('DD-MM-YYYY');
			var flag = false;
			for (var i = 0; i < arr.length; i++) {
				flag = flag || (date.format('DD-MM-YYYY') == moment(arr[i]).format('DD-MM-YYYY'))
			}
			return flag;
		}
	}, function(start, end, label) {
		console.log("New date range selected: ' + start.format('YYYY-MM-DD') + ' to ' + end.format('YYYY-MM-DD') + ' (predefined range: ' + label + ')");
	});
});
 
</script>
</body>
</html>