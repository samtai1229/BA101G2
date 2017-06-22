<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.motor.model.*"%>
<%
	MotorVO motorVO = (MotorVO) request.getAttribute("motorVO"); //MotorServlet.java (Controller), 存入req的motorVO物件 (包括幫忙取出的motorVO, 也包括輸入資料錯誤時的motorVO物件)
%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>機車資料修改 - updateMotorInput.jsp</title>

<meta name="keywords" content="">
<link href="" rel="stylesheet">
<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<script src="https://code.jquery.com/jquery.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/backend/motor/js/motorMgmtHqSelectPage_css.css">
</head>

<body>

	<table>
		<tr>
			<td><h3>車輛資料修改 - updateMotorInput.jsp</h3></td>
		</tr>
	</table>

	<h3>資料修改:</h3>
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

	<FORM METHOD="post" ACTION="motor4H.do" name="formUpdate">
		<table>
			<tr>
				<td>車輛編號:<font color=red><b>*</b></font></td>
				<td>${motorVO.motno}</td>
			</tr>
			<tr>
				<td>車牌號碼:</td>
				<td><input type="TEXT" name="plateno" size="45"
					value="<%=motorVO.getPlateno()%>" /></td>
			</tr>
			<tr>
				<td>引擎編號:</td>
				<td><input type="TEXT" name="engno" size="45"
					value="<%=motorVO.getEngno()%>" /></td>
			</tr>
			<tr>
				<td>出廠日期:</td>
				<td><input class="cal-TextBox" onFocus="this.blur()" size="9"
					readonly type="text" name="manudate"
					value="<%=motorVO.getManudate()%>"> <a class="so-BtnLink"
					href="javascript:calClick();return false;"
					onmouseover="calSwapImg('BTN_date', 'img_Date_OVER',true);"
					onmouseout="calSwapImg('BTN_date', 'img_Date_UP',true);"
					onclick="calSwapImg('BTN_date', 'img_Date_DOWN');showCalendar('formUpdate','manudate','BTN_date');return false;">
						<img align="middle" border="0" name="BTN_date"
						src="images/btn_date_up.gif" width="22" height="17" alt="開始日期">
				</a></td>
			</tr>
			<tr>
				<td>里程數:</td>
				<td><input type="TEXT" name="mile" size="45"
					value="<%=motorVO.getMile()%>" /></td>
			</tr>
			<tr>
				<td>所在地:</td>
				<td><input type="TEXT" name="locno" size="45"
					value="<%=motorVO.getLocno()%>" /></td>
			</tr>

			<jsp:useBean id="motorModelSvc" scope="page"
				class="com.motor_model.model.MotorModelService" />
			<tr>
				<td>車型編號:<font color=red><b>*</b></font></td>
				<td><select size="1" name="modtype">
						<c:forEach var="motorModelVO" items="${motorModelSvc.all}">
							<option value="${motorModelVO.modtype}"
								${(motorVO.modtype==motorModelVO.modtype)?'selected':'' }>${motorModelVO.brand}─
								${motorModelVO.name}
						</c:forEach>
				</select></td>
			</tr>
			<tr>
				<td>狀態:</td>
				<td><select size="1" name="status">
						<option value="unleasable">禁止出租</option>
						<option value="leasable">可出租</option>
						<option value="reserved">已預約</option>
						<option value="occupied">出租中</option>
						<option value="dispatching">調度中</option>
						<option value="seconsale">二手車；上架中</option>
						<option value="secpause">二手車；未上架</option>
						<option value="secreserved">二手車；已預約</option>
						<option value="secsaled">二手車；已售出</option>
						<option value="other">二手車；其他</option>
				</select></td>
			</tr>
			<tr>
				<td>備註:</td>
				<td><input type="TEXT" name="note" size="45"
					value="<%=motorVO.getNote()%>" /></td>
			</tr>

		</table>
		<br> <input type="hidden" name="action" value="update"> <input
			type="hidden" name="motno" value="<%=motorVO.getMotno()%>"> <input
			type="hidden" name="requestURL"
			value="<%=request.getParameter("requestURL")%>">
		<!--接收原送出修改的來源網頁路徑後,再送給Controller準備轉交之用-->
		<input type="hidden" name="whichPage"
			value="<%=request.getParameter("whichPage")%>">
		<!--只用於:listAllMotor.jsp-->
		<input type="submit" value="送出修改">
	</FORM>

<br>送出修改的來源網頁路徑:<br><b>
   <font color=blue>request.getParameter("requestURL"):</font> <%= request.getParameter("requestURL")%><br>
   <font color=blue>request.getParameter("whichPage"):</font> <%= request.getParameter("whichPage")%> (此範例目前只用於:istAllEmp.jsp))</b>

</body>
</html>
</body>
</html>