<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.motor.model.*" %>
<%@ page import="com.motor_model.model.*" %>
<%@ page import="com.member.model.*" %>
<%@ page import="java.util.*" %>
<% 
   String motno = (String)request.getAttribute("motno");
   MotorService mSvc = new MotorService();
   MotorVO motorVO = mSvc.findByPK(motno);
   MotorModelService  mmSvc = new MotorModelService();
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
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
		<title>Title Page</title>
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
		<!--[if lt IE 9]>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js"></script>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/respond.js/1.4.2/respond.min.js"></script>
		<![endif]-->
		  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
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
    box-shadow: inset 0 1px 1px rgba(0,0,0,0.075),0 0 8px rgba(255,0,0,0.6);
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



		</style>
	</head>
	<body >
		
	<nav class="navbar navbar-default" role="navigation">
	 	<div class="container">
	 		<div class="navbar-header">
	 			<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
	 				<span class="sr-only">選單切換</span>
	 				<span class="icon-bar"></span>
	 				<span class="icon-bar"></span>
	 				<span class="icon-bar"></span>
	 			</button>
	 			<a class="navbar-brand" href="#">付款區</a>
	 		</div>
	 		
	 		<!-- 手機隱藏選單區 -->
	 		<div class="collapse navbar-collapse navbar-ex1-collapse">
	 			<!-- 左選單 -->
	 			<ul class="nav navbar-nav">
	 				<li class="active"><a href="#">信用卡付款</a></li>
	 				<li><a href="<%=request.getContextPath()%>/index.jsp">回首頁</a></li>
	 			</ul>
	 		
	 			
	 		
	 			<!-- 右選單 -->
	 			<ul class="nav navbar-nav navbar-right">
	 				<li><a href="#">${memVO.memname} 您好</a></li>
	 				<li><a href="<%=request.getContextPath()%>/backend/member/member.do?action=logout"
              								data-toggle="modal">登出</a></li>
	 			</ul>
	 		</div>
	 		<!-- 手機隱藏選單區結束 -->
	 	</div>
	 </nav> 
  
 <form action="<%=request.getContextPath()%>/frontend/second_order/SecOrd.do">
<div class="col-xs-12 col-sm-12">
    <div class="row">
        <!-- You can make it whatever width you want. I'm making it full width
             on <= small devices and 4/12 page width on >= medium devices -->
        <div class="col-xs-12 col-md-4">
        
        
            <!-- CREDIT CARD FORM STARTS HERE -->
            <div class="panel panel-default credit-card-box">
                <div class="panel-heading display-table" >
                    <div class="row display-tr" >
                        <h3 class="panel-title display-td" >Payment Details</h3>
                        <div class="display-td" >                            
                            <img class="img-responsive pull-right" src="http://i76.imgup.net/accepted_c22e0.png">
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
                                        <input 
                                            type="tel"
                                            class="form-control"
                                            name="cardNumber"
                                            placeholder="Valid Card Number"
                                            autocomplete="cc-number"
                                            required autofocus 
                                        />
                                        <span class="input-group-addon"><i class="fa fa-credit-card"></i></span>
                                    </div>
                                </div>                            
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-7 col-md-7">
                                <div class="form-group">
                                    <label for="cardExpiry"><span class="hidden-xs">卡片到期日</span><span class="visible-xs-inline">EXP</span> DATE</label>
                                    <input 
                                        type="tel" 
                                        class="form-control" 
                                        name="cardExpiry"
                                        placeholder="MM / YY"
                                        autocomplete="cc-exp"
                                        required 
                                    />
                                </div>
                            </div>
                            <div class="col-xs-5 col-md-5 pull-right">
                                <div class="form-group">
                                    <label for="cardCVC">CV CODE</label>
                                    <input 
                                        type="tel" 
                                        class="form-control"
                                        name="cardCVC"
                                        placeholder="CVC"
                                        autocomplete="cc-csc"
                                        required
                                    />
                                </div>
                            </div>
                        </div>
<!--                         <div class="row"> -->
<!--                             <div class="col-xs-12"> -->
<!--                                 <div class="form-group"> -->
<!--                                     <label for="couponCode">COUPON CODE</label> -->
<!--                                     <input type="text" class="form-control" name="couponCode" /> -->
<!--                                 </div> -->
<!--                             </div>                         -->
<!--                         </div> -->
                        <div class="row">
                            <div class="col-xs-12">
                           
                                <input class="subscribe btn btn-success btn-lg btn-block" type="submit" onclick="ShowSuccess();" value="送出">
                          
                            </div>
                        </div>
                        <div class="row" style="display:none;">
                            <div class="col-xs-12">
                                <p class="payment-errors"></p>
                            </div>
                        </div>
                     <input type="hidden" name="memno" value="${memVO.memno}">
                     <input type="hidden" name="motno" value="${motorVO.motno}">
                     <input type="hidden" name="modtype" value="${mm.modtype}">
                     <input type="hidden" name="location" value="<%=request.getServletPath()%>">
                     <input type="hidden" name="action" value="buildOrder">
                    </form>
                </div>
            </div>            
            <!-- CREDIT CARD FORM ENDS HERE -->
            
            
        </div>   
          </form>         
        <div class="col-xs-12 col-sm-8">
        
          <table  class="table table-hover" border='1' bordercolor='#CCCCFF' width='100%' height="288px" >
	<tr>
<!-- 		<th>車輛編號</th> -->
<!-- 		<th>車型編號</th> -->
<!-- 		<th>車牌號碼</th> -->
		<th style="text-align:center">圖片</th>
		<th style="text-align:center">廠牌</th>
		<th style="text-align:center">型號</th>
		<th style="text-align:center">排氣量</th>
		<th style="text-align:center">介紹</th>
<!-- 		<th>引擎編號</th> -->
<!-- 		<th>出廠日期</th> -->
<!-- 		<th>里程數</th> -->
		<th style="text-align:center">售價(NTD)</th>
<!-- 		<th>據點</th> -->
<!-- 		<th>status</th> -->
<!-- 		<th>note</th> -->
	</tr>
<%-- 					begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>" --%>

		<tr  align='center' valign='middle'>
		
<%-- 			<td>${motorVO.motno}</td> --%>
<%-- 			<td>${motorVO.modtype}</td> --%>
<%-- 			<td>${motorVO.plateno}</td> --%>
			<td><img src="<%=request.getContextPath()%>/backend/motor_model/mmReader.do?modtype=${motorVO.modtype}" width='200' height='180'></td>
           
<%-- 			<td>${motorVO.engno}</td> --%>
<%-- 			<td><fmt:formatDate value="${motorVO.manudate}" pattern="yyyy年MM月dd日"/></td>		 --%>
<%-- 			<td>${motorVO.mile}</td> --%>
		
			   <td>${mm.brand}</td>	
			   <td>${mm.name}</td>	
			   <td>${mm.displacement}</td>
			   <td><span>${mm.intro}</span></td>	
			   <td>${mm.saleprice}</td>	
			
		</tr>


</table>
        
        
        
        </div>
      
        
    </div>
</div>




		
		<script src="https://code.jquery.com/jquery.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.13.1/jquery.validate.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery.payment/1.2.3/jquery.payment.min.js"></script>

<!-- If you're using Stripe for payments -->
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
    $form.find('.subscribe').html('Validating <i class="fa fa-spinner fa-pulse"></i>').prop('disabled', true);

    var PublishableKey = 'pk_test_6pRNASCoBOKtIshFeQd4XMUh'; // Replace with your API publishable key
    Stripe.setPublishableKey(PublishableKey);
    
    /* Create token */
    var expiry = $form.find('[name=cardExpiry]').payment('cardExpiryVal');
    var ccData = {
        number: $form.find('[name=cardNumber]').val().replace(/\s/g,''),
        cvc: $form.find('[name=cardCVC]').val(),
        exp_month: expiry.month, 
        exp_year: expiry.year
    };
    
    Stripe.card.createToken(ccData, function stripeResponseHandler(status, response) {
        if (response.error) {
            /* Visual feedback */
            $form.find('.subscribe').html('Try again').prop('disabled', false);
            /* Show Stripe errors on the form */
            $form.find('.payment-errors').text(response.error.message);
            $form.find('.payment-errors').closest('.row').show();
        } else {
            /* Visual feedback */
            $form.find('.subscribe').html('Processing <i class="fa fa-spinner fa-pulse"></i>');
            /* Hide Stripe errors on the form */
            $form.find('.payment-errors').closest('.row').hide();
            $form.find('.payment-errors').text("");
            // response contains id and card, which contains additional card details            
            console.log(response.id);
            console.log(response.card);
            var token = response.id;
            // AJAX - you would send 'token' to your server here.
            $.post('/account/stripe_card_token', {
                    token: token
                })
                // Assign handlers immediately after making the request,
                .done(function(data, textStatus, jqXHR) {
                    $form.find('.subscribe').html('Payment successful <i class="fa fa-check"></i>');
                })
                .fail(function(jqXHR, textStatus, errorThrown) {
                    $form.find('.subscribe').html('There was a problem').removeClass('success').addClass('error');
                    /* Show Stripe errors on the form */
                    $form.find('.payment-errors').text('Try refreshing the page and trying again.');
                    $form.find('.payment-errors').closest('.row').show();
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
    return this.optional(element) || Stripe.card.validateCardNumber(value);
}, "Please specify a valid credit card number.");

jQuery.validator.addMethod("cardExpiry", function(value, element) {    
    /* Parsing month/year uses jQuery.payment library */
    value = $.payment.cardExpiryVal(value);
    return this.optional(element) || Stripe.card.validateExpiry(value.month, value.year);
}, "Invalid expiration date.");

jQuery.validator.addMethod("cardCVC", function(value, element) {
    return this.optional(element) || Stripe.card.validateCVC(value);
}, "Invalid CVC.");

validator = $form.validate({
    rules: {
        cardNumber: {
            required: true,
            cardNumber: true            
        },
        cardExpiry: {
            required: true,
            cardExpiry: true
        },
        cardCVC: {
            required: true,
            cardCVC: true
        }
    },
    highlight: function(element) {
        $(element).closest('.form-control').removeClass('success').addClass('error');
    },
    unhighlight: function(element) {
        $(element).closest('.form-control').removeClass('error').addClass('success');
    },
    errorPlacement: function(error, element) {
        $(element).closest('.form-group').append(error);
    }
});

paymentFormReady = function() {
    if ($form.find('[name=cardNumber]').hasClass("success") &&
        $form.find('[name=cardExpiry]').hasClass("success") &&
        $form.find('[name=cardCVC]').val().length > 1) {
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

  function ShowSuccess()
  {
	  alert("訂單成立，請去會員專區查詢!");
  }


			
		</script>
	</body>
</html>