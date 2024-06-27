<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>SaveBoard</title>
	<link rel="stylesheet" type="text/css" href="/resources/css/saveBoard.css">
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script src="/resources/js/validation.js"></script>
	<script src="/resources/js/fileUpload.js"></script>
</head>
<body>
	<h2>게시글 작성</h2>
	<form id="saveArticleForm" enctype="multipart/form-data">
		<div class="form-group">
			<input type="text" name="writer" placeholder="작성자"   value="${sessionScope.loginId}" readonly>
		</div>
		<div class="form-group">
			<input type="password" name="password" id="password" placeholder="비밀번호" required>
			<span id="passwordError"  class="error"></span>
		</div>
		<div class="form-group">
			<input type="text" name="title" id="title" placeholder="제목" required>
        	<span id="titleError"  class="error"></span>
	    </div>
	    <div class="form-group">
			<textarea name="content" id="content" cols="30" rows="10" placeholder="내용" required></textarea>
        	<span id="contentError"  class="error"></span>
		</div>
		<div class="form-group">
	        <div id="fileInputContainer">
				<input type="file" name="files" id="files"multiple>
			</div>
		</div>
		<input type="submit"  id="saveButton" value="저장💾">
		<input type="button" id="cancelButton" value="취소❎">
	</form>
	
	<script>
		$(document).ready(function() {
            $('#saveButton').prop('disabled', true);

            $('#password').on('blur', function() {
                validateField('password', $(this).val(), '/board/validateField', displayFieldError, 'saveArticleForm', 'saveButton');
            });

            $('#title').on('blur', function() {
                validateField('title', $(this).val(), '/board/validateField', displayFieldError, 'saveArticleForm', 'saveButton');
            });

            $('#content').on('blur', function() {
                validateField('content', $(this).val(), '/board/validateField', displayFieldError, 'saveArticleForm', 'saveButton');
            });
            
            $('#files').on('change', function() {
                var files = $(this)[0].files;
                if (!FileUpload.validateFiles(files)) {
                    $(this).val('');
                }
            });
		    
			$('#saveArticleForm').on('submit', function(e) {
		        e.preventDefault();
	            var page = "${page}";
	            
	            var formData = new FormData();
	            formData.append('password', $('#password').val());
	            formData.append('title', $('#title').val());
	            formData.append('content', $('#content').val());

	            var files = $('#files')[0].files;
	            for (var i = 0; i < files.length; i++) {
	                formData.append('files', files[i]);
	            }
	            
	            var isConfirmed = confirm("저장하시겠습니까?");
	            if (isConfirmed) {
	                $.ajax({
	                    url: '/board/saveArticle',
	                    method: 'post',
	                    processData: false,
	                    contentType: false,
	                    data: formData,
	                    success: function(response) {
	                        window.location.href = '/board/pagingList?page=' + page;
	                    },
	                    error: function(xhr) {
	                        if (xhr.responseJSON && xhr.responseJSON.errorMessage) {
	                            alert(xhr.responseJSON.errorMessage);

	                            var errors = xhr.responseJSON;
	                            displayErrors(errors);
	                        } else {
	                            console.error('Unexpected error:', xhr.status);
	                        }
	                    }
	                });
	            } else {
	                window.location.href = '/board/saveArticleForm';
	            }
 	        });
			
		    $('#cancelButton').on('click', function(e) {
	            var page = "${page}";
		        window.location.href = '/board/pagingList?page=' + page;
		    });
		});
	</script>
</body>
</html>