<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>BoardDetail</title>
<link rel="stylesheet" type="text/css" href="/resources/css/boardDetail.css">
</head>
<body>
<h2>게시글 상세보기</h2>
	<table class="board-table">
	    <tr>
	       <th class="board-no">No.</th>
	       <td>${board.id}</td>
	   </tr>
	   <tr>
	       <th class="board-writer">작성자</th>
	       <td>${board.writer}</td>
	   </tr>
	   <tr>
	       <th class="board-writetime">작성일</th>
	       <td>${board.writeTime}</td>
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
	    <tfoot>
	        <tr>
	            <td colspan="5">
					<button class="link-button" onclick="listFn()">목록🏠</button>
					<button class="link-button" onclick="updateFn()">수정📝</button>
					<button class="link-button" onclick="deleteFn()">삭제🗑️</button>
				</td>
	        </tr>
		</tfoot>
	</table>
</body>
<script>
    const listFn = () => {
    	const page = '${page}';
        location.href = "/board/paging?page=" + page;
    }
    const updateFn = () => {
    	const page = '${page}';
        const id = '${board.id}';
        location.href = "/board/update?id=" + id + "&page=" + page;
    }
    const deleteFn = () => {
	    const page = '${page}'
    	const id = '${board.id}';
    	const isConfirmed = confirm("정말로 삭제하시겠습니까?");
    		
    	if (isConfirmed) {
   			location.href = "/board/delete?id=" + id;
   	    } else {
   	        location.href = "/board?id=" + id + "&page=" + page;
   	    }
    }
</script>
</html>