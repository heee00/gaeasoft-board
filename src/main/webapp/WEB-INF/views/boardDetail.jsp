<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>BoardDetail</title>
	<link rel="stylesheet" type="text/css" href="/resources/css/boardDetail.css">
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<h2>게시글 상세보기</h2>
	<table class="board-table">
		<tbody>
		   <tr>
		       <th class="board-no">No.</th>
		       <td>${rowNum}</td>
		   </tr>
		   <tr>
		       <th class="board-writer">작성자</th>
		       <td>${board.memberId}</td>
		   </tr>
		   <tr>
		       <th class="board-writetime">작성일</th>
		       <td><fmt:formatDate value="${board.writeTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
		   </tr>
		   <tr>
		       <th class="board-hits">조회수</th>
		       <td>${board.views}</td>
		   </tr>
		   <tr>
		       <th class="board-title">제목</th>
		       <td>${board.title}</td>
		   </tr>
		   <tr>
		       <th class="board-content">내용</th>
		       <td><textarea cols="30" rows="10" readonly>${board.content}</textarea></td>
		    </tr>
		</tbody>
	    <tfoot>
	        <tr>
	            <td colspan="5">
					<button class="link-button" id="listButton">목록🏠</button>
					<c:if test="${board.memberId eq sessionScope.loginId}">
	                    <button class="link-button" id="updateButton">수정📝</button>
	                    <button class="link-button" id="deleteButton">삭제🗑️</button>
	                </c:if>
				</td>
	        </tr>
		</tfoot>
	</table>
	
	<script>
	  $(document).ready(function() {
          $('#listButton').on('click', function(e) {
              var page = '${page}';
              window.location.href = '/board/pagingList?page=' + page;
          });

          $('#updateButton').on('click', function(e) {
              var noticeSeq = '${board.noticeSeq}';
              var page = '${page}';
	      	  var rowNum = '${rowNum}';
              window.location.href = '/board/updateArticleForm?noticeSeq=' + noticeSeq + '&page=' + page + '&rowNum=' + rowNum;
          });

          $('#deleteButton').on('click', function(e) {
              var noticeSeq = '${board.noticeSeq}';
              var page = '${page}';
	      	  var rowNum = '${rowNum}';
              var isConfirmed = confirm("정말로 삭제하시겠습니까?");
              
              if (isConfirmed) {
                  window.location.href = '/board/deleteArticle?noticeSeq=' + noticeSeq;
              } else {
                  window.location.href = '/board/viewDetail?noticeSeq=' + noticeSeq + '&page=' + page + '&rowNum=' + rowNum;
              }
          });
      });
	</script>
</body>
</html>