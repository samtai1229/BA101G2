<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.motor.model.*" %>
<%@ page import="com.adminis.model.*" %>
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
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Insert title here</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/google_family_kaushan_script.css" />
 	<link rel="stylesheet" href="<%=request.getContextPath()%>/backend/Modified/jquery_ui_1_10_3_theme.css"/>	
	<link rel="stylesheet" href="<%=request.getContextPath()%>/backend/Modified/twitter_bootstrap_3_3_7_min.css">    
	<link rel="stylesheet" href="<%=request.getContextPath()%>/backend/Modified/backendHP_css.css"> 
	<link rel="stylesheet" href="<%=request.getContextPath()%>/backend/Modified/main.css" >
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

 

<c:if test="<%=adminisVO!=null %>">
    <nav class="navbar navbar-default" role="navigation">
        <!-- logo區 -->
        <a class="navbar-brand page-scroll" href="<%=request.getContextPath()%>/backend/index.jsp" id="navA">AUTOBIKE</a>
        <!-- 左選單 -->
        <ul class="nav navbar-nav">
            <li><a href="#" id="navA">後端管理系統</a></li>
            <!-- 時鐘 -->
            <iframe scrolling="no" frameborder="no" clocktype="html5" style="overflow:hidden;border:0;margin:0;margin-top:5px;padding:0;width:120px;height:40px;" src="http://www.clocklink.com/html5embed.php?clock=004&timezone=CCT&color=yellow&size=120&Title=&Message=&Target=&From=2017,1,1,0,0,0&Color=yellow">
            </iframe>
        </ul>
        <!-- 右選單 -->
        
        <ul class="nav navbar-nav navbar-right">
        
			<li><a href="#" id="navA">哈囉! <%= adminisVO.getName() %></a></li>
			<li><a href="<%=request.getContextPath()%>/admin.do?action=logout" id="navA"><i
					class="glyphicon glyphicon-log-out"></i>登出</a></li>
		</ul>
    </nav>
<%--nav end --%>
    
<!------------------------------- 後端網頁的側邊欄  和權限控管的必要片段程式碼 -->
    <div class="col-xs-12 col-sm-2 leftBar">
     	<img id="menuLogo" src="<%=request.getContextPath()%>/backend/images/android_logo2.jpg">
       		<%if(adminisVO.getAuthno().equals("AC01") || adminisVO.getAuthno().equals("AC07")){%>     
        <button class="accordion accordionMenu accordion accordionMenuMenu">總部管理系統</button> 
        <div class="btn-group-vertical">
            <a class="btn btn-default" href="${pageContext.request.contextPath}/backend/motor/motorMgmtHqSelectPage.jsp"  role="button">車輛管理</a>
            <a class="btn btn-default" href="${pageContext.request.contextPath}/backend/loc_motor_dispatch/motorDispatchMgmtHq.jsp"  role="button">車輛調度管理</a>           
			<a class="btn btn-default" href="<%=request.getContextPath()%>/backend/rent_ord/backendRentOrd.jsp" role="button">租賃單管理</a>
            <a class="btn btn-default" href="${pageContext.request.contextPath}/backend/equipment/emtMgmtSelectPage.jsp" role="button">裝備管理</a>
            <a class="btn btn-default" href="<%=request.getContextPath()%>/backend/location/listAllLocation.jsp" role="button">據點管理</a>
        </div>
       		<%} %><%else {%>
        <div>
        	<button class="accordion accordionMenu accordion accordionMenuMenu" style="background-color:pink;">總部管理系統</button>		
        </div>
       		<%} %>
       		
     	<%if(adminisVO.getAuthno().equals("AC02") || adminisVO.getAuthno().equals("AC07")){%> 
        <button class="accordion accordionMenu">據點管理系統</button>
        <div class="btn-group-vertical">
        	<a class="btn btn-default" href="#" role="button">據點車輛管理</a>
            <a class="btn btn-default" href="<%=request.getContextPath()%>/backend/rent_ord/lease.jsp"  role="button">交車管理</a>
          	<a class="btn btn-default" href="<%=request.getContextPath()%>/backend/rent_ord/return.jsp"  role="button">還車管理</a>
            <a class="btn btn-default" href="${pageContext.request.contextPath}/backend/loc_motor_dispatch/locMotorDispatchApply.jsp" role="button">車輛調度申請</a>
            <a class="btn btn-default" href="${pageContext.request.contextPath}/backend/emt_dispatch/locEmtDispatchApply.jsp" role="button">裝備申請</a>
         </div>
         <%} %><%else {%>
        <div>
        	<button class="accordion accordionMenu accordion accordionMenuMenu" style="background-color:pink;">據點管理系統</button>		
        </div>
       		<%} %>
       		
       		 <%if(adminisVO.getAuthno().equals("AC05") || adminisVO.getAuthno().equals("AC07")){%>
        <button class="accordion accordionMenu">二手車管理系統</button>
        <div class="btn-group-vertical">
       		<a class="btn btn-default" href="<%=request.getContextPath()%>/backend/second_order/SaleOnOff.jsp?who=${admins}" role="button">二手車輛管理</a>
            <a class="btn btn-default" href="<%=request.getContextPath()%>/backend/second_order/listAllSecOrd.jsp" role="button">二手交易管理</a>
<%--             <a class="btn btn-default" href="<%=request.getContextPath()%>/backend/second_order/SaleOnOff.jsp" role="button">二手車交易管理</a> --%>
        </div>
         <%} %><%else {%>
        <div>
        	<button class="accordion accordionMenu accordion accordionMenuMenu" style="background-color:pink;">二手車管理系統</button>		
        </div>
       		<%} %>
       		
       		 <%if(adminisVO.getAuthno().equals("AC03") || adminisVO.getAuthno().equals("AC07")){%>
       <button class="accordion accordionMenu">會員管理系統</button>
        <div class="btn-group-vertical">
       		<a class="btn btn-default" href="<%=request.getContextPath()%>/backend/member/backendMember.jsp" role="button">會員管理</a>
            <a class="btn btn-default" href="<%=request.getContextPath()%>/backend/member/addMember.jsp" role="button">新增會員</a>
         </div>
        <%} %><%else {%>
        <div>
        	<button class="accordion accordionMenu accordion accordionMenuMenu" style="background-color:pink;">會員管理系統</button>		
        </div>
       		<%} %>
       		
       		<%if(adminisVO.getAuthno().equals("AC06") || adminisVO.getAuthno().equals("AC07")){%>
        <button class="accordion accordionMenu">活動企劃管理系統</button>
        <div class="btn-group-vertical">
            <a class="btn btn-default" href="<%=request.getContextPath()%>/backend/mes_board/listAllMesBoard.jsp" role="button">留言版管理</a>
            <a class="btn btn-default" href="<%=request.getContextPath()%>/backend/news/listAllNews.jsp" role="button">最新消息管理</a>
        </div>
        <%} %><%else {%>
        <div>
        	<button class="accordion accordionMenu accordion accordionMenuMenu" style="background-color:pink;">活動企劃管理系統</button>		
        </div>
       		<%} %>
       		
         <%if(adminisVO.getAuthno().equals("AC04") || adminisVO.getAuthno().equals("AC07")){%>
        <button class="accordion accordionMenu">後端管理系統</button>
        <div class="btn-group-vertical">
       		<a class="btn btn-default" href="<%=request.getContextPath()%>/backend/adminis/listAllAdminis.jsp" role="button">後端權限管理</a>
<!--             <a class="btn btn-default" href="#" role="button">推薦景點管理</a> -->
        </div>
         <%} %><%else {%>
        <div>
        	<button class="accordion accordionMenu accordion accordionMenuMenu" style="background-color:pink;">後端管理系統</button>		
        </div>
       		<%} %>
        <div class="btn-group-vertical"></div>
    </div>
</c:if> 
   <div class="col-xs-12 col-sm-10 rightHTML" id="demo">
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
<table class="table table-hover" border='1' bordercolor='#CCCCFF' width='100%'>
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
		
		    <td><img style="width:300px" src="<%=request.getContextPath()%>/backend/motor_model/mmReader.do?modtype=${motorVO.modtype}"></td>
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
		    <td><img style="width:300px" src="<%=request.getContextPath()%>/backend/motor_model/mmReader.do?modtype=${motorVO.modtype}"></td>
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
		    <td><img style="width:300px" src="<%=request.getContextPath()%>/backend/motor_model/mmReader.do?modtype=${motorVO.modtype}"></td>
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
    </div><!-- sm-10 rightHTML  -->

</body>
	<script src="https://code.jquery.com/jquery.js"></script>
	<script src="<%=request.getContextPath()%>/backend/Modified/jquery_1_10_1_min.js"></script>
    <script src="<%=request.getContextPath()%>/backend/Modified/twitter_bootstrap_3_3_7_min.js"></script>
    <script src="<%=request.getContextPath()%>/backend/Modified/motorKanli_js.js"></script>
    <script src="<%=request.getContextPath()%>/backend/Modified/indexNew.js"></script>
</html>