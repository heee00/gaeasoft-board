package com.gaeasoft.project.util;

import org.apache.commons.fileupload.FileUploadException;

@SuppressWarnings("serial")
public class FileValidationException extends FileUploadException {
	public FileValidationException(String errorMessage) {
		super(errorMessage);
	}

}
