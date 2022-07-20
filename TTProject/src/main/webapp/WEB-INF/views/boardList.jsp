<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글</title>
<link rel="stylesheet" href="resources/css/css.css">
<meta name="viewport" content="width=device-width, initial-scale=1">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script type="text/javascript">
	$(function() {
		//메시지 출력 부분
		var msg = "${msg}";
		if (msg != "") {
			alert(msg);
		}

		//검색 기능 수행 코드
		$("#search").click(
			function() {
				var keyword = $("#keyword").val();
				if (keyword == "") {
					alert("검색어를 입력하세요.");
					return;
				}
				var select = $("#sel").val();
				location.href = "list?colname=" + select + "&keyword=" + keyword + "&pageNum=1";
		});
	});
</script>
<style type="text/css">
section{
	border : 1px solid;
	width: 80%;
	height: 500px;
	margin: 0 auto;
	margin-top: 100px;
	position: relative;
}
.content{
	border : 1px solid;
	float: left;
	width: 200px;
	margin: 0 auto;
	text-align: center;
	
}
ul{
	list-style: none;
	
}
li{
	float: left;
	margin-right: 50px;
}

.d2{
	clear : both;
	margin : 0 auto;
	width: 70%;	
}
table{
	box-sizing: border-box;
	width : 100%;
	margin: 0 auto;
	text-align: center;
}
th:nth-child(1){
	width : 15%;
	border: 1px solid;
}
th:nth-child(2){
	width : 40%;
	border: 1px solid;
}
th:nth-child(3){
	width : 15%;
	border: 1px solid;
}
th:nth-child(4){
	width : 30%;
	border: 1px solid;
}
.d4{
	width : 200px;
	height : 50px;
	border : 1px solid;
	position: absolute;
	bottom: 10px;
	right: 50%;	
}

.button-52 {
  font-size: 16px;
  font-weight: 200;
  letter-spacing: 1px;
  padding: 13px 20px 13px;
  outline: 0;
  border: 1px solid black;
  cursor: pointer;
  position: relative;
  background-color: rgba(0, 0, 0, 0);
  user-select: none;
  -webkit-user-select: none;
  touch-action: manipulation;
}

.button-52:after {
  content: "";
  background-color: #ffe54c;
  width: 100%;
  z-index: -1;
  position: absolute;
  height: 100%;
  top: 7px;
  left: 7px;
  transition: 0.2s;
}

.button-52:hover:after {
  top: 0px;
  left: 0px;
}

@media (min-width: 768px) {
  .button-52 {
    padding: 13px 50px 13px;
  }
}

.d3{
	width: 30%;
	border: 1px solid;
	margin: 0 auto;
	margin-top: 50px;
}
</style>
</head>
<body>

	<section>
		<div class="content">
			<h2><a href="list?pageNum=1">게시글</a></h2>
		</div>
			<ul>
				<li>등급 [${mb.g_name}]</li>
				<li>POINT [${mb.m_point}]</li>
				<li><a href = "logout">로그아웃</a></li>
			</ul>
			
		<div class="search-area" style="margin-top: 35px; margin-left: 290px;">
			<select id="sel">
				<option value="b_title" selected>제목</option>
				<option value="b_contents">내용</option>
			</select> 
			<input type="text" id="keyword">
			<button id="search">검색</button>
		</div>
					
		<div class="d2">
			<table>
				<tr>
					<th>글번호</th>
					<th>제목</th>
					<th>작성자</th>
					<th>조회수</th>
				</tr>
					<c:if test = "${empty bList}">
					<tr><th colspan="4">리스트가 없습니다!</th></tr>
					</c:if>	
				<c:if test = "${!empty bList}">
				<c:forEach var="b" items="${bList}">
				<tr>
					<td>${b.b_num}</td>
					<td><a href = "bcontents?bnum=${b.b_num}&pageNum=${pageNum}">${b.b_title}</a></td>
					<td>${b.b_id}</td>
					<td>${b.b_views}</td>
				</tr>
				</c:forEach>
				</c:if>
			</table>
		</div>
		<div class="d4">
			<button class="button-52" role="button" onclick="goWirteFrom()">글쓰기</button>
		</div>
		<div class="d3">
			<div class="paging">${paging}</div>
		</div>
	</section>

</body>
<script type="text/javascript">
function goWirteFrom() {
	location.href="writeFrm?pageNum=${pageNum}"
}
</script>
</html>