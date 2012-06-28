package com.newccic.test.pattern.factory;

/**
 ***********************************************
 * @Title     PersonFactory.java					   
 * @Pageage   com.pattern.factory				   
 * @author    罗尧   Email:j2ee.xiao@gmail.com 
 * @since 1.0 创建时间 2012-6-28 上午11:36:46		   
 ***********************************************
 */
public class PersonFactory {

	public Person getPerson(String str){
		if(str.equalsIgnoreCase("chin")){
			return new Chinses();
		}
		else{
			return new American();
		}
	}
}
