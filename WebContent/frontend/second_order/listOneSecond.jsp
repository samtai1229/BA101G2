<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.motor.model.*"%>
<%@ page import="com.motor_model.model.*"%>
<%@ page import="com.member.model.*"%>
<%@ page import="java.util.*"%>
<%
 MotorVO motorVO = (MotorVO) request.getAttribute("motorVO");
 MotorModelVO mmVO = (MotorModelVO) request.getAttribute("mmVO");
 String memno = (String) session.getAttribute("memno");
 String memname = (String) session.getAttribute("memname");
 MemberService memSvc = new MemberService();
 MemberVO memVO = memSvc.getOneMember(memno);
 pageContext.setAttribute("memVO", memVO);
 pageContext.setAttribute("motorVO", motorVO);
 pageContext.setAttribute("mm", mmVO);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<style>
.motoimg {
 margin: 80px;
}

.buycar {
 text-align: center;
 margin: 20px;
}

.intro {
 margin: 220px 0;
 margin-bottom: 110px;
}
img{
    margin-left: 40px;
}
</style>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
 content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
<title>Title Page</title>

<!--[if lt IE 9]>
   <script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js"></script>
   <script src="https://cdnjs.cloudflare.com/ajax/libs/respond.js/1.4.2/respond.min.js"></script>
  <![endif]-->
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
   <div class="col-xs-12 col-sm-6">
    <div class="motoimg"></div>
    <img
     src="<%=request.getContextPath()%>/backend/motor_model/mmReader.do?modtype=${motorVO.modtype}"
     width='500' height='375'>
    <div class="col-xs-12 col-sm-12">
     <div style="text-align: center;margin-bottom: 40px;"><h2>${mm.brand} - ${mm.name}</h2></div>
     
    </div> 
   </div>

   <div class="col-xs-12 col-sm-6">

    <div class="intro">${mm.intro}</div>
    <div style="text-align: center;margin-bottom"><h3>售價：${mm.saleprice}</h3></div> 
    <div class="buycar">
     <FORM METHOD="post"
      ACTION="<%=request.getContextPath()%>/frontend/second_order/SecOrd.do">
      <input class="btn-lg btn-success" type="submit" value="我要買">
      <input type="hidden" name="motno" value="${motorVO.motno}">
      <input type="hidden" name="memno" value="${memVO.memno}">
      <input type="hidden" name="location"
       value="<%=request.getServletPath()%>">
      <!--送出本網頁的路徑給Controller-->
      <!-- 目前尚未用到  -->
      <input type="hidden" name="action" value="DEAL">
     </FORM>
    </div>
   </div>
   <div class="col-xs-12 col-sm-12">
   
   
   </div>
  </div>
 </div>
 </div>


 <script src="https://code.jquery.com/jquery.js"></script>
 <script
  src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
</html>