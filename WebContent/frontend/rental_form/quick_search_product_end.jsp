<%@page import="java.sql.Timestamp"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.rent_ord.model.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
<title>Title Page</title>

<link rel="stylesheet" href="<%=request.getContextPath()%>/vendor/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/frontend/rental_form/Modified/other.css" media="all" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/frontend/rental_form/Modified/daterangepicker.css" media="all" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/vendor/font-awesome/css/font-awesome.min.css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/google_family_kaushan_script.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/frontend/rental_form/Modified/agency.min.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/frontend/rental_form/Modified/agency.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/frontend/rental_form/Modified/other.css" />

</head>
<style type="text/css">
select {
    min-width:120px;
    min-height: 45px;
    border-width: 3px;
    border-color: rgba(50, 50, 50, 0.14);
    margin: 10px 10px 10px 0px;
}

.media{
	font-size:16px;
	  height: 60px;
  line-height: 60px;
}

input[type=checkbox]
{
  /* Double-sized Checkboxes */
  -ms-transform: scale(1); /* IE */
  -moz-transform: scale(1); /* FF */
  -webkit-transform: scale(1); /* Safari and Chrome */
  -o-transform: scale(1); /* Opera */
      margin-left:20px;
}
.media-object{
	margin-left:20px;
}

.title{
	margin-top:20px;
	margin-bottom:20px;
}

.btn{
	margin-right:5px;
	margin-left:5px;
}

.t1{

	margin-top:100px;

}
.t2{
	margin-top:100px;
}


.navtext{
	font-size:18px;
}

.t1{
	font-size:24px;
}

#rent{
	background-color:#ccc;

}

form{
height: 500px;
}

.credittext{
	font-size:16px;
	color:#000;
}

.navTextTag{
	font-size:16px!important;
}

</style>

<body>
<%-- 
	從RentOrdServlet.java過來:
	 進這jsp的參數有  int totalday, String startday、 endday, slocno, rlocno
	list eVOList, motno, (session)memno, total

--%>

 	<%
 		RentOrdVO roQueryVO = (RentOrdVO) request.getAttribute("roQueryVO");
		String startday = (String)request.getAttribute("startday");
		String endday = (String)request.getAttribute("endday");
		String motno = (String) request.getAttribute("motno");
		String dayPicker = (String) request.getAttribute("dayPicker");
		String memno = (String) session.getAttribute("memno");
		String emtno_list_str = (String) request.getAttribute("emtno_list_str");
		String action = (String)request.getAttribute("action");
		pageContext.setAttribute("memno", memno);


		
	%>

<%--  	這是quick_search_product_end.jsp
	<br> memno: <c:out value="${memno}" default="no member login" />
	<br> start_time: <c:out value="${startday}" default="no value" />
	<br> end_time: <c:out value="${endday}" default="no value" />
	<br>motno: <c:out value="${motno}"  default="no value" />
	<br>required:slocno: <c:out value="${slocno}"  default="no value" />
	<br>required:rlocno: <c:out value="${rlocno}"  default="no value" />
	emtno_list_str	
	<br>emtno_list_str: <c:out value="${emtno_list_str}"  default="no value" />
	<c:forEach var="equipVO" items="${eVOList}">
		<br>eq-emtno: <c:out value="${equipVO.emtno}"  default="no value" />
	</c:forEach>
	<c:forEach var="emtno" items="${emtnoList}">
		<br>emtno[0]: <c:out value="${emtno}"  default="no value" />		
	</c:forEach>	--%>
	
<%-- Navigation --%>
	<nav id="mainNav" class="navbar navbar-default navbar-custom navbar-fixed-top">
		<div class="container-fluid">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header page-scroll">
				<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Toggle navigation</span> Menu <i class="fa fa-bars"></i>
				</button>
				<a class="navbar-brand page-scroll" href="<%=request.getContextPath()%>/index.jsp">AutoBike</a>
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


	<!-- 租車主軸Header -->
	

	<header id="rent">
<%----------------------------------------------------VVVV building area VVVV-----------------------------------------------------------%>
		<div class="row  col-sm-10 col-md-10 col-sm-offset-1 col-md-offset-1 t1">

	        <div class="receipt-main col-xs-10 col-sm-10 col-md-10 col-xs-offset-1 col-sm-offset-1 col-md-offset-1">
       			<div class="alert alert-success">
					  <strong>租賃單已成立!</strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					  編號:&nbsp;<c:out value="${roQueryVO.rentno}"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					  下訂時間:&nbsp;<fmt:formatDate pattern="yyyy-MM-dd hh:mm a" value="${roQueryVO.filldate}" />
				</div>
				<c:if test="${action == 'quick_search_money_transfer'}">
					<div class="alert alert-warning">
					  <strong>請您於兩天內將結算金額匯入指定帳戶，謝謝您的配合!</strong>
					</div>
				</c:if>
				
				
				
	            <div class="row">
	    			<div class="receipt-header">
						<div class="col-xs-6 col-sm-6 col-md-6">
							<div class="receipt-left">
							</div>
						</div>
						<div class="col-xs-6 col-sm-6 col-md-6 text-right">
							<div class="receipt-right">
							</div>
						</div>
					</div>
	            </div>
				<jsp:useBean id="memSvc" scope="page" class="com.member.model.MemberService" />
				<div class="row">
					<div class="receipt-header receipt-header-mid">
						<div class="col-xs-10 col-sm-10 col-md-10 text-left">
							<div class="receipt-right"><br>
							<jsp:useBean id="locSvc" scope="page" class="com.location.model.LocationService" />
								<div><b>會員姓名 :&nbsp;</b>&nbsp;&nbsp;${memSvc.getOneMember(memno).memname}</div>
								<div><b>手機號碼 :&nbsp;</b>&nbsp;&nbsp;${memSvc.getOneMember(memno).phone}</div>
								<div><b>電子信箱 :&nbsp;</b>&nbsp;&nbsp;${memSvc.getOneMember(memno).mail}</div>
								<div><b>租用時段 :&nbsp;</b>&nbsp;&nbsp;${startday} - ${endday}, 共計${totalday}天</div>
								<div><b>取車地點 :&nbsp;</b>&nbsp;&nbsp;${locSvc.getOneLocation(slocno).locname}營業所
								 - ${locSvc.getOneLocation(slocno).addr}</div>
								<div><b>還車地點 :&nbsp;</b>&nbsp;&nbsp;${locSvc.getOneLocation(rlocno).locname}營業所
								 - ${locSvc.getOneLocation(rlocno).addr}</div>
							</div>
						</div>
						<div class="col-xs-2 col-sm-2 col-md-2">
							<div class="receipt-left">
								<h1>明細</h1>
							</div>
						</div>
					</div>
	            </div>
	            <jsp:useBean id="mmSvc" scope="page" class="com.motor_model.model.MotorModelService" />
	            <jsp:useBean id="motorSvc" scope="page" class="com.motor.model.MotorService" />
	            <jsp:useBean id="ecSvc" scope="page" class="com.emt_cate.model.EmtCateService"/>	
				
	            <div>
	                <table class="table table-bordered">
	                    <thead>
	                        <tr>
	                            <th>商品名稱</th>
	                            <th>單價 * 天數</th>
	                        </tr>
	                    </thead>
	                    <tbody>
	                        <tr>
	                            <td class="col-md-9">
	                            	機車${motorSvc.findByPK(motno).modtype} - 
		                            ${mmSvc.findByPK(motorSvc.findByPK(motno).modtype).brand}&nbsp;
		                            ${mmSvc.findByPK(motorSvc.findByPK(motno).modtype).name}&nbsp;
		                            ${mmSvc.findByPK(motorSvc.findByPK(motno).modtype).displacement}c.c.
	                            </td>
	                            <td class="col-md-3">NT$ ${mmSvc.findByPK(motorSvc.findByPK(motno).modtype).renprice * totalday}
	                           	</td>
	                        </tr>
							<jsp:useBean id="emtSvc" scope="page" class="com.equipment.model.EquipmentService" />
							<c:forEach var="emtno" items="${emtnoList}">
							    <tr>
							        <td class="col-md-9">裝備${emtSvc.getOneEquipment(emtno).ecno} - ${ecSvc.getOneEmtCate(emtSvc.getOneEquipment(emtno).ecno).type}</td>
							        <td class="col-md-3">NT$  ${ecSvc.getOneEmtCate(emtSvc.getOneEquipment(emtno).ecno).price * totalday}</td>
							    </tr>
							</c:forEach>                         
	                        <tr>
	                            <td class="text-right"><h2><strong>Total: </strong></h2></td>
	                            <td class="text-left text-danger"><h2><strong><mark>NT$&nbsp;${total}</mark></strong></h2></td>
	                        </tr>
	                    </tbody>
	                </table>
	            </div>
				
				<div class="row">
					<div class="receipt-header receipt-header-mid receipt-footer">
						<div class="col-xs-4 col-sm-4 col-md-4">
						</div>
					</div>
	            </div>
				       
				<p class="text-center">
					<a href="<%=request.getContextPath()%>/index.jsp" class="btn btn-success btn-lg">
						<i class="glyphicon glyphicon-home"></i> 返回首頁
					</a>
				</p>
	  
		</div>
		</div>
		
	
	
		

	<%----------------------------------------------------^^^^ building area ^^^^-----------------------------------------------------------%>		
	</header>
	<footer class="col-sm-12 col-md-12">
		<div class="container-fluid">
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

	<script src="<%=request.getContextPath()%>/backend/Modified/jquery_1_10_1_min.js"></script>
	<script src="<%=request.getContextPath()%>/backend/Modified/twitter_bootstrap_3_3_7_min.js"></script>
	<script src="<%=request.getContextPath()%>/js/owl.carousel.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/jquery.magnific-popup.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/agency.min.js"></script>	
	
</body>
</html>