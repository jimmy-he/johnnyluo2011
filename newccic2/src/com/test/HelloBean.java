package com.test;

/**
 ***********************************************
 * @Title     HelloBean.java					   
 * @Pageage   com.test				   
 * @author    罗尧   Email:j2ee.xiao@gmail.com 
 * @since 1.0 创建时间 2012-6-27 下午04:24:14		   
 ***********************************************
 */
public class HelloBean implements Action{

	private String helloWord="hello";

	/**
	 * @return the helloWord
	 */
	public String getHelloWord() {
		return helloWord;
	}

	/**
	 * @param helloWord the helloWord to set
	 */
	public void setHelloWord(String helloWord) {
		this.helloWord = helloWord;
	}

	public String execute(String string) {
		
		return (getHelloWord()+string).toUpperCase();
	}
	
	
}
