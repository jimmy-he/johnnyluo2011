package com.tinet.ccic.common.cache.memcache.javamemcache;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;

/**
 ***********************************************
 * @Title     CacheTest.java					   
 * @Pageage   com.tinet.ccic.common.cache.memcache				   
 * @author    罗尧   Email:j2ee.xiao@gmail.com 
 * @since 1.0 创建时间 2012-4-23 下午02:43:20		   
 ***********************************************
 */
public class CacheTest {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		 /**
	        * 初始化SockIOPool，管理memcached的连接池
	        * */
	       String[] servers = { "172.16.203.130:12000" };
	       SockIOPool pool = SockIOPool.getInstance();
	       pool.setServers(servers);
	       pool.setFailover(true);
	       pool.setInitConn(10);
	       pool.setMinConn(5);
	       pool.setMaxConn(250);
	       pool.setMaintSleep(30);
	       pool.setNagle(false);
	       pool.setSocketTO(3000);
	       pool.setAliveCheck(true);
	       pool.initialize();
	       /**
	        * 建立MemcachedClient实例
	        * */
	       MemCachedClient memCachedClient = new MemCachedClient();
	       for (int i = 0; i < 1000; i++) {

	           /**
	            * 将对象加入到memcached缓存
	            * */
	           boolean success = memCachedClient.set("" + i, "Hello!");
	           /**
	            * 从memcached缓存中按key值取对象
	            * */
	           String result = (String) memCachedClient.get("" + i);
	           Thread.sleep(1000);
	           System.out.println(String.format("set( %d ): %s", i, success));
	           System.out.println(String.format("get( %d ): %s", i, result));
	       }

	}

}
