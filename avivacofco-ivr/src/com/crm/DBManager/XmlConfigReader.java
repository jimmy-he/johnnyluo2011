package com.crm.DBManager;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


/**
 *@author 罗尧  Email:j2ee.xiao@gmail.com
 *@version V1.0 2010-10-8   下午03:24:19
 */
public class XmlConfigReader {


	
	private static XmlConfigReader instance = null;

	private Map<String, String> daoFactoryMap = new HashMap<String, String>();
	
	
	private JdbcConfig jdbcConfig = new JdbcConfig();
	
	private XmlConfigReader() {
		SAXReader reader = new SAXReader();
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("sys-config.xml");
		try {
			Document doc = reader.read(in);
			
			
			Element driverNameElt = (Element)doc.selectObject("/config/db-info/driver-name");
			Element urlElt = (Element)doc.selectObject("/config/db-info/url");
			Element userNameElt = (Element)doc.selectObject("/config/db-info/user-name");
			Element passwordElt = (Element)doc.selectObject("/config/db-info/password");
			
			
			jdbcConfig.setDriverName(driverNameElt.getStringValue());
			jdbcConfig.setUrl(urlElt.getStringValue());
			jdbcConfig.setUserName(userNameElt.getStringValue());
			jdbcConfig.setPassword(passwordElt.getStringValue());
			
			System.out.println("完整jdbcURL：" + jdbcConfig);

		} catch (DocumentException e) {
			e.printStackTrace();
		}			
	}
	
	public static synchronized XmlConfigReader getInstance() {
		if (instance == null) {
			instance = new XmlConfigReader();
		}
		return instance;
	}
	
	
	public JdbcConfig getJdbcConfig() {
		return jdbcConfig;
	}

	public String getDaoFactory(String name) {
		return daoFactoryMap.get(name);
	}
	
	public static void main(String[] args) {
		String itemDaoFactory = XmlConfigReader.getInstance().getDaoFactory("item-dao-facotry");
		System.out.println("itemDaoFactory=" + itemDaoFactory);
		JdbcConfig jdbcConfig = XmlConfigReader.getInstance().getJdbcConfig();
		System.out.println(jdbcConfig.getDriverName());
		System.out.println(jdbcConfig.getUrl());
		System.out.println(jdbcConfig.getUserName());
		System.out.println(jdbcConfig);
	}
}
