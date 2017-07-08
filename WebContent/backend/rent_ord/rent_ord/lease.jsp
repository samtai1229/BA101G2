<%@page import="java.sql.Timestamp"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.rent_ord.model.*"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">	

	<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
	<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>  	
	<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.11/jquery-ui.min.js"></script>
	<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css"/>	
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">     
	<link rel="stylesheet" href="<%=request.getContextPath()%>/backend/Modified/backendHP_css.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/backend/Modified/main.css" >

    <title>AutoBike 交車</title>
</head>
<style>
p{
margin-top:5px;

}

</style>

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
        </ul>
    </nav>
    <div class="col-xs-12 col-sm-2 leftBar">
        <img id="menuLogo" src="<%=request.getContextPath()%>/backend/images/android_logo2.jpg">
        <button class="accordion accordionMenu accordion accordionMenuMenu">總部管理系統</button>
        <div class="btn-group-vertical">
                
            <a class="btn btn-default" href="<%=request.getContextPath()%>/backend/motor/backendMotor.jsp"  role="button">車輛資料管理</a>
            <a class="btn btn-default" href="<%=request.getContextPath()%>/backend/motor_model/backendMotorModel.jsp"  role="button">車輛型號管理</a>           
            <a class="btn btn-default" href="#" role="button">車輛調度</a>
			<a class="btn btn-default" href="<%=request.getContextPath()%>/backend/rent_ord/backendRentOrd.jsp" role="button">租賃單管理</a>
            <a class="btn btn-default" href="#" role="button">裝備管理</a>
            <a class="btn btn-default" href="#" role="button">裝備調度</a>
            <a class="btn btn-default" href="#" role="button">據點管理</a>
        </div>
        <button class="accordion accordionMenu">據點管理系統</button>
        <div class="btn-group-vertical">
            <a class="btn btn-default" href="#" role="button">據點車輛管理</a>
            <a class="btn btn-default" href="<%=request.getContextPath()%>/backend/rent_ord/lease.jsp"  role="button">交車管理</a>
          	<a class="btn btn-default" href="<%=request.getContextPath()%>/backend/rent_ord/return.jsp"  role="button">還車管理</a>
            <a class="btn btn-default" href="#" role="button">車輛調度申請</a>
            <a class="btn btn-default" href="#" role="button">車輛保養/維修管理</a>
            <a class="btn btn-default" href="#" role="button">據點裝備管理</a>
            <a class="btn btn-default" href="#" role="button">裝備申請</a>
        </div>
        <button class="accordion accordionMenu">二手車管理系統</button>
        <div class="btn-group-vertical">
            <a class="btn btn-default" href="#" role="button">二手車輛管理</a>
            <a class="btn btn-default" href="#" role="button">二手車訂單管理</a>
            <a class="btn btn-default" href="#" role="button">二手車交易管理</a>
        </div>
        <button class="accordion accordionMenu">會員管理系統</button>
        <div class="btn-group-vertical"></div>
        <button class="accordion accordionMenu">活動企劃管理系統</button>
        <div class="btn-group-vertical">
            <a class="btn btn-default" href="#" role="button">推播管理</a>
            <a class="btn btn-default" href="#" role="button">留言版管理</a>
            <a class="btn btn-default" href="#" role="button">最新消息管理</a>
        </div>
        <button class="accordion accordionMenu">後端管理系統</button>
        <div class="btn-group-vertical">
            <a class="btn btn-default" href="#" role="button">後端權限管理</a>
            <a class="btn btn-default" href="#" role="button">推薦景點管理</a>
            <a class="btn btn-default" href="#" role="button">後端登入管理</a>
        </div>
        <div class="btn-group-vertical"></div>
    </div>
    <div class="col-xs-12 col-sm-10 rightHTML" id="demo">



		<div class="topTitle">
            <h1>交車系統</h1>
        </div>
        

        
<%
	RentOrdVO roVO = (RentOrdVO) request.getAttribute("roVO"); //MotorServlet.java (Concroller), 存入req的VO物件 (包括幫忙取出的VO, 也包括輸入資料錯誤時的VO物件)
%>       
   
         		<div class="container-fluid">       

<!--block1 --><div id="block1" class="col-xs-12 col-sm-3">
				
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
					
<!-- 標籤面板：開始 -->					
<!-- 標籤面板：標籤區開始 --><div role="tabpanel">
					      <ul class="nav nav-tabs" role="tablist">
					          <li role="presentation" class="active">
					              <a href="#tab1" aria-controls="tab1" role="tab" data-toggle="tab">查詢</a>
					          </li>
					          <li role="presentation">
					              <a href="#tab2" aria-controls="tab2" role="tab" data-toggle="tab">租賃單狀態說明</a>
					          </li>
<!-- 標籤面板：標籤區結束 -->	  </ul>				  

							  <jsp:useBean id="locSvc" scope="page" class="com.location.model.LocationService"/>
	<!-- 標籤面板：內容區開始 -->    <div class="tab-content">
							  <div role="tabpanel" class="tab-pane active" id="tab1">
					          		<fieldset>
		     						<legend>租賃單查詢</legend><!--form功能 依據點查詢  -->
										<div class="InputForm">
											<label class="title">據點查詢</label> 
											<select name="slocno" onchange="queryRentOrdBySlocno(this.value)">
												<option value="">==請選擇==</option>
					 							<c:forEach var="locVO" items="${locSvc.all}">
						 							<c:if test="${locVO.locno != 'TPE00'}"> 
														<option value="${locVO.locno}">${locVO.locname}營業所</option>
													</c:if>	
												</c:forEach> 															
											</select><br />
										</div>

<jsp:useBean id="roSvc" scope="page" class="com.rent_ord.model.RentOrdService"/>					          		
		<!--form功能 單一查詢  -->
										<div class="InputForm">
											<label class="title">單一查詢</label> 
											<select name="rentno" onchange="queryRentOrdByRentOrdPK(this.value)">
					 							<c:forEach var="roVO" items="${roSvc.all}">
													<option value="${roVO.rentno}">
														${roVO.rentno}
													</option>
												</c:forEach> 
											</select><br/>
										</div>
									<div id="showSingleQueryResult"></div><!--錨點div:單筆顯示   showSingleQueryResult  --> 
									</fieldset>
							  </div>
							  
							  <div role="tabpanel" class="tab-pane" id="tab2"><!--文字敘述  狀態說明-->
					          		<fieldset>
						          		<legend>租賃單狀態說明</legend>
						          		<blockquote>點擊右租賃單狀態列button進行租賃單查詢/結案。</blockquote>
						          		<input type="submit" value="逾期未取" class="btn btn-danger btn-lg"/>
						          		<blockquote>已過客戶取車時間，請據點人員確認租賃單後進行異常結案。</blockquote>
						          		<input type="submit" value="等待取車" class="btn btn-warning btn-lg"/>
						          		<blockquote>今日客戶會來取車，請注意車輛與裝備狀況，並與客戶保持連繫，確保取車流程能順利執行。</blockquote>
						          		<input type="submit" value="訂單取消" class="btn btn-info btn-lg"/>
						          		<blockquote>租賃單逾期匯款或客戶主動取消，請據點人員確認租賃單後進行異常結案。</blockquote>	
						          		<input type="submit" value="完成繳費" class="btn btn-success btn-lg"/>
						          		<blockquote>客戶完成繳費，請留意取車日期並請據點人員重新確認車輛狀況、裝備數量、裝備狀況。</blockquote>
						          		<input type="submit" value="尚未繳費" class="btn btn-primary btn-lg"/>
						          		<blockquote>新租賃單成立，客戶尚未匯款。</blockquote>
									</fieldset>
							  </div>
<!--標籤面板 內容區結束 -->      </div>
<!--標籤面板 結束 -->		   </div>
<!--end block1 --> </div>	
<!-- block3 表格 --> <div id="block3" class="col-xs-12 col-sm-9">
						<img src="<%=request.getContextPath()%>/backend/images/android_logo.png" id="PageLogo"
						 style="display:block; margin:auto;">
					</div>
<!--end: block3 --> 	
<!--container--></div>	

		
    </div><!-- sm-10 rightHTML  -->
 
<!--RWD部分:下面兩行我拿掉一行和JQuery有關的script, 不然datepicker會衝到  -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script src="<%=request.getContextPath()%>/backend/rent_ord/Modified/datepicker_for_ro.js"></script>
 	<script src="<%=request.getContextPath()%>/backend/rent_ord/Modified/motorKanli_for_ro.js"></script>    
    <script src="<%=request.getContextPath()%>/backend/rent_ord/Modified/rentOrdNew.js"></script>
 	<script src="<%=request.getContextPath()%>/backend/rent_ord/Modified/paging_for_ro.js"></script>
</body>
</html>
