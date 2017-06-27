<%@page import="java.sql.Timestamp"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.rent_ord.model.*"%>
<%@ page import="com.motor.model.*"%>
<%@ page import="com.member.model.*"%>
<%@ page import="com.motor_model.model.*"%>
<%@ page import="com.location.model.*"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">	

	<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
	<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>  	
	<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.11/jquery-ui.min.js"></script>
	<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css"/>	
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">     
<%--<link rel="stylesheet" href="<%=request.getContextPath()%>/backend/Modified/backendHP_css.css"> --%>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/backend/Modified/main.css" >
	<link rel="stylesheet" href="<%=request.getContextPath()%>/backend/Modified/form.css" >

	<title>AutoBike 還車</title>
</head>

<body>

<body>
<jsp:useBean id="ecSvc" scope="page" class="com.emt_cate.model.EmtCateService"/>

<%
String rentno = request.getParameter("rentno");
String status = request.getParameter("status");
String action = request.getParameter("action");
String differDate = request.getParameter("differDate");
pageContext.setAttribute("action", action);
pageContext.setAttribute("differDate", differDate);

MotorVO motorQueryVO = (MotorVO)request.getAttribute("motorQueryVO");
RentOrdVO roQueryVO = (RentOrdVO)request.getAttribute("roQueryVO");
MemberVO memQueryVO = (MemberVO)request.getAttribute("memQueryVO");
MotorModelVO mmQueryVO = (MotorModelVO)request.getAttribute("mmQueryVO");
LocationVO slocQueryVO =  (LocationVO)request.getAttribute("slocQueryVO");
LocationVO rlocQueryVO =  (LocationVO)request.getAttribute("rlocQueryVO");
%>

<c:set var="now" value="<%=new java.util.Date()%>" />

This is returnForm.jsp<br>

rentNo     = <c:out value="${roQueryVO.rentno}"    default="no value"/><br>
memName    = <c:out value="${memQueryVO.memname}"  default="no value"/><br> 
motorNo    = <c:out value="${motorQueryVO.motno}"  default="no value"/><br> 
slocName   = <c:out value="${slocQueryVO.locname}" default="no value"/><br> 
rlocName   = <c:out value="${rlocQueryVO.locname}" default="no value"/><br> 
mmName     = <c:out value="${mmQueryVO.name}"      default="no value"/><br>
differDate = <c:out value="${differDate}"          default="no value"/><br>

status: <c:out value="<%=status%>" default="no value"/><br>
action: <c:out value="${action}" default="no value"/><br>

<%! 
	int equipsPrice = 0; 
	int motorPrice = 0;
	int rentPerDay = 0;
	int fineDays = 0;
%>

<c:forEach var="emtVO" items="${get_equipmentVOs_by_rentno}">
	<c:set scope="page" var="price">
		<c:out value="${ecSvc.getOneEmtCate(emtVO.ecno).price}"/>
	</c:set> 	
	<%
	String str = String.valueOf(pageContext.getAttribute("price"));
	equipsPrice +=  java.lang.Integer.parseInt(str);
	%>
</c:forEach>

<c:set scope="page" var="differDateStr">
	<c:out value="${differDate}"/>
</c:set>
<c:set scope="page" var="motorPrice">
	<c:out value="${mmQueryVO.renprice}"/>
</c:set>



<%
String differDateStr = String.valueOf(pageContext.getAttribute("differDateStr"));
fineDays = java.lang.Integer.parseInt(differDateStr);

String str2 = String.valueOf(pageContext.getAttribute("motorPrice"));
motorPrice = java.lang.Integer.parseInt(str2);

rentPerDay = motorPrice + equipsPrice;
%>

=======Equip(s)Price:<%=equipsPrice %><br>
=======motorPrice:<%=motorPrice %><br>
=======rentPerDay:<%=rentPerDay%><br>
=======fineDays:<%=fineDays %><br>
=======totalFine<%=fineDays*rentPerDay%>


	<div class="container-fluid">
		<div class="row">
			<div class="col-xs-12 col-sm-10 col-sm-offset-1">

				<!-- 
				1.在form-group外層添加一個 .form-horizontal
				2.在label添加一個格線以及一個control-label的class
				3.把input輸入欄位，包裹在一個格線當中 
				-->
				<h1>AutoBike - 還車管理 - 逾期未還(建構中)</h1>

				<hr>
				
<!-- FORM -->
				<form method="post" action="<%=request.getContextPath()%>/backend/rent_ord/rentOrd.do">
						
					<div><h3>租賃單資料</h3></div>
					<hr>
					<div class="form-horizontal member">
						<div class="form-group  arr1">
							<label for="aa" class="col-xs-12 col-sm-4 control-label" >
								租賃編號:
							</label>
							<div class="col-xs-12 col-sm-8 innerDiv">
								<input type="text" name="rentno" value="${roQueryVO.rentno}" 
									class="form-control" readonly>
							</div>
						</div>
	
						<div class="form-group  arr1">
							<label for="aa" class="col-xs-12 col-sm-4 control-label">
								租賃狀態:
							</label>
							<div class="col-xs-12 col-sm-8 innerDiv">
								<input type="text" name="status" value="${roQueryVO.status}" 
								class="form-control" readonly>
							</div>
						</div>
	
						<div class="form-group  arr1">
							<label for="aa" class="col-xs-12 col-sm-4 control-label">
								交車據點:
							</label>
							<div class="col-xs-12 col-sm-8 innerDiv">
								<input type="text" name="slocno" value="${slocQueryVO.locname}營業所" 
								class="form-control" readonly>
							</div>
						</div>
	
						<div class="form-group  arr1">
							<label for="aa" class="col-xs-12 col-sm-4 control-label">
								交車時間:
							</label>
							<div class="col-xs-12 col-sm-8 innerDiv">
								<input type="text" name="startdate" 
								value="<fmt:formatDate pattern="yyyy-MM-dd" value="${roQueryVO.startdate}"/>" 
								class="form-control" readonly>
							</div>
						</div>
	
						<div class="form-group  arr1">
							<label for="aa" class="col-xs-12 col-sm-4 control-label">
								還車據點:
							</label>
							<div class="col-xs-12 col-sm-8 innerDiv">
								<input type="text" name="rlocno" value="${rlocQueryVO.locname}營業所" 
								class="form-control" readonly>
							</div>
						</div>
	
	
						<div class="form-group  arr1">
							<label for="aa" class="col-xs-12 col-sm-4 control-label">
								還車時間:
							</label>
							<div class="col-xs-12 col-sm-8 innerDiv">							
								<input type="text" name="enddate" 
								value="<fmt:formatDate pattern="yyyy-MM-dd" value="${roQueryVO.returndate}"/>" 
								class="form-control" readonly>
							</div>
						</div>
	
						<div class="form-group  arr1">
							<label for="aa" class="col-xs-12 col-sm-4 control-label">
								結束時間:
							</label>
							<div class="col-xs-12 col-sm-8 innerDiv">
								<input type="text" name="returndate" 
								value="<fmt:formatDate pattern="yyyy-MM-dd" value="${now}"/>" 
								class="form-control" readonly>
							</div>
						</div>
	
						<div class="form-group  arr1">
							<label for="aa" class="col-xs-12 col-sm-4 control-label">
								租金統計:
							</label>
							<div class="col-xs-12 col-sm-8 innerDiv">
								<input type="text" name="total" value="${roQueryVO.total}" 
								class="form-control" readonly>
							</div>
						</div>
	
						<div class="form-group  arr1">
							<label for="aa" class="col-xs-12 col-sm-4 control-label">
								<mark>累計罰金:</mark>
							</label>
							<div class="col-xs-12 col-sm-8 innerDiv">
								<input type="text" name="fine" value="<%=fineDays*rentPerDay%>" 
								class="form-control">
							</div>
						</div>
	
					</div>
	
	
	
					<div class="clear"></div>
	
	
	
					<div><h3>會員資料</h3></div>
					<hr>
	
					<div class="form-horizontal member">
							<div class="form-group  arr1">
								<label for="aa" class="col-xs-12 col-sm-4 control-label" >
									會員編號:
								</label>
								<div class="col-xs-12 col-sm-8 innerDiv">
									<input type="text" name="memno" value="${memQueryVO.memno}" 
									class="form-control" readonly>
								</div>
							</div>
							<div class="form-group  arr1">
								<label for="aa" class="col-xs-12 col-sm-4 control-label">
									姓名:
								</label>
								<div class="col-xs-12 col-sm-8 innerDiv">
									<input type="text" name="memname" value="${memQueryVO.memname}" 
									class="form-control" readonly>
								</div>
							</div>
							<div class="form-group  arr1">
								<label for="aa" class="col-xs-12 col-sm-4 control-label">
									電話:
								</label>
								<div class="col-xs-12 col-sm-8 innerDiv">
									<input type="text" name="phone" value="${memQueryVO.phone}" 
									class="form-control" readonly>
								</div>
							</div>
							<div class="form-group  arr1">
								<label for="aa" class="col-xs-12 col-sm-4 control-label">
									實名認證:
								</label>
								<div class="col-xs-12 col-sm-8 innerDiv">
									<input type="text" name="status" value="${memQueryVO.status}" 
									class="form-control" readonly>
								</div>
							</div>		
						</div>
					<div class="clear"></div>
	
					<h3>車輛資料</h3>
					<hr>
	
					<div class="form-horizontal motor">
	
							<div class="form-group  arr1">
								<label for="aa" class="col-xs-12 col-sm-4 control-label" >
									編號:
								</label>
								<div class="col-xs-12 col-sm-8 innerDiv">
									<input type="text" name="motno" value="${motorQueryVO.motno}" 
									class="form-control" readonly>
								</div>
							</div>
	
							<div class="form-group  arr1">
								<label for="aa" class="col-xs-12 col-sm-4 control-label">
									型號:
								</label>
								<div class="col-xs-12 col-sm-8 innerDiv">
									<input type="text" name="modtype" value="${motorQueryVO.modtype}" 
									class="form-control" readonly>
								</div>
							</div>
	
							<div class="form-group  arr1">
								<label for="aa" class="col-xs-12 col-sm-4 control-label">
									廠牌:
								</label>
								<div class="col-xs-12 col-sm-8 innerDiv">
									<input type="text" name="brand" value="${mmQueryVO.brand}" 
									class="form-control" readonly>
								</div>
							</div>
	
	
							<div class="form-group  arr1">
								<label for="aa" class="col-xs-12 col-sm-4 control-label" >
									車牌號碼:
								</label>
								<div class="col-xs-12 col-sm-8 innerDiv">
									<input type="text" name="plateno" value="${motorQueryVO.plateno}" 
									class="form-control" readonly>
								</div>
							</div>
							
							
							<div class="form-group  arr1">
								<label for="aa" class="col-xs-12 col-sm-4 control-label">
									狀態:
								</label>
								<div class="col-xs-12 col-sm-8 innerDiv">
									<input type="text" name="status" value="${motorQueryVO.status}" 
									class="form-control" readonly>
								</div>
							</div>
	
	
	
							<div class="form-group  arr1">
								<label for="aa" class="col-xs-12 col-sm-4 control-label">
									名稱:
								</label>
								<div class="col-xs-12 col-sm-8 innerDiv">
									<input type="text" name="name" value="${mmQueryVO.name}" 
									class="form-control" readonly>
								</div>
							</div>
	
							<div class="form-group  arr1">
								<label for="aa" class="col-xs-12 col-sm-4 control-label">
									起始里程(km):
								</label>
								<div class="col-xs-12 col-sm-8 innerDiv">
									<input type="text" name="milstart" value="${motorQueryVO.mile}" 
									class="form-control" readonly>
								</div>
							</div>
	
							<div class="form-group  arr1">
								<label for="aa" class="col-xs-12 col-sm-4 control-label">
									<mark>結束里程(km):</mark>
								</label>
									<div class="col-xs-12 col-sm-8 innerDiv">
										<input type="text" name="milend" 
										value="${(action =='change_lease_form_to_return_form')?'NA':''}" 
										class="form-control">
									</div>
							</div>
							<div class="form-group  arr1">
								<label for="aa" class="col-xs-12 col-sm-4 control-label">
									排氣量:
								</label>
								<div class="col-xs-12 col-sm-8 innerDiv">
									<input type="text" name="displacement" value="${mmQueryVO.displacement}cc" 
									class="form-control" readonly>
								</div>
							</div>
						</div>
					
					
					<div class="clear"></div>
	
	
					<h3>裝備資料					
					<a class="btn btn-primary" role="button" data-toggle="collapse" href=".collapse" aria-expanded="true" aria-controls=".collapse">
						開啟 / 關閉
					</a></h3>
					<hr>


						<div class="form-horizontal equip">		
						<!--依實際裝備數量動態新增  -->
						<%int count=0; %>
						<c:forEach var="emtVO" items="${get_equipmentVOs_by_rentno}">
							<%++count; %>
							<div class="collapse" id="${emtVO.emtno}">
								<div class="form-group  arr1">
									<label for="aa" class="col-xs-12 col-sm-4 control-label" >
										裝備編號:
									</label>
									<div class="col-xs-12 col-sm-8 innerDiv">
										<input type="text" name="emtno" value="${emtVO.emtno}" 
										class="form-control" readonly>
									</div>
								</div>
	
								<div class="form-group  arr1">
									<label for="aa" class="col-xs-12 col-sm-4 control-label">
										種類:
									</label>
									<div class="col-xs-12 col-sm-8 innerDiv">
										<input type="text" name="${emtVO.emtno}_${ecSvc.getOneEmtCate(emtVO.ecno).type}" 
											   value="${ecSvc.getOneEmtCate(emtVO.ecno).type}" 
											   class="form-control" readonly>
									</div>
								</div>
	
								<div class="form-group  arr1">
									<label for="aa" class="col-xs-12 col-sm-4 control-label">
										狀態:
									</label>
									<div class="col-xs-12 col-sm-8 innerDiv">
										<input type="text" name="${emtVO.status}" value="${emtVO.status}" 
										class="form-control" readonly>
									</div>
								</div>
								
								<div class="form-group  arr1">
									<label for="aa" class="col-xs-12 col-sm-4 control-label">
										備註:
									</label>
									<div class="col-xs-12 col-sm-8 innerDiv">
										<input type="text" name="${emtVO.note}" value="${emtVO.note}" 
										class="form-control" readonly>
									</div>
								</div>
															
							</div>
						</c:forEach>
							
					</div>
	
					<div class="clear"></div>
					
					<h3>確認項目</h3>
					<hr>
					
					<div><h4>裝備歸還確認:</h4></div>
					<div>
						<c:forEach var="emtVO" items="${get_equipmentVOs_by_rentno}">
							<label class="checkbox-inline">
								<input type="checkbox" name="${emtVO.emtno}" id="">
								裝備${emtVO.emtno}
							</label>
	
							
						</c:forEach>																							
					</div><br>

					<div><h4>還車確認:</h4></div>
					<div class="checkbox">
						<label>
							<input type="checkbox" name="" id="">
							檢查項目1: <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aliquid eos perferendis, dolores dignissimos praesentium. Deserunt enim iure ullam error suscipit.</p>
						</label>
					</div>
					<div class="checkbox">
						<label>
							<input type="checkbox" name="" id="">
							檢查項目2: <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Quisquam ratione, rem eos quia, perspiciatis temporibus ipsum nulla quo molestiae. Nulla?</p>
						</label>
					</div>
					<div class="checkbox">
						<label>
							<input type="checkbox" name="" id="">
							檢查項目3: <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Excepturi, nam ullam ut nulla error minima magni eaque corporis dolores culpa.</p>
						</label>
					</div>
					<div class="checkbox">
						<label>
							<input type="checkbox" name="" id="">
							檢查項目4: <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Blanditiis laborum quisquam nesciunt ipsam sequi animi nulla nisi iusto, aliquam sint!</p>
						</label>
					</div>
					<div class="checkbox">
						<label>
							<input type="checkbox" name="" id="">
							檢查項目5: <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Voluptatem repudiandae consequuntur expedita officia eligendi explicabo fugiat mollitia, ipsam dignissimos, consectetur.</p>
						</label>
					</div>
					<div class="checkbox">
						<label>
							<input type="checkbox" name="" id="">
							檢查項目6: <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Repellendus fugiat exercitationem rem omnis blanditiis quia laborum necessitatibus natus corporis optio.</p>
						</label>
					</div>

					<div class="clear"></div>
					
					<h3>逾期還車-備註說明</h3>	
	
				    <textarea class="form-control" rows="5" name="note" id="note">${roQueryVO.note}</textarea>
	
					<div class="clear"></div>

					<hr>
					<p class="text-center">
					    <input type="hidden" name="action" value="after_overtime_form">
	    					<button type="submit" class="btn btn-success btn-lg">
								<i class="glyphicon glyphicon-ok"></i>異常結案
							</button>
						<a href="javascript:window.close();" class="btn btn-danger btn-lg">
						<i class="glyphicon glyphicon-remove"></i>取消動作</a>
					</p>
				</form>
			</div>
		</div>
	</div>
	<script src="https://code.jquery.com/jquery.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script src="<%=request.getContextPath()%>/backend/rent_ord/Modified/datepicker_for_rent_ord.js"></script>
	<script src="<%=request.getContextPath()%>/backend/rent_ord/Modified/motorKanli_js.js"></script>
</body>
</html>
