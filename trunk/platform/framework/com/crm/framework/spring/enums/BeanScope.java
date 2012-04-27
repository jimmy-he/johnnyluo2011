package com.crm.framework.spring.enums;

/**
 * spring容器内bean的存活周期
 * 
 * @author 王永明
 * @since Jan 18, 2010 1:56:28 PM
 */
public enum BeanScope {
	/** 每次请求返回同一个实例 */
	singleton,
	/** 每次请求创建一个新的实例 */
	prototype
}
