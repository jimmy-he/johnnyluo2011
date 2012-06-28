/**
 * 
 */
package com.jtapi.OutObserver.terminalObserver;

import javax.telephony.Terminal;
import javax.telephony.TerminalObserver;
import javax.telephony.events.TermEv;

import com.cisco.jtapi.extensions.CiscoTerminalObserver;

/**
 *@author 罗尧  Email:j2ee.xiao@gmail.com
 *@version V1.0 2011-3-8   下午05:59:05
 */
public class OutTerminalObserver implements TerminalObserver ,CiscoTerminalObserver{
	boolean terminalInService = false;
	public int actor_phonestate = 0;	
	/*
	 * Terminal
	 * 对于具体话机监控记录
	 * @see javax.telephony.TerminalObserver#terminalChangedEvent(javax.telephony.events.TermEv[])
	 */
	public void terminalChangedEvent(TermEv[] eventList) {
		// TODO Auto-generated method stub
		//System.out.println("TerminalEv.length:"+eventList.length);
		for (int i = 0; i < eventList.length; ++i) {
		//System.out.println("CiscoTermianlEv:"+eventList[i].getID());
		Terminal terminal = eventList[i].getTerminal();
		//System.out.println("CiscoTermianlEv:"+eventList[i].getID()+eventList[i].getTerminal().getName());
		/*
		switch (eventList[i].getID()) {
		case CiscoTermInServiceEv.ID://
			this.terminalInService = true;
			try {
					eventList[i].getTerminal().addCallObserver(new OutCallObserver());
				} catch (ResourceUnavailableException e) {
					e.printStackTrace();
				} catch (MethodNotSupportedException e) {
					e.printStackTrace();
				}
			actor_phonestate = 1;
			break;
		case CiscoTermOutOfServiceEv.ID:
			this.terminalInService = false;
			if (this.terminalInService) {
				eventList[i].getTerminal().removeCallObserver(new OutCallObserver());
				//removeAddrObserver();
			}
			actor_phonestate = 0;
			break;				
		case CiscoTermButtonPressedEv.ID:
			actor_phonestate = 4;
			System.out.println("电话按键中......");
			break;
		case CiscoTermDeviceStateActiveEv.ID:
			actor_phonestate = 2;
			System.out.println("电话就绪状态，一般为摘机操作！");
			break;
		case CiscoTermDeviceStateAlertingEv.ID:
			actor_phonestate = 5;
			System.out.println("电话响铃状态");
			break;
		case CiscoTermDeviceStateHeldEv.ID:
			actor_phonestate = 6;
			//向Held电话发送信息
			System.out.println("电话Held状态");
			break;
		case CiscoTermDeviceStateIdleEv.ID:
			actor_phonestate = 3;
			System.out.println("电话Idle状态");
			break;
		case CiscoRTPInputStartedEv.ID:
			actor_phonestate=10;
			System.out.println("电话通话");
			break;
		}
		*/
	}
		
		
		/*
		 * 下面这段代码拿取物理地址		 
		for ( int i=0; i<events.length; i++ ) {
			Terminal terminal = events[i].getTerminal ();
			switch ( events[i].getID () ) {
				case CiscoTermInServiceEv.ID:
					 System.out.println(( "Received " + events[i] + "for " + terminal.getName ()));
					 break;
				case CiscoTermOutOfServiceEv.ID:
					 System.out.println(( "Received " + events[i] + "for " + terminal.getName () ));
					 break;
			}
		}
		*/
	}

}
