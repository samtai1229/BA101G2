<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page import = "com.motor.model.*" %>
        <%-- 此頁練習採用 EL 的寫法取值 --%>
<%  
// EmpService empSvc = new EmpService();
// List<EmpVO> list = empSvc.getAll();
// pageContext.setAttribute("list",list);
%>
                <jsp:useBean id="listMotors_ByModtype" scope="request" type="java.util.Set" />
				<jsp:useBean id="motorSvc" scope="page" class="com.motor.model.MotorService" />
				<jsp:useBean id="motorModelSvc" scope="page" class="com.motor_model.model.MotorModelService" />
                <html>

                <head>
                    <meta charset="utf-8">
                    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
                    <title>所有機車查詢 - listMotors_ByModtype.jsp</title>
                    <meta name="description" content="">
                    <meta name="keywords" content="">
                    <link href="" rel="stylesheet">
                    <script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
                    <script src="https://code.jquery.com/jquery.js"></script>
                    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
                    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
                    <link rel="stylesheet" href="js/motorMgmtHqSelectPage_css.css">
                    <script src="js/motorMgmtHqSelectPage_css.js"></script>
                </head>

                <body>
                <%--錯誤表列  --%>
<c:if test="${not empty errorMsgs}">
	<font color='red'>請修正以下錯誤:
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li>${message}</li>
		</c:forEach>
	</ul>
	</font>
</c:if>
                            <table class="table table-hover table-condensed table-striped table-bordered">
                                <thead>
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
                                </thead>
                                <c:forEach var="motorVO" items="${listMotors_ByModtype}" >
                                <tr>
                                    <td>${motorVO.motno}</td>
                                    <td>${motorVO.modtype}</td>
                                    <td><!-- ${motorModelVO.name} --></td>
                                    <td><!-- ${motorModelVO.DISPLACEMENT} --></td>
                                    <td><!-- ${motorModelVO.renprice} --></td>
                                    <td>${motorVO.plateno}</td>
                                    <td>${motorVO.engno}</td>
                                    <td>${motorVO.manudate}</td>
                                    <td>${motorVO.mile}</td>
                                    <td>${motorVO.locno}</td>
                                    <td>${motorVO.status}</td>
                                    <td>
                                    <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/motor/motor4H.do">
                                        <input type="submit" name="fix" value="修改" class="btn btn-default" role="button">
                                        <input type="hidden" name="motno" value="${motVO.motno}">
		                                <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
			    						<input type="hidden" name="action"	value="getOne_For_Update">
                                    </FORM>
                                    <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/motor/motor4H.do">
                                        <input type="submit" name="del" value="刪除" class="btn btn-default" role="button">
                                        <input type="hidden" name="motno" value="${motorVO.motno}">
			   					 		<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
			    						<input type="hidden" name="action" value="delete">
                                    </form>
                                    </td>
                                </tr>
                                </c:forEach>
                            </table>

                                    <!-- 頁籤-->
                                    <!-- <li class="active"> = hightlight -->
                                    <div class="text-center">
                                        <ul class="pagination pagination-sm">
                                            <li><a href="#"><i class="glyphicon glyphicon-circle-arrow-left"></i></a></li>
                                            <li><a href="#">1</a></li>
                                            <li><a href="#">2</a></li>
                                            <li><a href="#">3</a></li>
                                            <li><a href="#">4</a></li>
                                            <li><a href="#">5</a></li>
                                            <li><a href="#"><i class="glyphicon glyphicon-circle-arrow-right"></i></a></li>
                                        </ul>
                                    </div>
                        </div>
                    </div>
                </body>

                </html>
