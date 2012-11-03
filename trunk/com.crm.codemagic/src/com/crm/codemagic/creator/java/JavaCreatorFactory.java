package com.crm.codemagic.creator.java;

import com.crm.codemagic.creator.common.CodeCreator;
import com.crm.framework.util.StringUtil;

/**
 * @description
 * @author 王永明
 * @date May 25, 2009 2:42:35 PM
 * */
public class JavaCreatorFactory {
	public static CodeCreator newCreator(String name){
		String className = "com.crm.codemagic.creator.java." + StringUtil.firstUp(name) + "Creator";
		try{
			Class clazz = Class.forName(className);
			return (CodeCreator) clazz.newInstance();
		}catch(Exception ex){
			return new JavaCreator(name);
		}	
	}
	
	
}
