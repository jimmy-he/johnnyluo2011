package com.crm.framework.web.init;

import org.apache.log4j.Logger;

public class DefaultInit implements SystemInit {
private static Logger log=Logger.getLogger(DefaultInit.class);
	public void init() {
		log.debug("...系统初始化完毕...");
	}

}
