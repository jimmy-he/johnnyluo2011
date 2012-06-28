package com.jtapi.call.demo;

import javax.telephony.*;
import javax.telephony.events.*;
import javax.telephony.callcontrol.*;
import javax.telephony.callcontrol.events.*;

public class Receiver extends Actor
{
	Address		address;
	StopSignal	stopSignal;
	Originator	originator;

	public Receiver ( Address address, Trace trace, int actionDelayMillis, Originator originator ) {
		super ( trace, address, actionDelayMillis );
		this.address = address;
		this.originator = originator;
	}

	protected final void metaEvent ( CallEv [] eventList ) {
		for ( int i = 0; i < eventList.length; i++ ) {
			TerminalConnection tc = null;
			try {
				CallEv curEv = eventList[i];

				if ( curEv instanceof CallCtlTermConnRingingEv ) {
					tc = ((CallCtlTermConnRingingEv)curEv).getTerminalConnection ();
					delay ( "answering等待。。。。。。" );
					bufPrintln ( "应答   终端连接 " + tc );
					tc.answer ();
					stopSignal.canStop ();
				}
			}
			catch ( Exception e ) {
				bufPrintln ( "捕获   异常 " + e );
				bufPrintln ( "tc = " + tc );
			}
			finally {
				flush ();
			}
		}
	}

	protected final void onStart () {
		stopSignal = new StopSignal ();
	}

	protected final void onStop () {
		stopSignal.stop ();
		Connection[] connections = address.getConnections ();
		try {
			if ( connections != null ) {
				for (int i=0; i< connections.length; i++ ) {
					connections[i].disconnect ();
				}
			}
		}catch ( Exception e ) {
			println (" Caught Exception " + e);
		}
	}

	protected final void fireStateChanged () {
		originator.setReceiverState ( state );
	}
}
