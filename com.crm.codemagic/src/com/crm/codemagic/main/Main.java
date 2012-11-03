package com.crm.codemagic.main;

import java.util.ArrayList;
import java.util.List;


import com.crm.codemagic.creator.common.CodeCreator;
import com.crm.codemagic.creator.common.FileCreator;
import com.crm.codemagic.creator.java.JavaCreatorFactory;
import com.crm.codemagic.creator.xml.LanguageCreator;

/**
 * @description
 * @author 王永明
 * @date Apr 11, 2009 8:52:06 PM
 */
public class Main {

	public static void main(String[] args) throws Exception {
		String workspacePath = Main.class.getResource("/").getPath() + "../../";
		String pdmPath = workspacePath + "/platform/config/pdm/platform.pdm";
		//项目路径
		String projectPath = workspacePath + "/platform";
		//基础包名
		String rootPcakage = "com.crm.permission.resource";
		//创建人
		String author = "王永明";
		//要生成的表
		String[] tables = {"crm_cresource","CRM_Resource_Type","CRM_Resource_CONTENT","CRM_Resource_FIELD"};

		List creators = updateField();
		//List creators=all();

		FileCreator fileCreator = new FileCreator();
		fileCreator.create(pdmPath, projectPath, rootPcakage, author, tables, creators, "crm");
	}

	public static List<CodeCreator> all() throws Exception {
		//要生成的文件类型
		List<CodeCreator> creators = new ArrayList();
		creators.add(JavaCreatorFactory.newCreator("modelBase"));
		creators.add(JavaCreatorFactory.newCreator("model"));
		
		creators.add(JavaCreatorFactory.newCreator("pageBase"));
		creators.add(JavaCreatorFactory.newCreator("page"));
		
		creators.add(JavaCreatorFactory.newCreator("actionBase"));
		creators.add(JavaCreatorFactory.newCreator("action"));
		
		creators.add(JavaCreatorFactory.newCreator("serviceBase"));
		creators.add(JavaCreatorFactory.newCreator("service"));
		
		creators.add(JavaCreatorFactory.newCreator("validateBase"));
		creators.add(JavaCreatorFactory.newCreator("validate"));
		
		creators.add(JavaCreatorFactory.newCreator("daoBase"));
		creators.add(JavaCreatorFactory.newCreator("dao"));
		
		creators.add(JavaCreatorFactory.newCreator("webServiceBase"));
		creators.add(JavaCreatorFactory.newCreator("webService"));
		
		creators.add(JavaCreatorFactory.newCreator("iWebService"));
		
		creators.add(new LanguageCreator());

		return creators;
	}

	public static List<CodeCreator> updateField() throws Exception {
		//要生成的文件类型
		List<CodeCreator> creators = new ArrayList();
		creators.add(JavaCreatorFactory.newCreator("modelBase"));
		
		creators.add(JavaCreatorFactory.newCreator("pageBase"));
		
		creators.add(JavaCreatorFactory.newCreator("actionBase"));
		
		creators.add(JavaCreatorFactory.newCreator("serviceBase"));
		
		creators.add(JavaCreatorFactory.newCreator("validateBase"));
		
		creators.add(JavaCreatorFactory.newCreator("daoBase"));
		
		creators.add(JavaCreatorFactory.newCreator("webServiceBase"));
		
		creators.add(JavaCreatorFactory.newCreator("iWebService"));
		creators.add(new LanguageCreator());
		return creators;
	}

}
