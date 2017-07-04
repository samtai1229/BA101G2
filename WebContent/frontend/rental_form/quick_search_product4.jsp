<%@page import="java.sql.Timestamp"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.motor.model.*"%>
<!DOCTYPE html>
<html lang="">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
<title>Title Page</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" media="all" href="<%=request.getContextPath()%>/frontend/rental_form/Modified/other.css" />
<link rel="stylesheet" type="text/css" media="all" href="<%=request.getContextPath()%>/frontend/rental_form/Modified/daterangepicker.css" />


</head>
<style type="text/css">

.btn{
	margin-right:5px;
	margin-left:5px;
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
String dayPicker = (String)request.getAttribute("dayPicker");
String defday = (String)request.getAttribute("defday");
pageContext.setAttribute("start_time", defday);
pageContext.setAttribute("end_time", defday);
String memno = (String)session.getAttribute("memno");
pageContext.setAttribute("memno",memno);
%>
page4.jsp
memno:<c:out value="${memno}" default="no member login" /><br>
start_time:<c:out value="${start_time}" default="no value"/><br>
end_time:  <c:out value="${end_time}" default="no value"/><br>
dayPicker <c:out value="${dayPicker}" default="no value"></c:out>

<jsp:useBean id="mmSvc" scope="page" class="com.motor_model.model.MotorModelService"/>
	<div class="container-fluid">
		<div class="content-wrapper">
			<div class="item-container">
				<div class="container">
					<div class="col-md-offset-1 col-md-5">
						<img id="item-display"
							src="<%=request.getContextPath()%>/backend/motor_model/mmReader.do?modtype=${motorQueryVO.modtype}"
							alt=""></img>
					</div>
					<div class="col-md-6">
						<div class="product-title">
							<div>${mmSvc.findByPK(motorQueryVO.modtype).brand} ${mmSvc.findByPK(motorQueryVO.modtype).name}</div>
							<c:if test="${mmSvc.findByPK(motorQueryVO.modtype).displacement > 150}">
								<div><mark>重機專區</mark></div>
							</c:if>
								<div class="dynamic-text">${mmSvc.findByPK(motorQueryVO.modtype).displacement}c.c.</div>						
							</div>
						<div class="product-desc">The Corsair Gaming Series GS600 is
							the ideal price/performance choice for mid-spec gaming PC</div>
						<hr>
						
						<form METHOD="post" ACTION="<%=request.getContextPath()%>/backend/rent_ord/rentOrd.do">		
						<div class="product-price">日租價    NT$${mmSvc.findByPK(motorQueryVO.modtype).renprice}</div>
						<div class="dynamic-text">車輛編號 : ${motorQueryVO.motno}</div>
						<div class="product-stock">可出租</div>
						<hr>
						<div class="dynamic-text InputForm">租用日期:</div><li id="rentaled">紅色標記為當日出租中</li>
							
							<div class="input-group">
		                        <input type="text" id="invalidtodaynextweek" class="form-control" name="confirmed_rentday">
		                        <div class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></div>
		                    </div><br/>
	                    
	                    <jsp:useBean id="locSvc" scope="page" class="com.location.model.LocationService"/>
						
								
							<p class="text-center">
								<c:if test="<%=memno==null%>"> <!-- 轉到登入畫面????????????????????????????????????? -->
							    	<input type="hidden" name="action" value="redirect_to_login">
							    </c:if>
								<c:if test="<%=memno!=null%>">
									<input type="hidden" name="motno" value="${motorQueryVO.motno}">
							    	<input type="hidden" name="action" value="quick_search_product_2">
							    </c:if>	
							    						    
				   					<button type="submit" class="btn btn-success btn-lg">
										<i class="glyphicon glyphicon-ok"></i>我要訂車
									</button>
									
								<a href="<%=request.getContextPath()%>/index.jsp" class="btn btn-danger btn-lg">
								<i class="glyphicon glyphicon-remove"></i>返回首頁</a>
							</p>
						</form>
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
    <script type="text/javascript" src="https://code.jquery.com/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="http://netdna.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="https://raw.githack.com/JaapMoolenaar/bootstrap-daterangepicker/master/moment.js"></script>
    <script type="text/javascript" src="https://raw.githack.com/JaapMoolenaar/bootstrap-daterangepicker/master/daterangepicker.js"></script>


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