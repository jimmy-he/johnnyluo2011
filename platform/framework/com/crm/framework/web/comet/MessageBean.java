package com.crm.framework.web.comet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.crm.framework.common.beanfactory.CrmBeanFactory;
import com.crm.framework.common.util.LogUtil;

/**
 * 长连接的信息传递类
 * @author 王永明
 * @since 2010-12-2 下午08:28:35
 */
public class MessageBean {
	private Map<String,List<String>> map=new ConcurrentHashMap();
	
	public static MessageBean getBean(){
		return CrmBeanFactory.getBean(MessageBean.class);
	}
	
	public void addMessage(String userId,String message){
		List<String> msg=map.get(userId);
		if(msg==null){
			msg=new ArrayList();
		}
		msg.add(message);
		map.put(userId,msg);
	}
	public List<String> popMessage(String userId){
		return map.remove(userId);
	}
}
