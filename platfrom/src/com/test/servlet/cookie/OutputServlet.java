package com.test.servlet.cookie;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 ***********************************************
 * @ClassName:OutputServlet.java					   		   
 * @author    罗拯   Email:java.luozheng@gmail.com 
 * @date      2013-3-30 下午6:05:38	  
 ***********************************************
 */
public class OutputServlet extends HttpServlet {
	public void service(ServletRequest request,ServletResponse response)
			throws ServletException,IOException{
		String message=(String)request.getAttribute("msg");
		PrintWriter out=response.getWriter();
		out.println(message);
		out.close();
				
	}

}
