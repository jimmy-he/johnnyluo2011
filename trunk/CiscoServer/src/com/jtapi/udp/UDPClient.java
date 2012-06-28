/**
 * 
 */
package com.jtapi.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

/**
 *@author 罗尧  Email:j2ee.xiao@gmail.com
 *@version V1.0 2011-3-3   上午09:36:22
 */
public class UDPClient extends Thread {
	 private static final int  PORT=10000 ;
	 private static final int  DATA_LEN=2046 ;
	 private byte  []buff =new byte[DATA_LEN];
	 private DatagramSocket socket ;
	 private DatagramPacket inpacket =new DatagramPacket(buff,buff.length);
	 private DatagramPacket outpacket ;
	 public void run() {
	  int i =0;
	  try{
	   socket=new DatagramSocket();
	   outpacket =new DatagramPacket(new byte[0] ,0,
	    InetAddress.getByName("192.168.30.68"),2339);
	   Scanner sc =new Scanner(System.in);
	   while(sc.hasNextLine()){
	    byte [] buff1 =sc.nextLine().getBytes();
	    outpacket.setData(buff1);
	    socket.send(outpacket);
	    socket.receive(inpacket);
	    System.out.println(new String(buff,0,inpacket.getLength()));
	   }
	  }catch(Exception e){
	   e.printStackTrace();
	  }
	 }	    
	 public static void main(String []args){
	  new UDPClient().start();
	 }
}
