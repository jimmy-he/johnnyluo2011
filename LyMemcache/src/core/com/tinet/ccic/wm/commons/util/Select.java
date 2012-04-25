package com.tinet.ccic.wm.commons.util;

import java.util.List;

import com.tinet.ccic.wm.commons.dataSourse.DataType;

public class Select {
	private String selectType;//查询类型:eq等于,like 模糊查询,notEq不等于,in,ge大于,le小于,in 在..之中,addSql添加sql
	private String propertyName;//变量名 
	private DataType type;//变量名 
	private Object obj;//对象
	private String str;
	private Object lo;//小于对象
	private Object go;//大于对象
	private String[] values; 
	
	public String getSelectType() {
		return selectType;
	}
	public void setSelectType(String selectType) {
		this.selectType = selectType;
	}
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	public Object getLo() {
		return lo;
	}
	public void setLo(Object lo) {
		this.lo = lo;
	}
	public Object getGo() {
		return go;
	}
	public void setGo(Object go) {
		this.go = go;
	}
	public String getStr() {
		return str;
	}
	public void setStr(String str) {
		this.str = str;
	}
	public DataType getType() {
		return type;
	}
	public void setType(DataType type) {
		this.type = type;
	}
	public String[] getValues() {
		return values;
	}
	public void setValues(String[] values) {
		this.values = values;
	}

  
}
