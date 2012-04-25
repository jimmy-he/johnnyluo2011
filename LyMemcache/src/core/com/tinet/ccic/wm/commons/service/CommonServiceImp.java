package com.tinet.ccic.wm.commons.service;

import java.io.Serializable;

import com.tinet.ccic.wm.commons.dao.BaseDao;
/**
 * 公共业务逻辑实现类
*<p>
* 文件名： CommonServiceImp.java
*<p>
* Copyright (c) 2006-2010 T&I Net Communication CO.,LTD.  All rights reserved.
* @author 周营昭
* @since 1.0
* @version 1.0
 */
public class CommonServiceImp extends BaseServiceImp{
    private BaseDao commonDao;
	@Override
	protected BaseDao getBaseDao() {
		
		return commonDao;
	}
	public void setCommonDao(BaseDao commonDao) {
		this.commonDao = commonDao;
	}
	@Override
	public Serializable saveOrUpdate(Object o) {
		
		return null;
	}

}
