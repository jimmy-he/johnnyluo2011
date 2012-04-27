package com.crm.framework.action;

import com.crm.framework.common.Global;
import com.crm.framework.common.enums.ClassType;
import com.crm.framework.common.util.StringUtil;

/**
 * 
 * @author 王永明
 * @since Apr 7, 2010 10:13:08 PM
 */
public class PageForward {
	public  static String getForward(Class actionClass, String methodName) {
		String dir=getDir(actionClass);
		String actionName=getModelName(actionClass,ClassType.action);
		return dir + "/" + actionName + StringUtil.firstUp(methodName) + "."
				+ Global.getConfig().getViewPostfix();
	}
	
	// 全局转发页面
	public static String getGlobalForward(String forward){
		for(GlobalForward gl:GlobalForward.values()){
			if(gl.name().equals(forward)){
				return gl.getPath();
			}
		}
		return forward;
		
	}
	
	public static void main(String[] args) {
		GlobalForward.valueOf("redirect");
	}

	
	/**获得class类对应jsp的目录*/
	public  static String getDir(Class actionClass) {
		String dir = StringUtil.getLastAfter(actionClass.getPackage().getName().replace(".action", ""), "com.crm");
		return dir.replace(".", "/");
	}
	
	/**获得class列对应的model名,首字母小写*/
	public static String getModelName(Class clazz,ClassType classType){
		return StringUtil.firstLower(StringUtil.getLastBefore(clazz.getSimpleName(), classType.getPostfix())) ;
	}
	
	/**将请求格式化
	 * @deprecated
	 * */
	public static String formatRequest(String requestStr){
		int index=requestStr.lastIndexOf('/');
		if(index==-1){
			throw new RuntimeException("请求格式不正确,无法格式化:"+requestStr);
		}
		
		//如果是以.jsp改成.action
		if(requestStr.endsWith(".jsp")){
			requestStr=requestStr.replace(".jsp", ".action");
		}
			
		if(!requestStr.endsWith(".action")){
			 return requestStr;
		}
		
		//将每个单词用'-'隔开
		String before=requestStr.substring(0,index);
		String end=requestStr.substring(index+1);
		end=StringUtil.pareseUpCase(end,"-");	
		return before+"/"+end;
	}
	
}
