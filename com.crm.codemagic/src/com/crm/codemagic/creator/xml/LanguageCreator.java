package com.crm.codemagic.creator.xml;

import java.io.File;
import java.io.IOException;

import com.crm.codemagic.creator.common.CodeCreator;
import com.crm.codemagic.creator.common.FileUtil;
import com.crm.codemagic.pdmparser.Column;
import com.crm.framework.util.StringUtil;

public class LanguageCreator extends CodeCreator {

	@Override
	public String createFile() throws IOException {
		
		String directory = this.getProjectPath() + "/src/" + this.getRootPackage().replaceAll("\\.", "/") + "/" + "action";
		File directoryFile = new File(directory);
		directoryFile.mkdirs();

		String fileName = directory + "/" + this.getFirstUpModelName()+"Action_zh_CN.properties";
		return FileUtil.createFile(fileName, this.getCode());
	}

	@Override
	public String getCode() {
		StringBuffer sb = new StringBuffer();
		this.addLine(sb, "############################################### " + this.getTable().name
				+ "的资源文件 #######################################################################");
		for (Column column : this.getTable().getColumns()) {
		//	this.addLine(sb, "#" + column.name);
			String code=StringUtil.getUnicode(column.name);
			String key=this.getModelName()+"Page."+this.getModelName()+"."+column.getJavaCode();
			this.addLine(sb, key+"="+code);		
		}
		return sb.toString();
	}
	
}