<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ page import="com.emt_dispatch.model.*"%>
<%@ page import="com.equipment.model.*"%>
<%@ page import="com.emt_cate.model.*"%>
<%@ page import="java.util.*"%>

<jsp:useBean id="now" scope="page" class="java.util.Date" />
<jsp:useBean id="locSvc" scope="page"
	class="com.location.model.LocationService" />
<jsp:useBean id="edSvc" scope="page"
	class="com.emt_dispatch.model.EmtDispatchService" />
<jsp:useBean id="emtSvc" scope="page"
	class="com.equipment.model.EquipmentService" />
<jsp:useBean id="ecSvc" scope="page"
	class="com.emt_cate.model.EmtCateService" />
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
	EmtDispatchVO edVO = (EmtDispatchVO) request.getAttribute("edVO");
	List<EmtCateVO> allEmtCates = ecSvc.getAll();
	pageContext.setAttribute("allEmtCates", allEmtCates);

	String[] progsArray = { "request", "rejected", "canceled", "dispatching", "dispatched", "closed", "other" };
	request.setAttribute("progsArray", progsArray);
	
	List<String> emtnoInList = (List<String>) request.getAttribute("emtnoInList");
%>

<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>裝備調度單審核 - updateEmtDispatch.jsp</title>

<meta name="keywords" content="">
<!-- CSS -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet"
	href="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.3.1/css/bootstrap-combined.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/backend/loc_motor_dispatch/js/updateMotorDispatchInput_css.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/backend/motor/js/bootstrap-datetimepicker.min.css">


<!-- Javascript -->
<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<script src="https://code.jquery.com/jquery.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script
	src="${pageContext.request.contextPath}/backend/motor/js/updateMotorInput_js.js"></script>
<script
	src="${pageContext.request.contextPath}/backend/motor/js/bootstrap-datetimepicker.js"></script>

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
        	<a class="btn btn-default" href="#" role="button">據點車輛管理</a>
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
			<h1>裝備調度單審核</h1>
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
			<FORM METHOD="post"
				ACTION="${pageContext.request.contextPath}/backend/emt_dispatch/ed.do"
				name="formUpdate" class="form-horizontal">
				<div class="form-group">
					<label class="control-label col-sm-2" for="edno">調度單號：</label>
					<div class="col-sm-10">
						<p class="form-control">${edVO.edno}</p>
					</div>
				</div>

				<div class="form-group">
					<label class="control-label col-sm-2" for="locno">申請據點：</label>
					<div class="col-sm-10">
						<c:choose>
  						<c:when test="${edVO.locno=='TPE00'}">
						<input type="text" readonly class="form-control" value="總部" />
						<input type="hidden" readonly class="form-control" id="locno" name="locno" value="<%=edVO.getLocno()%>" />
						</c:when>
						<c:when test="${edVO.locno=='TPE01'}">
						<input type="text" readonly class="form-control" value="台北車站" />
						<input type="hidden" readonly id="locno" name="locno" value="<%=edVO.getLocno()%>" />
						</c:when>
						<c:when test="${edVO.locno=='NTC01'}">
						<input type="text" readonly class="form-control" value="板橋車站" />
						<input type="hidden" readonly id="locno" name="locno" value="<%=edVO.getLocno()%>" />
						</c:when>
						<c:when test="${edVO.locno=='TXG01'}">
						<input type="text" readonly class="form-control" value="台中車站" />
						<input type="hidden" readonly id="locno" name="locno" value="<%=edVO.getLocno()%>" />
						</c:when>
						<c:when test="${edVO.locno=='TNN01'}">
						<input type="text" readonly class="form-control" value="台南車站" />
						<input type="hidden" readonly id="locno" name="locno" value="<%=edVO.getLocno()%>" />
						</c:when>
						<c:when test="${edVO.locno=='KHH01'}">
						<input type="text" readonly class="form-control" value="高雄車站" />
						<input type="hidden" readonly id="locno" name="locno" value="<%=edVO.getLocno()%>" />
						</c:when>
  					</c:choose>
					</div>
				</div>

				<div class="form-group">
					<label class="control-label col-sm-2" for="filldate">請求日期：</label>
					<div class="col-sm-10">
						<input type="text" readonly class="form-control" id="demanddate"
							name="demanddate"
							value="<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${edVO.demanddate}"/>" />
					</div>
				</div>

				<div class="form-group">
					<label class="control-label col-sm-2" for="closeddate">結案日期：</label>
					<div id="datetimepicker1" class="col-sm-10 input-append">
						<input data-format="yyyy-MM-dd HH:mm:ss" type="text"
							class="form-control" name="closeddate"
							value="<%=edVO.getCloseddate()%>" /> <span class="add-on">
							<i data-time-icon="icon-time" data-date-icon="icon-calendar">
						</i>
						</span>
					</div>
				</div>
				<table>
				<h3 style="margin-left:500px;">申請裝備</h3>
				<c:forEach var="allECs" items="${allEmtCates}">
					<%
						int count = 0;
					%>
					<c:forEach var="edListVO" items="${edVO.emtDispLists}">

						<c:if test="<%=count < 1%>">
							<c:if
								test="${allECs.ecno == edListVO.equipmentVO.emtCateVO.ecno}">
								<%
									++count;
								%>

								<div class="form-group">
									<label class="control-label col-sm-2" for="ecno"
										name="ecno">${allECs.ecno}：</label>

									<c:forEach var="emtVO"
										items="${emtSvc.getEmtsByEcnoByHib(edListVO.equipmentVO.emtCateVO.ecno)}">

										<c:set var="emtVO" value="${emtVO }" />
										<%
											EquipmentVO emtVO = (EquipmentVO) pageContext.getAttribute("emtVO");
										%>

										<label class="checkbox-inline"> <input
											class="form-check-input" type="checkbox" name="emtno"
											id="${emtVO.emtno}" value="${emtVO.emtno}"
											<%= emtnoInList.contains(emtVO.getEmtno())?"checked":"" %>>
											${emtVO.emtno}-
											<c:choose>
												<c:when test="${emtVO.locationVO.locno=='TPE00'}">
													總部
												</c:when>
												<c:when test="${emtVO.locationVO.locno=='TPE01'}">
													台北車站
												</c:when>
												<c:when test="${emtVO.locationVO.locno=='NTC01'}">
													板橋車站
												</c:when>
												<c:when test="${emtVO.locationVO.locno=='TXG01'}">
													臺中車站
												</c:when>
												<c:when test="${emtVO.locationVO.locno=='TNN01'}">
													台南車站
												</c:when>
												<c:when test="${emtVO.locationVO.locno=='KHH01'}">
													高雄車站
												</c:when>
											</c:choose>
										</label>
									</c:forEach>
								</div>
							</c:if>
						</c:if>
					</c:forEach>
				</c:forEach>
				</table>
				<div class="form-group">
					<label class="control-label col-sm-2" for="prog">處理進度：</label>
					<div class="col-sm-10">
						<select name="prog" class="form-control" id="prog">
							<c:choose>
								<c:when test="${edVO.prog=='request'}">
									<option selected value="${edVO.prog}">審核中
								</c:when>
									<c:when test="${edVO.prog=='rejected'}">
									<option selected value="${edVO.prog}">否決
									</c:when>
									<c:when test="${edVO.prog=='canceled'}">
									<option selected value="${edVO.prog}">已取消
									</c:when>
									<c:when test="${edVO.prog=='dispatched'}">
									<option selected value="${edVO.prog}">調度完成
									</c:when>
									<c:when test="${edVO.prog=='dispatching'}">
									<option selected value="${edVO.prog}">調度中
									</c:when>
									<c:when test="${edVO.prog=='closed'}">
									<option selected value="${edVO.prog}">結案
									</c:when>
									<c:when test="${edVO.prog=='other'}">
									<option selected value="${edVO.prog}">其他
									</c:when>
							</c:choose>
							
								<c:forEach var="progs" items="${progsArray}">
										<c:if test="${edVO.prog!=progs}">
											<c:choose>
												<c:when test="${progs=='request'}">
													<option value="${progs}">審核中
												</c:when>
												<c:when test="${progs=='rejected'}">
													<option value="${progs}">否決
												</c:when>
												<c:when test="${progs=='canceled'}">
													<option value="${progs}">已取消
												</c:when>
												<c:when test="${progs=='dispatched'}">
													<option value="${progs}">調度完成
												</c:when>
												<c:when test="${progs=='dispatching'}">
													<option value="${progs}">調度中
												</c:when>
												<c:when test="${progs=='closed'}">
													<option value="${progs}">結案
												</c:when>
												<c:when test="${progs=='other'}">
													<option value="${progs}">其他
												</c:when>
											</c:choose>
										</c:if>
									</c:forEach>
						</select>
					</div>
				</div>



				<div class="form-group">
					<div class="col-sm-2"></div>
					<div class="col-sm-10">
						<table>
							<tr>
								<td><input type="submit" class="btn btn-default"
										value="審核完成"> 
									<input type="hidden" name="action"
										value="update"> 
									<input type="hidden" name="edno"
										value="${edVO.edno}"> 
									<input type="hidden" name="requestURL"
										value="<%=request.getParameter("requestURL")%>"> <!--接收原送出修改的來源網頁路徑後,再送給Controller準備轉交之用-->
									<input type="hidden" name="whichPage"
										value="<%=request.getParameter("whichPage")%>"> <!--只用於:listAllMotor.jsp-->
		</FORM>
									<FORM METHOD="post" style="display: inline;"
										ACTION="<%=request.getContextPath()%>/backend/emt_dispatch/ed.do">
										<input type="submit" name="reset" value="重置"
											class="btn btn-default" role="button"> <input
											type="hidden" name="edno" value="${edVO.edno}"> <input
											type="hidden" name="action" value="getOne_For_Update">
									</FORM></td>
							</tr>
						</table>
					</div>
				</div>
		</div>
	</div>
</body>
<script type="text/javascript">

var Msg ='<%=request.getAttribute("getAlert")%>';
    if (Msg != "null") {
 		function undispatchAlert(){
 			alert("某申請裝備已無法調度，請重新選擇！");
 		} 
 	}

<c:forEach var="edListVO" items="${edVO.emtDispLists}">
	<c:forEach var="emtVO" items="${emtSvc.getEmtsByEcnoByHib(edListVO.equipmentVO.emtCateVO.ecno)}">

	var status = "${emtVO.status}"
	if(status == 'dispatching' || status == 'reserved' ||
		status == 'occupied' || status == 'seconsale' ||
		status == 'secpause' || status == 'secreserved' ||
		status == 'secsaled' || status == 'other'){
		
// 		$("#${emtVO.emtno}").removeAttr("checked");
// 		$("#${emtVO.emtno}").attr("disabled",true);
		$("#${emtVO.emtno}").parent().css("text-decoration", "line-through");

	}
	</c:forEach>
</c:forEach>

</script> 
<script type="text/javascript">window.onload = undispatchAlert;</script> 
</html>
