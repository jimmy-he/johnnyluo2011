package com.crm.framework.web.crmtag.freemarker;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import com.crm.framework.common.config.PathConvertor;
import com.crm.framework.crmbean.interfaces.CrmUser;
import com.crm.framework.web.crmtag.SimpleTag;

import freemarker.template.TemplateException;

/**
 * 
 * @author 王永明
 * @since May 29, 2009 11:19:15 PM
 */
public class FootTag extends FreemarkerTag {
	public String getTemplate(){
		return PathConvertor.getSystemTemplate()+"/style2010/foot.ftl";
	}
	
	public Map rootMap() {
		return super.rootMap();
	}

}
