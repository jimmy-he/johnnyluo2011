package com.crm.framework.web.crmtag;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.crm.framework.common.config.vo.FrameworkConstanct;
import com.crm.framework.web.converter.DateConverter;

/**
*  将封装在page里的查询条件以<input type='hidden'.../>的方式显示到页面
 * @author 王永明
 * @since Apr 14, 2009 12:48:40 PM
 */
public class DateTimeTag extends SimpleTag {

	private String defValue;
	@Override
	protected String getPrintStr() throws Exception {
		DateConverter dateConverter=new DateConverter();
		String dateFormat=FrameworkConstanct.dateFormat.getValue();
		String timeFormat=FrameworkConstanct.datetimeFormat.getValue();
		Date date=(Date)this.getPageValue(pageContext, defValue);
		String str="";
		if(date!=null){
			str=dateConverter.convertToString(null, date);
			if(str.length()==dateFormat.length()){
				str+=timeFormat.replace(dateFormat,"").replaceAll("\\w", "0");
			}
		}
		
		
		Map<String,String> map=new HashMap();
		String className = "WdateTime";
		map.put("class", "WdateTime");
		map.put("onfocus", "WdatePicker({dateFmt:'"+timeFormat+"'})");
		map.putAll(this.getDefMap());
		map.put("value",str );
		String validate=map.get("validate");
		if(validate!=null){
			if(validate.indexOf("notNull")!=-1){
				className += "Must";
			}
			validate+=",dateTime";
			map.put("validate", validate);
		}
		map.put("class",className);
		return  this.createEl("input", map);
	}
	public String getDefValue() {
		return defValue;
	}
	public void setDefValue(String defValue) {
		this.defValue = defValue;
	}



}
