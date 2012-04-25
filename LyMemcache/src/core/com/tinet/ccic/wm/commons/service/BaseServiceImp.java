package com.tinet.ccic.wm.commons.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NestedRuntimeException;
import org.springframework.dao.DataAccessException;

import com.tinet.ccic.wm.commons.BaseStandardEntity;
import com.tinet.ccic.wm.commons.CCICException;
import com.tinet.ccic.wm.commons.dao.BaseDao;
import com.tinet.ccic.wm.commons.web.query.Page;
import com.tinet.ccic.wm.commons.web.query.SearchConditionModel;

/**
 * 基础业务逻辑实现类
 * <p>
 * 	文件名： BaseServiceImp.java
 * <p>
 * Copyright (c) 2006-2010 T&I Net Communication CO.,LTD.  All rights reserved.
 * @author 周营昭
 * @since 1.0
 * @version 1.0
 * @see com.tinet.ccic.wm.commons.service.BaseService
 */
@SuppressWarnings("unchecked")
abstract public class BaseServiceImp<T> implements BaseService<T> {

	/** 日志管理器 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 获取一个具体的DAO对象，由子类实现
	 */
	abstract protected BaseDao<T> getBaseDao();

	/**
	 * 获得一个对象，该对象是一个受管理的Hibernate对象
	 */
	public T get(Serializable id) {
		try {
			return getBaseDao().get(id);
		} catch (DataAccessException e) {
			logger.error("加载实体" + id, e);
			throw new CCICException("SYS01000", e);
		}
	}
	
	/**
	 * 获得某一个对象，该对象是一个受管理的Hibernate对象
	 */
	public Object get(Class clazz, Serializable id) {
		try {
			return getBaseDao().findUniqueBy(clazz, id);
		} catch (DataAccessException e) {
			logger.error("加载实体" + clazz.getName() + "@" + id, e);
			throw new CCICException("SYS01000", e);
		}
	}

	/**
	 * 获得全部实体对象
	 */
	public List<T> getAll() {
		try {
			return getBaseDao().getAll();
		} catch (DataAccessException e) {
			logger.error("加载所有实体", e);
			throw new CCICException("SYS01001", e);
		}
	}

	/**
	 * 获得一个被克隆对象的副本
	 */
	public T getClone(Serializable id) {
		try {
			return getBaseDao().getClone(id);
		} catch (DataAccessException e) {
			logger.error("获取副本时" + id, e);
			throw new CCICException("SYS01002", e);
		}
	}

	/**
	 * 执行对象删除
	 */
	public boolean remove(T o) {
		if (preCheckOrHandleForDelete(o)) {
			try {
				return getBaseDao().remove(o);
			} catch (DataAccessException e) {
				logger.error("当删除实体时出错：" + o.toString(), e);
				throw new CCICException("SYS01003", e);
			}
		} else {
			return false;
		}
	}
	
	/**
	 * 标记删除对象
	 */
	public void markDelete(T o) {
		// donothing
	}

	/**
	 * 执行对象创建
	 */
	public Serializable create(T o) {
		if (preCheckForCreateOrUpdate(o)) {
			try {
				return getBaseDao().create(o);
			} catch (DataAccessException e) {
				logger.error("创建实体时出错：" + o.toString(), e);
				throw new CCICException("SYS01004", e);
			} 
		} else {
			return null;
		}
	}
	
	public Serializable saveOrUpdate(T o) {
		return this.getBaseDao().saveOrUpdate(o);
	}

	/**
	 * 执行对象克隆？？？？？？？？
	 * 
	 * @param id 被Clone对象的主键
	 */
	public Serializable clone(Serializable id, T o) {
		if (preCheckForCreateOrUpdate(o)) {
			afterHandleForClone(this.get(id), o);
			return getBaseDao().create(o);
		} else {
			return null;
		}
	}

	/**
	 * 执行对象更新
	 */
	public void update(T o) throws CCICException {
		try {
			if (preCheckForCreateOrUpdate(o)) {
				getBaseDao().update(o);
			}
		} catch (DataAccessException e) {
			logger.error("更新实体时出错：" + o.toString(), e);
			throw new CCICException("SYS01005", e);
		}
	}
	
	/**
	 * 批量更新实体
	 */
	public void batchUpdate(final Collection detachedInstances) {
		try {
			getBaseDao().batchUpdate(detachedInstances);
		} catch (NestedRuntimeException e) {
			logger.error("批量更新实体时出错", e);
			throw new CCICException("SYS01005", e);
		}
	}

	/**
	 * 批量保存实体，新增或者修改。
	 */
	public void batchSaveOrUpdate(final Collection instances) {
		try {
			getBaseDao().batchSaveOrUpdate(instances);
		} catch (DataAccessException e) {
			logger.error("批量保存实体时出错", e);
			throw new CCICException("SYS01006", e);
		}
	}

	/**
	 * 
	 */
	public void batchSave(final Collection transientInstances) {
		try {
			getBaseDao().batchSave(transientInstances);
		} catch (DataAccessException e) {
			logger.error("批量创建实体时出错", e);
			throw new CCICException("SYS01006", e);
		}
	}

	public void batchRemove(final Collection persistentInstances)
			throws CCICException {
		try {
			getBaseDao().batchRemove(persistentInstances);
		} catch (NestedRuntimeException e) {
			logger.error("批量删除实体时出错", e);
			throw new CCICException("SYS01003", e);
		}
	}
	
	
	/**
	 * 通过封装好的查询条件来查询，通用查询都用这个。
	 */
	public Page<T> search(SearchConditionModel scModel) {
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(scModel.toString());
			}
			return this.getBaseDao().findBy(scModel.getFilterMapWithRealType(), scModel.getRuleMap(), scModel.getOrderMap(), 
											scModel.getStart(), scModel.getPageSize(), scModel.isPaged());
		} catch (DataAccessException e) {
			logger.error("查询实体时出错", e);
			throw new CCICException("SYS01007", e);
		}
	}

	/**
	 * 执行分页查询
	 */
	public Page<T> search(Map filterMap, Map orderMap, int start, int pageSize) {
		try {
			Page<T> page = getBaseDao().findBy(filterMap,orderMap,start,pageSize);
			return page;
		} catch (DataAccessException e) {
			logger.error("查询时出错", e);
			throw new CCICException("SYS01007", e);
		}
	}
	
	/**
	 * 无参数形式，SQL执行无分页查询
	 */
	public List findByHql(String hql) {
		try {
			return getBaseDao().findByHql(reDoSql(new StringBuffer(hql)));
		} catch (DataAccessException e) {
			logger.error("查询时出错，hql:" + hql, e);
			throw new CCICException("SYS01007", e);
		}
	}
	
	/**
	 * 无分页查询
	 */
	public List findBySql(String sql) {
		
		try {
			return getBaseDao().findBySql(reDoSql(new StringBuffer(sql)));
		} catch (DataAccessException e) {
			logger.error("查询时出错，sql:" + sql, e);
			throw new CCICException("SYS01007", e);
		}
	}
	
	
	/**
	 * 有参数形式，SQL执行分页查询
	 */
	public Page searchByHql(String hql, Object[] args, Type[] types, int start, int pageSize) {
		try {
			Page page = getBaseDao().findByHql(reDoSql(new StringBuffer(hql)), 
												args, types, start, pageSize);
			return page;
		} catch (DataAccessException e) {
			logger.error("查询时出错，hql:" + hql, e);
			throw new CCICException("SYS01007", e);
		}
	}
	
	/**
	 * 有参数形式，SQL执行无分页查询
	 */
	public List searchByHql(String hql, Object[] args, Type[] types) {
		try {
			return getBaseDao().findByHql(reDoSql(new StringBuffer(hql)), args, types);
		} catch (DataAccessException e) {
			logger.error("查询时出错，hql:" + hql, e);
			throw new CCICException("SYS01007", e);
		}
	}
	/**
	 * 有参数形式，SQL执行无分页查询
	 */
	public List searchBySql(String sql, Object[] args, Type[] types) {
		try {	
			return getBaseDao().findBySql(reDoSql(new StringBuffer(sql)), args, types);
		} catch (DataAccessException e) {
			logger.error("查询时出错，sql:" + sql, e);
			throw new CCICException("SYS01007", e);
		}
	}
	
	public Page searchBySql(String sql, Object[] args, Type[] types, int start, int pageSize) {
		try {
			Page page = getBaseDao().findBySql(reDoSql(new StringBuffer(sql)),
												args, types, start, pageSize );
			return page;
		} catch (DataAccessException e) {
			logger.error("查询时出错，sql:" + sql, e);
			throw new CCICException("SYS01007", e);
		}
	}

	/**
	 * 有参数形式，SQL执行无分页查询
	 */
	public List searchByHql(String hql, Object... values) {
		try {
			return getBaseDao().findByString(reDoSql(new StringBuffer(hql)), values);
		} catch (DataAccessException e) {
			logger.error("查询时出错，hql:" + hql, e);
			throw new CCICException("SYS01007", e);
		}
	}

	protected String reDoSql(StringBuffer hql) throws CCICException {
		return hql.toString();
	}

	/**
	 * 执行新建或更新之前的条件检查，诸如唯一性约束条件之类的检查。由子类视具体情况进行覆写
	 * 
	 * @param o 待检查对象
	 * @throws CCICException
	 */
	protected boolean preCheckForCreateOrUpdate(T o)
			throws CCICException {
        
		return true;
	}

	/**
	 * 执行删除之前的条件检查，诸如对象外键关联约束之类的检查。由子类视具体情况进行覆写
	 * 
	 * @param o 待检查对象
	 * @return 检查是否通过
	 */
	protected boolean preCheckOrHandleForDelete(T o)
			throws CCICException {
		return true;
	}

	/**
	 * 执行Clone以后关联Collection对象的处理，一般是取原始对象的Collection属性进行循环追加到一个新的Collection类型对象，然后赋给新Clone的对象
	 * 由子类视具体情况进行覆写
	 * 
	 * @param orgObj 被Clone的原始对象
	 * @param newObj 新建的Clone对象
	 */
	protected void afterHandleForClone(T orgObj, T newObj)
			throws CCICException {

	}

	/**
	 * 检测字段值是否已存在，excludeSelf标识是否排除自己,一般编辑对象检测时需要排除自己
	 * 
	 * @return true=检测值可用,false=检测值违反唯一约束
	 */
	public boolean checkUnique(String name, Object value, String id) {
		T object = getBaseDao().findUniqueBy(name, value);
		if (object == null) {
			return true;
		} else {
			if (StringUtils.isNotEmpty(id)) {
				BaseStandardEntity be = (BaseStandardEntity) object;
				if (id.equals(be.getId().toString())) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}
	}

	public Object findUniqueBy(Class pojoClass, Serializable id) {
		try{
			return getBaseDao().findUniqueBy(pojoClass, id);
		} catch (DataAccessException e) {
			logger.error("查询单个实体时出错，class:" + pojoClass.getName() + "@" + id, e);
			throw new CCICException("SYS01007", e);
		}
	}
	
	public List<T> findBy(String name, Object value) {
		return this.getBaseDao().findBy(name, value);
	}
	
	
	/**
	 * 使用hql查询，缓存查询结果。
	 * @author louxue
	 * @param hql hql语句。
	 * @return 返回Object数组的list。
	 */
	public List findByHqlCache(String hql){
		try {
			return getBaseDao().findByHqlCache(hql);
		} catch (DataAccessException e) {
			logger.error("查询时出错，hql:" + hql, e);
			throw new CCICException("SYS01007", e);
		}
	}
	
	
	/**
	 * 使用动态赋值的hql查询。
	 * @author louxue
	 * @param qryHql 查询的hql语句。
	 * @param args hql动态赋值参数取值数组。
	 * @param types hql动态赋值参数类型数组。
	 * @return 返回查询结果list。
	 */
	public List findByHqlCache(String qryHql, Object[] args, Type[] types){
		try {
			return getBaseDao().findByHqlCache(qryHql, args, types);
		} catch (DataAccessException e) {
			logger.error("查询时出错，hql:" + qryHql, e);
			throw new CCICException("SYS01007", e);
		}
	}
	
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
	public List findBySqlCache(String qrySql, Object[] args, Type[] types, String[] columnAlias, Type[] columnTypes){
		try {
			return getBaseDao().findBySqlCache(qrySql, args, types, columnAlias, columnTypes);
		} catch (DataAccessException e) {
			logger.error("查询时出错，sql:" + qrySql, e);
			throw new CCICException("SYS01007", e);
		}
	}
	
	/**
	 * 根据某一个属性以及属性值获取对象，缓存查询结果。
	 * @param name 属性名。
	 * @param value 属性值。
	 */
	public List<T> findByCache(String name, Object value){
		try {
			return getBaseDao().findByCache(name, value);
		} catch (DataAccessException e) {
			logger.error("查询时出错", e);
			throw new CCICException("SYS01007", e);
		}
	}
	
	/**
	 * 根据某一个属性以及属性值获取唯一的一个对象，缓存查询结果。
	 * @param name 属性名。
	 * @param value 属性值。
	 */
	public T findUniqueByCache(String name, Object value){
		try {
			return getBaseDao().findUniqueByCache(name, value);
		} catch (DataAccessException e) {
			logger.error("查询时出错", e);
			throw new CCICException("SYS01007", e);
		}
	}
	
	/**
	 * 根据查询过滤条件和排序条件进行查询，查询条件使用默认的取查询参数属性与值相等的比较方法。
	 * @author louxue
	 * @param filterMap 过滤条件，name-value对，name为泛型T对应实体的属性名，value为取值。
	 * @param orderMap 排序条件。
	 * @return 返回查询结果list对象。
	 */
	public List<T> findByCache(Map<String, Object> filterMap, Map<String, Object> orderMap){
		try {
			return getBaseDao().findByCache(filterMap, orderMap);
		} catch (DataAccessException e) {
			logger.error("查询时出错", e);
			throw new CCICException("SYS01007", e);
		}
	}
	
	/**
	 * 根据查询过滤条件和排序条件进行查询，查询条件使用自定义的setUpCriteriaMethod比较方法。
	 * @author louxue
	 * @param filterMap 过滤条件，name-value对，name为泛型T对应实体的属性名，value为取值。
	 * @param orderMap 排序条件。
	 * @param setUpCriteriaMethod  定义在自己DaoImp中的比较方法。
	 * @return 返回查询结果list对象。
	 */
	public List<T> findByCache(Map<String, Object> filterMap, Map<String, Object> orderMap, String setUpCriteriaMethod){
		try {
			return getBaseDao().findByCache(filterMap, orderMap, setUpCriteriaMethod);
		} catch (DataAccessException e) {
			logger.error("查询时出错", e);
			throw new CCICException("SYS01007", e);
		}
	}

}
