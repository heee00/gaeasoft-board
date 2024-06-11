<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <title>JoinMember</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
    <form id="joinMemberForm">
    	<div class="form-group">
	        <input type="text" name="name" placeholder="이름" required>
    	</div>
    	<div class="form-group">
	        <input type="email" name="email" placeholder="이메일" id="email" onblur="emailCheck()" required>
	        <span id="emailCheckResult"></span>
    	</div>
    	<div class="form-group">
	        <input type="text" name="id" placeholder="아이디" id="id" onblur="idCheck()" required>
	        <span id="idCheckResult"></span>
    	</div>
    	<div class="form-group">
	        <input type="password" name="password" placeholder="비밀번호" required>
    	</div>
        <input type="submit" value="회원가입">
    </form>
    
    <script>
	   	var emailCheck = () => {
	   		var email = document.getElementById("email").value;
	   		var checkResult = document.getElementById("emailCheckResult");
	           
	           $.ajax({
	           	type: "post",
	               url: "/member/emailCheck",
	               data: {
	                   "email": email
	               },
	               success: function(response) {
	                   if (response == "true") {
	                       emailCheckResult.style.color = "green";
	                       emailCheckResult.innerHTML = "사용 가능한 이메일입니다";
	                   } else {
	                       emailCheckResult.style.color = "red";
	                       emailCheckResult.innerHTML = "이미 사용중인 이메일입니다";
	                   }
	               },
	               error: function(xhr, status, err) {
	                   console.error('AJAX Error: ' + status + err);
	               }
	           });
	       };
	        
        var idCheck = () => {
    		var id = document.getElementById("id").value;
    		var checkResult = document.getElementById("idCheckResult");
            
            $.ajax({
            	type: "post",
                url: "/member/idCheck",
                data: {
                    "id": id
                },
                success: function(response) {
                    if (response == "true") {
                        idCheckResult.style.color = "green";
                        idCheckResult.innerHTML = "사용 가능한 아이디입니다";
                    } else {
                        idCheckResult.style.color = "red";
                        idCheckResult.innerHTML = "이미 사용중인 아이디입니다";
                    }
                },
                error: function(xhr, status, err) {
                    console.error('AJAX Error: ' + status + err);
                }
            });
        };
	        
	    $(document).ready(function() {
	    	$('#joinMemberForm').on('submit', function(e) {
	    		e.preventDefault();
	            var formData = $(this).serialize();
	
	    		$.ajax({
	    			url: '/member/join',
	    			method: 'post',
	                data: formData,
	                success: function(response) {
	                	alert("회원이 되신 것을 환영합니다.");
	                    window.location.href = '/member/login';
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