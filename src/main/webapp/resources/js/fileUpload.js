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
        var isValid = true;
        var totalFiles = files.length;
        var processedFiles = 0;

        for (var i = 0; i < files.length; i++) {
            var file = files[i];
            var fileExtension = file.name.split('.').pop().toLowerCase();
            
            if (!FileUpload.isAllowedExtension(fileExtension)) {
                alert(fileExtension + " 파일은 허용되지 않은 형식입니다.");
				isValid = false;
               	callback(isValid);
                return;
            }
            if (!FileUpload.isAllowedFileSize(file.size)) {
                alert(file.name + " 파일의 크기가 허용된 최대 크기 10MB를 초과했습니다.");
                isValid = false;
                callback(isValid);
                return;
            }
            if(!FileUpload.isAllowedMimeType(file.type)) {
            	alert(file.type + "파일의 형식이 올바르지 않습니다.");
            	isValid = false;
                callback(isValid);
                return;
            }
	        
	        FileUpload.checkFileHeader(file, function(isValidHeader) {
                if (!isValidHeader) {
                    alert(file.name + " 파일의 형식이 올바르지 않습니다.");
                    isValid = false;
                }
                processedFiles++;
                if (processedFiles === totalFiles) {
                    callback(isValid);
                }
            });
        }

        if (totalFiles === 0) {
            callback(isValid);
        }
    }
};