<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.motor.model.*"%>
<%@ page import="com.location.model.*"%>


<%-- 萬用複合查詢-可由客戶端select_page.jsp隨意增減任何想查詢的欄位 --%>
<%-- 此頁只作為複合查詢時之結果練習，可視需要再增加分頁、送出修改、刪除之功能--%>

<jsp:useBean id="motorSvc" scope="page"
	class="com.motor.model.MotorService" />
<jsp:useBean id="mmSvc" scope="page"
	class="com.motor_model.model.MotorModelService" />
<jsp:useBean id="now" scope="page" class="java.util.Date" />        
<jsp:useBean id="locSvc" scope="page" class="com.location.model.LocationService" />



<html>
<!DOCTYPE html>
<html lang="">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
<title>複合查詢 - listMotorsByFuzzySearch.jsp</title>

<!-- CSS -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/backend/motor_model/js/listAllMotorModel_css.css">
<style type="text/css">
#pageDiv {
	margin-left: 300px;
}
</style>

<!-- JS -->
<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<script src="https://code.jquery.com/jquery.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script
	src="${pageContext.request.contextPath}/backend/motor/js/motorMgmtHqSelectPage_js.js"></script>

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
		<button class="accordion accordionMenu"
			style="background-color: #ddd;">總部管理系統</button>
		<div class="btn-group-vertical" style="display: block;">
			<a class="btn btn-default"
				href="${pageContext.request.contextPath}/backend/motor/motorMgmtHqSelectPage.jsp"
				role="button"  style="background-color: #ddd;">車輛管理</a> <a class="btn btn-default" href="#"
				role="button">車輛調度</a> <a class="btn btn-default" href="#"
				role="button">租賃單管理</a> <a class="btn btn-default"
				href="${pageContext.request.contextPath}/backend/equipment/emtMgmtSelectPage.jsp"
				role="button">裝備管理</a> <a
				class="btn btn-default" href="#" role="button">裝備調度</a> <a
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
			<h1>車輛資料管理</h1>
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
						<td>
							<FORM METHOD="post"
								ACTION="<%=request.getContextPath()%>/backend/motor/motor4H.do" name="formSearch">
								<input type="text" name="fuzzyValue" id="searchText" value="" placeholder="輸入關鍵字搜尋">
								<input type="submit" id="searchBtn" class="btn btn-default" value="搜尋" >
								<input type="hidden" name="action" value=fuzzyGetAll>
							</Form>
							
						</td>

						<td><FORM METHOD="post"
								ACTION="<%=request.getContextPath()%>/backend/motor/listAllMotor.jsp">
								<input type="submit" name="serchAllMotor" value="搜尋全部車輛"
									class="btn btn-default" role="button">
							</FORM></td>

						<td><input type="button" name="insert" id="addMotor"
							class="btn btn-default" role="button"
							onclick="location.href='${pageContext.request.contextPath}/backend/motor/addMotor.jsp';"
							value="新增車輛"></td>
						<td><FORM METHOD="post"
								ACTION="<%=request.getContextPath()%>/backend/motor_model/listAllMotorModel.jsp">
								<input type="submit" name="serchAllMotorModel" value="搜尋全部車型"
									class="btn btn-default" role="button"> <input
									type="hidden" name="action" value="listAllMotorModel">
							</FORM></td>
						<td><input type="button" name="insert" id="addModel"
							class="btn btn-default" role="button"
							onclick="location.href='${pageContext.request.contextPath}/backend/motor_model/addMotorModel.jsp';"
							value="新增車型"></td>
					</tr>
				</table>
			</div>
			<br>

			<table
				class="table table-hover table-condensed table-striped table-bordered">
				<thead>
					<th>車輛編號</th>
					<th>車輛型號</th>
					<th>車輛廠牌</th>
					<th>車輛名稱</th>
					<th>排氣量</th>
					<th>租賃價格</th>
					<th>車牌號碼</th>
					<th>里程數</th>
					<th>所在據點</th>
					<th>引擎號碼</th>
					<th>出廠日期</th>
					<th>出售價格</th>
					<th>狀態</th>
					<th>修改/刪除</th>
				</thead>
<!-- 			警告：必須MOTOR及MOTOR_MODEL兩個table都找得到的關鍵字才會正常顯示.... -->
				<c:forEach var="motorVO" items="${fuzzyGetAll}">
					<tr>
						<td>${motorVO.motno}</td>
						<td>${motorVO.modtype}</td>
						<td>${motorVO.brand}</td>
						<td>${motorVO.name}</td>
						<td>${motorVO.displacement}</td>
						<td>${motorVO.renprice}元/日</td>
						<td>${motorVO.plateno}</td>
						<td>${motorVO.mile}</td>
						<td>
						<c:forEach var="locVO" items="${locSvc.all}">
							<c:if test="${motorVO.locno == locVO.locno }">
               							${locVO.locname}(${motorVO.locno})
                   			</c:if>
						</c:forEach>
						</td>
						<td>${motorVO.engno}</td>
						<td><fmt:formatDate pattern="yyyy-MM-dd" value="${motorVO.manudate}"/></td>
						<td>${motorVO.saleprice}元</td>
						<td>
							<c:choose>
  								<c:when test="${motorVO.status=='leasable'}">
  									可租賃<br>
  									leasable
  								</c:when>
  								<c:when test="${motorVO.status=='unleasable'}">
  									暫停租賃<br>
  									unleasable
  								</c:when>
  								<c:when test="${motorVO.status=='reserved'}">
  									已預約<br>
  									reserved
  								</c:when>
  								<c:when test="${motorVO.status=='occupied'}">
  									出租中<br>
  									occupied
  								</c:when>
  								<c:when test="${motorVO.status=='dispatching'}">
  									調度中<br>
  									dispatching
  								</c:when>
  								<c:when test="${motorVO.status=='seconsale'}">
  									二手車-上架中<br>
  									seconsale
  								</c:when>
  								<c:when test="${motorVO.status=='secpause'}">
  									二手車-下架中<br>
  									secpause
  								</c:when>
  								<c:when test="${motorVO.status=='secreserved'}">
  									二手車-已預約<br>
  									secreserved
  								</c:when>
  								<c:when test="${motorVO.status=='secsaled'}">
  									二手車-已出售<br>
  									secaled
  								</c:when>
  								<c:when test="${motorVO.status=='other'}">
  									其他<br>
  									other
  								</c:when>
  							</c:choose>
						</td>

						<td>
							<FORM METHOD="post" style="display: inline;" ACTION="<%=request.getContextPath()%>/backend/motor/motor4H.do">
								<input type="submit" name="fix" value="修改" class="btn btn-default" role="button"> 
								<input type="hidden" name="motno" value="${motorVO.motno}"> 
								<input type="hidden" name="requestURL" value="<%=request.getServletPath()%>"> 
								<input type="hidden" name="action" value="getOne_For_Update">
							</FORM>

							<FORM METHOD="post" style="display: inline;" ACTION="<%=request.getContextPath()%>/backend/motor/motor4H.do">
								<input type="submit" name="del" value="刪除" class="btn btn-default" role="button"> 
								<input type="hidden" name="motno" value="${motorVO.motno}"> 
								<input type="hidden" name="requestURL" value="<%=request.getServletPath()%>">
								<input type="hidden" name="action" value="delete">
							</FORM>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
</body>
<script type="text/javascript">
var Msg ='<%=request.getAttribute("inserted")%>';
    if (Msg == "inserted") {
 		function insertedAlert(){
 			alert("新增車輛成功！");
 		} 
 	}
</script> 
<script type="text/javascript">window.onload = insertedAlert;</script> 
</html>
