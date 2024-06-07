<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SaveBoard</title>
<link rel="stylesheet" type="text/css" href="/resources/css/saveBoard.css">
</head>
<body>
<h2>게시글 작성</h2>
	<form action="/board/save" method="post">
		<input type="text" name="writer" placeholder="작성자"  required>
		<input type="password" name="password" placeholder="비밀번호" required>
		<input type="text" name="title" placeholder="제목" required>
		<textarea name="content" cols="30" rows="10" placeholder="내용을 입력하세요" required></textarea>
		<input type="submit" value="저장💾">
		<input type="button" value="취소❎" onclick="cancelFn()">
	</form>
</body>
<script>
	const cancelFn = () => {
		const page = '${page}';
	    location.href = "/board/paging?page=" + page;
	    
	}
</script>
</html>