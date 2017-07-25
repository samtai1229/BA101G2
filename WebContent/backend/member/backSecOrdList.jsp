<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.sec_ord.model.*"%>
<!-- 後端網頁的側邊欄  和權限控管的必要片段程式碼 -->
<%@ page import="com.adminis.model.*"%>
<%  
	AdminisService as = new AdminisService();
	AdminisVO adminisVO= (AdminisVO)session.getAttribute("adminisVO");
	
	if(adminisVO==null){
		request.getRequestDispatcher("/backend/index.jsp").forward(request, response);
	}else{
		System.out.println("!!!!!!!!!!!"+adminisVO.getName());
	    session.setAttribute("admins", adminisVO.getName());     
	    session.setAttribute("adminisVO", adminisVO);
	}
	
%>
<!-- 後端網頁的側邊欄  和權限控管的必要片段程式碼 -->
<%
	SecOrdService soSvc = new SecOrdService();
    String status = request.getParameter("status");
    String memno =request.getParameter("memno");
    List<SecOrdVO> list = null;
    List<SecOrdVO> list2 = new ArrayList<SecOrdVO>(); 
    if("all".equalsIgnoreCase(status) 
      || status==null)
    	list =  (List<SecOrdVO>) request.getAttribute("list");  
    else	
    {
    	for(SecOrdVO ord :soSvc.getAll(status))
    	{
    		if(ord.getMemno().equals(memno))
    			list2.add(ord);
    	}
    	 list = list2;
    	
    }
    System.out.println("取得參數status:"+status);
	pageContext.setAttribute("list",list);

%>

<html>
<style>
#mamberTable{
	margin:0px;
	background-color: #fff!important;
	font-size:16px!important;
	
}

h3{
font: bold 30px 思源黑體;
 color:#edd14a;
}

table{
	border:1px;
}

</style>
<head>
<title>二手車訂單 -backSecOrdList.jsp</title>
</head>
<body>
<div  class="col-xs-12 col-sm-12">
<table  border='1' cellpadding='5' cellspacing='0' width='100%'>
	<tr bgcolor='#719c92' align='center' valign='middle' height='20'>
		<td>
		<h3>二手車訂單</h3>
	</td>
	</tr>
</table>
</div>
<div class="col-xs-12 col-sm-12">
	<table class="table table-striped"  id="mamberTable">
		<tr>
			<th style="text-align:center">二手車訂單編號</th>
			<th style="text-align:center">會員名稱</th>
			<th style="text-align:center">廠牌型號</th>
			<th style="text-align:center">訂單成立時間</th>
			<th style="text-align:center">訂單狀態</th>
			<th colspan="2" style="text-align:center">操作</th>
		</tr>
		<c:forEach var="soVO" items="${list}" >
			<tr align='center' valign='middle'>
				<td>${soVO.sono}</td>
				<td>${soVO.memno}</td>
				<td>${soVO.motorno}</td>
				<td><fmt:formatDate pattern = "yyyy-MM-dd hh:mm" value = "${soVO.buildtime}" /></td>
				<td>${soVO.status}</td>		
				<td>
				  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/second_order/SecOrd.do">
				     <input type="submit" value="修改"> 
				     <input type="hidden" name="sono" value="${soVO.sono}">
				     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller--><!-- 目前尚未用到  -->
				     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
				</td>
			</tr>
		</c:forEach>
	</table>
</div>
</body>
</html>
