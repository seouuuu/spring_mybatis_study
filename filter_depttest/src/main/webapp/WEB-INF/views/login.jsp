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
	<h2>�α���</h2>
	<c:if test="${re==1 }">
		�α��εǾ����ϴ�.
	</c:if>
	<c:if test="${re==0 }">
		��й�ȣ�� Ʋ�Ƚ��ϴ�.
	</c:if>
	<c:if test="${re==-1 }">
		���̵� �������� �ʽ��ϴ�.
	</c:if>
	<hr>
	<form action="login" method="post">
		���̵� : <input type="text" name="id"><br>
		��й�ȣ : <input type="password" name="pwd"><br>
		<input type="submit" value="�α���">
		<input type="reset" value="���">
	</form>
</body>
</html>