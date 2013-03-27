package com.test.classes;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 ***********************************************
 * @ClassName:RequestInfoServlet.java					   		   
 * @author    罗拯   Email:java.luozheng@gmail.com 
 * @date      2013-3-27 下午2:44:31	  
 ***********************************************
 */
public class RequestInfoServlet extends HttpServlet {	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=GBK");
		PrintWriter out = response.getWriter();
		out.println("<html><head><title>RequestInfo</title></head>");
		out.println("<body>");
		out.println("<br>LocalAddr:"+request.getLocalAddr());
		out.println("<br>LocalName:"+request.getLocalName());
		out.println("<br>LocalPort:"+request.getLocalPort());
		out.println("<br>Protocol:"+request.getProtocol());
		out.println("<br>RemoteAddr:"+request.getRemoteAddr());
		out.println("<br>RemoteHost:"+request.getRemoteHost());
		out.println("<br>RemotePort:"+request.getRemotePort());
		out.println("<br>Method:"+request.getMethod());
		out.println("<br>URI:"+request.getRequestURI());
		out.println("<br>ContextPath:"+request.getContextPath());
		out.println("<br>QueryString:"+request.getQueryString());
		out.println("<br>***打印HTTP请求头*****");
		Enumeration eu=request.getHeaderNames();
		while(eu.hasMoreElements()){
			String headerName=(String)eu.nextElement();
			out.println("<br>"+headerName+":"+request.getHeader(headerName));
		}
		out.println("<br>***打印HTTP请求头结束***<>");
		out.println("<br>username:"+request.getParameter("username"));
		out.println("</body></html>");
		out.close();
		
	}
}
