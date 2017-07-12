<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.member.model.*"%>
<%@ page import="com.rent_ord.model.*"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>

<%
	RentOrdService roSvc = new RentOrdService();
	String memno = (String)request.getAttribute("memno");
	List<RentOrdVO> list = roSvc.getAll();
	pageContext.setAttribute("list",list);
	pageContext.setAttribute("memno",memno);
%>

<html>
<head>
<title>我的租賃單資料</title>
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
<div  class="col-xs-12 col-sm-12">
<table  border='1' cellpadding='5' cellspacing='0' width='100%'>
	<tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
		<td>
		<h3>我的訂單 </h3>
	</td>
	</tr>
</table>
</div>
<%-- 錯誤表列 --%>
<%-- <c:if test="${not empty errorMsgs}"> --%>
<!-- 	<font color='red'>請修正以下錯誤: -->
<!-- 	<ul> -->
<%-- 		<c:forEach var="message" items="${errorMsgs}"> --%>
<%-- 			<li>${message}</li> --%>
<%-- 		</c:forEach> --%>
<!-- 	</ul> -->
<!-- 	</font> -->
<%-- </c:if> --%>
<div class="col-xs-12 col-sm-12">
<%-- <%@ include file="pages/page1.file" %>  --%>

<table border='1' class="table table-striped custab">
	<thead>
	<tr align='center'>
	
		<th>租賃單編號</th>
		<th>會員編號</th>
		<th>車輛型號</th>
		<th>取車據點</th>
		<th>還車據點</th>
		<th>起始里程</th>
		<th>行駛里程</th>
		<th>下單日期</th>
		<th>訂單開始時間</th>
		<th>訂單結束時間</th>
		<th>還車時間</th>
		<th>FINE</th>
		<th>TOTAL</th>
		<th>RANK</th>
		<th>Status</th>
		<th>Note</th>
	
	</tr>
	</thead>
<%-- 	begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>" --%>
	<c:forEach var="roVO" items="${list}" >
	 <c:if test="${memno==roVO.memno}">
		<tr align='center' valign='middle'>
			<td>${roVO.rentno}</td>
			<td>${roVO.memno}</td>
			<td>${roVO.motno}</td>
			<td>${roVO.slocno}</td>
			<td>${roVO.rlocno}</td>
			<td>${roVO.milstart}</td>	
			<td>${roVO.milend}</td>	
			<td><fmt:formatDate pattern = "yyyy年MM月dd號" 
         value = "${roVO.filldate}" /></td>
			<td><fmt:formatDate pattern = "yyyy年MM月dd號" 
         value = "${roVO.startdate}" /></td>
<td><fmt:formatDate pattern = "yyyy年MM月dd號" 
         value = "${roVO.enddate}" /></td>
			
<td><fmt:formatDate pattern = "yyyy年MM月dd號" 
         value = "${roVO.returndate}" /></td>				
<td>${roVO.fine}</td>
			<td>${roVO.total}</td>
			<td>${roVO.rank}</td>
			<td>${roVO.status}</td>
			<td>${roVO.note}</td>	
<!-- 			<td> -->
<%-- 			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/member/member.do"> --%>
<!-- 			     <input type="submit" value="修改">  -->
<%-- 			     <input type="hidden" name="memno" value="${roVO.memno}"> --%>
<%-- 			     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller--><!-- 目前尚未用到  --> --%>
<!-- 			     <input type="hidden" name="action"	value="getOne_For_Update"></FORM> -->
<!-- 			</td> -->
<!-- 			<td> -->
<%-- 			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/member/member.do"> --%>
<!-- 			    <input type="submit" value="刪除"> -->
<%-- 			    <input type="hidden" name="memno" value="${memVO.memno}"> --%>
<%-- 			    <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller--> --%>
<!-- 			    <input type="hidden" name="action"value="delete"></FORM> -->
<!-- 			</td> -->
		</tr>
		</c:if>
	</c:forEach>
</table>
<%-- <%@ include file="pages/page2.file" %> --%>

<br>本網頁的路徑:<br><b>
   <font color=blue>request.getServletPath():</font> <%= request.getServletPath()%><br>
   <font color=blue>request.getRequestURI(): </font> <%= request.getRequestURI()%> </b>
</div>



</body>
<script src="https://code.jquery.com/jquery.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
</html>
