<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 상세보기</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<link rel="stylesheet" href="resources/css/css.css">
<script type="text/javascript">
	$(function() {
		//메시지 출력 부분
		var msg = "${msg}";
		if (msg != "") {
			alert(msg);
		}
		
		//로그인한 회원 정보 및 로그아웃 출력
		var lname = "${mb.m_name}";
		$("#mname").html(lname + "님");
		$(".suc").css("display", "block");
		$(".bef").css("display", "none");
				
		// 수정,삭제버튼 숨김 처리 (본인의 글인지 아닌지에 따른것)
		$("#upbtn").hide();
		$("#delbtn").hide();
		
		var mid = "${mb.m_id}";
		var bid = "${BoardDto.b_id}";
		
		if(mid == bid){
			$("#upbtn").show();
			$("#delbtn").show();
		}
		
		
	});
</script>
<style type="text/css">
.contents{
	width: 60%;
	margin: 0 auto;
}
table{
	text-align: right;
}
span{
	border: 1px solid black;
	width: 100px;
	height: 50px;
	border-radius: 5px;
	text-align: center;
	background-color: white;
}

.write{
	width: 100%;
	margin: 0 auto;
}
.tabletr{
	width: 10%;
	margin: 0 auto;
}
#rtable{
	text-align: center;
}
</style>
</head>
<body>
	<section>
		<div class="user-info">
			<div class="user-info-sub">
				<p class="grade">등급 [${mb.g_name}]</p>
				<p class="point">POINT [${mb.m_point}]</p>
			</div>
		</div>
		
		
		<div class="contents">
			<table>
				<tr>
					<th>작성자</th>
					<td>${BoardDto.b_id}</td>
				</tr>
				<tr>
					<th>조회수</th>
					<td>${BoardDto.b_views}</td>
				</tr>
			</table>
			<fieldset>
				<legend>${BoardDto.b_title}</legend>
				${BoardDto.b_contents}
			</fieidset>	

			<div>
				<div class="t_content p-15 file_h">FILES</div>
				<div class="d_content p-85 file_h" style="overflow: auto;">
					<c:if test="${empty fList}">
						첨부된 파일이 없습니다.
				</c:if>
					<c:if test="${!empty fList}">
						<c:forEach var="flt" items="${fList}">
							<a href="download?f_sysname=${flt.f_sysname}&f_oriname=${flt.f_oriname}">
							<span class="file-title">${flt.f_oriname}</span></a>
						</c:forEach>
					</c:if>
				</div>
			</div>
		</div>
		<div  class = "tabletr">
			<table class="con_table">
				<!-- 이미지 파일 미리보기 -->
				<c:if test="${!empty fList}">
					<tr>	
					<th>PREVIEW</th>
					</tr>
	
					<tr>
						<td><c:forEach var="f" items="${fList}">
								<!-- 뒤에 확장자 구별용 스위치문 -->
								<c:choose>
									<c:when test="${fn:contains(f.f_sysname,'.jpg')}">
										<img src="resources/uploadfile/${f.f_sysname}" width="100">
								</c:when>
									<c:when test="${fn:contains(f.f_sysname,'.png')}">
										<img src="resources/uploadfile/${f.f_sysname}" width="100">
									</c:when>
									<c:when test="${fn:contains(f.f_sysname,'.gif')}">
										<img src="resources/uploadfile/${f.f_sysname}" width="100">
									</c:when>
									<c:otherwise>미리보기 없음</c:otherwise>
								</c:choose>
							</c:forEach></td>
					</tr>
				</c:if>
				<tr>
					<td colspan="6" align="center">
						<button class="btn-write" id="upbtn" onclick="updateFrm()">U</button>
						<button class="btn-write" id="delbtn" onclick="deletePost()">D</button>
						<button class="btn-sub" onclick="backbtn()">B</button>
					</td>
			</tr>
			</table>
		</div>
		<div class="contents">	
			<!-- 댓글 작성 양식 -->
			<form id="rFrm">
				<textarea rows="3" class="write" name="c_contents"
					id="comment" placeholder="댓글을 적어주세요~"></textarea>
				<input type="button" value="댓글 전송" class="btn-write"
					onclick="replyInsert(${BoardDto.b_num})"
					style="width: 100%; margin-bottom: 30px;">
			</form>
		</div>
	<div class="contents">	
			<table style="width: 100%">
				<!-- 제목 테이블 -->
				<tr bgcolor="pink" align="center" height="30">
					<td width="20%">WRITER</td>
					<td width="50%">CONTENTS</td>
					<td width="30%">DATE</td>
				</tr>
			</table>
			<table id="rtable" style="width: 100%">
				<!-- 목록 테이블 -->
					<c:forEach var="rlt" items="${cList}">
					<tr>
							<td width="20%">${rlt.c_id}</td>
						<td width="50%">${rlt.c_contents}</td>
						<td width="30%"><fmt:formatDate value="${rlt.c_date}"
								pattern="yyyy-MM-dd HH:mm" /></td>
					</tr>
				</c:forEach>
		</table>
		</div>
		</section>
	</body>
	<script src="resources/js/jquery.serializeObject.js"></script>
<script type="text/javascript">
// 백버튼
function backbtn() {
	location.href="list?pageNum=${pageNum}";
}

// 삭제
function deletePost() {
	if(confirm("정말 삭제하시겠습니까?")){
	location.href="deleteProc?b_num=${BoardDto.b_num}&pageNum=${pageNum}";
	}
}


// 업데이트
function updateFrm() {
	location.href="BoardUpdateFrm?b_num=${BoardDto.b_num}&pageNum=${pageNum}";
}

// 댓글
function replyInsert(bnum){
		console.log(bnum);
		
		var replyFrm = $("#rFrm").serializeObject();
		replyFrm.cb_num = bnum; // 게시글 번호 추가
		replyFrm.c_id = "${mb.m_id}";
		console.log(replyFrm);
		
		//controller에 ajax로 전송
		
		$.ajax({
			url: "commentProc",
			type: "post", // json은 무조건 post 방식입니다.
			data: replyFrm,
			dataType: "json",
			success: function (result) {
				var listStr = "";
				var rlist = result.cList;
				
				for(var i = 0; i < rlist.length; i++){
					listStr += "<tr><td width='20%'>"
					+ rlist[i].c_id + "</td>"
					+ "<td width='50%'>" + rlist[i].c_contents + "</td>"
					+ "<td width='30%'>" + rlist[i].c_date + "</td></tr>";
				}
				
				$("#rtable").html(listStr);
				$("#comment").val("");
			},
			error : function (result) {
				alert("댓글 입력 실패");
			}
		});
	}
</script>
</html>




