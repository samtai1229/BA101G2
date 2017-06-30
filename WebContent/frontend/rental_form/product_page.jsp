<%@page import="java.sql.Timestamp"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.motor_model.model.*"%>
<!DOCTYPE html>
<html lang="">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
<title>Title Page</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">


</head>
<style type="text/css">

/*********************************************
        		Theme Elements
*********************************************/
.gold {
	color: #FFBF00;
}

/*********************************************
					PRODUCTS
*********************************************/
.product {
	border: 1px solid #dddddd;
	height: 321px;
}

.product>img {
	max-width: 300px;
}

.product-rating {
	font-size: 20px;
	margin-bottom: 25px;
}

.product-title {
	font-size: 20px;
}

.product-desc {
	font-size: 14px;
}

.product-price {
	font-size: 22px;
}

.product-stock {
	color: #74DF00;
	font-size: 20px;
	margin-top: 10px;
}

.product-info {
	margin-top: 50px;
}

/*********************************************
					VIEW
*********************************************/
.content-wrapper {
	max-width: 1140px;
	background: #fff;
	margin: 0 auto;
	margin-top: 25px;
	margin-bottom: 10px;
	border: 0px;
	border-radius: 0px;
}

.container-fluid {
	max-width: 1140px;
	margin: 0 auto;
}

.view-wrapper {
	float: right;
	max-width: 70%;
	margin-top: 25px;
}

.container {
	padding-left: 0px;
	padding-right: 0px;
	max-width: 100%;
}

/*********************************************
				ITEM 
*********************************************/
.service1-items {
	padding: 0px 0 0px 0;
	float: left;
	position: relative;
	overflow: hidden;
	max-width: 100%;
	height: 321px;
	width: 130px;
}

.service1-item {
	height: 107px;
	width: 120px;
	display: block;
	float: left;
	position: relative;
	padding-right: 20px;
	border-right: 1px solid #DDD;
	border-top: 1px solid #DDD;
	border-bottom: 1px solid #DDD;
}

.service1-item>img {
	max-height: 110px;
	max-width: 110px;
	opacity: 0.6;
	transition: all .2s ease-in;
	-o-transition: all .2s ease-in;
	-moz-transition: all .2s ease-in;
	-webkit-transition: all .2s ease-in;
}

.service1-item>img:hover {
	cursor: pointer;
	opacity: 1;
}

.service-image-left {
	padding-right: 50px;
}

.service-image-right {
	padding-left: 50px;
}

.service-image-left>center>img, .service-image-right>center>img {
	max-height: 155px;
}
</style>



<body>

<%
MotorModelVO mmQueryVO = (MotorModelVO)request.getAttribute("mmQueryVO");
%>

	<div class="container-fluid">
		<div class="content-wrapper">
			<div class="item-container">
				<div class="container">
					<div class="col-md-offset-1 col-md-5">
						<img id="item-display"
							src="<%=request.getContextPath()%>/backend/motor_model/mmReader.do?modtype=${mmQueryVO.modtype}"
							alt=""></img>
					</div>

					<div class="col-md-6">
						<div class="product-title">${mmQueryVO.brand} ${mmQueryVO.name}</div>
						<div class="product-desc">The Corsair Gaming Series GS600 is
							the ideal price/performance choice for mid-spec gaming PC</div>
						<hr>
						<div class="product-price">日租價:$NT ${mmQueryVO.renprice}</div>
						<div class="product-stock">可出租</div>
						<hr>
						<div class="btn-group cart">
							<button type="button" class="btn btn-success btn-md">我要租車</button>
						</div>
						<div class="btn-group wishlist">
							<button type="button" class="btn btn-danger btn-md">取消租車</button>
						</div>
					</div>
				</div>
			</div>
			<div class="container-fluid">
				<div class="col-md-12 product-info">
					<ul id="myTab" class="nav nav-tabs nav_tabs">
						<li class="active"><a href="#service-one" data-toggle="tab">DESCRIPTION</a></li>
						<li><a href="#service-two" data-toggle="tab">PRODUCT INFO</a></li>
						<li><a href="#service-three" data-toggle="tab">REVIEWS</a></li>
					</ul>
					<div id="myTabContent" class="tab-content">
						<div class="tab-pane fade in active" id="service-one">

							<section class="container product-info">
								The Corsair Gaming Series GS600 power supply is the ideal
								price-performance solution for building or upgrading a Gaming
								PC. A single +12V rail provides up to 48A of reliable,
								continuous power for multi-core gaming PCs with multiple
								graphics cards. The ultra-quiet, dual ball-bearing fan
								automatically adjusts its speed according to temperature, so it
								will never intrude on your music and games. Blue LEDs bathe the
								transparent fan blades in a cool glow. Not feeling blue? You can
								turn off the lighting with the press of a button.

								<h3>Corsair Gaming Series GS600 Features:</h3>
								<li>It supports the latest ATX12V v2.3 standard and is
									backward compatible with ATX12V 2.2 and ATX12V 2.01 systems</li>
								<li>An ultra-quiet 140mm double ball-bearing fan delivers
									great airflow at an very low noise level by varying fan speed
									in response to temperature</li>
								<li>80Plus certified to deliver 80% efficiency or higher at
									normal load conditions (20% to 100% load)</li>
								<li>0.99 Active Power Factor Correction provides clean and
									reliable power</li>
								<li>Universal AC input from 90~264V — no more hassle of
									flipping that tiny red switch to select the voltage input!</li>
								<li>Extra long fully-sleeved cables support full tower
									chassis</li>
								<li>A three year warranty and lifetime access to Corsair’s
									legendary technical support and customer service</li>
								<li>Over Current/Voltage/Power Protection, Under Voltage
									Protection and Short Circuit Protection provide complete
									component safety</li>
								<li>Dimensions: 150mm(W) x 86mm(H) x 160mm(L)</li>
								<li>MTBF: 100,000 hours</li>
								<li>Safety Approvals: UL, CUL, CE, CB, FCC Class B, TÜV,
									CCC, C-tick</li>
							</section>

						</div>
						<div class="tab-pane fade" id="service-two">

							<section class="container"></section>

						</div>
						<div class="tab-pane fade" id="service-three"></div>
					</div>
					<hr>
				</div>
			</div>
		</div>
	</div>
	<script src="https://code.jquery.com/jquery.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
</html>