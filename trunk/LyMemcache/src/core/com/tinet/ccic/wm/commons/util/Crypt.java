package com.tinet.ccic.wm.commons.util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
/**
 * 文件加解密。
 *<p>
 * 文件名： Crypt.java
 *<p>
 * Copyright (c) 2006-2010 T&I Net Communication CO.,LTD.  All rights reserved.
 * @author 周营昭
 * @since 1.0
 * @version 1.0
 */  
public class Crypt {   
	
	public static Cipher cipher =null;
	
	/**
	 * 文件加解密方法。
	 * @param file 待处理文件。
	 * @throws Exception 读取文件时出错抛出异常
	 */
	public static void readFile(File file) throws Exception{
		
		for(File f:file.listFiles()){
			if(f.isDirectory()){
				readFile(f);
			}else{
				if(f.getName().lastIndexOf(".class")>0){
				
				FileInputStream fi2 = new FileInputStream(new File(f.getAbsolutePath()));   
		        byte data[] = new byte[fi2.available()];   
		        fi2.read(data);   
		        fi2.close();   
		        System.out.println(new String(data,"utf8"));
		        byte encryptedData[] = cipher.doFinal(data);   
		        FileOutputStream fo = new FileOutputStream(new File(f.getAbsolutePath()));   
		        fo.write(encryptedData);   
		        fo.close();   
				}
			}
		}
		
	}
  
    public static void main(String[] args) throws Exception {   
  
        SecureRandom sr = new SecureRandom();   
        FileInputStream fi = new FileInputStream(new File("d:/key.txt"));   
        byte rawKeyData[] = new byte[fi.available()];   
        fi.read(rawKeyData);   
        fi.close();   
        DESKeySpec dks = new DESKeySpec(rawKeyData);   
        SecretKey key = SecretKeyFactory.getInstance("DES").generateSecret(dks);   
        cipher = Cipher.getInstance("DES");   
        cipher.init(Cipher.ENCRYPT_MODE, key, sr);   
    	File file = new File("D:/project/jeaw-core/WebRoot/WEB-INF/classes/com/jeaw");
    	readFile(file);
 
       /* FileInputStream fi2 = new FileInputStream(new File("D:/project/jeaw/WebRoot/WEB-INF/classes/com/jeaw/commons/application/BaseDaoImp.class"));   
        byte data[] = new byte[fi2.available()];   
        fi2.read(data);   
        fi2.close();   
        byte encryptedData[] = cipher.doFinal(data);   
        FileOutputStream fo = new FileOutputStream(new File("D:/BaseDaoImp.class"));   
        fo.write(encryptedData);   
        fo.close();   */
    }   
}  
