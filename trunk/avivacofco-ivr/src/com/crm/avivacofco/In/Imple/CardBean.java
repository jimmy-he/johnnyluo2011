/**
 * 
 */
package com.crm.avivacofco.In.Imple;

/**
 *@author 罗尧  Email:j2ee.xiao@gmail.com
 *@version V1.0 2010-11-17   下午04:45:29
 */
public class CardBean {
	/*
	 * 缓存卡单数据
	 */
	private String ContCardNo;  //卡单号码
	private String ContCardName; //卡单类型
	private String ContCardPrem; //保险费用
	private String RiskSaleCompany;//销售单位
	private String ContCardState;//卡单状态
	private String RiskName;//卡单投保险种
	private String RiskAmnt;//卡单保险金额
	private String RiskCvalidate;//卡单保险责任生效日期
	private String RiskEnddate;//终止日期
	/**
	 * @return the contCardNo
	 */
	public String getContCardNo() {
		return ContCardNo;
	}
	/**
	 * @param contCardNo the contCardNo to set
	 */
	public void setContCardNo(String contCardNo) {
		ContCardNo = contCardNo;
	}
	/**
	 * @return the contCardName
	 */
	public String getContCardName() {
		return ContCardName;
	}
	/**
	 * @param contCardName the contCardName to set
	 */
	public void setContCardName(String contCardName) {
		ContCardName = contCardName;
	}
	/**
	 * @return the contCardPrem
	 */
	public String getContCardPrem() {
		return ContCardPrem;
	}
	/**
	 * @param contCardPrem the contCardPrem to set
	 */
	public void setContCardPrem(String contCardPrem) {
		ContCardPrem = contCardPrem;
	}
	/**
	 * @return the riskSaleCompany
	 */
	public String getRiskSaleCompany() {
		return RiskSaleCompany;
	}
	/**
	 * @param riskSaleCompany the riskSaleCompany to set
	 */
	public void setRiskSaleCompany(String riskSaleCompany) {
		RiskSaleCompany = riskSaleCompany;
	}
	/**
	 * @return the contCardState
	 */
	public String getContCardState() {
		return ContCardState;
	}
	/**
	 * @param contCardState the contCardState to set
	 */
	public void setContCardState(String contCardState) {
		ContCardState = contCardState;
	}
	/**
	 * @return the riskName
	 */
	public String getRiskName() {
		return RiskName;
	}
	/**
	 * @param riskName the riskName to set
	 */
	public void setRiskName(String riskName) {
		RiskName = riskName;
	}
	/**
	 * @return the riskAmnt
	 */
	public String getRiskAmnt() {
		return RiskAmnt;
	}
	/**
	 * @param riskAmnt the riskAmnt to set
	 */
	public void setRiskAmnt(String riskAmnt) {
		RiskAmnt = riskAmnt;
	}
	/**
	 * @return the riskCvalidate
	 */
	public String getRiskCvalidate() {
		return RiskCvalidate;
	}
	/**
	 * @param riskCvalidate the riskCvalidate to set
	 */
	public void setRiskCvalidate(String riskCvalidate) {
		RiskCvalidate = riskCvalidate;
	}
	/**
	 * @return the riskEnddate
	 */
	public String getRiskEnddate() {
		return RiskEnddate;
	}
	/**
	 * @param riskEnddate the riskEnddate to set
	 */
	public void setRiskEnddate(String riskEnddate) {
		RiskEnddate = riskEnddate;
	}
	
}
