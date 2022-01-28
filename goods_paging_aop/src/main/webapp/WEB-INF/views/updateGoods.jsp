<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>상품 수정</h2>
	<form action="updateGoods" method="post" enctype="multipart/form-data">
		상품번호: ${g.no }
		<input type="hidden" name="no" value=${g.no }><br>
		상품이름: <input type="text" name="name" value="${g.name }"><br>
		상품가격: <input type="text" name="price" value="${g.price }"><br>
		상품수량: <input type="text" name="qty" value="${g.qty }"><br>
		<img src="images/${g.fname }" width="100" height="100"><br>
		<input type="hidden" name="fname" value="${g.fname }"><br>
		상품사진: <input type="file" name="uploadFile"><br><br>
		<input type="submit" value="수정">
	</form>
</body>
</html>