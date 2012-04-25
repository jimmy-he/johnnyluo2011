package com.tinet.ccic.wm.commons.web.query;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.impl.CriteriaImpl.OrderEntry;

import com.tinet.ccic.wm.commons.dao.BaseDaoImp;
import com.tinet.ccic.wm.commons.util.BeanUtils;

/**
 * 使用Criteria的分页查询类. 支持执行Count查询, 利用JDBC的SrollLast()功能 和取出全部数据统计size三种方式取得总记录条数
 * 本类大量参考了Feiing, Robbin的设计 see
 * http://www.javaeye.com/display/opensourceframework/HibernateUtils
 *<p>
 * 文件名： CriteriaPage.java
 *<p>
 * Copyright (c) 2006-2010 T&I Net Communication CO.,LTD.  All rights reserved.
 * @author 周营昭
 * @since 1.0
 * @version 1.0
 */
public class CriteriaPage {

	/**
	 * 默认以getCount方式创建Page
	 */
	public static Page getPageInstance(Criteria criteria, int start,
			int pageSize) {
		return getPageInstance(criteria, start, pageSize,
				BaseDaoImp.COUNT_MODE);
	}

	/**
	 * 使用不同模式创建Page.
	 */
	public static Page getPageInstance(Criteria criteria, int start,
			int pageSize, int mode) {
		if (mode == BaseDaoImp.COUNT_MODE)
			return CriteriaPage.getPageInstanceByCount(criteria, start,
					pageSize);
		if (mode == BaseDaoImp.SCROLL_MODE)
			return CriteriaPage.getPageInstanceByScroll(criteria, start,
					pageSize);
		if (mode == BaseDaoImp.LIST_MODE)
			return CriteriaPage.getPageInstanceByList(criteria, start,
					pageSize);

		return null;
	}

	/**
	 * 以查询Count的形式获取totalCount的函数
	 */
	protected static Page getPageInstanceByCount(Criteria criteria, int start,
			int pageSize) {
		CriteriaImpl impl = (CriteriaImpl) criteria;

		// 先把Projection和OrderBy条件取出来,清空两者来执行Count操作
		Projection projection = impl.getProjection();
		List<OrderEntry> orderEntries;
		try {
			orderEntries = (List) BeanUtils.getPrivateProperty(impl,
					"orderEntries");
			BeanUtils.setPrivateProperty(impl, "orderEntries", new ArrayList());
		} catch (Exception e) {
			throw new InternalError(
					" Runtime Exception impossibility can't throw ");
		}

		// 执行查询
		int totalCount = (Integer) criteria.setProjection(
				Projections.rowCount()).uniqueResult();

		// 将之前的Projection和OrderBy条件重新设回去
		criteria.setProjection(projection);
		if (projection == null) {
			criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		}

		try {
			List innerOrderEntries = (List) BeanUtils.getPrivateProperty(impl,
					"orderEntries");
			for (OrderEntry orderEntry : orderEntries) {
				innerOrderEntries.add(orderEntry);
			}
		} catch (Exception e) {
			throw new InternalError(
					" Runtime Exception impossibility can't throw ");
		}

		return getPageResult(criteria, totalCount, start, pageSize);
	}

	/**
	 * 以JDBC Scroll to last的形式获取totalCount的函数
	 */
	protected static Page getPageInstanceByScroll(Criteria criteria,
			int start, int pageSize) {
		ScrollableResults scrollableResults = criteria
				.scroll(ScrollMode.SCROLL_SENSITIVE);
		scrollableResults.last();
		int totalCount = scrollableResults.getRowNumber();
		return getPageResult(criteria, totalCount, start, pageSize);
	}

	/**
	 * 取出全部数据然后取List.size()来获得totalCount的效率最差形式的函数
	 */
	protected static Page getPageInstanceByList(Criteria criteria, int start,
			int pageSize) {
		criteria.scroll(ScrollMode.FORWARD_ONLY);
		int totalCount = criteria.list().size();
		return getPageResult(criteria, totalCount, start, pageSize);
	}

	/**
	 * 取得totalCount后，根据start和PageSize, 执行criteria的分页查询，取得Page变量
	 */
	private static Page getPageResult(Criteria criteria, int totalCount,
			int start, int pageSize) {
		if (totalCount < 1) {
			return new Page(0, 0, pageSize, null);
		}

		List list = criteria.setFirstResult(start).setMaxResults(pageSize)
				.list();

		return new Page(start, totalCount, pageSize, list);
	}

}
