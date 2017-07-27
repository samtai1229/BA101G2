<%@page import="oracle.jdbc.driver.DMSFactory"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.equipment.model.*"%>
<%@ page import="com.emt_cate.model.*"%>
<%@ page import="com.motor.model.*"%>
<%@ page import="com.motor_model.model.*"%>
<%@ page import="com.rent_ord.model.*"%>

<!DOCTYPE html>
<%-- 此頁練習採用 EL 的寫法取值 --%>

<jsp:useBean id="mdSvc" scope="page" class="com.motor_dispatch.model.MotorDispatchService" />
<jsp:useBean id="motorSvc" scope="page" class="com.motor.model.MotorService" />
<jsp:useBean id="mmSvc" scope="page" class="com.motor_model.model.MotorModelService" />
<jsp:useBean id="roSvc" scope="page" class="com.rent_ord.model.RentOrdService" />
<jsp:useBean id="locSvc" scope="page" class="com.location.model.LocationService" />

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
    List<MotorModelVO> list = mmSvc.getAllByHib();
    pageContext.setAttribute("list",list);
%>


<html>

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>據點車輛申請 - locMotorDispatchApply.jsp</title>
<meta name="description" content="">
<meta name="keywords" content="">

<!-- CSS -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/backend/Modified/twitter_bootstrap_3_3_7_min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/backend/loc_motor_dispatch/js/locMotorDispatchApply_css.css">
<link rel="stylesheet"
	href="http://www.jacklmoore.com/colorbox/example1/colorbox.css">

<!-- JS -->
<script src="<%=request.getContextPath()%>/backend/Modified/jquery.js"></script>
<script
	src="<%=request.getContextPath()%>/backend/Modified/twitter_bootstrap_3_3_7_min.js"></script>
<script
	src="${pageContext.request.contextPath}/backend/loc_motor_dispatch/js/locMotorDispatchApply_js.js"></script>
<script type="text/javascript" src="http://www.jacklmoore.com/colorbox/jquery.colorbox.js"></script>
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
<!-- 	左邊選單表 -->
	<div class="col-xs-12 col-sm-2 leftBar">
		<img id="logo"
			src="${pageContext.request.contextPath}/backend/images/android_logo2.jpg">
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
        <button class="accordion accordionMenu"  style="background-color: #ddd;">據點管理系統</button>
        <div class="btn-group-vertical" style="display: block;">
        	<a class="btn btn-default" href="${pageContext.request.contextPath}/backend/motor/motorMgmtLocSelectPage.jsp" role="button">據點車輛管理</a>
        	<a class="btn btn-default" href="${pageContext.request.contextPath}/backend/equipment/locEmtMgmtSelectPage.jsp" role="button">據點裝備管理</a>
            <a class="btn btn-default" href="<%=request.getContextPath()%>/backend/rent_ord/lease.jsp"  role="button">交車管理</a>
          	<a class="btn btn-default" href="<%=request.getContextPath()%>/backend/rent_ord/return.jsp"  role="button">還車管理</a>
            <a class="btn btn-default" href="${pageContext.request.contextPath}/backend/loc_motor_dispatch/locMotorDispatchApply.jsp" role="button" style="background-color: #ddd;">車輛調度申請</a>
            <a class="btn btn-default" href="${pageContext.request.contextPath}/backend/emt_dispatch/locEmtDispatchApply.jsp" role="button" >裝備申請</a>
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
	
<!-- 	右邊HTML區塊 -->
	<div class="col-xs-12 col-sm-10 rightHTML" >
		<div class="topTitle">
			<h1>據點車輛調度申請</h1>
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
			<!--搜尋列 -->
			<div class="searchBar">
				<table>
					<tr>
						<td>
							<FORM METHOD="post" style="display: inline;"
								ACTION="${pageContext.request.contextPath}/backend/motor_dispatch/md.do">
								<div class="form-group" style="display: inline;">
									<div class="col-sm-4">
										<label class="control-label" for="locno">選取所在據點：</label>
									</div>
									<div class="col-sm-7">
										<select class="form-control" name="locno" id="locSelect"
											style="display: inline;">
											<c:forEach var="locVO" items="${locSvc.getAll()}">
												<option value="${locVO.locno}"
													${(locVO.locno==param.locno)?'selected':'' }>${locVO.locname}</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-1">
										<input type="submit" class="btn btn-default" value="確認">
										<input type="hidden" name="action" value="chooseLoc">
										<input type="hidden" name="locno" value="${locVO.locno}">
										<input type="hidden" name="requestURL"
											value="<%=request.getParameter("requestURL")%>">
									</div>
								</div>
							</form>

							<FORM METHOD="post" style="display: inline;"
								ACTION="${pageContext.request.contextPath}/backend/motor_dispatch/md.do">
								<div class="form-group" style="display: inline;">
									<div class="col-sm-4">
										<label class="control-label" for="locno">查詢所在據點申請單據：</label>
									</div>
									<div class="col-sm-7">
										<select class="form-control" name="locno"
											id="locMotorDispListSelect" style="display: inline;">
											<c:forEach var="locVO" items="${locSvc.getAll()}">
												<option value="${locVO.locno}"
													${(locVO.locno==param.locno)?'selected':'' }>${locVO.locname}</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-1">
										<input type="submit" class="btn btn-default" value="查詢">
										<input type="hidden" name="action" value="chooseLocForList">
										<input type="hidden" name="locno" value="${locVO.locno}">
										<input type="hidden" name="requestURL"
											value="<%=request.getParameter("requestURL")%>">
									</div>
								</div>
							</form>
						</td>
					</tr>
				</table>
			</div>
			<!--搜尋列結束 -->
			
			<div id="outer-wrapper">
				<div id="header-wrapper">
					<ul class="switch-button">
    					<li id="sw_01" title="圖文顯示" style="background-image: url(${pageContext.request.contextPath}/backend/loc_motor_dispatch/images/style-button.gif);"><span>圖文顯示</span></li>
    					<li id="sw_02" title="圖片顯示" style="background-image: url(${pageContext.request.contextPath}/backend/loc_motor_dispatch/images/style-button.gif);"><span>圖片顯示</span></li>
    					<li id="sw_03" title="文字顯示" style="background-image: url(${pageContext.request.contextPath}/backend/loc_motor_dispatch/images/style-button.gif);"><span>文字顯示</span></li>
					</ul>
				</div>

	<c:forEach var="mS" items="${modtypeSet}">
				<div class="content-wrapper" >
					<dl class="vw_01">
					<dd>
						<img class="motorImg"
							src="<%=request.getContextPath()%>/backend/motor_model/mmReader.do?modtype=${mS.modtype}">
						<div class="main">
							<table class="mmTable">
               				<tr class="mmList">
               					<td class="modtype">
               					<c:forEach var="mmVO" items="${list}">
               						<c:if test="${mmVO.modtype == mS.modtype }">
               							${mmVO.modtype}
                   					</c:if>
                   				</c:forEach>
               					</td>
               				<td>
               				<c:forEach var="mmVO" items="${list}">
               					<c:if test="${mmVO.modtype == mS.modtype }">
               							${mmVO.brand}
                   				</c:if>
                   			</c:forEach>
               				</td>
               				<td>
               				<c:forEach var="mmVO" items="${list}">
               					<c:if test="${mmVO.modtype == mS.modtype }">
               							${mmVO.name}
                   				</c:if>
                   			</c:forEach>
               				</td>
               				<td>
               				<c:forEach var="mmVO" items="${list}">
               					<c:if test="${mmVO.modtype == mS.modtype }">
               							${mmVO.displacement}
                   				</c:if>
                   			</c:forEach>
               				</td>
               				<td>
               				<c:forEach var="mmVO" items="${list}">
               					<c:if test="${mmVO.modtype == mS.modtype }">
               							${mmVO.renprice}
                   				</c:if>
                   			</c:forEach>
               				</td>
               					</tr>
								<tr class="motorAmount">
								<td>
									<c:forEach var="dM" items="${dispatchableMotorsInList}">
									
	                    				<%! int count=0;%>
	                   					<c:if test="${dM.motorModelVO.modtype == mS.modtype}">
		                    				<% count++;%>
	                   					</c:if>
	                				</c:forEach>
								</td>
                				<td>
                					<label for="aa" class="control-label col-sm-3">選擇數量</label>
                				</td>
                				<td>
                					<input type="number" class="count form-control" name="" min="0" max="<%= count %>" style="display: inline;">
                					<c:forEach var="dM" items="${dispatchableMotorsInList}">
                					<c:if test="${dM.motorModelVO.modtype == mS.modtype}">
		                    				<input type="hidden" class="motno" value="${dM.motno}">
	                   				</c:if>
	                   				</c:forEach>
                				</td>
                				<td>
                				/<%= count %>
                				</td>
                				<td> 
                					<input type="button" name="addButton" class="addButton btn btn-default" value="加入調度單">
                				</td>
               					<%  count=0;%>
               					</tr>
               				</table>
						</div>
					</dd>
					</dl>
				</div>
				<div class="clear"></div>
	</c:forEach>

</div>
		</div><!--container結束-->
	</div><!--右邊HTML區塊結束 -->
	
	
	<footer id="footer">	
	<div id="cartDiv">
	<div id="newMotor">
		<img src="" id="cartImage" style="margin-left:150px;">
		<span id="spanName"></span>
		<span id="motorAmt"></span>
<!--     		<img src="imgs/formosa.jpg" id="cartImage"> -->
<!-- 			<span>Formosa</span>  -->
<!--  			<span>5000</span>	 -->
    </div>
    <span class="checkout">check out</span>
    <div style="margin-bottom:300px;"></div><!-- 把下面的div壓下去好看不到用的 -->
   	<div id="dispatchListContent">
   	<FORM METHOD="post" ACTION="${pageContext.request.contextPath}/backend/motor_dispatch/md.do" name="formDispatch" id="formDispatch" class="form-horizontal">
    	<table id="dispatchListTable" class="table">
<!--     		<tr class="contentTr"> -->
<!--     			<td class = "contentTd"> -->
<!--     				<div class="form-group"> -->
<!-- 						<label class="contentName control-label col-sm-3" for="modtype">MM101</label> -->
<!-- 						<input type="hidden" name="modtype" value="MM101"> -->
<!-- 						<div class="col-sm-9"> -->
<!-- 							<input type="text" class="contentCount form-control" id="modtype" name="modtype" value="1" /> -->
<!-- 						</div> -->
<!-- 					</div> -->
<!--     			</td> -->
<!--     		</tr> -->
			<tr><td>
			<input type="hidden" name="action" value="insert"> 
			<input type="hidden" name="locno" value="${param.locno}">
			<input type="hidden" name="requestURL" value="<%=request.getParameter("requestURL")%>">
			<input type="submit" class="btn btn-default" value="確認提出">
			</td></tr>
    	</table>
    </form>
    </div>
    <div id="dispatchListButton">
    	<span class="contentTotal"></span>
    </div>
    <div style="clear:both;"></div>
    </div>
</footer>
</body>

</html>
