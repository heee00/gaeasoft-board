<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
		<div class="form-group">
			<input type="text" name="writer" placeholder="작성자"   value="${sessionScope.loginId}" readonly>
		</div>
		<div class="form-group">
			<input type="password" name="password" id="password" placeholder="비밀번호" required>
        	<span id="passwordError"></span>
		</div>
		<div class="form-group">
			<input type="text" name="title" id="title" placeholder="제목" required>
        	<span id="titleError"></span>
	    </div>
	    <div class="form-group">
			<textarea name="content" id="content" cols="30" rows="10" placeholder="내용" required></textarea>
        	<span id="contentError"></span>
		</div>
		<input type="submit" value="저장💾">
		<input type="button" id="cancelButton" value="취소❎">
	</form>
	
	<script>
		$(document).ready(function() {
			$('#password').on('blur', function(e) {
				var password = document.getElementById("password").value;
				var passwordError = document.getElementById("passwordError");
				
				if (password === '' || password.length <= 20) {
					passwordError.innerHTML = '';
		   	        return;
		   	    }

		        if (password.length > 20) {
		            passwordError.style.color = "red";
		            passwordError.innerHTML = "비밀번호는 20자 미만이어야 합니다.";
		            return;
		        }
			});
			
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
		            titleError.innerHTML = "제목은 100자 미만이어야 합니다.";
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
		            contentError.innerHTML = "내용은 1000자 미만이어야 합니다.";
		            return;
		        }
			});
			
			$('#saveArticleForm').on('submit', function(e) {
		        e.preventDefault();
	            var page = "${page}";
	            var formData = $(this).serialize();
	            var isConfirmed = confirm("저장하시겠습니까?");

	            if (isConfirmed) {
	                $.ajax({
	                    url: '/board/save',
	                    method: 'post',
	                    data: formData,
	                    success: function(response) {
	                        window.location.href = '/board/paging?page=' + page;
	                    },
	                    error: function(xhr, status, err) {
	                        console.error('AJAX Error: ' + status + err);
	                    }
	                });
	            } else {
	                window.location.href = '/board/save';
	            }
	        });
			
		    $('#cancelButton').on('click', function(e) {
	            var page = "${page}";
		        window.location.href = '/board/paging?page=' + page;
		    });
		});
	</script>
</body>
</html>