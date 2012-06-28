/**
 * 
 */
package com.jtapi.OutObserver.callObserver;

import java.io.BufferedReader;
import java.io.StringReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.telephony.Address;
import javax.telephony.CallObserver;
import javax.telephony.TerminalConnection;
import javax.telephony.callcontrol.CallControlCallObserver;
import javax.telephony.callcontrol.events.CallCtlConnDialingEv;
import javax.telephony.callcontrol.events.CallCtlConnDisconnectedEv;
import javax.telephony.callcontrol.events.CallCtlConnEstablishedEv;
import javax.telephony.callcontrol.events.CallCtlConnFailedEv;
import javax.telephony.callcontrol.events.CallCtlTermConnBridgedEv;
import javax.telephony.callcontrol.events.CallCtlTermConnTalkingEv;
import javax.telephony.events.CallActiveEv;
import javax.telephony.events.CallEv;
import javax.telephony.events.CallInvalidEv;
import javax.telephony.events.CallObservationEndedEv;
import javax.telephony.events.ConnAlertingEv;
import javax.telephony.events.ConnConnectedEv;
import javax.telephony.events.ConnCreatedEv;
import javax.telephony.events.ConnDisconnectedEv;
import javax.telephony.events.ConnEv;
import javax.telephony.events.ConnFailedEv;
import javax.telephony.events.ConnInProgressEv;
import javax.telephony.events.ConnUnknownEv;
import javax.telephony.events.TermConnEv;

import com.cisco.jtapi.extensions.CiscoCall;
import com.cisco.jtapi.extensions.CiscoConsultCallActiveEv;

/**
 *@author 罗尧  Email:j2ee.xiao@gmail.com
 *@version V1.0 2011-3-9   上午10:35:27
 */
public class OutCallObserver implements CallObserver,CallControlCallObserver{

	/* (non-Javadoc)
	 * @see javax.telephony.CallObserver#callChangedEvent(javax.telephony.events.CallEv[])
	 */
	public void callChangedEvent(CallEv[] eventList) {
		try {
			for(int i=0;i<eventList.length;i++){
				
				CallEv curEv=eventList[i];	
				CiscoCall ciscoCall;
				if (curEv instanceof CallCtlConnDialingEv) {
					//拨号操作，拨打电话。去电！
					CallCtlConnDialingEv Ev = (CallCtlConnDialingEv) curEv;
					System.out.println("正在拨号..."+Ev.getCall()+Ev.getID()+Ev.getCause());
				}
				if(curEv instanceof CallCtlTermConnBridgedEv){
					System.out.println("接通电话。。。。。。。。。。。。。。");
				}
				
				
				/*
				 * 挂机时间，得到结束时间，并且UDP发送
				 */
				
				if (curEv instanceof CallInvalidEv) {
						
//					CallInvalidEv Ev=(CallInvalidEv) curEv;
//					System.out.println("挂机了，看谁先挂机:"+Ev.getCall()+Ev.getID()+Ev.getCause());
//					java.util.Date nowdate = new java.util.Date();
//		  			java.text.SimpleDateFormat sdDateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		  			String strNowTime = sdDateFormat.format(nowdate);
//		  			System.out.println("结束时间："+strNowTime);
//		  			
//		  			String stopmessage="A:B:C:D:E:F:[G]:STOPRECORD,"+"";
//		  			System.out.println("stopmessage="+stopmessage);
		  		/*
		  		 * UDP发送数据包
		  		 */
//		  			DatagramSocket clientSocket = new DatagramSocket();										  			
//		  		    InetAddress IPAddress = InetAddress.getByName("192.168.30.68");
//		  		    BufferedReader inFromUser =new BufferedReader(new StringReader(stopmessage));
//		  		     byte[] sendData = new byte[1000];
//		  		     byte[] receiveData = new byte[1024];
//		  		     
//		  		     String sentence = inFromUser.readLine();
//		  		     sendData = sentence.getBytes();
//		  		     DatagramPacket sendPacket =new DatagramPacket(sendData, sendData.length,IPAddress,2339);    
//		  		     clientSocket.send(sendPacket);
//		  		     clientSocket.close();
				}
				if(curEv instanceof ConnDisconnectedEv){
					
				}
				if(curEv instanceof ConnDisconnectedEv){
					ConnDisconnectedEv Ev=(ConnDisconnectedEv) curEv;
					ciscoCall = (CiscoCall)Ev.getCall();
					System.out.println(ciscoCall.getCalledAddress().getName()+"目标终端挂机");
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

}
