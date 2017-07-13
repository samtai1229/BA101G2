<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="BIG5"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>login</title>
</head>
<body background="<%=request.getContextPath()%>/img/header.jpg"  style= margin:0px;padding:0px;background-size:cover;>

<center>
	<form action="<%=request.getContextPath()%>/loginhandler" method="post">
			<table border=1>
				<tr>
					<td colspan=2>
						<p align=center>
							<b>後端管理系統</b><br> 
					</td>
				</tr>

				<tr>
					<td>
						<p align=right>
							<b>account:</b>
					</td>
					<td>
						<p>
							<input type=text name="account" value="fat" size=15>
					</td>
				</tr>

				<tr>
					<td>
						<p align=right>
							<b>password:</b>
					</td>
					<td>
						<p>
							<input type=password name="password" value="j12345" size=15>
					</td>
				</tr>


				<tr>
					<td colspan=2 align=center>
						
							<input type="submit" value="ok">
						
					</td>
				</tr>
			</table>
	</form>
</center>
<script src="backendlogin.js"></script>
</body>
</html>