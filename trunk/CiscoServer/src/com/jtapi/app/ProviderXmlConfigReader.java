/**
 * 
 */
package com.jtapi.app;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.print.Doc;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 *@author 罗尧 Email:j2ee.xiao@gmail.com
 *@version V1.0 2011-3-10 下午06:04:29
 */
public class ProviderXmlConfigReader {

	private static ProviderXmlConfigReader instance = null;

	private Map<String, String> daoFactoryMap = new HashMap<String, String>();

	private ProvideServer callserver = new ProvideServer();

	private ProviderXmlConfigReader() {
		SAXReader reader = new SAXReader();
		//InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("sys-config.xml");
		File in = new File("c:\\CallManagerPhone.xml");
		try {
			Document doc = reader.read(in);

			Element element = (Element)doc.selectObject("/callmanager/providerName");
		    Element elementlogin = (Element)doc.selectObject("/callmanager/login");
		    Element elementpasswd = (Element)doc.selectObject("/callmanager/passwd");

			callserver.setProviderName(element.getStringValue());
		    callserver.setLogin(elementlogin.getStringValue());
			callserver.setPasswd(elementpasswd.getStringValue());

			//System.out.println("完整jdbcURL：" + callserver);

		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	public static synchronized ProviderXmlConfigReader getInstance() {
		if (instance == null) {
			instance = new ProviderXmlConfigReader();
		}
		return instance;
	}

	public ProvideServer getCallServer() {
		return callserver;
	}

	public String getDaoFactory(String name) {
		return daoFactoryMap.get(name);
	}

	public static void main(String[] args) {
		/*
		 * 测试
		String itemDaoFactory = ProviderXmlConfigReader.getInstance().getDaoFactory("item-dao-facotry");
		System.out.println("itemDaoFactory=" + itemDaoFactory);
		ProvideServer jdbcConfig = ProviderXmlConfigReader.getInstance().getCallServer();
		System.out.println(jdbcConfig.getProviderName());
		System.out.println(jdbcConfig.getLogin());
		System.out.println(jdbcConfig.getPasswd());
		System.out.println(jdbcConfig);
		*/
	}
}