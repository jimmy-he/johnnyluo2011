/**
 * 
 */
package com.crm.web.cache;

/**
 * 缓存接口
 *@author 罗尧  Email:j2ee.xiao@gmail.com
 *@version V1.0 2010-10-24   下午07:40:18
 */
public interface MessCacheable 
{
	public String setMess();
	
	public String getMess();

	/**
	 * @return
	 */
	public int getSize();
	
}

