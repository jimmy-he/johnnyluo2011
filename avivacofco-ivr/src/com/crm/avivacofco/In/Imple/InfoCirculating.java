/**
 * 
 */
package com.crm.avivacofco.In.Imple;

import java.util.Comparator;

/**
 *@author 罗尧  Email:j2ee.xiao@gmail.com
 *@version V1.0 2010-11-26   下午05:50:07
 */
public class InfoCirculating implements Comparator{
	public int compare(Object o1, Object o2) {		
		PayBean payBean1=(PayBean)o1;
		PayBean payBean2=(PayBean)o2;
		if(payBean1.getDate()<payBean2.getDate())
			 return 1;
		else
			return 0;
	}
}
