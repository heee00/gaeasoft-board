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
		<input type="hidden" name="rowNum" value="${rowNum}">
		<input type="hidden" name="id" value="${board.id}" readonly>
		<input type="text" name="writer" value="${board.writer}" readonly>
		<input type="password" name="password" value="${board.password}" readonly>
		<input type="text" name="title" value="${board.title}" placeholder="제목" required>
        <textarea name="content" cols="30" rows="10" placeholder="내용" required>${board.content}</textarea>
		<input type="submit" value="수정📝" >
		<input type="button" id="cancelButton" value="취소❎">
	</form>
	
	<script>
		$(document).ready(function() {
			$('#updateArticleForm').on('submit', function(e) {
		        e.preventDefault();
	        	var rowNum = '${rowNum}';
	        	var errorMessage = '${errorMessage}';
		        var id = '${board.id}';
		        var page = '${page}';
		        var formData = $(this).serialize();
		
		        $.ajax({
		            url: '/board/update?id=' + id + '&page=' + page,
		            method: 'post',
		            data: formData,
		            success: function(response) {
		                alert("게시글이 수정되었습니다.");
		                window.location.href = '/board?id=' + id + '&page=' + page + '&rowNum=' + rowNum;
		            },
		            error: function(xhr, status, err) {
		                console.error('AJAX Error: ' + status + err);
		            }
		        }); 
			});
		        
	        $('#cancelButton').on('click', function(e) {
	        	var rowNum = '${rowNum}';
		    	var id = '${board.id}';
		    	var page = '${page}';
		    	
		    	 $.ajax({
		             url: '/board?id=' + id + '&page=' + page + '&rowNum=' + rowNum,
		             method: 'get',
		             success: function(response) {
						alert("게시글 수정이 취소되었습니다.");
		                window.location.href = '/board?id=' + id + '&page=' + page + '&rowNum=' + rowNum;
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