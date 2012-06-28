package com.newccic.test;

import java.util.Date;

/**
 ***********************************************
 * @Title     Test.java					   
 * @Pageage   com.newccic.test				   
 * @author    罗尧   Email:j2ee.xiao@gmail.com 
 * @since 1.0 创建时间 2012-6-28 下午05:37:11		   
 ***********************************************
 */
public class Test extends Thread{

	public static void main(String[] args) throws InterruptedException {
		System.out.println("new Date().getTime()---"+new Date().getTime());
		System.out.println("System.currentTimeMillis()---"+System.currentTimeMillis());
		
		long start=System.currentTimeMillis();
		System.out.println("开始时间戳为："+start);
		Thread.sleep(5000);
		long end=System.currentTimeMillis();
		System.out.println("结束时间戳为："+end);
		long consume=end-start;
		long min=consume/1000/60;
		long sec=(consume/1000)%60;
		
		System.out.println("----------------系统已经正常启动,共耗时:"+consume+"毫秒,约合"+min+"分钟"+sec+"秒------------------");
	}
}
