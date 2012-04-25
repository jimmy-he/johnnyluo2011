package com.tinet.ccic.wm.commons.util;
/**
 * 随机数生成器。
 *<p>
 * 文件名： RandomGenerator.java
 *<p>
 * Copyright (c) 2006-2010 T&I Net Communication CO.,LTD.  All rights reserved.
 * @author 周营昭
 * @since 1.0
 * @version 1.0
 */
public class RandomGenerator {

	/**
	 * 生成随机的密码。也就是长度为6的随机字符串。
	 * @return
	 * @see RandomGenerator#randomString(int)
	 */
    public static String randomPassword() {
        return randomString(6);
    }
    
    /**
     * 生成随机的字符串，长度由length指定。生成的随机字符串中可以包含[0-9a-zA-Z]等字符。
     * @param length
     * @return
     */
    public static String randomString(int length) {
        return randomString(length, true);
    }
    
    /**
     * 生成一个随机的字符串，长度由参数length指定。
     * 如果参数includeNumbers为true，生成的随机字符串中可以包含数字[0-9],否则只能包含alpha字符[a-zA-Z]。
     * 
     * @param length
     * @param includeNumbers
     * @return
     */
    public static String randomString(int length, boolean includeNumbers) {
        StringBuffer b = new StringBuffer(length);
        for (int i = 0; i < length; i++) {
            if (includeNumbers) {
                b.append(randomCharacter());
            } else {
                b.append(randomAlpha());
            }
        }
        return b.toString();
    }
    
    /**
     * 获取随机的数字组成长度为参数length指定的字符串。
     * @param length 生成的随机数长度。
     * @return
     */
    public static String randomNumber(int length) {
        StringBuffer b = new StringBuffer(length);
        for (int i = 0; i < length; i++) {
            b.append(randomDigit());
        }
        return b.toString();
    }
    
    /**
     * 获取一个随机字符[0-9a-zA-Z]。
     * 
     * @return
     */
    public static char randomCharacter() {
        int i = (int) (Math.random() * 3D);
        if (i < 2) {
            return randomAlpha();
        } else {
            return randomDigit();
        }
    }
    
    /**
     * 获取一个随机alpha字符[a-zA-Z]。
     * 
     * @return char [a-zA-Z]
     */
    public static char randomAlpha() {
        int i = (int) (Math.random() * 52D);
        if(i > 25) {
            return (char) ((97 + i) - 26);
        } else {
            return (char) (65 + i);
        }
    }
    
    /**
     * 获取一个随机数字[0-9]。
     * 
     * @return char 随机数字。
     */
    public static char randomDigit() {
        int i = (int) (Math.random() * 10D);
        return (char) (48 + i);
    }
}
