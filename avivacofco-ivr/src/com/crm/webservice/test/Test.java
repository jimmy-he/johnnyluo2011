/**
 * 
 */
package com.crm.webservice.test;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;


/**
 *@author 罗尧  Email:j2ee.xiao@gmail.com
 *@version V1.0 2010-8-6   下午02:51:00
 */

	
public class Test {
	
	public static void main(String[] args) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> map1 = new HashMap<Object, Object>();
		Map<Object, Object> map2 = new HashMap<Object, Object>();
		String[] xStrings={"123","222","333","444"};
		String[] x1={"1231","2222","3333","4444"};
		String[] x2={"1231","2222","3333","4444"};
		map.put("li", xStrings);
		map1.put("li", x1);
		map2.put("li", x2);
		
		for(int i=1;i<=map.size();i++){
			for(int j=1;j<=map1.size();j++){
				System.out.println("---1-----"+map.get("li"));
			}
		}
		
		System.out.println(map.get("li"));
		System.out.println(map1.get("li"));
	}
}