package com.crm.framework.web.crmtag.freemarker;

import java.util.Map;

/**
 * 
 * @author 王永明
 * @since May 16, 2010 4:14:57 PM
 */
public class PageRecordTag extends  FreemarkerTag {
	
	public String getTemplate(){
		return"/framework/template/pageRecordInfo.ftl";
	}

	@Override
	public Map rootMap() {
		Map map=super.rootMap();
		map.put("changeCount", true);
		return map;
	}
}
