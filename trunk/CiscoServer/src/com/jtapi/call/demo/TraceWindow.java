package com.jtapi.call.demo;


import java.awt.*;
import java.awt.event.*;

public class TraceWindow extends Frame implements Trace
{

	TextArea textArea;
	boolean	traceEnabled = true;
	StringBuffer buffer = new StringBuffer ();

	public TraceWindow (String name ) {
		super ( name );
		initWindow ();
	}

	public TraceWindow(){
		this("");
	}


	private void initWindow() {
		this.addWindowListener(new WindowAdapter () {
			public void windowClosing(WindowEvent e){
				dispose ();
				System.exit(0);
			}
		});
		textArea = new TextArea();
		setSize(400,400);
		add(textArea);
		setEnabled(true);
		this.show();

	}


	public final void bufPrint ( String str ) {
		if ( traceEnabled ) {
			buffer.append ( str );
			System.out.println("TraceWindow333----"+str);
		}

	}

	public final void print ( String str ) {
		if ( traceEnabled ) {
			buffer.append ( str );
			System.out.println("TraceWindow444----"+str);
			flush ();
		}
    }
    public final void print ( char character ) {
		if ( traceEnabled ) {
			buffer.append ( character );
			System.out.println("TraceWindow555----"+character);
			flush ();
		}
    }
    public final void print ( int integer ) {
		if ( traceEnabled ) {
			buffer.append ( integer );
			System.out.println("TraceWindow666----"+integer);
			flush ();
		}
    }
	public final void println ( String str ) {
		if ( traceEnabled ) {
			print ( str );
			System.out.println("TraceWindow777--"+str);
			print ( '\n' );
			flush ();
		}
    }
    public final void println ( char character ) {
		if ( traceEnabled ) {
			print ( character );
			System.out.println("TraceWindow888--"+character);
			print ( '\n' );
			flush ();
		}
    }
    public final void println ( int integer ) {
		if ( traceEnabled ) {
			print ( integer );
			System.out.println("TraceWindow999--"+integer);
			print ( '\n' );
			flush ();
		}
    }

    public final void setTrace ( boolean traceEnabled ) {
        this.traceEnabled = traceEnabled;
    }

	public final void flush () {
		if ( traceEnabled ) {
			textArea.append ( buffer.toString());
			buffer = new StringBuffer ();
		}
	}

    public final void clear () {

        textArea.setText("");
    }
}

