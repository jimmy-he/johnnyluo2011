package com.crm.framework.validate.validateType;

import org.springframework.web.context.ContextLoader;

import com.crm.framework.action.ContextHolder;
import com.crm.framework.action.SimpleAction;
import com.crm.framework.common.exception.SystemException;
import com.crm.framework.common.i18n.TextProvider;
import com.crm.framework.common.util.BeanUtil;

/**
 * 
 * @author 王永明
 * @since Apr 16, 2010 5:47:04 PM
 */
public class NotNull implements ValidateType {

	public String validate(Object root, String key, String valieType) {
		TextProvider text=ContextHolder.getContext().getTextProvider();
		if( BeanUtil.getValue(root, key)!=null){
			return "success";
		}else{
			return text.getText(key)+text.getText("validate.notNull");
		}
	}
	
}
