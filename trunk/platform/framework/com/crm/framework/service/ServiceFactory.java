package com.crm.framework.service;

import java.lang.reflect.Method;
import java.util.List;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.apache.log4j.Logger;

import com.crm.framework.action.ContextHolder;
import com.crm.framework.common.beanfactory.CrmBeanFactory;
import com.crm.framework.crmbean.defaultbean.DefaultBeanCache;
import com.crm.framework.crmbean.defaultbean.DefaultBeanCache.BeanType;
import com.crm.framework.crmbean.interfaces.CrmTenant;
import com.crm.framework.crmbean.interfaces.CrmUser;
import com.crm.framework.dao.CrmDao;
import com.crm.framework.dao.DataBaseInfoHolder;
import com.crm.framework.dao.annotation.QueryOnly;
import com.crm.framework.dao.transaction.DatasourceMethod;
import com.crm.framework.dao.transaction.TransactionUtil;
import com.crm.framework.service.cache.ServiceMethodCache;
import com.crm.framework.service.cache.vo.ServiceMapping;

/**
 * 
 * @author 王永明
 * @since Apr 1, 2010 11:46:11 PM
 */
public class ServiceFactory {
	private static final Logger log = Logger.getLogger(ServiceFactory.class);

	public static <T> T getService(Class<T> clazz) {
		CrmUser current = CrmBeanFactory.getBean(ContextHolder.class).getCurrentUser();
		return getService(clazz, current);
	}

	/** 获得默认的service类 */
	public static SimpleService getDefaultService() {
		return getService(SimpleService.class, (CrmUser) DefaultBeanCache.getBean(BeanType.tempUser));
	}

	public static SimpleService getCurentService() {
		ContextHolder holder = CrmBeanFactory.getBean(ContextHolder.class);
		CrmUser user = holder.getCurrentUser();
		return getService(SimpleService.class, user);
	}

	public static SimpleService getSimpleService(String datasourceId) {
		CrmUser user = CrmBeanFactory.getBean(CrmUser.class);
		user.setUserCode("系统用户");
		CrmTenant tenant = CrmBeanFactory.getBean(CrmTenant.class);
		tenant.setDataSourceId(datasourceId);
		user.setCrmTenant(tenant);
		return getService(SimpleService.class, user);
	}

	public static <T> T getService(Class<T> clazz, String datasourceId) {
		CrmUser user = CrmBeanFactory.getBean(CrmUser.class);
		user.setUserCode("系统用户");
		CrmTenant tenant = CrmBeanFactory.getBean(CrmTenant.class);
		tenant.setDataSourceId(datasourceId);
		user.setCrmTenant(tenant);
		return getService(clazz, user);
	}

	public static <T> T getService(Class<T> clazz, CrmUser crmUser) {
		log.debug(crmUser.getUserCode() + "打开数据库打开数据链接:" + crmUser.getCrmTenant().getDataSourceId());

		try {
			T t = clazz.newInstance();

			
			if (t instanceof UserTransactionHandle) {
				String[] datasourceId = new String[] { crmUser.getCrmTenant().getDataSourceId() };
				t = addTransactionProxy(clazz, datasourceId);
			}
			
			if (t instanceof UserHandle) {
				UserHandle han = (UserHandle) t;
				han.setUser(crmUser);
			}


			return t;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static <T> T addTransactionProxy(final Class<T> clazz, final String[] datasourceId) {
		Enhancer en = new Enhancer();
		// 进行代理
		en.setSuperclass(clazz);
		final ServiceMapping mapping = ServiceMethodCache.getMapping(clazz.getName());
		en.setCallback(new MethodInterceptor() {
			public Object intercept(final Object obj, Method method, final Object[] args, final MethodProxy methodProxy)
					throws Throwable {
				// log.debug(method.getName());
				if (!mapping.contain(method)) {
					// log.debug("忽略方法:"+method.getName());
					return methodProxy.invokeSuper(obj, args);
				}

				final TransactionHandle handle = (TransactionHandle) obj;
				if (handle.getDaos() != null) {
					log.debug("数据库连接已开启,跳过:" + method.getName());
					return methodProxy.invokeSuper(obj, args);
				}

				List<CrmDao> dao = DataBaseInfoHolder.getDao(datasourceId);

				Object result = null;
				// 带有查询标示的不开启事务
				if (method.getAnnotation(QueryOnly.class) != null) {
					log.debug(clazz.getName() + "." + method.getName() + "打开数据库连接");
					result = TransactionUtil.query(new DatasourceMethod() {
						public Object exec(List<CrmDao> crmDaos) throws Throwable {
							handle.setDao(crmDaos);
							return methodProxy.invokeSuper(obj, args);
						}

					}, dao);
				} else {
					log.debug(clazz.getName() + "." + method.getName() + "开启事务");
					result = TransactionUtil.execInTransaction(new DatasourceMethod() {
						public Object exec(List<CrmDao> crmDaos) throws Throwable {
							handle.setDao(crmDaos);
							return methodProxy.invokeSuper(obj, args);
						}

					}, dao);
				}

				handle.setDao(null);
				return result;
			}
		});
		return (T) en.create();
	}
}
