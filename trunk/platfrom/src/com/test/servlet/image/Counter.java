package com.test.servlet.image;

/**
 ***********************************************
 * @ClassName:Counter.java					   		   
 * @author    罗拯   Email:java.luozheng@gmail.com 
 * @date      2013-3-30 下午4:33:19	  
 ***********************************************
 */
public class Counter {
	private int count;
	public Counter(){
		this(0);
	}
	public Counter(int count){
		this.count=count;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	public void add(int step){
		count+=step;
	}
}
