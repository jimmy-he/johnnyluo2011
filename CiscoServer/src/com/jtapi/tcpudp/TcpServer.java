/**
 * 
 */
package com.jtapi.tcpudp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *@author 罗尧  Email:j2ee.xiao@gmail.com
 *@version V1.0 2011-2-11   下午04:18:28
 */

public class TcpServer {
	//Tcp服务器端程序
	public static void main(String[] args) throws Exception {
		   ServerSocket ss = new ServerSocket(3000);
		   while(true){
		    Socket s = ss.accept();
		    new Thread(new Servicer(s)).start();
		   }
		}
		}
		class Servicer implements Runnable{
		Socket s;
		public Servicer(Socket s){
		   this.s = s;
		}
		public void run(){
		   try{
		    InputStream ips = s.getInputStream();
		    OutputStream ops = s.getOutputStream();
		   
		    BufferedReader br = new BufferedReader(new InputStreamReader(ips));
		    DataOutputStream dos = new DataOutputStream(ops);
		    while(true){
		     String strWord = br.readLine();
		     System.out.println(strWord);
		     if(strWord.equalsIgnoreCase("quit")){
		      break;
		     }
		     String strEcho = (new StringBuffer(strWord).reverse().toString());
		     dos.writeBytes(strWord + "------->" + strEcho + System.getProperty("line.separator"));
		    }
		    br.close();
		    dos.close();
		    s.close();
		   }catch(Exception e){
		    e.printStackTrace();
		   }
		}



}
