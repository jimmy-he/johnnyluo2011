package com.crm.framework.action.interceptor;

import org.apache.log4j.Logger;

import com.crm.framework.common.util.StringUtil;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 
 * @author 王永明
 * @since Feb 3, 2010 6:47:18 PM
 */
public class LoggerInterceptor extends AbstractInterceptor {
	private static Logger log = Logger.getLogger(LoggerInterceptor.class);

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		final String className = invocation.getAction().getClass().getName();
		final String methodName = invocation.getProxy().getMethod();
		final long start = System.currentTimeMillis();

		try {
			log.debug(StringUtil.getLine() + StringUtil.getLine() + StringUtil.getLine() + StringUtil.getLine()
					+ "=================================" + className + "." + methodName
					+ "================================================================================");
			String result = invocation.invoke();
			return result;
		} catch (Exception ex) {
			throw ex;
		} finally {
			long time = System.currentTimeMillis() - start;
			log.debug("=========================================" + time
					+ "ms=================================================");
		}
	}

}
