package com.tinet.ccic.wm.commons.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

import com.tinet.ccic.wm.commons.Constants;

/**
 * List对象排序通用方法。
 * <p>
 * 	文件名： SortListUtil.java
 * <p>
 * Copyright (c) 2006-2010 T&I Net Communication CO.,LTD.  All rights reserved.
 * @author 娄雪
 * @since 1.0
 * @version 1.0
 */

public final class SortListUtil<E> extends org.apache.commons.collections.ListUtils{
	
	/**
	 * 对list中对象的某一属性排序
	 * @author 娄雪
	 * @param list list对象。
	 * @param method list中一个对象的某一属性的get方法。
	 * @param sort 排序规则，取值desc或asc，不区分大小写。
	 */
	@SuppressWarnings("unchecked")
	public void Sort(List<E> list, final String method, final String sort){
		Collections.sort(list, new Comparator() {			
		    public int compare(Object a, Object b) {
		    	int ret = 0;
		    	try{
			    	Method m1 = ((E)a).getClass().getMethod(method);
			    	Method m2 = ((E)b).getClass().getMethod(method);
			    	if (sort != null && Constants.ORDER_DECEND.equalsIgnoreCase(sort)) { // 倒序排序
			    		ret = m2.invoke(((E)b)).toString().compareTo(m1.invoke(((E)a)).toString());	
			    	} else { // 正序排序
			    		ret = m1.invoke(((E)a)).toString().compareTo(m2.invoke(((E)b)).toString());
			    	}
		    	}catch(NoSuchMethodException ne){
		    		ne.printStackTrace();
		    		System.out.println(ne);
				}catch(IllegalAccessException ie){
					ie.printStackTrace();
					System.out.println(ie);
				}catch(InvocationTargetException it){
					it.printStackTrace();
					System.out.println(it);
				}
		    	return ret;
		    }
		 });
	}
}

