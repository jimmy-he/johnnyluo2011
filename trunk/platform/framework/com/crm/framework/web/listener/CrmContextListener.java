package com.crm.framework.web.listener;

import javax.servlet.ServletContextEvent;

import org.apache.log4j.Logger;
import org.springframework.web.context.ContextLoaderListener;

import com.crm.framework.common.Global;
import com.crm.framework.common.beanfactory.CrmBeanFactory;
import com.crm.framework.web.WebLoader;

/**
 * 系统启动监听器,初始化spring工厂,freemarker工厂,系统常量
 * 
 * @author 王永明
 * @since Apr 12, 2009 3:54:25 PM
 */
public class CrmContextListener extends ContextLoaderListener {

	private static final Logger log = Logger.getLogger(ContextLoaderListener.class);

	public void contextInitialized(ServletContextEvent event) {
		WebLoader loader=CrmBeanFactory.getBean(WebLoader.class);
		loader.setServletContextEvent(event);
		loader.setServerInfo();
		loader.startup();
		super.contextInitialized(loader.getEvent());	
		loader.saveSpring();
		//加载action
		if(Global.getConfig().getLoadAction().equals("true")){
			loader.addActions();
		}	
	}

}
