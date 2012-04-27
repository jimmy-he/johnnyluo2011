package com.crm.framework.web.crmtag;

import com.crm.framework.common.util.LogUtil;
import com.crm.framework.common.util.StringUtil;

/**
 * 
 * @author 王永明
 * @since Jun 8, 2010 1:56:33 PM
 */
public class SubStringTag extends SimpleTag {
	private String name;
	private int length;
	private boolean showTitile;
	private String tail="";

	@Override
	protected String getPrintStr() throws Exception {
		String value=(String) this.getPageValue(pageContext, name);
	
		if(StringUtil.isNull(value)){
			return "";
		}
		if(value.length()>length){
			return value.substring(0,length)+tail;
		}else{
			return value;
		}
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public int getLength() {
		return length;
	}


	public void setLength(int length) {
		this.length = length;
	}


	public boolean isShowTitile() {
		return showTitile;
	}


	public void setShowTitile(boolean showTitile) {
		this.showTitile = showTitile;
	}


	public String getTail() {
		return tail;
	}


	public void setTail(String tail) {
		this.tail = tail;
	}


}
