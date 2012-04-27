package com.crm.framework.web.crmtag;

import java.util.HashMap;
import java.util.Map;

import com.crm.base.permission.model.PermissionUser;
import com.crm.framework.action.ContextHolder;
import com.crm.framework.common.util.StringUtil;

public class ButtonTag extends SimpleTag {
	private String text;
	private String disabled;
	private String taskCode;
	private String display;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}	

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	protected String getPrintStr() {
		
		PermissionUser user=(PermissionUser) ContextHolder.getContext().getCurrentUser();
		if(taskCode!=null){
			if(!user.hasPermission(taskCode)){
				return "";
			}
			
		}
		
		String newText = this.getText(text);
		
		disabled = this.getDisabled();
		
		Map map = new HashMap();
		map.put("type", "button");
		map.put("class", "button49");
		map.putAll(this.getDefMap());
		map.put("value", newText);
		if("true".equals(disabled))
			map.put("disabled",disabled);
		
		String className = "";
		String overClassName = "";
		//查看value的长度
		int length = StringUtil.getBytesLength((String)map.get("value"));
		if(length <= 3){
			className = "button19";
			overClassName = "button19c";
		}else if(length <= 9){
			className = "button49";
			overClassName = "button49c";
		}else if(length <= 12){
			className = "button59";
			overClassName = "button59c";
		}else if(length <= 18){
			className = "button79";
			overClassName = "button79c";
		}else if(length <= 21){
			className = "button99";
			overClassName = "button99c";
		}else if(length > 21){
			String value = (String)map.get("value");
			String title = value;
			className = "button99";
			overClassName = "button99c";
			value = value.substring(0,21);
			map.put("title", title);
			map.put("value", value);
		}
		if("false".equals(display)){
			map.put("style", "display:none");
		}
		map.put("class", className);
		map.put("onmouseover", "this.className='"+overClassName+"'");
		map.put("onmouseout", "this.className='"+className+"'");		
		
		String returnStr = this.createEl("input",map);
		
		return returnStr;
	}

	public String getTaskCode() {
		return taskCode;
	}

	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}

	
	public String isDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

}
