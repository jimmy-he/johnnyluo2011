package com.tinet.ccic.wm.commons.util;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AbstractRefreshableWebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
/**
 * Spring框架相关工具类
 *<p>
 * 文件名： SpringUtil.java
 *<p>
 * Copyright (c) 2006-2010 T&I Net Communication CO.,LTD.  All rights reserved.
 * @author 周营昭
 * @since 1.0
 * @version 1.0
 */
public class SpringUtil {
 /*
 * spring动态注册一个bean，可以在运行时
 * 注册bean，然后可以即时使用。
 * 调用的列子：
 *   systemService = (SystemService)WebApplicationContextUtils.getRequiredWebApplicationContext(
		    getServletContext()).getBean("systemService");
 */
	/**
	 * spring动态注册一个bean，可以在运行时注册bean，然后即时使用。
	 * 
	 * @param impCls 实现类。
	 * @param sc Servlet上下文。
	 * @param 服务名称 指定分隔符。
	 * @return void
	 * 调用的列子：
     *   systemService = (SystemService)WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext()).getBean("systemService");
	 */

	public static void registerService(Class impCls, ServletContext sc,
			String name) {
		WebApplicationContext aa = WebApplicationContextUtils
				.getRequiredWebApplicationContext(sc);
		RootBeanDefinition beanDefinition = new RootBeanDefinition();
		beanDefinition.setBeanClassName(impCls.getName());// 实现类的名称
		beanDefinition.setSingleton(true); // 默认为true, 需要增加可配置性
		// beanDefinition.setDependencyCheck(1);
		beanDefinition
				.setAutowireMode(AutowireCapableBeanFactory.AUTOWIRE_BY_NAME);
		((DefaultListableBeanFactory) ((AbstractRefreshableWebApplicationContext) aa)
				.getBeanFactory()).registerBeanDefinition(name, beanDefinition);

		// ((DefaultListableBeanFactory)((AbstractRefreshableWebApplicationContext)aa).getBeanFactory()).createBean(com.jeaw.system.security.dao.hibernate.SystemDaoImp.class,
		// AutowireCapableBeanFactory.AUTOWIRE_BY_NAME, false);
	}

}
