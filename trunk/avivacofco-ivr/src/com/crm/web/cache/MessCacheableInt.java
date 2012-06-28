/**
 * 
 */
package com.crm.web.cache;

/**
 * 接口实现类
 *@author 罗尧 Email:j2ee.xiao@gmail.com
 *@version V1.0 2010-10-24 下午08:09:56
 */
public class MessCacheableInt implements  MessCacheable {
	private String parmtString;
	
	public MessCacheableInt(String parmtString) {
		this.parmtString = parmtString;
	}

	public String getString() {
		return parmtString;
	}

	/* (non-Javadoc)
	 * @see com.crm.web.cache.MessCache#getMess()
	 */
	public String getMess() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.crm.web.cache.MessCache#setMess()
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
