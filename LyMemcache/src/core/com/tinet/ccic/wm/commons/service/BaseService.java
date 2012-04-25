package com.tinet.ccic.wm.commons.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.type.Type;

import com.tinet.ccic.wm.commons.web.query.Page;
import com.tinet.ccic.wm.commons.web.query.SearchConditionModel;
/**
 * 基础业务逻辑接口
*<p>
* 文件名： BaseService.java
*<p>
* Copyright (c) 2006-2010 T&I Net Communication CO.,LTD.  All rights reserved.
* @author 周营昭
* @since 1.0
* @version 1.0
* @see com.tinet.ccic.wm.commons.service.BaseServiceImp
 */
@SuppressWarnings("unchecked")
public interface BaseService<T> {
	
	/**
	 * 根据主键获得一个对象。
	 * 
	 * @param id 主键
	 * @return 
	 */
	public T get(Serializable id);
	
	/**
	 * 根据主键获取一个指定类的对象。
	 * 
	 * @param clazz
	 * @param id
	 * @return
	 */
	public Object get(Class clazz, Serializable id);

	/**
	 * 获取当前泛型所有的实体。
	 * 
	 * @return
	 */
	public List<T> getAll();

	/**
	 * 根据主键id获取一个实体的副本。
	 * 
	 * @param id
	 * @return
	 */
	public T getClone(Serializable id);

	/**
	 * 创建一个实体。
	 * 
	 * @param o 被创建的实体。
	 * @return 创建实体的主键。
	 */
	public Serializable create(T o);
	
	/**
	 * 保存一个对象。保存或者修改。
	 * @param o
	 * @return
	 */
	public Serializable saveOrUpdate(T o);

	/**
	 * 
	 * 
	 * @param id
	 * @param o
	 * @return
	 */
	public Serializable clone(Serializable id, T o);

	/**
	 * 更新一个实体。
	 * 
	 * @param o 被更新的对象。
	 */
	public void update(T o);

	/**
	 * 删除一个对象。
	 * 
	 * @param o 待删除对象。
	 * @return 删除成功与否的标志。
	 */
	public boolean remove(T o);
	
	/**
	 * 标记删除一个对象。
	 * <p>
	 * 将一个对象的delFlag置为D。
	 * 
	 * @param o 待操作对象。
	 */
	public void markDelete(T o);
	
	/**
	 * 批量更新对象。
	 * 更新完成后，参数中的对象都变成持久化状态。
	 * 
	 * @param detachedInstances 要更新的一批对象。
	 */
	public void batchUpdate(final Collection detachedInstances);
	
	/**
	 * 批量新增或者保存一批对象。
	 * 
	 * @param instances 待操作对象。
	 */
	public void batchSaveOrUpdate(final Collection instances);
	
	/**
	 * 批量新增一批对象。
	 * 
	 * @param transientInstances 待新增对象。
	 */
	public void batchSave(final Collection transientInstances);
	
	/**
	 * 批量删除一批对象。
	 * 
	 * @param persistentInstances 待删除对象。
	 */
	public void batchRemove(final Collection persistentInstances);

	/**
	 * 
	 * @param name 属性名。
	 * @param value 属性值。
	 * @param id 对象ID。
	 * @return
	 */
	public boolean checkUnique(String name, Object value, String id);

	/**
	 * 获取由参数pojoClass指定类的，主键为id对象。
	 * @param pojoClass 指定实体类。
	 * @param id 主键id。
	 * @return
	 */
	public Object findUniqueBy(Class pojoClass, Serializable id);

	/**
	 * 通过hql语句查询实体。
	 * @param hql 查询所用hql。
	 * @return 查询出的记录，记录根据hql语句的不同而不同。
	 */
	public List findByHql(String hql);
	
	/**
	 * 通过sql语句查询实体。
	 * @param sql 查询所用sql。
	 * @return 查询出的记录。
	 */
	public List findBySql(String sql);

	/**
	 * 使用带有参数的hql语句查询记录。
	 * 
	 * @param hql 查询所用hql。
	 * @param args hql中使用的参数值。
	 * @param types 参数args一一对应的参数类型。一般使用Hibernate.STRING等常量来指定。
	 * @return
	 */
	public List searchByHql(String hql, Object[] args, Type[] types);

	/**
	 * 使用带有参数的sql语句查询记录。
	 * 
	 * @param sql 查询所用sql。
	 * @param args sql中使用的参数值。
	 * @param types 参数args一一对应的参数类型。一般使用Hibernate.STRING等常量来指定。
	 * @return
	 */
	public List searchBySql(String sql, Object[] args, Type[] types);
	
	/**
	 * 使用带有参数的hql语句查询记录。
	 * <li>searchByHql("form Entity where entityId=? and entityName=?", new Integer(1), "zhongguo");</li>
	 * <li>searchByHql("form Entity where entityId=? and entityName=? and entitytel=?", new Integer(1), "zhongguo", "82882828");</li>
	 * 
	 * @param hql 查询所用hql。
	 * @param values hql中对应的参数值。
	 * @return 查询出的记录。
	 */
	public List searchByHql(String hql, Object... values);
	
	/**
	 * 使用带有参数的hql语句分页查询记录。
	 * <p />for example:
	 * searchByHql("form Entity where entityId=? and entityName=?"
	 * 				, new Object[2]{"1", "zhongguo"}
	 * 				, new Type[2]{Hibernate.Integer, Hibernate.STRING}
	 * 				, 0, 10);
	 * 
	 * @param hql 查询所用hql。
	 * @param args hql中使用的参数值。
	 * @param types 参数args一一对应的参数类型。一般使用Hibernate.STRING等常量来指定。
	 * @param start 查询的起始记录数，从0计数。
	 * @param pageSize 分页大小。
	 * @return
	 */
    public Page searchByHql(String hql, Object[] args, Type[] types, int start, int pageSize);
    
    /**
	 * 使用带有参数的sql语句分页查询记录。
	 * 
	 * @param sql 查询所用sql。
	 * @param args hql中使用的参数值。
	 * @param types 参数args一一对应的参数类型。一般使用Hibernate.STRING等常量来指定。
	 * @param start 查询的起始记录数，从0计数。
	 * @param pageSize 分页大小。
	 * @return
	 */
    public Page searchBySql(String sql, Object[] args, Type[] types, int start, int pageSize);
    
    /**
     * 框架所用查询函数，不主张在自己的程序中使用。
     * 
     * @param scModel
     * @return
     */
    public Page search(SearchConditionModel scModel);
    
    /**
     * 根据制定的条件，分页查询泛型指定的实体。
     * 
     * @param filterMap 查询的条件，属性名到属性值的映射。
     * @param orderMap 排序条件，属性名到asc/desc的映射。
     * @param start 查询的起始记录数，从0计数。
     * @param pageSize 分页大小。
     * @return 封装好的Page对象。
     * 
     * @see com.tinet.ccic.wm.commons.dao.BaseDao#findBy(Map, Map, int, int)
     */
    public Page<T> search(Map filterMap, Map orderMap, int start, int pageSize);
	
    
    /**
     * 通过一个属性查找。
     * @param name
     * @param value
     * @return
     */
    public List<T> findBy(String name, Object value);
    
    /**
	 * 使用hql查询，缓存查询结果。
	 * @author louxue
	 * @param hql hql语句。
	 * @return 返回Object数组的list。
	 */
	public List findByHqlCache(String hql);
    
    /**
	 * 使用动态赋值的hql查询。
	 * @author louxue
	 * @param qryHql 查询的hql语句。
	 * @param args hql动态赋值参数取值数组。
	 * @param types hql动态赋值参数类型数组。
	 * @return 返回查询结果list。
	 */
	public List findByHqlCache(String qryHql, Object[] args, Type[] types);
	
	/**
	 * 使用动态赋值的sql查询。
	 * @author louxue
	 * @param qrySql 查询的hql语句。
	 * @param args sql动态赋值参数取值数组。
	 * @param types sql动态赋值参数类型数组。
	 * @param columnAlias 查询列的别名。
	 * @param columnTypes 查询列的数据类型。
	 * @return 返回查询结果list。
	 */
	public List findBySqlCache(String qrySql, Object[] args, Type[] types, String[] columnAlias, Type[] columnTypes);
	
	/**
	 * 根据某一个属性以及属性值获取对象，缓存查询结果。
	 * @param name 属性名。
	 * @param value 属性值。
	 */
	public List<T> findByCache(String name, Object value);
	
	/**
	 * 根据某一个属性以及属性值获取唯一的一个对象，缓存查询结果。
	 * @param name 属性名。
	 * @param value 属性值。
	 */
	public T findUniqueByCache(String name, Object value);
	
	/**
	 * 根据查询过滤条件和排序条件进行查询，查询条件使用默认的取查询参数属性与值相等的比较方法。
	 * @author louxue
	 * @param filterMap 过滤条件，name-value对，name为泛型T对应实体的属性名，value为取值。
	 * @param orderMap 排序条件。
	 * @return 返回查询结果list对象。
	 */
	public List<T> findByCache(Map<String, Object> filterMap, Map<String, Object> orderMap);
	
	/**
	 * 根据查询过滤条件和排序条件进行查询，查询条件使用自定义的setUpCriteriaMethod比较方法。
	 * @author louxue
	 * @param filterMap 过滤条件，name-value对，name为泛型T对应实体的属性名，value为取值。
	 * @param orderMap 排序条件。
	 * @param setUpCriteriaMethod  定义在自己DaoImp中的比较方法。
	 * @return 返回查询结果list对象。
	 */
	public List<T> findByCache(Map<String, Object> filterMap, Map<String, Object> orderMap, String setUpCriteriaMethod);
}
