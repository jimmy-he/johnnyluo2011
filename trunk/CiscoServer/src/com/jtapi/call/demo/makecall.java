package com.jtapi.call.demo;

import java.util.Enumeration;
import java.util.Vector;

import javax.telephony.JtapiPeer;
import javax.telephony.JtapiPeerFactory;
import javax.telephony.Provider;
import javax.telephony.ProviderObserver;
import javax.telephony.events.ProvEv;
import javax.telephony.events.ProvInServiceEv;

import com.cisco.cti.util.Condition;
import com.cisco.jtapi.extensions.CiscoJtapiVersion;

public class makecall extends TraceWindow implements ProviderObserver
{
	Vector		actors = new Vector ();
	Condition	conditionInService = new Condition ();
	Provider	provider;

	public makecall ( String [] args ) {

		super ( "makecall" + ": " + new CiscoJtapiVersion());
		try {

			System.out.println ( "Initializing Jtapi" );
			int curArg = 0;
			String providerName = args[curArg++];
			String login = args[curArg++];
			String passwd = args[curArg++];
			int actionDelayMillis = Integer.parseInt ( args[curArg++] );
			System.out.println("----111----"+actionDelayMillis);
			String src = null;
			String dest = null;

			JtapiPeer peer = JtapiPeerFactory.getJtapiPeer ( null );
			if ( curArg < args.length ) {

				String providerString = providerName + ";login=" + login + ";passwd=" + passwd;
				System.out.println ( "Opening " + providerString + "...\n" );
				provider = peer.getProvider ( providerString );
				provider.addObserver ( this );
				conditionInService.waitTrue ();

				System.out.println ( "Constructing actors" );

				for ( ; curArg < args.length; curArg++ ) {
					if ( src == null ) {
						src = args[curArg];
						System.out.println("主叫号码----"+src);
					}
					else {
						dest = args[curArg];
						System.out.println("被叫号码----"+dest);
						Originator originator = new Originator ( provider.getAddress ( src ), dest, (Trace)this, actionDelayMillis );
						System.out.println(originator);
						actors.addElement ( originator );
						actors.addElement (new Receiver ( provider.getAddress ( dest ), (Trace)this, actionDelayMillis, originator ));
						src = null;
						dest = null;
					}
				}
				if ( src != null ) {
					System.out.println ( "Skipping last originating address \"" + src + "\"; no destination specified" );
				}

			}

			Enumeration e = actors.elements ();
			while ( e.hasMoreElements () ) {
				Actor actor = (Actor) e.nextElement ();
				actor.initialize ();
			}

			Enumeration en = actors.elements ();
			while ( en.hasMoreElements () ) {
				Actor actor = (Actor) en.nextElement ();
				actor.start ();
			}
		}
		catch ( Exception e ) {
			System.out.println( "Caught exception " + e );
		}
	}

	public void dispose () {
		System.out.println ( "Stopping actors" );
		Enumeration e = actors.elements ();
		while ( e.hasMoreElements () ) {
			Actor actor = (Actor) e.nextElement ();
			actor.dispose ();
		}
	}

	public static void main ( String [] args )
	{
//		if ( args.length < 6 ) {
//			System.out.println ( "Usage: makecall <server> <login> <password> <delay> <origin> <destination> ..." );
//			System.exit ( 1 );
//		}
		String[] arg={"192.168.20.68","cti_1","123456","1000","2046","2045"};
		new makecall ( arg );
	}

	public void providerChangedEvent ( ProvEv [] eventList ) {
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

