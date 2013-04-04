package com.test.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 ***********************************************
 * @ClassName:MainServlet.java					   		   
 * @author    罗拯   Email:java.luozheng@gmail.com 
 * @date      2013-3-31 下午2:19:29	  
 ***********************************************
 */
public class MainServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html><head><title>MainServlet</title></head>");
		out.println("<body>");
		ServletContext context=getServletContext();
		RequestDispatcher headDispatcher=context.getRequestDispatcher("/header.html");
		RequestDispatcher greeDispatcher=context.getRequestDispatcher("/greet");
		RequestDispatcher footerDispatcher=context.getRequestDispatcher("/footer.html");
		headDispatcher.include(request, response);
		greeDispatcher.include(request, response);
		footerDispatcher.include(request, response);
		out.println("</head></html>");
		out.close();
	}

}
