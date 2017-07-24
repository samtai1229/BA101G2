<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.location.model.*"%>
<%
	LocationVO locationVO = (LocationVO) request.getAttribute("locationVO"); //EmpServlet.java (Concroller), 存入req的empVO物件 (包括幫忙取出的empVO, 也包括輸入資料錯誤時的empVO物件)
%>
<html>
<head>

	<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
	<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>	
	<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css"/>	
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
	<title>會員資料修改 - update_member_input.jsp</title>
</head>
<link rel="stylesheet" type="text/css" href="js/calendar.css">
<script language="JavaScript" src="js/calendarcode.js"></script>
<div id="popupcalendar" class="text"></div>

<body>

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

<h3>資料修改:</h3>
<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font color='red'>請修正以下錯誤:
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li>${message}</li>
		</c:forEach>
	</ul>
	</font>
</c:if>

<FORM METHOD="post" ACTION="location.do" name="form1" enctype="multipart/form-data">
<table border="1" width='500' align='center' valign='middle'>
	<tr>
		<td>據點編號:<font color=red><b>*</b></font></td>
		<td><%=locationVO.getLocno()%></td>
	</tr>
	<tr>
		<td>據點名稱:<font color=red><b>*</b></font></td>
		<td><input type="TEXT" name="locname" size="45" value="<%=locationVO.getLocname()%>" /></td>
	</tr>
	<tr>
		<td>據點電話:<font color=red><b>*</b></font></td>
		<td><input type="TEL" name="tel" size="45"	value="<%=locationVO.getTel()%>" /></td>
	</tr>
	

	<tr>
		<td>據點地址:<font color=red><b>*</b></font></td>
		<td><input type="TEXT" name="addr" size="45"	value="<%=locationVO.getAddr()%>" /></td>
	</tr>
	<tr>
		<td>據點照片:</td>
		<td><input type="FILE" name="pic" size="45" value="<%=locationVO.getPic()%>" /></td>
	</tr>
	<tr>
		<td>據點地圖精度:</td>
		<td><input type="TEXT" name="lon" size="45" value="<%=locationVO.getLon()%>" /></td>
	</tr>
	<tr>
		<td>據點地圖緯度:</td>
		<td><input type="TEXT" name="lat" size="45" value="<%=locationVO.getLat()%>" /></td>
	</tr>
	<tr>
		<td>據點狀態:<font color=red><b>*</b></font></td>
		<td><select id="selectStatus" name="status" value="<%=locationVO.getStatus() %>" ><%=locationVO.getStatus() %>
<!-- 		<option value="prepared">prepared</option> -->
		<option value="open">open</option>
		<option value="closed">closed</option></select></td>
	</tr>

</table>

<script>
window.onload =
	function init(){
		var sta = "<%=locationVO.getStatus()%>";
		console.log("sta= "+sta);
		var selectOpts = document.querySelectorAll("#selectStatus > option");
		for(var i = 0;  i < selectOpts.length; i++){
			if( selectOpts[i].value ==  sta ){
				selectOpts[i].selected = "selected";
			}
			if(	selectOpts[i].value.equal("preparing")){
				selectOpts[i].selected = "close";
			}
		}
	}
</script>

<br>
<input type="hidden" name="action" value="update">
<input type="hidden" name="locno" value="<%=locationVO.getLocno()%>">
<input type="submit" value="送出修改"></FORM>

</body>
</html>
