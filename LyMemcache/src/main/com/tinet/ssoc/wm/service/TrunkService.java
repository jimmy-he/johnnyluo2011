package com.tinet.ssoc.wm.service;

import java.util.List;

import com.tinet.ccic.wm.commons.service.BaseService;
import com.tinet.ccic.wm.commons.web.query.Page;
import com.tinet.ssoc.wm.model.Trunk;

/**
 * 目的码业务逻辑接口
 * <p>
 *  文件名：TrunkService.java
 * <p>
 * Copyright (c) 2006-2011 T&I Net Communication CO.,LTD.  All rights reserved.
 * @author 阎向清
 * @since 1.0
 * @version 1.0
 * @see com.tinet.jboss.wm.number.service.imp.TrunkServiceImp
 */
public interface TrunkService extends BaseService<Trunk> {
	/**
	 * 查询所有的目的码并分页显示
	 */
	public Page<Trunk> getAllTrunk(Integer start,Integer pageSize);
	/**
	 * 根据号码类型，所属平台，及目的码查询目的码详细信息并分页显示
	 */
	public Page<Trunk> getTrunkQuery(Integer start,Integer pageSize,Integer trunkType,Integer ccicId, String trunk);
	
}
