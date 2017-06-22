<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ page import="com.sec_ord.model.*"%>
<%@ page import="com.location.model.*"%>
<%-- 此頁練習採用 Script 的寫法取值 --%>

<%-- 取出 Concroller EmpServlet.java已存入request的EmpVO物件--%>
<%SecOrdVO soVO = (SecOrdVO) request.getAttribute("soVO");%>

<%-- 取出 對應的LocationVO物件--%>
<%
 // DeptService deptSvc = new DeptService();
 // DeptVO deptVO = deptSvc.getOneDept(empVO.getDeptno());
%>
<html>
<head>
<title>資料 - listOneSecOrd.jsp</title>
</head>
<body bgcolor='white'>
<b><font color=red>此頁練習採用 Script 的寫法取值:</font></b>
<table border='1' cellpadding='5' cellspacing='0' width='800'>
	<tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
		<td>
		<h3>資料 - ListOneSecOrd.jsp</h3>
		<a href="<%=request.getContextPath()%>/frontend/second_order/select_page.jsp"><img src="images/back1.gif" width="100" height="32" border="0">回首頁</a>
		</td>
	</tr>
</table>
<select size="1"></select>
<table border='1' bordercolor='#CCCCFF' width='800'>
	<tr>
		<th>二手車訂單編號</th>
		<th>會員編號</th>
		<th>車輛編號</th>
		<th>建單時間</th>
		<th>狀態</th>
		
	</tr>
	<tr align='center' valign='middle'>
		<td><%=soVO.getSono()%></td>
		<td><%=soVO.getMemno()%></td>
		<td><%=soVO.getMotorno()%></td>
		<td><%=soVO.getBuildtime()%></td>
		<td><%=soVO.getStatus()%></td>
	
		
	</tr>
</table>

</body>
</html>
