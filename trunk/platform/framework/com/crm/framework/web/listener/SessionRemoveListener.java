package com.crm.framework.web.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

import com.crm.framework.common.web.session.CrmSession;
import com.crm.framework.common.web.session.CrmSessionCache;
import com.crm.framework.common.web.session.CrmSessionManager;

/**
 * session监听器,用户清除过期用户
 * @author 王永明
 * @since Oct 26, 2009 3:40:13 PM
 * */
public class SessionRemoveListener implements HttpSessionListener {
	private static Logger log=Logger.getLogger(SessionRemoveListener.class);
	
	//@edit 2010-7-15 如果在集群环境中,虽然是创建的session但内存中可能依然存在,所以如果存在就不创建新的
	public void sessionCreated(HttpSessionEvent se) {
		if(se.getSession()!=null){
			String id=se.getSession().getId();
			if(CrmSessionCache.getRemoteSession(id)==null){
				CrmSessionManager.create(id);
			}else{
				CrmSessionCache.startThead(id);
			}
			
		}
	}

	public void sessionDestroyed(HttpSessionEvent se) {
		if(se.getSession()==null){
			return;
		}
		log.info("销毁HttpSession:"+se.getSession().getId());
		CrmSessionManager.remove(se.getSession().getId());
			
	}

}
