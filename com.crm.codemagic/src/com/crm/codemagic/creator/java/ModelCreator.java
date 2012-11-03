package com.crm.codemagic.creator.java;

import com.crm.framework.util.StringUtil;

/**
 * @description
 * @author 王永明
 * @date May 25, 2009 8:35:21 PM
 * */
public class ModelCreator extends SimpleJavaCodeCreator {

	@Override
	public String getClassName() {
		return this.getFirstUpModelName();
	}

	@Override
	public String getPackageName() {
		return this.getRootPackage()+".model";
	}

	@Override
	public String getTemplateName() {
		return "java/model";
	}

}
