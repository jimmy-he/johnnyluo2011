package com.crm.framework.test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.crm.base.permission.baseinfo.model.BaseInfo;
import com.crm.base.permission.user.model.User;
import com.crm.framework.common.beanfactory.CrmBeanFactory;
import com.crm.framework.common.config.PathConvertor;
import com.crm.framework.common.i18n.DefaultTextProvider;
import com.crm.framework.common.util.FileUtil;
import com.crm.framework.common.util.LogUtil;
import com.crm.framework.crmbean.cache.DatasourceInfoCache;
import com.crm.framework.crmbean.interfaces.CrmDatasource;
import com.crm.framework.crmbean.interfaces.CrmLog;
import com.crm.framework.dao.selector.Query;
import com.crm.framework.model.MappingCache;
import com.crm.framework.model.MappingEntity;
import com.crm.framework.model.MappingField;
import com.crm.framework.service.ServiceFactory;

/**
 * 测试用工具类
 * 
 * @author 王永明
 * @since May 5, 2010 11:59:51 AM
 */
public class TestUtil {
	private static Logger log = Logger.getLogger(TestUtil.class);
	private static Map<String, Long> startTime;
	private static Map<String, Long> endTime;

	static {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				if (startTime != null) {
					for (java.util.Map.Entry<String, Long> en : startTime.entrySet()) {
						String key = en.getKey();
						if (endTime == null) {
							endTime = new HashMap();
						}
						Long end = endTime.get(key);
						if (end == null) {
							end = System.nanoTime();
						}
						long time = end - en.getValue();
						if (time < 1000) {
							System.out.println("============执行" + en.getKey() + "耗时:" + time + "ns=============");
						} else {
							double consume = Double.parseDouble(time + "") / 1000 / 1000;
							System.out.println("============执行" + en.getKey() + "耗时:" + consume + "ms=============");
						}
					}
				}

			}
		});
	}

	public static void start(String text) {
		if (startTime == null) {
			startTime = new HashMap();
		}
		startTime.put(text, System.nanoTime());
	}

	public static void end(String text) {
		if (endTime == null) {
			endTime = new HashMap();
		}
		endTime.put(text, System.nanoTime());
	}

	public static void start() {
		start(LogUtil.getCallMethod());
	}

	/** 将默认租户的数据的映射类临时改为传入的class,此方法是为了方便测试,在创建session工厂的时候只为传入的类创建映射 */
	public static void setMappingClass(Class... clazz) {
		Set<Class> set = new HashSet();
		// 日志
		addClass(set, CrmBeanFactory.getBean(CrmLog.class).getClass());

		for (Class cls : clazz) {
			addClass(set, cls);
		}
		DatasourceInfoCache dsCache = CrmBeanFactory.getBean(DatasourceInfoCache.class);
		CrmDatasource ds = DatasourceInfoCache.getDefaultDatasourceInfo();
		ds.setCrmClass(new ArrayList(set));
		dsCache.putToCache(ds.getDatasourceInfoId(), ds);
	}

	/** 将与传入clazz关联的类放入set */
	public static void addClass(Set set, Class clazz) {
		if (set.contains(clazz)) {
			return;
		}
		set.add(clazz);
		MappingEntity en = MappingCache.getMapping(clazz);
		// 一对多映射
		for (MappingField field : en.getManyToOne()) {
			addClass(set, field.getReturnType());
		}
		// 多对一映射
		for (MappingField field : en.getOneToMany()) {
			addClass(set, field.getListType());
		}

		// 一对一映射
		for (MappingField field : en.getOneToOne()) {
			addClass(set, field.getReturnType());
		}
	}

	/** 设置日志级别 */
	public static void setLog(Level level) {
		Logger.getLogger("com.crm").setLevel(level);
	}

	public static void initI18n() {
		new DefaultTextProvider();
		ResourceBundle bound = ResourceBundle.getBundle(PathConvertor.getI18NResourceBase());
		DefaultTextProvider.testBound = bound;
	}

	/**
	 * 计算执行一个方法的耗时
	 * 
	 * @param time
	 *            执行次数
	 * @param obj
	 *            对象
	 * @param methodName
	 *            方法名称
	 * @param paraType
	 *            参数类型,如果参数值都不为空的话可以传null
	 * @param paraValues
	 *            参数值
	 * */
	public static double getConsume(int time, Object obj, String methodName, Class[] paraType, Object... paraValues) {
		ExecutorService es = Executors.newFixedThreadPool(time);
		try {
			if (paraType == null || paraType.length == 0) {
				if (paraValues != null && paraValues.length != 0) {
					paraType = new Class[paraValues.length];
					for (int i = 0; i < paraType.length; i++) {
						paraType[i] = paraValues[i].getClass();
					}
				}
			}
			Method method = obj.getClass().getMethod(methodName, paraType);
			
			List<Future> list = new ArrayList();
			for (int i = 0; i < time; i++) {
				Future future = es.submit(new TimeConsumeCall(method, obj, paraValues));
				list.add(future);
			}
			long consume = 0;
			for (Future fu : list) {
				consume += (Long) fu.get();
			}
			es.shutdown();
			return Double.parseDouble(consume + "") / 1000 / 1000;
		} catch (Throwable ex) {
			es.shutdown();
			throw new RuntimeException(ex);
		}

	}

	public static class TimeConsumeCall implements Callable<Long> {
		private Method method;
		private Object[] paras;
		private Object obj;

		public TimeConsumeCall(Method method, Object obj, Object... paras) {
			this.method = method;
			this.paras = paras;
			this.obj = obj;
		}

		public Long call() throws Exception {
			long start = System.nanoTime();
			method.invoke(obj, paras);
			return System.nanoTime() - start;
		}
	}
	
	public static void main(String[] args) {
		
		int time=3000;
		TestUtil u=new TestUtil(); 
		double a=TestUtil.getConsume(time, u, "gogo", null);
		System.out.println(a/time);
	}

}
