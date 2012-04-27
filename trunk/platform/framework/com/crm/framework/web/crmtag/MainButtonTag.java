package com.crm.framework.web.crmtag;

import java.util.HashMap;
import java.util.Map;

import com.crm.base.permission.model.PermissionUser;
import com.crm.framework.action.ContextHolder;
import com.crm.framework.common.util.StringUtil;

/**
 * 
 * @author 王永明
 * @since Jun 30, 2009 11:39:05 AM
 * */
public class MainButtonTag extends SimpleTag{
	private String text;
	private String disabled;
	private String taskCode;

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
		PermissionUser user=(PermissionUser)ContextHolder.getContext().getCurrentUser();
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
		if(length <= 1){
			className = "button19";
			overClassName = "button19c";
		}else if(length <= 5){
			className = "button49";
			overClassName = "button49c";
		}else if(length <= 8){
			className = "button59";
			overClassName = "button59c";
		}else if(length <= 11){
			className = "button79";
			overClassName = "button79c";
		}else if(length <= 14){
			className = "button99";
			overClassName = "button99c";
		}else if(length > 16){
			String value = (String)map.get("value");
			String title = value;
			className = "button99";
			overClassName = "button99c";
			value = value.substring(0,8);
			map.put("title", title);
			map.put("value", value);
		}
		map.put("name", "mainButton");
		//TODO 目前按钮没有根据文字自适应大小
		map.put("class", "mainButton");
		String returnStr = this.createEl("input",map);
		//returnStr="<img src='"+this.webRoot()+"/framework/images/blue/buttonGap.gif' class='refImg'>"+returnStr;
		return returnStr;
	}

	public String getTaskCode() {
		return taskCode;
	}

	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}
}
