package com.newccic.test.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 ***********************************************
 * @Title     MyTest.java					   
 * @Pageage   com.newccic.test				   
 * @author    罗尧   Email:j2ee.xiao@gmail.com 
 * @since 1.0 创建时间 2012-11-28 下午05:11:39		   
 ***********************************************
 */
public class MyTest {

	  public static void main(String args[]){ 
	        try { 
	            //在RMI服务注册表中查找名称为RHello的对象，并调用其上的方法 
	            IHello rhello =(IHello) Naming.lookup("rmi://localhost:8888/RHello"); 
	            System.out.println(rhello.helloWorld()); 
	            System.out.println(rhello.sayHelloToSomeBody("罗尧")); 
	        } catch (NotBoundException e) { 
	            e.printStackTrace(); 
	        } catch (MalformedURLException e) { 
	            e.printStackTrace(); 
	        } catch (RemoteException e) { 
	            e.printStackTrace();   
	        } 
	    } 
}
