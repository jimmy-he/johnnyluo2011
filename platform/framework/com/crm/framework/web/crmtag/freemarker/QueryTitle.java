package com.crm.framework.web.crmtag.freemarker;

import java.util.Map;

import com.crm.framework.action.ContextHolder;
import com.crm.framework.action.SimpleAction;
import com.crm.framework.common.util.StringUtil;
import com.crm.framework.dao.selector.PageRecord;

public class QueryTitle extends FreemarkerTag {
	
	private String fieldName;
	
	private String width;
	
	private String orderField;

	
	public String getTemplate(){
		return "/framework/template/queryTitle.ftl";
	}
	
	public Map rootMap() {
		Map map= super.rootMap();
		String text=ContextHolder.getContext().getTextProvider().getText(fieldName);
		map.put("text", text);
		if(!"false".equals(orderField)){
			map.put("image",this.getImage());
		}
		return map;
	}
	
	
	public String getImage(){
		SimpleAction action=(SimpleAction)this.getAction();
		PageRecord pageRecord=action.getPageRecord();
		
		String name=StringUtil.getFirstAfter(fieldName, ".");
		if(orderField!=null){
			name=orderField;
		}
		
		
		StringBuffer sb = new StringBuffer();
		String img = "ascdesc.gif";
	
		if(name.equals(pageRecord.getOrderField())){
			if("desc".equals(pageRecord.getOrderType())){
				img = "desc.gif";
			}else{
				img = "asc.gif";
			}
		}
		addLine(sb, "<img name='"+name+"' id='"+name+"Img' src='"+this.webRoot()+"/framework/images/sinosoft/"+img+"' class='menuImg'>");
		return sb.toString();
	}
	
	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}


	public String getOrderField() {
		return orderField;
	}

	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}
}
