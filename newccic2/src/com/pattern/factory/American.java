package com.pattern.factory;

/**
 ***********************************************
 * @Title     American.java					   
 * @Pageage   com.pattern.factory				   
 * @author    罗尧   Email:j2ee.xiao@gmail.com 
 * @since 1.0 创建时间 2012-6-28 上午11:35:16		   
 ***********************************************
 */
public class American implements Person {


	public String sayGoodBye(String name) {
		
		return name+",good bye";
	}


	public String sayHello(String name) {
		
		return name+",hello";
	}

}
