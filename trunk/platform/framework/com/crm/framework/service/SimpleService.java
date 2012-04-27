package com.crm.framework.service;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.crm.framework.common.exception.SystemException;
import com.crm.framework.common.util.BeanUtil;
import com.crm.framework.common.util.StringUtil;
import com.crm.framework.dao.annotation.QueryOnly;
import com.crm.framework.dao.selector.PageRecord;
import com.crm.framework.dao.selector.PageableSelector;
import com.crm.framework.dao.selector.Query;
import com.crm.framework.dao.selector.Selector;
import com.crm.framework.dao.sql.SimpleSql;
import com.crm.framework.dao.transaction.SessionMethod;
import com.crm.framework.model.MappingCache;

/**
 * 
 * @author 王永明
 * @since Apr 5, 2010 11:53:00 PM
 */
public class SimpleService extends CrmBaseService {
	private static Logger logger = Logger.getLogger(SimpleService.class);

	/** 插入一条数据 */
	public void insert(Object obj) {
		this.getDao().insert(obj);
	}

	/** 分页查找一个数据 */
	@QueryOnly
	public PageRecord queryPageBySample(Object obj) {
		Query query = Query.forClass(obj.getClass());
		query.addSample(obj);
		return this.getDao().queryPage(query, 1, this.getDefaultPageNo());
	}

	/** 分页查找一个数据 */
	@QueryOnly
	public PageRecord queryPageBySample(Object obj, int first, int max) {
		Query query = Query.forClass(obj.getClass());
		query.addSample(obj);
		return this.getDao().queryPage(query, first, max);
	}

	/** 分页查找一个数据 */
	@QueryOnly
	public PageRecord queryPageBySample(Object obj, String orderField, String orderType) {
		Query query = Query.forClass(obj.getClass());
		query.addSample(obj);
		if (StringUtil.isNotNull(orderField)) {
			if ("desc".equals(orderType)) {
				query.orderDesc(orderField);
			} else {
				query.orderAsc(orderField);
			}
		}

		PageRecord pageRecord = this.getDao().queryPage(query, 1, this.getDefaultPageNo());
		pageRecord.setOrderField(orderField);
		pageRecord.setOrderType(orderType);
		return pageRecord;
	}

	/** 分页查找一个数据 */
	@QueryOnly
	public PageRecord queryPage(Query query) {
		return this.getDao().queryPage(query, 1, this.getDefaultPageNo());
	}

	/** 分页查找一个数据 */
	@QueryOnly
	public PageRecord queryPage(Query query, int pageNo, int rowsperPage) {
		return this.getDao().queryPage(query, pageNo, rowsperPage);
	}

	/** 通过模板查找列表 */
	@QueryOnly
	public List queryBySample(Object obj) {
		Query query = Query.forClass(obj.getClass());
		query.addSample(obj);
		return this.getDao().query(query);
	}

	/** 通过对象查找列表 */
	@QueryOnly
	public List select(Selector selector) {
		return this.getDao().query(selector);
	}

	/** 通过对象查找列表 */
	@QueryOnly
	public List select(Selector selector, int first, int fetchSize) {
		return this.getDao().query(selector, first, fetchSize);
	}

	/** 通过hql查询 */
	public List queryByHql(String hql, Object... param) {
		Selector sel = Selector.forHql(hql, param);
		return this.getDao().query(sel);
	}
	
	/** 通过hql查询 */
	public List queryBySql(String sql, Object... param) {
		Selector sel = Selector.forSql(sql, param);
		return this.getDao().query(sel);
	}

	/** 查找所有类 */
	@QueryOnly
	public List queryAll(Class clazz) {
		Query query = Query.forClass(clazz);
		return this.getDao().query(query);
	}

	/** 查找下一页 */
	@QueryOnly
	public PageRecord queryPageContinue(PageRecord pageRecord) {
		PageRecord result = this.getDao().queryPage(pageRecord.getQuery(), pageRecord.getPageNo(),
				pageRecord.getRowsPerPage());
		result.setOrderField(pageRecord.getOrderField());
		result.setOrderType(pageRecord.getOrderType());
		return result;
	}

	/** 更新一条数据 */
	public void updateFull(Object obj) {
		this.getDao().update(obj);
	}

	/** 更新一条数据,只更新非null的字段 */
	public void update(Class clazz, String id, Object newObject) {
		Object old = this.getDao().get(clazz, id);
		if (old == null) {
			throw new SystemException("该数据已被删除!");
		}
		BeanUtil.copyProperty(newObject, old);
		this.getDao().update(old);
	}

	/** 更新一条数据 */
	public int updateByHql(Class clazz, String hql, Object... args) {
		return this.getDao().execHql(new Class[] { clazz }, hql, args);
	}

	/** 删除一个列表 */
	public void deleteList(List list) {
		this.getDao().delete(list);
	}

	/** 删除一调记录 */
	public void delete(Object obj) {
		this.getDao().delete(obj);
	}

	/** 通过主键获得一条记录 */
	@QueryOnly
	public <T> T get(Class<T> clazz, String id, String... initFiled) {
		if(StringUtil.isNull(id)){
			throw new RuntimeException("通过主键查找记录,但传入的id为空,请检查代码!");
		}
		Query query = Query.forClass(clazz);
		String pk = MappingCache.getMapping(clazz).getPk().getName();
		if (initFiled != null && initFiled.length != 0) {
			query.init(initFiled);
		}
		query.eq(pk, id);
		List list = this.getDao().query(query);
		if (list.size() == 0) {
			logger.warn("数据已经被删除:" + clazz.getSimpleName() + "#" + id);
			return null;
		} else {
			return (T) list.get(0);
		}

	}

	/** 获得第一个值 */
	public int getFirstInt(Selector selector) {
		Object o = this.getDao().query(selector).get(0);
		return this.getIntValue(o);
	}

	public Object execInSession(SessionMethod method) {
		return method.exec(this.getDao().getSession());
	}

	/** 执行hql语句 */
	public int execHql(Class[] clazz, String hql, Object... args) {
		return this.getDao().execHql(clazz, hql, args);
	}
	
	
	/** 执行一批sql语句 */
	public void execSqls(List<String> sqls) {
		for(String sql:sqls){
			SimpleSql sqlOp=new SimpleSql(sql);
			this.getDao().execSql(null, sqlOp);
		}
	}
	
	/** 执行hql语句 */
	public int execHql(String hql, Object... args) {
		return this.getDao().execHql(new Class[0], hql, args);
	}
	
	/** 执行hql语句 */
	public int execSql(Class[] clazz, String sql, Object... args) {
		SimpleSql sqlOp=new SimpleSql(sql);
		return this.getDao().execSql(clazz, sqlOp, args);
	}

	/** 执行hql语句 */
	public int execSql (String sql, Object... args) {
		SimpleSql sqlOp=new SimpleSql(sql);
		return this.getDao().execSql(new Class[0], sqlOp, args);
	}
	
	/** 执行hql语句 */
	public int execHql(Class clazz, String hql, Object... args) {
		return this.getDao().execHql(clazz, hql, args);
	}

	/** 获得一个整数 */
	public int getIntValue(Object o) {
		if (o instanceof BigDecimal) {
			return ((BigDecimal) o).intValue();
		} else if (o instanceof Integer) {
			return ((Integer) o).intValue();
		} else if (o instanceof Long) {
			return ((Long) o).intValue();
		} else {
			logger.debug("未知类型:" + o.getClass());
			return Integer.parseInt(o + "");
		}
	}
	
	public boolean execProc(String proc,Object... args){
		Connection con=this.getDao().getSession().getSession().connection();
		try {
			CallableStatement call = con.prepareCall(proc);
			return call.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} 
	}

	public PageRecord selectPage(PageableSelector selector, int pageNo, int rowsPerPage) {
		return this.getDao().queryPage(selector, pageNo, rowsPerPage);
	}
	
	public static void main(String[] args) throws SecurityException, NoSuchMethodException {
		Method m=SimpleService.class.getMethod("get", Class.class,String.class);
		System.out.println(m);
	}

	

}
