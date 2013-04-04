package com.test.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 ***********************************************
 * @ClassName:CheckServlet.java					   		   
 * @author    罗拯   Email:java.luozheng@gmail.com 
 * @date      2013-3-30 下午4:58:17	  
 ***********************************************
 */
public class CheckServlet extends HttpServlet {
	public void service(ServletRequest request,ServletResponse response)
			throws ServletException, IOException {
		String username=request.getParameter("username");
		String message=null;
		if(username==null){
			message="Please input username";
		}else{
			message="Hello"+username;
		}
		request.setAttribute("msg", message);
		ServletContext context=getServletContext();
		//下面一行代码是转发到output、
		RequestDispatcher dispatcher=context.getRequestDispatcher("/output");
		PrintWriter out=response.getWriter();
		out.println("Output from CheckServlet before forwarding request.");
		System.out.println("Output from CheckServlet before forwarding request.");
		
		dispatcher.forward(request, response);
		out.println("Output from CheckServlet before forwarding request.");
		System.out.println("Output from CheckServlet before forwarding request.");
		

		
	}

}
