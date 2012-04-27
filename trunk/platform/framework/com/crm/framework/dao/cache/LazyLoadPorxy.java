package com.crm.framework.dao.cache;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.apache.log4j.Logger;

import com.crm.framework.common.beanfactory.CrmBeanFactory;
import com.crm.framework.common.cache.JavaCodeInfoCache;
import com.crm.framework.model.MappingCache;
import com.crm.framework.common.util.StringUtil;

/**
 * 初始化一个字段,key的形式为:类名_数据源id
 * @author 王永明
 * @since Mar 20, 2010 10:35:38 AM
 */
public class LazyLoadPorxy{
//
//	/**获得实体的代理类*/
//	public static Object getPorxy(Class clazz,String dataSourceId){
//		Repository rp=RepositoryCache.getRepository(dataSourceId);
//		Enhancer en = new Enhancer();
//		en.setSuperclass(clazz);
//		en.setCallback(new InitField(clazz,rp));
//		return en.create();
//	}
//	
//	public static CrmList getListPorxy(){
//		Enhancer en = new Enhancer();
//		en.setSuperclass(CrmList.class);
//		en.setCallback(new InitList());
//		return (CrmList) en.create();
//	} 
//	
//	
//	/**初始化hibernate懒加载的字段*/
//	public static class InitField implements MethodInterceptor{
//		private Repository repository;
//		private Class clazz;
//		private Object initedObject;
//		private String pkName;
//		private String pk;
//		InitField(Class clazz ,Repository repository){
//			this.repository=repository;
//			this.clazz=clazz;
//			pkName=MappingCache.getMapping(clazz).getPkName();
//		}
//		public Object intercept(Object source, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
//			if(method.getName().equals("set"+StringUtil.firstUp(pkName))){
//				pk=args[0]+"";
//				return null;
//			}
//			if(initedObject==null){
//				initedObject=repository.get(clazz, pk);
//			}
//			return method.invoke(initedObject, args);		
//		}	
//	}
//	
//	/**初始化hibernate懒加载的List*/
//	public static class InitList implements MethodInterceptor{
//		private List list;
//		private String hql;
//		private String datasourceInfoId; 
//		public Object intercept(Object source, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {	
//			if(list!=null){
//				return method.invoke(list, args);
//			}
//			if(method.getName().equals("preInit")){
//				hql=args[0]+"";
//				datasourceInfoId=args[1]+"";	
//				return null;
//			}		
//			Repository rp=RepositoryCache.getRepository(datasourceInfoId);
//			list=rp.queryByHql(hql);
//			return method.invoke(list, args);
//		}	
//	}
//	

}
	
