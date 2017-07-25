<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.motor.model.*"%>
<%@ page import="com.motor_model.model.*"%>
<%@ page import="java.util.*"%>
<%
 MotorService moSvc = new MotorService();
 List<MotorVO> list = moSvc.getAll();
 MotorModelService mmSvc = new MotorModelService();
 List<MotorModelVO> mlist = mmSvc.getAll();
 pageContext.setAttribute("list", list);
 pageContext.setAttribute("mlist", mlist);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
.activity-card-title{
margin:0 0 10px;
max-height:348px;
font-size:18px;
font-weight:700;
line-height:24px;
overflow:hidden;
text-align: center;
}
form {
    margin: 15px 0 10px;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
<title>二手車交易區</title>
<link rel="stylesheet"
 href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
</head>
<body>


 <nav class="navbar navbar-default" role="navigation">
 <div class="container">
  <div class="navbar-header">
   <button type="button" class="navbar-toggle" data-toggle="collapse"
    data-target=".navbar-ex1-collapse">
    <span class="sr-only">選單切換</span> <span class="icon-bar"></span> <span
     class="icon-bar"></span> <span class="icon-bar"></span>
   </button>
   <a class="navbar-brand" href="#">二手車交易區</a>
  </div>

  <!-- 手機隱藏選單區 -->
  <div class="collapse navbar-collapse navbar-ex1-collapse">
   <!-- 左選單 -->
   <ul class="nav navbar-nav">
    <li class="active"><a href="#">選擇你喜歡的車</a></li>
    <li><a href="<%=request.getContextPath()%>/index.jsp">回首頁</a></li>
   </ul>



   <!-- 右選單 -->
   <c:if test="${not empty memVO}">
    <ul class="nav navbar-nav navbar-right">
     <li><a href="#">${memVO.memname} 您好</a></li>
     <li><a
      href="<%=request.getContextPath()%>/backend/member/member.do?action=logout"
      data-toggle="modal">登出</a></li>
    </ul>
   </c:if>

  </div>
  <!-- 手機隱藏選單區結束 -->
 </div>
 </nav>
 <div class="container">
  <div class="row">
   <c:forEach var="motorVO" items="${list}">
    <c:if test="${ motorVO.status=='seconsale'}">
    <div class="col-xs-12 col-sm-3" >
    <div class="activity-card-title">
     <img width='200' height='180' src="<%=request.getContextPath()%>/backend/motor_model/mmReader.do?modtype=${motorVO.modtype}" >
     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/second_order/SecOrd.do">
      <input class="btn btn-success" type="submit" value="我有興趣">
      <input type="hidden" name="motno" value="${motorVO.motno}">
      <input type="hidden" name="location" value="<%=request.getServletPath()%>">
      <!--送出本網頁的路徑給Controller-->
      <!-- 目前尚未用到  -->
      <input type="hidden" name="action" value="I_WANT_IT">
     </FORM>
     </div>
     </div>
    </c:if>
   </c:forEach>
  </div>
 </div>

 <script src="https://code.jquery.com/jquery.js"></script>
 <script
  src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
</html>