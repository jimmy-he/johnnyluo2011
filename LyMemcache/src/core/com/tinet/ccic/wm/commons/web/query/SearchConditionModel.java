package com.tinet.ccic.wm.commons.web.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.tinet.ccic.wm.commons.Constants;
import com.tinet.ccic.wm.commons.util.ArrayUtil;
import com.tinet.ccic.wm.commons.util.TypeConverterUtil;

/**
 * 用于封装页面查询条件
 *<p>
 * 文件名： SearchConditionModel.java
 *<p>
 * Copyright (c) 2006-2010 T&I Net Communication CO.,LTD.  All rights reserved.
 * @author 周营昭
 * @since 1.0
 * @version 1.0
 */
public class SearchConditionModel {
	
	private int pageSize = 0;
	private int start = 0;
	
	private Map filterMap = new HashMap(5);
	private Map filterMapWithRealType = null;
	private Map ruleMap = new HashMap(5);
	private Map orderMap = new LinkedHashMap(2);
	
	private boolean isPaged = false;
	
	private Class rootClass = null;
	public SearchConditionModel(Class rootClass) {
		this.rootClass = rootClass;
	}
	public Class getRootClass() {
		return rootClass;
	}
	public void setRootClass(Class rootClass) {
		this.rootClass = rootClass;
	}

	public Map getFilterMapWithRealType() {
		if (this.filterMapWithRealType == null) {
			this.constructRealTypeMap();
		}
		return filterMapWithRealType;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
		this.isPaged = true;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	
	
	public Map getFilterMap() {
		if (filterMap == null) {
			filterMap = new HashMap(1);
		}
		
		return filterMap;
	}
	public void setFilterMap(Map filterMap) {
		this.filterMap = filterMap;
	}
	
	public Map getRuleMap() {
		if (ruleMap == null) {
			ruleMap = new HashMap(1);
		}
		return ruleMap;
	}
	public void setRuleMap(Map ruleMap) {
		this.ruleMap = ruleMap;
	}
	
	public Map getOrderMap() {
		return orderMap;
	}
	public void setOrderMap(Map orderMap) {
		this.orderMap = orderMap;
	}
	
	public boolean isPaged() {
		return isPaged;
	}
	public void setPaged(boolean isPaged) {
		this.isPaged = isPaged;
	}
	
	public void reset(Class rootClass) {
		this.rootClass = rootClass;
		this.filterMap.clear();
		this.orderMap.clear();
		this.ruleMap.clear();
		this.filterMapWithRealType = null;
	}
	
	public void addCondition(String property, String[] values) {
		String[] arrProp = StringUtils.split(property, RICH_RULE_SEPERATOR);
		String prop = arrProp[0];
		String rule = null;
		if (arrProp.length > 1) {
			rule = arrProp[1];
		} else {
			rule = Constants.RICH_RULE_EQUAL;
		}
		
		if (values.length > 1) {
			this.addCondition(prop, values, Constants.RICH_RULE_IN);
		} else {
			if (Constants.RICH_RULE_IN.equals(rule)) {
				values = StringUtils.split(values[0], ',');
				
				if (values.length == 1) {
					this.addCondition(prop, values[0], Constants.RICH_RULE_EQUAL);
				} else {
					this.addCondition(prop, values, Constants.RICH_RULE_IN);
				}
			} else {
				this.addCondition(prop, values[0], rule);
			}
		}
	}
	
	public void addCondition(String property, Object value, String rule) {
		List vlist = null;
		List rlist = null;
		
		if (this.filterMap.get(property) == null) {
			vlist = new ArrayList(1);
			filterMap.put(property, vlist);
			rlist = new ArrayList(1);
			ruleMap.put(property, rlist);
		} else {
			vlist = (List)this.filterMap.get(property);
			rlist = (List)this.ruleMap.get(property);
		}
		
		vlist.add(value);
		rlist.add(rule);
	}
	
	public void addOrder(String property, String dir) {
		this.orderMap.put(property, dir);
	}
	
	public void constructRealTypeMap() {
		if (filterMapWithRealType == null) {
			filterMapWithRealType = new HashMap(5);
		} else {
			filterMapWithRealType.clear();
		}
		
		for (Iterator it = filterMap.entrySet().iterator(); it.hasNext(); ) {
			Map.Entry entry = (Map.Entry) it.next();
			List realTypeValues = TypeConverterUtil.convertValue(rootClass, (String)entry.getKey(), (List)entry.getValue());
			if (realTypeValues != null) {
				filterMapWithRealType.put(entry.getKey(), realTypeValues);
			}
		}
	}
	
	public void fromRequest(HttpServletRequest request) {
		Map pMap = request.getParameterMap();
		for (Object obj : pMap.entrySet() ) {
			Map.Entry entry = (Map.Entry)obj;
			handleSearchParameter((String)entry.getKey(), (String[])entry.getValue());
		}
	}

	private void handleSearchParameter(String parameterName, String[] arrParameterValue) {
		if (ArrayUtil.isEmptyStringArray(arrParameterValue)) {
			return;
		}

		if (Constants.PARAMETER_RECORDSTART.equals(parameterName)) {
			this.setStart(Integer.valueOf(arrParameterValue[0]));
		} else if (Constants.PARAMETER_PAGESIZE.equals(parameterName)) {
			this.setPageSize(Integer.valueOf(arrParameterValue[0]));
		} else if (Constants.PARAMETER_ORDER_INDEX.equals(parameterName)) {
			this.handleOrderParameter(arrParameterValue[0]);
		} else if (Constants.FIELDS_ARRAY.equals(parameterName)
				|| Constants.EXT_AVOID_CACHE_PARAM.equals(parameterName)) {
			// donothing
		} else {
			this.addCondition(parameterName, arrParameterValue);
		}
	}
	
	private void handleOrderParameter(String strOrder) {
		String[] orderItems = StringUtils.split(strOrder, ';');
		
		for (String orderItem : orderItems) {
			String [] order = StringUtils.split(orderItem, ',');
			this.addOrder(order[0], order[1]);
		}
	}
	
	static final char RICH_RULE_SEPERATOR = '$';

	public String toString() {
		StringBuilder strb = new StringBuilder();
		for (Iterator it = this.filterMap.entrySet().iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry)it.next();
			
			String prop = (String)entry.getKey();
			strb.append(";").append(prop)
				.append(" ").append(this.ruleMap.get(prop))
				.append(" ").append(entry.getValue());
		}
		
		if (strb.length() > 0) {
			strb.replace(0, 1, "[").append("],");
		}
		
		strb.append("start:").append(this.start).append(",").append("pageSize").append(this.pageSize);
		
		return strb.toString();
	}
	
	
}
