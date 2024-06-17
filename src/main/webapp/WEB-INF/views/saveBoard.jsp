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
</head>
<body>
<h2>게시글 작성</h2>
	<form id="saveArticleForm">
		<div class="form-group">
			<input type="text" name="writer" placeholder="작성자"   value="${sessionScope.loginId}" readonly>
		</div>
		<div class="form-group">
			<input type="password" name="password" id="password" placeholder="비밀번호">
			<span id="passwordError"  class="error"></span>
		</div>
		<div class="form-group">
			<input type="text" name="title" id="title" placeholder="제목">
        	<span id="titleError"  class="error"></span>
	    </div>
	    <div class="form-group">
			<textarea name="content" id="content" cols="30" rows="10" placeholder="내용"></textarea>
        	<span id="contentError"  class="error"></span>
		</div>
		<input type="submit"  id="saveButton" value="저장💾">
		<input type="button" id="cancelButton" value="취소❎">
	</form>
	
	<script>
		$(document).ready(function() {
            $('#saveButton').prop('disabled', true);

			$('#password').on('blur', function() {
		        validateField('password', $(this).val());
		    });

		    $('#title').on('blur', function() {
		        validateField('title', $(this).val());
		    });

		    $('#content').on('blur', function() {
		        validateField('content', $(this).val());
		    });
		    
			$('#saveArticleForm').on('submit', function(e) {
		        e.preventDefault();
	            var page = "${page}";
	            var formData = $(this).serialize();
	            var isConfirmed = confirm("저장하시겠습니까?");

	            if (isConfirmed) {
	                $.ajax({
	                    url: '/board/saveArticle',
	                    method: 'post',
	                    data: formData,
	                    success: function(response) {
	                        window.location.href = '/board/pagingList?page=' + page;
	                    },
	                    error: function(xhr) {
	                    	if (xhr.status === 400) {
	                            var errors = xhr.responseJSON;
	                            displayErrors(errors);
	                        } else {
	                            console.error('AJAX Error: ' + status + err);
	                        }
	                    }
	                });
	            } else {
	                window.location.href = '/board/saveArticleForm';
	            }
 	        });
			
		 	// 유효성 검사 함수
		    function validateField(fieldName, fieldValue) {
	            $.ajax({
	                type: 'POST',
	                url: '/board/validateField',
	                contentType: 'application/json',
	                data: JSON.stringify({ fieldName: fieldName, fieldValue: fieldValue }),
	                success: function(errors) {
	                    displayFieldError(fieldName, errors);
		                validateForm();
	                },
	                error: function(xhr) {
	                    console.error('Validation Error: ' + xhr.statusText);
	                }
	            });
	        }
		 	
		 	// 전체 폼의 유효성 검사
	    	function validateForm() {
	            var passwordError = $('#passwordError').text().trim();
	            var titleError = $('#titleError').text().trim();
	            var contentError = $('#contentError').text().trim();

	            if (passwordError === '' && titleError === '' && contentError === '') {
	                $('#saveButton').prop('disabled', false);
	            } else {
	                $('#saveButton').prop('disabled', true);
	            }
	        }

		    // 개별 필드 에러 메시지 표시 함수
		    function displayFieldError(fieldName, errors) {
		        if (errors[fieldName]) {
		            $('#' + fieldName + 'Error').html('<div>' + errors[fieldName][0] + '</div>').css('color', 'red');
		        } else {
		            $('#' + fieldName + 'Error').empty();
		        }
		    }

		    // 전체 에러 메시지 표시 함수
		    function displayErrors(errors) {
		        if (errors.password) {
		            $('#passwordError').html('<div>' + errors.password[0] + '</div>').css('color', 'red');
		        } else {
		            $('#passwordError').empty();
		        }
		        if (errors.title) {
		            $('#titleError').html('<div>' + errors.title[0] + '</div>').css('color', 'red');
		        } else {
		            $('#titleError').empty();
		        }
		        if (errors.content) {
		            $('#contentError').html('<div>' + errors.content[0] + '</div>').css('color', 'red');
		        } else {
		            $('#contentError').empty();
		        }
		    }
		    
		    $('#cancelButton').on('click', function(e) {
	            var page = "${page}";
		        window.location.href = '/board/pagingList?page=' + page;
		    });
		});
	</script>
</body>
</html>