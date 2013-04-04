package com.test.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 ***********************************************
 * @ClassName:Servlet00.java					   		   
 * @author    罗拯   Email:java.luozheng@gmail.com 
 * @date      2013-3-24 下午6:51:55	  
 ***********************************************
 */
public class Servlet00 extends HttpServlet {
	static int x=0;
	public void init() throws ServletException {
		
		System.out.println("init  网页被访问了："+x+"次");
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		       this.doPost(request, response);
   	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String userName=request.getParameter("username");
		out.print(userName+"123"+"</br>");
		out.print("</br>");
		String passWord=request.getParameter("password");
		out.print(passWord);
		out.print("调用了doPost方法");
		System.out.println("网页被访问了："+x+++"次");
		out.flush();
		out.close();
	}

}
