package com.crm.framework.action;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.dao.DeadlockLoserDataAccessException;

import com.crm.framework.common.beanfactory.CrmBeanFactory;
import com.crm.framework.common.config.vo.FrameworkConstanct;
import com.crm.framework.common.i18n.DefaultTextProvider;
import com.crm.framework.common.i18n.TextProvider;
import com.crm.framework.common.ui.PageLayout;
import com.crm.framework.common.util.LogDbUtil;
import com.crm.framework.common.util.LogUtil;
import com.crm.framework.common.util.StringUtil;
import com.crm.framework.common.web.session.CrmSession;
import com.crm.framework.common.web.session.CrmSessionCache;
import com.crm.framework.common.web.session.CrmSessionManager;
import com.crm.framework.crmbean.defaultbean.DefaultBeanCache;
import com.crm.framework.crmbean.defaultbean.DefaultBeanCache.BeanType;
import com.crm.framework.crmbean.interfaces.CrmTenant;
import com.crm.framework.crmbean.interfaces.CrmUser;
import com.opensymphony.xwork2.ActionContext;

/**
 * 
 * @author 王永明
 * @since Apr 2, 2010 8:47:48 PM
 */
public class ContextHolder{

	private static Logger log=Logger.getLogger(ContextHolder.class);
	
	private static ThreadLocal<CrmBaseAction> action=new ThreadLocal<CrmBaseAction>();
	
	private static ThreadLocal<String> actionMethod=new ThreadLocal<String>();
	
	
	public CrmUser getCurrentUser() {
		CrmSession crmSession=CrmSessionCache.getLocalSession();
		if(crmSession==null){
			String sessionId=this.getSessionId();
			if(sessionId==null){
				log.debug("当前请求没有sessionId,创建默认session");
				crmSession=new CrmSession(FrameworkConstanct.defaultSessionId.toString());
			}else{
				crmSession=CrmSessionCache.getRemoteSession(sessionId);
			}
		}	
		return (CrmUser) crmSession.getAttribute(CrmSession.USER);
	}
	
	
	/**获得临时用户*/
	public CrmUser getTempUser(){
		return (CrmUser) DefaultBeanCache.getBean(BeanType.tempUser);
	}

	public String getSessionId() {
		String sessionId=null;
		if(ActionContext.getContext()!=null){
			HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(
					ServletActionContext.HTTP_REQUEST);
			if(request!=null){
				sessionId=request.getSession().getId();	
			}
		}
		return sessionId;
	}
	
	public TextProvider getTextProvider() {
		return CrmBeanFactory.getBean(DefaultTextProvider.class);
	}


	public void setAction(CrmBaseAction baseAction) {
		action.set(baseAction);
	}


	public String getActionMethod() {
		return actionMethod.get();
	}


	public void setActionMethod(String method) {
		actionMethod.set(method);
	}
	
	public static ContextHolder getContext(){
		return CrmBeanFactory.getBean(ContextHolder.class);
	}
 
}
