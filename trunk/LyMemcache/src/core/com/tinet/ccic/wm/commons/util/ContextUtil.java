package com.tinet.ccic.wm.commons.util;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 获取spring的ApplicationContext。通过ApplicationContext可以获取在spring配置文件中配置的类。
 *<p>
 * 文件名： ContextUtil.java
 *<p>
 * Copyright (c) 2006-2010 T&I Net Communication CO.,LTD.  All rights reserved.
 * @author 周营昭
 * @since 1.0
 * @version 1.0
 */
public class ContextUtil extends ContextLoaderListener {
	
	private static ApplicationContext ctx = null;
	
	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);
		ServletContext context = event.getServletContext();
		ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
	}
	
	/**
	 * 获取spring的ApplicationContext。
	 * @return Spring的ApplicationContext.
	 */
	public static ApplicationContext getContext(){
		return ctx; 
	}
}
