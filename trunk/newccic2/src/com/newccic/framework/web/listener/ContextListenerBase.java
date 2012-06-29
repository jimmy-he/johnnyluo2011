package com.newccic.framework.web.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

/** 系统启动监听器,初始化spring工厂,freemarker工厂,系统常量
 ************************************************
 *@Title 	newccic2						
 *@Pageage 	com.newccic.framework.web.listener					
 *@author   罗尧  Email：j2ee.xiao@gmail.com		
 *@since	1.0  创建时间  2012-6-29  下午11:40:42		
 ************************************************
 */
public class ContextListenerBase extends ContextLoaderListener{

	private static final Logger log = Logger.getLogger(ContextListenerBase.class);
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
