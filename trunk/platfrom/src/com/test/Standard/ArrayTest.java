package com.test.Standard;

public class ArrayTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String a[]={"123","abc","ABC","!!!"};	//定义数组   一个字符串类型的数组
		//1.打印数组中每个元素   2.打印第3个元素    3.求数组的长度    4.把数组中第1个值变为大写，然后打印出数组中每个元素
		for(int x=0;x<a.length;x++){
			System.out.println(a[x]);	
		}
		System.out.println(a[2]);
		System.out.println(a.length);

		System.out.println(a[1].toUpperCase());
		
	}

}
