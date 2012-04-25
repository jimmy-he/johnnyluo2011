package com.tinet.ssoc.wm.model;

import com.tinet.ssoc.inc.Const;
/**
 * 目的码实体类
 * <p>
 * 	文件名：Trunk.java
 * <p>
 * Copyright (c) 2006-2011 T&I Net Communication CO.,LTD.  All rights reserved.
 * @author 阎向清
 * @since 1.0
 * @version 1.0
 */

public class Trunk extends com.tinet.ccic.wm.commons.BaseStandardEntity
		implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String trunk;
	private Integer trunkType;
	private Integer bureauId;
	private Integer ccicId;
	private String comment;
	private String trunkTypeDesc;
	private Integer status;

	// Constructors

	/** default constructor */
	public Trunk() {
		status = Const.TRUNK_STATUS_UNUSED;
	}

	public Trunk(Integer id, String trunk, Integer trunkType, Integer bureauId, 
			Integer ccicId, String comment) {
		super.setId(id);
		this.trunk = trunk;
		this.trunkType = trunkType;
		this.bureauId = bureauId;
		this.ccicId = ccicId;
		this.comment = comment;
	}
	// Property accessors

	public String getTrunk() {
		return this.trunk;
	}

	public void setTrunk(String trunk) {
		this.trunk = trunk;
	}

	public Integer getBureauId() {
		return this.bureauId;
	}

	public void setBureauId(Integer bureauId) {
		this.bureauId = bureauId;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}


	public Integer getTrunkType() {
		return this.trunkType;
	}

	public void setTrunkType(Integer trunkType) {
		this.trunkType = trunkType;
	}

	public Integer getCcicId() {
		return this.ccicId;
	}

	public void setCcicId(Integer ccicId) {
		this.ccicId = ccicId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getTrunkTypeDesc() {
		switch(getTrunkType()){
			case Const.TRUNK_TRUNK_TYPE_GENERAL:
				this.trunkTypeDesc="普通号码";
				break;
			case Const.TRUNK_TRUNK_TYPE_FAX:
				this.trunkTypeDesc="传真号码";
				break;
			
		}
		return this.trunkTypeDesc;
	}

	public void setTrunkTypeDesc(String trunkTypeDesc) {
		this.trunkTypeDesc = trunkTypeDesc;
	}
}