<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.member.model.*"%>
<%@ page import="com.rent_ord.model.*"%>
<%@ page import="com.emt_list.model.*"%>
<%@ page import="com.equipment.model.*"%>
<%-- �����m�߱ĥ� EL ���g�k���� --%>

<%
	RentOrdService roSvc = new RentOrdService();
    MemberService mSvc = new MemberService(); 
    EmtListService emtlistSvc = new EmtListService();
    EquipmentService equipSvc = new EquipmentService();
	String memno = (String)request.getAttribute("memno");
	List<RentOrdVO> list = roSvc.getAll();
	pageContext.setAttribute("mems",mSvc.getAll());
	pageContext.setAttribute("emtlist",emtlistSvc.getAll());
	pageContext.setAttribute("equips",equipSvc.getAll());
	pageContext.setAttribute("list",list);
	pageContext.setAttribute("memno",memno);
	
%>

<html>
<head>
<title>�ڪ������</title>
<style type="text/css">

.custab{
    border: 1px solid #ccc;
    padding: 5px;
    margin: 5% 0;
    box-shadow: 3px 3px 2px #ccc;
    transition: 0.5s;
    }
.custab:hover{
    box-shadow: 3px 3px 0px transparent;
    transition: 0.5s;
    }

</style>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
</head>
<body bgcolor='white'>
<div  class="col-xs-12 col-sm-12">
<table  border='1' cellpadding='5' cellspacing='0' width='100%'>
	<tr  bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
		<td>
		<h3>�ڪ������ </h3>
	</td>
	</tr>
</table>
</div>
<div  class="col-xs-12 col-sm-12">
<table border='1'  bordercolor='#CCCCFF' class="table table-striped">
	
	<tr>
	
		<th style="text-align:center">�����s��</th>
		<th style="text-align:center">�|���s��</th>
		<th style="text-align:center">��������</th>
		<th style="text-align:center">�|���W��</th>
		<th style="text-align:center">�������I</th>
		<th style="text-align:center">�٨����I</th>
		<th style="text-align:center">�_�l���{</th>
		<th style="text-align:center">��p���{</th>
		<th style="text-align:center">�U����</th>
		<th style="text-align:center">�q��}�l�ɶ�</th>
		<th style="text-align:center">�q�浲���ɶ�</th>
		<th style="text-align:center">�٨��ɶ�</th>
		<th style="text-align:center">�@��</th>
		<th style="text-align:center">�`���B</th>
		<th style="text-align:center">RANK</th>
		<th style="text-align:center">���A</th>
		<th style="text-align:center">���O</th>
		<th style="text-align:center">�˳ƽs��</th>
	</tr>
	
<%-- 	begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>" --%>
	<c:forEach var="roVO" items="${list}" >
	 <c:if test="${memno==roVO.memno}">
		<tr align='center' valign='middle'>
			<td>${roVO.rentno}</td>
			<td>${roVO.memno}</td>
			<td>${roVO.motorVO.motno}</td>
		<c:forEach var="mem" items="${mems}" >	
		  <c:if test="${mem.memno==roVO.memno}">
		     <td>${mem.memname}</td>
		  </c:if>
			</c:forEach>
			<td>${roVO.slocno}</td>
			<td>${roVO.rlocno}</td>
			<td>${roVO.milstart}</td>	
			<td>${roVO.milend}</td>	
			<td><fmt:formatDate pattern = "yyyy�~MM��dd��" 
         value = "${roVO.filldate}" /></td>
			<td><fmt:formatDate pattern = "yyyy�~MM��dd��" 
         value = "${roVO.startdate}" /></td>
<td><fmt:formatDate pattern = "yyyy�~MM��dd��" 
         value = "${roVO.enddate}" /></td>
			
<td><fmt:formatDate pattern = "yyyy�~MM��dd��" 
         value = "${roVO.returndate}" /></td>				
<td>${roVO.fine}</td>
			<td>${roVO.total}</td>
			<td>${roVO.rank}</td>
			<td>${roVO.status}</td>
			<td>${roVO.note}</td>
			<c:forEach var="emtlistVO" items="${emtlist}">
			 
			    <c:if test="${roVO.rentno==emtlistVO.rentno}">
			     <c:forEach var="equipVO" items="${equips}">
			       <c:if test="${emtlistVO.emtno==equipVO.emtno}">
			       
			        
			          <td><c:out value="${equipVO.emtno}" ></c:out></td> 
			       </c:if>
			      
			      </c:forEach>
			    </c:if>
			      
			 
			</c:forEach>
			
<!-- 			<td> -->
<%-- 			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/member/member.do"> --%>
<!-- 			     <input type="submit" value="�ק�">  -->
<%-- 			     <input type="hidden" name="memno" value="${roVO.memno}"> --%>
<%-- 			     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--�e�X�����������|��Controller--><!-- �ثe�|���Ψ�  --> --%>
<!-- 			     <input type="hidden" name="action"	value="getOne_For_Update"></FORM> -->
<!-- 			</td> -->
<!-- 			<td> -->
<%-- 			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/backend/member/member.do"> --%>
<!-- 			    <input type="submit" value="�R��"> -->
<%-- 			    <input type="hidden" name="memno" value="${memVO.memno}"> --%>
<%-- 			    <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--�e�X�����������|��Controller--> --%>
<!-- 			    <input type="hidden" name="action"value="delete"></FORM> -->
<!-- 			</td> -->
		</tr>
		</c:if>
	</c:forEach>
</table>
<%-- <%@ include file="pages/page2.file" %> --%>

<br>�����������|:<br><b>
   <font color=blue>request.getServletPath():</font> <%= request.getServletPath()%><br>
   <font color=blue>request.getRequestURI(): </font> <%= request.getRequestURI()%> </b>

</div>


</body>
<script src="https://code.jquery.com/jquery.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
</html>
