/**
 * 
 */
package com.jtapi.tcpudp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 *@author 罗尧  Email:j2ee.xiao@gmail.com
 *@version V1.0 2011-2-11   下午04:28:00
 */
public class UdpRecv {
	//接收程序
	public static void main(String[] args) throws Exception {
		   DatagramSocket ds = new DatagramSocket(3000);
		   byte[] buf = new byte[1024];
		   DatagramPacket dp = new DatagramPacket(buf,buf.length);
		   ds.receive(dp);
		   String str = new String(dp.getData(),0,dp.getLength());
		   System.out.println(str);
		   System.out.println("IP:" + dp.getAddress().getHostAddress() + ",PORT:" + dp.getPort());
		   //ds.close();
		}

}
