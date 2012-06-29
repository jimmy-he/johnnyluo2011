package com.newccic.framework.common.util.config;

/**
 ************************************************
 *@Title 	newccic2						
 *@Pageage 	com.newccic.common.util.config						
 *@author   罗尧  Email：j2ee.xiao@gmail.com		
 *@since	1.0  创建时间  2012-6-30  上午12:24:05		
 ************************************************
 */
public enum PathConfig {
	springDir("/spring"),//spring所在目录
	classPath("/"),//class文件所在路径
	webRoot(""),//webRoot
	springPath(""), 	
	modelClass("com.**.model");//要扫描的model类
	
	private String path;
	PathConfig(String path){
		this.path=path;
	}
	
	public String getPath(){
		return path;
	}
}
