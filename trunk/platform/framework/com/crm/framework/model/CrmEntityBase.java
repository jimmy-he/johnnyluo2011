package com.crm.framework.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.crm.framework.common.beanfactory.CrmBeanFactory;
import com.crm.framework.common.util.BeanUtil;
import com.crm.framework.crmbean.interfaces.CrmUser;

/**
 * 所有数据库模型的基类
 * 
 * @author 王永明
 * @since Jan 27, 2010 5:00:49 PM
 */
@MappedSuperclass
public class CrmEntityBase implements Serializable {
	
	private String tenantId;
	private Date createDate;
	private Date updateDate;
	private String createUserId;
	private String createOrganId;
	private String updateUserId;
	private String updateOrganId;
	private Date createDateBegin;
	private Date createDateEnd;
	private Date updateDateBegin;
	private Date updateDateEnd;

	@Column(name = "tenant_id",length=60)
	public String getTenantId() {
	
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	@Column
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Column
	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	@Column
	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	@Column
	public String getCreateOrganId() {
		return createOrganId;
	}

	public void setCreateOrganId(String createOrganId) {
		this.createOrganId = createOrganId;
	}

	@Column
	public String getUpdateOrganId() {
		return updateOrganId;
	}

	public void setUpdateOrganId(String updateOrganId) {
		this.updateOrganId = updateOrganId;
	}

	@Transient
	public Date getCreateDateBegin() {
		return createDateBegin;
	}

	public void setCreateDateBegin(Date createDateBegin) {
		this.createDateBegin = createDateBegin;
	}

	@Transient
	public Date getCreateDateEnd() {
		return createDateEnd;
	}

	public void setCreateDateEnd(Date createDateEnd) {
		this.createDateEnd = createDateEnd;
	}

	@Transient
	public Date getUpdateDateEnd() {
		return updateDateEnd;
	}

	public void setUpdateDateEnd(Date updateDateEnd) {
		this.updateDateEnd = updateDateEnd;
	}

	@Transient
	public Date getUpdateDateBegin() {
		return updateDateBegin;
	}

	public void setUpdateDateBegin(Date updateDateBegin) {
		this.updateDateBegin = updateDateBegin;
	}

	/** 准备新增一条数据 */
	public void prepareInsert(CrmUser user) {
		Date now = new Date();
		this.setTenantId(user.getTenantId());
		this.setCreateDate(now);
		this.setCreateUserId(user.getUserId());
		this.setCreateOrganId(user.getOrganId());
		this.setUpdateDate(now);
		this.setUpdateUserId(user.getUserId());
		this.setUpdateOrganId(user.getOrganId());
		this.checkPk(true);
	}

	/** 准备更新一条数据 */
	public void prepareUpdate(CrmUser user) {
		Date now = new Date();
		this.setUpdateDate(now);
		this.setUpdateUserId(user.getUserId());
		this.setUpdateOrganId(user.getOrganId());
	}

	/** 检查一个新增对象的主键是否存在,如果不存在就设置id的值 */
	public void checkPk(boolean checkExist) {
		String idName = this.mapping().getPkName();
		if (idName != null) {
			String id = (String) BeanUtil.getValue(this, idName);
			if (id == null || !checkExist) {
				String uuid = (UUID.randomUUID() + "").replaceAll("-", "");
				id = this.getClass().getSimpleName() + uuid;
				BeanUtil.setField(this, idName, id);
			}
		}
	}

	/** 设置一个对象的id */
	public void initPk() {
		this.checkPk(false);
	}
	
	/** 设置一个对象的id */
	public void addPk(String id) {
		BeanUtil.setField(this, this.pkName(), id);
	}
	
	/**获得主键名称*/
	public String pkName(){
		return  this.mapping().getPkName();
	}

	/**获得类映射信息*/
	public MappingEntity mapping(){
		return (MappingEntity) CrmBeanFactory.getBean(MappingCache.class).get(this.getClass().getName());
	}
	
	
	/**获得对象的主角值*/
	
	public String pk(){
		return (String) BeanUtil.getValue(this, this.pkName());
	}
	
	/**获得初始化某个字段的hql*/
	public String initHql(String fieldName){
		return this.mapping().getOneToManyHql(fieldName, this.pk());
	}

}

