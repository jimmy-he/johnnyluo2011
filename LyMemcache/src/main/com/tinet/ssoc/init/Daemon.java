package com.tinet.ssoc.init;

import javax.servlet.ServletContextEvent;

import org.apache.log4j.Logger;

import com.tinet.ccic.wm.commons.util.ContextUtil;


/**
* 鍒濆鍖栧悇妯″潡鍚姩鍏ㄥ眬鍙傛暟
*<p>
* 鏂囦欢鍚嶏細 Daemon.java
*<p>
* Copyright (c) 2006-2010 T&I Net Communication CO.,LTD.  All rights reserved.
* @author 瀹夐潤娉�
* @since 1.0
* @version 1.0
*/
public class Daemon implements javax.servlet.ServletContextListener {
	static Logger logger = Logger.getLogger(Daemon.class.getName());

	//	@Autowired
	//	@Qualifier("moduleManagerService")
	//	private ModuleManagerService moduleManagerService;
	public Daemon() {
	}

	/**
	 * 绯荤粺鍒濆鍖�
	 */
	public void contextInitialized(ServletContextEvent sce) {
		/*
		SystemSettingService systemSettingService = (SystemSettingService) ContextUtil.getContext().getBean("systemSettingService");
		logger.debug("Init global variables:" + systemSettingService.initGlobal());
		
		CcicService ccicService = (CcicService) ContextUtil.getContext().getBean("ccicService");
		logger.debug("cache ccic Server");
		ccicService.cache();
		
		ReceiveSmsManager receiveSmsManager = new ReceiveSmsManager();
		receiveSmsManager.start();
		System.out.println("SMS Receive Server Start .......    OK");
		*/
		return;
	}

	/**
	 * 鍏抽棴
	 */
	public void contextDestroyed(ServletContextEvent sce) {
		
		logger.info("&&&&&&&&&&&&&&&&    destroied    &&&&&&&&&&&&&&&&&& ");
	}
}
