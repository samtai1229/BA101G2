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

<script src="<%=request.getContextPath()%>/frontend/location/js/googlemap.js"></script>

<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.11/jquery-ui.min.js"></script>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css"/>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
<title>Insert title here</title>
</head>

<style type="text/css">
.location table,th,td{
	border: 1px solid black;
	border-collapse: collapse;
	padding:15px;
	text-align:center;
}
</style>

<body>

<jsp:useBean id="locationSvc" scope="page" class="com.location.model.LocationService" />

<div class="row">
	

<div class="col-xs-12 col-sm-6">
	<div id="myMap" style="width:800px;height:800px;"></div>
</div>

<div class="col-xs-12 col-sm-6">
		<table id="location" >
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

	<!-- jQuery -->
	<!--  <script src="vendor/jquery/jquery.min.js"></script> -->
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