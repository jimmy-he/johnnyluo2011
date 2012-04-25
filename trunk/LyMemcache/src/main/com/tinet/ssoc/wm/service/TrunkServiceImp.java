package com.tinet.ssoc.wm.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.tinet.ccic.wm.commons.Constants;
import com.tinet.ccic.wm.commons.dao.BaseDao;
import com.tinet.ccic.wm.commons.service.BaseServiceImp;
import com.tinet.ccic.wm.commons.web.query.Page;
import com.tinet.ssoc.inc.Const;
import com.tinet.ssoc.wm.dao.TrunkDao;
import com.tinet.ssoc.wm.model.Trunk;

/**
 * 目的码业务逻辑接口实现类
 * <p>
 *  文件名：TrunkServiceImp.java
 * <p>
 * Copyright (c) 2006-2011 T&I Net Communication CO.,LTD.  All rights reserved.
 * @author 阎向清
 * @since 1.0
 * @version 1.0
 * @see com.tinet.jboss.wm.number.service.TrunkService
 */
public class TrunkServiceImp extends BaseServiceImp<Trunk> implements TrunkService {

	private TrunkDao trunkDao;
	
	
	public TrunkDao getTrunkDao() {
		return trunkDao;
	}


	public void setTrunkDao(TrunkDao trunkDao) {
		this.trunkDao = trunkDao;
	}


	@Override
	protected BaseDao<Trunk> getBaseDao() {
		return getTrunkDao();
	}

	/**
	 * 查询所有的目的码并分页显示
	 */
	@SuppressWarnings("unchecked")
	public Page<Trunk> getAllTrunk(Integer start,Integer pageSize){
		// 查询所有的目的码，所以filterMap为空
		Map filterMap = new HashMap();
		// 按创建时间倒序排列
		Map orderMap = new HashMap ();
		orderMap.put("createTime", Constants.ORDER_DECEND);
		Page<Trunk> trunkPage = trunkDao.findBy(filterMap, orderMap, start, pageSize);
		return trunkPage;
	}

	/**
	 * 根据号码类型，所属平台，及目的码查询目的码详细信息并分页显示
	 */
	@SuppressWarnings("unchecked")
	public Page<Trunk> getTrunkQuery(Integer start,Integer pageSize,Integer trunkType,Integer ccicId, String trunk){
		//构造查询条件
		Map trunkfilter = new HashMap();
		if (trunkType!=0) { //trunkType==0 表明查询所有类型的目的码
			trunkfilter.put("trunkType", trunkType);
		}
		if (ccicId!=0) { //ccicId==0 表明查询所有平台的目的码
			trunkfilter.put("ccicId", ccicId);
		}
		if (trunk!=null && !"".equals(trunk)) {
			trunkfilter.put("trunk", trunk);
		}
		
		// 按创建时间倒序排列
		Map orderMap = new HashMap ();
		orderMap.put("createTime", Constants.ORDER_DECEND);
		//根据目的码类型和所在平台查询目的码
		Page<Trunk> trunkPage = trunkDao.findBy(trunkfilter, orderMap, start, pageSize);
		return trunkPage;
	}

}
