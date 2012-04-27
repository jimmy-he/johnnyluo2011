package com.crm.framework.web.crmtag;

import java.util.Date;

import com.crm.framework.web.converter.DateConverter;

/**
*  将封装在page里的查询条件以<input type='hidden'.../>的方式显示到页面
 * @author 王永明
 * @since Apr 14, 2009 12:48:40 PM
 */
public class ShowDateTag extends SimpleTag {
	private String name;

	@Override
	protected String getPrintStr() throws Exception {
		DateConverter dateConverter=new DateConverter();
		Date date =(Date)this.getPageValue(pageContext, name);
		if(date!=null){
			return dateConverter.convertToString(null, date);
		}
		return "";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	
	

}
