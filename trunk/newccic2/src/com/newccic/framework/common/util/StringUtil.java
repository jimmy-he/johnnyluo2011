package com.newccic.framework.common.util;

import org.apache.log4j.Logger;

import sun.security.provider.MD5;

/**
 ************************************************
 *@Title 	newccic2						
 *@Pageage 	com.newccic.common.util						
 *@author   罗尧  Email：j2ee.xiao@gmail.com		
 *@since	1.0  创建时间  2012-6-29  下午11:57:11		
 ************************************************
 */
public class StringUtil {
	
	private static Logger log=Logger.getLogger(StringUtil.class);
	private static MD5 md5 = new MD5();
	
	/** 将首字母转为大写，其他不变 */
	public static String firstUp(String str) {
		char ch[];   
        ch = str.toCharArray();   
        if (ch[0] >= 'a' && ch[0] <= 'z') {   
            ch[0] = (char) (ch[0] - 32);   
        }   
        String newString = new String(ch);   
        return newString;		
	}
	/** 将首字母转为小写，其他不变 */
	public static String firstLower(String str) {
		String first = str.substring(0, 1);
		String orther = str.substring(1);
		return first.toLowerCase() + orther;
	}	
	/** 将符合数据库的命名转为java的命名 */
	public static String pareseUnderline(String code) {
		String[] strs = code.split("_");
		String first = strs[0].toLowerCase();
		if (strs.length == 1) {
			return first;
		}
		StringBuffer sb = new StringBuffer(first);
		for (int i = 1; i < strs.length; i++) {
			sb.append(firstUpOnly(strs[i]));
		}
		return sb.toString();
	}
	/** 将首字母转为大写，其他变小写 */
	public static String firstUpOnly(String str) {
		String first = str.substring(0, 1);
		String orther = str.substring(1);
		return first.toUpperCase() + orther.toLowerCase();
	}
	/** 判断对象是否为空 */
	public static boolean isNull(Object object) {
		if (object instanceof String) {
			return isNull((String) object);
		}
		return object == null;
	}
	/** 判断字符串是否为空 */
	public static boolean isNull(String value) {
		return value == null || value.equals("");
	}
	
	/** 判断字符串是否为空 */
	public static boolean isNotNull(String value) {
		return value != null &&! value.equals("");
	}
	public static String getLine(){
		return System.getProperty("line.separator");
	}
}
