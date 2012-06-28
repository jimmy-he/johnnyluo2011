/**
 * 
 */
package com.jtapi.tcpudp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 *@author 罗尧  Email:j2ee.xiao@gmail.com
 *@version V1.0 2011-2-11   下午04:25:56
 */
public class UdpSend {
	/*
	 * 测试要先运行接收程序,再运行发送程序.如果接收程序没有接收到数据,则会一直阻塞,
	 * 接收到数据后才会关闭程序.如果网络上没有数据发送过来,接收程序也没有阻塞,
	 * 通常都是使用了一个已经被占用的端口.
	 */
	//发送程序
	public static void main(String[] args) throws IOException {
		DatagramSocket ds = new DatagramSocket();
		InetAddress ip=InetAddress.getByName("172.16.35.109");
		int port=3000;
		   String starttime = "2010-02-11 16:33";
		   String stoptime  ="2010-02-11 16:40";
		   DatagramPacket dp = new DatagramPacket(starttime.getBytes(),starttime.length(),ip,port);
		   DatagramPacket dp1 = new DatagramPacket(stoptime.getBytes(),stoptime.length(),ip,port);
		   ds.send(dp);
		   ds.send(dp1);
		   //ds.close(); //关闭连接


	}

}
