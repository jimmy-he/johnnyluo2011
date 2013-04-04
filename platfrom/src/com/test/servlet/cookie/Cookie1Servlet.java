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
 * @ClassName:Cookie1Servlet.java					   		   
 * @author    罗拯   Email:java.luozheng@gmail.com 
 * @date      2013-3-30 下午3:43:24	  
 ***********************************************
 */
public class Cookie1Servlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Cookie cookie=null;

		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		Cookie[] cookies=request.getCookies();
		if(cookies!=null){
			for(int i=0;i<cookies.length;i++){
				out.println("Cookie name:"+cookies[i].getName());
				out.println("Cookie value:"+cookies[i].getValue());
				if(cookies[i].getName().equals("username"))
					cookie=cookies[i];
			 }	
			}else{
				out.println("No cookie");
			}if(cookie==null){
				cookie=new Cookie("username","Tom");
				cookie.setMaxAge(60*60);
				response.addCookie(cookie);
			}else if(cookie.getValue().equals("Tom")){
				cookie.setValue("Jake");
				response.addCookie(cookie);
			}else if(cookie.getValue().equals("Jake")){
			cookie.setMaxAge(0);
			response.addCookie(cookie);
			}
		
		}
		
	}


