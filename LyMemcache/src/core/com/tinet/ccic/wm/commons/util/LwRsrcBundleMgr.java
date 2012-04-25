// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   LwRsrcBundleMgr.java

package com.tinet.ccic.wm.commons.util;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;
/**
 * 国际化相关热加载
 *<p>
 * 文件名： LwRsrcBundleMgr.java
 *<p>
 * Copyright (c) 2006-2010 T&I Net Communication CO.,LTD.  All rights reserved.
 * @author 周营昭
 * @since 1.0
 * @version 1.0
 */
public class LwRsrcBundleMgr implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static boolean DEBUG = false;
	private static LwRsrcBundleMgr g_rsrcMgr = new LwRsrcBundleMgr();
	private Locale m_locale;
	private ClassLoader m_classLoader;

	public LwRsrcBundleMgr() {
		m_classLoader = null;
		m_locale = Locale.getDefault();
	}

	public LwRsrcBundleMgr(Locale locale) {
		m_classLoader = null;
		m_locale = locale;
	}

	public static LwRsrcBundleMgr getResourceBundleManager() {
		return g_rsrcMgr;
	}

	public void setLocale(Locale locale) {
		m_locale = locale;
	}

	public Locale getLocale() {
		return m_locale;
	}

	public void setClassLoader(ClassLoader classloader) {
		m_classLoader = classloader;
	}

	public ClassLoader getClassLoader() {
		return m_classLoader;
	}

	public String buildMessage(String bdlid, String msgid) {
		ResourceBundle resourcebundle;
		if (m_classLoader != null)
			resourcebundle = getResourceBundle(bdlid, m_locale, m_classLoader);
		else
			resourcebundle = getResourceBundle(bdlid, m_locale);
		return getMessage(resourcebundle, msgid);
	}

	public String buildMessage(String bdlid, String msgid, String param) {
		ResourceBundle resourcebundle;
		if (m_classLoader != null)
			resourcebundle = getResourceBundle(bdlid, m_locale, m_classLoader);
		else
			resourcebundle = getResourceBundle(bdlid, m_locale);
		return getMessage(resourcebundle, msgid, param);
	}

	public String buildMessage(String bdlid, String msgid, String param1,
			String param2) {
		ResourceBundle resourcebundle;
		if (m_classLoader != null)
			resourcebundle = getResourceBundle(bdlid, m_locale, m_classLoader);
		else
			resourcebundle = getResourceBundle(bdlid, m_locale);
		return getMessage(resourcebundle, msgid, param1, param2);
	}

	public String buildMessage(String bdlid, String msgid, String params[]) {
		ResourceBundle resourcebundle;
		if (m_classLoader != null)
			resourcebundle = getResourceBundle(bdlid, m_locale, m_classLoader);
		else
			resourcebundle = getResourceBundle(bdlid, m_locale);
		return getMessage(resourcebundle, msgid, params);
	}

	public String buildMessage(String bdlid, String msgid, Locale locale) {
		ResourceBundle resourcebundle;
		if (m_classLoader != null)
			resourcebundle = getResourceBundle(bdlid, locale, m_classLoader);
		else
			resourcebundle = getResourceBundle(bdlid, locale);
		return getMessage(resourcebundle, msgid);
	}

	public String buildMessage(String bdlid, String msgid, Locale locale,
			String param) {
		ResourceBundle resourcebundle;
		if (m_classLoader != null)
			resourcebundle = getResourceBundle(bdlid, locale, m_classLoader);
		else
			resourcebundle = getResourceBundle(bdlid, locale);
		return getMessage(resourcebundle, msgid, param);
	}

	public String buildMessage(String bdlid, String msgid, Locale locale,
			String param1, String param2) {
		ResourceBundle resourcebundle;
		if (m_classLoader != null)
			resourcebundle = getResourceBundle(bdlid, locale, m_classLoader);
		else
			resourcebundle = getResourceBundle(bdlid, locale);
		return getMessage(resourcebundle, msgid, param1, param2);
	}

	public String buildMessage(String bdlid, String msgid, Locale locale,
			String params[]) {
		ResourceBundle resourcebundle;
		if (m_classLoader != null)
			resourcebundle = getResourceBundle(bdlid, locale, m_classLoader);
		else
			resourcebundle = getResourceBundle(bdlid, locale);
		return getMessage(resourcebundle, msgid, params);
	}

	public String getMessage(ResourceBundle bundle, String msgid) {
		String msg = bundle.getString(msgid);
		if (msg == null)
			msg = msgid;
		return msg;
	}

	public String getMessage(ResourceBundle bundle, String msgid, String param) {
		return getMessage(bundle, msgid, param, null);
	}

	public String getMessage(ResourceBundle bundle, String msgid,
			String param1, String param2) {
		String params[] = {param1, param2};
		return getMessage(bundle, msgid, params);
	}

	public String getMessage(ResourceBundle bundle, String msgid,
			String params[]) {
		String pattern = bundle.getString(msgid);
		String msg = null;
		if (pattern == null)
			msg = msgid;
		else
			msg = MessageFormat.format(pattern, params);
		return msg;
	}

	public static ResourceBundle getResourceBundle(String bdlid, Locale locale) {
		return ResourceBundle.getBundle(bdlid, locale);
	}

	public static ResourceBundle getResourceBundle(String bdlid, Locale locale,
			ClassLoader loader) {
		return ResourceBundle.getBundle(bdlid, locale, loader);
	}

}
