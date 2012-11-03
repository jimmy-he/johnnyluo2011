package com.crm.codemagic.creator.common;

import java.util.ArrayList;
import java.util.List;

import com.crm.codemagic.pdmparser.DataBase;
import com.crm.codemagic.pdmparser.PdmParser;
import com.crm.codemagic.pdmparser.Table;

/**
 * @description 生成文件
 * @author 王永明
 * @date 2009-3-23 下午09:10:18
 * 
 */
public class FileCreator {

	/**
	 * 生成文件
	 * 
	 * @param pdmPath pdm文件路径
	 * @param projectPath 项目根路径
	 * @param rootPackage 包名前缀
	 * @param author 创建人
	 */
	public void createAll(String pdmPath, String projectPath, String rootPcakage, String author, String prifix)
			throws Exception {
		this.createAll(pdmPath, projectPath, rootPcakage, author, null, prifix);
	}

	/**
	 * 生成文件
	 * 
	 * @param pdmPath pdm文件路径
	 * @param projectPath 项目根路径
	 * @param rootPackage 包名前缀
	 * @param author 创建人
	 * @param tables 要生成代码的表
	 */
	public void createAll(String pdmPath, String projectPath, String rootPcakage, String author, String[] tables,
			String prifix) throws Exception {
		this.create(pdmPath, projectPath, rootPcakage, author, tables, this.getAllCreator(), prifix);
	}

	/**
	 * 生成文件
	 * 
	 * @param pdmPath pdm文件路径
	 * @param projectPath 项目根路径
	 * @param rootPackage 包名前缀
	 * @param author 创建人
	 * @param tables 要生成代码的表
	 * @param creators 要生成的类型
	 */
	public String create(String pdmPath, String projectPath, String rootPcakage, String author, String[] tables,
			List<CodeCreator> creators, String prifix) throws Exception {
		StringBuffer returnStr = new StringBuffer();
		PdmParser pdmParser = new PdmParser();
		DataBase dataBase = pdmParser.getDataBase(pdmPath);
		// 遍历所有数据库中的表
		for (Table table : dataBase.getTables()) {
			// 是否要生成代码
			boolean create = false;

			// 参数tables==null生成全部
			if (tables == null || tables.length == 0) {
				create = true;
			} else {
				// 判断数据中的表是和要生成代码的表名一致
				for (String tableName : tables) {
					if (table.code.equalsIgnoreCase(tableName)) {
						create = true;
						break;
					}
				}
			}

			if (!create)
				continue;

			for (CodeCreator codeCreator : creators) {
				codeCreator.setTable(table);
				codeCreator.setRootPackage(rootPcakage);
				codeCreator.setAuthor(author);
				codeCreator.setPrifix(prifix);
				codeCreator.setProjectPath(projectPath);
				String filePath = codeCreator.createFile();				
				returnStr.append("生成文件:" + filePath + "\r\n");
			}
		}
		return returnStr.toString();
	}

	/** 所有目前已有的类型 */
	public List<CodeCreator> getAllCreator() {
		List<CodeCreator> creators = new ArrayList();
	
		return creators;
	}
}
