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
/****end 自已加的 ****/
</style>
<%
String dayrange = request.getParameter("dayrange");
pageContext.setAttribute("dayrange", dayrange);

%>


<body>
<button onclick="topFunction()" id="myBtn" title="Go to top">Top</button>
	<div class="container-fluid" style="background-color:#e8e8e8">
		<div class="container container-pad" id="property-listings">
			<div class="row">
				<div class="col-md-12">
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
		                      			<button type="submit" class="btn btn-primary btn-block btn-lg">點我觀看</button>
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
		                      			<button type="submit" class="btn btn-primary btn-block btn-lg">點我觀看</button>
			                    	</form>
								</div>
							</div>
						</div><!-- End Listing-->
					</c:if>
				</c:forEach>
					
				</div><!-- End Col -->
			</div><!-- End row -->
		</div><!-- End container -->
	</div>
	<script src="https://code.jquery.com/jquery.js"></script> 
	<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
<script>
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