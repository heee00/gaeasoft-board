<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>BoardList</title>
	<link rel="stylesheet" type="text/css" href="/resources/css/boardList.css">
</head>
<body>
<h2>게시판</h2>
	 <table class="board-table">
         <thead>
	        <tr>
	            <th>No.</th>
	            <th>제목</th>
	            <th>작성자</th>
	            <th>작성일</th>
	            <th>조회수</th>
	        </tr>
	    </thead>
        <tbody>
	        <c:forEach items="${boardList}" var="boardList">
	            <tr>
	                <td>${boardList.rowNum}</td>
	                <td>
	                    <a href="/board?id=${boardList.id}">${boardList.title}</a>
	                </td>
	                <td>${boardListwriter}</td>
    				<td><fmt:formatDate value="${boardList.writeTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
	                <td>${boardList.views}</td>
	            </tr>
	        </c:forEach>
	    </tbody>
	    <tfoot>
	        <tr>
	            <td colspan="5">
	                <button class="link-button" id="writeButton">작성✍🏻</button>
	            </td>
	        </tr>
	    </tfoot>
    </table>
    
    <script>
	    $(document).ready(function() {
	        $('#writeButton').on('click', function(e) {
	            window.location.href = '/board/saveArticleForm';
	        });
	    });
	</script>
</body>
</html>