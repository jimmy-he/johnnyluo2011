package com.crm.codemagic.pdmparser;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JOptionPane;

import com.crm.codemagic.creator.common.FileUtil;
import com.crm.codemagic.creator.common.TemplateUtil;
import com.crm.framework.util.JarResources;
import com.crm.framework.util.Path;

public class Keywords {
	
	public static Set keywords;
	
	public static Set getKeywords(){
		String jarFilePath = "";	// 得到插件JAR文件地绝对路径
		try {
			jarFilePath = Path.getPathFromClass(Keywords.class);
		} catch (IOException e) {
			throw new RuntimeException("没有找到类。" + e.getCause());
		}
		String resourceName = "bin/com/crm/codemagic/pdmparser/dbKeyWords.txt";
		String templateStr = "";
		try {
			templateStr = JarResources.getResourceContent(jarFilePath,resourceName);
		} catch (Exception e) {
			throw new RuntimeException("读取KeyWords内容时返回错误。" + e.getCause());
		}
		String text = templateStr;
		String[] txts = text.split("\\s+");
		Set keywords = new HashSet();
		for(String str:txts){
			if(str.length() > 1){
				keywords.add(str.toUpperCase());
			}
		}
		return keywords;
	}
	
	public static  boolean isKeywords(String str){
		keywords = getKeywords();
		boolean result = keywords.contains(str.toUpperCase());
		return result;
	}
	
	public static void main(String[] args){
		getKeywords();
	}
}
