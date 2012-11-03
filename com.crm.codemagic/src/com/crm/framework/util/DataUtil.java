package com.crm.framework.util;

import java.util.Date;

/**
 * @description
 * @author 王永明
 * @date Apr 16, 2009 5:49:50 PM
 * */
public class DataUtil {
	public static boolean isDate(Date date){
		long time=date.getTime();
		return (time+28800000)%(24*60*60*1000)==0;
	}
	
	
}
