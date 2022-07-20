<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입 페이지</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<link rel="stylesheet" href="resources/css/css.css">
<style type="text/css">
/* section 시작 */
section{
	margin : 0 auto;
	width: 80%;
	height: 1200px;
	}
.content{
	width : 200px;
	margin: 0 auto;
	margin-top : 200px;
}
</style>
</head>
<body>
<section>
<div class="content">
	<form name="joinFrm" class="login-form" action="joinProc"
		method="post" onsubmit="return check()">
		<h2 class="login-header">회원 가입</h2>
		<input type="text" class="login-input" id="mid" title="아이디" name="m_id" autofocus placeholder="아이디"><br>
	    <input type="button" class="idcheck-btn" value="중복확인" 
	    onclick="idcheck()"><br>
		<input type="password" class="login-input" title="비밀번호" name="m_pwd" placeholder="비밀번호"><br> 
		<input type="text" class="login-input" title="이름" name="m_name" placeholder="이름" ><br>
		<input type="submit" class="login-btn" value="가입">
		<a href="./">    Home</a>
	</form>
</div>
</section>
</body>
<script type="text/javascript">
var msg = "${msg}";
if(msg != ""){
	alert(msg);
}

var ckOk = false;

function check(){
	if(ckOk == false){
		alert("아이디 중복체크를 해주세요");
		return false; 
	}
	
	var frm = document.joinFrm;
	console.log(frm);
	
	var length = frm.length - 1;
	console.log(length);
	
	for(var i = 0; i<length; i++){
		if(frm[i].value == "" || frm[i].value == null){
			alert(frm[i].title + " 입력!");
			frm[i].focus();
			return false;
		}
	}
	
	return ckOk;
}
function idcheck() {
	var m_id = $("#mid").val();
	
	var obj = {"m_id" : m_id};
	
	$.ajax({
		url : "idcheck",
		type : "get",
		data : obj,
		success : function (result) {
			if(result == "아이디 중복"){
				alert("아이디 중복");
				ckOk = false;
			} else{
				alert("아이디 사용가능");
				ckOk = true;
			}
		},
		error : function (result) {
			alert("통신실패");
		}
	});
}
</script>
</html>