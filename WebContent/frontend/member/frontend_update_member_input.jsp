<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.member.model.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
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
}

.alert {
    padding: 20px;
    background-color: #f44336; /* Red */
    color: white;
    margin-bottom: 15px;
}

</style>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	
	<link rel="stylesheet" href="<%=request.getContextPath()%>/backend/Modified/jquery_ui_1_10_3_theme.css"/>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/twitter_bootstrap_3_3_7_min.css">   
	<link rel="stylesheet" href="<%=request.getContextPath()%>/vendor/bootstrap/css/bootstrap.min.css" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/vendor/font-awesome/css/font-awesome.min.css" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/google_family_kaushan_script.css" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/frontend/rental_form/Modified/agency.min.css"  />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/frontend/rental_form/Modified/agency.css" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/frontend/rental_form/Modified/other.css" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/frontend/rental_form/Modified/daterangepicker.css" />

	<title>會員資料修改</title>
</head>

<style type="text/css">
.gallery-title {
	font-size: 36px;
	color: #42B32F;
	text-align: center;
	font-weight: 500;
	margin-bottom: 70px;
}

.gallery-title:after {
	content: "";
	position: absolute;
	width: 7.5%;
	left: 46.5%;
	height: 45px;
	border-bottom: 1px solid #5e5e5e;
}

.filter-button {
	font-size: 18px;
	border: 1px solid #42B32F;
	border-radius: 5px;
	text-align: center;
	color: #42B32F;
	margin-bottom: 30px;
}

.filter-button:hover {
	font-size: 18px;
	border: 1px solid #42B32F;
	border-radius: 5px;
	text-align: center;
	color: #ffffff;
	background-color: #42B32F;
}

.btn-default:active .filter-button:active {
	background-color: #42B32F;
	color: white;
}

.port-image {
	width: 100%;
}

.gallery_product {
	margin-bottom: 30px;
}

#myBtn {
  display: none;
  position: fixed;
  bottom: 20px;
  right: 30px;
  z-index: 99;
  border: none;
  outline: none;
  background-color: red;
  color: white;
  cursor: pointer;
  padding: 15px;
  border-radius: 10px;
}

#myBtn:hover {
  background-color: #555;
}

.topdiv{

	margin-top:100px;
	color:#eee;

}

body {
       background: url(/BA101G2/img/header1.jpg) no-repeat center center fixed; 
	    -webkit-background-size: cover;
	    -moz-background-size: cover;
	    -o-background-size: cover;
	    background-size: cover;
}

#containerDiv{
	margin-top:8%;
}

.bgTag{
 /* background-color: #0f0 !important; */
}

.fileTag{
	margin-bottom:10px;
	margin-top:10px;
}

mark{
	line-height:34px;
	font-size:16px;
}

#addressMark{
	line-height:94px;
}

h1{
	margin-top:0px;
	margin-bottom:30px;
	text-align:center;
	color:#fff;
}

input[type="file"] {
    display: none !important;
}
.custom-file-upload {
    border: 1px solid #ccc;
    display: inline-block;
    padding: 6px 12px;
    cursor: pointer;
    margin-left:15%;
    background-color:#fff;
}

#licenseTag{
	margin-left:10%;

}

.radioBgTag{
	background-color:#fff;
	vertical-align: middle!important;
	height: 34px;
     width: 247px; 
    margin-left: 15px;
    padding: 6px 12px;
    font-size: 16px;
    line-height: 1.42857143;
    color: #555;
    background-color: #fff;
    background-image: none;
    border: 1px solid #ccc;
    border-radius: 4px;
}

</style>


<body>




<!-- Navigation -->
	<nav id="mainNav" class="navbar navbar-default navbar-custom navbar-fixed-top">
		<div class="container-fluid">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header page-scroll">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Toggle navigation</span> Menu <i
						class="fa fa-bars"></i>
				</button>
				<a class="navbar-brand page-scroll" href="<%=request.getContextPath()%>/index.jsp">AutoBike</a>
			</div>

			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav navbar-right">
					<li class="hidden"><a href="#page-top"></a></li>
					<li><a class="page-scroll" href="<%=request.getContextPath()%>/frontend/rental_form/rental_category.jsp">
					<i class="glyphicon glyphicon-heart"></i>我要租車</a></li>
					<li><a class="page-scroll" href="#news">
					<i class="glyphicon glyphicon-alert"></i>最新消息</a></li>
					<li><a class="page-scroll" href="#board">
					<i class="fa fa-comments-o"></i>留言板</a></li>
					<li><a class="page-scroll" href="#loc">
					<i class="fa fa-search"></i>服務據點</a></li>
					<li><a href="<%=request.getContextPath()%>/backend/member/member.do">
					<i class="fa fa-shopping-cart"></i>二手車購買</a></li>
					<c:if test="${not empty memno}">
						<li><a href="<%=request.getContextPath()%>/backend/member/member.do?action=getOne_For_Enter&memid=${memno}">歡迎，${(memname == null) ? '會員':memname}</a></li>
						<li><a href="<%=request.getContextPath()%>/backend/member/member.do?action=logout" data-toggle="modal">
							<i class="glyphicon glyphicon-user"></i>登出</a>
						</li>
					</c:if>
				</ul>
			</div>
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container-fluid -->
	</nav>
	
	<div id="blocker"></div>


	<!-- 租車主軸Header -->

<%----------------------------------------------------VVVV building area VVVV-----------------------------------------------------------%>
	

	<div id="containerDiv">
		<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/member/member.do" name="form1" enctype="multipart/form-data">
			<div class="col-xs-12 col-sm-3">
				<h1>會員資料修改</h1>	
				<div class="form-group  col-xs-12 col-sm-12 bgTag">
					<label for="memname" class="col-xs-12 col-sm-4 control-label" >
						<mark>姓名:</mark>
					</label>
					<div class="col-xs-12 col-sm-8">
					<input type="text" name="memname" 
					  value="${memVO.memname}" placeholder="文字" class="form-control" id="memname" style="width:100%"/>
					</div>
				</div>
				
				<c:if test="${memVO.sex !=null}">
					<div class="form-group col-xs-12 col-sm-12 bgTag">
						<label for="sex" class="col-xs-12 col-sm-4 control-label">
							<mark>性別:</mark>
						</label>
						<div class="col-xs-12 col-sm-8">
							<input type="text" name="sex" 
							value="${memVO.sex}" placeholder="文字" class="form-control" id="sex" style="width:100%" readonly/>
						</div>
					</div>
				</c:if>
				<c:if test="${memVO.sex ==null}">
					<div class="form-group col-xs-12 col-sm-12 bgTag">
						<label for="sex" class="col-xs-12 col-sm-4 control-label">
							<mark>性別:</mark>
						</label>
						<div class="col-xs-12 col-sm-8 radioBgTag">
							<label class="radio-inline "><input type="radio" name="sex" id="male" value="male"/>男</label>
							<label class="radio-inline"><input type="radio" name="sex" id="female" value="female">女</label>
						</div>
					</div>
				</c:if>				
				
 				<div class="form-group col-xs-12 col-sm-12 bgTag">
					<label for="birth" class="col-xs-12 col-sm-4">
						<mark>生日:</mark>
					</label>
					<div class="col-xs-12 col-sm-8">
						<input type="text" name="birth" id="birth"  class="form-control" style="width:100%"  
						value="<fmt:formatDate pattern = "yyyy/MM/dd" value ="${memVO.birth}"/>" readonly/>
					</div>
				</div>		
												 
				<div class="form-group col-xs-12 col-sm-12  bgTag">
					<label for="create" class="col-xs-12 col-sm-4">
						<mark>註冊日期:</mark>
					</label>
					<div class="col-xs-12 col-sm-8">
						<input type="text" name="create"  class="form-control" style="width:100%"  
						value="<fmt:formatDate pattern = "yyyy/MM/dd" value = "${memVO.credate}"/>" disabled/>
					</div>
				</div>
				
	
				<div class="form-group col-xs-12 col-sm-12">
					<label for="mail" class="col-xs-12 col-sm-4">
						<mark>信箱:</mark>
					</label>
					<div class="col-xs-12 col-sm-8">
					<input type="email" name="mail"  class="form-control" style="width:100%"  value="${memVO.mail}"/>
					</div>
				</div>
				
				<div class="form-group col-xs-12 col-sm-12">
					<label for="phone" class="col-xs-12 col-sm-4">
						<mark>電話:</mark>
					</label>
					<div class="col-xs-12 col-sm-8">
						<input type="tel" name="phone"  class="form-control"  style="width:100%"  value="${memVO.phone}" maxlength="10"/>
					</div>
				</div>
				
				<div class="form-group col-xs-12 col-sm-12">
					<label for="address" class="col-xs-12 col-sm-4">
						<mark id="addressMark">地址:</mark>
					</label>
					<div class="col-xs-12 col-sm-8">
					<textarea rows="4" name="address" class="form-control" style="width:100%" maxlength="60">${memVO.addr}</textarea>
					</div>
				</div>
				
				<div class="form-group col-xs-12 col-sm-12">
					<label for="acc" class="col-xs-12 col-sm-4">
						<mark>帳號:</mark>
					</label>
					<div class="col-xs-12 col-sm-8">
						<input type="text" name="acc" class="form-control" style="width:100%" value="${memVO.acc}"/>
					</div>
				</div>
				
				<div class="form-group col-xs-12 col-sm-12">
					<label for="acc" class="col-xs-12 col-sm-4">
						<mark>密碼:</mark>
					</label>
					<div class="col-xs-12 col-sm-8">
						<input type="password" name="pwd" id="pwd" class="form-control" style="width:100%" value="${memVO.pwd}"/>
					</div>
				</div>
				
				<div class="form-group col-xs-12 col-sm-12">
					<label for="acc" class="col-xs-12 col-sm-4">
						<mark>重填密碼:</mark>
					</label>
					<div class="col-xs-12 col-sm-8">
						<input type="password" name="pwd2" id="pwd2" class="form-control" style="width:100%" value=""/>
					</div>
				</div>								
				
				<div class="form-group col-xs-12 col-sm-12">
					<label for="status" class="col-xs-12 col-sm-4">
						<mark>狀態:</mark>
					</label>
					<div class="col-xs-12 col-sm-8">
				<%
					Map<String, String> statusMap = new HashMap<String, String>();
					statusMap.put("uncompleted", "簡易註冊");
					statusMap.put("unconfirmed", "還未認證");
					statusMap.put("verifing", "等待認證");
					statusMap.put("confirmed", "認證合格");	
				%>	
					<c:set scope="page" var="temp">
						<c:out value="${memVO.status}"/>
					</c:set>
				<% 
					String key = String.valueOf(pageContext.getAttribute("temp"));
					if(statusMap.containsKey(key)){
				%>
				
						<input type="text" name="statusShow" class="form-control" style="width:100%" value="<%=statusMap.get(key)%>" readonly/>
						<input type="hidden" name="status" value="${memVO.status}">
			<%};%>			
					</div>
				</div>
			</div>
		
			<div class="col-xs-12 col-sm-3">
				<label><mark>身分證(正面):</mark></label><label for="myFile" class="custom-file-upload">
   				<i class="fa fa-cloud-upload"></i>點我上傳圖片</label>
				<input type="file" name="idcard1" id="myFile"/>
				<img src='<%=request.getContextPath()%>/backend/member/memReader.do?memno=${memVO.memno}&card=idcard1' 
				width='300px' height='300px' id="idcard1">
	        </div>
	        <div class="col-xs-12 col-sm-3">
				<label><mark>身分證(反面):</mark></label><label for="myFile1" class="custom-file-upload">
   				<i class="fa fa-cloud-upload"></i>點我上傳圖片</label>
   				<input type="file" name="idcard2"  id="myFile1"/>
				<img src='<%=request.getContextPath()%>/backend/member/memReader.do?memno=${memVO.memno}&card=idcard2'
				 width='300px' height='300px' id="idcard2">
	        </div>
	        <div class="col-xs-12 col-sm-3">	
				<label id="licenseTag"><mark>駕照:</mark></label><label for="myFile2" class="custom-file-upload">
   				<i class="fa fa-cloud-upload"></i>點我上傳圖片</label>
   				<input type="file" name="license"  id="myFile2"/>
				<img src='<%=request.getContextPath()%>/backend/member/memReader.do?memno=${memVO.memno}&card=license'
				 width='300px' height='300px' id="license">
			 </div>
		
			<input type="hidden" name="action" value="update_frontend_verified">
			<input type="hidden" name="memno" value="${memVO.memno}">
			<input type="hidden" name="pwd" value="${memVO.pwd}">
			<input type="hidden" name="sex" value="${memVO.sex}">
			<input type="hidden" name="birth" value="<fmt:formatDate value="${memVO.birth}" pattern='yyyy-MM-dd HH:mm:ss'/>">
			<input type="hidden" name="credate" value="<fmt:formatDate value="${memVO.credate}" pattern='yyyy-MM-dd HH:mm:ss'/>">
			<div class="col-xs-12 col-sm-12">
				<p class="text-center">
					<button type="submit" onClick="return check()" class="btn btn-success btn-lg">
						<i class="glyphicon glyphicon-ok"></i>送出修改
					</button>
					<c:if test="${memVO.status=='unconfirmed'||memVO.status=='uncompleted'}">
						<button type="button" onclick="changeStatus(); this.disabled=true;"
						class="btn btn-info btn-lg" id="sendConfirm">
							<i class="glyphicon glyphicon-ok"></i>送出認證請求
						</button>					
					</c:if>
					<c:if test="${memVO.status =='verifing'}">
						<button type="submit" class="btn btn-info btn-lg" disabled>
							<i class="glyphicon glyphicon-ok"></i>認證中
						</button>					
					</c:if>					
					<a href="<%=request.getContextPath()%>/backend/member/member.do?action=getOne_For_Enter&memno=${memno}"
					 class="btn btn-danger btn-lg">
						<i class="glyphicon glyphicon-arrow-up"></i>返回上頁
					</a>
				</p>
			</div>
		</FORM>
	</div>
	
  
	
		<%----------------------------------------------------^^^^ building area ^^^^-----------------------------------------------------------%>		

	<footer>
		<div class="container-fluid topdiv">
			<div class="col-xs-12 col-sm-4">
				<span>地址:桃園市平鎮區中央路115號</span>
			</div>
			<div class="col-xs-12 col-sm-4">
				<span>EMAIL:taic@oregonstate.edu</span>
			</div>
			<div class="col-xs-12 col-sm-4">
				<span>TEL:0900-000-000</span>
			</div>
		</div>
	</footer>


</body>

	<script src="<%=request.getContextPath()%>/backend/Modified/jquery_1_10_1_min.js"></script>
	<script src="<%=request.getContextPath()%>/backend/Modified/jquery_ui_1_10_3.js"></script>
	<script src="<%=request.getContextPath()%>/backend/Modified/twitter_bootstrap_3_3_7_min.js"></script>
	<script src="<%=request.getContextPath()%>/js/agency.min.js"></script>	
<script>

	function loadDoc() {
	    var xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
		     document.getElementById("demo").innerHTML=this.responseText;
		    }
		 };
		  xhttp.open("GET","<%=request.getContextPath()%>/backend/member/member.do?addaction=frontend&action=get_second_ord_per_member&memno=${memVO.memno}", true);
		  xhttp.send();
	}

	function check(){
		var p1 = document.getElementById('pwd').value; 
		var p2 = document.getElementById('pwd2').value;
		alert("已更新");
		
		if(p1!='${memVO.pwd}'||p2.trim().length!=0){
			if ( p1 == p2 ) { 
				if ( p1.length > 6 && p2.length > 6 ){
					alert("密碼更新成功!");
					return true;
				}else{
					alert("密碼設定至少 7 碼以上"); return false;
				}
			}else{
				alert("兩組密碼不一致，請重新輸入密碼");
				return false;
			}
		}
	}

	function changeStatus() {
   var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
	if (this.readyState == 4 && this.status == 200) {
	     //document.getElementById("sendConfirm").innerHTML=this.responseText;
	    }
	 };
	  xhttp.open("GET","<%=request.getContextPath()%>/backend/member/member.do?action=change_status&status=verifing&memno=${memVO.memno}", true);
	  xhttp.send();
	}

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
	
	  $( function() {
		    $( "#birth" ).datepicker({
		    	dateFormat: 'yy/mm/dd',
		      changeMonth: true,
		      changeYear: true,
		      defaultDate:"1984/01/01",
		      yearRange: "1930:1999"
		    });
		  } );
	
window.addEventListener('load',doFirst,false);
</script>
</html>