<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.equipment.model.*"%>
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
	EquipmentVO emtVO = (EquipmentVO) request.getAttribute("emtVO");
	String[] statusArray = { "unleasable", "leasable", "reserved", "occupied", "dispatching", "other" };
	request.setAttribute("statusArray", statusArray);
%>
<jsp:useBean id="ecSvc" scope="page" class="com.emt_cate.model.EmtCateService" />
<jsp:useBean id="locSvc" scope="page" class="com.location.model.LocationService" />

<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>裝備資料修改 - updateEmtInput.jsp</title>

<meta name="keywords" content="">
<!-- CSS -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/backend/Modified/twitter_bootstrap_3_3_7_min.css">
<link rel="stylesheet"
	href="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.3.1/css/bootstrap-combined.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/backend/equipment/js/addEmt_css.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/backend/motor/js/bootstrap-datetimepicker.min.css">


<!-- Javascript -->
<script src="<%=request.getContextPath()%>/backend/Modified/jquery_1_10_1_min.js"></script>
<script src="<%=request.getContextPath()%>/backend/Modified/jquery.js"></script>
<script
	src="<%=request.getContextPath()%>/backend/Modified/twitter_bootstrap_3_3_7_min.js"></script>
<script
	src="${pageContext.request.contextPath}/backend/emt_cate/js/addEc_js.js"></script>
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
	
	<!--右邊整塊HTML區塊 -->
	<div class="col-xs-12 col-sm-10 rightHTML">
		<div class="topTitle">
			<h1>裝備資料管理</h1>
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
			<FORM METHOD="post" ACTION="emt.do" name="formUpdate" class="form-horizontal">
				<div class="form-group">
					<label class="control-label col-sm-2" for="emtno">裝備編號：</label>
					<div class="col-sm-10">
						<p class="form-control">${emtVO.emtno}</p>
					</div>
				</div>

				<div class="form-group">
					<label class="control-label col-sm-2" for=ecno>裝備類別：</label>
					<div class="col-sm-10">
						<select name="ecno" class="form-control">
							<c:forEach var="ecVO" items="${ecSvc.all}">
								<option value="${ecVO.ecno}"
									${(emtVO.ecno==ecVO.ecno)?'selected':'' }>${ecVO.type}─${ecVO.ecno}
							</c:forEach>
						</select>
					</div>
				</div>

				<div class="form-group">
					<label class="control-label col-sm-2" for="locno">所在地：</label>
					<div class="col-sm-10">
						<select name="locno" class="form-control">
							<c:forEach var="locVO" items="${locSvc.all}">
								<option value="${locVO.locno}"
									${(emtVO.locno==locVO.locno)?'selected':'' }>${locVO.locname}
							</c:forEach>
						</select>
					</div>
				</div>

				<div class="form-group">
					<label class="control-label col-sm-2" for="purchdate">購入日期：</label>
					<div id="datetimepicker1" class="col-sm-10 input-append">
						<input readonly data-format="yyyy-MM-dd HH:mm:ss" type="text"
							class="form-control" name="purchdate"
							value="${emtVO.purchdate}" /> <span class="add-on">
							<i data-time-icon="icon-time" data-date-icon="icon-calendar">
						</i>
						</span>
					</div>
				</div>

				<div class="form-group">
					<label class="control-label col-sm-2" for="status">狀態：</label>
					<div class="col-sm-10">
						<select name="status" class="form-control" id="status">

							<c:choose>
								<c:when test="${emtVO.status=='leasable'}">
									<option selected value="${emtVO.status}">可出租(${emtVO.status})
								</c:when>
								<c:when test="${emtVO.status=='unleasable'}">
									<option selected value="${emtVO.status}">暫停租賃(${emtVO.status})
								</c:when>
								<c:when test="${emtVO.status=='reserved'}">
									<option selected value="${emtVO.status}">已預約(${emtVO.status})
								</c:when>
								<c:when test="${emtVO.status=='occupied'}">
									<option selected value="${emtVO.status}">出租中(${emtVO.status})
								</c:when>
								<c:when test="${emtVO.status=='other'}">
									<option selected value="${emtVO.status}">其他(${emtVO.status})
								</c:when>
							</c:choose>

							<c:forEach var="s" items="${statusArray}">
								<c:if test="${emtVO.status!=s}">
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
							name="note">${(emtVO.note == null) ? '' : emtVO.getNote()}</textarea>
					</div>
				</div>

				<div class="form-group">
					<div class="col-sm-2"></div>
					<div class="col-sm-10">
					
						<input type="submit" class="btn btn-default" value="送出修改">
						<input type="hidden" name="action" value="update"> 
						<input type="hidden" name="emtno" value="${emtVO.emtno}">
						<input type="hidden" name="requestURL" value="<%=request.getParameter("requestURL")%>">
						<input type="hidden" name="whichPage" value="<%=request.getParameter("whichPage")%>">
			</FORM>
					<FORM METHOD="post" style="display: inline;" ACTION="emt.do" >
						<input type="submit" name="reset" value="重置" class="btn btn-default" role="button">
						<input type="hidden" name="emtno" value="${emtVO.emtno}">
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
