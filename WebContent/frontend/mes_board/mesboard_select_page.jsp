<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head><title>留言板</title></head>
<body bgcolor='white'>

<table border='1' cellpadding='5' cellspacing='0' width='400'>
  <tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
    <td><h3>留言板</h3><font color=red>( MVC )</font></td>
  </tr>
</table>

<h3>資料查詢:</h3>
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
  <li><a href="listAllMesBoard.jsp">List</a> all MesBoard. </li> <br><br>
  
  <li>
    <FORM METHOD="post" ACTION="mesboard.do" >
        <b>輸入留言板編號 (如MB001):</b>
        <input type="text" name="mesno">
        <input type="submit" value="送出">
        <input type="hidden" name="action" value="getOne_For_Display">
    </FORM>
  </li>

   <jsp:useBean id="mesboardSvc" scope="page" class="com.mes_board.model.MesBoardService" />
   
  <li>
     <FORM METHOD="post" ACTION="mesboard.do" >
       <b>選擇留言板編號:</b>
       <select size="1" name="mesno">
         <c:forEach var="mesboardVO" items="${mesboardSvc.all}" > 
          <option value="${mesboardVO.mesno}">${mesboardVO.mesno}
         </c:forEach>   
       </select>
       <input type="submit" value="送出">
       <input type="hidden" name="action" value="getOne_For_Display">
       
    </FORM>
  </li>
</ul>


<h3>最新留言管理</h3>

<ul>
  <li><a href="addMesBoard.jsp">Add</a> a new Mes.</li>
</ul>

</body>

</html>
