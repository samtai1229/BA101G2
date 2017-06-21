<%@page import="java.sql.Timestamp"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.motor.model.*"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>車輛管理-AutoBike</title>
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
    <script src="Modified/motorNew.js"></script>
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
        </ul>
    </nav>
 <%--保留寫法     href="<%=request.getContextPath()%>/backend/backendRentOrd.jsp"  --%>
    <div class="col-xs-12 col-sm-2 leftBar">
        <img id="menuLogo" src="images/logo.jpg">
        <button class="accordion accordionMenu accordion accordionMenuMenu">總部管理系統</button>
        <div class="btn-group-vertical">
                
            <a class="btn btn-default" href="<%=request.getContextPath()%>/backend/motor/backendMotor.jsp"  role="button">車輛資料管理</a>
            <a class="btn btn-default" href="<%=request.getContextPath()%>/backend/motor_model/backendMotorModel.jsp"  role="button">車輛型號管理</a>           
            <a class="btn btn-default" href="#" role="button">車輛調度</a>            
<!--        <a class="btn btn-default" role="button" onclick="loadRentOrd()">租賃單管理</a> -->
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




<%
	MotorVO motorVO = (MotorVO) request.getAttribute("motorVO"); //MotorServlet.java (Concroller), 存入req的VO物件 (包括幫忙取出的VO, 也包括輸入資料錯誤時的VO物件)
%>  
<jsp:useBean id="motorSvc" scope="page" class="com.motor.model.MotorService"/>
<jsp:useBean id="mmSvc" scope="page" class="com.motor_model.model.MotorModelService"/>

    
	<div class="topTitle">
		<h1>車輛管理系統</h1>
	</div>      
    <div class="container-fluid">      

<!--block1 -->
	<div id="block1" class="col-xs-12 col-sm-4">
				
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
					              <a href="#tab2" aria-controls="tab2" role="tab" data-toggle="tab">新增</a>
					          </li>
					          <li role="presentation">
					              <a href="#tab3" aria-controls="tab3" role="tab" data-toggle="tab">修改</a>
					          </li>
					          <li role="presentation">
					              <a href="#tab4" aria-controls="tab4" role="tab" data-toggle="tab">刪除</a>
						          </li>
					          <li role="presentation">
					              <a href="#tab5" aria-controls="tab3" role="tab" data-toggle="tab">ByLoc</a>
					          </li>
<!-- 標籤面板：標籤區結束 -->	  </ul>				  

<!-- 標籤面板：內容區開始 -->     <div class="tab-content">
							  <div role="tabpanel" class="tab-pane active" id="tab1">
					          		<fieldset>
					                <legend>型號查詢</legend>
		<!--form功能 依型號查詢  -->
										<div class="InputForm">
											<label class="title">型號選單</label> 										
										
										<select name="modtype" onchange="queryMotorByModtype(this.value)">
						 							<c:forEach var="mmVO" items="${mmSvc.all}">
														<option value="${mmVO.modtype}">
															${mmVO.brand}${mmVO.name}
														</option>
													</c:forEach> 
												</select><br />
											</div>

										
					          		<legend>單筆查詢</legend>
		<!--form功能 單一查詢  -->
									<form method="post" action="motor.do">
										<div class="InputForm">
 											<label class="title">編號選單</label>
												<select name="motno"  onchange="queryMotorByMotorPK(this.value)">
						 							<c:forEach var="motorVO" items="${motorSvc.all}">
														<option value="${motorVO.motno}">
															${motorVO.motno}
															${motorVO.plateno}
														</option>
														
													</c:forEach> 
												</select><br />
										</div>
<!-- 										<div class="InputForm">
											<input type="hidden" name="action" value="getOne_For_Display">
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
								    <legend>車輛資料新增</legend>
									<form method="post" action="motor.do">
										<div class="InputForm">
											<label class="title">車輛型號</label> 
												<select name="modtype">
													<c:forEach var="mmVO" items="${mmSvc.all}">
														<option value="${mmVO.modtype}">
															${mmVO.brand} ${mmVO.name}
														</option>
													</c:forEach>
												</select><br />
										</div>
										<div class="InputForm">
											<label class="title">車牌號碼</label><input type="text"
												name="plateno" maxlength="8" placeholder="計8位字元，第4碼為 - 號" /><br>
										</div>
										<div class="InputForm">
											<label class="title">引擎號碼</label><input type="text"
												name="engno" maxlength="15" placeholder="請輸入引擎號碼" /><br>
										</div>
										<div class="InputForm">
											<label class="title">出廠日期</label><input type="text" name="manudate"
												class="datepicker2" /><br>
										</div>
										
										<div class="InputForm">
											<label class="title">里程數</label><input type="text" name="mile"/><br>
										</div>
														
										<div class="InputForm">
											<label class="title">據點編號</label><input type="text"
												name="locno" maxlength="10" placeholder="等有據點DAO再用選單" /><br>
										</div>
										<div class="InputForm">
											<label class="title">車輛狀態</label> 
												<select name="status">
														<option value="unleasable">不可租
														<option value="leasable">可出租
														<option value="dispatching">調度中
														<option value="secpause">二手未上架
														<option value="secreserved">二手已預約
														<option value="other">其它(備註)								
												</select><br />
										</div>								
										<div class="InputForm">
											<label class="title">備註</label>
											<textarea name="note" rows="1" cols="20" placeholder="請在這裡輸入"></textarea>
											<br />
										</div>
										<div class="InputForm">
											<input type="hidden" name="action" value="insert">
											<input type="submit" value="Submit" class="click" /> 
											<input type="reset" name="Reset" value="reset" class="click" />
										</div>
									</form>
									</fieldset>
									<!--end: form功能 新增 -->
							  </div><!--tab1 -->
							  
					          <div role="tabpanel" class="tab-pane" id="tab3">
		<!--form功能 修改  -->
					                <fieldset>
							  	    <legend>車輛資料修改</legend>
									<form method="post" action="motor.do">
										<div class="InputForm">
											<label class="title">車輛編號</label> 
												<select name="motno">
													<c:forEach var="motorVO" items="${motorSvc.all}">
														<option value="${motorVO.motno}">
															${motorVO.motno}
															${motorVO.plateno}
														</option>
													</c:forEach>
												</select><br />
										</div>			
										<div class="InputForm">
											<label class="title">車輛型號</label> 
												<select name="modtype">
													<c:forEach var="mmVO" items="${mmSvc.all}">
														<option value="${mmVO.modtype}">
															${mmVO.brand} ${mmVO.name}
														</option>
													</c:forEach>
												</select><br />
										</div>
										<div class="InputForm">
											<label class="title">車牌號碼</label><input type="text"
												name="plateno" maxlength="8" placeholder="計8位字元，第4碼為 - 號" /><br>
										</div>
										<div class="InputForm">
											<label class="title">引擎號碼</label><input type="text"
												name="engno" maxlength="15" placeholder="請輸入引擎號碼" /><br>
										</div>
										<div class="InputForm">
											<label class="title">出廠日期</label><input type="text" name="manudate"
												class="datepicker2" /><br>
										</div>
										
										<div class="InputForm">
											<label class="title">里程更新</label><input type="text" name="mile"/><br>
										</div>
														
										<div class="InputForm">
											<label class="title">據點編號</label><input type="text"
												name="locno" maxlength="10" placeholder="等有據點DAO再用選單" /><br>
										</div>	
										<div class="InputForm">
											<label class="title">車輛狀態</label> 
												<select name="status">
														<option value="unleasable">不可租
														<option value="leasable">出租已上架
														<option value="reserved">出租已預約
														<option value="occupied">出租中								
														<option value="dispatching">調度中
														<option value="secpause">二手車未上架
														<option value="seconsale">二手車上架中
														<option value="secreserved">二手車已預約
														<option value="secsaled">二手車已售出
														<option value="other">其它(備註)								
												</select><br />
										</div>	
										<div class="InputForm">
											<label class="title">備註</label>
											<textarea name="note" rows="1" cols="20" placeholder="請在這裡輸入"></textarea>
											<br />
										</div>
										<div class="InputForm">
											<input type="hidden" name="action" value="update">
											<input type="submit" value="Update" class="click" /> 
										</div>			
									</form>
									</fieldset>
									<!--end: form功能 修改 -->
							  
							  </div>
					          <div role="tabpanel" class="tab-pane" id="tab4">
					          		<fieldset>
					          		<legend>車輛資料刪除</legend>
		<!--form功能 刪除  -->
									<form method="post" action="motor.do">
										<div class="InputForm">
											<label class="title">待刪車輛</label> 
												<select name="motno">
						 							<c:forEach var="motorVO" items="${motorSvc.all}">
														<option value="${motorVO.motno}">
															${motorVO.motno}
															${motorVO.plateno}
														</option>
													</c:forEach> 
												</select><br />
										</div>
										<div class="InputForm">
											<input type="hidden" name="action" value="delete">
											<input type="submit" value="Delete" class="click" /> 
										</div>	
									</form>
									</fieldset>
							  </div>
							  <div role="tabpanel" class="tab-pane" id="tab5">
					          		<fieldset>
					          		<legend>依地點查詢</legend>
		<!--form功能 依地點查詢  -->
									<form method="post" action="motor.do">
										<div class="InputForm">
											<label class="title">???</label> 										
										</div>
										<div class="InputForm">
											<input type="hidden" name="action" value="delete">
											<input type="submit" value="Query" class="click" /> 
										</div>										
										
									</form>
									</fieldset>
							  </div>
<!--標籤面板 內容區結束 -->      </div>
<!--標籤面板 結束 -->		   </div>
<!--end block1 --> </div>	


<!--錨點:表格顯示   block3  --> <div id="block3" class="col-xs-12 col-sm-8">

					</div>
<!--end: block3 --> 	
<!--container--></div>	
 
     </div><!-- sm-10 rightHTML  -->
 
 
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script src="Modified/motorNew.js"></script>
<script>

</script>


</body>
</html>
