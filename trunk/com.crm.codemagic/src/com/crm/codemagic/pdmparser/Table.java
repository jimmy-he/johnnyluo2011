package com.crm.codemagic.pdmparser;

import com.crm.framework.util.StringUtil;

import java.util.List;

/**
 * @author 王永明
 * @date date 2009-3-16 下午02:44:31
 * 
 */
public class Table {
	public String id;
	public String name;// 名称

	public String code;// 代码

	public List<Column> columns;// 所有列

	public Column pkColum;// 主键列
	
	public List<Key> keys;// 所有约束

	public List<Column> getColumns() {
		return columns;
	}
	
	/**返回java命名规范的代码*/
	public String getJavaCode(){
		return StringUtil.pareseUnderline(code);
	}

	public String getFistUpJavaCode(){
		return StringUtil.firstUp(StringUtil.pareseUnderline(code));
	}

}
