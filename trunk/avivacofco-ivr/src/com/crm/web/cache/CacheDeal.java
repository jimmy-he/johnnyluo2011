/**
 * 
 */
package com.crm.web.cache;

/**
 * 核心处理代码
 *@author 罗尧  Email:j2ee.xiao@gmail.com
 *@version V1.0 2010-10-24   下午09:15:13
 */
public class CacheDeal {

	private CacheEntity cache = new CacheEntity();   
	  
	    private long expireTime = 1200000L;// 20分钟   
	  
	   private long maxCacheSize = 80L; // 最大缓存数量   
	  
	    private Long[] longArray = new Long[]{expireTime, maxCacheSize};   
	  
	    private long loadtime; // 加载时间   
	  
	    private static CacheDeal cacheDeal = null;   
	  
	    private JdomXmlOperator jdomXmlOperator = new JdomXmlOperator();   
	  
	   // private Reflection reflection = new Reflection();   
	 
	    private CacheDeal() {   
	  
	    }   
	    public synchronized static CacheDeal getInstance() {   
	       if (cacheDeal == null)   
	           cacheDeal = new CacheDeal();   
	       return cacheDeal;   
	    }   
	  
	    /**  
	     *  
	     * @param 实例对象  
	     * @param invokeFunction 表示要调用的函数名  
	     * @param []objValue数组  
     * @return  
	     * @throws Exception  
	    */  
	    public synchronized Object getListByParams(Object obj, String invokeFunction,   
	            Object[] objValue) throws Exception {   
	  
	       // longArray = jdomXmlOperator.getCacheConfig("cache.xml");   
	        expireTime = longArray[0];   
	        maxCacheSize = longArray[1] * 3;   
	  
	        StringBuilder sbuilder = new StringBuilder();   
	        sbuilder.append(obj.getClass());   
	        sbuilder.append(invokeFunction);   
	       sbuilder.append(CacheDeal.class.getMethod("getListByParams",new Class[]{Object.class, String.class, Object[].class}));   
	        if(objValue.length>0)   
	        {   
	            sbuilder.append(objValue.getClass()); // 被调用方法的参数类型   
	        }   
	        for (int i = 0; i < objValue.length; i++) {   
            sbuilder.append(objValue[i]);   
	        }   
	  
	        String signClass = sbuilder.toString();   
	  
	        int index = cache.getGlobalList().indexOf(signClass);   
	  
	        // 判断在缓存中存不存在，-1表示不存在，否者替换原来的值   
//	        if (cache.getGlobalList().contains(signClass)) {   
//	            loadtime = (Long) cache.getGlobalList().get(index + 1);   
//	            // 判断时间有没有超时   
//	            if (new Date().getTime() > loadtime + expireTime)   
//	            {   
//	              reflection.reflectionByParams(obj, invokeFunction, objValue,index - 1, "getListByParams",   
//	                        this, new Class[]{Object.class, String.class, Object[].class},cache);   
//	            }   
//	            else  
//	                return  cache.getGlobalList().get(index - 1);   
//	  
//	        }   
//	        else // 在缓存中不存在   
//	        {   
//	            if (cache.getGlobalList().size() >= maxCacheSize)   
//	            {   
//	               cache.getGlobalList().clear();   
//	           }   
//	           reflection.reflectionByParams(obj, invokeFunction, objValue, -1,"getListByParams",   
//	                    this, new Class[]{Object.class,String.class, Object[].class}, cache);   
//	       }   
//	  
	        // 刷新索引   
	       index = cache.getGlobalList().indexOf(signClass);   
	 
	       return cache.getGlobalList().get(index - 1);   
	    }   
	  
	   public synchronized void reLoadListByParams(Object obj,   
	            String invokeFunction, Object[] objValue) throws Exception {   
	  
	        //longArray = jdomXmlOperator.getCacheConfig("cache.xml");   
	        expireTime = longArray[0];   
	        maxCacheSize = longArray[1] * 3;   
	  
	       StringBuilder sbuilder = new StringBuilder();   
	        sbuilder.append(obj.getClass());   
	       sbuilder.append(invokeFunction);   
	        sbuilder.append(CacheDeal.class.getMethod("getListByParams",new Class[]{Object.class, String.class, Object[].class}));   
	       if(objValue.length>0)   
	       {   
	            sbuilder.append(objValue.getClass()); // 被调用方法的参数类型   
	        }   
	        for (int i = 0; i < objValue.length; i++) {   
	            sbuilder.append(objValue[i]);   
	        }   
	        String signClass = sbuilder.toString();   
	  
	        int index = cache.getGlobalList().indexOf(signClass);   
	  
	       // 判断在缓存中存不存在，-1表示不存在，否者替换原来的值   
//	        if (cache.getGlobalList().contains(signClass)) {   
//	            reflection.reflectionByParams(obj, invokeFunction, objValue,index - 1, "getListByParams",   
//	                    this, new Class[]{Object.class, String.class, Object[].class}, cache);   
//	 
//	        } else // 在缓存中不存在   
//	        {   
//	            if (cache.getGlobalList().size() >= maxCacheSize)   
//	            {   
//	                cache.getGlobalList().clear();   
//	            }   
//	            reflection.reflectionByParams(obj, invokeFunction, objValue, -1,"getListByParams",   
//	                    this, new Class[]{Object.class,String.class, Object[].class}, cache);   
//	        }   
	  
	   }   


}
