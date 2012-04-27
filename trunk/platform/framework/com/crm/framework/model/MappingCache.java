package com.crm.framework.model;

import org.apache.log4j.Logger;

import com.crm.framework.common.beanfactory.CrmBeanFactory;
import com.crm.framework.common.cache.JavaCodeInfoCache;
import com.crm.framework.dao.annotation.CrmEntity;

/**
 * 
 * @author 王永明
 * @since Mar 9, 2010 5:38:52 PM
 */
public class MappingCache extends JavaCodeInfoCache {
	private static Logger log = Logger.getLogger(MappingCache.class);

	@Override
	protected Object getImpl(String id) {
		Class clazz;
		try {
			clazz = Class.forName(id);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
			
		}
		
		if(clazz.getAnnotation(CrmEntity.class)!=null){
			return new CrmMappingEntity(clazz);
		}
		return new JavaMappingEntity(clazz);
	}

	
	public static  MappingEntity getMapping(Class clazz){
		return (MappingEntity) CrmBeanFactory.getBean(MappingCache.class).get(clazz.getName());
	}
	
}

















