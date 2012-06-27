package com.test;

import java.io.FileNotFoundException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 ***********************************************
 * @Title     SpringTest.java					   
 * @Pageage   com.test				   
 * @author    罗尧   Email:j2ee.xiao@gmail.com 
 * @since 1.0 创建时间 2012-6-27 下午04:28:56		   
 ***********************************************
 */
public class SpringTest {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		//InputStream ins=new FileInputStream("classpath:spring/applicationContext*.xml");
		//ApplicationContext ac = new ClassPathXmlApplicationContext("resources/spring/applicationContext-bean.xml"); 
		ApplicationContext ctx = new FileSystemXmlApplicationContext("resources/spring/applicationContext-bean.xml"); 
		Action action=(Action) ctx.getBean("helloBean");
		System.out.println(action.execute("____Johnny2012____"));
	}

}
