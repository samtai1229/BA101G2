<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.motor.model.*"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>

<jsp:useBean id="motorSvc" scope="page"
	class="com.motor.model.MotorService" />

<!DOCTYPE html>
<html lang="">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
<title>所有車輛資料- listAllMotor.jsp</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/backend/motor/js/motorMgmtHqSelectPage_css.css">
<script
	src="${pageContext.request.contextPath}/backend/motor/js/motorMgmtHqSelectPage_js.js"></script>
</head>
<body>
	<%-- 錯誤列表 --%>
	<c:if test="${not empty errorMsgs}">
		<font color='red'>請修正以下錯誤:
			<ul>
				<c:forEach var="message" items="${errorMsgs}">
					<li>${message}</li>
				</c:forEach>
			</ul>
		</font>
	</c:if>
	<table
		class="table table-hover table-condensed table-striped table-bordered">
		<thead>
		<tr>
			<td>車輛編號</td>
			<td>車輛型號</td>
			<td>車輛名稱</td>
			<td>排氣量</td>
			<td>租賃價格</td>
			<td>車牌號碼</td>
			<td>引擎號碼</td>
			<td>出廠日期</td>
			<td>里程數</td>
			<td>所在據點</td>
			<td>狀態</td>
			<td>修改/刪除</td>
		</tr>
		</thead>
		<c:forEach var="motorVO" items="${motorSvc.all}">

			<tr>
				<!-- <jsp:useBean id="motorModelSvc" scope="page" class="com.motor_model.model.MotorModelService" /> -->
				<td>${motorVO.motno}</td>
				<td>${motorVO.modtype}</td>

				<td><c:forEach var="motorModelVO" items="${motorModelSvc.all}">
						<c:if test="${motorVO.modtype==motorModelVO.modtype}">${motorModelVO.name}
                    	</c:if>
					</c:forEach></td>
				<td><c:forEach var="motorModelVO" items="${motorModelSvc.all}">
						<c:if test="${motorVO.modtype==motorModelVO.modtype}">${motorModelVO.displacement}c.c.
                   		</c:if>
					</c:forEach></td>
				<td><c:forEach var="motorModelVO" items="${motorModelSvc.all}">
						<c:if test="${motorVO.modtype==motorModelVO.modtype}">${motorModelVO.renprice}/日
                    	</c:if>
					</c:forEach></td>
				<td>${motorVO.plateno}</td>
				<td>${motorVO.engno}</td>
				<td>${motorVO.manudate}</td>
				<td>${motorVO.mile}</td>
				<td>${motorVO.locno}</td>
				<td>${motorVO.status}</td>

				<td >
					<FORM METHOD="post"  style="display: inline;"
						ACTION="<%=request.getContextPath()%>/backend/motor/motor4H.do" >
						<input type="submit" name="fix" value="修改" class="btn btn-default"
							role="button"> <input type="hidden" name="motno"
							value="${motorVO.motno}"> <input type="hidden"
							name="requestURL" value="<%=request.getServletPath()%>">
						<input type="hidden" name="action" value="getOne_For_Update">
					</FORM>
				
					<FORM METHOD="post" style="display: inline;"
						ACTION="<%=request.getContextPath()%>/backend/motor/motor4H.do">
						<input type="submit" name="del" value="刪除" class="btn btn-default"
							role="button"> <input type="hidden" name="motno"
							value="${motorVO.motno}"> <input type="hidden"
							name="requestURL" value="<%=request.getServletPath()%>">
						<!--送出本網頁的路徑給Controller-->
						<input type="hidden" name="action" value="delete">
					</FORM>
				</td>
			</tr>
		</c:forEach>
	</table>


	<script src="https://code.jquery.com/jquery.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>


</body>
</html>