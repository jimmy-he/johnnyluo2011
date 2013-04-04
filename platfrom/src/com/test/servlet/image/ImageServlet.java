package com.test.servlet.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 ***********************************************
 * @ClassName:ImageServlet.java					   		   
 * @author    罗拯   Email:java.luozheng@gmail.com 
 * @date      2013-3-29 下午7:47:53	  
 ***********************************************
 */
public class ImageServlet extends HttpServlet {
	private Font font=new Font("Courier",Font.BOLD,12);     //字体
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String count=request.getParameter("count");//parameter:参数
		if(count==null) count="1";
		int len=count.length();         //数字的长度

		response.setContentType("image/jpeg");//设定响应正文的类型
		ServletOutputStream out=response.getOutputStream();
		//创建一个位于缓冲中的图像，长和高
		BufferedImage image=new BufferedImage(11*len, 16, BufferedImage.TYPE_INT_BGR);
		Graphics g=image.getGraphics();   //获得graphice画笔
	    g.setColor(Color.black);
	    g.fillRect(0, 0, 11*len, 16);//画一个黑色矩形，并设置其大小，长和高
	    g.setColor(Color.white);
	    g.setFont(font);
	    char c;
	    for(int i=0;i<len;i++){
	    	c=count.charAt(i);
	    	g.drawString(c+"", i*11+1, 12);           //画一个白色的数字
	    	g.drawLine((i+1)*11-1, 0, (i+1)*11-1, 16);//画一条白色的竖线
	    }
	    JPEGImageEncoder encoder=JPEGCodec.createJPEGEncoder(out);
	    encoder.encode(image);
		out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
