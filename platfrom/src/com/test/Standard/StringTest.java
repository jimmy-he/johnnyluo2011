package com.test.Standard;

public class StringTest {

	
	public static void main(String[] args) {
		//String是什么？    String是一个类，字符串类型
		 String str = "hello java word!";			//定义了一个String类型的str对象
		 System.out.println(str.length());			//计算字符串的长度
		 
		 System.out.println(str.substring(6,7));	//截取字符串
		 
//		 String a="   java    ";
//		 System.out.println(a.trim());
		 System.out.println(str.toUpperCase());
		 
		 String a="java";
		 String b="se";
		 String c=a+b;
		 System.out.println(c);
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
//		 String str1= "abc";
//		 
//		 String a=new String();			//实例化一个对象      实例化一个String类的对象a
//		 a="abc";
//		 String b=new String();			//当两个String对象的值指向同一个的时候，对象的比较与值的比较可以认为是同一比较
//		 b="abd";
//		 System.out.println(str==str1);
//		 System.out.println(str.equals(str1));
//		 System.out.println("--------------");
//		 System.out.println(a==b);
//		 System.out.println(a.equals(b));


	}

}
