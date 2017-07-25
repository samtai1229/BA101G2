<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.sec_ord.model.*"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>

<%
	SecOrdService soSvc = new SecOrdService();
    String status = request.getParameter("status");
    String status2 = request.getParameter("status");
    List<SecOrdVO> list = null;
    if("all".equalsIgnoreCase(status) 
      || "all".equalsIgnoreCase(status2)
      || status==null
      || status2==null)
    	list = soSvc.getAll();  
    else	
    {
    
    	 list = soSvc.getAll(status);
    	if(status!=null)
			list = soSvc.getAll(status2);
    	
    }
    System.out.println("取得參數status:"+status);
    System.out.println("取得參數status2:"+status2);
	pageContext.setAttribute("list",list);

%>

<html>
<head>
<title>所有二手車訂單資料</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
</head>
<body bgcolor='white'>
<nav class="navbar navbar-default" role="navigation">
	 	<div class="container">
	 		<div class="navbar-header">
	 			<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
	 				<span class="sr-only">選單切換</span>
	 				<span class="icon-bar"></span>
	 				<span class="icon-bar"></span>
	 				<span class="icon-bar"></span>
	 			</button>
	 			<a class="navbar-brand" href="#">二手車訂單資料</a>
	 		</div>
	 		
	 		<!-- 手機隱藏選單區 -->
	 		<div class="collapse navbar-collapse navbar-ex1-collapse">
	 			<!-- 左選單 -->
	 			<ul class="nav navbar-nav">
	 				<li class="active"><a href="#">查詢特定訂單</a></li>
	 				<li><a href="<%=request.getContextPath()%>/backend/index.jsp">回首頁</a></li>
	 			</ul>
	 		
	 			
	 		
	 			<!-- 右選單 -->
	 			<c:if test="${not empty adminisVO}">
	 			<ul class="nav navbar-nav navbar-right">
	 				<li><a href="#">${adminisVO.name} 您好</a></li>
	 				<li><a href="<%=request.getContextPath()%>/backend/member/member.do?action=logout"
              								data-toggle="modal">登出</a></li>
	 			</ul>
	 			</c:if>
	 			
	 		</div>
	 		<!-- 手機隱藏選單區結束 -->
	 	</div>
	 </nav> 
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
<%@ include file="pages/page1.file" %> 
    <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/second_order/SecOrd.do" >
       <b><font color=orange>訂單狀態:</font></b>
       <span><select size="1" name="status">
     
       	   <option  ${status == 'all' ? 'selected="selected"' : ''}  value="all">全部
       	   <option ${status == 'unpaid' ? 'selected="selected"' : ''} value="unpaid">未付款
       	   <option ${status == 'paid' ? 'selected="selected"' : ''} value="paid">已付款
       	   <option ${status == 'closed' ? 'selected="selected"' : ''} value="closed">已結單
      
       	</select></span>
       <input type="submit" value="送出">
       <input type="hidden" name="action" value="listSecOrd_ByStatus">
     </FORM>
      <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/second_order/SecOrd.do" >
       <b><font color=orange>會員編號:</font></b>
       <input type="text" name="memno" placeholder="會員編號">
       <input type="submit" value="送出">
       <input type="hidden" name="action" value="getAll_For_Display_By_Memno">
     </FORM>
      <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/second_order/SecOrd.do" >
       <b><font color=orange>會員姓名:</font></b>
       <input type="text" name="memname" placeholder="會員姓名">
       <input type="submit" value="送出">
       <input type="hidden" name="action" value="getAll_For_Display_By_Memname">
     </FORM>
     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/second_order/SecOrd.do" >
       <b><font color=orange>訂單編號:</font></b>
       <input type="text" name="memname" placeholder="訂單編號">
       <input type="submit" value="送出">
       <input type="hidden" name="action" value="getAll_For_Display_By_Sono">
     </FORM>
<table  border="1" class="table table-striped table-inverse table-hover" bordercolor='#CCCCFF' width='100%'>
	<tr >
		<th style="text-align:center">二手車訂單編號</th>
		<th style="text-align:center">會員編號</th>
		<th style="text-align:center">會員姓名</th>
		<th style="text-align:center">廠牌型號</th>
		<th style="text-align:center">訂單成立時間</th>
		<th style="text-align:center">訂單狀態</th>
		<th style="text-align:center" colspan="2">操作</th>
	</tr>
	<jsp:useBean id="memSvc" scope="page" class="com.member.model.MemberService"></jsp:useBean>
<%-- 					begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>" --%>
	<c:forEach var="soVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>" >
		<tr align='center' valign='middle'>
			<td>${soVO.sono}</td>
			<td>${soVO.memno}</td>
		    <c:forEach var="memVO" items="${memSvc.all}">
		       <c:if test="${memVO.memno==soVO.memno}">
		        <td>${memVO.memname}</td>
		       </c:if>
		    </c:forEach>
			<td>${soVO.motorno}</td>
			<td><fmt:formatDate pattern = "yyyy-MM-dd hh:mm" 
         value = "${soVO.buildtime}" /></td>
          <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/second_order/SecOrd.do">
			<td><select  name="Order_Status">
	       	   <option ${soVO.status == 'unpaid' ? 'selected="selected"' : ''} value="unpaid">未付款
	       	   <option ${soVO.status == 'paid' ? 'selected="selected"' : ''} value="paid">已付款
	       	   <option ${soVO.status == 'closed' ? 'selected="selected"' : ''} value="closed">已交車
			    </select></td>		 
			
			<td>
			 
			     <input type="submit" value="修改"> 
			     <input type="hidden" name="sono" value="${soVO.sono}">
			     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller--><!-- 目前尚未用到  -->
			     <input type="hidden" name="action"	value="getOne_For_Update">
			</td>
			</FORM>
<!-- 			<td> -->
<%-- 			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/second_order/SecOrd.do"> --%>
<!-- 			    <input type="submit" value="刪除"> -->
<%-- 			    <input type="hidden" name="sono" value="${soVO.sono}"> --%>
<%-- 			    <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller--> --%>
<!-- 			    <input type="hidden" name="action"value="delete"></FORM> -->
<!-- 			</td> -->
		</tr>
	</c:forEach>
</table>
<%@ include file="pages/page2.file" %>

<!-- <br>本網頁的路徑:<br><b> -->
<%--    <font color=blue>request.getServletPath():</font> <%= request.getServletPath()%><br> --%>
<%--    <font color=blue>request.getRequestURI(): </font> <%= request.getRequestURI()%> </b> --%>
<script src="https://code.jquery.com/jquery.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>

</html>
