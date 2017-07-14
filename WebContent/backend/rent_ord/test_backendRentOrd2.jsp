<%@page import="java.sql.Timestamp"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">	

	<script src="http://code.jquery.com/jquery-1.12.4.js"></script>
	<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
	<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css"/>	
 	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">  
	<link rel="stylesheet" type="text/css" href="//cdn.datatables.net/1.10.15/css/jquery.dataTables.min.css">
	<script type="text/javascript" src="//cdn.datatables.net/1.10.15/js/jquery.dataTables.min.js"></script>
	
   <title>Test</title>
</head>

<style>

#myDiv {
  display: none;
  text-align: center;
}


/*自定*/
	 th, tr{
		/*死都不換行*/
		white-space:nowrap;
	} 
	.showHideColumn{
		cursor:pointer;
		color:blue;
	}
</style>

<body>
 <jsp:useBean id="roSvc" scope="page" class="com.rent_ord.model.RentOrdService"/>
<jsp:useBean id="locSvc" scope="page" class="com.location.model.LocationService"/>
<jsp:useBean id="motorSvc" scope="page" class="com.motor.model.MotorService"/>
						<div style="padding:5px; padding-left:0px">
							<b>Show / Hide Columns: </b>
							<a class="showHideColumn" data-columnindex="0">租賃單號</a> -
							<a class="showHideColumn" data-columnindex="1">會員編號</a> -
							<a class="showHideColumn" data-columnindex="2">車輛編號</a> -
							<a class="showHideColumn" data-columnindex="3">交車據點</a> -
							<a class="showHideColumn" data-columnindex="4">還車據點</a> -
							<a class="showHideColumn" data-columnindex="5">年份</a> -
							<a class="showHideColumn" data-columnindex="6">填表日</a> -
							<a class="showHideColumn" data-columnindex="7">起始日</a> -
							<a class="showHideColumn" data-columnindex="8">結束日</a> -
							<a class="showHideColumn" data-columnindex="9">還車日</a> -
							<a class="showHideColumn" data-columnindex="10">罰金</a> -
							<a class="showHideColumn" data-columnindex="11">總金額</a> -
							<a class="showHideColumn" data-columnindex="12">評價</a> -
							<a class="showHideColumn" data-columnindex="13">狀態</a> -
						</div>												
						<table id="dataTable" class="stripe hover" width="100%" cellspacing="0">
							  <thead>	
									<tr>
										<th>租賃單號</th>
										<th>會員編號</th>		
										<th>車輛編號</th>
										<th>交車據點</th>				
										<th>還車據點</th>
										<th>年份</th>
										<th>填表日</th>				
										<th>起始日</th>
										<th>結束日</th>
										<th>還車日</th>
										<th>罰金</th>				
										<th>總金額</th>
										<th>評價</th>
										<th>狀態</th>	
										<th>修改</th>	
										<th>刪除</th>											
									</tr>
								  </thead>
								  <tbody>				  		
							 		<c:forEach var="roVO" items="${roSvc.all}">
										<tr">
											<td>${roVO.rentno}</td>
											<td>${roVO.memno}</td>
											<td>${roVO.motorVO.motno}</td>	
											<td>${roVO.slocno}</td>
											<td>${roVO.rlocno}</td>
 											<td><fmt:formatDate pattern = "yyyy" value = "${roVO.filldate}" /></td>
											<td><fmt:formatDate pattern = "MM/dd" value = "${roVO.filldate}" /></td>
											<td><fmt:formatDate pattern = "MM/dd" value = "${roVO.startdate}" /></td>									
											<td><fmt:formatDate pattern = "MM/dd" value = "${roVO.enddate}" /></td>
											<td><fmt:formatDate pattern = "MM/dd" value = "${roVO.returndate}" /></td>
											<td>${roVO.fine}</td>
											<td>${roVO.total}</td>
											<td>${roVO.rank}</td>
											<td>${roVO.status}</td>
											<td>
												<form method="POST" target="print_popup" 
						      				  		  action="<%=request.getContextPath()%>/backend/rent_ord/rentOrd.do" 
						       						  onsubmit="window.open('about:blank','print_popup','width=1000,height=900');">
													<input type="hidden" name="rentno" value="${roVO.rentno}">
													<input type="hidden" name="action" value="query_for_update">
													<input type="submit" class="btn btn-default" value="修改">
												</form>	
											</td>
											
											<!-- 
											<td>${roVO.milstart}</td>
											<td>${roVO.milend}</td>
											<td>${roVO.note}</td>-->	
											<td>
												<form method="POST" action="<%=request.getContextPath()%>/backend/rent_ord/rentOrd.do">
													<input type="hidden" name="action" value="delete">
													<input type="hidden" name="rentno" value="${roVO.rentno}">
													<input type="submit" value="刪除" class="btn btn-danger" disabled/>
												</form> 
											</td>											
												
										</tr>
									</c:forEach>							
							  </tbody> 	  								
						</table>

<script>
	$(document).ready(function(){
		var datatableInstance = $('#dataTable').DataTable();
		

		$('.showHideColumn').on('click',function(){
			var tableColumn = datatableInstance.column($(this).attr('data-columnindex'));
			tableColumn.visible(!tableColumn.visible());
		})
		
	});
	



</script>    

</body>
</html>


