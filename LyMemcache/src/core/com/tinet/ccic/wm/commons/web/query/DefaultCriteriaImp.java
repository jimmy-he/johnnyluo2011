package com.tinet.ccic.wm.commons.web.query;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.tinet.ccic.wm.commons.BaseStandardEntity;
import com.tinet.ccic.wm.commons.Constants;
import com.tinet.ccic.wm.commons.CCICException;
import com.tinet.ccic.wm.commons.util.CriterionUtil;
/**
 * 默认Criteria接口实现类。
 *<p>
 * 文件名： DefaultCriteriaImp.java
 *<p>
 * Copyright (c) 2006-2010 T&I Net Communication CO.,LTD.  All rights reserved.
 * @author 周营昭
 * @since 1.0
 * @version 1.0
 */
public class DefaultCriteriaImp implements DefaultCriteria {
	protected static String logger = Constants.COMMONS;// LogHandler.getLogger(getClass());
	protected static CriterionUtil criterionUtil;
	
	public CriterionUtil getCriterionUtil() {
		if(criterionUtil==null){
			criterionUtil = new CriterionUtil();
		}
		return criterionUtil;
	}
	public void setCriterionUtil(CriterionUtil criterionUtil) {
		this.criterionUtil = criterionUtil;
	}
	public String createCriterion(Criterion criterion,Map filterMap) {
		try{
	    	Set keys = filterMap.keySet();
	    	Conjunction conjunction = Restrictions.conjunction();
			for (Object key : keys) {
				if (!Constants.USER_IN_SESSION.equals(key)) {
					Object value = (Object) filterMap.get(key);
					if (value instanceof String
							&& StringUtils.isNotBlank((String) value)
							|| value != null && !(value instanceof String)
							&& !(value instanceof BaseStandardEntity)
							&& !(value instanceof Collection)
							&& isCriteria(value)) {
						if (value instanceof String
								&& StringUtils.isNotBlank((String) value)) {
							appandCriteria(conjunction, key, value,false);
						} else if (value instanceof Long
								|| value instanceof Integer
								|| value instanceof Float
								|| value instanceof Double) {
							getCriterionUtil().generateCriteria(conjunction, (String) key,
									value, 1,false);
						} else {
							conjunction.add(Restrictions.eq((String) key,
									value));
						}
					} else if (value != null
							&& value instanceof BaseStandardEntity) {

					}
				}
			}
			if(criterion!=null){
				conjunction.add(criterion);
			}
			criterion = conjunction;
			}catch(Exception e){
				throw new CCICException("SYS00000", e);
			}
			if(criterion!=null){
				return criterion.toString();
			}else{
				return null;
			}
		
	}
	 public void createCriteria(Criteria criteria, Map filterMap) {
	    try{
	    	Set keys = filterMap.keySet();
			for (Object key : keys) {
				if (!Constants.USER_IN_SESSION.equals(key)) {
					Object value = (Object) filterMap.get(key);
					if (value instanceof String && StringUtils.isNotBlank((String) value)
							|| value != null && !(value instanceof String)
							&& !(value instanceof BaseStandardEntity)
							&& !(value instanceof Collection)
							&& isCriteria(value)) {
						if (value instanceof String && StringUtils.isNotBlank((String) value)) {
							appandCriteria(criteria, key, value,true);
						} else if (value instanceof Long
								|| value instanceof Integer
								|| value instanceof Float
								|| value instanceof Double) {
							getCriterionUtil().generateCriteria(criteria, (String) key,
									value, 1,true);
						} else {
							criteria.add(Restrictions.eq((String) key,
									value));
						}
					} else if (value != null
							&& value instanceof BaseStandardEntity) {
						Criteria temp = criteria
								.createCriteria((String) key);
						Map map = null;
						// 将一个对象转换为Map
						map = PropertyUtils.describe(value);
						map.remove("class");
						createCriteria(temp, map);
					
					}
				}
			}
			}catch(Exception e){
				throw new CCICException("SYS00000", e);
			}
	    }
		private void appandCriteria(Object criteria, Object key, Object value,boolean isCriteria) {
			if (((String) key).endsWith("_STRING")) {
				// 字符型
				key = ((String) key).substring(0, ((String) key).lastIndexOf("_STRING"));
				getCriterionUtil().generateCriteria(criteria, (String) key, ((String) value).trim(), 0,isCriteria);
			} else if (((String) key).endsWith("_LONG")
					|| ((String) key).endsWith("_INTEGER")
					|| ((String) key).endsWith("_DOUBLE")
					|| ((String) key).endsWith("_FLOAT")) {
			
				getCriterionUtil().generateCriteria(criteria, (String) key,((String) value).trim(), 1,isCriteria);
			} else if (((String) key).endsWith("_DATE8")
					|| ((String) key).endsWith("_DATE12")) {
				// 日期型
				if (((String) key).endsWith("_DATE8")) {
					key = ((String) key).substring(0, ((String) key).lastIndexOf("_DATE8"));
					getCriterionUtil().generateCriteria(criteria,
							(String) key, ((String) value)
									.trim(), 8,isCriteria);
				} else if (((String) key)
						.endsWith("_DATE12")) {
					key = ((String) key)
							.substring(0, ((String) key)
									.lastIndexOf("_DATE12"));
					getCriterionUtil().generateCriteria(criteria,
							(String) key, ((String) value)
									.trim(), 12,isCriteria);
				}
			} else if (((String) key)
					.endsWith("_BIGSTRING")) {
				// 大字符型
				key = ((String) key).substring(0,
						((String) key)
								.lastIndexOf("_BIGSTRING"));
				getCriterionUtil().generateCriteria(criteria, (String) key,
						((String) value).trim(), 10,isCriteria);
			} else {
				getCriterionUtil().generateCriteria(criteria, (String) key,
						((String) value).trim(), 0,isCriteria);
			}
		}
		
		/**
		 * 扩展不需要加入查询的特殊对象
		 * 
		 * @param value
		 * @return
		 */
		protected boolean isCriteria(Object value) {
			return true;
		}
}
