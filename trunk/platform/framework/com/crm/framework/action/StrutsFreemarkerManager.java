package com.crm.framework.action;

/**
 * 
 * @author 王永明
 * @since Jan 19, 2010 4:48:50 PM
 */

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.apache.struts2.views.freemarker.FreemarkerManager;

import com.crm.framework.common.Global;
import com.crm.framework.common.beanfactory.CrmBeanFactory;
import com.crm.framework.common.config.vo.FreemarkConfig;
import com.crm.framework.common.ui.TemplateUtil;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

public class StrutsFreemarkerManager extends FreemarkerManager {
	private static Logger log = Logger.getLogger(StrutsFreemarkerManager.class);

	public Configuration createConfiguration(ServletContext servletContext) {
		log.debug("开始加载freemark...");
		Configuration configuration=null;
		try {
			configuration = super.createConfiguration(servletContext);
		} catch (TemplateException e) {
			throw new RuntimeException();
		}

		FreemarkConfig config = CrmBeanFactory.getBean(FreemarkConfig.class);
		configuration.setClassicCompatible("true".equals(config.getClassicCompatible()));//
		configuration.setDefaultEncoding(config.getDefaultEncoding());
		configuration.setNumberFormat(config.getNumberFormat());
		TemplateUtil.setFreemarkConfig(configuration);
		return configuration;
	}
}
