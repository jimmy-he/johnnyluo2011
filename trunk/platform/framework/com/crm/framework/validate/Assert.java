package com.crm.framework.validate;

import com.crm.framework.action.SimpleAction;
import com.crm.framework.common.beanfactory.CrmBeanFactory;
import com.crm.framework.common.exception.SystemException;
import com.crm.framework.common.util.StringUtil;
import com.crm.framework.validate.validateType.ValidateType;

/**
 * 自定义异常生成工具类
 * 
 * @author 王永明
 * @since Apr 16, 2009 1:14:35 PM
 */
public class Assert {

	/**根据验证明获得验证类型*/
	public static  ValidateType getValidate(String typeName){
		int index=typeName.indexOf("(");
		if(index!=-1){
			typeName=typeName.substring(0,index);
		}
		String className=ValidateType.class.getPackage().getName()+"."+StringUtil.firstUp(typeName);
		return CrmBeanFactory.getBeanByFullName(className);
	}
	

	/**验证某个属性是值是否正确*/
	public static void validate(SimpleAction action,String key,String valiType){
		ValidateType type=getValidate(valiType);
		String msg=type.validate(action, key, valiType);
		if(msg.equals("success")){
			return;
		}
		throw new SystemException(msg);
	}
	
	/**验证字段不能为空*/
	public static void notNull(SimpleAction action,String key){
		validate(action,key,ValidateType.NOT_NULL);
	}
	
	
}
