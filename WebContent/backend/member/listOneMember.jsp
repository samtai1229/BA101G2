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
<html>
<head>
<title>會員資料 - listOneMember.jsp</title>
</head>
<body bgcolor='white'>
<b><font color=red>此頁練習採用 Script 的寫法取值:</font></b>
<table border='1' cellpadding='5' cellspacing='0' width='1600'>
	<tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
		<td>
		<h3>會員資料 - ListOneMember.jsp</h3>
		<a href="<%=request.getContextPath()%>/index.jsp"><img src="images/back1.gif" width="100" height="32" border="0">回首頁</a>
		</td>
	</tr>
</table>

<table border='1' bordercolor='#CCCCFF' width='1600'>
	<tr>
		<th>會員編號</th>
		<th width='100' >會員名稱</th>
		<th width='50'>性別 </th>
		<th>生日</th>
		<th>Mail</th>
		<th>電話</th>
		<th>地址</th>
		<th>帳號</th>
		<th>密碼</th>
		<th>身分證正面</th>
		<!-- <th>影片</th> -->
		<th>身分證反面</th>
		<th>駕照</th>
		<th  width='150'>加入時間</th>
		<th>認證狀態</th>
	</tr>
	<tr align='center' valign='middle'>
			<td>${memVO.memno}</td>
			<td width='100'>${memVO.memname}</td>
			<td width='50'>${memVO.sex}</td>
			<td  width='150'><fmt:formatDate pattern = "yyyy年MM月dd號" 
         value = "${memVO.birth}" /></td>
			<td>${memVO.mail}</td>
			<td>${memVO.phone}</td>	
			<td   width='200'>${memVO.addr}</td>	
			<td>${memVO.acc}</td>
			<td>${memVO.pwd}</td>
<!-- 正面 --><td><img src='<%=request.getContextPath()%>/backend/member/memReader.do?memno=${memVO.memno}&card=idcard1' width='200' height='180'></td>	
<!-- 影片 --><!-- <td><video src='<%=request.getContextPath()%>/backend/member/memReader.do?memno=${memVO.memno}&card=idcard1' width='200' height='180' controls></video></td> -->				
<!-- 反面 --><td><img src='<%=request.getContextPath()%>/backend/member/memReader.do?memno=${memVO.memno}&card=idcard2'  width='200' height='180'></td>				
<!-- 駕照 --><td><img src='<%=request.getContextPath()%>/backend/member/memReader.do?memno=${memVO.memno}&card=license'  width='200' height='180'></td>							
			<td><fmt:formatDate pattern = "yyyy年MM月dd號" 
         value = "${memVO.credate}" /></td>
			<td>${memVO.status}</td>
				
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/member/member.do">
			     <input type="submit" value="修改"> 
			     <input type="hidden" name="memno" value="${memVO.memno}">
			     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller--><!-- 目前尚未用到  -->
			     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
			</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/member/member.do">
			    <input type="submit" value="刪除">
			    <input type="hidden" name="memno" value="${memVO.memno}">
			    <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
			    <input type="hidden" name="action"value="delete"></FORM>
			</td>
		</tr>
</table>
<button type="button" onclick="loadDoc()" >按我測試</button>
<div id="demo"></div>


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
	  
	  xhttp.open("GET","<%=request.getContextPath()%>/backend/member/member.do?action=getOne_For_Enter&memid=${memVO.memno}", true);
	
	  xhttp.send();
	}
</script>
</html>
