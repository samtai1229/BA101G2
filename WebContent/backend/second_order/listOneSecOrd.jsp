<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ page import="com.spots.model.*"%>
<%@ page import="com.location.model.*"%>
<%-- 此頁練習採用 Script 的寫法取值 --%>

<%-- 取出 Concroller EmpServlet.java已存入request的EmpVO物件--%>
<%SpotsVO spotVO = (SpotsVO) request.getAttribute("spotVO");%>

<%-- 取出 對應的LocationVO物件--%>
<%
 // DeptService deptSvc = new DeptService();
 // DeptVO deptVO = deptSvc.getOneDept(empVO.getDeptno());
%>
<html>
<head>
<title>景點資料 - listOneSpot.jsp</title>
</head>
<body bgcolor='white'>
<b><font color=red>此頁練習採用 Script 的寫法取值:</font></b>
<table border='1' cellpadding='5' cellspacing='0' width='800'>
	<tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
		<td>
		<h3>景點資料 - ListOneSpot.jsp</h3>
		<a href="<%=request.getContextPath()%>/backend/second_order/select_page.jsp"><img src="images/back1.gif" width="100" height="32" border="0">回首頁</a>
		</td>
	</tr>
</table>

<table border='1' bordercolor='#CCCCFF' width='800'>
	<tr>
		<th>景點編號</th>
		<th>景點名稱</th>
		<th>經度</th>
		<th>緯度</th>
		<th>據點編號</th>
		
	</tr>
	<tr align='center' valign='middle'>
		<td><%=spotVO.getSpno()%></td>
		<td><%=spotVO.getSpname()%></td>
		<td><%=spotVO.getSplong()%></td>
		<td><%=spotVO.getSplat()%></td>
		<td><%=spotVO.getLocno()%></td>
	
		
	</tr>
</table>

</body>
</html>
