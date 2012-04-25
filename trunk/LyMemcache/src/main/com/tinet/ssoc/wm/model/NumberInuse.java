package com.tinet.ssoc.wm.model;

import java.math.BigDecimal;

import com.tinet.ssoc.wm.model.Ccic;

/**
 * 已售400号码实体类
 * <p>
 * 	文件名： NumberInuse.java
 * <p>
 * Copyright (c) 2006-2011 T&I Net Communication CO.,LTD.  All rights reserved.
 * @author 王云龙
 * @author 娄雪
 * @since 1.0
 * @version 1.0
 */

@SuppressWarnings("serial")
public class NumberInuse extends com.tinet.ccic.wm.commons.BaseStandardEntity implements java.io.Serializable {

	// Fields    

	private Integer numberId;
	private String number;
	private Integer enterpriseId;
	private BigDecimal priceNext;
	private Ccic ccic;

	// Constructors

	/** default constructor */
	public NumberInuse() {
	}

	// Property accessors

	public Integer getNumberId() {
		return this.numberId;
	}

	public void setNumberId(Integer numberId) {
		this.numberId = numberId;
	}

	public String getNumber() {
		return this.number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Integer getEnterpriseId() {
		return this.enterpriseId;
	}

	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public BigDecimal getPriceNext() {
		return priceNext;
	}

	public void setPriceNext(BigDecimal priceNext) {
		this.priceNext = priceNext;
	}

	public Ccic getCcic() {
		return ccic;
	}

	public void setCcic(Ccic ccic) {
		this.ccic = ccic;
	}
}