<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.motor.model.*"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>

<%
	String[] statusArray = {"unleasable", "leasable", "reserved", "occupied", "other"};
	request.setAttribute("statusArray", statusArray);
%>

<jsp:useBean id="now" scope="page" class="java.util.Date" />
<jsp:useBean id="locSvc" scope="page"
	class="com.location.model.LocationService" />
<jsp:useBean id="motorModelSvc" scope="page"
	class="com.motor_model.model.MotorModelService" />

<!DOCTYPE html>
<html lang="">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
<title>據點車輛管理- LocListMotors.jsp</title>

<!-- CSS -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://cdn.datatables.net/1.10.15/css/jquery.dataTables.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/backend/motor/js/locListMotors_css.css">

<!-- JS -->
<script src="https://code.jquery.com/jquery.js"></script>
<link rel="stylesheet"
	href="https://cdn.datatables.net/1.10.15/css/jquery.dataTables.min.css">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script
	src="https://cdn.datatables.net/1.10.15/js/jquery.dataTables.min.js"></script>
<script
	src="${pageContext.request.contextPath}/backend/motor/js/motorMgmtHqSelectPage_js.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$('#locMotorTable').DataTable();
	});
</script>
</head>
<body>
	<nav class="navbar navbar-default" role="navigation">
		<!-- logo區 -->
		<a class="navbar-brand" href="#" id="navA">AUTOBIKE</a>
		<!-- 左選單 -->
		<ul class="nav navbar-nav">
			<li><a href="#" id="navA">後端管理系統</a></li>
			<!-- 時鐘 -->
			<iframe scrolling="no" frameborder="no" clocktype="html5"
				style="overflow: hidden; border: 0; margin: 0; padding: 0; width: 120px; height: 40px;"
				src="http://www.clocklink.com/html5embed.php?clock=004&timezone=CCT&color=yellow&size=120&Title=&Message=&Target=&From=2017,1,1,0,0,0&Color=yellow">
			</iframe>

		</ul>
		<!-- 右選單 -->
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
				role="button">車輛管理</a> <a class="btn btn-default"
				href="${pageContext.request.contextPath}/backend/loc_motor_dispatch/motorDispatchMgmtHq.jsp"
				role="button">車輛調度</a> <a class="btn btn-default" href="#"
				role="button">租賃單管理</a> <a class="btn btn-default"
				href="${pageContext.request.contextPath}/backend/equipment/emtMgmtSelectPage.jsp"
				role="button">裝備管理</a> <a class="btn btn-default" href="#"
				role="button">裝備調度</a> <a class="btn btn-default" href="#"
				role="button">據點管理</a>
		</div>
		<button class="accordion accordionMenu">據點管理系統</button>
		<div class="btn-group-vertical" style="display: block;">
			<a class="btn btn-default"
				href="${pageContext.request.contextPath}/backend/motor/motorMgmtLocSelectPage.jsp"
				role="button" style="background-color: #ddd;">據點車輛管理</a> <a
				class="btn btn-default" href="#" role="button">交車管理</a> <a
				class="btn btn-default" href="#" role="button">還車管理</a> <a
				class="btn btn-default"
				href="${pageContext.request.contextPath}/backend/loc_motor_dispatch/locMotorDispatchApply.jsp"
				role="button">車輛調度申請</a> <a class="btn btn-default" href="#"
				role="button">車輛保養/維修管理</a> <a class="btn btn-default" href="#"
				role="button">據點裝備管理</a> <a class="btn btn-default"
				href="${pageContext.request.contextPath}/backend/emt_dispatch/locEmtDispatchApply.jsp"
				role="button">裝備申請</a>
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
	</div>

	<div class="col-xs-12 col-sm-10 rightHTML">
		<div class="topTitle">
			<h1>據點車輛管理</h1>
		</div>
		<div class="container">
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
			<!--搜尋列 -->
			<div class="searchBar">
				<FORM METHOD="post" style="display: inline;"
					ACTION="${pageContext.request.contextPath}/backend/motor/motor4H.do">
					<div class="form-group" style="display: inline;">
						<div class="col-sm-3">
							<label class="control-label" for="locno">查詢所在據點車輛：</label>
						</div>
						<div class="col-sm-7">
							<select class="form-control" name="locno"
								style="display: inline;">
								<c:forEach var="locVO" items="${locSvc.getAll()}">
									<option value="${locVO.locno}"
										${(locVO.locno==param.locno)?'selected':'' }>${locVO.locname}</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-sm-2">
							<input type="submit" class="btn btn-default" value="查詢">
							<input type="hidden" name="action" value="listMotorsByLoc">
						</div>
					</div>
				</form>
			</div>
			<!--搜尋列結束 -->

		</div>
		<br>

		<table id="locMotorTable"
			class="table table-hover table-condensed table-striped table-bordered "
			cellspacing="0" width="100%">
			<thead>
				<tr>
					<th>車輛編號</th>
					<th>車輛型號</th>
					<th>車輛廠牌</th>
					<th>車輛名稱</th>
					<th>排氣量</th>
					<th>租賃價格</th>
					<th>車牌號碼</th>
					<th>里程數</th>
					<th>引擎號碼</th>
					<th>出廠日期</th>
					<th>狀態</th>
					<th>修改狀態</th>
				</tr>
			</thead>
			<tfoot>
				<tr>
					<th>車輛編號</th>
					<th>車輛型號</th>
					<th>車輛廠牌</th>
					<th>車輛名稱</th>
					<th>排氣量</th>
					<th>租賃價格</th>
					<th>車牌號碼</th>
					<th>里程數</th>
					<th>引擎號碼</th>
					<th>出廠日期</th>
					<th>狀態</th>
					<th>修改狀態</th>
				</tr>
			</tfoot>
			<tbody>
				<c:forEach var="motorVO" items="${motorVOInList}">
					<tr
						${(motorVO.motno == param.motno) ? 'style="background-color:#84d8d1;"':''}>
						<td>${motorVO.motno}</td>
						<td>${motorVO.modtype}</td>
						<td><c:forEach var="motorModelVO"
								items="${motorModelSvc.all}">
								<c:if test="${motorVO.modtype==motorModelVO.modtype}">${motorModelVO.brand}</c:if>
							</c:forEach></td>
						<td><c:forEach var="motorModelVO"
								items="${motorModelSvc.all}">
								<c:if test="${motorVO.modtype==motorModelVO.modtype}">${motorModelVO.name}</c:if>
							</c:forEach></td>
						<td><c:forEach var="motorModelVO"
								items="${motorModelSvc.all}">
								<c:if test="${motorVO.modtype==motorModelVO.modtype}">${motorModelVO.displacement}c.c.</c:if>
							</c:forEach></td>
						<td><c:forEach var="motorModelVO"
								items="${motorModelSvc.all}">
								<c:if test="${motorVO.modtype==motorModelVO.modtype}">${motorModelVO.renprice}元/日</c:if>
							</c:forEach></td>
						<td>${motorVO.plateno}</td>
						<td>${motorVO.mile}</td>
						<td>${motorVO.engno}</td>
						<td><fmt:formatDate pattern="yyyy-MM-dd"
								value="${motorVO.manudate}" /></td>
						<FORM METHOD="post" style="display: inline;"
							ACTION="<%=request.getContextPath()%>/backend/motor/motor4H.do">
							<td>
								<select name="status" class="form-control" id="${motorVO.status}">
									<c:choose>
										<c:when test="${motorVO.status=='leasable'}">
											<option selected value="${motorVO.status}">可出租
										</c:when>
										<c:when test="${motorVO.status=='unleasable'}">
											<option selected value="${motorVO.status}">暫停租賃
										</c:when>
										<c:when test="${motorVO.status=='reserved'}">
											<option selected value="${motorVO.status}">已預約
										</c:when>
										<c:when test="${motorVO.status=='occupied'}">
											<option selected value="${motorVO.status}">出租中
										</c:when>
										<c:when test="${motorVO.status=='other'}">
											<option selected value="${motorVO.status}">其他
										</c:when>
									</c:choose>

									<c:forEach var="s" items="${statusArray}">
										<c:if test="${motorVO.status!=s}">
											<c:choose>
												<c:when test="${s=='leasable'}">
													<option value="${s}">可出租
												</c:when>
												<c:when test="${s=='unleasable'}">
													<option value="${s}">暫停租賃
												</c:when>
												<c:when test="${s=='reserved'}">
													<option value="${s}">已預約
												</c:when>
												<c:when test="${s=='occupied'}">
													<option value="${s}">出租中
												</c:when>
												<c:when test="${s=='other'}">
													<option value="${s}">其他
												</c:when>
											</c:choose>
										</c:if>
									</c:forEach>
								</select>
							</td>
							<td><input type="submit" name="del" value="修改狀態"
								class="btn btn-default" role="button"> <input
								type="hidden" name="motno" value="${motorVO.motno}"> <input
								type="hidden" name="locno" value="${motorVO.locno}"> <input
								type="hidden" name="action" value="setStatus"></td>
						</FORM>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>