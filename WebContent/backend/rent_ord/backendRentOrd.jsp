<%@page import="java.sql.Timestamp"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.rent_ord.model.*"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>租賃單管理-AutoBike</title>
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


		<div class="topTitle">
            <h1>租賃單管理系統</h1>
        </div>
        

        
<%
	RentOrdVO roVO = (RentOrdVO) request.getAttribute("roVO"); //MotorServlet.java (Concroller), 存入req的VO物件 (包括幫忙取出的VO, 也包括輸入資料錯誤時的VO物件)
%>       
        
         		<div class="container-fluid">       
<jsp:useBean id="roSvc" scope="page" class="com.rent_ord.model.RentOrdService"/>
<!--block1 --><div id="block1" class="col-xs-12 col-sm-4">
				
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
					
<!-- 標籤面板：開始 -->					
<!-- 標籤面板：標籤區開始 --><div role="tabpanel">
					      <ul class="nav nav-tabs" role="tablist">
					          <li role="presentation" class="active">
					              <a href="#tab1" aria-controls="tab1" role="tab" data-toggle="tab">查詢</a>
					          </li>
					          <li role="presentation">
					              <a href="#tab2" aria-controls="tab2" role="tab" data-toggle="tab">新增</a>
					          </li>
					          <li role="presentation">
					              <a href="#tab3" aria-controls="tab3" role="tab" data-toggle="tab">修改</a>
					          </li>
					          <li role="presentation">
					              <a href="#tab4" aria-controls="tab4" role="tab" data-toggle="tab">刪除</a>
					          </li>
					          <li role="presentation">
					              <a href="#tab5" aria-controls="tab3" role="tab" data-toggle="tab">?</a>
					          </li>
					          <li role="presentation">
					              <a href="#tab6" aria-controls="tab4" role="tab" data-toggle="tab">?</a>
					          </li>
<!-- 標籤面板：標籤區結束 -->	  </ul>				  

<!-- 標籤面板：內容區開始 -->     <div class="tab-content">
							  <div role="tabpanel" class="tab-pane active" id="tab1">
					          		<fieldset>
					          		<legend>租賃單查詢</legend>
		<!--form功能 單一查詢  -->
									<form method="get" action="rentOrd.do">
										<div class="InputForm">
											<label class="title">查詢</label> 
												<select name="rentno" onchange="queryRentOrdByRentOrdPK(this.value)">
						 							<c:forEach var="roVO" items="${roSvc.all}">
														<option value="${roVO.rentno}">
															${roVO.rentno}
														</option>
													</c:forEach> 
												</select><br />
										</div>
<!-- 										<div class="InputForm">
											<input type="hidden" name="action" value="query">
											<input type="submit" value="query" class="click" /> 
										</div>	 -->
									</form>
									
<!--錨點div:單筆顯示   showSingleQueryResult  --> 

									<div id="showSingleQueryResult"></div>


<%-- 									<%
										RentOrdVO roQueryVO = (RentOrdVO)request.getAttribute("roQueryVO");
									%>
									租賃單號:<c:out value="${roQueryVO.rentno}" default="無資料"/><br>
									會員編號:<c:out value="${roQueryVO.memno}" default="無資料"/><br>
									車輛編號:<c:out value="${roQueryVO.motno}" default="無資料"/><br>
									交車據點:<c:out value="${roQueryVO.slocno}" default="無資料"/><br>
								            還車據點:<c:out value="${roQueryVO.rlocno}" default="無資料"/><br>
									起始里程:<c:out value="${roQueryVO.milstart}" default="無資料"/><br>
									結束里程:<c:out value="${roQueryVO.milend}" default="無資料"/><br>
									填表日期:<fmt:formatDate pattern = "yyyy-MM-dd" value = "${roQueryVO.filldate}" /><br>
									起始時間:<fmt:formatDate pattern = "yyyy-MM-dd" value = "${roQueryVO.startdate}" /><br>
									結束日期:<fmt:formatDate pattern = "yyyy-MM-dd" value = "${roQueryVO.enddate}" /><br>
									還車日期:<fmt:formatDate pattern = "yyyy-MM-dd" value = "${roQueryVO.returndate}" /><br>
									        罰金:<c:out value="${roQueryVO.fine}" default="無資料"/><br>
									    總金額:<c:out value="${roQueryVO.total}" default="無資料"/><br>
									        評價:<c:out value="${roQueryVO.rank}" default="無資料"/><br>
									        狀態:<c:out value="${roQueryVO.status}" default="無資料"/><br>
									        備註<c:out value="${roQueryVO.note}" default="無資料"/><br> --%>


																
									</fieldset>
							  </div>
					          <div role="tabpanel" class="tab-pane" id="tab2">
		<!--form功能 新增  -->
							     	<fieldset>
								    <legend>租賃單新增</legend>
									<form method="post" action="rentOrd.do">
									
										<div class="InputForm">
											<label class="title">會員編號</label><input type="text" name="memno" maxlength="10" /><br>
										</div>
										<div class="InputForm">
											<label class="title">車輛編號</label><input type="text" name="motno" maxlength="10" /><br>
										</div>
										<div class="InputForm">
											<label class="title">取車地點</label><input type="text" name="slocno" maxlength="10" /><br>
										</div>
										<div class="InputForm">
										    <label class="title">還車地點</label><input type="text" name="rlocno" maxlength="10" /><br>
										</div>
										<div class="InputForm">
											<label class="title">起始里程</label><input type="text" name="milstart"/><br>
										</div>
										<div class="InputForm">
											<label class="title">取車日期</label><input type="text" name="startdate"
												class="from" /><br>
										</div>										
										<div class="InputForm">
											<label class="title">還車日期</label><input type="text" name="enddate"
												class="to" /><br>
										</div>	
										<div class="InputForm">
											<label class="title">備註</label>
											<textarea name="note" rows="1" cols="20" placeholder="請在這裡輸入"></textarea>
											<br />
										</div>
										<div class="InputForm">
											<input type="hidden" name="action" value="insert">
											<input type="submit" value="submit" class="click" /> 
											<input type="reset" name="reset" value="reset" class="click" />
										</div>
									</form>
									</fieldset>
									<!--end: form功能 新增 -->
							  </div><!--tab1 -->
							  
					          <div role="tabpanel" class="tab-pane" id="tab3">
		<!--form功能 修改  -->
					                <fieldset>
							  	    <legend>租賃單修改</legend>
									<form method="get" action="rentOrd.do">
<!-- 									    //合理版本: 去掉 filldate, memno
	private static final String UPDATE = "UPDATE RENT_ORD set  motno=?,"
			+ " slocno=?, rlocno=?, milstart=?, milend=?, startdate=?, enddate=?,"
			+ "returndate=?, fine=?, total=?, rank=?, status=?, note=? where rentno = ?"; -->
									
									
										<div class="InputForm">
											<label class="title">租賃單號</label> 
												<select name="rentno">
						 							<c:forEach var="roVO" items="${roSvc.all}">
														<option value="${roVO.rentno}">
															${roVO.rentno}
														</option>
													</c:forEach> 
												</select><br />
										</div>								
										<div class="InputForm">
											<label class="title">車輛編號</label><input type="text" name="motno" maxlength="10" /><br>
										</div>
										<div class="InputForm">
											<label class="title">取車地點</label><input type="text" name="slocno" maxlength="10" /><br>
										</div>
										<div class="InputForm">
										    <label class="title">還車地點</label><input type="text" name="rlocno" maxlength="10" /><br>
										</div>
										<div class="InputForm">
											<label class="title">起始里程</label><input type="text" name="milstart"/><br>
										</div>
										<div class="InputForm">
											<label class="title">結束里程</label><input type="text" name="milend"/><br>
										</div>
										<div class="InputForm">
											<label class="title">起始時間</label><input type="text" name="startdate"class="from" /><br>
										</div>										
										<div class="InputForm">
											<label class="title">結束時間</label><input type="text" name="enddate" class="to" /><br>
										</div>
										<div class="InputForm">
											<label class="title">還車時間</label><input type="text" name="returndate" class="to" /><br>
										</div>
										<div class="InputForm">
											<label class="title">罰金</label><input type="text" name="fine" /><br>
										</div>
										<div class="InputForm">
											<label class="title">總金額</label><input type="text" name="total" /><br>
										</div>																				
										<div class="InputForm">
											<label class="title">評價</label><input type="text" name="rank" /><br>
										</div>										
										<div class="InputForm">
											<label class="title">狀態</label> 
												<select name="status">
														<option value="unpaid">待繳費
														<option value="unoccupied">未交車
														<option value="noshow">逾期未交
														<option value="noreturn">未還車
														<option value="overtime">逾期末還
														<option value="abnormalclosed">異常結案
														<option value="closed">正常結案
														<option value="other">其它
														</option>
												</select><br />
										</div>											
										<div class="InputForm">
											<label class="title">備註</label>
											<textarea name="note" rows="1" cols="20" placeholder="請在這裡輸入"></textarea>
											<br />
										</div>
										<div class="InputForm">
											<input type="hidden" name="action" value="update">
											<input type="submit" value="submit" class="click" /> 
											<input type="reset" name="reset" value="reset" class="click" />
										</div>
									</form>
									</fieldset>
									<!--end: form功能 修改 -->
							  
							  </div>
					          <div role="tabpanel" class="tab-pane" id="tab4">
					          		<fieldset>
					          		<legend>租賃單刪除</legend>
		<!--form功能 刪除  -->
									<form method="post" action="rentOrd.do">
										<div class="InputForm">
											<label class="title">待刪租賃單</label> 
												<select name="rentno">
						 							<c:forEach var="roVO" items="${roSvc.all}">
														<option value="${roVO.rentno}">
															${roVO.rentno}
														</option>
													</c:forEach> 
												</select><br />
										</div>
										<div class="InputForm">
											<input type="hidden" name="action" value="delete">
											<input type="submit" value="delete" class="click" /> 
										</div>	
									</form>
									</fieldset>
							  </div>
							  <div role="tabpanel" class="tab-pane" id="tab5">
					          		<fieldset>
					          		<legend>依??查詢</legend>
		<!--form功能 ??  -->
									<form method="post" action="rentOrd.do">
										<div class="InputForm">
											<label class="title">???</label> 										
										</div>
									</form>
									</fieldset>
							  </div>
							  <div role="tabpanel" class="tab-pane" id="tab6">
					          		<fieldset>
					          		<legend>依??查詢</legend>
		<!--form功能 ?? -->
									<form method="post" action="rentOrd.do">
										<div class="InputForm">
											<label class="title">???</label> 										
										</div>
									</form>
									</fieldset>
							  </div>
<!--標籤面板 內容區結束 -->      </div>
<!--標籤面板 結束 -->		   </div>
<!--end block1 --> </div>	

<!-- block3 表格 --> <div id="block3" class="col-xs-12 col-sm-8">
						<table id="QueryTable">
							  <thead>	
									<tr class="QueryTable_TR">
										<th>租賃單編號</th>
										<th>會員編號</th>		
										<th>車輛編號</th>
										<th>交車據點</th>				
										<th>還車據點</th>
										<th>里程數起始</th>
										<th>里程數結束</th>
										<th>填表日期</th>				
										<th>起始時間</th>
										<th>結束時間</th>
										<th>還車時間</th>
										<th>罰金</th>				
										<th>總金額</th>
										<th>評價</th>
										<th>狀態</th>	
										<th>備註</th>
										<th>修改</th>	
										<th>刪除</th>																			
									</tr>
								  </thead>
								  <tbody>				  		
							 		<c:forEach var="roVO" items="${roSvc.all}">
										<tr class="QueryTable_TR">
											<td>${roVO.rentno}</td>
											<td>${roVO.memno}</td>
											<td>${roVO.motno}</td>	
											<td>${roVO.slocno}</td>
											<td>${roVO.rlocno}</td>
											<td>${roVO.milstart}</td>
											<td>${roVO.milend}</td>
											<td><fmt:formatDate pattern = "yyyy-MM-dd" value = "${roVO.filldate}" /></td>
											<td><fmt:formatDate pattern = "yyyy-MM-dd" value = "${roVO.startdate}" /></td>									
											<td><fmt:formatDate pattern = "yyyy-MM-dd" value = "${roVO.enddate}" /></td>
											<td><fmt:formatDate pattern = "yyyy-MM-dd" value = "${roVO.returndate}" /></td>
											<td>${roVO.fine}</td>
											<td>${roVO.total}</td>
											<td>${roVO.rank}</td>
											<td>${roVO.status}</td>
											<td>${roVO.note}</td>
											<td>
											<input type="hidden" name="action" value="update">
											<input type="submit" value="update" class="click2" /> 
											</td>
											<td>
											<input type="hidden" name="action" value="delete">
											<input type="submit" value="delete" class="click2" /> 
											</td>								
										</tr>
									</c:forEach>							
							  </tbody>	 	  								
						</table>
					<script src="Modified/QueryTablePagination.js"></script> 		
					</div>
<!--end: block3 --> 	
<!--container--></div>	

 
<!--RWD部分:下面兩行我拿掉一行和JQuery有關的script, 不然datepicker會衝到  -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>

</body>
</html>

<%-- <% DateFormat df = new SimpleDateFormat("yyyy-MM"); %> --%>
<%-- <c:set scope="page" var="temp">
 <c:out value="${motorVO.manudate}"/> 
</c:set>
<% 
  String str = String.valueOf(pageContext.getAttribute("temp"));
  java.util.Date du = (java.util.Date)(df.parse(str));
  String date = df.format(du);	
  <td><%=date %></td> 
%>	 --%>
