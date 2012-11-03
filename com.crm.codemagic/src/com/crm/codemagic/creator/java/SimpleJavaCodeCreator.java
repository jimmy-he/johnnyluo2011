package com.crm.codemagic.creator.java;

import com.crm.codemagic.creator.common.CodeCreator;
import com.crm.codemagic.creator.common.FileUtil;
import com.crm.codemagic.creator.common.TemplateUtil;

/**
 * @description 生成简单的java文件,只是将模板里的变量进行简单替换
 * @author 王永明
 * @date 2009-3-23 下午11:05:09
 * 
 */
public abstract class SimpleJavaCodeCreator  extends CodeCreator{
	/** 获得包名 */
	abstract public String getPackageName();

	/** 获得生成的类名 */
	abstract public String getClassName();

	abstract public String getTemplateName();
	
	/** 获得生成的包名+类名 */
	public String getClassFullName() {
		return this.getPackageName() + "." + this.getClassName();
	}
	
	

	
	public String createFile() throws Exception {
		return FileUtil.createClass(this.getProjectPath(), this.getPackageName(), this.getClassName(), this.getCode());
	}
	
	public String getCode(){
		String template=TemplateUtil.getTemplate(this.getTemplateName());
		return TemplateUtil.fromTemplate(template, this);
	}

}
