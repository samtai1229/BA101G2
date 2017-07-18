<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.member.model.*"%>
<%@ page import="com.adminis.model.*"%>
<% 
AdminisService as = new AdminisService();
AdminisVO adminisVO= (AdminisVO)session.getAttribute("adminisVO");
MemberVO memVO = (MemberVO) request.getAttribute("memVO");

%>

<%-- 取出 對應的LocationVO物件--%>
<%
//  DeptService deptSvc = new DeptService();
//  DeptVO deptVO = deptSvc.getOneDept(empVO.getDeptno());
%>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">	

	<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
	<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
	<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css"/>	
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">     
	<link rel="stylesheet" href="<%=request.getContextPath()%>/backend/Modified/backendHP_css.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/backend/Modified/main.css" >
	<link rel="stylesheet" type="text/css" href="//cdn.datatables.net/1.10.15/css/jquery.dataTables.min.css">
	<script type="text/javascript" src="//cdn.datatables.net/1.10.15/js/jquery.dataTables.min.js"></script>

   <title>會員管理-AutoBike</title>
</head>
<title>會員資料 - listOneMember.jsp</title>

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
    
    /*自定*/
	 th{
		/*死都不換行*/
		white-space:nowrap;
	} 
	.btn-lg{
		margin:5px;
	}
	
	td{
		vertical-align: middle!important;
	}


</style>

<body bgcolor='white'>

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
        <div class="btn-group-vertical">
            <a class="btn btn-default" href="<%=request.getContextPath()%>/backend/member/backendMember.jsp" role="button">會員管理</a>
            <a class="btn btn-default" href="<%=request.getContextPath()%>/backend/member/addMember.jsp" role="button">新增會員</a>
        </div>
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
    <div class="col-xs-12 col-sm-10 rightHTML">
    	<div class="topTitle">
            <h1>會員資料管理系統</h1>
        </div>
     <div class="container-fluid">   
	    <table border="1" class="table table-striped custab stripe hover">
		    <thead>
		        <tr>
					<th>會員編號</th>
					<th>會員名稱</th>
					<th>性別 </th>
					<th>生日</th>
					<th>Mail</th>
					<th>電話</th>
					<th>地址</th>
					<th>帳號</th>
					<th>身分證正面</th>
					<th>身分證反面</th>
					<th>駕照</th>
					<th>加入時間</th>
					<th>認證狀態</th>
					<th>修改</th>
				</tr>
		    </thead>
		    <tbody>
		            <tr align='center' valign='middle'>
					<td>${memVO.memno}</td>
					<td>${memVO.memname}</td>
					<td>${memVO.sex}</td>
					<td><fmt:formatDate pattern = "yyyy/MM/dd" value = "${memVO.birth}" /></td>
					<td>${memVO.mail}</td>
					<td>${memVO.phone}</td>	
					<td><p align="left">${memVO.addr}</p></td>	
					<td>${memVO.acc}</td>
					<td>
						<c:if test="${memVO.idcard1!=null}">
						<img src='<%=request.getContextPath()%>
						/backend/member/memReader.do?memno=${memVO.memno}&card=idcard1' width='100' height='100'>
						</c:if><c:if test="${memVO.idcard1==null}">沒有圖片</c:if>
					</td>	
					<td>
						<c:if test="${memVO.idcard2!=null}">
						<img src='<%=request.getContextPath()%>
						/backend/member/memReader.do?memno=${memVO.memno}&card=idcard2'  width='100' height='100'>
						</c:if><c:if test="${memVO.idcard2==null}">沒有圖片</c:if>
					</td>				
					<td>
						<c:if test="${memVO.idcard2!=null}">
						<img src='<%=request.getContextPath()%>
						/backend/member/memReader.do?memno=${memVO.memno}&card=license' width='100' height='100'>
						</c:if><c:if test="${memVO.license==null}">沒有圖片</c:if>
					</td>							
					<td><fmt:formatDate pattern = "yyyy/MM/dd" value = "${memVO.credate}" /></td>
					<td>${memVO.status}</td>
					<td class="text-center"><a class='btn btn-info btn-md' 
					href="<%=request.getContextPath()%>/backend/member/member.do?addAction=modifyMember&action=getOne_For_Update&memno=${memVO.memno}">
					<span class="glyphicon glyphicon-edit"></span>修改</a></td>
				</tr>
				</tbody>
	    </table>
		<form METHOD="post" ACTION="<%=request.getContextPath()%>/backend/member/member.do">
			<input type="hidden" name="addaction" value="backend">
			<input type="hidden" name="action" value="getAllRentOrder">
			<input type="hidden" name="memno" value="${memVO.memno}">
			<p class="text-center">
				<button type="button" onclick="loadDoc()" class="btn btn-info btn-lg">二手車訂單查詢</button>
				<button type="submit" class="btn btn-success btn-lg">
					租賃單查詢
				</button>
				<a onclick="history.back()" class="btn btn-danger btn-lg">
					<i class="glyphicon glyphicon-arrow-up"></i>返回上頁
				</a>
			</p>
		</form>


<div id="demo"></div>
<div id="demo2"></div>
		</div>
</div>	

</body>
<script type="text/javascript">
 function loadDoc() {
    var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
	if (this.readyState == 4 && this.status == 200) {
	     document.getElementById("demo2").innerHTML=this.responseText;
	    }
	 };
	  xhttp.open("GET","<%=request.getContextPath()%>/backend/member/member.do?addaction=backend&action=get_second_ord_per_member&memno=${memVO.memno}", true);
	  xhttp.send();
	}

 function loadOrder() {
    var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
	if (this.readyState == 4 && this.status == 200) {
	     document.getElementById("demo2").innerHTML=this.responseText;
	    }
	 };
	  xhttp.open("GET","<%=request.getContextPath()%>/backend/member/member.do?addaction=backend&action=getAllRentOrder&memno=${memVO.memno}", true);
	  xhttp.send();
	}	
 
 function toggle() {
	    var xhttp = new XMLHttpRequest();
	    var ss = document.getElementById("mystatus").value;
		xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
		     document.getElementById("demo").innerHTML=this.responseText;
		    }
		 };
		  xhttp.open("GET","<%=request.getContextPath()%>/backend/member/member.do?action=get_second_ord_per_member&memno=${memVO.memno}&status="+ss, true);
		  xhttp.send();
		} 
 
</script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="<%=request.getContextPath()%>/backend/rent_ord/Modified/rentOrdNew.js"></script>
    <script src="<%=request.getContextPath()%>/backend/rent_ord/Modified/motorKanli_for_ro.js"></script>
</body>
</html>
