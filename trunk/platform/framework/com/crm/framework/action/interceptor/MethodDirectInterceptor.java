package com.crm.framework.action.interceptor;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.crm.framework.action.CrmBaseAction;
import com.crm.framework.action.ContextHolder;
import com.crm.framework.common.Global;
import com.crm.framework.common.beanfactory.CrmBeanFactory;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 如果action的方法存在则执行相应方法,否则直接转发
 * 
 * @author 王永明
 * @since Jan 20, 2010 5:53:40 PM
 */
@Component
public class MethodDirectInterceptor extends AbstractInterceptor {
	private static Logger log = Logger.getLogger(MethodDirectInterceptor.class);

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		CrmBaseAction baseAction = (CrmBaseAction) invocation.getAction();
		// 设置action当前正在执行的方法
		String methodName = invocation.getProxy().getMethod();
		ContextHolder  context=CrmBeanFactory.getBean(ContextHolder.class);
		context.setAction(baseAction);
		context.setActionMethod(methodName);
		
		for (Method method : baseAction.getClass().getMethods()) {
			if (method.getName().equals(methodName)) {
				return invocation.invoke();
			}
		}
		log.debug("未找到方法:" + baseAction.getClass().getName().replace("\\$.*", "") + "." + methodName + "直接转发.");
		return Global.getConfig().getViewPostfix();
	}
	
}
