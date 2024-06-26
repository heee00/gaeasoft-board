package com.gaeasoft.project.util;

import java.util.Arrays;
import java.util.List;

public class FileUpload {

	private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png", "gif", "pdf", "doc", "docx", "xls", "xlsx", "txt");

    public static boolean isAllowedExtension(String fileName) {
        if (fileName == null) {
            return false;
        }
        String fileExtension = getFileExtension(fileName);
        return ALLOWED_EXTENSIONS.contains(fileExtension);
    }

    private static String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex == -1 || dotIndex == fileName.length() - 1) {
            return ""; // No valid extension found
        }
        return fileName.substring(dotIndex + 1).toLowerCase();
    }
    
}
