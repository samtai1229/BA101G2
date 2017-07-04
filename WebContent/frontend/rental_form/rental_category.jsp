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

	<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
	<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>  	
	<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.11/jquery-ui.min.js"></script>
	<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css"/>	
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">     
	<link rel="stylesheet" href="<%=request.getContextPath()%>/backend/Modified/backendHP_css.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/backend/Modified/main.css" >

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

</style>

<body>

<button onclick="topFunction()" id="myBtn" title="Go to top">Top</button>

	<div class="container">
		<div class="row">
			<div class="gallery col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<h1 class="gallery-title">AutoBike - 租車</h1>
			</div>

			<div align="center">
				<button class="btn btn-default filter-button" data-filter="all">全部車種</button>
				<button class="btn btn-default filter-button" data-filter="heavy">重機專區</button>
				<button class="btn btn-default filter-button" data-filter="light">輕旅租車</button>
 				<button class="btn btn-default filter-button" data-filter="new" disabled>新車上架</button>
			</div>
			<br />

<jsp:useBean id="mmSvc" scope="page" class="com.motor_model.model.MotorModelService"/>


	 		<c:forEach var="mmVO" items="${mmSvc.all}">
<%-- 	 			<c:set scope="page" var="displacementStr">
					<c:out value="${mmVO.displacement}"/>
				</c:set>
				<%
					int displacement = Integer.parseInt(String.valueOf(pageContext.getAttribute("displacementStr")));
				%>
 --%>
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

    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script src="<%=request.getContextPath()%>/backend/motor_model/Modified/paging_for_mm.js"></script>
    <script src="<%=request.getContextPath()%>/backend/motor_model/Modified/motorModelNew.js"></script>   
    <script src="<%=request.getContextPath()%>/backend/motor_model/Modified/motorKanli_for_mm.js"></script>
    <script src="<%=request.getContextPath()%>/backend/motor_model/Modified/upload.js"></script> 
</body>
</html>