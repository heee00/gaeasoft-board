package com.gaeasoft.project.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.tika.Tika;
import org.springframework.stereotype.Component;

@Component
public class FileValidator  {

	private static final Tika tika = new Tika();
	private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png", "gif", "pdf", "hwp", "doc", "docx", "ppt", "pptx", "xls", "xlsx", "csv", "txt");
    private static final int MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB
    private static final Map<String, String> EXTENSION_TO_MIME_TYPE = new HashMap<>();
    
    static {
        // 파일 확장자와 MIME 타입 매핑 설정
    	 EXTENSION_TO_MIME_TYPE.put("jpg", "image/jpeg");
         EXTENSION_TO_MIME_TYPE.put("jpeg", "image/jpeg");
         EXTENSION_TO_MIME_TYPE.put("png", "image/png");
         EXTENSION_TO_MIME_TYPE.put("gif", "image/gif");
         EXTENSION_TO_MIME_TYPE.put("pdf", "application/pdf");
         EXTENSION_TO_MIME_TYPE.put("hwp", "application/x-hwp");
         EXTENSION_TO_MIME_TYPE.put("doc", "application/msword");
         EXTENSION_TO_MIME_TYPE.put("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
         EXTENSION_TO_MIME_TYPE.put("ppt", "application/vnd.ms-powerpoint");
         EXTENSION_TO_MIME_TYPE.put("pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation");
         EXTENSION_TO_MIME_TYPE.put("xls", "application/vnd.ms-excel");
         EXTENSION_TO_MIME_TYPE.put("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
         EXTENSION_TO_MIME_TYPE.put("csv", "text/csv");
         EXTENSION_TO_MIME_TYPE.put("txt", "text/plain");
    }
    
    // 파일 이름의 확장자가 허용된 확장자 목록에 있는지 확인
    public boolean isAllowedExtension(String fileName, List<String> allowedExtensions) {
        if (fileName == null) {
            return false;
        }
        String fileExtension = getFileExtension(fileName);
        return (allowedExtensions != null ? allowedExtensions : ALLOWED_EXTENSIONS).contains(fileExtension);
    }
    
    // 파일 크기가 최대 허용 크기 이내인지 확인
    public boolean isAllowedFileSize(long fileSize) {
        return fileSize <= MAX_FILE_SIZE;
    }
    
    // 파일의 MIME 타입이 허용된 MIME 타입 목록에 있는지 확인
    public boolean isAllowedExtensions(InputStream fileStream, String fileName, List<String> allowedExtensions) {
    	 try {
    		 String mimeType = tika.detect(fileStream);
             String fileExtension = getFileExtension(fileName);
             return isAllowedMimeType(mimeType) && isExtensionMatchingMimeType(fileExtension, mimeType);
         } catch (IOException e) {
             return false;
         }
    }
    
    // 허용된 MIME 타입 목록에 있는지 확인
    private boolean isAllowedMimeType(String mimeType) {
        List<String> ALLOWED_MIME_TYPES = Arrays.asList("image/jpeg", "image/png", "image/gif", "application/pdf", "application/x-hwp",
                                                        "application/msword", "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
                                                        "application/vnd.ms-excel", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                                                        "text/csv", "text/plain");
        return ALLOWED_MIME_TYPES.contains(mimeType);
    }
    
    // 파일 확장자와 MIME 타입이 일치하는지 확인
    private boolean isExtensionMatchingMimeType(String fileExtension, String mimeType) {
        return EXTENSION_TO_MIME_TYPE.getOrDefault(fileExtension, "").equals(mimeType);
    }

    // 파일 이름에서 확장자를 추출
    private String getFileExtension(String fileName) {
    	int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex == -1 || dotIndex == fileName.length() - 1) {
            return ""; // No valid extension found
        }
        return fileName.substring(dotIndex + 1).toLowerCase();
    }
    
    // 파일 유효성 검사
    public void  validateFile(String fileName, long fileSize, InputStream fileStream, List<String> allowedExtensions) throws FileValidationException {
    	if (allowedExtensions == null || allowedExtensions.isEmpty()) {
            throw new FileValidationException("확장자를 입력해주세요.");
        } else if (!isAllowedExtension(fileName, allowedExtensions)) {
        	throw new FileValidationException("허용되지 않는 파일 형식입니다.");
        } else if (!isAllowedFileSize(fileSize)) {
        	throw new FileValidationException("파일 크기가 너무 큽니다. 최대 파일 크기는 10MB입니다.");
        } else if (!isAllowedExtensions(fileStream, fileName, allowedExtensions)) {
        	throw new FileValidationException("확장자와 MIME 타입이 일치하지 않습니다.");
        }
 	}
 	
 	// 파일 저장 이름 설정
	public String setFileName(String originalFileName, String savePath) throws IOException {
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf(".")).toLowerCase();
        String storedFileName = UUID.randomUUID().toString() + fileExtension;
    	// 원본 파일명에서 확장자 추출
    	try {
	        @SuppressWarnings("unused")
			File file = new File(savePath + storedFileName);
        } catch (Exception e) {
            return "파일 저장 중 오류가 발생하였습니다.";
        }

        return storedFileName;
   }
    
}
