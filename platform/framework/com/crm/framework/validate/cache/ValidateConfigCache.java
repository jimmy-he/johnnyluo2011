package com.crm.framework.validate.cache;

import java.io.File;
import java.util.Map;

import org.apache.tools.ant.types.FlexInteger;

import com.crm.framework.common.beanfactory.CrmBeanFactory;
import com.crm.framework.common.cache.FileInfoCache;
import com.crm.framework.common.enums.ClassType;
import com.crm.framework.common.enums.FileType;
import com.crm.framework.common.util.FileUtil;
import com.crm.framework.common.util.PropertyUtil;

/**
 * 
 * @author 王永明
 * @since Apr 16, 2010 5:23:14 PM
 */
public class ValidateConfigCache extends FileInfoCache {

	@Override
	/**如果修改的文件名是以Validate.properties结尾的就认为是验证用的配置文件*/
	public boolean needClear(File file) {
		if(file.getName().endsWith(ClassType.validate.getPostfix()+"."+FileType.properties.getPostfix())){
			return true;
		}
		return false;
	}

	@Override
	protected Object getImpl(String id) {
		try {
			Class clazz=Class.forName(id);
			Class validate=ClassType.action.getOrtherType(clazz, ClassType.validate);
			String text=FileUtil.getText(validate, validate.getSimpleName()+"."+FileType.properties.getPostfix());
			return PropertyUtil.createProperties(text);		
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Map<String,String> getConfig(Class actionClass){
		ValidateConfigCache cache=CrmBeanFactory.getBean(ValidateConfigCache.class);
		return (Map<String, String>) cache.get(actionClass.getName());
	}
	
}
