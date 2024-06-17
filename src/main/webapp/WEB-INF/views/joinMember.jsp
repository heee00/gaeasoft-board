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
	        <input type="text" name="name" id="name" placeholder="이름">
	         <span id="nameError"  class="error"></span>
    	</div>
    	<div class="form-group">
	        <input type="text" name="email" id="email" placeholder="example@example.com">
	        <span id="emailError"  class="error"></span>
    	</div>
    	<div class="form-group">
	        <input type="text" name="id" id="id" placeholder="아이디">
	        <span id="idError"  class="error"></span>
    	</div>
    	<div class="form-group">
	        <input type="password" name="password" id="password" placeholder="비밀번호">
	        <span id="passwordError"  class="error"></span>
    	</div>
    	<div class="form-group">
	        <input type="password" name="passwordCheck" id="passwordCheck" placeholder="비밀번호 확인">
       		<span id="passwordCheckError" class="error"></span>
    	</div>
        <input type="submit" id="joinButton" value="회원가입">
	    <input type="button" id="cancelButton" value="취소">
    </form>
    
    <script>
	    $(document).ready(function() {
            $('#joinButton').prop('disabled', true);

	    	$('#name').on('blur', function() {
	            validateField('name', $(this).val());
	    	});
	    	
			$('#email').on('blur', function() {
		        validateField('email', $(this).val());
			});
			
			$('#id').on('blur', function() {
		        validateField('id', $(this).val());
			});
			
			$('#password').on('blur', function() {
		        validateField('password', $(this).val());
			});

			$('#passwordCheck').on('blur', function(e) {
				var password = $('#password').val();
		        var passwordCheck = $(this).val();
		        var passwordCheckError = $('#passwordCheckError');

		        if (passwordCheck === '' || password === passwordCheck) {
		            passwordCheckError.empty();
		        } else {
		            passwordCheckError.html('비밀번호가 일치하지 않습니다.').css('color', 'red');
		        }
                validateForm();
			});
			
	    	$('#joinMemberForm').on('submit', function(e) {
    		 	e.preventDefault();
	   	        var formData = $(this).serialize();
	   	        var isConfirmed = confirm("가입하시겠습니까?");
	
	   	        if (isConfirmed) {
	   	            $.ajax({
	   	                url: '/member/join',
	   	                method: 'post',
	   	                data: formData,
	   	                success: function(response) {
	   	                    window.location.href = '/member/loginForm';
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
	   	        }
	    	});
	    	
	    	// 전체 유효성 검사
	    	function validateForm() {
	            var emailError = $('#emailError').text().trim();
	            var idError = $('#idError').text().trim();
	            var nameError = $('#nameError').text().trim();
	            var passwordError = $('#passwordError').text().trim();
	            var passwordCheckError = $('#passwordCheckError').text().trim();

	            if (emailError === '' && idError === '' && nameError === '' && passwordError === '' && passwordCheckError === '') {
	                $('#joinButton').prop('disabled', false);
	            } else {
	                $('#joinButton').prop('disabled', true);
	            }
	        }
	    	
	    	// 유효성 검사 함수
		    function validateField(fieldName, fieldValue) {
		    	$.ajax({
		            type: 'POST',
		            url: '/member/validateField',
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
		    	if (errors.name) {
		            $('#nameError').html('<div>' + errors.name[0] + '</div>').css('color', 'red');
		        } else {
		            $('#nameError').empty();
		        }
		        if (errors.email) {
		            $('#emailError').html('<div>' + errors.email[0] + '</div>').css('color', 'red');
		        } else {
		            $('#emailError').empty();
		        }
		        if (errors.id) {
		            $('#idError').html('<div>' + errors.id[0] + '</div>').css('color', 'red');
		        } else {
		            $('#idError').empty();
		        }
		        if (errors.password) {
		            $('#passwordError').html('<div>' + errors.password[0] + '</div>').css('color', 'red');
		        } else {
		            $('#passwordError').empty();
		        }
		    }
	    	
	    	$('#cancelButton').on('click', function(e) {
	       		window.location.href = '/member/loginForm';
		    });
	    });
    </script>
</body>
</html>