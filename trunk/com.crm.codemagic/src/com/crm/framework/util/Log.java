package com.crm.framework.util;

import java.util.List;

/**
 * @description
 * @author 王永明
 * @date Apr 23, 2009 12:48:54 PM
 */
public class Log {
	public static void debug(Object obj) {
		System.out.println("==================================================");
		System.out.println("");
		System.out.println("" + obj);
		System.out.println("");
		System.out.println("==================================================");
	}

	public static void ex() {
		StackTraceElement stack[] = (new Throwable()).getStackTrace();
		for (StackTraceElement st : stack) {
			String name = st.getClassName();
			if (name.startsWith("com.crm")) {
				String method = st.getMethodName();
				int no = st.getLineNumber();
				System.err.println(name + "." + method + ":" + no);
			}

		}
	}

	public static void debug(List list) {
		if (list == null)
			return;
		System.out.println("==================================================");
		for (Object o : list) {

			System.out.println(o);

		}
		System.out.println("==================================================");
	}
}
