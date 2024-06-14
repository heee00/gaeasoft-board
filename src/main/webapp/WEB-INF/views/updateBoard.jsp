<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>UpdateBoard</title>
	<link rel="stylesheet" type="text/css" href="/resources/css/updateBoard.css">
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<h2>게시글 수정</h2>
	<form id="updateArticleForm">
		<div class="form-group">
			<input type="hidden" name="rowNum" value="${rowNum}">
		</div>
		<div class="form-group">
			<input type="hidden" name="id" value="${board.id}" readonly>
		</div>
		<div class="form-group">
			<input type="text" name="writer" value="${board.writer}" readonly>
		</div>
		<div class="form-group">
			<input type="password" name="password" value="${board.password}" readonly>
		</div>
		<div class="form-group">
			<input type="text" name="title" id="title" value="${board.title}" placeholder="제목" required>
	        <span id="titleError"></span>
		</div>
		<div class="form-group">
	        <textarea name="content" id="content" cols="30" rows="10" placeholder="내용" required>${board.content}</textarea>
			<span id="contentError"></span>
		</div>
		<input type="submit" value="수정📝" >
		<input type="button" id="cancelButton" value="취소❎">
	</form>
	
	<script>
		$(document).ready(function() {
			$('#title').on('blur', function(e) {
				var title = document.getElementById("title").value;
		        var titlePattern = /^[ㄱ-ㅎ가-힣a-zA-Z0-9]*$/;
		        var titleError = document.getElementById("titleError");
		        
		        if (title === '' || (titlePattern.test(title)) && title.length <= 100) {
		        	titleError.innerHTML = '';
		   	        return;
		   	    }
		
		        if (!titlePattern.test(title)) {
		            titleError.style.color = "red";
		            titleError.innerHTML = "제목에는 특수 문자를 사용할 수 없습니다.";
		            return;
		        }
		
		        if (title.length > 100) {
		            titleError.style.color = "red";
		            titleError.innerHTML = "제목은 100자 이하이어야 합니다.";
		            return;
		        }
			});
			
			$('#content').on('blur', function(e) {
				var content = document.getElementById("content").value;
		        var contentError = document.getElementById("contentError");
		        
		        if (content === '' || content.length <= 1000) {
		        	contentError.innerHTML = '';
		   	        return;
		   	    }
		        
		        if (content.length > 1000) {
		            contentError.style.color = "red";
		            contentError.innerHTML = "내용은 1000자 이하이어야 합니다.";
		            return false;
		        }
			});
			
			$('#updateArticleForm').on('submit', function(e) {
		        e.preventDefault();
		        var id = '${board.id}';
		        var page = '${page}';
	        	var rowNum = '${rowNum}';
		        var formData = $(this).serialize();
            	var isConfirmed = confirm("수정하시겠습니까?");
		
		        $.ajax({
		            url: '/board/updateArticle?id=' + id + '&page=' + page,
		            method: 'post',
		            data: formData,
		            success: function(response) {
		            	
		                if (isConfirmed) {
		                    window.location.href = '/board/viewDetail?id=' + id + '&page=' + page + '&rowNum=' + rowNum;
		                } else {
		                    window.location.href = '/board/updateArticleForm?id=' + id + '&page=' + page + '&rowNum=' + rowNum;
		                }
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
		             url: '/board/viewDetail?id=' + id + '&page=' + page + '&rowNum=' + rowNum,
		             method: 'get',
		             success: function(response) {
		                window.location.href = '/board/viewDetail?id=' + id + '&page=' + page + '&rowNum=' + rowNum;
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