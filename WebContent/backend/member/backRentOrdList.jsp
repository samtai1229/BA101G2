<%@page import="java.sql.Timestamp"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<!-- 後端網頁的側邊欄  和權限控管的必要片段程式碼 -->
<%@ page import="com.adminis.model.*"%>
<%  AdminisService as = new AdminisService();
	AdminisVO adminisVO= (AdminisVO)session.getAttribute("adminisVO");
	System.out.println("!!!!!!!!!!!"+adminisVO.getName());
     session.setAttribute("admins", adminisVO.getName());
     session.setAttribute("adminisVO", adminisVO);
     
 	Map<String, String> roStatusMap = new HashMap<String, String>();
 	roStatusMap.put("unpaid", "待繳費");
 	roStatusMap.put("canceled", "取消");
 	roStatusMap.put("unoccupied", "準備交車");
 	roStatusMap.put("available", "今日取車");	
 	roStatusMap.put("noshow", "逾期未取");	
 	roStatusMap.put("noreturn", "待還車");	
 	roStatusMap.put("overtime", "逾期未還");	
 	roStatusMap.put("abnormalclosed", "異常結案");	
 	roStatusMap.put("closed", "正常結案");	
 	roStatusMap.put("other", "其他"); 
     
%>
<!-- 後端網頁的側邊欄  和權限控管的必要片段程式碼 -->
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">	

 	<link rel="stylesheet" href="<%=request.getContextPath()%>/backend/Modified/jquery_ui_1_10_3_theme.css"/>	
	<link rel="stylesheet" href="<%=request.getContextPath()%>/backend/Modified/twitter_bootstrap_3_3_7_min.css">        
	<link rel="stylesheet" href="<%=request.getContextPath()%>/backend/Modified/backendHP_css.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/backend/Modified/main.css" >
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/dataTables.min.css">
	
   <title>會員管理-AutoBike</title>
</head>

<style>

#myDiv {
  display: none;
  text-align: center;
}

th,td{
	height:20px;
}
td{
	vertical-align: middle!important;
	
}

table{
	border:1px;
}

/*自定*/
 th{
	/*不換行*/
	white-space:nowrap;
} 


form, input{
	padding:0px;
	border:0px;
	margin:0px;
}

#logoutBtn{
	float:right;
	margin-top:7px;
	margin-right:20px;
}
</style>

<body>

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
        </ul>
        <div id="logoutBtn">
	        <form method="post" action="<%=request.getContextPath()%>/admin.do?action=logout">
	        	<input type="submit" value="登出" class="btn btn-default">
				<b><%=adminisVO.getName() %></b>
	       </form>
       </div>
    </nav>
<%--nav end --%>
    
<!------------------------------- 後端網頁的側邊欄  和權限控管的必要片段程式碼 -->
    <div class="col-xs-12 col-sm-2 leftBar">
     	
        <img id="menuLogo" src="<%=request.getContextPath()%>/backend/images/android_logo2.jpg">
        <button class="accordion accordionMenu accordion accordionMenuMenu">總部管理系統</button> 
        <div class="btn-group-vertical">
         <%if(adminisVO.getAuthno().equals("AC01") || adminisVO.getAuthno().equals("AC07")){%>     
            <a class="btn btn-default" href="${pageContext.request.contextPath}/backend/motor/motorMgmtHqSelectPage.jsp"  role="button">車輛管理</a>
            <a class="btn btn-default" href="${pageContext.request.contextPath}/backend/loc_motor_dispatch/motorDispatchMgmtHq.jsp"  role="button">車輛調度管理</a>           
			<a class="btn btn-default" href="<%=request.getContextPath()%>/backend/rent_ord/backendRentOrd.jsp" role="button">租賃單管理</a>
            <a class="btn btn-default" href="${pageContext.request.contextPath}/backend/equipment/emtMgmtSelectPage.jsp" role="button">裝備管理</a>
            <a class="btn btn-default" href="<%=request.getContextPath()%>/backend/location/listAllLocation.jsp" role="button">據點管理</a>
         <%} %>  
        </div>
       
        <button class="accordion accordionMenu">據點管理系統</button>
        <div class="btn-group-vertical">
        <%if(adminisVO.getAuthno().equals("AC02") || adminisVO.getAuthno().equals("AC07")){%> 
            <a class="btn btn-default" href="#" role="button">據點車輛管理</a>
            <a class="btn btn-default" href="<%=request.getContextPath()%>/backend/rent_ord/lease.jsp"  role="button">交車管理</a>
          	<a class="btn btn-default" href="<%=request.getContextPath()%>/backend/rent_ord/return.jsp"  role="button">還車管理</a>
            <a class="btn btn-default" href="${pageContext.request.contextPath}/backend/loc_motor_dispatch/locMotorDispatchApply.jsp" role="button">車輛調度申請</a>
            <a class="btn btn-default" href="${pageContext.request.contextPath}/backend/emt_dispatch/locEmtDispatchApply.jsp" role="button">裝備申請</a>
         <%} %>
        </div>
         
        <button class="accordion accordionMenu">二手車管理系統</button>
        <div class="btn-group-vertical">
        <%if(adminisVO.getAuthno().equals("AC05") || adminisVO.getAuthno().equals("AC07")){%>
            <a class="btn btn-default" href="<%=request.getContextPath()%>/backend/second_order/SaleOnOff.jsp?who=${admins}" role="button">二手車輛管理</a>
            <a class="btn btn-default" href="<%=request.getContextPath()%>/backend/second_order/listAllSecOrd.jsp" role="button">二手車訂單管理</a>
            <a class="btn btn-default" href="<%=request.getContextPath()%>/backend/second_order/SaleOnOff.jsp" role="button">二手車交易管理</a>
         <%} %>
        </div>
       <button class="accordion accordionMenu">會員管理系統</button>
        <div class="btn-group-vertical">
        <%if(adminisVO.getAuthno().equals("AC03") || adminisVO.getAuthno().equals("AC07")){%>
             <a class="btn btn-default" href="<%=request.getContextPath()%>/backend/member/backendMember.jsp" role="button">會員管理</a>
            <a class="btn btn-default" href="<%=request.getContextPath()%>/backend/member/addMember.jsp" role="button">新增會員</a>
         <%} %>
        </div>
        <button class="accordion accordionMenu">活動企劃管理系統</button>
        <div class="btn-group-vertical">
        <%if(adminisVO.getAuthno().equals("AC06") || adminisVO.getAuthno().equals("AC07")){%>
<!--             <a class="btn btn-default" href="#" role="button">推播管理</a> -->
            <a class="btn btn-default" href="<%=request.getContextPath()%>/backend/mes_board/listAllMesBoard.jsp" role="button">留言版管理</a>
            <a class="btn btn-default" href="<%=request.getContextPath()%>/backend/news/news_select_page.jsp" role="button">最新消息管理</a>
         <%} %>
        </div>
        <button class="accordion accordionMenu">後端管理系統</button>
        <div class="btn-group-vertical">
        <%if(adminisVO.getAuthno().equals("AC04") || adminisVO.getAuthno().equals("AC07")){%>
           <a class="btn btn-default" href="<%=request.getContextPath()%>/backend/adminis/adm_select_page.jsp" role="button">後端權限管理</a>
<!--             <a class="btn btn-default" href="#" role="button">推薦景點管理</a> -->
         <%} %>
        </div>
        <div class="btn-group-vertical"></div>
    </div>
<!----------------------------------------------- 後端網頁的側邊欄  和權限控管的必要片段程式碼 -->
    
    
    
    <div class="col-xs-12 col-sm-10 rightHTML">
		<div class="topTitle">
            <h1>會員資料管理系統　－　租賃單查詢</h1>
        </div>
         		<div class="container-fluid"> 
       		
<div  class="col-xs-12 col-sm-12">
<jsp:useBean id="roSvc" scope="page" class="com.rent_ord.model.RentOrdService"/>
<jsp:useBean id="memSvc" scope="page" class="com.member.model.MemberService"/>
<jsp:useBean id="locSvc" scope="page" class="com.location.model.LocationService"/>  
<h4><mark>查詢會員:${memno}&nbsp;${memSvc.getOneMember(memno).memname}</mark></h4>
<table id="dataTable" class="table table-striped stripe hover">
	<thead>
		<tr>
		<th>明細查詢</th>		
		<th>租賃單編號</th>
		<th>車輛型號</th>
		<th>取車據點</th>
		<th>還車據點</th>
		<th>起始里程</th>
		<th>結束里程</th>
		<th>開始時間</th>
		<th>還車時間</th>
		<th>罰金</th>
		<th>租金</th>
		<th>評價</th>
		<th>租單狀態</th>
		<th>備註</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="roVO" items="${roSvc.all}" >
			<c:if test="${memno==roVO.memno}">
				<tr align='center' valign='middle'>
					<td>
						<form method="POST" target="print_popup" 
      				  		  action="<%=request.getContextPath()%>/backend/rent_ord/rentOrd.do" 
       						  onsubmit="window.open('about:blank','print_popup','width=1000,height=900');">
							<input type="hidden" name="rentno" value="${roVO.rentno}">
							<input type="hidden" name="action" value="query_for_member">
							<input type="submit" class="btn btn-default" value="查詢">
						</form>	
					</td>				
					<td>${roVO.rentno}</td>
					<td>${roVO.motorVO.motno}</td>
					<td>${locSvc.getOneLocation(roVO.slocno).locname}</td>
					<td>${locSvc.getOneLocation(roVO.rlocno).locname}</td>
					<td>${roVO.milstart}</td>	
					<td>${roVO.milend}</td>	
					<td><fmt:formatDate pattern = "yyyy/MM/dd" value = "${roVO.startdate}" /></td>
					<td><fmt:formatDate pattern = "yyyy/MM/dd" value = "${roVO.returndate}" /></td>				
					<td>${roVO.fine}</td>
					<td>${roVO.total}</td>
					<td>${roVO.rank}</td>
					
					<%-- show readable-status with map --%>
					<c:set scope="page" var="temp"><c:out value="${roVO.status}"/></c:set>
					<% String key = String.valueOf(pageContext.getAttribute("temp")); %>
					<td><%=roStatusMap.get(key)%></td>
					<td>${roVO.note}</td>
				</tr>
			</c:if>
		</c:forEach>
	</tbody>
</table>
			<p class="text-center">
				<a onclick="history.back()" class="btn btn-danger btn-lg">
					<i class="glyphicon glyphicon-arrow-up"></i>返回上頁
				</a>
			</p>
</div>
</div>
</div>	
	<script src="<%=request.getContextPath()%>/backend/Modified/jquery_1_10_1_min.js"></script>
	<script src="<%=request.getContextPath()%>/backend/Modified/jquery_ui_1_10_3.js"></script>
	<script src="<%=request.getContextPath()%>/backend/Modified/twitter_bootstrap_3_3_7_min.js"></script>	
	<script src="<%=request.getContextPath()%>/js/dataTables.min.js"></script>
    <script src="<%=request.getContextPath()%>/backend/rent_ord/Modified/rentOrdNew.js"></script>
    <script src="<%=request.getContextPath()%>/backend/rent_ord/Modified/motorKanli_for_ro.js"></script>
    <script src="<%=request.getContextPath()%>/backend/rent_ord/Modified/datepicker_for_ro.js"></script>
<script>
//table
	$(document).ready(function(){
		var datatableInstance = $('#dataTable').DataTable({
		});
		$('.showHideColumn').on('click',function(){
			var tableColumn = datatableInstance.column($(this).attr('data-columnindex'));
			tableColumn.visible(!tableColumn.visible());
		})
	});
</script>
</body>
</html>


