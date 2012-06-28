package com.crm.avivacofco.In.Test;

import java.util.HashMap;



public class Q {
	
			public static void main(String[] args) {
				String xString="2010年12月31%2121%bfdfdsa";
				String y=xString.split("\\%")[0];
				String yy=xString.split("\\%")[1];
				String yyy=xString.split("\\%")[2];
				System.out.println(y);
				System.out.println(yy);
				System.out.println(yyy);
				
				
				String xString2="3.850000000";
				System.out.println(xString2.substring(0,4));
			}
}
