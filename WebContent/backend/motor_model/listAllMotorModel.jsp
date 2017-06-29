<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.motor_model.model.*"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>

<jsp:useBean id="motorModelSvc" scope="page"
	class="com.motor_model.model.MotorModelService" />

<!DOCTYPE html>
<html lang="">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
<title>所有車型資料- listAllMotorModel.jsp</title>

<!-- CSS -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/backend/motor_model/js/listAllMotorModel_css.css">

<!-- JS -->
<script src="${pageContext.request.contextPath}/backend/motor/js/motorMgmtHqSelectPage_js.js"></script>
<script src="https://code.jquery.com/jquery.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>

</head>
<body>
	<nav class="navbar navbar-default" role="navigation">
		<!-- logo區 -->
		<a class="navbar-brand" href="#" id="navA">AUTOBIKE</a>
		<!-- 左選單 -->
		<ul class="nav navbar-nav">
			<li><a href="#" id="navA">後端管理系統</a></li>
			 <!-- 時鐘 -->
            <iframe scrolling="no" frameborder="no" clocktype="html5" style="overflow:hidden;border:0;margin:0;padding:0;width:120px;height:40px;" src="http://www.clocklink.com/html5embed.php?clock=004&timezone=CCT&color=yellow&size=120&Title=&Message=&Target=&From=2017,1,1,0,0,0&Color=yellow">
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
		<img id = "logo" src="${pageContext.request.contextPath}/backend/images/logo.jpg">
		<button class="accordion accordionMenu">總部管理系統</button>
		<div class="btn-group-vertical">
			<a class="btn btn-default" href="${pageContext.request.contextPath}/backend/motor/motorMgmtHqSelectPage.jsp" role="button">車輛管理</a> <a
				class="btn btn-default" href="#" role="button">車輛調度</a> <a
				class="btn btn-default" href="#" role="button">租賃單管理</a> <a
				class="btn btn-default" href="#" role="button">裝備管理</a> <a
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
				<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/motor/motor4H.do" name="formSearch">
				<input type="text" name="motno" id="searchText" value="">
				<input type="submit" id="searchBtn" class="btn btn-default" value="搜尋">
				<input type="hidden" name="action" value="listMotors_ByCompositeQuery">
				</Form>
				</td>

				<td><FORM METHOD="post"	ACTION="<%=request.getContextPath()%>/backend/motor/motor4H.do"> 
					<input type="submit" name="serchAllMotor" value="蒐尋全部車輛" class="btn btn-default" role="button"> 
					<input type="hidden" name="action" value="listAllMotor_B" >
				</FORM></td>
				
				<td><input type="button" name="insert" id="addMotor" class="btn btn-default" role="button" onclick="location.href='${pageContext.request.contextPath}/backend/motor/addMotor.jsp';" value="新增車輛"></td>
				<td><FORM METHOD="post"	ACTION="<%=request.getContextPath()%>/backend/motor_model/listAllMotorModel.jsp"> 
					<input type="submit" name="serchAllMotorModel" value="蒐尋全部車型" class="btn btn-default" role="button"> 
					<input type="hidden" name="action" value="listAllMotorModel">
				</FORM></td>
				<td><input type="button" name="insert" id="addModel" class="btn btn-default" role="button" onclick="location.href='${pageContext.request.contextPath}/backend/motor_model/addMotorModel.jsp';" value="新增車型"></td>
				</tr>
				</table>
				<br>
			</div>
	<table
		class="table table-hover table-condensed table-striped table-bordered">
		<thead>
			<td>車輛型號</td>
			<td>廠牌</td>
			<td>排氣量</td>
			<td>名稱</td>
			<td>租賃價格</td>
			<td>出售價格</td>
			<td>圖示</td>
			<td>修改/刪除</td>
		</thead>
		<c:forEach var="motorModelVO" items="${motorModelSvc.all}">

			<tr ${(motorModelVO.modtype==param.modtype) ? 'style="background-color:red;"':''}>
				<td>--${motorModelVO.modtype==param.modtype}--${motorModelVO.modtype}</td>
				<td>${motorModelVO.brand}</td>
				<td>${motorModelVO.displacement}</td>
				<td>${motorModelVO.name}</td>
				<td>${motorModelVO.renprice}</td>
				<td>${motorModelVO.saleprice}</td>
				<td><img id = "motpic" src="<%=request.getContextPath()%>/backend/motor_model/mmReader.do?modtype=${motorModelVO.modtype}"></td>
				<td >
					<FORM METHOD="post"  style="display: inline;"
						ACTION="<%=request.getContextPath()%>/backend/motor_model/motorModel4H.do" >
						<input type="submit" name="fix" value="修改" class="btn btn-default"
							role="button"> 
						<input type="hidden" name="modtype"
							value="${motorModelVO.modtype}"> 
						<input type="hidden"
							name="requestURL" value="<%=request.getServletPath()%>">
						<input type="hidden" name="action" value="getOne_For_Update">
					</FORM>
				
					<FORM METHOD="post" style="display: inline;"
						ACTION="<%=request.getContextPath()%>/backend/motor_model/motorModel4H.do">
						<input type="submit" name="del" value="刪除" class="btn btn-default"
							role="button"> 
						<input type="hidden" name="modtype" value="${motorModelVO.modtype}"> 
						<input type="hidden"
							name="requestURL" value="<%=request.getServletPath()%>">
						<!--送出本網頁的路徑給Controller-->
						<input type="hidden" name="action" value="delete">
					</FORM>
				</td>
			</tr>
		</c:forEach>
	</table>


	

			
			
			

		</div>
	</div>
</body>
</html>