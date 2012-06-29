package com.newccic.framework.common.beanfactory;

import org.apache.log4j.Logger;

/**
 *************************************************
 *因为spring启动太慢,而我们系统要用到spring功能的bean不是太多,所以对于一般的类用自己的工厂<br>
 *通过该工厂创建的bean必须是pojo的,而且必须是无状态的
 *@Title 	newccic2						
 *@Pageage 	com.newccic.common.beanfactory						
 *@author   罗尧  Email：j2ee.xiao@gmail.com		
 *@since	1.0  创建时间  2012-6-30  上午12:30:43		
 ************************************************
 */
public class Ccic2BeanFactory {
	private static Logger log=Logger.getLogger(Ccic2BeanFactory.class);
	
	
}
