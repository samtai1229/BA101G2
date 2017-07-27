<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.motor.model.*"%>
<%@ page import="com.motor_model.model.*"%>
<%@ page import="com.member.model.*"%>
<%@ page import="java.util.*"%>
<%
	String motno = (String) request.getAttribute("motno");
	MotorService mSvc = new MotorService();
	MotorVO motorVO = mSvc.findByPK(motno);
	MotorModelService mmSvc = new MotorModelService();
	MotorModelVO mmVO = mmSvc.findByPK(motorVO.getModtype());
	String memno = (String) session.getAttribute("memno");
	String memname = (String) session.getAttribute("memname");
	MemberService memSvc = new MemberService();
	MemberVO memVO = memSvc.getOneMember(memno);
	pageContext.setAttribute("memVO", memVO);
	pageContext.setAttribute("motorVO", motorVO);
	pageContext.setAttribute("mm", mmVO);
%>
<!DOCTYPE html>
<html>
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
	
<head>
<title>二手車交易</title>


<style type="text/css">

/* Padding - just for asthetics on Bootsnipp.com */

/* CSS for Credit Card Payment form */
.credit-card-box .panel-title {
	display: inline;
	font-weight: bold;
}

.credit-card-box .form-control.error {
	border-color: red;
	outline: 0;
	box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075), 0 0 8px
		rgba(255, 0, 0, 0.6);
}

.credit-card-box label.error {
	font-weight: bold;
	color: red;
	padding: 2px 8px;
	margin-top: 2px;
}

.credit-card-box .payment-errors {
	font-weight: bold;
	color: red;
	padding: 2px 8px;
	margin-top: 2px;
}

.credit-card-box label {
	display: block;
}
/* The old "center div vertically" hack */
.credit-card-box .display-table {
	display: table;
}

.credit-card-box .display-tr {
	display: table-row;
}

.credit-card-box .display-td {
	display: table-cell;
	vertical-align: middle;
	width: 50%;
}
/* Just looks nicer */
.credit-card-box .panel-heading img {
	min-width: 180px;
}

.table {
	margin-top: 90px;
}

.motoimg {
	margin-top: 25px;
}
.panel {
    margin-top: -200px;
}
body {
       background: linear-gradient( rgba(0, 0, 0, 0.3), rgba(0, 0, 0, 0.3) ),url(/BA101G2/img/header1.jpg) no-repeat center center fixed; 
	    -webkit-background-size: cover;
	    -moz-background-size: cover;
	    -o-background-size: cover;
	    background-size: cover;
}

.divTag{
	margin-top:150px;
}

#carBoxTag{margin-top:0px;}

#introTag,td,th{color:#fff;font-size:20px;}
</style>
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
		
		
			<div class="col-xs-12 col-sm-4">
				<div class="motoimg">
					<img
						src="<%=request.getContextPath()%>/backend/motor_model/mmReader.do?modtype=${motorVO.modtype}"
						width='300' height='300' style="display: block; margin: auto;">
				</div>
			</div>
			<div class="col-xs-12 col-sm-8">

				<table class="table" width='100%' height="100px">
					<tr>
						<h2>
						<th style="text-align: center; width: 100px;">廠牌</th>
						<th style="text-align: center; width: 100px;">型號</th>
						<th style="text-align: center; width: 100px;">排氣量</th>
						<th style="text-align: center; width: 100px;">售價(NTD)</th>
					</tr>
					</h2>
					<tr align='center' valign='middle'>
						<td>${mm.brand}</td>
						<td>${mm.name}</td>
						<td>${mm.displacement}</td>
						<td>${mm.saleprice}</td>
					</tr>
				</table>
			</div>
			
			
			<div class="col-xs-12 col-sm-12">
				<div class="col-xs-12 col-sm-5" style="margin-top: 50px" id="introTag">${mm.intro}</div>
				<div class="col-xs-12 col-sm-7">
					<form action="<%=request.getContextPath()%>/frontend/second_order/SecOrd.do">
								<div class="panel panel-default credit-card-box" id="carBoxTag">
									<div class="panel-heading display-table">
										<div class="row display-tr">
											<h3 class="panel-title display-td">Payment Details</h3>
											<div class="display-td">
												<img class="img-responsive pull-right"
													src="http://i76.imgup.net/accepted_c22e0.png">
											</div>
										</div>
									</div>
									<div class="panel-body">
										<form role="form" id="payment-form" method="POST" ACTION="<%=request.getContextPath()%>/frontend/second_order/SecOrd.do">
											<div class="row">
												<div class="col-xs-12">
													<div class="form-group">
														<label for="cardNumber">卡號</label>
														<div class="input-group">
															<input type="tel" class="form-control" name="cardNumber"
																placeholder="Valid Card Number" autocomplete="cc-number"
																required autofocus /> <span class="input-group-addon"><i
																class="fa fa-credit-card"></i></span>
														</div>
													</div>
												</div>
											</div>
											<div class="row">
												<div class="col-xs-7 col-md-7">
													<div class="form-group">
														<label for="cardExpiry"><span class="hidden-xs">卡片到期日</span><span
															class="visible-xs-inline">EXP</span> DATE</label> <input
															type="tel" class="form-control" name="cardExpiry"
															placeholder="MM / YY" autocomplete="cc-exp" required />
													</div>
												</div>
												<div class="col-xs-5 col-md-5 pull-right">
													<div class="form-group">
														<label for="cardCVC">CV CODE</label> <input type="tel"
															class="form-control" name="cardCVC" placeholder="CVC"
															autocomplete="cc-csc" required />
													</div>
												</div>
											</div>
											<div class="row">
												<div class="col-xs-12">
	
													<input class="subscribe btn btn-success btn-lg btn-block"
														type="submit" onclick="ShowSuccess();" value="送出">
	
												</div>
											</div>
											<div class="row" style="display: none;">
												<div class="col-xs-12">
													<p class="payment-errors"></p>
												</div>
											</div>
											<input type="hidden" name="memno" value="${memVO.memno}">
											<input type="hidden" name="motno" value="${motorVO.motno}">
											<input type="hidden" name="modtype" value="${mm.modtype}">
											<input type="hidden" name="location" value="<%=request.getServletPath()%>"> <input
												type="hidden" name="action" value="buildOrder">
										</form>
									</div>
								</div>
					</form>
				</div>
			</div>
		</div>
	</div>

</body>

	<script src="<%=request.getContextPath()%>/backend/Modified/jquery_1_10_1_min.js"></script>
	<script src="<%=request.getContextPath()%>/backend/Modified/twitter_bootstrap_3_3_7_min.js"></script>
	<script src="<%=request.getContextPath()%>/js/agency.min.js"></script>
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.13.1/jquery.validate.min.js"></script>
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery.payment/1.2.3/jquery.payment.min.js"></script>
	<script type="text/javascript" src="https://js.stripe.com/v2/"></script>

	<script type="text/javascript">
		var $form = $('#payment-form');
		$form.find('.subscribe').on('click', payWithStripe);

		/* If you're using Stripe for payments */
		function payWithStripe(e) {
			e.preventDefault();

			/* Abort if invalid form data */
			if (!validator.form()) {
				return;
			}

			/* Visual feedback */
			$form.find('.subscribe').html(
					'Validating <i class="fa fa-spinner fa-pulse"></i>').prop(
					'disabled', true);

			var PublishableKey = 'pk_test_6pRNASCoBOKtIshFeQd4XMUh'; // Replace with your API publishable key
			Stripe.setPublishableKey(PublishableKey);

			/* Create token */
			var expiry = $form.find('[name=cardExpiry]').payment(
					'cardExpiryVal');
			var ccData = {
				number : $form.find('[name=cardNumber]').val().replace(/\s/g,
						''),
				cvc : $form.find('[name=cardCVC]').val(),
				exp_month : expiry.month,
				exp_year : expiry.year
			};

			Stripe.card
					.createToken(
							ccData,
							function stripeResponseHandler(status, response) {
								if (response.error) {
									/* Visual feedback */
									$form.find('.subscribe').html('Try again')
											.prop('disabled', false);
									/* Show Stripe errors on the form */
									$form.find('.payment-errors').text(
											response.error.message);
									$form.find('.payment-errors').closest(
											'.row').show();
								} else {
									/* Visual feedback */
									$form
											.find('.subscribe')
											.html(
													'Processing <i class="fa fa-spinner fa-pulse"></i>');
									/* Hide Stripe errors on the form */
									$form.find('.payment-errors').closest(
											'.row').hide();
									$form.find('.payment-errors').text("");
									// response contains id and card, which contains additional card details            
									console.log(response.id);
									console.log(response.card);
									var token = response.id;
									// AJAX - you would send 'token' to your server here.
									$
											.post('/account/stripe_card_token',
													{
														token : token
													})
											// Assign handlers immediately after making the request,
											.done(
													function(data, textStatus,
															jqXHR) {
														$form
																.find(
																		'.subscribe')
																.html(
																		'Payment successful <i class="fa fa-check"></i>');
													})
											.fail(
													function(jqXHR, textStatus,
															errorThrown) {
														$form
																.find(
																		'.subscribe')
																.html(
																		'There was a problem')
																.removeClass(
																		'success')
																.addClass(
																		'error');
														/* Show Stripe errors on the form */
														$form
																.find(
																		'.payment-errors')
																.text(
																		'Try refreshing the page and trying again.');
														$form
																.find(
																		'.payment-errors')
																.closest('.row')
																.show();
													});
								}
							});
		}
		/* Fancy restrictive input formatting via jQuery.payment library*/
		$('input[name=cardNumber]').payment('formatCardNumber');
		$('input[name=cardCVC]').payment('formatCardCVC');
		$('input[name=cardExpiry').payment('formatCardExpiry');

		/* Form validation using Stripe client-side validation helpers */
		jQuery.validator.addMethod("cardNumber", function(value, element) {
			return this.optional(element)
					|| Stripe.card.validateCardNumber(value);
		}, "Please specify a valid credit card number.");

		jQuery.validator.addMethod("cardExpiry", function(value, element) {
			/* Parsing month/year uses jQuery.payment library */
			value = $.payment.cardExpiryVal(value);
			return this.optional(element)
					|| Stripe.card.validateExpiry(value.month, value.year);
		}, "Invalid expiration date.");

		jQuery.validator.addMethod("cardCVC", function(value, element) {
			return this.optional(element) || Stripe.card.validateCVC(value);
		}, "Invalid CVC.");

		validator = $form.validate({
			rules : {
				cardNumber : {
					required : true,
					cardNumber : true
				},
				cardExpiry : {
					required : true,
					cardExpiry : true
				},
				cardCVC : {
					required : true,
					cardCVC : true
				}
			},
			highlight : function(element) {
				$(element).closest('.form-control').removeClass('success')
						.addClass('error');
			},
			unhighlight : function(element) {
				$(element).closest('.form-control').removeClass('error')
						.addClass('success');
			},
			errorPlacement : function(error, element) {
				$(element).closest('.form-group').append(error);
			}
		});

		paymentFormReady = function() {
			if ($form.find('[name=cardNumber]').hasClass("success")
					&& $form.find('[name=cardExpiry]').hasClass("success")
					&& $form.find('[name=cardCVC]').val().length > 1) {
				return true;
			} else {
				return false;
			}
		}

		$form.find('.subscribe').prop('disabled', true);
		var readyInterval = setInterval(function() {
			if (paymentFormReady()) {
				$form.find('.subscribe').prop('disabled', false);
				clearInterval(readyInterval);
			}
		}, 250);

		function ShowSuccess() {
			alert("訂單成立，請去會員專區查詢!");
		}
	</script>

</html>