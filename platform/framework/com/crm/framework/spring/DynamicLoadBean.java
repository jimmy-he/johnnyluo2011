package com.crm.framework.spring;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.ResourceEntityResolver;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ConfigurableApplicationContext;

import com.crm.framework.common.util.StringUtil;
import com.crm.framework.common.util.xml.XmlUtil;
import com.crm.framework.spring.enums.BeanScope;

/**
 * 
 * 运行时添加javabean
 * 
 * 
 */
public class DynamicLoadBean {
	private static final String XSI = "http://www.w3.org/2001/XMLSchema-instance";
	private static final String SCHEMA_LOCATION = "http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-2.0.xsd";
	private static final String XMLNS = "http://www.springframework.org/schema/beans";

	private static Logger log = Logger.getLogger(DynamicLoadBean.class);

	

	public ConfigurableApplicationContext getApplicationContext() {
		return (ConfigurableApplicationContext) SpringBeanFactory.getApplicationContext();
	}

	public void loadBean(String id, String clazz) {
		this.loadBean(id, clazz, null, BeanScope.singleton);
	}
	
	public void loadBean(Class clazz) {
		String id=StringUtil.firstLower(clazz.getSimpleName());
		this.loadBean(id, clazz.getName(), null, BeanScope.singleton);
	}


	public void loadBean(String id, String clazz, Map<String, String> properties) {
		this.loadBean(id, clazz, properties, BeanScope.singleton);
	}
	

	public void loadBean(String id, String clazz, BeanScope scope) {
		this.loadBean(id, clazz, null, scope);
	}

	public void loadBean(Class clazz, Map<String, String> properties) {
		this.loadBean(StringUtil.firstLower(clazz.getSimpleName()), clazz.getName(), properties, BeanScope.singleton);
	}

	/**
	 * 往spring容器里增加一个bean
	 * 
	 * @param id
	 *            唯一标识
	 * @param clazz
	 *            类名
	 * @param properties
	 *            类属性
	 * @param beanScope
	 *            存活周期
	 * @throws IOException
	 */
	public void loadBean(final String id, final String clazz, final Map<String, String> properties,
			final BeanScope beanScope) {
		this.loadBean(new BeanElement() {
			public void fillBean(Element bean) {
				bean.addAttribute("id", id).addAttribute("class", clazz).addAttribute("scope", beanScope + "");
				if (properties == null) {
					return;
				}
				Set<Entry<String, String>> set = properties.entrySet();
				for (Entry<String, String> entry : set) {
					bean.addElement("property").addAttribute("name", entry.getKey()).addElement("value").addText(
							entry.getValue());
				}
			}

		});

	}

	public void loadBean(BeanElement beanElement) {
		Document document = DocumentHelper.createDocument();
		Element beans = document.addElement("beans", XMLNS);
		beans.addAttribute("xmlns:xsi", XSI);
		beans.addAttribute("xsi:schemaLocation", SCHEMA_LOCATION);
		Element bean = beans.addElement("bean");
		beanElement.fillBean(bean);
		this.loadBeanFromStirng(document.asXML());
	}
	
	
	public String addXmls(String content) {
		Document document = DocumentHelper.createDocument();
		Element beans = document.addElement("beans", XMLNS);
		beans.addAttribute("xmlns:xsi", XSI);
		beans.addAttribute("xsi:schemaLocation", SCHEMA_LOCATION);
		return document.asXML()+content;
	}
	
	public void loadBean(BeanElement beanElement,Map properties) {
		Document document = DocumentHelper.createDocument();
		Element beans = document.addElement("beans", XMLNS);
		beans.addAttribute("xmlns:xsi", XSI);
		beans.addAttribute("xsi:schemaLocation", SCHEMA_LOCATION);
		Element bean = beans.addElement("bean");
		Set<Entry<String, String>> set = properties.entrySet();
		for (Entry<String, String> entry : set) {
			bean.addElement("property").addAttribute("name", entry.getKey()).addElement("value").addText(
					entry.getValue());
		}
	
		beanElement.fillBean(bean);
		this.loadBeanFromStirng(document.asXML());
	}

	public void loadBeanFromStirng(String xml) {
		if (log.isDebugEnabled()) {
			log.debug("装载bean:" + StringUtil.getLine() + "" + XmlUtil.formate(xml));
		}
		XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(
				(BeanDefinitionRegistry) getApplicationContext().getBeanFactory());
		beanDefinitionReader.setResourceLoader(getApplicationContext());
		beanDefinitionReader.setEntityResolver(new ResourceEntityResolver(getApplicationContext()));
		beanDefinitionReader.loadBeanDefinitions(new StringResource(xml));
	}

	/**
	 * 向spring的beanFactory动态地装载bean
	 * 
	 * @param configLocationString
	 *            要装载的bean所在的xml配置文件位置。
	 *            spring配置中的contextConfigLocation，同样支持诸如"/WEB-INF/ApplicationContext-*.xml"的写法。
	 */
	public void loadBean(String configLocationString) throws Exception {

		XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(
				(BeanDefinitionRegistry) getApplicationContext().getBeanFactory());
		beanDefinitionReader.setResourceLoader(getApplicationContext());
		beanDefinitionReader.setEntityResolver(new ResourceEntityResolver(getApplicationContext()));
		String[] configLocations = new String[] { configLocationString };

		for (int i = 0; i < configLocations.length; i++)
			beanDefinitionReader.loadBeanDefinitions(getApplicationContext().getResources(configLocations[i]));
	}

	public interface BeanElement {
		public void fillBean(Element bean);
	}

}
