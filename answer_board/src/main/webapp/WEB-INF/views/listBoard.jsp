<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

</head>
<body>
	<h2>게시판 목록</h2>
	<br>
	<a href="insertBoard">게시물 등록</a>
	<br>
	<table border="1" width="80%">
		<tr>
			<td>번호</a></td>
			<td>제목</a></td>
			<td>작성자</a></td>
		</tr>
		<c:forEach var="b" items="${list }">
			<tr>
				<td>${b.no }</td>
				<td>
					<c:if test="${b.b_level > 0 }">
						<c:forEach begin="1" end="${b.b_level }">
							&nbsp;&nbsp;
						</c:forEach>
						<font color="red">==></font>
					</c:if>
					<a href="detailBoard?no=${b.no }">${b.title }</a></td>
				<td>${b.writer }</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>