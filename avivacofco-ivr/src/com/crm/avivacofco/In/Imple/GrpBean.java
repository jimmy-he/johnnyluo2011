/**
 * 
 */
package com.crm.avivacofco.In.Imple;

/**
 *@author 罗尧 Email:j2ee.xiao@gmail.com
 *@version V1.0 2010-11-17 下午02:49:53
 */
public class GrpBean {
	/*
	 * 缓存团单信息
	 */
	private String GrpcontNo; //保单号码
	private String RiskSaleCompany; //销售单位名称
	private String InsuredName; //被保险人
	private String GrpInsuranceState;  //保险状态
	private String RiskName; //团单投保险种
	private String RiskAmnt;//团单保险金额
	private String RiskCvalidate;//团单保险责任生效日期
	private String RiskEnddate; //终止日期
	
	/**
	 * @return the grpcontNo
	 */
	public String getGrpcontNo() {
		return GrpcontNo;
	}
	/**
	 * @param grpcontNo the grpcontNo to set
	 */
	public void setGrpcontNo(String grpcontNo) {
		GrpcontNo = grpcontNo;
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
	 * @return the insuredName
	 */
	public String getInsuredName() {
		return InsuredName;
	}
	/**
	 * @param insuredName the insuredName to set
	 */
	public void setInsuredName(String insuredName) {
		InsuredName = insuredName;
	}
	/**
	 * @return the grpInsuranceState
	 */
	public String getGrpInsuranceState() {
		return GrpInsuranceState;
	}
	/**
	 * @param grpInsuranceState the grpInsuranceState to set
	 */
	public void setGrpInsuranceState(String grpInsuranceState) {
		GrpInsuranceState = grpInsuranceState;
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
