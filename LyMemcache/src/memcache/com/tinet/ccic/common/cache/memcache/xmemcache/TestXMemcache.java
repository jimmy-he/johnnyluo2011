package com.tinet.ccic.common.cache.memcache.xmemcache;

import java.io.IOException;

import java.util.concurrent.TimeoutException;
import net.rubyeye.xmemcached.utils.AddrUtil;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.exception.MemcachedException;

/**
 ***********************************************
 * @Title     TestXMemcache.java					   
 * @Pageage   com.tinet.ccic.common.cache.memcache.xmemcache				   
 * @author    罗尧   Email:j2ee.xiao@gmail.com 
 * @since 1.0 创建时间 2012-4-23 下午05:14:09		   
 ***********************************************
 */
public class TestXMemcache {
	
	public static void main(String[] args) {

		 MemcachedClientBuilder builder = new XMemcachedClientBuilder(AddrUtil.getAddresses("172.16.203.130:12000"));
		 MemcachedClient memcachedClient;
	     try {
	    	
	         memcachedClient = builder.build();
	         memcachedClient.set("luoyao", 0, "Hello,xmemcached");
	         String value = memcachedClient.get("luoyao");
	         System.out.println("luoyao-----存储缓存之后的的值：" + value);
	         memcachedClient.delete("luoyao");
	         value = memcachedClient.get("luoyao");
	         System.out.println("luoyao-----删除缓存之后的值：" + value);
	         // close memcached client
	         memcachedClient.shutdown();
	     } catch (MemcachedException e) {
	         System.err.println("MemcachedClient operation fail");
	         e.printStackTrace();
	     } catch (TimeoutException ee) {
	         System.err.println("MemcachedClient operation timeout");
	         ee.printStackTrace();
	     } catch (InterruptedException eee) {
	         // ignore
	     }catch (IOException eeee) {
	         System.err.println("Shutdown MemcachedClient fail");
	         eeee.printStackTrace();
	     }
	}

}
