package com.crm.framework.web.fileListener;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;

import com.crm.framework.common.beanfactory.CrmBeanFactory;
import com.crm.framework.common.cache.JavaCodeInfoCache;
import com.crm.framework.common.config.PathConvertor;
import com.crm.framework.common.util.FileUtil;
import com.crm.framework.common.util.xml.XmlBeanConvert;
import com.crm.framework.common.util.xml.XmlUtil;

/**
 * 
 * @author 王永明
 * @since 2011-3-9 上午11:01:47
 */
public class FileListenerCache extends JavaCodeInfoCache {

	public FileListenerCache(){
		List<File> files=FileUtil.getAllFile(PathConvertor.getClassPath()+"/fileListener");
		for(File f:files){
			Document doc=XmlUtil.getDocument(f.getAbsolutePath());
			for(Iterator it=doc.getRootElement().elementIterator();it.hasNext();){
				Element el=(Element) it.next();
				FileListenerConfig lis=new FileListenerConfig(); 
				XmlBeanConvert.elementToBean(lis,  el);
				super.set(lis.getFilePath(), lis);
			}
		}
		
	}
	@Override
	protected Object getImpl(String id) {
		throw new RuntimeException("文件监听器缓存不允许调用这个方法");
	}
	
	/**获得所有需要监听的文件*/
	public static List<FileListenerConfig> getListenrs(){
		FileListenerCache c=CrmBeanFactory.getBean(FileListenerCache.class);
		return new ArrayList(c.getMap().values());
	}

}
