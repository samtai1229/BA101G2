<%@page import="java.sql.Timestamp"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">	
	<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
	<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
	<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css"/>	
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">     
	<link rel="stylesheet" href="<%=request.getContextPath()%>/backend/Modified/backendHP_css.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/backend/Modified/main.css" >
	<link rel="stylesheet" type="text/css" href="//cdn.datatables.net/1.10.15/css/jquery.dataTables.min.css">
	<script type="text/javascript" src="//cdn.datatables.net/1.10.15/js/jquery.dataTables.min.js"></script>
   <title>�|���޲z-AutoBike</title>
</head>

<style>

#myDiv {
  display: none;
  text-align: center;
}

th,td{
	height:20px;

}

/*�۩w*/
	 th, tr{
		/*����������*/
		white-space:nowrap;
	} 
</style>

<body>
    <nav class="navbar navbar-default" role="navigation">
        <!-- logo�� -->
        <a class="navbar-brand" href="#" id="navA">AUTOBIKE</a>
        <!-- ����� -->
        <ul class="nav navbar-nav">
            <li><a href="#" id="navA">��ݺ޲z�t��</a></li>
            <!-- ���� -->
            <iframe scrolling="no" frameborder="no" clocktype="html5" style="overflow:hidden;border:0;margin:0;padding:0;width:120px;height:40px;" src="http://www.clocklink.com/html5embed.php?clock=004&timezone=CCT&color=yellow&size=120&Title=&Message=&Target=&From=2017,1,1,0,0,0&Color=yellow">
            </iframe>
        </ul>
        <!-- �k��� -->
        <ul class="nav navbar-nav navbar-right">
        </ul>
    </nav>
    <div class="col-xs-12 col-sm-2 leftBar">
        <img id="menuLogo" src="<%=request.getContextPath()%>/backend/images/android_logo2.jpg">
        <button class="accordion accordionMenu accordion accordionMenuMenu">�`���޲z�t��</button>
        <div class="btn-group-vertical">
                
            <a class="btn btn-default" href="<%=request.getContextPath()%>/backend/motor/backendMotor.jsp"  role="button">������ƺ޲z</a>
            <a class="btn btn-default" href="<%=request.getContextPath()%>/backend/motor_model/backendMotorModel.jsp"  role="button">���������޲z</a>           
            <a class="btn btn-default" href="#" role="button">�����ի�</a>            
			<a class="btn btn-default" href="<%=request.getContextPath()%>/backend/rent_ord/backendRentOrd.jsp" role="button">�����޲z</a>
            <a class="btn btn-default" href="#" role="button">�˳ƺ޲z</a>
            <a class="btn btn-default" href="#" role="button">�˳ƽի�</a>
            <a class="btn btn-default" href="#" role="button">���I�޲z</a>
        </div>
        <button class="accordion accordionMenu">���I�޲z�t��</button>
        <div class="btn-group-vertical">
            <a class="btn btn-default" href="#" role="button">���I�����޲z</a>
            <a class="btn btn-default" href="<%=request.getContextPath()%>/backend/rent_ord/lease.jsp"  role="button">�樮�޲z</a>
          	<a class="btn btn-default" href="<%=request.getContextPath()%>/backend/rent_ord/return.jsp"  role="button">�٨��޲z</a>
            <a class="btn btn-default" href="#" role="button">�����իץӽ�</a>
            <a class="btn btn-default" href="#" role="button">�����O�i/���׺޲z</a>
            <a class="btn btn-default" href="#" role="button">���I�˳ƺ޲z</a>
            <a class="btn btn-default" href="#" role="button">�˳ƥӽ�</a>
        </div>
        <button class="accordion accordionMenu">�G�⨮�޲z�t��</button>
        <div class="btn-group-vertical">
            <a class="btn btn-default" href="#" role="button">�G�⨮���޲z</a>
            <a class="btn btn-default" href="#" role="button">�G�⨮�q��޲z</a>
            <a class="btn btn-default" href="#" role="button">�G�⨮����޲z</a>
        </div>
        <button class="accordion accordionMenu">�|���޲z�t��</button>
        <div class="btn-group-vertical">
            <a class="btn btn-default" href="<%=request.getContextPath()%>/backend/member/backendMember.jsp" role="button">�|���޲z</a>
            <a class="btn btn-default" href="<%=request.getContextPath()%>/backend/member/addMember.jsp" role="button">�s�W�|��</a>
        </div>
        <button class="accordion accordionMenu">���ʥ����޲z�t��</button>
        <div class="btn-group-vertical">
            <a class="btn btn-default" href="#" role="button">�����޲z</a>
            <a class="btn btn-default" href="#" role="button">�d�����޲z</a>
            <a class="btn btn-default" href="#" role="button">�̷s�����޲z</a>
        </div>
        <button class="accordion accordionMenu">��ݺ޲z�t��</button>
        <div class="btn-group-vertical">
            <a class="btn btn-default" href="#" role="button">����v���޲z</a>
            <a class="btn btn-default" href="#" role="button">���˴��I�޲z</a>
            <a class="btn btn-default" href="#" role="button">��ݵn�J�޲z</a>
        </div>
        <div class="btn-group-vertical"></div>
    </div>
    <div class="col-xs-12 col-sm-10 rightHTML">

		<div class="topTitle">
            <h1>�|����ƺ޲z�t��</h1>
        </div>
         		<div class="container-fluid">       
<jsp:useBean id="roSvc" scope="page" class="com.rent_ord.model.RentOrdService"/>
<jsp:useBean id="locSvc" scope="page" class="com.location.model.LocationService"/>
<jsp:useBean id="motorSvc" scope="page" class="com.motor.model.MotorService"/>
<jsp:useBean id="memSvc" scope="page" class="com.member.model.MemberService"/>
						<div style="padding:5px; padding-left:0px">
							<b>Show / Hide Columns: </b>
							<a class="showHideColumn" data-columnindex="0">�|���s��</a> -
							<a class="showHideColumn" data-columnindex="1">�m�W</a> -
							<a class="showHideColumn" data-columnindex="2">�|�����A</a> -
							<a class="showHideColumn" data-columnindex="3">�ʧO</a> -
							<a class="showHideColumn" data-columnindex="4">�ͤ�</a> -
							<a class="showHideColumn" data-columnindex="5">�H�c</a> -
							<a class="showHideColumn" data-columnindex="6">������X</a> -
							<a class="showHideColumn" data-columnindex="7">�a�}</a> -
							<a class="showHideColumn" data-columnindex="8">�b��</a> -
							<a class="showHideColumn" data-columnindex="10">���ɤ��</a> -
							
						</div>	
						<table id="dataTable" class="stripe hover" width="100%" cellspacing="0">
							  <thead>	
									<tr class="QueryTable_TR">
										<th>�d��</th>	
										<th>�|���s��</th>
										<th>�m�W</th>	
										<th>�|�����A</th>	
										<th>�ʧO</th>
										<th>�ͤ�</th>				
										<th>�H�c</th>
										<th>������X</th>
										<th>�a�}</th>				
										<th>�b��</th>
										<th>���ɤ��</th>
									</tr>
							  </thead>
							  <tbody>				  		
						 		<c:forEach var="memVO" items="${memSvc.all}">
									<tr class="QueryTable_TR">
										<td>
										  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/member/member.do">
										     <input type="submit" value="�d��" class="btn btn-default"> 
										     <input type="hidden" name="memno" value="${memVO.memno}">
										     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--�e�X�����������|��Controller--><!-- �ثe�|���Ψ�  -->
										     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
										</td>
										<td>${memVO.memno}</td>
										<td>${memVO.memname}</td>
										<td>${memVO.status}</td>
										<td>${memVO.sex}</td>	
										<td><fmt:formatDate pattern = "yyyy/MM/dd" value = "${memVO.birth}" /></td>
										<td>${memVO.mail}</td>
										<td>${memVO.phone}</td>
										<td>${memVO.addr}</td>
										<td>${memVO.acc}</td>
										<td><fmt:formatDate pattern = "yyyy/MM/dd" value = "${memVO.credate}" /></td>
									</tr>
								</c:forEach>							
						  </tbody>	 	  								
						</table>
					</div>
<!--end: block3 --> 	
<!--container--></div>	
<script>
//table
$(document).ready(function(){
	var datatableInstance = $('#dataTable').DataTable({
	});
	$('.showHideColumn').on('click',function(){
		var tableColumn = datatableInstance.column($(this).attr('data-columnindex'));
		tableColumn.visible(!tableColumn.visible());
	})
});

</script>  
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="<%=request.getContextPath()%>/backend/rent_ord/Modified/rentOrdNew.js"></script>
    <script src="<%=request.getContextPath()%>/backend/rent_ord/Modified/motorKanli_for_ro.js"></script>
    <script src="<%=request.getContextPath()%>/backend/rent_ord/Modified/datepicker_for_ro.js"></script>
    
</body>
</html>

