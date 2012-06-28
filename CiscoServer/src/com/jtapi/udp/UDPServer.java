/**
 * 
 */
package com.jtapi.udp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 *@author 罗尧  Email:j2ee.xiao@gmail.com
 *@version V1.0 2011-3-3   上午09:36:35
 */
public class UDPServer {

	public static void main(String args[]) throws Exception
	{
	     DatagramSocket serverSocket = new DatagramSocket(2339);
	     byte[] receiveData = new byte[3];
	     byte[] sendData = new byte[1024];
	     while(true){
	         DatagramPacket receivePacket = 
	         new DatagramPacket(receiveData, receiveData.length);
	         serverSocket.receive(receivePacket);
	         String sentence = new String(receivePacket.getData());
	         InetAddress IPAddress = receivePacket.getAddress();
	         int port = receivePacket.getPort();
	         System.out.println("sentence = "+sentence);
	         try{
	     String temp = null;
	     File f = new File(sentence+".txt");
	     FileReader file = new FileReader(f);
	     BufferedReader in = new BufferedReader(file);
	     while((temp = in.readLine())!=null){
	      temp += '\n';
	      sendData = temp.getBytes();
	      DatagramPacket sendPacket = 
	           new DatagramPacket(sendData, sendData.length,IPAddress,port);
	      serverSocket.send(sendPacket);   
	    }
	    file.close();
	    in.close();
	      }catch(FileNotFoundException e1){
	      
	    } catch (IOException e) {
	     // TODO Auto-generated catch block
	      e.printStackTrace();
	    }
	     }
	}


}
