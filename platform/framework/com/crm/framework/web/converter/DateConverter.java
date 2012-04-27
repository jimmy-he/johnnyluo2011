package com.crm.framework.web.converter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.util.StrutsTypeConverter;

import com.crm.framework.common.config.vo.FrameworkConstanct;
import com.crm.framework.common.util.DateTime;
import com.crm.framework.common.util.DateUtil;

/**
 * 日期转换器
 * 
 * @author 王永明
 * @since Apr 14, 2009 12:43:44 AM
 */
public class DateConverter extends StrutsTypeConverter {
	private static Logger log = Logger.getLogger(DateConverter.class);

	/**
	 * 将字符串转换为日期
	 * 
	 * @param context 当前的ActionContext
	 */
	public Object convertFromString(Map context, String[] values, Class toClass) {
		return DateUtil.toDate(values[0]);
	}
	
	
	
	@Override
	/** 将日期类型转换为字符串 */
	public String convertToString(Map context, Object obj) {
		String datetimeFormat = FrameworkConstanct.datetimeFormat.getValue();
		String dateFormat =FrameworkConstanct.dateFormat.getValue();
		
		Date date = (Date) obj;
		long time = date.getTime();
		Date day=new DateTime(date,DateTime.YEAR_TO_DAY);
		
		
		SimpleDateFormat sdf = null;
		
		if (day.getTime()==time) {
			sdf = new SimpleDateFormat(dateFormat);
		} else {
			sdf = new SimpleDateFormat(datetimeFormat);
		}
		return sdf.format(date);
	}
	
}
