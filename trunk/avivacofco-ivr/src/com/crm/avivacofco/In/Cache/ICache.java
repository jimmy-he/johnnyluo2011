/**
 * 
 */
package com.crm.avivacofco.In.Cache;

/**
 *@author 罗尧  Email:j2ee.xiao@gmail.com
 *@version V1.0 2010-10-24   下午10:43:55
 */
public interface ICache {

	/**
	 * @param key
	 * @return 从缓存中得到一个实例
	 * 			如果返回null，则表示缓存中没有此实例
	 * <p>1.0.0.0</p>
	 */
	public Object get(String key);
	
	/**
	 * 将 key 和 value 放入缓存
	 * @param key 
	 * @param value 缓存实例
	 * <p>1.0.0.0 </p>
	 */
	public void put(String key, Object value);
	
	/**
	 * 将key移除出缓存
	 * @param key
	 * <p>1.0.0.0 </p>
	 */
	public void remove(String key);

	/**
	 * 销毁资源
	 * <p>1.0.0.0</p>
	 */
	public void destroy();
}
