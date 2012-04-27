package com.crm.framework.web.crmtag;

import com.crm.framework.common.util.StringUtil;

/**
*  将封装在page里的查询条件以<input type='hidden'.../>的方式显示到页面
 * @author 王永明
 * @since Apr 14, 2009 12:48:40 PM
 */
public class UnicodeTag extends SimpleTag {
	private String name;

	
	protected String getPrintStr() throws Exception {
		String text=this.getText(name);
		return StringUtil.getUnicode(text);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	
	

}
