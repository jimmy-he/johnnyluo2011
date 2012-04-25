package com.tinet.ccic.wm.commons.action;

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
 * �򵥷�װStruts DispatchAction�Ļ���. �ṩһЩ��ļ򻯺���,��������ǿ.
 *<p>
 * �ļ��� ComnAction.java
 *<p>
 * Copyright (c) 2006-2010 T&I Net Communication CO.,LTD.  All rights reserved.
 * @author ��Ӫ��
 * @since 1.0
 * @version 1.0
 */
public class ComnAction extends ActionSupport {

	/** ��־������ */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	static {
		registConverter();
	}

	/**
	 * ����Struts ������<->�ַ�ת�����ַ�Ϊ��ֵʱ,����Ĭ��Ϊnull������0.
	 * Ҳ������web.xml������struts�Ĳ���ﵽ��ͬЧ�����������ÿ��Է�ֹ�û�©��web.xml.
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
	 * �������ļ���ʽ������Ϣ
	 */
	protected void saveMessage(String key, String... values) {
		addActionMessage(getText(key, values));
	}

	/**
	 * ֱ�ӽ��ı�����Ϊ��Ϣ
	 */
	protected void saveMessage(String value) {
		addActionMessage(value);
	}

	/**
	 * �������ļ���ʽ���������Ϣ
	 */
	protected void saveError(String key, String... values) {
		addActionError(getText(key, values));
	}

	/**
	 * ֱ�ӽ��ı�����Ϊ������Ϣ
	 */
	protected void saveError(String value) {
		addActionError(value);
	}

	/**
	 * ֱ�ӱ��������Ϣ
	 */
	protected void saveError(List errors) {
		setActionErrors(errors);
	}

	/**
	 * ֱ�ӱ��������Ϣ
	 */
	protected void saveMessage(List messages) {
		setActionMessages(messages);
	}

	/**
	 * ֱ��������ַ�
	 */
	public void renderText(String text) {
		try {
			HttpServletResponse response = this.getResponse();
			response.setContentType("text/plain;charset=UTF-8");
			response.getWriter().write(text);
		} catch (IOException e) {
			logger.error("��Response���{}ʱ����" + text);
		}
	}

	/**
	 * ֱ�������HTML
	 */
	public void renderHtml(String text) {
		try {
			HttpServletResponse response = this.getResponse();
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().write(text);
		} catch (IOException e) {
			logger.error("��Response���{}ʱ����" + text);
		}
	}

	/**
	 * ֱ�������XML
	 */
	public void renderXML(String text) {
		try {
			HttpServletResponse response = this.getResponse();
			response.setContentType("text/xml;charset=UTF-8");
			response.getWriter().write(text);
		} catch (IOException e) {
			logger.error("��Response���{}ʱ����" + text);
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