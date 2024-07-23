// 파일 확장자 검사 함수
var FileUpload = {
    ALLOWED_EXTENSIONS: ['jpg', 'jpeg', 'png', 'gif', 'pdf', 'hwp', 'doc', 'docx', 'xls', 'xlsx', 'csv', 'txt'],
    MAX_FILE_SIZE: 10 * 1024 * 1024, //10MB
    MIME_TYPE: ['image/jpeg', 'image/png', 'image/gif', 'application/pdf', 'application/x-hwp',
			                'application/msword', 'application/vnd.openxmlformats-officedocument.wordprocessingml.document',
			                'application/vnd.ms-excel', 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
			                'text/csv', 'text/plain'],

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
    
    isAllowedMimeType: function(fileType) {
    	  if (!fileType) {
            return false;
        }
        return FileUpload.MIME_TYPE.includes(fileType);
    },

  	checkFileHeader: function(file, callback) {
        var fileReader = new FileReader();
        fileReader.onloadend = function(e) {
            var arr = (new Uint8Array(e.target.result)).subarray(0, 4);
            var header = "";
            for (var i = 0; i < arr.length; i++) {
                header += arr[i].toString(16);
            }

            var fileExtension = file.name.split('.').pop().toLowerCase();
            var isValidHeader = false;

            switch (fileExtension) {
                case "jpg":
                case "jpeg":
                    isValidHeader = ["ffd8ffe0", "ffd8ffe1", "ffd8ffe2", "ffd8ffe3", "ffd8ffe8"].includes(header);
                    break;
                case "png":
                    isValidHeader = header === "89504e47";
                    break;
                case "gif":
                    isValidHeader = header === "47494638";
                    break;
                case "pdf":
                    isValidHeader = header === "25504446";
                    break;
                case "doc":
                    isValidHeader = header === "d0cf11e0";
                    break;
                case "docx":
                case "xlsx":
                    isValidHeader = header === "504b0304";
                    break;
                case "xls":
                    isValidHeader = header === "d0cf11e0";
                    break;
                case "txt":
                case "csv":
                    isValidHeader = true;
                    break;
                default:
                    isValidHeader = false;
            }

            callback(isValidHeader);
        };
        fileReader.readAsArrayBuffer(file.slice(0, 4));
    },
    
	 validateFiles: function(files, callback) {
        var results = [];
        var totalFiles = files.length;
        var processedFiles = 0;

        if (totalFiles === 0) {
            callback(true); // No files to validate
            return;
        }
        
         function handleFileResult(file, isValid, message) {
            results.push({
                file: file,
                isValid: isValid,
                message: message
            });
            processedFiles++;
            if (processedFiles === totalFiles) {
                // Only when all files are processed, handle final results
                var isValidOverall = results.every(r => r.isValid);
                if (!isValidOverall) {
                    results.forEach(r => {
                        if (!r.isValid) {
                            alert(r.file.name + " " + r.message);
                        }
                    });
                }
                callback(isValidOverall);
            }
        }

        for (var i = 0; i < files.length; i++) {
            var file = files[i];
            var fileExtension = file.name.split('.').pop().toLowerCase();
            var fileType = file.type;

           if (!FileUpload.isAllowedExtension(file.name)) {
                handleFileResult(file, false, "허용되지 않는 파일 형식입니다.");
            } else if (!FileUpload.isAllowedFileSize(file.size)) {
                handleFileResult(file, false, "파일 크기가 허용된 최대 크기 10MB를 초과했습니다.");
            } else if (!FileUpload.isAllowedMimeType(file.type)) {
                handleFileResult(file, false, "파일의 MIME 타입이 올바르지 않습니다.");
            } else {
                FileUpload.checkFileHeader(file, function(isValidHeader) {
                    if (isValidHeader) {
                        handleFileResult(file, true, "");
                    } else {
                        handleFileResult(file, false, "파일의 형식이 올바르지 않습니다.");
                    }
                });
            }
        }
    }
};