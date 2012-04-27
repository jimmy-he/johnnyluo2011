package com.crm.framework.web.crmtag.freemarker;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.struts2.views.jsp.TagUtils;

import com.crm.framework.action.ContextHolder;
import com.crm.framework.action.CrmBaseAction;
import com.crm.framework.action.GeneralAction;
import com.crm.framework.common.Global;
import com.crm.framework.common.beanfactory.CrmBeanFactory;
import com.crm.framework.common.ui.TemplateUtil;
import com.crm.framework.common.vo.SystemInfo;
import com.crm.framework.crmbean.interfaces.CrmUser;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * 包含标签体的模板标签
 * @author 王永明
 * @since Apr 11, 2009 8:52:06 PM
 */
abstract public class FreemarkBodyTag  extends BodyTagSupport {
	
	private String endText;
	private String SPLIT="%%%%";
	
	public int doStartTag() throws JspException {
		JspWriter out = pageContext.getOut();
		try {
			out.print(this.getStart());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_BODY_INCLUDE;
	}

	private  String getStart() {
		Map map=this.getMap();
		map.put(this.split(), SPLIT);
		String[] texts=TemplateUtil.getTemplateText(map, this.getTemplate()).split(SPLIT);
		endText=texts[1];
		return texts[0];
	}

	// 遇到结束标签执行
	public int doEndTag() throws JspException {
		JspWriter out = pageContext.getOut();
		try {
			out.print(endText);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}
	
	
	/**获得当前界面对应的action*/
	public CrmBaseAction getAction() {
		if(pageContext==null){
			return new GeneralAction();
		}
		ValueStack valueStack = TagUtils.getStack(pageContext);	
		for (Iterator iterator = valueStack.getRoot().iterator(); iterator.hasNext();) {
			Object o = iterator.next();
			if (o instanceof CrmBaseAction) {
				return (CrmBaseAction) o;
			}
		}
		return null;
	}
	
	protected String webRoot() {
		if(pageContext==null){
			return Global.getConfig().getWebRoot();
		}
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		return request.getContextPath();
	}
	
	protected Map rootMap() {
		CrmUser user=CrmBeanFactory.getBean(ContextHolder.class).getCurrentUser();
		Map map = new HashMap();
		map.put("action", this.getAction());
		map.put("user", user);
		map.put("webRoot", this.webRoot());
		map.put("system", SystemInfo.newInstance());
		return map;
	}
	
	abstract String getTemplate();
	

	abstract Map getMap();
	
	abstract public String split();
}
