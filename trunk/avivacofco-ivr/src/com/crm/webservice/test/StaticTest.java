/**
 * 
 */
package com.crm.webservice.test;

import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

/**
 *@author 罗尧  Email:j2ee.xiao@gmail.com
 *@version V1.0 2010-8-6   下午02:51:00
 */
public class StaticTest {

	public static Document document;
	public static Document sdocument;
	static {
		try {
			SAXReader reader = new SAXReader();
			InputStream xmlFilePath = Thread.currentThread().getContextClassLoader().getResourceAsStream("avivacofco.xml");
			InputStream manager = Thread.currentThread().getContextClassLoader().getResourceAsStream("system.xml");
			document = reader.read(xmlFilePath);
			sdocument=reader.read(manager);
		} catch (DocumentException e) {
			e.printStackTrace();
		}finally{
			
		}
	}
	
}
