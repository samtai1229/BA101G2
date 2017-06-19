<%@page import="java.sql.Timestamp"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.motor.model.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>車輛查詢-AutoBike</title>
<meta name="description" content="">
<meta name="keywords" content="">
<link href="" rel="stylesheet">
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<link rel="stylesheet" href="Modified/backendHP_css.css">
<link href="Modified/main.css" rel="stylesheet">
<script src="Modified/motorKanli_js.js"></script>
<script src="Modified/datepicker.js"></script>

</head>
<body>
	<table id="QueryTable">
		<thead>
			<tr class="QueryTable_TR">
				<th>租賃單編號</th>
				<th>租賃單狀態</th>
				<th>交車據點</th>
				<th>起始時間</th>
				<th>會員編號</th>
				<th>車輛編號</th>
				<th>還車據點</th>
				<th>結束時間</th>
				<th>備註</th>
				<th>修改訂單</th>
				<th>交車</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="roVO" items="${get_for_lease_view}">
				<tr class="QueryTable_TR">
					
					<!-- 產生超連結，直接進入表格頁面  -->
					<c:if test="${roVO.status == 'unoccupied' || roVO.status =='noshow'}">
						<td><a href="javascript:presses(${roVO.rentno})">${roVO.rentno}</a></td>						 
					</c:if>
					<c:if test="${roVO.status == 'unpaid'}">
						<td><c:out value="${roVO.rentno}" default="無資料" /></td>						 
					</c:if> 					 

					<td><c:out value="${roVO.status}" default="無資料" /></td>
					<td><c:out value="${roVO.slocno}" default="無資料" /></td>
					<td><fmt:formatDate pattern="yyyy-MM-dd" value="${roVO.startdate}" /></td>
					<td><c:out value="${roVO.memno}" default="無資料" /></td>
					<td><c:out value="${roVO.motno}" default="無資料" /></td>
					<td><c:out value="${roVO.rlocno}" default="無資料" /></td>
					<td><fmt:formatDate pattern="yyyy-MM-dd" value="${roVO.enddate}" /></td>
					<td><c:out value="${roVO.note}" default="無資料" /></td>
					<td><input type="hidden" name="action" value="update">
						<input type="submit" value="update" class="click2" /></td>
					<td><input type="hidden" name="action" value="lease">
						<input type="submit" value="lease" class="click2" disabled /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<script>
function presses(rentno){
	document.open("rentOrd.do?rentno="+rentno+"&action=lease_ord_form", "" ,"height=250,width=850,left=65,top=157,resizable=yes,scrollbars=yes");
}
</script>
	<script src="Modified/QueryTablePagination.js"></script>
</body>
</html>

