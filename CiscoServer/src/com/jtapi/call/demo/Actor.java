package com.jtapi.call.demo;
import javax.telephony.*;
import javax.telephony.events.*;
import javax.telephony.callcontrol.*;
import javax.telephony.callcontrol.events.*;

import com.cisco.jtapi.extensions.*;
public abstract class Actor implements  AddressObserver, TerminalObserver, CallControlCallObserver, Trace
{
	/*
	 * CallControlCall这个不仅有基本的电话功能，还有特殊的会议电话、转接电话、监听电话等 功能。
	 * 同样呼叫中心专用包的电话对象CallCenterCall也是一样，它的特殊功能有预拨电话、得到用户数据等方法。
	 */

	public static final int ACTOR_OUT_OF_SERVICE = 0;
	public static final int ACTOR_IN_SERVICE =1;
	private Trace	trace;
	protected int		actionDelayMillis;   //传递进来的1000毫秒
	private Address	observedAddress;
	private Terminal observedTerminal;
	private boolean addressInService;
	private boolean terminalInService;
	protected int state = Actor.ACTOR_OUT_OF_SERVICE;

	public Actor( Trace trace, Address observed, int actionDelayMillis ) {
		this.trace = trace;
		this.observedAddress = observed;
		this.observedTerminal = observed.getTerminals ()[0];
		this.actionDelayMillis = actionDelayMillis;
	}

	public void initialize () {
		try {
			if ( observedAddress != null ) {
				bufPrintln ("增加 观察者的地址 "+ observedAddress.getName ());
				observedAddress.addCallObserver ( this );

				//Now add observer on Address and Terminal
				bufPrintln ("增加 观察者的地址"+ observedAddress.getName ());
				observedAddress.addObserver ( this );

				bufPrintln ("增加 终端的 观察者终端"+ observedTerminal.getName ());
				observedTerminal.addObserver ( this );
			}
		}
		catch ( Exception e ) {
		} finally {
			flush ();
		}
	}

	public final void start () {
			onStart ();
	}



	public final void dispose () {

		try {
			onStop ();
			if ( observedAddress != null ) {

					bufPrintln ("去除观察者地址 "+ observedAddress.getName ());
					observedAddress.removeObserver ( this );

					bufPrintln ("去除观察者地址 "+ observedAddress.getName ());
					observedAddress.removeCallObserver ( this );

			}
			if ( observedTerminal != null ){
					bufPrintln ("观察者去除从终端 "+ observedTerminal.getName ());
					observedTerminal.removeObserver ( this );
			}
		}
		catch ( Exception e ) {
			println ( "Caught exception " + e );
		}
		finally {
			flush ();
		}
	}

	public final void stop () {
		onStop ();
	}


	public final void callChangedEvent ( CallEv [] events ) {
		//
		// for now, all metaevents are delivered in the
		// same package...
		//
		metaEvent ( events );
	}

	public void addressChangedEvent ( AddrEv [] events ) {

		for ( int i=0; i<events.length; i++ ) {
			Address address = events[i].getAddress ();
			switch ( events[i].getID () ) {
				case CiscoAddrInServiceEv.ID:
					 bufPrintln ( "Received " + events[i] + "for "+ address.getName ());
					 addressInService = true;
					 if ( terminalInService ) {
						if ( state != Actor.ACTOR_IN_SERVICE ) {
							state = Actor.ACTOR_IN_SERVICE ;
							fireStateChanged ();
						}
					 }
	    	     	 break;
				case CiscoAddrOutOfServiceEv.ID:
					 bufPrintln ( "Received " + events[i] + "for "+ address.getName ());
					 addressInService = false;
 					 if ( state != Actor.ACTOR_OUT_OF_SERVICE ) {
						state = Actor.ACTOR_OUT_OF_SERVICE; // you only want to notify when you had notified earlier that you are IN_SERVICE
						fireStateChanged ();
					 }
					break;
			}
		}
		flush ();
	}

	public  void terminalChangedEvent ( TermEv [] events ) {

		for ( int i=0; i<events.length; i++ ) {
			Terminal terminal = events[i].getTerminal ();
			switch ( events[i].getID () ) {
				case CiscoTermInServiceEv.ID:
					 bufPrintln ( "Received " + events[i] + "for " + terminal.getName ());
					 terminalInService = true;
					 if ( addressInService ) {
						if ( state != Actor.ACTOR_IN_SERVICE ) {
							state = Actor.ACTOR_IN_SERVICE;
							fireStateChanged ();
						}
					 }
					 break;
				case CiscoTermOutOfServiceEv.ID:
					 bufPrintln ( "Received " + events[i] + "for " + terminal.getName () );
					 terminalInService = false;
					 if ( state != Actor.ACTOR_OUT_OF_SERVICE ) { // you only want to notify when you had notified earlier that you are IN_SERVICE
						state = Actor.ACTOR_OUT_OF_SERVICE;
						fireStateChanged ();
					 }
					 break;
			}
		}
		flush();
	}

	final void delay ( String action ) {
		if ( actionDelayMillis != 0 ) {
			println ( "等待 " + actionDelayMillis + " 毫秒  后 " + action );
			try {
				Thread.sleep ( actionDelayMillis );
			}
			catch ( InterruptedException e ) {}
		}
	}

	protected abstract void metaEvent ( CallEv [] events );

	protected abstract void onStart ();
	protected abstract void onStop ();
	protected abstract void fireStateChanged ();

	public final void bufPrint ( String string ) {
		trace.bufPrint ( string );
		System.out.println("Actor111"+string);
	}
	public final void bufPrintln ( String string ) {
		trace.bufPrint ( string );
		System.out.println("Actor222"+string);
		trace.bufPrint ("\n");
	}
	public final void print ( String string ) {
		trace.print ( string );
		System.out.println("Actor333"+string);
	}
	public final void print ( char character ) {
		trace.print ( character );
		System.out.println("Actor444"+character);
	}
	public final void print ( int integer ) {
		trace.print ( integer );
		System.out.println("Actor555"+integer);
	}
	public final void println ( String string ) {
		trace.println ( string );
		System.out.println("Actor666"+string);
	}
	public final void println ( char character ) {
		trace.println ( character );
		System.out.println("Actor777"+character);
	}
	public final void println ( int integer ) {
		trace.println ( integer );
		System.out.println("Actor888"+integer);
	}
	public final void flush () {
		trace.flush ();
		
	}
}
