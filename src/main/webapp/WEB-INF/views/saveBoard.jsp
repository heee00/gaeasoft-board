<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SaveBoard</title>
<link rel="stylesheet" type="text/css" href="/resources/css/saveBoard.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<h2>게시글 작성</h2>
	<form id="saveArticleForm">
		<input type="text" name="writer" placeholder="작성자"  required>
		<input type="password" name="password" placeholder="비밀번호" required>
		<input type="text" name="title" placeholder="제목" required>
		<textarea name="content" cols="30" rows="10" placeholder="내용을 입력하세요" required></textarea>
		<input type="submit" value="저장💾">
		<input type="button" id="cancelButton" value="취소❎">
	</form>
	
	<script>
		$(document).ready(function() {
			$('#saveArticleForm').on('submit', function(e) {
		        e.preventDefault();
	            var page = "${page}";
	            var formData = $(this).serialize();

	            $.ajax({
	                url: '/board/save',
	                method: 'post',
	                data: formData,
	                success: function(response) {
	                    alert("게시글이 저장되었습니다.");
	                    window.location.href = '/board/paging?page=' + page;
	                },
	                error: function(xhr, status, err) {
	                    console.error('AJAX Error: ' + status + err);
	                }
	            });
	        });
			
		    $('#cancelButton').on('click', function(e) {
	            var page = "${page}";
		        window.location.href = '/board/paging?page=' + page;
		    });
		});
	</script>
</body>
</html>