/**
 * 
 */
package com.jtapi.app;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 *@author 罗尧  Email:j2ee.xiao@gmail.com
 *@version V1.0 2011-3-11   上午10:05:10
 */
public class UDPXmlConfigReader {
	private static UDPXmlConfigReader instance = null;

	private Map<String, String> daoFactoryMap = new HashMap<String, String>();

	private UDPServer udpserver = new UDPServer();

	private UDPXmlConfigReader() {
		SAXReader reader = new SAXReader();
		//InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("sys-config.xml");
		File in = new File("c:\\UDPServer.xml");
		try {
			Document doc = reader.read(in);

			Element element = (Element)doc.selectObject("/udp-server/IPAddress");
		    Element elementlogin = (Element)doc.selectObject("/udp-server/port");

		    udpserver.setIPAddress(element.getStringValue());
		    udpserver.setPort(elementlogin.getStringValue());

			//System.out.println("完整jdbcURL：" + callserver);

		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	public static synchronized UDPXmlConfigReader getInstance() {
		if (instance == null) {
			instance = new UDPXmlConfigReader();
		}
		return instance;
	}

	public UDPServer getUDPServer() {
		return udpserver;
	}

	public String getDaoFactory(String name) {
		return daoFactoryMap.get(name);
	}
	public static void main(String[] args) {
		/*
		 * 测试
		String itemDaoFactory = UDPXmlConfigReader.getInstance().getDaoFactory("item-dao-facotry");
		System.out.println("itemDaoFactory=" + itemDaoFactory);
		UDPServer jdbcConfig = UDPXmlConfigReader.getInstance().getUDPServer();
		System.out.println(jdbcConfig.getIPAddress());
		System.out.println(jdbcConfig.getPort());
		System.out.println(jdbcConfig);
		*/

	}

}
