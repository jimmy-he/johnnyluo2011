// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   Dom4jUtil.java

package com.tinet.ccic.wm.commons.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.dom4j.Element;
import org.dom4j.QName;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
/**
 * Dom4j相关工具类。
 *<p>
 * 文件名： Dom4jUtil.java
 *<p>
 * Copyright (c) 2006-2010 T&I Net Communication CO.,LTD.  All rights reserved.
 * @author 周营昭
 * @since 1.0
 * @version 1.0
 */

public class Dom4jUtil {

	public Dom4jUtil() {
	}
	
	public static Element child(Element element, String name) {
		return element.element(name);
	}
	
	
	public static List children(Element element, String name) {
		return element.elements(name);
	}

	/**
	 * 获取一个dom元素中的text。
	 * @param element dom元素。
	 * @param name 
	 * @return
	 */
	public static String elementAsString(Element element, String name) {
		String s = element.elementTextTrim(new QName(name, element.getNamespace()));
		return StringUtil.isEmpty(s) ? null : s;
	}
	
	/**
	 * 将一个输入流解析成{@link org.w3c.dom.Document}。
	 * @param xmlContent 输入流。
	 * @return Document
	 * @throws Exception
	 */
	public static Document parseFile(InputStream xmlContent) throws Exception {
		Document document = null;

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(false);
		DocumentBuilder builder = factory.newDocumentBuilder();

		boolean b = builder.isValidating();
		document = builder.parse(xmlContent);// 在这句有时会报错

		return document;

	}
	
	/**
	 * 将一个指定的文件解析成{@link org.w3c.dom.Document}。
	 * @param xmlFile String xml文件的路径。
	 * @return Document
	 * @throws Exception
	 */
	public static Document parseFile(String xmlFile) throws Exception {
		BufferedReader bufferedReader = null;
		DocumentBuilder builder = null;
		Document doc = null;
		
		try {
			bufferedReader = new BufferedReader(new FileReader(xmlFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		InputSource inputsource = new InputSource(bufferedReader);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		try {
			doc = builder.parse(inputsource);
			// doc = builder.parse(thefile);
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return doc;
	}

}
