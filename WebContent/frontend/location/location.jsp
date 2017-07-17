<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*"%>
<%@ page import="com.location.model.*"%>
<%@ page import="com.news.model.*"%>


<!--  	LocationService locationSvc = new LocationService();
 	List<LocationVO> list = locationSvc.getAll();
 	pageContext.setAttribute("list",list); -->


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 <meta name="generator" content="HTML Tidy for HTML5 (experimental) for Windows https://github.com/w3c/tidy-html5/tree/c63cc39" />
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <meta name="description" content="" />
    <meta name="author" content="" />
    <!-- Bootstrap Core CSS -->
    <link href="<%=request.getContextPath()%>/js/bootstrap.min.css" rel="stylesheet" />
    <link href="<%=request.getContextPath()%>/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
	<link href='https://fonts.googleapis.com/css?family=Roboto+Slab:400,100,300,700' rel='stylesheet' type='text/css' />
    <!-- Theme CSS -->
    <link href="<%=request.getContextPath()%>/js/gallery.css" rel="stylesheet" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/js/owl.carousel.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/js/owl.theme.default.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/magnific-popup.css">
    <!-- Theme Style -->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
    <link href="<%=request.getContextPath()%>/js/agency.css" rel="stylesheet" />
    <link href="<%=request.getContextPath()%>/css/agency.min.css" rel="stylesheet" />

<script src="<%=request.getContextPath()%>/frontend/location/js/googlemap.js"></script>

<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.11/jquery-ui.min.js"></script>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css"/>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
<title>Insert title here</title>

<style>
    input[type=date] {
        box-sizing: border-box;
        border: 2px solid #ccc;
        border-radius: 16px;
        font-size: 30px;
        background-color: transparent;
        text-align: center;
        background-position: 10px 10px;
        line-height: 48px;
    }
    /*input[type=month]:focus {
    width: 100%;
}
*/
    .map{
        margin-top:10px;
    }
    input[type=submit] {
        background-color: transparent;
        text-align: center;
        box-sizing: border-box;
        font-size: 30px;
        border-radius: 16px;
        line-height: 48px;
        border: 2px solid #ccc;
    }
	

    </style>

</head>

<style type="text/css">
.location table,th,td{
	border: 1px solid black;
	border-collapse: collapse;
	padding:15px;
	text-align:center;
}
</style>

<body id="page-top" class="index">
	  <header style=" height:100px; background-image: url(<%=request.getContextPath()%>/img/header2.jpg);">
        <!-- Navigation -->
        <nav id="mainNav" class="navbar navbar-default navbar-custom navbar-fixed-top">
            <div class="container-fluid">
                <!-- Brand and toggle get grouped for better mobile display -->
                <div class="navbar-header page-scroll">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                        <span class="sr-only">Toggle navigation</span> Menu</button>
                    <a class="navbar-brand page-scroll" href="#page-top">AutoBike</a></div>
                <!-- Collect the nav links, forms, and other content for toggling -->
                <div class="collapse navbar-collapse text-center" id="bs-example-navbar-collapse-1">
                    <ul class="nav navbar-nav navbar-right">
                        <li class="hidden">
                            <a href="#page-top"></a>
                        </li>
                        <li>
                            <a class="page-scroll" href="rent.html"><i class="fa fa-smile-o"></i> 我要租車</a>
                        </li>
                        <li>
                            <a class="page-scroll" href="news.html"><i class="glyphicon glyphicon-alert"></i>最新消息</a>
                        </li>
                        <li>
                            <a class="page-scroll" href="leave_message_Page.html"><i class="fa fa-comments-o"></i>留言</a>
                        </li>
                       
                        <li>
                            <a href="second.html"><i class="fa fa-shopping-cart"></i>二手車購買</a>
                        </li>
                        <li>
                            <a href="#modal-id" data-toggle="modal"><i class="glyphicon glyphicon-log-in"></i>會員登入</a>
                        </li>
                    </ul>
                </div>
                <!-- /.navbar-collapse -->
            </div>
            <!-- /.container-fluid -->
        </nav>
    </header>
    
    </header>
        <div class="container">
            <div class="row container-fluid">
            <br>
                <div class="col-xs-12 col-sm-12" >
                   <h1>聯絡據點</h1>
                </div>
                
                   <!--  <audio hidden="true" controls autoplay loop="1">
                        <source src="家家- 家家酒.mp3" type="audio/mpeg" />
                    </audio> -->
               
            </div>
        </div>
    
<jsp:useBean id="locationSvc" scope="page" class="com.location.model.LocationService" />

<div class="row">
	

<div class="col-xs-12 col-sm-6 map">
	<div id="myMap" style="width:800px;height:800px;"></div>
</div>

<div class="col-xs-12 col-sm-6">
		<table id="location" class="table">
			<tr text-align="center">
				<th>據點地點</th>
				<th>據點地址</th>
				<th>據點經度</th>
				<th>據點緯度</th>
				<th>據點電話</th>
				<th>據點照片</th>
			</tr>
					
			<c:forEach var="locationVO" items="${locationSvc.allStatusOpen}" >
						
						<tr> 
							<td>${locationVO.locname}</td>
							<td>${locationVO.addr}</td>
							<td>${locationVO.lon}</td>
							<td>${locationVO.lat}</td>
							<td>${locationVO.tel}</td>
							<td > 
							<img id="address" name="address" src="<%=request.getContextPath()%>/frontend/location/locReader.do?locno=${locationVO.locno}"
										class="img-responsive img-circle" alt="" width="100" height="100" onclick=changeLatLng('${locationVO.lon}','${locationVO.lat}') />
							</td>
						</tr>
						
			</c:forEach>
		</table>
	</div>

</div>

<footer style="background-color: lightgray">
        <div class="col-xs-12 col-sm-12"><span>聯絡資訊</span></div>
        <div class="container-fluid">
            <div class="col-xs-12 col-sm-4"><span>地址:桃園市平鎮區中央路115號</span></div>
            <div class="col-xs-12 col-sm-4"><span>EMAIL:taic@oregonstate.edu</span></div>
            <div class="col-xs-12 col-sm-4"><span>TEL:0900-000-000</span></div>
        </div>
    </footer>

	<!-- jQuery -->
	 <script src="<%=request.getContextPath()%>vendor/jquery/jquery.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
	<script type="text/javascript"
		src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
	
	
	
	<!-- Bootstrap Core JavaScript -->
	<script src="<%=request.getContextPath()%>/vendor/bootstrap/js/bootstrap.min.js"></script>
	<!-- Plugin JavaScript -->
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.3/jquery.easing.min.js"
		integrity="sha384-mE6eXfrb8jxl0rzJDBRanYqgBxtJ6Unn4/1F7q4xRRyIw7Vdg9jP4ycT7x1iVsgb"
		crossorigin="anonymous"></script>
	<!-- Owl carousel -->
	<script src="<%=request.getContextPath()%>/js/owl.carousel.min.js"></script>
	<!-- Waypoints -->
	<!-- Magnific Popup -->
	<!-- Main JS -->
	<script src="<%=request.getContextPath()%>/js/main.js"></script>
	<!-- Contact Form JavaScript -->
	<script src="<%=request.getContextPath()%>/js/jqBootstrapValidation.js"></script>
	<script src="<%=request.getContextPath()%>/js/contact_me.js"></script>
	<!-- Theme JavaScript -->
	<script src="<%=request.getContextPath()%>/js/jquery.magnific-popup.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/agency.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/moment.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/daterangepicker.js"></script>
</body>
<!-- <script src="http://maps.google.com/maps/api/js"></script> -->

 <script async defer
     src="https://maps.googleapis.com/maps/api/js?key=AIzaSyB-fsKJXrclxXV39v3nt_zjC8RaS454leM&libraries=visualization">  
    </script>


</html>