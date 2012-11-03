package com.crm.codemagic.creator.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.crm.codemagic.creator.common.CodeCreator;

/** 
 * @author 王永明
 * @date 2009-3-23 下午10:34:36
 * 
 */
public abstract class XmlCodeCreator extends CodeCreator{

	/**获得xml文档对象*/
	abstract public Document getDocument();
	
	/**获得文件的保存路径*/
	abstract public String  getSavePath();
	
	/**获得文件名*/
	abstract public String  getFileName();
	
	/**生成文件
	 * @throws Exception */
	public String createFile(String projectPath) throws Exception{
		String directory=projectPath+this.getSavePath();
		File directoryFile=new File(directory);
		directoryFile.mkdirs();
		String fileName=projectPath+this.getSavePath()+"/"+this.getFileName();
		OutputFormat format = OutputFormat.createPrettyPrint();
		XMLWriter xmlWriter =new XMLWriter(new FileOutputStream(fileName),format);	
		xmlWriter.write(this.getDocument());
		xmlWriter.close();
		return fileName;
	}

}
