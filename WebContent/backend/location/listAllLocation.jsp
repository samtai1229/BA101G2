<%@page import="java.sql.Timestamp"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.location.model.*"%>
<%-- �����m�߱ĥ� EL ���g�k���� --%>

<%
/* 	LocationService locationSvc = new LocationService();
	List<LocationVO> list = locationSvc.getAll();
	pageContext.setAttribute("list",list); */
	LocationVO locationVO = (LocationVO) request.getAttribute("locationVO");
%>
<%-- <jsp:useBean id="locationSvc" scope="page" class="com.location.model.LocationService" /> --%>


<html>
<head>
	<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
	<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>	
	<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css"/>	
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/backend/Modified/backendHP_css.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/backend/Modified/main.css" >
	<title>�Ҧ����u��� - listAllMember.jsp</title>
</head>
<body>
	
		<nav class="navbar navbar-default" role="navigation">
        <!-- logo�� -->
        <a class="navbar-brand" href="#" id="navA">AUTOBIKE</a>
        <!-- ����� -->
        <ul class="nav navbar-nav">
            <li><a href="#" id="navA" id="navA">��ݺ޲z�t��</a></li>
            <!-- ���� -->
            <iframe scrolling="no" frameborder="no" clocktype="html5" style="overflow:hidden;border:0;margin:0;padding:0;width:120px;height:40px;" src="http://www.clocklink.com/html5embed.php?clock=004&timezone=CCT&color=yellow&size=120&Title=&Message=&Target=&From=2017,1,1,0,0,0&Color=yellow" >
            </iframe>
        </ul>
        <!-- �k��� -->
        <ul class="nav navbar-nav navbar-right">
        </ul>
    </nav>
<%--�O�d�g�k     href="<%=request.getContextPath()%>/backend/backendRentOrd.jsp"  --%>
   <%--  <div class="col-xs-12 col-sm-2 leftBar">
        <img id="menuLogo" src="<%=request.getContextPath()%>/backend/images/android_logo2.jpg">
        <button class="accordion accordionMenu accordion accordionMenuMenu">�`���޲z�t��</button>
        <div class="btn-group-vertical">
                
            <a class="btn btn-default" href="<%=request.getContextPath()%>/backend/motor/backendMotor.jsp"  role="button">������ƺ޲z</a>
            <a class="btn btn-default" href="<%=request.getContextPath()%>/backend/motor_model/backendMotorModel.jsp"  role="button">���������޲z</a>           
            <a class="btn btn-default" href="#" role="button">�����ի�</a>            
<!--        <a class="btn btn-default" role="button" onclick="loadRentOrd()">�����޲z</a> -->
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
            <a class="btn btn-default" href="#" role="button">�|���޲z</a>
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
    </div> --%>
    <div class="col-xs-12 col-sm-12 rightHTML" id="demo">

		<div class="topTitle">
            <h1>���I�޲z�t��</h1>
        </div>
        
         		<div class="container-fluid">       
<jsp:useBean id="roSvc" scope="page" class="com.rent_ord.model.RentOrdService"/>
<jsp:useBean id="locSvc" scope="page" class="com.location.model.LocationService"/>
<jsp:useBean id="motorSvc" scope="page" class="com.motor.model.MotorService"/>
	
<!--block1 --><div id="block1" class="col-xs-12 col-sm-4">

<%-- ���~��C --%>
<c:if test="${not empty errorMsgs}">
	<font color='red'>�Эץ��H�U���~:
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li>${message}</li>
		</c:forEach>
	</ul>
	</font>
</c:if>

<!-- ���ҭ��O�G�}�l -->					
<!-- ���ҭ��O�G���Ұ϶}�l --><div role="tabpanel">
					      <ul class="nav nav-tabs" role="tablist">
					          <li role="presentation" class="active">
					              <a href="#tab1" aria-controls="tab1" role="tab" data-toggle="tab">�d��</a>
					          </li>
					          <li role="presentation">
					              <a href="#tab2" aria-controls="tab2" role="tab" data-toggle="tab">�s�W</a>
					          </li>
					          <li role="presentation">
					              <a href="#tab3" aria-controls="tab3" role="tab" data-toggle="tab">�ק�</a>
					          </li>
					          <li role="presentation">
					              <a href="#tab4" aria-controls="tab4" role="tab" data-toggle="tab">�R��</a>
					          </li>
					          <li role="presentation">
					              <a href="#tab5" aria-controls="tab3" role="tab" data-toggle="tab">?</a>
					          </li>
					          <li role="presentation">
					              <a href="#tab6" aria-controls="tab4" role="tab" data-toggle="tab">?</a>
					          </li>
<!-- ���ҭ��O�G���Ұϵ��� -->	  </ul>	

<!-- ���ҭ��O�G���e�϶}�l -->     <div class="tab-content">
							  <div role="tabpanel" class="tab-pane active" id="tab1">
					          		<fieldset>
					          		<legend>���I�d��</legend>
		<!--form�\�� ��@�d��  -->
									<form method="get" action="location.do">
										<div class="InputForm">
											<label class="title">�d��</label> 
												<select name="locno" onchange="queryLocationByLocationPK(this.value)">
													<option value="clean">�п��</option>
						 							<c:forEach var="locationVO" items="${locSvc.all}">
														<option value="${locationVO.locno}">
															${locationVO.locno}
														</option>
													</c:forEach> 
												</select><br />
										</div>
									</form>
									
<!--���Idiv:�浧���   showSingleQueryResult  --> 

									<div id="showSingleQueryResult"></div>
																
									</fieldset>
							  </div>

								<div role="tabpanel" class="tab-pane" id="tab2">
		<!--form�\�� �s�W  -->
							     	<fieldset>
								    <legend>���I�s�W</legend>
									<form method="post" action="location.do" enctype="multipart/form-data">
									
										<div class="InputForm">
											<label class="title">���I�s��</label>
											<input type="text" name="locno" maxlength="10" /><br>
										</div>
										<div class="InputForm">
											<label class="title">���I�W��</label>
											<input type="text" name="locname" maxlength="10" /><br>
										</div>
										<div class="InputForm">
											<label class="title">���I�q��</label>
											<input type="text" name="tel" maxlength="10" /><br>
										</div>
										<div class="InputForm">
										    <label class="title">���I�a�}</label>
										    <input type="text" name="addr" maxlength="10" /><br>
										</div>
										<div class="InputForm">
											<label class="title">���I�Ӥ�</label>
											<input type="file" name="pic" /><br>
										</div>
										<div class="InputForm">
											<label class="title">���I�a�Ϻ��</label>
											<input type="text" name="lon" maxlength="10"/><br>
										</div>										
										<div class="InputForm">
											<label class="title">���I�a�Ͻn��</label>
											<input type="text" name="lat" maxlength="10"/><br>
										</div>	
										<div class="InputForm">
											<label class="title">���I���A</label>
											<label>preparing</label><br>
										</div>
										<div class="InputForm">
											<input type="hidden" name="action" value="insert">
											<input type="submit" value="submit" class="click" /> 
											<input type="reset" name="reset" value="reset" class="click" />
										</div>
									</form>
									</fieldset>
									<!--end: form�\�� �s�W -->
							  </div><!--tab1 -->

					          <div role="tabpanel" class="tab-pane" id="tab3">
		<!--form�\�� �ק�  -->
					                <fieldset>
							  	    <legend>���I��ƭק�</legend>
									<form method="get" action="location.do" enctype="multipart/form-data">
									
										<div class="InputForm">
											<label class="title" id="locno">���I�s��</label> 
											<select name="locno" onchange="queryLocationUpdate(this.value)">
													<option value="">�п��</option>
					 							<c:forEach var="locationVO" items="${locSvc.all}" >
													<option value="${locationVO.locno}">
														${locationVO.locno}
													</option>
												</c:forEach> 
											</select><br />
										</div>	
										<%-- <div>
											<input type="hidden" name="action" value="update">
											<input type="hidden" name="requestURL" value="<%=request.getContextPath()%>">
										</div> --%>
										
																	
										<%-- <div class="InputForm">
											<label class="title">���I�W��</label>
											<input type="text" id="locname" name="locname" maxlength="10" value=""/><br>
										</div>
										<div class="InputForm">
											<label class="title">���I�q��</label>
											<input type="text" id="tel" name="tel" maxlength="10" value="" /><br>
										</div>
										<div class="InputForm">
											<label class="title">���I�a�}</label>
											<input type="text" id="addr" name="addr" maxlength="100" value="" /><br>
										</div>
										<div class="InputForm">
											<label class="title">���I�Ӥ�</label>
											<input type="file" id="pic" name="pic" value="" /><br>
										</div>
										<div class="InputForm">
											<label class="title">���I�a�Ϻ��</label>
											<input type="text" id="lon" name="lon" value="" /><br>
										</div>
										<div class="InputForm">
											<label class="title">���I�a�Ͻn��</label>
											<input type="text" id="lat" name="lat" value="${locationVO.getLat}" /><br>
										</div>										
										<div class="InputForm">
											<label class="title">���I���A</label> 
												<select name="status">
													<option value="open">��~��
													<option value="closed">�w��~
													<option value="preparing">�w�Ƥ�
												</select><br />
										</div>											
										<div class="InputForm">
											<input type="hidden" name="action" value="update">
											<input type="submit" value="submit" class="click" /> 
											<input type="reset" name="reset" value="reset" class="click" />
										</div> --%>
									</form>
									
									<div id="showOneResult"></div>
									
									</fieldset>
									<!--end: form�\�� �ק� -->
							  
							  </div>

					          <div role="tabpanel" class="tab-pane" id="tab4">
					          		<fieldset>
					          		<legend>���I�R��</legend>
		<!--form�\�� �R��  -->
									<form method="post" action="location.do">
										<div class="InputForm">
											<label class="title">�ݧR���I</label> 
												<select name="locno">
													<option value="">�п��</option>
						 							<c:forEach var="locationVO" items="${locSvc.all}">
														<option value="${locationVO.locno}">
															${locationVO.locno}
														</option>
													</c:forEach> 
												</select><br />
										</div>
										<div class="InputForm">
											<input type="hidden" name="action" value="delete">
											<input type="hidden" name="requestURL" value="<%= request.getServletPath()%>">
											<input type="submit" value="delete" class="click" /> 
										</div>	
									</form>
									</fieldset>
							  </div>

<!--���ҭ��O ���e�ϵ��� -->      </div>
<!--���ҭ��O ���� -->		   </div>
<!--end block1 --> </div>	

<!-- block3 ��� --> <div id="block3" class="col-xs-12 col-sm-8">
						<table id="QueryTable"  class="table table-bordred table-striped  table-hover">
							  <thead>	
									<tr class="QueryTable_TR">
										<th>���I�s��</th>
										<th>���I�a�I</th>
										<th>���I�q��</th>
										<th>���I�a�}</th>
										<th>���I�ۤ�</th>
										<th>���I���</th>
										<th>���I�n��</th>
										<th>���I���A</th>
										
																											
									</tr>
								  </thead>
								  <tbody>				  		
							 		<c:forEach var="locationVO" items="${locSvc.all}">
										<tr class="QueryTable_TR">
										
											<td>${locationVO.locno}</td>
											<td>${locationVO.locname}</td>
											<td>${locationVO.tel}</td>	
											<td>${locationVO.addr}</td>
											<td><img src="<%=request.getContextPath()%>/frontend/location/locReader.do?locno=${locationVO.locno}&card=pic" height="200" width="200"></td>
											<td>${locationVO.lon}</td>
											<td>${locationVO.lat}</td>
											<td>${locationVO.status}</td>
											<!-- 
											<td>
											<input type="hidden" name="action" value="update">
											<input type="submit" value="Update" class="click2" /> 
											</td>
											 -->
											<!-- 
											<td>${roVO.milstart}</td>
											<td>${roVO.milend}</td>
											<td>${roVO.note}</td>
											<td>
											<input type="hidden" name="action" value="delete">
											<input type="submit" value="Delete" class="click2" /> 
											</td> 
											-->								
										</tr>
									</c:forEach>							
							  </tbody>	 	  								
						</table>
					 		
					</div>
<!--end: block3 --> 	
<!--container--></div>	

    </div><!-- sm-10 rightHTML  -->




<!-- =============================================================================== -->
<%-- <table border='1' bordercolor='#CCCCFF' width='1500'>
	<tr>
		<th>���I�s��</th>
		<th>���I�a�I</th>
		<th>���I�q��</th>
		<th>���I�a�}</th>
		<th>���I�ۤ�</th>
		<th>���I���</th>
		<th>���I�n��</th>
		<th>���I���A</th>
		<th>�ק���I</th>
		<th>�R�����I</th>
	</tr>
	<%@ include file="pages/page1.file" %> 
	<c:forEach var="locationVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
	<c:forEach var="locationVO" items="${list}">
		<tr align='center' valign='middle' ${(locationVO.locno==param.locno) ? 'bgcolor=#CCCCFF':''}><!--�N�ק諸���@���[�J����Ӥw-->
			<td>${locationVO.locno}</td>
			<td>${locationVO.locname}</td>
			<td>${locationVO.tel}</td>
			<td>${locationVO.addr}</td>
			<td><img src="<%=request.getContextPath()%>/frontend/location/locReader.do?locno=${locationVO.locno}&card=pic" height="200" width="200"></td>
			<td>${locationVO.lon}</td>
			<td>${locationVO.lat}</td>
			<td>${locationVO.status}</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/location/location.do">
			     <input type="submit" value="�ק�"> 
			     <input type="hidden" name="locno" value="${locationVO.locno}">
			     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--�e�X�����������|��Controller-->
			    <input type="hidden" name="whichPage"	>               <!--�e�X��e�O�ĴX����Controller-->
			     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
			</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/location/location.do">
			    <input type="submit" value="�R��">
			    <input type="hidden" name="locno" value="${locationVO.locno}">
			    <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--�e�X�����������|��Controller-->
			    <input type="hidden" name="whichPage"	>               <!--�e�X��e�O�ĴX����Controller-->
			    <input type="hidden" name="action"value="delete"></FORM>
			</td>
		</tr>
	</c:forEach>
</table> --%>
<%-- <%@ include file="pages/page2.file" %> --%>

	<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="<%=request.getContextPath()%>/backend/location/js/locationNew.js"></script>
    <script src="<%=request.getContextPath()%>/backend/rent_ord/Modified/motorKanli_for_ro.js"></script>
    <script src="<%=request.getContextPath()%>/backend/rent_ord/Modified/datepicker_for_ro.js"></script>
 	<script src="<%=request.getContextPath()%>/backend/Modified/paging.js"></script>
  <%--  <font color=blue>request.getServletPath():</font> <%= request.getServletPath()%><br>
   <font color=blue>request.getRequestURI(): </font> <%= request.getRequestURI()%> </b> --%>
</body>
</html>
