package com.crm.framework.spring;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.crm.framework.common.config.PathConvertor;
import com.crm.framework.common.util.StringUtil;

/**
 * spring的bean工厂
 * 
 * @author 王永明
 * @since Jan 26, 2010 4:04:53 PM
 */
public class SpringBeanFactory {
	private static Logger log = Logger.getLogger(SpringBeanFactory.class);
	private static ApplicationContext applicationContext;

	/** 设置spring的applicationContext，目前只有通过web启动时会调用 */
	public static void setApplicationContext(ApplicationContext applicationContext) {
		if(SpringBeanFactory.applicationContext!=null){
			log.debug("重新设置applicationContext:" + applicationContext);
		}	
		SpringBeanFactory.applicationContext = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		
		if (applicationContext == null) {	
			log.warn("获得applicationContext为空,如果不是测试请检查代码,创建默认的!");
			long start=System.currentTimeMillis();
			String[] path=PathConvertor.getSpringConfigLocation();
			for(String str:path){
				log.debug("找到spring文件:"+str);
			}
			applicationContext = new ClassPathXmlApplicationContext(path);
			log.info("spring工厂创建耗时:"+(System.currentTimeMillis()-start));
		}

		return applicationContext;
	}



	/** 判读容器内是否包含某个bean */
	public static boolean containsBean(String beanId) {
		return SpringBeanFactory.getApplicationContext().containsBean(beanId);
	}

	/** 从spring容器内获得一个实例 */
	public static <T> T getBean(Class<T> clazz) {
		String name = StringUtil.firstLower(clazz.getSimpleName());
		return (T) SpringBeanFactory.getApplicationContext().getBean(name);
	}

	/** 从spring容器内获得一个实例 */
	public static <T> T getBean(String name) {
		return (T) SpringBeanFactory.getApplicationContext().getBean(name);
	}

	
	
	/**获得实现某接口或者继承自某一个类的所有bean*/
	public static Collection getBeans(Class clazz){
		Map map=getApplicationContext().getBeansOfType(clazz);
		return map.values();
	}

}
