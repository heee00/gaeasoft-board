<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>update</title>
<link rel="stylesheet" type="text/css" href="/resources/css/updateBoard.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<h2>게시글 수정</h2>
	<form id="updateArticleForm">
		<input type="hidden" name="id" value="${board.id}" readonly>
		<input type="text" name="writer" value="${board.writer}" placeholder="작성자" readonly>
		<input type="password" name="password" id="password" placeholder="비밀번호" required>
		<input type="text" name="title" value="${board.title}" required>
        <textarea name="content" cols="30" rows="10" required>${board.content}</textarea>
		<input type="submit" value="수정📝" >
		<input type="button" id="cancelButton" value="취소❎">
	</form>
	
	<script>
		$(document).ready(function() {
			$('#updateArticleForm').on('submit', function(e) {
		        e.preventDefault();
		        var id = '${board.id}';
		        var page = "${page}";
		        var formData = $(this).serialize();
		
		        $.ajax({
		            url: '/board/update?id=' + id + '&page=' + page,
		            method: 'post',
		            data: formData,
		            success: function(response) {
		            	if (response.errorMessage) {
		                    alert(response.errorMessage);
		                } else {
			                alert("게시글이 수정되었습니다.");
			                window.location.href = '/board?id=' + id + '&page=' + page;
		                }
		            },
		            error: function(xhr, status, err) {
		                console.error('AJAX Error: ' + status + err);
		            }
		        }); 
			});
		        
	        $('#cancelButton').on('click', function(e) {
		    	var id = '${board.id}';
		    	var page = "${page}";
		    	
		    	 $.ajax({
		             url: '/board?id=' + id + '&page=' + page,
		             method: 'get',
		             success: function(response) {
						alert("게시글 수정이 취소되었습니다.");
		                window.location.href = '/board?id=' + id + '&page=' + page;
		             },
		             error: function(xhr, status, err) {
		                 console.error('AJAX Error: ' + status + err);
		             }
		         });
		    });
	    });
	</script>
</body>
</html>