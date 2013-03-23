package com.test.classes;

public class ClassTest {		//ClassTest类，class定义

	/**
	 * java的命名规则，要符合骆驼型      方法第一个字母小写 getX  类名第一个字母大写  
	 * java的类和对象，核心。方法、方法的调用；参数，带参数的方法，带返回值的方法；默认构造方法；this、static的使用
	 * 权限修饰符 public private  默认的   protected
	 * 一个类里面可以有多个方法，一个项目或程序从main方法开始执行
	 */
	/**
	 * 成员变量、方法都属于一个类的属性
	 * 方法的调用，首先要拿到类的对象，用对象去调用这个方法，或用对象去引用成员变量
	 */
	public void paoBu(){				//定义了一个方法，不带返回值，不带参数
		System.out.println("你好");	//方法内部为方法的实现
	}
	public void tiaoGao(int x){			//定义了一个方法，带参数，不带返回值
		System.out.println(x+100);
	}
	public int tiaoYuan(){				//定义了一个不带参数、带返回值的方法
		return 100;
	}
	public int getX(int x){
		return x+11;
	}
	public static void main(String[] args) {
		//方法的调用，首先要拿到类的对象，用对象去调用这个方法
		ClassTest classTest=new ClassTest();	//实例化、创建ClassTest类的对象 classTest
		classTest.paoBu();						//用实例化出来的classTest对象去调用setX（）方法
		
		classTest.tiaoGao(99);					//调用一个带参数的方法，需要在调用时传入参数，参数的类型必须一致
		
		int x=classTest.tiaoYuan();				//调用了一个带返回值的方法，我们定义一个和返回值类型一样的参数去接收这个返回值
		System.out.println("x="+x);
		
		int y=classTest.getX(90);				//调用了一个带参数并且带返回值的方法，调用的时候需传入参数
		System.out.println(y);
	}

}
