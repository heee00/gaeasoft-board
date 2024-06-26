// 파일 확장자 검사 함수
var FileUpload = {
    ALLOWED_EXTENSIONS: ['jpg', 'jpeg', 'png', 'gif', 'pdf', 'doc', 'docx', 'xls', 'xlsx', 'txt'],

    isAllowedExtension: function(fileName) {
        if (!fileName) {
            return false;
        }
        var fileExtension = fileName.split('.').pop().toLowerCase();
        return FileUpload.ALLOWED_EXTENSIONS.includes(fileExtension);
    }
};