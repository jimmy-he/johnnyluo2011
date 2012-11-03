package com.crm.framework.util;


/**
 * @description
 * @author 王永明
 * @date Apr 15, 2009 11:38:16 AM
 * */
public class JavaTypeUtil {
	public static String parseDb(String dbType,int length){
		String type=dbType.toUpperCase();
		if(type.startsWith("VARCHAR")){
			return "String";
		}
		if(type.startsWith("DATE")){
			return "Date";
		}
		
		if(type.startsWith("NUMBER")){
			if(length>=10){
				return "Long";
			}else{
				return "Integer";
			}
		}
		return "";
		
	}
}
