package com.tinet.ccic.wm.commons.util;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.tinet.ccic.wm.commons.Constants;
import com.tinet.ccic.wm.commons.CCICException;

/**
 * 配置参数管理器，对配置参数进行统一管理，提高取配置参数的效率。
 *<p>
 * 文件名： PropertyXmlMgr.java
 *<p>
 * Copyright (c) 2006-2010 T&I Net Communication CO.,LTD.  All rights reserved.
 * @author 周营昭
 * @since 1.0
 * @version 1.0
 */

public class PropertyXmlMgr {
	/**
	 * 配置文件系统参数,需要在服务器启动文件里配置
	 */
	public static final String CONFIG_FILE = Constants.CONFIG_FILE;
	
	/** 日志管理器 */
	private static Logger logger = LoggerFactory.getLogger(PropertyXmlMgr.class);
	
	/**
	 * 用来缓存配置
	 */
	private static Map<String, Configuration> map = new HashMap<String, Configuration>();

	/**
	 * 用来保存文件名和别名的对应关系
	 */
	private static Map<String, String> fileMap = new HashMap<String, String>();

	/**
	 * web应用的基础路径也就是WEB-INF的路径
	 */
	private static String basePath = null;

	private static boolean flag = false;
	
	/**
	 * 设置绝对路径，一般在jdk环境下运行时，而配置文件又不在相应的环境下，需要配置绝对路径
	 * @param basePath
	 *            要设置的 basePath。
	 */
	public static void setBasePath(String basePath) {
		PropertyXmlMgr.basePath = basePath;
	}

	/**
	 * 用来保存系统各配置文件路径
	 */
	private static final String FILENAME = Constants.XML_FILENAME;

	private static final String classes = "classes/";

	static {
		init(basePath);// 初始化，装载所有配置文件
	}

	
	/**
	 * 装载配置好的所有系统配置文件，只在第一次调用时执行
	 */
	private static synchronized void init(String path) {
		try {
			if (path != null) {
				basePath = path;
			} else {
				// 取配置文件基础路径
				if (basePath == null) {
					File file = null;
					URL url = null;
					basePath = System.getProperty(CONFIG_FILE);//java虚拟机变量定义
					if(basePath != null){
						logger.info("系统参数配置文件{}的路径：{}" , FILENAME, basePath);
					}
					if (basePath == null) {
						if (url == null)// 定位classes目录
						{
							url = PropertyXmlMgr.class.getClass().getResource("/");
							logger.info("系统参数配置文件{}路径：{}", FILENAME, url);
							if (url != null) {
								basePath = url.getPath();
								if (basePath.indexOf("WEB-INF") > 0) {//  /WEB-INF/classes/
									basePath = basePath.substring(0, basePath
											.length() - 8);
								} else {
									basePath = null;
									url = null;
									logger.info("系统参数配置文件{}不在web容器路径。", FILENAME);
								}

							}
						}
						if (url == null) {
							basePath = getFilePath(url, FILENAME);
						}
					}
					
					if(basePath!=null){
					String filePath = basePath + FILENAME;

					file = new File(filePath);

					if (file.exists()) {// 在web-inf/目录下
						readConfigFile(filePath);
						flag=true;
					} else {// 在web-inf/classes/目录下
						filePath = basePath + classes + FILENAME;
						if (new File(filePath).exists()) {
							readConfigFile(filePath);
							flag=true;
						} else {
							logger.info("配置文件{}不存在", FILENAME);
						}
					}
					}else{
						logger.info("未找到配置文件{}", FILENAME);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	/**
	 * @param url
	 * @return
	 */
	private static String getFilePath(URL url, String fileName) {
		String path = null;
		boolean flag = true;
		if (url == null) {
			if (Thread.currentThread().getContextClassLoader() != null) {// classes目录下
				url = Thread.currentThread().getContextClassLoader()
						.getResource(fileName);
				logger.debug("get context url222:{}", url);
				if (url == null) {// web应用根目录
					url = Thread.currentThread().getContextClassLoader().getResource("WEB-INF/");

					if (url == null) {
						url = Thread.currentThread().getContextClassLoader()
								.getResource("/");
					}
					flag = false;
				}
			}
		}
		if (url == null) {
			url = PropertyXmlMgr.class.getClass().getResource("/" + fileName);
		}

		if (url == null) {
			if (PropertyXmlMgr.class.getClass().getClassLoader() != null) {
				url = PropertyXmlMgr.class.getClass().getClassLoader()
						.getResource(fileName);
			}
		}
		if (url != null) {
			path = url.getPath();
			if (basePath == null && flag) {
				path = path.substring(0, path.length()
						- (classes.length() + fileName.length()));
			}
			if (path.indexOf("WEB-INF/classes/") > 0 && !flag) {
				path = path.substring(0, path.length() - 8);
			}
			if (path.indexOf("WEB-INF/classes") > 0 && !flag) {
				path = path.substring(0, path.length() - 7);
			}

		}

		return path;
	}

	/**
	 * 读取FILENAME配置文件,并装载所有配置文件
	 * 
	 * @throws XmlUtilException
	 * @throws Exception
	 */
	private static void readConfigFile(String filePath) throws Exception {
		// 读取FILENAME配置文件
		Document doc = Dom4jUtil.parseFile(filePath);
		Node node = doc.getFirstChild();
		NodeList nodeList = node.getChildNodes();

		for (int i = 0; i < nodeList.getLength(); i++) {
			// 分析是properties文件还是xml文件，如果是properties文件，则
			Node fileNode = (Node) nodeList.item(i);
			if (fileNode != null) {
				if (fileNode.getAttributes() != null) {
					String fileName = fileNode.getAttributes().getNamedItem("fileName").toString();
					fileName = fileName.substring(fileName.indexOf("=") + 2);
					fileName = fileName.substring(0, fileName.indexOf('"'));
					String tempPath = getFilePath(null, fileName);
					
					if (tempPath == null) {
						logger.error("配置文件{}没有找到", fileName);
					} else {
						fileMap.put(fileNode.getFirstChild().getNodeValue(), tempPath.toLowerCase());
					}
					
					setConfiguration(fileNode.getFirstChild().getNodeValue(), tempPath);
				}
			}

		}
	}
	
	private static void setConfiguration(String alias, String filePath) throws Exception{
		Configuration configuration = null;
		
		if (filePath.endsWith("properties")) {
			configuration = getPropertiesConfiguration(filePath);
		} else if (filePath.endsWith("xml")) {
			configuration = getXMLConfiguration(filePath);
		}
		
		map.put(alias, configuration);
	}
	
	/**
	 * 取指定Properties配置文件中的所有配置参数
	 * 
	 * @param alias
	 *            文件别名
	 * @return
	 * @throws Exception
	 */
	private static synchronized Configuration getPropertiesConfiguration(String filePath) {
		try {
			return new PropertiesConfiguration(filePath);
		} catch (ConfigurationException e) {
			logger.error("在读取配置文件{}时出错，请检查配置文件中的配置项。", filePath);
			throw new CCICException("SYS04004", new String[]{filePath});
		}	
	}

	
	/**
	 * 取指定XML配置文件中的所有配置参数
	 * 
	 * @param alias
	 *            别名
	 * @return
	 * @throws Exception
	 */
	private static synchronized XMLConfiguration getXMLConfiguration(String filePath) {
		XMLConfiguration configuration = new XMLConfiguration();
		File file = new File(filePath);
		
		configuration.setFile(file);
		try {
			configuration.load();
		} catch (ConfigurationException e) {
			logger.error("在读取配置文件{}时出错，请检查配置文件中的配置项。", filePath);
			throw new CCICException("SYS04004", new String[]{filePath});
		}
		return configuration;
	}


	
	
	/**
	 * 从Property配置文件中取配置参数
	 * 
	 * @param alias 配置文件别名
	 * @param key 配置参数名
	 * @return 配置参数的值。如果未取到，则返回空字符串。
	 */
	public static String getPropertyToString(String alias, String key) {
		try {
			getFileBySetPath();
			Configuration configuration = map.get(alias);
			if (configuration != null) {
				List list = configuration.getList(key);

				if (list != null && list.size() > 0) {
					String temp = list.toString();
					return temp.substring(1, temp.length() - 1);
				} 
			} else {
				basePath=null;
				init(null);
			}
			return null;

		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	private static void getFileBySetPath() throws Exception {
		if(!flag){
			if(basePath!=null){
				String fileName = basePath+"/"+FILENAME;
				File file = new File(fileName);
				if (file.exists()) {
					readConfigFile(fileName);
					flag = true;
			}
			}
		}
	}

	/**
	 * 从Property配置文件中取配置参数,并转化为List
	 * 
	 * @param alias 配置文件别名
	 * @param key 配置参数名
	 * @return 配置参数的值。如果未取到，则返回空字符串。
	 */
	public static List getPropertyToList(String alias, String key) {
		try {
			getFileBySetPath();
			Configuration configuration = map.get(alias);
			if (configuration != null) {
				List list = configuration.getList(key);
				if (list != null) {
					return list;
				}
			} else {
				basePath=null;
				init(null);
			}
			return null;

		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * 从xml配置文件中取配置参数
	 * 
	 * @param alias 配置文件别名
	 * @param key xpath路径
	 * @return 配置参数的值。如果未取到，则返回null。
	 */
	public static String getPropertyByXpath(String alias, String key) {
		try {
			getFileBySetPath();
			Configuration configuration = map.get(alias);
			if (configuration != null) {
				String str = (String) configuration.getProperty(key);
				if (str != null) {
					return str;
				} 
			} else {
				basePath=null;
				init(null);
			}
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 刷新参数absolutePath指定配置文件的配置属性。
	 * 
	 * @param absolutePath 文件绝对路径
	 */
	public static void refreshPropertiesInFile(String absolutePath) {
		String alias = null;
		String lowAbsolutePath = absolutePath.toLowerCase();
		
		for (Map.Entry<String, String> entry : fileMap.entrySet()) {
			if (entry.getValue().endsWith(lowAbsolutePath)) {
				alias = entry.getKey();
				break;
			}
		}
		
		if (alias == null) {
			throw new java.lang.IllegalArgumentException("The file indexed by absolutePath is not managered by this Class");
		}
		
		Configuration configuration = PropertyXmlMgr.getPropertiesConfiguration(absolutePath);
		map.put(alias, configuration);
	}
}