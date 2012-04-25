package com.tinet.ccic.wm.commons.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

/**
 * 为文件生成Hash值，通过Hash值可以区分图片或者文件；提供多种类型："MD2", "MD5", "SHA1",
			"SHA-256", "SHA-384", "SHA-512"
 *<p>
 * 文件名： HashCalc.java
 *<p>
 * Copyright (c) 2006-2010 T&I Net Communication CO.,LTD.  All rights reserved.
 * @author 周营昭
 * @since 1.0
 * @version 1.0
 */
public class HashCalc {
	
	public static final String[] hashTypes = new String[]{"MD2", "MD5", "SHA1",
			"SHA-256", "SHA-384", "SHA-512"};
    
	/**
	 * @author  Jathy Lee
	 * @version 1.0	
	 * 功能描述： 把字节数组转换为16进制字符串
	 * @see 
	 * @param value[] 字节数组
	 * @return String 转换后的字符串
	 * @exception 
	*/
	public static String toHexString(byte[] value) {
		StringBuilder sb = new StringBuilder(value.length * 2);
	        for (int i = 0; i < value.length; i++) {
	            byte b = value[i];
	            String str = Integer.toHexString(b);
	            if (str.length() > 2) {
	                str = str.substring(str.length() - 2);
	            }
	            if (str.length() < 2) {
	                str = "0" + str;
	            }
	            sb.append(str);
	        }
	        return sb.toString().toUpperCase();

	}
	/**
	 * @author  Jathy Lee
	 * @version 1.0	
	 * 功能描述： 生成指定文件的MD2算法Hash值
	 * @see 
	 * @param fileName 文件名称
	 * @return String 生成的Hash值
	 * @exception java.lang.Exception
	*/
	public static String MD2HashCalc(String fileName) throws Exception{
		MessageDigest md = MessageDigest.getInstance("MD2");
		InputStream fis = null;
		try {
			fis = new FileInputStream(fileName);
			byte[] buffer = new byte[1024];
			int numRead = 0;
			while ((numRead = fis.read(buffer)) > 0) {
					md.update(buffer, 0, numRead);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (fis != null) {
				fis.close();
			}
		}
		return toHexString(md.digest());
	}
	/**
	 * @author  Jathy Lee
	 * @version 1.0	
	 * 功能描述： 生成指定文件的SHA1算法Hash值
	 * @see 
	 * @param fileName 文件名称
	 * @return String 生成的Hash值
	 * @exception java.lang.Exception
	*/
	public static String SHA1HashCalc(String fileName) throws Exception{
		MessageDigest md = MessageDigest.getInstance("SHA1");
		InputStream fis = null;
		try {
			fis = new FileInputStream(fileName);
			byte[] buffer = new byte[1024];
			int numRead = 0;
			while ((numRead = fis.read(buffer)) > 0) {
					md.update(buffer, 0, numRead);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (fis != null) {
				fis.close();
			}
		}
		return toHexString(md.digest());
	}
	/**
	 * @author  Jathy Lee
	 * @version 1.0	
	 * 功能描述： 生成指定文件的SHA512算法Hash值
	 * @see 
	 * @param fileName 文件名称
	 * @return String 生成的Hash值
	 * @exception java.lang.Exception
	*/
	public static String SHA512HashCalc(String fileName) throws Exception{
		MessageDigest md = MessageDigest.getInstance("SHA-512");
		InputStream fis = null;
		try {
			fis = new FileInputStream(fileName);
			byte[] buffer = new byte[1024];
			int numRead = 0;
			while ((numRead = fis.read(buffer)) > 0) {
					md.update(buffer, 0, numRead);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (fis != null) {
				fis.close();
			}
		}
		return toHexString(md.digest());
	}
	/**
	 * @author  Jathy Lee
	 * @version 1.0	
	 * 功能描述： 生成指定文件的SHA384算法Hash值
	 * @see 
	 * @param fileName 文件名称
	 * @return String 生成的Hash值
	 * @exception java.lang.Exception
	*/
	public static String SHA384HashCalc(String fileName) throws Exception{
		MessageDigest md = MessageDigest.getInstance("SHA-384");
		InputStream fis = null;
		try {
			fis = new FileInputStream(fileName);
			byte[] buffer = new byte[1024];
			int numRead = 0;
			while ((numRead = fis.read(buffer)) > 0) {
					md.update(buffer, 0, numRead);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (fis != null) {
				fis.close();
			}
		}
		return toHexString(md.digest());
	}
	/**
	 * @author  Jathy Lee
	 * @version 1.0	
	 * 功能描述： 生成指定文件的SHA256算法Hash值
	 * @see 
	 * @param fileName 文件名称
	 * @return String 生成的Hash值
	 * @exception java.lang.Exception
	*/
	public static String SHA256HashCalc(String fileName) throws Exception{
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		InputStream fis = null;
		try {
			fis = new FileInputStream(fileName);
			byte[] buffer = new byte[1024];
			int numRead = 0;
			while ((numRead = fis.read(buffer)) > 0) {
					md.update(buffer, 0, numRead);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (fis != null) {
				fis.close();
			}
		}
		return toHexString(md.digest());
	}
	/**
	 * @author  Jathy Lee
	 * @version 1.0	
	 * 功能描述： 生成指定文件的MD5算法Hash值
	 * @see 
	 * @param fileName 文件名称
	 * @return String 生成的Hash值
	 * @exception java.lang.Exception
	*/
	public static String MD5HashCalc(String fileName) throws Exception{
		MessageDigest md = MessageDigest.getInstance("MD5");
		InputStream fis = null;
		try {
			fis = new FileInputStream(fileName);
			byte[] buffer = new byte[1024];
			int numRead = 0;
			while ((numRead = fis.read(buffer)) > 0) {
					md.update(buffer, 0, numRead);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (fis != null) {
				fis.close();
			}
		}
		return toHexString(md.digest());
	}

	/**
	 * @author  Jathy Lee
	 * @version 1.0	
	 * 功能描述： 生成指定文件的MD5算法Hash值
	 * @see 
	 * @param fis 文件流
	 * @return String 生成的Hash值
	 * @exception java.lang.Exception
	*/
	public static String MD5HashCalc2(InputStream fis) throws Exception{
		MessageDigest md = MessageDigest.getInstance("MD5");
		try {
			byte[] buffer = new byte[1024];
			int numRead = 0;
			while ((numRead = fis.read(buffer)) > 0) {
					md.update(buffer, 0, numRead);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (fis != null) {
				fis.close();
			}
		}
		return toHexString(md.digest());
	}
	public static void main(String[] args) throws Exception {
		if (args == null || args.length < 1) {
			System.out
					.println("示例： com.jeaw.commons.util.HashCalc HashCalc.exe");
			System.exit(1);
		}
		String fileName = args[0];
		System.out.println("需要获取hash的文件为：　" + fileName);
		System.out.println(MD5HashCalc(fileName));
	}

}
