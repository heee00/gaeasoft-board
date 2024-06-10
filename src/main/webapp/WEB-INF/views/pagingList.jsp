<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>PagingList</title>
<link rel="stylesheet" type="text/css" href="/resources/css/pagingList.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
	<div>
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
		        <c:forEach items="${pagingList}" var="pagingList">
		            <tr>
		                <td>${pagingList.rowNum}</td>
		                <td>
	                    	<a href="/board?id=${pagingList.id}&page=${paging.page}&rowNum=${pagingList.rowNum}">${pagingList.title}</a>
		                </td>
		                <td>${pagingList.writer}</td>
	    				<td><fmt:formatDate value="${pagingList.writeTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
		                <td>${pagingList.views}</td>
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
	</div>
	
	<div class="paging-navigation">
	    <c:choose>
	        <%-- 현재 페이지가 1페이지면 이전 글자만 보여줌 --%>
	        <c:when test="${paging.page<=1}">
	            <span class="beforePage">[◀️이전]</span>
	        </c:when>
	        <c:otherwise>
	            <a href="/board/paging?page=${paging.page-1}">[◀️이전]</a>
	        </c:otherwise>
	    </c:choose>
	
	    <%--  for(int i=startPage; i<=endPage; i++)      --%>
	    <c:forEach begin="${paging.startPage}" end="${paging.endPage}" var="i" step="1">
	        <c:choose>
	            <%-- 요청한 페이지에 있는 경우 현재 페이지 번호는 텍스트만 보이게 --%>
	            <c:when test="${i eq paging.page}">
	                <span class="currentPage">${i}</span>
	            </c:when>
	            <c:otherwise>
	                <a href="/board/paging?page=${i}">${i}</a>
	            </c:otherwise>
	        </c:choose>
	    </c:forEach>
	
	    <c:choose>
	        <c:when test="${paging.page>=paging.maxPage}">
	            <span class="nextPage">[다음▶️]</span>
	        </c:when>
	        <c:otherwise>
	                <a href="/board/paging?page=${paging.page+1}">[다음▶️]</a>
	        </c:otherwise>
	    </c:choose>
	</div>
	
	<script>
	    $(document).ready(function() {
	        $('#writeButton').on('click', function(e) {
	            window.location.href = '/board/save';
	        });
	    });
	</script>
</body>
</html>