package com.pattern.factory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 ***********************************************
 * @Title     FactoryTest.java					   
 * @Pageage   com.pattern.factory				   
 * @author    罗尧   Email:j2ee.xiao@gmail.com 
 * @since 1.0 创建时间 2012-6-28 上午11:40:04		   
 ***********************************************
 */
public class FactoryTest {

	public static void main(String[] args) {
		//PersonFactory pfFactory=new PersonFactory();				//用spring的方式代替了工厂模式
		Person person=null;
		
		ApplicationContext ctx=new ClassPathXmlApplicationContext("classpath:spring/applicationContext*.xml");
		System.out.println(ctx);
		person=(Person) ctx.getBean("chinese");
		//person=pfFactory.getPerson("chin");
		System.out.println(person.sayHello("wawa"));
		System.out.println(person.sayGoodBye("wawa"));
		person=(Person) ctx.getBean("american");
		//person=pfFactory.getPerson("ame");
		System.out.println(person.sayHello("wawa"));
		System.out.println(person.sayGoodBye("wawa"));
	}
}
