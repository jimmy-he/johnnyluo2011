package com.crm.framework.validate.validateType;

import com.crm.framework.action.ContextHolder;
import com.crm.framework.action.SimpleAction;
import com.crm.framework.common.enums.RegexString;
import com.crm.framework.common.i18n.TextProvider;
import com.crm.framework.common.util.BeanUtil;
import com.crm.framework.common.util.StringUtil;

/**
 * 
 * @author 王永明
 * @since Apr 16, 2010 8:14:37 PM
 */
public class LessThan implements ValidateType {

	public String validate(Object root, String key, String valieType) {
		TextProvider text=ContextHolder.getContext().getTextProvider();
		Object value=BeanUtil.getValue(root, key);
		if(value==null){
			return "success";
		}
		String noStr=StringUtil.pickUpFirst(valieType, RegexString.number);
		
		if((value+"").length()<Integer.parseInt(noStr)){
			return "success";
		}
		
		return text.getText(key)+text.getText("validate.lessThan")+noStr;
	}
	

}
