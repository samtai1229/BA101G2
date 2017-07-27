<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.sec_ord.model.*"%>

<%
	SecOrdService soSvc = new SecOrdService();
    String status = request.getParameter("status");
    String memno =request.getParameter("memno");
    List<SecOrdVO> list = null;
    List<SecOrdVO> list2 = new ArrayList<SecOrdVO>(); 
    if("all".equalsIgnoreCase(status) 
      || status==null)
    	list =  (List<SecOrdVO>) request.getAttribute("list");  
    else	
    {
    	for(SecOrdVO ord :soSvc.getAll(status))
    	{
    		if(ord.getMemno().equals(memno))
    			list2.add(ord);
    	}
    	 list = list2;
    	
    }
    System.out.println("取得參數status:"+status);
	pageContext.setAttribute("list",list);

%>

<html>
<style>

#SOTable{
	background-color: #111!important;
	font-size:16px!important;
}

td,th{
text-align: center!important;
padding:0px!important;
line-height:51px!important;
}

td{
	background-color: #fff!important;
}

</style>
<head>
<title>我的二手車訂單</title>
<link rel="Short Icon" href="<%=request.getContextPath()%>/img/smallIcon.ico">
</head>
<body bgcolor='white'>
<div  class="col-xs-12 col-sm-12">
<table  border='1' cellpadding='5' cellspacing='0' width='100%'>
	<tr bgcolor='#FFF' align='center' valign='middle' height='20'>
		<td>
			<h3>我的二手車訂單</h3>
		</td>
	</tr>
</table>
</div>
<div class="col-xs-12 col-sm-12">
<jsp:useBean id="motorSvc" scope="page" class="com.motor.model.MotorService"/>
<jsp:useBean id="mmSvc" scope="page" class="com.motor_model.model.MotorModelService"/>
	<table class="table table-striped" border='1' width='100%' id="SOTable">
		<tr>
			<th style="text-align:center">二手車訂單編號</th>
			<th style="text-align:center">會員編號</th>
			<th style="text-align:center">車輛編號</th>
			<th style="text-align:center">廠牌型號</th>
			<th style="text-align:center">車輛圖片</th>
			<th style="text-align:center">售價</th>
			<th style="text-align:center">訂單成立時間</th>
		</tr>
		<c:forEach var="soVO" items="${list}" >
			<tr align='center' valign='middle'>
				<td>${soVO.sono}</td>
				<td>${soVO.memno}</td>
				<td>${soVO.motorno}</td>
				<td>${mmSvc.findByPK(motorSvc.findByPK(soVO.motorno).modtype).brand} - ${mmSvc.findByPK(motorSvc.findByPK(soVO.motorno).modtype).name}</td>
				<td><img src='<%=request.getContextPath()%>/backend/motor_model/mmReader.do?modtype=${motorSvc.findByPK(soVO.motorno).modtype}' width='200' height='200'></td>
				<td>NT$ ${mmSvc.findByPK(motorSvc.findByPK(soVO.motorno).modtype).saleprice}</td>
				<td><fmt:formatDate pattern = "yyyy-MM-dd hh:mm" value = "${soVO.buildtime}" /></td>
			</tr>
		</c:forEach>
	</table>
</div>
</body>

	<script src="<%=request.getContextPath()%>/backend/Modified/jquery_1_10_1_min.js"></script>
	<script src="<%=request.getContextPath()%>/backend/Modified/twitter_bootstrap_3_3_7_min.js"></script>

</html>
