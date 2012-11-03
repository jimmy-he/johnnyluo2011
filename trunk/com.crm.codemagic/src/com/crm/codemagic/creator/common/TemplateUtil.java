package com.crm.codemagic.creator.common;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import com.crm.framework.util.JarResources;
import com.crm.framework.util.Path;


/**
 * @description
 * @author 王永明
 * @date May 25, 2009 1:15:05 AM
 * */
public class TemplateUtil {
	
	public static String fromTemplate(String template,CodeCreator codeCreator){
		return template.replaceAll("\\{rootPackage\\}", codeCreator.getRootPackage())
		.replaceAll("\\{author\\}",codeCreator.getAuthor())
		.replaceAll("\\{date\\}", codeCreator.getDate())
		.replaceAll("\\{ModelName\\}",codeCreator.getFirstUpModelName())
		.replaceAll("\\{modelName\\}",codeCreator.getModelName())
		.replaceAll("\\{tableName\\}",codeCreator.getTable().name)
		.replaceAll("\\{tableCode\\}",codeCreator.getTable().code)
		.replaceAll("\\{primKey\\}",codeCreator.getTable().pkColum.getJavaCode())
		.replaceAll("\\{PrimKey\\}",codeCreator.getTable().pkColum.getFistUpJavaCode());
	}
	
	
	/**
	 * 通过名称获得相应的文件模板
	 * 
	 * @param name 模板名称
	 */
	public  static String getTemplateOld(String name) {
		URL url = TemplateUtil.class.getResource("/template");
		String root = url.getFile();
		String filePath = root + "/" + name + ".template";


		try {
			return FileUtil.readFile(filePath);
		} catch (Exception ex) {
			throw new RuntimeException(root);
		}
	}
	
	public static String getTemplate(String name){
		String jarFilePath = "";	// 得到插件JAR文件地绝对路径
		try {
			jarFilePath = Path.getPathFromClass(TemplateUtil.class);
		} catch (IOException e) {
			throw new RuntimeException("没有找到类。" + e.getCause());
		}
		String resourceName = "bin/template/" + name + ".template";
		String templateStr = "";
		try {
			templateStr = JarResources.getResourceContent(jarFilePath,resourceName);
		} catch (Exception e) {
			throw new RuntimeException("读取模析内容时返回错误。" + e.getCause());
		}
		
		return templateStr;
	}
	
	public static void main(String[] args) {
		String path = "";
		try {
			path = Path.getPathFromClass(TemplateUtil.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(path);
		path = path.substring(0,path.indexOf("com\\crm\\codemagic"));
		System.out.println(path);
		
//		
//		String root = TemplateUtil.class.getResource("/template").getFile();
//		 //String root = path;
//		System.out.println(root);
//		String a=FileUtil.readFile(root + "/java/" + "action" + ".template");
//		System.out.println(a);
	}
	
	
}
