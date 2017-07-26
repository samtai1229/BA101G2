<%
	AdminisVO adminisvo = (AdminisVO) request.getAttribute("adminisVO"); //AdminisServlet.java (Concroller), 存入req的empVO物件 (包括幫忙取出的adminisVO, 也包括輸入資料錯誤時的adminisVO物件)
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.sql.Timestamp"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.news.model.*"%>
<!-- 後端網頁的側邊欄  和權限控管的必要片段程式碼 -->
<%@ page import="com.adminis.model.*"%>
<%  AdminisService as = new AdminisService();
	AdminisVO adminisVO= (AdminisVO)session.getAttribute("adminisVO");
     session.setAttribute("admins", adminisVO.getName());
     session.setAttribute("adminisVO", adminisVO);
     
     if(adminisVO==null){
   	  request.getRequestDispatcher("/backend/index.jsp").forward(request, response);
   	 }else{
   	  System.out.println("!!!!!!!!!!!"+adminisVO.getName());
   	     session.setAttribute("admins", adminisVO.getName());     
   	     session.setAttribute("adminisVO", adminisVO);
   	 } 
    
     
%>
<!-- 後端網頁的側邊欄  和權限控管的必要片段程式碼 -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>管理員資料修改</title>
<meta name="description" content="">
    <meta name="keywords" content="">
    <link href="" rel="stylesheet">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
    <link rel="stylesheet" href="Modified/backendHP_css.css">
    <link href="Modified/main.css" rel="stylesheet">
    <script src="Modified/motorKanli_js.js"></script>
    <script src="Modified/datepicker.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/backend/motor/js/motorMgmtHqSelectPage_css.css">
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">	
	<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
	<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>  	
	<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.11/jquery-ui.min.js"></script>
	<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css"/>	
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">     
	<link rel="stylesheet" href="<%=request.getContextPath()%>/backend/Modified/backendHP_css.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/backend/Modified/main.css" >	
</head>
<div id="popupcalendar" class="text"></div>
<body bgcolor='white'>

  <%--nav start --%>
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
    
<!------------------------------- 後端網頁的側邊欄  和權限控管的必要片段程式碼 -->
    <div class="col-xs-12 col-sm-2 leftBar">
     	<img id="menuLogo" src="<%=request.getContextPath()%>/backend/images/android_logo2.jpg">
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
        	<a class="btn btn-default" href="${pageContext.request.contextPath}/backend/motor/locListMotors.jsp" role="button">據點車輛管理</a>
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
            <a class="btn btn-default" href="<%=request.getContextPath()%>/backend/second_order/listAllSecOrd.jsp" role="button">二手車訂單管理</a>
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
        <button class="accordion accordionMenu"  style="background-color: #ddd;">後端管理系統</button>
         <div class="btn-group-vertical" style="display: block;">
       		<a class="btn btn-default" href="<%=request.getContextPath()%>/backend/adminis/listAllAdminis.jsp" role="button" style="background-color: #ddd;">後端權限管理</a>
<!--             <a class="btn btn-default" href="#" role="button">推薦景點管理</a> -->
        </div>
         <%} %><%else {%>
        <div>
        	<button class="accordion accordionMenu accordion accordionMenuMenu" style="background-color:pink;">後端管理系統</button>		
        </div>
       		<%} %>
        <div class="btn-group-vertical"></div>
    </div>
<!----------------------------------------------- 後端網頁的側邊欄  和權限控管的必要片段程式碼 -->

     <div class="col-xs-12 col-sm-10 rightHTML">
		<div class="topTitle">
            <h1>後端權限管理</h1>
        </div>

	
	<c:if test="${not empty errorMsgs}">
		<font color='red'>請修正以下錯誤:
			<ul>
				<c:forEach var="message" items="${errorMsgs}">
					<li>${message}</li>
				</c:forEach>
			</ul>
		</font>
	</c:if>

	<FORM METHOD="post" ACTION="adminis.do" name="form1">
		<table class="table table-hover table-condensed table-striped table-bordered" style="margin-left:1px;">
		<tr><th colspan="2"><h3>資料修改</h3></tr>
		
			<tr>
				<td>管理員編號:<font color=red><b>*<b></font></td>
				<td><font size="5"><%=adminisvo.getAdmno()%></font></td>
			</tr>
			<tr >
				<td>管理員姓名:</td>
				<td><input type="TEXT" name="name" size="100"   
					value="<%=adminisvo.getName()%>" /></td>
			</tr>
			<tr>
				<td>管理員帳號:</td>
				<td><input type="text" name="acc" size="100"
					value="<%=(adminisvo == null) ? " " : adminisvo.getAcc()%>"></td>
			</tr>
			<tr>
				<td>管理員密碼:</td>
				<td><input type="text" name="pw" size="100"
					value="<%=(adminisvo == null) ? " " : adminisvo.getPw()%>"></td>
			</tr>
			
			<jsp:useBean id="locationSvc" scope="page" class="com.location.model.LocationService" />
					<tr>
						<td>據點:<font color=red><b>*</b></font></td>
						<td><select size="1" name="locno">
								<c:forEach var="locationVO" items="${locationSvc.all}">
									<option value="${locationVO.locno}"
										${(adminisVO.locno==locationVO.locno)? 'selected':'' }>${locationVO.locname}
								</c:forEach>
						</select></td>
					</tr>
			
			
<!-- 			<tr> -->
<!-- 				<td>據點:</td> -->
<!-- 				<td><input type="text" name="locno" size="45" -->
<%-- 					value="<%=(adminisvo == null) ? " " : adminisvo.getLocno()%>"></td> --%>
<!-- 			</tr> -->
<%-- 			<jsp:useBean id="locationSvc" scope="page" class="com.location.model.LocationService" /> --%>
<!-- 			<tr> -->
<!-- 				<td>據點:<font color=red><b>*</b></font></td> -->
<!-- 				<td><select size="1" name="locno"> -->
<%-- 						<c:forEach var="locationVO" items="${locationSvc.all}"> --%>
<%-- 							<option value="${locationVO.locno}" --%>
<%-- 								${(adminisVO.locno==locationVO.locno)? 'selected':'' }>${locationVO.locname} --%>
<%-- 						</c:forEach> --%>
<!-- 				</select></td> -->
<!-- 			</tr> -->

			<jsp:useBean id="authcateSvc" scope="page"
				class="com.auth_cate.model.AuthCateService" />
			<tr>
				<td>權限類別編號:<font color=red><b>*</b></font></td>
				<td><select size="1" name="authno">
						<c:forEach var="authcateVO" items="${authcateSvc.all}">
							<option value="${authcateVO.authno}"
								${(adminisvo.authno==authcateVO.authno)? 'selected':'' }>${authcateVO.descr}
						</c:forEach>
				</select></td>
			</tr>
		</table>
		<br> <input type="hidden" name="action" value="update"> <input
			type="hidden" name="admno" value="<%=adminisvo.getAdmno()%>">
		<input type="submit" value="送出修改">
	</FORM>

</div>
<!----------------------------------------------- 後端網頁的側邊欄  和權限控管的必要片段程式碼 -->
	<!--RWD部分:下面兩行我拿掉一行和JQuery有關的script, 不然datepicker會衝到  -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="<%=request.getContextPath()%>/backend/Modified/motorKanli_js.js"></script>
    <script src="<%=request.getContextPath()%>/backend/Modified/indexNew.js"></script>
</body>
</html>