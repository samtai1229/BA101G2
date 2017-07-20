<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="BIG5"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src= ckeditor/ckeditor.js></script>
<title>Insert title here</title>
</head>
<body>
	
	<form name ='form' action ='#' method='post'>
            <textarea name="content" id="content" rows="10" cols="80"></textarea>
            <script>
				CKEDITOR.replace( 'content', { width: 1000, });
			</script>
            
            <input type = 'button' value = '°e¥X' onclick = 'processData()'>
        </form>
	
	
</body>
</html>