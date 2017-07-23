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
	<link rel="stylesheet" href="<%=request.getContextPath()%>/backend/Modified/bootstrap.min.css">    
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/daterangepicker.css" />

<head>
<style>
.control-label, .form-control{

font-size:18px;

}

</style>
<body>
<%
	RentOrdVO roQueryVO = (RentOrdVO) request.getAttribute("roQueryVO");

	Map<String, String> roStatusMap = new HashMap<String, String>();
	roStatusMap.put("unpaid", "待繳費");
	roStatusMap.put("canceled", "取消");
	roStatusMap.put("unoccupied", "準備交車");
	roStatusMap.put("available", "今日取車");	
	roStatusMap.put("noshow", "逾期未取");	
	roStatusMap.put("noreturn", "待還車");	
	roStatusMap.put("overtime", "逾期未還");	
	roStatusMap.put("abnormalclosed", "異常結案");	
	roStatusMap.put("closed", "正常結案");	
	roStatusMap.put("other", "其他");
%>

	<div class="container">
		<div id="signupbox" style="margin-top: 50px"
			class="mainbox mainbox col-md-10 col-md-offset-1 col-sm-10 col-sm-offset-1">
			<div class="panel panel-info">
				<div class="panel-heading">
					<div class="panel-title control-label">租賃單取消申請</div>
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
									style="margin-bottom: 10px" type="text" value="${roQueryVO.rentno}" readonly/>
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
									style="margin-bottom: 10px" type="text" value="${roQueryVO.memno}" readonly/>
							</div>
						</div>
						
						<div id="div_motno" class="form-group required">
							<label for="id_motno"
								class="control-label col-md-3  requiredField">
								車輛編號<span class="asteriskField"></span>
							</label>
							<div class="controls col-md-9 ">
								<input class="input-md  textinput textInput form-control"
									id="id_motno" maxlength="30" name="motno" value="${roQueryVO.motorVO.motno}"
									placeholder="請填入車輛編號"
									style="margin-bottom: 10px" type="text" placeholder="no data" readonly/>
							</div>
						</div>
<jsp:useBean id="locSvc" scope="page" class="com.location.model.LocationService"/>

						<div id="div_slocno" class="form-group required">
							<label for="id_slocno" class="control-label col-md-3 requiredField">
								取車地點<span class="asteriskField"></span>
							</label>
							<div class="controls col-md-9 ">
								<select name="slocno"  class="input-md  textinput textInput form-control"
										id="id_slocno" style="margin-bottom: 10px" readonly>
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
								<select name="rlocno"  class="input-md  textinput textInput form-control" id="id_rlocno" style="margin-bottom: 10px" readonly>
		 							<c:forEach var="locVO" items="${locSvc.all}">
			 							<c:if test="${locVO.locno != 'TPE00'}">
											<option value="${locVO.locno}" ${(locVO.locno==roQueryVO.rlocno)? 'selected':''}>${locVO.locname}營業所</option>
										</c:if>	
									</c:forEach> 															
								</select>
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
									style="margin-bottom: 10px" type="text" placeholder="no data" readonly/>
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
									style="margin-bottom: 10px" type="text" placeholder="no data" readonly/>
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
									style="margin-bottom: 10px" type="text" placeholder="no data" readonly/>
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
									style="margin-bottom: 10px" type="text" placeholder="no data" readonly/>
							</div>
						</div>

						<div id="div_status" class="form-group required">
							<label for="id_status"
								class="control-label col-md-3  requiredField">
								我要申請:<span class="asteriskField"></span>
							</label>
							<div class="controls col-md-9 ">
								<select name="status"  class="input-md  textinput textInput form-control" id="id_status" style="margin-bottom: 10px">								
									<c:if test="${(roQueryVO.status=='canceled'||roQueryVO.status=='unpaid'
									||roQueryVO.status=='unoccupied'||roQueryVO.status=='available')}">
										<option value="canceled" ${(roQueryVO.status=="canceled")? 'selected':''}><%=roStatusMap.get("canceled")%></option>
									</c:if>
								</select>
							</div>
						</div>
						
						<div id="div_note" class="form-group required">
							<label for="id_note" class="control-label col-md-3  requiredField">
								備註<span class="asteriskField"></span>
							</label>
							<div class="controls col-md-9 ">
								<textarea rows="4" name="note" class="input-md  textinput textInput form-control" 
								style="width:100%" maxlength="60" id="note">${roQueryVO.note}</textarea>
							</div>
						</div>									
						<div class="form-group">
							<div class="controls col-md-12 ">
								<div class="modal-footer">
									<p class="text-center">
									
										<input type="hidden" name="milstart" value="${roQueryVO.milstart}">
										<input type="hidden" name="milend" value="${roQueryVO.milend}">
										<input type="hidden" name="fine" value="${roQueryVO.fine}">
										<input type="hidden" name="rank" value="${roQueryVO.rank}">
										
										<c:if test="${roQueryVO.returndate!=null}">
											<input type="hidden" name="returndate" value="<fmt:formatDate pattern = "MM/dd/yyyy H:mm" value ="${roQueryVO.returndate}"/>"/>
										</c:if>
										<c:if test="${roQueryVO.returndate==null}">
											<input type="hidden" name="returndate" value="${roQueryVO.returndate}" />
										</c:if>
										<input type="hidden" name="action" value="update_and_close">
										<button type="submit" onClick="return check()" class="btn btn-danger btn-lg">
											<i class="glyphicon glyphicon-alert"></i> 送出申請
										</button>
										<a href="javascript:window.close();" class="btn btn-info btn-lg">
											<i class="glyphicon glyphicon-thumbs-up"></i> 取消申請
										</a>
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
	
	<script src="<%=request.getContextPath()%>/backend/Modified/jquery_1_10_1_min.js"></script>
	<script src="<%=request.getContextPath()%>/backend/Modified/twitter_bootstrap_3_3_7_min.js"></script>
	<script src="<%=request.getContextPath()%>/js/moment.js"></script>
    <script src="<%=request.getContextPath()%>/js/daterangepicker.js"></script>
    
<script type="text/javascript">

//check 
function check(){

	var note = document.getElementById('note').value;
	var message = "";
	var   r   =   /^[0-9]*[1-9][0-9]*$/
	
	//alert(".js check on");
	
	//note
	if(note.length==0){
		message +="請填寫取消原因:在填寫完畢、送出申請後，本公司會指派專人幫您處理租單取消流程。";
	}
	
	//result
	if(message.length!=0){
		alert(message);
		return false;
	}
	
}

<%--timepicker--%>	
$(document).ready(function() {

    $('.timer').daterangepicker({
        "autoUpdateInput": false,
        "singleDatePicker": true,
        "timePicker": true,
        "timePicker24Hour": true,
        "timePickerIncrement": 30,
        locale: {
            cancelLabel: 'Clear'
        }
    });

    $('.timer').on('apply.daterangepicker', function(ev, picker) {
        $(this).val(picker.startDate.format('MM/DD/YYYY  H:mm'));
    });

    $('.timer').on('cancel.daterangepicker', function(ev, picker) {
        $(this).val('');
    });
    
});	
	
	
$(document).ready(function() {

<%--form--%>	
    var enrollType;
  //  $("#div_id_As").hide();
    $("input[name='As']").change(function() {
        memberType = $("input[name='select']:checked").val();
        providerType = $("input[name='As']:checked").val();
		toggleIndividInfo();
    });
    
    $("input[name='select']").change(function() {
		memberType = $("input[name='select']:checked").val();
		toggleIndividInfo();
		toggleLearnerTrainer();
	});
	
	function toggleLearnerTrainer() {

	if (memberType == 'P' || enrollType=='company') {
		$("#cityField").hide();
		$("#providerType").show();
		$(".provider").show();
		$(".locationField").show();
		if(enrollType=='INSTITUTE'){
			$(".individ").hide();
		}
	
	} 
    else {
		$("#providerType").hide();
		$(".provider").hide();
		$('#name').show();
		$("#cityField").hide();
		$(".locationField").show();
		$("#instituteName").hide();
		$("#cityField").show();
		
	}
    }
    function toggleIndividInfo(){

	if(((typeof memberType!=='undefined' && memberType == 'TRAINER')||enrollType=='INSTITUTE') && providerType=='INDIVIDUAL'){
		$("#instituteName").hide();
		$(".individ").show();
		$('#name').show();
	}
    else if((typeof memberType!=='undefined' && memberType == 'TRAINER')|| enrollType=='INSTITUTE'){
		$('#name').hide();
		$("#instituteName").show();
		$(".individ").hide();
	}
    }
   
 });
 
</script>
</html>