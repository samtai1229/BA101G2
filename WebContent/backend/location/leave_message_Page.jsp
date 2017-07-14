<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.location.model.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<%
	LocationService locationSvc = new LocationService();
	List<LocationVO> list = locationSvc.getAll();
	pageContext.setAttribute("list",list);
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
<title>Insert title here</title>
</head>
<body>

<script src="http://maps.google.com/maps/api/js"></script>
   <div class="col-xs-12 col-sm-6 map">
   
		
           <script type="text/javascript">
           	function doFirst(){
            	var myMap = document.getElementById('myMap');
            	var myPosition = new google.maps.LatLng(24.9708264,121.18820769999999);
        		
        
            	var map = new google.maps.Map(myMap,{
            	    zoom: 14,
            	    center: myPosition,
            	    mapTypeId: google.maps.MapTypeId.ROADMAP
            	});
                    
                var marker = new google.maps.Marker({
	                position: myPosition,
	                map: map,
	                icon: '../../images/number/dgtp.gif',
	                title: '這不是中央大學'
                });
            }
    		window.addEventListener('load',doFirst,false);
			</script>
			
			<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyB-fsKJXrclxXV39v3nt_zjC8RaS454leM&callback=initMap"
    async defer></script>
		
		<div class="col-xs-12 col-sm-6">
<table border='1' bordercolor='#CCCCFF' width='1500'>

	<!-- <tr>
		<td><div id="myMap" style="width:800px;height:600px;"></div></td>
	</tr> -->
	<tr>
		<th>據點編號</th>
		<th>據點地點</th>
		<th>據點電話</th>
		<th>據點地址</th>
		<th>據點相片</th>
		<th>據點經度</th>
		<th>據點緯度</th>
		<th>據點狀態</th>
	</tr>
	<c:forEach var="locationVO" items="${list}">
		<tr align='center' valign='middle' ${(locationVO.locno==param.locno) ? 'bgcolor=#CCCCFF':''}><!--將修改的那一筆加入對比色而已-->
			<td>${locationVO.locno}</td>
			<td>${locationVO.locname}</td>
			<td>${locationVO.tel}</td>
			<td>${locationVO.addr}</td>
			<td><img src="<%=request.getContextPath()%>/PhotoReader1.do?locno=${locationVO.locno}" height="200" width="200"></td>
			<td>${locationVO.lon}</td>
			<td>${locationVO.lat}</td>
			<td>${locationVO.status}</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/location/location.do">
			     <input type="submit" value="修改"> 
			     <input type="hidden" name="locno" value="${locationVO.locno}">
			     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
			    <input type="hidden" name="whichPage"	>               <!--送出當前是第幾頁給Controller-->
			     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
			</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/location/location.do">
			    <input type="submit" value="刪除">
			    <input type="hidden" name="locno" value="${locationVO.locno}">
			    <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
			    <input type="hidden" name="whichPage"	>               <!--送出當前是第幾頁給Controller-->
			    <input type="hidden" name="action"value="delete"></FORM>
			</td>
		</tr>
	</c:forEach>
    </div>
                
</div>

</body>
</html>