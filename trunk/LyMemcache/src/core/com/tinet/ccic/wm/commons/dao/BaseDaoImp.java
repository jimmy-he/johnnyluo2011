package com.tinet.ccic.wm.commons.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.ToListResultTransformer;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.tinet.ccic.wm.commons.BaseStandardEntity;
import com.tinet.ccic.wm.commons.CCICException;
import com.tinet.ccic.wm.commons.Constants;
import com.tinet.ccic.wm.commons.util.ArrayUtil;
import com.tinet.ccic.wm.commons.util.CollectionUtil;
import com.tinet.ccic.wm.commons.util.CriterionUtil;
import com.tinet.ccic.wm.commons.util.DateUtil;
import com.tinet.ccic.wm.commons.util.GenericsUtils;
import com.tinet.ccic.wm.commons.util.HibernateUtils;
import com.tinet.ccic.wm.commons.util.PropertyXmlMgr;
import com.tinet.ccic.wm.commons.util.QuerySelect;
import com.tinet.ccic.wm.commons.util.Select;
import com.tinet.ccic.wm.commons.web.query.CriteriaPage;
import com.tinet.ccic.wm.commons.web.query.HqlPage;
import com.tinet.ccic.wm.commons.web.query.Page;
/**
* 基础数据存取对象实现类
*<p>
* 文件名： BaseDaoImp.java
*<p>
* Copyright (c) 2006-2010 T&I Net Communication CO.,LTD.  All rights reserved.
* @author 娄雪
* @since 1.0
* @version 1.0
* @see com.tinet.ccic.wm.commons.dao.BaseDao
*/
@SuppressWarnings("unchecked")
abstract public class BaseDaoImp<T> extends HibernateDaoSupport 
									implements BaseDao<T> {
	/** 查询记录数模式 */
	public static final int COUNT_MODE = 1;
	/** 分页滚动模式 */
	public static final int SCROLL_MODE = 2;
	/** 全部查询模式 */
	public static final int LIST_MODE = 3;

	/** 字符类型：0|字符串，1|长整形 */
	protected int TYPE_DEFAULT = 0;

	/** 使用默认的criteria */
	protected static final int CRITERIA_USE_DEFAULT = 10;
	/** 使用重载的criteria */
	protected static final int CRITERIA_USE_OVERRIDE = 11;
	
	/** 日志管理器 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/** Dao所管理的Entity类型. */
	protected Class<T> entityClass;
	
	// 类型转换器
	protected ThreadLocal<ResultTransformer> resultType = new ThreadLocal<ResultTransformer>();
	// 由于复杂sql有可能自动生成的查询总条数的sql无法正确查询出数量，所以自己提供查询总条数的sql
	protected ThreadLocal<String>  countSql = new ThreadLocal<String>();
	
	protected String entityName = null;

	/**
	 * 取得entityClass的函数. 不支持泛型的子类可以抛开Class<T>
	 * entityClass,重新实现此函数达到相同效果。
	 */
	protected Class getEntityClass() {
		return entityClass;
	}
	
	/**
	 * 在构造函数中将泛型T.class赋给entityClass
	 */
	public BaseDaoImp() {
		entityClass = (Class<T>) GenericsUtils.getGenericClass(getClass());
	}
	
	/**
	 * 根据主键返回对象 
	 * 
	 * @param id
	 * @throws DataAccessException
	 */
	public T get(Serializable id) {
		T o=null;
        if(getEntityName()!=null){
    	    o = (T) getHibernateTemplate().get(getEntityName(), id);
		}else{
		    o = (T) getHibernateTemplate().get(getEntityClass(), id);
		}
        return o;
	}
	
	/**
	 * 根据主键返回克隆对象
	 * 
	 * @param id
	 * @throws DataAccessException
	 */
	public T getClone(Serializable id) {
		T o = get(id);
		getHibernateTemplate().evict(o);
		return o;
	}
	
	/**
	 * 返回全部的实体
	 * @throws DataAccessException
	 */
	public List<T> getAll() {
		return getHibernateTemplate().loadAll(getEntityClass());
	}
	
	/**
	 * 新建项目的Create操作
	 * 
	 * @param pojo 要被创建的对象
	 * @throw DataAccessException
	 */
	public Serializable create(T pojo) {
		if(getEntityName()==null){
		   getHibernateTemplate().save(pojo);
		}else{
		   getHibernateTemplate().save(getEntityName(),pojo);
		}
		// 保存成功后，将生成的id返回
		if(pojo instanceof BaseStandardEntity) {
			return ((BaseStandardEntity)pojo).getId();
		}
		
		String keyName = getSessionFactory().getClassMetadata(pojo.getClass()).getIdentifierPropertyName();
		Serializable id = null;
		if (keyName != null) {
			try {
				id = (Serializable)PropertyUtils.getProperty(pojo, keyName);
			} catch (Exception e) {
				logger.error("创建实体后，反射获取id时错误", e);
				throw new CCICException("SYS01004", e);
			}
		}
		return id;
	}
	
	public Serializable saveOrUpdate(T o) {
		getHibernateTemplate().saveOrUpdate(o);
		return ((BaseStandardEntity)o).getId();
	}
	
	/**
	 * 项目的Update操作
	 * @throw DataAccessException
	 */
	public void update(T pojo) throws CCICException {
		if(pojo instanceof BaseStandardEntity){
			BaseStandardEntity stdEntity = (BaseStandardEntity)pojo;
		}
		//清除Session对象中的对象
		getHibernateTemplate().clear();
		if(getEntityName()==null){
		    getHibernateTemplate().update(pojo);
		}else{
			getHibernateTemplate().update(getEntityName(),pojo);
		}
		
	}
	

	/**
	 * 批量更新对象
	 * @param 要被修改的对象列表
	 * @throw DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public void batchUpdate(final Collection detachedInstances) {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				HibernateDAOHelper.checkWriteOperationAllowed(getHibernateTemplate(), session);
				if (!CollectionUtil.isEqualCollection(detachedInstances,
						CollectionUtil.EMPTY_COLLECTION)) {
					int max = CollectionUtil.size(detachedInstances);
					int i = 0;
					for (Object t : detachedInstances) {
						session.update(t);
						setOperatorInfo(t, Constants.PERSISTENCE_OPERATION_UPDATE);
						
						if ((i != 0 && i % Constants.DEFAULT_BATCH_SIZE == 0) || i == max - 1) {
							session.flush();
							session.clear();
						}
						i++;
					}
				}
				return null;
			}
		});
	}
	
	private void setOperatorInfo(Object pojo, String optr) {
		if(pojo instanceof BaseStandardEntity){
			BaseStandardEntity stdEntity = (BaseStandardEntity)pojo;
		}
	}

	/**
	 * 批量保存对象
	 * @see com.tinet.ccic.wm.commons.dao.BaseDao#batchSave(java.util.Collection)
	 * @throw DataAccessException
	 */
	public void batchSave(final Collection transientInstances) {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				HibernateDAOHelper.checkWriteOperationAllowed(getHibernateTemplate(), session);
				
				if (!CollectionUtil.isEqualCollection(transientInstances,
						CollectionUtil.EMPTY_COLLECTION)) {
					int max = CollectionUtil.size(transientInstances);
					int i = 0;
					for (Object t : transientInstances) {
						session.save(t);
						setOperatorInfo(t, Constants.PERSISTENCE_OPERATION_CREATE);
						
						if ((i != 0 && i % Constants.DEFAULT_BATCH_SIZE == 0) || i == max - 1) {
							session.flush();
							session.clear();
						}
						i++;
					}
				}
				return null;
			}
		});
	}
	
	/**
	 * 高级update操作，只update更新了的字段，对没有更新或者更新而内容不变的不予执行update操作
	 * 
	 * @param 一组待更新对象列表
	 * @throw DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public void merge(T t) {
		this.getHibernateTemplate().merge(t);
	}

	/**
	 * <tt>save()</tt> 或者 <tt>update()</tt> 所给出的实例。
	 * @param Collection的子类实例
	 * @throw DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public void batchSaveOrUpdate(final Collection instances) {
		
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				HibernateDAOHelper.checkWriteOperationAllowed(getHibernateTemplate(), session);
				if (!CollectionUtil.isEqualCollection(instances, CollectionUtil.EMPTY_COLLECTION)) {
					int max = CollectionUtil.size(instances);
					int i = 0;
					for (Object t : instances) {
						session.saveOrUpdate(t);
						setOperatorInfo(t, Constants.PERSISTENCE_OPERATION_UPDATE);
						if ((i != 0 && i % Constants.DEFAULT_BATCH_SIZE == 0)
								|| i == max - 1) {
							session.flush();
							session.clear();
						}
						i++;
					}
				}
				return null;
			}
		});
	}

	/**
	 * 批量删除对象
	 * 
	 * @param 待删除对象列表
	 * @throw DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public void batchRemove(final Collection persistentInstances) {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				HibernateDAOHelper.checkWriteOperationAllowed(getHibernateTemplate(), session);
				if (!CollectionUtil.isEqualCollection(persistentInstances,
						CollectionUtil.EMPTY_COLLECTION)) {
					int max = CollectionUtil.size(persistentInstances);
					int i = 0;
					for (Object t : persistentInstances) {
						session.refresh(t);
						session.delete(t);
						if ((i != 0 && i % Constants.DEFAULT_BATCH_SIZE == 0) || i == max - 1) {
							session.flush();
							session.clear();
						}
						i++;
					}
				}
				return null;
			}
		});
	}
	
	/**
	 * 删除对象
	 * 
	 * @param 待删除对象
	 * @throw DataAccessException
	 */
	public boolean remove(T pojo){
		getHibernateTemplate().delete(pojo);
		return true;
	}

	/**
	 * 使用Criteria实现进行分页查询
	 * 
	 * @param filterMap 过滤条件.
	 * @param orderMap 排序条件.
	 * @param start 当前查询起始记录
	 * @param pageSize 每页显示记录数.
	 * @param setUpCriteriaMethodName 将Map中条件转换为criteria的函数名,支持子类对Map中的条件有多种处理方法.
	 */
	public Page<T> findBy(Map filterMap, Map orderMap, int start, int pageSize, 
						  String setUpCriteriaMethodName) {
		Session session = getSession();
		session.setFlushMode(FlushMode.MANUAL);
		
		Criteria criteria = session.createCriteria(getEntityClass());
		
		T entity = null;
		
		try {
			entity = (T)this.entityClass.newInstance();
			if (!CollectionUtils.isEmpty(filterMap)) {
				MethodUtils.invokeMethod(this, setUpCriteriaMethodName, new Object[]{criteria, filterMap});
			}
		} catch (Exception e) {
			throw new CCICException("SYS01007", e);
		}

		sortCriteria(criteria, orderMap, entity);
		criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		return pagedQuery(criteria, start, pageSize);
	}
	
	
	
	
	
	//findByCriteria
	public Page<T> findByCriteria(QuerySelect querySelect, Map orderMap, int start, int pageSize) {
		Session session = getSession();
		session.setFlushMode(FlushMode.MANUAL);
		
		
		Criteria criteria=session.createCriteria(getEntityClass());
		T entity = null;
		
		try {
		  entity = (T)this.entityClass.newInstance();		  
		  //设置查询条件
		  query(criteria,querySelect);

		} catch (Exception e) {
		throw new CCICException("SYS01007", e);
		}
		
		sortCriteria(criteria, orderMap, entity);
		criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		return pagedQuery(criteria, start, pageSize);
	}
	
	private void query(Criteria criteria,QuerySelect querySelect) {
		if(querySelect!=null)
		for(Select select:querySelect.getSelect()){
			if(select.getSelectType().equals("eq")){
				criteria.add(Restrictions.eq(select.getPropertyName(), select.getObj()));
			}else if(select.getSelectType().equals("like")){
				criteria.add(Restrictions.like(select.getPropertyName(), select.getStr()));
			}else if(select.getSelectType().equals("le")&&select.getLo()!=null){
				criteria.add(Restrictions.le(select.getPropertyName(), select.getLo()));
			}else if(select.getSelectType().equals("ge")&&select.getGo()!=null){
				criteria.add(Restrictions.ge(select.getPropertyName(), select.getGo()));
			}			
		}
		
	}

	/**
	 * 查询
	 * 
	 * @param filterMap 过滤条件 
	 * @param ruleMap 过滤条件及条件规则
	 * @param orderMap 排序条件
	 * @param start 当前查询起始记录
	 * @param pageSize 每页显示记录数.
	 * @return Page
	 */
	public Page<T> findBy(Map filterMap, Map ruleMap, Map orderMap, int start, int pageSize, boolean isPaged) {
		Session session = getSession();
		session.setFlushMode(FlushMode.MANUAL);
		
		Map<String, Criteria> subCriterias = new HashMap<String, Criteria>(4);
		Criteria criteria = session.createCriteria(getEntityClass());
		
		try {
			for (Iterator<Map.Entry> it = filterMap.entrySet().iterator(); it.hasNext(); ) {
				Map.Entry entry = it.next();
				String key = (String)entry.getKey();
				List values = (List)entry.getValue();
				List rules = (List)ruleMap.get(key);
				
				// 说明是多条件
				int size = ((List)values).size();
				for (int i = 0; i < size; i++) {
					Object subValue = ((List)values).get(i);
					String subRule = (String)((List)rules).get(i);
					CriterionUtil.appendCriteria(criteria, subCriterias, subRule, key, subValue);
				}
			}
			
			// 加上排序
			CriterionUtil.addCriteriaOrder(criteria, subCriterias, orderMap);
			
			trimCriteria(criteria, subCriterias);
			
		} catch (Exception e) {
			logger.error("查询出错", e);
			throw new CCICException("", e);
		}

		criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		if (isPaged) {
			return pagedQuery(criteria, start, pageSize);
		} else {
			return new Page(criteria.list());
		}
	}
	
	/**
	 * 修整构建的Criteria <br />
	 * 一般使用CriterionUtil.appendCriteria(criteria, subCriterias, rule, key, subValue)，就可以将
	 * 查询规则rule, key, value 添加到整个查询条件中。
	 * 
	 * @param criteria 主Criteria
	 * @param subCriterias Map，由属性指向的关联查询Criteria
	 */
	protected void trimCriteria(Criteria criteria, Map subCriterias) {
		// 默认实现，doNothing
	}
	

	/**
	 * 使用Criteria实现进行分页查询
	 * 
	 * @param filterMap 过滤条件.
	 * @param orderMap 排序条件.
	 * @param start 当前查询起始记录
	 * @param pageSize 每页显示记录数.
	 */
	public Page<T> findBy(Map filterMap, Map orderMap, int start, int pageSize) {
		return findBy(filterMap, orderMap, start, pageSize, "setUpCriteria");
	}

	/**
	 * 使用Criteria实现进行分页查询
	 * 
	 * @param filterMap 过滤条件.
	 * @param start 当前查询起始记录
	 * @param pageSize 每页显示记录数.
	 */
	public Page findBy(Map filterMap, int start, int pageSize) {
		return findBy(filterMap, null, start, pageSize);
	}
	
	/**
	 * 使用HQL实现分页查询
	 * 
	 * @param hql HQL串
	 * @param start 当前查询起始记录
	 * @param pageSize 每页显示记录数.
	 */
	public Page findByHql(String qryHql, int start, int pageSize) {
		return pagedQuery(qryHql, start, pageSize, false);
	}
	
	/**
	 * 使用HQL实现分页查询(带有参数)
	 * 
	 * @param hql HQL串
	 * @param args 参数值数组
	 * @param types 参数值类型数组
	 * @param start 当前查询起始记录
	 * @param pageSize 每页显示记录数.
	 */
	public Page findByHql(String qryHql, Object[] args, Type[] types,
			int start, int pageSize) {
		return pagedQuery(qryHql, args, types, start, pageSize, false);
	}
	/**
	 * 使用HQL实现查询(带有参数)
	 * 
	 * @param hql HQL串
	 * @param args 参数值数组
	 * @param types 参数值类型数组
	 */
	public List findByHql(String qryHql, Object[] args, Type[] types) {
		return pagedQuery(qryHql, args, types, false, false);
	}

	/**
	 * 使用HQL实现查询
	 * 
	 * @param hql HQL串
	 */
	public List findByHql(String hql) {
		
		return getHibernateTemplate().find(hql);//回滚至通用版本
	}

	/**
	 * 使用SQL实现分页查询
	 * 
	 * @param sql SQL串
	 * @param start 当前查询起始记录
	 * @param pageSize 每页显示记录数.
	 */
	public Page findBySql(String sql, int start, int pageSize) {
		return pagedQuery(sql, start, pageSize, true);
	}
	/**
	 * 使用SQL实现分页查询(带有参数)
	 * 
	 * @param sql SQL串
	 * @param args 参数值数组
	 * @param types 参数值类型数组
	 * @param start 当前查询起始记录
	 * @param pageSize 每页显示记录数.
	 */
	public Page findBySql(String sql, Object[] args, Type[] types, int start, int pageSize) {
		return pagedQuery(sql, args, types, start, pageSize, true);
	}
	/**
	 * 使用SQL实现查询(带有参数)
	 * 
	 * @param sql SQL串
	 * @param args 参数值数组
	 * @param types 参数值类型数组
	 */
	public List findBySql(String qrySql, Object[] args, Type[] types) {
		return pagedQuery(qrySql, args, types, true, false);
	}
	
	public String findTextBySql(String qrySql) {
		List list = this.findBySql(qrySql);
		
		if (list.isEmpty()) {
			return null;
		} else {
			return list.get(0).toString();
		}
	}

	/**
	 * 使用SQL实现查询
	 * 
	 * @param sql SQL串
	 */
	public List findBySql(String sql) throws CCICException {
		Session session = null;
		FlushMode fm = null;
		try {
			session = getSession();
			fm = session.getFlushMode();
			session.setFlushMode(FlushMode.NEVER);

			Query query = session.createSQLQuery(sql);
			List list = null;
			if (getResultType()!=null) {
				list = query.setResultTransformer(getResultType()).list();
			} else {
			    list = query.list();
			}
			return list;
		} finally {
			this.setResultType(null);
			if (fm != null) {
				// 恢复之前的刷新模式。
				session.setFlushMode(fm);
			}
			closeSession(session);
		}
	}

	/**
	 * 使用命名查询技术实现查询<br />
	 * 这种需要在*.hbm.xml文件中定义&lt;query&gt;元素来制定命名查询
	 * 
	 * @param queryName 在*.hbm.xml文件中定义的命名查询
	 */
	public List findBySqlName(String queryName) {
		List list = null;
		Session session = null;
		try {
			session = getSession();
			session.setFlushMode(FlushMode.NEVER);

			Query query = session.getNamedQuery(queryName);
			list = query.list();
		} finally {
			session.setFlushMode(FlushMode.AUTO);
			closeSession(session);
		}
		return list;
	}

	/**
	 * 使用命名查询技术实现查询<br />
	 * 这种需要在*.hbm.xml文件中定义&lt;query&gt;元素来制定命名查询
	 * 
	 * @param queryName 在*.hbm.xml文件中定义的命名查询
	 * @param args 参数值数组
	 * @param types 参数类型数组
	 */
	public List findBySqlName(String queryName, Object[] args, Type[] types) {
		Session session = null;
		Assert.notNull(queryName);
		
		if (args != null && types != null && args.length != types.length) {
			throw new IllegalArgumentException("The size of object[]  must equals with Type[]'s.");
		}
		
		try {
			session = getSession();
			session.setFlushMode(FlushMode.NEVER);

			Query query = null;
			if (args == null) {
				query = session.getNamedQuery(queryName);
			} else {
				query = session.getNamedQuery(queryName).setParameters(args, types);
			}
			List list = null;
			if(getResultType()!=null){
				list = query.setResultTransformer(getResultType()).list();
			}else{
			    list = query.list();
			}
			return list;
		} finally {
			this.setResultType(null);
			session.setFlushMode(FlushMode.AUTO);
			closeSession(session);
		}
	}
	
	/**
	 * 分页查询函数，使用hql.
	 * 
	 * @param hql HQL串
	 * @param start 页号,从0开始.
	 * @param pageSize 每页显示记录数.
	 * @param values 参数值
	 */
	public Page findByString(String hql, int start, int pageSize, Object... values) {
		Session session = null;
		Assert.hasText(hql);
		
		try {
			// Count查询
			String countQueryString = " select count (*) " + HibernateUtils.removeSelect(HibernateUtils.removeOrders(hql));
			List countlist = getHibernateTemplate().find(countQueryString, values);
			int totalCount = (Integer)countlist.get(0);
			if (totalCount < 1) {
				return new Page(0, 0, pageSize, null);
			}
			
			// 实际查询返回分页对象
			session = getSession(); 
			session.setFlushMode(FlushMode.NEVER);

			Query query = session.createQuery(hql);
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
			
			List list = query.setFirstResult(start).setMaxResults(pageSize).list();
			return new Page(start, totalCount, pageSize, list);
		} finally {
			session.setFlushMode(FlushMode.AUTO);
			closeSession(session);
		}
	}
	
	/**
	 * 分页查询函数，使用hql或者sql.
	 * 
	 * @param qryString 查询串
	 * @param start 页号,从0开始.
	 * @param pageSize 每页显示记录数.
	 * @param sqlFlag 查询串的标志 true-sql; false-hql.
	 */
	protected Page pagedQuery(String qryString, int start, int pageSize, boolean sqlFlag) {
		return pagedQuery(qryString, start, pageSize, sqlFlag, false);
	}

	/**
	 * 分页查询，使用hql或者sql.
	 * 
	 * @param qryString 查询串
	 * @param start 页号,从0开始.
	 * @param pageSize 每页显示记录数.
	 * @param sqlFlag 查询串的标志 true-sql; false-hql.
	 * @param isScroll ?
	 */
	protected Page pagedQuery(String qryString, int start, int pageSize,
			boolean sqlFlag, boolean isScroll) {
		return pagedQuery(qryString, null, null, start, pageSize, sqlFlag, isScroll, false);
	}
	

	/**
	 * 分页查询，使用hql或者sql.
	 * 
	 * @param qryString 查询串
	 * @param args 查询sql/hql中参数取值.
	 * @param pageSize 查询sql/hql中参数取值类型.
	 * @param sqlFlag 查询串的标志 true-sql; false-hql.
	 * @param isCache 查询结果是否缓存.
	 */
	protected List pagedQuery(String qryString, Object[] args, Type[] types, boolean sqlFlag, boolean isCache) {
		Session session = null;
		
		Assert.hasText(qryString);
		if (args != null && types != null && args.length != types.length) {
			throw new IllegalArgumentException("The size of object[]  must equals with Type[]'s.");
		}
		
		try { 
			Query query = null;
			session = getSession();
			session.setFlushMode(FlushMode.NEVER);
			
			if (!sqlFlag) {
				if (args == null) {
					query = session.createQuery(qryString);
				} else {
					query = session.createQuery(qryString).setParameters(args, types);
				}
			} else {
				if (args == null) {
					query = session.createSQLQuery(qryString);//.addScalar(columnAlias, type);
				} else {
					query = session.createSQLQuery(qryString).setParameters(args, types);
				}
			}
			if (isCache) {
				query.setCacheable(true);
			}
			List list = null;
			if(getResultType()!=null){
				list = query.setResultTransformer(getResultType()).list();
			}else{
			    list = query.list();
			}
			return list;
		} finally {
			this.setResultType(null);
			session.setFlushMode(FlushMode.AUTO);
			closeSession(session);
		}

	}

	/**
	 * 分页查询，使用hql或者sql.
	 * 
	 * @param qryString 查询串
	 * @param start 页号,从0开始.
	 * @param pageSize 每页显示记录数.
	 * @param sqlFlag 查询串的标志 true-sql; false-hql.
	 */
	protected Page pagedQuery(String qryString, Object[] args, Type[] types,
			int start, int pageSize, boolean sqlFlag) {
		return pagedQuery(qryString, args, types, start, pageSize, sqlFlag, false, false);
	}

	/**
	 * 分页查询，使用hql或者sql.
	 * 
	 * @param qryString 查询串
	 * @param start 页号,从0开始.
	 * @param pageSize 每页显示记录数.
	 * @param sqlFlag 查询串的标志 true-sql; false-hql.
	 * @param isScroll
	 */
	protected Page pagedQuery(String qryString, Object[] args, Type[] types,
			int start, int pageSize, boolean sqlFlag, boolean isScroll, boolean isCache) {
		Session session = null; 
		Assert.hasText(qryString);
		if (args != null && types != null && args.length != types.length) {
			throw new IllegalArgumentException("The size of object[]  must equals with Type[]'s.");
		}
		
		logger.debug("sql/hql:{}", qryString);
		
		try {
			Query query = null;
			session = getSession();
			session.setFlushMode(FlushMode.NEVER);
			
			if (!sqlFlag) {
				// HQL串查询
				if (isScroll) {
					return HqlPage.getPageInstance(query, null,start, pageSize, SCROLL_MODE);
				} else {
					int totalCount=0;
					
					String countHql = this.getCountSql();
					if (countHql == null) {
						if (args == null) {
							totalCount = HibernateUtils.getTotalCount(getHibernateTemplate(),qryString,false,null,null, true);
						}else{
							totalCount = HibernateUtils.getTotalCount(getHibernateTemplate(),qryString,false,args,types, true);
						}
					} else {
						if (args == null) {
							totalCount = HibernateUtils.getTotalCount(getHibernateTemplate(),countHql,false,null,null, false);
						}else{
							totalCount = HibernateUtils.getTotalCount(getHibernateTemplate(),countHql,false,args,types, false);
						}
					}
					
					if(totalCount>0){
						if (args == null) {
							query = session.createQuery(qryString); 
						} else {
							query = session.createQuery(qryString).setParameters(args, types);
						}
						if (isCache) {
							query.setCacheable(true);
						}
						return HqlPage.getPageInstance(query, getResultType(),start, pageSize, COUNT_MODE,totalCount);

					} else {
						return new Page(0, 0, pageSize, null);
					}
				}
			} else {
				// SQL串查询
				// qryString = StringUtils.upperCase(qryString);
				if (isScroll) {
					return HqlPage.getPageInstance(query, getResultType(),start, pageSize, SCROLL_MODE);
				} else {
					int totalCount=0;
					String countSql = this.getCountSql();
					if (countSql == null) {
						if (args == null){
							totalCount = HibernateUtils.getTotalCountSql(session,qryString,null,null, true);	
						}else{
							totalCount = HibernateUtils.getTotalCountSql(session,qryString,ArrayUtil.toString(args),types, true);
						}
					} else {
						if (args == null){
							totalCount = HibernateUtils.getTotalCountSql(session,countSql,null,null, false);	
						}else{
							totalCount = HibernateUtils.getTotalCountSql(session,countSql,ArrayUtil.toString(args),types, false);
						}
					}

					if(totalCount>0){
						if (args == null) {
							query = session.createSQLQuery(qryString);
						} else {
							query = session.createSQLQuery(qryString).setParameters(args, types);
						}
						if (isCache) {
							query.setCacheable(true);
						}
						return HqlPage.getPageInstance(query, getResultType(),start, pageSize, COUNT_MODE,totalCount);
					}else{
						return new Page(0, 0, pageSize, null);
					}
				}
			}
		} finally {
			this.setResultType(null);
			session.setFlushMode(FlushMode.AUTO);
			closeSession(session);
		}
	}

	/**
	 * 使用HQL进行查询(带参数)
	 * 
	 * @param hql HQL串
	 * @param values 参数
	 */
	public List findByString(String hql, Object... values) {
		Assert.hasText(hql);
		return getHibernateTemplate().find(hql, values);
	}

	/**
	 * 使用Criteria进行查询
	 * 
	 * @param criteria 
	 * @param start 当前查询起始记录 从0计数
	 * @param pageSize 每页显示的记录数
	 */
	protected Page pagedQuery(Criteria criteria, int start, int pageSize) {
		return pagedQuery(criteria, start, pageSize, COUNT_MODE);
	}
	
	/**
	 * 使用Criteria进行查询
	 * 
	 * @param criteria 
	 * @param start 当前查询起始记录 从0计数
	 * @param pageSize 每页显示的记录数
	 * @param mode 查询模式
	 */
	protected Page pagedQuery(Criteria criteria, int start, int pageSize, int mode) {
		Page page = CriteriaPage.getPageInstance(criteria, start, pageSize, mode);
		return page;
	}

	/**
	 * 为criteria设置排序
	 * 
	 * @param criteria Criteria实例.
	 * @param sortMap 排序条件.
	 * @param entity entity对象,用于使用反射来获取某些属性信息
	 */
	protected void sortCriteria(Criteria criteria, Map sortMap, Object entity) {	
		if (sortMap == null || sortMap.isEmpty()) {
			return ;
		}
		
		for (Object o : sortMap.keySet()) {
			String fieldName = o.toString();
			String orderType = sortMap.get(fieldName).toString();
			if (orderType == null || orderType.length() < 1) {
				continue;
			}
			if (fieldName.indexOf('_') != -1) {
				fieldName = fieldName.replaceAll("_", ".");
			
				String alias = StringUtils.substringBefore(fieldName, ".");
				String aliasType = alias;
				
				criteria.createAlias(aliasType, alias);
			}

			if (Constants.ORDER_ASCEND.equalsIgnoreCase(orderType)) {
				criteria.addOrder(Order.asc(fieldName));
			} else {
				criteria.addOrder(Order.desc(fieldName));
			}
		} 
	}

	/**
	 * 根据某一个属性以及属性值获取唯一的一个对象
	 * 
	 * @param name 属性名
	 * @param value 属性值
	 */
	public T findUniqueBy(String name, Object value) {
		Session session = null;
		Assert.hasText(name);
		try {
			session = getSession();
			session.setFlushMode(FlushMode.NEVER);
			
			Criteria criteria = session.createCriteria(getEntityClass());
			criteria.add(Restrictions.eq(name, value));
			T t = (T) criteria.uniqueResult();
			return t;
		} finally {
			session.setFlushMode(FlushMode.AUTO);
			closeSession(session);
		}
	}

	/**
	 * 根据某一个属性以及属性值获取对象
	 * 
	 * @param name 属性名
	 * @param value 属性值
	 */
	public List<T> findBy(String name, Object value) {
		Session session = null;
		try {
			Assert.hasText(name);
			session = getSession();
			session.setFlushMode(FlushMode.NEVER);
			
			Criteria criteria = session.createCriteria(getEntityClass()).setCacheable(true) ;
			criteria.add(Restrictions.eq(name, value));
			List<T> list = criteria.list();
			return list;
		} finally {
			session.setFlushMode(FlushMode.AUTO);
			closeSession(session);
		}
	}

	/**
	 * 根据某一个属性以及属性值以like方式获取对象
	 * 
	 * @param name 属性名
	 * @param value 模糊匹配的属性值
	 */
	public List<T> findByLike(String name, String value) {
		Session session = null;
		try {
			Assert.hasText(name);
			session = getSession();
			session.setFlushMode(FlushMode.NEVER);
			
			Criteria criteria = session.createCriteria(getEntityClass());
			criteria.add(Restrictions.like(name, value, MatchMode.ANYWHERE));

			List<T> list = criteria.list();
			return list;
		} finally {
			session.setFlushMode(FlushMode.AUTO);
			closeSession(session);
		}
	}

	public List<T> findBy(Map<String, Object> filter) {
		return this.findBy(filter, DEFAULT_SETUPCRITIRIA_METHOD);
	}
	
	public List<T> findBy(Map<String, Object> filter, String setUpCriteriaMethod) {
		return findBy(filter, null,setUpCriteriaMethod, false);
	}

	/**
	 * 根绝查询条件及排序条件查询。
	 * @author louxue
	 * @param filterMap 查询条件。
	 * @param setUpCriteriaMethod 查询条件name与value之间比较关系的方法，写在自己的DaoImp中，该方法的参数为Criteria criteria和Map filterMap。
	 * @param isCache 是否缓存查询结果。
	 * @return
	 */
	protected List<T> findBy(Map<String, Object> filterMap, Map<String, Object> orderMap, String setUpCriteriaMethod, boolean isCache){
		Session session = null;
		try {
			session = getSession();
			session.setFlushMode(FlushMode.NEVER);
			
			Criteria criteria = session.createCriteria(getEntityClass());
			
			if (!CollectionUtils.isEmpty(filterMap)) {
				try {
					MethodUtils.invokeMethod(this, setUpCriteriaMethod, new Object[]{criteria, filterMap});
				} catch (Exception e) {
					e.printStackTrace();
					throw new IllegalArgumentException("Function " + setUpCriteriaMethod + " not found.");
				}
			}
			if (isCache) {
				criteria.setCacheable(true);
			}
			T entity = null;
			try {
				entity = (T)this.entityClass.newInstance();		  
			} catch (Exception e) {
				throw new CCICException("SYS01007", e);
			}
			if (!CollectionUtils.isEmpty(filterMap)) {
				sortCriteria(criteria, orderMap, entity);
			}
			List<T> list = criteria.list();
			return list; 
		} finally {
			session.setFlushMode(FlushMode.AUTO);
			closeSession(session);
		}
	}
	
	/**
	 * 根据主键id查询制定对象的实体 
	 *
	 * @param pojoClass 制定Class
	 * @param id 主键id
	 */
	public Object findUniqueBy(Class pojoClass, Serializable id) {
		return getHibernateTemplate().get(pojoClass, id);
	}

	/**
	 * 使用HQL查询出一个实体。
	 * 
	 * @param hql HQL串
	 */
	public Object findUniqueByHql(String hql) {
		List list = getHibernateTemplate().find(hql);
		if (list != null && list.size() != 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 不建议使用此方法
	 */
	protected Criteria getEntityCriteria() {
		Session session = getSession();
		return session.createCriteria(getEntityClass());
	}

	/**
	 * 判断对象某列的值在数据库中不存在重复
	 * 
	 * @param names 在POJO里相对应的属性名,列组合时以逗号分割<br>
	 *            如"name,loginid,password"
	 * @return true 
	 */
	public boolean checkUnique(Object entity, String names) {
		Assert.hasText(names);
		boolean notUnique = true;
		Session session = null;
		
		try {
			session = getSession();
			session.setFlushMode(FlushMode.NEVER);
			
			Criteria criteria = session.createCriteria(entity.getClass())
					.setProjection(Projections.rowCount());
			String[] nameList = names.split(",");
			for (String name : nameList) {
				criteria.add(Restrictions.eq(name, PropertyUtils.getProperty(entity, name)));
			}
			String keyName = getSessionFactory().getClassMetadata(entity.getClass()).getIdentifierPropertyName();
			if (keyName != null) {
				Object id = PropertyUtils.getProperty(entity, keyName);
				if (id != null)
					criteria.add(Restrictions.not(Restrictions.eq(keyName, id)));
			}

			notUnique = ((Integer) criteria.uniqueResult()) > 0;
		} catch (Exception e) {
			logger.error("通过{}确定{}唯一性时出错", names, entity.toString());
			throw new CCICException("SYS00000");
		} finally {
			session.setFlushMode(FlushMode.AUTO);
			closeSession(session);
		}

		return notUnique;
	}

	public int executeHQL(String hql, Object... values) {
		Session session = this.getSession();
		session.flush();
		Query query = session.createQuery(hql);
		try {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
			return query.executeUpdate();
		} finally {
			this.closeSession(session);
		}
	}

	/**
	 * 创建Criteria对象
	 * 
	 * @param entityClass  
	 * @param Criterion 可变条件列表,Restrictions生成的条件
	 */
	public <T> Criteria getCriteria(Class<T> entityClass, Criterion... criterion) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(entityClass);
		for (Criterion c : criterion) {
			criteria.add(c);
		}
		return criteria;
	}

	/**
	 * 获取系统时间
	*/
	public Date getSystemDate(){
		Session session = null;
		FlushMode fm = null;
		
		try{
			String sql = PropertyXmlMgr.getPropertyToString("SYSTEM","sql_getSystemDate");
			session = getSession();
			fm = session.getFlushMode();
			session.setFlushMode(FlushMode.NEVER);

			Query query = session.createSQLQuery(sql);
			List list = query.list();
			Object object = list.get(0);
			
			return DateUtil.convertStringToDate("yyyy-MM-dd hh:mm:ss",object.toString());
		}catch (Exception e){
			logger.error("获取系统时间出错", e);
		} finally {
			if (fm != null) {
				// 恢复之前的刷新模式。
				session.setFlushMode(fm);
			}
			closeSession(session);
		}
		
		return null;
	}
	
	/**
	 * 根据sql和参数获取Hibernate Query
	 * 
	 * @sql 查询的SQL
	 * @values 可变参数，对应sql所需参数
	 */
	public int executeSQL(String sql, Object... values) {
		Session session = this.getSession();
		session.flush();
		
		Query query = session.createSQLQuery(sql);
		try {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
			return query.executeUpdate();
		} finally {
			this.closeSession(session);
		}
	}

	/**
	 * 关闭session
	 */
	public void closeSession(Session session) {
		if (session == null) {
			/* 在事务内才可以这样用false */
			session = getSession(false);
		}
		if (!SessionFactoryUtils.isSessionTransactional(session, this
				.getSessionFactory())) {
			releaseSession(session);
			session = null;
		}
	}
	/**
	 * 将一个持久状态的对象从Hibernate缓存中清除， 那么接下来对该实体的修改不会持久化到数据中. 
	 * 如果设置了 <tt>cascade="evict"</tt>，那么它所关联的对象也会如此。
	 * 
	 * @param 持久化对象
	 */
	public void evict(Object persistentInstance) {
		getHibernateTemplate().evict(persistentInstance);
	}

	/**
	 * 刷新Hibernate <tt>Session</tt>。 这个操作必须处于完成的操作单元中。 
	 * 提交事务和关闭session会调用这个方法. 
	 * 刷新缓存是将缓存中的对象同步到数据库的一个操作。
	 */
	public void flush() {
		getHibernateTemplate().flush();
	}

	/**
	 * 清除session. 游离所有已经加载的实例，取消所有之前对持久化实体进行的修改，删除等操作. 
	 * Do not close open iterators or instances of <tt>ScrollableResults</tt>.
	 */
	public void clear() {
		getHibernateTemplate().clear();
	}
	/**
	 * Return the instance of HibernateDAOHelper.
	 * 
	 * @return the instance of HibernateDAOHelper
	 */
	public HibernateDAOHelper getHelper() {
		return new HibernateDAOHelper(getSessionFactory());
	}

	/**
	 * Return the ORM provider.
	 * 
	 * @return the ORM provider
	 */
	public Object getORMProvider() {
		return getHibernateTemplate();
	}

	/**
	 * 扩展不需要加入查询的特殊对象
	 * 
	 * @param value
	 * @return
	 */
	protected boolean isCriteria(Object value) {
		return true;
	}

	protected ResultTransformer getResultType() {
		return (ResultTransformer)resultType.get();
	}

	protected void setResultType(ResultTransformer resultType) {
		this.resultType.set(resultType);
	}
	
	public String getCountSql() {
		return countSql.get();
	}

	public void setCountSql(String countSql) {
		this.countSql.set(countSql);
	}

	protected String getEntityName() {
		return entityName;
	}

	protected void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	
	public void setUpCriteria(Criteria criteria, Map filterMap) {
		for (Object key : filterMap.keySet()) {
			criteria.add(Restrictions.eq(key.toString(), filterMap.get(key)));
		}
	}
	
		
	/**
	 * 使用hql查询，缓存查询结果。
	 * @author louxue
	 * @param hql hql语句。
	 * @return 返回Object数组的list。
	 */
	public List findByHqlCache(String hql){
			HibernateTemplate hibernateTemplate = getHibernateTemplate();
			hibernateTemplate.setCacheQueries(true);
			return hibernateTemplate.find(hql);		
	}
	
	/**
	 * 使用sql查询，并缓存查询结果。
	 * @author louxue
	 * @param qryString 查询的sql语句。
	 * @param args 查询sql中参数的取值。
	 * @param types 查询sql中参数取值的类型。
	 * @param columnAlias 查询列的别名。
	 * @param columnTypes 查询列的数据类型。
	 * @return 返回查询结果列表。
	 * 使用举例：
	 * 查询sql="select a, count(*) as b from test";
	 * 方法调用：querySqlCache(sql, null, null, new String[]{"a","b"}, new Type[]{Hibernate.STRING, Hibernate.INTEGER})
	 */
	protected List querySqlCache(String qryString, Object[] args, Type[] types, String[] columnAlias, Type[] columnTypes) {
		Session session = null;
		
		Assert.hasText(qryString);
		if (args != null && types != null && args.length != types.length) {
			throw new IllegalArgumentException("The size  object[] args must equals with Type[]'s.");
		}
		if (columnAlias==null ||columnAlias.length==0 || columnTypes==null || columnTypes.length==0) {
			throw new IllegalArgumentException("The String[] columnAlias and Type[] rsTypes are all not allowed null or zero length.");
		}
		if (columnAlias.length != columnTypes.length) {
			throw new IllegalArgumentException("The size of String[] columnAlias must equals with Type[] rsTypes's.");
		}
		try { 
			Query query = null;
			session = getSession();
			session.setFlushMode(FlushMode.NEVER);
			SQLQuery sqlQuery = null;
			sqlQuery = session.createSQLQuery(qryString);
			
			for (int i = 0; i < columnAlias.length; i++) {
				sqlQuery.addScalar(columnAlias[i], columnTypes[i]);
			}
			query = sqlQuery;
			if (args != null) {
				query.setParameters(args, types);
			}
			query.setCacheable(true);
			List list = null;
			if(getResultType()!=null){
				list = query.setResultTransformer(getResultType()).list();
			}else{
			    list = query.list();
			}
			return list;
		} finally {
			this.setResultType(null);
			session.setFlushMode(FlushMode.AUTO);
			closeSession(session);
		}
	}
	
	/**
	 * 使用动态赋值的hql查询
	 * @author louxue
	 * @param qryHql 查询的hql语句。
	 * @param args hql动态赋值参数取值数组。
	 * @param types hql动态赋值参数类型数组。
	 * @return 返回查询结果list。
	 */
	public List findByHqlCache(String qryHql, Object[] args, Type[] types){
		return pagedQuery(qryHql, args, types, false, true);
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
		return querySqlCache(qrySql, args, types, columnAlias, columnTypes);
	}
	
	/**
	 * 根据某一个属性以及属性值获取对象，缓存查询结果。
	 * @param name 属性名。
	 * @param value 属性值。
	 */
	public List<T> findByCache(String name, Object value) {
		Session session = null;
		try {
			Assert.hasText(name);
			session = getSession();
			session.setFlushMode(FlushMode.NEVER);
			
			Criteria criteria = session.createCriteria(getEntityClass()).setCacheable(true) ;
			criteria.add(Restrictions.eq(name, value));
			criteria.setCacheable(true);
			List<T> list = criteria.list();
			return list;
		} finally {
			session.setFlushMode(FlushMode.AUTO);
			closeSession(session);
		}
	}
	
	/**
	 * 根据某一个属性以及属性值获取唯一的一个对象，缓存查询结果。
	 * @param name 属性名。
	 * @param value 属性值。
	 */
	public T findUniqueByCache(String name, Object value) {
		Session session = null;
		Assert.hasText(name);
		try {
			session = getSession();
			session.setFlushMode(FlushMode.NEVER);
			
			Criteria criteria = session.createCriteria(getEntityClass());
			criteria.add(Restrictions.eq(name, value));
			criteria.setCacheable(true);
			T t = (T) criteria.uniqueResult();
			return t;
		} finally {
			session.setFlushMode(FlushMode.AUTO);
			closeSession(session);
		}
	}
	
	/**
	 * 根据查询过滤条件和排序条件进行查询，查询条件使用默认的取查询参数属性与值相等的比较方法。
	 * @author louxue
	 * @param filterMap 过滤条件，name-value对，name为泛型T对应实体的属性名，value为取值。
	 * @param orderMap 排序条件。
	 * @return 返回查询结果list对象。
	 */
	public List<T> findByCache(Map<String, Object> filterMap, Map<String, Object> orderMap) {
		return findBy(filterMap, orderMap , DEFAULT_SETUPCRITIRIA_METHOD, true);
	}
	
	/**
	 * 根据查询过滤条件和排序条件进行查询，查询条件使用自定义的setUpCriteriaMethod比较方法，
	 * 写在自己的DaoImp中，该方法的参数为Criteria criteria和Map filterMap。
	 * @author louxue
	 * @param filterMap 过滤条件，name-value对，name为泛型T对应实体的属性名，value为取值。
	 * @param orderMap 排序条件。
	 * @param setUpCriteriaMethod  定义在自己DaoImp中的比较方法。
	 * @return 返回查询结果list对象。
	 */
	public List<T> findByCache(Map<String, Object> filterMap, Map<String, Object> orderMap, String setUpCriteriaMethod) {
		return findBy(filterMap, orderMap , setUpCriteriaMethod, true);
	}
}