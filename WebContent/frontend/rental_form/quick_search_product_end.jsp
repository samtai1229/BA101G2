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
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" media="all" href="<%=request.getContextPath()%>/frontend/rental_form/Modified/other.css" />
<link rel="stylesheet" type="text/css" media="all" href="<%=request.getContextPath()%>/frontend/rental_form/Modified/daterangepicker.css" />

<%-- basic --%>
<link href="<%=request.getContextPath()%>/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
<link href="<%=request.getContextPath()%>/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
<link href='https://fonts.googleapis.com/css?family=Kaushan+Script' rel='stylesheet' type='text/css' />
<link href="<%=request.getContextPath()%>/frontend/rental_form/Modified/agency.min.css" rel="stylesheet" />
<link href="<%=request.getContextPath()%>/frontend/rental_form/Modified/agency.css" rel="stylesheet" />
<link href="<%=request.getContextPath()%>/frontend/rental_form/Modified/other.css" rel="stylesheet" />

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

</style>

<body>
<%-- 
	�qRentOrdServlet.java�L��:
	 �i�ojsp���ѼƦ�  int totalday, String startday�B endday, slocno, rlocno
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

<%--  	�o�Oquick_search_product_end.jsp
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
	
	<!-- Navigation -->
	<nav id="mainNav" class="navbar navbar-default navbar-custom navbar-fixed-top">
		<div class="container-fluid">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header page-scroll">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Toggle navigation</span> Menu <i class="fa fa-bars"></i>
				</button>
				<a class="navbar-brand page-scroll" href="<%=request.getContextPath()%>/index.jsp">AutoBike</a>
			</div>

			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav navbar-right">
					<li class="hidden"><a href="#page-top"></a></li>
					<li><a class="page-scroll navtext" href="<%=request.getContextPath()%>/frontend/rental_form/rental_category.jsp">
					<i class="glyphicon glyphicon-heart"></i>�ڭn����</a></li>
					<li><a class="page-scroll navtext" href="#news">
					<i class="glyphicon glyphicon-alert navtext"></i>�̷s����</a></li>
					<li><a class="page-scroll navtext" href="#board">
					<i class="fa fa-comments-o"></i>�d���O</a></li>
					<li><a class="page-scroll navtext" href="#loc">
					<i class="fa fa-search"></i>�A�Ⱦ��I</a></li>
					<li><a class="page-scroll navtext" href="<%=request.getContextPath()%>/backend/member/member.do">
					<i class="fa fa-shopping-cart"></i>�G�⨮�ʶR</a></li>
					<c:if test="${not empty memno}">
						<li><a class="page-scroll navtext" href="<%=request.getContextPath()%>/backend/member/member.do?action=getOne_For_Enter&memid=${memno}">�w��A${memname}</a></li>
						<li><a class="page-scroll navtext" href="<%=request.getContextPath()%>/backend/member/member.do?action=logout" data-toggle="modal">
						<i class="glyphicon glyphicon-user navtext"></i>�n�X</a>
						</li>
					</c:if>
				</ul>
			</div>
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container-fluid -->
	</nav>
	<!-- �����D�bHeader -->
	

	<header id="rent">
<%----------------------------------------------------VVVV building area VVVV-----------------------------------------------------------%>
		<div class="row  col-sm-10 col-md-10 col-sm-offset-1 col-md-offset-1 t1">

	        <div class="receipt-main col-xs-10 col-sm-10 col-md-10 col-xs-offset-1 col-sm-offset-1 col-md-offset-1">
       			<div class="alert alert-success">
					  <strong>�����w����!</strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					  �s��:&nbsp;<c:out value="${roQueryVO.rentno}"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					  �U�q�ɶ�:&nbsp;<fmt:formatDate pattern="yyyy-MM-dd hh:mm a" value="${roQueryVO.filldate}" />
				</div>
				<c:if test="${action == 'quick_search_money_transfer'}">
					<div class="alert alert-warning">
					  <strong>�бz���Ѥ��N������B�פJ���w�b��A���±z���t�X!</strong>
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
								<div><b>�|���m�W :&nbsp;</b>&nbsp;&nbsp;${memSvc.getOneMember(memno).memname}</div>
								<div><b>������X :&nbsp;</b>&nbsp;&nbsp;${memSvc.getOneMember(memno).phone}</div>
								<div><b>�q�l�H�c :&nbsp;</b>&nbsp;&nbsp;${memSvc.getOneMember(memno).mail}</div>
								<div><b>���ήɬq :&nbsp;</b>&nbsp;&nbsp;${startday} - ${endday}, �@�p${totalday}��</div>
								<div><b>�����a�I :&nbsp;</b>&nbsp;&nbsp;${locSvc.getOneLocation(slocno).locname}��~��
								 - ${locSvc.getOneLocation(slocno).addr}</div>
								<div><b>�٨��a�I :&nbsp;</b>&nbsp;&nbsp;${locSvc.getOneLocation(rlocno).locname}��~��
								 - ${locSvc.getOneLocation(rlocno).addr}</div>
							</div>
						</div>
						<div class="col-xs-2 col-sm-2 col-md-2">
							<div class="receipt-left">
								<h1>����</h1>
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
	                            <th>�ӫ~�W��</th>
	                            <th>��� * �Ѽ�</th>
	                        </tr>
	                    </thead>
	                    <tbody>
	                        <tr>
	                            <td class="col-md-9">
	                            	����${motorSvc.findByPK(motno).modtype} - 
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
							        <td class="col-md-9">�˳�${emtSvc.getOneEquipment(emtno).ecno} - ${ecSvc.getOneEmtCate(emtSvc.getOneEquipment(emtno).ecno).type}</td>
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
						<i class="glyphicon glyphicon-home"></i> ��^����
					</a>
				</p>
	  
		</div>
		</div>
		
	
	
		

	<%----------------------------------------------------^^^^ building area ^^^^-----------------------------------------------------------%>		
	</header>
	<footer class="col-sm-12 col-md-12">
		<div class="container-fluid">
			<div class="col-xs-12 col-sm-4">
				<span>�a�}:��饫����Ϥ�����115��</span>
			</div>
			<div class="col-xs-12 col-sm-4">
				<span>EMAIL:taic@oregonstate.edu</span>
			</div>
			<div class="col-xs-12 col-sm-4">
				<span>TEL:0900-000-000</span>
			</div>
		</div>
	</footer>

	<script src="https://code.jquery.com/jquery.js"></script>
    <script type="text/javascript" src="https://code.jquery.com/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="http://netdna.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
		<!-- basic -->
	<script src="<%=request.getContextPath()%>/js/owl.carousel.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/jquery.magnific-popup.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/agency.min.js"></script>	

	
</body>
</html>