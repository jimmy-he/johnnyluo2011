package com.crm.framework.web.crmtag.freemarker;

import java.util.Map;

/**
 * 
 * @author 王永明
 * @since May 16, 2010 4:14:57 PM
 */
public class NavigateTag extends FreemarkBodyTag {
	
	/**是否能修改每页显示条数*/
	private boolean changeCount; 
	
	public String getTemplate(){
		return"/framework/template/navigate.ftl";
	}
	

	public boolean isChangeCount() {
		return changeCount;
	}

	public void setChangeCount(boolean changeCount) {
		this.changeCount = changeCount;
	}


	@Override
	public String split() {
		return "buttonInfo";
	}


	@Override
	public Map getMap() {
		Map map=super.rootMap();
		map.put("changeCount", changeCount);
		return map;
	}



}
