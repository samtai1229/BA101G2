<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.member.model.*"%>
<%
MemberVO memVO = (MemberVO) request.getAttribute("memVO");
%>

<%@ page import="com.adminis.model.*"%>
<%  
	AdminisService as = new AdminisService();
	AdminisVO adminisVO= (AdminisVO)session.getAttribute("adminisVO");
%>

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
	
<title>�|����Ʒs�W - addSpot.jsp</title>
</head>
<style>
.btn-lg{
	margin:5px;
	margin-top:10px;
}
textarea{
	resize:none;
}
.showImage{
	margin-top:5%;
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
         <%if(adminisVO.getAuthno().equals("AC01") || adminisVO.getAuthno().equals("AC07")){%>     
            <a class="btn btn-default" href="<%=request.getContextPath()%>/backend/motor/backendMotor.jsp"  role="button">������ƺ޲z</a>
            <a class="btn btn-default" href="<%=request.getContextPath()%>/backend/motor_model/backendMotorModel.jsp"  role="button">���������޲z</a>           
            <a class="btn btn-default" href="#" role="button">�����ի�</a>
			<a class="btn btn-default" href="<%=request.getContextPath()%>/backend/rent_ord/backendRentOrd.jsp" role="button">�����޲z</a>
            <a class="btn btn-default" href="#" role="button">�˳ƺ޲z</a>
            <a class="btn btn-default" href="#" role="button">�˳ƽի�</a>
            <a class="btn btn-default" href="#" role="button">���I�޲z</a>
         <%} %>  
        </div>
       
        <button class="accordion accordionMenu">���I�޲z�t��</button>
        <div class="btn-group-vertical">
        <%if(adminisVO.getAuthno().equals("AC02") || adminisVO.getAuthno().equals("AC07")){%> 
            <a class="btn btn-default" href="#" role="button">���I�����޲z</a>
            <a class="btn btn-default" href="<%=request.getContextPath()%>/backend/rent_ord/lease.jsp"  role="button">�樮�޲z</a>
          	<a class="btn btn-default" href="<%=request.getContextPath()%>/backend/rent_ord/return.jsp"  role="button">�٨��޲z</a>
            <a class="btn btn-default" href="#" role="button">�����իץӽ�</a>
            <a class="btn btn-default" href="#" role="button">�����O�i/���׺޲z</a>
            <a class="btn btn-default" href="#" role="button">���I�˳ƺ޲z</a>
            <a class="btn btn-default" href="#" role="button">�˳ƥӽ�</a>
         <%} %>
        </div>
         
        <button class="accordion accordionMenu">�G�⨮�޲z�t��</button>
        <div class="btn-group-vertical">
        <%if(adminisVO.getAuthno().equals("AC05") || adminisVO.getAuthno().equals("AC07")){%>
            <a class="btn btn-default" href="<%=request.getContextPath()%>/backend/second_order/SaleOnOff.jsp?who=${admins}" role="button">�G�⨮���޲z</a>
            <a class="btn btn-default" href="#" role="button">�G�⨮�q��޲z</a>
            <a class="btn btn-default" href="#" role="button">�G�⨮����޲z</a>
         <%} %>
        </div>
       <button class="accordion accordionMenu">�|���޲z�t��</button>
        <div class="btn-group-vertical">
            <a class="btn btn-default" href="<%=request.getContextPath()%>/backend/member/backendMember.jsp" role="button">�|���޲z</a>
            <a class="btn btn-default" href="<%=request.getContextPath()%>/backend/member/addMember.jsp" role="button">�s�W�|��</a>
        </div>
        <button class="accordion accordionMenu">���ʥ����޲z�t��</button>
        <div class="btn-group-vertical">
        <%if(adminisVO.getAuthno().equals("AC06") || adminisVO.getAuthno().equals("AC07")){%>
            <a class="btn btn-default" href="#" role="button">�����޲z</a>
            <a class="btn btn-default" href="<%=request.getContextPath()%>/backend/mes_board/listAllMesBoard.jsp" role="button">�d�����޲z</a>
            <a class="btn btn-default" href="<%=request.getContextPath()%>/backend/news/news_select_page.jsp" role="button">�̷s�����޲z</a>
         <%} %>
        </div>
        <button class="accordion accordionMenu">��ݺ޲z�t��</button>
        <div class="btn-group-vertical">
        <%if(adminisVO.getAuthno().equals("AC04") || adminisVO.getAuthno().equals("AC07")){%>
            <a class="btn btn-default"  href="<%=request.getContextPath()%>/backend/adminis/adm_select_page.jsp" role="button">����v���޲z</a>
            <a class="btn btn-default" href="#" role="button">���˴��I�޲z</a>
            <a class="btn btn-default" href="#" role="button">��ݵn�J�޲z</a>
         <%} %>
        </div>
        <div class="btn-group-vertical"></div>
    </div>
    
    
    
    
    <div class="col-xs-12 col-sm-10 rightHTML">

		<div class="topTitle">
            <h1>�|����ƺ޲z�t��</h1>
        </div>
        
        
		<div class="container-fluid">  

		
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
			
			
			
			<FORM METHOD="post" ACTION="member.do" name="form1" enctype="multipart/form-data">
				<div class="container col-xs-12 col-sm-6 col-sm-offset-2">
				<h3>�s�W�|��:</h3>
				<table width="100%">
					<tr>
						<td>�|���W��:</td>
						<td><input type="TEXT" name="memname" 
							value="${memVO.memname()}"  class="form-control" style="width:100%" /></td>
					</tr>
					<tr>
						<td>�ʧO:</td>
						<td>
							<select name="sex" class="form-control" style="width:100%" >
								<option value="male">�k
								<option value="female">�k
							</select>
						</td>
					</tr>	
					<tr>
						<td>�ͤ�:</td>
						<td><input type="date" name="birth" class="form-control" style="width:100%" ></td>
					</tr>	
					<tr>
						<td>�H�c:</td>
						<td><input type="email" name="mail" class="form-control" style="width:100%" /></td>
					</tr>	
					<tr>
						<td>�q��:</td>
						<td><input type="tel" name="phone" class="form-control" style="width:100%" /></td>
					</tr>
					<tr>
						<td>�a�}:</td>
						
						<td><textarea rows="4" name="address" class="form-control" style="width:100%" ></textarea></td>
					</tr>
					<tr>
						<td>�b��:</td>
						<td><input type="text" name="acc" class="form-control" style="width:100%" /></td>
					</tr>
					<tr>
						<td>�K�X:</td>
						<td><input type="password" name="pwd" class="form-control" style="width:100%" /></td>
					</tr>
					<tr>
						<td>������(����):</td>
						<td><input type="file" name="idcard1" id="myFile" class="form-control" style="width:100%" /></td>
					</tr>
					<tr>
						<td>������(�ϭ�):</td>
						<td><input type="file" name="idcard2" id="myFile1" class="form-control" style="width:100%" /></td>
					</tr>
					<tr>
						<td>�r��:</td>
						<td><input type="file" name="license" id="myFile2" class="form-control" style="width:100%;" /></td>
					</tr>
				</table>
				<input type="hidden" name="action" value="insert">
					<p class="text-center">
						<button type="submit" class="btn btn-success btn-lg">
							<i class="glyphicon glyphicon-ok"></i>�e�X�ק�
						</button>
						<a onclick="history.back()" class="btn btn-danger btn-lg">
							<i class="glyphicon glyphicon-arrow-up"></i>��^�W��
						</a>
					</p>
				
				</div>
				
				
			</FORM>
				
				<div class="col-xs-12 col-sm-4 showImage">
					<h4>Show Image</h4>
					<p>
						<textarea id="fileInfo" rows="6" style="width:100%"></textarea>
					</p>
					<p>
						<img id="image">
					</p>
				</div>
		</div>
	</div>
</body>
<script>
function doFirst(){
	document.getElementById('myFile').onchange = fileChange;
	document.getElementById('myFile1').onchange = fileChange1;
	document.getElementById('myFile2').onchange = fileChange2;
}
function fileChange() {
	var file = document.getElementById('myFile').files[0];
	var message = '';

	message += 'File Name: '+file.name+'\n';
	message += 'File Type: '+file.type+'\n';
	message += 'File Size: '+file.size+' byte(s)\n';
	message += 'Last modified: '+file.lastModifiedDate+'\n';
	document.getElementById('fileInfo').innerHTML = message;

	var readFile = new FileReader();
	readFile.readAsDataURL(file);
	readFile.addEventListener('load',function(){
		var image = document.getElementById('image');
		image.src = this.result;
		image.style.maxWidth = '500px';
		image.style.maxHeight = '500px';
	},false);
}

function fileChange1() {
	var file = document.getElementById('myFile1').files[0];
	var message = '';

	message += 'File Name: '+file.name+'\n';
	message += 'File Type: '+file.type+'\n';
	message += 'File Size: '+file.size+' byte(s)\n';
	message += 'Last modified: '+file.lastModifiedDate+'\n';
	document.getElementById('fileInfo').innerHTML = message;

	var readFile = new FileReader();
	readFile.readAsDataURL(file);
	readFile.addEventListener('load',function(){
		var image = document.getElementById('image');
		image.src = this.result;
		image.style.maxWidth = '500px';
		image.style.maxHeight = '500px';
	},false);
}

function fileChange2() {
	var file = document.getElementById('myFile2').files[0];
	var message = '';

	message += 'File Name: '+file.name+'\n';
	message += 'File Type: '+file.type+'\n';
	message += 'File Size: '+file.size+' byte(s)\n';
	message += 'Last modified: '+file.lastModifiedDate+'\n';
	document.getElementById('fileInfo').innerHTML = message;

	var readFile = new FileReader();
	readFile.readAsDataURL(file);
	readFile.addEventListener('load',function(){
		var image = document.getElementById('image');
		image.src = this.result;
		image.style.maxWidth = '500px';
		image.style.maxHeight = '500px';
	},false);
}
window.addEventListener('load',doFirst,false);
</script>
</html>
