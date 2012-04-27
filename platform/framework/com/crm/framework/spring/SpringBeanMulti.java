package com.crm.framework.spring;


/**
 * spring工厂创建的bean默认是单例的<br>
 * 实现这个接口的bean在设置时将会加上属性@Scope("prototype"),变成非单例的
 * 
 * @author 王永明
 * @since Mar 19, 2010 10:00:23 AM
 */
public interface SpringBeanMulti extends SpringBean {

}
