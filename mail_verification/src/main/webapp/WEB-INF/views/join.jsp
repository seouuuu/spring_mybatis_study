<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
	#form,#input_code{
		visibility: hidden;
	}
</style>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(function(){
		let code="";
		let email=""
		let type="email";
		$("#btnSend").click(function(){
			to = $("#to").val();			
			let data = {to:to,type:type};
			$.ajax({
				url:"checkVerification",
				data:data,
				success:function(data){
					code = data;
					alert("인증번호를 전송하였습니다.");
					$("#input_code").css("visibility","visible");
				}
			})
		});
		
		$("#btnOK").click(function(){
			let userCode = $("#userCode").val();
			if( userCode == code){
				alert("인증되었습니다.");
				$("#form").css("visibility","visible");
				$("#email2").val(email);
				$("#phone2").val(phone);
			}else{
				alert("인증에 실패하였습니다.");
				$("#input_code").css("visibility","hidden");
			}
		});
		
		$(".type").click(function() {
			type = $(this).val();
			if(type=="email"){
				$("#title").html("이메일 입력");
			}else{
				$("#title").html("휴대폰 번호 입력");
			}
		});
	});
</script>
</head>
<body>
	<h2>회원가입</h2>
	인증방법 선택
	<input type="radio" value="phone" id="phone" name="type" class="type">휴대폰 인증
	<input type="radio" value="email" id="email" name="type" checked="checked" class="type">이메일 인증
	<br>
	<span id="title">이메일 </span> : <input type="text" id="to">
	<button id="btnSend">인증코드 받기</button><br>
	
	<div id="input_code">
	인증번호 입력 : <input type="text" id="userCode">
	<button id="btnOK">확인</button>
	</div>
	<hr>
	<form action="join" method="post" id="form">
		아이디 : <input type="text" name="id"><br>
		암호 : <input type="text" name="pwd"><br>
		이름 : <input type="text" name="name"><br>
		전화 : <input type="text" name="phone" id="phone2"><br>
		이메일 : <input type="text" name="email"  id="email2"><br>
		<input type="submit" value="가입">
		<input type="reset" value="취소">
	</form>
</body>
</html>
