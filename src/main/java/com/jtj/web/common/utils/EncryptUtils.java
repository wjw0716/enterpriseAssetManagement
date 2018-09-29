package com.jtj.web.common.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptUtils {

	/** 
	 * MD5 加密 
	 */  
	public static String getMD5(String str) {

		StringBuilder str_md5 = new StringBuilder();
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] digest = md5.digest(str.getBytes("UTF-8"));
			for (byte b : digest) {
				String hexString = Integer.toHexString(b & 0xff);
				if (hexString.length() < 2) {
					hexString = "0" + hexString;
				}
				str_md5.append(hexString);
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str_md5.toString();
	} 
}
