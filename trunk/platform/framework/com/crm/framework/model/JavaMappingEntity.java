package com.crm.framework.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.log4j.Logger;

import com.crm.framework.common.util.StringUtil;
import com.crm.framework.dao.annotation.CrmCache;

/**
 * 通过java的标签标注的model类
 * 
 * @author 王永明
 * @since Mar 11, 2010 11:45:44 AM
 */
public class JavaMappingEntity extends AbstracMapptionEntity{
	private static Logger log=Logger.getLogger(JavaMappingEntity.class);
	private String pk;
	private Class clazz;
	private Map<String,MappingField> mappingFields;
	private CrmCache crmCache;
	private Table table;


	public JavaMappingEntity(Class clazz) {
		this.clazz=clazz;
		crmCache=(CrmCache) clazz.getAnnotation(CrmCache.class);
		table=(Table) clazz.getAnnotation(Table.class);
		// 设置主键
		for (Method method : clazz.getMethods()) {
			Annotation an = method.getAnnotation(Id.class);
			if (an != null) {
				pk = StringUtil.getFieldName(method.getName());
				break;
			}
		}
		this.initMapping(clazz);
		log.debug("新增映射:"+clazz);
	}
	
	
	
	
	/** 获得类的主键名称 */
	public String getPkName() {
		return pk;
	}
	
	/**初始化映射信息*/
	private void initMapping(Class clazz) {
		mappingFields=new HashMap();
		for (Method method :clazz.getMethods()) {		
			if (!method.getName().startsWith("get")) {
				continue;
			}
			if (method.getParameterTypes().length != 0) {
				continue;
			}
			if(method.getAnnotations().length==0){
				continue;
			}
			if(method.getAnnotation(Transient.class)!=null){
				continue;
			}
			MappingField filed=new MappingField(method); 
			mappingFields.put(filed.getFieldName(), filed);
		}
	}



	public Map<String,MappingField> getMappingFields(){
		return mappingFields;
	}

	public boolean isCache(){
		return crmCache!=null;
	}

	public Class getEntityClass() {
		return clazz;
	}


	public String getTableName() {
		if(table==null){
			return super.getDefaultTableName();
		}
		return table.name();
	}
}
