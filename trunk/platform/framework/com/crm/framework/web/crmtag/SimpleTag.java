package com.crm.framework.web.crmtag;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts2.views.jsp.TagUtils;

import com.crm.framework.action.CrmBaseAction;
import com.crm.framework.action.GeneralAction;
import com.crm.framework.common.Global;
import com.crm.framework.common.beanfactory.CrmBeanFactory;
import com.crm.framework.common.i18n.DefaultTextProvider;
import com.crm.framework.common.util.BeanUtil;
import com.opensymphony.xwork2.util.ValueStack;

public abstract class SimpleTag extends TagSupport {

	protected String onclick;
	protected String onchange;
	protected String onfocus;
	protected String onblur;
	protected String name;
	protected String validate;
	protected String type;
	protected String value;
	protected String id;
	protected String className;
	protected Boolean readOnly;
	protected String style;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	protected String createEl(String tagNaem, Map<String, String> map, boolean end) {
		StringBuffer sb = new StringBuffer("<" + tagNaem + " ");
		for (String str : map.keySet()) {
			sb.append(str + "=\"" + map.get(str) + "\" ");
		}
		if (end) {
			sb.append("/>");
		} else {
			sb.append(">");
		}
		return sb.toString();
	}


	protected Map getDefMap() {
		Map map = new HashMap();
		if (notNull(onclick)) {
			map.put("onclick", onclick);
		}
		if (notNull(onchange)) {
			map.put("onchange", onchange);
		}
		if (notNull(onfocus)) {
			map.put("onfocus", onfocus);
		}
		if (notNull(onblur)) {
			map.put("onblur", onblur);
		}
		if (notNull(name)) {
			map.put("name", name);
		}
		if (notNull(validate)) {
			map.put("validate", validate);
		}
		if (notNull(type)) {
			map.put("type", type);
		}
		if (notNull(value)) {
			map.put("value", value);
		}
		if (notNull(id)) {
			map.put("id", id);
		}
		if (notNull(className)) {
			map.put("class", className);
		}
		if (readOnly!=null) {
			map.put("readOnly", readOnly+"");
		}
		if (notNull(style)) {
			map.put("style",style);
		}


		return map;
	}

	protected String createEl(String name, Map<String, String> map) {
		return this.createEl(name, map, true);
	}

	public String getOnclick() {
		return onclick;
	}

	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	public void addLine(StringBuffer sb, String str) {
		sb.append(str);
		sb.append(System.getProperty("line.separator"));
	}

	public int doStartTag() {
		JspWriter out = pageContext.getOut();
		try {
			out.print(getPrintStr());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EVAL_BODY_INCLUDE;
	}

	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

	protected abstract String getPrintStr() throws Exception;

	/**
	 * 仿EL表达式,获得页面的值类似{a.b.c},标签专用
	 */
	protected Object getPageValue(PageContext pageContext, String expression) {
		try {
			if (expression == null || expression.equals("")) {
				return null;
			}
			if(!expression.trim().startsWith("%{")){
				return expression;
			}
			
			expression=expression.trim().substring(2,expression.length()-1);		
			
			Object reValue = null;
			int point = expression.indexOf(".");
			if (point == -1) {
				reValue = getGvalue(pageContext, expression);
			} else {
				String rootName = expression.substring(0, point);
				String newExp = expression.substring(point + 1);
				Object root = getGvalue(pageContext, rootName);
				reValue = BeanUtil.getValue(root, newExp);
			}
			return reValue;
		} catch (Exception ex) {
			return null;
		}
	}

	protected String getPageText(PageContext pageContext, String expression) {
		Object obj = this.getPageValue(pageContext, expression);
		return obj == null ? "" : obj.toString();
	}

	/** 获请求里的某个值顺序:page-->request-->session-->application */
	private Object getGvalue(PageContext page, String name) {
		Object value = page.getAttribute(name);
		if (value != null) {
			return value;
		}
		value = page.getRequest().getAttribute(name);
		if (value != null) {
			return value;
		}
		value = page.getSession().getAttribute(name);
		if (value != null) {
			return value;
		}
		value = page.getServletContext().getAttribute(name);
		return value;
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

	protected boolean notNull(String str) {
		return str != null && !str.equals("");
	}

	protected String toEmpty(String str) {
		return str == null ? "" : str;
	}

	public String getOnchange() {
		return onchange;
	}

	public void setOnchange(String onchange) {
		this.onchange = onchange;
	}

	public String getOnfocus() {
		return onfocus;
	}

	public void setOnfocus(String onfocus) {
		this.onfocus = onfocus;
	}

	public String getOnblur() {
		return onblur;
	}

	public void setOnblur(String onblur) {
		this.onblur = onblur;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValidate() {
		return validate;
	}

	public void setValidate(String validate) {
		this.validate = validate;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(Boolean readOnly) {
		this.readOnly = readOnly;
	}

	public String getText(String key){
		return CrmBeanFactory.getBean(DefaultTextProvider.class).getText(key);
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}
}
