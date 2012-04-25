package com.tinet.ccic.common.cache.memcache.test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

/**
 ***********************************************
 * @Title     TestCache.java					   
 * @Pageage   com.tinet.ccic.common.cache.memcache.test				   
 * @author    罗尧   Email:j2ee.xiao@gmail.com 
 * @since 1.0 创建时间 2012-4-24 下午02:53:01		   
 ***********************************************
 */
public class TestCache {

	public static void main(String[] args) {
		MemcachedClient client;
		   try {
		    client = new XMemcachedClient("172.16.203.130",12000);//默认端口
		    // store a value for one hour(synchronously).
		    String someObject = "强制缓存这个，一个小时可以吗？";
		    client.set("key", 3600, someObject);

		    // Retrieve a value.(synchronously).
		    Object getSomeObject = client.get("key");
		    // delete
		    client.delete("key");
		    System.out.println(getSomeObject.toString());
		   } catch (TimeoutException e) {
		    e.printStackTrace();
		   } catch (InterruptedException ee) {
		    ee.printStackTrace();
		   } catch (MemcachedException eee) {
		    eee.printStackTrace();
		   } catch (IOException eeee) {
		    eeee.printStackTrace();
		   }

	}

}
