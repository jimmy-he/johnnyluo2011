package com.crm.framework.web.crmtag;

import java.util.Enumeration;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author 王永明
 * @since Jul 30, 2009 11:09:25 AM
 * */
public class TagUtil {
	public static String getParams(ServletRequest servletRequest){
		Enumeration<String> enu=servletRequest.getParameterNames();
		StringBuilder sb=new StringBuilder();
		while(enu.hasMoreElements()){
			String name=enu.nextElement();
			String value=servletRequest.getParameter(name);
			name=name.replaceAll("#", "%23");
			sb.append(name+"="+value+"&");		
		}
		if(sb.length()>1){
			return sb.substring(0,sb.length()-1);
		}else{
			return "";
		}
		
	}
}	
