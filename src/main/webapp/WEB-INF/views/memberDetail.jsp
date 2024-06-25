<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <title>MemberDetail</title>
    <link rel="stylesheet" type="text/css" href="/resources/css/memberDetail.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<h2>내 정보 보기</h2>
	<table class="member-table">
		<tbody>
		   <tr>
		       <th class="member-name">이름</th>
		       <td>${member.name}</td>
		   </tr>
		   <tr>
		       <th class="member-id">아이디</th>
		       <td>${member.memberId}</td>
		   </tr>
		   <tr>
		       <th class="member-id">이메일</th>
		       <td>${member.email}</td>
		   </tr>
		   <tr>
		       <th class="member-joinTime">가입일</th>
		       <td><fmt:formatDate value="${member.joinTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
		   </tr>
		   <tr>
		   		<th class="member-address">주소</th>
		   		<td>${member.address }</td>
		   </tr>
		</tbody>
    	<tfoot>
	        <tr>
	            <td colspan="5">
                    <button class="link-button" id="updateButton">수정📝</button>
                    <button class="link-button" id="deleteButton">탈퇴🗑️</button>
                    <button class="link-button" id="cancelButton">취소❎</button>
				</td>
	        </tr>
		</tfoot>
	</table>
    
    <script>
	    $(document).ready(function() {
	    	 $('#updateButton').on('click', function(e) {
	    		 var id = '${loginId}';
		         window.location.href = '/member/updatePersonalInfoForm?memberId=' + id;
	          });
	    	 
	    	 $('#deleteButton').on('click', function(e) {
   		 		var id = '${loginId}';
	            var isConfirmed = confirm("정말로 탈퇴하시겠습니까?");
              	if (isConfirmed) {
                    alert("탈퇴되었습니다.");
		         	window.location.href = '/member/deleteMember?memberId=' + id;
	            } else {
		            window.location.href = '/member/viewPersonalInfo?memberId=' + id;
	            }
	        });
	    	
	    	$('#cancelButton').on('click', function(e) {
	    		var page = '${page}';
	            window.location.href = '/board/pagingList?page=' + page;
		    });
	    });
    </script>
</body>
</html>