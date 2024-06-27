// 파일 확장자 검사 함수
var FileUpload = {
    ALLOWED_EXTENSIONS: ['jpg', 'jpeg', 'png', 'gif', 'pdf', 'hwp', 'doc', 'docx', 'ppt', 'pptx', 'xls', 'xlsx', 'csv', 'txt'],
    MAX_FILE_SIZE: 10 * 1024 * 1024, //10MB

    isAllowedExtension: function(fileName) {
        if (!fileName) {
            return false;
        }
        var fileExtension = fileName.split('.').pop().toLowerCase();
        return FileUpload.ALLOWED_EXTENSIONS.includes(fileExtension);
    },

    isAllowedFileSize: function(fileSize) {
        return fileSize <= FileUpload.MAX_FILE_SIZE;
    },

    validateFiles: function(files) {
        for (var i = 0; i < files.length; i++) {
            var file = files[i];
            var fileExtension = file.name.split('.').pop().toLowerCase();
            
            if (!FileUpload.isAllowedExtension(fileExtension)) {
                alert(fileExtension + " 파일은 허용되지 않은 형식입니다.");
                return false;
            }
            if (!FileUpload.isAllowedFileSize(file.size)) {
                alert(file.name + " 파일의 크기가 허용된 최대 크기 10MB를 초과했습니다.");
                return false;
            }
        }
        return true;
    }
};