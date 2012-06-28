package com.newccic.test.pattern.singleton;

/**
 ***********************************************
 * @Title     SingletonTest.java					   
 * @Pageage   com.pattern.singleton				   
 * @author    罗尧   Email:j2ee.xiao@gmail.com 
 * @since 1.0 创建时间 2012-6-28 上午11:25:50		   
 ***********************************************
 */
public class SingletonTest {

	int value;
	private static SingletonTest instance;
	private SingletonTest(){
		System.out.println("正在执行构造器...");
	}
	public static SingletonTest getInstance(){
		if(instance==null){
			instance=new SingletonTest();
		}
		return instance;
	}
	
	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(int value) {
		this.value = value;
	}
	public static void main(String[] args) {
		SingletonTest t1=SingletonTest.getInstance();
		SingletonTest t2=SingletonTest.getInstance();
		t2.setValue(9);
		System.out.println(t1==t2);

	}

}
