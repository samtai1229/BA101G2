<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.motor.model.*"%>
<!-- 後端網頁的側邊欄  和權限控管的必要片段程式碼 -->
<%@ page import="com.adminis.model.*"%>
<%  
	AdminisService as = new AdminisService();
	AdminisVO adminisVO= (AdminisVO)session.getAttribute("adminisVO");
	
	if(adminisVO==null){
		request.getRequestDispatcher("/backend/index.jsp").forward(request, response);
	}else{
		System.out.println("!!!!!!!!!!!"+adminisVO.getName());
	    session.setAttribute("admins", adminisVO.getName());     
	    session.setAttribute("adminisVO", adminisVO);
	}
%>
<!-- 後端網頁的側邊欄  和權限控管的必要片段程式碼 -->
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
	href="<%=request.getContextPath()%>/backend/Modified/twitter_bootstrap_3_3_7_min.css">
<link rel="stylesheet"
	href="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.3.1/css/bootstrap-combined.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/backend/motor/js/addMotor_css.css">
<link rel="stylesheet"
	href="https://tarruda.github.io/bootstrap-datetimepicker/assets/css/bootstrap-datetimepicker.min.css">


<!-- Javascript -->
<script src="<%=request.getContextPath()%>/backend/Modified/jquery.js"></script>
<script
	src="<%=request.getContextPath()%>/backend/Modified/twitter_bootstrap_3_3_7_min.js"></script>
<script
	src="${pageContext.request.contextPath}/backend/motor/js/updateMotorInput_js.js"></script>
<script
	src="${pageContext.request.contextPath}/backend/motor/js/bootstrap-datetimepicker.js"></script>

</head>

<style>
.accordion {
    margin-bottom: 0px;
}
</style>


<body>
<%--nav start --%>
<c:if test="<%=adminisVO!=null %>">
    <nav class="navbar navbar-default" role="navigation">
        <!-- logo區 -->
        <a class="navbar-brand page-scroll" href="<%=request.getContextPath()%>/backend/index.jsp" id="navA">AUTOBIKE</a>
        <!-- 左選單 -->
        <ul class="nav navbar-nav">
            <li><a href="#" id="navA">後端管理系統</a></li>
            <!-- 時鐘 -->
            <iframe scrolling="no" frameborder="no" clocktype="html5" style="overflow:hidden;border:0;margin:0;margin-top:5px;padding:0;width:120px;height:40px;" src="http://www.clocklink.com/html5embed.php?clock=004&timezone=CCT&color=yellow&size=120&Title=&Message=&Target=&From=2017,1,1,0,0,0&Color=yellow">
            </iframe>
        </ul>
        <!-- 右選單 -->
        
        <ul class="nav navbar-nav navbar-right">
        
			<li><a href="#" id="navA">哈囉! <%= adminisVO.getName() %></a></li>
			<li><a href="<%=request.getContextPath()%>/admin.do?action=logout" id="navA"><i
					class="glyphicon glyphicon-log-out"></i>登出</a></li>
		</ul>
    </nav>
<%--nav end --%>
	<div class="col-xs-12 col-sm-2 leftBar">
	<!------------------------------- 後端網頁的側邊欄  和權限控管的必要片段程式碼 -->
		<img id="logo" src="${pageContext.request.contextPath}/backend/images/android_logo2.jpg">
       		<%if(adminisVO.getAuthno().equals("AC01") || adminisVO.getAuthno().equals("AC07")){%>     
        <button class="accordion accordionMenu accordion accordionMenuMenu">總部管理系統</button> 
        <div class="btn-group-vertical">
            <a class="btn btn-default" href="${pageContext.request.contextPath}/backend/motor/motorMgmtHqSelectPage.jsp"  role="button">車輛管理</a>
            <a class="btn btn-default" href="${pageContext.request.contextPath}/backend/loc_motor_dispatch/motorDispatchMgmtHq.jsp"  role="button">車輛調度管理</a>           
			<a class="btn btn-default" href="<%=request.getContextPath()%>/backend/rent_ord/backendRentOrd.jsp" role="button">租賃單管理</a>
            <a class="btn btn-default" href="${pageContext.request.contextPath}/backend/equipment/emtMgmtSelectPage.jsp" role="button">裝備管理</a>
            <a class="btn btn-default" href="<%=request.getContextPath()%>/backend/location/listAllLocation.jsp" role="button">據點管理</a>
        </div>
       		<%} %><%else {%>
        <div>
        	<button class="accordion accordionMenu accordion accordionMenuMenu" style="background-color:pink;">總部管理系統</button>		
        </div>
       		<%} %>
       		
     	<%if(adminisVO.getAuthno().equals("AC02") || adminisVO.getAuthno().equals("AC07")){%> 
        <button class="accordion accordionMenu">據點管理系統</button>
        <div class="btn-group-vertical">
        	<a class="btn btn-default" href="${pageContext.request.contextPath}/backend/motor/motorMgmtLocSelectPage.jsp" role="button">據點車輛管理</a>
        	<a class="btn btn-default" href="${pageContext.request.contextPath}/backend/equipment/locEmtMgmtSelectPage.jsp" role="button">據點裝備管理</a>
            <a class="btn btn-default" href="<%=request.getContextPath()%>/backend/rent_ord/lease.jsp"  role="button">交車管理</a>
          	<a class="btn btn-default" href="<%=request.getContextPath()%>/backend/rent_ord/return.jsp"  role="button">還車管理</a>
            <a class="btn btn-default" href="${pageContext.request.contextPath}/backend/loc_motor_dispatch/locMotorDispatchApply.jsp" role="button">車輛調度申請</a>
            <a class="btn btn-default" href="${pageContext.request.contextPath}/backend/emt_dispatch/locEmtDispatchApply.jsp" role="button">裝備申請</a>
         </div>
         <%} %><%else {%>
        <div>
        	<button class="accordion accordionMenu accordion accordionMenuMenu" style="background-color:pink;">據點管理系統</button>		
        </div>
       		<%} %>
       		
       		 <%if(adminisVO.getAuthno().equals("AC05") || adminisVO.getAuthno().equals("AC07")){%>
        <button class="accordion accordionMenu">二手車管理系統</button>
        <div class="btn-group-vertical">
       		<a class="btn btn-default" href="<%=request.getContextPath()%>/backend/second_order/SaleOnOff.jsp?who=${admins}" role="button">二手車輛管理</a>
            <a class="btn btn-default" href="<%=request.getContextPath()%>/backend/second_order/listAllSecOrd.jsp" role="button">二手交易管理</a>
<%--             <a class="btn btn-default" href="<%=request.getContextPath()%>/backend/second_order/SaleOnOff.jsp" role="button">二手車交易管理</a> --%>
        </div>
         <%} %><%else {%>
        <div>
        	<button class="accordion accordionMenu accordion accordionMenuMenu" style="background-color:pink;">二手車管理系統</button>		
        </div>
       		<%} %>
       		
       		 <%if(adminisVO.getAuthno().equals("AC03") || adminisVO.getAuthno().equals("AC07")){%>
       <button class="accordion accordionMenu">會員管理系統</button>
        <div class="btn-group-vertical">
       		<a class="btn btn-default" href="<%=request.getContextPath()%>/backend/member/backendMember.jsp" role="button">會員管理</a>
            <a class="btn btn-default" href="<%=request.getContextPath()%>/backend/member/addMember.jsp" role="button">新增會員</a>
         </div>
        <%} %><%else {%>
        <div>
        	<button class="accordion accordionMenu accordion accordionMenuMenu" style="background-color:pink;">會員管理系統</button>		
        </div>
       		<%} %>
       		
       		<%if(adminisVO.getAuthno().equals("AC06") || adminisVO.getAuthno().equals("AC07")){%>
        <button class="accordion accordionMenu">活動企劃管理系統</button>
        <div class="btn-group-vertical">
            <a class="btn btn-default" href="<%=request.getContextPath()%>/backend/mes_board/listAllMesBoard.jsp" role="button">留言版管理</a>
            <a class="btn btn-default" href="<%=request.getContextPath()%>/backend/news/listAllNews.jsp" role="button">最新消息管理</a>
        </div>
        <%} %><%else {%>
        <div>
        	<button class="accordion accordionMenu accordion accordionMenuMenu" style="background-color:pink;">活動企劃管理系統</button>		
        </div>
       		<%} %>
       		
         <%if(adminisVO.getAuthno().equals("AC04") || adminisVO.getAuthno().equals("AC07")){%>
        <button class="accordion accordionMenu">後端管理系統</button>
        <div class="btn-group-vertical">
       		<a class="btn btn-default" href="<%=request.getContextPath()%>/backend/adminis/listAllAdminis.jsp" role="button">後端權限管理</a>
<!--             <a class="btn btn-default" href="#" role="button">推薦景點管理</a> -->
        </div>
         <%} %><%else {%>
        <div>
        	<button class="accordion accordionMenu accordion accordionMenuMenu" style="background-color:pink;">後端管理系統</button>		
        </div>
       		<%} %>
        <div class="btn-group-vertical"></div>
    </div>
</c:if> 
<!----------------------------------------------- 後端網頁的側邊欄  和權限控管的必要片段程式碼 -->
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
						<input readonly data-format="yyyy-MM-dd HH:mm:ss" type="text"
							class="form-control" name="manudate"
							value="<%=motorVO.getManudate()%>" /> <span class="add-on">
							<i data-time-icon="icon-time" data-date-icon="icon-calendar">
						</i>
						</span>
					</div>
				</div>

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
									${(motorVO.modtype==motorModelVO.modtype)?'selected':'' }>${motorModelVO.modtype}：${motorModelVO.brand}─
									${motorModelVO.name}
							</c:forEach>
						</select>
					</div>
				</div>

				<div class="form-group">
					<label class="control-label col-sm-2" for="status">狀態：</label>
					<div class="col-sm-10">
						<select name="status" class="form-control" id="status">

							<c:choose>
								<c:when test="${motorVO.status=='leasable'}">
									<option selected value="${motorVO.status}">可出租(${motorVO.status})
								</c:when>
								<c:when test="${motorVO.status=='unleasable'}">
									<option selected value="${motorVO.status}">暫停租賃(${motorVO.status})
								</c:when>
								<c:when test="${motorVO.status=='reserved'}">
									<option selected value="${motorVO.status}">已預約(${motorVO.status})
								</c:when>
								<c:when test="${motorVO.status=='occupied'}">
									<option selected value="${motorVO.status}">出租中(${motorVO.status})
								</c:when>
								<c:when test="${motorVO.status=='other'}">
									<option selected value="${motorVO.status}">其他(${motorVO.status})
								</c:when>
							</c:choose>

							<c:forEach var="s" items="${statusArray}">
								<c:if test="${motorVO.status!=s}">
									<c:choose>
										<c:when test="${s=='leasable'}">
											<option value="${s}">可出租(${s})
										</c:when>
										<c:when test="${s=='unleasable'}">
											<option value="${s}">暫停租賃(${s})
										</c:when>
										<c:when test="${s=='reserved'}">
											<option value="${s}">已預約(${s})
										</c:when>
										<c:when test="${s=='occupied'}">
											<option value="${s}">出租中(${s})
										</c:when>
										<c:when test="${s=='other'}">
											<option value="${s}">其他(${s})
										</c:when>
									</c:choose>
								</c:if>
							</c:forEach>
						</select>
					</div>
				</div>

				<div class="form-group">
					<label class="control-label col-sm-2" for="note">備註：</label>
					<div class="col-sm-10">
						<textarea class="form-control" id="note" rows="5" cols="70" style="resize:none;"
							name="note">${(motorVO.note == null) ? '' : motorVO.getNote()}</textarea>
					</div>
				</div>

				<div class="form-group">
					<div class="col-sm-2"></div>
					<div class="col-sm-10">
						<table>
							<tr>
								<td><input type="submit" class="btn btn-default"
									value="送出修改"> <input type="hidden" name="action"
									value="update"> <input type="hidden" name="motno"
									value="<%=motorVO.getMotno()%>"> <input type="hidden"
									name="requestURL"
									value="<%=request.getParameter("requestURL")%>"> <!--接收原送出修改的來源網頁路徑後,再送給Controller準備轉交之用-->
									<input type="hidden" name="whichPage"
									value="<%=request.getParameter("whichPage")%>"> <!--只用於:listAllMotor.jsp-->
									</FORM>
									<FORM METHOD="post" style="display: inline;"
										ACTION="<%=request.getContextPath()%>/backend/motor/motor4H.do">
										<input type="submit" name="reset" value="重置"
											class="btn btn-default" role="button"> <input
											type="hidden" name="motno" value="${motorVO.motno}">
										<input type="hidden" name="action" value="getOne_For_Update">
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
</html>
