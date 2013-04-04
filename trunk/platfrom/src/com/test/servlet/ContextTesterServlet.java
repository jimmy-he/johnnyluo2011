package com.test.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 ***********************************************
 * @ClassName:ContextTesterServlet.java					   		   
 * @author    罗拯   Email:java.luozheng@gmail.com 
 * @date      2013-3-28 下午7:11:53	  
 ***********************************************
 */
public class ContextTesterServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//获得ServletContext 
		ServletContext context=getServletContext();
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html><head><title>FontSrevlet</title></head>");
		out.println("<body>");
		out.println("<br>Emali:"+context.getInitParameter("EmaliOFwebmaster"));
		out.println("<br>Path:"+context.getRealPath("/WEB-INF"));
		out.println("<br>MimeType:"+context.getMimeType("WEB-INF/web.xml"));
		out.println("<br>MajorVersion:"+context.getMajorVersion());
		out.println("<br>ServerInfo:"+context.getServerInfo());
		out.println("</body></html>");
		context.log("这是ContextTesterServlet输出的日志");
		out.close();
	}

}
