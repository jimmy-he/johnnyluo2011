package com.crm.codemagic.creator.common;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.crm.codemagic.pdmparser.Table;
import com.crm.framework.util.StringUtil;

/**
 * @description 根据表信息生成代码
 * @author 王永明
 * @date 2009-3-23 下午01:46:26
 * 
 */
public abstract class CodeCreator {
	public static final String LINE_SEPARATOR = System.getProperty("line.separator");
	
	private Table table;

	private String author;

	private String rootPackage;

	private String prifix;
	
	private String projectPath;


	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getRootPackage() {
		return rootPackage;
	}

	public void setRootPackage(String rootPackage) {
		this.rootPackage = rootPackage;
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public String getModelName() {
		String name=table.getJavaCode().replaceFirst(prifix, "");
		return StringUtil.firstLower(name);
	}

	public String getFirstUpModelName() {
		return StringUtil.firstUp(this.getModelName());
	}


	public String getDate() {
		Date date = new Date();
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return s.format(date);

	}

	/**获得生成的代码*/
	abstract public String getCode();
	
	/**生成文件
	 * @throws IOException 
	 * @throws Exception */
	abstract public String createFile() throws Exception;
	
	public String getPrifix() {
		return prifix;
	}

	public void setPrifix(String prifix) {
		this.prifix = prifix;
	}

	public String getProjectPath() {
		return projectPath;
	}

	public void setProjectPath(String projectPath) {
		this.projectPath = projectPath;
	}
	
	public void addLine(StringBuffer sb, String str) {
		sb.append(LINE_SEPARATOR);
		sb.append(str);
	}

	
	/** 在每行字符传前加制表符 */
	public String addTabs(String str,int no) {
		for(int i=0;i<no;i++){
			str=this.addTabs(str);
		}
		return str;
	}
	
	/** 在每行字符传前加制表符 */
	public String addTabs(String str) {
		String[] strs = str.split("\n");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < strs.length; i++) {
			sb.append("\n" + "\t" + strs[i]);
		}
		return sb.toString();
	}
}
