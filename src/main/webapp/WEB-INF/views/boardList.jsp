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
	        <c:forEach items="${boardList}" var="board">
	            <tr>
	                <td>${board.boardRowNum}</td>
	                <td>
	                    <a href="/board?id=${board.id}">${board.boardTitle}</a>
	                </td>
	                <td>${board.boardWriter}</td>
    				<td><fmt:formatDate value="${board.boardWriteTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
	                <td>${board.boardHits}</td>
	            </tr>
	        </c:forEach>
	    </tbody>
	    <tfoot>
	        <tr>
	            <td colspan="5">
	                <button class="link-button" onclick="location.href='/board/save'">작성✍🏻</button>
	            </td>
	        </tr>
	    </tfoot>
    </table>
</body>
</html>