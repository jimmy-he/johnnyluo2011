package com.tinet.ccic.common.cache.memcache.service;

import java.util.concurrent.TimeoutException;
import javax.annotation.Resource;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

/**
 *********************************************** 
 * 接口CacheClient 的 XMemcached客户端 实现 (并发性能甚至超越spymemcached)
 * 
 * @Title CacheClientXmemcachedImpl.java
 * @Pageage com.tinet.ccic.common.cache.memcache
 * @author 罗尧 Email:j2ee.xiao@gmail.com
 * @since 1.0 创建时间 2012-4-23 下午05:08:13
 *********************************************** 
 */
public class CacheClientXmemcachedImpl implements CacheClient {

	protected MemcachedClient memcachedClient;
	/**
	 * @return the memcachedClient
	 */
	public MemcachedClient getMemcachedClient() {
		return memcachedClient;
	}

	/**
	 * @param memcachedClient the memcachedClient to set
	 */
	public void setMemcachedClient(MemcachedClient memcachedClient) {
		this.memcachedClient = memcachedClient;
	}

	public void setCacheProvider(MemcachedClient memcachedClient) {

		this.memcachedClient = memcachedClient;

	}

	public boolean add(String key, Object value) {

		try {

			return this.memcachedClient.add(key, 0, value);

		} catch (TimeoutException e) {

			e.printStackTrace();

		} catch (InterruptedException e) {

			e.printStackTrace();

		} catch (MemcachedException e) {

			e.printStackTrace();

		}

		return false;

	}

	public boolean add(String key, Object value, int expiry) {

		try {

			return this.memcachedClient.add(key, expiry, value);

		} catch (TimeoutException e) {

			e.printStackTrace();

		} catch (InterruptedException e) {

			e.printStackTrace();

		} catch (MemcachedException e) {

			e.printStackTrace();

		}

		return false;

	}

	public boolean add(String key, Object value, int expiry, Integer hashCode) {

		try {

			return this.memcachedClient.add(key, expiry, value);

		} catch (TimeoutException e) {

			e.printStackTrace();

		} catch (InterruptedException e) {

			e.printStackTrace();

		} catch (MemcachedException e) {

			e.printStackTrace();

		}

		return false;

	}

	public boolean add(String key, Object value, Integer hashCode) {

		try {

			return this.memcachedClient.add(key, 0, value);

		} catch (TimeoutException e) {

			e.printStackTrace();

		} catch (InterruptedException e) {

			e.printStackTrace();

		} catch (MemcachedException e) {

			e.printStackTrace();

		}

		return false;

	}

	public boolean delete(String key) {

		try {

			return this.memcachedClient.delete(key);

		} catch (TimeoutException e) {

			e.printStackTrace();

		} catch (InterruptedException e) {

			e.printStackTrace();

		} catch (MemcachedException e) {

			e.printStackTrace();

		}

		return false;

	}

	public boolean delete(String key, int expiry) {

		try {

			return this.memcachedClient.delete(key, expiry);

		} catch (TimeoutException e) {

			e.printStackTrace();

		} catch (InterruptedException e) {

			e.printStackTrace();

		} catch (MemcachedException e) {

			e.printStackTrace();

		}

		return false;

	}

	public boolean delete(String key, Integer hashCode, int expiry) {

		try {

			return this.memcachedClient.delete(key, expiry);

		} catch (TimeoutException e) {

			e.printStackTrace();

		} catch (InterruptedException e) {

			e.printStackTrace();

		} catch (MemcachedException e) {

			e.printStackTrace();

		}

		return false;

	}

	public Object get(String key) {

		try {

			return this.memcachedClient.get(key);

		} catch (TimeoutException e) {

			e.printStackTrace();

		} catch (InterruptedException e) {

			e.printStackTrace();

		} catch (MemcachedException e) {

			e.printStackTrace();

		}

		return null;

	}

	public Object get(String key, Integer hashCode) {

		try {

			return this.memcachedClient.get(key);

		} catch (TimeoutException e) {

			e.printStackTrace();

		} catch (InterruptedException e) {

			e.printStackTrace();

		} catch (MemcachedException e) {

			e.printStackTrace();

		}

		return null;

	}

	public Object get(String key, Integer hashCode, boolean asString) {

		try {

			if (asString)
				return this.memcachedClient.get(key).toString();

			return this.memcachedClient.get(key);

		} catch (TimeoutException e) {

			e.printStackTrace();

		} catch (InterruptedException e) {

			e.printStackTrace();

		} catch (MemcachedException e) {

			e.printStackTrace();

		}

		return null;

	}

	public boolean keyExists(String key) {

		return this.get(key) == null ? false : true;

	}

	public boolean replace(String key, Object value) {

		try {

			return this.memcachedClient.replace(key, 0, value);

		} catch (TimeoutException e) {

			e.printStackTrace();

		} catch (InterruptedException e) {

			e.printStackTrace();

		} catch (MemcachedException e) {

			e.printStackTrace();

		}

		return false;

	}

	public boolean replace(String key, Object value, int expiry) {

		try {

			return this.memcachedClient.replace(key, expiry, value);

		} catch (TimeoutException e) {

			e.printStackTrace();

		} catch (InterruptedException e) {

			e.printStackTrace();

		} catch (MemcachedException e) {

			e.printStackTrace();

		}

		return false;

	}

	public boolean replace(String key, Object value, int expiry,

	Integer hashCode) {

		try {

			return this.memcachedClient.replace(key, expiry, value);

		} catch (TimeoutException e) {

			e.printStackTrace();

		} catch (InterruptedException e) {

			e.printStackTrace();

		} catch (MemcachedException e) {

			e.printStackTrace();

		}

		return false;

	}

	public boolean replace(String key, Object value, Integer hashCode) {

		try {

			return this.memcachedClient.replace(key, 0, value);

		} catch (TimeoutException e) {

			e.printStackTrace();

		} catch (InterruptedException e) {

			e.printStackTrace();

		} catch (MemcachedException e) {

			e.printStackTrace();

		}

		return false;

	}

	public boolean set(String key, Object value) {

		try {

			return this.memcachedClient.set(key, 0, value);

		} catch (TimeoutException e) {

			e.printStackTrace();

		} catch (InterruptedException e) {

			e.printStackTrace();

		} catch (MemcachedException e) {

			e.printStackTrace();

		}

		return false;

	}

	public boolean set(String key, Object value, int expiry) {

		try {

			return this.memcachedClient.set(key, expiry, value);

		} catch (TimeoutException e) {

			e.printStackTrace();

		} catch (InterruptedException e) {

			e.printStackTrace();

		} catch (MemcachedException e) {

			e.printStackTrace();

		}

		return false;

	}

	public boolean set(String key, Object value, int expiry, Integer hashCode) {

		try {

			return this.memcachedClient.set(key, expiry, value);

		} catch (TimeoutException e) {

			e.printStackTrace();

		} catch (InterruptedException e) {

			e.printStackTrace();

		} catch (MemcachedException e) {

			e.printStackTrace();

		}

		return false;

	}

	public boolean set(String key, Object value, Integer hashCode) {

		try {

			return this.memcachedClient.set(key, 0, value);

		} catch (TimeoutException e) {

			e.printStackTrace();

		} catch (InterruptedException e) {

			e.printStackTrace();

		} catch (MemcachedException e) {

			e.printStackTrace();

		}

		return false;

	}

}
