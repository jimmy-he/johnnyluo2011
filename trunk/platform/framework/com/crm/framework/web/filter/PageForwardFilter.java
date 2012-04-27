package com.crm.framework.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.crm.framework.common.beanfactory.CrmBeanFactory;
import com.crm.framework.common.web.session.CrmSessionCache;
import com.crm.framework.crmbean.interfaces.CrmUser;
import com.crm.framework.web.WebLoader;
/**
 * 
 * @author 王永明
 * @since Apr 8, 2010 11:29:12 AM
 */
public class PageForwardFilter implements Filter {
	private static Logger log=Logger.getLogger(PageForwardFilter.class);
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void destroy() {

	}

	
	/**开始一个请求时将session从缓存放到本地,请求结束将本次的放回缓存*/
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException,
			ServletException {
		
		HttpServletRequest request=(HttpServletRequest)servletRequest;
		
		String url=request.getRequestURL().toString();
		if((url.endsWith(".jsp")&&!url.endsWith("text.jsp"))||url.endsWith(".action")){
			String sessionId=request.getSession().getId();
			CrmSessionCache.startThead(sessionId);
			CrmUser user=(CrmUser) CrmSessionCache.getLocalSession().getAttribute("user");
			log.debug(url+":"+user.getUserCode()+":"+sessionId);
			chain.doFilter(request, servletResponse);
			CrmSessionCache.stopThread(sessionId);
		}else{
			chain.doFilter(request, servletResponse);
		}	
		
	}
	
	

}
