package com.tinet.ssoc.wm.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tinet.ccic.wm.commons.Constants;
import com.tinet.ccic.wm.commons.dao.BaseDao;
import com.tinet.ccic.wm.commons.service.BaseServiceImp;
import com.tinet.ccic.wm.commons.util.DateUtil;
import com.tinet.ccic.wm.commons.util.MD5Encoder;
import com.tinet.ccic.wm.commons.web.query.Page;
import com.tinet.ssoc.inc.Const;
import com.tinet.ssoc.inc.Macro;
import com.tinet.ssoc.util.RemoteClient;
import com.tinet.ssoc.wm.dao.EntityDao;
import com.tinet.ssoc.wm.model.Entity;

/**
 * 系统用户管理业务逻辑接口实现类。
 * <p>
 *  文件名：EntityServiceImp.java
 * <p>
 * Copyright (c) 2006-2010 T&I Net Communication CO.,LTD.  All rights reserved.
 * @author 娄雪
 * @since 1.0
 * @version 1.0
 * @see com.tinet.jboss.wm.service.EntityService
 * @see com.tinet.jboss.wm.dao.EntityDao
 */

public class EntityServiceImp extends BaseServiceImp<Entity> implements EntityService {
	
	private EntityDao entityDao;

	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

	@Override
	protected BaseDao<Entity> getBaseDao() {
		return getEntityDao();
	}

	/**
	 * 获取所有系统用户，包括entityType=11,12,13,14,15,16的。
	 * @return 返回entity列表。
	 */
	@SuppressWarnings("unchecked")
	public List<Entity> getAllSysUsers() {
		StringBuffer hqlStr = new StringBuffer("select new Entity(e.id,e.entityName,e.entityPwd,e.fullName,e.entityType,e.status) from Entity e ");
		hqlStr.append(" where e.entityType in(");
		StringBuffer entityTypeCondition = new StringBuffer();
		entityTypeCondition.append(Const.ENTITY_ENTITY_TYPE_ADMIN)
			.append(",").append(Const.ENTITY_ENTITY_TYPE_CASHIER)
			.append(",").append(Const.ENTITY_ENTITY_TYPE_CUSTOMER_SERVICE)
			.append(",").append(Const.ENTITY_ENTITY_TYPE_CUSTOMER_SERVICE_MAINTAINER)
			.append(",").append(Const.ENTITY_ENTITY_TYPE_AREA_SUPERVISOR)
			.append(",").append(Const.ENTITY_ENTITY_TYPE_CHANNEL_MANAGER);
		hqlStr.append(entityTypeCondition).append(") order by e.entityType,e.entityName");
		List<Entity> sysUersList = (List<Entity>)this.searchByHql(hqlStr.toString());
		return sysUersList;
	}
	
	
}
