/**
 * 
 */
package com.jtapi.udp;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringBufferInputStream;
import java.io.StringReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 *@author 罗尧  Email:j2ee.xiao@gmail.com
 *@version V1.0 2011-3-3   上午09:47:10
 */
public class UDPCliento {
	public static void main(String args[]) throws Exception
	{
	    
	     DatagramSocket clientSocket = new DatagramSocket();
	     InetAddress IPAddress = InetAddress.getByName("192.168.30.68");
	     //InetAddress IPAddress = InetAddress.getByName("192.168.20.160");
	     String xString="fdasfdaf";

	     BufferedReader inFromUser =new BufferedReader(new StringReader(xString));
	     
	     byte[] sendData = new byte[1000];
	     byte[] receiveData = new byte[1024];
	     
	     String sentence = inFromUser.readLine();
	     sendData = sentence.getBytes();
	     DatagramPacket sendPacket =new DatagramPacket(sendData, sendData.length,IPAddress,2339);    
	     clientSocket.send(sendPacket);
	     
	     DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
	     clientSocket.receive(receivePacket);
	     String modifiedSentence=new String(receivePacket.getData());
	     
	     System.out.println("FROM SERVER:" + modifiedSentence);
	     clientSocket.close();
	     }
}
