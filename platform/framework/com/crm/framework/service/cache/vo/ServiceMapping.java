package com.crm.framework.service.cache.vo;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author 王永明
 * @since Apr 5, 2010 9:02:08 PM
 */
public class ServiceMapping {
	private Class clazz;
	private Set<ServiceMethodMapping>  mapingSet;
	private Set<Method> methodSet;
	
	public ServiceMapping(Class clazz){
		this.clazz=clazz;
		//映射
		mapingSet=new HashSet();
		methodSet=new HashSet();
		for(Method method:clazz.getDeclaredMethods()){
			ServiceMethodMapping smm=new ServiceMethodMapping(method);
			mapingSet.add(smm);
			methodSet.add(method);
		}
	}
	
	public boolean contain(Method method){
		return methodSet.contains(method);
	}
}
