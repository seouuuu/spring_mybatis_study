<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
	<h2>로그인</h2>
	<c:if test="${re==1 }">
		로그인되었습니다.
	</c:if>
	<c:if test="${re==0 }">
		비밀번호가 틀렸습니다.
	</c:if>
	<c:if test="${re==-1 }">
		아이디가 존재하지 않습니다.
	</c:if>
	<hr>
	<form action="login" method="post">
		아이디 : <input type="text" name="id"><br>
		비밀번호 : <input type="password" name="pwd"><br>
		<input type="submit" value="로그인">
		<input type="reset" value="취소">
	</form>
</body>
</html>