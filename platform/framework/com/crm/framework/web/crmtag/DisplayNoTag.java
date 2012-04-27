package com.crm.framework.web.crmtag;

/**
 * 
 * @author 王永明
 * @since Jul 8, 2009 10:12:41 AM
 * */
public class DisplayNoTag extends SimpleTag {
	private String name;
	private Integer start;
	private Integer max;
	private Integer selectedValue;

	@Override
	protected String getPrintStr() throws Exception {
		if(start==null){
			start=1;
		}
		if(selectedValue==null){
			selectedValue=start;
		}
		StringBuffer sb=new StringBuffer("<select name='"+name+"'>");
		for(int i=start;i<=max;i++){
			if(i==selectedValue){
				sb.append("<option value='"+i+"' selected>"+i+"</option>");
			}else{
				sb.append("<option value='"+i+"'>"+i+"</option>");
			}
		}
		return sb.toString();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}


	public Integer getSelectedValue() {
		return selectedValue;
	}

	public void setSelectedValue(Integer selectedValue) {
		this.selectedValue = selectedValue;
	}

	public Integer getMax() {
		return max;
	}

	public void setMax(Integer max) {
		this.max = max;
	}

}
