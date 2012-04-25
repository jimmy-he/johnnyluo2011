package com.tinet.ssoc.wm.model;

import static javax.persistence.GenerationType.SEQUENCE;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 区号实体类
 * <p>
 * 	FileName: AreaCode.java
 * <p>
 * Copyright (c) 2006-2011 T&I Net Communication CO.,LTD.  All rights reserved.
 * @author 娄雪
 * @since 1.0
 * @version 1.0
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "area_code", schema = "public")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,region="com.tinet.ccic.wm.common.entity.AreaCode")
public class AreaCode extends com.tinet.ccic.wm.commons.BaseStandardEntity implements java.io.Serializable {

	// Fields    

	private String province;
	private String prefix;
	private String areaCode;
	private String city;

	// Constructors

	/** default constructor */
	public AreaCode() {
	}


	public AreaCode(String city, String areaCode){
		this.city = city;
		this.areaCode = areaCode;
	}
	// Property accessors

	@Id
	@SequenceGenerator(name = "generator", sequenceName="area_code_id_seq",allocationSize=1)
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return super.getId();
	}

	@Column(name = "province", length = 20)
	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	@Column(name = "prefix", length = 10)
	public String getPrefix() {
		return this.prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	@Column(name = "area_code", length = 10)
	public String getAreaCode() {
		return this.areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	@Column(name = "city", length = 20)
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

}