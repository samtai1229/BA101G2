<%@page import="java.sql.Timestamp"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.member.model.*"%>
<%@ page import="com.rent_ord.model.*"%>
<%@ page import="com.emt_list.model.*"%>
<%@ page import="com.equipment.model.*"%>
<html>
<style type="text/css">

.custab{
    border: 1px solid #ccc;
    padding: 5px;
    margin: 5% 0;
    box-shadow: 3px 3px 2px #ccc;
    transition: 0.5s;
    }
.custab:hover{
    box-shadow: 3px 3px 0px transparent;
    transition: 0.5s;
    }


		/**** BASE ****/
body {
    color: #888;   
}
a {
    color: #03a1d1;
    text-decoration: none!important;
    font-size:18px;
}

/**** LAYOUT ****/
.list-inline>li {
    padding: 0 10px 0 0;
}
.container-pad {
    padding: 30px 15px;
}


/**** MODULE ****/
.bgc-fff {
    background-color: #fff!important;
}
.box-shad {
    -webkit-box-shadow: 1px 1px 0 rgba(0,0,0,.2);
    box-shadow: 1px 1px 0 rgba(0,0,0,.2);
}
.brdr {
    border: 1px solid #ededed;
}

/* Font changes */
.fnt-smaller {
    font-size: .9em;
}
.fnt-lighter {
    color: #bbb;
}

/* Padding - Margins */
.pad-10 {
    padding: 10px!important;
}
.mrg-0 {
    margin: 0!important;
}
.btm-mrg-10 {
    margin-bottom: 10px!important;
}
.btm-mrg-20 {
    margin-bottom: 20px!important;
}

/* Color  */
.clr-535353 {
    color: #535353;
}


/**** TO top MODULE ****/
#myBtn {
  display: none;
  position: fixed;
  bottom: 20px;
  right: 30px;
  z-index: 99;
  border: none;
  outline: none;
  background-color: red;
  color: white;
  cursor: pointer;
  padding: 15px;
  border-radius: 10px;
}

#myBtn:hover {
  background-color: #555;
}
/****end TO top MODULE ****/


/**** 自已加的  ****/
li{
	margin-top:10px;
	font-size:16px;
}

/* .btn{
	position:relative;
	margin-top:0%;
} */

body {
       background: linear-gradient( rgba(0, 0, 0, 0.3), rgba(0, 0, 0, 0.3) ), url(/BA101G2/img/header1.jpg) no-repeat center center fixed; 
	    -webkit-background-size: cover;
	    -moz-background-size: cover;
	    -o-background-size: cover;
	    background-size: cover;
}

.topdiv{

	margin-top:80px;
	color:#eee;

}


#myDiv {
  display: none;
  text-align: center;
}

#mamberDiv{

margin-top:5%;

}

#mamberTable{
	background-color: #111!important;
	font-size:16px!important;
}

td,th{
text-align: center!important;
padding:0px!important;
line-height:51px!important;
}

th{
	color:#fff;
}


.paginate_button{
	background-color: #fff!important;
}

.queryTag{
    margin-top:13px!important; 
    position:relative;
}

.navTextTag{
	font-size:16px!important;
}


td{min-width:50px;}
tr.dataTr:nth-child(2n+1) {background: rgba(255, 255, 255, 0.95)!important;}
tr.dataTr:nth-child(2n) {background: rgba(230, 230, 230, 0.95)!important;}


/****end 自已加的 ****/
</style>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">	
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/twitter_bootstrap_3_3_7_min.css">   
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/google_family_kaushan_script.css" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/frontend/rental_form/Modified/agency.min.css"  />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/frontend/rental_form/Modified/agency.css" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/frontend/rental_form/Modified/other.css" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/dataTables.min.css">
<title>我的租賃單</title>

</head>
<body>
<%
	RentOrdService roSvc = new RentOrdService();
    MemberService mSvc = new MemberService(); 
    EmtListService emtlistSvc = new EmtListService();
    EquipmentService equipSvc = new EquipmentService();
	String memno = (String)request.getAttribute("memno");
	List<RentOrdVO> list = roSvc.getAll();
	pageContext.setAttribute("mems",mSvc.getAll());
	pageContext.setAttribute("emtlist",emtlistSvc.getAll());
	pageContext.setAttribute("equips",equipSvc.getAll());
	pageContext.setAttribute("list",list);
	pageContext.setAttribute("memno",memno);
	
	Map<String, String> statusMap = new HashMap<String, String>();
	statusMap.put("unpaid", "待繳費");
	statusMap.put("canceled", "取消訂單");
	statusMap.put("unoccupied", "訂單成立");
	statusMap.put("available", "今日取車");
	statusMap.put("noshow", "逾期未取");
	statusMap.put("noreturn", "出租中");
	statusMap.put("overtime", "逾期未還");
	statusMap.put("abnormalclosed", "異常結案");
	statusMap.put("closed", "結案");
	statusMap.put("other", "其它");
%>

<%-- Navigation --%>
	<nav id="mainNav" class="navbar navbar-default navbar-custom navbar-fixed-top">
		<div class="container-fluid">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header page-scroll">
				<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Toggle navigation</span> Menu <i class="fa fa-bars"></i>
				</button>
				<a class="navbar-brand page-scroll" href="<%=request.getContextPath()%>/index.jsp">AutoBike&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;個人租賃單查詢</a>
			</div>
			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
				 <ul class="nav navbar-nav navbar-right">
					<li class="hidden"><a href="#page-top"></a></li>
					<li><a class="page-scroll navTextTag" href="<%=request.getContextPath()%>/index.jsp">
						<i class="glyphicon glyphicon-home"></i>
						回首頁</a>
					</li>
					<li>
						<a class="page-scroll navTextTag" href="<%=request.getContextPath()%>/frontend/rental_form/rental_category.jsp">
						<i class="glyphicon glyphicon-heart"></i>
						我要租車</a>
					</li>
					<li>
						<a class="navTextTag" href="<%=request.getContextPath()%>/frontend/second_order/listAllSecond.jsp">
						<i class="fa fa-shopping-cart"></i>
						二手車購買</a>
					</li>
					<c:if test="${not empty memno}">	
						<li>
							<a class="navTextTag" href="<%=request.getContextPath()%>/backend/member/member.do?action=getOne_For_Enter&memid=${memno}">
							會員專區</a>
						</li>
						<li><a class="navTextTag" href="#" class="disabled">歡迎，${(memname == null) ? '會員':memname}</a></li>
						<li>
							<a class="navTextTag" href="<%=request.getContextPath()%>/backend/member/member.do?action=logout" data-toggle="modal"><i class="glyphicon glyphicon-user"></i>
							登出</a>
						</li>
					</c:if>
				</ul>
			</div>
		</div>
	</nav>
<%--end Navigation --%>
	
	<div id="blocker"></div>


	<!-- 租車主軸Header -->

<%----------------------------------------------------VVVV building area VVVV-----------------------------------------------------------%>


<div class="col-xs-12 col-sm-12" id="mamberDiv">
<jsp:useBean id="memSvc" scope="page" class="com.member.model.MemberService"/>
<jsp:useBean id="mmSvc" scope="page" class="com.motor_model.model.MotorModelService"/>
<jsp:useBean id="locSvc" scope="page" class="com.location.model.LocationService"/>

<table id="dataTable" class="table table-striped stripe hover">
	<thead>
		<tr>
		<th>明細查詢</th>		
		<th>租賃單編號</th>
		<th>車輛</th>
		<th>取車據點</th>
		<th>還車據點</th>
		<th>下訂時間</th>
		<th>取車時間</th>
		<th>還車時間</th>
		<th>租金</th>
		<th>評價</th>
		<th>租單狀態</th>
		<th>其它</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="roVO" items="${list}" >
			<c:if test="${memno==roVO.memno}">
				<tr align='center' valign='middle' class="dataTr">
					<td>
						<form method="POST" target="print_popup" 
      				  		  action="<%=request.getContextPath()%>/backend/rent_ord/rentOrd.do" 
       						  onsubmit="window.open('about:blank','print_popup','width=1000,height=980');">
							<input type="hidden" name="rentno" value="${roVO.rentno}">
							<input type="hidden" name="action" value="query_for_member_frontend">
							<input type="submit" class="btn btn-default queryTag" value="查詢">
						</form>	
					</td>				
					<td>${roVO.rentno}</td>
					<td>${mmSvc.findByPK(roVO.motorVO.modtype).brand}&nbsp;${mmSvc.findByPK(roVO.motorVO.modtype).name}</td>
					<td>${locSvc.getOneLocation(roVO.slocno).locname}</td>
					<td>${locSvc.getOneLocation(roVO.rlocno).locname}</td>
					<td><fmt:formatDate pattern = "yyyy/MM/dd hh:mm a" value = "${roVO.filldate}" /></td>	
					<td><fmt:formatDate pattern = "yyyy/MM/dd hh:mm a" value = "${roVO.startdate}" /></td>
					<td><fmt:formatDate pattern = "yyyy/MM/dd hh:mm a" value = "${roVO.returndate}" /></td>				
					<td>${roVO.total}</td>
					<td>${roVO.rank}</td>
					
					<c:set scope="page" var="temp">
					 	<c:out value="${roVO.status}"/>
					</c:set>
					<% 
					  String key = String.valueOf(pageContext.getAttribute("temp"));
					  if(statusMap.containsKey(key)){;
					%>
					<td><%=statusMap.get(key)%></td>
					<%};%>
					<!-- onsubmit="window.open('about:blank','print_popup','width=1000,height=900');" -->
					<td>
						<c:if test="${roVO.status == 'unpaid' || roVO.status =='unoccupied'}">
							<form method="POST" target="print_popup" 
	      				  		  action="<%=request.getContextPath()%>/backend/rent_ord/rentOrd.do" 
	       						  >
								<input type="hidden" name="rentno" value="${roVO.rentno}">
								<input type="hidden" name="status" value="canceled">
								<input type="hidden" name="action" value="member_cancel_rentord">
								<input type="submit" class="btn btn-default queryTag" onClick="return repeat_click_check()" id="submitBtn" value="申請取消">
							</form>
						</c:if>
					</td>
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



		<%----------------------------------------------------^^^^ building area ^^^^-----------------------------------------------------------%>		


	<script src="<%=request.getContextPath()%>/backend/Modified/jquery_1_10_1_min.js"></script>
	<script src="<%=request.getContextPath()%>/backend/Modified/jquery_ui_1_10_3.js"></script>
	<script src="<%=request.getContextPath()%>/backend/Modified/twitter_bootstrap_3_3_7_min.js"></script>
	<script src="<%=request.getContextPath()%>/js/dataTables.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/agency.min.js"></script>
<script>

count=0;
function repeat_click_check(){

	//result
	if(count!=0){
		alert("該筆訂單已點擊過取消申請，請勿重覆點擊。");
		return false;
	}else{
		window.open('about:blank','print_popup','width=1000,height=900');
	}
	count++;
}
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
