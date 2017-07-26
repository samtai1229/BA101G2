<%@ page contentType="text/html; charset=Big5"%>
<%@ page import="com.location.model.*"%>
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
LocationVO locationVO = (LocationVO) request.getAttribute("locationVO"); //EmpServlet.java(Concroller), 存入req的empVO物件
%>
<html>
<head>

<title>據點管理</title>
</head>
<body bgcolor='white'>
<table border='1' bordercolor='#CCCCFF' width='500' align='center' valign='middle'>
		<tr>
		<td>據點編號</td>
		<td><%=locationVO.getLocno()%></td>
		</tr>
		<tr>
		<td>據點名稱</td>
		<td><%=locationVO.getLocname()%></td>
		</tr>
		<tr>
		<td>據點電話</td>
		<td><%=locationVO.getTel()%></td>
		</tr>
		<tr>
		<td>據點地址</td>
		<td><%=locationVO.getAddr()%></td>
		</tr>
		<tr>
		<td>據點照片</td>
		<td><img src="<%=request.getContextPath()%>/frontend/location/locReader.do?locno=${locationVO.locno}&card=pic" height="300" width="300"></td>
		</tr>
		<tr>
		<td>據點地圖精度</td>
		<td><%=locationVO.getLon()%></td>
		</tr>
		<tr><td>據點地圖緯度</td>
		<td><%=locationVO.getLat()%></td>
		</tr>
		<tr>
		<td>據點狀態</td>
		<td><%=locationVO.getStatus()%></td>
		</tr>
</table>

</body>
</html>
