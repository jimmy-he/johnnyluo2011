package com.crm.framework.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description
 * @author 王永明
 * @date Apr 12, 2009 1:18:15 PM
 */
public class StringUtil {
	/** 将首字母转为大写，其他不变 */
	public static String firstUp(String str) {
		String first = str.substring(0, 1);
		String orther = str.substring(1);
		return first.toUpperCase() + orther;
	}

	/** 将首字母转为小写，其他不变 */
	public static String firstLower(String str) {
		String first = str.substring(0, 1);
		String orther = str.substring(1);
		return first.toLowerCase() + orther;
	}

	/**
	 * 通过get或set方法名获得字段名称
	 * 
	 * @param methodName 方法名称
	 */
	public static String getFieldName(String methodName) {
		return firstLower(methodName.substring(3));
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
	
	/** 将符合java的命名转为数据库的命名 */
	public static String pareseUpCase(String code) {
		char[] old=code.toCharArray();
		char[] news=code.toLowerCase().toCharArray();
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<old.length;i++){
			if(old[i]==news[i]){
				sb.append(news[i]);
			}else{
				sb.append("_"+news[i]);
			}
		}
		return sb.toString();
	}

	public static void addLine(StringBuffer sb, String str) {
		sb.append(str);
		sb.append(System.getProperty("line.separator"));
	}

	/** 将字符串转为uicode */
	public static String getUnicode(String str) {
		if (str == null) {
			return "";
		}
		char[] chars = str.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (char ch : chars) {
			String temp = Integer.toHexString(ch);
			if (temp.length() == 1) {
				temp = "000" + temp;
			}
			if (temp.length() == 2) {
				temp = "00" + temp;
			}
			if (temp.length() == 3) {
				temp = "0" + temp;
			}
			sb.append("\\u" + temp);
		}
		return sb.toString();
	}

	/**将字符串转为map
	 * 字符串:userName=张三,userCode=zhangsan,age
	 * 结果:map.put(userName,张三);
	 * 	   map.put(userCode,zhangsan);
	 *     map.put(age,null);
	 * */
	public static Map toMap(String str) {
		return toMap(str,",");
	}
	
	
	public static Map toMap(String str, String splitString) {
		Map map = new HashMap();
		if(str==null||str.equals("")){
			return  map;
		}
		String values[] =str.split(splitString);
		for (int i = 0; i < values.length; i++) {
			String tempValue = values[i];
			int pos = tempValue.indexOf("=");
			String key = "";
			String value = "";
			if (pos > -1) {
				key = tempValue.substring(0, pos);
				value = tempValue.substring(pos + splitString.length());
			} else {
				key = tempValue;
			}
			map.put(key, value);
		}

		return map;
	}
	
	public static Map toMap(List<String> strs){
		Map map = new HashMap();
		if(strs==null){
			return map;
		}
		for(String st:strs){
			map.put(st, null);
		}
		return map;
	}
	
	public static List<String> getIds(List<String> pageId,String prefix){
		List list=new ArrayList();
		if(pageId==null){
			return list;
		}
		for(String id:pageId){
			list.add(id);
		}
		return list;
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
}



