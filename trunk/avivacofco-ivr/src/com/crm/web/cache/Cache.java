/**
 * 
 */
package com.crm.web.cache;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Mess缓存操作类
 *@author 罗尧  Email:j2ee.xiao@gmail.com
 *@version V1.0 2010-10-24   下午08:21:57
 */
public class Cache implements MessCacheable {
	 protected HashMap cachedObjectsHash;
	 protected LinkedList lastAccessedList;
	 protected LinkedList ageList;
	//缓存元素的最大尺寸128KB,可修改
	 protected int maxSize =  128 * 1024; 
	 //整个缓存的大小
	 protected int size = 0;
	 //缓存元素的最大保存时间，用Cache(long maxLifetime)初始化
	 protected long maxLifetime = -1;
	 //记录cache的命中次数和未命中次数
	 protected long cacheHits, cacheMisses = 0L; 
	 //向哈希表中添加一个关键字为Key的缓存对象object
	 public synchronized void add(Object key, MessCacheable object) {
	  //先把原来的对象remove掉
	  remove(key);
	  int objectSize = object.getSize();
	  //如果对象太大，则不加入缓存
	  if (objectSize > maxSize * .90) {
	   return;
	  }
	  size += objectSize;
	  //新建一个缓存对象，并放入哈希表中
	 // MessCacheObject cacheObject = new MessCacheObject(object, objectSize);
	  //cachedObjectsHash.put(key, cacheObject);
	  // 把缓存元素的Key放到lastAccessed List链表的最前面
	 // LinkedListNode lastAccessedNode = lastAccessedList.addFirst(key);
	  //cacheObject.lastAccessedListNode = lastAccessedNode;
	  //把缓存元素的Key放到ageList链表的最前面,并记下当前时间
	  //LinkedListNode ageNode = ageList.addFirst(key);
	 // ageNode.timestamp = System.currentTimeMillis();
	  //cacheObject.ageListNode = ageNode;
	  // 在cullCache()中，先调用deleteExpiredEntries()把过期对象删掉，
	  //如果缓存还是太满，则调用  remove(lastAccessedList.getLast().object)把
	  //lastAccessedList中不常访问的对象删掉
	  //cullCache();
	 }
	 /**
	 * @param key
	 */
	private void remove(Object key) {
		// TODO Auto-generated method stub
		
	}
	//在哈希表中得到一个关键字为Key的缓存对象object
	 public synchronized MessCacheable get(Object key) {
	  // 清理过期对象
	  deleteExpiredEntries();
	  MessCacheObject cacheObject = (MessCacheObject)cachedObjectsHash.get(key);
	  if (cacheObject == null) {
	   //没找到则未命中次数加一
	   cacheMisses++;
	   return null;
	  }

	  //找到则命中次数加一
	  cacheHits++;
	  //将该缓存对象从lastAccessedList链表中取下并插入到链表头部
	  ((LinkedList) cacheObject.lastAccessedListNode).remove();
	  lastAccessedList.addFirst(cacheObject.lastAccessedListNode);
	  return cacheObject.object;
	 }


	/**
	 * 
	 */
	private void deleteExpiredEntries() {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see com.crm.web.cache.MessCacheable#getMess()
	 */
	public String getMess() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.crm.web.cache.MessCacheable#setMess()
	 */
	public String setMess() {
		// TODO Auto-generated method stub
		return null;
	}
	/* (non-Javadoc)
	 * @see com.crm.web.cache.MessCacheable#getSize()
	 */
	public int getSize() {
		// TODO Auto-generated method stub
		return 0;
	}

}
