package com.tinet.ccic.common.cache.memcache.service;

/**
 *********************************************** 
 * 缓存接口(所有缓存均必须实现该接口)
 * 
 * @Title CacheClient.java
 * @Pageage com.tinet.ccic.common.cache.memcache
 * @author 罗尧 Email:j2ee.xiao@gmail.com
 * @since 1.0 创建时间 2012-4-23 下午05:02:51
 *********************************************** 
 */
public interface CacheClient {

	/** 
	 * 添加一个值,如果存在则失败
	 * @param key
	 * @param value
	 * @return 如果存在则false
	 */
	boolean add(String key, Object value);

	/**
	 * 添加一个值,如果存在则失败
	 * @param key
	 * @param value
	 * @param expiry
	 *            过期时间(秒),最大设置时间为30天
	 *            An expiration time, in seconds. Can be up to 30 days. After 30
     *            days, is treated as a unix timestamp of an exact date.
	 * @return
	 */
	boolean add(String key, Object value, int expiry);

	/**
	 * 添加一个值,如果存在则失败
	 * @param key
	 * @param value
	 * @param expiry
	 *            过期时间(秒),最大设置时间为30天
	 *            An expiration time, in seconds. Can be up to 30 days. After 30
     *            days, is treated as a unix timestamp of an exact date.
	 * @param hashCode
	 *            集群时用于负载权重
	 * @return
	 */
	boolean add(String key, Object value, int expiry, Integer hashCode);

	/**
	 * 添加一个值,如果存在则失败
	 * @param key
	 * @param value
	 * @param hashCode
	 *            集群时用于负载权重
	 * @return
	 */
	boolean add(String key, Object value, Integer hashCode);

	/**
	 * 删除一个值
	 * @param key
	 * @return
	 */
	boolean delete(String key);

	/**
	 * 删除一个值
	 * @param key 
	 * @param expiry
	 * @return
	 */
	boolean delete(String key, int expiry);

	boolean delete(String key, Integer hashCode, int expiry);

	/**
	 * 获取一个值
	 * @param key
	 * @return
	 */
	Object get(String key);

	/**
	 * 获取一个值
	 * @param key
	 * @param hashCode
	 *            集群时用于负载权重
	 * @return
	 */
	Object get(String key, Integer hashCode);

	/**
	 * 获取一个值
	 * @param key
	 * @param hashCode
	 *            集群时用于负载权重
	 * @param asString
	 * @return
	 */
	Object get(String key, Integer hashCode, boolean asString);

	/**
	 * 替换一个值,如果不存在则失败
	 * @param key
	 * @param value
	 * @return
	 */
	boolean replace(String key, Object value);

	/**
	 * 替换一个值 ,如果不存在则失败
	 * @param key
	 * @param value
	 * @param expiry
	 *            过期时间(秒),最大设置时间为30天
	 *            An expiration time, in seconds. Can be up to 30 days. After 30
     *            days, is treated as a unix timestamp of an exact date.
	 * @return
	 */
	boolean replace(String key, Object value, int expiry);

	/**
	 * 替换一个值 ,如果不存在则失败
	 * @param key
	 * @param value
	 * @param expiry
	 * @param hashCode
	 *            集群时用于负载权重
	 * @return
	 */
	boolean replace(String key, Object value, int expiry, Integer hashCode);

	/**
	 * 替换一个值 ,如果不存在则失败
	 * @param key
	 * @param value
	 * @param hashCode
	 *            集群时用于负载权重
	 * @return
	 */
	boolean replace(String key, Object value, Integer hashCode);

	/**
	 * 设置一个值到缓存，如果存在则替换，否则添加
	 * @param key
	 * @param value
	 * @return
	 */
	boolean set(String key, Object value);

	/**
	 * 设置一个值到缓存，如果存在则替换，否则添加
	 * @param key
	 * @param value
	 * @param expiry
	 *            过期时间(秒),最大设置时间为30天
	 *            An expiration time, in seconds. Can be up to 30 days. After 30
     *            days, is treated as a unix timestamp of an exact date.
	 * @return
	 */
	boolean set(String key, Object value, int expiry);

	/**
	 * 设置一个值到缓存，如果存在则替换，否则添加
	 * @param key
	 * @param value
	 * @param expiry
	 *            过期时间(秒),最大设置时间为30天
	 *            An expiration time, in seconds. Can be up to 30 days. After 30
     *            days, is treated as a unix timestamp of an exact date.
	 * @param hashCode
	 *            集群时用于负载权重
	 * @return
	 */
	boolean set(String key, Object value, int expiry, Integer hashCode);

	/**
	 * 设置一个值到缓存，如果存在则替换，否则添加
	 * @param key
	 * @param value
	 * @param hashCode
	 *            集群时用于负载权重
	 * @return
	 */
	boolean set(String key, Object value, Integer hashCode);

	/**
	 * 判断指定key是否存在
	 * @param key
	 * @return
	 */
	boolean keyExists(String key);

}
