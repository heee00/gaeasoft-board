<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>UpdateBoard</title>
	<link rel="stylesheet" type="text/css" href="/resources/css/updateBoard.css">
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script src="/resources/js/validation.js"></script>
</head>
<body>
<h2>게시글 수정</h2>
	<form id="updateArticleForm">
		<div class="form-group">
			<input type="hidden" name="rowNum" value="${rowNum}">
		</div>
		<div class="form-group">
			<input type="hidden" name="noticeSeq" value="${board.noticeSeq}" readonly>
		</div>
		<div class="form-group">
			<input type="text" name="memberId" value="${board.memberId}" readonly>
		</div>
		<div class="form-group">
			<input type="password" name="password" value="${board.password}" readonly>
		</div>
		<div class="form-group">
			<input type="text" name="title" id="title" value="${board.title}" placeholder="제목" required>
        	<span id="titleError"  class="error"></span>
		</div>
		<div class="form-group">
	        <textarea name="content" id="content" cols="30" rows="10" placeholder="내용" required>${board.content}</textarea>
        	<span id="contentError"  class="error"></span>
		</div>
		<input type="submit" id="updateButton" value="수정📝" >
		<input type="button" id="cancelButton" value="취소❎">
	</form>
	
	<script>
		$(document).ready(function() {
            $('#updateButton').prop('disabled', true);

            $('#title').on('blur', function() {
                validateField('title', $(this).val(), '/board/validateField', displayFieldError, 'updateArticleForm', 'updateButton');
            });

            $('#content').on('blur', function() {
                validateField('content', $(this).val(), '/board/validateField', displayFieldError, 'updateArticleForm', 'updateButton');
            });
            
			$('#updateArticleForm').on('submit', function(e) {
		        e.preventDefault();
		        var noticeSeq = '${board.noticeSeq}';
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
               				window.location.href = '/board/viewDetail?noticeSeq=' + noticeSeq + '&page=' + page + '&rowNum=' + rowNum;
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
	            	window.location.href = '/board/updateArticleForm?noticeSeq=' + noticeSeq + '&page=' + page + '&rowNum=' + rowNum;
	            }
           });

	        $('#cancelButton').on('click', function(e) {
		    	var noticeSeq = '${board.noticeSeq}';
		    	var page = '${page}';
	        	var rowNum = '${rowNum}';
		    	
		     	window.location.href = '/board/viewDetail?noticeSeq=' + noticeSeq + '&page=' + page + '&rowNum=' + rowNum;
		    });
	    });
	</script>
</body>
</html>