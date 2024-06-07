<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>update</title>
<link rel="stylesheet" type="text/css" href="/resources/css/updateBoard.css">
</head>
<body>
<h2>게시글 수정</h2>
	<form action="/board/update?id=${board.id}&page=${page}" method="post" name="update">
		<input type="hidden" name="id" value="${board.id}" readonly>
		<input type="text" name="boardWriter" value="${board.boardWriter}" placeholder="작성자" readonly>
		<input type="password" name="boardPassword" id="boardPassword" placeholder="비밀번호" required>
		<input type="text" name="boardTitle" value="${board.boardTitle}" required>
        <textarea name="boardContent" cols="30" rows="10" required>${board.boardContent}</textarea>
		<input type="button" value="수정📝" onclick="updateFn()">
		<input type="button" value="취소❎" onclick="cancelFn()">
	</form>
</body>
<script>
    const updateFn = () => {
    	const form = document.forms['update'];
        
    	if (form.reportValidity()) {
            const passwordInput = document.getElementById("boardPassword").value;
            const passwordDB = '${board.boardPassword}';
            
            if (passwordInput == passwordDB) {
                form.submit();
            } else {
                alert("비밀번호가 일치하지 않습니다");
            }
        }
    }
    
    const cancelFn = () => {
    	const page = '${page}';
    	 const id = '${board.id}';
    	location.href = "/board?id=" + id + "&page=" + page;
    }
</script>
</html>