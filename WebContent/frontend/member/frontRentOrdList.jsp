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
       background: url(/BA101G2/img/header1.jpg) no-repeat center center fixed; 
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


.paginate_button{
	background-color: #fff!important;
}

.queryTag{
    margin-top:13px!important; 
    position:relative;
}

/****end 自已加的 ****/
</style>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">	
	<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
	<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">     
	<link rel="stylesheet" type="text/css" href="//cdn.datatables.net/1.10.15/css/jquery.dataTables.min.css">
	<script type="text/javascript" src="//cdn.datatables.net/1.10.15/js/jquery.dataTables.min.js"></script>
	<link href="<%=request.getContextPath()%>/frontend/rental_form/Modified/agency.min.css" rel="stylesheet" />
	<link href="<%=request.getContextPath()%>/frontend/rental_form/Modified/agency.css" rel="stylesheet" />
	<link href="<%=request.getContextPath()%>/frontend/rental_form/Modified/other.css" rel="stylesheet" />
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

<!-- Navigation -->
	<nav id="mainNav" class="navbar navbar-default navbar-custom navbar-fixed-top">
		<div class="container-fluid">
			<div class="navbar-header page-scroll">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Toggle navigation</span> Menu <i class="fa fa-bars"></i>
				</button>
				<a class="navbar-brand page-scroll" href="<%=request.getContextPath()%>/index.jsp">AutoBike</a>
			</div>
			<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav navbar-right">
					<li class="hidden"><a href="#page-top"></a></li>
					<li><a class="page-scroll" href="<%=request.getContextPath()%>/frontend/rental_form/rental_category.jsp">
					<i class="glyphicon glyphicon-heart"></i>我要租車</a></li>
					<li><a class="page-scroll" href="#news">
					<i class="glyphicon glyphicon-alert"></i>最新消息</a></li>
					<li><a class="page-scroll" href="#board">
					<i class="fa fa-comments-o"></i>留言板</a></li>
					<li><a class="page-scroll" href="#loc">
					<i class="fa fa-search"></i>服務據點</a></li>
					<li><a href="<%=request.getContextPath()%>/backend/member/member.do">
					<i class="fa fa-shopping-cart"></i>二手車購買</a></li>
					<c:if test="${not empty memno}">
						<li><a href="<%=request.getContextPath()%>/backend/member/member.do?action=getOne_For_Enter&memid=${memno}">歡迎，${memname}</a></li>
						<li><a href="<%=request.getContextPath()%>/backend/member/member.do?action=logout" data-toggle="modal">
						<i class="glyphicon glyphicon-user"></i>登出</a>
						</li>
					</c:if>
				</ul>
			</div>
		</div>
	</nav>
	
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
		<th>罰金</th>
		<th>評價</th>
		<th>租單狀態</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="roVO" items="${list}" >
			<c:if test="${memno==roVO.memno}">
				<tr align='center' valign='middle'>
					<td>
						<form method="POST" target="print_popup" 
      				  		  action="<%=request.getContextPath()%>/backend/rent_ord/rentOrd.do" 
       						  onsubmit="window.open('about:blank','print_popup','width=1000,height=900');">
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
					<td>${roVO.fine}</td>
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

<!-- 	<footer>
		<div class="container-fluid topdiv">
			<div class="col-xs-12 col-sm-4">
				<span>地址:桃園市平鎮區中央路115號</span>
			</div>
			<div class="col-xs-12 col-sm-4">
				<span>EMAIL:taic@oregonstate.edu</span>
			</div>
			<div class="col-xs-12 col-sm-4">
				<span>TEL:0900-000-000</span>
			</div>
		</div>
	</footer> -->

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
	<script src="<%=request.getContextPath()%>/js/agency.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>    
</body>
