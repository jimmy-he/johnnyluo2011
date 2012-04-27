package com.crm.framework.service.cache;

import com.crm.framework.common.beanfactory.CrmBeanFactory;
import com.crm.framework.common.cache.JavaCodeInfoCache;
import com.crm.framework.service.cache.vo.ServiceMapping;

/**
 * 
 * @author 王永明
 * @since Apr 5, 2010 8:54:27 PM
 */
public class ServiceMethodCache extends JavaCodeInfoCache {

	@Override
	protected Object getImpl(String id) {
		Class claz;
		try {
			 claz=Class.forName(id);
			 return new ServiceMapping(claz);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}		
	}
	
	public static ServiceMapping getMapping(String className){
		ServiceMethodCache cache=CrmBeanFactory.getBean(ServiceMethodCache.class);
		return  (ServiceMapping) cache.get(className);
	}

}
