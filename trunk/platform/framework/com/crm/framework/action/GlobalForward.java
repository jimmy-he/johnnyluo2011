package com.crm.framework.action;
/**
 * 
 * @author 王永明
 * @since Apr 8, 2010 1:30:13 PM
 */
public enum GlobalForward {
	error("/framework/template/errormsg.jsp"),
	json("/framework/template/json.jsp"),
	info("/framework/template/infomsg.jsp"),
	redirect("/framework/template/redirect.jsp");
	
	private String path;
	
	private GlobalForward(String  path){
		this.path=path;
	}

	public String getPath() {
		return path;
	}
	
	public String toString() {
		return path;
	}

}
