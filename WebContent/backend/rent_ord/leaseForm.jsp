<%@page import="java.sql.Timestamp"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.rent_ord.model.*"%>
<%@ page import="com.motor.model.*"%>
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
	<link rel="stylesheet" href="<%=request.getContextPath()%>/backend/Modified/backendHP_css.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/backend/Modified/main.css" >

	<title>AutoBike 交車</title>
</head>
	<style>
		.arr1{
			width: 300px;
			float:left;
/* 			border: 1px solid red; */
		}
		.clear{
			clear:both;
		}

		.innerDiv{
			width:200px;
		}
		 .control-label{
			/*死都不換行*/
			white-space:nowrap;
			padding-right: 1px;
			float: left;
		} 
		h1{
			text-align: center;
		}

	</style>
<body>

<body>
<%

String rentno = request.getParameter("rentno");
String status = request.getParameter("status");
String action = request.getParameter("action");

MotorVO motorQueryVO = (MotorVO)request.getAttribute("motorQueryVO");
RentOrdVO roQueryVO = (RentOrdVO)request.getAttribute("roQueryVO");

%>


<jsp:useBean id="roSvc" scope="page" class="com.rent_ord.model.RentOrdService"/>



This is leaseForm.jsp<br>
rentno = <c:out value="<%=rentno%>" default="no value"/><br>
status: <c:out value="<%=status%>" default="no value"/><br>
action: <c:out value="<%=action%>" default="no value"/><br>
action: <c:out value="<%=action%>" default="no value"/><br>





		<div class="container" fluid>
			<div class="row">
				<div class="col-xs-12 col-sm-10 col-sm-offset-1">

					<!-- 
					1.在form-group外層添加一個 .form-horizontal
					2.在label添加一個格線以及一個control-label的class
					3.把input輸入欄位，包裹在一個格線當中 
					-->
					<h1>租賃單-交車流程</h1>

					<hr>

					

					<form>

					<div><h3>租賃單資料</h3></div>
					<hr>

					<div class="form-horizontal member">
						<div class="form-group  arr1">
							<label for="aa" class="col-xs-12 col-sm-4 control-label" >
								租賃編號:
							</label>
							<div class="col-xs-12 col-sm-8 innerDiv">
								<input type="text" name="rentno" value="rentno" 
								class="form-control" disabled="disabled">
							</div>
						</div>

						<div class="form-group  arr1">
							<label for="aa" class="col-xs-12 col-sm-4 control-label">
								租賃狀態:
							</label>
							<div class="col-xs-12 col-sm-8 innerDiv">
								<input type="text" name="status" value="status" 
								class="form-control" disabled="disabled">
							</div>
						</div>

						<div class="form-group  arr1">
							<label for="aa" class="col-xs-12 col-sm-4 control-label">
								交車據點:
							</label>
							<div class="col-xs-12 col-sm-8 innerDiv">
								<input type="text" name="slocno" value="slocno" 
								class="form-control" disabled="disabled">
							</div>
						</div>

						<div class="form-group  arr1">
							<label for="aa" class="col-xs-12 col-sm-4 control-label">
								交車時間:
							</label>
							<div class="col-xs-12 col-sm-8 innerDiv">
								<input type="text" name="startdate" value="startdate" 
								class="form-control" disabled="disabled">
							</div>
						</div>

						<div class="form-group  arr1">
							<label for="aa" class="col-xs-12 col-sm-4 control-label">
								還車據點:
							</label>
							<div class="col-xs-12 col-sm-8 innerDiv">
								<input type="text" name="rlocno" value="rlocno" 
								class="form-control" disabled="disabled">
							</div>
						</div>


						<div class="form-group  arr1">
							<label for="aa" class="col-xs-12 col-sm-4 control-label">
								還車時間:
							</label>
							<div class="col-xs-12 col-sm-8 innerDiv">
								<input type="text" name="returndate" value="returndate" 
								class="form-control"  disabled="disabled">
							</div>
						</div>

						<div class="form-group  arr1">
							<label for="aa" class="col-xs-12 col-sm-4 control-label">
								結束時間:
							</label>
							<div class="col-xs-12 col-sm-8 innerDiv">
								<input type="text" name="enddate" value="enddate" 
								class="form-control"  disabled="disabled">
							</div>
						</div>

						<div class="form-group  arr1">
							<label for="aa" class="col-xs-12 col-sm-4 control-label">
								累計金額:
							</label>
							<div class="col-xs-12 col-sm-8 innerDiv">
								<input type="text" name="total" value="total" 
								class="form-control"  disabled="disabled">
							</div>
						</div>

						<div class="form-group  arr1">
							<label for="aa" class="col-xs-12 col-sm-4 control-label">
								備註:
							</label>
							<div class="col-xs-12 col-sm-8 innerDiv">
								<input type="text" name="note" value="note" 
								class="form-control"  disabled="disabled">
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
									<input type="text" name="memno" value="memno" 
									class="form-control" disabled="disabled">
								</div>
							</div>
							<div class="form-group  arr1">
								<label for="aa" class="col-xs-12 col-sm-4 control-label">
									姓名:
								</label>
								<div class="col-xs-12 col-sm-8 innerDiv">
									<input type="text" name="memname" value="memname" 
									class="form-control" disabled="disabled">
								</div>
							</div>
							<div class="form-group  arr1">
								<label for="aa" class="col-xs-12 col-sm-4 control-label">
									電話:
								</label>
								<div class="col-xs-12 col-sm-8 innerDiv">
									<input type="text" name="phone" value="phone" 
									class="form-control"  disabled="disabled">
								</div>
							</div>
							<div class="form-group  arr1">
								<label for="aa" class="col-xs-12 col-sm-4 control-label">
									實名認證:
								</label>
								<div class="col-xs-12 col-sm-8 innerDiv">
									<input type="text" name="status" value="status" 
									class="form-control"  disabled="disabled">
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
									<input type="text" name="motno" value="motno" 
									class="form-control" disabled="disabled">
								</div>
							</div>

							<div class="form-group  arr1">
								<label for="aa" class="col-xs-12 col-sm-4 control-label">
									型號:
								</label>
								<div class="col-xs-12 col-sm-8 innerDiv">
									<input type="text" name="modtype" value="modtype" 
									class="form-control" disabled="disabled">
								</div>
							</div>

							<div class="form-group  arr1">
								<label for="aa" class="col-xs-12 col-sm-4 control-label">
									狀態:
								</label>
								<div class="col-xs-12 col-sm-8 innerDiv">
									<input type="text" name="status" value="status" 
									class="form-control"  disabled="disabled">
								</div>
							</div>

							<div class="form-group  arr1">
								<label for="aa" class="col-xs-12 col-sm-4 control-label" >
									車牌號碼:
								</label>
								<div class="col-xs-12 col-sm-8 innerDiv">
									<input type="text" name="plateno" value="plateno" 
									class="form-control" disabled="disabled">
								</div>
							</div>

							<div class="form-group  arr1">
								<label for="aa" class="col-xs-12 col-sm-4 control-label">
									廠牌:
								</label>
								<div class="col-xs-12 col-sm-8 innerDiv">
									<input type="text" name="brand" value="brand" 
									class="form-control" disabled="disabled">
								</div>
							</div>

							<div class="form-group  arr1">
								<label for="aa" class="col-xs-12 col-sm-4 control-label">
									名稱:
								</label>
								<div class="col-xs-12 col-sm-8 innerDiv">
									<input type="text" name="name" value="name" 
									class="form-control"  disabled="disabled">
								</div>
							</div>

							<div class="form-group  arr1">
								<label for="aa" class="col-xs-12 col-sm-4 control-label">
									排氣量:
								</label>
								<div class="col-xs-12 col-sm-8 innerDiv">
									<input type="text" name="displacement" value="displacement" 
									class="form-control"  disabled="disabled">
								</div>
							</div>

							<div class="form-group  arr1">
								<label for="aa" class="col-xs-12 col-sm-4 control-label">
									起始里程數:
								</label>
								<div class="col-xs-12 col-sm-8 innerDiv">
									<input type="text" name="milstart" value="milstart" 
									class="form-control"  disabled="disabled">
								</div>
							</div>

							<div class="form-group  arr1">
								<label for="aa" class="col-xs-12 col-sm-4 control-label">
									結束里程數:
								</label>
								<div class="col-xs-12 col-sm-8 innerDiv">
									<input type="text" name="milend" value="milend" 
									class="form-control"  disabled="disabled">
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

							<div class="collapse" id="equip_1">
								<div class="form-group  arr1">
									<label for="aa" class="col-xs-12 col-sm-4 control-label" >
										裝備1 編號:
									</label>
									<div class="col-xs-12 col-sm-8 innerDiv">
										<input type="text" name="emtno" value="emtno" 
										class="form-control" disabled="disabled">
									</div>
								</div>

								<div class="form-group  arr1">
									<label for="aa" class="col-xs-12 col-sm-4 control-label">
										種類:
									</label>
									<div class="col-xs-12 col-sm-8 innerDiv">
										<input type="text" name="type" value="type" 
										class="form-control" disabled="disabled">
									</div>
								</div>

								<div class="form-group  arr1">
									<label for="aa" class="col-xs-12 col-sm-4 control-label">
										狀態:
									</label>
									<div class="col-xs-12 col-sm-8 innerDiv">
										<input type="text" name="status" value="status" 
										class="form-control"  disabled="disabled">
									</div>
								</div>
							</div>



							<div class="collapse" id="equip_2">
								<div class="form-group  arr1">
									<label for="aa" class="col-xs-12 col-sm-4 control-label" >
										裝備2 編號:
									</label>
									<div class="col-xs-12 col-sm-8 innerDiv">
										<input type="text" name="emtno" value="emtno" 
										class="form-control" disabled="disabled">
									</div>
								</div>

								<div class="form-group  arr1">
									<label for="aa" class="col-xs-12 col-sm-4 control-label">
										種類:
									</label>
									<div class="col-xs-12 col-sm-8 innerDiv">
										<input type="text" name="type" value="type" 
										class="form-control" disabled="disabled">
									</div>
								</div>

								<div class="form-group  arr1">
									<label for="aa" class="col-xs-12 col-sm-4 control-label">
										狀態:
									</label>
									<div class="col-xs-12 col-sm-8 innerDiv">
										<input type="text" name="status" value="status" 
										class="form-control"  disabled="disabled">
									</div>
								</div>
							</div>

							<div class="collapse" id="equip_3">
								<div class="form-group  arr1">
									<label for="aa" class="col-xs-12 col-sm-4 control-label" >
										裝備3 編號:
									</label>
									<div class="col-xs-12 col-sm-8 innerDiv">
										<input type="text" name="emtno" value="emtno" 
										class="form-control" disabled="disabled">
									</div>
								</div>

								<div class="form-group  arr1">
									<label for="aa" class="col-xs-12 col-sm-4 control-label">
										種類:
									</label>
									<div class="col-xs-12 col-sm-8 innerDiv">
										<input type="text" name="type" value="type" 
										class="form-control" disabled="disabled">
									</div>
								</div>

								<div class="form-group  arr1">
									<label for="aa" class="col-xs-12 col-sm-4 control-label">
										狀態:
									</label>
									<div class="col-xs-12 col-sm-8 innerDiv">
										<input type="text" name="status" value="status" 
										class="form-control"  disabled="disabled">
									</div>
								</div>
							</div>			


							<div class="collapse" id="equip_4">
								<div class="form-group  arr1">
									<label for="aa" class="col-xs-12 col-sm-4 control-label" >
										裝備4 編號:
									</label>
									<div class="col-xs-12 col-sm-8 innerDiv">
										<input type="text" name="emtno" value="emtno" 
										class="form-control" disabled="disabled">
									</div>
								</div>

								<div class="form-group  arr1">
									<label for="aa" class="col-xs-12 col-sm-4 control-label">
										種類:
									</label>
									<div class="col-xs-12 col-sm-8 innerDiv">
										<input type="text" name="type" value="type" 
										class="form-control" disabled="disabled">
									</div>
								</div>

								<div class="form-group  arr1">
									<label for="aa" class="col-xs-12 col-sm-4 control-label">
										狀態:
									</label>
									<div class="col-xs-12 col-sm-8 innerDiv">
										<input type="text" name="status" value="status" 
										class="form-control"  disabled="disabled">
									</div>
								</div>
							</div>


							<div class="collapse" id="equip_5">
								<div class="form-group  arr1">
									<label for="aa" class="col-xs-12 col-sm-4 control-label" >
										裝備5 編號:
									</label>
									<div class="col-xs-12 col-sm-8 innerDiv">
										<input type="text" name="emtno" value="emtno" 
										class="form-control" disabled="disabled">
									</div>
								</div>

								<div class="form-group  arr1">
									<label for="aa" class="col-xs-12 col-sm-4 control-label">
										種類:
									</label>
									<div class="col-xs-12 col-sm-8 innerDiv">
										<input type="text" name="type" value="type" 
										class="form-control" disabled="disabled">
									</div>
								</div>

								<div class="form-group  arr1">
									<label for="aa" class="col-xs-12 col-sm-4 control-label">
										狀態:
									</label>
									<div class="col-xs-12 col-sm-8 innerDiv">
										<input type="text" name="status" value="status" 
										class="form-control"  disabled="disabled">
									</div>
								</div>
							</div>

							<div class="collapse" id="equip_6">
								<div class="form-group  arr1">
									<label for="aa" class="col-xs-12 col-sm-4 control-label" >
										裝備6 編號:
									</label>
									<div class="col-xs-12 col-sm-8 innerDiv">
										<input type="text" name="emtno" value="emtno" 
										class="form-control" disabled="disabled">
									</div>
								</div>

								<div class="form-group  arr1">
									<label for="aa" class="col-xs-12 col-sm-4 control-label">
										種類:
									</label>
									<div class="col-xs-12 col-sm-8 innerDiv">
										<input type="text" name="type" value="type" 
										class="form-control" disabled="disabled">
									</div>
								</div>

								<div class="form-group  arr1">
									<label for="aa" class="col-xs-12 col-sm-4 control-label">
										狀態:
									</label>
									<div class="col-xs-12 col-sm-8 innerDiv">
										<input type="text" name="status" value="status" 
										class="form-control"  disabled="disabled">
									</div>
								</div>
							</div>

							<div class="collapse" id="equip_7">
								<div class="form-group  arr1">
									<label for="aa" class="col-xs-12 col-sm-4 control-label" >
										裝備7 編號:
									</label>
									<div class="col-xs-12 col-sm-8 innerDiv">
										<input type="text" name="emtno" value="emtno" 
										class="form-control" disabled="disabled">
									</div>
								</div>

								<div class="form-group  arr1">
									<label for="aa" class="col-xs-12 col-sm-4 control-label">
										種類:
									</label>
									<div class="col-xs-12 col-sm-8 innerDiv">
										<input type="text" name="type" value="type" 
										class="form-control" disabled="disabled">
									</div>
								</div>

								<div class="form-group  arr1">
									<label for="aa" class="col-xs-12 col-sm-4 control-label">
										狀態:
									</label>
									<div class="col-xs-12 col-sm-8 innerDiv">
										<input type="text" name="status" value="status" 
										class="form-control"  disabled="disabled">
									</div>
								</div>
							</div>			


							<div class="collapse" id="equip_8">
								<div class="form-group  arr1">
									<label for="aa" class="col-xs-12 col-sm-4 control-label" >
										裝備8 編號:
									</label>
									<div class="col-xs-12 col-sm-8 innerDiv">
										<input type="text" name="emtno" value="emtno" 
										class="form-control" disabled="disabled">
									</div>
								</div>

								<div class="form-group  arr1">
									<label for="aa" class="col-xs-12 col-sm-4 control-label">
										種類:
									</label>
									<div class="col-xs-12 col-sm-8 innerDiv">
										<input type="text" name="type" value="type" 
										class="form-control" disabled="disabled">
									</div>
								</div>

								<div class="form-group  arr1">
									<label for="aa" class="col-xs-12 col-sm-4 control-label">
										狀態:
									</label>
									<div class="col-xs-12 col-sm-8 innerDiv">
										<input type="text" name="status" value="status" 
										class="form-control"  disabled="disabled">
									</div>
								</div>
							</div>
					</div>

					<div class="clear"></div>
					
					<h3>確認項目</h3>
					<hr>
					

					<div><h5>裝備領取確認:</h5></div>
						<div>

							<label class="checkbox-inline">
								<input type="checkbox" name="" id="">
								裝備1
							</label>

							<label class="checkbox-inline">
								<input type="checkbox" name="" id="">
								裝備2
							</label>

							<label class="checkbox-inline">
								<input type="checkbox" name="" id="">
								裝備3
							</label>

							<label class="checkbox-inline">
								<input type="checkbox" name="" id="">
								裝備4
							</label>

							<label class="checkbox-inline">
								<input type="checkbox" name="" id="">
								裝備5
							</label>

							<label class="checkbox-inline">
								<input type="checkbox" name="" id="">
								裝備6
							</label>

							<label class="checkbox-inline">
								<input type="checkbox" name="" id="">
								裝備7
							</label>

							<label class="checkbox-inline">
								<input type="checkbox" name="" id="">
								裝備8
							</label>																							
						</div>

					<div><h5>交車確認:</h5></div>
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




						<hr>
						<p class="text-center">
							<a href="#" class="btn btn-info"><i class="glyphicon glyphicon-ok"></i> 送出</a>
							<a href="#" class="btn btn-info"><i class="glyphicon glyphicon-remove"></i> 取消</a>
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
