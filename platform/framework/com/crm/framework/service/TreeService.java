package com.crm.framework.service;

import java.text.MessageFormat;
import java.util.List;

import com.crm.framework.common.beanfactory.CrmBeanFactory;
import com.crm.framework.common.exception.SystemException;
import com.crm.framework.common.i18n.DefaultTextProvider;
import com.crm.framework.common.util.StringUtil;
import com.crm.framework.common.util.tree.PersistenceNode;
import com.crm.framework.dao.selector.Selector;

/**
 * 操作树的service类
 * 
 * @author 王永明
 * @since May 22, 2010 10:53:11 AM
 */
public class TreeService extends SimpleService {

	/** 增加一个节点 */
	public void addNode(PersistenceNode newNode) {
		// 如果没设置显示序号
		int max = this.getMaxNo(newNode.getClass(), newNode, this.getUser().getTenantId())+1;
		if (newNode.getDisplayNo() == null || newNode.getDisplayNo() > max) {
			newNode.setDisplayNo(max);
		}
		if (newNode.getDisplayNo() < 1) {
			newNode.setDisplayNo(1);
		}
		this.addDisplayNo(newNode.getClass(), newNode, this.getUser().getTenantId());
		this.getDao().insert(newNode);
	}

	/** 获得最大显示序号 */
	private int getMaxNo(Class clazz, PersistenceNode newNode, String tenantId) {
		String hql = "select count(*) from {0} where {1}=?";
		if (StringUtil.isNull(newNode.getParentId())) {
			hql = "select count(*) from {0} where {1} is null";
		}
		if (tenantId != null) {
			hql += " and tenantId=?'";
		}
		hql = MessageFormat.format(hql, clazz.getName(), newNode.getParentField());

		if (StringUtil.isNull(newNode.getParentId())) {
			return super.getFirstInt(Selector.forHql(hql, tenantId));
		} else {
			return super.getFirstInt(Selector.forHql(hql, newNode.getParentId(), tenantId));
		}

	}

	/** 删除节点 */
	public String delNode(Class clazz, PersistenceNode node) {
		String tenantId = this.getUser().getTenantId();
		String hql = "from " + clazz.getName() + " where " + node.getParentField() + "=?";
		node=super.get(node.getClass(), node.getId());
		
		if (tenantId != null) {
			hql += " and tenantId=?";
		}
		List list = super.queryByHql(hql, node.getId(), tenantId);
		if (list.size() != 0) {
			String msg = CrmBeanFactory.getBean(DefaultTextProvider.class).getText("delSubNode");
			return msg;
		}

		PersistenceNode old = node;
		if (node.getDisplayNo() == null) {
			old = this.getDao().get(node.getClass(), node.getId());
		}
		this.reduceDisplayNo(node.getClass(), old, this.getUser().getTenantId());
		this.getDao().delete(node);
		return "success";
	}

	/** 更新节点 */
	public void updateNode(PersistenceNode newNode) {
		String tenantId = this.getUser().getTenantId();
		// 如果显示序号没变,就只更新基本信息
		if (newNode.getDisplayNo() == null) {
			this.update(newNode.getClass(), newNode.getId(), newNode);
			return;
		}
		PersistenceNode old = this.getDao().get(newNode.getClass(), newNode.getId());

		// 如果没设置显示序号
		int max = this.getMaxNo(newNode.getClass(), newNode, tenantId);
		if (newNode.getDisplayNo() == null || newNode.getDisplayNo() > max) {
			newNode.setDisplayNo(max);
		}
		if (newNode.getDisplayNo() < 1) {
			newNode.setDisplayNo(1);
		}

		this.reduceDisplayNo(newNode.getClass(), old, this.getUser().getTenantId());
		this.addDisplayNo(newNode.getClass(), newNode, this.getUser().getTenantId());
		this.update(newNode.getClass(), newNode.getId(), newNode);
	}

	/** 移除一个节点,修改相关的显示序号 */
	private int reduceDisplayNo(Class clazz, PersistenceNode node, String tenantId) {
		String updateHql = "update {0} set {1}={1}-1 where {1}>=? and {2}=?";

		if (StringUtil.isNull(node.getParentId())) {
			updateHql = "update {0} set {1}={1}-1 where {1}>=? and {2} is null";
		}

		if (tenantId != null) {
			updateHql += " and tenantId=?";
		}
		String hql = MessageFormat.format(updateHql, clazz.getName(), node.getDisplayFiled(), node.getParentField());

		if (StringUtil.isNull(node.getParentId())) {
			return this.getDao().execHql(clazz, hql, node.getDisplayNo(), tenantId);
		} else {
			return this.getDao().execHql(clazz, hql, node.getDisplayNo(), node.getParentId(), tenantId);
		}
	}

	/** 新增一个节点,修改相关的显示序号 */
	private int addDisplayNo(Class clazz, PersistenceNode node, String tenantId) {
		String updateHql = "update {0} set {1}={1}+1 where {1}>=? and {2}=?";

		if (StringUtil.isNull(node.getParentId())) {
			updateHql = "update {0} set {1}={1}+1 where {1}>=? and {2} is null";
		}

		if (tenantId != null) {
			updateHql += " and tenantId=?";
		}
		String hql = MessageFormat.format(updateHql, clazz.getName(), node.getDisplayFiled(), node.getParentField());

		if (StringUtil.isNull(node.getParentId())) {
			return this.getDao().execHql(clazz, hql, node.getDisplayNo(), tenantId);
		} else {
			return this.getDao().execHql(clazz, hql, node.getDisplayNo(), node.getParentId(), tenantId);
		}

	}

}
