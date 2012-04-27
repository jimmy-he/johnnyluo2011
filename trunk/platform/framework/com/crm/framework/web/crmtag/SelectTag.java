package com.crm.framework.web.crmtag;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.crm.framework.common.util.BeanUtil;
import com.crm.framework.common.util.JavaTypeUtil;
import com.crm.framework.service.ServiceFactory;

/**
* 
 * @author 王永明
 * @since Apr 19, 2009 11:52:20 AM
 */
public class SelectTag extends SimpleTag {

	private String hql;
	private String key;
	private String value;
	private String firstName;
	private String selectedValue;
	private String list;
	private String template;
	private String size;
	private int begin=0;
	private int end=0;
	

	protected String getPrintStr() {

		if(firstName!=null){
			firstName=this.getText(firstName);
		}
		
		if(template==null||template.equals("")){
			template="{0}";
		}
		
		if(selectedValue!=null&&!selectedValue.equals("")){
			String temp=(String )this.getPageValue(pageContext, selectedValue);
			if(temp!=null){
				selectedValue=temp;
			}
		}
		StringBuffer sb=new StringBuffer();
		Map map=this.getDefMap();
		map.put("size", size);
		sb.append(this.createEl("select",map, false));
		if(firstName!=null&&!firstName.equals("")){
			this.addLine(sb, "<option value=''>"+firstName+"</option>");
		}
		
		List content=new ArrayList();
		
		
		//如果开始和结束都不等于0,则显示从开始到结束的select
		
			
		
		if(hql!=null&&!hql.equals("")){
			content=ServiceFactory.getCurentService().queryByHql(hql);
		}else if(begin!=0&&end!=0){
			List list=new ArrayList();
			for(int i=begin;i<=end;i++){
				list.add(i);
			}
			content=list;
		}else{
			content=(List) this.getPageValue(pageContext, list);
		}
		
		//防止添加重复的key
		HashMap addedMap=new HashMap();
		
		for(Object o:content){	
			String k="";
			if(JavaTypeUtil.isSimple(o)){
				k=o+"";
			}else{
				k=(String)BeanUtil.getValue(o,key);
			}
			
			if(addedMap.get(k)!=null){
				continue;
			}
			
			
			String v=this.getShow(o);
			
			
			if(k.equals(selectedValue)){
				this.addLine(sb, "<option value='"+k+"' selected='selected'>"+v+"</option>");
			}else{
				this.addLine(sb, "<option value='"+k+"'>"+v+"</option>");
			}
			addedMap.put(k, k);
		}
		this.addLine(sb, "</select>");
		return sb.toString();
	}

	private String getShow(Object o) {
		if(value==null){
			value="";
		}
		String[] vs = value.split(",");
		Object[] para = new String[vs.length];
		for (int i = 0; i < vs.length; i++) {
			if(JavaTypeUtil.isSimple(o)){
				para[i]=o+"";
			}else{
				para[i] = BeanUtil.getValue(o, vs[i]);
			}	
		}
		return MessageFormat.format(template, para);

	}

	public String getHql() {
		return hql;
	}

	public void setHql(String hql) {
		this.hql = hql;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSelectedValue() {
		return selectedValue;
	}

	public void setSelectedValue(String selectedValue) {
		this.selectedValue = selectedValue;
	}

	public String getList() {
		return list;
	}

	public void setList(String list) {
		this.list = list;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public int getBegin() {
		return begin;
	}

	public void setBegin(int begin) {
		this.begin = begin;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

}
