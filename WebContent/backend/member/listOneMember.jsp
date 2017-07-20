<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.member.model.*"%>
<%@ page import="com.adminis.model.*"%>
<% 
AdminisService as = new AdminisService();
AdminisVO adminisVO= (AdminisVO)session.getAttribute("adminisVO");
MemberVO memVO = (MemberVO) request.getAttribute("memVO");

%>

<%-- ���X ������LocationVO����--%>
<%
//  DeptService deptSvc = new DeptService();
//  DeptVO deptVO = deptSvc.getOneDept(empVO.getDeptno());
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

   <title>�|���޲z-AutoBike</title>
</head>
<style type="text/css">

.custab{
    border: 1px solid #ccc;
    padding: 5px;
    margin: 5% 0;
    box-shadow: 3px 3px 2px #ccc;
    transition: 0.5s;
    }
.custab:hover{
    box-shadow: 3px 3px 0px transparent;
    transition: 0.5s;
    }
    
    /*�۩w*/
	 th{
		/*����������*/
		white-space:nowrap;
	} 
	.btn-lg{
		margin:5px;
	}
	
	td{
		vertical-align: middle!important;
	}


</style>

<body bgcolor='white'>

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
	    <table border="1" class="table table-striped custab stripe hover">
		    <thead>
		        <tr>
		        	<th>�ק�</th>
		        	<th>�{�Ҫ��A</th>
					<th>�|���s��</th>
					<th>�|���W��</th>
					<th>�ʧO </th>
					<th>�ͤ�</th>
					<th>Mail</th>
					<th>�q��</th>
					<th>�a�}</th>
					<th>�b��</th>
					<th>�����ҥ���</th>
					<th>�����Ҥϭ�</th>
					<th>�r��</th>
					<th>�[�J�ɶ�</th>
				</tr>
		    </thead>
		    <tbody>
		            <tr align='center' valign='middle'>
		            <td class="text-center"><a class='btn btn-info btn-md' 
						href="<%=request.getContextPath()%>/backend/member/member.do?addAction=modifyMember&action=getOne_For_Update&memno=${memVO.memno}">
						<span class="glyphicon glyphicon-edit"></span>�ק�</a>
					</td>
					
					
				<% 
					Map<String, String> statusMap = new HashMap<String, String>();
					statusMap.put("uncompleted", "²�����U");
					statusMap.put("unconfirmed", "�٥��{��");
					statusMap.put("verifing", "���ݻ{��");
					statusMap.put("confirmed", "�{�ҦX��");					
				%>	
					<c:set scope="page" var="temp">
					 	<c:out value="${memVO.status}"/>
					</c:set>
				<% 
				  String key = String.valueOf(pageContext.getAttribute("temp"));
				  if(statusMap.containsKey(key)){
					  if("verifing".equals(key)){;%>
					<td style="color:red"><%=statusMap.get(key)%></td>
					<%}else{;%>
					<td><%=statusMap.get(key)%></td>
					<%}};
				%>						
					
					<td>${memVO.memno}</td>
					<td>${memVO.memname}</td>
					<td>${memVO.sex}</td>
					<td><fmt:formatDate pattern = "yyyy/MM/dd" value = "${memVO.birth}" /></td>
					<td>${memVO.mail}</td>
					<td>${memVO.phone}</td>	
					<td><p align="left">${memVO.addr}</p></td>	
					<td>${memVO.acc}</td>
					<td>
						<c:if test="${memVO.idcard1!=null}">
						<img src='<%=request.getContextPath()%>
						/backend/member/memReader.do?memno=${memVO.memno}&card=idcard1' width='100' height='100'>
						</c:if><c:if test="${memVO.idcard1==null}">�S���Ϥ�</c:if>
					</td>	
					<td>
						<c:if test="${memVO.idcard2!=null}">
						<img src='<%=request.getContextPath()%>
						/backend/member/memReader.do?memno=${memVO.memno}&card=idcard2'  width='100' height='100'>
						</c:if><c:if test="${memVO.idcard2==null}">�S���Ϥ�</c:if>
					</td>				
					<td>
						<c:if test="${memVO.idcard2!=null}">
						<img src='<%=request.getContextPath()%>
						/backend/member/memReader.do?memno=${memVO.memno}&card=license' width='100' height='100'>
						</c:if><c:if test="${memVO.license==null}">�S���Ϥ�</c:if>
					</td>							
					<td><fmt:formatDate pattern = "yyyy/MM/dd" value = "${memVO.credate}" /></td>
				</tr>
				</tbody>
	    </table>
		<form METHOD="post" ACTION="<%=request.getContextPath()%>/backend/member/member.do">
			<input type="hidden" name="addaction" value="backend">
			<input type="hidden" name="action" value="getAllRentOrder">
			<input type="hidden" name="memno" value="${memVO.memno}">
			<p class="text-center">
				<button type="button" onclick="loadDoc()" class="btn btn-info btn-lg">�G�⨮�q��d��</button>
				<button type="submit" class="btn btn-success btn-lg">
					�����d��
				</button>
				<a onclick="history.back()" class="btn btn-danger btn-lg">
					<i class="glyphicon glyphicon-arrow-up"></i>��^�W��
				</a>
			</p>
		</form>


	<div id="demo"></div>
	<div id="demo2"></div>
		</div>
	</div>	

<script type="text/javascript">
 function loadDoc() {
    var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
	if (this.readyState == 4 && this.status == 200) {
	     document.getElementById("demo2").innerHTML=this.responseText;
	    }
	 };
	  xhttp.open("GET","<%=request.getContextPath()%>/backend/member/member.do?addaction=backend&action=get_second_ord_per_member&memno=${memVO.memno}", true);
	  xhttp.send();
	}

 function loadOrder() {
    var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
	if (this.readyState == 4 && this.status == 200) {
	     document.getElementById("demo2").innerHTML=this.responseText;
	    }
	 };
	  xhttp.open("GET","<%=request.getContextPath()%>/backend/member/member.do?addaction=backend&action=getAllRentOrder&memno=${memVO.memno}", true);
	  xhttp.send();
	}	
 
 function toggle() {
	    var xhttp = new XMLHttpRequest();
	    var ss = document.getElementById("mystatus").value;
		xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
		     document.getElementById("demo").innerHTML=this.responseText;
		    }
		 };
		  xhttp.open("GET","<%=request.getContextPath()%>/backend/member/member.do?action=get_second_ord_per_member&memno=${memVO.memno}&status="+ss, true);
		  xhttp.send();
		} 
 
</script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="<%=request.getContextPath()%>/backend/rent_ord/Modified/rentOrdNew.js"></script>
    <script src="<%=request.getContextPath()%>/backend/rent_ord/Modified/motorKanli_for_ro.js"></script>
</body>
</html>
