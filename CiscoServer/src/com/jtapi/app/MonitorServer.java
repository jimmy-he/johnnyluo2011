/**
 * 
 */
package com.jtapi.app;

import javax.telephony.Address;
import javax.telephony.Call;
import javax.telephony.JtapiPeer;
import javax.telephony.JtapiPeerFactory;
import javax.telephony.Provider;
import javax.telephony.ProviderObserver;
import javax.telephony.Terminal;
import javax.telephony.events.ProvEv;
import javax.telephony.events.ProvInServiceEv;

import org.apache.log4j.Logger;

import com.cisco.cti.util.Condition;
import com.cisco.jtapi.extensions.CiscoJtapiPeer;
import com.cisco.jtapi.extensions.CiscoJtapiProperties;
import com.cisco.jtapi.extensions.CiscoProvider;
import com.jtapi.OutObserver.addressObserver.OutAddressObserver;
import com.jtapi.OutObserver.callObserver.MyOutCallObserver;
import com.jtapi.OutObserver.terminalObserver.OutTerminalObserver;

/**
 *@author 罗尧  Email:j2ee.xiao@gmail.com
 *@version V1.0 2011-2-14   下午12:40:09
 */
public class MonitorServer implements ProviderObserver {
	static Logger logger = Logger.getLogger(MonitorServer.class.getName());
	String terminalLock = new String("terminalLock");
	Condition	conditionInService = new Condition ();
	Provider	provider;
	Terminal terminal;
	Address[] address;
	Call call;
	public MonitorServer(){		
		ProvideServer callServer=ProviderXmlConfigReader.getInstance().getCallServer();
		String providerName = callServer.getProviderName();
		String login = callServer.getLogin();
		String passwd = callServer.getPasswd();
		try {
			JtapiPeer peer = JtapiPeerFactory.getJtapiPeer ( null );
			String providerString = providerName + ";login=" + login + ";passwd=" + passwd;
			//System.out.println ( "Opening " + providerString + "...\n" );
			logger.info("Opening " + providerString + "...\n" );
			provider = peer.getProvider ( providerString );
			if(peer instanceof CiscoProvider)
			{
				CiscoJtapiProperties jProps = ((CiscoJtapiPeer) peer).getJtapiProperties();
				jProps.setUseJavaConsoleTrace(false);
				this.provider.addObserver(this);
				this.conditionInService.waitTrue();
			}
			//System.out.println("---------连接成功--------------->"+conditionInService.getCause());
			logger.info("---------连接成功--------------->"+conditionInService.getCause());
			//System.out.println("------连接成功,得到服务器IP----->"+provider.getName());
			logger.info("------连接成功,得到服务器IP----->"+provider.getName());
			//获取交换机上面所有的分机
			Thread.sleep(2000);	
			//provider.addObserver(this);
			//conditionInService.waitTrue ();
			address=provider.getAddresses();
			/*
			String[] callphone=MonitorServer.getCallXml();
			String addStrings=address.toString();
			System.out.println(addStrings);
			*/
			for(int i=0;i<address.length;i++){
				address[i].addObserver(new OutAddressObserver());
				address[i].addCallObserver(new MyOutCallObserver());
				//System.out.println("分机号码------->"+address[i]);
				logger.info("phone:--"+address[i]);
			}
			//Terminal terminal;
			Terminal[] tterminal=provider.getTerminals();
			if(tterminal==null){
				System.out.println("No Terminals on Address.");
				System.exit(0);
			}
			for(int j=0;j<tterminal.length;j++){
				//System.out.println(tterminal[j]);
				tterminal[j].addObserver(new OutTerminalObserver());
				//tterminal[j].addCallObserver(new MyOutCallObserver());
			}
			//System.out.println ( "-----------------------------开始监听----------------------" );
			logger.info("-----------------------------开始监听----------------------");			
		} catch (Exception e) {
			System.out.println( "Caught exception " + e );
		}
	}
/*
 * provider对象监控
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
