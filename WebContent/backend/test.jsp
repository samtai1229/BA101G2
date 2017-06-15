<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<link href="../../Modified/main.css" rel="stylesheet">
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<script src="Modified/new.js"></script>
<title></title>
</head>
<body>


	<!--block1 -->
	<div id="block1" class="block">
		<fieldset>
			<!--form功能 新增  -->
			<legend>新增車輛</legend>
			<form method="post" action="motor.do">
				<div class="InputForm">
					<label class="title">車輛型號</label> 
						<select name="modtype">
<%-- 							<c:forEach var="mmVO" items="${motorModelSvc.all}">
								<option value="${mmVO.modtype}">
									${mmVO.brand} ${mmVO.name}
								</option>
							</c:forEach> --%>
						</select><br />
				</div>
				<div class="InputForm">
					<label class="title">車牌號碼</label><input type="text"
						name="plateno" maxlength="8" placeholder="計8位字元，第4碼為 - 號" /><br>
				</div>
				<div class="InputForm">
					<label class="title">引擎號碼</label><input type="text"
						name="plateno" maxlength="15" placeholder="請輸入引擎號碼" /><br>
				</div>
				<div class="InputForm">
					<label class="title">出廠日期:</label><input type="text" name="manudate"
						id="datepicker" /><br>
				</div>
				
				<div class="InputForm">
					<label class="title">里程數</label><input type="text" name="engno"/><br>
				</div>
								
				<div class="InputForm">
					<label class="title">據點編號</label><input type="text"
						name="locno" maxlength="10" placeholder="等有據點DAO再用選單" /><br>
				</div>
				<div class="InputForm">
					<label class="title">車輛狀態</label> 
						<select name="status">
								<option value="unleasable">不可租
								<option value="leasable">可出租
								<option value="dispatching">調度中
								<option value="secpause">二手未上架
								<option value="secreserved">二手已預約
								<option value="other">其它(備註)								
						</select><br />
				</div>								
				<div class="InputForm">
					<label class="title">備註:</label>
					<textarea name="note" rows="1" cols="20" placeholder="請在這裡輸入"></textarea>
					<br />
				</div>
				<div class="InputForm">
					<input type="hidden" name="action" value="insert">
					<input type="submit" value="submit" class="click" /> 
					<input type="reset" name="reset" value="reset" class="click" />
				</div>

			</form>
			<!--end: form功能 新增 -->
		</fieldset>
	</div>
	<!--end block1 -->

	<!--block2 -->
	<div id="block2" class="block">

		<!--form功能 修改  -->

		<fieldset>
			<legend>修改資料</legend>
			<form method="post" action="motor.do">
				<div class="InputForm">
					<label class="title">車輛編號</label> 
						<select name="modno">
<%-- 							<c:forEach var="motorVO" items="${motorlSvc.all}">
								<option value="${motorVO.motno}">
									${motorVO.motno}
								</option>
							</c:forEach> --%>
						</select><br />
				</div>			
				<div class="InputForm">
					<label class="title">里程更新</label><input type="text" name="engno"/><br>
				</div>			
				<div class="InputForm">
					<label class="title">據點編號</label><input type="text"
						name="locno" maxlength="10" placeholder="等有據點DAO再用選單" /><br>
				</div>	
				<div class="InputForm">
					<label class="title">車輛狀態</label> 
						<select name="status">
								<option value="unleasable">不可租
								<option value="leasable">出租已上架
								<option value="reserved">出租已預約
								<option value="occupied">出租中								
								<option value="dispatching">調度中
								<option value="secpause">二手車未上架
								<option value="seconsale">二手車上架中
								<option value="secreserved">二手車已預約
								<option value="secsaled">二手車已售出
								<option value="other">其它(備註)								
						</select><br />
				</div>	
				<div class="InputForm">
					<label class="title">備註:</label>
					<textarea name="note" rows="1" cols="20" placeholder="請在這裡輸入"></textarea>
					<br />
				</div>
				<div class="InputForm">
					<input type="hidden" name="action" value="update">
					<input type="submit" value="submit" class="click" /> 
				</div>			
			</form>
		</fieldset>

		<!--end: form功能 修改 -->


		<fieldset>
			<legend>刪除資料</legend>
			<!--form功能 刪除  -->
			<form method="post" action="motor.do">
				<div class="InputForm">
					<label class="title">待刪車輛</label> 
						<select name="modno">
 							<c:forEach var="motorVO" items="${motorlSvc.all}">
								<option value="${motorVO.motno}">
									${motorVO.motno}
								</option>
							</c:forEach> 
						</select><br />
				</div>
				<div class="InputForm">
					<input type="hidden" name="action" value="delete">
					<input type="submit" value="submit" class="click" /> 
				</div>	
			</form>
		</fieldset>
	</div>
	<!--end: block2 -->
	<!--block3 -->



		<!-- 查詢 -->
	<jsp:useBean id="motorSvc" scope="page" class="com.motor.model.MotorService"/>

	<div id="block3" class="block">
	<table id="QueryTable">
	  <thead>	
		<tr class="QueryTable_TR">
			<th>車輛編號</th>
			<th>車型編號</th>		
			<th>車牌號碼</th>
			<th>引擎號碼</th>				
			<th>出廠日期</th>
			<th>累計里程</th>
			<th>據點編號</th>
			<th>車輛狀態</th>				
			<th>備註</th>
		</tr>
	  </thead>
	  <tbody><!-- notice: javaBean offered from index.jsp, line10 -->			
<%--  		<c:forEach var="MotorVO" items="${motorSvc.all}">
			<tr class="QueryTable_TR">
				<td>${MotorVO.motno}</td>
				<td>${MotorVO.modtype}</td>
				<td>${MotorVO.plateno}</td>
				<td>${MotorVO.engno}</td>
				<td>${MotorVO.manudate}</td>
				<td>${MotorVO.mile}</td>
				<td>${MotorVO.locno}</td>
				<td>${MotorVO.status}</td>
				<td>${MotorVO.note}</td>							
			</tr>
		</c:forEach> --%>
	  </tbody>	 										
	</table>
	<script src="../../Modified/QueryTablePagination.js"></script> 
	</div>
	<!--end: block3 -->
</body>
</html>