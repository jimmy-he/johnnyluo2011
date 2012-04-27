package com.crm.framework.validate.validateType;

import com.crm.framework.action.SimpleAction;

/**
 * 
 * @author 王永明
 * @since Apr 16, 2010 5:48:02 PM
 */
public interface ValidateType {
	public final static String NOT_NULL="notNull";
	public final static String NUMBER="number";
	public final static String DATE="date";
	public final static String DATE_TIME="dateTime";
	
	
	public String validate(Object obj,String key,String valieType);
}
