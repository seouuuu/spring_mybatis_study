<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>게시글 상세보기</h2>
	<hr>
	번호:${b.no } <br>
	제목:${b.title } <br>
	작성자:${b.writer } <br>
	내용: <br>
	<textarea rows="10" cols="50" readonly="readonly">${b.content }</textarea><br>
	등록일:${b.regdate } <br>
	조회수:${b.hit } <br>
	첨부파일명:${b.fname } <br>
	<a href="listBoard">목록</a>
	<a href="updateBoard?no=${b.no }">수정</a>
</body>
</html>