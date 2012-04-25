// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   NamingUtil.java

package com.tinet.ccic.wm.commons.util;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.Name;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * 命名工具类。
 *<p>
 * 文件名： NamingUtil.java
 *<p>
 * Copyright (c) 2006-2010 T&I Net Communication CO.,LTD.  All rights reserved.
 * @author 周营昭
 * @since 1.0
 * @version 1.0
 */

public final class NamingUtil {

	private static final Log log;

	public NamingUtil() {
	}

	public static InitialContext getInitialContext() throws NamingException {
		try {
			return new InitialContext();
		} catch (NamingException e) {
			log.error("Could not obtain initial context", e);
			throw e;
		}
	}

	public static void bind(Context ctx, String name, Object val)
			throws NamingException {
		try {
			log.trace("binding: " + name);
			ctx.rebind(name, val);
		} catch (Exception e) {
			Name n;
			for (n = ctx.getNameParser("").parse(name); n.size() > 1; n = n
					.getSuffix(1)) {
				String ctxName = n.get(0);
				Context subctx = null;
				try {
					log.trace("lookup: " + ctxName);
					subctx = (Context) ctx.lookup(ctxName);
				} catch (NameNotFoundException namenotfoundexception) {
				}
				if (subctx != null) {
					log.debug("Found subcontext: " + ctxName);
					ctx = subctx;
				} else {
					log.info("Creating subcontext: " + ctxName);
					ctx = ctx.createSubcontext(ctxName);
				}
			}

			log.trace("binding: " + n);
			ctx.rebind(n, val);
		}
		log.debug("Bound name: " + name);
	}

	public static void unbind(Context ctx, String name) throws NamingException {
		ctx.unbind(name);
		log.debug("unbind name:" + name);
	}

	static {
		log = LogFactory.getLog(com.tinet.ccic.wm.commons.util.NamingUtil.class);
	}
}
