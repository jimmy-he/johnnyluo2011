package com.crm.framework.web.init;

import javax.servlet.ServletContextEvent;

import com.crm.base.permission.login.logininfo.enums.LoginOpType;
import com.crm.base.permission.login.logininfo.event.LoginEvent;
import com.crm.framework.common.beanfactory.CrmBeanFactory;

/**
 * 更新登录信息表
 * @author 王永明
 * @since May 30, 2010 12:29:41 PM
 */
public class UpdateLoginInfo implements ContextInit{

	public void run(ServletContextEvent event) {
		LoginEvent logEvent=new LoginEvent(null,LoginOpType.restart);
		CrmBeanFactory.publishEvent(logEvent);
	}

}
