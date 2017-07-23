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

 	<link rel="stylesheet" href="<%=request.getContextPath()%>/backend/Modified/jquery_ui_1_10_3_theme.css"/>	
	<link rel="stylesheet" href="<%=request.getContextPath()%>/backend/Modified/twitter_bootstrap_3_3_7_min.css">        
	<link rel="stylesheet" href="<%=request.getContextPath()%>/backend/Modified/backendHP_css.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/backend/Modified/main.css" >
	<link rel="stylesheet" href="<%=request.getContextPath()%>/vendor/bootstrap/css/bootstrap.min.css" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/vendor/font-awesome/css/font-awesome.min.css" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/google_family_kaushan_script.css" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/frontend/rental_form/Modified/agency.min.css" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/frontend/rental_form/Modified/agency.css" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/frontend/rental_form/Modified/other.css" />
	
</head>
<style type="text/css">
.gallery-title {
	font-size: 36px;
	color: #42B32F;
	text-align: center;
	font-weight: 500;
	margin-bottom: 70px;
}

.gallery-title:after {
	content: "";
	position: absolute;
	width: 7.5%;
	left: 46.5%;
	height: 45px;
	border-bottom: 1px solid #5e5e5e;
}

.filter-button {
	font-size: 18px;
	border: 1px solid #42B32F;
	border-radius: 5px;
	text-align: center;
	color: #42B32F;
	margin-bottom: 30px;
}

.filter-button:hover {
	font-size: 18px;
	border: 1px solid #42B32F;
	border-radius: 5px;
	text-align: center;
	color: #ffffff;
	background-color: #42B32F;
}

.btn-default:active .filter-button:active {
	background-color: #42B32F;
	color: white;
}

.port-image {
	width: 100%;
}

.gallery_product {
	margin-bottom: 30px;
}

#myBtn {
  display: none;
  position: fixed;
  bottom: 20px;
  right: 30px;
  z-index: 99;
  border: none;
  outline: none;
  background-color: red;
  color: white;
  cursor: pointer;
  padding: 15px;
  border-radius: 10px;
}

#myBtn:hover {
  background-color: #555;
}

.topdiv{

	margin-top:100px;
	color:#eee;

}

body {
       background: linear-gradient( rgba(0, 0, 0, 0.3), rgba(0, 0, 0, 0.3) ),url(/BA101G2/img/header1.jpg) no-repeat center center fixed; 
	    -webkit-background-size: cover;
	    -moz-background-size: cover;
	    -o-background-size: cover;
	    background-size: cover;
}


.navTextTag{
	font-size:16px!important;
}



#main:after {
    content : "";
    display: block;
    position: absolute;
    top: 0;
    left: 0;
    background-image: url(/BA101G2/img/header1.jpg); 
    width: 100%;
    height: 100%;
    opacity : 0.2;
    z-index: -1;
}

</style>

<body>

<%-- Navigation --%>
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
				 <ul class="nav navbar-nav navbar-right">
					<li class="hidden"><a href="#page-top"></a></li>
					<li><a class="page-scroll navTextTag" href="<%=request.getContextPath()%>/index.jsp">
						<i class="glyphicon glyphicon-home"></i>
						回首頁</a>
					</li>
					<li>
						<a class="page-scroll navTextTag" href="<%=request.getContextPath()%>/frontend/rental_form/rental_category.jsp">
						<i class="glyphicon glyphicon-heart"></i>
						我要租車</a>
					</li>
					<li>
						<a class="navTextTag" href="<%=request.getContextPath()%>/frontend/second_order/listAllSecond.jsp">
						<i class="fa fa-shopping-cart"></i>
						二手車購買</a>
					</li>
					<c:if test="${not empty memno}">	
						<li>
							<a class="navTextTag" href="<%=request.getContextPath()%>/backend/member/member.do?action=getOne_For_Enter&memid=${memno}">
							會員專區</a>
						</li>
						<li><a class="navTextTag" href="#" class="disabled">歡迎，${(memname == null) ? '會員':memname}</a></li>
						<li>
							<a class="navTextTag" href="<%=request.getContextPath()%>/backend/member/member.do?action=logout" data-toggle="modal"><i class="glyphicon glyphicon-user"></i>
							登出</a>
						</li>
					</c:if>
				</ul>
			</div>
		</div>
	</nav>
<%--end Navigation --%>
	
	<div id="blocker"></div>


	<!-- 租車主軸Header -->

<%----------------------------------------------------VVVV building area VVVV-----------------------------------------------------------%>

<button onclick="topFunction()" id="myBtn" title="Go to top">Top</button>

	<div class="container">
		<div class="row">
<!-- 			<div class="gallery col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<h1 class="gallery-title topdiv">AutoBike - 租車</h1>
			</div> -->

			<div align="center" class="topdiv">
				<button class="btn btn-default filter-button" data-filter="all">全部車種</button>
				<button class="btn btn-default filter-button" data-filter="light">輕旅租車</button>
				<button class="btn btn-default filter-button" data-filter="heavy">重機專區</button>
			</div>
			<br />

<jsp:useBean id="mmSvc" scope="page" class="com.motor_model.model.MotorModelService"/>


	 		<c:forEach var="mmVO" items="${mmSvc.all}">
	  			<c:if test="${mmVO.displacement <=150}">
					<div class="thumbnail col-lg-4 col-md-4 col-sm-6 col-xs-12  filter light">
	                    <img src="<%=request.getContextPath()%>/backend/motor_model/mmReader.do?modtype=${mmVO.modtype}"
	                     alt="ALT NAME" class="img-responsive" />
	                    <div class="caption">
	                    	<h3>${mmVO.brand} ${mmVO.name}</h3>
	                        <p>${mmVO.displacement}cc</p>
	                        <p align="center"></p>
	                      		<form method="post" action="<%=request.getContextPath()%>/backend/rent_ord/rentOrd.do">
	                      			<input type="hidden" name="modtype" value="${mmVO.modtype}">
	                      			<input type="hidden" name="action" value="query_product_info">
	                      			<button type="submit" class="btn btn-primary btn-block btn-lg">點我觀看</button>
		                    	</form>
	                        
	                    </div>
	                </div>					
				</c:if>
				
 				<c:if test="${mmVO.displacement > 150}">
					<div class="thumbnail col-lg-4 col-md-4 col-sm-6 col-xs-12  filter heavy">
	                    <img src="<%=request.getContextPath()%>/backend/motor_model/mmReader.do?modtype=${mmVO.modtype}"
	                     alt="ALT NAME" class="img-responsive" />
	                    <div class="caption">
	                    	<h3>${mmVO.brand} ${mmVO.name}</h3>
	                        <p><mark>${mmVO.displacement}cc</mark></p>
	                        <p align="center"></p>
	                      		<form method="post" action="<%=request.getContextPath()%>/backend/rent_ord/rentOrd.do">
	                      			<input type="hidden" name="modtype" value="${mmVO.modtype}">
	                      			<input type="hidden" name="action" value="query_product_info">
	                      			<button type="submit" class="btn btn-primary btn-block btn-lg">點我觀看</button>
		                    	</form>
	                        
	                    </div>
	                </div>
				</c:if>
			</c:forEach>
		</div>
	</div>
	
	
	
		<%----------------------------------------------------^^^^ building area ^^^^-----------------------------------------------------------%>		

	<footer>
		<div class="container-fluid topdiv">
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
	<script src="<%=request.getContextPath()%>/backend/Modified/jquery_1_10_1_min.js"></script>
	<script src="<%=request.getContextPath()%>/backend/Modified/twitter_bootstrap_3_3_7_min.js"></script>
	<script src="<%=request.getContextPath()%>/js/agency.min.js"></script>
	<script type="text/javascript">
	
//動態過濾功能		

	$(document).ready(function() {


		$(".filter-button").click(function() {
			var value = $(this).attr('data-filter');

			if (value == "all") {
				//$('.filter').removeClass('hidden');
				$('.filter').show('1000');
			} else {
				//            $('.filter[filter-item="'+value+'"]').removeClass('hidden');
				//            $(".filter").not('.filter[filter-item="'+value+'"]').addClass('hidden');
				$(".filter").not('.' + value).hide('3000');
				$('.filter').filter('.' + value).show('3000');


			}
		});

		if ($(".filter-button").removeClass("active")) {
			$(this).removeClass("active");
		}
		$(this).addClass("active");

	});
		
//end 動態過濾功能		
		
//回到上頁功能		
		// When the user scrolls down 20px from the top of the document, show the button
		window.onscroll = function() {scrollFunction()};

		function scrollFunction() {
		    if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
		        document.getElementById("myBtn").style.display = "block";
		    } else {
		        document.getElementById("myBtn").style.display = "none";
		    }
		}

		// When the user clicks on the button, scroll to the top of the document
		function topFunction() {
		    document.body.scrollTop = 0;
		    document.documentElement.scrollTop = 0;
		}
//end 回到上頁功能		
	</script>
</body>
</html>