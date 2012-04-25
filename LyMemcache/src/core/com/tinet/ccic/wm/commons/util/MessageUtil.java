package com.tinet.ccic.wm.commons.util;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.ObjectMessage;
/**
 * 消息工具类。
 *<p>
 * 文件名： MessageUtil.java
 *<p>
 * Copyright (c) 2006-2010 T&I Net Communication CO.,LTD.  All rights reserved.
 * @author 周营昭
 * @since 1.0
 * @version 1.0
 */
public class MessageUtil {
	public static Map getHashFromMessage(MapMessage me) throws JMSException {
		Map map = new HashMap();
		Enumeration en = me.getMapNames();
		while (en.hasMoreElements()) {
			String key = (String) en.nextElement();
			XMLDecoder decoder = new XMLDecoder(new BufferedInputStream( new ByteArrayInputStream(
		            (byte[])(me.getObject(key)))));
			Object obj = (Object)decoder.readObject();
			decoder.close();
			map.put(key, obj);
		}
		if(map.size()>0){
			return map;
		}else{
			return null;
		}
	}
	public static List getListFromMessage(ObjectMessage message) throws JMSException {
		XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(
				new ByteArrayInputStream((byte[]) (message
						.getObjectProperty("List")))));
		List obj = (List) decoder.readObject();
		decoder.close();
		return obj;
	}
}
