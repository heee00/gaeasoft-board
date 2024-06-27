<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <title>UpdateMember</title>
    <link rel="stylesheet" type="text/css" href="/resources/css/updateMember.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script src="/resources/js/validation.js"></script>
   	<script src="/resources/js/addressSearch.js"></script>
</head>
<body>
<h2>회원 수정</h2>
    <form id="updateMemberForm">
    	<div class="form-group">
	        <input type="hidden" name="email" id="email"  value="${member.email}">
    	</div>
    	<div class="form-group">
	        <input type="hidden" name="memberId" id="memberId" value="${member.memberId}">
    	</div>
    	<div class="form-group">
	        <input type="text" name="name" id="name" value="${member.name}" required>
	         <span id="nameError"  class="error"></span>
    	</div>
    	<div class="form-group">
	        <input type="password" name="password" id="password" placeholder="새로운 비밀번호" required>
	        <span id="passwordError"  class="error"></span>
    	</div>
    	<div class="form-group">
	        <input type="password" name="passwordCheck" id="passwordCheck" placeholder="비밀번호 확인" required>
       		<span id="passwordCheckError" class="error"></span>
    	</div>
    	<div class="form-group">
   	 	    <div class="input-container">
			    <input type="text" name="address" id="address" placeholder="주소 검색(도로명, 지번명)" required>
				<input type="button" id="searchAddressButton" value="주소 검색">
		    </div>
       		<span id="addressError" class="error"></span>
		</div>
    	<div class="form-group">
    		<input type="text" name="detailAddress" id="detailAddress" placeholder="상세주소" required>
       		<span id="detailAddressError" class="error"></span>
    	</div>
        <div id="addressList">
	        <input type="hidden" name="zipNo" id="zipNo">
			<input type="hidden" name="roadAddr" id="roadAddr">
			<input type="hidden" name="jibunAddr" id="jibunAddr">
        </div>
        <input type="submit" id="updateButton" value="수정📝" >
		<input type="button" id="cancelButton" value="취소❎">
		
		 <!-- Modal -->
	    <div id="addressModal">
	        <div id="modalContent">
	            <span class="close">&times;</span>
	            <div id="modalAddressList"></div>
	        </div>
	    </div>
    </form>
    
    <script>
	    $(document).ready(function() {
            $('#updateButton').prop('disabled', true);

            $('#name').on('blur', function() {
                validateField('name', $(this).val(), '/member/validateField', displayFieldError, 'updateMemberForm', 'updateButton');
	    	});
			
            $('#password').on('blur', function() {
                validateField('password', $(this).val(), '/member/validateField', displayFieldError, 'updateMemberForm', 'updateButton');
			});
            
            $('#address').on('blur', function() {
                validateField('address', $(this).val(), '/member/validateField', displayFieldError, 'updateMemberForm', 'updateButton');
			});
			
			$('#detailAddress').on('blur', function() {
                validateField('detailAddress', $(this).val(), '/member/validateField', displayFieldError, 'updateMemberForm', 'updateButton');
			});

			$('#passwordCheck').on('blur', function(e) {
				var password = $('#password').val();
		        var passwordCheck = $(this).val();
		        var passwordCheckError = $('#passwordCheckError');

		        if (passwordCheck.length > 0 && password === passwordCheck) {
		            passwordCheckError.empty();
                    $('#updateButton').prop('disabled', false);
		        } else {
		        	if (passwordCheck.length == 0) {
			            passwordCheckError.html('비밀번호를 확인해 주세요.').css('color', 'red');
	                    $('#updateButton').prop('disabled', true);
		        	} else {
		            	passwordCheckError.html('비밀번호가 일치하지 않습니다.').css('color', 'red');
	                    $('#updateButton').prop('disabled', true);
		       		}
		        }
			});
			
	    	$('#updateMemberForm').on('submit', function(e) {
    		 	e.preventDefault();
	    		var id = '${loginId}';
	   	        var formData = $(this).serialize();
	   	        var isConfirmed = confirm("수정하시겠습니까?");
	
	   	        if (isConfirmed) {
	   	            $.ajax({
	   	                url: '/member/updatePersonalInfo',
	   	                method: 'post',
	   	                data: formData,
	   	                success: function(response) {
	   	                    window.location.href = '/member/viewPersonalInfo?memberId=' + id;
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
	    	
	    	$('#cancelButton').on('click', function(e) {
	    		var id = '${loginId}';
	            window.location.href = '/member/viewPersonalInfo?memberId=' + id;
		    });
	    });
    </script>
</body>
</html>