/**
 * 
 */
package com.jtapi.call.observer;

import javax.telephony.Address;
import javax.telephony.CallObserver;
import javax.telephony.events.AddrEv;
import javax.telephony.events.CallEv;
import javax.telephony.events.ConnAlertingEv;
import javax.telephony.events.ConnConnectedEv;
import javax.telephony.events.ConnDisconnectedEv;

/**
 *@author 罗尧  Email:j2ee.xiao@gmail.com
 *@version V1.0 2011-3-24   下午12:30:15
 */
public class MyOutCallObserver  implements CallObserver{

	/* (non-Javadoc)
	 * @see javax.telephony.CallObserver#callChangedEvent(javax.telephony.events.CallEv[])
	 */
	public void callChangedEvent(CallEv[] evlist) {
		for (int i = 0; i < evlist.length; i++) {
			 
			//判断是否得到有关Address的事件
			 
			if (evlist[i] instanceof AddrEv) {
			 
			//如果是有关于Address的事件，则利用这个事件获得相关的Address实例
			 
			String name = null;
			 
			try {
			 
			Address addr = ((AddrEv )evlist[i]).getAddress();
			 
			//从这个Address实例获取主叫号码
			 
			name = addr.getName();
			 
			} catch (Exception excp) {
			 
			//处理异常
			 
			}
			 
			String msg = "Connection to Address: " + name + " is ";
			 
			//分析这个事件的具体类型并进行相应处理
			 
			//这个事件属于“震铃”事件，进行相应处理。
			 
			if (evlist[i].getID() == ConnAlertingEv.ID) {
			 
			System.out.println(msg + "ALERTING");
			 
			}
			 
			//这个事件属于“来话接起”事件，进行相应处理。
			 
			else if (evlist[i].getID() == ConnConnectedEv.ID) {
			 
			System.out.println(msg + "CONNECTED");
			 
			}
			 
			//这个事件属于“挂机”事件，进行相应处理。
			 
			else if (evlist[i].getID() == ConnDisconnectedEv.ID) {
			 
			System.out.println(msg + "DISCONNECTED");
			 
			}
			 
			}
			 
			}

		
	}

}
