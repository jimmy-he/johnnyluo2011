package com.crm.framework.web.fileListener;

import java.io.File;

import org.apache.log4j.Logger;
import org.jfree.util.Log;

import com.crm.framework.common.beanfactory.CrmBeanFactory;
import com.crm.framework.common.i18n.DefaultTextProvider;
import com.crm.framework.common.util.file.FileListener;

/**
 * 资源文件监听处理了,资源文件改变的时候重新加载国际化类
 * @author 王永明
 * @since 2011-3-9 上午11:30:13
 */
public class I18nListener implements FileListener {
	private static Logger log=Logger.getLogger(I18nListener.class);
	private FileListenerConfig config;
	
	public void fileChanged(File file) {
		//只要有文件修改就会调用这个方法,所以这里要加个判断只处理资源文件
		if(!config.isListenFile(file)){
			return;
		}
		log.debug("资源文件已经修改,重新加载国际化信息");
		CrmBeanFactory.getBean(DefaultTextProvider.class).init();			
	}
	
	/**初始化监听器是会将配置信息传进来,这里拿一个变量保存住就好了*/
	public void setListenerConfig(FileListenerConfig config) {
		this.config=config;
	}
}
