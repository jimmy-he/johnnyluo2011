package com.crm.framework.web.crmtag;

/**
 * 对象为空时信息提示
 * */
public class NullToMessageTag extends SimpleTag {
	
	private String obj;//应显示信息
	private String message;//如果应显示信息为空后的提示
	protected String getPrintStr() throws Exception {
		if(obj!=null&&!obj.equals("")){
			return obj;
		}else{
			return message;
		}
	}
	public String getObj() {
		return obj;
	}
	public void setObj(String obj) {
		this.obj = obj;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

}
