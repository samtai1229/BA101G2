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
    <title>AutoBike 交車</title>
    <meta name="description" content="">
    <meta name="keywords" content="">
    <link href="" rel="stylesheet">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
    <script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
	<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
    
    <link rel="stylesheet" href="Modified/backendHP_css.css">
    <link href="Modified/main.css" rel="stylesheet">
    <script src="Modified/motorKanli_js.js"></script>
    <script src="Modified/datepicker.js"></script>

</head>

<body>


		<div class="topTitle">
            <h1>交車系統</h1>
        </div>
        

        
<%
	RentOrdVO roVO = (RentOrdVO) request.getAttribute("roVO"); //MotorServlet.java (Concroller), 存入req的VO物件 (包括幫忙取出的VO, 也包括輸入資料錯誤時的VO物件)
%>       
        
         		<div class="container-fluid">       
<jsp:useBean id="roSvc" scope="page" class="com.rent_ord.model.RentOrdService"/>
<!--block1 --><div id="block1" class="col-xs-12 col-sm-4">
				
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
					              <a href="#tab5" aria-controls="tab2" role="tab" data-toggle="tab">?</a>
					          </li>
					          <li role="presentation">
					              <a href="#tab6" aria-controls="tab3" role="tab" data-toggle="tab">?</a>
					          </li>
<!-- 標籤面板：標籤區結束 -->	  </ul>				  

<!-- 標籤面板：內容區開始 -->     <div class="tab-content">
							  <div role="tabpanel" class="tab-pane active" id="tab1">
					          		<fieldset>
					          		<legend>租賃單查詢</legend>
	<!--form功能 依據點查詢  -->
									<form method="get" action="rentOrd.do">
										<div class="InputForm">
											<label class="title">據點查詢</label> 
												<select name="slocno">
														<option value="L000001">L000001</option>
														<option value="L000002">L000002</option>
														<option value="L000003">L000003</option>
														<option value="L000004">L000004</option>														
														<option value="L000005">L000005</option>
														<option value="L000006">L000006</option>														
												</select><br />
										</div>
										<div class="InputForm">
											<input type="hidden" name="action" value="get_for_lease_view">
											<input type="submit" value="query" class="click" /> 
										</div>	
									</form>
					          		
		<!--form功能 單一查詢  -->
									<form method="get" action="rentOrd.do">
										<div class="InputForm">
											<label class="title">單一查詢</label> 
												<select name="rentno" onchange="queryRentOrdByRentOrdPK(this.value)">
						 							<c:forEach var="roVO" items="${roSvc.all}">
														<option value="${roVO.rentno}">
															${roVO.rentno}
														</option>
													</c:forEach> 
												</select><br />
										</div>
										<div class="InputForm">
											<input type="hidden" name="action" value="query">
											<input type="submit" value="query" class="click" /> 
										</div>	
									</form>
									
<!--錨點div:單筆顯示   showSingleQueryResult  --> 
									<div id="showSingleQueryResult"></div>
									
									</fieldset>
							  </div>
							  <div role="tabpanel" class="tab-pane" id="tab2">
					          		<fieldset>
					          		<legend>依??查詢</legend>
		<!--form功能 ??  -->
									<form method="post" action="rentOrd.do">
										<div class="InputForm">
											<label class="title">???</label> 										
										</div>
									</form>
									</fieldset>
							  </div>
							  <div role="tabpanel" class="tab-pane" id="tab3">
					          		<fieldset>
					          		<legend>依??查詢</legend>
		<!--form功能 ?? -->
									<form method="post" action="rentOrd.do">
										<div class="InputForm">
											<label class="title">???</label> 										
										</div>
									</form>
									</fieldset>
							  </div>
<!--標籤面板 內容區結束 -->      </div>
<!--標籤面板 結束 -->		   </div>
<!--end block1 --> </div>	

<!-- block3 表格 --> <div id="block3" class="col-xs-12 col-sm-8">

					<%if(request.getAttribute("get_for_lease_view")!=null){ %>
						<jsp:include page="get_for_lease_view.jsp"/>
					<%}%>
 					

					</div>
<!--end: block3 --> 	
<!--container--></div>	

 
<!--RWD部分:下面兩行我拿掉一行和JQuery有關的script, 不然datepicker會衝到  -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>

</body>
</html>
