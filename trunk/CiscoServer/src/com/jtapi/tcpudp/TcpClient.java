/**
 * 
 */
package com.jtapi.tcpudp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *@author 罗尧  Email:j2ee.xiao@gmail.com
 *@version V1.0 2011-2-11   下午04:48:03
 */
public class TcpClient {
	public static InetAddress ip;
	public static BufferedReader brKey;
	public static DataOutputStream dos;
	public static BufferedReader brNet;
	static {	
		try {
			ip = InetAddress.getByName("172.16.35.109");
			int port=3000;
			   Socket s = new Socket(ip,port);
			   InputStream ips = s.getInputStream();
			   OutputStream ops = s.getOutputStream();
			    brKey = new BufferedReader(new InputStreamReader(System.in));
			    dos = new DataOutputStream(ops);
			    brNet = new BufferedReader(new InputStreamReader(ips));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	//TCP客户端程序
	public static void main(String[] args) throws Exception{	  
		  while(true){
		    //String strWord = brKey.readLine();
		    //String xString="123qwe";
			    String strWord = brKey.readLine();
			    dos.writeBytes(strWord + System.getProperty("line.separator"));	
			    System.out.println("被叫号码:"+strWord + System.getProperty("line.separator"));
		    if("quit".equalsIgnoreCase(strWord)){
		     break;
		    }else{
		    	System.out.println("被叫"+brNet.readLine());
		    }
		  }
		   dos.close();
		   brNet.close();
		   brKey.close();
		  // s.close();
		}
}
