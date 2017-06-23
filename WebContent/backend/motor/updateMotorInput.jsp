<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.motor.model.*"%>
<%
	MotorVO motorVO = (MotorVO) request.getAttribute("motorVO"); //MotorServlet.java (Controller), 存入req的motorVO物件 (包括幫忙取出的motorVO, 也包括輸入資料錯誤時的motorVO物件)
	String[] statusArray = { "unleasable", "leasable", "reserved", "occupied", "dispatching", "seconsale",
			"secpause", "secreserved", "secsaled", "other" };
	request.setAttribute("statusArray", statusArray);
%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>機車資料修改 - updateMotorInput.jsp</title>

<meta name="keywords" content="">
<!-- CSS -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
<link
	href="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.3.1/css/bootstrap-combined.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/backend/motor/js/updateMotorInput_css.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/backend/motor/js/bootstrap-datetimepicker.min.css">

<!-- Javascript -->
<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<script src="https://code.jquery.com/jquery.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script
	src="${pageContext.request.contextPath}/backend/motor/js/updateMotorInput_js.js"></script>
<script
	src="${pageContext.request.contextPath}/backend/motor/js/bootstrap-datetimepicker.js"></script>

<div id="popupcalendar" class="text"></div>
</head>




<body>
	<nav class="navbar navbar-default" role="navigation">
		<!-- navlogo區 -->
		<a class="navbar-brand" href="#" id="navA">AUTOBIKE</a>
		<!-- nav左選單 -->
		<ul class="nav navbar-nav">
			<li><a href="#" id="navA">後端管理系統</a></li>
			<!-- nav時鐘 -->
			<iframe scrolling="no" frameborder="no" clocktype="html5"
				style="overflow: hidden; border: 0; margin: 0; padding: 0; width: 120px; height: 40px;"
				src="http://www.clocklink.com/html5embed.php?clock=004&timezone=CCT&color=yellow&size=120&Title=&Message=&Target=&From=2017,1,1,0,0,0&Color=yellow">
			</iframe>

		</ul>
		<!-- nav右選單 -->
		<ul class="nav navbar-nav navbar-right">
			<li><a href="#" id="navA">HI EMT9413 歡迎回來為公司奉獻</a></li>
			<li><a href="#" id="navA"><i
					class="glyphicon glyphicon-wrench"></i>個人設定</a></li>
			<li><a href="#" id="navA"><i
					class="glyphicon glyphicon-log-out"></i>登出</a></li>
		</ul>
	</nav>
	<div class="col-xs-12 col-sm-2 leftBar">
		<img id="logo"
			src="${pageContext.request.contextPath}/backend/images/logo.jpg">
		<button class="accordion accordionMenu">總部管理系統</button>
		<div class="btn-group-vertical">
			<a class="btn btn-default"
				href="${pageContext.request.contextPath}/backend/motor/motorMgmtHqSelectPage.jsp"
				role="button">車輛管理</a> <a class="btn btn-default" href="#"
				role="button">車輛調度</a> <a class="btn btn-default" href="#"
				role="button">租賃單管理</a> <a class="btn btn-default" href="#"
				role="button">裝備管理</a> <a class="btn btn-default" href="#"
				role="button">裝備調度</a> <a class="btn btn-default" href="#"
				role="button">據點管理</a>
		</div>
		<button class="accordion accordionMenu">據點管理系統</button>
		<div class="btn-group-vertical">
			<a class="btn btn-default" href="#" role="button">據點車輛管理</a> <a
				class="btn btn-default" href="#" role="button">交車管理</a> <a
				class="btn btn-default" href="#" role="button">還車管理</a> <a
				class="btn btn-default" href="#" role="button">車輛調度申請</a> <a
				class="btn btn-default" href="#" role="button">車輛保養/維修管理</a> <a
				class="btn btn-default" href="#" role="button">據點裝備管理</a> <a
				class="btn btn-default" href="#" role="button">裝備申請</a>
		</div>
		<button class="accordion accordionMenu">二手車管理系統</button>
		<div class="btn-group-vertical">
			<a class="btn btn-default" href="#" role="button">二手車輛管理</a> <a
				class="btn btn-default" href="#" role="button">二手車訂單管理</a> <a
				class="btn btn-default" href="#" role="button">二手車交易管理</a>
		</div>
		<button class="accordion accordionMenu">會員管理系統</button>
		<div class="btn-group-vertical"></div>
		<button class="accordion accordionMenu">活動企劃管理系統</button>
		<div class="btn-group-vertical">
			<a class="btn btn-default" href="#" role="button">推播管理</a> <a
				class="btn btn-default" href="#" role="button">留言版管理</a> <a
				class="btn btn-default" href="#" role="button">最新消息管理</a>
		</div>
		<button class="accordion accordionMenu">後端管理系統</button>
		<div class="btn-group-vertical">
			<a class="btn btn-default" href="#" role="button">後端權限管理</a> <a
				class="btn btn-default" href="#" role="button">推薦景點管理</a> <a
				class="btn btn-default" href="#" role="button">後端登入管理</a>
		</div>
		<div class="btn-group-vertical"></div>
	</div>
	<div class="col-xs-12 col-sm-10 rightHTML">
		<div class="topTitle">
			<h1>車輛資料管理</h1>
		</div>


		<table>
			<tr>
				<td><h3>車輛資料修改 - updateMotorInput.jsp</h3></td>
			</tr>
		</table>

		<h3>資料修改:</h3>
		<%-- 錯誤表列 --%>
		<c:if test="${not empty errorMsgs}">
			<font color='red'>請修正以下錯誤:
				<ul>
					<c:forEach var="message" items="${errorMsgs}">
						<li>${message}</li>
					</c:forEach>
				</ul>
			</font>
		</c:if>
		<div class="container">
			<FORM METHOD="post" ACTION="motor4H.do" name="formUpdate"
				class="form-horizontal">
				<div class="form-group">
					<label class="control-label col-sm-2" for="motno">車輛編號：</label>
					<div class="col-sm-10">
						<p class="form-control">${motorVO.motno}</p>
					</div>
				</div>

				<div class="form-group">
					<label class="control-label col-sm-2" for="plateno">車牌號碼：</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="plateno"
							name="plateno" value="<%=motorVO.getPlateno()%>" />
					</div>
				</div>

				<div class="form-group">
					<label class="control-label col-sm-2" for="engno">引擎編號：</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="engno" name="engno"
							value="<%=motorVO.getEngno()%>" />
					</div>
				</div>

				<div class="form-group">
					<label class="control-label col-sm-2" for="manudate">出廠日期：</label>
						<div id="datetimepicker1" class="col-sm-10 input-append">
							<input readonly data-format="yyyy-MM-dd HH:mm:ss" type="text" class="form-control" name="manudate" value="<%=motorVO.getManudate()%>" />
							<span class="add-on"> <i data-time-icon="icon-time"
								data-date-icon="icon-calendar"> </i>
							</span>
						</div>
				</div>

<!-- 				<div class="form-group"> -->
<!-- 					<label class="control-label col-sm-2" for="manudate">出廠日期：</label> -->
<!-- 					<div class="col-sm-10"> -->
<!-- 						<input class="cal-TextBox form-control" id="manudate" -->
<!-- 							onFocus="this.blur()" size="9" readonly type="text" -->
<%-- 							name="manudate" value="<%=motorVO.getManudate()%>"> <a --%>
<!-- 							class="so-BtnLink" href="javascript:calClick();return false;" -->
<!-- 							onmouseover="calSwapImg('BTN_date', 'img_Date_OVER',true);" -->
<!-- 							onmouseout="calSwapImg('BTN_date', 'img_Date_UP',true);" -->
<!-- 							onclick="calSwapImg('BTN_date', 'img_Date_DOWN');showCalendar('formUpdate','manudate','BTN_date');return false;"> -->
<!-- 							<img align="middle" border="0" name="BTN_date" -->
<!-- 							src="../images/btn_date_up.gif" width="22" height="17" alt="開始日期"> -->
<!-- 						</a> -->
<!-- 					</div> -->
<!-- 				</div> -->

				<div class="form-group">
					<label class="control-label col-sm-2" for="mile">里程數：</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="mile" name="mile"
							value="<%=motorVO.getMile()%>" />
					</div>
				</div>

				<jsp:useBean id="locationSvc" scope="page"
					class="com.location.model.LocationService" />
				<div class="form-group">
					<label class="control-label col-sm-2" for="locno">所在地：</label>
					<div class="col-sm-10">
						<select name="locno" class="form-control">
							<c:forEach var="locationVO" items="${locationSvc.all}">
								<option value="${locationVO.locno}"
									${(motorVO.locno==locationVO.locno)?'selected':'' }>${locationVO.locname}
							</c:forEach>
						</select>
					</div>
				</div>

				<jsp:useBean id="motorModelSvc" scope="page"
					class="com.motor_model.model.MotorModelService" />
				<div class="form-group">
					<label class="control-label col-sm-2" for="mile">車型編號：</label>
					<div class="col-sm-10">
						<select name="modtype" class="form-control" id="modtype">
							<c:forEach var="motorModelVO" items="${motorModelSvc.all}">
								<option value="${motorModelVO.modtype}"
									${(motorVO.modtype==motorModelVO.modtype)?'selected':'' }>${motorModelVO.brand}─
									${motorModelVO.name}
							</c:forEach>
						</select>
					</div>
				</div>

				<jsp:useBean id="motorSvc" scope="page"
					class="com.motor.model.MotorService" />
				<div class="form-group">
					<label class="control-label col-sm-2" for="status">狀態：</label>
					<div class="col-sm-10">
						<select name="status" class="form-control" id="status">
							<option selected value="${motorVO.status}">${motorVO.status}
								<c:forEach var="s" items="${statusArray}">
									<c:if test="${motorVO.status!=s}">
										<option value="${s}">${s}
									</c:if>
								</c:forEach>
						</select>
					</div>
				</div>

				<div class="form-group">
					<label class="control-label col-sm-2" for="note">備註：</label>
					<div class="col-sm-10">
						<textarea class="form-control" id="note" rows="5" cols="70" name="note"><%=motorVO.getNote()%></textarea>
					</div>
				</div>

				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<input type="submit" class="btn btn-default" value="送出修改">
						<input type="hidden" name="action" value="update"> <input
							type="hidden" name="motno" value="<%=motorVO.getMotno()%>">
						<input type="hidden" name="requestURL"
							value="<%=request.getParameter("requestURL")%>">
						<!--接收原送出修改的來源網頁路徑後,再送給Controller準備轉交之用-->
						<input type="hidden" name="whichPage"
							value="<%=request.getParameter("whichPage")%>">
						<!--只用於:listAllMotor.jsp-->
					</div>
				</div>
			</FORM>
		</div>
	</div>
</body>
</html>
</body>
</html>