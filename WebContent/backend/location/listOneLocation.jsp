<%@ page contentType="text/html; charset=Big5"%>
<%@ page import="com.location.model.*"%>
<%
LocationVO locationVO = (LocationVO) request.getAttribute("locationVO"); //EmpServlet.java(Concroller), �s�Jreq��empVO����
%>
<html>
<head>

	<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
	<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>	
	<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css"/>	
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
<title>���u��� - listOneLocation.jsp</title>
</head>
<body bgcolor='white'>

<nav class="navbar navbar-default" role="navigation">
        <div class="container">
	        <!-- logo�� -->
	        <a class="navbar-brand" href="<%=request.getContextPath()%>/backend/location/select_page.jsp" id="navA">AUTOBIKE</a>
	        <!-- ����� -->
	        <ul class="nav navbar-nav">
	            <li><a href="listAllLocation.jsp">�d�ߥ������I</a></li>
	        	<li><a href="addLocation.jsp">�s�W���I</a></li>
	        </ul>
        </div>
    </nav>

<table border='1' bordercolor='#CCCCFF' width='500' align='center' valign='middle'>
	
		<tr>
		<td>���I�s��</td>
		<td><%=locationVO.getLocno()%></td>
		</tr>
		<tr>
		<td>���I�W��</td>
		<td><%=locationVO.getLocname()%></td>
		</tr>
		<tr>
		<td>���I�q��</td>
		<td><%=locationVO.getTel()%></td>
		</tr>
		<tr>
		<td>���I�a�}</td>
		<td><%=locationVO.getAddr()%></td>
		</tr>
		<tr>
		<td>���I�Ӥ�</td>
		<td><img src="<%=request.getContextPath()%>/frontend/location/locReader.do?locno=${locationVO.locno}&card=pic" height="300" width="300"></td>
		</tr>
		<tr>
		<td>���I�a�Ϻ��</td>
		<td><%=locationVO.getLon()%></td>
		</tr>
		<tr><td>���I�a�Ͻn��</td>
		<td><%=locationVO.getLat()%></td>
		</tr>
		<tr>
		<td>���I���A</td>
		<td><%=locationVO.getStatus()%></td>
		</tr>
	
	<%-- <tr>
		<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/location/location.do">
			     <input type="submit" value="�ק�"> 
			     <input type="hidden" name="locno" value="${locationVO.locno}">
			     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--�e�X�����������|��Controller-->
			    <input type="hidden" name="whichPage"	>               <!--�e�X��e�O�ĴX����Controller-->
			     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
			</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/location/location.do">
			    <input type="submit" value="�R��">
			    <input type="hidden" name="locno" value="${locationVO.locno}">
			    <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--�e�X�����������|��Controller-->
			    <input type="hidden" name="whichPage"	>               <!--�e�X��e�O�ĴX����Controller-->
			    <input type="hidden" name="action"value="delete"></FORM>
			</td>
	</tr> --%>
</table>

</body>
</html>
