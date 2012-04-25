package com.tinet.ccic.common.cache.memcache.test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tinet.ccic.common.cache.memcache.service.CacheClientXmemcachedImpl;
import com.tinet.ccic.wm.commons.util.ContextUtil;

/**
 ***********************************************
 * @Title     TestSsoc.java					   
 * @Pageage   com.tinet.ccic.common.cache.memcache.test				   
 * @author    罗尧   Email:j2ee.xiao@gmail.com 
 * @since 1.0 创建时间 2012-4-24 下午03:40:31		   
 ***********************************************
 */
public class TestSsoc extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		CacheClientXmemcachedImpl cacheClientXmemcachedImpl=(CacheClientXmemcachedImpl) ContextUtil.getContext().getBean("cacheClientXmemcachedImpl");
		
		cacheClientXmemcachedImpl.add("name", "Luo Yao");
	    System.out.println(cacheClientXmemcachedImpl.get("name"));
		out.println(cacheClientXmemcachedImpl.get("name"));
		
		
		out.flush();
		out.close();
	}

}
