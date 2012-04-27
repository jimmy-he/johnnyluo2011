package com.crm.framework.model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.MappedSuperclass;

import com.crm.framework.common.Global;
import com.crm.framework.common.util.JavaTypeUtil;
import com.crm.framework.common.util.StringUtil;
import com.crm.framework.dao.annotation.CrmEntity;

/**
 * 
 * @author 王永明
 * @since Apr 4, 2010 2:59:59 PM
 */
public class CrmMappingEntity  extends AbstracMapptionEntity{
	
	private String pkName;
	private CrmEntity entity;
	private Class clazz;
	private Map<String, MappingField> map;
	

	public CrmMappingEntity(Class clazz) {
		entity=(CrmEntity) clazz.getAnnotation(CrmEntity.class);
		pkName=entity.pk();
		if(StringUtil.isNull(pkName)){
			pkName=StringUtil.firstLower(clazz.getSimpleName())+"Id";
		}
		this.clazz=clazz;
		if(this.getMappingFields().get(pkName)==null){
			throw new RuntimeException("未找到实体类的主键,请检查代码:"+clazz+","+pkName);
		}
		
	
	}


	public Class getEntityClass() {
		return clazz;
	}


	/**获得该类的所有映射字段*/
	public Map<String, MappingField> getMappingFields() {
		if(map!=null){
			return map;
		}
		map=new HashMap();
		for(Field field:clazz.getDeclaredFields()){
			this.addField(map, field);
		}
		Class parent=clazz.getSuperclass();
		for(int i=0;i<100;i++){
			if(parent==Object.class){
				break;
			}
			if(parent.getAnnotation(MappedSuperclass.class)!=null){
				for(Field field:parent.getDeclaredFields()){
					this.addField(map, field);
				}
			}
			parent=clazz.getSuperclass();
		}
		return map;
	}
	
	

	
	private void addField(Map<String, MappingField> map,Field field){
		MappingField mapPingFiled=new MappingField(field,this);
		map.put(field.getName(), mapPingFiled);
	}

	public String getPkName() {
		return pkName;
	}


	public boolean isCache() {
		return entity.isCache();
	}


	public String getTableName() {
		if(StringUtil.isNull(entity.tableName())){
			return super.getDefaultTableName();
		}
		return  entity.tableName();
	}

}
