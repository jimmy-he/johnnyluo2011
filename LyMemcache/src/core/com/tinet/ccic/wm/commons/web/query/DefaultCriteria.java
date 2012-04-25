package com.tinet.ccic.wm.commons.web.query;

import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
/**
 * 默认Criteria接口。
 *<p>
 * 文件名： DefaultCriteria.java
 *<p>
 * Copyright (c) 2006-2010 T&I Net Communication CO.,LTD.  All rights reserved.
 * @author 周营昭
 * @since 1.0
 * @version 1.0
 */
public interface DefaultCriteria {
	public void createCriteria(Criteria criteria, Map filterMap);
	public String createCriterion(Criterion criterion,Map filterMap);
}
