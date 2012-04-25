package com.tinet.ssoc.wm.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.tinet.ccic.wm.commons.dao.BaseDaoImp;
import com.tinet.ssoc.wm.model.Entity;

/**
 * entity瀹炰綋琛ㄦ暟鎹瓨鍙栧璞″疄鐜扮被
 *<p>
 * 鏂囦欢鍚嶏細 EntityDaoImp.java
 *<p>
 * Copyright (c) 2006-2010 T&I Net Communication CO.,LTD.  All rights reserved.
 * @author 濞勯洩
 * @since 1.0
 * @version 1.0
 * @see  com.tinet.jboss.wm.dao.EntityDao
 */
@SuppressWarnings("unchecked")
public class EntityDaoImp extends BaseDaoImp<Entity> implements EntityDao {

	
	/**
	 * 瀹℃牳瀹㈡埛绠＄悊鏌ヨ鎸夌収浜屼唬id鏌ヨ鍏舵墍鏈夊鎴枫�
	 * @param criteria Hibernate鐨凜riteria瀵硅薄銆�
	 * @param filterMap 鏌ヨ鏉′欢閿�瀵广�
	 */
	public void setUpCriteria(Criteria criteria, Map filterMap) {
		
		for (Object key : filterMap.keySet()) {
			if (key.equals((Object)("entityParent"))) {
				criteria.add(Restrictions.in(key.toString(), (List<Integer>)filterMap.get(key)));
			} else {
				criteria.add(Restrictions.eq(key.toString(), Integer.parseInt(filterMap.get(key).toString())));
			} 
		}
	}
}