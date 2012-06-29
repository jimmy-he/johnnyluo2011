package com.newccic.framework.web.listener;

import javax.servlet.ServletContextEvent;

import org.apache.log4j.Logger;

import com.newccic.framework.common.util.StringUtil;

/**
 ************************************************
 *@Title 	newccic2						*
 *@Pageage 	com.newccic.framework.web.listener						*
 *@author   罗尧  Email：j2ee.xiao@gmail.com		*
 *@since	1.0  创建时间  2012-6-29  下午11:36:55		*
 ************************************************
 */
public class ContextListener extends ContextListenerBase{

	private static final Logger log = Logger.getLogger(ContextListener.class);
	public void contextInitialized(ServletContextEvent event) {
		log.info("系统开始启动.....");
		long start = System.currentTimeMillis();
		super.contextInitialized(event);
		
		
		
		
		long end=System.currentTimeMillis();
		long consume=end-start;
		long min=consume/1000/60;
		long sec=(consume/1000)%60;		
		if(min!=0){
			log.info(StringUtil.getLine()+"----------------系统已经正常启动,共耗时:"+consume+"毫秒,约合"+min+"分钟"+sec+"秒------------------");
		}else{
			log.info(StringUtil.getLine()+"----------------系统已经正常启动,共耗时:"+consume+"毫秒,约合"+sec+"秒-----------------------");
		}
	}
	
}
