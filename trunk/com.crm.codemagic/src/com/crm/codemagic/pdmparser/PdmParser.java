package com.crm.codemagic.pdmparser;

import java.io.File;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;


/**
 * 将pdm文件解析为java类
 * 
 * @author 王永明
 * @date date 2009-3-16 下午02:51:00
 * 
 */
public class PdmParser {
	
	/**
	 * 构造方法
	 * */
	public PdmParser(){
		
	}

	/**
	 * 将pdm里的xml信息转换为java对象
	 * @param filePath pdm文件路径
	 */

	public DataBase getDataBase(String filePath) throws DocumentException {
		File file = new File(filePath);
		SAXReader reader = new SAXReader();
		Document document = reader.read(file);

		String name = document.selectSingleNode("//c:DBMS/o:Shortcut/a:Name").getText();
		String code = document.selectSingleNode("//c:DBMS/o:Shortcut/a:Code").getText();

		Node node = document.selectSingleNode("//c:Tables");
		TableVisitor tableVisitor = new TableVisitor();
		node.accept(tableVisitor);
		List<Table> tables = tableVisitor.getTable();

		DataBase dataBase = new DataBase();
		dataBase.setCode(code);
		dataBase.setName(name);
		dataBase.setTables(tables);
		return dataBase;
	}

}
