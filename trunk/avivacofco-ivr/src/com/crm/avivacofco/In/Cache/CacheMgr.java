/**
 * 
 */
package com.crm.avivacofco.In.Cache;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *@author 罗尧  Email:j2ee.xiao@gmail.com
 *@version V1.0 2010-10-26   下午01:43:31
 */
public  class CacheMgr {
	/**
	 * logger for this clazz
	 */
	private static Log log = LogFactory.getLog(CacheMgr.class);
	
	private static CacheMgr cacheMgr = new CacheMgr();
	
	public static CacheMgr getInstance(){
		return cacheMgr;
	}
	

	public static  void AbstractCacheBean() {
	}

	// HashMap缓存
	public  static Map map = new java.util.concurrent.ConcurrentHashMap();

	static {
		//map.put("0", "1000");
		//map.put("1", "1001");
		//map.put("2", "1002");
		//map.put("3", "1003");
	}
	/**
	 * 将 key 和 value 放入缓存
	 * @param key 
	 * @param value 缓存实例
	 * <p>1.0.0.0 </p>
	 */
	public static void put(String key, Object value) {
		//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+key);
		map.put(key, value);
		//System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%"+(IvrWork)value);
	}
	/**
	 * 将key移除出缓存
	 * @param key
	 * <p>1.0.0.0 </p>
	 */
	public static void remove(Object key) {
		//System.out.println("RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR:"+key);
		map.remove(key);
	}
	/**
	 * @param key
	 * @return 从缓存中得到一个实例
	 * 			如果返回null，则表示缓存中没有此实例
	 * <p>1.0.0.0</p>
	 */
	public static Object get(Object key) {
		return map.get(key);
	}
	/**
	 * 销毁资源
	 * <p>1.0.0.0</p>
	 */
	public static void destroy(Object key) {
	}
}
