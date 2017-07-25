<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.motor_model.model.*"%>
<%@ page import="com.motor.model.*"%>
<%@ page import="com.motor_dispatch.model.*"%>
<%@ page import="java.util.*"%>

<jsp:useBean id="now" scope="page" class="java.util.Date" />
<jsp:useBean id="locSvc" scope="page"
	class="com.location.model.LocationService" />
<jsp:useBean id="mdSvc" scope="page"
	class="com.motor_dispatch.model.MotorDispatchService" />
<jsp:useBean id="motorSvc" scope="page"
	class="com.motor.model.MotorService" />
<jsp:useBean id="mmSvc" scope="page"
	class="com.motor_model.model.MotorModelService" />
<%
	MotorDispatchVO mdVO = (MotorDispatchVO) request.getAttribute("mdVO");
	List<MotorModelVO> allMotorModels = mmSvc.getAll();
	pageContext.setAttribute("allMotorModels", allMotorModels);

	String[] progsArray = { "request", "rejected", "canceled", "dispatching", "dispatched", "closed", "other" };
	request.setAttribute("progsArray", progsArray);

	List<String> motnoInList = (List<String>) request.getAttribute("motnoInList");
%>

<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>調度單審核 - updateMotorDispatch.jsp</title>

<meta name="keywords" content="">
<!-- CSS -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet"
	href="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.3.1/css/bootstrap-combined.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/backend/loc_motor_dispatch/js/updateMotorDispatchInput_css.css">
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
		<div class="accordion accordionMenu" style="background-color: #ddd;">總部管理系統</div>
		<div class="btn-group-vertical" style="display: block;">
			<a class="btn btn-default"
				href="${pageContext.request.contextPath}/backend/motor/motorMgmtHqSelectPage.jsp"
				role="div">車輛管理</a> <a class="btn btn-default"
				href="${pageContext.request.contextPath}/backend/loc_motor_dispatch/motorDispatchMgmtHq.jsp"
				role="div" style="background-color: #ddd;">車輛調度</a> <a
				class="btn btn-default" href="#" role="div">租賃單管理</a> <a
				class="btn btn-default"
				href="${pageContext.request.contextPath}/backend/equipment/emtMgmtSelectPage.jsp"
				role="div">裝備管理</a> <a class="btn btn-default" href="#" role="div">裝備調度</a>
			<a class="btn btn-default" href="#" role="div">據點管理</a>
		</div>
		<div class="accordion accordionMenu">據點管理系統</div>
		<div class="btn-group-vertical">
			<a class="btn btn-default" href="#" role="div">據點車輛管理</a> <a
				class="btn btn-default" href="#" role="div">交車管理</a> <a
				class="btn btn-default" href="#" role="div">還車管理</a> <a
				class="btn btn-default" href="#" role="div">車輛調度申請</a> <a
				class="btn btn-default" href="#" role="div">車輛保養/維修管理</a> <a
				class="btn btn-default" href="#" role="div">據點裝備管理</a> <a
				class="btn btn-default" href="#" role="div">裝備申請</a>
		</div>
		<div class="accordion accordionMenu">二手車管理系統</div>
		<div class="btn-group-vertical">
			<a class="btn btn-default" href="#" role="div">二手車輛管理</a> <a
				class="btn btn-default" href="#" role="div">二手車訂單管理</a> <a
				class="btn btn-default" href="#" role="div">二手車交易管理</a>
		</div>
		<div class="accordion accordionMenu">會員管理系統</div>
		<div class="btn-group-vertical"></div>
		<div class="accordion accordionMenu">活動企劃管理系統</div>
		<div class="btn-group-vertical">
			<a class="btn btn-default" href="#" role="div">推播管理</a> <a
				class="btn btn-default" href="#" role="div">留言版管理</a> <a
				class="btn btn-default" href="#" role="div">最新消息管理</a>
		</div>
		<div class="accordion accordionMenu">後端管理系統</div>
		<div class="btn-group-vertical">
			<a class="btn btn-default" href="#" role="div">後端權限管理</a> <a
				class="btn btn-default" href="#" role="div">推薦景點管理</a> <a
				class="btn btn-default" href="#" role="div">後端登入管理</a>
		</div>
	</div>
	<div class="col-xs-12 col-sm-10 rightHTML">
		<div class="topTitle">
			<h1>調度單審核</h1>
		</div>

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
			<FORM METHOD="post"
				ACTION="${pageContext.request.contextPath}/backend/motor_dispatch/md.do"
				name="formUpdate" class="form-horizontal">
				<div class="form-group">
					<label class="control-label col-sm-2" for="mdno">調度單號：</label>
					<div class="col-sm-10">
						<p class="form-control">${mdVO.mdno}</p>
					</div>
				</div>

				<div class="form-group">
					<label class="control-label col-sm-2" for="locno">申請據點：</label>
					<div class="col-sm-10">
					<c:choose>
  						<c:when test="${mdVO.locno=='TPE00'}">
						<input type="text" readonly class="form-control" value="總部" />
						<input type="hidden" readonly class="form-control" id="locno" name="locno" value="<%=mdVO.getLocno()%>" />
						</c:when>
						<c:when test="${mdVO.locno=='TPE01'}">
						<input type="text" readonly class="form-control" value="台北車站" />
						<input type="hidden" readonly id="locno" name="locno" value="<%=mdVO.getLocno()%>" />
						</c:when>
						<c:when test="${mdVO.locno=='NTC01'}">
						<input type="text" readonly class="form-control" value="板橋車站" />
						<input type="hidden" readonly id="locno" name="locno" value="<%=mdVO.getLocno()%>" />
						</c:when>
						<c:when test="${mdVO.locno=='TXG01'}">
						<input type="text" readonly class="form-control" value="台中車站" />
						<input type="hidden" readonly id="locno" name="locno" value="<%=mdVO.getLocno()%>" />
						</c:when>
						<c:when test="${mdVO.locno=='TNN01'}">
						<input type="text" readonly class="form-control" value="台南車站" />
						<input type="hidden" readonly id="locno" name="locno" value="<%=mdVO.getLocno()%>" />
						</c:when>
						<c:when test="${mdVO.locno=='KHH01'}">
						<input type="text" readonly class="form-control" value="高雄車站" />
						<input type="hidden" readonly id="locno" name="locno" value="<%=mdVO.getLocno()%>" />
						</c:when>
  					</c:choose>
					</div>
				</div>

				<div class="form-group">
					<label class="control-label col-sm-2" for="filldate">填單日期：</label>
					<div class="col-sm-10">
						<input type="text" readonly class="form-control" id="filldate"
							name="filldate"
							value="<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${mdVO.filldate}"/>" />
					</div>
				</div>

				<div class="form-group">
					<label class="control-label col-sm-2" for="closeddate">結案日期：</label>
					<div id="datetimepicker1" class="col-sm-10 input-append">
						<input data-format="yyyy-MM-dd HH:mm:ss" type="text"
							class="form-control" name="closeddate"
							value="<%=mdVO.getCloseddate()%>" /> <span class="add-on">
							<i data-time-icon="icon-time" data-date-icon="icon-calendar">
						</i>
						</span>
					</div>
				</div>

				<h3>申請車輛</h3>
				<c:forEach var="allMMs" items="${allMotorModels}">
					<%
						int count = 0;
					%>
					<c:forEach var="mdListVO" items="${mdVO.motorDispLists}">

						<c:if test="<%=count < 1%>">
							<c:if
								test="${allMMs.modtype == mdListVO.motorVO.motorModelVO.modtype}">
								<%
									++count;
								%>

								<div class="form-group">
									<label class="control-label col-sm-2" for="modtype"
										name="modtype">${allMMs.modtype}：</label>

									<c:forEach var="motorVO"
										items="${motorSvc.getMotorsByModtypeByHib(mdListVO.motorVO.motorModelVO.modtype)}">

										<c:set var="motorVO" value="${motorVO }" />
										<%
											MotorVO motorVO = (MotorVO) pageContext.getAttribute("motorVO");
										%>

										<label class="checkbox-inline"> <input
											class="form-check-input" type="checkbox" name="motno"
											id="${motorVO.motno}" value="${motorVO.motno}"
											<%= motnoInList.contains(motorVO.getMotno())?"checked":"" %>>
											${motorVO.motno} - 
											<c:choose>
												<c:when test="${motorVO.locno=='TPE00'}">
													總部
												</c:when>
												<c:when test="${motorVO.locno=='TPE01'}">
													台北車站
												</c:when>
												<c:when test="${motorVO.locno=='NTC01'}">
													板橋車站
												</c:when>
												<c:when test="${motorVO.locno=='TXG01'}">
													臺中車站
												</c:when>
												<c:when test="${motorVO.locno=='TNN01'}">
													台南車站
												</c:when>
												<c:when test="${motorVO.locno=='KHH01'}">
													高雄車站
												</c:when>
											</c:choose>
										</label>
									</c:forEach>
								</div>
							</c:if>
						</c:if>
					</c:forEach>
				</c:forEach>

				<div class="form-group">
					<label class="control-label col-sm-2" for="prog">處理進度：</label>
					<div class="col-sm-10">
						<select name="prog" class="form-control" id="prog">
							<c:choose>
								<c:when test="${mdVO.prog=='request'}">
									<option selected value="${mdVO.prog}">審核中
								</c:when>
									<c:when test="${mdVO.prog=='rejected'}">
									<option selected value="${mdVO.prog}">否決
									</c:when>
									<c:when test="${mdVO.prog=='canceled'}">
									<option selected value="${mdVO.prog}">已取消
									</c:when>
									<c:when test="${mdVO.prog=='dispatched'}">
									<option selected value="${mdVO.prog}">調度完成
									</c:when>
									<c:when test="${mdVO.prog=='dispatching'}">
									<option selected value="${mdVO.prog}">調度中
									</c:when>
									<c:when test="${mdVO.prog=='closed'}">
									<option selected value="${mdVO.prog}">結案
									</c:when>
									<c:when test="${mdVO.prog=='other'}">
									<option selected value="${mdVO.prog}">其他
									</c:when>
							</c:choose>
							
								<c:forEach var="progs" items="${progsArray}">
										<c:if test="${mdVO.prog!=progs}">
											<c:choose>
												<c:when test="${progs=='request'}">
													<option value="${progs}">審核中
												</c:when>
												<c:when test="${progs=='rejected'}">
													<option value="${progs}">否決
												</c:when>
												<c:when test="${progs=='canceled'}">
													<option value="${progs}">已取消
												</c:when>
												<c:when test="${progs=='dispatched'}">
													<option value="${progs}">調度完成
												</c:when>
												<c:when test="${progs=='dispatching'}">
													<option value="${progs}">調度中
												</c:when>
												<c:when test="${progs=='closed'}">
													<option value="${progs}">結案
												</c:when>
												<c:when test="${progs=='other'}">
													<option value="${progs}">其他
												</c:when>
											</c:choose>
										</c:if>
									</c:forEach>
						</select>
					</div>
				</div>



				<div class="form-group">
					<div class="col-sm-2"></div>
					<div class="col-sm-10">
						<table>
							<tr>
								<td><input type="submit" class="btn btn-default"
									value="審核完成"> <input type="hidden" name="action"
									value="update"> <input type="hidden" name="mdno"
									value="${mdVO.mdno}"> <input type="hidden"
									name="requestURL"
									value="<%=request.getContextPath()%>">
									<input type="hidden" name="whichPage"
									value="<%=request.getParameter("whichPage")%>"> 
									</FORM>
									<FORM METHOD="post" style="display: inline;"
										ACTION="<%=request.getContextPath()%>/backend/motor_dispatch/md.do">
										<input type="submit" name="reset" value="重置"
											class="btn btn-default" role="button"> <input
											type="hidden" name="mdno" value="${mdVO.mdno}"> <input
											type="hidden" name="action" value="getOne_For_Update">
									</FORM></td>
							</tr>
						</table>
					</div>
				</div>
		</div>
	</div>
</body>
</html>
</body>
<script type="text/javascript">

var Msg ='<%=request.getAttribute("getAlert")%>';
    if (Msg != "null") {
 		function undispatchAlert(){
 			alert("某申請車輛已無法調度，請重新選擇！");
 		} 
 	}

<c:forEach var="mdListVO" items="${mdVO.motorDispLists}">
	<c:forEach var="motorVO" items="${motorSvc.getMotorsByModtypeByHib(mdListVO.motorVO.motorModelVO.modtype)}">

	var status = "${motorVO.status}"
	if(status == 'dispatching' || status == 'reserved' ||
		status == 'occupied' || status == 'seconsale' ||
		status == 'secpause' || status == 'secreserved' ||
		status == 'secsaled' || status == 'other'){
		
// 		$("#${motorVO.motno}").removeAttr("checked");
// 		$("#${motorVO.motno}").attr("disabled",true);
		$("#${motorVO.motno}").parent().css("text-decoration", "line-through");

	}
	</c:forEach>
</c:forEach>

</script> 
<script type="text/javascript">window.onload = undispatchAlert;</script> 
</html>
