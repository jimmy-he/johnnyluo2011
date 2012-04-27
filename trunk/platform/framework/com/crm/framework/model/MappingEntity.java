package com.crm.framework.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * model类的映射信息
 * 
 * @author 王永明
 * @since Mar 11, 2010 11:45:44 AM
 */
//@edit 2010-7-13 新增通过映射信息创建sql语句的方法
public interface MappingEntity {

	
	/** 获得类的主键名称 */
	public String getPkName();

	/** 获得某个属性的返回类型 */
	public Class getReturnClass(String fieldName) ;

	
	/**获得某个多对一的实体对应的外键字段,例如入参为shop返回shopId
	 * @param fieldName 实体对应的字段名
	 * */
	public String getManyToOneField(String fieldName);
	
	
	/**获得初始化某个list的hql
	 * @param 字段值
	 * @param 该类的主键值*/
	public String getOneToManyHql(String fieldName,String pk);
	
	/**获得所有需要懒加载的字段*/
	public Set<String> getLazyLoadFileds();

	/**设置对象的主键*/
	public void setPk(Object obj,String value) ;

	/**获得某个字段的对应外键的值*/
	public String getFkValue(Object obj,String field) ;
	
	/**该实体类是是否需要缓存,在数据库更新的是否会根据此字段来决定是否发布数据库更新事件*/
	public boolean isCache();
	
	/**获得映射字段信息*/
	public Map<String,MappingField> getMappingFields();
	

	/**获得对应实体的类名*/
	public Class getEntityClass();
	
	
	/**获得所在包名称*/
	public String getPackageName();
	
	/**获得实现类的简单名称*/
	public String getSimpleName();
	
	/**获得对应表名*/
	public String getTableName();
	
	/**获得主键字段*/
	public MappingField getPk();
	
	/**获得所有属性映射*/
	public List<MappingField> getPropertys();
	
	/**获得所有属性映射*/
	public List<MappingField> getOneToMany();
	
	/**获得所有属性映射*/
	public List<MappingField> getOneToOne();
	
	/**获得所有属性映射*/
	public List<MappingField> getManyToOne();
	
	/**检查对象的主键是否存在,如果不存在就自动设置主键值*/
	public void addPk(Object obj);
	
	/**获得查询字段的sql语句*/
	public String getQueryFieldSql();
	
	
	/**将一个查询结果转换为本类的实例*/
	public Object getInstance(List<MappingField> MappingFields, Object[] result);
	
	/**将一个查询结果转换为本类的实例*/
	public Object getInstance(Object[] result);
	
}
