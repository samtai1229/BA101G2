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
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
<title>Title Page</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
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
<style>
		/**** BASE ****/
body {
    color: #888;   
}
a {
    color: #03a1d1;
    text-decoration: none!important;
    font-size:18px;
}

/**** LAYOUT ****/
.list-inline>li {
    padding: 0 10px 0 0;
}
.container-pad {
    padding: 30px 15px;
}


/**** MODULE ****/
.bgc-fff {
    background-color: #fff!important;
}
.box-shad {
    -webkit-box-shadow: 1px 1px 0 rgba(0,0,0,.2);
    box-shadow: 1px 1px 0 rgba(0,0,0,.2);
}
.brdr {
    border: 1px solid #ededed;
}

/* Font changes */
.fnt-smaller {
    font-size: .9em;
}
.fnt-lighter {
    color: #bbb;
}

/* Padding - Margins */
.pad-10 {
    padding: 10px!important;
}
.mrg-0 {
    margin: 0!important;
}
.btm-mrg-10 {
    margin-bottom: 10px!important;
}
.btm-mrg-20 {
    margin-bottom: 20px!important;
}

/* Color  */
.clr-535353 {
    color: #535353;
}


/**** TO top MODULE ****/
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
/****end TO top MODULE ****/


/**** 自已加的  ****/
li{
	margin-top:10px;
	font-size:16px;
}

.btn{
	position:relative;
	margin-top:30%;
}

.text_type{

color: #000;
}


body {
       background: url(/BA101G2/img/header1.jpg) no-repeat center center fixed; 
	    -webkit-background-size: cover;
	    -moz-background-size: cover;
	    -o-background-size: cover;
	    background-size: cover;
}

.topdiv{

	margin-top:80px;
	color:#eee;

}

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

/****end 自已加的 ****/
</style>
<%
	String dayrange = request.getParameter("dayrange");
	pageContext.setAttribute("dayrange", dayrange);
%>


<body onload="myFunction()" style="margin:0;">

<div id="loader"></div>

<div style="display:none;" id="myDiv" class="animate-bottom">




<!-- Navigation -->
	<nav id="mainNav" class="navbar navbar-default navbar-custom navbar-fixed-top">
		<div class="container-fluid">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header page-scroll">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Toggle navigation</span> Menu <i class="fa fa-bars"></i>
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

<%----------------------------------------------------VVVV building area VVVV-----------------------------------------------------------%>
	
	
	




<button onclick="topFunction()" id="myBtn" title="Go to top">Top</button>
	
		<div class="container container-pad" id="property-listings">
			<div class="row">
				<div class="col-md-12 topdiv">
					<h1>AutoBike - 租車 - 快速搜尋</h1>
					<h3>時間範圍 : <%=dayrange%></h3>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6">
				
				<jsp:useBean id="mmSvc" scope="page" class="com.motor_model.model.MotorModelService"/>
				
				<%int count =0; %>
				<c:forEach var="motorVO" items="${quick_search}">
				<%count++;%>
					<c:if test="<%=count%2==1%>">
					<!-- Begin Listing: 609 W GRAVERS LN-->
						<div class="brdr bgc-fff pad-10 box-shad btm-mrg-20 property-listing">
							<div class="media">
								<a class="pull-left" href="#" target="_parent">
								<img src="<%=request.getContextPath()%>/backend/motor_model/mmReader.do?modtype=${motorVO.modtype}"></a>
								<div class="clearfix visible-sm"></div>
								<div class="media-body fnt-smaller">
									<a href="#" target="_parent"></a>
									<h4 class="media-heading"><a href="#" target="_parent">${mmSvc.findByPK(motorVO.modtype).name}
									<small class="pull-right"></small></a></h4>
									<ul class="list-inline mrg-0 btm-mrg-10 clr-535353"><%-- reference: ${ecSvc.getOneEmtCate(emtVO.ecno).type} --%>
										<li>${mmSvc.findByPK(motorVO.modtype).brand}</li><br>
										<li>${mmSvc.findByPK(motorVO.modtype).displacement}c.c.</li>
										<c:if test="${mmSvc.findByPK(motorVO.modtype).displacement > 150}">
											<li><mark>重機專區</mark></li><br>
										</c:if>
										<li>日租價    NT$${mmSvc.findByPK(motorVO.modtype).renprice}</li>
										<li>車輛編號 : ${motorVO.motno}</li>
									</ul>
									<p class="hidden-xs">Situated between fairmount park and the prestigious philadelphia cricket club, this beautiful 2+ acre property is truly ...</p>
									<form method="post" action="<%=request.getContextPath()%>/backend/rent_ord/rentOrd.do">
										<input type="hidden" name="motno" value="${motorVO.motno}">		                      			
		                      			<input type="hidden" name="modtype" value="${mmVO.modtype}">
		                      			<input type="hidden" name="dayrange" value="${dayrange}">
		                      			<input type="hidden" name="action" value="quick_search_product">
		                      			<button type="submit" class="btn btn-primary btn-block btn-lg text_type">點我觀看</button>
			                    	</form>
								</div>
							</div>
						</div><!-- End Listing-->
					</c:if>
				</c:forEach>
					
					
				</div>
				<div class="col-sm-6">
				
				<%int count2 =0; %>
				<c:forEach var="motorVO" items="${quick_search}">
				<%count2++;%>
					<c:if test="<%=count2%2==0%>">
					<!-- Begin Listing: 609 W GRAVERS LN-->
						<div class="brdr bgc-fff pad-10 box-shad btm-mrg-20 property-listing">
							<div class="media">
								<a class="pull-left" href="#" target="_parent">
								<img src="<%=request.getContextPath()%>/backend/motor_model/mmReader.do?modtype=${motorVO.modtype}"></a>
								<div class="clearfix visible-sm"></div>
								<div class="media-body fnt-smaller">
									<a href="#" target="_parent"></a>
									<h4 class="media-heading"><a href="#" target="_parent">${mmSvc.findByPK(motorVO.modtype).name}
									<small class="pull-right"></small></a></h4>
									<ul class="list-inline mrg-0 btm-mrg-10 clr-535353"><%-- reference: ${ecSvc.getOneEmtCate(emtVO.ecno).type} --%>
										<li>${mmSvc.findByPK(motorVO.modtype).brand}</li><br>
										<li>${mmSvc.findByPK(motorVO.modtype).displacement}c.c.</li>
										<c:if test="${mmSvc.findByPK(motorVO.modtype).displacement > 150}">
											<li><mark>重機專區</mark></li><br>
										</c:if>
										<li>日租價    NT$${mmSvc.findByPK(motorVO.modtype).renprice}</li>
										<li>車輛編號 : ${motorVO.motno}</li>
									</ul>
									<p class="hidden-xs">Situated between fairmount park and the prestigious philadelphia cricket club, this beautiful 2+ acre property is truly ...</p>
									<form method="post" action="<%=request.getContextPath()%>/backend/rent_ord/rentOrd.do">
										<input type="hidden" name="motno" value="${motorVO.motno}">		                      			
		                      			<input type="hidden" name="modtype" value="${mmVO.modtype}">
		                      			<input type="hidden" name="dayrange" value="${dayrange}">
		                      			<input type="hidden" name="action" value="quick_search_product">
		                      			<button type="submit" class="btn btn-primary btn-block btn-lg text_type">點我觀看</button>
			                    	</form>
								</div>
							</div>
						</div><!-- End Listing-->
					</c:if>
				</c:forEach>
					
				</div><!-- End Col -->
			</div><!-- End row -->
		</div><!-- End container -->
	
	
	
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
	
	
	<script src="https://code.jquery.com/jquery.js"></script>
    <script type="text/javascript" src="https://code.jquery.com/jquery-1.11.3.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="https://raw.githack.com/JaapMoolenaar/bootstrap-daterangepicker/master/moment.js"></script>
    <script type="text/javascript" src="https://raw.githack.com/JaapMoolenaar/bootstrap-daterangepicker/master/daterangepicker.js"></script>

		<!-- basic -->

	<script src="<%=request.getContextPath()%>/js/owl.carousel.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/jquery.magnific-popup.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/agency.min.js"></script>
	

</body>
<script>

//loader
var myVar;

function myFunction() {
    myVar = setTimeout(showPage, 50);
}

function showPage() {
  document.getElementById("loader").style.display = "none";
  document.getElementById("myDiv").style.display = "block";
}



//When the user scrolls down 20px from the top of the document, show the button
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

</script>

</html>