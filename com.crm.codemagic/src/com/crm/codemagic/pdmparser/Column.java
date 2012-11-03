package com.crm.codemagic.pdmparser;


import com.crm.framework.util.JavaTypeUtil;
import com.crm.framework.util.StringUtil;


/** 
 * 表的某一列
 * @author 王永明
 * @date date 2009-3-16 下午02:44:40 
 * 
 */
public class Column {
	public String id;//字段标识
	public String dataType;//数据类型
	public int length;//长度
	public String name;//名称
	public String code;//代码
	public boolean mandatory;//是否必填
	
	/**返回java命名规范的代码*/
	public final String getJavaCode(){
		return StringUtil.pareseUnderline(code);
	}
	
	/**返回java符合java的类型*/
	public String getJavaType(){
		return JavaTypeUtil.parseDb(dataType, length);
	}
	
	/**首字母大写的java代码*/
	public String getFistUpJavaCode(){
		return StringUtil.firstUp(StringUtil.pareseUnderline(code));
	}
}
