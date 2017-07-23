<%@page import="java.sql.Timestamp"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.rent_ord.model.*"%>

<!DOCTYPE html>
<html>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/twitter_bootstrap_3_3_7_min.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/daterangepicker.css" />
<head>
<style>
.control-label, .form-control{

font-size:18px;

}

.navTextTag{
	font-size:16px!important;
}

</style>
<body>
<%
	RentOrdVO roQueryVO = (RentOrdVO) request.getAttribute("roQueryVO");
%>

	<div class="container">
		<div id="signupbox" style="margin-top: 50px"
			class="mainbox mainbox col-md-10 col-md-offset-1 col-sm-10 col-sm-offset-1">
			<div class="panel panel-info">
				<div class="panel-heading">
					<div class="panel-title control-label">租賃單明細</div>
				</div>
				<div class="panel-body">
				
					<form method="post" action="<%=request.getContextPath()%>/backend/rent_ord/rentOrd.do">
						<div id="div_rentno" class="form-group required">
							<label for="id_rentno"
								class="control-label col-md-3  requiredField">
								租單編號<span class="asteriskField"></span>
							</label>
							<div class="controls col-md-9 ">
								<input class="input-md  textinput textInput form-control"
									id="id_rentno" maxlength="30" name="rentno"  placeholder="no data"
									style="margin-bottom: 10px" type="text" value="${roQueryVO.rentno}" disabled/>
							</div>
						</div>
						
						<div id="div_memno" class="form-group required">
							<label for="id_memno"
								class="control-label col-md-3  requiredField">
								會員編號<span class="asteriskField"></span>
							</label>
							<div class="controls col-md-9 ">
								<input class="input-md  textinput textInput form-control"
									id="id_memno" maxlength="30" name="memno"  placeholder="no data"
									style="margin-bottom: 10px" type="text" value="${roQueryVO.memno}" disabled/>
							</div>
						</div>
<jsp:useBean id="mmSvc" scope="page" class="com.motor_model.model.MotorModelService"/>						
						<div id="div_motno" class="form-group required">
							<label for="id_motno"
								class="control-label col-md-3  requiredField">
								車輛<span class="asteriskField"></span>
							</label>
							<div class="controls col-md-9 ">
								<input class="input-md  textinput textInput form-control"
									id="id_motno" maxlength="30" name="motno" 
									value="${roQueryVO.motorVO.motno}&nbsp;&nbsp;&nbsp;${mmSvc.findByPK(roQueryVO.motorVO.modtype).brand}&nbsp;&nbsp;&nbsp;${mmSvc.findByPK(roQueryVO.motorVO.modtype).name}"
									placeholder="請填入車輛編號"
									style="margin-bottom: 10px" type="text" placeholder="no data" disabled/>
							</div>
						</div>
<jsp:useBean id="locSvc" scope="page" class="com.location.model.LocationService"/>

						<div id="div_slocno" class="form-group required">
							<label for="id_slocno" class="control-label col-md-3 requiredField">
								取車地點<span class="asteriskField"></span>
							</label>
							<div class="controls col-md-9 ">
								<select name="slocno"  class="input-md  textinput textInput form-control"
										id="id_slocno" style="margin-bottom: 10px" disabled>
		 							<c:forEach var="locVO" items="${locSvc.all}">
			 							<c:if test="${locVO.locno != 'TPE00'}">
											<option value="${locVO.locno}" ${(locVO.locno==roQueryVO.slocno)? 'selected':''}>${locVO.locname}營業所</option>
										</c:if>	
									</c:forEach> 															
								</select>
							</div>
						</div>
						
						<div id="div_rlocno" class="form-group required">
							<label for="id_rlocno"
								class="control-label col-md-3  requiredField">
								還車地點<span class="asteriskField"></span>
							</label>
							<div class="controls col-md-9 ">
								<select name="rlocno"  class="input-md  textinput textInput form-control" id="id_rlocno" style="margin-bottom: 10px" disabled>
		 							<c:forEach var="locVO" items="${locSvc.all}">
			 							<c:if test="${locVO.locno != 'TPE00'}">
											<option value="${locVO.locno}" ${(locVO.locno==roQueryVO.rlocno)? 'selected':''}>${locVO.locname}營業所</option>
										</c:if>	
									</c:forEach> 															
								</select>
							</div>
						</div>

						<div id="div_milstart" class="form-group required">
							<label for="id_milstart"
								class="control-label col-md-3  requiredField">
								起始里程(KM)<span class="asteriskField"></span>
							</label>
							<div class="controls col-md-9 ">
								<input class="input-md  textinput textInput form-control"
									id="id_milstart" maxlength="30" name="milstart"
									placeholder="no data"  value="${roQueryVO.milstart}"
									style="margin-bottom: 10px" type="text" placeholder="no data"  disabled/>
							</div>
						</div>
						
						<div id="div_milend" class="form-group required">
							<label for="id_milend"
								class="control-label col-md-3  requiredField">
								結束里程(KM)<span class="asteriskField"></span>
							</label>
							<div class="controls col-md-9 ">
								<input class="input-md  textinput textInput form-control"
									id="id_milend" maxlength="30" name="milend"
									placeholder="no data"  value="${roQueryVO.milend}"
									style="margin-bottom: 10px" type="text" placeholder="no data"  disabled/>
							</div>
						</div>
						
						<div id="div_filldate" class="form-group required">
							<label for="id_filldate"
								class="control-label col-md-3  requiredField">
								填表日期<span class="asteriskField"></span>
							</label>
							<div class="controls col-md-9 ">
								<input class="input-md  textinput textInput form-control timer"
									id="id_filldate" maxlength="30" name="filldate"  
									value="<fmt:formatDate pattern = "MM/dd/yyyy H:mm" value = "${roQueryVO.filldate}"/>"
									style="margin-bottom: 10px" type="text" placeholder="no data" disabled/>
							</div>
						</div>
						
						<div id="div_startdate" class="form-group required">
							<label for="id_startdate"
								class="control-label col-md-3  requiredField">
								起始日期<span class="asteriskField"></span>
							</label>
							<div class="controls col-md-9 ">
								<input class="input-md  textinput textInput form-control timer"
									id="id_startdate" maxlength="30" name="startdate"
									value="<fmt:formatDate pattern = "MM/dd/yyyy H:mm" value = "${roQueryVO.startdate}"/>"
									style="margin-bottom: 10px" type="text" placeholder="no data" disabled/>
							</div>
						</div>
						
						<div id="div_enddate" class="form-group required">
							<label for="id_enddate"
								class="control-label col-md-3  requiredField">
								結束日期<span class="asteriskField"></span>
							</label>
							<div class="controls col-md-9 ">
								<input class="input-md  textinput textInput form-control timer"
									id="id_enddate" maxlength="30" name="enddate"
									value="<fmt:formatDate pattern = "MM/dd/yyyy H:mm" value = "${roQueryVO.enddate}"/>"
									style="margin-bottom: 10px" type="text" placeholder="no data" disabled/>
							</div>
						</div>
						
						
						<div id="div_returndate" class="form-group required">
							<label for="id_returndate"
								class="control-label col-md-3  requiredField">
								還車日期<span class="asteriskField"></span>
							</label>
							<div class="controls col-md-9 ">
								<c:if test="${roQueryVO.returndate!=null}">
									<input class="input-md  textinput textInput form-control timer"
										id="id_returndate" maxlength="30" name="returndate"
										value="<fmt:formatDate pattern = "MM/dd/yyyy H:mm" value = "${roQueryVO.returndate}"/>"
										style="margin-bottom: 10px" type="text" placeholder="no data" disabled/>
								</c:if>
								<c:if test="${roQueryVO.returndate==null}">
									<input class="input-md  textinput textInput form-control timer"
										id="id_returndate" maxlength="30" name="returndate" value="${roQueryVO.returndate}"
										style="margin-bottom: 10px" type="text" placeholder="no data" disabled/>
								</c:if>	
							</div>
						</div>

						
						<div id="div_fine" class="form-group required">
							<label for="id_fine"
								class="control-label col-md-3  requiredField">
								總罰金<span class="asteriskField"></span>
							</label>
							<div class="controls col-md-9 ">
								<input class="input-md  textinput textInput form-control"
									id="id_fine" maxlength="30" name="fine" value="${roQueryVO.fine}"
									style="margin-bottom: 10px" type="text" placeholder="no data"  disabled/>
							</div>
						</div>
						
						<div id="div_total" class="form-group required">
							<label for="id_total"
								class="control-label col-md-3  requiredField">
								總租金<span class="asteriskField"></span>
							</label>
							<div class="controls col-md-9 ">
								<input class="input-md  textinput textInput form-control"
									id="id_total" maxlength="30" name="total" value="${roQueryVO.total}"
									style="margin-bottom: 10px" type="text" placeholder="no data"  disabled/>
							</div>
						</div>
						
						
						<div id="div_rank" class="form-group required">
							<label for="id_rank"
								class="control-label col-md-3  requiredField">
								評分<span class="asteriskField"></span>
							</label>
							<div class="controls col-md-9 ">
								<select name="rank"  class="input-md  textinput textInput form-control" 
								id="id_rank" style="margin-bottom: 10px"  disabled>
									<option value="" ${(roQueryVO.rank=="")? 'selected':''}>no data</option>
									<option value="1" ${(roQueryVO.rank=="1")? 'selected':''}>1(worst)</option>
									<option value="2" ${(roQueryVO.rank=="2")? 'selected':''}>2</option>
									<option value="3" ${(roQueryVO.rank=="3")? 'selected':''}>3</option>
									<option value="4" ${(roQueryVO.rank=="4")? 'selected':''}>4</option>
									<option value="5" ${(roQueryVO.rank=="5")? 'selected':''}>5(best)</option>
								</select>
							</div>
						</div>
						
						<div id="div_status" class="form-group required">
							<label for="id_status"
								class="control-label col-md-3  requiredField">
								狀態<span class="asteriskField"></span>
							</label>
							<div class="controls col-md-9 ">
								<select name="status"  class="input-md  textinput textInput form-control" id="id_status" style="margin-bottom: 10px" disabled>
									<c:if test="${(roQueryVO.status=='unpaid')}">
										<option value="unpaid" ${(roQueryVO.status=="unpaid")? 'selected':''}>unpaid</option>
									</c:if>
									<c:if test="${(roQueryVO.status=='unpaid'||roQueryVO.status=='unoccupied')}">
										<option value="unoccupied" ${(roQueryVO.status=="unoccupied")? 'selected':''}>unoccupied</option>
									</c:if>
									<c:if test="${(roQueryVO.status=='available')}">
										<option value="available" ${(roQueryVO.status=="available")? 'selected':''}>available</option>
									</c:if>									
									<c:if test="${(roQueryVO.status=='canceled'||roQueryVO.status=='unpaid'
									||roQueryVO.status=='unoccupied'||roQueryVO.status=='available')}">
										<option value="canceled" ${(roQueryVO.status=="canceled")? 'selected':''}>canceled</option>
									</c:if>
									<c:if test="${(roQueryVO.status=='noshow')}">
										<option value="noshow" ${(roQueryVO.status=="noshow")? 'selected':''}>noshow</option>
									</c:if>
									
									<c:if test="${(roQueryVO.status=='noreturn')}">
										<option value="noreturn" ${(roQueryVO.status=="noreturn")? 'selected':''}>noreturn</option>
									</c:if>
								
									<c:if test="${(roQueryVO.status=='overtime')}">
										<option value="overtime" ${(roQueryVO.status=="overtime")? 'selected':''}>overtime</option>
									</c:if>
									
									<c:if test="${(roQueryVO.status=='abnromalclosed')}">
										<option value="abnromalclosed" ${(roQueryVO.status=="abnromalclosed")? 'selected':''}>abnromalclosed</option>
									</c:if>
									
									<c:if test="${(roQueryVO.status=='closed')}">
										<option value="closed" ${(roQueryVO.status=="closed")? 'selected':''}>closed</option>
									</c:if>
									
									<c:if test="${(roQueryVO.status=='other')}">
										<option value="other" ${(roQueryVO.status=="other")? 'selected':''}>other</option>
									</c:if>
								</select>
							</div>
						</div>
						
						<div id="div_note" class="form-group required">
							<label for="id_note"
								class="control-label col-md-3  requiredField">
								備註<span class="asteriskField"></span>
							</label>
							<div class="controls col-md-9 ">
								<input class="input-md  textinput textInput form-control"
									id="id_note" maxlength="30" name="note" value="${roQueryVO.note}"
									style="margin-bottom: 10px" type="text" placeholder="no data" disabled/>
							</div>
						</div>
						<jsp:useBean id="ecSvc" scope="page" class="com.emt_cate.model.EmtCateService"/>										
						<c:forEach var="emtVO" items="${get_equipmentVOs_by_rentno}">
							<div class="form-group required">
								<label for="emtno" class="control-label col-md-3 requiredField" >
									裝備
								</label>
								<div class="controls col-md-9">
									<input type="text" name="emtno" value="${emtVO.emtno}&nbsp;&nbsp;&nbsp;${ecSvc.getOneEmtCate(emtVO.ecno).type}" 
									class="form-control" style="margin-bottom: 10px" disabled>
								</div>
							</div>
						</c:forEach>
						<div class="form-group">
							<div class="controls col-md-12 ">
								<div class="modal-footer">
									<p class="text-center">
										<a href="javascript:window.close();" class="btn btn-info btn-lg">
										<i class="glyphicon glyphicon-remove"></i>Close</a>
									</p>
								</div>
							</div>
						</div>
					</form>
					
				</div>
			</div>
		</div>
	</div>
</body>
</html>