package com.test.servlet;

import java.io.IOException;

import javax.servlet.GenericServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 ***********************************************
 * @ClassName:DispatcherServlet.java					   		   
 * @author    罗拯   Email:java.luozheng@gmail.com 
 * @date      2013-3-24 下午3:47:38	  
 ***********************************************
 */
public class DispatcherServlet extends GenericServlet  {

	private String target="/hello.jsp";
	public void service (ServletRequest request,ServletResponse response){
		
		String userName=request.getParameter("username");
		String passWord=request.getParameter("password");
		
		request.setAttribute("USER", userName);
		request.setAttribute("PASSWOED", passWord);
		
		ServletContext context=getServletContext();
		
		RequestDispatcher dispatcher=context.getRequestDispatcher(target);
		try {
			dispatcher.forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}
