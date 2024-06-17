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
			<input type="text" name="title" id="title" value="${board.title}" placeholder="제목">
        	<span id="titleError"  class="error"></span>
		</div>
		<div class="form-group">
	        <textarea name="content" id="content" cols="30" rows="10" placeholder="내용">${board.content}</textarea>
        	<span id="contentError"  class="error"></span>
		</div>
		<input type="submit" id="updateButton" value="수정📝" >
		<input type="button" id="cancelButton" value="취소❎">
	</form>
	
	<script>
		$(document).ready(function() {
            $('#updateButton').prop('disabled', true);

			$('#title').on('blur', function() {
		        validateField('title', $(this).val());
		    });

		    $('#content').on('blur', function() {
		        validateField('content', $(this).val());
		    });
			
			$('#updateArticleForm').on('submit', function(e) {
		        e.preventDefault();
		        var id = '${board.id}';
		        var page = '${page}';
	        	var rowNum = '${rowNum}';
		        var formData = $(this).serialize();
            	var isConfirmed = confirm("수정하시겠습니까?");

	            if (isConfirmed) {
	                $.ajax({
	                    url: '/board/updateArticle',
	                    method: 'post',
	                    data: formData,
	                    success: function(response) {
               				window.location.href = '/board/viewDetail?id=' + id + '&page=' + page + '&rowNum=' + rowNum;
	                    },
	                    error: function(xhr) {
		                    if (xhr.status === 400) {
		                        var errors = xhr.responseJSON;
		                        displayErrors(errors);
		                    } else {
		                        console.error('AJAX Error: ' + xhr.statusText);
		                    }
		                }
					});
	            } else {
	            	window.location.href = '/board/updateArticleForm?id=' + id + '&page=' + page + '&rowNum=' + rowNum;
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
	            var titleError = $('#titleError').text().trim();
	            var contentError = $('#contentError').text().trim();

	            if (titleError === '' && contentError === '') {
	                $('#updateButton').prop('disabled', false);
	            } else {
	                $('#updateButton').prop('disabled', true);
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
		    	var id = '${board.id}';
		    	var page = '${page}';
	        	var rowNum = '${rowNum}';
		    	
		     	window.location.href = '/board/viewDetail?id=' + id + '&page=' + page + '&rowNum=' + rowNum;
		    });
	    });
	</script>
</body>
</html>