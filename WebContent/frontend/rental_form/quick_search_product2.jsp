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
<meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
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

.media-object{
	margin-left:20px;
}

.btn{
	margin-top:5px;
	margin-right:10px;
	margin-left:10px;
	
}

.t1{

margin-top:100px;

}

p{
	height:20px;
}

.list-group-item, .InputForm{
	color:#000;
}

</style>
<body>
<%-- 
	從RentOrdServlet.java過來:
	 進這jsp的參數有  int totalday, String startday、 endday,
	list ecno1~ecno4, int ecno1~4.size, motno, (session)memno
	
	應該要從這個頁面送出去的資料有(next -> servlet -> 結算 + 付款.jsp)
	memno, equip count(each), slocno, rlocno, startday, endday, totalday
--%>

	<%
		MotorVO motorQueryVO = (MotorVO) request.getAttribute("motorQueryVO");
		String dayPicker = (String) request.getAttribute("dayPicker");
		String confirmed_rentday = (String)request.getAttribute("confirmed_rentday");
		
		String memno = (String) session.getAttribute("memno");
		pageContext.setAttribute("memno", memno);
	%>
<%--
	這是quick_search_product2.jsp
	<br> memno: <c:out value="${memno}" default="no member login" />
	<br> start_time: <c:out value="${startday}" default="no value" />
	<br> end_time: <c:out value="${endday}" default="no value" />
	<br> availableEmtnoList: <c:out value="${availableEmtnoList}" default="no value" />
	<br> confirmed_rentday: <c:out value="${confirmed_rentday}" default="no value" />--%>	
	<%-- 每種裝備先隨便選兩個來，不夠再顯示  --%>
<%--	
	<br>available ecno1_List_size: <c:out value="${ecno1_List_size}"  default="no value" />
	<br>available ecno2_List_size: <c:out value="${ecno2_List_size}"  default="no value" />
	<br>available ecno3_List_size: <c:out value="${ecno3_List_size}"  default="no value" />
	<br>available ecno4_List_size: <c:out value="${ecno4_List_size}"  default="no value" />

--%>	
	<jsp:useBean id="mmSvc" scope="page" class="com.motor_model.model.MotorModelService" />
	<!-- Navigation -->
	<nav id="mainNav" class="navbar navbar-default navbar-custom navbar-fixed-top">
		<div class="container-fluid">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header page-scroll">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Toggle navigation</span> Menu <i
						class="fa fa-bars"></i>
				</button>
				<a class="navbar-brand page-scroll" href="<%=request.getContextPath()%>/index.jsp">AutoBike</a>
			</div>

			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav navbar-right">
					<li class="hidden"><a href="#page-top"></a></li>
					<li><a class="page-scroll" href="<%=request.getContextPath()%>/frontend/rental_form/rental_category.jsp">
					<i class="glyphicon glyphicon-heart"></i>我要租車</a></li>
					<li><a class="page-scroll" href="#news">
					<i class="glyphicon glyphicon-alert"></i>最新消息</a></li>
					<li><a class="page-scroll" href="#board">
					<i class="fa fa-comments-o"></i>留言板</a></li>
					<li><a class="page-scroll" href="#loc">
					<i class="fa fa-search"></i>服務據點</a></li>
					<li><a href="<%=request.getContextPath()%>/backend/member/member.do">
					<i class="fa fa-shopping-cart"></i>二手車購買</a></li>
					<c:if test="${not empty memno}">
						<li><a href="<%=request.getContextPath()%>/backend/member/member.do?action=getOne_For_Enter&memid=${memno}">歡迎，${memname}</a></li>
						<li><a href="<%=request.getContextPath()%>/backend/member/member.do?action=logout" data-toggle="modal">
						<i class="glyphicon glyphicon-user"></i>登出</a>
						</li>
					</c:if>
				</ul>
			</div>
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container-fluid -->
	</nav>


	<!-- 租車主軸Header -->
	<header id="rent">
		
<%----------------------------------------------------VVVV building area VVVV-----------------------------------------------------------%>

		<div class="container">
			<form METHOD="post" ACTION="<%=request.getContextPath()%>/backend/rent_ord/rentOrd.do" id="myForm">
				
					<div class="container">
						<div class="col-md-offset-1 col-md-5 t1">
							<img id="item-display"
								src="<%=request.getContextPath()%>/backend/motor_model/mmReader.do?modtype=${motorQueryVO.modtype}"
								alt=""></img>
						</div>
						<div class="col-md-6 t1">
							<div class="product-title">
								<div>${mmSvc.findByPK(motorQueryVO.modtype).brand}
									${mmSvc.findByPK(motorQueryVO.modtype).name}</div>
								<c:if
									test="${mmSvc.findByPK(motorQueryVO.modtype).displacement > 150}">
									<div>重機專區</div>
								</c:if>
								<div class="dynamic-text">${mmSvc.findByPK(motorQueryVO.modtype).displacement}c.c.</div>
							</div>
							<div class="product-desc">The Corsair Gaming Series GS600
								is the ideal price/performance choice for mid-spec gaming PC</div>
							<hr>
							<div class="product-price">日租價  NT$${mmSvc.findByPK(motorQueryVO.modtype).renprice}</div>
							<div class="dynamic-text">車輛編號 : ${motorQueryVO.motno}</div>
							<div class="dynamic-text">起始時間 : <mark>${startday}</mark></div>
							<div class="dynamic-text">結束時間 : <mark>${endday}</mark></div>
							<div class="dynamic-text">租金統計 : <mark>${totalday}天 *  ${mmSvc.findByPK(motorQueryVO.modtype).renprice} NT$/天
							= NT$ ${mmSvc.findByPK(motorQueryVO.modtype).renprice * totalday}</mark></div>
							<hr>

							<jsp:useBean id="locSvc" scope="page" class="com.location.model.LocationService" />
							<div class="InputForm">
								<label class="title"><mark>請選擇取車地點</mark>:</label><label id="slocnoAnchor" style="color:red; background-color:white"></label>
								<select name="slocno" id="slocno">
									<option value="">---------------</option>
		 							<c:forEach var="locVO" items="${locSvc.all}">
		 							<c:if test="${locVO.locno != 'TPE00'}"> 
										<option value="${locVO.locno}">${locVO.locname}營業所: ${locVO.addr}</option>
									</c:if>	
									</c:forEach> 															
								</select><br/>
							</div>
							<div class="InputForm">
								<label class="title"><mark>請選擇還車地點</mark>:</label><label id="rlocnoAnchor"  style="color:red; background-color:white""></label>
								<select name="rlocno" id="rlocno">
									<option value="">---------------</option>
		 							<c:forEach var="locVO" items="${locSvc.all}">
		 							<c:if test="${locVO.locno != 'TPE00'}"> 
										<option value="${locVO.locno}">${locVO.locname}營業所: ${locVO.addr}</option>
									</c:if>	
									</c:forEach> 															
								</select><br/>
							</div>	 
						</div>
					</div>
				
			
					
					<jsp:useBean id="ecSvc" scope="page" class="com.emt_cate.model.EmtCateService"/>						
				
					<hr>
					
							<div class="list-group-item"> 
								<label for="check_1">
										<div class="media" class="btn">
											<div class="pull-right">
											<c:if test="${ecno1_List_size!=0}">
												<select name="ecno1" id="number">
													<option>0</option>
												<c:if test="${ecno1_List_size>0}">
													<option>1</option>
												</c:if>
												<c:if test="${ecno1_List_size>1}">
													<option>2</option>
												</c:if>
												</select>
											</c:if>
											</div>
											<c:if test="${ecno1_List_size==0}">
												<div class="pull-right">目前無庫存</div>
												<input type="hidden" name="ecno1" value="0">
											</c:if>
											<c:if test="${ecno1_List_size!=0}">
												<div class="pull-right">日租價: NT$${ecSvc.getOneEmtCate("EC01").price}
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;數量:</div>
											</c:if>
											<div class="pull-left">
												<img alt="Image" class="media-object" 
												src="http://placehold.it/100x70">
											</div>
											<div class="media-body">
												<h4 class="media-heading">${ecSvc.getOneEmtCate("EC01").type}</h4>
												<p>helmet</p>
											</div>
										</div>
								</label>
							</div> 
							<div class="list-group-item">
							
								<label for="check_2">
										<div class="media">
											<div class="pull-right">
											<c:if test="${ecno2_List_size!=0}">
												<select name="ecno2" id="number">
													<option>0</option>
												<c:if test="${ecno2_List_size>0}">
													<option>1</option>
												</c:if>
												<c:if test="${ecno2_List_size>1}">
													<option>2</option>
												</c:if>
												</select>
											</c:if>
											</div>
											<c:if test="${ecno2_List_size==0}">
												<div class="pull-right">目前無庫存</div>
												<input type="hidden" name="ecno2" value="0">
											</c:if>
											<c:if test="${ecno2_List_size!=0}">
												<div class="pull-right">日租價: NT$${ecSvc.getOneEmtCate("EC02").price}
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;數量:</div>
											</c:if>
											<div class="pull-left">
												<img alt="Image" class="media-object" src="http://placehold.it/100x70">
											</div>
											<div class="media-body">
												<h4 class="media-heading">${ecSvc.getOneEmtCate("EC02").type}</h4>
												<p>Anti-fall gloves</p>
											</div>
										</div>
								</label>
							</div> 
							<div class="list-group-item">
								<label for="check_3">
										<div class="media">
											<div class="pull-right">
											<c:if test="${ecno3_List_size!=0}">
												<select name="ecno3" id="number">
													<option>0</option>
												<c:if test="${ecno3_List_size>0}">
													<option>1</option>
												</c:if>
												<c:if test="${ecno3_List_size>1}">
													<option>2</option>
												</c:if>
												</select>
											</c:if>
											</div>
											<c:if test="${ecno3_List_size==0}">
												<div class="pull-right">目前無庫存</div>
												<input type="hidden" name="ecno3" value="0">
											</c:if>
											<c:if test="${ecno3_List_size!=0}">
												<div class="pull-right">日租價: NT$${ecSvc.getOneEmtCate("EC03").price}
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;數量:</div>
											</c:if>
											<div class="pull-left">
												<img alt="Image" class="media-object" src="http://placehold.it/100x70">
											</div>
											<div class="media-body">
												<h4 class="media-heading">${ecSvc.getOneEmtCate("EC03").type}</h4>
												<p>Anti-wrestling clothing</p>
											</div>
										</div>
								</label>
							</div> 
							<div class="list-group-item"> 
								<label for="check_4">
										<div class="media">
											<div class="pull-right">
											<c:if test="${ecno4_List_size!=0}">
												<select name="ecno4" id="number">
													<option>0</option>
												<c:if test="${ecno4_List_size>0}">
													<option>1</option>
												</c:if>
												<c:if test="${ecno4_List_size>1}">
													<option>2</option>
												</c:if>
												</select>
											</c:if>
											</div>
											<c:if test="${ecno4_List_size==0}">
												<div class="pull-right">目前無庫存</div>
												<input type="hidden" name="ecno4" value="0">
											</c:if>
											<c:if test="${ecno4_List_size!=0}">
												<div class="pull-right">日租價: NT$${ecSvc.getOneEmtCate("EC04").price}
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;數量:</div>
											</c:if>
											<div class="pull-left">
												<img alt="Image" class="media-object" src="http://placehold.it/100x70">
											</div>
											<div class="media-body">
												<h4 class="media-heading">${ecSvc.getOneEmtCate("EC04").type}</h4>
												<p>Driving recorder</p>
											</div>
										</div>
								</label>
							</div>
						
					
				
				<p class="text-center">
					<c:if test="<%=memno == null%>">
						<input type="hidden" name="action" value="redirect_to_login">
					</c:if>
					<c:if test="<%=memno != null%>">
						<input type="hidden" name="action" value="quick_search_product_3">
						<input type="hidden" name="startday" value="${startday}">
						<input type="hidden" name="endday" value="${endday}">
						<input type="hidden" name="totalday" value="${totalday}">
						<input type="hidden" name="motno" value="${motorQueryVO.motno}">
					</c:if>
					<button type="submit" class="btn btn-success btn-lg" id="submitButton">
						<i class="glyphicon glyphicon-ok"></i>確認定單
					</button>
					<a onclick="history.back()" class="btn btn-danger btn-lg">
						<i class="glyphicon glyphicon-remove"></i>返回前頁
					</a>
				</p>
			</form>
		</div>
	<br><br><br><br><br><br>
	
	<%----------------------------------------------------^^^^ building area ^^^^-----------------------------------------------------------%>		
	</header>
	<footer>
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

	<script src="https://code.jquery.com/jquery.js"></script>
    <script type="text/javascript" src="https://code.jquery.com/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="http://netdna.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
		<!-- basic -->
	
	<script src="<%=request.getContextPath()%>/frontend/rental_form/Modified/quick_search_product.js"></script>
	<script src="<%=request.getContextPath()%>/js/owl.carousel.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/jquery.magnific-popup.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/agency.min.js"></script>
</body>
</html>