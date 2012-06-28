/**
 * 
 */
package com.crm.avivacofco.In.Test;

/**
 *@author 罗尧  Email:j2ee.xiao@gmail.com
 *@version V1.0 2010-11-26   下午01:48:21
 */
import java.util.*;
public class Mycomparator implements Comparator{



/* (non-Javadoc)
 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
 */
public int compare(Object o1, Object o2) {
	TestB p1=(TestB)o1;
	TestB p2=(TestB)o2; 
	if(p1.date<p2.date)
	  return 1;
	else
	  return 0;
	

}

}

