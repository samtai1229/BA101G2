<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.member.model.*"%>
<%-- 此頁練習採用 Script 的寫法取值 --%>

<%-- 取出 Concroller EmpServlet.java已存入request的EmpVO物件--%>
<%MemberVO memVO = (MemberVO) request.getAttribute("memVO");

%>

<%-- 取出 對應的LocationVO物件--%>
<%
//  DeptService deptSvc = new DeptService();
//  DeptVO deptVO = deptSvc.getOneDept(empVO.getDeptno());
%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>會員專區</title>

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

</style>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
</head>
<body bgcolor='white'>
<div class="col-xs-12 col-sm-12">
<table  border='1' cellpadding='5' cellspacing='0' width='100%'>
	<tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
		<td>
		<h3>會員專區 -MemberCenter</h3>
		<a href="<%=request.getContextPath()%>/index.jsp"><img src="images/back1.gif" width="100" height="32" border="0">回首頁</a>
		</td>
	</tr>
</table>
</div>
<div class="col-xs-12 col-sm-12">
    <table   border="1" class="table table-striped table-inverse" bordercolor='#CCCCFF' width='1260'>
    
        <tr>
		<th style="text-align:center">會員編號</th>
		<th style="text-align:center">會員名稱</th>
		<th style="text-align:center">性別 </th>
		<th style="text-align:center">生日</th>
		<th style="text-align:center">Mail</th>
		<th style="text-align:center">電話</th>
		<th style="text-align:center">地址</th>
		<th style="text-align:center">帳號</th>
		<th style="text-align:center">身分證正面</th>
		<th style="text-align:center">身分證反面</th>
		<th style="text-align:center">駕照</th>
		<th style="text-align:center">認證狀態</th>
		<th style="text-align:center">操作</th>
	
	</tr>
    
            <tr align='center' valign='middle'>
			<td>${memVO.memno}</td>
			<td>${memVO.memname}</td>
			<td>${memVO.sex}</td>
			<td><fmt:formatDate pattern = "yyyy年MM月dd號" 
         value = "${memVO.birth}" /></td>
			<td>${memVO.mail}</td>
			<td>${memVO.phone}</td>	
			<td>${memVO.addr}</td>	
			<td>${memVO.acc}</td>
<!-- 正面 --><td><img src='<%=request.getContextPath()%>/backend/member/memReader.do?memno=${memVO.memno}&card=idcard1' width='200' height='180'></td>	
<!-- 反面 --><td><img src='<%=request.getContextPath()%>/backend/member/memReader.do?memno=${memVO.memno}&card=idcard2'  width='200' height='180'></td>				
<!-- 駕照 --><td><img src='<%=request.getContextPath()%>/backend/member/memReader.do?memno=${memVO.memno}&card=license'  width='200' height='180'></td>							
			<c:if test="${memVO.status=='confirmed'}"><td><font color=red><b>完整會員</b></font></td></c:if>
			<c:if test="${memVO.status=='unconfirmed'}"><td><font color=red><b>簡易會員</b></font></td></c:if>
			<td class="text-center"><a class='btn btn-info btn-xs' href="<%=request.getContextPath()%>/backend/member/member.do?action=getOne_For_Update&memno=${memVO.memno}"><span class="glyphicon glyphicon-edit"></span> Edit</a></td>
		</tr>
    </table>
   </div>
   <div class="col-xs-12 col-sm-12">
    <button type="button" onclick="loadDoc()" >我的二手車訂單</button>
	<button type="button" onclick="loadOrder()" >我的租賃單</button>
	</div>
	<BR/>
<div class="col-xs-12 col-sm-12"  id="demo"></div>



</body>
<script src="https://code.jquery.com/jquery.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript">
 function loadDoc() {
    var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
	if (this.readyState == 4 && this.status == 200) {
	     document.getElementById("demo").innerHTML=this.responseText;
	    }
	 };
	  
	  xhttp.open("GET","<%=request.getContextPath()%>/frontend/second_order/SecOrd.do?action=getAll_For_Display_By_Memno&memno=${memVO.memno}", true);
	
	  xhttp.send();
	}

 function loadOrder() {
    var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
	if (this.readyState == 4 && this.status == 200) {
	     document.getElementById("demo").innerHTML=this.responseText;
	    }
	 };
	  
	  xhttp.open("GET","<%=request.getContextPath()%>/backend/member/member.do?action=getAllRentOrder&memno=${memVO.memno}", true);
	
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
		  
		  xhttp.open("GET","<%=request.getContextPath()%>/frontend/second_order/SecOrd.do?action=getAll_For_Display_By_Memno_Status&memno=${memVO.memno}&status="+ss, true);
		
		  xhttp.send();
		} 
 
 
 
</script>
</html>
