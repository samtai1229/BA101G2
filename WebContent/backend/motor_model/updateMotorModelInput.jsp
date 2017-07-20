<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.motor_model.model.*"%>
<%
	MotorModelVO mmVO = (MotorModelVO) request.getAttribute("mmVO");
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
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/backend/motor/js/updateMotorInput_css.css">

<!-- Javascript -->
<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<script src="https://code.jquery.com/jquery.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script
	src="${pageContext.request.contextPath}/backend/motor_model/js/addMotorModel_js.js"></script>

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
	<!--左邊選單BAR -->
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
	</div><!--左邊選單BAR結束 -->
	
	<!--右邊整塊HTML區塊 -->
	<div class="col-xs-12 col-sm-10 rightHTML">
		<div class="topTitle">
			<h1>車輛資料管理</h1>
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
		<!--update區塊 -->
		<div class="container">
			<FORM METHOD="post" ACTION="motorModel4H.do" name="formUpdate" class="form-horizontal"  enctype="multipart/form-data">

				<div class="form-group">
					<label class="control-label col-sm-2" for="modtype">車型編號：</label>
					<div class="col-sm-10">
						<p class="form-control">${mmVO.modtype}</p>
					</div>
				</div>
				
				<div class="form-group">
					<label class="control-label col-sm-2" for="brand">廠牌名稱：</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="brand" name="brand" value="<%=mmVO.getBrand()%>" />
					</div>
				</div>

				<div class="form-group">
					<label class="control-label col-sm-2" for="displacement">排氣量：</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="displacement" name="displacement"
							value="<%=mmVO.getDisplacement()%>" />
					</div>
				</div>

				<div class="form-group">
					<label class="control-label col-sm-2" for="name">車型名稱：</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="name" name="name"
							value="<%=mmVO.getName()%>" />
					</div>
				</div>

				<div class="form-group">
					<label class="control-label col-sm-2" for="renprice">租賃價格：</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="renprice" name="renprice"
							value="<%=mmVO.getRenprice()%>" />
					</div>
				</div>

				<div class="form-group">
					<label class="control-label col-sm-2" for="saleprice">出售價格：</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="saleprice" name="saleprice"
							value="<%=mmVO.getSaleprice()%>" />
					</div>
				</div>
				
				<div class="form-group">
					<label class="control-label col-sm-2" for="intro">車型說明：</label>
					<div class="col-sm-10">
						<textarea class="form-control" id="intro" rows="5" cols="70"
							name="intro">${(mmVO.intro == null) ? '' : mmVO.getIntro()}</textarea>
					</div>
				</div>

				<div class="form-group">
					<label class="control-label col-sm-2" for="motpic">更改圖片：</label>
					<div class="col-sm-10">
					<input type="file" id="filePic" name="motpic" class="btn btn-default">
					<p>
					<img id="imgPic" src="<%=request.getContextPath()%>/backend/motor_model/mmReader.do?modtype=${mmVO.modtype}">
					</p>
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-sm-2"></label>
				<div class="col-sm-10">
						<input type="submit" class="btn btn-default" value="送出修改">
						<input type="hidden" name="action" value="update"> 
						<input type="hidden" name="modtype" value="<%=mmVO.getModtype()%>">
						<input type="hidden" name="requestURL" value="<%=request.getParameter("requestURL")%>">
						<!--接收原送出修改的來源網頁路徑後,再送給Controller準備轉交之用-->
						<input type="hidden" name="whichPage" value="<%=request.getParameter("whichPage")%>">
			</FORM>
							<FORM METHOD="post"  style="display: inline;" ACTION="<%=request.getContextPath()%>/backend/motor_model/motorModel4H.do" >
								<input type="submit" name="reset" value="重置" class="btn btn-default" role="button"><input type="hidden" name="modtype" value="${mmVO.modtype}">
								<input type="hidden" name="action" value="getOne_For_Update">
							</FORM>
					</div>
				</div>	
			
		</div><!--update區塊結束 -->
	</div><!--右邊整塊HTML區塊結束 -->
</body>
</html>
</body>
</html>
