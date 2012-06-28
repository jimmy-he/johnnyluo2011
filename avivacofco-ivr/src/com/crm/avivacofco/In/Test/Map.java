package com.crm.avivacofco.In.Test;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * @author 王永明
 * @since 2010-12-14 下午04:53:29
 */
public class Map {
	public static java.util.Map map=new ConcurrentHashMap();
	
	
	public static void main(String[] args) throws InterruptedException {
		map.put(1,"1");
		Thread1 a=new Thread1();
		Thread2 b=new Thread2();
		a.start();
		b.start();
		while(true){
			System.out.println(map.get("test"));
			Thread.sleep(1000);
		}
	}
	
	public static class Thread1 extends Thread{
		public void run(){
			while(true){
				map.put("test","thread1");
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	}
	
	public static class Thread2 extends Thread{
		public void run(){
			while(true){
				map.put("test","thread2");
				try {
					Thread.sleep(400);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	}
}
