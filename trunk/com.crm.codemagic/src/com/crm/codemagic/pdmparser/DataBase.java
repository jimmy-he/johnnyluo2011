package com.crm.codemagic.pdmparser;

import java.util.List;

/** 
 * @author 王永明
 * @date date 2009-3-16 下午02:53:37 
 * 
 */
public class DataBase {
	private String name;//数据库名称
	private String code;//数据库代码
	private List<Table> tables;//所有表
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public List<Table> getTables() {
		return tables;
	}
	public void setTables(List<Table> tables) {
		this.tables = tables;
	}
}
