package com.crm.framework.web.fileListener;

import java.io.File;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import com.crm.framework.common.Global;
import com.crm.framework.common.PlatformConfig;
import com.crm.framework.common.util.BeanUtil;
import com.crm.framework.common.util.StringUtil;
import com.crm.framework.common.util.file.FileListener;
import com.crm.framework.common.util.xml.SpringConfigUtil;
import com.crm.framework.common.util.xml.XmlUtil;
import com.crm.framework.common.vo.SystemInfo;

/**
 * 
 * @author 王永明
 * @since 2011-3-9 下午02:09:34
 */
public class ApplicationConfigListener implements FileListener {
	private static Logger log=Logger.getLogger(ApplicationConfigListener.class);
private FileListenerConfig config;
	public void fileChanged(File file) {
		if(!config.isListenFile(file)){
			return;
		}
		this.reConfig(file.getAbsolutePath());
		log.debug("applicationContext-config.xml改变");
		
	}
	
	private void reConfig(String path){
		Document document = XmlUtil.getDocument(path);
		Element beans = document.getRootElement();
		for (Iterator it = beans.elementIterator(); it.hasNext();) {
			Element bean = (Element) it.next();
			String clazz = bean.attributeValue("class");
			Class clas;
			try {
				clas = Class.forName(clazz);
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
			String clId = bean.attributeValue("id");
			if (clId == null) {
				clId = StringUtil.firstLower(clas.getName());
			}
			if (clId.equals("platformConfig")) {
				try {
					PlatformConfig reValue =(PlatformConfig) clas.newInstance();
					for(Iterator i=bean.elementIterator();i.hasNext();){
						
						Element p=(Element)i.next();
						String name=p.attributeValue("name");
						Object value=p.attributeValue("value");
						if(value.equals("true")||value.equals("false")){
							value=Boolean.parseBoolean(value+"");
						}
						BeanUtil.setField(reValue, name, value);
						
						
						
					}	
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	public void setListenerConfig(FileListenerConfig config) {
		this.config=config;
	}
	
	public static void main(String[] args) {
		boolean value=Boolean.parseBoolean("false");
		System.out.println(value);
	}

}
