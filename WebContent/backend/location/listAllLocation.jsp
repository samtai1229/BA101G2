<%@page import="java.sql.Timestamp"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.location.model.*"%>
<%@ page import="com.adminis.model.*" %>
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
/* 	LocationService locationSvc = new LocationService();
	List<LocationVO> list = locationSvc.getAll();
	pageContext.setAttribute("list",list); */
	LocationVO locationVO = (LocationVO) request.getAttribute("locationVO");
%>
<%-- <jsp:useBean id="locationSvc" scope="page" class="com.location.model.LocationService" /> --%>


<html>
<head>
	<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
	<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>	
	<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css"/>	
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/backend/Modified/backendHP_css.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/backend/Modified/main.css" >
	
	
	<title>據點管理</title>
</head>
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
<%--保留寫法     href="<%=request.getContextPath()%>/backend/backendRentOrd.jsp"  --%>
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
    <div class="col-xs-12 col-sm-10 rightHTML" id="demo">  <!-- 左方表單註解掉可用col-sm-12 -->

		<div class="topTitle">
            <h1>據點管理系統</h1>
        </div>
        
         		<div class="container-fluid">       
<jsp:useBean id="roSvc" scope="page" class="com.rent_ord.model.RentOrdService"/>
<jsp:useBean id="locSvc" scope="page" class="com.location.model.LocationService"/>
<jsp:useBean id="motorSvc" scope="page" class="com.motor.model.MotorService"/>
	
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
					              <a href="#tab2" aria-controls="tab2" role="tab" data-toggle="tab">新增</a>
					          </li>
					          <li role="presentation">
					              <a href="#tab3" aria-controls="tab3" role="tab" data-toggle="tab">修改</a>
					          </li>
					          <li role="presentation">
					              <a href="#tab4" aria-controls="tab4" role="tab" data-toggle="tab">刪除</a>
					          <!-- </li>
					          <li role="presentation">
					              <a href="#tab5" aria-controls="tab3" role="tab" data-toggle="tab">?</a>
					          </li>
					          <li role="presentation">
					              <a href="#tab6" aria-controls="tab4" role="tab" data-toggle="tab">?</a>
					          </li> -->
<!-- 標籤面板：標籤區結束 -->	  </ul>	

<!-- 標籤面板：內容區開始 -->     <div class="tab-content">
							  <div role="tabpanel" class="tab-pane active" id="tab1">
					          		<fieldset>
					          		<legend>據點查詢</legend>
		<!--form功能 單一查詢  -->
									<form method="get" action="location.do">
										<div class="InputForm">
											<label class="title">查詢</label> 
												<select name="locno" onchange="queryLocationByLocationPK(this.value)">
													<option value="clean">請選擇</option>
						 							<c:forEach var="locationVO" items="${locSvc.all}">
														<option value="${locationVO.locno}">
															${locationVO.locno}
														</option>
													</c:forEach> 
												</select><br />
										</div>
									</form>
									
<!--錨點div:單筆顯示   showSingleQueryResult  --> 

									<div id="showSingleQueryResult"></div>
																
									</fieldset>
							  </div>

								<div role="tabpanel" class="tab-pane" id="tab2">
		<!--form功能 新增  -->
							     	<fieldset>
								    <legend>據點新增</legend>
									<form method="post" action="location.do" enctype="multipart/form-data">
									
										<div class="InputForm">
											<label class="title">據點編號</label>
											<input type="text" name="locno" maxlength="10" /><br>
										</div>
										<div class="InputForm">
											<label class="title">據點名稱</label>
											<input type="text" name="locname" maxlength="10" /><br>
										</div>
										<div class="InputForm">
											<label class="title">據點電話</label>
											<input type="text" name="tel" maxlength="10" /><br>
										</div>
										<div class="InputForm">
										    <label class="title">據點地址</label>
										    <input type="text" name="addr" maxlength="50" /><br>
										</div>
										<div class="InputForm">
											<label class="title">據點照片</label>
											<input type="file" name="pic" /><br>
										</div>
										<div class="InputForm">
											<label class="title">據點地圖精度</label>
											<input type="text" name="lon" maxlength="10"/><br>
										</div>										
										<div class="InputForm">
											<label class="title">據點地圖緯度</label>
											<input type="text" name="lat" maxlength="10"/><br>
										</div>	
										<div class="InputForm">
											<label class="title">據點狀態</label>
											<label>preparing</label><br>
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
							  	    <legend>據點資料修改</legend>
									<form method="get" action="location.do" enctype="multipart/form-data">
									
										<div class="InputForm">
											<label class="title" id="locno">據點編號</label> 
											<select name="locno" onchange="queryLocationUpdate(this.value)">
													<option value="">請選擇</option>
					 							<c:forEach var="locationVO" items="${locSvc.all}" >
													<option value="${locationVO.locno}">
														${locationVO.locno}
													</option>
												</c:forEach> 
											</select><br />
										</div>	
										<%-- <div>
											<input type="hidden" name="action" value="update">
											<input type="hidden" name="requestURL" value="<%=request.getContextPath()%>">
										</div> --%>
										
																	
										<%-- <div class="InputForm">
											<label class="title">據點名稱</label>
											<input type="text" id="locname" name="locname" maxlength="10" value=""/><br>
										</div>
										<div class="InputForm">
											<label class="title">據點電話</label>
											<input type="text" id="tel" name="tel" maxlength="10" value="" /><br>
										</div>
										<div class="InputForm">
											<label class="title">據點地址</label>
											<input type="text" id="addr" name="addr" maxlength="100" value="" /><br>
										</div>
										<div class="InputForm">
											<label class="title">據點照片</label>
											<input type="file" id="pic" name="pic" value="" /><br>
										</div>
										<div class="InputForm">
											<label class="title">據點地圖精度</label>
											<input type="text" id="lon" name="lon" value="" /><br>
										</div>
										<div class="InputForm">
											<label class="title">據點地圖緯度</label>
											<input type="text" id="lat" name="lat" value="${locationVO.getLat}" /><br>
										</div>										
										<div class="InputForm">
											<label class="title">據點狀態</label> 
												<select name="status">
													<option value="open">營業中
													<option value="closed">已休業
													<option value="preparing">籌備中
												</select><br />
										</div>											
										<div class="InputForm">
											<input type="hidden" name="action" value="update">
											<input type="submit" value="submit" class="click" /> 
											<input type="reset" name="reset" value="reset" class="click" />
										</div> --%>
									</form>
									
									<div id="showOneResult"></div>
									
									</fieldset>
									<!--end: form功能 修改 -->
							  
							  </div>

					          <div role="tabpanel" class="tab-pane" id="tab4">
					          		<fieldset>
					          		<legend>據點刪除</legend>
		<!--form功能 刪除  -->
									<form method="post" action="location.do">
										<div class="InputForm">
											<label class="title">待刪據點</label> 
												<select name="locno">
													<option value="">請選擇</option>
						 							<c:forEach var="locationVO" items="${locSvc.all}">
														<option value="${locationVO.locno}">
															${locationVO.locno}
														</option>
													</c:forEach> 
												</select><br />
										</div>
										<div class="InputForm">
											<input type="hidden" name="action" value="delete">
											<input type="hidden" name="requestURL" value="<%= request.getServletPath()%>">
											<input type="submit" value="delete" class="click" /> 
										</div>	
									</form>
									</fieldset>
							  </div>

<!--標籤面板 內容區結束 -->      </div>
<!--標籤面板 結束 -->		   </div>
<!--end block1 --> </div>	

<!-- block3 表格 --> <div id="block3" class="col-xs-12 col-sm-8">
						<table id="QueryTable"  class="table table-bordred table-striped  table-hover">
							  <thead>	
									<tr class="QueryTable_TR">
										<th>據點編號</th>
										<th>據點地點</th>
										<th>據點電話</th>
										<th>據點地址</th>
										<th>據點相片</th>
										<th>據點精度</th>
										<th>據點緯度</th>
										<th>據點狀態</th>
										
																											
									</tr>
								  </thead>
								  <tbody>				  		
							 		<c:forEach var="locationVO" items="${locSvc.all}">
										<tr class="QueryTable_TR">
										
											<td>${locationVO.locno}</td>
											<td>${locationVO.locname}</td>
											<td>${locationVO.tel}</td>	
											<td>${locationVO.addr}</td>
											<td><img src="<%=request.getContextPath()%>/frontend/location/locReader.do?locno=${locationVO.locno}&card=pic" height="200" width="200"></td>
											<td>${locationVO.lon}</td>
											<td>${locationVO.lat}</td>
											<td>${locationVO.status}</td>
											<!-- 
											<td>
											<input type="hidden" name="action" value="update">
											<input type="submit" value="Update" class="click2" /> 
											</td>
											 -->
											<!-- 
											<td>${roVO.milstart}</td>
											<td>${roVO.milend}</td>
											<td>${roVO.note}</td>
											<td>
											<input type="hidden" name="action" value="delete">
											<input type="submit" value="Delete" class="click2" /> 
											</td> 
											-->								
										</tr>
									</c:forEach>							
							  </tbody>	 	  								
						</table>
					 		
					</div>
<!--end: block3 --> 	
<!--container--></div>	

    </div><!-- sm-10 rightHTML  -->




<!-- =============================================================================== -->
<%-- <table border='1' bordercolor='#CCCCFF' width='1500'>
	<tr>
		<th>據點編號</th>
		<th>據點地點</th>
		<th>據點電話</th>
		<th>據點地址</th>
		<th>據點相片</th>
		<th>據點精度</th>
		<th>據點緯度</th>
		<th>據點狀態</th>
		<th>修改據點</th>
		<th>刪除據點</th>
	</tr>
	<%@ include file="pages/page1.file" %> 
	<c:forEach var="locationVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
	<c:forEach var="locationVO" items="${list}">
		<tr align='center' valign='middle' ${(locationVO.locno==param.locno) ? 'bgcolor=#CCCCFF':''}><!--將修改的那一筆加入對比色而已-->
			<td>${locationVO.locno}</td>
			<td>${locationVO.locname}</td>
			<td>${locationVO.tel}</td>
			<td>${locationVO.addr}</td>
			<td><img src="<%=request.getContextPath()%>/frontend/location/locReader.do?locno=${locationVO.locno}&card=pic" height="200" width="200"></td>
			<td>${locationVO.lon}</td>
			<td>${locationVO.lat}</td>
			<td>${locationVO.status}</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/location/location.do">
			     <input type="submit" value="修改"> 
			     <input type="hidden" name="locno" value="${locationVO.locno}">
			     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
			    <input type="hidden" name="whichPage"	>               <!--送出當前是第幾頁給Controller-->
			     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
			</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/location/location.do">
			    <input type="submit" value="刪除">
			    <input type="hidden" name="locno" value="${locationVO.locno}">
			    <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
			    <input type="hidden" name="whichPage"	>               <!--送出當前是第幾頁給Controller-->
			    <input type="hidden" name="action"value="delete"></FORM>
			</td>
		</tr>
	</c:forEach>
</table> --%>
<%-- <%@ include file="pages/page2.file" %> --%>

	<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="<%=request.getContextPath()%>/backend/location/js/locationNew.js"></script>
    <script src="<%=request.getContextPath()%>/backend/rent_ord/Modified/motorKanli_for_ro.js"></script>
    <script src="<%=request.getContextPath()%>/backend/rent_ord/Modified/datepicker_for_ro.js"></script>
 	<script src="<%=request.getContextPath()%>/backend/Modified/paging.js"></script>
  <%--  <font color=blue>request.getServletPath():</font> <%= request.getServletPath()%><br>
   <font color=blue>request.getRequestURI(): </font> <%= request.getRequestURI()%> </b> --%>
</body>
</html>