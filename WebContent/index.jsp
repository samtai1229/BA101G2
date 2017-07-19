<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.location.model.*"%>
<%@ page import="com.news.model.*"%>
<%-- �����m�߱ĥ� EL ���g�k���� --%>

<%
	LocationService locSvc = new LocationService();
	NewsService newSvc = new NewsService();
	List<NewsVO> listnormal = newSvc.getAllnormal();
	List<NewsVO> newslist = newSvc.getAll();
	List<LocationVO> list = locSvc.getAll();
	String memno = (String)session.getAttribute("memno");
	String memname = (String)session.getAttribute("memname");
	String error = (String)request.getAttribute("error");
	System.out.println("index.jsp-Session memno:"+memno+", memename: "+memname);
	pageContext.setAttribute("list",list);
	pageContext.setAttribute("memno",memno);
	pageContext.setAttribute("memname",memname);
	pageContext.setAttribute("newslist", newslist);
	pageContext.setAttribute("error", error);
	pageContext.setAttribute("listnormal",listnormal);
	
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta name="generator"
	content="HTML Tidy for HTML5 (experimental) for Windows https://github.com/w3c/tidy-html5/tree/c63cc39" />
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta name="description" content="" />
<meta name="author" content="" />
<title>AutoBike Homepage</title>
<link href="<%=request.getContextPath()%>/css/news.css" rel="stylesheet" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
<!-- Bootstrap Core CSS -->
<link href="<%=request.getContextPath()%>/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
<!-- Custom Fonts -->
<link href="<%=request.getContextPath()%>/vendor/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css" />
<link href="https://fonts.googleapis.com/css?family=Montserrat:400,700"
	rel="stylesheet" type="text/css" />
<link href='https://fonts.googleapis.com/css?family=Kaushan+Script'
	rel='stylesheet' type='text/css' />
<link
	href='https://fonts.googleapis.com/css?family=Droid+Serif:400,700,400italic,700italic'
	rel='stylesheet' type='text/css' />
<link
	href='https://fonts.googleapis.com/css?family=Roboto+Slab:400,100,300,700'
	rel='stylesheet' type='text/css' />
<!-- Theme CSS -->
<link href="<%=request.getContextPath()%>/css/gallery.css" rel="stylesheet" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/owl.carousel.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/owl.theme.default.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/magnific-popup.css">
<!-- Theme Style -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
<link href="<%=request.getContextPath()%>/css/agency.css" rel="stylesheet" />
<link href="<%=request.getContextPath()%>/css/agency.min.css" rel="stylesheet" />
<link href="<%=request.getContextPath()%>/css/daterangepicker.css" rel="stylesheet" />
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js" integrity="sha384-0s5Pv64cNZJieYFkXYOTId2HMA2Lfb6q2nAcx2n0RTLUnCAoTTsS0nKEO27XyKcY" crossorigin="anonymous"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js" integrity="sha384-ZoaMbDF+4LeFxg6WdScQ9nnR1QC2MIRxA1O9KWEXQwns1G8UNyIEZIQidzb0T1fo" crossorigin="anonymous"></script>
    <![endif]-->


<link rel='stylesheet prefetch' href='https://fonts.googleapis.com/css?family=Open+Sans:600'>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/style_login.css">

<style>

 #demo{
 width:277px;
 }
 </style>
</head>

<body id="page-top" class="index">
	<!-- �|���n�J�O�c -->
	<div class="modal fade" id="modal-id">
		<div class="modal-dialog">
			<div >
			<!-- 	<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title">�n�J�|��</h4>
				</div> -->
				<div class="modal-body">
				   <button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					

										<div class="login-wrap">
        <div class="login-html" style="width:100% height:1000px">
            <input id="tab-1" type="radio" name="tab" class="sign-in" checked>
            <label for="tab-1" class="tab">�n�J</label>
            <input id="tab-2" type="radio" name="tab" class="sign-up">
            <label for="tab-2" class="tab">���U</label>
            <div class="login-form">
            <form method="post" action="<%=request.getContextPath()%>/backend/member/member.do">
                <div class="sign-in-htm">
                    <div class="group">
                        <label for="acc" class="label">�b��</label>
                        <input id="acc" name="acc" type="text" class="input">
                    </div>
                    <div class="group">
                        <label for="pwd" class="label">�K�X</label>
                        <input id="pwd" name="pwd" type="password" class="input" data-type="password">
                    </div>
                    <div class="group">
                        <input id="check" name="check" type="checkbox" class="check" checked>
                        <label for="check"><span class="icon"></span>�O���</label>
                    </div>
                    <div class="group">
                        <input type="submit" class="button" value="Sign In">
                        <input type="hidden" name="action" value="login">
                        <input type="hidden" name="location" value="<%=request.getServletPath()%>">
                    </div>
                    <div class="hr"></div>
                    <div class="foot-lnk">
                        <a href="#forgot">�ѰO�K�X?</a>
                    </div>
                   
                </div>
				</form>
                <form method="post" action="<%=request.getContextPath()%>/backend/member/member.do">
                <div class="sign-up-htm">
                    <div class="group">
                        <label for="new_acc" class="label">�b��</label>
                        <input name="new_acc" type="text" class="input">
                    </div>
                    <div class="group">
                        <label for="new_pwd" class="label">�K�X</label>
                        <input name="new_pwd" type="password" class="input" data-type="password">
                    </div>
                    <div class="group">
                        <label for="pass" class="label">�T�{�K�X</label>
                        <input name="pass" type="password" class="input" data-type="password">
                    </div>
                    <div class="group">
                        <label for="mail" class="label">�H�c</label>
                        <input id="mail" name="mail" type="email" class="input">
                    </div>
                    <div class="group">
                        <input type="submit" class="button" value="Sign Up">
                        <input type="hidden" name="action" value="register">
                        <input type="hidden" name="location" value="<%=request.getServletPath()%>">
                    </div>
                    <div class="hr"></div>
                    <div class="foot-lnk">
                        <label for="tab-1">�w�g�O�|��?</label>
                    </div>
                </div>
                </form>
            </div>
        </div>
    </div>

				</div>
			</div>
		</div>
		</div>
	<!-- Navigation -->
	<nav id="mainNav" class="navbar navbar-default navbar-custom navbar-fixed-top">
        <div class="container-fluid">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header page-scroll">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only">Toggle navigation</span> Menu <i class="fa fa-bars"></i>
                </button>
                   <a class="navbar-brand page-scroll" href="#page-top">AutoBike</a>
            </div>

			<!-- Collect the nav links, forms, and other content for toggling -->
			 <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
               
             
<!-- search bar  -->					
						<form  method="post" action="<%=request.getContextPath()%>/backend/rent_ord/rentOrd.do" class="navbar-form navbar-left" role="search">
							<input id="demo" name="dayrange" class="form-control" type="text" style="background-color: transparent; color:#fff;" >							
						<%-- 	<jsp:useBean id="locSvc" scope="page" class="com.location.model.LocationService"/> --%>
							<input type="hidden" name="action" value="quick_search">
							<input style="background-color: transparent; color:#fff;" type="submit" class="form-control" value="�ֳt�d��">
						</form>

              					 <ul class="nav navbar-nav navbar-right">
              	                    <li class="hidden">
              	                        <a href="#page-top"></a>
              	                    </li>
              					<li><a class="page-scroll" href="<%=request.getContextPath()%>/frontend/rental_form/rental_category.jsp"><i class="glyphicon glyphicon-heart"></i>�ڭn����</a></li>
              						<li><a class="page-scroll" href="#news"><i
              								class="glyphicon glyphicon-alert"></i>�̷s����</a></li>
              						<li><a class="page-scroll" href="#board"><i
              								class="fa fa-comments-o"></i>�d���O</a></li>
              						<li><a class="page-scroll" href="#loc"><i
              								class="fa fa-search"></i>�A�Ⱦ��I</a></li>
              						<li><a href="<%=request.getContextPath()%>/frontend/second_order/listAllSecond.jsp"><i class="fa fa-shopping-cart"></i>�G�⨮�ʶR</a>
              						</li>
              						
              						<c:if test="${not empty memno}">	
              						<li><a href="#">�w��A${(memname == null) ? '�|��':memname}</a></li>	
              							<li><a href="<%=request.getContextPath()%>/backend/member/member.do?action=getOne_For_Enter&memid=${memno}"><b>�|���M��</b></a></li>
              									<li>
              										<a href="<%=request.getContextPath()%>/backend/member/member.do?action=logout"
              								data-toggle="modal"><i class="glyphicon glyphicon-user"></i>�n�X</a>
              									</li>
              			
              						
              						</c:if>
              						
              						<c:if test="${ empty memno}">
              							<li>
              								<a href="#modal-id"
              								data-toggle="modal"><i class="glyphicon glyphicon-user"></i>�|���n�J</a>
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
		
		<div class="container">
		
			<div class="intro-text">
				<div class="intro-lead-in">Welcome To Autobike!</div>
				<div class="intro-heading">The Best Bike For You!</div>
				
				<!-- 				<a href="rent.html" class="page-scroll btn btn-xl">�I�گ���</a> -->
			</div>

		</div>
	</header>
	<!-- news Section -->
	<section id="news">
  <div class="container-fluid">
   <div class="row ">
    <div class="col-xs-12 col-sm-12 text-center">
     <h2 class="section-heading">�̷s����</h2>

    </div>

   </div>
 </div>
<div style= margin-right:45px;>

   <div class="container">
  <div class="row">
   <div class="col-xs-12 col-sm-4">
    <div class="container">
     <div class="row">
      <div class="col-sm-4">
       <div class="news">
        <div class="img-figure">
         <img src=<%=request.getContextPath()%>/backend/news/newsread.do?newsno=<%=listnormal.get(0).getNewsno()%>
         class="img-responsive"  alt="Responsive image">
        </div>

        <div class="title">
         <h1><%=listnormal.get(0).getTitle()%></h1>
        </div>
        <p class="description">
         <%=listnormal.get(0).getCont()%>
        </p>

        <p class="more">
         <a href="#">read more</a><i class="fa fa-angle-right"
          aria-hidden="true"></i>
        </p>
       </div>


      </div>
     </div>
    </div>
   </div>
   <div class="col-xs-12 col-sm-4">
    <div class="container">
     <div class="row">
      <div class="col-sm-4">
       <div class="news">
        <div class="img-figure">
         <img src=<%=request.getContextPath()%>/backend/news/newsread.do?newsno=<%=listnormal.get(1).getNewsno()%> class="img-responsive">
        </div>

        <div class="title">
         <h1><%=listnormal.get(1).getTitle()%></h1>
        </div>
        <p class="description"><%=listnormal.get(1).getCont()%></p>

        <p class="more">
         <a href="#">read more</a><i class="fa fa-angle-right"
          aria-hidden="true"></i>
        </p>
       </div>


      </div>
     </div>
    </div>
   </div>
   <div class="col-xs-12 col-sm-4">

    
     
       <div class="news">
        <div class="img-figure">
         <img src=<%=request.getContextPath()%>/backend/news/newsread.do?newsno=<%=listnormal.get(2).getNewsno()%> 
         class="img-responsive">
        </div>

        <div class="title">
         <h1><%=listnormal.get(2).getTitle()%></h1>
        </div>
        <p class="description"><%=listnormal.get(2).getCont()%></p>

        <p class="more">
         <a href="#">read more</a><i class="fa fa-angle-right"
          aria-hidden="true"></i>
        </p>
       </div>


      
     
   
   </div>
  </div>
 </div>
 </div>
 </section>
	<!--  <section id="board" class="col-xs-12 col-sm-12">
      
            <div class="row">
                <br/>
                <div class="col-xs-12 col-sm-12 text-center">
                    <h2 class="intro-lead-in">�d���O</h2>
                    <h3 class="intro-heading">�O���P�ʪ��C�@��</h3>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-12 col-sm-4 aa">
                    <br/>
                    <div class="col-xs-12 col-sm-12">
                        <a href="leave_message_Page.html"> <img src="https://api.fnkr.net/testimg/450x200/00CED1/FFF/?text=img+placeholder" class="img-responsive img-circle  img-centered"></a>
                    </div>
                    <div class="col-xs-12 col-sm-12  text-center">
                        Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
                    </div>
                </div>
                <div class="col-xs-12 col-sm-4 aa ">
                    <br/>
                    <div class="col-xs-12 col-sm-12">
                        <a href="leave_message_Page.html"><img src="https://api.fnkr.net/testimg/450x200/00CED1/FFF/?text=img+placeholder" class="img-responsive img-circle  img-centered"></a>
                    </div>
                    <div class="col-xs-12 col-sm-12  text-center">
                        Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
                    </div>
                </div>
                <div class="col-xs-12 col-sm-4 aa ">
                    <br/>
                    <div class="col-xs-12 col-sm-12">
                        <a href="contact.html"> <img src="https://api.fnkr.net/testimg/450x200/00CED1/FFF/?text=img+placeholder" class="img-responsive img-circle  img-centered"></a>
                    </div>
                    <div class="col-xs-12 col-sm-12 text-center">
                        Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
                    </div>
                </div>
            </div>
        
    </section> -->
	<section id="board">
		<div class="container-fluid">
			<div class="row">
				<div class="col-xs-12 col-sm-12 text-center">
					<h2 class="section-heading">�d���O</h2>
				<h4>	<a href="#" id="general">Enter MESSAGE BOARD</a></h4>


				</div>
			</div>
			<div class="row text-center">
				<div class="col-md-4">
					<span class="fa-stack fa-4x"> <i
						class="fa fa-circle fa-stack-2x text-primary"></i> <i
						class="fa fa-shopping-cart fa-stack-1x fa-inverse"></i>
					</span>
					<h4 class="service-heading">E-Commerce</h4>
					<p class="text-muted">Lorem ipsum dolor sit amet, consectetur
						adipisicing elit. Minima maxime quam architecto quo inventore
						harum ex magni, dicta impedit.</p>
				</div>
				<div class="col-md-4">
					<span class="fa-stack fa-4x"> <i
						class="fa fa-circle fa-stack-2x text-primary"></i> <i
						class="fa fa-laptop fa-stack-1x fa-inverse"></i>
					</span>
					<h4 class="service-heading">Responsive Design</h4>
					<p class="text-muted">Lorem ipsum dolor sit amet, consectetur
						adipisicing elit. Minima maxime quam architecto quo inventore
						harum ex magni, dicta impedit.</p>
				</div>
				<div class="col-md-4">
					<span class="fa-stack fa-4x"> <i
						class="fa fa-circle fa-stack-2x text-primary"></i> <i
						class="fa fa-lock fa-stack-1x fa-inverse"></i>
					</span>
					<h4 class="service-heading">Web Security</h4>
					<p class="text-muted">Lorem ipsum dolor sit amet, consectetur
						adipisicing elit. Minima maxime quam architecto quo inventore
						harum ex magni, dicta impedit.</p>
				</div>
			</div>
		</div>
	</section>
	<aside id="loc">
		<div class="container-fluid bg-light-gray">
		 
			<div class="row">
				<br />
				<div class="col-xs-12 col-sm-12 text-center">
					<h2>�ڭ̪����I</h2>
					<h3
							style="color: blue" class="section-subheading"><a href="leave_message_Page.html" id="general">
							See More
							</a></h3>
				</div>
			</div>
		   	
			<div class="row">
			 <c:forEach var="locVO" items="${list}" >
				<div class="col-xs-12 col-sm-2">
					<div class="team-member">
						<a href="<%=request.getContextPath()%>/frontend/location/location.jsp"> <img src='<%=request.getContextPath()%>/frontend/location/locReader.do?locno=${locVO.locno}'
							class="img-responsive img-circle" alt="" /></a>
						<h4>${locVO.locname}</h4>
						<h5>address :${locVO.addr} phone :${locVO.tel}</h5>
					</div>
				</div>
			</c:forEach>
			</div>
			
		</div>
		<!-- <div class="row">
                <div class="col-lg-8 col-lg-offset-2 text-center">
                    <p class="large text-muted">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aut eaque, laboriosam veritatis, quos non quis ad perspiciatis, totam corporis ea, alias ut unde.</p>
                </div>-->
		
	</aside>
	<footer>
		<div class="col-xs-12 col-sm-12">
			<span>�p����T</span>
		</div>
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
	<!-- jQuery -->
	<!--  <script src="vendor/jquery/jquery.min.js"></script> -->
	<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
	<script type="text/javascript"
		src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
	<!-- Bootstrap Core JavaScript -->
	<script src="<%=request.getContextPath()%>/vendor/bootstrap/js/bootstrap.min.js"></script>
	<!-- Plugin JavaScript -->
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.3/jquery.easing.min.js"
		integrity="sha384-mE6eXfrb8jxl0rzJDBRanYqgBxtJ6Unn4/1F7q4xRRyIw7Vdg9jP4ycT7x1iVsgb"
		crossorigin="anonymous"></script>
	<!-- Owl carousel -->
	<script src="<%=request.getContextPath()%>/js/owl.carousel.min.js"></script>
	<!-- Waypoints -->
	<!-- Magnific Popup -->
	<!-- Main JS -->
	<script src="<%=request.getContextPath()%>/js/main.js"></script>
	<!-- Contact Form JavaScript -->
	<script src="<%=request.getContextPath()%>/js/jqBootstrapValidation.js"></script>
	<script src="<%=request.getContextPath()%>/js/contact_me.js"></script>
	<!-- Theme JavaScript -->
	<script src="<%=request.getContextPath()%>/js/jquery.magnific-popup.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/agency.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/moment.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/daterangepicker.js"></script>
</body>

    <script>
    $('#demo').daterangepicker({
        "timePicker": true,
        "timePicker24Hour": true,
        "timePickerIncrement": 30,
        "startDate": moment().add(3, 'days'),
        "endDate": moment().add(6, 'days'),
        "opens": "center",
        "applyClass": "btn-success",
        "cancelClass": "btn-primary",
        "minDate": moment().add(2, 'days'),
        "maxDate": moment().add(60, 'days'),
        locale: {
            format: 'MM/DD/YYYY H:mm'
        }
    }, function(start, end, label) {
      console.log("New date range selected: ' + start.format('MM/DD/YYYY H:mm') + ' to ' + end.format('MM/DD/YYYY H:mm') + ' (predefined range: ' + label + ')");
    });
    
    
    
  
    </script>
    <script type="text/javascript">
      

    function doFirst()
    {
    	var error = "${error}";
        console.log(error);
    	if(error)
    	{
    		alert(error+"�A�Э��s�n�J");
    		$("#modal-id").modal();
    	}
    	
    	
    	
    }
    window.addEventListener('load',doFirst,false);
    
    
    
    </script>
    
    

</html>