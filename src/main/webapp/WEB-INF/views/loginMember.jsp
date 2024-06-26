<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <title>LoginMember</title>
    <link rel="stylesheet" type="text/css" href="/resources/css/loginMember.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<h2>로그인</h2>
    <form id="loginMemberForm">
    	<div class="form-group">
	    	<input type="text" name="memberId" placeholder="아이디" required>
	    </div>
	    <div class="form-group">
	        <input type="password" name="password" placeholder="비밀번호" required>
	    </div>
	        <input type="submit" value="로그인">
	        <input type="button" id="joinButton" value="회원가입">
    </form>
    
    <script>
	    $(document).ready(function() {
	    	$('#loginMemberForm').on('submit', function(e) {
	    		e.preventDefault();
	            var formData = $(this).serialize();
	
	    		$.ajax({
	    			url: '/member/login',
	    			method: 'post',
	                data: formData,
	                success: function(response) {
	                	 if (response.status === 'success') {
	                         window.location.href = '/board/pagingList?page=1&startDate=&endDate=&searchKeyword=&searchOption=';
	                     } else {
	                         alert(response.message);
	                     }
	                },
	                error: function(xhr, status, err) {
	                    console.error('AJAX Error: ' + status + err);
	                }
	    		});
	    	});
	    	
	    	$('#joinButton').on('click', function(e) {
	       		window.location.href = '/member/joinForm';
		    });
		});
    </script>
</body>
</html>