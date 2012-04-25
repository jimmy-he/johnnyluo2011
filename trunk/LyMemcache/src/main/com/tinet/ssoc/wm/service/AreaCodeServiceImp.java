package com.tinet.ssoc.wm.service;

import com.tinet.ccic.wm.commons.dao.BaseDao;
import com.tinet.ccic.wm.commons.service.BaseServiceImp;
import com.tinet.ssoc.wm.dao.AreaCodeDao;
import com.tinet.ssoc.wm.model.AreaCode;

/**
 * 省份城市区号表业务逻辑实现类
 * <p>
 *  FileName：AreaCodeServiceImp.java
 * <p>
 * Copyright (c) 2006-2011 T&I Net Communication CO.,LTD.  All rights reserved.
 * @author 娄雪
 * @since 1.0
 * @version 1.0
 * @see com.tinet.ccic.wm.common.service.AreaCodeService
 * @see com.tinet.ccic.wm.common.dao.AreaCodeDao
 */

public class AreaCodeServiceImp 
		extends BaseServiceImp<AreaCode> 
		implements AreaCodeService{

	private AreaCodeDao areaCodeDao;
	
	public AreaCodeDao getAreaCodeDao() {
		return areaCodeDao;
	}

	public void setAreaCodeDao(AreaCodeDao areaCodeDao) {
		this.areaCodeDao = areaCodeDao;
	}

	@Override
	protected BaseDao<AreaCode> getBaseDao() {
		return this.getAreaCodeDao();
	}


	
	
}
