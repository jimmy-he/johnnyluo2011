package com.test.servlet.image;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 ***********************************************
 * @ClassName:CounterServlet.java					   		   
 * @author    罗拯   Email:java.luozheng@gmail.com 
 * @date      2013-3-29 下午8:48:19	  
 ***********************************************
 */
/**
 * 明天问晓哥，代码明明跟书上写的一样为什么错
 * 
 */
public class CounterServlet extends HttpServlet {	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//获取ServletContext的引用
		ServletContext context=getServletContext();
		Counter counter=(Counter)context.getAttribute("counter");//从servletcontext中读取counter属性
		//如果ServletContext中没有counter属性，就创建counter属性
		if ( counter == null ){
			counter = new Counter(1);
			context.setAttribute("counter", counter);
		}
		String count=String.valueOf(counter.getCount());

		response.setContentType("text/html;charset=GB2312");
		PrintWriter out = response.getWriter();
		out.println("<html><head><title>CounterServlet</title><head>");
		out.println("<body>");
		String imageLink="<img src='a?count="+count+"'/>";
		out.println("欢迎光临本站。你是第"+imageLink+"位访问者。");
		out.println("</body></html>");
		counter.add(1);
		out.close();
	}

}
