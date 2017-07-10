<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.motor.model.*" %>
<%@ page import="java.util.*" %>
<% MotorService moSvc = new MotorService();
   List<MotorVO> list = moSvc.getAll();
   String status =(String) request.getAttribute("status");
   if(status==null)
   {
	   status="all";
   }
   pageContext.setAttribute("list", list);
   pageContext.setAttribute("status", status);

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
<title>Insert title here</title>
</head>
<body>
 <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/second_order/SaleOnOff.jsp?status=${status}" >
       <b><font color=orange>車輛狀態:</font></b>
       <span><select size="1" name="status">
     
       	   <option  ${status == 'all' ? 'selected="selected"' : ''}  value="all">全部
       	   <option ${status == 'secpause' ? 'selected="selected"' : ''} value="secpause">未上架
       	   <option ${status == 'seconsale' ? 'selected="selected"' : ''} value="seconsale">上架中
       	</select></span>
       <input type="submit" value="送出">
       <input type="hidden" name="action" value="listMotorByStatus">
     </FORM>
<table border='1' bordercolor='#CCCCFF' width='1260'>
	<tr>
		<th>車輛編號</th>
		<th>車型編號</th>
		<th>車牌號碼</th>
		<th>引擎編號</th>
		<th>出廠日期</th>
		<th>Mile</th>
		<th>Locno</th>
		<th>status</th>
		<th>note</th>
	</tr>
<%-- 					begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>" --%>
	
 
	<c:forEach var="motorVO" items="${list}" >
	  <c:if test="${ motorVO.status=='secpause' || motorVO.status=='seconsale'}">
		<tr align='center' valign='middle'>
		
			<td>${motorVO.motno}</td>
			<td>${motorVO.modtype}</td>
			<td>${motorVO.plateno}</td>
			<td>${motorVO.engno}</td>
			<td>${motorVO.manudate}</td>		
			<td>${motorVO.mile}</td>	
			<td>${motorVO.locno}</td>	
			<td>${motorVO.status}</td>	
			<td>${motorVO.note}</td>	
			<c:if test="${motorVO.status=='secpause'}">
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/second_order/SecOrd.do">
			     <input type="submit" value="二手車上架"> 
			     <input type="hidden" name="motno" value="${motorVO.motno}">
			     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller--><!-- 目前尚未用到  -->
			     <input type="hidden" name="action"	value="getOneMotor_OnOffSale"></FORM>
			</td>
			</c:if>
			<c:if test="${motorVO.status=='seconsale'}">
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/second_order/SecOrd.do">
			     <input type="submit" value="二手車下架"> 
			     <input type="hidden" name="motno" value="${motorVO.motno}">
			     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller--><!-- 目前尚未用到  -->
			     <input type="hidden" name="action"	value="getOneMotor_OnOffSale"></FORM>
			</td>
			</c:if>
		</tr>
		</c:if>
	</c:forEach>
</table>




</body>
</html>