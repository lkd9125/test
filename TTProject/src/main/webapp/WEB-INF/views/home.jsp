<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<title>Home</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<link rel="stylesheet" href="resources/css/css.css">
<style type="text/css">
/* header */
header {
	width: 80%;
	margin: 0 auto;
	height: 60px;
	line-height: 60px;
}

.content {
	text-align: center;
}
.d1 {
	float: right;
}
.d1>a:nth-child(2) {
	margin-left: 15px;
}

</style>
</head>
<body>
	<header>
		<div class="content" id="content">
			<div class="d1">
			<a href="loginFrm">로그인</a> 
			<a href="joinFrm">회원가입</a>
			</div>
			<div class="d2">
				<h2>Home입니다.</h2>
			</div>
		</div>
	</header>
	
	
</body>
<script type="text/javascript">
var msg = "${msg}";
if(msg != ""){
	alert(msg);
}
</script>
</html>
