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
		<button class="link-button" id="logoutButton">로그아웃🚪</button>
		<button class="link-button" id="infoButton">회원 정보🔦</button>

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
	        	<c:if test="${empty pagingList}">
			        <tr>
			            <td colspan="5">일치하는 글이 없습니다.</td>
			        </tr>
			    </c:if>
		        <c:forEach items="${pagingList}" var="pagingList">
		            <tr>
		                <td>${pagingList.rowNum}</td>
		                <td>
	                    	<a href="/board/viewDetail?noticeSeq=${pagingList.noticeSeq}&page=${paging.page}&rowNum=${pagingList.rowNum}">${pagingList.title}</a>
		                </td>
		                <td>${pagingList.memberId}</td>
	    				<td><fmt:formatDate value="${pagingList.writeTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
		                <td>${pagingList.views}</td>
		            </tr>
		        </c:forEach>
		    </tbody>
		    <tfoot>
		        <tr>
		            <td colspan="5">
		                <button class="link-button" id="writeButton">작성✍🏻</button>
				        <form id="searchForm">
				        	<input type="date" name ="startDate" value="${param.startDate}">
				        	<input type="date" name="endDate" value="${param.endDate}">
				            <input type="text" name="searchKeyword" placeholder="검색어를 입력하세요" value="${param.searchKeyword}">
				            <select name="searchOption">
				                <option value="Title" ${param.searchOption == 'Title' ? 'selected' : ''}>제목</option>
				                <option value="All" ${param.searchOption == 'All' ? 'selected' : ''}>제목+내용</option>
				            </select>
		            		<input type="submit"  id="searchButton" value="검색🔍">
				        </form>
		            </td>
		        </tr>
		    </tfoot>
	    </table>
	</div>
	
	<div class="paging-navigation">
        <c:choose>
            <c:when test="${paging.page <= 1}">
                <span class="beforePage">[◀️이전]</span>
            </c:when>
            <c:otherwise>
                <a href="/board/pagingList?page=${paging.page - 1}&keyword=${param.keyword}&option=${param.option}">[◀️이전]</a>
            </c:otherwise>
        </c:choose>
    
        <c:forEach begin="${paging.startPage}" end="${paging.endPage}" var="i" step="1">
            <c:choose>
                <c:when test="${i eq paging.page}">
                    <span class="currentPage">${i}</span>
                </c:when>
                <c:otherwise>
                    <a href="/board/pagingList?page=${i}&keyword=${param.keyword}&option=${param.option}">${i}</a>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    
        <c:choose>
            <c:when test="${paging.page >= paging.maxPage}">
                <span class="nextPage">[다음▶️]</span>
            </c:when>
            <c:otherwise>
                <a href="/board/pagingList?page=${paging.page + 1}&keyword=${param.keyword}&option=${param.option}">[다음▶️]</a>
            </c:otherwise>
        </c:choose>
	</div>
	
	<script>
	    $(document).ready(function() {
	    	$('h2').on('click', function() {
                window.location.href = '/board/pagingList?page=1';
	        });
	    	
	    	$('#infoButton').on('click', function(e) {
	    		var id = '${loginId}';
	            window.location.href = '/member/viewPersonalInfo?memberId=' + id;
	        });
	    	
	    	$('#logoutButton').on('click', function(e) {
	            var page = '${paging.page}';
	    		var isConfirmed = confirm("정말 로그아웃하시겠습니까?");
				
	    		if (isConfirmed) {
		            window.location.href = '/member/logout';
	    		} else {
	                window.location.href = '/board/pagingList?page=' + page;
	    		}
	        });
	    	
	        $('#writeButton').on('click', function(e) {
	            window.location.href = '/board/saveArticleForm';
	        });
	        
	    	$('#searchForm').on('submit', function(e) {
	    		e.preventDefault();
	    		
    			var page = '${paging.page}';
	            var startDate = $('input[name="startDate"]').val();
	            var endDate = $('input[name="endDate"]').val();
	            var searchKeyword = $('input[name="searchKeyword"]').val();
	            var searchOption = $('select[name="searchOption"]').val();

                $.ajax({
                    url: '/board/pagingList',
                    method: 'get',
                    data: {
                        page: page,
                        startDate: startDate,
                        endDate: endDate,
                        searchKeyword: searchKeyword,
                        searchOption: searchOption
                    },
                    success: function(response) {
                        window.location.href = '/board/pagingList?page=' + page + '&startDate=' + startDate + '&endDate=' + endDate + '&searchKeyword=' + searchKeyword + '&searchOption=' + searchOption;
                    },
                    error: function(xhr) {
                    	if (xhr.status === 400) {
                            var errors = xhr.responseJSON;
                            displayErrors(errors);
                        } else {
                            console.error('AJAX Error: ' + status + err);
                        }
                    }
                });
	        });
	    });
	</script>
</body>
</html>