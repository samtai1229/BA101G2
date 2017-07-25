<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.emt_dispatch.model.*"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>

<%
	EmtDispatchService edSvc = new EmtDispatchService();
	List<EmtDispatchVO> list = edSvc.getAllByHib();
	pageContext.setAttribute("list", list);
%>
<jsp:useBean id="locSvc" scope="page"
	class="com.location.model.LocationService" />
<jsp:useBean id="now" scope="page" class="java.util.Date" />  

<!DOCTYPE html>
<html lang="">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
<title>總部裝備調度單管理- emtDispatchMgmgHq.jsp</title>

<!-- CSS -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/backend/motor_model/js/listAllMotorModel_css.css">
<style type="text/css">
#pageDiv {
	margin-left: 300px;
}
.table{
	margin:0px;
}
</style>

<!-- JS -->
<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<script src="https://code.jquery.com/jquery.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/backend/motor/js/motorMgmtHqSelectPage_js.js"></script>

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
			<li><a href="#" id="navA">HI EMP9413 歡迎回來為公司奉獻</a></li>
			<li><a href="#" id="navA"><i
					class="glyphicon glyphicon-wrench"></i>個人設定</a></li>
			<li><a href="#" id="navA"><i
					class="glyphicon glyphicon-log-out"></i>登出</a></li>
		</ul>
	</nav>
	<div class="col-xs-12 col-sm-2 leftBar">
		<img id="logo"
			src="${pageContext.request.contextPath}/backend/images/logo.jpg">
		<button class="accordion accordionMenu"
			style="background-color: #ddd;">總部管理系統</button>
		<div class="btn-group-vertical" style="display: block;">
			<a class="btn btn-default"
				href="${pageContext.request.contextPath}/backend/motor/motorMgmtHqSelectPage.jsp"
				role="button">車輛管理</a> 
				<a class="btn btn-default" href="${pageContext.request.contextPath}/backend/loc_motor_dispatch/motorDispatchMgmtHq.jsp"
				role="button">車輛調度</a> <a class="btn btn-default" href="#"
				role="button">租賃單管理</a> <a class="btn btn-default"
				href="${pageContext.request.contextPath}/backend/equipment/emtMgmtSelectPage.jsp"
				role="button">裝備管理</a> <a
				class="btn btn-default" href="${pageContext.request.contextPath}/backend/emt_dispatch/emtDispatchMgmtHq.jsp" role="button" style="background-color: #ddd;">裝備調度</a> <a
				class="btn btn-default" href="#" role="button">據點管理</a>
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
	</div>

	<div class="col-xs-12 col-sm-10 rightHTML">
		<div class="topTitle">
			<h1>總部裝備調度管理系統</h1>
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
			<div class="searchBar">
				<table>
					<tr>
						
						<td><FORM METHOD="post"
								ACTION="${pageContext.request.contextPath}/backend/emt_dispatch/emtDispatchMgmtHq.jsp">
								<input type="submit" name="serchAllEmtDispatch" value="調閱所有裝備調度單"
									class="btn btn-default" role="button">
							</FORM></td>
					</tr>
				</table>
				<br>

			</div>
			<br>

			<table
				class="table table-hover table-condensed table-striped table-bordered">
				<thead>
					<th>調度單號</th>
					<th>申請據點</th>
					<th>請求日期</th>
					<th>結案日期</th>
					<th>申請數量</th>
					<th>處理進度</th>
					<th>管理功能</th>
				</thead>
				<%@ include file="pages/page1.file"%>
				<c:forEach var="edVO" items="${list}" begin="<%=pageIndex%>"
					end="<%=pageIndex+rowsPerPage-1%>">

					<tr ${(edVO.edno == param.edno) ? 'style="background-color:#84d8d1;"':''}>
						<td>${edVO.edno}</td>
						<td><c:forEach var="locVO" items="${locSvc.all}">
								${(edVO.locno==locVO.locno)?locVO.locname:""}
							</c:forEach></td>
						<td><fmt:formatDate pattern="yyyy-MM-dd" value="${edVO.demanddate}"/></td>
						<td><fmt:formatDate pattern="yyyy-MM-dd" value="${edVO.closeddate}"/></td>
						<td>
							<%! int count=0;%>
							<c:forEach var="edListVO" items="${edVO.emtDispLists}">
								<% count++;%>
							</c:forEach>
							<%= count %>件
						</td>
							<%  count=0;%>
						<td>
							<c:choose>
							<c:when test="${edVO.prog=='request'}">
  								待審核<br>
  								request
  							</c:when>
  							<c:when test="${edVO.prog=='rejected'}">
  								否決<br>
  								rejected
  							</c:when>
  							<c:when test="${edVO.prog=='canceled'}">
  								已取消<br>
  								canceled
  							</c:when>
  							<c:when test="${edVO.prog=='dispatching'}">
  								調度中<br>
  								dispatching
  							</c:when>
  							<c:when test="${edVO.prog=='dispatched'}">
  								調度完成<br>
  								dispatched
  							</c:when>
  							<c:when test="${edVO.prog=='closed'}">
  								已結案<br>
  								closed
  							</c:when>
  							<c:when test="${edVO.prog=='other'}">
  								其他<br>
  								other
  							</c:when>
  							</c:choose>
						</td>

						<td>
							<FORM METHOD="post" style="display: inline;"
								ACTION="<%=request.getContextPath()%>/backend/emt_dispatch/ed.do">
								<input type="submit" name="fix" value="審核"
									class="btn btn-default" role="button"> <input
									type="hidden" name="edno" value="${edVO.edno}"> <input
									type="hidden" name="requestURL"
									value="<%=request.getServletPath()%>"> <input
									type="hidden" name="whichPage" value="<%=whichPage%>">
								<input type="hidden" name="action" value="getOne_For_Update">
							</FORM>
						</td>
					</tr>
				</c:forEach>

			</table>

			<%@ include file="pages/page2.file"%>

		</div>
	</div>
</body>
</html>