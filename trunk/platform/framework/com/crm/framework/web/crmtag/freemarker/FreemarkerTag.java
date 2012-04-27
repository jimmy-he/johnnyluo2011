package com.crm.framework.web.crmtag.freemarker;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspWriter;

import com.crm.framework.action.ContextHolder;
import com.crm.framework.common.beanfactory.CrmBeanFactory;
import com.crm.framework.common.config.PathConvertor;
import com.crm.framework.common.ui.TemplateUtil;
import com.crm.framework.common.vo.SystemInfo;
import com.crm.framework.crmbean.interfaces.CrmUser;
import com.crm.framework.web.crmtag.SimpleTag;

import freemarker.template.Template;

/**
* 
 * @author 王永明
 * @since May 28, 2009 11:07:50 PM
 */
public class FreemarkerTag extends SimpleTag {
	private String template;
	
	public Map rootMap() {
		CrmUser user=CrmBeanFactory.getBean(ContextHolder.class).getCurrentUser();
		Map map = new HashMap();
		map.putAll(super.getDefMap());
		map.put("action", this.getAction());
		map.put("user", user);
		map.put("webRoot", super.webRoot());
		map.put("system", SystemInfo.newInstance());
		return map;
	}
	
	public int doStartTag() {
		JspWriter out = pageContext.getOut();
		try {
			this.template().process(this.rootMap(), out);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EVAL_BODY_INCLUDE;
	}

	public Template template() throws IOException {
		return TemplateUtil.getFreemarkConfig().getTemplate(this.getTemplate());
	}
	
	public String getTemplate(){
		return template;
	}

	protected String getPrintStr() {
		return null;
	}

	public void setTemplate(String template) {
		this.template = template;
	}
	
	
	
	public static void main(String[] args) throws IOException {
		TemplateUtil.getFreemarkConfig().getTemplate(PathConvertor.getSystemTemplate()+"/head.ftl");
	}
	
	
	
	
	
	
	
	
	
	
	
}
