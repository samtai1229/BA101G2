<%@page import="java.sql.Timestamp"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.motor_model.model.*"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>車型管理-AutoBike</title>
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
            <h1>車型登錄管理系統</h1>
        </div>
        
        <div class="container-fluid">
        
<%
	MotorModelVO mmVO = (MotorModelVO) request.getAttribute("mmVO"); //Servlet.java (Concroller), 存入req的VO物件 (包括幫忙取出的VO, 也包括輸入資料錯誤時的VO物件)
%>         
<jsp:useBean id="mmSvc" scope="page" class="com.motor_model.model.MotorModelService"/>
			
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
					              <a href="#tab2" aria-controls="tab2" role="tab" data-toggle="tab">新增(pic?)</a>
					          </li>
					          <li role="presentation">
					              <a href="#tab3" aria-controls="tab3" role="tab" data-toggle="tab">修改</a>
					          </li>
					          <li role="presentation">
					              <a href="#tab4" aria-controls="tab4" role="tab" data-toggle="tab">刪除</a>
					          </li>
<!-- 標籤面板：標籤區結束 -->	  </ul>				  

<!-- 標籤面板：內容區開始 -->     <div class="tab-content">
							  <div role="tabpanel" class="tab-pane active" id="tab1">
					          		<fieldset>
					          		<legend>車型資料查詢</legend>
		<!--form功能 單一查詢  -->
									<form method="get" action="motorModel.do">
										<div class="InputForm">
											<label class="title">查詢</label> 
												<select name="modtype"  onchange="queryMotorTypeByModtype(this.value)">
						 							<c:forEach var="mmVO" items="${mmSvc.all}">
														<option value="${mmVO.modtype}">
															${mmVO.brand}
															${mmVO.name}
														</option>
														
													</c:forEach> 
												</select><br />
										</div>
<!-- 										<div class="InputForm">
											<input type="hidden" name="action" value="query">
											<input type="submit" value="query" class="click" /> 
										</div>	 -->
									</form>

<!--錨點div:單筆顯示   showSingleQueryResult  --> 
									<div id="showSingleQueryResult"></div>
									
									</fieldset>
							  </div>
					          <div role="tabpanel" class="tab-pane" id="tab2">
		<!--form功能 新增  -->
							     	<fieldset>
								    <legend>車型資料新增</legend>
									<form method="post" action="motorModel.do">
										<div class="InputForm">
											<label class="title">廠牌</label><input type="text"
												name="brand" maxlength="30" /><br>
										</div>
										<div class="InputForm">
											<label class="title">排氣量</label><input type="text"
												name="displacement" maxlength="10" placeholder="請輸入數字" /><br>
										</div>
										<div class="InputForm">
											<label class="title">車輛名稱</label><input type="text"
												name="engno" maxlength="30" /><br>
										</div>
										<div class="InputForm">
											<label class="title">租賃價格</label><input type="text" name="renprice" /><br>
										</div>
										
										<div class="InputForm">
											<label class="title">出售價格</label><input type="text" name="saleprice"/><br>
										</div>
														
										<div class="InputForm">
											<label class="title">車輛圖片</label><input type="text"
												name="locno" maxlength="10" placeholder="待改上傳方式" /><br>
										</div>
										<div class="InputForm">
											<input type="hidden" name="action" value="insert">
											<input type="submit" value="submit" class="click" /> 
											<input type="reset" name="reset" value="reset" class="click" />
										</div>
									</form>
									</fieldset>
									<!--end: form功能 新增 -->
							  </div><!--tab1 -->
							  
					          <div role="tabpanel" class="tab-pane" id="tab3">
		<!--form功能 修改  -->
					                <fieldset>
							  	    <legend>車型資料修改</legend>
									<form method="post" action="motorModel.do">
									
										<div class="InputForm">
											<label class="title">車輛型號</label> 
												<select name="modtype">
													<c:forEach var="mmVO" items="${mmSvc.all}">
														<option value="${mmVO.modtype}">
															${mmVO.modtype}
														</option>
													</c:forEach>
												</select><br />
										</div>
										<div class="InputForm">
											<label class="title">廠牌</label><input type="text"
												name="brand" maxlength="30" /><br>
										</div>
										<div class="InputForm">
											<label class="title">排氣量</label><input type="text"
												name="displacement" maxlength="10" placeholder="請輸入數字" /><br>
										</div>
										<div class="InputForm">
											<label class="title">車輛名稱</label><input type="text"
												name="engno" maxlength="30" /><br>
										</div>
										<div class="InputForm">
											<label class="title">租賃價格</label><input type="text" name="renprice" /><br>
										</div>
										
										<div class="InputForm">
											<label class="title">出售價格</label><input type="text" name="saleprice"/><br>
										</div>
														
										<div class="InputForm">
											<label class="title">車輛圖片</label><input type="text"
												name="locno" maxlength="10" placeholder="待改上傳方式" /><br>
										</div>
										<div class="InputForm">
											<input type="hidden" name="action" value="update">
											<input type="submit" value="update" class="click" /> 
										</div>			
									</form>
									</fieldset>
									<!--end: form功能 修改 -->
							  
							  </div>
					          <div role="tabpanel" class="tab-pane" id="tab4">
					          		<fieldset>
					          		<legend>車型資料刪除</legend>
		<!--form功能 刪除  -->
									<form method="post" action="motorModel.do">
										<div class="InputForm">
											<label class="title">待刪型號</label> 
												<select name="modtype">
													<c:forEach var="mmVO" items="${mmSvc.all}">
														<option value="${mmVO.modtype}">
															${mmVO.modtype}
														</option>
													</c:forEach>
												</select><br />
										</div>
										<div class="InputForm">
											<input type="hidden" name="action" value="delete">
											<input type="submit" value="delete" class="click" /> 
										</div>	
									</form>
									</fieldset>
							  </div>
<!--標籤面板 內容區結束 -->      </div>
<!--標籤面板 結束 -->		   </div>
<!--end block1 --> </div>	

<!-- block3 表格 --> <div id="block3" class="col-xs-12 col-sm-8">
						<table id="QueryTable">
							  <thead>	
									<tr class="QueryTable_TR">
										<th>車輛型號</th>
										<th>廠牌</th>		
										<th>排氣量</th>
										<th>車輛名稱</th>				
										<th>租賃價格</th>
										<th>出售價格</th>
										<th>車輛圖片</th>							
									</tr>
								  </thead>
								  <tbody>				  		
							 		<c:forEach var="mmVO" items="${mmSvc.all}">
										<tr class="QueryTable_TR">
											<td>${mmVO.modtype}</td>
											<td>${mmVO.brand}</td>
											<td>${mmVO.displacement}</td>
											<td>${mmVO.name}</td>
											<td>${mmVO.renprice}</td>
											<td>${mmVO.saleprice}</td>
											<td>pic?</td>							
										</tr>
									</c:forEach>							
							  </tbody>	 	  								
						</table>
					<script src="Modified/QueryTablePagination.js"></script> 		
					</div>
<!--end: block3 --> 	
<!--container--></div>	

 
<!--RWD部分:下面兩行我拿掉一行和JQuery有關的script, 不然datepicker會衝到  -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>

</body>
</html>