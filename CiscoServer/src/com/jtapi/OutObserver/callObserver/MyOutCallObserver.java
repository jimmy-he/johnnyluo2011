/**
 * 
 */
package com.jtapi.OutObserver.callObserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.StringReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.telephony.Address;
import javax.telephony.CallObserver;
import javax.telephony.TerminalConnection;
import javax.telephony.callcontrol.CallControlCallObserver;
import javax.telephony.callcontrol.events.CallCtlConnDialingEv;
import javax.telephony.callcontrol.events.CallCtlConnEstablishedEv;
import javax.telephony.callcontrol.events.CallCtlConnFailedEv;
import javax.telephony.callcontrol.events.CallCtlConnOfferedEv;
import javax.telephony.callcontrol.events.CallCtlEv;
import javax.telephony.callcontrol.events.CallCtlTermConnDroppedEv;
import javax.telephony.callcontrol.events.CallCtlTermConnHeldEv;
import javax.telephony.callcontrol.events.CallCtlTermConnTalkingEv;
import javax.telephony.events.CallActiveEv;
import javax.telephony.events.CallEv;
import javax.telephony.events.CallInvalidEv;
import javax.telephony.events.ConnConnectedEv;
import javax.telephony.events.ConnCreatedEv;
import javax.telephony.events.ConnDisconnectedEv;
import javax.telephony.events.ConnFailedEv;
import javax.telephony.events.ConnInProgressEv;
import javax.telephony.events.TermConnCreatedEv;
import javax.telephony.events.TermConnDroppedEv;
import javax.telephony.events.TermConnRingingEv;

import org.apache.log4j.Logger;

import com.cisco.jtapi.extensions.CiscoCall;
import com.cisco.jtapi.extensions.CiscoConferenceEndEv;
import com.cisco.jtapi.extensions.CiscoConferenceStartEv;
import com.cisco.jtapi.extensions.CiscoConnection;
import com.cisco.jtapi.extensions.CiscoConsultCallActiveEv;
import com.cisco.jtapi.extensions.CiscoTransferStartEv;
import com.jtapi.app.MonitorServer;
import com.jtapi.app.UDPServer;
import com.jtapi.app.UDPXmlConfigReader;

/**
 *@author 罗尧  Email:j2ee.xiao@gmail.com
 *@version V1.0 2011-2-14   下午12:43:34
 */

//类MyOutCallObserver实现了接口CallObserver的若干方法
//  MyOutCallObserver对Call相关的各种事件进行了处理。
public  class MyOutCallObserver implements  CallObserver,CallControlCallObserver{
	static Logger logger = Logger.getLogger(MonitorServer.class.getName());
	public static InetAddress ip;
	public static BufferedReader brKey;
	public static DataOutputStream dos;
	public static BufferedReader brNet;
	UDPServer udpServer=UDPXmlConfigReader.getInstance().getUDPServer();
	String ipaddress=udpServer.getIPAddress();
	String  port=udpServer.getPort();
	int newport=Integer.parseInt(port);
	/*
	 * Call
	 * 全局监控call事件，根据事件类型获得相应的信息
	 * @see javax.telephony.CallObserver#callChangedEvent(javax.telephony.events.CallEv[])
	 */
	public void callChangedEvent(CallEv[] eventList) {
		try{
			int dropreason;
			//System.out.println("摘机了！："+eventList);
		for(int i=0;i<eventList.length;i++){
			//返回呼叫控制事件的具体原因
			if(eventList[i] instanceof CallCtlEv){
				CallCtlEv Ev = (CallCtlEv)eventList[i];
				dropreason = Ev.getCallControlCause();//这句话返回的是包的具体原因为事件A的呼叫控制，返回呼叫控制包的原因与事件关联
				//System.out.println("呼叫控制包的原因与事件关联的代码:"+dropreason);
			}
			//该ConnInProgressEv接口指示该对象连接状态已更改为Connection.IN_PROGRESS 。
			if(eventList[i] instanceof ConnInProgressEv){				
				ConnInProgressEv ccedev = (ConnInProgressEv)eventList[i];
				//System.out.println(ccedev.getConnection().getAddress()+"这个地方循环主被叫，其实主要起状态显示作用");
			}	
			CiscoCall ciscoCall;
			CallEv curEv=eventList[i];			
			if(curEv instanceof CallActiveEv){
				//获得Call对象，如果APP中不存在此对象则创建Call对象
				CallActiveEv Ev = (CallActiveEv) curEv;
				ciscoCall = (CiscoCall)Ev.getCall();
				//System.out.println(ciscoCall);
				//System.out.println(Ev.getID());
				//System.out.println("3");
				if (curEv instanceof CiscoConsultCallActiveEv)
		          {
					CiscoConsultCallActiveEv conneEv=(CiscoConsultCallActiveEv) curEv;
		            System.out.println(conneEv.getHeldTerminalConnection()+"old");    
		           
		          }
			}
			//ConnFailedEv接口指示该对象连接状态已更改为Connection.FAILED（失败、死亡） 
			//呼叫控制连接包状态的是现在CallControlConnection.FAILED（失败、死亡）
			if ((!(curEv instanceof ConnFailedEv))&& (!(curEv instanceof CallCtlConnFailedEv))) {
				Address addr ;
				//创建Connection对象
				if (curEv instanceof ConnCreatedEv) {
					//创建Connection对象
					//这个地方谁摘机，为谁创建连接状态，可以得到主叫号码
					ConnCreatedEv Ev = (ConnCreatedEv) curEv;
					addr = Ev.getConnection().getAddress();
					//System.out.println("主叫号码"+","+"摘机号码："+addr);
					
				}else if (curEv instanceof TermConnCreatedEv) {
					//创建TerminalConnection对象
					//将此对象添加到DevConnection中，记录TerminalConnection创建时间
					//将TerminalConnection和DevConnection添加到HashTable中
					TermConnCreatedEv Ev = (TermConnCreatedEv) curEv;
					//System.out.println("创建状态1-Ev.getTerminalConnection()"+Ev.getTerminalConnection());
					//System.out.println("创建状态2-Ev.getID()"+Ev.getID());
					//System.out.println("创建状态3-Ev.getCall()"+Ev.getCall());
			}else{
				//呼叫控制TerminalConnection包状态的是现在CallControlTerminalConnection.DROPPED （销毁、丢弃）
				if (curEv instanceof CallCtlTermConnDroppedEv) {
					//电话挂机！可能是拿起电话未拨，直接挂机；可能是电话结束后挂机！
					CallCtlTermConnDroppedEv Ev = (CallCtlTermConnDroppedEv) curEv;
					
					System.out.println("挂机"+Ev.getTerminalConnection().getTerminal().getName());
//					if (Ev.getTerminalConnection().getTerminal().getName().compareToIgnoreCase(this.devName) == 0) {
//						
//						
//						//根据每个TerminalConnection获得信息，保存通话记录
//						try{
//							String AddressName = "";
//							
//							//ciscoCall = (CiscoCall)this.getConnection().getCall();
//							
//							this.actor_phonestate=2;
//			                
//						}catch(Exception e){
//							e.printStackTrace();
//						}
//						//getSendClientInfo((CiscoCall)Ev.getCall(),devconn,ReleaseCall);
//						//savePhoneOption((CiscoCall)Ev.getCall(),devconn,ReleaseCall);
//						//挂断电话
//						
//					}
				}else 
					//对象状态的TerminalConnection已更改为TerminalConnection.DROPPED （销毁、丢弃）
				if (!(curEv instanceof TermConnDroppedEv)) 
				{
							//呼叫控制连接包状态的是现在CallControlConnection.OFFERING 
						if (curEv instanceof CallCtlConnOfferedEv) {
							//验证黑名单
							CallCtlConnOfferedEv Ev = (CallCtlConnOfferedEv) curEv;
							//isSelfEvent(Ev.getConnection());
							ciscoCall = (CiscoCall)Ev.getCall();
							String zString=ciscoCall.getCallingAddress().getName();
							String bString=ciscoCall.getCalledAddress().getName();
							//System.out.println("主叫号码："+zString+"被叫号码："+bString);
							logger.info("主叫号码："+zString+"被叫号码："+bString);
							CiscoConnection conn = (CiscoConnection)Ev.getConnection();
							//System.out.println("被叫的软电话信息："+conn);
							
						}else 
							//拨号事件
						if (curEv instanceof CallCtlConnDialingEv) {
							//拨号操作，拨打电话。去电！
							CallCtlConnDialingEv Ev = (CallCtlConnDialingEv) curEv;
							//System.out.println("正在拨号..."+Ev.getCall()+Ev.getID()+Ev.getCause());
							logger.info("正在拨号..."+Ev.getCall()+Ev.getID()+Ev.getCause());
						}else
							{
								if (curEv instanceof TermConnRingingEv) {
										//来电！
										TermConnRingingEv Ev = (TermConnRingingEv) curEv;
										//System.out.println("来电话啦："+Ev.getCall()+Ev.getID()+Ev.getCause());						
								}else 
								if (curEv instanceof CallInvalidEv) {
									CallInvalidEv Ev=(CallInvalidEv) curEv;
									System.out.println("挂机了，看谁先挂机:"+Ev.getCall()+Ev.getID()+Ev.getCause());
									java.util.Date nowdate = new java.util.Date();
						  			java.text.SimpleDateFormat sdDateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						  			String strNowTime = sdDateFormat.format(nowdate);
						  			//System.out.println("结束时间："+strNowTime);
						  			logger.info("结束时间："+strNowTime);
						  			ciscoCall = (CiscoCall)Ev.getCall();
						  			String zString=ciscoCall.getCallingAddress().getName();						  			
						  			String bString;
									if(ciscoCall.getCalledAddress()!=null&&!(ciscoCall.getCalledAddress()).equals("")){
										bString=ciscoCall.getCalledAddress().getName();
										
									}else {
										bString="";
									}
						  			String stopmessage="A:B:C:D:E:F:[G]:STOPRECORD,"+zString;
						  			String stopmessage1="A:B:C:D:E:F:[G]:STOPRECORD,"+bString;
						  			//System.out.println("stopmessage="+stopmessage);
						  			//System.out.println("stopmessage="+stopmessage1);
						  			logger.info("stopmessage="+stopmessage);
						  			logger.info("stopmessage="+stopmessage1);
						  		//UDP发送数据包
						  			DatagramSocket clientSocket = new DatagramSocket();										  			
						  		    InetAddress IPAddress = InetAddress.getByName(ipaddress);
						  		    byte[] sendData = new byte[1000];
						  		    byte[] receiveData = new byte[1024];
						  		    //主叫挂机
						  		    if(zString.length()==4){
						  		    BufferedReader inFromUser =new BufferedReader(new StringReader(stopmessage));
						  		    String sentence = inFromUser.readLine();
						  		    sendData = sentence.getBytes();
						  		    DatagramPacket sendPacket =new DatagramPacket(sendData, sendData.length,IPAddress,newport);
						  		    clientSocket.send(sendPacket);
						  		    }
						  		    //被叫挂机
						  		    if(bString.length()==4){
						  		    BufferedReader inFromUser1 =new BufferedReader(new StringReader(stopmessage1));						  		    						  		    
						  		    String sentence1 = inFromUser1.readLine();					  		     
						  		    sendData=sentence1.getBytes();
						  		    DatagramPacket sendPacket1 =new DatagramPacket(sendData, sendData.length,IPAddress,newport);    
						  		    clientSocket.send(sendPacket1);
						  		    }
						  		    clientSocket.close();
						  		     
								}else{
										if(curEv instanceof CiscoConferenceStartEv){
													CiscoConferenceStartEv Ev = (CiscoConferenceStartEv)curEv;
													//System.out.println("这些地方都为获得状态属性:"+Ev.getCall()+Ev.getID()+Ev.getCause());
													
										}else if(curEv instanceof CiscoTransferStartEv){
													CiscoTransferStartEv Ev = (CiscoTransferStartEv)curEv;
													//System.out.println("这些地方都为获得状态属性:"+Ev.getCall()+Ev.getID()+Ev.getCause());
										}
										else if (curEv instanceof CiscoConferenceEndEv) {
													CiscoConferenceEndEv Ev = (CiscoConferenceEndEv) curEv;
													//System.out.println("这些地方都为获得状态属性:"+Ev.getCall()+Ev.getID()+Ev.getCause());
										}else if (curEv instanceof CallCtlTermConnTalkingEv) {
													CallCtlTermConnTalkingEv Ev = (CallCtlTermConnTalkingEv) curEv;
													//System.out.println("这些地方都为获得状态属性:"+Ev.getCall()+Ev.getID()+Ev.getCause());
													//摘机，记时间													
										}else if(curEv instanceof ConnConnectedEv){
													ConnConnectedEv Ev = (ConnConnectedEv)curEv;
													//System.out.println("这些地方都为获得状态属性:"+Ev.getCall()+Ev.getID()+Ev.getCause());
										}else if(curEv instanceof CallCtlTermConnHeldEv){
													CallCtlTermConnHeldEv Ev =(CallCtlTermConnHeldEv)curEv;
													//System.out.println("这些地方都为获得状态属性:"+Ev.getCall()+Ev.getID()+Ev.getCause());
													
												}
											//接听，并建立通话
											//该CallCtlConnEstablishedEv接口表明，呼叫控制连接包状态的是现在CallControlConnection.ESTABLISHED(建立、接通)
										else if (curEv instanceof CallCtlConnEstablishedEv) 
												{
											
													//终端操作完成!
													//可能是源地址事件也可能是目标地址事件
													CallCtlConnEstablishedEv Ev1 = (CallCtlConnEstablishedEv) curEv;
													//TerminalConnection[] mTerminalConns = Ev1.getConnection().getTerminalConnections();
													//TerminalConnection[] mTerminalConns = Ev1.getConnection().getAddress().getName();
													//System.out.println(mTerminalConns.toString());
													ciscoCall = (CiscoCall)Ev1.getCall();
													//System.out.println(ciscoCall.getCallingAddress()+"---"+ciscoCall.getCalledAddress()+"成功建立通话！");
													logger.info(ciscoCall.getCallingAddress().getName()+"---"+ciscoCall.getCalledAddress().getName()+"成功建立通话！");
													String startmessage=null;
													String startmessage1 = null;
													String zString=ciscoCall.getCallingAddress().getName();
													String bString=ciscoCall.getCalledAddress().getName();
													logger.info("主叫号码："+zString+"被叫号码："+bString);
													java.util.Date nowdate = new java.util.Date();
										  			java.text.SimpleDateFormat sdDateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
										  			String strNowTime = sdDateFormat.format(nowdate);
										  			logger.info("开始时间："+strNowTime);
										  			
										  			startmessage="A:B:C:D:E:F:[G]:STARTRECORD,!@$,|CPID|"+zString+"|YY|ZZ|"+zString+"|"+bString+"|"+strNowTime;
												
										  			startmessage1="A:B:C:D:E:F:[G]:STARTRECORD,!@$,|CPID|"+bString+"|YY|ZZ|"+zString+"|"+bString+"|"+strNowTime;
													
										  			 
										  			//System.out.println("startmessage="+startmessage);
										  			//System.out.println("startmessage1="+startmessage1);
										  			logger.info("startmessage="+startmessage);
										  			logger.info("startmessage="+startmessage1);
										  			//UDP发送数据包
										  			DatagramSocket clientSocket = new DatagramSocket();										  			
										  		    InetAddress IPAddress = InetAddress.getByName(ipaddress);
										  		    byte[] sendData = new byte[1000];
										  		    byte[] receiveData = new byte[1024];
										  		    //发送主叫分机号
										  		    if(zString.length()==4){
										  		    BufferedReader inFromUser =new BufferedReader(new StringReader(startmessage));
										  		    String sentence = inFromUser.readLine();
										  		    sendData = sentence.getBytes();
										  		    DatagramPacket sendPacket =new DatagramPacket(sendData, sendData.length,IPAddress,newport);
										  		    clientSocket.send(sendPacket);
										  		    }
										  		    if(bString.length()==4){
//										  		    //发送被叫分机号
										  		    BufferedReader inFromUser1 =new BufferedReader(new StringReader(startmessage1));	  		     
										  		    String sentence1 = inFromUser1.readLine();
										  		    sendData =sentence1.getBytes();	
										  		    DatagramPacket sendPacket1 =new DatagramPacket(sendData, sendData.length,IPAddress,newport);
										  		    clientSocket.send(sendPacket1);
										  		    }
										  		     clientSocket.close();
										  		     //DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
										  		     //clientSocket.receive(receivePacket);
										  		     //String modifiedSentence=new String(receivePacket.getData());
										  	     
										  		     //System.out.println("FROM SERVER:" + modifiedSentence);
										  		    // clientSocket.close();
										  		     
										  		     
										  		     
													boolean mSelfConn = false;									
													//call = (CiscoCall)(this.app.get_CallData(Ev1.getCall(), false)).call;
													//if(call!=null){
														if (!(mSelfConn)) 
														{

															ciscoCall = (CiscoCall)Ev1.getConnection().getCall();
																				
														}
													
												}
									}
								}
				}
				else {
					if(curEv instanceof ConnDisconnectedEv){
						
						ConnDisconnectedEv Ev = (ConnDisconnectedEv)curEv;
						TerminalConnection[] tc_array = Ev.getConnection().getTerminalConnections();
						for(int it=0;it<tc_array.length;it++){
							
						}
					}
				}
			
			}
						
			}
			else 
			{			
			//这个号码不在cti注册内
				if((curEv instanceof CallCtlConnFailedEv))
				{
					ciscoCall = (CiscoCall)curEv.getCall();
					if(ciscoCall.getCalledAddress()==null)
					{
						//System.out.println("无被叫号码！");
						logger.debug("无被叫号码！此电话号码错误");
					}else
					{
						System.out.println("电话："+ciscoCall.getCalledAddress().getName()+"未接通");
					}
				}
			}
		 }
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	} 
}
