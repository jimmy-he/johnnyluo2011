package com.crm.framework.service.cache.vo;

import java.lang.reflect.Method;

/**
 * 
 * @author 王永明
 * @since Apr 5, 2010 8:57:35 PM
 */
public class ServiceMethodMapping {
	private  Method method;
	public ServiceMethodMapping(Method method){
		this.method=method;
	}
	
	public String getName(){
		return method.getName();
	}
	
}	
