/**
 ***********************************************
 * @Title     StringAndArray.java					   
 * @Pageage   com.test.Standard			
 *Copyright:Copyright(c) 2013
 *Company:北京天润融通科技有限公司
 *	   
 * @author    罗尧   Email:j2ee.xiao@gmail.com 
 * @date       2013-3-21 下午4:31:10	
 * @version V1.0   
 ***********************************************
 */
package com.test.Standard;

/**
 ***********************************************
 * @ClassName:StringAndArray					   		   
 * @author    罗尧   Email:j2ee.xiao@gmail.com 
 * @date      2013-3-21 下午4:31:10	  
 ***********************************************
 */
public class StringAndArray {

	public static void main(String[] args) {
		
		/**
		 * String 类
		 * 说明、用法
		 */

		String str="abcdef";  //定义一个String类型的对象,str是一个对象，不是一个变量。字符串是常量，他们的值在创建之后不能改变
		System.out.println(str);
		
		System.out.println(str.length()); 		//计算出这个字符串的长度   字符串长度从1开始
		String a=str.substring(2,3);			//截取字符串
		System.out.println(a);
		/************************************************************************************************/
		//判断字符串的值是否相等、对象是否相等
		String str1="abc";
		String str2="bcd";
		if(str1.equals(str2)){
			System.out.println("值相等");
		}else{
			System.out.println("值不相等");
		}
		//判断字符串的对象是否是同一个
		if(str1==str2){
			System.out.println("对象相同");
		}else{
			System.out.println("对象不同同");
		}
		/************************************************************************************************/
		//把大写的字符串转换为小姐
		String str3="ABC";
		System.out.println(str3.toLowerCase());
		//把大写的字符串转换为小姐
		String str4="abc";
		System.out.println(str4.toUpperCase());
		
		//找到大写字母A
		String str5="fdafdsfguiouiAofdafd";
		System.out.println(str5.matches("A"));
	}

}
