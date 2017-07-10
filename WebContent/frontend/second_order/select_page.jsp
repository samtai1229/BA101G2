<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head><title>BA101G2 二手車訂單: Home</title></head>
<body bgcolor='white'>

<table border='1' cellpadding='5' cellspacing='0' width='400'>
  <tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
    <td><h3>BA101G2 Second Order: Home</h3><font color=red>( MVC )</font></td>
  </tr>
</table>

<p>This is the Home page for BA101G2 Second Order: Home</p>

<h3>二手車訂單查詢:</h3>
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

<ul>
  <li><a href='<%=request.getContextPath()%>/backend/second_order/listAllSecOrd.jsp'>List</a>全部二手車訂單 </li> <br>
  
  <li>
    <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/second_order/SecOrd.do" >
        <b>輸入二手車訂單編號 (如S000001):</b>
        <input type="text" name="sono">
        <input type="submit" value="送出">
        <input type="hidden" name="action" value="getOne_For_Display">
    </FORM>
  </li>

  <jsp:useBean id="soSvc" scope="page" class="com.sec_ord.model.SecOrdService" />
   
 <li>
    <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/second_order/SecOrd.do" >
        <b>輸入會員編號 (如MEM000001):</b>
        <input type="text" name="memno">
        <input type="submit" value="送出">
        <input type="hidden" name="action" value="getOne_For_Display">
    </FORM>
  </li>
   
<h3>新增訂單</h3>   
<ul>
  <li><a href='<%=request.getContextPath()%>/frontend/second_order/addSecOrd.jsp'>Add </a>二手車訂單</li>
</ul>
  
<!-- <h3>車輛管理</h3> -->

<!-- <ul> -->
<%--   <li><a href='<%=request.getContextPath()%>/backend/motor/listAllMotor.jsp'>List </a> All Motors</li> --%>
<!-- </ul> -->






</body>

</html>
