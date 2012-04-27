package com.crm.framework.web.crmtag;


/**将key转换成value标签*/
public class TextTag extends SimpleTag {
	
	private String type;
	private String key;
	
	protected String getPrintStr() throws Exception {
		if(key!=null&&!key.equals("")){
			Object v=this.getPageValue(pageContext, key);
			if(v!=null){
				key=v+"";
			}else{
				key=null;
			}
		}
		if(key==null){
			return "";
		}
		return this.getText(type+"."+key);
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}

}
