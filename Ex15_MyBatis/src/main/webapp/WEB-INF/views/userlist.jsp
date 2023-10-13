<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	out.println("MyBatis : Hello World");
%>
<br>
	<c:forEach items="${users}" var="dto">
		${dto.id} / ${dto.name}<br>
	</c:forEach>
</body>
</html>