package com.crm.framework.web.crmtag.freemarker;

import java.util.Map;

import com.crm.framework.common.config.PathConvertor;
import com.crm.framework.common.util.StringUtil;

/**
 * 
 * @author 王永明
 * @since Apr 12, 2010 2:25:19 PM
 */
public class RedirectTag extends FreemarkerTag {
	
	private String url;
	private String param;
	
	public String getTemplate(){
		return "/framework/template/redirect.ftl";
	}
	
	public Map rootMap() {
		StringBuilder sb=new StringBuilder();
		if(StringUtil.isNotNull(param)){
			String modelName=url.split("-")[0];
			String[] pas=param.split(",");
			for(String p:pas){
				if(p.indexOf(".")==-1){
					p=modelName+"."+p;
				}
				sb.append("&"+p+"="+"%{"+p+"}");
			}
		}
		
		sb.append("&redirect=true");
		Map map= super.rootMap();
		
		if(url.indexOf("?")==-1){
			url+=sb.toString().replaceFirst("&", "?");
		}else{
			url+=sb.toString();
		}	
		map.put("url",url);
		return map;
	}
	
	public static void main(String[] args) {
		RedirectTag tag=new RedirectTag();
		tag.setUrl("user-insert");
		tag.setParam("userId");
		System.out.println(tag.rootMap());
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}
}
