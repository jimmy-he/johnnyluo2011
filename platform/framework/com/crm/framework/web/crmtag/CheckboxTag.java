package com.crm.framework.web.crmtag;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.crm.framework.common.beanfactory.CrmBeanFactory;
import com.crm.framework.common.util.BeanUtil;
import com.crm.framework.service.ServiceFactory;

/**
 * 显示多个checkbox或者radio
 * @author 王永明
 * @since Jul 10, 2009 10:39:11 PM
 * */
public class CheckboxTag  extends SimpleTag{
	private String name;
	private String value;
	private String text;
	private String hql;
	private String selectedValue;
	private String type;
	private String orderBy;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getHql() {
		return hql;
	}
	
	public void setHql(String hql) {
		this.hql = hql;
	}
	@Override
	protected String getPrintStr() throws Exception {
		if(type == null){
			type = "checkBox";
		}
		Set<String> selectedSet = new HashSet();
		Object selected = this.getPageValue(pageContext, selectedValue);
		if(selected != null){
			if(selected instanceof String){
				if(selected.toString().indexOf(",")!=-1){
					for(String st:selected.toString().split(","))
					selectedSet.add(st);
				}else{
					selectedSet.add(selected.toString());
				}
				
			}else{		
				selectedSet.addAll((Collection<String>) selected);
			}
			
		}
		
		List list =ServiceFactory.getCurentService().queryByHql(hql);
		if(orderBy!=null){
			Class clazz=Class.forName(orderBy);
			Comparator comparator=(Comparator) CrmBeanFactory.getBean(clazz);
			Collections.sort(list,comparator);
		}
		
		
		
		StringBuffer sb = new StringBuffer();
		Boolean readOnly=false;
		if(this.isReadOnly()!=null&&this.readOnly){
			readOnly=true;
		}
		
		for(Object o:list){
			String checkValue = (String) BeanUtil.getValue(o, value);
			String checkText = (String)BeanUtil.getValue(o, text);
			String readOnlyStr="readOnly";
			if(selectedSet.contains(checkValue)){
				sb.append("<span style='display:inline-block;'><input name='"+name+"'"+(readOnly?readOnlyStr:"")+" onclick=\""+onclick+"\" type='"+type+"' checked value='"+checkValue+"'>"+checkText+"</span>");
			}else{			
				sb.append("<span style='display:inline-block;'><input name='"+name+"' "+(readOnly?readOnlyStr:"")+"   onclick=\""+onclick+"\" type='"+type+"' value='"+checkValue+"'>"+checkText+"</span>");
			}
						
		}
		return sb.toString();
	}
	public String getSelectedValue() {
		return selectedValue;
	}
	public void setSelectedValue(String selectedValue) {
		this.selectedValue = selectedValue;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
}
