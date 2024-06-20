package com.gaeasoft.project.util;

import java.security.MessageDigest;

public class EncodePassword {
	
	public static String encrypt(String plainText) {
		try {
			MessageDigest message = MessageDigest.getInstance("SHA-256");
			message.update(plainText.getBytes());
			byte byteData[] = message.digest();
			
			StringBuffer buffer = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
                String string = Integer.toHexString(0xff & byteData[i]);
                if (string.length() == 1) {
                	buffer.append('0');
                }
                buffer.append(string);
            }
            return buffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

}