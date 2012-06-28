package com.newccic.test.pattern.factory;

/**
 ***********************************************
 * @Title     Chinses.java					   
 * @Pageage   com.pattern.factory				   
 * @author    罗尧   Email:j2ee.xiao@gmail.com 
 * @since 1.0 创建时间 2012-6-28 上午11:38:41		   
 ***********************************************
 */
public class Chinses implements Person {

	
	public String sayGoodBye(String name) {

		return name + ",再见";
	}

	
	public String sayHello(String name) {
		
		return name + ",你好";
	}

}
