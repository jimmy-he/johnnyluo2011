package com.tinet.ssoc.wm.dao;

import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.tinet.ccic.wm.commons.dao.BaseDaoImp;
import com.tinet.ssoc.wm.model.Trunk;

/**
 * 对目的码进行相应操作的实现类
 *<p>
 * 文件名： TrunkDaoImp.java
 *<p>
 * Copyright (c) 2006-2011 T&I Net Communication CO.,LTD.  All rights reserved.
 * @author 阎向清
 * @since 1.0
 * @version 1.0
 * @see  com.tinet.jboss.wm.number.dao.TrunkDao
 */
@SuppressWarnings("unchecked")
public class TrunkDaoImp extends BaseDaoImp<Trunk> implements TrunkDao {

	/**
	 * 目的码查询条件，目的码模糊查询
	 * @param criteria Hibernate的Criteria对象。
	 * @param filterMap 查询条件键值对。
	 */
	@Override
	public void setUpCriteria(Criteria criteria, Map filterMap) {
		for(Object key : filterMap.keySet()){
			if (key.equals((Object)("trunk"))) {
				criteria.add(Restrictions.like(key.toString(), filterMap.get(key).toString(), MatchMode.ANYWHERE));
			} else {
				criteria.add(Restrictions.eq(key.toString(), filterMap.get(key).toString()));
			}
		}
	}

	
}
