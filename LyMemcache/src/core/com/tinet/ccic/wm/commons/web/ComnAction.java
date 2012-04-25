package com.tinet.ccic.wm.commons.web;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.ByteConverter;
import org.apache.commons.beanutils.converters.DoubleConverter;
import org.apache.commons.beanutils.converters.FloatConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.beanutils.converters.LongConverter;

import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.util.ValueStack;
import com.tinet.ccic.wm.commons.util.DateConverter;

/**
 * 简单封装Struts DispatchAction的基类. 提供一些基本的简化函数,将不断增强.
 *<p>
 * 文件名： ComnAction.java
 *<p>
 * Copyright (c) 2006-2010 T&I Net Communication CO.,LTD.  All rights reserved.
 * @author 周营昭
 * @since 1.0
 * @version 1.0
 */
public class ComnAction extends ActionSupport {

	/** 日志管理器 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	static {
		registConverter();
	}

	/**
	 * 设置Struts 中数字<->字符串转换，字符串为空值时,数字默认为null，而不是0.
	 * 也可以在web.xml中设置struts的参数达到相同效果，在这里设置可以防止用户漏设web.xml.
	 */
	public static void registConverter() {
		ConvertUtils.register(new IntegerConverter(null), Integer.class);
		ConvertUtils.register(new FloatConverter(null), Float.class);
		ConvertUtils.register(new DoubleConverter(null), Double.class);
		ConvertUtils.register(new LongConverter(null), Long.class);
		ConvertUtils.register(new ByteConverter(null), Byte.class);
		ConvertUtils.register(new DateConverter("yyyy-MM-dd"), Date.class);
	}

	/**
	 * 以属性文件方式保存消息
	 */
	protected void saveMessage(String key, String... values) {
		addActionMessage(getText(key, values));
	}

	/**
	 * 直接将文本保存为信息
	 */
	protected void saveMessage(String value) {
		addActionMessage(value);
	}

	/**
	 * 以属性文件方式保存错误消息
	 */
	protected void saveError(String key, String... values) {
		addActionError(getText(key, values));
	}

	/**
	 * 直接将文本保存为错误消息
	 */
	protected void saveError(String value) {
		addActionError(value);
	}

	/**
	 * 直接保存错误消息
	 */
	protected void saveError(List errors) {
		setActionErrors(errors);
	}

	/**
	 * 直接保存错误消息
	 */
	protected void saveMessage(List messages) {
		setActionMessages(messages);
	}

	/**
	 * 直接输出纯字符串
	 */
	public void renderText(String text) {
		try {
			HttpServletResponse response = this.getResponse();
			response.setContentType("text/plain;charset=UTF-8");
			response.getWriter().write(text);
		} catch (IOException e) {
			logger.error("向Response输出{}时出错" + text);
		}
	}

	/**
	 * 直接输出纯HTML
	 */
	public void renderHtml(String text) {
		try {
			HttpServletResponse response = this.getResponse();
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().write(text);
		} catch (IOException e) {
			logger.error("向Response输出{}时出错" + text);
		}
	}

	/**
	 * 直接输出纯XML
	 */
	public void renderXML(String text) {
		try {
			HttpServletResponse response = this.getResponse();
			response.setContentType("text/xml;charset=UTF-8");
			response.getWriter().write(text);
		} catch (IOException e) {
			logger.error("向Response输出{}时出错" + text);
		}
	}

	protected HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	protected HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}
	
	protected ValueStack getValueStatck() {
	    return ActionContext.getContext().getValueStack(); 
	}

	protected HttpSession getSession() {
		return ServletActionContext.getRequest().getSession();
	}

	protected ServletContext getServletContext() {
		return ServletActionContext.getServletContext();
	}
	
	protected String getParameter(String param) {
		return getRequest().getParameter(param);
	}

	public Object getBean(String beanName) {
		Object bean = null;
		bean = WebApplicationContextUtils.getRequiredWebApplicationContext(
				getServletContext()).getBean(beanName);
		return bean;
	}
}