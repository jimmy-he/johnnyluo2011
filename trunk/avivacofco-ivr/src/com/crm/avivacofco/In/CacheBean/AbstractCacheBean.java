/**
 * 
 */
package com.crm.avivacofco.In.CacheBean;

import org.dom4j.Element;


/**
 *@author 罗尧  Email:j2ee.xiao@gmail.com
 *@version V1.0 2010-10-25   下午05:33:24
 */
public abstract class AbstractCacheBean {
	
	public abstract String sfYanZheng();
	public abstract String bdYanZheng();
	public abstract String liPei();
	public abstract String bdXinXi();
	public abstract String ZHjianChe();
	public abstract String PolicyStatus();
	public abstract String bdzlxqsf();
	
	public abstract String BorrowFunds();
	public abstract String ExistJing();
	public abstract String AllPowerfulType();
	public abstract String SubsistOverOneYear();
	public abstract String WonderfulRepeatedly();
	public abstract String WonderfulRepeatedlyB();
	
	/*
	 * 每次传递进来的ID_NO
	 */
	private String ID_NO;
	/*
	 * 通道号
	 */
	protected String ChannelNo;
	/*
	 * 命令号
	 */
	private String CommandNo;
	/*
	 * 个险报文
	 */
	private String onexml;
	/*
	 * 完整的个险保单号码
	 */
	private String policy_no;
	/*
	 * 6位保单号、团单号、卡单号
	 */
	private String six_one_no;
	private String six_group_no;
	private String six_card_no;
	/*
	 * 个险中的分公司号码
	 */
	private String company_code;
	/*
	 * 个险中判断是否有返回报文
	 */
	private Element onenumber;
	/*
	 * 团险和卡单中是否有返回报文
	 */
	private Element groupidnumber;
	private Element cardgroupidnumber;
	/*
	 * 团险报文
	 */
	private String groupxml;

	/*
	 * 团险报文中的保费
	 */
	private String ContCardPrem;
	/*
	 * 第一个存储过程的渠道号码，缓存起来
	 */
	private String qudao;
	/*
	 * 第一个存储过程返回的分公司号
	 */
	private String Cp_NO;
	/*
	 * 第一个存储过程返回的身份证号码
	 */
	private String SF;
	/*
	 * 第一个存储过程返回的代理人工号
	 */
	private String agentNO;
	/*
	 * 第二个存储过程返回的保单号
	 */
	private String twobdString;
	/*
	 * 第二个存储过程返回的保单状态
	 */
	private String BDZT;
	/*
	 * 第二个存储过程返回的金额
	 */
	private String amount;
	/*
	 * 第二个存储过程返回的缴别类型
	 */
	private String jbLX;
	private String nextDay;
	/*
	 * 每个实现类最后返回的值都在此统一
	 */
	private String returnString;
		
	/**
	 * @return the nextDay
	 */
	public String getNextDay() {
		return nextDay;
	}
	/**
	 * @param nextDay the nextDay to set
	 */
	public void setNextDay(String nextDay) {
		this.nextDay = nextDay;
	}
	/**
	 * @return the jbLX
	 */
	public String getJbLX() {
		return jbLX;
	}
	/**
	 * @param jbLX the jbLX to set
	 */
	public void setJbLX(String jbLX) {
		this.jbLX = jbLX;
	}
	
	/**
	 * @return the agentNO
	 */
	public String getAgentNO() {
		return agentNO;
	}
	/**
	 * @param agentNO the agentNO to set
	 */
	public void setAgentNO(String agentNO) {
		this.agentNO = agentNO;
	}
	/**
	 * @return the twobdString
	 */
	public String getTwobdString() {
		return twobdString;
	}
	/**
	 * @param twobdString the twobdString to set
	 */
	public void setTwobdString(String twobdString) {
		this.twobdString = twobdString;
	}

	
	
	/**
	 * @return the amount
	 */
	public String getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}
	/**
	 * @return the bDZT
	 */
	public String getBDZT() {
		return BDZT;
	}
	/**
	 * @param bDZT the bDZT to set
	 */
	public void setBDZT(String bDZT) {
		BDZT = bDZT;
	}
	/**
	 * @return the sF
	 */
	public String getSF() {
		return SF;
	}
	/**
	 * @param sF the sF to set
	 */
	public void setSF(String sF) {
		SF = sF;
	}

	/**
	 * @return the iD_NO
	 */
	public String getID_NO() {
		return ID_NO;
	}

	/**
	 * @param iDNO the iD_NO to set
	 */
	public void setID_NO(String iDNO) {
		ID_NO = iDNO;
	}

	/**
	 * @return the channelNo
	 */
	public String getChannelNo() {
		return ChannelNo;
	}

	/**
	 * @param channelNo the channelNo to set
	 */
	public void setChannelNo(String channelNo) {
		ChannelNo = channelNo;
	}

	/**
	 * @return the commandNo
	 */
	public String getCommandNo() {
		return CommandNo;
	}

	/**
	 * @param commandNo the commandNo to set
	 */
	public void setCommandNo(String commandNo) {
		CommandNo = commandNo;
	}

	/**
	 * @return the onexml
	 */
	public String getOnexml() {
		return onexml;
	}

	/**
	 * @param onexml the onexml to set
	 */
	public void setOnexml(String onexml) {
		this.onexml = onexml;
	}

	/**
	 * @return the groupxml
	 */
	public String getGroupxml() {
		return groupxml;
	}

	/**
	 * @param groupxml the groupxml to set
	 */
	public void setGroupxml(String groupxml) {
		this.groupxml = groupxml;
	}

	/**
	 * @return the cp_NO
	 */
	public String getCp_NO() {
		return Cp_NO;
	}

	/**
	 * @param cpNO the cp_NO to set
	 */
	public void setCp_NO(String cpNO) {
		Cp_NO = cpNO;
	}

	/**
	 * @return the returnString
	 */
	public String getReturnString() {
		return returnString;
	}

	/**
	 * @param returnString the returnString to set
	 */
	public void setReturnString(String returnString) {
		this.returnString = returnString;
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
	 * @return the qudao
	 */
	public String getQudao() {
		return qudao;
	}
	/**
	 * @param qudao the qudao to set
	 */
	public void setQudao(String qudao) {
		this.qudao = qudao;
	}
	
	
	
	/**
	 * @return the six_one_no
	 */
	public String getSix_one_no() {
		return six_one_no;
	}
	/**
	 * @param sixOneNo the six_one_no to set
	 */
	public void setSix_one_no(String sixOneNo) {
		six_one_no = sixOneNo;
	}
	/**
	 * @return the six_group_no
	 */
	public String getSix_group_no() {
		return six_group_no;
	}
	/**
	 * @param sixGroupNo the six_group_no to set
	 */
	public void setSix_group_no(String sixGroupNo) {
		six_group_no = sixGroupNo;
	}
	/**
	 * @return the six_card_no
	 */
	public String getSix_card_no() {
		return six_card_no;
	}
	/**
	 * @param sixCardNo the six_card_no to set
	 */
	public void setSix_card_no(String sixCardNo) {
		six_card_no = sixCardNo;
	}
	/**
	 * @return the onenumber
	 */
	public Element getOnenumber() {
		return onenumber;
	}
	/**
	 * @param onenumber the onenumber to set
	 */
	public void setOnenumber(Element onenumber) {
		this.onenumber = onenumber;
	}
	/**
	 * @return the groupidnumber
	 */
	public Element getGroupidnumber() {
		return groupidnumber;
	}
	/**
	 * @param groupidnumber the groupidnumber to set
	 */
	public void setGroupidnumber(Element groupidnumber) {
		this.groupidnumber = groupidnumber;
	}
	/**
	 * @return the cardgroupidnumber
	 */
	public Element getCardgroupidnumber() {
		return cardgroupidnumber;
	}
	/**
	 * @param cardgroupidnumber the cardgroupidnumber to set
	 */
	public void setCardgroupidnumber(Element cardgroupidnumber) {
		this.cardgroupidnumber = cardgroupidnumber;
	}
	/**
	 * @return the policy_no
	 */
	public String getPolicy_no() {
		return policy_no;
	}
	/**
	 * @param policyNo the policy_no to set
	 */
	public void setPolicy_no(String policyNo) {
		policy_no = policyNo;
	}
	/**
	 * @return the company_code
	 */
	public String getCompany_code() {
		return company_code;
	}
	/**
	 * @param companyCode the company_code to set
	 */
	public void setCompany_code(String companyCode) {
		company_code = companyCode;
	}
	
	
}
