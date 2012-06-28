/**
 * 
 */
package com.crm.web.cache;

/**
 * 定义缓存基本元素
 *@author 罗尧  Email:j2ee.xiao@gmail.com
 *@version V1.0 2010-10-24   下午08:14:38
 */
public final  class MessCacheObject {
	public MessCacheable object;
	 public String size;
	public Object lastAccessedListNode;
	
	 public void CacheObject(MessCacheable object, String size) {
	  this.object = object;
	  this.size = size;
	 }


}
