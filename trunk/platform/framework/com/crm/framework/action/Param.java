package com.crm.framework.action;

import java.io.Serializable;

import org.bouncycastle.crypto.RuntimeCryptoException;

import com.crm.framework.common.util.DateTime;
import com.crm.framework.common.util.StringUtil;

/**
 * 通用查询操作的查询字段,因为是通用操作,所以字段必须带上字段类型
 * @author 王永明
 * @since 2010-9-12 下午05:28:14
 */
public class Param implements Serializable{
	private String name;
	private String code;
	private String value;
	private String type;
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

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	public Object getRealVale() {
		if(StringUtil.isNull(type)){
			return value.replace('*', '%');
		}
		if(type.equals("String")){
			return value.replace('*', '%');
		}
		if(type.equals("int")){
			return Integer.parseInt(value);
		}
		if(type.equals("long")){
			return Long.parseLong(value);
		}
		if(type.equals("date")){
			return new DateTime(value,DateTime.YEAR_TO_DAY);
		}
		if(type.equals("dateTime")){
			return new DateTime(value,DateTime.YEAR_TO_SECOND);
		}
		throw new RuntimeException("目前还支持"+type+"类型的参数!!");
	}
}
