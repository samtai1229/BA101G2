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

	<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
	<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>	
	<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css"/>	
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
<title>員工資料 - listOneLocation.jsp</title>
</head>
<body bgcolor='white'>

<nav class="navbar navbar-default" role="navigation">
        <div class="container">
	        <!-- logo區 -->
	        <a class="navbar-brand" href="<%=request.getContextPath()%>/backend/location/select_page.jsp" id="navA">AUTOBIKE</a>
	        <!-- 左選單 -->
	        <ul class="nav navbar-nav">
	            <li><a href="listAllLocation.jsp">查詢全部據點</a></li>
	        	<li><a href="addLocation.jsp">新增據點</a></li>
	        </ul>
        </div>
    </nav>

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
	
	<%-- <tr>
		<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/location/location.do">
			     <input type="submit" value="修改"> 
			     <input type="hidden" name="locno" value="${locationVO.locno}">
			     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
			    <input type="hidden" name="whichPage"	>               <!--送出當前是第幾頁給Controller-->
			     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
			</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/location/location.do">
			    <input type="submit" value="刪除">
			    <input type="hidden" name="locno" value="${locationVO.locno}">
			    <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
			    <input type="hidden" name="whichPage"	>               <!--送出當前是第幾頁給Controller-->
			    <input type="hidden" name="action"value="delete"></FORM>
			</td>
	</tr> --%>
</table>

</body>
</html>
