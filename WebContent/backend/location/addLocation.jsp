<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.location.model.*"%>
<%
LocationVO locationVO = (LocationVO) request.getAttribute("locationVO");
%>

<html>
<head>
<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
	<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>	
	<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css"/>	
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">

<title>���I��Ʒs�W - addLocation.jsp</title></head>
<link rel="stylesheet" type="text/css" href="js/calendar.css">
<script language="JavaScript" src="js/calendarcode.js"></script>
<div id="popupcalendar" class="text"></div>

<body bgcolor='white'>

	<nav class="navbar navbar-default" role="navigation">
		<div class="container">
	        <!-- logo�� -->
	        <a class="navbar-brand" href="<%=request.getContextPath()%>/backend/location/select_page.jsp" id="navA">AUTOBIKE</a>
	        <!-- ����� -->
	        <ul class="nav navbar-nav">
	            <li><a href="listAllLocation.jsp">�d�ߥ������I</a></li>
	        </ul>
        </div>
    </nav>
	</nav>

<h3>���I���:</h3>
<%-- ���~��C --%>
<c:if test="${not empty errorMsgs}">
	<font color='red'>�Эץ��H�U���~:
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li>${message}</li>
		</c:forEach>
	</ul>
	</font>
</c:if>

<FORM METHOD="post" ACTION="location.do" name="form1" enctype="multipart/form-data">
<table border="0">

	<tr>
		<td>���I�s��:<font color=red><b>*</b></font></td>
		<td><input type="TEXT" name="locno" size="45" 
			value="<%= (locationVO==null)? "TPE02" : locationVO.getLocno()%>" /></td>
	</tr>
	<tr>
		<td>���I�W��:<font color=red><b>*</b></font></td>
		<td><input type="TEXT" name="locname" size="45" 
			value="<%= (locationVO==null)? "�x�_�l��" : locationVO.getLocname()%>" /></td>
	</tr>
	<tr>
		<td>���I�q��:<font color=red><b>*</b></font></td>
		<td><input type="TEXT" name="tel" size="45"
			value="<%= (locationVO==null)? "025863152" : locationVO.getTel()%>" /></td>
	</tr>
	
	<tr>
		<td>���I�a�}:<font color=red><b>*</b></font></td>
		<td><input type="TEXT" name="addr" size="45"
			value="<%= (locationVO==null)? "addr123123" : locationVO.getAddr()%>" /></td>
	</tr>
	<tr>
		<td>���I�Ӥ�:</td>
		<td><input type="FILE" name="pic" size="45"
			value="<%= (locationVO==null)? "" : locationVO.getPic()%>" /></td>
	</tr>
	<tr>
		<td>���I�a�Ϻ��:</font></td>
		<td><input type="TEXT" name="lon" size="45"
			value="<%= (locationVO==null)? "24.963809" : locationVO.getLon()%>" /></td>
	</tr>
	<tr>
		<td>���I�a�Ͻn��:</font></td>
		<td><input type="TEXT" name="lat" size="45"
			value="<%= (locationVO==null)? "121.190725" : locationVO.getLat()%>" /></td>
	</tr>
	<tr>
		<td>���I���A:</td>
		<td>preparing(�ǳƤ�)</td>
	</tr>
	
</table>
<br>
<input type="hidden" name="action" value="insert">
<input type="submit" value="�e�X�s�W"></FORM>
</body>

</html>
