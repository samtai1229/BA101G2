<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
session.setMaxInactiveInterval(2);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>login</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/backend/backendlogin.css">
</head>
<body background="<%=request.getContextPath()%>/img/header.jpg"  style= margin:0px;padding:0px;background-size:cover;>


<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="pr-wrap">
                <div class="pass-reset">
                    <label>
                        Enter the email you signed up with</label>
                    <input type="email" placeholder="Email" />
                    <input type="submit" value="Submit" class="pass-reset-submit btn btn-success btn-sm" />
                </div>
            </div>
            <div class="wrap">
                <p class="form-title">
                    Sign In</p>
                <form class="login" action="<%=request.getContextPath()%>/loginhandler" method="post">
                <input type="text" placeholder="Username" name="account" />
                <input type="password" placeholder="Password" name="password" />
                <input type="submit" value="Sign In" class="btn btn-success btn-sm" />
                <div class="remember-forgot">
                    <div class="row">
                        <div class="col-md-6">
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox" />
                                    Remember Me
                                </label>
                            </div>
                        </div>
                    </div>
                </div>
                </form>
            </div>
        </div>
    </div>
    </div>































<!-- ------------------------------------------------------------------------------------------------- -->
<%-- <center> --%>
<%-- 	<form action="<%=request.getContextPath()%>/loginhandler" method="post"> --%>
<!-- 			<table border=1> -->
<!-- 				<tr> -->
<!-- 					<td colspan=2> -->
<!-- 						<p align=center> -->
<!-- 							<b>後端管理系統</b><br>  -->
<!-- 					</td> -->
<!-- 				</tr> -->

<!-- 				<tr> -->
<!-- 					<td> -->
<!-- 						<p align=right> -->
<!-- 							<b>account:</b> -->
<!-- 					</td> -->
<!-- 					<td> -->
<!-- 						<p> -->
<!-- 							<input type=text name="account" value="fat" size=15> -->
<!-- 					</td> -->
<!-- 				</tr> -->

<!-- 				<tr> -->
<!-- 					<td> -->
<!-- 						<p align=right> -->
<!-- 							<b>password:</b> -->
<!-- 					</td> -->
<!-- 					<td> -->
<!-- 						<p> -->
<!-- 							<input type=password name="password" value="j12345" size=15> -->
<!-- 					</td> -->
<!-- 				</tr> -->


<!-- 				<tr> -->
<!-- 					<td colspan=2 align=center> -->
						
<!-- 							<input type="submit" value="ok"> -->
						
<!-- 					</td> -->
<!-- 				</tr> -->
<!-- 			</table> -->
<!-- 	</form> -->
<%-- </center> --%>
		<script src="https://code.jquery.com/jquery.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
		<script src="backendlogin.js"></script>
		 <script type="text/javascript">
var Msg ='<%=session.getAttribute("getAlert")%>';
    if (Msg != "null") {
 function alertName(){
 alert("輸入帳號/密碼錯誤");
 } 
 }
 </script>
 <script type="text/javascript"> window.onload = alertName; </script>
 
</body>
</html>