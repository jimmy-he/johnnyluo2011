package com.test.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 ***********************************************
 * @ClassName:DownloadSevlet.java					   		   
 * @author    罗拯   Email:java.luozheng@gmail.com 
 * @date      2013-3-29 下午1:43:40	  
 ***********************************************
 */
public class DownloadSevlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		OutputStream out;   //输出响应正文的输出流。。。output输出
		InputStream in;     //读取本地文件的输入流。。。。input输入
		//获得filename请求参数
		String filename=request.getParameter("filename");//filename：文件名
		if(filename==null){
			out=response.getOutputStream();
			out.write("Please input filename.".getBytes());
			out.close();
			return;
		}
		in=getServletContext().getResourceAsStream("/store"+filename);
		int length=in.available();
		response.setContentType("application/force-download");
		response.setHeader("Content-Lenath", String.valueOf(length));
		response.setHeader("Content-Disposition", "attachment;filename=\""+filename+"\"");
		
		out=response.getOutputStream();
		int bytesRead=0;
		byte[] buffer=new byte[512];
		while((bytesRead=in.read(buffer))!=-1){
			out.write(buffer, 0, bytesRead);
		}
		in.close();
		out.close();
		
	}

}
