package com.crm.framework.web.init;

import javax.servlet.ServletContextEvent;

/**
 * 初始化系统时要执行的类
 * @author 王永明
 * @since May 30, 2010 12:28:51 PM
 */
public interface ContextInit {
	public void run(ServletContextEvent event);
}
