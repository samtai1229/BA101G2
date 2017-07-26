<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*"%>
<%@ page import="com.location.model.*"%>
<%@ page import="com.news.model.*"%>


<!--LocationService locationSvc = new LocationService();
 	List<LocationVO> list = locationSvc.getAll();
 	pageContext.setAttribute("list",list); -->
<%

LocationService locSvcc = new LocationService();
	List<LocationVO> list = locSvcc.getAllStatusOpen();
	int size = list.size();
	LocationVO[] locArray = new LocationVO[size];
	for(int i=0 ;i<size ;i++)
	{
		locArray[i]=list.get(i);
		System.out.println(locArray[i].getAddr());
	}
	//pageContext.setAttribute("locArray",locArray);
Float lon = (Float)request.getAttribute("lon");
Float lat = (Float)request.getAttribute("lat");
pageContext.setAttribute("lon", lon);
pageContext.setAttribute("lat", lat);
%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

	<link rel="stylesheet" href="<%=request.getContextPath()%>/backend/Modified/jquery_ui_1_10_3_theme.css"/>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/twitter_bootstrap_3_3_7_min.css">   
    <link rel="stylesheet" href="<%=request.getContextPath()%>/vendor/bootstrap/css/bootstrap.min.css" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/vendor/font-awesome/css/font-awesome.min.css" />
    
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/magnific-popup.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/frontend/rental_form/Modified/agency.min.css" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/frontend/rental_form/Modified/agency.css" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/gallery.css"/>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/owl.carousel.min.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/owl.theme.default.min.css">
	
<title>服務據點</title>

<style>

body {
       background: linear-gradient( rgba(0, 0, 0, 0.3), rgba(0, 0, 0, 0.3) ),url(/BA101G2/img/header1.jpg) no-repeat center center fixed; 
	    -webkit-background-size: cover;
	    -moz-background-size: cover;
	    -o-background-size: cover;
	    background-size: cover;
}

 	footer{
 		margin-top:100px;
 	}


	.divTag{
		margin-top:200px;
	}
	
	th, td{
		color:#fff;
		font-size:20px;
	}

	.navTextTag{ font-size:16px!important; }
</style>
</head>
<body id="page-top" class="index">


<%-- Navigation --%>
	<nav id="mainNav" class="navbar navbar-default navbar-custom navbar-fixed-top">
		<div class="container-fluid">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header page-scroll">
				<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Toggle navigation</span> Menu <i class="fa fa-bars"></i>
				</button>
				<a class="navbar-brand page-scroll" href="<%=request.getContextPath()%>/index.jsp">AutoBike&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;服務據點</a>
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
						<a class="page-scroll navTextTag" href="<%=request.getContextPath()%>/frontend/location/location.jsp">
						<i class="glyphicon glyphicon-map-marker"></i>
						服務據點</a>
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


<jsp:useBean id="locationSvc" scope="page" class="com.location.model.LocationService" />

	<div class="container-fluid">
		<div class="col-xs-12 col-sm-6 divTag">
			<div id="myMap" style="width:100%;height:750px;"></div>
		</div>
		<div class="col-xs-12 col-sm-6 divTag">
			<table id="location" class="table">
				<tr>
					<th>據點</th>
					<th>地址</th>
					<th>經度</th>
					<th>緯度</th>
					<th>電話</th>
					<th>照片</th>
				</tr>
				<c:forEach var="locationVO" items="${locationSvc.allStatusOpen}" >
					<tr> 
						<td>${locationVO.locname}</td>
						<td>${locationVO.addr}</td>
						<td>${locationVO.lon}</td>
						<td>${locationVO.lat}</td>
						<td>${locationVO.tel}</td>
						<td> 
						<img id="address" name="address" class="img-responsive img-circle"
						src="<%=request.getContextPath()%>/frontend/location/locReader.do?locno=${locationVO.locno}"
						width="100" height="100" onclick=changeLatLng('${locationVO.locname}','${locationVO.addr}','${locationVO.lon}','${locationVO.lat}','${locationVO.tel}','${locationVO.locno}') />
						</td>
					</tr>
				</c:forEach>
			</table>
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


<%-- <script src="<%=request.getContextPath()%>/frontend/location/js/googlemap.js"></script> --%>
	<script src="<%=request.getContextPath()%>/backend/Modified/jquery_1_10_1_min.js"></script>
	<script src="<%=request.getContextPath()%>/backend/Modified/jquery_ui_1_10_3.js"></script>
	<script src="<%=request.getContextPath()%>/backend/Modified/twitter_bootstrap_3_3_7_min.js"></script>
	<script src="<%=request.getContextPath()%>/js/owl.carousel.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/main.js"></script>
	<script src="<%=request.getContextPath()%>/js/jqBootstrapValidation.js"></script>
	<script src="<%=request.getContextPath()%>/js/agency.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/location_google_map_api.js"></script>


<script>
var Lon,Lat;
var myMap;
var map;
var myPosition =[];
var i=0,j=0;
var count;

// var secretMessages = ['This', 'is', 'the', 'secret', 'message','QUITE'];

var secretMessages = ['123123123'+
'<h2><%=locArray[0].getLocname()%></h2><br>'+
'<%=locArray[0].getAddr()%><br>'+
'<%=locArray[0].getLon()%><br>'+
'<%=locArray[0].getLat()%><br>'+
'<%=locArray[0].getTel()%><br>'+
'<img class="infoImg" src="<%=request.getContextPath()%>/frontend/location/locReader.do?locno=<%=locArray[0].getLocno()%>"><br>'
,'123123123'+
'<h2><%=locArray[1].getLocname()%></h2><br>'+
'<%=locArray[1].getAddr()%><br>'+
'<%=locArray[1].getLon()%><br>'+
'<%=locArray[1].getLat()%><br>'+
'<%=locArray[1].getTel()%><br>'+
	'<img class="infoImg" src="<%=request.getContextPath()%>/frontend/location/locReader.do?locno=<%=locArray[1].getLocno()%>"><br>'
,'123123123'+
'<h2><%=locArray[2].getLocname()%></h2><br>'+
'<%=locArray[2].getAddr()%><br>'+
'<%=locArray[2].getLon()%><br>'+
'<%=locArray[2].getLat()%><br>'+
'<%=locArray[2].getTel()%><br>'+
'<img class="infoImg" src="<%=request.getContextPath()%>/frontend/location/locReader.do?locno=<%=locArray[2].getLocno()%>"><br>'
,'123123123'+
'<h2><%=locArray[3].getLocname()%></h2><br>'+
'<%=locArray[3].getAddr()%><br>'+
'<%=locArray[3].getLon()%><br>'+
'<%=locArray[3].getLat()%><br>'+
'<%=locArray[3].getTel()%><br>'+
'<img class="infoImg" src="<%=request.getContextPath()%>/frontend/location/locReader.do?locno=<%=locArray[3].getLocno()%>"><br>'
,'123123123'+
'<h2><%=locArray[4].getLocname()%></h2><br>'+
'<%=locArray[4].getAddr()%><br>'+
'<%=locArray[4].getLon()%><br>'+
'<%=locArray[4].getLat()%><br>'+
'<%=locArray[4].getTel()%><br>'+
'<img class="infoImg" src="<%=request.getContextPath()%>/frontend/location/locReader.do?locno=<%=locArray[4].getLocno()%>"><br>'
,'123123123'+
'<h2><%=locArray[5].getLocname()%></h2><br>'+
'<%=locArray[5].getAddr()%><br>'+
'<%=locArray[5].getLon()%><br>'+
'<%=locArray[5].getLat()%><br>'+
'<%=locArray[5].getTel()%><br>'+
'<img class="infoImg" src="<%=request.getContextPath()%>/frontend/location/locReader.do?locno=<%=locArray[5].getLocno()%>"><br>'
];	
	
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
		
	 	function doFirst(){
	         myMap = document.getElementById('myMap');
	         Lon = 24.01;
	         Lat = 121.1;
	         myPosition = new google.maps.LatLng(Lon,Lat);
	         var a=2;
	         var b=3;
	         var c=6;
	         var lon = new Array(1);
	         var lat = new Array(1);
	         map = new google.maps.Map(myMap,{
		    	 zoom: 8,
		    	 center:  myPosition,
		    	 mapTypeId: google.maps.MapTypeId.ROADMAP
		     });
	         //建立地圖 marker 的集合
		     var marker_config = new Array();
		     var markers = [];
	         while(($("tr td:eq("+(a+i*c)+")").text()) != ""){
	        	 
	        	 
	console.log( "lons["+i+"]= "+$("tr td:eq("+(a+i*c)+")").text()  );
	console.log( "lats["+j+"]= "+$("tr td:eq("+(b+j*c)+")").text()  );

			     lon[i] = $("tr td:eq("+(a+i*c)+")").text();
			     lat[j] = $("tr td:eq("+(b+j*c)+")").text();
			     myPosition=new google.maps.LatLng(lon[i],lat[j]);
			     console.log("myPosition="+myPosition);
			     //建立地圖 marker 的集合
			     marker_config[i] = {
			       position: myPosition,
			       map: map
			     };
			     
		         i++;
		         j++;
	         }
	         
	         
	         
	         //標出 marker
		       marker_config.forEach(function(e,k){
			       markers[k] = new google.maps.Marker(e);
			       markers[k].setMap(map);
			       console.log("markers["+k+"]= "+markers[k]);
			       attachSecretMessage(markers[k], secretMessages[k]);//每筆標記地點訊息
		       }); 
		       
	 	
	 	
	 	//點擊顯示據點資料
//	 				markers[k].addListener('click',function(){
	//console.log("xxxxxconsolelogxxxxxxxxxxxxxx");
//	 					$.ajax({
//	 						type:"GET",
//	 						url:"location.do",
//	 						data:{action:"getJson",Lon:"Lon",Lat:"Lat"},
//	 						dataType:"json",
//	 						success:function (data){
//	 							drawTable(data);
//	 						},
//	 						error:function(){alert("error")}
//	 					})
//	 				});
//		     }); 
	 	}	
	 	
	 	
	 	
	 	//標記地點訊息
	 	var infowindowopen = false; 
	 	
	 	function attachSecretMessage(marker, secretMessage) {
	        var infowindow = new google.maps.InfoWindow({
	          content: secretMessage
	        });

	        marker.addListener('click', function() {
	        	if(infowindowopen){
	        		infowindowopen.close();
	        	}
	        	infowindowopen = infowindow;
	        	infowindow.open(marker.get('map'), marker);
	        });
	      }
	 	
	 	
	 	function changeLatLng(name,addr,lon,lat,tel,no){
	 		infowindowopen = false;
	 		 count =1;
	 		 Name = name;
	 		 Addr = addr;
	 		 Lon = lon;
	 		 Lat = lat;
	 		 Tel = tel;
	 		 No = no;
	 	console.log("Name= "+name); 			
	 	console.log("Addr= "+addr); 			
	 	console.log("lon= "+Lon); 			
	 	console.log("lat= "+Lat); 	
	 	console.log("No= "+No);
	 			myPosition[count] = new google.maps.LatLng(Lon,Lat);
	 	console.log("myPosition["+count+"]= "+myPosition[count]);
	 			 map = new google.maps.Map(myMap,{
	 	            zoom: 10,
	 	            center: myPosition[count],
	 	            mapTypeId: google.maps.MapTypeId.ROADMAP
	 	    });
	 			 
	 		 marker = 'marker'+count;	 
	 		 marker = new google.maps.Marker({
	 				position: myPosition[count],
	 				map: map,
	 				animation: google.maps.Animation.DROP
	 			});
	 		 secretMessage = '<h2>'+Name+'</h2><br>'+
	 		 						Addr+'<br>'+
	 		 						Lon+'<br>'+
	 		 						Lat+'<br>'+
	 		 						Tel+'<br>'+
	 		 						'<img class="infoImg" src="<%=request.getContextPath()%>/frontend/location/locReader.do?locno='+No+'"> ';
	 		 attachSecretMessage(marker, secretMessage);
	 		 count++;
	 		}

//   window.addEventListener('read',indexclick(lons,lats),false);
   window.addEventListener('load',doFirst,false);
	
</script>
    
</body>    

</html>