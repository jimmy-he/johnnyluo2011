package com.test;

class Person
{
	private String name;
	private int age;

	public void setName(String n){
		name=n;
	}
	public void setAge(int a){
		age=a;
	}
	public String getName(){
		return name;
	}
	public int getAge(){
		return age;
	}
	public String shout(){
		return  "姓名"+this.name+"\n年龄"+this.age;
	}
};

public class LzTest
{
	public static void main(String args[]){
		Person pen=new Person();
		pen.setName("张三");
		pen.setAge(21);
		System.out.println(pen.shout());
	}
}