<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.member.model.*"%>

<%
	MemberVO memVO = (MemberVO) request.getAttribute("memVO");
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
	<title>MemberCenter</title>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/backend/Modified/twitter_bootstrap_3_3_7_min.css">  
</head>
 <style>
   	@media screen and (max-width:767px){
   		.navbar{
   			z-index: 10;
   		}
   		.my-navbar{
   			position: fixed;
   			background-color: #ccc;
   			overflow: auto;
   			width: 70%;
   			top: 50px;
   			z-index: 9;
   			height: calc(100vh - 50px);
   			transition: 1s;
   		}
   		.my-navbar .navbar-nav{
   			margin: 0;
   		}
   		.leftIn{
   			left: -100%;
   		}
   		.rightIn{
   			right: -100%;
   		}
   		.my-navbar.leftIn.open{
   			left: 0;
   		}
   		.my-navbar.rightIn.open{
   			right: 0;
   		}
   	}
</style>
<body>
     <nav class="navbar navbar-default" role="navigation">
     		<div class="container">
     			<div class="navbar-header">
     				<button type="button" class="navbar-toggle">
     					<span class="sr-only">選單切換</span>
     					<span class="icon-bar"></span>
     					<span class="icon-bar"></span>
     					<span class="icon-bar"></span>
     				</button>
     				<a class="navbar-brand" href="#">CSS可樂</a>
     			</div>
     			
     			<!-- 手機隱藏選單區 -->
     			<div class="my-navbar leftIn">
     				<!-- 左選單 -->
     				<ul class="nav navbar-nav">
     					<li class="active"><a href="#">關於CSS可樂</a></li>
     					<li><a href="#">CSS教學</a></li>
     					<li><a href="#">CSS範例</a></li>
     					<li><a href="#">原創CSS</a></li>
     				</ul>
     			
     				<!-- 搜尋表單 -->
     				<form class="navbar-form navbar-left" role="search">
     					<div class="form-group">
     						<input type="text" class="form-control" placeholder="請輸入關鍵字">
     					</div>
     					<button type="submit" class="btn btn-default">搜尋</button>
     				</form>
     			
     				<!-- 右選單 -->
     				<ul class="nav navbar-nav navbar-right">
     					<li><a href="#">${memVO.memname} 您好</a></li>
     					<li><a href="#">登出</a></li>
     					<li><a href="#">資料修改</a></li>
     					<!-- <li class="dropdown">
     						<a href="#" class="dropdown-toggle" data-toggle="dropdown">繁體中文 <b class="caret"></b></a>
     						<ul class="dropdown-menu">
     							<li><a href="#">繁體中文</a></li>
     							<li><a href="#">English</a></li>
     							<li><a href="#">日本語</a></li>
     						</ul>
     					</li> -->
     				</ul>
     			</div>
     			<!-- 手機隱藏選單區結束 -->
     		</div>
     	</nav>
     	
	
<script src="<%=request.getContextPath()%>/backend/Modified/jquery_1_10_1_min.js"></script>
<script src="<%=request.getContextPath()%>/backend/Modified/twitter_bootstrap_3_3_7_min.js"></script>
<script type="text/javascript">
	     	     
   	$(function(){
   		$('.navbar-toggle').on('click', switchNav)
   		function switchNav(){
   			$('.my-navbar').toggleClass('open');
   		}
   	})
</script>
	     		
</body>
</html>