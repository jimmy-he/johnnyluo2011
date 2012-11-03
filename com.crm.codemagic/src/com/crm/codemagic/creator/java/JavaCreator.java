package com.crm.codemagic.creator.java;

import com.crm.framework.util.StringUtil;

/**
 * @description
 * @author 王永明
 * @date May 25, 2009 2:35:45 PM
 * */
public class JavaCreator extends SimpleJavaCodeCreator{

	private String type;
	
	public JavaCreator(String type){
		this.type=type;
	}
	
	public String getClassName() {
		return this.getFirstUpModelName()+StringUtil.firstUp(type);
	}
	public String getPackageName() {
		return (this.getRootPackage()+"."+type.replaceAll("Base", "")).toLowerCase();
	}
	public String getTemplateName() {
		return "java/"+type;
	}
	
	
}
