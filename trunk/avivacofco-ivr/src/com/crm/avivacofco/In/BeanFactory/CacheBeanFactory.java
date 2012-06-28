/**
 * 
 */
package com.crm.avivacofco.In.BeanFactory;

import com.crm.avivacofco.In.CacheBean.AbstractCacheBean;
import com.crm.avivacofco.In.Imple.IvrWork;

/**
 *@author 罗尧 Email:j2ee.xiao@gmail.com
 *@version V1.0 2010-10-25 下午05:33:24
 */
public class CacheBeanFactory {

	public static AbstractCacheBean getCacheBeanInstance(String channelNo,String commandNo,String id_NO) {
		AbstractCacheBean catchBean = null;
		catchBean = new IvrWork(channelNo,commandNo,id_NO);
		return catchBean;
	}
}
