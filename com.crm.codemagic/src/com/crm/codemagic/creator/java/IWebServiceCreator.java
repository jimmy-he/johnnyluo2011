package com.crm.codemagic.creator.java;

/**
 * @description
 * @author 王永明
 * @date May 25, 2009 8:44:14 PM
 * */
public class IWebServiceCreator extends SimpleJavaCodeCreator{

	@Override
	public String getClassName() {
		return "I"+this.getFirstUpModelName()+"WebService";
	}

	@Override
	public String getPackageName() {
		return this.getRootPackage()+".webservice";
	}

	@Override
	public String getTemplateName() {
		return "java/iWebService";
	}

}
