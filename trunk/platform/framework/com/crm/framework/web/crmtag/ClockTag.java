package com.crm.framework.web.crmtag;



public class ClockTag extends SimpleTag {
	
	private String name;
	private String defaultValue;//默认值
	private String format;
	private String style;
	
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	protected String getPrintStr() throws Exception{
		StringBuffer sb=new StringBuffer();		
		int maxLength=format.length();
		if(format.indexOf("%Y")!=-1){
			maxLength+=2;
		}
		if(style==null){		
			if (defaultValue != null && !defaultValue.equals("")){
				String value=this.getPageValue(pageContext, defaultValue).toString();
				sb.append("<input class='common' name='"+name+"' id='"+name+"' value='"+value+"' style='width:"+(maxLength*7+5)+"px' maxLength='"+maxLength+"'>");
			}else{
				sb.append("<input class='common' name='"+name+"' id='"+name+"' style='width:"+(maxLength*7+5)+"px' maxLength='"+maxLength+"'>");
			}
			
		}else{
			if (defaultValue != null && !defaultValue.equals("")){
				String value=this.getPageValue(pageContext, defaultValue).toString();
				sb.append("<input class='common' name='"+name+"' id='"+name+"' value='"+value+"' style='"+style+"' maxLength='"+maxLength+"'>");
			}else{
				sb.append("<input class='common' name='"+name+"' id='"+name+"' style='"+style+"' maxLength='"+maxLength+"'>");
			}
			
		}
		
		sb.append("<img style='cursor: hand' src='images/bgcalendar.gif' onClick=\"return showCalendar('"+name+"', '"+format+"', '24', true);\">");
		return sb.toString();
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
}
