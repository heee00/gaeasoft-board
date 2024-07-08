package com.gaeasoft.project.util;

import java.util.Arrays;
import java.util.List;

public class FileUpload {

	private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png", "gif", "pdf", "hwp", "doc", "docx", "ppt", "pptx", "xls", "xlsx", "csv", "txt");
    private static final int MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB
    private static final List<String> ALLOWED_MIME_TYPES = Arrays.asList("image/jpeg", "image/png", "image/gif", "application/pdf", "application/x-hwp",
																													            "application/msword", "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
																													            "application/vnd.ms-excel", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
																													            "text/csv", "text/plain");


    public static boolean isAllowedExtension(String fileName) {
        if (fileName == null) {
            return false;
        }
        String fileExtension = getFileExtension(fileName);
        return ALLOWED_EXTENSIONS.contains(fileExtension);
    }
    
    public static boolean isAllowedFileSize(long fileSize) {
        return fileSize <= MAX_FILE_SIZE;
    }
    
    public static boolean isAllowedMimeType(String fileType) {
        if (fileType == null) {
            return false;
        }
        return ALLOWED_MIME_TYPES.contains(fileType);
    }

    private static String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex == -1 || dotIndex == fileName.length() - 1) {
            return ""; // No valid extension found
        }
        return fileName.substring(dotIndex + 1).toLowerCase();
    }
    
}
