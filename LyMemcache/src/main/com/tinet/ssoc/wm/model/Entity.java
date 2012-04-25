package com.tinet.ssoc.wm.model;
import com.tinet.ssoc.inc.Const;


/**
 * entity用户实体类
 * <p>
 * 	文件名： Entity.java
 * <p>
 * Copyright (c) 2006-2011 T&I Net Communication CO.,LTD.  All rights reserved.
 * @author 娄雪
 * @since 1.0
 * @version 1.0
 */

@SuppressWarnings("serial")
public class Entity extends com.tinet.ccic.wm.commons.BaseStandardEntity implements java.io.Serializable {

	// Fields    

	private String entityName;
	private String entityPwd;
	private Integer entityType;
	private Integer entityParent;
	private Integer entityParentType;
	private String entitySn;
	private String fullName;
	private String areaCode;
	private String principal;
	private Integer sex;
	private String sexTitle;
	private String mobile;
	private String tel;
	private String email;
	private String fax;
	private String address;
	private String post;
	private Integer status;
	private String legalPerson;
	private String trade;
	private String businessNo;
	private String question;
	private String answer;
	private String website;
	private Integer certificateType;
	private String certificateId;
	private String certificateTime;
	private String comment;
	private String country;
	private String state;
	private String city;
	private String language;
	private String roleName;
	private String statusDesc;
	private String roleSex;
	private String roleCertificate;
	private String entityParentName;
	private String directSectionId;
	// Constructors

	/** default constructor */
	public Entity() {
		this.sex = Const.ENTITY_SEX_DEFAULT;
		this.sexTitle = "先生";
		this.entityParent = Const.ENTITY_ENTITY_PARENT_DEFAULT;
		this.certificateType = Const.ENTITY_CERTIFICATE_TYPE_DEFAULT;
		this.status = Const.ENTITY_STATUS_OK;
		this.country = Const.ENTITY_COUNTRY_DEFAULT;
		this.language = Const.ENTITY_LANGUAGE_DEFAULT;
		this.answer = "";
		this.question = "";
		this.certificateId = "";
		this.certificateTime = "";
		this.state = "";
		this.city = "";
	}

	public Entity (Integer id, String entityName, String entityPwd, String fullName, Integer entityType, Integer status) {
		this.setId(id);
		this.entityName = entityName;
		this.entityPwd = entityPwd;
		this.fullName = fullName;
		this.entityType = entityType;
		this.status = status;
	}
	
	public Entity(String entityName, String entityPwd,Integer entityType, Integer entityParent,
			String entitySn, String fullName, String areaCode,
			String principal, Integer sex, String sexTitle, String mobile,
			String tel, String email, String fax, String address, String post,
			Integer status, String legalPerson, String trade,
			String businessNo, String website, Integer certificateType,
			String certificateId, String certificateTime, String comment,
			String state, String city) {
		super();
		this.entityName = entityName;
		this.entityPwd = entityPwd;
		this.entityType= entityType;
		this.entityParent = entityParent;
		this.entitySn = entitySn;
		this.fullName = fullName;
		this.areaCode = areaCode;
		this.principal = principal;
		this.sex = sex;
		this.sexTitle = sexTitle;
		this.mobile = mobile;
		this.tel = tel;
		this.email = email;
		this.fax = fax;
		this.address = address;
		this.post = post;
		this.status = status;
		this.legalPerson = legalPerson;
		this.trade = trade;
		this.businessNo = businessNo;
		this.website = website;
		this.certificateType = certificateType;
		this.certificateId = certificateId;
		this.certificateTime = certificateTime;
		this.comment = comment;
		this.state = state;
		this.city = city;
	}
	// Property accessors

	public String getEntityName() {
		return this.entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getEntityPwd() {
		return this.entityPwd;
	}

	public void setEntityPwd(String entityPwd) {
		this.entityPwd = entityPwd;
	}

	public Integer getEntityType() {
		return this.entityType;
	}

	public void setEntityType(Integer entityType) {
		this.entityType = entityType;
	}

	public Integer getEntityParent() {
		return this.entityParent;
	}

	public void setEntityParent(Integer entityParent) {
		this.entityParent = entityParent;
	}

	public Integer getEntityParentType() {
		return entityParentType;
	}

	public void setEntityParentType(Integer entityParentType) {
		this.entityParentType = entityParentType;
	}

	public String getEntitySn() {
		return this.entitySn;
	}

	public void setEntitySn(String entitySn) {
		this.entitySn = entitySn;
	}

	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getAreaCode() {
		return this.areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getPrincipal() {
		return this.principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public Integer getSex() {
		return this.sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getSexTitle() {
		return this.sexTitle;
	}

	public void setSexTitle(String sexTitle) {
		this.sexTitle = sexTitle;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPost() {
		return this.post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getLegalPerson() {
		return this.legalPerson;
	}

	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}

	public String getTrade() {
		return this.trade;
	}

	public void setTrade(String trade) {
		this.trade = trade;
	}

	public String getBusinessNo() {
		return this.businessNo;
	}

	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}

	public String getQuestion() {
		return this.question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return this.answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getWebsite() {
		return this.website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public Integer getCertificateType() {
		return this.certificateType;
	}

	public void setCertificateType(Integer certificateType) {
		this.certificateType = certificateType;
	}

	public String getCertificateId() {
		return this.certificateId;
	}

	public void setCertificateId(String certificateId) {
		this.certificateId = certificateId;
	}

	public String getCertificateTime() {
		return this.certificateTime;
	}

	public void setCertificateTime(String certificateTime) {
		this.certificateTime = certificateTime;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getLanguage() {
		return this.language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getRoleName() {
		switch(getEntityType()){
			case Const.ENTITY_ENTITY_TYPE_AGENT1: 
					this.roleName = "一级代理商";
					break;
			case Const.ENTITY_ENTITY_TYPE_AGENT2: 
				this.roleName = "二级代理商";
				break;
			case Const.ENTITY_ENTITY_TYPE_ENTERPRISE: 
				this.roleName = "客户";
				break;
			case Const.ENTITY_ENTITY_TYPE_DIRECT_SECTION: 
				this.roleName = "直销经理";
				break;
			case Const.ENTITY_ENTITY_TYPE_DIRECT_MANAGER: 
				this.roleName = "销售经理";
				break;
			case Const.ENTITY_ENTITY_TYPE_ADMIN: 
				this.roleName = "系统管理员";
				break;
			case Const.ENTITY_ENTITY_TYPE_CASHIER: 
				this.roleName = "出纳";
				break;
			case Const.ENTITY_ENTITY_TYPE_CUSTOMER_SERVICE: 
				this.roleName = "客服";
				break;
			case Const.ENTITY_ENTITY_TYPE_CUSTOMER_SERVICE_MAINTAINER: 
				this.roleName = "维护客服";
				break;
			case Const.ENTITY_ENTITY_TYPE_AREA_SUPERVISOR: 
				this.roleName = "大区总监";
				break;
			case Const.ENTITY_ENTITY_TYPE_CHANNEL_MANAGER: 
				this.roleName = "渠道经理";
				break;
			default: this.roleName = "未知用户类型";
		}
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getStatusDesc() {
		switch(getStatus()){
			case Const.ENTITY_STATUS_OK:
				this.statusDesc="正常";
				break;
			case Const.ENTITY_STATUS_FORBIDDEN:
				this.statusDesc="暂停";
				break;
			default:this.statusDesc="未知状态";
		}
		return this.statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	public String getRoleSex() {
		switch(getSex()){
		case Const.ENTITY_SEX_MALE:
			this.roleSex = "男";
			break;
		case Const.ENTITY_SEX_FEMALE:
			this.roleSex = "女";
			break;
		default:this.roleSex="未知性别";
		}
		return this.roleSex;
	}

	public void setRoleSex(String roleSex) {
		this.roleSex = roleSex;
	}

	public String getRoleCertificate() {
		switch(getCertificateType()){
		case Const.ENTITY_CERTIFICATE_TYPE_IDENTITY:
			this.roleCertificate = "身份证";
			break;
		case Const.ENTITY_CERTIFICATE_TYPE_PASSPORT:
			this.roleCertificate = "护照";
			break;
		case Const.ENTITY_CERTIFICATE_TYPE_OTHER:
			this.roleCertificate = "其它证";
			break;
		default:this.roleCertificate = "未知证件";
		}
		return this.roleCertificate;
	}

	public void setRoleCertificate(String roleCertificate) {
		this.roleCertificate = roleCertificate;
	}

	public String getEntityParentName() {
		return entityParentName;
	}

	public void setEntityParentName(String entityParentName) {
		this.entityParentName = entityParentName;
	}

	public String getDirectSectionId() {
		return directSectionId;
	}

	public void setDirectSectionId(String directSectionId) {
		this.directSectionId = directSectionId;
	}

}