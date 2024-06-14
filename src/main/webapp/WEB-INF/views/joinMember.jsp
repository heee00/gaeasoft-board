<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <title>JoinMember</title>
    <link rel="stylesheet" type="text/css" href="/resources/css/joinMember.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
    <form id="joinMemberForm">
    	<div class="form-group">
	        <input type="text" name="name" id="name" placeholder="이름" required>
	         <span id="nameError"></span>
    	</div>
    	<div class="form-group">
	        <input type="text" name="email" id="email" placeholder="example@example.com" required>
	        <span id="emailError"></span>
    	</div>
    	<div class="form-group">
	        <input type="text" name="id" id="id" placeholder="아이디" required>
	        <span id="idError"></span>
    	</div>
    	<div class="form-group">
	        <input type="password" name="password" id="password" placeholder="비밀번호" required>
	        <span id="passwordError"></span>
    	</div>
    	<div class="form-group">
	        <input type="password" name="passwordCheck" id="passwordCheck" placeholder="비밀번호 확인" required>
       		<span id="passwordCheckError"></span>
    	</div>
        <input type="submit" value="회원가입">
	    <input type="button" id="cancelButton" value="취소">
    </form>
    
    <script>
	    $(document).ready(function() {
	    	$('#name').on('blur', function(e) {
				var name = document.getElementById("name").value;
	    	    var nameError = document.getElementById("nameError");
	    	   
	    	    if (name === '' || name.length <= 50) {
	    	    	nameError.innerHTML = '';
		   	        return;
		   	    }

	    	    if (name.length > 50) {
	    	    	nameError.style.color = "red";
	    	    	nameError.innerHTML = "이름은 50자 이하이어야 합니다.";
		            return;
		        }
	    	});
	    	
			$('#email').on('blur', function(e) {
				var email = document.getElementById("email").value;
		   		var emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
		   		var emailError = document.getElementById("emailError");
		        
		   		if (email === '') {
		   			emailError.innerHTML = '';
		   	        return;
		   	    }

		   	    if (!emailPattern.test(email)) {
		   	    	emailError.style.color = "red";
		   	    	emailError.innerHTML = "유효한 이메일 형식이 아닙니다";
		   	        return;
		   	    }
		   	    
		   	 	if (email.length > 100) {
			   	 	emailError.style.color = "red";
			   		emailError.innerHTML = "이메일은 100자 이하이어야 합니다.";
		            return;
		        }
	   		
	           $.ajax({
	           	type: "post",
	               url: "/member/emailCheck",
	               data: {
	                   "email": email
	               },
	               success: function(response) {
	                   if (response == "false") {
	                	  emailError.style.color = "red";
	                	  emailError.innerHTML = "이미 사용중인 이메일입니다";
	                   } else {
	                	   emailError.innerHTML = '';
	   		   	        	return;
	                   }
	               },
	               error: function(xhr, status, err) {
	                   console.error('AJAX Error: ' + status + err);
	               }
	           });
			});
			
			$('#id').on('blur', function(e) {
				var id = document.getElementById("id").value;
		   		var idPattern = /^[ㄱ-ㅎ가-힣a-zA-Z0-9]*$/;
	    	    var idError = document.getElementById("idError");
	    	    
	    	    if (id === '') {
	    	    	idError.innerHTML = '';
		   	        return;
		   	    }

		   	    if (!idPattern.test(id)) {
		   	    	idError.style.color = "red";
		   	    	idError.innerHTML = "아이디에는 특수 문자를 포함할 수 없습니다.";
		   	        return;
		   	    }
		   	    
		   		if (id.length > 50) {
		   			idError.style.color = "red";
		   			idError.innerHTML = "아이디는 50자 이하이어야 합니다.";
		            return;
		        }

	            $.ajax({
	            	type: "post",
	                url: "/member/idCheck",
	                data: {
	                    "id": id
	                },
	                success: function(response) {
	                    if (response == "false") {
	                    	idError.style.color = "red";
	                    	idError.innerHTML = "이미 사용중인 아이디입니다";
	                    } else {
	                    	idError.innerHTML = '';
	   		   	        	return;
	                   }
	                },
	                error: function(xhr, status, err) {
	                    console.error('AJAX Error: ' + status + err);
	                }
	            });
			});
			
			$('#password').on('blur', function(e) {
				var password = document.getElementById('password').value;
	            var passwordError = document.getElementById('passwordError');

	            if (password === '' || password.length <= 20) {
	            	passwordError.innerHTML = '';
		   	        return;
		   	    }
	            
				if (password.length > 20) {
					passwordError.style.color = "red";
					passwordError.innerHTML = "비밀번호는 20자 이하이어야 합니다.";
		            return;
		        }
			});

			$('#passwordCheck').on('blur', function(e) {
				var password = document.getElementById('password').value;
	            var passwordCheck = document.getElementById('passwordCheck').value;
	            var passwordCheckError = document.getElementById('passwordCheckError');

	            if (passwordCheck === '' || password === passwordCheck) {
	            	passwordCheckError.innerHTML = '';
		   	        return;
		   	    }

	            if (password !== passwordCheck) {
	            	passwordCheckError.style.color = 'red';
	            	passwordCheckError.innerHTML = '비밀번호가 일치하지 않습니다.';
	                return ;
	            }
			});
			
	    	$('#joinMemberForm').on('submit', function(e) {
	    		e.preventDefault();
	            var formData = $(this).serialize();
	            
	            var nameError = document.getElementById("nameError").innerHTML;
	            var emailError = document.getElementById("emailError").innerHTML;
	            var idError = document.getElementById("idError").innerHTML;
	            var passwordError = document.getElementById("passwordError").innerHTML;
	            var passwordCheckError = document.getElementById("passwordCheckError").innerHTML;

	            if (nameError !== '' || emailError !== '' || idError !== '' || passwordError !== '' || passwordCheckError !== '') {
	                alert("에러 메세지를 확인해 주세요.");
	                return;
	            }
	
	    		$.ajax({
	    			url: '/member/join',
	    			method: 'post',
	                data: formData,
	                success: function(response) {
	                	alert("회원이 되신 것을 환영합니다.");
	                    window.location.href = '/member/loginForm';
	                },
	                error: function(xhr, status, err) {
	                    console.error('AJAX Error: ' + status + err);
	                }
	    		});
	    	});
	    	
	    	$('#cancelButton').on('click', function(e) {
	       		window.location.href = '/member/loginForm';
		    });
	    });
    </script>
</body>
</html>