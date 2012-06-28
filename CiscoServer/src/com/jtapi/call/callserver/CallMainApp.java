/**
 * 
 */
package com.jtapi.call.callserver;

import javax.telephony.Address;
import javax.telephony.Call;
import javax.telephony.JtapiPeer;
import javax.telephony.JtapiPeerFactory;
import javax.telephony.Provider;
import javax.telephony.ProviderObserver;
import javax.telephony.Terminal;
import javax.telephony.events.ProvEv;
import javax.telephony.events.ProvInServiceEv;

import com.cisco.cti.util.Condition;
import com.cisco.jtapi.extensions.CiscoJtapiPeer;
import com.cisco.jtapi.extensions.CiscoJtapiProperties;
import com.cisco.jtapi.extensions.CiscoProvider;
import com.jtapi.call.observer.MyOutCallObserver;

/**
 *@author 罗尧  Email:j2ee.xiao@gmail.com
 *@version V1.0 2011-3-24   下午12:20:32
 */
public class CallMainApp implements ProviderObserver{
	Condition	conditionInService = new Condition ();
	public static void main(String[] args) {

		// 产生一个Provider实例		 
		Provider myprovider = null;	
		String providerName="192.168.20.68";
		String login="cti_1";
		String passwd="123456";
		Condition	conditionInService = new Condition ();
		try {	 
			JtapiPeer peer = JtapiPeerFactory.getJtapiPeer(null);
			String providerString = providerName + ";login=" + login + ";passwd=" + passwd;
			System.out.println("Opening " + providerString + "...\n" );
			myprovider = peer.getProvider(providerString);	
			if(peer instanceof CiscoProvider)
			{
				CiscoJtapiProperties jProps = ((CiscoJtapiPeer) peer).getJtapiProperties();
				jProps.setUseJavaConsoleTrace(false);
				myprovider.addObserver(new CallMainApp());
				conditionInService.waitTrue();
			}
			//System.out.println("------连接成功,得到服务器IP----->"+provider.getName());
			System.out.println("------连接成功,得到服务器IP----->"+myprovider.getName());
			//获取交换机上面所有的分机
			Thread.sleep(2000);	
			
		} catch (Exception excp) {	 
			System.out.println("Can't get Provider: " + excp.toString());		 
			System.exit(0);		 
		}		 		 		 
		// 利用Provider实例产生一个和电话4761111的Address实例		 
		Address origaddr = null;		 
		Terminal origterm = null;		 
		try {		 
			origaddr = myprovider.getAddress("2045");		 		 		 
		//得到这个Address上绑定的Terminial(物理话机实例)列表，取出第一个。		 
		//这一操作，可以理解为对于电话4761111，有可能不止绑定了一部分机。		 
		//如果有若干分机，任意取出一个可用的进行后续操作。		 
			Terminal[] terminals = origaddr.getTerminals();
		 
		if (terminals == null) {
		 
			System.out.println("No Terminals on Address.");
		 
			System.exit(0);
		 
		}	 
			origterm = terminals[0];		 
		} catch (Exception excp) {		 
		//处理异常;	
			System.out.println("0");
		}		 		 		 		 		 
		//建立一个空的Call 对象		 
			Call mycall = null;		 
		try {		 
			mycall = myprovider.createCall();		 
		//在这个Call上绑定一个Observer，这个Observer可以		 
		//对整个外拨过程进行消息分析。（Observer的代码下文有讲解）
			mycall.addObserver(new MyOutCallObserver());		 
		} catch (Exception excp) {
		 
		//处理异常
		 System.out.println("1");
		}		 		 
		//进行外拨，所需的资源有刚才得到的Address、Terminal、Call对象		 
		//以及被叫号码
		try {		 
			mycall.connect(origterm, origaddr, "2046");		 
		} catch (Exception excp) {
		
		//处理异常
			 System.out.println("2");
		}


	}

	/* (non-Javadoc)
	 * @see javax.telephony.ProviderObserver#providerChangedEvent(javax.telephony.events.ProvEv[])
	 */
	public void providerChangedEvent(ProvEv[] eventList) {
		if ( eventList != null ) {
			for ( int i = 0; i < eventList.length; i++ )
			{
				if ( eventList[i] instanceof ProvInServiceEv ) {
					conditionInService.set ();
				}
			}
		}		
		
	}

}
