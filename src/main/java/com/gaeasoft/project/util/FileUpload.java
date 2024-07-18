package com.gaeasoft.project.util;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tika.Tika;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUpload {

	private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png", "gif", "pdf", "hwp", "doc", "docx", "ppt", "pptx", "xls", "xlsx", "csv", "txt");
    private static final int MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB
    private static final Tika tika = new Tika();
    
    private static Map<String, String> EXTENSION_TO_MIME_TYPE;
    
    static {
        EXTENSION_TO_MIME_TYPE = new HashMap<>();
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
    
    public boolean isAllowedExtension(String fileName) {
        if (fileName == null) {
            return false;
        }
        String fileExtension = getFileExtension(fileName);
        return ALLOWED_EXTENSIONS.contains(fileExtension);
    }
    
    public boolean isAllowedFileSize(long fileSize) {
        return fileSize <= MAX_FILE_SIZE;
    }
    
    public boolean isAllowedMimeType(MultipartFile file) {
    	 try {
             String mimeType = tika.detect(file.getInputStream());
             String fileExtension = getFileExtension(file.getOriginalFilename());
             return isAllowedMimeType(mimeType) && isExtensionMatchingMimeType(fileExtension, mimeType);
         } catch (IOException e) {
             e.printStackTrace();
             return false;
         }
    }
    
    private boolean isAllowedMimeType(String mimeType) {
        List<String> ALLOWED_MIME_TYPES = Arrays.asList("image/jpeg", "image/png", "image/gif", "application/pdf", "application/x-hwp",
                                                        "application/msword", "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
                                                        "application/vnd.ms-excel", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                                                        "text/csv", "text/plain");
        return ALLOWED_MIME_TYPES.contains(mimeType);
    }
    
    private boolean isExtensionMatchingMimeType(String fileExtension, String mimeType) {
        return EXTENSION_TO_MIME_TYPE.getOrDefault(fileExtension, "").equals(mimeType);
    }

    private String getFileExtension(String fileName) {
    	int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex == -1 || dotIndex == fileName.length() - 1) {
            return ""; // No valid extension found
        }
        return fileName.substring(dotIndex + 1).toLowerCase();
    }
    
}
