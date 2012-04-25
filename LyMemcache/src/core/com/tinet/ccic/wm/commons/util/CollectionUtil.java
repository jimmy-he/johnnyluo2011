package com.tinet.ccic.wm.commons.util;
import java.lang.reflect.Array;
import java.util.Collection;

/**
 * 扩展自org.apache.commons.collections.CollectionUtils，提供一些集合类的工具函数。
 *<p>
 * 文件名： CollectionUtil.java
 *<p>
 * Copyright (c) 2006-2010 T&I Net Communication CO.,LTD.  All rights reserved.
 * @author 周营昭
 * @since 1.0
 * @version 1.0
 */
public class CollectionUtil extends org.apache.commons.collections.CollectionUtils {
    
	/**
	 * 从集合中获取唯一的对象。如果集合中有多于一个的对象，则抛出异常。
	 * @param <T>
	 * @param c
	 * @return
	 */
	@SuppressWarnings("unchecked")
    public static <T> T getFromUniqueCollection(Collection<T> c) {
		if (c == null || c.size() == 0) {
			return null;
		}
    	if (isUniqueCollection(c)) {
    		return (T)get(c, 0);
    	} else {
    		throw new RuntimeException("found more than one object in this collection, this collection size is: " + size(c));
    	}
    }
    
	/**
	 * 判断一个集合中是否有唯一的对象。
	 * 如果有多于一个的对象，则返回false，否则返回true。
	 * @param c 被判断的集合类。
	 * @return
	 */
	@SuppressWarnings("unchecked")
    public static boolean isUniqueCollection(Collection c) {
    	return (size(c) > 1) ? false : true;
    }
    
	/**
	 * 将一个集合转换成数组。
	 * @param <T>
	 * @param c
	 * @return
	 */
    @SuppressWarnings("unchecked")
	public static  <T> T[] toArray(Collection<T> c) {
    	if(c == null) {
    		return null;
    	} else {
    		T[] result = (T[]) Array.newInstance(c.getClass().getComponentType(), c.size());
    		int i = 0;
    		for (T r : c) {
    			result[i] = r;
    			i++;
    		}
    		return result;
    	}
    }

}
