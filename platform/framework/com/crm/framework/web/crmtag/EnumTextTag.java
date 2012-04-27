package com.crm.framework.web.crmtag;

import java.lang.reflect.Field;

import org.apache.log4j.Logger;

import com.crm.framework.common.beanfactory.CrmBeanFactory;
import com.crm.framework.common.enums.ClassType;
import com.crm.framework.common.util.BeanUtil;
import com.crm.framework.common.util.StringUtil;

/**
 * 
 * @author 王永明
 * @since Mar 24, 2010 3:12:19 PM
 */
public class EnumTextTag extends SimpleTag {
	private static Logger log=Logger.getLogger(EnumTextTag.class);
	/**对应枚举的类如果不是在com.crm.framework.common.enums下要写全名*/
	private String enumClass; 
	
	/**key所对应的enum属性值*/
	private String keyFiled;
	
	@Override
	protected String getPrintStr()   {
		try {
			
			value=this.getPageValue(pageContext, value)+"";
			
			Class clazz=null;
			
			if(enumClass.indexOf('.')==-1){
				//如果是类名称就直接用com.crm.framework.common.enums下的类,获得市对应action所在的enums包
				String pkg=ClassType.action.getOrtherPackage(this.getAction().getClass(), ClassType.enums);
				String clsName=pkg+"."+StringUtil.firstUp(enumClass);
				try{	
					clazz=Class.forName(clsName);
				} catch (ClassNotFoundException e) {
					clazz=Class.forName("com.crm.framework.common.enums"+"."+StringUtil.firstUp(enumClass));
				}
			}else{
				clazz=Class.forName(enumClass);
			}
			
			clazz=CrmBeanFactory.getImplClass(clazz);
			
			
			
			for(Field f:clazz.getFields()){
				String key=f.getName();
				if(StringUtil.isNotNull(keyFiled)){
					Object o=f.get(clazz);
					key=(String) BeanUtil.getValue(o,keyFiled);
				}
				
				if(key.equals(value)){
					return this.getText(StringUtil.firstLower(clazz.getSimpleName())+"."+f.getName());
				}				
				
			}			
			return "";
				
		} catch (Exception e) {
			log.warn("找不到枚举:"+e);
			throw new RuntimeException(e);
		}
		
	}


	public String getEnumClass() {
		return enumClass;
	}


	public void setEnumClass(String enumClass) {
		this.enumClass = enumClass;
	}

	public String getKeyFiled() {
		return keyFiled;
	}


	public void setKeyFiled(String keyFiled) {
		this.keyFiled = keyFiled;
	}
	

}
