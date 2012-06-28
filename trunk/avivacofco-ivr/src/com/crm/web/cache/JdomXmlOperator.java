/**
 * 
 */
package com.crm.web.cache;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.jdom.input.SAXBuilder;

/**
 *@author 罗尧  Email:j2ee.xiao@gmail.com
 *@version V1.0 2010-10-24   下午09:20:43
 */
public class JdomXmlOperator {
//
//	org.jdom.Document doc = null;   
//	    public JdomXmlOperator() {   
//	 
//  }   
	 
	  // private boolean isNumeric(String str) {   
	      //  Pattern pattern = Pattern.compile("[0-9]*");   
	       // Matcher isNum = pattern.matcher(str);   
	    //   return isNum.matches() ? true : false;
	  // }
	   // 改变属性节点的值   
//	    protected Long[] getCacheConfig(String filename) {   
//	  
//	        SAXBuilder sb = new SAXBuilder();// 建立构造器   
//	        try {   
//	  
//	            doc = sb.build(JdomXmlOperator.class.getClassLoader().getResourceAsStream(filename));// 读入指定文件   
//	        } catch (Exception e) {   
//	        }   
//	  
//	        org.jdom.Element root = doc.getRootElement();// 获得根节点   
//	        List list = root.getChildren();// 将根节点下的所有子节点放入List中   
//	       final String NULLSTR = "";   
//	  
//	        long expireTime = 1200000L;   
//	        long maxCacheSize = 80L;   
//	  
//	        String expireTimeByConfig = null;   
//	        String maxCacheSizeByConfig = null;   
//	  
//	        for (int i = 0; i < list.size(); i++) {   
//	  
//	            Element item = (Element) list.get(i);// 取得节点实例   
	  
	         //   if (item.getAttribute("expireTime") != null) {   
	         //       expireTimeByConfig = item.getAttribute("expireTime").getValue()   
	         //               .intern();// 取得属性值   
	        //    }   
	        //  if (item.getAttribute("maxCacheSize") != null) {   
	        //        maxCacheSizeByConfig = item.getAttribute("maxCacheSize")   
	         //              .getValue().intern();// 取得属性值   
           // }   
//	  
//	            if (expireTimeByConfig != null  
//	                    && (!NULLSTR.equals(expireTimeByConfig))) {   
	          //      if (this.isNumeric(expireTimeByConfig)) {   
             //       long longTime = Long.valueOf(expireTimeByConfig);   
	                    // 如果大于0，则使用配置的的刷新时间   
	           //         if (longTime >= 0) {   
	          //              expireTime = longTime * 60 * 1000;   
	                      
	          //      }   
	        //    }   
	  
//	            if (maxCacheSizeByConfig != null  
//	                    && (!NULLSTR.equals(maxCacheSizeByConfig))) {   
//	                if (this.isNumeric(maxCacheSizeByConfig)) {   
//	                    long cacheSize = Long.valueOf(maxCacheSizeByConfig);   
//	                    // 如果大于0，则使用配置的的刷新时间   
//	                    if (cacheSize >= 0) {   
//	                       maxCacheSize = cacheSize;   
//	                    }   
//	                }   
//	            }   
	  
//	            if (maxCacheSizeByConfig != null && expireTimeByConfig != null) {   
//	                break;   
//	            }   
//	        }   
//	        return new Long[]{expireTime, maxCacheSize};   
//	  
//	    }   
//	  


	
}
