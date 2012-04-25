package com.tinet.ssoc.wm.model;


/**
 * SystemSetting系统设置实体类
 * <p>
 * 	文件名： SystemSetting.java
 * <p>
 * Copyright (c) 2006-2011 T&I Net Communication CO.,LTD.  All rights reserved.
 * @author 王云龙
 * @since 1.0
 * @version 1.0
 */
@SuppressWarnings("serial")
public class SystemSetting extends com.tinet.ccic.wm.commons.BaseStandardEntity implements java.io.Serializable {

	// Fields    

	private String name;
	private String value;
	private String property;
	private String nameDesc;
	private String propertyDesc;
	// Constructors

	
	/** default constructor */
	
	
	public SystemSetting() {
	}
	public SystemSetting(Integer id,String name,String value,String property) {
		this.setId(id);
		this.name=name;
		this.value=value;
		this.property=property;
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getProperty() {
		return this.property;
	}

	public void setProperty(String property) {
		this.property = property;
	}
}