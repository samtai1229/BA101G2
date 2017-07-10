<%@page import="java.sql.Timestamp"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.motor.model.*"%>
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


*{
	font-size:18px;
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
	從RentOrdServlet.java過來:
	 進這jsp的參數有  int totalday, String startday、 endday, slocno, rlocno
	list eVOList, motno, (session)memno, total

--%>

 	<%
		MotorVO motorQueryVO = (MotorVO) request.getAttribute("motorQueryVO");
		//String startday = (String)request.getAttribute("startday");
		//String endday = (String)request.getAttribute("endday");
		String dayPicker = (String) request.getAttribute("dayPicker");

		String memno = (String) session.getAttribute("memno");
		pageContext.setAttribute("memno", memno);
	%>

<%--	這是quick_search_product3.jsp
	<br> memno: <c:out value="${memno}" default="no member login" />
	<br> start_time: <c:out value="${startday}" default="no value" />
	<br> end_time: <c:out value="${endday}" default="no value" />
	
	每種裝備先隨便選兩個來，不夠再顯示 
	<br>required:ecno1_List_size: <c:out value="${ecno1_List_size}"  default="no value" />
	<br>required:ecno2_List_size: <c:out value="${ecno2_List_size}"  default="no value" />
	<br>required:ecno3_List_size: <c:out value="${ecno3_List_size}"  default="no value" />
	<br>required:ecno4_List_size: <c:out value="${ecno4_List_size}"  default="no value" />
	<br>required:slocno: <c:out value="${slocno}"  default="no value" />
	<br>required:rlocno: <c:out value="${rlocno}"  default="no value" />
	emtno_list_str	
	<br>emtno_list_str: <c:out value="${emtno_list_str}"  default="no value" />
	<c:forEach var="equipVO" items="${eVOList}">
		<br>eq-emtno: <c:out value="${equipVO.emtno}"  default="no value" />
	</c:forEach>  --%>		
		
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
		<div class="row  col-sm-6 col-md-6 t1">
	        <div class="receipt-main col-xs-10 col-sm-10 col-md-10 col-xs-offset-1 col-sm-offset-1 col-md-offset-1">
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
	
							<c:forEach var="equipVO" items="${eVOList}">
							    <tr>
							        <td class="col-md-9">裝備${equipVO.ecno} - ${ecSvc.getOneEmtCate(equipVO.ecno).type}</td>
							        <td class="col-md-3">NT$  ${ecSvc.getOneEmtCate(equipVO.ecno).price * totalday}</td>
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
						<div class="col-xs-8 col-sm-8 col-md-8 text-left">
							<div class="receipt-right"><%java.util.Date da = new java.util.Date();%>
								<h4><b>目前時間:&nbsp;</b>&nbsp;<%=da%></h4>
								<h4>確認無誤後，請您至右方進行結帳手續</h4>
							</div>
						</div>
						<div class="col-xs-4 col-sm-4 col-md-4">
						</div>
					</div>
	            </div>
				
	        </div>    
		</div>
	
	
		<div class="title nav nav-tabs col-sm-6 col-md-6 t2">
			<div class="title nav nav-tabs col-sm-8 col-md-8 col-sm-offset-2 col-md-offset-2">結帳方式:</div>
			<div role="tabpanel">
	<!-- 標籤面板：標籤區 -->
			    <ul class="nav nav-tabs col-sm-6 col-md-6 col-sm-offset-3 col-md-offset-3" role="tablist">
			        <li role="presentation" class="active">
			       		<a href="#tab1" aria-controls="tab1" role="tab" data-toggle="tab"><label>信用卡</label></a>
			        </li>
			        <li role="presentation">
				    	<a href="#tab2" aria-controls="tab2" role="tab" data-toggle="tab"><label>銀行匯款</label></a>
			        </li>
			    </ul>
			
	<!-- 標籤面板：內容區 -->
	
			    <div class="tab-content col-sm-8 col-md-8 col-sm-offset-2 col-md-offset-2">
				    <div role="tabpanel" class="tab-pane active" id="tab1">
					    <form METHOD="post" ACTION="<%=request.getContextPath()%>/backend/rent_ord/rentOrd.do">
							<div class="container">
								<div class="row">
									<div class="col-sm-12 col-md-12">
										<fieldset><br>
											<div class="form-group">
												<label class="control-label">持卡人姓名</label>
												<div class="controls">
													<input class="form-control" pattern="\w+ \w+.*" required="" title="Fill your first and last name" type="text">
												</div>
											</div>
											<div class="form-group">
												<label class="control-label">信用卡卡號</label>
												<div class="controls">
													<div class="row">
														<div class="col-sm-3 col-md-3">
															<input autocomplete="off" class="form-control" maxlength="4" pattern="\d{4}" required="" title="First four digits" type="text">
														</div>
														<div class="col-sm-3 col-md-3">
															<input autocomplete="off" class="form-control" maxlength="4" pattern="\d{4}" required="" title="Second four digits" type="text">
														</div>
														<div class="col-sm-3 col-md-3">
															<input autocomplete="off" class="form-control" maxlength="4" pattern="\d{4}" required="" title="Third four digits" type="text">
														</div>
														<div class="col-sm-3 col-md-3">
															<input autocomplete="off" class="form-control" maxlength="4" pattern="\d{4}" required="" title="Fourth four digits" type="text">
														</div>
													</div>
												</div>
											</div>
											<div class="form-group">
												<label class="control-label">信用卡到期 月份  / 年份</label>
												<div class="controls">
													<div class="row">
														<div class="col-xs-3 col-sm-3 col-md-3 credittext">
															<select class="">
																<option>January</option>
																<option>February</option>
																<option>March</option>
																<option>April</option>
																<option>May</option>
																<option>June</option>
																<option>July</option>
																<option>August</option>
																<option>September</option>
																<option>October</option>																
																<option>November</option>	
																<option>December</option>	
															</select>
														</div>
														<div class="col-xs-3 col-sm-3 col-md-3 credittext">
															<select class="">
																<option>2017</option>
																<option>2018</option>
																<option>2019</option>
																<option>2020</option>
																<option>2021</option>
																<option>2022</option>
																<option>2023</option>
																<option>2024</option>
															</select>
														</div>
														
														
													</div>
												</div>
											</div>
											<div class="form-group">
												<label class="control-label">驗證碼</label>
												<div class="controls">
													<div class="row">
														<div class="col-sm-4 col-md-4">
															<input autocomplete="off" class="form-control" maxlength="3" pattern="\d{3}" required="" title="Three digits at back of your card" type="text">
														</div>
														<div class="col-sm-8 col-md-8">
															<!-- screenshot may be here -->
														</div>
													</div>
												</div>
											</div>
										</fieldset>
									</div>
								</div>
							</div>
				        	<p class="title text-center">
								<c:if test="<%=memno == null%>">
									<input type="hidden" name="action" value="redirect_to_login">
								</c:if>
								<c:if test="<%=memno != null%>">
									<input type="hidden" name="startdate" value="${startday}">
									<input type="hidden" name="enddate" value="${endday}">
									<input type="hidden" name="totaldate" value="${totalday}">
									<input type="hidden" name="slocno" value="${slocno}">
									<input type="hidden" name="rlocno" value="${rlocno}">
									<input type="hidden" name="filldate" value="<%=da%>">									
									<input type="hidden" name="motno" value="${motorQueryVO.motno}">
									<input type="hidden" name="total" value="${total}">
									<input type="hidden" name="emtno_list_str" value="${emtno_list_str}">
									<input type="hidden" name="action"    value="quick_search_credit_card">
								</c:if>
								<button type="submit" class="btn btn-success btn-lg">
									<i class="glyphicon glyphicon-ok"></i>刷卡結帳
								</button>
								<a onclick="history.back()" class="btn btn-danger btn-lg">
									<i class="glyphicon glyphicon-remove"></i>返回前頁
								</a>
							</p>					
						</form>	
					</div>
						
						
					
			    	<div role="tabpanel" class="tab-pane" id="tab2">
				        <form METHOD="post" ACTION="<%=request.getContextPath()%>/backend/rent_ord/rentOrd.do">	
				        	
				        	<br><div>以下為匯款帳戶，請您於<mark>成立訂單後兩天內</mark>匯入結算金額</div>
				        	<div>我們會先為您保留車輛與裝備</div>
				        	<div>愈期匯款時訂單會自動取消，請盡速匯款</div>
				        	<div>感謝您的配合</div><br>	
				        	<div>匯款帳戶: XXX-XXXXXX-XXXXXX 戶名:XXX</div>
				        	<br><br><br><br><br><br><br><br><br>
				        	<p class="title text-center">
								<c:if test="<%=memno == null%>">
									<input type="hidden" name="action" value="redirect_to_login">
								</c:if>
								<c:if test="<%=memno != null%>">
									<input type="hidden" name="startdate" value="${startday}">
									<input type="hidden" name="enddate" value="${endday}">
									<input type="hidden" name="totaldate" value="${totalday}">
									<input type="hidden" name="slocno" value="${slocno}">
									<input type="hidden" name="rlocno" value="${rlocno}">
									<input type="hidden" name="filldate" value="<%=da%>">									
									<input type="hidden" name="motno" value="${motorQueryVO.motno}">
									<input type="hidden" name="total" value="${total}">
									<input type="hidden" name="emtno_list_str" value="${emtno_list_str}">
									<input type="hidden" name="action"   value="quick_search_money_transfer">
								</c:if>
								<button type="submit" class="btn btn-success btn-lg">
									<i class="glyphicon glyphicon-ok"></i>成立訂單
								</button>
								<a onclick="history.back()" class="btn btn-danger btn-lg">
									<i class="glyphicon glyphicon-remove"></i>返回上頁
								</a>
							</p>
						</form>
					</div>
			    </div>
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