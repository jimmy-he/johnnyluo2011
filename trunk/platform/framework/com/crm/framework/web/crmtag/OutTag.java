package com.crm.framework.web.crmtag;

/**
 * 截取前几位后显示
 * */
public class OutTag extends SimpleTag {
	private String value; //信息
	private String length;//截取长度
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	protected String getPrintStr() throws Exception {
		if(name!=null){
			value=(String) this.getPageValue(pageContext, name);
		}
		if(value!=null&&!value.equals("")){
			if(value.length()>5){
				return value.substring(0, Integer.parseInt(length));
			}else{
				return value;
			}
		}else{
			return "";
		}
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
}
