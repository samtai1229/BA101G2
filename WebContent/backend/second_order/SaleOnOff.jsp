<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.motor.model.*" %>
<%@ page import="java.util.*" %>
<% MotorService moSvc = new MotorService();
   List<MotorVO> list = moSvc.getAll();
   String status =(String) request.getAttribute("status");
   System.out.println("狀態選:"+status);
   if(status==null)
   {
	   status="all";
   }

   String who = (String)request.getParameter("who");
  String who2 = new String(who.getBytes("ISO-8859-1"),"UTF-8");
   System.out.println(who);
  System.out.println(who2);
   session.setAttribute("who", who2);
   pageContext.setAttribute("list", list);
   pageContext.setAttribute("status", status);

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Insert title here</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
<style type="text/css">
	
@media screen and (min-width:768px) and (max-width: 991px){
	.navbar .container{
		width: 100%;
	}
	/* 漢堡選單與頁首 */
	.navbar-toggle{
		display: block;
		float: right;
		margin-right: 0;
	}
	.navbar-header{
		width: 100%;
	}
	/* 選單隱藏區 */
	.collapsing.navbar-collapse{
		margin-left: -15px;
		margin-right: -15px;
	}
	.collapse.navbar-collapse{
		/* padding: 0; */

		margin-left: -15px;
		margin-right: -15px;
		display: none!important;
	}
	.collapse.navbar-collapse.in{
		display: block!important;
	}
	/* 選單項目 */
	.navbar-nav{
		margin-left: -15px;
		margin-right: -15px;
		float: none;
	}
	.navbar-nav.navbar-right,
	.navbar-form.navbar-left{
		float: none!important;
		display: block;
		padding: 0;
	}
	.nav>li {
	    position: relative;
	    display: block;
	    width: 100%;
	}
	.nav>li>a {
	    position: relative;
	    display: block;
	    padding: 10px 15px;
	}
	.navbar-nav>li>a {
	    line-height: 20px;
	}
	.navbar-form{
		position: relative;
	}
	.navbar-form .form-group{
		display: block;
	}
	.navbar-form .btn{
		position: absolute;
		right: 0;
		top: 0;
		border-radius:0 6px 6px 0; 
	}
	.navbar-form .form-control{
		display: block;
		width: 100%;
	}
}

</style>
</head>
<body>
 <nav class="navbar navbar-default" role="navigation">
 	<div class="container">
 		<div class="navbar-header">
 			<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
 				<span class="sr-only">選單切換</span>
 				<span class="icon-bar"></span>
 				<span class="icon-bar"></span>
 				<span class="icon-bar"></span>
 			</button>
 			<a class="navbar-brand" href="<%=request.getContextPath()%>/backend/index.jsp">回後端首頁</a>
 		</div>
 		
 		<!-- 手機隱藏選單區 -->
 		<div class="collapse navbar-collapse navbar-ex1-collapse">
 			<!-- 左選單 -->
 			<ul class="nav navbar-nav">
 				<li class="active"><a href="#">二手車上下架管理</a></li>
 			</ul>
 		
 			<!-- 搜尋表單 -->
 			<!-- <form class="navbar-form navbar-left" role="search">
 				<div class="form-group">
 					<input type="text" class="form-control" placeholder="請輸入關鍵字">
 				</div>
 				<button type="submit" class="btn btn-default">搜尋</button>
 			</form> -->
 		
 			<!-- 右選單 -->
 			<ul class="nav navbar-nav navbar-right">
 				<li><a href="#">${who} 您好</a></li>
 				<li><a href="<%=request.getContextPath()%>/admin.do?action=logout">登出</a></li>
<!--  				<li><a href="#">個人設定</a></li> -->
<!--  				<li class="dropdown"> -->
<!--  					<a href="#" class="dropdown-toggle" data-toggle="dropdown">繁體中文 <b class="caret"></b></a> -->
<!--  					<ul class="dropdown-menu"> -->
<!--  						<li><a href="#">繁體中文</a></li> -->
<!--  						<li><a href="#">English</a></li> -->
<!--  						<li><a href="#">日本語</a></li> -->
<!--  					</ul> -->
<!--  				</li> -->
 			</ul>
 		</div>
 		<!-- 手機隱藏選單區結束 -->
 	</div>
 </nav>
 <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/SaleOnOff.do" >
       <b><font color=orange>車輛狀態:</font></b>
       <span><select size="1" name="status">
     
       	   <option  ${status == 'all' ? 'selected="selected"' : ''}  value="all">全部
       	   <option ${status == 'secpause' ? 'selected="selected"' : ''} value="secpause">未上架
       	   <option ${status == 'seconsale' ? 'selected="selected"' : ''} value="seconsale">上架中
       	</select></span>
       <input type="submit" value="送出">
       <input type="hidden" name="who" value="${who}">
       <input type="hidden" name="action" value="listMotorByStatus">
     </FORM>
<table border='1' bordercolor='#CCCCFF' width='100%'>
	<tr align='center' valign='middle'>
	    <th style="text-align:center">相片</th>
		<th style="text-align:center">車輛編號</th>
		<th style="text-align:center">車型編號</th>
		<th style="text-align:center">車牌號碼</th>
		<th style="text-align:center">引擎編號</th>
		<th style="text-align:center">出廠日期</th>
		<th style="text-align:center">里程數</th>
		<th style="text-align:center">據點編號</th>
		<th style="text-align:center">狀態</th>
		<th style="text-align:center">註記</th>
		<th style="text-align:center">上/下架</th>
	</tr>
<%-- 					begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>" --%>
	
     <c:if test="${status=='all'}">
       <c:forEach var="motorVO" items="${list}" >
	  <c:if test="${motorVO.status=='secpause' || motorVO.status=='seconsale'}">
		<tr align='center' valign='middle'>
		
		    <td><img src="<%=request.getContextPath()%>/backend/motor_model/mmReader.do?modtype=${motorVO.modtype}"></td>
			<td>${motorVO.motno}</td>
			<td>${motorVO.modtype}</td>
			<td>${motorVO.plateno}</td>
			<td>${motorVO.engno}</td>
			<td><fmt:formatDate value="${motorVO.manudate}" pattern="yyyy年MM月dd日"/>		</td>
			
			<td>${motorVO.mile}</td>	
			<td>${motorVO.locno}</td>	
			<td>${motorVO.status}</td>	
			<td>${motorVO.note}</td>	
			<c:if test="${motorVO.status=='secpause'}">
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/second_order/SecOrd.do">
			     <input type="submit" value="二手車上架"> 
			     <input type="hidden" name="motno" value="${motorVO.motno}">
			     <input type="hidden" name="who" value="${who}">
			     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller--><!-- 目前尚未用到  -->
			     <input type="hidden" name="action"	value="getOneMotor_OnOffSale"></FORM>
			</td>
			</c:if>
			<c:if test="${motorVO.status=='seconsale'}">
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/second_order/SecOrd.do">
			     <input type="submit" value="二手車下架"> 
			     <input type="hidden" name="motno" value="${motorVO.motno}">
			     <input type="hidden" name="who" value="${who}">
			     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller--><!-- 目前尚未用到  -->
			     <input type="hidden" name="action"	value="getOneMotor_OnOffSale"></FORM>
			</td>
			</c:if>
		</tr>
		</c:if>
		
		
	</c:forEach>
     
     
     
     </c:if>
     
     <c:if test="${status=='secpause'}">
           <c:forEach var="motorVO" items="${list}" >
	  <c:if test="${motorVO.status==status}">
		<tr align='center' valign='middle'>
		    <td><img src="<%=request.getContextPath()%>/backend/motor_model/mmReader.do?modtype=${motorVO.modtype}"></td>
			<td>${motorVO.motno}</td>
			<td>${motorVO.modtype}</td>
			<td>${motorVO.plateno}</td>
			<td>${motorVO.engno}</td>
			<td><fmt:formatDate value="${motorVO.manudate}" pattern="yyyy年MM月dd日"/>		</td>
			<td>${motorVO.mile}</td>	
			<td>${motorVO.locno}</td>	
			<td>${motorVO.status}</td>	
			<td>${motorVO.note}</td>	
			
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/second_order/SecOrd.do">
			     <input type="submit" value="二手車上架"> 
			     <input type="hidden" name="motno" value="${motorVO.motno}">
			      <input type="hidden" name="who" value="${who}">
			     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller--><!-- 目前尚未用到  -->
			     <input type="hidden" name="action"	value="getOneMotor_OnOffSale"></FORM>
			</td>
			
		</tr>
		</c:if>
		
		
	</c:forEach>
        
     
     
     </c:if>
     
     <c:if test="${status=='seconsale'}">
           <c:forEach var="motorVO" items="${list}" >
	  <c:if test="${motorVO.status==status}">
		<tr align='center' valign='middle'>
		    <td><img src="<%=request.getContextPath()%>/backend/motor_model/mmReader.do?modtype=${motorVO.modtype}"></td>
			<td>${motorVO.motno}</td>
			<td>${motorVO.modtype}</td>
			<td>${motorVO.plateno}</td>
			<td>${motorVO.engno}</td>
			<td><fmt:formatDate value="${motorVO.manudate}" pattern="yyyy年MM月dd日"/>		</td>
			<td>${motorVO.mile}</td>	
			<td>${motorVO.locno}</td>	
			<td>${motorVO.status}</td>	
			<td>${motorVO.note}</td>	
			
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/second_order/SecOrd.do">
			     <input type="submit" value="二手車下架"> 
			     <input type="hidden" name="motno" value="${motorVO.motno}">
			      <input type="hidden" name="who" value="${who}">
			     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller--><!-- 目前尚未用到  -->
			     <input type="hidden" name="action"	value="getOneMotor_OnOffSale"></FORM>
			</td>
			
		</tr>
		</c:if>
		
		
	</c:forEach>
        
     
     
     </c:if>
     
	
</table>




</body>
	<script src="https://code.jquery.com/jquery.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
</html>