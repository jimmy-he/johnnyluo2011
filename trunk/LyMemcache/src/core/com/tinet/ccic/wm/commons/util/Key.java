package com.tinet.ccic.wm.commons.util;
import java.io.File;   
import java.io.FileOutputStream;   
import java.security.SecureRandom;   
import javax.crypto.KeyGenerator;   
import javax.crypto.SecretKey;   
/**
 * 序列号生成器。
 *<p>
 * 文件名： Key.java
 *<p>
 * Copyright (c) 2006-2010 T&I Net Communication CO.,LTD.  All rights reserved.
 * @author 周营昭
 * @since 1.0
 * @version 1.0
 */  
class Key {   
  
    private String keyName;   
  
    public Key() {   
  
    }   
  
    public Key(String keyName) {   
        this.keyName = keyName;   
    }   
  
    /**
     * 生成序列号方法。
     * @param keyName 序列号名称。
     * @throws Exception 文件读写出错时抛出异常。
     */
    public void createKey(String keyName) throws Exception {   
  
        SecureRandom sr = new SecureRandom();   
        KeyGenerator kg = KeyGenerator.getInstance("DES");   
        kg.init(sr);   
        SecretKey key = kg.generateKey();   
        System.out.println(key.toString());   
        byte rawKeyData[] = key.getEncoded();   
        FileOutputStream fo = new FileOutputStream(new File(keyName));   
        fo.write(rawKeyData);   
        fo.close();   
    }   
  
    public static void main(String args[]) {   
        try {   
            new Key("").createKey("d:/key.txt");   
  
        } catch (Exception e) {   
            e.printStackTrace();   
        }   
  
    }   
}  
