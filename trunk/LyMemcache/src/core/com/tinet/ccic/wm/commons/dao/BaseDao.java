package com.tinet.ccic.wm.commons.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Criterion;
import org.hibernate.type.Type;

import com.tinet.ccic.wm.commons.util.QuerySelect;
import com.tinet.ccic.wm.commons.web.query.Page;
/**
* 基础数据存取对象接口
*<p>
* 文件名： BaseDao.java
*<p>
* Copyright (c) 2006-2010 T&I Net Communication CO.,LTD.  All rights reserved.
* @author 娄雪
* @since 1.0
* @version 1.0
* @see com.tinet.ccic.wm.commons.dao.BaseDaoImp
*/
@SuppressWarnings("unchecked")
public interface BaseDao<T> {

	public static final String DEFAULT_SETUPCRITIRIA_METHOD = "setUpCriteria";
	
	/**
	 * 根据主键获取对象。
	 * 
	 * @param id 主键
	 * @return 当前泛型的对象
	 */
	public T get(Serializable id);

	/**
	 * 根据主键返回克隆对象。
	 * 
	 * @param id 主键
	 * @return clone对象
	 */
	public T getClone(Serializable id);

	/**
	 * 返回当前泛型全部的实体。
	 * 
	 * @return 当前泛型全部的实体
	 */
	public List<T> getAll();

	/**
	 * 新建实体。
	 * 
	 * @param pojo 要被创建的对象
	 * @return 创建实体后实体的主键值。
	 */
	public Serializable create(T pojo);

	/**
	 * 保存一个对象。保存或者修改。
	 * @param o
	 * @return
	 */
	public Serializable saveOrUpdate(T o);
	
	/**
	 * 更新一个实体。
	 * 
	 * @param pojo 待更新实体
	 */
	public void update(T pojo);
	
	
	/**
	 * 批量更新数据。
	 * 
	 * @param detachedInstances 待更新实体集合
	 */
	public void batchUpdate(final Collection detachedInstances);

	/** 
	 * 更新数据。
	 * 更新前后，实体的状态(游离、持久化)不变。
	 * 
	 * @param t 待更新实体。
	 */
	public void merge(T t);
	
	/**
	 * 保存或者更新一批数据。
	 * 如果id为空，则save，如果id不为空则update。

	 * @param instances 待保存实体集合。
	 */
	public void batchSaveOrUpdate(final Collection instances);
	
	/**
	 * 批量保存一批数据。
	 * 
	 * @param transientInstances 待新增的实体集合。
	 */
	public void batchSave(final Collection transientInstances);
	
	/**
	 * 批量删除一批持久化对象
	 * 
	 */
	public void batchRemove(final Collection persistentInstances);

	/**
	 * 删除对象。
	 * 
	 * @param pojo 待删除的持久化对象。
	 */
	public boolean remove(T pojo);
	

	/**
	 * 根据属性名和属性值查询一个实体。
	 * 查询条件是name=value，如果可查出多条记录，则抛出异常。
	 * 
	 * @param name 属性名称
	 * @param value 属性值
	 * @return 符合条件的唯一泛型对象
	 */
	public T findUniqueBy(String name, Object value);

	/**
	 * 根据属性名和属性值查询符合条件的一批对象。
	 * 查询条件是name=value，可以根据参数查询出多条记录。
	 * 
	 * @param name 属性名称
	 * @param value 属性值
	 * @return 符合条件的对象列表。
	 */
	public List<T> findBy(String name, Object value);

	/**
	 * 根据属性名和属性值以Like AnyWhere方式查询对象。
	 * 可以根据参数查询出多条记录。
	 * 
	 * @param name 属性名称
	 * @param value 属性值
	 * @return 符合条件的对象列表
	 */
	public List<T> findByLike(String name, String value);

	/**
	 * 通过filter中设置的查询条件来查询一批实体。
	 * <p>
	 * 实体的查询条件由{@link BaseDaoImp#setUpCriteria(Criteria, Map)}来设置，setUpCriteria默认的实现方法是将map中的条件都设置为相等。
	 * 在具体的dao实现中，你可以重写setUpCriteria方法，实现特殊的设置，比如like,大于等。
	 * 
	 * @param filter Map，查询条件，由属性到值的映射。
	 */
	public List<T> findBy(Map<String, Object> filter);
	
	
	/**
	 * 通过filter中设置的查询条件来查询一批实体。
	 * <p>
	 * 查询条件由filter来给出，但是具体的设置由第二个参数指定的设置方法来设置，你必须在dao实现中，实现第二个参数指定的设置方法。
	 * 比如第二个参数为setupMyCriteria,那么你必须实现方法<code>public void setUpMyCriteria(Criteria criteria, Map filterMap)</code>
	 * 
	 * @param filter Map，查询条件，由属性到值的映射。
	 * @param setUpCriteriaMethod String， 设置查询条件的方法名。
	 */
	public List<T> findBy(Map<String, Object> filter, String setUpCriteriaMethod);

	
	/**
	 * 通过filter中设置的查询条件分页来查询一批实体，排序按照orderMap指定的排序规则来排序。分页由start，pageSize来指定。
	 * 
	 * <p>
	 * 查询条件的设置由setUpCriteriaMethodName指定的方法来设置。你必须在dao实现中，实现第二个参数指定的设置方法。
	 * 比如第二个参数为setupMyCriteria,那么你必须实现方法<code>public void setUpMyCriteria(Criteria criteria, Map filterMap)</code>
	 * 
	 * 
	 * @param filterMap Map 过滤条件。
	 * @param orderMap 排序条件，属性名到字符串asc,desc的映射(字符串的值由Constants.ORDER_ASCEND指定，目前等于'asc','desc')。
	 * @param start 当前查询起始记录，从0计数。
	 * @param pageSize 每页显示记录数。
	 * @param setUpCriteriaMethodName 将Map中条件转换为criteria的函数名,支持子类对Map中的条件有多种处理方法.
	 * 
	 * @return Page，查询出的分页数据。
	 */
	public Page<T> findBy(Map filterMap, Map orderMap, int start, int pageSize, String setUpCriteriaMethodName);
	
	
	
	public Page<T> findByCriteria(QuerySelect querySelect, Map orderMap, int start, int pageSize);
	
	/**
	 * 函数作用同{@link #findBy(Map,Map,int,int,String)}；<br>
	 * 只不过使用默认的方法{@link BaseDaoImp#setUpCriteria(Criteria, Map)}来设置查询条件。
	 */
	public Page<T> findBy(Map filterMap, Map orderMap, int start, int pageSize);
	
	/**
	 * 函数作用同{@link #findBy(Map,Map,int,int,String)}；<br>
	 * 没有排序条件，并且使用默认的方法{@link BaseDaoImp#setUpCriteria(Criteria, Map)}来设置查询条件。
	 * 
	 * @param filterMap 过滤条件.
	 * @param start 当前页码
	 * @param pageSize 每页显示记录数.
	 */
	public Page<T> findBy(Map filterMap, int start, int pageSize);
	
	
	/**
	 * 查询分页数据，由框架调用，一般不会被重写。如果需要修改一下查询条件，可以调用{@link BaseDaoImp#trimCriteria(Criteria, Map)}对查询条件进行调整。
	 * 
	 * @param filterMap 过滤条件 
	 * @param ruleMap 过滤条件及条件规则
	 * @param orderMap 排序条件
	 * @param start 当前查询起始记录
	 * @param pageSize 每页显示记录数.
	 * @return Page
	 */
	public Page<T> findBy(Map filterMap, Map ruleMap, Map orderMap, int start, int pageSize, boolean isPaged);
	
	//************************************************************************** 
	//* 上面的针对当前泛型的实体DAO操作
	//* 以下方法提供通用对其他实体访问接口
	//**************************************************************************

	/**
	 * 根据指定的列来判断对象在数据库中不存在重复。
	 * 
	 * @param entity 被判断的对象实例。
	 * @param names 在POJO里相对应的属性名,列组合时以逗号分割。
	 *            如"name,loginid,password"
	 */
	public boolean checkUnique(Object entity, String names);
	
	/**
	 * 根据主键查询指定类型的对象。
	 * 
	 * @param pojoClass 要返回对象的class
	 * @param id 主键id
	 * @return 符合条件的唯一对象
	 */
	public Object findUniqueBy(Class pojoClass, Serializable id);

	/**
	 * 根据HQL查询一个对象。
	 * 如果这个条件能查出多条记录，则只返回第一条记录。
	 * 
	 * @param hql 查询hql字符串
	 * @return 符合条件的一个对象。
	 */
	public Object findUniqueByHql(String hql);

	/**
	 * 根据HQL查询一组对象。
	 * 
	 * @param hql 查询hql字符串
	 * @return 符合条件的对象链表
	 */
	public List findByHql(String hql);
	
	/**
	 * 根据SQL查询一组对象，可以返回非本泛型实体的对象
	 * 
	 * @param sql 查询所用sql
	 * @return 符合条件的对象链表
	 */
	public List findBySql(String sql);

	/**
	 * 根据SQL进行分页查询，可查询非本泛型的对象
	 * 
	 * @param qryHql 查询所用Hql.
	 * @param start 当前查询起始记录，从0计数。
	 * @param pageSize 每页显示记录数。
	 */
	public Page findByHql(String qryHql, int start, int pageSize);

    /**
	 * 有参数形式，根据HQL进行分页查询.
	 * 
	 * @param qryHql 查询所用Hql，可带有位置参数?。
	 * @param args 参数值数组。
	 * @param values 参数类型数组，一般使用{@link Hibernate}下的类型常量等来设置。
	 * @param start 当前查询起始记录，从0计数。
	 * @param pageSize 每页显示记录数.
	 */
	public Page findByHql(String qryHql,  Object[] args, Type[] values, int start, int pageSize);
	
	/**
	 * 有参数形式，查询满足条件的所有对象。
	 * 
	 * @param qryHql hql条件，带有位置参数?。
	 * @param args 参数值数组。
	 * @param types 参数类型数组，一般使用{@link Hibernate}下的类型常量等来设置。
	 */
	public List findByHql(String qryHql,  Object[] args, Type[] types);
	
	/**
	 * 按顺序填入参数，查询满足条件的所有实体
	 * 
	 * @param hql 查询使用的hql，带有位置参数?。
	 * @param values 根据参数需要，任意多的参数。
	 */
    public List findByString(String hql, Object... values);
    
    /**
	 * 分页查询函数，使用hql。
	 * 
	 * @param hql 查询使用的hql
	 * @param start 查询的起始记录号,从0开始.
	 * @param pageSize 每页条数。
	 * @param values 根据参数需要，任意多的参数
	 */
	public Page findByString(String hql, int start, int pageSize, Object... values);
	
	
	 /**
	 * 有参数形式，根据SQL进行查询.
	 * 
	 * @param qrySql 查询使用的sql语句。
	 * @param args 参数值。
	 * @param types 参数类型数组，一般使用{@link Hibernate}下的类型常量等来设置。
	 */
	public List findBySql(String qrySql,  Object[] args, Type[] types);
	
	
	/**
	 * 使用sql语句分页查询。
	 * 
	 * @param sql sql条件。
	 * @param start 本次查询起始记录数，从0计数。
	 * @param pageSize 每页显示记录数。
	 */
	public Page findBySql(String sql, int start, int pageSize);
	
	
	/**
	 * 使用sql语句按照指定的条件分页查询。
	 * 
	 * @param sql sql条件。
	 * @param args 参数值。
	 * @param types 参数类型数组，一般使用{@link Hibernate}下的类型常量等来设置。
	 * @param start 本次查询起始记录数，从0计数。
	 * @param pageSize 每页显示记录数。
	 */
	public Page findBySql(String sql, Object[] args, Type[] types, int start, int pageSize);
	
	/**
	 * 直接执行Hql.
	 * 对于需要first,max,fetchsize,cache,cacheRegion等诸多设置的函数,可以返回Query后自行设置.
	 * 留意可以连续设置,如 dao.getQuery(hql).setMaxResult(100).setCacheable(true).list();
	 *
	 * @param values 可变参数
	 */
	public int executeHQL(String hql, Object... values);

	/**
	 * 直接执行sql。
	 *
	 * @param values 可变参数
	 *               用户可以如下四种方式使用
	 *               dao.excuteSQLQuery(sql)
	 *               dao.excuteSQLQuery(sql,arg0);
	 *               dao.excuteSQLQuery(sql,arg0,arg1);
	 */
	public int executeSQL(String sql, Object... values);
	
	
	/**
	 * 创建Criteria对象
	 *
	 * @param criterion 可变条件列表,Restrictions生成的条件
	 */
	public <T> Criteria getCriteria(Class<T> entityClass, Criterion... criterion);
	
	/**
	 * 获取数据库时间
	 */
	public Date getSystemDate();
	
	/**
	 * 使对象从Hibernate缓存中移除。
	 * 
	 * @param persistentInstance
	 */
	public void evict(Object persistentInstance);
	
	public void flush();
	
	
	public String findTextBySql(String qrySql);
	
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
	 * 使用动态赋值的sql查询
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
