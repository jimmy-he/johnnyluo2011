/**
 * 
 */
package com.jtapi.OutObserver.addressObserver;

import javax.telephony.Address;
import javax.telephony.AddressObserver;
import javax.telephony.events.AddrEv;

import com.cisco.jtapi.extensions.CiscoAddrInServiceEv;
import com.cisco.jtapi.extensions.CiscoAddrOutOfServiceEv;
import com.cisco.jtapi.extensions.CiscoAddressObserver;

/**
 *@author 罗尧  Email:j2ee.xiao@gmail.com
 *@version V1.0 2011-3-8   下午05:56:58
 */
public class OutAddressObserver implements AddressObserver ,CiscoAddressObserver{

	/* 
	 * Address
	 * 对于分机进行监控
	 * @see javax.telephony.AddressObserver#addressChangedEvent(javax.telephony.events.AddrEv[])
	 */
	public void addressChangedEvent(AddrEv[] events) {
		// TODO Auto-generated method stub
		for ( int i=0; i<events.length; i++ ) {
			Address address = events[i].getAddress ();
			switch ( events[i].getID () ) {
				case CiscoAddrInServiceEv.ID:
					 //System.out.println(( "Received " + events[i] + "for "+ address.getName ()));
	    	     	 break;
				case CiscoAddrOutOfServiceEv.ID:
					 //System.out.println(( "Received " + events[i] + "for "+ address.getName ()));					 				 
					break;
			}
		}
	}

}
