package com.tinet.ssoc.wm.service;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tinet.ccic.wm.commons.service.BaseService;
import com.tinet.ccic.wm.commons.web.query.Page;
import com.tinet.ssoc.wm.model.Entity;

/**
 * 系统用户管理业务逻辑接口
 * <p>
 *  文件名：EntityService.java
 * <p>
 * Copyright (c) 2006-2011 T&I Net Communication CO.,LTD.  All rights reserved.
 * @author 娄雪
 * @since 1.0
 * @version 1.0
 * @see com.tinet.jboss.wm.service.imp.EntityServiceImp
 */

public interface EntityService extends BaseService<Entity>{

	/**
	 * 获取所有系统用户，包括entityType=11,12,13,14,15,16的。
	 * @return 返回entity列表。
	 */
	public List<Entity> getAllSysUsers();

}
