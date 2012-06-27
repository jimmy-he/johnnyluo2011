package com.test;

/**
 ***********************************************
 * @Title     LowerHelloBean.java					   
 * @Pageage   com.test				   
 * @author    罗尧   Email:j2ee.xiao@gmail.com 
 * @since 1.0 创建时间 2012-6-27 下午04:40:18		   
 ***********************************************
 */
public class LowerHelloBean implements Action {

	private String message;
	
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	public String execute(String string) {
		return (getMessage()+string).toLowerCase();
	}

}
