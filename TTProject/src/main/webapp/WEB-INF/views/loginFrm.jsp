<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<link rel="stylesheet" href="resources/css/css.css">
<script type="text/javascript">
$(function(){
	var msg = "${msg}";
	if(msg != ""){
		alert(msg);
	}
});
</script>
<style type="text/css">
section{
	width: 80%;
	margin: 0 auto;
}
.content{
	margin: 0 auto;
	width : 200px;
	margin-top: 200px;
}
</style>
</head>
<body>
	<section>
	<div class="content">
		<form class="login-form" action="loginProc" method="post">
			<h2 class="login-header">로그인</h2>
			<input type="text" name="m_id" autofocus required placeholder="아이디"><br>
			<input type="password" name="m_pwd" placeholder="비밀번호" required><br>
			<input type="submit" class="login-btn" value="로그인">
			<a href="./">    Home</a>
		</form>
	</div>
	</section>
</body>
</html>