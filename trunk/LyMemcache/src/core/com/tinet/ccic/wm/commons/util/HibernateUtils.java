package com.tinet.ccic.wm.commons.util;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projection;
import org.hibernate.hql.QueryTranslator;
import org.hibernate.hql.ast.QueryTranslatorImpl;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.impl.CriteriaImpl.OrderEntry;
import org.hibernate.type.Type;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.util.Assert;
/**
 * Hibernate框架相关工具类。
 *<p>
 * 文件名： HibernateUtils.java
 *<p>
 * Copyright (c) 2006-2010 T&I Net Communication CO.,LTD.  All rights reserved.
 * @author 周营昭
 * @since 1.0
 * @version 1.0
 */
public abstract class HibernateUtils {

	private static Log logger = LogFactory.getLog(HibernateUtils.class);

	public static String removeSelect(String sql) {
		Assert.notNull(sql, "sql must be specified ");
		int beginPos = sql.toLowerCase().indexOf("from");
		Assert.isTrue(beginPos != -1, " the sql : " + sql
										+ " must has a keyword 'from'");
		return sql.substring(beginPos);
	}

	public static String removeOrders(String sql) {
		Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", 
									Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(sql);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}

	public static int getTotalCount(HibernateTemplate hibernateTemplate,
			String queryString, boolean isNamedQuery, Object[] paramNames,
			Object[] paramValues, boolean needTransfer) throws IllegalArgumentException,
			DataAccessException {
		if (StringUtils.isBlank(queryString)) {
			throw new IllegalArgumentException(" queryString can't be blank ");
		}
		
		String countQueryString = null;
		if (needTransfer) {
			countQueryString = " select count(*) " + removeSelect(removeOrders(queryString));
		} else {
			countQueryString = queryString;
		}
		
		QueryTranslator queryTranslator = new QueryTranslatorImpl(queryString, queryString,
		           Collections.EMPTY_MAP, (org.hibernate.engine.SessionFactoryImplementor)hibernateTemplate.getSessionFactory());  
		
		List countList;
		if (isNamedQuery) {// paramNames在此表示参数名称，paramValues表示参数值
			countList = hibernateTemplate.findByNamedParam(countQueryString,
															ArrayUtil.toString(paramNames), 
															paramValues);
		} else {
			if (paramValues != null && paramValues.length > 0) {// paramNames在此表示参数值
				countList = hibernateTemplate.find(countQueryString, paramNames);
			} else {
				countList = hibernateTemplate.find(countQueryString);
			}
		}
		
		if (countList == null || countList.size() == 0) {
			return 0;
		} else if (countList.size() == 1){
			return Integer.valueOf(countList.get(0).toString());
		} else {
			return countList.size();
		}
	}
	
	
	public static int getTotalCountSql(Session session, String queryString,
			String[] paramNames, Object[] paramValues, boolean needTransfer) {
		if (StringUtils.isBlank(queryString)) {
			throw new IllegalArgumentException(" queryString can't be blank ");
		}
		
		
		String countQueryString = queryString.toUpperCase();;
		
		if (needTransfer) {
			if(countQueryString.indexOf(" distinct ")>0||countQueryString.indexOf(" group by ")>0){
				countQueryString = " select count(*) from(" + queryString + ")";
			}else{
				countQueryString = " select count(*) " + removeSelect(removeOrders(queryString));
			}
		} 
			
		List countList;
		if (paramValues != null && paramValues.length > 0) {
			Query query = session.createSQLQuery(countQueryString)
					.setParameters(paramNames, toType(paramValues));
			countList = query.list();
		} else {
			Query query = session.createSQLQuery(countQueryString);
			countList = query.list();
		}
		
		if (countList == null || countList.size() == 0) {
			return 0;
		} else if (countList.size() == 1){
			return Integer.valueOf(countList.get(0).toString());
		} else {
			return countList.size();
		}
	}
	
	public static List getPageResult(Criteria criteria, int offset,
			int maxPageItems) throws HibernateException {
		criteria.setFirstResult(offset);
		criteria.setMaxResults(maxPageItems);
		return criteria.list();
	}

	public static Projection getProjection(Criteria criteria) {
		CriteriaImpl impl = (CriteriaImpl) criteria;
		return impl.getProjection();
	}

	public static OrderEntry[] getOrders(Criteria criteria) {
		CriteriaImpl impl = (CriteriaImpl) criteria;
		Field field = getOrderEntriesField(criteria);
		try {
			return (OrderEntry[]) ((List) field.get(impl)).toArray(new OrderEntry[0]);
		} catch (Exception e) {
			throw new InternalError(" Runtime Exception impossibility can't throw ");
		}
	}

	public static Criteria removeOrders(Criteria criteria) {
		CriteriaImpl impl = (CriteriaImpl) criteria;
		try {
			Field field = getOrderEntriesField(criteria);
			field.set(impl, new ArrayList());
			return impl;
		} catch (Exception e) {
			throw new InternalError(" Runtime Exception impossibility can't throw ");
		}
	}

	public static Criteria addOrders(Criteria criteria,
			OrderEntry[] orderEntries) {
		CriteriaImpl impl = (CriteriaImpl) criteria;
		try {
			Field field = getOrderEntriesField(criteria);
			for (int i = 0; i < orderEntries.length; i++) {
				List innerOrderEntries = (List) field.get(criteria);
				innerOrderEntries.add(orderEntries[i]);
			}
			return impl;
		} catch (Exception e) {
			throw new InternalError(" Runtime Exception impossibility can't throw ");
		}
	}
	
	public static Field getOrderEntriesField(Criteria criteria) {
		Assert.notNull(criteria, " criteria is requried. ");
		try {
			Field field = CriteriaImpl.class.getDeclaredField("orderEntries");
			field.setAccessible(true);
			return field;
		} catch (Exception e) {
			throw new InternalError();
		}
	}

	public static Type[] toType(Object[] obj) {
		Type[] type = null;
		if (obj != null) {
			type = new Type[obj.length];
			for (int i = 0; i < obj.length; i++) {
				type[i] = (Type) obj[i];
			}
		}
		return type;
	}

}