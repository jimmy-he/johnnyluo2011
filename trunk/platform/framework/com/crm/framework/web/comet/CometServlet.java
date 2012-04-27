package com.crm.framework.web.comet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.crm.framework.common.util.JsonUtil;
import com.crm.framework.common.util.StringUtil;

/**
 * 
 * @author 王永明
 * @since 2010-12-1 下午07:12:16
 */
public class CometServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String actionType=request.getParameter("actionType");
		if("get".equals(actionType)){
			this.get(request, response);
		}
		if("put".equals(actionType)){
			this.put(request, response);
		}
	}
	
	private void put(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String userId=request.getParameter("userId");
		String message=request.getParameter("message");
		MessageBean.getBean().addMessage(userId, message);	
	}
	
	private void get(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String userId=request.getParameter("userId");
		while(true){	
			List<String> message=MessageBean.getBean().popMessage(userId);
			if(message!=null&&message.size()>0){
				String str=JsonUtil.toString(message);
				response.getWriter().write(str);
				return;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				response.getWriter().write("failure");
			}
		}
	}

	
	
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		doGet(request, response);
	}
	
	
}
