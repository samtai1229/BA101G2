<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.motor.model.*"%>
<%@ page import="com.motor_model.model.*"%>
<%@ page import="java.util.*"%>
<%
 MotorService moSvc = new MotorService();
 List<MotorVO> list = moSvc.getAll();
 MotorModelService mmSvc = new MotorModelService();
 List<MotorModelVO> mlist = mmSvc.getAll();
 pageContext.setAttribute("list", list);
 pageContext.setAttribute("mlist", mlist);
%>
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/backend/Modified/jquery_ui_1_10_3_theme.css"/>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/twitter_bootstrap_3_3_7_min.css">   
    <link rel="stylesheet" href="<%=request.getContextPath()%>/vendor/bootstrap/css/bootstrap.min.css" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/vendor/font-awesome/css/font-awesome.min.css" />
    <link rel="Short Icon" href="<%=request.getContextPath()%>/img/smallIcon.ico">  
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/magnific-popup.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/frontend/rental_form/Modified/agency.min.css" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/frontend/rental_form/Modified/agency.css" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/gallery.css"/>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/owl.carousel.min.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/owl.theme.default.min.css">
<style>

.activity-card-title{
margin:0 0 10px;
max-height:348px;
font-size:18px;
font-weight:700;
line-height:24px;
overflow:hidden;
text-align: center;
}
form {
    margin: 15px 0 10px;
}
body {
       background: linear-gradient( rgba(0, 0, 0, 0.3), rgba(0, 0, 0, 0.3) ),url(/BA101G2/img/header1.jpg) no-repeat center center fixed; 
	    -webkit-background-size: cover;
	    -moz-background-size: cover;
	    -o-background-size: cover;
	    background-size: cover;
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
<title>二手車交易</title>

</head>
<body>


<%-- Navigation --%>
	<nav id="mainNav" class="navbar navbar-default navbar-custom navbar-fixed-top">
		<div class="container-fluid">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header page-scroll">
				<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Toggle navigation</span> Menu <i class="fa fa-bars"></i>
				</button>
				<a class="navbar-brand page-scroll" href="<%=request.getContextPath()%>/index.jsp">AutoBike&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;二手車交易</a>
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


 <div class="container divTag">
  <div class="row">
   <c:forEach var="motorVO" items="${list}">
    <c:if test="${ motorVO.status=='seconsale'}">
    <div class="col-xs-12 col-sm-3" >
    <div class="activity-card-title">
     <img width='200' height='180' src="<%=request.getContextPath()%>/backend/motor_model/mmReader.do?modtype=${motorVO.modtype}" >
     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/second_order/SecOrd.do">
      <input class="btn btn-success" type="submit" value="我有興趣">
      <input type="hidden" name="motno" value="${motorVO.motno}">
      <input type="hidden" name="location" value="<%=request.getServletPath()%>">
      <!--送出本網頁的路徑給Controller-->
      <!-- 目前尚未用到  -->
      <input type="hidden" name="action" value="I_WANT_IT">
     </FORM>
     </div>
     </div>
    </c:if>
   </c:forEach>
  </div>
 </div>

	<script src="<%=request.getContextPath()%>/backend/Modified/jquery_1_10_1_min.js"></script>
	<script src="<%=request.getContextPath()%>/backend/Modified/jquery_ui_1_10_3.js"></script>
	<script src="<%=request.getContextPath()%>/backend/Modified/twitter_bootstrap_3_3_7_min.js"></script>
	<script src="<%=request.getContextPath()%>/js/agency.min.js"></script>
</body>
</html>