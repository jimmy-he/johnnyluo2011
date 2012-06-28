/**
 * 
 */
package com.crm.web.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *@author 罗尧  Email:j2ee.xiao@gmail.com
 *@version V1.0 2010-10-24   下午09:19:16
 */
public class CacheEntity {

	    private List globalList = new ArrayList();   
	    private Map map;   
	  
	    public Map getMap() {   
	        return map;   
	    }   
	  
	   public void setMap(Map map) {   
	        this.map = map;   
	    }   
	  
	    public List getGlobalList() {   
	        return globalList;   
	    }   
  
    public void setGlobalList(List globalList) {   
	        this.globalList = globalList;   
	   }  

	
}
