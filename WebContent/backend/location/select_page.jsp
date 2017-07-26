<%@page import="java.sql.Timestamp"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*" %>
<html>
<head>
	<meta caharset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	
	<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
	<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>	
	<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css"/>	
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
	<%-- <link rel="stylesheet" href="<%=request.getContextPath()%>/backend/Modified/backendHP_css.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/backend/Modified/main.css" > --%>
	<title>據點管理</title>
</head>

<body>

	<nav class="navbar navbar-default" role="navigation">
        <div class="container">
	        <!-- logo區 -->
	        <a class="navbar-brand" href="<%=request.getContextPath()%>/backend/location/select_page.jsp" id="navA">AUTOBIKE</a>
	        <!-- 左選單 -->
	        <ul class="nav navbar-nav">
	            <li><a href="listAllLocation.jsp">查詢全部據點</a></li>
	        	<li><a href="addLocation.jsp">新增據點</a></li>
	        </ul>
        </div>
    </nav>


${errorMsgs}

  <div class="container">
<h2>資料查詢:</h2>
<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font color='red'>請修正以下錯誤:
	
		<c:forEach var="message" items="${errorMsgs}">
			<li>${message}</li>
		</c:forEach>

	</font>
</c:if>

<ul>
<!--   <li><a href='listAllLocation.jsp'>List</a> all Locations<font color=blue>(byDAO).</font> </li> -->
<!--   <li><a href='location.do?action=getAll'>List</a>  all Locations<font color=blue>(getFromSession).</font> </li><br><br> -->
  <li>
    <FORM METHOD="post" ACTION="location.do" >
        <b>輸入據點編號 (如TPE00):</b>
        <input type="text" name="locno">
        <input type="submit" value="送出"><font color=blue>(資料格式驗證  by Controller ).</font> 
        <input type="hidden" name="action" value="getOne_For_Display">
    </FORM>
  </li>
  
  <!-- <li>
    <FORM METHOD="post" ACTION="location.do" name="form1">
        <b>輸入據點編號 (如TPE00):</b>
        <input type="text" name="locno">
        <input type="button" value="送出" onclick="fun1()"><font color=blue>(資料格式驗證  by Java Script).</font> 
        <input type="hidden" name="action" value="getOne_For_Display">
    </FORM>
  </li> -->

  <jsp:useBean id="dao" scope="page" class="com.location.model.LocationDAO" />
   
  <li>
     <FORM METHOD="post" ACTION="location.do" >
       <b>選擇據點編號:</b>
       <select size="1" name="locno">
         <c:forEach var="locationVO" items="${dao.all}" > 
          <option value="${locationVO.locno}">${locationVO.locno}
         </c:forEach>   
       </select>
       <input type="submit" value="送出">
       <input type="hidden" name="action" value="getOne_For_Display">
    </FORM>
  </li>
  
  <li>
     <FORM METHOD="post" ACTION="location.do" >
       <b>選擇據點名稱:</b>
       <select size="1" name="locno">
         <c:forEach var="locationVO" items="${dao.all}" > 
          <option value="${locationVO.locno}">${locationVO.locname}
         </c:forEach>   
       </select>
       <input type="submit" value="送出">
       <input type="hidden" name="action" value="getOne_For_Display">
     </FORM>
  </li>
</ul>
<script>    
   function fun1(){
      with(document.form1){
         if (locno.value=="") 
             alert("請輸入員工編號!");
         else if (isNaN(locno.value)) 
             alert("員工編號格式不正確!");
        
         else
             submit();
      }
   }
</script>
<h3>據點管理</h3>

<ul>
  <li><a href='addLocation.jsp'>Add</a> a new Location.</li>
</ul>
</div>
</body>
</body>

</html>
