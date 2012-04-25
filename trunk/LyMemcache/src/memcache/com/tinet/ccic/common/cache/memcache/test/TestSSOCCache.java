package com.tinet.ccic.common.cache.memcache.test;

import javax.swing.text.AbstractDocument.Content;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.tinet.ccic.common.cache.memcache.service.CacheClientXmemcachedImpl;
import com.tinet.ccic.wm.commons.util.ContextUtil;

/**
 ***********************************************
 * @Title     TestCMCache.java					   
 * @Pageage   com.tinet.ccic.common.cache.memcache.test				   
 * @author    罗尧   Email:j2ee.xiao@gmail.com 
 * @since 1.0 创建时间 2012-4-23 下午05:35:51		   
 ***********************************************
 */
public class TestSSOCCache {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//下面获取service实例化对象,这个得根据我们框架配置执行
		//CacheClientXmemcachedImpl cacheClientXmemcachedImpl = (CacheClientXmemcachedImpl)Global.getSpringBean("cacheClientXmemcachedImpl");
		ApplicationContext ac = new FileSystemXmlApplicationContext("resources/spring/applicationContext-service.xml");
		//CacheClientXmemcachedImpl cacheClientXmemcachedImpl = (CacheClientXmemcachedImpl) ac.getBean("cacheClientXmemcachedImpl");
		CacheClientXmemcachedImpl cacheClientXmemcachedImpl=(CacheClientXmemcachedImpl) ContextUtil.getContext().getBean("cacheClientXmemcachedImpl");
		cacheClientXmemcachedImpl.add("name", "Luo Yao");
	    System.out.println(cacheClientXmemcachedImpl.get("name"));
	}

}
