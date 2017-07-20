<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*"%>
<%@ page import="com.location.model.*"%>
<%@ page import="com.news.model.*"%>


<!--  	LocationService locationSvc = new LocationService();
 	List<LocationVO> list = locationSvc.getAll();
 	pageContext.setAttribute("list",list); -->
<%
Float lon = (Float)request.getAttribute("lon");
Float lat = (Float)request.getAttribute("lat");
pageContext.setAttribute("lon", lon);
pageContext.setAttribute("lat", lat);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 <meta name="generator" content="HTML Tidy for HTML5 (experimental) for Windows https://github.com/w3c/tidy-html5/tree/c63cc39" />
<!--     <meta charset="utf-8" /> -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <meta name="description" content="" />
    <meta name="author" content="" />
    <!-- Bootstrap Core CSS -->
    <link href="<%=request.getContextPath()%>/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
    <link href="<%=request.getContextPath()%>/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
	<link href='https://fonts.googleapis.com/css?family=Roboto+Slab:400,100,300,700' rel='stylesheet' type='text/css' />
    <!-- Theme CSS -->
<%--     <link rel="stylesheet" href="<%=request.getContextPath()%>/js/owl.theme.default.min.js"> --%>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/magnific-popup.css">
    <!-- Theme Style -->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
    <link href="<%=request.getContextPath()%>/js/agency.js" rel="stylesheet" />
    <link href="<%=request.getContextPath()%>/css/agency.min.css" rel="stylesheet" />
	
	<!-- Custom Fonts -->
	<link href="<%=request.getContextPath()%>/vendor/font-awesome/css/font-awesome.min.css"
		rel="stylesheet" type="text/css" />
	<link href="https://fonts.googleapis.com/css?family=Montserrat:400,700"
		rel="stylesheet" type="text/css" />
	<link href='https://fonts.googleapis.com/css?family=Kaushan+Script'
		rel='stylesheet' type='text/css' />
	<link
		href='https://fonts.googleapis.com/css?family=Droid+Serif:400,700,400italic,700italic'
		rel='stylesheet' type='text/css' />
	<link
		href='https://fonts.googleapis.com/css?family=Roboto+Slab:400,100,300,700'
		rel='stylesheet' type='text/css' />
	<!-- Theme CSS -->
	<link href="<%=request.getContextPath()%>/css/gallery.css" rel="stylesheet" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/owl.carousel.min.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/owl.theme.default.min.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/magnific-popup.css">
	<!-- Theme Style -->
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
	<link href="<%=request.getContextPath()%>/css/agency.css" rel="stylesheet" />
	<link href="<%=request.getContextPath()%>/css/agency.min.css" rel="stylesheet" />
	<link href="<%=request.getContextPath()%>/css/daterangepicker.css" rel="stylesheet" />
	<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
	<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
	<!--[if lt IE 9]>
	        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js" integrity="sha384-0s5Pv64cNZJieYFkXYOTId2HMA2Lfb6q2nAcx2n0RTLUnCAoTTsS0nKEO27XyKcY" crossorigin="anonymous"></script>
	        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js" integrity="sha384-ZoaMbDF+4LeFxg6WdScQ9nnR1QC2MIRxA1O9KWEXQwns1G8UNyIEZIQidzb0T1fo" crossorigin="anonymous"></script>
	    <![endif]-->
	
	<link rel='stylesheet prefetch' href='https://fonts.googleapis.com/css?family=Open+Sans:600'>
	
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
        font-size: 28px;
        background-color: transparent;
        text-align: center;
        background-position: 10px 10px;
        line-height: 45px;
    }
    /*input[type=month]:focus {
    width: 100%;
}
*/
    .tab{
        margin-top:20px;
    }
    input[type=submit] {
        background-color: transparent;
        text-align: center;
        box-sizing: border-box;
        font-size: 25px;
/*         border-radius: 16px; */
        line-height: 20px;
        border: 2px solid #ccc;
    }
	
	 nav{
		background-image:url(<%=request.getContextPath()%>/img/header2.jpg);
		background-attachment: scroll;
		background-attachment:  fixed;
	} 
	
	#demo{
 		width:277px;
 	}
 	footer{
 		margin-top:100px;
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
	<!-- �|���n�J�O�c -->
	<div class="modal fade" id="modal-id">
		<div class="modal-dialog">
			<div >
			<!-- 	<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title">�n�J�|��</h4>
				</div> -->
				<div class="modal-body">
				   <button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					

										<div class="login-wrap">
        <div class="login-html" style="width:100% height:1000px">
            <input id="tab-1" type="radio" name="tab" class="sign-in" checked>
            <label for="tab-1" class="tab">�n�J</label>
            <input id="tab-2" type="radio" name="tab" class="sign-up">
            <label for="tab-2" class="tab">���U</label>
            <div class="login-form">
            <form method="post" action="<%=request.getContextPath()%>/backend/member/member.do">
                <div class="sign-in-htm">
                    <div class="group">
                        <label for="acc" class="label">�b��</label>
                        <input id="acc" name="acc" type="text" class="input">
                    </div>
                    <div class="group">
                        <label for="pwd" class="label">�K�X</label>
                        <input id="pwd" name="pwd" type="password" class="input" data-type="password">
                    </div>
                    <div class="group">
                        <input id="check" name="check" type="checkbox" class="check" checked>
                        <label for="check"><span class="icon"></span>�O���</label>
                    </div>
                    <div class="group">
                        <input type="submit" class="button" value="Sign In">
                        <input type="hidden" name="action" value="login">
                        <input type="hidden" name="location" value="<%=request.getServletPath()%>">
                    </div>
                    <div class="hr"></div>
                    <div class="foot-lnk">
                        <a href="#forgot">�ѰO�K�X?</a>
                    </div>
                   
                </div>
				</form>
                <form method="post" action="<%=request.getContextPath()%>/backend/member/member.do">
                <div class="sign-up-htm">
                    <div class="group">
                        <label for="new_acc" class="label">�b��</label>
                        <input name="new_acc" type="text" class="input">
                    </div>
                    <div class="group">
                        <label for="new_pwd" class="label">�K�X</label>
                        <input name="new_pwd" type="password" class="input" data-type="password">
                    </div>
                    <div class="group">
                        <label for="pass" class="label">�T�{�K�X</label>
                        <input name="pass" type="password" class="input" data-type="password">
                    </div>
                    <div class="group">
                        <label for="mail" class="label">�H�c</label>
                        <input id="mail" name="mail" type="email" class="input">
                    </div>
                    <div class="group">
                        <input type="submit" class="button" value="Sign Up">
                        <input type="hidden" name="action" value="register">
                        <input type="hidden" name="location" value="<%=request.getServletPath()%>">
                    </div>
                    <div class="hr"></div>
                    <div class="foot-lnk">
                        <label for="tab-1">�w�g�O�|��?</label>
                    </div>
                </div>
                </form>
            </div>
        </div>
    </div>

				</div>
			</div>
		</div>
		</div>
	<!-- Navigation -->
	<nav id="mainNav" class="navbar navbar-default navbar-custom navbar-fixed-top">
        <div class="container-fluid">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header page-scroll">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only">Toggle navigation</span> Menu <i class="fa fa-bars"></i>
                </button>
                   <a class="navbar-brand page-scroll" href="<%=request.getContextPath()%>/index.jsp">AutoBike</a>
            </div>

			<!-- Collect the nav links, forms, and other content for toggling -->
			 <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
               
             
<!-- search bar  -->					
						<form  method="post" action="<%=request.getContextPath()%>/backend/rent_ord/rentOrd.do" class="navbar-form navbar-left" role="search">
							<input id="demo" name="dayrange" class="form-control" type="text" style="background-color: transparent; color:#fff;" >							
						<%-- 	<jsp:useBean id="locSvc" scope="page" class="com.location.model.LocationService"/> --%>
							<input type="hidden" name="action" value="quick_search">
							<input style="background-color: transparent; color:#fff;" type="submit" class="form-control" value="�ֳt�d��">
						</form>

              					 <ul class="nav navbar-nav navbar-right">
              	                    <li class="hidden">
              	                        <a href="#page-top"></a>
              	                    </li>
              					<li><a class="page-scroll" href="<%=request.getContextPath()%>/frontend/rental_form/rental_category.jsp"><i class="glyphicon glyphicon-heart"></i>�ڭn����</a></li>
              						<li><a class="page-scroll" href="#news"><i
              								class="glyphicon glyphicon-alert"></i>�̷s����</a></li>
              						<li><a class="page-scroll" href="#board"><i
              								class="fa fa-comments-o"></i>�d���O</a></li>
              						<li><a class="page-scroll" href="#loc"><i
              								class="fa fa-search"></i>�A�Ⱦ��I</a></li>
              						<li><a href="<%=request.getContextPath()%>/frontend/second_order/listAllSecond.jsp"><i class="fa fa-shopping-cart"></i>�G�⨮�ʶR</a>
              						</li>
              						
              						<c:if test="${not empty memno}">	
              						<li><a href="#">�w��A${memname}</a></li>		
              							<li><a href="<%=request.getContextPath()%>/backend/member/member.do?action=getOne_For_Enter&memid=${memno}"><b>�|���M��</b></a></li>
              									<li>
              										<a href="<%=request.getContextPath()%>/backend/member/member.do?action=logout"
              								data-toggle="modal"><i class="glyphicon glyphicon-user"></i>�n�X</a>
              									</li>
              			
              						
              						</c:if>
              						
              						<c:if test="${ empty memno}">
              							<li>
              								<a href="#modal-id"
              								data-toggle="modal"><i class="glyphicon glyphicon-user"></i>�|���n�J</a>
              							</li>
              						</c:if>
              					</ul>
            
			</div>
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container-fluid -->
	</nav>
	
	
	
        <div class="container">
            <div class="row container-fluid">
            <br>
                <div class="col-xs-12 col-sm-12" >
                   <h1>�p�����I</h1>
                </div>
                
                   <!--  <audio hidden="true" controls autoplay loop="1">
                        <source src="�a�a- �a�a�s.mp3" type="audio/mpeg" />
                    </audio> -->
               
            </div>
        </div>
    
<jsp:useBean id="locationSvc" scope="page" class="com.location.model.LocationService" />

<div class="row center-text tab">
	

<div class="col-xs-12 col-sm-6">
	<div id="myMap" style="width:800px;height:800px;"></div>
</div>

<div class="col-xs-12 col-sm-6">
		<table id="location" class="table">
			<tr text-align="center">
				<th>���I�a�I</th>
				<th>���I�a�}</th>
				<th>���I�g��</th>
				<th>���I�n��</th>
				<th>���I�q��</th>
				<th>���I�Ӥ�</th>
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
        <div class="col-xs-12 col-sm-12"><span>�p����T</span></div>
        <div class="container-fluid">
            <div class="col-xs-12 col-sm-4"><span>�a�}:��饫����Ϥ�����115��</span></div>
            <div class="col-xs-12 col-sm-4"><span>EMAIL:taic@oregonstate.edu</span></div>
            <div class="col-xs-12 col-sm-4"><span>TEL:0900-000-000</span></div>
        </div>
    </footer>

	<!-- jQuery -->
	 <script src="<%=request.getContextPath()%>/vendor/jquery/jquery.min.js"></script>
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

<script>
var Lon,Lat;
var myMap;
var map;
var myPosition =[];
var i=0,j=0;
var count;

function indexclick(lon,lat){
		 count =1;
		 Lon = lon;
		 Lat = lat;
console.log("lon= "+Lon); 			
console.log("lat= "+Lat); 	
			myPosition[count] = new google.maps.LatLng(Lon,Lat);
console.log("myPosition["+count+"]= "+myPosition[count]);
			 map = new google.maps.Map(myMap,{
	            zoom: 10,
	            center: myPosition[count],
	            mapTypeId: google.maps.MapTypeId.ROADMAP
	    });
			 
		var markers = 'marker'+count;	 
		 markers = new google.maps.Marker({
				position: myPosition[count],
				map: map,
				animation: google.maps.Animation.DROP
			});
			
		 count++;
		}
		
var lons = "${lon}";
var lats = "${lat}";

// window.addEventListener('read',indexclick(lons,lats),false);
	
</script>

</html>