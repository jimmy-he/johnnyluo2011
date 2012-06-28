/**
 * 
 */
package com.crm.avivacofco.In.Cache;

/**
 *@author 罗尧  Email:j2ee.xiao@gmail.com
 *@version V1.0 2010-10-25   下午05:20:42
 */
public class CacheManager {

	/**
	 * Servlet缓存
	 */
	private static ICache servletCache;

	/**
	 * @return the servletCache
	 */
	public static ICache getServletCache() {
		return servletCache;
	}

	/**
	 * @param servletCache the servletCache to set
	 */
	public static void setServletCache(ICache servletCache) {
		CacheManager.servletCache = servletCache;
	}
	
}
