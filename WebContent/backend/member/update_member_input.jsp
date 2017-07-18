<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.member.model.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	MemberVO memVO = (MemberVO) request.getAttribute("memVO"); //EmpServlet.java (Concroller), 存入req的empVO物件 (包括幫忙取出的empVO, 也包括輸入資料錯誤時的empVO物件)
	String[] statusArray = {"uncompleted","confirmed","unconfirmed"};
	String[] gender = {"Girl","Boy"};
	pageContext.setAttribute("memVO", memVO);
    pageContext.setAttribute("statusArray", statusArray);
	pageContext.setAttribute("gender", gender);
%>
<%@ page import="com.adminis.model.*"%>
<%  
	AdminisService as = new AdminisService();
	AdminisVO adminisVO= (AdminisVO)session.getAttribute("adminisVO");
%>
<html>
<style>
.form-tag{
margin-top:10px;

}
.control-label {
  display: block;
  line-height:34px;
}
label{
	text-align:right;
	size:16px;
}
.form-control{
	size:16px;
}

input[type="file"] {
    display: none !important;
}
.custom-file-upload {
    border: 1px solid #ccc;
    display: inline-block;
    padding: 6px 12px;
    cursor: pointer;
    margin-left:20%;
}

#licenseTag{
	margin-left:10%;

}
</style>

<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">	

	<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.11/jquery-ui.min.js"></script>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">  
	<link rel="stylesheet" href="<%=request.getContextPath()%>/backend/Modified/backendHP_css.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/backend/Modified/main.css" >
	<title>會員資料修改 - update_member_input.jsp</title>
</head>
<body>
    <nav class="navbar navbar-default" role="navigation">
        <!-- logo區 -->
        <a class="navbar-brand" href="#" id="navA">AUTOBIKE</a>
        <!-- 左選單 -->
        <ul class="nav navbar-nav">
            <li><a href="#" id="navA">後端管理系統</a></li>
            <!-- 時鐘 -->
            <iframe scrolling="no" frameborder="no" clocktype="html5" style="overflow:hidden;border:0;margin:0;padding:0;width:120px;height:40px;" src="http://www.clocklink.com/html5embed.php?clock=004&timezone=CCT&color=yellow&size=120&Title=&Message=&Target=&From=2017,1,1,0,0,0&Color=yellow">
            </iframe>
        </ul>
        <!-- 右選單 -->
        <ul class="nav navbar-nav navbar-right">
        </ul>
        <form method="post" action="<%=request.getContextPath()%>/admin.do?action=logout">
        <input type="submit" value="登出" >
<!--           <input type="hidden" name="action1" value="logout" > -->
       </form>
       <b><%= adminisVO.getName() %></b>
    </nav>

    <div class="col-xs-12 col-sm-2 leftBar">
     	
        <img id="menuLogo" src="<%=request.getContextPath()%>/backend/images/android_logo2.jpg">
        <button class="accordion accordionMenu accordion accordionMenuMenu">總部管理系統</button> 
        <div class="btn-group-vertical">
         <%if(adminisVO.getAuthno().equals("AC01") || adminisVO.getAuthno().equals("AC07")){%>     
            <a class="btn btn-default" href="<%=request.getContextPath()%>/backend/motor/backendMotor.jsp"  role="button">車輛資料管理</a>
            <a class="btn btn-default" href="<%=request.getContextPath()%>/backend/motor_model/backendMotorModel.jsp"  role="button">車輛型號管理</a>           
            <a class="btn btn-default" href="#" role="button">車輛調度</a>
			<a class="btn btn-default" href="<%=request.getContextPath()%>/backend/rent_ord/backendRentOrd.jsp" role="button">租賃單管理</a>
            <a class="btn btn-default" href="#" role="button">裝備管理</a>
            <a class="btn btn-default" href="#" role="button">裝備調度</a>
            <a class="btn btn-default" href="#" role="button">據點管理</a>
         <%} %>  
        </div>
       
        <button class="accordion accordionMenu">據點管理系統</button>
        <div class="btn-group-vertical">
        <%if(adminisVO.getAuthno().equals("AC02") || adminisVO.getAuthno().equals("AC07")){%> 
            <a class="btn btn-default" href="#" role="button">據點車輛管理</a>
            <a class="btn btn-default" href="<%=request.getContextPath()%>/backend/rent_ord/lease.jsp"  role="button">交車管理</a>
          	<a class="btn btn-default" href="<%=request.getContextPath()%>/backend/rent_ord/return.jsp"  role="button">還車管理</a>
            <a class="btn btn-default" href="#" role="button">車輛調度申請</a>
            <a class="btn btn-default" href="#" role="button">車輛保養/維修管理</a>
            <a class="btn btn-default" href="#" role="button">據點裝備管理</a>
            <a class="btn btn-default" href="#" role="button">裝備申請</a>
         <%} %>
        </div>
         
        <button class="accordion accordionMenu">二手車管理系統</button>
        <div class="btn-group-vertical">
        <%if(adminisVO.getAuthno().equals("AC05") || adminisVO.getAuthno().equals("AC07")){%>
            <a class="btn btn-default" href="#" role="button">二手車輛管理</a>
            <a class="btn btn-default" href="#" role="button">二手車訂單管理</a>
            <a class="btn btn-default" href="#" role="button">二手車交易管理</a>
         <%} %>
        </div>
       <button class="accordion accordionMenu">會員管理系統</button>
        <div class="btn-group-vertical">
            <a class="btn btn-default" href="<%=request.getContextPath()%>/backend/member/backendMember.jsp" role="button">會員管理</a>
            <a class="btn btn-default" href="<%=request.getContextPath()%>/backend/member/addMember.jsp" role="button">新增會員</a>
        </div>
        <button class="accordion accordionMenu">活動企劃管理系統</button>
        <div class="btn-group-vertical">
        <%if(adminisVO.getAuthno().equals("AC06") || adminisVO.getAuthno().equals("AC07")){%>
            <a class="btn btn-default" href="#" role="button">推播管理</a>
            <a class="btn btn-default" href="#" role="button">留言版管理</a>
            <a class="btn btn-default" href="#" role="button">最新消息管理</a>
         <%} %>
        </div>
        <button class="accordion accordionMenu">後端管理系統</button>
        <div class="btn-group-vertical">
        <%if(adminisVO.getAuthno().equals("AC04") || adminisVO.getAuthno().equals("AC07")){%>
            <a class="btn btn-default" href="#" role="button">後端權限管理</a>
            <a class="btn btn-default" href="#" role="button">推薦景點管理</a>
            <a class="btn btn-default" href="#" role="button">後端登入管理</a>
         <%} %>
        </div>
        <div class="btn-group-vertical"></div>
    </div>
    <div class="col-xs-12 col-sm-10 rightHTML" id="demo">

		<div class="topTitle">
            <h1>會員資料管理系統</h1>
        </div>
        
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
	
		<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/member/member.do" name="form1" enctype="multipart/form-data">
				
				<div class="col-xs-12 col-sm-3">	
				
					<div class="form-group  col-xs-12 col-sm-12">
						<label for="memname" class="col-xs-12 col-sm-4 control-label">
							姓名:
						</label>
						<div class="col-xs-12 col-sm-8">
						<input type="text" name="memname" 
						  value="${memVO.memname}" placeholder="文字" class="form-control" id="memname" style="width:100%"/>
						</div>
					</div>
					
					
					<div class="form-group col-xs-12 col-sm-12">
						<label for="sex" class="col-xs-12 col-sm-4 control-label">
							性別:
						</label>
						<div class="col-xs-12 col-sm-8">
						<input type="text" name="sex" 
						value="${memVO.sex}" placeholder="文字" class="form-control" id="sex" style="width:100%" readonly/>
						</div>
					</div>
					
					
					<div class="form-group col-xs-12 col-sm-12">
						<label for="birth" class="col-xs-12 col-sm-4">
							生日:
						</label>
						<div class="col-xs-12 col-sm-8">
							<fmt:formatDate pattern = "yyyy/MM/dd" value ="${memVO.birth}" />
						</div>
					</div>		
													
					<div class="form-group col-xs-12 col-sm-12">
						<label for="birth" class="col-xs-12 col-sm-4">
							註冊日期:
						</label>
						<div class="col-xs-12 col-sm-8">
							<fmt:formatDate pattern = "yyyy/MM/dd" value = "${memVO.credate}"/>
						</div>
					</div>
					
					
					<div class="form-group col-xs-12 col-sm-12">
						<label for="mail" class="col-xs-12 col-sm-4">
							信箱:
						</label>
						<div class="col-xs-12 col-sm-8">
						<input type="email" name="mail"  class="form-control" style="width:100%"  value="<%=memVO.getMail()%>"/>
						</div>
					</div>
					
					
					
					<div class="form-group col-xs-12 col-sm-12">
						<label for="phone" class="col-xs-12 col-sm-4">
							電話:
						</label>
						<div class="col-xs-12 col-sm-8">
							<input type="tel" name="phone"  class="form-control"  style="width:100%"  value="<%=memVO.getPhone()%>"/>
						</div>
					</div>
					
					
					
					<div class="form-group col-xs-12 col-sm-12">
						<label for="address" class="col-xs-12 col-sm-4">
							地址:
						</label>
						<div class="col-xs-12 col-sm-8">
						<textarea rows="4" name="address" class="form-control" style="width:100%" ><%=memVO.getAddr()%></textarea>
						</div>
					</div>
					
					
					
					<div class="form-group col-xs-12 col-sm-12">
						<label for="acc" class="col-xs-12 col-sm-4">
							帳號:
						</label>
						<div class="col-xs-12 col-sm-8">
							<input type="text" name="acc" class="form-control" style="width:100%" value="<%=memVO.getAcc()%>"/>
						</div>
					</div>
					
					<div class="form-group col-xs-12 col-sm-12">
						<label for="status" class="col-xs-12 col-sm-4">
							狀態:
						</label>
						<div class="col-xs-12 col-sm-8">
							<select name="status"  class="form-control" style="width:100%">
								<c:forEach var="status_value" items="${statusArray}">
									<option value="${status_value}" ${(memVO.status==status_value)? 'selected':''}>${status_value}</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>
			
			
			
				<div class="col-xs-12 col-sm-3">
					<label>身分證(正面):</label><label for="myFile" class="custom-file-upload">
    				<i class="fa fa-cloud-upload"></i>點我上傳圖片</label>
					<input type="file" name="idcard1" id="myFile"/>
					<img src='<%=request.getContextPath()%>/backend/member/memReader.do?memno=${memVO.memno}&card=idcard1' 
					width='300px' height='300px' id="idcard1">
		        </div>
		        <div class="col-xs-12 col-sm-3">
					<label>身分證(反面):</label><label for="myFile1" class="custom-file-upload">
    				<i class="fa fa-cloud-upload"></i>點我上傳圖片</label>
    				<input type="file" name="idcard2"  id="myFile1"/>
					<img src='<%=request.getContextPath()%>/backend/member/memReader.do?memno=${memVO.memno}&card=idcard2'
					 width='300px' height='300px' id="idcard2">
		        </div>
		        <div class="col-xs-12 col-sm-3">	
					<label id="licenseTag">駕照:</label><label for="myFile2" class="custom-file-upload">
    				<i class="fa fa-cloud-upload"></i>點我上傳圖片</label>
    				<input type="file" name="license"  id="myFile2"/>
					<img src='<%=request.getContextPath()%>/backend/member/memReader.do?memno=${memVO.memno}&card=license'
					 width='300px' height='300px' id="license">
				 </div>
			
				<input type="hidden" name="action" value="update_backend_verified">
				<input type="hidden" name="memno" value="${memVO.memno}">
				<input type="hidden" name="pwd" value="${memVO.pwd}">
				<input type="hidden" name="sex" value="${memVO.sex}">
				<input type="hidden" name="birth" value="<fmt:formatDate value="${memVO.birth}" pattern='yyyy-MM-dd HH:mm:ss'/>">
				<input type="hidden" name="credate" value="<fmt:formatDate value="${memVO.credate}" pattern='yyyy-MM-dd HH:mm:ss'/>">
				<div class="col-xs-12 col-sm-12">
					<p class="text-center">
						<button type="submit" class="btn btn-success btn-lg">
							<i class="glyphicon glyphicon-ok"></i>送出修改
						</button>
						<a onclick="history.back()" class="btn btn-danger btn-lg">
							<i class="glyphicon glyphicon-arrow-up"></i>返回上頁
						</a>
						
						
					</p>
				</div>
		</FORM>

    </div><!-- sm-10 rightHTML  -->

	<script src="https://code.jquery.com/jquery.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="<%=request.getContextPath()%>/backend/Modified/motorKanli_js.js"></script>
    <script src="<%=request.getContextPath()%>/backend/Modified/indexNew.js"></script>
<script>
function doFirst(){
	document.getElementById('myFile').onchange = fileChange;
	document.getElementById('myFile1').onchange = fileChange1;
	document.getElementById('myFile2').onchange = fileChange2;
}
function fileChange() {
	var file = document.getElementById('myFile').files[0];

	var readFile = new FileReader();
	readFile.readAsDataURL(file);
	readFile.addEventListener('load',function(){
		var image = document.getElementById('idcard1');
		image.src = this.result;
		image.style.maxWidth = '500px';
		image.style.maxHeight = '500px';
	},false);
}

function fileChange1() {
	var file = document.getElementById('myFile1').files[0];

	var readFile = new FileReader();
	readFile.readAsDataURL(file);
	readFile.addEventListener('load',function(){
		var image = document.getElementById('idcard2');
		image.src = this.result;
		image.style.maxWidth = '500px';
		image.style.maxHeight = '500px';
	},false);
}

function fileChange2() {
	var file = document.getElementById('myFile2').files[0];

	var readFile = new FileReader();
	readFile.readAsDataURL(file);
	readFile.addEventListener('load',function(){
		var image = document.getElementById('license');
		image.src = this.result;
		image.style.maxWidth = '500px';
		image.style.maxHeight = '500px';
	},false);
}
window.addEventListener('load',doFirst,false);
</script>

</body>
</html>
