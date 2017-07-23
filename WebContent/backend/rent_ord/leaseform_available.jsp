<%@page import="java.sql.Timestamp"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.rent_ord.model.*"%>
<%@ page import="com.member.model.*"%>
<%@ page import="com.motor_model.model.*"%>
<%@ page import="com.location.model.*"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">	

 	<link rel="stylesheet" href="<%=request.getContextPath()%>/backend/Modified/jquery_ui_1_10_3_theme.css"/>	
	<link rel="stylesheet" href="<%=request.getContextPath()%>/backend/Modified/twitter_bootstrap_3_3_7_min.css">     
	<link rel="stylesheet" href="<%=request.getContextPath()%>/backend/Modified/main.css" >
	<link rel="stylesheet" href="<%=request.getContextPath()%>/backend/Modified/form.css" >

	<title>AutoBike 交車</title>
</head>


<body>

<body>
<%

String rentno = request.getParameter("rentno");
String status = request.getParameter("status");
String action = request.getParameter("action");
pageContext.setAttribute("action", action);

MotorForRentOrdVO motorQueryVO = (MotorForRentOrdVO)request.getAttribute("motorQueryVO");
RentOrdVO roQueryVO = (RentOrdVO)request.getAttribute("roQueryVO");
MemberVO memQueryVO = (MemberVO)request.getAttribute("memQueryVO");
MotorModelVO mmQueryVO = (MotorModelVO)request.getAttribute("mmQueryVO");
LocationVO slocQueryVO =  (LocationVO)request.getAttribute("slocQueryVO");
LocationVO rlocQueryVO =  (LocationVO)request.getAttribute("rlocQueryVO");

Map<String, String> statusMap = new HashMap<String, String>();
statusMap.put("uncompleted", "簡易註冊");
statusMap.put("unconfirmed", "還未認證");
statusMap.put("verifing", "等待認證");
statusMap.put("confirmed", "認證合格");	
%>	
<c:set var="now" value="<%=new java.util.Date()%>" />


<%-- This is leaseForm.jsp<br>
rentNo =  <c:out value="${roQueryVO.rentno}" default="no value"/><br>
memName = <c:out value="${memQueryVO.memname}" default="no value"/><br> 
motorNo = <c:out value="${motorQueryVO.motno}" default="no value"/><br> 
slocName = <c:out value="${slocQueryVO.locname}" default="no value"/><br> 
rlocName = <c:out value="${rlocQueryVO.locname}" default="no value"/><br> 
mmName =  <c:out value="${mmQueryVO.name}" default="no value"/><br> 
status: <c:out value="<%=status%>" default="no value"/><br>
action: <c:out value="${action}" default="no value"/><br> --%>


	<div class="container-fluid">
		<div class="row">
			<div class="col-xs-12 col-sm-10 col-sm-offset-1">

				<!-- 
				1.在form-group外層添加一個 .form-horizontal
				2.在label添加一個格線以及一個control-label的class
				3.把input輸入欄位，包裹在一個格線當中 
				-->
				<h1>AutoBike - 租賃單 - 可取車</h1>

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
								<input type="text" name="slocname" value="${slocQueryVO.locname}營業所" 
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
								<input type="text" name="rlocname" value="${rlocQueryVO.locname}營業所"  
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
								<input type="text" name="enddate" 
								value="<fmt:formatDate pattern="yyyy-MM-dd" value="${roQueryVO.enddate}"/>" 
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
							<c:set scope="page" var="temp"><c:out value="${memQueryVO.status}"/></c:set>
							<%String key = String.valueOf(pageContext.getAttribute("temp"));%>
							<div class="form-group  arr1">
								<label for="aa" class="col-xs-12 col-sm-4 control-label">
									實名認證:
								</label>
								<div class="col-xs-12 col-sm-8 innerDiv">
									<input type="text" name="status" value="<%=statusMap.get(key)%>" class="form-control" readonly>
									<input type="hidden" name="status" value="${memQueryVO.status}">
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
									結束里程(km):
								</label>
									<div class="col-xs-12 col-sm-8 innerDiv">
										<input type="text" name="milend" 
										value="${(action =='change_lease_form_to_return_form')?'NA':''}" 
										class="form-control" readonly>
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
	
<jsp:useBean id="ecSvc" scope="page" class="com.emt_cate.model.EmtCateService"/>	
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
										<input type="text" name="equip${emtVO.note}" value="${emtVO.note}" 
										class="form-control" readonly>
									</div>
								</div>
							</div>
						</c:forEach>
							
					</div>
	
					<div class="clear"></div>
					共 <%=count%> 件裝備。
					<hr>
					<h3>交接確認項目</h3>
					<hr>
					
					<c:if test="<%=count!=0 %>">
					<div><h5>請在裝備交接給客戶後，勾選下方對應欄位進行確認:</h5></div>
					</c:if>
						<div>
							<c:forEach var="emtVO" items="${get_equipmentVOs_by_rentno}">
								<label class="checkbox-inline">
									<input type="checkbox" name="check${emtVO.emtno}" 
									value="裝備${emtVO.emtno}-${ecSvc.getOneEmtCate(emtVO.ecno).type}交接" class="check_group">
									裝備${emtVO.emtno}-${ecSvc.getOneEmtCate(emtVO.ecno).type}
								</label>
							</c:forEach>																							
						</div>
						<div><h4>還車確認:</h4></div>
						<div class="checkbox">
							<label>
								<input type="checkbox" name="check1" id="check1" value="檢查項目1" class="check_group">
								檢查項目1: <p>核對會員證件與基本資料，是否與網路填寫的資料相符。</p>
							</label>
						</div>
						<div class="checkbox">
							<label>
								<input type="checkbox" name="check2" id="check2"  value="檢查項目2" class="check_group">
								檢查項目2: <p>陪同客戶確認車輛狀況: 煞車（按一按看手感）、輪胎（看胎紋）、大燈有沒有亮、發動的聲音、機車外殼..等等。</p>
							</label>
						</div>
						<div class="checkbox">
							<label>
								<input type="checkbox" name="check3" id="check3"  value="檢查項目3" class="check_group">
								檢查項目3: <p>告知客戶車輛注意事項，如部分車輛加油指定95無鉛，不能混加..等等。</p>
							</label>
						</div>
						<div class="checkbox">
							<label>
								<input type="checkbox" name="check4" id="check4"  value="檢查項目4" class="check_group">
								檢查項目4: <p>告知客戶事故處理事項，如:車輛事故、拋錨時的注意事項。</p>
							</label>
						</div>	
					<div class="clear"></div>
	
					<hr>	
								
					<h3>備註說明</h3>	
	
				    <textarea class="form-control" rows="5" name="note" id="note" >${roQueryVO.note}</textarea>
				    
					<hr>
					<p class="text-center">
					    <input type="hidden" name="action" value="after_available_form">
	    					<button type="submit" onClick="return check()" class="btn btn-success btn-lg">
								完成取車
							</button>
						<a href="javascript:window.close();" class="btn btn-danger btn-lg">
							取消動作
						</a>
					</p>
				</form>
			</div>
		</div>
	</div>
	
	<script src="<%=request.getContextPath()%>/backend/Modified/jquery_ui_1_10_3.js"></script>  	
	<script src="<%=request.getContextPath()%>/backend/Modified/jquery_1_10_1_min.js"></script>
	<script src="<%=request.getContextPath()%>/backend/Modified/twitter_bootstrap_3_3_7_min.js"></script>
	<script src="<%=request.getContextPath()%>/backend/rent_ord/Modified/datepicker_for_rent_ord.js"></script>
	<script src="<%=request.getContextPath()%>/backend/rent_ord/Modified/motorKanli_js.js"></script>
	
	<script type="text/javascript">
	
	function check(){
		
		var check = document.getElementsByClassName('check_group');
		var message = "";

		//checkbox
		for(i = 0; i< check.length ; i++){
			if(check[i].checked==false){
				message += check[i].value+", ";
			}
		}
		
		//result:
		if(message.length!=0){
			message +="..等注意事項尚未勾選確認."
			alert(message);
			return false;
		}
	}
	
	</script>
</body>
</html>
