package com.tinet.ccic.wm.commons.web.query;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.transform.ResultTransformer;

import com.tinet.ccic.wm.commons.dao.BaseDaoImp;

/**
 * 使用Hql查询的的分页查询类. 支持执行Count查询, 利用JDBC的SrollLast()功能 和取出全部数据统计size三种方式取得总记录条数
 * 本类参考了Feiing, Robbin的设计 see
 *<p>
 * 文件名： HqlPage.java
 *<p>
 * Copyright (c) 2006-2010 T&I Net Communication CO.,LTD.  All rights reserved.
 * @author 周营昭
 * @since 1.0
 * @version 1.0
 */
public class HqlPage{

	/**
	 * 默认以getCount方式创建Page
	 */
	public static Page getPageInstance(Query query, ResultTransformer resultType,int start, int pageSize) {
		return getPageInstance(query,resultType, start, pageSize, BaseDaoImp.SCROLL_MODE);
	}

	/**
	 * 使用不同模式创建Page.
	 */
	public static Page getPageInstance(Query query, ResultTransformer resultType,int start, int pageSize, int mode) {

		if (mode == BaseDaoImp.SCROLL_MODE)
			return HqlPage.getPageInstanceByScroll(query,resultType,  start, pageSize);
		if (mode == BaseDaoImp.LIST_MODE)
			return HqlPage.getPageInstanceByList(query, resultType, start, pageSize);
		if (mode == BaseDaoImp.COUNT_MODE)
			throw new IllegalArgumentException(
					"Error Mode,you should use getPageInstance(Query query, int start, int pageSize, int mode,int totalCount)");
		return null;
	}
    public static Page getPageInstance(Query query, ResultTransformer resultType,int start, int pageSize, int mode,int totalCount){
    	return HqlPage.getPageInstanceByCount(query, resultType,start, pageSize,totalCount);
    }
	public static Page getPageInstanceByCount(Query query,ResultTransformer resultType, int start,
			int pageSize, int totalCount) {
		return getPageResult(query, resultType,totalCount, start, pageSize);
	}

	protected static Page getPageInstanceByScroll(Query query,  ResultTransformer resultType,int start,
			int pageSize) {
		ScrollableResults scrollableResults = query
				.scroll(ScrollMode.SCROLL_SENSITIVE);
		scrollableResults.last();
		int totalCount = scrollableResults.getRowNumber();
		return getPageResult(query, resultType, totalCount + 1, start, pageSize);
	}

	protected static Page getPageInstanceByList(Query query, ResultTransformer resultType, int start,
			int pageSize) {
		query.scroll(ScrollMode.FORWARD_ONLY);
		int totalCount = query.list().size();
		return getPageResult(query, resultType, totalCount, start, pageSize);
	}

	private static Page getPageResult(Query q, ResultTransformer resultType,int totalCount, int start,
			int pageSize) {
		if (totalCount < 1) {
			return new Page(0, 0, pageSize, null);
		}
		
		List list=null;
		if(resultType==null){
		    list = q.setFirstResult(start).setMaxResults(pageSize).list();
		}else{
			list = q.setFirstResult(start).setMaxResults(pageSize).setResultTransformer(resultType).list();
		}

		return new Page(start, totalCount, pageSize, list);
	}
}
