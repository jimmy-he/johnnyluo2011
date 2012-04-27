package com.crm.framework.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.crm.framework.common.Global;
import com.crm.framework.common.util.BeanUtil;
import com.crm.framework.common.util.CUID;
import com.crm.framework.common.util.JavaTypeUtil;
import com.crm.framework.common.util.StringUtil;

/**
 * 
 * @author 王永明
 * @since Apr 4, 2010 3:15:06 PM
 */
 abstract public class AbstracMapptionEntity implements MappingEntity {


	/**获得某个字段的对应外键的值*/
	public String getFkValue(Object obj,String field) {
		String fk=this.getManyToOneField(field);
		return BeanUtil.getValue(obj, fk)+"";
	}


	/**获得初始化某个list的hql
	 * @param 字段值
	 * @param 该类的主键值*/
	public String getOneToManyHql(String fieldName,String pk){
		MappingField field=this.getMappingFields().get(fieldName);	
		String oneToManyField=field.getOneToMany().mappedBy();
		Class clazz=field.getListType();
		MappingEntity mapping=MappingCache.getMapping(clazz);	
		String realField=mapping.getManyToOneField(oneToManyField);	
		String hql="from "+clazz.getSimpleName()+" where "+realField+"='"+pk+"'";
		return hql;
	}
	

	/** 获得某个属性的返回类型 */
	public Class getReturnClass(String fieldName) {
		return this.getMappingFields().get(fieldName).getReturnType();
	}

	/**设置对象的主键*/
	public void setPk(Object obj,String value) {
		BeanUtil.setField(obj, this.getPkName(), value);
	}
	
	public void addPk(Object obj){
		Object pk=BeanUtil.getValue(obj, this.getPkName());
		if(StringUtil.isNull(pk)){
			String id=CUID.createUUID(this.getSimpleName());
			BeanUtil.setField(obj, this.getPkName(), id);
		}
	}
	

	/**获得所有懒加的字段*/
	public Set<String> getLazyLoadFileds(){	
		Set lazyLoadFiled=new HashSet();
		
		for(Entry<String,MappingField> en:this.getMappingFields().entrySet()){
			if(en.getValue().getLazy()){
				lazyLoadFiled.add(en.getKey());
			}
		}
		return lazyLoadFiled;
	}
	
	/**获得某个多对一的实体对应的外键字段,例如入参为shop返回shopId
	 * @param fieldName 实体对应的字段名
	 * */
	public String getManyToOneField(String fieldName) {
		Map<String,MappingField>  mappingFields=this.getMappingFields();
		MappingField field=mappingFields.get(fieldName);
		for(MappingField f:mappingFields.values()){
			if(f.getColumn()==null){
				continue;
			}
			if(field.getJoinColumn().equals(f.getColumn()))	
				return f.getFieldName();
		}
		throw new RuntimeException(this.getEntityClass()+"映射不正确,找不到实体"+fieldName+"对应的字段!");
	}


	public String getPackageName() {
		return this.getEntityClass().getPackage().getName();
	}


	public String getSimpleName() {
		return this.getEntityClass().getSimpleName();
	}

	public MappingField getPk() {
		return this.getMappingFields().get(this.getPkName());
	}

	
	public List<MappingField> getManyToOne() {
		List list=new ArrayList();
		for(MappingField f:this.getMappingFields().values()){
			if(f.getIsManyToOne()){
				list.add(f);
			}
		}
		return list;
	}


	public List<MappingField> getOneToMany() {
		List list=new ArrayList();
		for(MappingField f:this.getMappingFields().values()){
			if(f.getIsOneToMany()){
				list.add(f);
			}
		}
		return list;
	}


	public List<MappingField> getPropertys() {
		List list=new ArrayList();
		for(MappingField f:this.getMappingFields().values()){
			if(f.getIsProperty()){
				list.add(f);
			}
		}
		return list;
	}
	
	

	public List<MappingField> getOneToOne() {
		List list=new ArrayList();
		for(MappingField f:this.getMappingFields().values()){
			if(f.getIsOneToOne()){
				list.add(f);
			}
		}
		return list;
	}

	
	public String getDefaultTableName( ) {
		String pix=Global.getConfig().getTablePrefix();
		String tableName=JavaTypeUtil.toDatabaseName(this.getSimpleName());
		if(tableName.indexOf(pix)==0){
			return tableName;
		}
		return pix+tableName;
		
	}

	/**获得查询字段的sql语句*/
	public String getQueryFieldSql(){
		StringBuilder sb=new StringBuilder();
		sb.append("select ");
		for(MappingField field:this.getPropertys()){
			sb.append(field.getColumn()+",");
		}
		return sb.substring(0,sb.length()-1);
	}
	
	
	/**将一个查询结果转换为本类的实例*/
	public Object getInstance(List<MappingField> mappingFields, Object[] result){
		Object o;
		try {
			o = this.getEntityClass().newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		for(int i=0;i<result.length&&i<mappingFields.size();i++){
			MappingField field=mappingFields.get(i);
			BeanUtil.setField(o, field.getFieldName(), result[i]);
		}
		return o;
	}
 
 	/**将一个查询结果转换为本类的实例*/
	public Object getInstance(Object[] result){
		return getInstance(this.getPropertys(),result);
	}
	

}
