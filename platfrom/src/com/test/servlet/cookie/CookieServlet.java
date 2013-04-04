package com.test.servlet.cookie;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 ***********************************************
 * @ClassName:CookieServlet.java					   		   
 * @author    罗拯   Email:java.luozheng@gmail.com 
 * @date      2013-3-30 下午3:23:40	  
 ***********************************************
 */
public class CookieServlet extends HttpServlet {
	int count=0;
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		//获取HTTP请求中的所有Cookie
		Cookie[] cookies=request.getCookies();
		if(cookies!=null){
			//循环遍历，访问每一个Cookie
			for(int i=0;i<cookies.length;i++){
				out.println("Cookie name:"+cookies[i].getName());
				out.println("Cookie value:"+cookies[i].getValue());
				out.println("Max Age:"+cookies[i].getMaxAge()+"\r\n");
			}
		}else{
			out.println("No cookie");
		}
		//向客服端写一个Cookie
		response.addCookie(new Cookie("cookieName"+count,"cookieValue"+count));
		count++;
	}

}
