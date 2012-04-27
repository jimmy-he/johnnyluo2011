package com.crm.framework.web.crmtag;


/**
 * @description 将封装在page里的查询条件以<input type='hidden'.../>的方式显示到页面
 * @author 王永明
 * @date Apr 14, 2009 12:48:40 PM
 */
public class URLEncoder extends SimpleTag {
	private String name;
	private String encoder;
	
	protected String getPrintStr() throws Exception {
		return java.net.URLEncoder.encode(name,encoder);
	}

	public String getEncoder() {
		return encoder;
	}

	public void setEncoder(String encoder) {
		this.encoder = encoder;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	
	

}
