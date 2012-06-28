/**
 * 
 */
package com.crm.web.cache;

/**
 * 存放缓存bean
 *@author 罗尧  Email:j2ee.xiao@gmail.com
 *@version V1.0 2010-10-24   下午07:46:09
 */
public class CacheData {
	 private Object data;
	    private long time;
	    private int count;
	    
	    public CacheData() {
	        
	    }
	    
	    public CacheData(Object data, long time, int count) {
	        this.data = data;
	        this.time = time;
	        this.count = count;
	    }
	    
	    public CacheData(Object data) {
	        this.data = data;
	        this.time = System.currentTimeMillis();
	        this.count = 1;
	    }
	    
	    public void addCount() {
	        count++;
	    }
	    
	    public int getCount() {
	        return count;
	    }
	    public void setCount(int count) {
	        this.count = count;
	    }
	    public Object getData(){
	        return data;
	    }
	    public void setData(Object data){
	        this.data = data;
	    }
	    public long getTime(){
	        return time;
	    }
	    public void setTime(long time){
	        this.time = time;
	    }

}
